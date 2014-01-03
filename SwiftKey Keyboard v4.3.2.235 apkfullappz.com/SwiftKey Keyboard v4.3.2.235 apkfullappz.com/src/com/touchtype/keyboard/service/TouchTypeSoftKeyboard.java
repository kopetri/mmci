package com.touchtype.keyboard.service;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.inputmethodservice.AbstractInputMethodService.AbstractInputMethodSessionImpl;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.InputMethodService.Insets;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.TypedValue;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import com.google.android.voiceime.VoiceRecognitionTrigger;
import com.touchtype.BackupRequestScheduledJob;
import com.touchtype.CustomUpdaterScheduledJob;
import com.touchtype.JobScheduler;
import com.touchtype.RefreshLanguageConfigurationScheduledJob;
import com.touchtype.UserStatsScheduledJob;
import com.touchtype.VoiceInputDialogActivity;
import com.touchtype.VoiceRecognition;
import com.touchtype.VoiceRecognition.VoiceRecognizedContent;
import com.touchtype.broadcast.NotificationRegistrar;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudService.LocalBinder;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.cloud.sync.SyncLearner;
import com.touchtype.cloud.sync.SyncScheduledJob;
import com.touchtype.common.iris.IrisDataSenderService;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype.keyboard.FluencyLearner;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.LearnerDelegator;
import com.touchtype.keyboard.candidates.CandidateStateHandler;
import com.touchtype.keyboard.candidates.CandidatesUpdater;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.candidates.RibbonStateHandler.RibbonState;
import com.touchtype.keyboard.candidates.SwitchLayoutStateHandler;
import com.touchtype.keyboard.candidates.VisibilityListener;
import com.touchtype.keyboard.concurrent.AndroidForegroundExecutor;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype.keyboard.inputeventmodel.FlowAutoCommitter;
import com.touchtype.keyboard.inputeventmodel.FlowFailedCommitter;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.InputEventModelDelegator;
import com.touchtype.keyboard.inputeventmodel.InputEventModelImpl;
import com.touchtype.keyboard.inputeventmodel.KeyPressModelImpl;
import com.touchtype.keyboard.inputeventmodel.ListenerManagerImpl;
import com.touchtype.keyboard.inputeventmodel.MinimalInputMethodService;
import com.touchtype.keyboard.view.BaseKeyboardView;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.keyboard.view.RibbonContainer;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import com.touchtype.logcat.LogcatDumper;
import com.touchtype.preferences.PrioritisedChooserActivity;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.ForceCloseMonitor;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.social.KeystrokesSavedNotificationCreator;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.startup.SplashScreenActivity;
import com.touchtype.util.EnvironmentInfoUtil;
import com.touchtype.util.InstallationId;
import com.touchtype.util.IntentWrapper;
import com.touchtype.util.LogUtil;
import com.touchtype.util.NetworkUtil;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.SwiftKeySDK;
import com.touchtype_fluency.service.FluencyServiceImpl;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorListener;
import com.touchtype_fluency.service.TokenizationProviderImpl;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManager;
import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardMountedListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import java.util.Date;
import java.util.Vector;
import java.util.concurrent.Executors;

