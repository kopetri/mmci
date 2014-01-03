package com.touchtype.settings;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Handler;
import android.os.IBinder;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.widget.Toast;
import com.touchtype.broadcast.NotificationRegistrar;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.settings.dialogs.DeleteDynamicLanguageModelDialog;
import com.touchtype.util.PreferencesUtil;
import com.touchtype_fluency.service.personalize.PersonalizerService;
import com.touchtype_fluency.service.personalize.PersonalizerService.LocalBinder;
import java.util.List;
import junit.framework.Assert;

public class AdvancedPreferenceConfiguration
  extends PreferenceWrapper
{
  private static final String TAG = AdvancedPreferenceConfiguration.class.getSimpleName();
  private DeleteDynamicLanguageModelDialog mClearLanguageDataPreference;
  private PersonalizerServiceConnection mConnection = new PersonalizerServiceConnection(null);
  private PreferenceActivity mPreferenceActivity;
  private PersonalizerService mService;
  private Runnable mTimedTask;
  private Handler mWatcherHandler;
  private final Resources resources = getContext().getResources();
  
  public AdvancedPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    addPreference(2131034789);
  }
  
  public AdvancedPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    addPreference(2131034789);
  }
  
  private void bindToService()
  {
    Intent localIntent = new Intent(this.mPreferenceActivity, PersonalizerService.class);
    this.mPreferenceActivity.bindService(localIntent, this.mConnection, 1);
  }
  
  private void removeTipsAndAchievementSettingOrSetSharedPref()
  {
    CheckBoxPreference localCheckBoxPreference = (CheckBoxPreference)findPreference(this.resources.getString(2131296740));
    if (localCheckBoxPreference == null) {
      return;
    }
    if (!this.resources.getBoolean(2131492915))
    {
      removePreference(localCheckBoxPreference);
      return;
    }
    localCheckBoxPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(AdvancedPreferenceConfiguration.this.getApplicationContext());
        boolean bool1 = localTouchTypePreferences.getBoolean(AdvancedPreferenceConfiguration.this.resources.getString(2131296740), AdvancedPreferenceConfiguration.this.resources.getBoolean(2131492883));
        if (!bool1) {}
        for (boolean bool2 = true;; bool2 = false)
        {
          localTouchTypePreferences.putBoolean("do_not_bother_me", bool2);
          NotificationRegistrar.receivePublicityMessages(AdvancedPreferenceConfiguration.this.getContext(), bool1);
          return true;
        }
      }
    });
  }
  
  private void unbindFromService()
  {
    if (this.mConnection.isConnected()) {
      this.mPreferenceActivity.unbindService(this.mConnection);
    }
  }
  
  private void updateClearLanguageDataPreference()
  {
    if ((this.mConnection.isConnected()) && (this.mService.isPredictorReady()) && (this.mClearLanguageDataPreference != null) && (!this.mClearLanguageDataPreference.isEnabled())) {
      this.mClearLanguageDataPreference.setEnabled(true);
    }
    if (this.mConnection.isConnected())
    {
      long l = this.mService.getClearLanguageDataLastRun();
      this.mClearLanguageDataPreference.setSummary(PreferencesUtil.buildSummary(l, this.mPreferenceActivity.getText(2131297178).toString(), this.mPreferenceActivity.getResources().getString(2131297184)));
    }
  }
  
  public void clearUserModel()
  {
    boolean bool1 = this.mConnection.isConnected();
    boolean bool2 = false;
    if (bool1) {
      bool2 = this.mService.clearLanguageData();
    }
    if (bool2) {}
    for (String str = this.resources.getString(2131297179);; str = this.resources.getString(2131297180))
    {
      Toast.makeText(getApplicationContext(), str, 1).show();
      return;
    }
  }
  
  public void onStart()
  {
    bindToService();
    this.mWatcherHandler = new Handler();
    this.mTimedTask = new ClearLanguageStatusWatcher(null);
    this.mWatcherHandler.post(this.mTimedTask);
  }
  
  public void onStop()
  {
    this.mWatcherHandler.removeCallbacks(this.mTimedTask);
    unbindFromService();
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    Assert.assertNotNull("Has the advanced preference screen been removed?", getPreferenceScreen());
    this.mPreferenceActivity = paramPreferenceActivity;
    this.mClearLanguageDataPreference = ((DeleteDynamicLanguageModelDialog)findPreference(this.mPreferenceActivity.getResources().getString(2131297110)));
    this.mClearLanguageDataPreference.setConfiguration(this);
    removeTipsAndAchievementSettingOrSetSharedPref();
  }
  
  public void update()
  {
    Context localContext = getApplicationContext();
    if (ProductConfiguration.buildAvailablePackageList(localContext).size() <= 0)
    {
      Assert.assertNotNull("Has the advanced preference screen been removed?", getPreferenceScreen());
      PreferenceCategory localPreferenceCategory = (PreferenceCategory)findPreference(localContext.getResources().getString(2131296673));
      if (localPreferenceCategory != null)
      {
        Preference localPreference = findPreference(this.resources.getString(2131296680));
        if (localPreference != null) {
          localPreferenceCategory.removePreference(localPreference);
        }
      }
    }
  }
  
  private final class ClearLanguageStatusWatcher
    implements Runnable
  {
    private ClearLanguageStatusWatcher() {}
    
    public void run()
    {
      if (AdvancedPreferenceConfiguration.this.mConnection.isConnected()) {
        AdvancedPreferenceConfiguration.this.updateClearLanguageDataPreference();
      }
      AdvancedPreferenceConfiguration.this.mWatcherHandler.postDelayed(AdvancedPreferenceConfiguration.this.mTimedTask, 500L);
    }
  }
  
  private final class PersonalizerServiceConnection
    implements ServiceConnection
  {
    private volatile boolean mConnected = false;
    
    private PersonalizerServiceConnection() {}
    
    public boolean isConnected()
    {
      try
      {
        boolean bool = this.mConnected;
        return bool;
      }
      finally
      {
        localObject = finally;
        throw localObject;
      }
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      this.mConnected = true;
      PersonalizerService.LocalBinder localLocalBinder = (PersonalizerService.LocalBinder)paramIBinder;
      AdvancedPreferenceConfiguration.access$302(AdvancedPreferenceConfiguration.this, localLocalBinder.getService());
      AdvancedPreferenceConfiguration.this.updateClearLanguageDataPreference();
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.mConnected = false;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.AdvancedPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */