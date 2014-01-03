package com.touchtype_fluency.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touchtype.backup.SafeBackupRequest;
import com.touchtype.common.iris.IrisDataSenderService;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.report.json.UserStatsReport;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.settings.LanguagePreferenceSetting.LanguagePreferenceActivity;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.util.LogUtil;
import com.touchtype.util.WeakHashSet;
import com.touchtype_fluency.LicenseException;
import com.touchtype_fluency.LoggingListener;
import com.touchtype_fluency.LoggingListener.Level;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.SwiftKeySDK;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.AndroidLanguagePackModelStorage;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackInstaller;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManager;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManagerImpl;
import com.touchtype_fluency.service.personalize.PersonalizationToggleReceiver;
import com.touchtype_fluency.service.util.MyPathClassLoader;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import junit.framework.Assert;

public class FluencyServiceImpl
  extends IntentService
  implements FluencyService
{
  public static final String ACTION_FORCED_REFRESH_LANGUAGES = "com.touchtype.FORCED_REFRESH_LANGUAGES";
  public static final String ACTION_REFRESH_LANGUAGE_CONFIGURATION = "com.touchtype.REFRESH_CONFIGURATION";
  public static final String ACTION_REPORT_TO_IRIS = "com.touchtype.REPORT_TO_IRIS";
  private static final int FLUENCY_RETRY_WAIT = 400;
  private static final String FLUENCY_SERVICE_ACTIONS_SHARED_PREFERENCES = "FluencyServiceActions";
  static final String FLUENCY_STATS_FILE = "fluency_stats.json";
  public static final String RESCHEDULE = "reschedule";
  private static final String TAG = FluencyServiceImpl.class.getSimpleName();
  private final IBinder binder = new LocalBinder();
  private FluencyMetrics fluencyMetrics;
  private File fluencyMetricsStorageFile;
  private FolderDecorator folder;
  private Handler handler;
  private LayoutManager layoutManager;
  private final Set<Listener> listeners = new WeakHashSet();
  private final LoadFluencyTask loadFluencyTask = new LoadFluencyTask(null);
  private DynamicModelHandler mDynamicModelHandler;
  private Session mFluency;
  private LanguageLoader mLanguageLoader;
  private LanguagePackManager mLanguagePackManager;
  private PredictorImpl mPredictor;
  private UserNotificationManager mUserNotificationManager;
  private final Object monitor = this;
  
  public FluencyServiceImpl()
  {
    super("FluencyService");
  }
  
  private void forcedRefreshLanguages()
  {
    getLanguagePackManager().forciblyUpdateConfiguration();
  }
  
  public static Date getExpiry()
  {
    return new Date(1000L * SwiftKeySDK.getExpiry(FluencyLicense.getFluencyLicense()));
  }
  
  private String getExtendedError()
  {
    String str1 = getPackageName();
    for (;;)
    {
      int k;
      int n;
      int i2;
      try
      {
        String str2 = getPackageManager().getApplicationInfo(str1, 0).sourceDir;
        String str3 = System.getProperty("java.library.path");
        StringBuilder localStringBuilder = new StringBuilder();
        localStringBuilder.append("Trying to find shared lib in " + str2 + " with package name " + str1 + ". Library path is " + str3 + ". ");
        int i = 0;
        String[] arrayOfString = str3.split(":");
        int j = arrayOfString.length;
        k = 0;
        if (k < j)
        {
          String str4 = arrayOfString[k];
          File localFile1 = new File(str4);
          File[] arrayOfFile1 = localFile1.listFiles();
          localStringBuilder.append("Searching in java.library.path " + str4 + ": ");
          if (arrayOfFile1 == null) {
            break label463;
          }
          int m = arrayOfFile1.length;
          n = 0;
          if (n >= m) {
            break label463;
          }
          File localFile2 = arrayOfFile1[n];
          if (localFile2.toString().toLowerCase().contains("swiftkeysdk"))
          {
            localStringBuilder.append("Found lib in " + localFile2.getCanonicalPath());
            i = 1;
          }
        }
        else
        {
          String str5 = getFilesDir().toString().replace("/files", "") + "/lib";
          localStringBuilder.append("Searching in private app folder " + str5 + ": ");
          File localFile3 = new File(str5);
          File[] arrayOfFile2 = localFile3.listFiles();
          if (arrayOfFile2 != null)
          {
            int i1 = arrayOfFile2.length;
            i2 = 0;
            if (i2 < i1)
            {
              File localFile4 = arrayOfFile2[i2];
              if (!localFile4.toString().toLowerCase().contains("swiftkeysdk")) {
                break label469;
              }
              localStringBuilder.append(" Found lib in " + localFile4.getCanonicalPath());
              i = 1;
              break label469;
            }
          }
          if (i == 0) {
            localStringBuilder.append("***** libswiftkeysdk not found! *****");
          }
          String str6 = localStringBuilder.toString();
          return str6;
        }
      }
      catch (Exception localException)
      {
        return "File system or other system exception (" + localException.getClass().getName() + "): " + localException.getMessage();
      }
      n++;
      continue;
      label463:
      k++;
      continue;
      label469:
      i2++;
    }
  }
  
  private boolean getFluency()
  {
    try
    {
      this.fluencyMetrics.getLoadSession().start();
      this.mFluency = SwiftKeySDK.createSession(FluencyLicense.getFluencyLicense());
      Assert.assertNotNull(this.mFluency);
      this.fluencyMetrics.getLoadSession().stop();
      SwiftKeySDK.setLoggingListener(new LoggingListener()
      {
        private static final String TAG = "Fluency_Log";
        
        public void log(LoggingListener.Level paramAnonymousLevel, String paramAnonymousString)
        {
          switch (FluencyServiceImpl.4.$SwitchMap$com$touchtype_fluency$LoggingListener$Level[paramAnonymousLevel.ordinal()])
          {
          default: 
            LogUtil.e("Fluency_Log", paramAnonymousString);
          }
        }
      });
      return true;
    }
    catch (ExceptionInInitializerError localExceptionInInitializerError)
    {
      LogUtil.w(TAG, "Failed to load Fluency's native library");
      return false;
    }
    catch (LicenseException localLicenseException) {}
    return true;
  }
  
  private FluencyMetrics getFluencyMetrics(File paramFile)
  {
    FluencyMetrics localFluencyMetrics;
    if (!paramFile.exists()) {
      localFluencyMetrics = new FluencyMetrics();
    }
    for (;;)
    {
      return localFluencyMetrics;
      try
      {
        String str = Files.toString(paramFile, Charsets.UTF_8);
        localFluencyMetrics = (FluencyMetrics)new Gson().fromJson(str, FluencyMetrics.class);
        if (localFluencyMetrics != null) {
          continue;
        }
        return new FluencyMetrics();
      }
      catch (IOException localIOException)
      {
        for (;;)
        {
          LogUtil.e(TAG, localIOException.getMessage(), localIOException);
          localFluencyMetrics = null;
        }
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        for (;;)
        {
          LogUtil.e(TAG, localJsonSyntaxException.getMessage(), localJsonSyntaxException);
          localFluencyMetrics = null;
        }
      }
    }
  }
  
  private LanguageLoader getLanguageLoader()
  {
    return this.mLanguageLoader;
  }
  
  private void handleActionIfPresent(Intent paramIntent)
  {
    if (paramIntent.getAction() != null) {
      startService(paramIntent);
    }
  }
  
  private void notifyListenersOnMainThread()
  {
    this.handler.post(new Runnable()
    {
      public void run()
      {
        synchronized (FluencyServiceImpl.this.monitor)
        {
          Iterator localIterator = FluencyServiceImpl.this.listeners.iterator();
          if (localIterator.hasNext()) {
            ((FluencyServiceImpl.Listener)localIterator.next()).onReady();
          }
        }
        FluencyServiceImpl.this.listeners.clear();
      }
    });
  }
  
  private void onFirstRun(Context paramContext, SwiftKeyPreferences paramSwiftKeyPreferences)
  {
    paramSwiftKeyPreferences.putLong("first_run_time", System.currentTimeMillis());
    InstallerPreferences localInstallerPreferences = InstallerPreferences.newInstance(paramContext);
    if ((!localInstallerPreferences.isInstallComplete(paramContext)) && (ProductConfiguration.hasBundledLanguagePacks(paramContext))) {}
    try
    {
      LanguagePackInstaller.installBundledLanguagePacks(paramContext);
      localInstallerPreferences.setInstallerCompleted();
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
  }
  
  private void refreshConfiguration()
  {
    final CountDownLatch localCountDownLatch = new CountDownLatch(1);
    MultipleDownloadListener local2 = new MultipleDownloadListener()
    {
      private int retriesForConfiguration = 3;
      
      public void onCompletion(Set<DownloadListener.Result> paramAnonymousSet)
      {
        if ((paramAnonymousSet.contains(DownloadListener.Result.CONFIG_FAILURE)) && (this.retriesForConfiguration > 0))
        {
          this.retriesForConfiguration = (-1 + this.retriesForConfiguration);
          FluencyServiceImpl.this.getLanguagePackManager().scheduledDownloadConfiguration(this);
          return;
        }
        if ((paramAnonymousSet.contains(DownloadListener.Result.NETWORK_FAILURE)) || (paramAnonymousSet.contains(DownloadListener.Result.CONFIG_FAILURE)))
        {
          FluencyServiceImpl.rerunActionOnConnection(FluencyServiceImpl.this, "com.touchtype.REFRESH_CONFIGURATION", true);
          localCountDownLatch.countDown();
          return;
        }
        FluencyServiceImpl.rerunActionOnConnection(FluencyServiceImpl.this, "com.touchtype.REFRESH_CONFIGURATION", false);
        localCountDownLatch.countDown();
      }
    };
    getLanguagePackManager().scheduledDownloadConfiguration(local2);
    try
    {
      localCountDownLatch.await();
      return;
    }
    catch (InterruptedException localInterruptedException)
    {
      Thread.currentThread().interrupt();
    }
  }
  
  private void reportToIris()
  {
    Context localContext = getApplicationContext();
    startService(IrisDataSenderService.sendDataIntent(localContext, getString(2131296332), new Gson().toJson(new UserStatsReport(localContext, this.fluencyMetrics, this.mLanguagePackManager), UserStatsReport.class)));
    TouchTypeStats localTouchTypeStats = TouchTypePreferences.getInstance(localContext).getTouchTypeStats();
    resetFluencyMetrics();
    localTouchTypeStats.resetLanguageModelMetrics();
  }
  
  public static void rerunActionOnConnection(Context paramContext, String paramString, boolean paramBoolean)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences("FluencyServiceActions", 0).edit();
    localEditor.putBoolean(paramString, paramBoolean);
    localEditor.commit();
  }
  
  public static void rerunActionsNowConnected(Context paramContext)
  {
    SharedPreferences localSharedPreferences = paramContext.getSharedPreferences("FluencyServiceActions", 0);
    Iterator localIterator = localSharedPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (localSharedPreferences.getBoolean(str, false))
      {
        rerunActionOnConnection(paramContext, str, false);
        startServiceForAction(str, paramContext);
      }
    }
  }
  
  private void resetFluencyMetrics()
  {
    this.fluencyMetrics.reset();
    saveFluencyMetrics();
  }
  
  public static void startServiceForAction(String paramString, Context paramContext)
  {
    try
    {
      Intent localIntent = new Intent(paramString);
      localIntent.setClass(paramContext, FluencyServiceImpl.class);
      paramContext.startService(localIntent);
      return;
    }
    catch (SecurityException localSecurityException)
    {
      LogUtil.e(TAG, "SecurityException when attempting to start FluencyServiceImpl.ACTION_FORCED_REFRESH_LANGUAGES");
      LogUtil.e(TAG, localSecurityException.getMessage(), localSecurityException);
    }
  }
  
  public void addListener(Listener paramListener)
  {
    synchronized (this.monitor)
    {
      if (isReady())
      {
        paramListener.onReady();
        return;
      }
      this.listeners.add(paramListener);
    }
  }
  
  public DynamicModelHandler getDynamicModelHandler()
  {
    return this.mDynamicModelHandler;
  }
  
  public File getFolder()
  {
    return this.folder.getBaseFolder();
  }
  
  public LanguagePackManager getLanguagePackManager()
  {
    return this.mLanguagePackManager;
  }
  
  public LayoutManager getLayoutManager()
  {
    return this.layoutManager;
  }
  
  public Predictor getPredictor()
  {
    synchronized (this.monitor)
    {
      if (this.mPredictor == null) {
        LogUtil.e(TAG, "Predictor is null");
      }
      PredictorImpl localPredictorImpl = this.mPredictor;
      return localPredictorImpl;
    }
  }
  
  public Session getSession()
  {
    return this.mFluency;
  }
  
  public UserNotificationManager getUserNotificationManager()
  {
    return UserNotificationManager.getInstance(getApplicationContext());
  }
  
  public boolean isReady()
  {
    synchronized (this.monitor)
    {
      if (this.mPredictor != null)
      {
        bool = true;
        return bool;
      }
      boolean bool = false;
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    handleActionIfPresent(paramIntent);
    return this.binder;
  }
  
  public void onCreate()
  {
    super.onCreate();
    Context localContext = getApplicationContext();
    Assert.assertNull(this.mUserNotificationManager);
    this.mUserNotificationManager = UserNotificationManager.getInstance(localContext);
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(localContext);
    if (localTouchTypePreferences.isFlagSet("first_run_key"))
    {
      localTouchTypePreferences.clearFlag("first_run_key");
      onFirstRun(localContext, localTouchTypePreferences);
    }
    this.folder = AndroidLanguagePackModelStorage.getInstance(localContext).getDynamicModelDirectory();
    localContext.getFilesDir().mkdirs();
    this.fluencyMetricsStorageFile = new File(localContext.getFilesDir(), "fluency_stats.json");
    this.fluencyMetrics = getFluencyMetrics(this.fluencyMetricsStorageFile);
    this.mLanguagePackManager = new LanguagePackManager(localContext);
    this.mLanguageLoader = new LanguageLoader(localContext);
    this.mDynamicModelHandler = new DynamicModelHandler(localContext);
    this.layoutManager = new LayoutManagerImpl(getLanguagePackManager(), localContext);
    this.handler = new Handler();
    this.loadFluencyTask.start();
    SafeBackupRequest.requestBackupIfNewVersion(this);
  }
  
  public void onDestroy()
  {
    this.mUserNotificationManager.onDestroy();
    this.mUserNotificationManager = null;
    synchronized (this.monitor)
    {
      if (this.mPredictor != null)
      {
        this.mPredictor.onDestroy();
        this.mPredictor = null;
      }
      super.onDestroy();
      return;
    }
  }
  
  protected void onHandleIntent(Intent paramIntent)
  {
    this.loadFluencyTask.awaitCompletion();
    String str = paramIntent.getAction();
    if (isReady())
    {
      if (!"com.touchtype.REFRESH_CONFIGURATION".equals(str)) {
        break label33;
      }
      refreshConfiguration();
    }
    label33:
    do
    {
      return;
      if ("com.touchtype.FORCED_REFRESH_LANGUAGES".equals(str))
      {
        forcedRefreshLanguages();
        return;
      }
    } while (!"com.touchtype.REPORT_TO_IRIS".equals(str));
    reportToIris();
  }
  
  public void saveFluencyMetrics()
  {
    try
    {
      Files.write(this.fluencyMetrics.toJSON(), this.fluencyMetricsStorageFile, Charsets.UTF_8);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, localIOException.getMessage(), localIOException);
    }
  }
  
  public void showManagementUI(Context paramContext)
  {
    paramContext.startActivity(new Intent(this, LanguagePreferenceSetting.LanguagePreferenceActivity.class));
  }
  
  public static abstract interface Listener
  {
    public abstract void onReady();
  }
  
  private class LoadFluencyTask
    extends Thread
  {
    private LoadFluencyTask()
    {
      super();
    }
    
    /* Error */
    private String installLibFluency(String paramString1, String paramString2)
    {
      // Byte code:
      //   0: aconst_null
      //   1: astore_3
      //   2: aconst_null
      //   3: astore 4
      //   5: aconst_null
      //   6: astore 5
      //   8: new 24	java/io/FileInputStream
      //   11: dup
      //   12: aload_1
      //   13: invokespecial 25	java/io/FileInputStream:<init>	(Ljava/lang/String;)V
      //   16: astore 6
      //   18: new 27	java/util/zip/ZipInputStream
      //   21: dup
      //   22: aload 6
      //   24: invokespecial 30	java/util/zip/ZipInputStream:<init>	(Ljava/io/InputStream;)V
      //   27: astore 7
      //   29: aload 7
      //   31: invokevirtual 34	java/util/zip/ZipInputStream:getNextEntry	()Ljava/util/zip/ZipEntry;
      //   34: astore 10
      //   36: aconst_null
      //   37: astore_3
      //   38: aload 10
      //   40: ifnull +87 -> 127
      //   43: aconst_null
      //   44: astore 11
      //   46: aload 10
      //   48: invokevirtual 40	java/util/zip/ZipEntry:getName	()Ljava/lang/String;
      //   51: astore 13
      //   53: aload 13
      //   55: ldc 42
      //   57: invokevirtual 48	java/lang/String:contains	(Ljava/lang/CharSequence;)Z
      //   60: istore 14
      //   62: aconst_null
      //   63: astore 11
      //   65: iload 14
      //   67: ifeq +72 -> 139
      //   70: new 50	java/io/File
      //   73: dup
      //   74: aload_2
      //   75: new 50	java/io/File
      //   78: dup
      //   79: aload 13
      //   81: invokespecial 51	java/io/File:<init>	(Ljava/lang/String;)V
      //   84: invokevirtual 52	java/io/File:getName	()Ljava/lang/String;
      //   87: invokespecial 55	java/io/File:<init>	(Ljava/lang/String;Ljava/lang/String;)V
      //   90: astore 15
      //   92: aload 15
      //   94: invokestatic 61	org/apache/commons/io/FileUtils:openOutputStream	(Ljava/io/File;)Ljava/io/FileOutputStream;
      //   97: astore 11
      //   99: aload 7
      //   101: aload 11
      //   103: invokestatic 67	org/apache/commons/io/IOUtils:copy	(Ljava/io/InputStream;Ljava/io/OutputStream;)I
      //   106: pop
      //   107: aload 15
      //   109: invokevirtual 70	java/io/File:getAbsolutePath	()Ljava/lang/String;
      //   112: astore 17
      //   114: aload 17
      //   116: astore_3
      //   117: aload 7
      //   119: invokevirtual 74	java/util/zip/ZipInputStream:closeEntry	()V
      //   122: aload 11
      //   124: invokestatic 78	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/OutputStream;)V
      //   127: aload 7
      //   129: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   132: aload 6
      //   134: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   137: aload_3
      //   138: areturn
      //   139: aload 7
      //   141: invokevirtual 74	java/util/zip/ZipInputStream:closeEntry	()V
      //   144: aconst_null
      //   145: invokestatic 78	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/OutputStream;)V
      //   148: goto -119 -> 29
      //   151: astore 9
      //   153: aload 7
      //   155: astore 5
      //   157: aload 6
      //   159: astore 4
      //   161: invokestatic 85	com/touchtype_fluency/service/FluencyServiceImpl:access$500	()Ljava/lang/String;
      //   164: new 87	java/lang/StringBuilder
      //   167: dup
      //   168: ldc 89
      //   170: invokespecial 90	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   173: aload 9
      //   175: invokevirtual 93	java/lang/Exception:getMessage	()Ljava/lang/String;
      //   178: invokevirtual 97	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
      //   181: invokevirtual 100	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   184: invokestatic 105	com/touchtype/util/LogUtil:e	(Ljava/lang/String;Ljava/lang/String;)V
      //   187: aload 5
      //   189: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   192: aload 4
      //   194: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   197: aload_3
      //   198: areturn
      //   199: astore 12
      //   201: aload 7
      //   203: invokevirtual 74	java/util/zip/ZipInputStream:closeEntry	()V
      //   206: aload 11
      //   208: invokestatic 78	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/OutputStream;)V
      //   211: aload 12
      //   213: athrow
      //   214: astore 8
      //   216: aload 7
      //   218: astore 5
      //   220: aload 6
      //   222: astore 4
      //   224: aload 5
      //   226: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   229: aload 4
      //   231: invokestatic 80	org/apache/commons/io/IOUtils:closeQuietly	(Ljava/io/InputStream;)V
      //   234: aload 8
      //   236: athrow
      //   237: astore 8
      //   239: goto -15 -> 224
      //   242: astore 8
      //   244: aload 6
      //   246: astore 4
      //   248: aconst_null
      //   249: astore 5
      //   251: goto -27 -> 224
      //   254: astore 9
      //   256: aconst_null
      //   257: astore 4
      //   259: aconst_null
      //   260: astore_3
      //   261: aconst_null
      //   262: astore 5
      //   264: goto -103 -> 161
      //   267: astore 9
      //   269: aload 6
      //   271: astore 4
      //   273: aconst_null
      //   274: astore_3
      //   275: aconst_null
      //   276: astore 5
      //   278: goto -117 -> 161
      // Local variable table:
      //   start	length	slot	name	signature
      //   0	281	0	this	LoadFluencyTask
      //   0	281	1	paramString1	String
      //   0	281	2	paramString2	String
      //   1	274	3	localObject1	Object
      //   3	269	4	localObject2	Object
      //   6	271	5	localObject3	Object
      //   16	254	6	localFileInputStream	java.io.FileInputStream
      //   27	190	7	localZipInputStream	java.util.zip.ZipInputStream
      //   214	21	8	localObject4	Object
      //   237	1	8	localObject5	Object
      //   242	1	8	localObject6	Object
      //   151	23	9	localException1	Exception
      //   254	1	9	localException2	Exception
      //   267	1	9	localException3	Exception
      //   34	13	10	localZipEntry	java.util.zip.ZipEntry
      //   44	163	11	localFileOutputStream	java.io.FileOutputStream
      //   199	13	12	localObject7	Object
      //   51	29	13	str1	String
      //   60	6	14	bool	boolean
      //   90	18	15	localFile	File
      //   112	3	17	str2	String
      // Exception table:
      //   from	to	target	type
      //   29	36	151	java/lang/Exception
      //   117	127	151	java/lang/Exception
      //   139	148	151	java/lang/Exception
      //   201	214	151	java/lang/Exception
      //   46	62	199	finally
      //   70	114	199	finally
      //   29	36	214	finally
      //   117	127	214	finally
      //   139	148	214	finally
      //   201	214	214	finally
      //   8	18	237	finally
      //   161	187	237	finally
      //   18	29	242	finally
      //   8	18	254	java/lang/Exception
      //   18	29	267	java/lang/Exception
    }
    
    private void loadLibFluency()
    {
      FluencyServiceImpl.access$302(FluencyServiceImpl.this, null);
      try
      {
        String str2 = checkLibFluency(FluencyServiceImpl.this);
        if (str2 != null) {
          System.setProperty("com.touchtype_fluency.nativeLibrary", str2);
        }
        boolean bool3 = FluencyServiceImpl.this.getFluency();
        bool1 = bool3;
      }
      catch (NoClassDefFoundError localNoClassDefFoundError2)
      {
        for (;;)
        {
          bool1 = false;
        }
      }
      catch (UnsatisfiedLinkError localUnsatisfiedLinkError1)
      {
        for (;;)
        {
          label62:
          label83:
          boolean bool1 = false;
        }
      }
      if (!bool1) {
        LogUtil.w(FluencyServiceImpl.TAG, "Failed to get Fluency instance on first attempt");
      }
      try
      {
        Thread.sleep(400L);
        FluencyServiceImpl.access$302(FluencyServiceImpl.this, null);
        try
        {
          boolean bool2 = FluencyServiceImpl.this.getFluency();
          bool1 = bool2;
        }
        catch (NoClassDefFoundError localNoClassDefFoundError1)
        {
          String str1;
          break label83;
        }
        catch (UnsatisfiedLinkError localUnsatisfiedLinkError2)
        {
          break label83;
        }
        if (!bool1)
        {
          LogUtil.e(FluencyServiceImpl.TAG, "Failed to get Fluency instance on second attempt");
          str1 = FluencyServiceImpl.this.getExtendedError();
          LogUtil.e(FluencyServiceImpl.TAG, str1);
          throw new ExceptionInInitializerError(str1);
        }
        synchronized (FluencyServiceImpl.this.monitor)
        {
          Assert.assertNull(FluencyServiceImpl.this.mPredictor);
          FluencyServiceImpl.access$702(FluencyServiceImpl.this, new PredictorImpl(FluencyServiceImpl.this.folder, FluencyServiceImpl.this.getLanguagePackManager(), FluencyServiceImpl.this.getLanguageLoader(), FluencyServiceImpl.this.getDynamicModelHandler(), FluencyServiceImpl.this.getUserNotificationManager(), FluencyServiceImpl.this.fluencyMetrics));
          Context localContext = FluencyServiceImpl.this.getApplicationContext();
          if (FluencyServiceImpl.this.mFluency != null) {
            FluencyServiceImpl.this.mFluency.setParameterLearning(ProductConfiguration.getSDKParameterLearning(localContext));
          }
          FluencyServiceImpl.this.mPredictor.onCreate(FluencyServiceImpl.this.mFluency, localContext);
          new PersonalizationToggleReceiver().checkAlarmSet(localContext);
          FluencyServiceImpl.this.notifyListenersOnMainThread();
          return;
        }
      }
      catch (InterruptedException localInterruptedException)
      {
        break label62;
      }
    }
    
    public void awaitCompletion()
    {
      try
      {
        join();
        return;
      }
      catch (InterruptedException localInterruptedException) {}
    }
    
    public String checkLibFluency(Context paramContext)
    {
      String str1;
      try
      {
        ApplicationInfo localApplicationInfo = paramContext.getPackageManager().getApplicationInfo(paramContext.getPackageName(), 0);
        String str2 = new File(localApplicationInfo.dataDir, "lib").getAbsolutePath();
        if (new MyPathClassLoader(localApplicationInfo.sourceDir, str2, paramContext.getClassLoader()).myFindLibrary("swiftkeysdk-java-internal") != null) {
          return null;
        }
        str1 = new MyPathClassLoader(localApplicationInfo.sourceDir, localApplicationInfo.dataDir, paramContext.getClassLoader()).myFindLibrary("swiftkeysdk-java-internal");
        if (str1 == null)
        {
          String str3 = installLibFluency(localApplicationInfo.sourceDir, localApplicationInfo.dataDir);
          return str3;
        }
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        LogUtil.e(FluencyServiceImpl.TAG, "Exception " + localNameNotFoundException.getMessage());
        str1 = null;
      }
      return str1;
    }
    
    public void run()
    {
      loadLibFluency();
    }
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    public FluencyService getService()
    {
      return FluencyServiceImpl.this;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyServiceImpl
 * JD-Core Version:    0.7.0.1
 */