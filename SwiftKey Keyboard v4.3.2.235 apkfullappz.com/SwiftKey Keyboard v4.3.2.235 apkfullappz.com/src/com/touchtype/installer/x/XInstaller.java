package com.touchtype.installer.x;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.Toast;
import android.widget.ViewAnimator;
import com.google.gson.Gson;
import com.touchtype.Launcher;
import com.touchtype.TouchTypeUtilities;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.common.iris.IrisDataSenderService;
import com.touchtype.installer.EulaLicenceView;
import com.touchtype.installer.InstallerPreferences;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.json.InstallerStatsReport;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.IntentWrapper;
import com.touchtype.util.LogUtil;
import com.touchtype.util.NetworkUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorListener;
import com.touchtype_fluency.service.ProgressListener;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import com.touchtype_fluency.service.personalize.DynamicPersonalizerModel;
import com.touchtype_fluency.service.personalize.Personalizer;
import com.touchtype_fluency.service.personalize.Personalizer.PersonalizerAuthenticationCallback;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

public class XInstaller
  extends FragmentActivity
  implements PredictorListener
{
  protected static final String TAG = XInstaller.class.getSimpleName();
  protected MenuItem mActiveView;
  protected boolean mBackgrounded = false;
  private String mCloudUsername;
  private final LanguagePackListener mConfigurationListener = new InstallerLanguagePackListener(this);
  protected boolean mDownloadProgressUpdating = false;
  protected boolean mDownloadingCancelled = false;
  protected boolean mDownloadingLanguagePackInProgress = false;
  protected boolean mDownloadingLanguagesInProgress = false;
  private boolean mEulaAccepted = false;
  private Bundle mExtras;
  private final FluencyServiceProxy mFluencyServiceProxy = new FluencyServiceProxy();
  private int mFrame;
  protected Handler mHandler = new Handler();
  private InstallerPreferences mInstallerPrefs;
  private String mLanguageID;
  protected LanguagePackManager mLanguagePackManager;
  protected int mLanguagePackRetryCount;
  protected Handler mLanguagePackWatcher = new Handler();
  protected Runnable mLanguagePackWatcherTimedTask;
  protected ProgressListener mLanguageProgressListener;
  private int mMaxFrame;
  private List<String> mPersonalizationServicesLaunched = new ArrayList();
  protected String mProductName;
  private Resources mResources;
  protected LanguagePack mSelectedLanguage;
  protected boolean mShowMoreLanguagesToast = true;
  protected Runnable mTimedTask;
  private UserNotificationManager mUserNotificationManager;
  private ViewAnimator mViewAnimator;
  private RunTimeUpdater rtu;
  
  private void checkForSDCard()
  {
    if (!TouchTypeUtilities.isStorageAvailable()) {
      showDialogFragment(6);
    }
  }
  
  private Personalizer.PersonalizerAuthenticationCallback createPersonalizerAuthenticationCallback()
  {
    new Personalizer.PersonalizerAuthenticationCallback()
    {
      public void onAuthenticationFailed(String paramAnonymousString) {}
      
      public void onAuthenticationStarted(String paramAnonymousString1, String paramAnonymousString2) {}
      
      public void onAuthenticationSuccess(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4)
      {
        XInstaller.this.mPersonalizationServicesLaunched.add(paramAnonymousString1);
      }
    };
  }
  
  private int getInitialFrame()
  {
    Context localContext = getApplicationContext();
    int i = this.mInstallerPrefs.getInt("prefs_install_state", -1);
    if ((this.mInstallerPrefs.isInstallComplete(localContext)) && (!this.mInstallerPrefs.isRestoreComplete(localContext)))
    {
      showRestoreDialog();
      i = 1;
    }
    return i;
  }
  
  private LanguagePack getLanguagePackToAutoInstall()
  {
    Vector localVector;
    String str;
    if (this.mLanguagePackManager != null)
    {
      localVector = this.mLanguagePackManager.getLanguagePacks();
      str = getResources().getString(2131296323);
      if (str.length() == 0) {}
    }
    try
    {
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext())
      {
        LanguagePack localLanguagePack = (LanguagePack)localIterator.next();
        if (str.equals(localLanguagePack.getID())) {
          return localLanguagePack;
        }
      }
      return null;
    }
    finally {}
  }
  
  private void launchLocalParser(ServiceConfiguration paramServiceConfiguration)
  {
    DynamicPersonalizerModel localDynamicPersonalizerModel = new DynamicPersonalizerModel(DynamicPersonalizerModel.generateKey(paramServiceConfiguration.getName(), null), null, null);
    Personalizer localPersonalizer = new Personalizer(getApplicationContext(), paramServiceConfiguration, createPersonalizerAuthenticationCallback());
    localPersonalizer.setFromInstaller(true);
    localPersonalizer.startPersonalization(this, localDynamicPersonalizerModel);
  }
  
  private void restoreState(Bundle paramBundle)
  {
    Bundle localBundle = paramBundle.getBundle("installer_state");
    this.mLanguageID = localBundle.getString("mLanguageID");
    this.mCloudUsername = localBundle.getString("mCloudUsername");
    this.mShowMoreLanguagesToast = localBundle.getBoolean("mShowMoreLanguagesToast", true);
  }
  
  private void saveState()
  {
    synchronized (this.mInstallerPrefs.edit())
    {
      ???.putInt("prefs_install_state", this.mFrame).commit();
      return;
    }
  }
  
  private void sendInstallerStatsReport()
  {
    Context localContext = getApplicationContext();
    startService(IrisDataSenderService.sendDataIntent(localContext, getString(2131296333), new Gson().toJson(new InstallerStatsReport(localContext), InstallerStatsReport.class)));
  }
  
  private void setCustomFrameProperties()
  {
    Context localContext = getApplicationContext();
    this.mInstallerPrefs.setInstallerProgressStat(this.mFrame);
    switch (this.mFrame)
    {
    default: 
    case 0: 
    case 1: 
    case 2: 
      do
      {
        do
        {
          return;
          setDefaultInputStatus(2131230949);
          setSkEnabledStatus(2131230948);
          setEnableCloudStatus(2131230950);
          setFlowStatus(2131230951);
          setDownloadingInProgress();
          return;
          setDefaultInputStatus(2131230954);
          setDownloadedDisplay(2131230952);
          setSkEnabledStatus(2131230953);
          setEnableCloudStatus(2131230955);
          setFlowStatus(2131230956);
        } while (this.mInstallerPrefs.isRestoreComplete(localContext));
        setRestoredDisplay(2131230952, 2131230956, 2131230955);
        return;
        setDefaultInputStatus(2131230959);
        setDownloadedDisplay(2131230957);
        setEnableCloudStatus(2131230960);
        setFlowStatus(2131230961);
      } while (this.mInstallerPrefs.isRestoreComplete(localContext));
      setRestoredDisplay(2131230957, 2131230961, 2131230960);
      return;
    case 3: 
      setDefaultInputStatus(2131230964);
      setDownloadedDisplay(2131230962);
      setEnableCloudStatus(2131230965);
      setFlowStatus(2131230966);
      wrapupInstaller();
      return;
    case 4: 
      setDefaultInputStatus(2131230969);
      setDownloadedDisplay(2131230967);
      setEnableCloudStatus(2131230970);
      setEnableCloudDisplay(2131230970);
      setFlowStatus(2131230971);
      return;
    }
    ((ScrollView)findViewById(2131230945)).scrollTo(0, 0);
    MenuItemIcon localMenuItemIcon = (MenuItemIcon)findViewById(2131230973);
    if (this.mInstallerPrefs.getInstallerCloudPersonalisedGmail()) {
      localMenuItemIcon.setSummary(getString(2131296385));
    }
    if (ProductConfiguration.isCloudBuild(localContext)) {}
    for (int i = 0;; i = 8)
    {
      localMenuItemIcon.setVisibility(i);
      String str = this.mResources.getString(2131296405);
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mProductName;
      Toast.makeText(this, String.format(str, arrayOfObject), 1).show();
      wrapupRestore();
      this.rtu = new RunTimeUpdater(this);
      this.rtu.useAlertDialog();
      this.rtu.checkVersionAndUpgradeIfNeeded();
      return;
    }
  }
  
  private void setDefaultInputStatus(int paramInt)
  {
    if ((TouchTypeUtilities.checkIMEStatus(this)) && (this.mFrame < 2))
    {
      MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
      localMenuItem.setCompleted(true);
      localMenuItem.setupInternalState();
      localMenuItem.setDefaultColors();
      String str1 = this.mResources.getString(2131296399);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mProductName;
      localMenuItem.setTitle(String.format(str1, arrayOfObject1));
      String str2 = this.mResources.getString(2131296364);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mProductName;
      localMenuItem.setSummary(String.format(str2, arrayOfObject2));
    }
  }
  
  private void setDownloadedDisplay(int paramInt)
  {
    MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
    if (ProductConfiguration.hasBundledLanguagePacks(getApplicationContext()))
    {
      localMenuItem.setVisibility(8);
      return;
    }
    localMenuItem.setSummary(this.mResources.getString(2131296369) + " " + this.mInstallerPrefs.getString("installer_downloaded_language", ""));
  }
  
  private void setEnableCloudDisplay(int paramInt)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    this.mInstallerPrefs.setInstallerCloudEnabled(localTouchTypePreferences.isCloudAccountSetup());
    this.mInstallerPrefs.setInstallerCloudMarketingEnabled(localTouchTypePreferences.isCloudMarketingAllowed());
    this.mInstallerPrefs.setInstallerCloudDeviceId(localTouchTypePreferences.getCloudDeviceId());
    if (localTouchTypePreferences.contains("cloud_personalised_gmail")) {
      this.mInstallerPrefs.setInstallerCloudPersonalisedGmail(localTouchTypePreferences.getBoolean("cloud_personalised_gmail", false));
    }
    MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
    String str2;
    Object[] arrayOfObject;
    if (localTouchTypePreferences.isCloudAccountSetup())
    {
      str2 = getString(2131296372);
      arrayOfObject = new Object[1];
      arrayOfObject[0] = this.mCloudUsername;
    }
    for (String str1 = String.format(str2, arrayOfObject);; str1 = getString(2131296373))
    {
      localMenuItem.setSummary(str1);
      return;
    }
  }
  
  private void setEnableCloudStatus(int paramInt)
  {
    MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
    if (this.mResources.getBoolean(2131492889)) {}
    for (int i = 0;; i = 8)
    {
      localMenuItem.setVisibility(i);
      return;
    }
  }
  
  private void setFlowStatus(int paramInt)
  {
    MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
    if (this.mResources.getBoolean(2131492935)) {}
    for (int i = 0;; i = 8)
    {
      localMenuItem.setVisibility(i);
      if (!this.mResources.getBoolean(2131492889)) {
        localMenuItem.setPosition(4);
      }
      return;
    }
  }
  
  private void setRestoredDisplay(int paramInt1, int paramInt2, int paramInt3)
  {
    ((MenuItem)findViewById(paramInt1)).setSummary(this.mResources.getString(2131296370));
    MenuItem localMenuItem1 = (MenuItem)findViewById(paramInt2);
    localMenuItem1.setCompleted(true);
    localMenuItem1.setupInternalState();
    localMenuItem1.setSummary(this.mResources.getString(2131296370));
    MenuItem localMenuItem2 = (MenuItem)findViewById(paramInt3);
    localMenuItem2.setCompleted(true);
    localMenuItem2.setupInternalState();
    localMenuItem2.setSummary(this.mResources.getString(2131296370));
  }
  
  private void setSkEnabledStatus(int paramInt)
  {
    MenuItem localMenuItem = (MenuItem)findViewById(paramInt);
    if ((TouchTypeUtilities.isTouchTypeEnabled(this)) && (this.mFrame != 1))
    {
      localMenuItem.setCompleted(true);
      localMenuItem.setupInternalState();
      localMenuItem.setDefaultColors();
      String str1 = this.mResources.getString(2131296400);
      Object[] arrayOfObject1 = new Object[1];
      arrayOfObject1[0] = this.mProductName;
      localMenuItem.setTitle(String.format(str1, arrayOfObject1));
      String str2 = this.mResources.getString(2131296364);
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = this.mProductName;
      localMenuItem.setSummary(String.format(str2, arrayOfObject2));
    }
  }
  
  private void setState()
  {
    if ((this.mExtras != null) && (this.mExtras.containsKey("com.touchtype.FromKeyboardInstallNotComplete")) && (this.mExtras.getBoolean("com.touchtype.FromKeyboardInstallNotComplete")))
    {
      Toast.makeText(this, getApplicationContext().getResources().getString(2131297302), 1).show();
      if ((this.mLanguagePackManager != null) && (this.mLanguagePackManager.getEnabledLanguagePacks() != null) && (this.mLanguagePackManager.getEnabledLanguagePacks().size() > 0)) {
        break label141;
      }
      this.mFrame = 0;
    }
    for (;;)
    {
      getIntent().removeExtra("com.touchtype.FromKeyboardInstallNotComplete");
      saveState();
      if (this.mFrame <= 0) {
        break;
      }
      this.mViewAnimator.setAnimation(null);
      for (int i = 0; i < this.mFrame; i++) {
        this.mViewAnimator.showNext();
      }
      label141:
      if (TouchTypeUtilities.checkIMEStatus(this)) {
        this.mFrame = 3;
      } else if (TouchTypeUtilities.isTouchTypeEnabled(this)) {
        this.mFrame = 2;
      } else {
        this.mFrame = 1;
      }
    }
    setCustomFrameProperties();
  }
  
  private void showRestoreDialog()
  {
    new AlertDialog.Builder(getApplicationContext()).setIcon(getResources().getDrawable(2130837990)).setTitle(getString(2131296424)).setMessage(getString(2131296425)).setPositiveButton(getString(2131297201), null).create().show();
  }
  
  private void stopUpdatingAndReschedule()
  {
    if (this.rtu == null) {
      return;
    }
    this.rtu.cancelAndReschedule();
  }
  
  private void wrapupInstaller()
  {
    this.mInstallerPrefs.setInstallerCompleted();
    ((NotificationManager)getApplicationContext().getSystemService("notification")).cancel(2131297302);
    enableLanguagePack(this.mResources.getBoolean(2131492891));
  }
  
  private void wrapupRestore()
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    this.mInstallerPrefs.setInstallerFlowEnabled(localTouchTypePreferences.isFlowEnabled());
    this.mInstallerPrefs.setRestoreCompleted();
  }
  
  protected void cancelLanguageDownload()
  {
    this.mDownloadingCancelled = true;
    setDownloadingInProgress();
    Toast.makeText(this, 2131296426, 1).show();
  }
  
  protected void checkEula()
  {
    if (!this.mEulaAccepted)
    {
      this.mInstallerPrefs.setInstallerProgressStat(-2);
      Intent localIntent = new Intent();
      localIntent.setClass(this, EulaLicenceView.class);
      startActivityForResult(localIntent, 4);
    }
  }
  
  protected boolean checkTrialUpgrade()
  {
    if (ProductConfiguration.buildAvailablePackageList(getApplicationContext()).size() > 0)
    {
      Launcher.launchUpgradeTrial(this);
      return true;
    }
    return false;
  }
  
  public void chooseLanguage(View paramView)
  {
    if ((this.mLanguagePackManager != null) && (this.mLanguagePackManager.isReady()) && (this.mLanguagePackManager.getLanguagePacks().size() > 0) && (!this.mDownloadingLanguagePackInProgress))
    {
      this.mDownloadingCancelled = false;
      LanguagePack localLanguagePack = getLanguagePackToAutoInstall();
      if (localLanguagePack != null) {
        downloadLanguage(localLanguagePack);
      }
    }
    for (;;)
    {
      this.mInstallerPrefs.setInstallerStepChooseLang("selected");
      return;
      wrapShowDialog(1);
      continue;
      if (this.mDownloadingLanguagePackInProgress) {
        wrapShowDialog(4);
      }
    }
  }
  
  protected void downloadLanguage(LanguagePack paramLanguagePack)
  {
    Context localContext = getApplicationContext();
    this.mSelectedLanguage = paramLanguagePack;
    this.mLanguageID = paramLanguagePack.getID();
    if (this.mLanguageProgressListener == null) {
      this.mLanguageProgressListener = new DownloadProgressListener(this);
    }
    try
    {
      this.mDownloadingLanguagePackInProgress = true;
      this.mActiveView.setSummary(this.mResources.getString(2131296387));
      paramLanguagePack.setInstallerContext(getPackageName());
      paramLanguagePack.download(this.mLanguageProgressListener);
      this.mInstallerPrefs.setInstallerStepChooseLang("chosen " + this.mLanguageID + " " + NetworkUtil.getInternetAvailability(localContext));
      return;
    }
    catch (Exception localException)
    {
      for (;;)
      {
        LogUtil.e(TAG, "Got exception starting language download " + localException.getMessage(), localException);
        this.mDownloadingLanguagePackInProgress = false;
      }
    }
  }
  
  public void enable(View paramView)
  {
    wrapShowDialog(5);
  }
  
  public void enableCloud(View paramView)
  {
    Intent localIntent = new Intent(this, CloudSetupActivity.class);
    localIntent.putExtra("CloudSetupActivity.FromInstaller", true);
    startActivityForResult(localIntent, 2);
  }
  
  public void enableFlow(View paramView)
  {
    Intent localIntent = new Intent("com.touchtype.FLOW_NOTIFICATION_FROM_INSTALLER");
    localIntent.setClassName(getPackageName(), getResources().getString(2131296497));
    startActivityForResult(localIntent, 6);
  }
  
  void enableLanguagePack(boolean paramBoolean)
  {
    if ((this.mLanguagePackManager != null) && (this.mLanguageID != null))
    {
      if (paramBoolean) {
        this.mFluencyServiceProxy.getPredictor().addListener(this);
      }
      synchronized (this.mLanguagePackManager.getLanguagePacks())
      {
        Iterator localIterator = ???.iterator();
        LanguagePack localLanguagePack;
        boolean bool;
        do
        {
          if (!localIterator.hasNext()) {
            break;
          }
          localLanguagePack = (LanguagePack)localIterator.next();
          bool = localLanguagePack.getID().equals(this.mLanguageID);
        } while (!bool);
        try
        {
          this.mLanguagePackManager.enableLanguage(localLanguagePack, true);
          TouchTypePreferences.getInstance(getApplicationContext()).setKeyboardLayout(localLanguagePack.getDefaultLayout());
          return;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            LogUtil.w(TAG, "Got exception from LanguagePack: " + localException.getMessage(), localException);
          }
        }
      }
    }
  }
  
  public void enableSwiftkey()
  {
    startActivityForResult(TouchTypeUtilities.getIMSettingsIntent(), 1);
  }
  
  public void exit(View paramView)
  {
    setResult(-1);
    finish();
  }
  
  protected void exitWithError()
  {
    setResult(0);
    finish();
  }
  
  public int getCurrentFrame()
  {
    return this.mFrame;
  }
  
  protected void nextFrame()
  {
    int i;
    if ((this.mFrame == 2) && (!this.mInstallerPrefs.isRestoreComplete(getApplicationContext()))) {
      i = 2;
    }
    while (i < 5)
    {
      this.mViewAnimator.showNext();
      this.mFrame = (1 + this.mFrame);
      i++;
      continue;
      this.mViewAnimator.showNext();
      this.mFrame = (1 + this.mFrame);
    }
    saveState();
    setCustomFrameProperties();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (new InstallerActivityResult(this, this.mInstallerPrefs).activityResult(paramInt1, paramInt2, paramIntent)) {
      nextFrame();
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903125);
    Context localContext = getApplicationContext();
    this.mFluencyServiceProxy.onCreate(localContext);
    this.mInstallerPrefs = InstallerPreferences.newInstance(localContext);
    this.mViewAnimator = ((ViewAnimator)findViewById(2131230946));
    this.mActiveView = ((MenuItem)findViewById(2131230947));
    this.mProductName = getString(2131296303);
    this.mUserNotificationManager = UserNotificationManager.getInstance(localContext);
    this.mExtras = getIntent().getExtras();
    this.mViewAnimator.requestFocus();
    this.mMaxFrame = (-1 + this.mViewAnimator.getChildCount());
    this.mFrame = getInitialFrame();
    this.mResources = getResources();
    InstallerPreferences localInstallerPreferences = this.mInstallerPrefs;
    if (!getResources().getBoolean(2131492934)) {}
    for (boolean bool1 = true;; bool1 = false)
    {
      this.mEulaAccepted = localInstallerPreferences.getBoolean("installer_eula_accepted", bool1);
      if (paramBundle != null) {
        restoreState(paramBundle);
      }
      checkForSDCard();
      if ((this.mFrame < 0) || (this.mFrame > this.mMaxFrame))
      {
        LogUtil.w(TAG, "stored frame out of range - defaulting to 0");
        boolean bool2 = ProductConfiguration.hasBundledLanguagePacks(localContext);
        int i = 0;
        if (bool2) {
          i = 1;
        }
        this.mFrame = i;
      }
      setState();
      checkEula();
      this.mInstallerPrefs.edit().putLong("installation_time", new Date().getTime()).commit();
      this.mInstallerPrefs.edit().putBoolean("cloud_notification_shown", true).commit();
      this.mFluencyServiceProxy.runWhenConnected(new Runnable()
      {
        public void run()
        {
          XInstaller.this.mLanguagePackManager = XInstaller.this.mFluencyServiceProxy.getLanguagePackManager();
          XInstaller.this.mLanguagePackWatcherTimedTask = new LanguagePackDetector(XInstaller.this);
          XInstaller.this.mLanguagePackWatcher.post(XInstaller.this.mLanguagePackWatcherTimedTask);
          if (XInstaller.this.mLanguagePackManager != null) {
            XInstaller.this.mLanguagePackManager.addListener(XInstaller.this.mConfigurationListener);
          }
        }
      });
      return;
    }
  }
  
  public void onDestroy()
  {
    if (this.mDownloadingLanguagePackInProgress) {
      this.mSelectedLanguage.cancelDownload();
    }
    if (this.mLanguagePackWatcher != null)
    {
      this.mLanguagePackWatcher.removeCallbacksAndMessages(null);
      this.mLanguagePackWatcher = null;
    }
    if (this.mHandler != null)
    {
      this.mHandler.removeCallbacksAndMessages(null);
      this.mHandler = null;
    }
    if (isFinishing())
    {
      sendInstallerStatsReport();
      stopUpdatingAndReschedule();
    }
    this.mFluencyServiceProxy.onDestroy(getApplicationContext());
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = false;
    if (paramInt == 4)
    {
      if (this.mFrame <= 0) {
        break label66;
      }
      if (this.mDownloadingLanguagePackInProgress) {
        break label58;
      }
      if (this.mFrame >= this.mMaxFrame) {
        break label42;
      }
      wrapShowDialog(8);
      bool = true;
    }
    label42:
    label58:
    label66:
    int i;
    do
    {
      return bool;
      saveState();
      setResult(0);
      finish();
      break;
      wrapShowDialog(4);
      break;
      i = this.mFrame;
      bool = false;
    } while (i != 0);
    this.mFrame = -1;
    saveState();
    setResult(0);
    finish();
    return false;
  }
  
  public void onPause()
  {
    super.onPause();
    this.mBackgrounded = true;
  }
  
  public void onPredictorError()
  {
    this.mFluencyServiceProxy.getPredictor().removeListener(this);
  }
  
  public void onPredictorReady()
  {
    if (this.mResources.getBoolean(2131492900)) {
      launchLocalParser(ServiceConfiguration.SMS);
    }
    if (getResources().getBoolean(2131492904)) {
      launchLocalParser(ServiceConfiguration.CONTACTS);
    }
    this.mFluencyServiceProxy.getPredictor().removeListener(this);
  }
  
  public void onResume()
  {
    super.onResume();
    if (this.mHandler == null) {
      this.mHandler = new Handler();
    }
    this.mUserNotificationManager.clearNotification(2131297085);
    this.mBackgrounded = false;
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    if ((this.mSelectedLanguage != null) && (this.mLanguageProgressListener != null))
    {
      if (this.mSelectedLanguage.hasListener(this.mLanguageProgressListener)) {
        this.mSelectedLanguage.removeListener(this.mLanguageProgressListener);
      }
      this.mLanguageProgressListener = null;
    }
    if ((this.mLanguagePackManager != null) && (this.mConfigurationListener != null)) {
      this.mLanguagePackManager.removeListener(this.mConfigurationListener);
    }
    Bundle localBundle = new Bundle();
    localBundle.putString("mLanguageID", this.mLanguageID);
    localBundle.putString("mCloudUsername", this.mCloudUsername);
    localBundle.putBoolean("mShowMoreLanguagesToast", this.mShowMoreLanguagesToast);
    paramBundle.putBundle("installer_state", localBundle);
  }
  
  public void personalization(View paramView)
  {
    Launcher.launchPersonalizationSettings(getApplicationContext());
    this.mInstallerPrefs.setInstallerCompleted();
    setResult(-1);
    finish();
  }
  
  protected void resetLanguageDownloadCounter()
  {
    this.mLanguagePackRetryCount = 0;
    this.mLanguagePackWatcherTimedTask = new LanguagePackDetector(this);
    if (this.mLanguagePackWatcher == null) {
      this.mLanguagePackWatcher = new Handler();
    }
    this.mLanguagePackWatcher.post(this.mLanguagePackWatcherTimedTask);
  }
  
  public void select(View paramView)
  {
    TouchTypeUtilities.openIMESelector(this);
    this.mTimedTask = new IMEDetector(this);
    this.mHandler.post(this.mTimedTask);
  }
  
  public void setCloudSignedInUsername(String paramString)
  {
    this.mCloudUsername = paramString;
  }
  
  protected void setDownloadingInProgress()
  {
    MenuItem localMenuItem = (MenuItem)findViewById(2131230947);
    if (this.mDownloadingCancelled) {
      localMenuItem.setSummary(this.mResources.getString(2131296367));
    }
    for (;;)
    {
      localMenuItem.invalidate();
      return;
      if (this.mDownloadingLanguagesInProgress) {
        localMenuItem.setSummary(this.mResources.getString(2131296366));
      } else {
        localMenuItem.setSummary(this.mResources.getString(2131296365));
      }
    }
  }
  
  public void settings(View paramView)
  {
    Launcher.launchSettings(getApplicationContext());
    this.mInstallerPrefs.setInstallerCompleted();
    setResult(-1);
    finish();
  }
  
  protected void showDialogFragment(int paramInt)
  {
    if (!this.mBackgrounded)
    {
      InstallerDialogFragment localInstallerDialogFragment = InstallerDialogFragment.newInstance(this, this.mInstallerPrefs, this.mLanguagePackManager, paramInt);
      FragmentTransaction localFragmentTransaction = getSupportFragmentManager().beginTransaction();
      localFragmentTransaction.add(localInstallerDialogFragment, null);
      localFragmentTransaction.commitAllowingStateLoss();
    }
  }
  
  protected void stopLanguageDownload()
  {
    this.mDownloadingCancelled = true;
    this.mSelectedLanguage.cancelDownload();
    this.mDownloadingLanguagePackInProgress = false;
    this.mLanguageProgressListener = null;
    setDownloadingInProgress();
  }
  
  public void tutorial(View paramView)
  {
    IntentWrapper.launchIntent(this, Uri.parse("http://video.swiftkey.net"));
  }
  
  protected void wrapShowDialog(int paramInt)
  {
    if ((!this.mBackgrounded) && (this.mHandler != null)) {
      this.mHandler.postDelayed(new PendingDialog(paramInt, this), 200L);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.XInstaller
 * JD-Core Version:    0.7.0.1
 */