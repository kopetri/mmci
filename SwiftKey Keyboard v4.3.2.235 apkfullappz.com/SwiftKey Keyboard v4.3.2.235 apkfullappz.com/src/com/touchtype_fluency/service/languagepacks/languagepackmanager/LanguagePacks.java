package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import com.google.gson.JsonSyntaxException;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.AndroidTmpDirectoryHandler;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.storage.TmpDirectoryHandler;
import com.touchtype.util.LogUtil;
import com.touchtype.util.WeakHashSet;
import com.touchtype_fluency.service.DownloadListener;
import com.touchtype_fluency.service.DownloadListener.Result;
import com.touchtype_fluency.service.DynamicModelStorage;
import com.touchtype_fluency.service.FluencyServiceImpl;
import com.touchtype_fluency.service.MultipleDownloadListener;
import com.touchtype_fluency.service.http.Downloader;
import com.touchtype_fluency.service.http.Downloader.DownloadFailedReason;
import com.touchtype_fluency.service.http.DownloaderProgressListener;
import com.touchtype_fluency.service.http.SSLClientFactory;
import com.touchtype_fluency.service.languagepacks.LanguagePackUtil;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;
import com.touchtype_fluency.service.receiver.ConnectivityListener;
import com.touchtype_fluency.service.receiver.ConnectivityReceiver;
import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardMountedListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import com.touchtype_fluency.service.util.RemoteLanguagePackJsonBuilder;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.apache.commons.io.FileUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.params.BasicHttpParams;

public class LanguagePacks
{
  public static final String ETAG_FILENAME = "etag.dat";
  private static final String TAG = LanguagePacks.class.getSimpleName();
  private boolean configurationDownloadSuccess = true;
  private String configurationURL;
  private Context context;
  private boolean deferNotifications;
  private boolean deferredNotification;
  private final ConnectivityListener forciblyUpdateOnConnectionAvailable = new ConnectivityListener()
  {
    public void handleConnectionAvailable()
    {
      LanguagePacks.this.forciblyUpdateConfiguration();
    }
  };
  private final Handler handler = new Handler(Looper.getMainLooper());
  private AndroidLanguagePackModelStorage languagePackModelStorage;
  protected LanguagePacksList languagePacks;
  private LanguagePacksListFactory languagePacksFactory;
  private Set<LanguagePackListener> listeners = Collections.synchronizedSet(new WeakHashSet());
  private Downloader mConfigurationDownloader;
  private Executor mExecutor;
  private SDCardListener mSDCardListener = null;
  private boolean mSetup = false;
  private final TmpDirectoryHandler mTemporaryDirectoryHandler;
  private SwiftKeyPreferences mTouchTypePreferences;
  private String preinstallDir;
  private boolean ready = false;
  
  public LanguagePacks(Context paramContext)
  {
    this.languagePackModelStorage = AndroidLanguagePackModelStorage.getInstance(paramContext.getApplicationContext());
    this.languagePacksFactory = new LanguagePacksListFactory(new LanguagePackFactory(paramContext, this.languagePackModelStorage.getStaticModelDirectory(), UserNotificationManager.getInstance(paramContext)));
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    this.configurationURL = paramContext.getString(2131297193);
    this.preinstallDir = paramContext.getString(2131297194);
    this.context = paramContext;
    this.mTemporaryDirectoryHandler = new AndroidTmpDirectoryHandler(paramContext);
  }
  
