package com.touchtype.cloud;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.touchtype.JobScheduler;
import com.touchtype.cloud.sync.SyncDeltaManager;
import com.touchtype.cloud.sync.SyncScheduledJob;
import com.touchtype.cloud.sync.SyncStartedListener;
import com.touchtype.cloud.sync.SyncWifiRestoredListener;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.sync.client.CommonUtilities.AuthTokenType;
import com.touchtype.sync.client.CommonUtilities.Platform;
import com.touchtype.sync.client.CommonUtilities.SyncAuthenticationState;
import com.touchtype.sync.client.CompletionListener;
import com.touchtype.sync.client.Credential;
import com.touchtype.sync.client.Device;
import com.touchtype.sync.client.DynamicModelHandler;
import com.touchtype.sync.client.LoggingListener;
import com.touchtype.sync.client.LoggingListener.Level;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.sync.client.SSLTools;
import com.touchtype.sync.client.SyncClient;
import com.touchtype.sync.client.SyncClientImpl;
import com.touchtype.sync.client.SyncListener;
import com.touchtype.sync.client.SyncStorage;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.LogUtil;
import com.touchtype.util.NetworkUtil;
import com.touchtype.util.PreferencesUtil;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.service.DynamicModelStorage;
import com.touchtype_fluency.service.FluencyServiceProxy;
import java.io.File;
import java.lang.ref.WeakReference;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public class CloudService
  extends Service
{
  private static final String TAG = CloudService.class.getSimpleName();
  private final IBinder binder = new LocalBinder();
  private SyncDeltaManager deltaManager;
  private final FluencyServiceProxy fluencyServiceProxy = new FluencyServiceProxy();
  private boolean gcmAvailable;
  private Handler handler;
  private JobScheduler jobScheduler;
  private int keepAliveCount = 0;
  private TouchTypePreferences prefs;
  private SyncClient syncClient;
  private SyncScheduledJob syncScheduledJob;
  private WeakReference<SyncStartedListener> syncStartedListener = null;
  private SyncWifiRestoredListener wifiRestoredListener;
  
  private void alertSyncStartedListener(SyncSource paramSyncSource)
  {
    if (this.syncStartedListener != null)
    {
      SyncStartedListener localSyncStartedListener = (SyncStartedListener)this.syncStartedListener.get();
      if (localSyncStartedListener != null) {
        localSyncStartedListener.syncStarted(paramSyncSource);
      }
    }
    else
    {
      return;
    }
    this.syncStartedListener = null;
  }
  
  private RequestListener createPushDataListener(final SyncSource paramSyncSource, final SyncListener paramSyncListener)
  {
    new RequestListener()
    {
      public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        if (!CloudService.this.handleUnauthorizedError(paramAnonymousSyncError))
        {
          if (paramAnonymousSyncError == RequestListener.SyncError.INVALID_MODEL)
          {
            LogUtil.e(CloudService.TAG, "Tried to push an invalid model - deleting it");
            CloudService.this.deltaManager.clearPushModel();
          }
          if (CloudService.this.prefs.getSyncScheduledTime() == 0L) {
            CloudService.this.setSyncRetryAlarm();
          }
        }
        for (;;)
        {
          if (paramSyncListener != null) {
            paramSyncListener.onError(paramAnonymousSyncError, paramAnonymousString);
          }
          CloudService.this.decrementKeepAliveCount();
          return;
          if (paramSyncSource == CloudService.SyncSource.AUTO) {
            CloudService.this.showCloudNotificationOnError();
          }
        }
      }
      
      public void onSuccess(Map<String, String> paramAnonymousMap)
      {
        CloudService.this.prefs.setSyncFailuresCount(0);
        CloudService.this.unregisterWifiRestoredListener();
        if (paramSyncListener != null) {
          paramSyncListener.onSuccess(paramAnonymousMap);
        }
        CloudService.this.deltaManager.clearPushModel();
        CloudService.this.decrementKeepAliveCount();
      }
    };
  }
  
  private SyncListener createSyncNowListener(final SyncSource paramSyncSource, final SyncListener paramSyncListener)
  {
    new SyncListener()
    {
      public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        if (!CloudService.this.handleUnauthorizedError(paramAnonymousSyncError)) {
          if ((paramSyncSource == CloudService.SyncSource.AUTO) && (CloudService.this.prefs.getSyncScheduledTime() == 0L) && (!CloudService.this.setSyncRetryAlarm())) {
            CloudService.this.setNextScheduledSyncAlarm();
          }
        }
        for (;;)
        {
          if (paramSyncListener != null) {
            paramSyncListener.onError(paramAnonymousSyncError, paramAnonymousString);
          }
          CloudService.this.decrementKeepAliveCount();
          return;
          if (paramSyncSource == CloudService.SyncSource.AUTO) {
            CloudService.this.showCloudNotificationOnError();
          }
        }
      }
      
      public void onPullError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        if (paramSyncListener != null) {
          paramSyncListener.onPullError(paramAnonymousSyncError, paramAnonymousString);
        }
      }
      
      public void onPullSuccess(Map<String, String> paramAnonymousMap)
      {
        if (paramSyncListener != null) {
          paramSyncListener.onPullSuccess(paramAnonymousMap);
        }
      }
      
      public void onPushError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        if (paramAnonymousSyncError == RequestListener.SyncError.INVALID_MODEL)
        {
          LogUtil.e(CloudService.TAG, "Tried to push an invalid model - deleting it");
          CloudService.this.deltaManager.clearPushModel();
        }
        if (paramSyncListener != null) {
          paramSyncListener.onPushError(paramAnonymousSyncError, paramAnonymousString);
        }
      }
      
      public void onPushSuccess(Map<String, String> paramAnonymousMap)
      {
        CloudService.this.deltaManager.clearPushModel();
        if (paramSyncListener != null) {
          paramSyncListener.onPushSuccess(paramAnonymousMap);
        }
      }
      
      public void onSuccess(Map<String, String> paramAnonymousMap)
      {
        CloudService.this.prefs.setSyncFailuresCount(0);
        CloudService.this.prefs.setSyncPendingPull(false);
        CloudService.this.unregisterWifiRestoredListener();
        CloudService.this.setNextScheduledSyncAlarm();
        if (paramSyncListener != null) {
          paramSyncListener.onSuccess(paramAnonymousMap);
        }
        CloudService.this.decrementKeepAliveCount();
      }
    };
  }
  
  private void decrementKeepAliveCount()
  {
    if (this.handler == null) {
      return;
    }
    this.handler.post(new Runnable()
    {
      public void run()
      {
        if (CloudService.access$106(CloudService.this) == 0) {
          CloudService.this.stopSelf();
        }
      }
    });
  }
  
  private void enableNotificationsOnServer(String paramString)
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put("registration_id", paramString);
    incrementKeepAliveCount();
    this.syncClient.enableSubscription(CommonUtilities.Platform.ANDROID, localHashMap, new RequestListener()
    {
      public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        LogUtil.w(CloudService.TAG, "Error enabling GCM on the server: " + paramAnonymousString);
        CloudService.this.decrementKeepAliveCount();
      }
      
      public void onSuccess(Map<String, String> paramAnonymousMap)
      {
        CloudService.this.decrementKeepAliveCount();
      }
    });
  }
  
  private List<String> getEnabledLocales()
  {
    return Lists.newArrayList(this.prefs.getEnabledLocales());
  }
  
  private File getKeyboardDeltaModelDirectory(File paramFile)
  {
    return new File(paramFile, "keyboard_delta/");
  }
  
  private LoggingListener getLoggingListener()
  {
    new LoggingListener()
    {
      public void log(LoggingListener.Level paramAnonymousLevel, String paramAnonymousString)
      {
        switch (CloudService.16.$SwitchMap$com$touchtype$sync$client$LoggingListener$Level[paramAnonymousLevel.ordinal()])
        {
        case 1: 
        case 2: 
        case 3: 
        default: 
          return;
        case 4: 
          LogUtil.w("SyncClientLibrary", paramAnonymousString);
          return;
        }
        LogUtil.e("SyncClientLibrary", paramAnonymousString);
      }
    };
  }
  
  private File getPushDeltaModelDirectory(File paramFile)
  {
    return new File(paramFile, "push_delta/");
  }
  
  private SSLTools getSSLTools()
  {
    if (EnvironmentInfoUtil.shouldSupportSSL()) {
      return null;
    }
    new SSLTools()
    {
      public SSLContext getContext()
      {
        return CloudServerSSLTools.getTrustAllSslContext();
      }
      
      public HostnameVerifier getHostnameVerifier()
      {
        return CloudServerSSLTools.DO_NOT_VERIFY_HOST;
      }
    };
  }
  
  private File getSyncFilesDirectory()
  {
    return getFilesDir();
  }
  
  private SyncStorage getSyncStorage(final File paramFile)
  {
    new SyncStorage()
    {
      public File getPushDeltaModelDirectory()
      {
        return CloudService.this.getPushDeltaModelDirectory(paramFile);
      }
      
      public File getSyncFilesDirectory()
      {
        return CloudService.this.getSyncFilesDirectory();
      }
      
      public File getTempDirectory()
      {
        return CloudService.this.getDir("temp", 0);
      }
    };
  }
  
  private File getUserModelDirectory(File paramFile)
  {
    return new File(paramFile, DynamicModelStorage.USER_LM_FOLDER);
  }
  
  private void incrementKeepAliveCount()
  {
    int i = this.keepAliveCount;
    this.keepAliveCount = (i + 1);
    if (i == 0) {
      startService(new Intent(this, CloudService.class));
    }
  }
  
  private void pushBlacklistIfNeeded()
  {
    Set localSet = this.deltaManager.getBlackListWords();
    if (localSet.size() > 0)
    {
      incrementKeepAliveCount();
      this.syncClient.pushBlacklist(getEnabledLocales(), localSet, new RequestListener()
      {
        public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
        {
          CloudService.this.decrementKeepAliveCount();
        }
        
        public void onSuccess(Map<String, String> paramAnonymousMap)
        {
          CloudService.this.deltaManager.clearBlackListWords();
          CloudService.this.decrementKeepAliveCount();
        }
      });
    }
  }
  
  private void registerWifiRestoredListener()
  {
    if (this.wifiRestoredListener == null)
    {
      this.wifiRestoredListener = new SyncWifiRestoredListener();
      IntentFilter localIntentFilter = new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
      registerReceiver(this.wifiRestoredListener, localIntentFilter);
    }
  }
  
  private void setNextScheduledSyncAlarm()
  {
    int i = this.prefs.getSyncFrequency();
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) == 0) {}
    for (int j = 1;; j = 0)
    {
      if ((i != 0) || (j == 0))
      {
        long l1 = this.syncScheduledJob.getDefaultInterval(getApplicationContext());
        if (l1 > 0L)
        {
          long l2 = l1 / 8L;
          l1 += new Random(System.currentTimeMillis()).nextInt(Ints.saturatedCast(1L + 2L * l2)) - l2;
        }
        this.jobScheduler.scheduleJob(this.syncScheduledJob, this, true, l1);
      }
      return;
    }
  }
  
  private boolean setSyncRetryAlarm()
  {
    int i = this.prefs.getSyncFailuresCount();
    if (i < 3L)
    {
      int j = i + 1;
      this.prefs.setSyncFailuresCount(j);
      long l = 600000L + (new Random(System.currentTimeMillis()).nextInt(Ints.saturatedCast(900001L)) - 300000L);
      this.jobScheduler.scheduleJob(this.syncScheduledJob, this, true, l * j);
      return true;
    }
    this.prefs.setSyncFailuresCount(0);
    return false;
  }
  
  private void syncIfRealtime()
  {
    long l;
    if (this.prefs.getSyncFrequency() == 0)
    {
      l = CloudUtils.getDelayRequiredUntilNextSync(getLastSyncTime());
      if ((l != 0L) || (isSyncing())) {
        break label51;
      }
      this.jobScheduler.cancelJob(this.syncScheduledJob, this);
      performSync(SyncSource.AUTO);
    }
    label51:
    while (this.prefs.getSyncScheduledTime() != 0L) {
      return;
    }
    this.jobScheduler.scheduleJob(this.syncScheduledJob, this, true, l);
  }
  
  private void unregisterWifiRestoredListener()
  {
    if (this.wifiRestoredListener != null)
    {
      unregisterReceiver(this.wifiRestoredListener);
      this.wifiRestoredListener = null;
    }
  }
  
  public void addLMToSyncModel(final String paramString, final RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    new FluencyDependentTask(new Runnable()
    {
      public void run()
      {
        if (CloudService.this.deltaManager.addLMToDeltaLM(paramString))
        {
          CloudService.this.syncIfRealtime();
          if (paramRequestListener != null) {
            paramRequestListener.onSuccess(null);
          }
        }
        for (;;)
        {
          CloudService.this.decrementKeepAliveCount();
          return;
          if (paramRequestListener != null) {
            paramRequestListener.onError(null, null);
          }
        }
      }
    }, null).execute(new Void[0]);
  }
  
  public void addMultipleTextSequencesToSyncModel(final List<String> paramList1, final Sequence.Type paramType, final List<String> paramList2)
  {
    if (paramList1.size() > 0)
    {
      incrementKeepAliveCount();
      new FluencyDependentTask(new Runnable()
      {
        public void run()
        {
          if (CloudService.this.deltaManager.addMultipleTextToKeyboardDelta(paramList1, paramType, paramList2)) {
            CloudService.this.syncIfRealtime();
          }
          CloudService.this.decrementKeepAliveCount();
        }
      }, null).execute(new Void[0]);
    }
  }
  
  public void addSyncListener(SyncListener paramSyncListener)
  {
    this.syncClient.addSyncListener(paramSyncListener);
  }
  
  public void addTextSequenceToSyncModel(final Sequence paramSequence)
  {
    incrementKeepAliveCount();
    new FluencyDependentTask(new Runnable()
    {
      public void run()
      {
        if (CloudService.this.deltaManager.addTextSequenceToKeyboardDelta(paramSequence)) {
          CloudService.this.syncIfRealtime();
        }
        CloudService.this.decrementKeepAliveCount();
      }
    }, null).execute(new Void[0]);
  }
  
  public void addToBlackList(final String paramString)
  {
    incrementKeepAliveCount();
    new AsyncTask()
    {
      protected Void doInBackground(Void... paramAnonymousVarArgs)
      {
        CloudService.this.deltaManager.addToBlackList(paramString);
        CloudService.this.decrementKeepAliveCount();
        return null;
      }
    }.execute(new Void[0]);
  }
  
  public void changeDeviceDescription(String paramString1, String paramString2, RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.changeDeviceDescription(paramString1, paramString2, new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void deleteAccount(RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.deleteAccount(new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void deleteDevice(String paramString, RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.deleteDevice(paramString, new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void disableNotifications()
  {
    if (this.gcmAvailable) {
      new GcmRegistrarCompat.BaseUnRegisterTask(this).execute(new String[] { "1057140433302" });
    }
  }
  
  public void enableNotifications()
  {
    String str;
    if (this.gcmAvailable)
    {
      str = GcmRegistrarCompat.getRegistrationId(this);
      if (TextUtils.isEmpty(str)) {
        new GcmRegisterTask(this).execute(new String[] { "1057140433302" });
      }
    }
    else
    {
      return;
    }
    enableNotificationsOnServer(str);
  }
  
  public String getAccountIdentifier()
  {
    Credential localCredential = this.syncClient.getAuthenticationCredential();
    if (localCredential != null) {
      return localCredential.getIdentifier();
    }
    return "";
  }
  
  public boolean getAllowMarketingStatus()
  {
    return this.syncClient.hasOptedInMarketing();
  }
  
  public String getAuthenticationDeviceId()
  {
    return this.syncClient.getAuthenticationDeviceId();
  }
  
  public List<Device> getDevices()
  {
    return this.syncClient.getDevices();
  }
  
  public long getLastSyncTime()
  {
    return this.syncClient.getLastSyncTime();
  }
  
  public boolean getNotificationsEnabled()
  {
    return this.syncClient.getNotificationsEnabled();
  }
  
  public void googleAuthenticate(String paramString1, String paramString2, String paramString3, CommonUtilities.AuthTokenType paramAuthTokenType, boolean paramBoolean, RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    SyncClient localSyncClient = this.syncClient;
    String str1 = Locale.getDefault().toString();
    String str2 = DeviceUtils.getDeviceId(this);
    String str3 = getString(2131296340);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = getString(2131296304);
    localSyncClient.googleAuthenticate(paramString1, paramString2, paramString3, paramAuthTokenType, str1, str2, false, String.format(str3, arrayOfObject), paramBoolean, new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public boolean handleUnauthorizedError(RequestListener.SyncError paramSyncError)
  {
    RequestListener.SyncError localSyncError = RequestListener.SyncError.UNAUTHORIZED;
    boolean bool = false;
    if (paramSyncError == localSyncError)
    {
      LogUtil.w(TAG, "A sync operation has failed due to being unauthorized - disabling sync altogether");
      resetCloudState();
      bool = true;
    }
    return bool;
  }
  
  public void initialiseSync()
  {
    incrementKeepAliveCount();
    new FluencyDependentTask(new Runnable()
    {
      public void run()
      {
        CloudService.this.deltaManager.copyUserModel();
        CloudService.this.performSync(CloudService.SyncSource.AUTO);
        CloudService.this.decrementKeepAliveCount();
      }
    }, null).execute(new Void[0]);
  }
  
  public boolean isSyncing()
  {
    return this.syncClient.isSyncing();
  }
  
  public boolean isUnauthenticated()
  {
    return this.syncClient.getAuthenticationStatus() == CommonUtilities.SyncAuthenticationState.UNAUTHENTICATED;
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.binder;
  }
  
  public void onCreate()
  {
    File localFile = new DynamicModelStorage(this).getDynamicModelStorageLocation();
    this.syncClient = new SyncClientImpl(getString(2131296329), getString(2131296330), getSSLTools(), getLoggingListener(), getSyncStorage(localFile), new DynamicModelHandler()
    {
      public void mergeServerDelta(File paramAnonymousFile, Collection<String> paramAnonymousCollection, CompletionListener paramAnonymousCompletionListener)
      {
        CloudService.this.deltaManager.mergeServerDelta(paramAnonymousFile, paramAnonymousCollection, paramAnonymousCompletionListener);
      }
    });
    this.fluencyServiceProxy.onCreate(this);
    this.deltaManager = new SyncDeltaManager(this.fluencyServiceProxy, getSyncFilesDirectory(), getUserModelDirectory(localFile), getKeyboardDeltaModelDirectory(localFile), getPushDeltaModelDirectory(localFile));
    this.jobScheduler = new JobScheduler();
    this.syncScheduledJob = new SyncScheduledJob();
    try
    {
      GcmRegistrarCompat.checkPlayServices(this);
      this.gcmAvailable = true;
      this.prefs = TouchTypePreferences.getInstance(this);
      this.handler = new Handler();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "GCM unsupported device! " + localIllegalStateException.getMessage());
        this.gcmAvailable = false;
      }
    }
  }
  
  public void onDestroy()
  {
    this.fluencyServiceProxy.onDestroy(this);
    this.syncClient.shutDown();
    this.handler = null;
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    String str;
    if (paramIntent != null)
    {
      str = paramIntent.getAction();
      if (str != null)
      {
        incrementKeepAliveCount();
        if (!str.equals("CloudService.performSync")) {
          break label64;
        }
        if (paramIntent.getBooleanExtra("CloudService.wifiRestoredSync", false)) {
          unregisterWifiRestoredListener();
        }
        performSync(SyncSource.AUTO);
      }
    }
    for (;;)
    {
      decrementKeepAliveCount();
      return 1;
      str = null;
      break;
      label64:
      if (str.equals("CloudService.registerGcmWithServer")) {
        enableNotificationsOnServer(paramIntent.getStringExtra("CloudService.gcmPlatformRegistrationId"));
      } else if (str.equals("CloudService.unregisterFromGcm")) {
        disableNotifications();
      }
    }
  }
  
  public void performSync(SyncSource paramSyncSource)
  {
    performSync(paramSyncSource, null);
  }
  
  public void performSync(final SyncSource paramSyncSource, final SyncListener paramSyncListener)
  {
    if (this.prefs.isSyncEnabled()) {
      if (!this.syncClient.isSyncing())
      {
        pushBlacklistIfNeeded();
        incrementKeepAliveCount();
      }
    }
    while (paramSyncListener == null)
    {
      new FluencyDependentTask(new Runnable()
      {
        public void run()
        {
          if (CloudService.this.deltaManager.prepareDeltaForPush())
          {
            if ((CloudService.this.prefs.isSyncWifiOnly()) && (!NetworkUtil.isWifiActive(CloudService.this)))
            {
              CloudService.this.registerWifiRestoredListener();
              CloudService.this.setNextScheduledSyncAlarm();
              if (paramSyncListener != null) {
                paramSyncListener.onError(RequestListener.SyncError.CLIENT, "WifiConstraintFailed");
              }
              CloudService.this.decrementKeepAliveCount();
              return;
            }
            if ((paramSyncSource == CloudService.SyncSource.AUTO) && (CloudService.this.prefs.getSyncFrequency() == 0) && (CloudService.this.getNotificationsEnabled()) && (!CloudService.this.prefs.isSyncPendingPull())) {
              CloudService.this.syncClient.pushLM(CloudService.this.getEnabledLocales(), CloudService.this.createPushDataListener(paramSyncSource, paramSyncListener));
            }
            for (;;)
            {
              CloudService.this.alertSyncStartedListener(paramSyncSource);
              return;
              CloudService.this.syncClient.syncNow(CloudService.this.getEnabledLocales(), false, CloudService.this.createSyncNowListener(paramSyncSource, paramSyncListener));
            }
          }
          CloudService.this.setNextScheduledSyncAlarm();
          if (paramSyncListener != null) {
            paramSyncListener.onError(RequestListener.SyncError.CLIENT, "FluencyNotReady");
          }
          CloudService.this.decrementKeepAliveCount();
        }
      }, null).execute(new Void[0]);
      do
      {
        return;
      } while (paramSyncListener == null);
      paramSyncListener.onError(RequestListener.SyncError.THROTTLE, "A sync is aready in progress");
      return;
    }
    paramSyncListener.onError(RequestListener.SyncError.SYNC_NOW, "Sync has been disabled by the user");
  }
  
  public void refreshDevices(RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.refreshDevices(new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void refreshMarketingPreferences(RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.refreshMarketingPreferences(new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void resetCloudState()
  {
    this.deltaManager.clearState();
    this.syncClient.resetClient();
    this.prefs.setCloudAccountIsSetup(false);
    this.prefs.setSyncEnabled(false);
    this.prefs.setSyncFailuresCount(0);
    this.prefs.setSyncPendingPull(false);
    this.prefs.setLiveLanguagesEnabled(false);
    this.prefs.setCloudDeviceId("");
    this.prefs.setCloudAccountIdentifier("");
    PreferencesUtil.clearAllPreferences(this, "DynamicPersonalizers");
    disableNotifications();
    this.jobScheduler.cancelJob(this.syncScheduledJob, this);
  }
  
  public void setAllowMarketingStatus(boolean paramBoolean, RequestListener paramRequestListener)
  {
    incrementKeepAliveCount();
    this.syncClient.changeMarketingPreferences(paramBoolean, new KeepAliveRequestListener(paramRequestListener, null));
  }
  
  public void setSyncListener(SyncStartedListener paramSyncStartedListener)
  {
    this.syncStartedListener = new WeakReference(paramSyncStartedListener);
  }
  
  public void showCloudNotificationOnError()
  {
    UserNotificationManager.getInstance(this).displayReEnableCloudServicesNotification();
  }
  
  private final class FluencyDependentTask
    extends AsyncTask<Void, Void, Void>
  {
    private Runnable task;
    
    private FluencyDependentTask(Runnable paramRunnable)
    {
      this.task = paramRunnable;
    }
    
    protected Void doInBackground(Void... paramVarArgs)
    {
      if (this.task != null) {
        CloudService.this.fluencyServiceProxy.runWhenConnected(this.task);
      }
      return null;
    }
  }
  
  private final class GcmRegisterTask
    extends GcmRegistrarCompat.BaseRegisterTask
  {
    GcmRegisterTask(Context paramContext)
    {
      super();
    }
    
    protected void sendRegistrationIdToServer(String paramString)
    {
      if (paramString != null)
      {
        CloudService.this.enableNotificationsOnServer(paramString);
        return;
      }
      LogUtil.e(CloudService.TAG, "GCM registrationID is null! Not registering with server");
    }
  }
  
  private final class KeepAliveRequestListener
    implements RequestListener
  {
    private RequestListener listener;
    
    private KeepAliveRequestListener(RequestListener paramRequestListener)
    {
      this.listener = paramRequestListener;
    }
    
    public void onError(RequestListener.SyncError paramSyncError, String paramString)
    {
      this.listener.onError(paramSyncError, paramString);
      CloudService.this.decrementKeepAliveCount();
    }
    
    public void onSuccess(Map<String, String> paramMap)
    {
      this.listener.onSuccess(paramMap);
      CloudService.this.decrementKeepAliveCount();
    }
  }
  
  public final class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    public CloudService getService()
    {
      return CloudService.this;
    }
  }
  
  public static enum SyncSource
  {
    static
    {
      AUTO = new SyncSource("AUTO", 1);
      SyncSource[] arrayOfSyncSource = new SyncSource[2];
      arrayOfSyncSource[0] = MANUAL;
      arrayOfSyncSource[1] = AUTO;
      $VALUES = arrayOfSyncSource;
    }
    
    private SyncSource() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudService
 * JD-Core Version:    0.7.0.1
 */