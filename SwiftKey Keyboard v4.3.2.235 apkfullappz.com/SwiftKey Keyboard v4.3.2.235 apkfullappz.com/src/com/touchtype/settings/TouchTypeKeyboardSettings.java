package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceActivity.Header;
import android.preference.PreferenceFragment;
import android.preference.PreferenceGroup;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.touchtype.JobScheduler;
import com.touchtype.Launcher;
import com.touchtype.TouchTypeUtilities;
import com.touchtype.UserStatsScheduledJob;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.service.receiver.SDCardListener;
import com.touchtype_fluency.service.receiver.SDCardReceiver;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;

public class TouchTypeKeyboardSettings
  extends PreferenceActivity
  implements SharedPreferences.OnSharedPreferenceChangeListener
{
  private static final String TAG = TouchTypeKeyboardSettings.class.getSimpleName();
  private static Object mCurrentFragment = null;
  private Context mContext;
  private String mCurrentScreenKey;
  private OnActivityReadyRunner mDefaultIMEChecker;
  public Handler mHandler;
  private final ArrayList<PreferenceActivity.Header> mHeaders = new ArrayList();
  private Object mInitialHeader;
  private SDCardListener mSDCardListener;
  private TouchTypePreferences mTouchTypePreferences;
  private TouchTypeStats mTouchTypeStats;
  
  private void attachListenerToPreference(String paramString1, final String paramString2)
  {
    Preference localPreference = findPreference(paramString1);
    if (localPreference != null) {
      localPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
      {
        public boolean onPreferenceClick(Preference paramAnonymousPreference)
        {
          TouchTypeKeyboardSettings.this.mTouchTypePreferences.setSettingsMenuItemsOpened(paramString2);
          return false;
        }
      });
    }
  }
  
  private boolean canViewUrl()
  {
    return !getApplicationContext().getPackageManager().queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(getApplicationContext().getResources().getString(2131297187))), 65536).isEmpty();
  }
  
  private void changeSettings()
  {
    new AlertDialog.Builder(this).setIcon(this.mContext.getResources().getDrawable(2130837990)).setTitle(this.mContext.getString(2131297062)).setMessage(this.mContext.getString(2131297063) + "\n\n" + this.mContext.getString(2131296408)).setPositiveButton(this.mContext.getString(2131297064), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        Intent localIntent = new Intent();
        localIntent.setAction("android.settings.INPUT_METHOD_SETTINGS");
        TouchTypeKeyboardSettings.this.startActivity(localIntent);
      }
    }).show();
  }
  
  private void checkInstallerHasRun()
  {
    if (PreferenceManager.getDefaultSharedPreferences(this).getInt("pref_install_state", 0) != -1)
    {
      Launcher.launchInstaller(this, 335544320, new String[] { "com.touchtype.FromKeyboardInstallNotComplete" });
      setResult(0);
      finish();
    }
  }
  
  private void configureCloudPreference()
  {
    if (this.mTouchTypePreferences.isCloudAccountSetup()) {}
    for (int i = 2131296521;; i = 2131296522)
    {
      Preference localPreference = findPreference(getString(i));
      if (localPreference != null) {
        ((PreferenceGroup)findPreference(getString(2131296461))).removePreference(localPreference);
      }
      return;
    }
  }
  
  @TargetApi(11)
  private void forwardOnActivityResultToFragment(int paramInt1, int paramInt2, Intent paramIntent)
  {
    ((PreferenceFragment)mCurrentFragment).onActivityResult(paramInt1, paramInt2, paramIntent);
  }
  
  public static Object getCurrentFragment()
  {
    return mCurrentFragment;
  }
  
  private PreferenceActivity.Header getInitialHeader(List<PreferenceActivity.Header> paramList)
  {
    if (paramList.size() > 1) {
      for (int i = 0; i < paramList.size(); i++)
      {
        PreferenceActivity.Header localHeader = (PreferenceActivity.Header)paramList.get(i);
        if ((!PreferenceHeaderAdapter.isCategoryHeader(localHeader)) && (!PreferenceHeaderAdapter.dontShowFragmentHeader(localHeader, getResources()))) {
          return localHeader;
        }
      }
    }
    return null;
  }
  
  @TargetApi(11)
  private void highlightHeader(String paramString)
  {
    if ((paramString != null) && (isMultiPane())) {}
    for (int i = 0;; i++) {
      if (i < this.mHeaders.size())
      {
        if (paramString.equals(((PreferenceActivity.Header)this.mHeaders.get(i)).fragment)) {
          getListView().setItemChecked(i, true);
        }
      }
      else {
        return;
      }
    }
  }
  
  @TargetApi(11)
  private void invalidateHeadersIfNecessary()
  {
    if (hasHeaders()) {
      invalidateHeaders();
    }
  }
  
  private void noExternalStorage()
  {
    new AlertDialog.Builder(this).setIcon(this.mContext.getResources().getDrawable(2130837990)).setTitle(this.mContext.getString(2131297048)).setMessage(this.mContext.getString(2131297053)).setNeutralButton("Dismiss", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    }).show();
  }
  
  private void setFragmentTitle(String paramString)
  {
    if (paramString != null) {
      showBreadCrumbs(paramString, paramString);
    }
  }
  
  @TargetApi(11)
  private void setParentTitle(String paramString)
  {
    if ((paramString != null) && (isMultiPane())) {}
    for (int i = 0;; i++) {
      if (i < this.mHeaders.size())
      {
        final PreferenceActivity.Header localHeader = (PreferenceActivity.Header)this.mHeaders.get(i);
        if (paramString.equals(localHeader.fragment))
        {
          CharSequence localCharSequence = localHeader.getTitle(getResources());
          final int j = i;
          setParentTitle(localCharSequence, localCharSequence, new View.OnClickListener()
          {
            public void onClick(View paramAnonymousView)
            {
              TouchTypeKeyboardSettings.this.switchToHeader(localHeader);
              TouchTypeKeyboardSettings.this.getListView().setItemChecked(j, true);
            }
          });
          getListView().setItemChecked(j, true);
        }
      }
      else
      {
        getIntent().removeExtra(getString(2131296459));
        return;
      }
    }
  }
  
  private void showDownloadPref(boolean paramBoolean)
  {
    Preference localPreference = findPreference("pref_lm_menu_key");
    if (localPreference != null)
    {
      localPreference.setEnabled(paramBoolean);
      if (paramBoolean) {
        localPreference.setSummary(this.mContext.getString(2131296759));
      }
    }
    else
    {
      return;
    }
    localPreference.setSummary(this.mContext.getString(2131296967));
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (mCurrentFragment != null) {
      if (Build.VERSION.SDK_INT < 11) {
        break label33;
      }
    }
    label33:
    for (boolean bool = true;; bool = false)
    {
      Assert.assertTrue("Fragment detected on pre-Honeycomb device", bool);
      forwardOnActivityResultToFragment(paramInt1, paramInt2, paramIntent);
      return;
    }
  }
  
  @TargetApi(11)
  public void onBuildHeaders(List<PreferenceActivity.Header> paramList)
  {
    loadHeadersFromResource(2131034787, paramList);
    this.mHeaders.clear();
    this.mHeaders.addAll(paramList);
    Resources localResources = getResources();
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    boolean bool = localTouchTypePreferences.isCloudAccountSetup();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator1 = paramList.iterator();
    if (localIterator1.hasNext())
    {
      PreferenceActivity.Header localHeader2 = (PreferenceActivity.Header)localIterator1.next();
      if ((localHeader2.fragment != null) && (localHeader2.fragment.contains("PredictionsPreferenceFragment")))
      {
        if (!localResources.getBoolean(2131492923)) {
          break label242;
        }
        if (!localTouchTypePreferences.isPredictionEnabled()) {
          break label227;
        }
        localHeader2.summary = localResources.getString(2131296486);
      }
      for (;;)
      {
        if (((localHeader2.id == 2131230756L) && (!bool)) || ((localHeader2.id == 2131230755L) && (bool))) {
          localArrayList.add(localHeader2);
        }
        Bundle localBundle = localHeader2.fragmentArguments;
        if ((canViewUrl()) || (localBundle == null) || (!localBundle.getBoolean(localResources.getString(2131296457)))) {
          break;
        }
        localArrayList.add(localHeader2);
        break;
        label227:
        localHeader2.summary = localResources.getString(2131296487);
        continue;
        label242:
        localArrayList.add(localHeader2);
      }
    }
    Iterator localIterator2 = localArrayList.iterator();
    while (localIterator2.hasNext())
    {
      PreferenceActivity.Header localHeader1 = (PreferenceActivity.Header)localIterator2.next();
      this.mHeaders.remove(localHeader1);
      paramList.remove(localHeader1);
    }
    this.mInitialHeader = getInitialHeader(paramList);
  }
  
  public void onContentChanged()
  {
    super.onContentChanged();
    this.mDefaultIMEChecker.notifyOnContentChanged();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    this.mHandler = new Handler();
    this.mDefaultIMEChecker = new OnActivityReadyRunner(new Runnable()
    {
      public void run()
      {
        if (!TouchTypeUtilities.isTouchTypeEnabled(TouchTypeKeyboardSettings.this)) {
          TouchTypeKeyboardSettings.this.changeSettings();
        }
        while ((TouchTypeUtilities.checkIMEStatus(TouchTypeKeyboardSettings.this)) || (TouchTypeKeyboardSettings.this.mContext.getResources().getBoolean(2131492924))) {
          return;
        }
        TouchTypeKeyboardSettings.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            TouchTypeUtilities.openIMESelector(TouchTypeKeyboardSettings.this);
          }
        }, 1000L);
      }
    });
    super.onCreate(paramBundle);
    this.mContext = getApplicationContext();
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(this.mContext);
    this.mTouchTypeStats = this.mTouchTypePreferences.getTouchTypeStats();
    this.mTouchTypePreferences.registerOnSharedPreferenceChangeListener(this);
    if (Build.VERSION.SDK_INT < 11)
    {
      addPreferencesFromResource(2131034799);
      attachListenerToPreference(getString(2131296484), "stats_settings_predictions_opens");
      attachListenerToPreference("pref_lm_menu_key", "stats_settings_languages_opens");
      attachListenerToPreference(getString(2131296465), "stats_settings_themes_opens");
      attachListenerToPreference(getString(2131296684), "stats_settings_keyboard_feedback_opens");
      attachListenerToPreference(getString(2131296661), "stats_settings_input_methods_opens");
      attachListenerToPreference(getString(2131296670), "stats_settings_advanced_opens");
      attachListenerToPreference(getString(2131296480), "stats_settings_stats_opens");
      attachListenerToPreference(getString(2131296701), "stats_settings_support_opens");
      attachListenerToPreference(getString(2131296704), "stats_settings_about_opens");
      attachListenerToPreference(getString(2131296521), "stats_settings_cloud_setup_opens");
      attachListenerToPreference(getString(2131296522), "stats_settings_cloud_opens");
      configureCloudPreference();
    }
    if (Build.VERSION.SDK_INT >= 11) {
      if (getTitle().equals(getResources().getString(2131296305))) {
        this.mTouchTypeStats.incrementStatistic("stats_settings_opens");
      }
    }
    for (;;)
    {
      checkInstallerHasRun();
      boolean bool = Environment.getExternalStorageState().equals("mounted");
      if ((this.mContext.getResources().getBoolean(2131492928)) && (!bool))
      {
        noExternalStorage();
        showDownloadPref(false);
      }
      if (this.mTouchTypePreferences.isSendStatsEnabled()) {
        new JobScheduler().scheduleJobDefaultInterval(new UserStatsScheduledJob(), this.mContext, false);
      }
      return;
      this.mTouchTypeStats.incrementStatistic("stats_settings_opens");
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.mSDCardListener != null)
    {
      SDCardReceiver.removeListener(this.mSDCardListener);
      this.mSDCardListener = null;
    }
    this.mDefaultIMEChecker = null;
    this.mHandler.removeCallbacksAndMessages(null);
    this.mHandler = null;
    mCurrentFragment = null;
    this.mTouchTypePreferences.unregisterOnSharedPreferenceChangeListener(this);
    this.mInitialHeader = null;
    this.mHeaders.clear();
  }
  
  @TargetApi(11)
  public PreferenceActivity.Header onGetInitialHeader()
  {
    if (this.mInitialHeader == null) {
      this.mInitialHeader = super.onGetInitialHeader();
    }
    return (PreferenceActivity.Header)this.mInitialHeader;
  }
  
  @TargetApi(11)
  public void onHeaderClick(PreferenceActivity.Header paramHeader, int paramInt)
  {
    switch (paramHeader.titleRes)
    {
    }
    for (;;)
    {
      super.onHeaderClick(paramHeader, paramInt);
      return;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_predictions_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_languages_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_themes_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_keyboard_feedback_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_input_methods_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_advanced_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_stats_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_support_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_about_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_cloud_setup_opens");
      continue;
      this.mTouchTypePreferences.setSettingsMenuItemsOpened("stats_settings_cloud_opens");
    }
  }
  
  protected void onNewIntent(Intent paramIntent)
  {
    if (mCurrentFragment != null) {
      if (Build.VERSION.SDK_INT < 11) {
        break label43;
      }
    }
    label43:
    for (boolean bool = true;; bool = false)
    {
      Assert.assertTrue("Fragment detected on pre-Honeycomb device", bool);
      if ((mCurrentFragment instanceof IntentSafePreferenceFragment)) {
        ((IntentSafePreferenceFragment)mCurrentFragment).onNewIntent(paramIntent);
      }
      return;
    }
  }
  
  protected void onRestoreInstanceState(Bundle paramBundle)
  {
    super.onRestoreInstanceState(paramBundle);
    this.mCurrentScreenKey = paramBundle.getString("touchtype:currentPreference");
    if (this.mCurrentScreenKey != null)
    {
      PreferenceScreen localPreferenceScreen = (PreferenceScreen)findPreference(this.mCurrentScreenKey);
      if ((localPreferenceScreen != null) && (localPreferenceScreen.getDialog() != null))
      {
        Drawable localDrawable = this.mContext.getResources().getDrawable(2130838264);
        localPreferenceScreen.getDialog().getWindow().setBackgroundDrawable(localDrawable);
      }
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    if (Build.VERSION.SDK_INT >= 11)
    {
      invalidateHeadersIfNecessary();
      setFragmentTitle(getIntent().getStringExtra(getString(2131296460)));
      setParentTitle(getIntent().getStringExtra(getString(2131296459)));
      highlightHeader(getIntent().getStringExtra(":android:show_fragment"));
    }
    if (this.mSDCardListener == null)
    {
      this.mSDCardListener = new SDCardListener()
      {
        public void onMediaMounted()
        {
          TouchTypeKeyboardSettings.this.showDownloadPref(true);
        }
        
        public void onMediaUnmounted()
        {
          TouchTypeKeyboardSettings.this.showDownloadPref(false);
        }
      };
      SDCardReceiver.addListener(this.mSDCardListener);
    }
    this.mDefaultIMEChecker.notifyOnResume();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    paramBundle.putString("touchtype:currentPreference", this.mCurrentScreenKey);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (Build.VERSION.SDK_INT >= 11) {
      if ((paramString.equals(getResources().getString(2131296489))) || (paramString.equals("cloud_account_setup"))) {
        invalidateHeadersIfNecessary();
      }
    }
    while (!paramString.equals("cloud_account_setup")) {
      return;
    }
    getPreferenceScreen().removeAll();
    addPreferencesFromResource(2131034799);
    configureCloudPreference();
  }
  
  public void setListAdapter(ListAdapter paramListAdapter)
  {
    if (this.mHeaders.size() == 0) {
      for (int i = 0; i < paramListAdapter.getCount(); i++) {
        this.mHeaders.add((PreferenceActivity.Header)paramListAdapter.getItem(i));
      }
    }
    super.setListAdapter(new PreferenceHeaderAdapter(this, this.mHeaders));
  }
  
  @TargetApi(11)
  public void switchToHeader(PreferenceActivity.Header paramHeader)
  {
    if (paramHeader.fragment != null) {
      super.switchToHeader(paramHeader);
    }
  }
  
  public static abstract class IntentSafePreferenceActivity
    extends PreferenceActivity
  {
    protected void onStart()
    {
      TouchTypeUtilities.removeUnsafePreferences(getPreferenceScreen());
      super.onStart();
    }
  }
  
  @TargetApi(11)
  public static class IntentSafePreferenceFragment
    extends PreferenceFragment
  {
    public void onNewIntent(Intent paramIntent) {}
    
    public void onStart()
    {
      TouchTypeUtilities.removeUnsafePreferences(getPreferenceScreen());
      super.onStart();
    }
    
    public void setCurrentFragment(Fragment paramFragment)
    {
      TouchTypeKeyboardSettings.access$402(this);
    }
  }
  
  static final class OnActivityReadyRunner
  {
    private final Object lock = new Object();
    private boolean mOnContentChangedCalled = false;
    private boolean mOnResumeCalled = false;
    private final Runnable mRunnable;
    
    public OnActivityReadyRunner(Runnable paramRunnable)
    {
      Assert.assertNotNull(paramRunnable);
      this.mRunnable = paramRunnable;
    }
    
    private void runIfReady()
    {
      synchronized (this.lock)
      {
        if ((this.mOnResumeCalled) && (this.mOnContentChangedCalled)) {
          this.mRunnable.run();
        }
        return;
      }
    }
    
    public void notifyOnContentChanged()
    {
      this.mOnContentChangedCalled = true;
      runIfReady();
    }
    
    public void notifyOnResume()
    {
      this.mOnResumeCalled = true;
      runIfReady();
    }
  }
  
  @TargetApi(11)
  public static abstract class PersistentConfigPreferenceFragment<T extends PreferenceWrapper>
    extends TouchTypeKeyboardSettings.IntentSafePreferenceFragment
  {
    public abstract T createNewConfig();
    
    public T fetchPreferenceConfig()
    {
      String str = getFragmentTag();
      PreferenceConfigFragment localPreferenceConfigFragment = (PreferenceConfigFragment)getFragmentManager().findFragmentByTag(str);
      if (localPreferenceConfigFragment == null)
      {
        localPreferenceConfigFragment = new PreferenceConfigFragment();
        localPreferenceConfigFragment.setConfig(createNewConfig());
        getFragmentManager().beginTransaction().add(localPreferenceConfigFragment, str).commit();
      }
      for (;;)
      {
        return localPreferenceConfigFragment.getConfig();
        if (localPreferenceConfigFragment.getConfig() == null)
        {
          PreferenceWrapper localPreferenceWrapper = createNewConfig();
          localPreferenceConfigFragment.setConfig(localPreferenceWrapper);
          localPreferenceWrapper.removeDialog(0);
        }
        localPreferenceConfigFragment.getConfig().setFragment(this);
      }
    }
    
    public abstract String getFragmentTag();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.TouchTypeKeyboardSettings
 * JD-Core Version:    0.7.0.1
 */