  private HttpGet addETagToRequest(HttpGet paramHttpGet)
  {
    String str = "NONE";
    try
    {
      Scanner localScanner = new Scanner(new File(this.context.getCacheDir(), "etag.dat"));
      if (localScanner.hasNextLine()) {
        str = localScanner.nextLine();
      }
      localScanner.close();
    }
    catch (Exception localException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Error reading eTag file, defaulting to null : " + localException.getLocalizedMessage());
        localException.printStackTrace();
      }
    }
    paramHttpGet.setHeader("Cache-Control", "public");
    paramHttpGet.setHeader("If-None-Match", str);
    return paramHttpGet;
  }
  
  private void attemptToLoadConfig()
  {
    if (this.languagePackModelStorage.isConfigurationAvailable("languagePacks.json"))
    {
      this.ready = false;
      readConfiguration();
      syncEnabledLanguagesLocales();
      return;
    }
    setReady();
    notifyListeners();
  }
  
  private void cancelAllDownloads()
  {
    synchronized (getLanguagePacks())
    {
      Iterator localIterator = ???.iterator();
      if (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        localLanguagePack.cancelDownload();
        localLanguagePack.cancelLiveDownload();
      }
    }
    if (this.mConfigurationDownloader != null) {
      this.mConfigurationDownloader.cancel();
    }
  }
  
  private Vector<String> getIDsForLanguagePacks(Vector<LanguagePack> paramVector)
  {
    Vector localVector = new Vector();
    Iterator localIterator = paramVector.iterator();
    while (localIterator.hasNext()) {
      localVector.add(((LanguagePack)localIterator.next()).getID());
    }
    return localVector;
  }
  
  private boolean hasLanguagePacks(File paramFile)
  {
    File[] arrayOfFile = paramFile.listFiles();
    if (arrayOfFile == null) {}
    for (;;)
    {
      return false;
      int i = arrayOfFile.length;
      for (int j = 0; j < i; j++)
      {
        File localFile = arrayOfFile[j];
        if ((localFile.isDirectory()) && (!(localFile.getPath() + File.separator).contentEquals(this.languagePackModelStorage.getStaticModelDirectory().getBaseFolder() + File.separator + DynamicModelStorage.USER_LM_FOLDER)) && (new File(localFile, ".config").exists())) {
          return true;
        }
      }
    }
  }
  
  public static boolean liveLanguagesEnabled(SharedPreferences paramSharedPreferences)
  {
    return paramSharedPreferences.getBoolean("pref_screen_live_language_key", false);
  }
  
  private void readConfiguration()
  {
    try
    {
      String str2 = this.languagePackModelStorage.loadConfiguration("languagePacks.json");
      this.languagePacks = this.languagePacksFactory.create(str2, this);
      setReady();
      notifyListeners();
      saveConfiguration();
      return;
    }
    catch (Exception localException)
    {
      LogUtil.e(TAG, "Unable to read configuration: " + localException.toString());
      if (this.preinstallDir.length() == 0) {}
      for (String str1 = null; str1 != null; str1 = RemoteLanguagePackJsonBuilder.createPreinstalledConfiguration(new File(this.preinstallDir)))
      {
        this.languagePacks = this.languagePacksFactory.create(str1, this);
        setReady();
        notifyListeners();
        saveConfiguration();
        return;
      }
      downloadConfiguration();
    }
  }
  
  private void removeETagFile()
  {
    new File(this.context.getCacheDir(), "etag.dat").delete();
  }
  
  private void saveConfiguration()
  {
    try
    {
      this.languagePackModelStorage.saveConfiguration(this.languagePacks.toJSON(), "languagePacks.json");
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "Unable to save configuration: " + localIOException.getMessage(), localIOException);
    }
  }
  
  private void setPreference(String paramString, boolean paramBoolean)
  {
    this.mTouchTypePreferences.putBoolean(paramString, paramBoolean);
  }
  
  private void setReady()
  {
    try
    {
      this.ready = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  private void startDownload(final DownloadConfigurationMainThreadListener paramDownloadConfigurationMainThreadListener)
  {
    try
    {
      File localFile1 = this.mTemporaryDirectoryHandler.getOrCreateTmpRoot();
      File localFile2 = File.createTempFile("tmpConfigurationFile", "", localFile1);
      DownloadConfigurationListener localDownloadConfigurationListener = new DownloadConfigurationListener(localFile1, localFile2, paramDownloadConfigurationMainThreadListener, this.handler);
      this.mConfigurationDownloader = new Downloader(addETagToRequest(new HttpGet(this.configurationURL)), SSLClientFactory.createHttpClient(new BasicHttpParams()), localFile2, localDownloadConfigurationListener, new File(this.context.getCacheDir(), "etag.dat"));
      this.mExecutor.execute(this.mConfigurationDownloader);
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "Unable to download configuration, can't create temp directory", localIOException);
      this.configurationDownloadSuccess = false;
      this.handler.post(new Runnable()
      {
        public void run()
        {
          paramDownloadConfigurationMainThreadListener.onFailure();
        }
      });
    }
  }
  
  private void syncEnabledLanguagesLocales()
  {
    HashSet localHashSet = new HashSet();
    Iterator localIterator = getEnabledLanguagePacks().iterator();
    while (localIterator.hasNext()) {
      localHashSet.add(LanguagePackUtil.getLocaleString((LanguagePack)localIterator.next()));
    }
    this.mTouchTypePreferences.setEnabledLocales(localHashSet);
  }
  
  public void addListener(LanguagePackListener paramLanguagePackListener)
  {
    try
    {
      this.listeners.add(paramLanguagePackListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void changeSelectedLayout(String paramString1, String paramString2, String paramString3)
  {
    setSelectedLayout(paramString1, paramString2, paramString3);
    notifyListeners();
  }
  
  public void deleteLanguage(LanguagePack paramLanguagePack)
    throws IOException
  {
    try
    {
      FileUtils.deleteDirectory(paramLanguagePack.getDirectory());
      File localFile = paramLanguagePack.getLiveDirectory();
      if (localFile.exists()) {
        FileUtils.deleteDirectory(localFile);
      }
      attemptToLoadConfig();
      return;
    }
    finally {}
  }
  
  public void downloadConfiguration()
  {
    if (this.languagePacks != null)
    {
      if (this.languagePackModelStorage.getStaticModelDirectory().isAvailable())
      {
        this.ready = false;
        if ((this.languagePacks.getLanguagePacks().isEmpty()) && (hasPreinstalledLanguagePacks()))
        {
          readConfiguration();
          return;
        }
        startDownload(new DefaultDownloadMainThreadListener(null));
        return;
      }
      setReady();
      return;
    }
    LogUtil.w(TAG, "LanguagePacks is null");
    setReady();
  }
  
  public void enableLanguage(LanguagePack paramLanguagePack, boolean paramBoolean1, boolean paramBoolean2)
    throws DownloadRequiredException, MaximumLanguagesException
  {
    setPreference(paramLanguagePack.getID(), paramBoolean1);
    paramLanguagePack.setEnabled(paramBoolean1, paramBoolean2);
  }
  
  public LanguagePack findLanguage(String paramString1, String paramString2)
  {
    return this.languagePacks.findLanguage(paramString1, paramString2);
  }
  
  public void forciblyUpdateConfiguration()
  {
    if (this.languagePacks != null)
    {
      if (this.languagePackModelStorage.getStaticModelDirectory().isAvailable())
      {
        this.ready = false;
        startDownload(new ForcedUpdateDownloadMainThreadListener(null));
        return;
      }
      setReady();
      return;
    }
    LogUtil.w(TAG, "LanguagePacks is null");
    setReady();
  }
  
  public LayoutData.LayoutMap getCurrentLayout(String paramString1, String paramString2)
  {
    String str = this.mTouchTypePreferences.getString(paramString1 + "_" + paramString2 + "_layout", null);
    if (str == null)
    {
      LayoutData.LayoutMap localLayoutMap2 = LayoutData.getLayoutFromLanguage(paramString1, paramString2);
      setSelectedLayout(paramString1, paramString2, localLayoutMap2.getLayoutName());
      return localLayoutMap2;
    }
    LayoutData.LayoutMap localLayoutMap1 = LayoutData.get(str);
    if (localLayoutMap1 == null)
    {
      LogUtil.w(TAG, "Failed to get layout from preference with name " + str + ". Using QWERTY");
      localLayoutMap1 = LayoutData.LayoutMap.QWERTY;
    }
    return localLayoutMap1;
  }
  
  public Vector<String> getDownloadedLanguagePackIDs()
  {
    return getIDsForLanguagePacks(getDownloadedLanguagePacks());
  }
  
  public Vector<LanguagePack> getDownloadedLanguagePacks()
  {
    Vector localVector1 = getLanguagePacks();
    Vector localVector2 = new Vector();
    try
    {
      Iterator localIterator = localVector1.iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        if (localLanguagePack.isDownloaded()) {
          localVector2.add(localLanguagePack);
        }
      }
    }
    finally {}
    return localVector2;
  }
  
  public Vector<String> getEnabledLanguagePackIDs()
  {
    return getIDsForLanguagePacks(getEnabledLanguagePacks());
  }
  
  public Vector<LanguagePack> getEnabledLanguagePacks()
  {
    Vector localVector1 = getLanguagePacks();
    Vector localVector2 = new Vector();
    try
    {
      Iterator localIterator = localVector1.iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        if (localLanguagePack.isEnabled()) {
          localVector2.add(localLanguagePack);
        }
      }
    }
    finally {}
    return localVector2;
  }
  
  public Vector<String> getLanguagePackIDs()
  {
    synchronized (getLanguagePacks())
    {
      Vector localVector2 = getIDsForLanguagePacks(???);
      return localVector2;
    }
  }
  
  public Vector<LanguagePack> getLanguagePacks()
  {
    if (this.languagePacks != null) {
      return this.languagePacks.getLanguagePacks();
    }
    return new Vector();
  }
  
  public List<LanguagePack> getSortedLanguagePacks()
  {
    ArrayList localArrayList = new ArrayList();
    if (this.languagePacks == null) {
      return localArrayList;
    }
    synchronized (this.languagePacks)
    {
      Iterator localIterator = this.languagePacks.getLanguagePacks().iterator();
      if (localIterator.hasNext()) {
        localArrayList.add((LanguagePack)localIterator.next());
      }
    }
    return localArrayList;
  }
  
  public boolean hasPreinstalledLanguagePacks()
  {
    if (this.preinstallDir.length() == 0) {}
    for (;;)
    {
      return false;
      File[] arrayOfFile = new File(this.preinstallDir).listFiles(new FilenameFilter()
      {
        public boolean accept(File paramAnonymousFile, String paramAnonymousString)
        {
          return paramAnonymousString.endsWith(".zip");
        }
      });
      FolderDecorator localFolderDecorator = this.languagePackModelStorage.getStaticModelDirectory();
      if (localFolderDecorator.isAvailable()) {}
      for (boolean bool = hasLanguagePacks(localFolderDecorator.getBaseFolder()); ((arrayOfFile != null) && (arrayOfFile.length > 0)) || (bool); bool = false) {
        return true;
      }
    }
  }
  
  public boolean isDownloadConfigurationSuccess()
  {
    return this.configurationDownloadSuccess;
  }
  
  public boolean isLanguagePackEnabled(String paramString, boolean paramBoolean)
  {
    return this.mTouchTypePreferences.getBoolean(paramString, paramBoolean);
  }
  
  public boolean isReady()
  {
    return this.ready;
  }
  
  protected void languagePackDownloaded()
  {
    notifyListeners();
    saveConfiguration();
  }
  
  protected void liveLanguagePackDownloaded()
  {
    this.mTouchTypePreferences.putLong("live_language_last_update_time", System.currentTimeMillis());
    notifyListeners();
    saveConfiguration();
  }
  
  protected boolean liveLanguagesEnabled()
  {
    return liveLanguagesEnabled(this.mTouchTypePreferences);
  }
  
  public void notifyListeners()
  {
    for (;;)
    {
      Iterator localIterator;
      try
      {
        localIterator = this.listeners.iterator();
        if (!localIterator.hasNext()) {
          break;
        }
        LanguagePackListener localLanguagePackListener = (LanguagePackListener)localIterator.next();
        if ((this.deferNotifications) && (localLanguagePackListener.isDeferrable()))
        {
          this.deferredNotification = true;
          continue;
        }
        if (!localLanguagePackListener.onChange(this.context)) {
          continue;
        }
      }
      finally {}
      localIterator.remove();
    }
  }
  
  public void onCreate()
  {
    if (this.languagePacks == null)
    {
      if (this.mSetup) {
        break label83;
      }
      this.mSetup = true;
      if (this.mExecutor == null) {
        this.mExecutor = Executors.newCachedThreadPool();
      }
      this.languagePacks = this.languagePacksFactory.create("[]", this);
      this.mSDCardListener = SDCardReceiver.addMountedListenerGuaranteedOnce(new SDCardMountedListener()
      {
        public void sdCardIsMounted()
        {
          LanguagePacks.this.attemptToLoadConfig();
        }
      }, this.context);
      if (this.mSDCardListener != null)
      {
        setReady();
        notifyListeners();
      }
    }
    return;
    label83:
    LogUtil.w(TAG, "onCreate called twice");
  }
  
  public void onDestroy()
  {
    if (this.listeners.isEmpty())
    {
      this.mExecutor = null;
      cancelAllDownloads();
      this.languagePacks = null;
      this.ready = false;
      this.mSetup = false;
      if (this.mSDCardListener != null)
      {
        SDCardReceiver.removeListener(this.mSDCardListener);
        this.mSDCardListener = null;
      }
    }
  }
  
  public void removeListener(LanguagePackListener paramLanguagePackListener)
  {
    try
    {
      this.listeners.remove(paramLanguagePackListener);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void scheduledDownloadConfiguration(MultipleDownloadListener paramMultipleDownloadListener)
  {
    Vector localVector = getLanguagePacks();
    if ((localVector.isEmpty()) && (hasPreinstalledLanguagePacks())) {
      return;
    }
    try
    {
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext()) {
        if (((LanguagePack)localIterator.next()).isPreinstalled()) {
          if (!this.mTouchTypePreferences.getBoolean("refresh_preinstalled_languages", false))
          {
            setPreference("refresh_preinstalled_languages", true);
            return;
          }
        }
      }
    }
    finally {}
    if (this.languagePackModelStorage.getStaticModelDirectory().isAvailable())
    {
      this.ready = false;
      startDownload(new ScheduledDownloadMainThreadListener(paramMultipleDownloadListener));
      return;
    }
    setReady();
  }
  
  public DownloadListener scheduledRetryLiveListener()
  {
    new DownloadListener()
    {
      public void onCompletion(DownloadListener.Result paramAnonymousResult)
      {
        switch (LanguagePacks.6.$SwitchMap$com$touchtype_fluency$service$DownloadListener$Result[paramAnonymousResult.ordinal()])
        {
        default: 
          return;
        case 1: 
          FluencyServiceImpl.startServiceForAction("com.touchtype.REFRESH_CONFIGURATION", LanguagePacks.this.context);
          return;
        }
        FluencyServiceImpl.rerunActionOnConnection(LanguagePacks.this.context, "com.touchtype.REFRESH_CONFIGURATION", true);
      }
    };
  }
  
  public void setSelectedLayout(String paramString1, String paramString2, String paramString3)
  {
    this.mTouchTypePreferences.putString(paramString1 + "_" + paramString2 + "_layout", paramString3);
  }
  
  public void startDeferringNotifications()
  {
    try
    {
      this.deferNotifications = true;
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public void stopDeferringNotifications()
  {
    try
    {
      this.deferNotifications = false;
      if (this.deferredNotification)
      {
        this.deferredNotification = false;
        Iterator localIterator = this.listeners.iterator();
        while (localIterator.hasNext())
        {
          LanguagePackListener localLanguagePackListener = (LanguagePackListener)localIterator.next();
          if (localLanguagePackListener.isDeferrable()) {
            localLanguagePackListener.onChange(this.context);
          }
        }
      }
    }
    finally {}
  }
  
  private class DefaultDownloadMainThreadListener
    implements LanguagePacks.DownloadConfigurationMainThreadListener
  {
    private DefaultDownloadMainThreadListener() {}
    
    public void onFailure()
    {
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
    }
    
    public void onSuccess(LanguagePacksList paramLanguagePacksList)
    {
      LanguagePacks.this.languagePacks.merge(paramLanguagePacksList, true);
      ConnectivityReceiver.removeListener(LanguagePacks.this.forciblyUpdateOnConnectionAvailable);
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
      LanguagePacks.this.saveConfiguration();
    }
  }
  
  private final class DownloadConfigurationListener
    implements DownloaderProgressListener
  {
    private final Handler mMainThreadHandler;
    private final LanguagePacks.DownloadConfigurationMainThreadListener mMainThreadListener;
    private final File mTempDir;
    private final File mTempFile;
    
    public DownloadConfigurationListener(File paramFile1, File paramFile2, LanguagePacks.DownloadConfigurationMainThreadListener paramDownloadConfigurationMainThreadListener, Handler paramHandler)
    {
      this.mTempFile = paramFile2;
      this.mTempDir = paramFile1;
      this.mMainThreadListener = paramDownloadConfigurationMainThreadListener;
      this.mMainThreadHandler = paramHandler;
    }
    
    private void cleanDirectory()
    {
      FileUtils.deleteQuietly(this.mTempFile);
      FileUtils.deleteQuietly(this.mTempDir);
    }
    
    public void onCancelled()
    {
      cleanDirectory();
    }
    
    public void onComplete()
    {
      try
      {
        String str = FileUtils.readFileToString(this.mTempFile, "UTF-8");
        final LanguagePacksList localLanguagePacksList = LanguagePacks.this.languagePacksFactory.create(str, LanguagePacks.this);
        this.mMainThreadHandler.post(new Runnable()
        {
          public void run()
          {
            LanguagePacks.DownloadConfigurationListener.this.mMainThreadListener.onSuccess(localLanguagePacksList);
          }
        });
        LanguagePacks.access$802(LanguagePacks.this, true);
        return;
      }
      catch (IOException localIOException)
      {
        LogUtil.e(LanguagePacks.TAG, "Download Configuration Failed reading the config file", localIOException);
        LanguagePacks.this.removeETagFile();
        this.mMainThreadHandler.post(new Runnable()
        {
          public void run()
          {
            LanguagePacks.DownloadConfigurationListener.this.mMainThreadListener.onFailure();
          }
        });
        return;
      }
      catch (JsonSyntaxException localJsonSyntaxException)
      {
        LogUtil.e(LanguagePacks.TAG, "Download Configuration Failed writing the JSON file", localJsonSyntaxException);
        LanguagePacks.this.removeETagFile();
        this.mMainThreadHandler.post(new Runnable()
        {
          public void run()
          {
            LanguagePacks.DownloadConfigurationListener.this.mMainThreadListener.onFailure();
          }
        });
        return;
      }
      finally
      {
        cleanDirectory();
      }
    }
    
    public void onFailed(Downloader.DownloadFailedReason paramDownloadFailedReason)
    {
      LanguagePacks.access$802(LanguagePacks.this, false);
      this.mMainThreadHandler.post(new Runnable()
      {
        public void run()
        {
          LanguagePacks.DownloadConfigurationListener.this.mMainThreadListener.onFailure();
        }
      });
      cleanDirectory();
    }
    
    public void onProgress(int paramInt1, int paramInt2) {}
    
    public void onStarted() {}
  }
  
  private static abstract interface DownloadConfigurationMainThreadListener
  {
    public abstract void onFailure();
    
    public abstract void onSuccess(LanguagePacksList paramLanguagePacksList);
  }
  
  private class ForcedUpdateDownloadMainThreadListener
    implements LanguagePacks.DownloadConfigurationMainThreadListener
  {
    private ForcedUpdateDownloadMainThreadListener() {}
    
    public void onFailure()
    {
      ConnectivityReceiver.addListener(LanguagePacks.this.forciblyUpdateOnConnectionAvailable);
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
    }
    
    public void onSuccess(LanguagePacksList paramLanguagePacksList)
    {
      LanguagePacks.this.languagePacks.merge(paramLanguagePacksList, false);
      Iterator localIterator = LanguagePacks.this.getEnabledLanguagePacks().iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        if (localLanguagePack.isUpdateAvailable()) {
          localLanguagePack.download(null);
        }
        if ((LanguagePacks.this.liveLanguagesEnabled()) && (localLanguagePack.isLiveUpdateNecessary())) {
          localLanguagePack.downloadLive(LanguagePacks.this.scheduledRetryLiveListener());
        }
      }
      ConnectivityReceiver.removeListener(LanguagePacks.this.forciblyUpdateOnConnectionAvailable);
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
      LanguagePacks.this.saveConfiguration();
    }
  }
  
  private class MultipleLanguagePackDownloadListener
    implements DownloadListener
  {
    private int mNumPacksLeftToComplete;
    private MultipleDownloadListener mParentListener;
    private Set<DownloadListener.Result> mResults = new HashSet();
    
    public MultipleLanguagePackDownloadListener(int paramInt, MultipleDownloadListener paramMultipleDownloadListener)
    {
      this.mNumPacksLeftToComplete = paramInt;
      this.mParentListener = paramMultipleDownloadListener;
    }
    
    public void onCompletion(DownloadListener.Result paramResult)
    {
      try
      {
        this.mResults.add(paramResult);
        this.mNumPacksLeftToComplete = (-1 + this.mNumPacksLeftToComplete);
        if (this.mNumPacksLeftToComplete == 0) {
          this.mParentListener.onCompletion(this.mResults);
        }
        return;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
  }
  
  private class ScheduledDownloadMainThreadListener
    implements LanguagePacks.DownloadConfigurationMainThreadListener
  {
    private MultipleDownloadListener mDownloadListener;
    
    public ScheduledDownloadMainThreadListener(MultipleDownloadListener paramMultipleDownloadListener)
    {
      this.mDownloadListener = paramMultipleDownloadListener;
    }
    
    public void onFailure()
    {
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
      HashSet localHashSet = new HashSet();
      localHashSet.add(DownloadListener.Result.NETWORK_FAILURE);
      this.mDownloadListener.onCompletion(localHashSet);
    }
    
    public void onSuccess(LanguagePacksList paramLanguagePacksList)
    {
      LanguagePacks.this.languagePacks.merge(paramLanguagePacksList, true);
      LanguagePacks.this.setReady();
      LanguagePacks.this.notifyListeners();
      LanguagePacks.this.saveConfiguration();
      ArrayList localArrayList = new ArrayList();
      if (LanguagePacks.this.liveLanguagesEnabled())
      {
        Iterator localIterator2 = LanguagePacks.this.getEnabledLanguagePacks().iterator();
        while (localIterator2.hasNext())
        {
          LanguagePack localLanguagePack = (LanguagePack)localIterator2.next();
          if (localLanguagePack.isLiveUpdateNecessary()) {
            localArrayList.add(localLanguagePack);
          }
        }
      }
      if (localArrayList.size() > 0)
      {
        LanguagePacks.MultipleLanguagePackDownloadListener localMultipleLanguagePackDownloadListener = new LanguagePacks.MultipleLanguagePackDownloadListener(LanguagePacks.this, localArrayList.size(), this.mDownloadListener);
        Iterator localIterator1 = localArrayList.iterator();
        while (localIterator1.hasNext()) {
          ((LanguagePack)localIterator1.next()).downloadLive(localMultipleLanguagePackDownloadListener);
        }
      }
      HashSet localHashSet = new HashSet();
      localHashSet.add(DownloadListener.Result.SUCCESS);
      this.mDownloadListener.onCompletion(localHashSet);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacks
 * JD-Core Version:    0.7.0.1
 */