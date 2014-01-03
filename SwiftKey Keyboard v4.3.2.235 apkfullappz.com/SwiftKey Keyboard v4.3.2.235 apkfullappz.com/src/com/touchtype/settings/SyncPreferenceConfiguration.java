package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Build.VERSION;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.touchtype.JobScheduler;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudService.SyncSource;
import com.touchtype.cloud.sync.SyncScheduledJob;
import com.touchtype.cloud.sync.SyncStartedListener;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.settings.custompreferences.SyncEnabledPreference;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.sync.client.SyncListener;
import java.util.Map;

public final class SyncPreferenceConfiguration
  extends BaseCloudPreferenceConfiguration
  implements SharedPreferences.OnSharedPreferenceChangeListener, SyncStartedListener
{
  private JobScheduler jobScheduler;
  private SyncEnabledPreference syncEnabledPreference;
  private ListPreference syncFrequencyPreference;
  private RequestListener.SyncError syncPullError;
  private RequestListener.SyncError syncPushError;
  private SyncScheduledJob syncScheduledJob;
  private Preference wifiOnlyPreference;
  
  public SyncPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
  }
  
  public SyncPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
  }
  
  private SyncListener getSyncListener(final boolean paramBoolean)
  {
    this.syncPullError = null;
    this.syncPushError = null;
    new SyncListener()
    {
      public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        if (SyncPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) {
          if (paramBoolean)
          {
            if ((SyncPreferenceConfiguration.this.syncPullError != RequestListener.SyncError.THROTTLE) && (SyncPreferenceConfiguration.this.syncPushError != RequestListener.SyncError.THROTTLE) && (paramAnonymousSyncError != RequestListener.SyncError.THROTTLE)) {
              break label67;
            }
            SyncPreferenceConfiguration.this.showToast(2131296607);
          }
        }
        for (;;)
        {
          SyncPreferenceConfiguration.this.updateSyncEnabled();
          return;
          label67:
          if ((paramAnonymousSyncError == RequestListener.SyncError.CLIENT) && (paramAnonymousString.equals("WifiConstraintFailed"))) {
            SyncPreferenceConfiguration.this.showToast(2131296608);
          } else {
            SyncPreferenceConfiguration.this.showToast(2131296609);
          }
        }
      }
      
      public void onPullError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        SyncPreferenceConfiguration.access$302(SyncPreferenceConfiguration.this, paramAnonymousSyncError);
      }
      
      public void onPullSuccess(Map<String, String> paramAnonymousMap) {}
      
      public void onPushError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        SyncPreferenceConfiguration.access$402(SyncPreferenceConfiguration.this, paramAnonymousSyncError);
      }
      
      public void onPushSuccess(Map<String, String> paramAnonymousMap) {}
      
      public void onSuccess(Map<String, String> paramAnonymousMap)
      {
        if (SyncPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) {
          SyncPreferenceConfiguration.this.updateSyncEnabled();
        }
      }
    };
  }
  
  private void handleSyncEnabledChangedTo(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      getCloudService().performSync(CloudService.SyncSource.MANUAL, getSyncListener(true));
      return;
    }
    this.jobScheduler.cancelJob(this.syncScheduledJob, getContext());
  }
  
  @TargetApi(11)
  private void updateOptionsMenu()
  {
    if (Build.VERSION.SDK_INT >= 11) {
      getActivity().invalidateOptionsMenu();
    }
  }
  
  private void updateSyncEnabled()
  {
    if (shouldUpdateUiFromBackground()) {
      runOnUiThread(new Runnable()
      {
        public void run()
        {
          String str1;
          if ((SyncPreferenceConfiguration.this.isBound()) && (SyncPreferenceConfiguration.this.getContext() != null) && (SyncPreferenceConfiguration.this.syncEnabledPreference != null))
          {
            if (!SyncPreferenceConfiguration.this.syncEnabledPreference.isChecked()) {
              break label156;
            }
            if (!SyncPreferenceConfiguration.this.getCloudService().isSyncing()) {
              break label96;
            }
            SyncPreferenceConfiguration.this.syncEnabledPreference.setSyncingState(true);
            str1 = SyncPreferenceConfiguration.this.getString(2131296590);
          }
          for (;;)
          {
            SyncPreferenceConfiguration.this.syncEnabledPreference.setSummary(str1);
            SyncPreferenceConfiguration.this.updateOptionsMenu();
            return;
            label96:
            SyncPreferenceConfiguration.this.syncEnabledPreference.setSyncingState(false);
            String str2 = SyncPreferenceConfiguration.this.getString(2131296591);
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = HumanReadableRelativeDate.sinceLastEvent(SyncPreferenceConfiguration.this.getContext(), SyncPreferenceConfiguration.this.getCloudService().getLastSyncTime(), 2131296593);
            str1 = String.format(str2, arrayOfObject);
            continue;
            label156:
            str1 = SyncPreferenceConfiguration.this.getString(2131296592);
          }
        }
      });
    }
  }
  
  private void updateSyncEnabledDependentPreferences()
  {
    this.syncFrequencyPreference.setEnabled(this.syncEnabledPreference.isChecked());
    this.wifiOnlyPreference.setEnabled(this.syncEnabledPreference.isChecked());
  }
  
  public void cleanup()
  {
    super.cleanup();
    TouchTypePreferences.getInstance(getContext()).unregisterOnSharedPreferenceChangeListener(this);
  }
  
  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    getActivity().getMenuInflater().inflate(2131755010, paramMenu);
    return true;
  }
  
  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    boolean bool = true;
    switch (paramMenuItem.getItemId())
    {
    default: 
      bool = false;
    }
    do
    {
      return bool;
    } while (!isBound());
    if (!getCloudService().isSyncing()) {
      getCloudService().performSync(CloudService.SyncSource.MANUAL, getSyncListener(bool));
    }
    for (;;)
    {
      updateOptionsMenu();
      return bool;
      showToast(2131296606);
    }
  }
  
  public boolean onPrepareOptionsMenu(Menu paramMenu)
  {
    MenuItem localMenuItem = paramMenu.findItem(2131231059);
    if ((isBound()) && (this.syncEnabledPreference.isChecked()) && (!getCloudService().isSyncing())) {}
    for (boolean bool = true;; bool = false)
    {
      localMenuItem.setEnabled(bool);
      return true;
    }
  }
  
  public void onResume()
  {
    super.onResume();
    if ((isBound()) && (getCloudService().isSyncing())) {
      getCloudService().addSyncListener(getSyncListener(false));
    }
    updateSyncEnabled();
  }
  
  protected void onServiceBound()
  {
    if (getCloudService().isSyncing()) {
      getCloudService().addSyncListener(getSyncListener(false));
    }
    getCloudService().setSyncListener(this);
    updateSyncEnabled();
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (paramString.equals("pref_sync_frequency_key"))
    {
      this.syncFrequencyPreference.setSummary(this.syncFrequencyPreference.getEntry());
      i = Integer.parseInt(this.syncFrequencyPreference.getValue());
      if ((i == 0) && (!getCloudService().getNotificationsEnabled())) {
        getCloudService().enableNotifications();
      }
    }
    label72:
    label118:
    while (!paramString.equals("pref_sync_enabled_key"))
    {
      int i;
      break label118;
      int j;
      if (i == 0)
      {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext()) != 0) {
          break label143;
        }
        j = 1;
        if (j == 0) {
          break label149;
        }
        this.jobScheduler.cancelJob(this.syncScheduledJob, getContext());
      }
      for (;;)
      {
        if ((i == 1) || (i == 2)) {
          this.jobScheduler.scheduleJobDefaultInterval(this.syncScheduledJob, getContext(), true);
        }
        return;
        if ((i == 0) || (!getCloudService().getNotificationsEnabled())) {
          break;
        }
        getCloudService().disableNotifications();
        break;
        j = 0;
        break label72;
        this.jobScheduler.scheduleJobDefaultInterval(this.syncScheduledJob, getContext(), true);
      }
    }
    label143:
    label149:
    handleSyncEnabledChangedTo(this.syncEnabledPreference.isChecked());
    updateSyncEnabled();
    updateSyncEnabledDependentPreferences();
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    super.setup(paramPreferenceActivity);
    TouchTypePreferences.getInstance(getContext()).registerOnSharedPreferenceChangeListener(this);
    this.jobScheduler = new JobScheduler();
    this.syncScheduledJob = new SyncScheduledJob();
    this.syncEnabledPreference = ((SyncEnabledPreference)findPreference(getString(2131296581)));
    this.wifiOnlyPreference = findPreference(getString(2131296585));
    this.syncFrequencyPreference = ((ListPreference)findPreference(getString(2131296583)));
    this.syncFrequencyPreference.setSummary(this.syncFrequencyPreference.getEntry());
    updateSyncEnabledDependentPreferences();
  }
  
  public void syncStarted(CloudService.SyncSource paramSyncSource)
  {
    if ((paramSyncSource != CloudService.SyncSource.MANUAL) && (isBound())) {
      getCloudService().addSyncListener(getSyncListener(false));
    }
    updateSyncEnabled();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.SyncPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */