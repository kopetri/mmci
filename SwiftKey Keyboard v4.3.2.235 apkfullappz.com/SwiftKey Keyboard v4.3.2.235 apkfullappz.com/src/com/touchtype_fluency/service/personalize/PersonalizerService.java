package com.touchtype_fluency.service.personalize;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import com.touchtype.backup.SafeBackupRequest;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.storage.AndroidTmpDirectoryHandler;
import com.touchtype.storage.TmpDirectoryHandler;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.personalize.tasks.ContactsParseTask;
import com.touchtype_fluency.service.personalize.tasks.PersonalizationTask;
import com.touchtype_fluency.service.personalize.tasks.PersonalizationTaskExecutor;
import com.touchtype_fluency.service.personalize.tasks.PersonalizationTaskFactory;
import com.touchtype_fluency.service.personalize.tasks.PersonalizationTaskListener;
import com.touchtype_fluency.service.personalize.tasks.SMSParseTask;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class PersonalizerService
  extends Service
{
  public static final String ACTION_ENABLE_PERSONALIZATION = "com.touchtype_fluency.service.personalize.ACTION_ENABLE_PERSONALIZATION";
  public static final boolean INTENT_REQUIRED = true;
  public static final String KEY_DISABLED = "enabled";
  public static final String KEY_ENABLE_TIME = "enable_time";
  public static final String KEY_LASTRUN = "lastrun";
  public static final String KEY_PERSONALIZER_ERROR = "com.touchtype.personalizer.error";
  public static final String KEY_PREVIOUS_STATE = "previous_state";
  public static final String KEY_STATE = "state";
  public static final boolean NO_INTENT_REQUIRED = false;
  public static final int PERSONALIZER_AWAITING_AUTHENTICATION = 5;
  public static final int PERSONALIZER_COMPLETE = 3;
  public static final int PERSONALIZER_DISABLED = -1;
  public static final int PERSONALIZER_FAILED = 2;
  public static final int PERSONALIZER_NOT_RUN = 0;
  public static final int PERSONALIZER_NO_CONTENT = 4;
  public static final String PERSONALIZER_PREFS = "personalizer_service";
  public static final int PERSONALIZER_RUNNING = 1;
  private static final long SLEEP_TIME = 500L;
  private static final String TAG = PersonalizerService.class.getSimpleName();
  final FluencyServiceProxy fluencyServiceProxy = new FluencyServiceProxy()
  {
    protected void onServiceConnected()
    {
      PersonalizerService.access$002(PersonalizerService.this, true);
      PersonalizerService.access$102(PersonalizerService.this, getPredictor());
      PersonalizerService.access$202(PersonalizerService.this, getLanguagePackManager());
    }
  };
  private int mActivePersonalizerCount = 0;
  private final IBinder mBinder = new LocalBinder();
  private long mClearLanguageDataLastRun = 0L;
  private Runnable mContactsTimedTask;
  private Context mContext;
  private Runnable mDeleteRemoteTimedTask;
  private Handler mHandler;
  private LanguagePackManager mLanguagePackManager;
  private volatile boolean mOnServiceConnected;
  private Handler mPersonalizerHandler;
  private Map<String, Long> mPersonalizerLastRun = new HashMap();
  private Map<String, Integer> mPersonalizerPreviousState = new HashMap();
  private Map<String, Integer> mPersonalizerState = new HashMap();
  private Predictor mPredictor;
  private SharedPreferences mPreferences;
  private Runnable mSmsTimedTask;
  private PersonalizationTaskExecutor mTaskExecutor;
  private UserNotificationManager mUserNotificationManager;
  
  private void createFollowLikeNotification(int paramInt, ServiceConfiguration paramServiceConfiguration, String paramString1, String paramString2)
  {
    String str1 = this.mContext.getText(paramInt).toString();
    String str2;
    Intent localIntent;
    if (str1.contains("%1$s"))
    {
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = paramServiceConfiguration.getName();
      str2 = String.format(str1, arrayOfObject);
      localIntent = new Intent();
      localIntent.setClassName(getPackageName(), "com.touchtype_fluency.service.personalize.PersonalizerFollowAndShareActivity");
      if (!paramServiceConfiguration.equals(ServiceConfiguration.TWITTER)) {
        break label221;
      }
      localIntent.setAction(getString(2131297147));
    }
    for (;;)
    {
      localIntent.putExtra("serviceName", paramServiceConfiguration.getName());
      localIntent.putExtra("authParams", paramString2);
      localIntent.putExtra("personalizerKey", paramString1);
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 134217728);
      Notification localNotification = new Notification(2130838153, str2, System.currentTimeMillis());
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.setLatestEventInfo(this.mContext, this.mContext.getText(2131296302), str2, localPendingIntent);
      ((NotificationManager)this.mContext.getSystemService("notification")).notify(paramServiceConfiguration.ordinal(), localNotification);
      return;
      str2 = str1;
      break;
      label221:
      if (paramServiceConfiguration.equals(ServiceConfiguration.FACEBOOK)) {
        localIntent.setAction(getString(2131297144));
      }
    }
  }
  
  private void createNotification(int paramInt, boolean paramBoolean1, String paramString, ServiceConfiguration paramServiceConfiguration, boolean paramBoolean2)
  {
    createNotification(paramInt, paramBoolean1, paramString, paramServiceConfiguration, paramBoolean2, null);
  }
  
  private void createNotification(int paramInt, boolean paramBoolean1, String paramString1, ServiceConfiguration paramServiceConfiguration, boolean paramBoolean2, String paramString2)
  {
    String str1 = this.mContext.getText(paramInt).toString();
    String str2;
    Intent localIntent;
    if (str1.contains("%1$s"))
    {
      str2 = String.format(str1, new Object[] { paramString1 });
      if (!paramBoolean2) {
        break label243;
      }
      if (!paramBoolean1) {
        break label211;
      }
      localIntent = new Intent("com.touchtype_fluency.service.PERSONALIZE_ACTIVITY_FROM_INSTALLER");
      localIntent.setClassName(getPackageName(), getString(2131296752));
      label76:
      localIntent.addFlags(536870912);
      if ((!paramServiceConfiguration.isLocal()) && (getPersonalizerState(paramString2) != -1)) {
        localIntent.putExtra("com.touchtype.personalizer.error", true);
      }
    }
    for (;;)
    {
      PendingIntent localPendingIntent = PendingIntent.getActivity(this.mContext, 0, localIntent, 134217728);
      Notification localNotification = new Notification(2130838153, str2, System.currentTimeMillis());
      localNotification.flags = (0x10 | localNotification.flags);
      localNotification.setLatestEventInfo(this.mContext, this.mContext.getText(2131296302), str2, localPendingIntent);
      ((NotificationManager)this.mContext.getSystemService("notification")).notify(paramServiceConfiguration.ordinal(), localNotification);
      return;
      str2 = str1;
      break;
      label211:
      localIntent = new Intent("com.touchtype_fluency.service.PERSONALIZE_ACTIVITY");
      localIntent.setClassName(getPackageName(), getString(2131296752));
      break label76;
      label243:
      localIntent = new Intent();
    }
  }
  
  private void disableRemotePersonalizers()
  {
    Iterator localIterator = this.mPersonalizerLastRun.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!str.equals(DynamicPersonalizerModel.generateKey(ServiceConfiguration.SMS.getName(), null))) {
        setPersonalizerState(str, -1, false);
      }
    }
    writeStoredState();
  }
  
  private void enableRemotePersonalizers()
  {
    Iterator localIterator = this.mPersonalizerLastRun.keySet().iterator();
    while (localIterator.hasNext())
    {
      String str = (String)localIterator.next();
      if (!str.equals(DynamicPersonalizerModel.generateKey(ServiceConfiguration.SMS.getName(), null))) {
        setPersonalizerState(str, getPersonalizerPreviousState(str), true);
      }
    }
    writeStoredState();
    this.mUserNotificationManager.displayPersonalizationNotification(2131297114);
  }
  
  private int getPersonalizerPreviousState(String paramString)
  {
    if (paramString != null)
    {
      Integer localInteger = (Integer)this.mPersonalizerPreviousState.get(paramString);
      if (localInteger != null) {
        return localInteger.intValue();
      }
      return 0;
    }
    return -1;
  }
  
  private void init(Intent paramIntent)
  {
    if (paramIntent != null)
    {
      if ((paramIntent.getAction() == null) || (!paramIntent.getAction().equals("com.touchtype_fluency.service.personalize.ACTION_ENABLE_PERSONALIZATION"))) {
        break label32;
      }
      enableRemotePersonalizers();
      stopSelf();
    }
    label32:
    Bundle localBundle;
    int i;
    do
    {
      do
      {
        return;
        localBundle = paramIntent.getExtras();
      } while (localBundle == null);
      i = localBundle.getInt("serviceid", -1);
    } while (i == -1);
    String str1 = localBundle.getString("id");
    String str2 = localBundle.getString("path");
    String str3 = localBundle.getString("params");
    String str4 = localBundle.getString("modelname");
    boolean bool = localBundle.getBoolean("packagename");
    String str5 = localBundle.getString("name");
    String str6 = localBundle.getString("personalizerkey");
    ServiceConfiguration localServiceConfiguration = ServiceConfiguration.values()[i];
    switch (3.$SwitchMap$com$touchtype_fluency$service$personalize$ServiceConfiguration[localServiceConfiguration.ordinal()])
    {
    default: 
      RunPersonalizer localRunPersonalizer = new RunPersonalizer(bool, str5, str1, str2, str3, str4, localServiceConfiguration, str6);
      personalizerStarted();
      this.mPersonalizerHandler.post(localRunPersonalizer);
      return;
    case 1: 
      this.mSmsTimedTask = new RunLocalPersonalizer(bool, str5, localServiceConfiguration, str6);
      personalizerStarted();
      this.mPersonalizerHandler.post(this.mSmsTimedTask);
      return;
    case 2: 
      this.mContactsTimedTask = new RunLocalPersonalizer(bool, str5, localServiceConfiguration, str6);
      personalizerStarted();
      this.mPersonalizerHandler.post(this.mContactsTimedTask);
      return;
    }
    writeStoredState();
    this.mDeleteRemoteTimedTask = new DeleteRemoteRunner(str1, str2);
    personalizerStarted();
    this.mPersonalizerHandler.post(this.mDeleteRemoteTimedTask);
  }
  
  private void initStoredState()
  {
    Iterator localIterator = this.mPreferences.getAll().keySet().iterator();
    while (localIterator.hasNext())
    {
      String str1 = (String)localIterator.next();
      if (str1.startsWith("lastrun_"))
      {
        String str2 = str1.substring(8);
        setPersonalizerState(str2, this.mPreferences.getInt("state_" + str2, 0), false);
        setPersonalizerLastRun(str2, this.mPreferences.getLong("lastrun_" + str2, 0L));
        setPersonalizerPreviousState(str2, this.mPreferences.getInt("previous_state_" + str2, ((Integer)this.mPersonalizerState.get(str2)).intValue()));
      }
    }
    this.mClearLanguageDataLastRun = this.mPreferences.getLong("lastrun_ClearLanguageData", 0L);
  }
  
  private void personalizerFinished()
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        if (PersonalizerService.this.mActivePersonalizerCount > 0)
        {
          PersonalizerService.access$310(PersonalizerService.this);
          if (PersonalizerService.this.mActivePersonalizerCount == 0) {
            PersonalizerService.this.stopSelf();
          }
        }
      }
    });
  }
  
  private void personalizerStarted()
  {
    this.mActivePersonalizerCount = (1 + this.mActivePersonalizerCount);
  }
  
  private void requestDataBackup()
  {
    SafeBackupRequest.requestBackup(getApplicationContext());
  }
  
  private void setPersonalizerLastRun(String paramString, long paramLong)
  {
    this.mPersonalizerLastRun.put(paramString, Long.valueOf(paramLong));
  }
  
  private void setPersonalizerPreviousState(String paramString, int paramInt)
  {
    this.mPersonalizerPreviousState.put(paramString, Integer.valueOf(paramInt));
  }
  
  private void writeStoredState()
  {
    if (this.mPreferences != null) {}
    for (;;)
    {
      synchronized (this.mPreferences.edit())
      {
        Iterator localIterator = this.mPersonalizerLastRun.keySet().iterator();
        if (!localIterator.hasNext()) {
          break;
        }
        String str = (String)localIterator.next();
        ???.putInt("state_" + str, getPersonalizerState(str));
        ???.putLong("lastrun_" + str, getPersonalizerLastRun(str));
        ???.putInt("previous_state_" + str, getPersonalizerPreviousState(str));
      }
      ??? = getSharedPreferences("personalizer_service", 0).edit();
    }
    ???.putLong("lastrun_ClearLanguageData", this.mClearLanguageDataLastRun);
    ???.commit();
  }
  
  public boolean clearLanguageData()
  {
    if (isPredictorReady())
    {
      for (;;)
      {
        try
        {
          this.mPredictor.clearUserModel();
          this.mClearLanguageDataLastRun = System.currentTimeMillis();
          Iterator localIterator = this.mPersonalizerLastRun.keySet().iterator();
          if (!localIterator.hasNext()) {
            break;
          }
          String str = (String)localIterator.next();
          if (((Integer)this.mPersonalizerState.get(str)).intValue() == -1)
          {
            this.mPersonalizerPreviousState.put(str, Integer.valueOf(0));
            this.mPersonalizerLastRun.put(str, Long.valueOf(0L));
          }
          else
          {
            this.mPersonalizerState.put(str, Integer.valueOf(0));
          }
        }
        catch (PredictorNotReadyException localPredictorNotReadyException)
        {
          return false;
        }
      }
      writeStoredState();
      requestDataBackup();
      return true;
    }
    return false;
  }
  
  public long getClearLanguageDataLastRun()
  {
    return this.mClearLanguageDataLastRun;
  }
  
  public long getPersonalizerLastRun(String paramString)
  {
    long l = 0L;
    if (paramString != null)
    {
      Long localLong = (Long)this.mPersonalizerLastRun.get(paramString);
      if (localLong != null) {
        l = localLong.longValue();
      }
    }
    return l;
  }
  
  public int getPersonalizerState(String paramString)
  {
    if (paramString != null)
    {
      Integer localInteger = (Integer)this.mPersonalizerState.get(paramString);
      if (localInteger != null) {
        return localInteger.intValue();
      }
      return 0;
    }
    return -1;
  }
  
  public boolean isPredictorReady()
  {
    if (this.mPredictor == null) {
      return false;
    }
    return this.mPredictor.isReady();
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCreate()
  {
    super.onCreate();
    this.mOnServiceConnected = false;
    this.mContext = getApplicationContext();
    this.fluencyServiceProxy.onCreate(this.mContext);
    this.mPreferences = getSharedPreferences("personalizer_service", 0);
    this.mUserNotificationManager = UserNotificationManager.getInstance(this.mContext);
    this.mHandler = new Handler();
    this.mPersonalizerHandler = new Handler();
    initStoredState();
    this.mTaskExecutor = new PersonalizationTaskExecutor();
  }
  
  public void onDestroy()
  {
    this.mTaskExecutor.stop();
    this.mTaskExecutor = null;
    this.mPersonalizerHandler = null;
    this.mHandler = null;
    this.mPreferences = null;
    this.mSmsTimedTask = null;
    this.mDeleteRemoteTimedTask = null;
    this.fluencyServiceProxy.onDestroy(this.mContext);
    this.mContext = null;
    this.fluencyServiceProxy.onDestroy(this);
    super.onDestroy();
  }
  
  public int onStartCommand(Intent paramIntent, int paramInt1, int paramInt2)
  {
    init(paramIntent);
    return 1;
  }
  
  public boolean onUnbind(Intent paramIntent)
  {
    return super.onUnbind(paramIntent);
  }
  
  public void restorePersonalizerState(String paramString)
  {
    setPersonalizerState(paramString, getPersonalizerPreviousState(paramString), false);
  }
  
  public void setFailed(String paramString)
  {
    setPersonalizerState(paramString, 2, true);
    setPersonalizerLastRun(paramString, System.currentTimeMillis());
    writeStoredState();
  }
  
  public void setPersonalizerState(String paramString, int paramInt, boolean paramBoolean)
  {
    this.mPersonalizerState.put(paramString, Integer.valueOf(paramInt));
    if (paramBoolean) {
      setPersonalizerPreviousState(paramString, paramInt);
    }
  }
  
  private class DeleteRemoteRunner
    implements Runnable
  {
    private String mId;
    private PersonalizationTaskListener mPersonalizationListener;
    private String mServicePath;
    
    DeleteRemoteRunner(String paramString1, String paramString2)
    {
      this.mServicePath = paramString2;
      this.mId = paramString1;
      this.mPersonalizationListener = new PersonalizationTaskListener()
      {
        public void onFail(PersonalizationFailedReason paramAnonymousPersonalizationFailedReason)
        {
          PersonalizerService.this.personalizerFinished();
        }
        
        public void onSuccess()
        {
          PersonalizerService.this.personalizerFinished();
        }
      };
    }
    
    public void run()
    {
      Context localContext = PersonalizerService.this.getApplicationContext();
      UrlComposer localUrlComposer = new UrlComposer(this.mId, this.mServicePath, localContext);
      String str = localUrlComposer.addAuthToParams("", localContext.getResources().getString(2131296322));
      PersonalizationTask localPersonalizationTask = PersonalizationTaskFactory.createDeleteRemoteTask(localUrlComposer.getDeleteUrlString(), str, PersonalizerService.this.mTaskExecutor, this.mPersonalizationListener);
      PersonalizerService.this.mTaskExecutor.runTask(localPersonalizationTask);
    }
  }
  
  public class LocalBinder
    extends Binder
  {
    public LocalBinder() {}
    
    public PersonalizerService getService()
    {
      return PersonalizerService.this;
    }
  }
  
  private class RunLocalPersonalizer
    implements Runnable
  {
    private final boolean mFromInstaller;
    private PersonalizationTaskListener mPersonalizationListener;
    private final String mPersonalizerKey;
    private final ServiceConfiguration mServiceId;
    private final String mServiceName;
    private final RunLocalPersonalizer thisParent;
    
    RunLocalPersonalizer(boolean paramBoolean, String paramString1, ServiceConfiguration paramServiceConfiguration, String paramString2)
    {
      this.mServiceName = paramString1;
      this.mFromInstaller = paramBoolean;
      this.mServiceId = paramServiceConfiguration;
      this.thisParent = this;
      this.mPersonalizerKey = paramString2;
      this.mPersonalizationListener = new PersonalizationTaskListener()
      {
        public void onFail(PersonalizationFailedReason paramAnonymousPersonalizationFailedReason)
        {
          if (paramAnonymousPersonalizationFailedReason.equals(PersonalizationFailedReason.NO_LOCAL_CONTENT))
          {
            LogUtil.w(PersonalizerService.TAG, "Can't personalize from " + PersonalizerService.RunLocalPersonalizer.this.mServiceName + " because there is no content");
            if (!PersonalizerService.RunLocalPersonalizer.this.mFromInstaller) {
              PersonalizerService.this.createNotification(2131297115, PersonalizerService.RunLocalPersonalizer.this.mFromInstaller, PersonalizerService.RunLocalPersonalizer.this.mServiceName, PersonalizerService.RunLocalPersonalizer.this.mServiceId, false);
            }
            PersonalizerService.this.setPersonalizerState(PersonalizerService.RunLocalPersonalizer.this.mPersonalizerKey, 4, false);
          }
          for (;;)
          {
            PersonalizerService.this.setPersonalizerLastRun(PersonalizerService.RunLocalPersonalizer.this.mPersonalizerKey, System.currentTimeMillis());
            PersonalizerService.this.writeStoredState();
            PersonalizerService.this.personalizerFinished();
            return;
            PersonalizerService.this.setPersonalizerState(PersonalizerService.RunLocalPersonalizer.this.mPersonalizerKey, 2, false);
            if (!PersonalizerService.RunLocalPersonalizer.this.mFromInstaller) {
              PersonalizerService.this.createNotification(2131297117, PersonalizerService.RunLocalPersonalizer.this.mFromInstaller, PersonalizerService.RunLocalPersonalizer.this.mServiceName, PersonalizerService.RunLocalPersonalizer.this.mServiceId, true);
            }
            LogUtil.w(PersonalizerService.TAG, "Ran out of retry attempts when attempting to parse " + PersonalizerService.RunLocalPersonalizer.this.mServiceName);
          }
        }
        
        public void onSuccess()
        {
          if (!PersonalizerService.RunLocalPersonalizer.this.mFromInstaller) {
            PersonalizerService.this.createNotification(2131297122, PersonalizerService.RunLocalPersonalizer.this.mFromInstaller, PersonalizerService.RunLocalPersonalizer.this.mServiceName, PersonalizerService.RunLocalPersonalizer.this.mServiceId, false);
          }
          PersonalizerService.this.setPersonalizerState(PersonalizerService.RunLocalPersonalizer.this.mPersonalizerKey, 3, false);
          PersonalizerService.this.setPersonalizerLastRun(PersonalizerService.RunLocalPersonalizer.this.mPersonalizerKey, System.currentTimeMillis());
          PersonalizerService.this.writeStoredState();
          PersonalizerService.this.personalizerFinished();
          if (PersonalizerService.RunLocalPersonalizer.this.mServiceId.equals(ServiceConfiguration.SMS))
          {
            PersonalizerService.this.mHandler.removeCallbacks(PersonalizerService.this.mSmsTimedTask);
            return;
          }
          PersonalizerService.this.mHandler.removeCallbacks(PersonalizerService.this.mContactsTimedTask);
        }
      };
    }
    
    public void run()
    {
      if ((!PersonalizerService.this.mOnServiceConnected) || (!PersonalizerService.this.mPredictor.isReady()))
      {
        PersonalizerService.this.fluencyServiceProxy.onCreate(PersonalizerService.this.mContext);
        PersonalizerService.this.mPersonalizerHandler.postDelayed(this.thisParent, 500L);
        return;
      }
      PersonalizerService.this.setPersonalizerState(this.mPersonalizerKey, 1, false);
      PersonalizerService.this.writeStoredState();
      if (this.mServiceId.equals(ServiceConfiguration.SMS)) {}
      for (Object localObject = new SMSParseTask(PersonalizerService.this.mTaskExecutor, PersonalizerService.this.getApplicationContext(), this.mPersonalizationListener, PersonalizerService.this.mPredictor);; localObject = new ContactsParseTask(PersonalizerService.this.mTaskExecutor, PersonalizerService.this.getApplicationContext(), this.mPersonalizationListener, PersonalizerService.this.mPredictor, PersonalizerService.this.mLanguagePackManager))
      {
        PersonalizerService.this.mTaskExecutor.runTask((PersonalizationTask)localObject);
        return;
      }
    }
  }
  
  private class RunPersonalizer
    implements Runnable
  {
    private final boolean mFromInstaller;
    private final String mId;
    private final String mModelname;
    private final String mParams;
    private final PersonalizationTaskListener mPersonalizationListener;
    private final String mPersonalizerKey;
    private final ServiceConfiguration mServiceId;
    private final String mServiceName;
    private final String mServicePath;
    private TmpDirectoryHandler mTmpStore;
    private final RunPersonalizer thisParent;
    
    RunPersonalizer(boolean paramBoolean, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, ServiceConfiguration paramServiceConfiguration, String paramString6)
    {
      this.mFromInstaller = paramBoolean;
      this.mServiceName = paramString1;
      this.mId = paramString2;
      this.mServicePath = paramString3;
      this.mParams = paramString4;
      this.mModelname = paramString5;
      this.mServiceId = paramServiceConfiguration;
      this.thisParent = this;
      this.mPersonalizerKey = paramString6;
      this.mPersonalizationListener = new PersonalizationTaskListener()
      {
        private int getFailureMessageID(PersonalizationFailedReason paramAnonymousPersonalizationFailedReason)
        {
          switch (PersonalizerService.3.$SwitchMap$com$touchtype_fluency$service$personalize$PersonalizationFailedReason[paramAnonymousPersonalizationFailedReason.ordinal()])
          {
          default: 
            return 2131297117;
          case 1: 
            return 2131297118;
          case 2: 
            return 2131297119;
          case 3: 
            return 2131297120;
          }
          return 2131297121;
        }
        
        public void onFail(PersonalizationFailedReason paramAnonymousPersonalizationFailedReason)
        {
          if (paramAnonymousPersonalizationFailedReason.equals(PersonalizationFailedReason.SERVER_BUSY))
          {
            new PersonalizationToggleReceiver().disablePersonalization(PersonalizerService.this.mContext);
            PersonalizerService.this.disableRemotePersonalizers();
            PersonalizerService.this.createNotification(2131297124, PersonalizerService.RunPersonalizer.this.mFromInstaller, PersonalizerService.RunPersonalizer.this.mServiceName, PersonalizerService.RunPersonalizer.this.mServiceId, true, PersonalizerService.RunPersonalizer.this.mPersonalizerKey);
          }
          for (;;)
          {
            PersonalizerService.this.writeStoredState();
            PersonalizerService.this.personalizerFinished();
            PersonalizerService.RunPersonalizer.this.deleteTmpDirectory();
            return;
            PersonalizerService.this.setPersonalizerState(PersonalizerService.RunPersonalizer.this.mPersonalizerKey, 2, true);
            PersonalizerService.this.setPersonalizerLastRun(PersonalizerService.RunPersonalizer.this.mPersonalizerKey, System.currentTimeMillis());
            PersonalizerService.this.createNotification(getFailureMessageID(paramAnonymousPersonalizationFailedReason), PersonalizerService.RunPersonalizer.this.mFromInstaller, PersonalizerService.RunPersonalizer.this.mServiceName, PersonalizerService.RunPersonalizer.this.mServiceId, true, PersonalizerService.RunPersonalizer.this.mPersonalizerKey);
          }
        }
        
        public void onSuccess()
        {
          if ((PersonalizerService.RunPersonalizer.this.mServiceId.equals(ServiceConfiguration.FACEBOOK)) || (PersonalizerService.RunPersonalizer.this.mServiceId.equals(ServiceConfiguration.TWITTER))) {
            PersonalizerService.this.createFollowLikeNotification(2131297122, PersonalizerService.RunPersonalizer.this.mServiceId, PersonalizerService.RunPersonalizer.this.mPersonalizerKey, PersonalizerService.RunPersonalizer.this.mParams);
          }
          for (;;)
          {
            PersonalizerService.this.setPersonalizerState(PersonalizerService.RunPersonalizer.this.mPersonalizerKey, 3, true);
            PersonalizerService.this.setPersonalizerLastRun(PersonalizerService.RunPersonalizer.this.mPersonalizerKey, System.currentTimeMillis());
            PersonalizerService.this.writeStoredState();
            PersonalizerService.this.requestDataBackup();
            PersonalizerService.this.personalizerFinished();
            PersonalizerService.RunPersonalizer.this.deleteTmpDirectory();
            return;
            PersonalizerService.this.createNotification(2131297122, PersonalizerService.RunPersonalizer.this.mFromInstaller, PersonalizerService.RunPersonalizer.this.mServiceName, PersonalizerService.RunPersonalizer.this.mServiceId, false, PersonalizerService.RunPersonalizer.this.mPersonalizerKey);
          }
        }
      };
    }
    
    private void deleteTmpDirectory()
    {
      if (this.mTmpStore != null) {
        this.mTmpStore.deleteRoot();
      }
    }
    
    public void run()
    {
      if ((!PersonalizerService.this.mOnServiceConnected) || (!PersonalizerService.this.mPredictor.isReady()))
      {
        PersonalizerService.this.fluencyServiceProxy.onCreate(PersonalizerService.this.mContext);
        PersonalizerService.this.mPersonalizerHandler.postDelayed(this.thisParent, 500L);
        return;
      }
      PersonalizerService.this.setPersonalizerState(this.mPersonalizerKey, 1, false);
      PersonalizerService.this.writeStoredState();
      Context localContext = PersonalizerService.this.getApplicationContext();
      TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(PersonalizerService.this.mContext);
      UrlComposer localUrlComposer = new UrlComposer(this.mId, this.mServicePath, localContext);
      this.mTmpStore = new AndroidTmpDirectoryHandler(localContext);
      try
      {
        File localFile = new File(this.mTmpStore.getOrCreateTmpRoot(), this.mModelname);
        boolean bool = localContext.getResources().getBoolean(2131492932);
        String str = localUrlComposer.addLocaleToParams(localUrlComposer.addAuthToParams(localUrlComposer.addTextRetentionToParams(this.mParams, bool), localContext.getResources().getString(2131296322)), localTouchTypePreferences.getEnabledLocales());
        PersonalizationTask localPersonalizationTask = PersonalizationTaskFactory.createRemotePersonalizationChain(PersonalizerService.this.mContext, localUrlComposer.getInitialUrlString(), str, PersonalizerService.this.mTaskExecutor, this.mPersonalizationListener, localFile, PersonalizerService.this.mPredictor);
        PersonalizerService.this.mTaskExecutor.runTask(localPersonalizationTask);
        return;
      }
      catch (IOException localIOException)
      {
        LogUtil.e(PersonalizerService.TAG, "Error creating the tmp folder: " + localIOException.getMessage(), localIOException);
        this.mPersonalizationListener.onFail(PersonalizationFailedReason.OTHER);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PersonalizerService
 * JD-Core Version:    0.7.0.1
 */