public class TouchTypeSoftKeyboard
  extends InputMethodService
  implements SharedPreferences.OnSharedPreferenceChangeListener, VisibilityListener, MinimalInputMethodService, PredictorListener, SDCardListener
{
  private static final String TAG = TouchTypeSoftKeyboard.class.getSimpleName();
  private static TouchTypeSoftKeyboard instance = null;
  private BackgroundExecutor mBackgroundExecutor;
  private CandidateStateHandler mCandidateStateHandler;
  private CandidatesUpdater mCandidatesUpdater;
  private CloudService mCloudService;
  private final ServiceConnection mCloudServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      TouchTypeSoftKeyboard.access$002(TouchTypeSoftKeyboard.this, ((CloudService.LocalBinder)paramAnonymousIBinder).getService());
      TouchTypeSoftKeyboard.this.onCloudServiceBound();
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      TouchTypeSoftKeyboard.this.onCloudServiceUnBound();
      TouchTypeSoftKeyboard.access$002(TouchTypeSoftKeyboard.this, null);
    }
  };
  private FlowAutoCommitter mFlowAutoCommitter;
  private FlowFailedCommitter mFlowFailedCommitter;
  private final FluencyServiceProxy mFluencyServiceProxy = new FluencyServiceProxy();
  private AndroidForegroundExecutor mForegroundExecutor;
  private InputEventModel mInputEventModel;
  private AbstractInputMethodService.AbstractInputMethodSessionImpl mInputMethodSession;
  private boolean mInstallerComplete = false;
  private KeyboardChoreographer mKeyboardChoreographer;
  private KeyboardSwitcher mKeyboardSwitcher;
  private LanguagePackWarning mLanguagePackWarning;
  private LearnerDelegator mLearners;
  private PackageInfoUtil mPackageInfoUtil;
  private RibbonStateHandler mRibbonStateHandler;
  private RibbonContainer mRibbonViewContainer;
  private Predictor mServicePredictor;
  private SwitchLayoutStateHandler mSwitchLayoutStateHandler;
  private SyncLearner mSyncLearner;
  private TouchTypePreferences mTouchTypePreferences;
  private VoiceRecognitionTrigger mVoiceRecognitionTrigger;
  private VoiceRecognition.VoiceRecognizedContent mVoiceRecognizedContent;
  
  protected TouchTypeSoftKeyboard()
  {
    if (instance == null) {
      instance = this;
    }
  }
  
  private void bindToSyncService()
  {
    bindService(new Intent(this, CloudService.class), this.mCloudServiceConnection, 1);
  }
  
  private View createInputView()
  {
    if (this.mKeyboardChoreographer != null) {
      this.mKeyboardChoreographer.closing();
    }
    this.mKeyboardChoreographer = new KeyboardChoreographer(getBaseContext(), this.mKeyboardSwitcher, this.mTouchTypePreferences, this.mRibbonStateHandler, this);
    Context localContext = getApplicationContext();
    if (InstallerPreferences.newInstance(localContext).isInstallComplete(localContext)) {
      showSplashScreenIfRequired();
    }
    return this.mKeyboardChoreographer.getInputView();
  }
  
  private RibbonStateHandler createRibbonStateHandler(Context paramContext, Learner paramLearner, InputEventModel paramInputEventModel, CandidateStateHandler paramCandidateStateHandler, FluencyServiceProxy paramFluencyServiceProxy, TouchTypeStats paramTouchTypeStats)
  {
    RibbonStateHandler localRibbonStateHandler = new RibbonStateHandler(this, paramLearner, paramInputEventModel, paramCandidateStateHandler, paramFluencyServiceProxy, paramTouchTypeStats);
    this.mInputEventModel.addPredictionsEnabledListener(localRibbonStateHandler);
    localRibbonStateHandler.addVisibilityListener(this);
    return localRibbonStateHandler;
  }
  
  private void displayCloudPopupDialog()
  {
    Context localContext = getApplicationContext();
    Intent localIntent = new Intent(this, CloudSetupActivity.class);
    localIntent.setFlags(268468224);
    localIntent.putExtra("CloudSetupActivity.FromUpdate", true);
    localContext.startActivity(localIntent);
  }
  
  private void enableDefaultLanguage(EditorInfo paramEditorInfo)
  {
    if ((ProductConfiguration.isPreinstalled(getApplicationContext())) && (paramEditorInfo != null) && ((0xF & paramEditorInfo.inputType) == 1) && (this.mFluencyServiceProxy.isReady()))
    {
      LanguagePackManager localLanguagePackManager = this.mFluencyServiceProxy.getLanguagePackManager();
      if (localLanguagePackManager != null) {
        localLanguagePackManager.enableDefaultLanguage();
      }
    }
  }
  
  private void enableReporting()
  {
    if ((getResources().getBoolean(2131492909)) && (this.mTouchTypePreferences.isSendErrorEnabled())) {
      ForceCloseMonitor.setUp(getApplicationContext(), getString(2131296335));
    }
  }
  
  private void enabledLanguagePacksChanged()
  {
    this.mLanguagePackWarning.predictorConnected();
    this.mInputEventModel.enabledLanguagePacksChanged(this.mFluencyServiceProxy.getLanguagePackManager().getEnabledLanguagePacks().size());
    refreshCandidates(true);
  }
  
  @Deprecated
  public static TouchTypeSoftKeyboard getInstance()
  {
    return instance;
  }
  
  private int getPackageVersion(Context paramContext)
  {
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      PackageInfo localPackageInfo2 = localPackageManager.getPackageInfo(paramContext.getPackageName(), 0);
      localPackageInfo1 = localPackageInfo2;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      for (;;)
      {
        PackageInfo localPackageInfo1 = null;
      }
    }
    if (localPackageInfo1 != null) {
      return localPackageInfo1.versionCode;
    }
    return -1;
  }
  
  private boolean hasHardKeyboard()
  {
    Configuration localConfiguration = getResources().getConfiguration();
    if (localConfiguration.hardKeyboardHidden == 1) {}
    WindowManager localWindowManager;
    do
    {
      do
      {
        return true;
        if ((localConfiguration.keyboard != 2) || (localConfiguration.hardKeyboardHidden == 2)) {
          break label73;
        }
        localWindowManager = (WindowManager)getSystemService("window");
        if (Build.VERSION.SDK_INT < 8) {
          break;
        }
      } while (!isRotatedFroyoOnwards(localWindowManager));
      return false;
    } while (!isRotatedEclairAndEarlier(localWindowManager));
    return false;
    label73:
    return false;
  }
  
  private boolean hasHardKeyboardAndHasToShowCandidates()
  {
    return (hasHardKeyboard()) && (!getResources().getBoolean(2131492937));
  }
  
  private void incrementLayoutActionStatistics(int paramInt, Resources paramResources)
  {
    if (paramInt == paramResources.getInteger(2131558454)) {
      this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_compact_uses");
    }
    do
    {
      return;
      if (paramInt == paramResources.getInteger(2131558453))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_split_uses");
        return;
      }
      if (paramInt == paramResources.getInteger(2131558452))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_full_uses");
        return;
      }
      if (paramInt == paramResources.getInteger(2131558457))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_dock_uses");
        return;
      }
    } while (paramInt != paramResources.getInteger(2131558456));
    this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_undock_uses");
  }
  
  private void incrementUsage()
  {
    int i = this.mTouchTypePreferences.getInt("usage_count", 0);
    TouchTypePreferences localTouchTypePreferences = this.mTouchTypePreferences;
    int j = i + 1;
    localTouchTypePreferences.putInt("usage_count", j);
    if (!ProductConfiguration.isPreinstalled(getApplicationContext())) {}
    do
    {
      do
      {
        return;
        switch (j)
        {
        default: 
          return;
        }
      } while (getResources().getInteger(2131558409) <= 1);
      displaySettingsNotification(2131297067);
      return;
    } while (!getResources().getBoolean(2131492891));
    displayPersonalizationNotification(2131297114);
  }
  
  private boolean isNewInstall()
  {
    return this.mTouchTypePreferences.getInt("stored_app_version", -1) == -1;
  }
  
  private boolean isRotatedEclairAndEarlier(WindowManager paramWindowManager)
  {
    return paramWindowManager.getDefaultDisplay().getOrientation() != 0;
  }
  
  @TargetApi(8)
  private boolean isRotatedFroyoOnwards(WindowManager paramWindowManager)
  {
    return paramWindowManager.getDefaultDisplay().getRotation() != 0;
  }
  
  private void onCloudServiceBound()
  {
    this.mSyncLearner = new SyncLearner(this.mCloudService);
    this.mLearners.addLearner(this.mSyncLearner);
  }
  
  private void onCloudServiceUnBound()
  {
    this.mLearners.removeLearner(this.mSyncLearner);
  }
  
  private void requestShowSelf()
  {
    if ((this.mInputMethodSession != null) && (!this.mKeyboardChoreographer.isShown())) {
      this.mInputMethodSession.toggleSoftInput(1, 0);
    }
  }
  
  private void resetComposingText()
  {
    this.mInputEventModel.selectionUpdated(-1, -1, -1, -1, -1, -1);
  }
  
  private void runForcedConfigurationUpdate()
  {
    this.mFluencyServiceProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        FluencyServiceImpl.startServiceForAction("com.touchtype.FORCED_REFRESH_LANGUAGES", TouchTypeSoftKeyboard.this);
      }
    });
  }
  
  private void saveLatestUsageStats()
  {
    TouchTypePreferences.getInstance(this).saveStatistics();
    this.mFluencyServiceProxy.saveFluencyMetrics();
  }
  
  private void scheduleJobs()
  {
    boolean bool1 = this.mTouchTypePreferences.areLiveLanguagesEnabled();
    boolean bool2 = this.mTouchTypePreferences.wereLiveLanguagesEnabled();
    JobScheduler localJobScheduler = new JobScheduler();
    if (versionUpdated())
    {
      localJobScheduler.scheduleJobDefaultInterval(new UserStatsScheduledJob(), this, false);
      SDCardReceiver.addMountedListenerGuaranteedOnce(new SDCardMountedListener()
      {
        public void sdCardIsMounted()
        {
          TouchTypeSoftKeyboard.this.runForcedConfigurationUpdate();
        }
      }, this);
      localJobScheduler.scheduleJobDefaultInterval(new RefreshLanguageConfigurationScheduledJob(), this, true);
    }
    for (;;)
    {
      if ((this.mTouchTypePreferences.isSyncEnabled()) && (this.mTouchTypePreferences.getSyncFrequency() != 0)) {
        localJobScheduler.scheduleJobDefaultInterval(new SyncScheduledJob(), this, false);
      }
      localJobScheduler.scheduleJobDefaultInterval(new BackupRequestScheduledJob(), this, false);
      this.mTouchTypePreferences.setWereLiveLanguagesEnabled(bool1);
      if (!this.mTouchTypePreferences.getBoolean("installed_from_playstore", false)) {
        localJobScheduler.scheduleJobDefaultInterval(new CustomUpdaterScheduledJob(), this, false);
      }
      return;
      localJobScheduler.scheduleJob(new UserStatsScheduledJob(), this, false, 5000L);
      if ((bool1) && (!bool2)) {
        localJobScheduler.scheduleJob(new RefreshLanguageConfigurationScheduledJob(), this, true, 1000L);
      } else {
        localJobScheduler.scheduleJobDefaultInterval(new RefreshLanguageConfigurationScheduledJob(), this, false);
      }
    }
  }
  
  private boolean shouldShowSplash()
  {
    int i = getPackageVersion(getApplicationContext());
    int j;
    if (!this.mTouchTypePreferences.getString("splash_screen_last_version", "").equals(""))
    {
      this.mTouchTypePreferences.putString("splash_screen_last_version", "");
      j = getResources().getInteger(2131558460);
    }
    do
    {
      this.mTouchTypePreferences.putInt("splash_screen_last_version_int", i);
      int k = getResources().getInteger(2131558459);
      boolean bool = false;
      if (j < k)
      {
        bool = false;
        if (i >= k) {
          bool = true;
        }
      }
      return bool;
      j = this.mTouchTypePreferences.getInt("splash_screen_last_version_int", -2);
    } while (j != -2);
    this.mTouchTypePreferences.putInt("splash_screen_last_version_int", i);
    return false;
  }
  
  private void showCloudPopupDialogIfRequired()
  {
    boolean bool1 = this.mTouchTypePreferences.getBoolean("cloud_notification_shown", false);
    boolean bool2 = this.mTouchTypePreferences.isCloudAccountSetup();
    boolean bool3 = getResources().getBoolean(2131492890);
    if ((ProductConfiguration.isCloudBuild(getApplicationContext())) && (!bool2) && (!bool1) && (bool3)) {
      displayCloudPopupDialog();
    }
  }
  
  private void showSplashScreenIfRequired()
  {
    boolean bool1 = shouldShowSplash();
    if (!bool1) {}
    boolean bool2;
    String[] arrayOfString;
    do
    {
      return;
      bool2 = getResources().getBoolean(2131492888);
      arrayOfString = getResources().getStringArray(2131623959);
    } while ((!bool2) || (arrayOfString == null) || (arrayOfString.length <= 0) || (!bool1));
    Intent localIntent = new Intent(getApplicationContext(), SplashScreenActivity.class);
    localIntent.setFlags(268435456);
    getApplicationContext().startActivity(localIntent);
  }
  
  private boolean versionUpdated()
  {
    int i = this.mTouchTypePreferences.getInt("stored_app_version", -1);
    int j = getPackageVersion(getApplicationContext());
    this.mTouchTypePreferences.putInt("stored_app_version", j);
    return (i != -1) && (i < j);
  }
  
  private View wrappedRibbon(View paramView)
  {
    this.mRibbonViewContainer = new RibbonContainer(getApplicationContext());
    this.mRibbonViewContainer.addView(paramView);
    return this.mRibbonViewContainer;
  }
  
  public void acceptCompletion(CompletionInfo paramCompletionInfo)
  {
    this.mInputEventModel.onCompletionAccepted(paramCompletionInfo);
  }
  
  public void displayHardKeyboardSettingsNotification(int paramInt)
  {
    if (getResources().getBoolean(2131492887))
    {
      UserNotificationManager localUserNotificationManager = this.mFluencyServiceProxy.getUserNotificationManager();
      if (localUserNotificationManager != null)
      {
        localUserNotificationManager.displayHardKeyboardSettingsNotification(paramInt);
        this.mTouchTypePreferences.putBoolean(getResources().getString(2131296766), false);
      }
    }
  }
  
  public void displayPersonalizationNotification(int paramInt)
  {
    if (getResources().getBoolean(2131492887))
    {
      UserNotificationManager localUserNotificationManager = this.mFluencyServiceProxy.getUserNotificationManager();
      if (localUserNotificationManager != null) {
        localUserNotificationManager.displayPersonalizationNotification(paramInt);
      }
    }
  }
  
  public void displaySettingsNotification(int paramInt)
  {
    if (getResources().getBoolean(2131492887))
    {
      UserNotificationManager localUserNotificationManager = this.mFluencyServiceProxy.getUserNotificationManager();
      if (localUserNotificationManager != null) {
        localUserNotificationManager.displaySettingsNotification(paramInt);
      }
    }
  }
  
  public int getCandidatesHiddenVisibility()
  {
    if ((this.mKeyboardChoreographer != null) && (this.mKeyboardChoreographer.isHandlingCandidateBarItself()) && (!hasHardKeyboard())) {
      return 8;
    }
    return super.getCandidatesHiddenVisibility();
  }
  
  public Date getExpiry()
  {
    return new Date(0L);
  }
  
  public BaseKeyboardView<?> getKeyboardView()
  {
    return this.mKeyboardChoreographer.getKeyboardView();
  }
  
  public void handleClose()
  {
    if (this.mKeyboardChoreographer.hasKeyboard()) {
      requestHideSelf(0);
    }
  }
  
  @TargetApi(11)
  public void onComputeInsets(InputMethodService.Insets paramInsets)
  {
    super.onComputeInsets(paramInsets);
    if ((this.mKeyboardChoreographer != null) && (!hasHardKeyboard()))
    {
      this.mKeyboardChoreographer.computeInsets(paramInsets);
      return;
    }
    paramInsets.contentTopInsets = paramInsets.visibleTopInsets;
  }
  
  public void onConfigurationChanged(Configuration paramConfiguration)
  {
    if ((this.mKeyboardChoreographer != null) && (this.mKeyboardChoreographer.getKeyboardView() != null)) {
      this.mKeyboardChoreographer.closeKeyboardView();
    }
    super.onConfigurationChanged(paramConfiguration);
    if (this.mKeyboardSwitcher != null) {
      this.mKeyboardSwitcher.resetKeyboardToLastKnownState();
    }
    if (this.mRibbonStateHandler != null) {
      this.mRibbonStateHandler.updateView();
    }
    if ((hasHardKeyboard()) && (this.mRibbonStateHandler != null)) {
      this.mRibbonStateHandler.onStartInput();
    }
  }
  
  public void onCreate()
  {
    super.onCreate();
    Context localContext = getApplicationContext();
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(localContext);
    this.mTouchTypePreferences.registerOnSharedPreferenceChangeListener(this);
    this.mVoiceRecognizedContent = null;
    this.mKeyboardSwitcher = null;
    this.mFluencyServiceProxy.onCreate(localContext);
    this.mVoiceRecognitionTrigger = new VoiceRecognitionTrigger(this);
    this.mPackageInfoUtil = new PackageInfoUtil(localContext);
    enableReporting();
    SwiftKeySDK.getVersion();
    LegacyTouchUtils localLegacyTouchUtils = new LegacyTouchUtils();
    this.mLanguagePackWarning = new LanguagePackWarning(this.mFluencyServiceProxy, ProductConfiguration.isPreinstalled(localContext));
    this.mBackgroundExecutor = new BackgroundExecutor(Executors.newSingleThreadExecutor());
    this.mForegroundExecutor = new AndroidForegroundExecutor();
    this.mLearners = new LearnerDelegator();
    this.mCandidatesUpdater = new CandidatesUpdater(this.mFluencyServiceProxy, this.mBackgroundExecutor, this.mForegroundExecutor);
    this.mCandidateStateHandler = CandidateStateHandler.createCandidateStateHandler(getApplicationContext(), this.mTouchTypePreferences.getTouchTypeStats());
    ListenerManagerImpl localListenerManagerImpl = new ListenerManagerImpl();
    InputEventModelDelegator localInputEventModelDelegator = new InputEventModelDelegator(InputEventModelImpl.createDefault(localContext, this, this.mBackgroundExecutor, localListenerManagerImpl, this.mCandidatesUpdater, this.mCandidateStateHandler, this.mLearners, new TokenizationProviderImpl(this.mFluencyServiceProxy), new KeyPressModelImpl(this.mFluencyServiceProxy), this.mTouchTypePreferences, localLegacyTouchUtils), this.mFluencyServiceProxy, this.mCandidateStateHandler, localListenerManagerImpl, this.mLearners);
    this.mInputEventModel = localInputEventModelDelegator;
    this.mInputEventModel.addUpdatedCandidatesListener(this.mCandidateStateHandler, this.mCandidateStateHandler.getNumberOfCandidates());
    this.mRibbonStateHandler = createRibbonStateHandler(getBaseContext(), this.mLearners, this.mInputEventModel, this.mCandidateStateHandler, this.mFluencyServiceProxy, this.mTouchTypePreferences.getTouchTypeStats());
    this.mFlowAutoCommitter = new FlowAutoCommitter(this.mInputEventModel, this.mBackgroundExecutor);
    this.mCandidatesUpdater.addListener(this.mFlowAutoCommitter);
    this.mFlowFailedCommitter = new FlowFailedCommitter(this.mInputEventModel);
    this.mCandidatesUpdater.addListener(this.mFlowFailedCommitter);
    this.mInputEventModel.onCreate(localContext);
    this.mKeyboardSwitcher = new KeyboardSwitcher(localContext, this.mTouchTypePreferences, this.mInputEventModel, this.mFluencyServiceProxy, this.mCandidateStateHandler);
    this.mKeyboardSwitcher.addKeyboardChangeListener(this.mInputEventModel);
    this.mKeyboardSwitcher.addKeyboardChangeListener(this.mCandidateStateHandler);
    this.mSwitchLayoutStateHandler = new SwitchLayoutStateHandler(this, this.mKeyboardSwitcher);
    this.mKeyboardSwitcher.addKeyboardChangeListener(this.mSwitchLayoutStateHandler);
    this.mTouchTypePreferences.registerOnSharedPreferenceChangeListener(this.mKeyboardSwitcher);
    SDCardReceiver.addListener(this);
    this.mFluencyServiceProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        TouchTypeSoftKeyboard.access$302(TouchTypeSoftKeyboard.this, TouchTypeSoftKeyboard.this.mFluencyServiceProxy.getPredictor());
        if (TouchTypeSoftKeyboard.this.mServicePredictor != null)
        {
          TouchTypeSoftKeyboard.this.mServicePredictor.addListener(TouchTypeSoftKeyboard.this);
          if (TouchTypeSoftKeyboard.this.mServicePredictor.isReady()) {
            TouchTypeSoftKeyboard.this.enabledLanguagePacksChanged();
          }
          TouchTypeSoftKeyboard.this.mLearners.addLearner(new FluencyLearner(TouchTypeSoftKeyboard.this.mServicePredictor, TouchTypeSoftKeyboard.this.mBackgroundExecutor));
        }
        TouchTypeSoftKeyboard.this.mFluencyServiceProxy.getLayoutManager().addListener(TouchTypeSoftKeyboard.this.mKeyboardSwitcher);
        TouchTypeSoftKeyboard.this.mFluencyServiceProxy.getPredictor().addListener(TouchTypeSoftKeyboard.this.mKeyboardSwitcher);
        Session localSession = TouchTypeSoftKeyboard.this.mFluencyServiceProxy.getSession();
        if (localSession != null) {
          TouchTypeSoftKeyboard.this.mInputEventModel.setFluencyPunctuator(localSession.getPunctuator());
        }
      }
    });
    if (this.mTouchTypePreferences.isCloudAccountSetup()) {
      bindToSyncService();
    }
    if (!isNewInstall()) {
      TouchTypePreferences.getInstance(localContext).putBoolean("installed_from_playstore", true);
    }
    scheduleJobs();
    NotificationRegistrar.register(this, IrisDataSenderService.sendDataClass());
    LogUtil.e(TAG, "INSTALLATION ID: " + InstallationId.getId(localContext));
  }
  
  public View onCreateCandidatesView()
  {
    View localView = this.mRibbonStateHandler.getNewRibbonView();
    this.mRibbonStateHandler.addVisibilityListener(this);
    if (ProductConfiguration.isWatchBuild(this))
    {
      LinearLayout localLinearLayout1 = new LinearLayout(this);
      if (ProductConfiguration.isWatchOnLargeBuild(this)) {}
      for (int i = Math.round(TypedValue.applyDimension(5, 28.0F, getResources().getDisplayMetrics()));; i = -1)
      {
        ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(i, -2);
        localLinearLayout1.addView(this.mSwitchLayoutStateHandler.getNewLeftLsView());
        localLinearLayout1.addView(localView, new LinearLayout.LayoutParams(-1, -2, 70.0F));
        localLinearLayout1.addView(this.mSwitchLayoutStateHandler.getNewRightLsView());
        LinearLayout localLinearLayout2 = (LinearLayout)getLayoutInflater().inflate(2130903110, null);
        localLinearLayout2.addView(localLinearLayout1, localLayoutParams);
        return localLinearLayout2;
      }
    }
    return wrappedRibbon(localView);
  }
  
  public AbstractInputMethodService.AbstractInputMethodSessionImpl onCreateInputMethodSessionInterface()
  {
    this.mInputMethodSession = super.onCreateInputMethodSessionInterface();
    return this.mInputMethodSession;
  }
  
  public View onCreateInputView()
  {
    return createInputView();
  }
  
  public void onDestroy()
  {
    if (this.mTouchTypePreferences != null)
    {
      this.mTouchTypePreferences.unregisterOnSharedPreferenceChangeListener(this.mKeyboardSwitcher);
      this.mTouchTypePreferences.unregisterOnSharedPreferenceChangeListener(this);
    }
    if (this.mServicePredictor != null) {
      this.mServicePredictor.removeListener(this);
    }
    SDCardReceiver.removeListener(this);
    if (this.mFluencyServiceProxy != null) {
      this.mFluencyServiceProxy.onDestroy(this);
    }
    instance = null;
    super.onDestroy();
    this.mCandidatesUpdater.removeListener(this.mFlowAutoCommitter);
    this.mFlowAutoCommitter = null;
    this.mCandidatesUpdater.removeListener(this.mFlowFailedCommitter);
    this.mFlowFailedCommitter = null;
    this.mKeyboardSwitcher.removeKeyboardChangeListener(this.mInputEventModel);
    this.mInputEventModel.onDestroy(this);
    if (this.mCloudService != null) {
      unbindService(this.mCloudServiceConnection);
    }
    this.mForegroundExecutor.shutdown();
  }
  
  public void onDisplayCompletions(CompletionInfo[] paramArrayOfCompletionInfo)
  {
    super.onDisplayCompletions(paramArrayOfCompletionInfo);
    if (this.mRibbonStateHandler != null) {
      this.mRibbonStateHandler.onDisplayCompletions(paramArrayOfCompletionInfo, this, isFullscreenMode());
    }
  }
  
  public boolean onEvaluateFullscreenMode()
  {
    if (getResources().getConfiguration().orientation == 2)
    {
      EditorInfo localEditorInfo = getCurrentInputEditorInfo();
      if (localEditorInfo == null) {
        LogUtil.w(TAG, "onEvaluateFullscreenMode: EditorInfo is null!");
      }
      int i;
      int k;
      do
      {
        return false;
        i = 0x10000000 & localEditorInfo.imeOptions;
        int j = Build.VERSION.SDK_INT;
        k = 0;
        if (j >= 11) {
          k = 0x2000000 & localEditorInfo.imeOptions;
        }
      } while ((i != 0) || (k != 0) || (this.mKeyboardChoreographer == null) || (!this.mKeyboardChoreographer.isDocked()) || (!getResources().getBoolean(2131492907)));
      return true;
    }
    return getResources().getBoolean(2131492908);
  }
  
  public boolean onEvaluateInputViewShown()
  {
    Context localContext = getApplicationContext();
    int i;
    if (!InstallerPreferences.newInstance(localContext).isInstallComplete(localContext))
    {
      i = this.mTouchTypePreferences.getInt("pref_installer_not_run", -1);
      if ((i > 8) || (i < 0))
      {
        String str = getResources().getString(2131297302);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = getResources().getString(2131296303);
        Toast.makeText(this, String.format(str, arrayOfObject), 1).show();
        this.mTouchTypePreferences.putInt("pref_installer_not_run", 0);
        UserNotificationManager localUserNotificationManager = this.mFluencyServiceProxy.getUserNotificationManager();
        if (localUserNotificationManager != null) {
          localUserNotificationManager.displayInstallerNotification();
        }
        if ((!hasHardKeyboardAndHasToShowCandidates()) || (this.mRibbonStateHandler == null)) {
          break label169;
        }
        this.mRibbonStateHandler.onStartInput();
      }
    }
    label169:
    while ((getCurrentInputBinding() == null) && (isInputViewShown()) && (!Settings.Secure.getString(getContentResolver(), "default_input_method").startsWith(getPackageName() + "/")))
    {
      return false;
      this.mTouchTypePreferences.putInt("pref_installer_not_run", i + 1);
      break;
      this.mInstallerComplete = true;
      this.mLanguagePackWarning.keyboardVisible();
      break;
    }
    if ((this.mInputEventModel != null) && (this.mInputEventModel.evaluateInputShownUsedInsteadOfUpdateSelection()))
    {
      resetComposingText();
      refreshCandidates(false);
      this.mInputEventModel.updateShiftState();
    }
    return super.onEvaluateInputViewShown();
  }
  
  public void onExtractedCursorMovement(int paramInt1, int paramInt2) {}
  
  public void onExtractedTextClicked() {}
  
  public void onFinishCandidatesView(boolean paramBoolean)
  {
    super.onFinishCandidatesView(paramBoolean);
  }
  
  public void onFinishInput()
  {
    this.mInputEventModel.onFinishInput();
    saveLatestUsageStats();
    KeystrokesSavedNotificationCreator.initiateKeystrokesNotificationIfRequired(getApplicationContext());
    super.onFinishInput();
  }
  
  public void onFinishInputView(boolean paramBoolean)
  {
    super.onFinishInputView(paramBoolean);
    this.mInputEventModel.onFinishInputView();
    KeystrokesSavedNotificationCreator.initiateKeystrokesNotificationIfRequired(getApplicationContext());
    LogcatDumper.createOrAppendLogsIfRequired(this);
    Context localContext = getApplicationContext();
    if (InstallerPreferences.newInstance(localContext).isInstallComplete(localContext)) {
      showCloudPopupDialogIfRequired();
    }
    if (EnvironmentInfoUtil.getManufacturer().hashCode() == EnvironmentInfoUtil.MANUFACTURER_HTC_HASHCODE)
    {
      Intent localIntent = new Intent("HTC_IME_CURRENT_STATE", null);
      localIntent.putExtra("SIP_VISIBLE", false);
      getApplicationContext().sendBroadcast(localIntent);
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = true;
    switch (paramInt)
    {
    default: 
      if ((!this.mInputEventModel.onHardKeyDown(paramInt, paramKeyEvent)) && (!super.onKeyDown(paramInt, paramKeyEvent))) {
        break;
      }
    }
    for (;;)
    {
      if (bool)
      {
        showHardKeyboardNotificationsIfRequired();
        if ((this.mRibbonStateHandler != null) && (this.mRibbonStateHandler.getRibbonState() == RibbonStateHandler.RibbonState.HIDDEN)) {
          this.mRibbonStateHandler.onStartInput();
        }
      }
      do
      {
        return bool;
        if (paramKeyEvent.getRepeatCount() != 0) {
          break;
        }
      } while ((this.mKeyboardChoreographer != null) && (this.mKeyboardChoreographer.handleBack()));
      if (super.onKeyDown(paramInt, paramKeyEvent))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_back_toclosekb_uses");
        return bool;
      }
      return false;
      if (!EnvironmentInfoUtil.getManufacturer().contentEquals("motorola")) {
        break;
      }
      startVoiceRecognition();
      return bool;
      bool = false;
    }
    return false;
  }
  
  public boolean onKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return (this.mInputEventModel.onHardKeyUp(paramInt, paramKeyEvent)) || (super.onKeyUp(paramInt, paramKeyEvent));
  }
  
  protected void onLicenseAndNotificationCheck() {}
  
  public void onMediaMounted()
  {
    if (this.mInputEventModel != null)
    {
      this.mInputEventModel.setStorageAvailable(true);
      this.mKeyboardSwitcher.reloadKeyboard();
    }
  }
  
  public void onMediaUnmounted()
  {
    if (this.mInputEventModel != null)
    {
      this.mInputEventModel.setStorageAvailable(false);
      this.mKeyboardSwitcher.reloadKeyboard();
    }
  }
  
  public void onPredictorError() {}
  
  public void onPredictorReady()
  {
    this.mForegroundExecutor.execute(new Runnable()
    {
      public void run()
      {
        TouchTypeSoftKeyboard.this.enabledLanguagePacksChanged();
      }
    });
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (paramString.equals("cloud_account_setup")) {
      if (this.mTouchTypePreferences.isCloudAccountSetup()) {
        bindToSyncService();
      }
    }
    while (!paramString.equals("pref_screen_live_language_key"))
    {
      return;
      if (this.mCloudService != null) {
        unbindService(this.mCloudServiceConnection);
      }
      this.mCloudService = null;
      return;
    }
    boolean bool = this.mTouchTypePreferences.areLiveLanguagesEnabled();
    if (bool != this.mTouchTypePreferences.wereLiveLanguagesEnabled())
    {
      if (!bool) {
        break label110;
      }
      new JobScheduler().scheduleJob(new RefreshLanguageConfigurationScheduledJob(), this, true, 5000L);
    }
    for (;;)
    {
      this.mTouchTypePreferences.setWereLiveLanguagesEnabled(bool);
      return;
      label110:
      if (this.mServicePredictor != null) {
        this.mServicePredictor.reloadLanguagePacks();
      }
    }
  }
  
  public void onStartInput(EditorInfo paramEditorInfo, boolean paramBoolean)
  {
    this.mTouchTypePreferences.clearCache();
    if (this.mKeyboardChoreographer != null) {
      this.mKeyboardChoreographer.newEditorInfo(paramEditorInfo);
    }
    super.onStartInput(paramEditorInfo, paramBoolean);
    this.mInputEventModel.onStartInput(paramEditorInfo, this.mPackageInfoUtil, paramBoolean, hasHardKeyboard());
    enableDefaultLanguage(paramEditorInfo);
    if (this.mVoiceRecognizedContent != null)
    {
      this.mVoiceRecognizedContent.fill(this.mInputEventModel);
      this.mVoiceRecognizedContent = null;
    }
    onLicenseAndNotificationCheck();
    if (hasHardKeyboardAndHasToShowCandidates()) {
      this.mRibbonStateHandler.onStartInput();
    }
    if (this.mInputEventModel.isPredictionEnabled()) {
      incrementUsage();
    }
  }
  
  public void onStartInputView(EditorInfo paramEditorInfo, boolean paramBoolean)
  {
    super.onStartInputView(paramEditorInfo, paramBoolean);
    this.mVoiceRecognitionTrigger.onStartInputView();
    this.mInputEventModel.onStartInputView(paramEditorInfo, this.mPackageInfoUtil, paramBoolean, hasHardKeyboard());
    this.mKeyboardChoreographer.newEditorInfo(paramEditorInfo);
    this.mKeyboardChoreographer.startInput();
    this.mKeyboardSwitcher.startInputView(paramEditorInfo);
    this.mRibbonStateHandler.onStartInput();
    if (EnvironmentInfoUtil.getManufacturer().hashCode() == EnvironmentInfoUtil.MANUFACTURER_HTC_HASHCODE)
    {
      Intent localIntent = new Intent("HTC_IME_CURRENT_STATE", null);
      localIntent.putExtra("SIP_VISIBLE", true);
      getApplicationContext().sendBroadcast(localIntent);
    }
  }
  
  public void onUpdateSelection(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    super.onUpdateSelection(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
    this.mInputEventModel.selectionUpdated(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void onVisibilityChanged(boolean paramBoolean)
  {
    if ((this.mKeyboardChoreographer != null) && (this.mKeyboardChoreographer.showsItsOwnCandidateBar()) && (!hasHardKeyboard()))
    {
      setCandidatesViewShown(false);
      return;
    }
    setCandidatesViewShown(paramBoolean);
  }
  
  public void onWindowHidden()
  {
    try
    {
      super.onWindowHidden();
      this.mInputEventModel.onKeyboardHidden();
      if (this.mRibbonStateHandler != null) {
        this.mRibbonStateHandler.onWindowHidden();
      }
      this.mTouchTypePreferences.getTouchTypeStats().keyboardClosed(getApplicationContext());
      return;
    }
    catch (SecurityException localSecurityException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Power management in InputMethodService.onWindowHidden caused SecurityException");
      }
    }
  }
  
  public void onWindowShown()
  {
    super.onWindowShown();
    TouchTypeStats localTouchTypeStats = this.mTouchTypePreferences.getTouchTypeStats();
    if ((this.mKeyboardChoreographer != null) && (this.mKeyboardChoreographer.hasKeyboard())) {
      localTouchTypeStats.keyboardOpened();
    }
    if (this.mInputEventModel.isPredictionEnabled()) {
      localTouchTypeStats.predictionsOpened();
    }
    localTouchTypeStats.updateOrientationStatistic(getResources().getConfiguration().orientation);
  }
  
  public void prefs(View paramView)
  {
    Object localObject = paramView.getTag();
    int i = -1;
    if (localObject != null) {
      i = Integer.parseInt(localObject.toString());
    }
    Resources localResources = getResources();
    KeyboardChoreographer localKeyboardChoreographer = this.mKeyboardChoreographer;
    boolean bool;
    if (i != -1)
    {
      bool = true;
      localKeyboardChoreographer.dismissPopupMenu(bool);
      if (i != localResources.getInteger(2131558439)) {
        break label96;
      }
      this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_popup_support_uses");
      paramView.performHapticFeedback(1);
      IntentWrapper.launchIntent(this, localResources.getString(2131296750));
    }
    label96:
    do
    {
      return;
      bool = false;
      break;
      if (i == localResources.getInteger(2131558443))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_popup_tutorial_uses");
        paramView.performHapticFeedback(1);
        IntentWrapper.launchIntent(this, Uri.parse("http://video.swiftkey.net"));
        return;
      }
      if (i == localResources.getInteger(2131558436))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_popup_settings_uses");
        paramView.performHapticFeedback(1);
        IntentWrapper.launchIntent(this, localResources.getString(2131296751));
        return;
      }
      if (i == localResources.getInteger(2131558440))
      {
        paramView.performHapticFeedback(1);
        IntentWrapper.launchIntent(this, localResources.getString(2131296639));
        return;
      }
      if (i == localResources.getInteger(2131558444))
      {
        paramView.performHapticFeedback(1);
        IntentWrapper.launchIntent(this, localResources.getString(2131296640));
        return;
      }
      if (i == localResources.getInteger(2131558437))
      {
        paramView.performHapticFeedback(1);
        if (localResources.getBoolean(2131492914))
        {
          this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_popup_share_uses");
          String str1 = localResources.getString(2131296303);
          String str2 = String.format(localResources.getString(2131297069), new Object[] { str1 });
          String str3 = String.format(localResources.getString(2131297070), new Object[] { str1 });
          String str4 = String.format(localResources.getString(2131297068), new Object[] { str1 });
          Intent localIntent1 = new Intent("android.intent.action.SEND");
          localIntent1.setType("text/plain");
          localIntent1.putExtra("android.intent.extra.SUBJECT", str2);
          localIntent1.putExtra("android.intent.extra.TEMPLATE", str2);
          localIntent1.putExtra("android.intent.extra.TEXT", str3);
          Intent localIntent2 = PrioritisedChooserActivity.createChooser(this, localIntent1, str4);
          localIntent2.addFlags(268435456);
          startActivity(localIntent2);
          return;
        }
        Toast.makeText(this, localResources.getString(2131296892), 1).show();
        return;
      }
      if (i == localResources.getInteger(2131558441))
      {
        this.mTouchTypePreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_popup_web_uses");
        paramView.performHapticFeedback(1);
        IntentWrapper.launchIntent(this, Uri.parse("http://www.swiftkey.net/"));
        return;
      }
    } while ((i != localResources.getInteger(2131558454)) && (i != localResources.getInteger(2131558453)) && (i != localResources.getInteger(2131558452)) && (i != localResources.getInteger(2131558455)) && (i != localResources.getInteger(2131558457)) && (i != localResources.getInteger(2131558456)));
    incrementLayoutActionStatistics(i, localResources);
    paramView.performHapticFeedback(1);
    this.mKeyboardChoreographer.requestState(i);
  }
  
  public void refreshCandidates(boolean paramBoolean)
  {
    this.mInputEventModel.refreshPredictions(paramBoolean);
  }
  
  public void resize(View paramView)
  {
    Resources localResources = getResources();
    Object localObject = paramView.getTag();
    int i = -1;
    if (localObject != null) {
      i = Integer.parseInt(localObject.toString());
    }
    if (i == localResources.getInteger(2131558446)) {
      this.mKeyboardChoreographer.dismissPopupMenu(false);
    }
    while (i != localResources.getInteger(2131558445)) {
      return;
    }
    this.mKeyboardChoreographer.dismissPopupMenu(true);
  }
  
  public void saveVoiceRecognitionText(VoiceRecognition.VoiceRecognizedContent paramVoiceRecognizedContent)
  {
    if (!paramVoiceRecognizedContent.verifiedFill(this.mInputEventModel, getCurrentInputConnection())) {
      this.mVoiceRecognizedContent = paramVoiceRecognizedContent;
    }
    requestShowSelf();
  }
  
  public void showHardKeyboardNotificationsIfRequired()
  {
    if (this.mTouchTypePreferences.getBoolean("during_cloud_account_setup", false)) {}
    Context localContext;
    InstallerPreferences localInstallerPreferences;
    do
    {
      return;
      localContext = getApplicationContext();
      localInstallerPreferences = InstallerPreferences.newInstance(localContext);
    } while ((!this.mTouchTypePreferences.isHardKeyboardSettingsNotificationEnabled()) || (!localInstallerPreferences.isInstallComplete(localContext)));
    displayHardKeyboardSettingsNotification(2131296936);
  }
  
  public void startVoiceRecognition()
  {
    InputConnection localInputConnection = getCurrentInputConnection();
    int i;
    if (localInputConnection == null)
    {
      i = 1;
      if (this.mTouchTypePreferences.isVoiceEnabled()) {
        break label37;
      }
    }
    label37:
    for (int j = 1;; j = 0)
    {
      if ((i == 0) && (j == 0)) {
        break label42;
      }
      return;
      i = 0;
      break;
    }
    label42:
    if ((NetworkUtil.isInternetAvailable(getApplicationContext())) || (Build.VERSION.SDK_INT > 15))
    {
      if (Build.VERSION.SDK_INT < 14)
      {
        requestHideSelf(0);
        VoiceRecognition.attemptVoiceRecognition(this, this.mInputEventModel.isSearchField(), localInputConnection);
        return;
      }
      this.mVoiceRecognitionTrigger.startVoiceRecognition();
      return;
    }
    Intent localIntent = new Intent(getApplicationContext(), VoiceInputDialogActivity.class);
    localIntent.setFlags(268435456);
    getApplicationContext().startActivity(localIntent);
  }
  
  public static enum ShiftState
  {
    static
    {
      SHIFTED = new ShiftState("SHIFTED", 1);
      CAPSLOCKED = new ShiftState("CAPSLOCKED", 2);
      ShiftState[] arrayOfShiftState = new ShiftState[3];
      arrayOfShiftState[0] = UNSHIFTED;
      arrayOfShiftState[1] = SHIFTED;
      arrayOfShiftState[2] = CAPSLOCKED;
      $VALUES = arrayOfShiftState;
    }
    
    private ShiftState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.service.TouchTypeSoftKeyboard
 * JD-Core Version:    0.7.0.1
 */