package com.touchtype.settings;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.facebook.Session;
import com.google.common.base.Preconditions;
import com.touchtype.settings.dialogs.DynamicPersonalizerPreference;
import com.touchtype.settings.dialogs.PersonalizerPreference;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.PreferencesUtil;
import com.touchtype_fluency.service.personalize.DynamicPersonalizerModel;
import com.touchtype_fluency.service.personalize.Personalizer;
import com.touchtype_fluency.service.personalize.Personalizer.PersonalizerAuthenticationCallback;
import com.touchtype_fluency.service.personalize.PersonalizerService;
import com.touchtype_fluency.service.personalize.PersonalizerService.LocalBinder;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import junit.framework.Assert;

public class RecentPersonalizersPreferenceConfiguration
  extends BaseCloudPreferenceConfiguration
{
  private static final String TAG = RecentPersonalizersPreferenceConfiguration.class.getSimpleName();
  private PersonalizerServiceConnection mConnection = new PersonalizerServiceConnection(null);
  private List<DynamicPersonalizerPreference> mDynamicPreferences = Collections.synchronizedList(new ArrayList());
  private boolean mFromInstaller = false;
  private List<PersonalizerPreference> mMorePreferences;
  private PersonalizerService mPersonalizerService;
  private PreferenceActivity mPreferenceActivity;
  private PreferenceCategory mRecentSourcesCategory;
  private SharedPreferences mSharedPreferences;
  private PreferenceCategory mSourcesCategory;
  private ArrayList<String> mSourcesFilter = new ArrayList();
  private PersonalizerStatusWatcher mTimedTask;
  private UserNotificationManager mUserNotificationManager;
  
  public RecentPersonalizersPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
  }
  
  public RecentPersonalizersPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
  }
  
  private void bindToServices()
  {
    Intent localIntent = new Intent(this.mPreferenceActivity, PersonalizerService.class);
    this.mPreferenceActivity.bindService(localIntent, this.mConnection, 1);
    super.setup(this.mPreferenceActivity);
  }
  
  private void createNewDynamicPreference(String paramString1, String paramString2, String paramString3)
  {
    if (this.mRecentSourcesCategory == null)
    {
      this.mRecentSourcesCategory = new PreferenceCategory(getContext());
      this.mRecentSourcesCategory.setTitle(getString(2131297163));
      this.mRecentSourcesCategory.setKey(getString(2131297183));
      this.mRecentSourcesCategory.setOrder(0);
      getPreferenceScreen().addPreference(this.mRecentSourcesCategory);
      this.mSourcesCategory.setTitle(2131297161);
    }
    AuthenticationCallback localAuthenticationCallback = new AuthenticationCallback(null);
    DynamicPersonalizerModel localDynamicPersonalizerModel = new DynamicPersonalizerModel(paramString1, paramString2, paramString3);
    DynamicPersonalizerPreference localDynamicPersonalizerPreference = new DynamicPersonalizerPreference(getContext(), localDynamicPersonalizerModel);
    localDynamicPersonalizerPreference.setFromInstaller(this.mFromInstaller);
    localDynamicPersonalizerPreference.registerCallback(localAuthenticationCallback);
    this.mRecentSourcesCategory.addPreference(localDynamicPersonalizerPreference);
    this.mDynamicPreferences.add(localDynamicPersonalizerPreference);
    ServiceConfiguration localServiceConfiguration = localDynamicPersonalizerModel.getService();
    if (localServiceConfiguration.isUnique()) {
      this.mSourcesFilter.add(localServiceConfiguration.getName());
    }
  }
  
  private DynamicPersonalizerPreference getCallerDynamicPreference(int paramInt1, int paramInt2, String paramString)
  {
    Iterator localIterator = this.mDynamicPreferences.iterator();
    while (localIterator.hasNext())
    {
      DynamicPersonalizerPreference localDynamicPersonalizerPreference = (DynamicPersonalizerPreference)localIterator.next();
      String str = localDynamicPersonalizerPreference.getDynamicPersonalizer().getAccountName();
      if ((str != null) && (str.equals(paramString)) && (isCallerService(localDynamicPersonalizerPreference, paramInt1, paramInt2))) {
        return localDynamicPersonalizerPreference;
      }
    }
    return null;
  }
  
  private PersonalizerPreference getCallerPreference(int paramInt1, int paramInt2, String paramString)
  {
    if (paramString != null) {}
    for (DynamicPersonalizerPreference localDynamicPersonalizerPreference = getCallerDynamicPreference(paramInt1, paramInt2, paramString); localDynamicPersonalizerPreference != null; localDynamicPersonalizerPreference = null) {
      return localDynamicPersonalizerPreference;
    }
    return getCallerStaticPreference(paramInt1, paramInt2);
  }
  
  private PersonalizerPreference getCallerStaticPreference(int paramInt1, int paramInt2)
  {
    synchronized (this.mMorePreferences)
    {
      Iterator localIterator = this.mMorePreferences.iterator();
      while (localIterator.hasNext())
      {
        PersonalizerPreference localPersonalizerPreference = (PersonalizerPreference)localIterator.next();
        if (isCallerService(localPersonalizerPreference, paramInt1, paramInt2)) {
          return localPersonalizerPreference;
        }
      }
      return null;
    }
  }
  
  private DynamicPersonalizerPreference getDynamicPreference(String paramString1, String paramString2)
  {
    String str = DynamicPersonalizerModel.generateKey(paramString1, paramString2);
    Iterator localIterator = this.mDynamicPreferences.iterator();
    while (localIterator.hasNext())
    {
      DynamicPersonalizerPreference localDynamicPersonalizerPreference = (DynamicPersonalizerPreference)localIterator.next();
      if (localDynamicPersonalizerPreference.getDynamicPersonalizer().getKey().equals(str)) {
        return localDynamicPersonalizerPreference;
      }
    }
    return null;
  }
  
  private List<PersonalizerPreference> getStaticPersonalizerPreferences()
  {
    List localList = Collections.synchronizedList(new ArrayList());
    if (this.mSourcesCategory != null) {
      for (int i = 0; i < this.mSourcesCategory.getPreferenceCount(); i++) {
        if ((this.mSourcesCategory.getPreference(i) instanceof PersonalizerPreference)) {
          localList.add((PersonalizerPreference)this.mSourcesCategory.getPreference(i));
        }
      }
    }
    return localList;
  }
  
  private boolean isCallerService(PersonalizerPreference paramPersonalizerPreference, int paramInt1, int paramInt2)
  {
    return (paramPersonalizerPreference.getPersonalizer() != null) && ((paramPersonalizerPreference.getPersonalizer().getService().ordinal() == paramInt1) || ((paramPersonalizerPreference.getPersonalizer().getService().equals(ServiceConfiguration.FACEBOOK)) && (paramInt1 == 64206)) || ((paramPersonalizerPreference.getPersonalizer().getService().equals(ServiceConfiguration.GMAIL)) && (paramInt1 == 1001)));
  }
  
  private boolean isKnownDynamicPreference(String paramString1, String paramString2)
  {
    return getDynamicPreference(paramString1, paramString2) != null;
  }
  
  private void loadDynamicPersonalizers()
  {
    TreeMap localTreeMap = new TreeMap();
    AuthenticationCallback localAuthenticationCallback = new AuthenticationCallback(null);
    Iterator localIterator1 = this.mSharedPreferences.getAll().keySet().iterator();
    while (localIterator1.hasNext())
    {
      String str1 = (String)localIterator1.next();
      if (DynamicPersonalizerModel.isDynamicPersonalizerKey(str1))
      {
        String str2 = this.mSharedPreferences.getString(str1, null);
        String str3 = this.mSharedPreferences.getString(DynamicPersonalizerModel.createAuthParamsKey(str2), null);
        String str4 = this.mSharedPreferences.getString(DynamicPersonalizerModel.createSessionKey(str2), null);
        int i = this.mSharedPreferences.getInt("dynamic_order_" + str2, 0);
        DynamicPersonalizerModel localDynamicPersonalizerModel = new DynamicPersonalizerModel(str2, str3, str4);
        DynamicPersonalizerPreference localDynamicPersonalizerPreference2 = new DynamicPersonalizerPreference(getContext(), localDynamicPersonalizerModel);
        localDynamicPersonalizerPreference2.setFromInstaller(this.mFromInstaller);
        localDynamicPersonalizerPreference2.registerCallback(localAuthenticationCallback);
        localTreeMap.put(Integer.valueOf(i), localDynamicPersonalizerPreference2);
        ServiceConfiguration localServiceConfiguration = localDynamicPersonalizerModel.getService();
        if (localServiceConfiguration.isUnique()) {
          this.mSourcesFilter.add(localServiceConfiguration.getName());
        }
      }
    }
    Preconditions.checkNotNull(this.mRecentSourcesCategory);
    Iterator localIterator2 = localTreeMap.keySet().iterator();
    while (localIterator2.hasNext())
    {
      DynamicPersonalizerPreference localDynamicPersonalizerPreference1 = (DynamicPersonalizerPreference)localTreeMap.get((Integer)localIterator2.next());
      this.mRecentSourcesCategory.addPreference(localDynamicPersonalizerPreference1);
      this.mDynamicPreferences.add(localDynamicPersonalizerPreference1);
    }
    if (this.mDynamicPreferences.isEmpty())
    {
      removePreference(this.mRecentSourcesCategory);
      this.mRecentSourcesCategory = null;
      this.mSourcesCategory.setTitle(2131297162);
    }
  }
  
  private void onActivityRequestReceived(int paramInt1, int paramInt2, Intent paramIntent)
  {
    String str1 = null;
    PersonalizerPreference localPersonalizerPreference;
    String str2;
    if (paramIntent != null)
    {
      if (paramInt1 == 1001) {
        str1 = paramIntent.getStringExtra("authAccount");
      }
    }
    else
    {
      localPersonalizerPreference = getCallerPreference(paramInt1, paramInt2, str1);
      if (localPersonalizerPreference != null)
      {
        localPersonalizerPreference.getPersonalizer().setActive(false);
        str2 = localPersonalizerPreference.getPersonalizer().getService().getName();
      }
    }
    switch (paramInt2)
    {
    case 1: 
    default: 
    case -1: 
    case 0: 
      DynamicPersonalizerPreference localDynamicPersonalizerPreference2;
      do
      {
        return;
        str1 = paramIntent.getStringExtra("account_name");
        break;
        if (paramInt1 == 1001)
        {
          onNewPreferenceReceived(str2, str1, null, null);
          getDynamicPreference(str2, str1).launchPersonalizer();
        }
        for (;;)
        {
          storeDynamicPersonalizers();
          return;
          String str3 = paramIntent.getStringExtra("params");
          onNewPreferenceReceived(str2, str1, str3, paramIntent.getStringExtra("session"));
          localPersonalizerPreference.getPersonalizer().startPersonalizationRequest(str3, DynamicPersonalizerModel.generateKey(str2, str1));
        }
        localDynamicPersonalizerPreference2 = getCallerDynamicPreference(paramInt1, paramInt2, str1);
      } while (localDynamicPersonalizerPreference2 == null);
      this.mPersonalizerService.restorePersonalizerState(localDynamicPersonalizerPreference2.getDynamicPersonalizer().getKey());
      return;
    }
    DynamicPersonalizerPreference localDynamicPersonalizerPreference1 = getCallerDynamicPreference(paramInt1, paramInt2, str1);
    if (localDynamicPersonalizerPreference1 != null) {
      this.mPersonalizerService.restorePersonalizerState(localDynamicPersonalizerPreference1.getDynamicPersonalizer().getKey());
    }
    showAuthenticationFailedToast(str2);
  }
  
  private void onNewPreferenceReceived(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    if (getDynamicPreference(paramString1, paramString2) != null)
    {
      updateDynamicPreference(paramString1, paramString2, paramString3, paramString4);
      return;
    }
    createNewDynamicPreference(DynamicPersonalizerModel.generateKey(paramString1, paramString2), paramString3, paramString4);
    storeDynamicPersonalizers();
  }
  
  private void setPreferenceState()
  {
    DynamicPersonalizerPreference localDynamicPersonalizerPreference;
    boolean bool;
    long l;
    int i;
    label95:
    String str2;
    PreferenceActivity localPreferenceActivity;
    for (;;)
    {
      try
      {
        Iterator localIterator = this.mDynamicPreferences.iterator();
        if (!localIterator.hasNext()) {
          break label332;
        }
        localDynamicPersonalizerPreference = (DynamicPersonalizerPreference)localIterator.next();
        bool = true;
        if (!this.mConnection.isConnected()) {
          break;
        }
        l = 0L;
        DynamicPersonalizerModel localDynamicPersonalizerModel = localDynamicPersonalizerPreference.getDynamicPersonalizer();
        i = 0;
        if (localDynamicPersonalizerModel == null) {
          break label335;
        }
        String str1 = localDynamicPersonalizerPreference.getDynamicPersonalizer().getKey();
        i = this.mPersonalizerService.getPersonalizerState(str1);
        l = this.mPersonalizerService.getPersonalizerLastRun(str1);
      }
      finally {}
      localDynamicPersonalizerPreference.update(str2);
      localDynamicPersonalizerPreference.setEnabled(bool);
      continue;
      str2 = this.mPreferenceActivity.getResources().getString(2131297132);
      continue;
      str2 = this.mPreferenceActivity.getResources().getString(2131297164);
      continue;
      str2 = this.mPreferenceActivity.getResources().getString(2131297165);
      continue;
      str2 = PreferencesUtil.buildSummary(l, this.mPreferenceActivity.getText(2131297169).toString(), this.mPreferenceActivity.getResources().getString(2131297166));
      continue;
      localPreferenceActivity = this.mPreferenceActivity;
      if (!localDynamicPersonalizerPreference.getPersonalizer().getService().equals(ServiceConfiguration.CONTACTS)) {
        break label384;
      }
    }
    label384:
    for (int j = 2131297168;; j = 2131297167)
    {
      str2 = PreferencesUtil.buildSummary(l, localPreferenceActivity.getText(j).toString(), this.mPreferenceActivity.getResources().getString(2131297166));
      break label95;
      str2 = PreferencesUtil.buildSummary(l, this.mPreferenceActivity.getText(2131297170).toString(), this.mPreferenceActivity.getResources().getString(2131297171));
      break label95;
      str2 = this.mPreferenceActivity.getResources().getString(2131297172);
      bool = false;
      break label95;
      localDynamicPersonalizerPreference.update("");
      break;
      label332:
      return;
      label335:
      str2 = null;
      switch (i)
      {
      }
    }
  }
  
  private void showAuthenticationFailedToast(String paramString)
  {
    if (shouldUpdateUiFromBackground())
    {
      String str = String.format(this.mPreferenceActivity.getResources().getString(2131297116), new Object[] { paramString });
      Toast.makeText(getApplicationContext(), str, 1).show();
    }
  }
  
  private void showUseWifiToast(Bundle paramBundle)
  {
    if ((paramBundle != null) && (paramBundle.containsKey("com.touchtype.personalizer.error")) && (paramBundle.getBoolean("com.touchtype.personalizer.error")))
    {
      String str = this.mPreferenceActivity.getString(2131297192);
      Toast.makeText(this.mPreferenceActivity, str, 1).show();
    }
  }
  
  private void storeDynamicPersonalizers()
  {
    SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
    Iterator localIterator = this.mDynamicPreferences.iterator();
    while (localIterator.hasNext())
    {
      DynamicPersonalizerPreference localDynamicPersonalizerPreference = (DynamicPersonalizerPreference)localIterator.next();
      DynamicPersonalizerModel localDynamicPersonalizerModel = localDynamicPersonalizerPreference.getDynamicPersonalizer();
      String str1 = localDynamicPersonalizerModel.getKey();
      String str2 = DynamicPersonalizerModel.createPersonalizerKey(str1);
      String str3 = DynamicPersonalizerModel.createAuthParamsKey(str1);
      String str4 = DynamicPersonalizerModel.createSessionKey(str1);
      localEditor.putString(str2, str1);
      localEditor.putString(str3, localDynamicPersonalizerModel.getAuthParams());
      localEditor.putString(str4, localDynamicPersonalizerModel.getSession());
      localEditor.putInt("dynamic_order_" + str1, this.mDynamicPreferences.indexOf(localDynamicPersonalizerPreference));
    }
    localEditor.commit();
  }
  
  private void unbindFromServices()
  {
    if (this.mConnection.isConnected()) {
      this.mPreferenceActivity.unbindService(this.mConnection);
    }
    super.cleanup();
  }
  
  private void updateDynamicPreference(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DynamicPersonalizerPreference localDynamicPersonalizerPreference = getDynamicPreference(paramString1, paramString2);
    Assert.assertNotNull(localDynamicPersonalizerPreference);
    localDynamicPersonalizerPreference.getDynamicPersonalizer().update(paramString3, paramString4);
  }
  
  private void updatePersonaliserModel(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    DynamicPersonalizerModel localDynamicPersonalizerModel = getDynamicPreference(paramString1, paramString2).getDynamicPersonalizer();
    Assert.assertNotNull(localDynamicPersonalizerModel);
    localDynamicPersonalizerModel.update(paramString3, paramString4);
  }
  
  public void onActivityResult(Activity paramActivity, int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 64206) {}
    for (;;)
    {
      try
      {
        Session localSession = Session.getActiveSession();
        if (localSession != null) {
          localSession.onActivityResult(paramActivity, paramInt1, paramInt2, paramIntent);
        }
        return;
      }
      finally {}
      onActivityRequestReceived(paramInt1, paramInt2, paramIntent);
    }
  }
  
  public void onNewIntent(Intent paramIntent)
  {
    showUseWifiToast(paramIntent.getExtras());
  }
  
  protected void onServiceBound() {}
  
  public void onStart()
  {
    bindToServices();
    this.mTimedTask = new PersonalizerStatusWatcher(null);
    this.mTimedTask.start();
    this.mUserNotificationManager.clearNotification(2131297114);
  }
  
  public void onStop()
  {
    this.mTimedTask.stop();
    unbindFromServices();
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    this.mRecentSourcesCategory = ((PreferenceCategory)findPreference(getString(2131297183)));
    this.mSourcesCategory = ((PreferenceCategory)findPreference(getString(2131297182)));
    this.mPreferenceActivity = paramPreferenceActivity;
    this.mUserNotificationManager = UserNotificationManager.getInstance(getContext());
    this.mSharedPreferences = this.mPreferenceActivity.getSharedPreferences("DynamicPersonalizers", 0);
    Intent localIntent = this.mPreferenceActivity.getIntent();
    String str = localIntent.getAction();
    if ((str != null) && (str.equals("com.touchtype_fluency.service.PERSONALIZE_ACTIVITY_FROM_INSTALLER"))) {
      this.mFromInstaller = true;
    }
    loadDynamicPersonalizers();
    showUseWifiToast(localIntent.getExtras());
    Assert.assertNotNull("PreferenceCategory unexpectedly doesn't contain the sources", this.mSourcesCategory);
    ArrayList localArrayList = this.mSourcesFilter;
    for (ServiceConfiguration localServiceConfiguration : ServiceConfiguration.values()) {
      if (localArrayList.contains(localServiceConfiguration.getName()))
      {
        Preference localPreference2 = findPreference(localServiceConfiguration.getPreferenceKey());
        if (localPreference2 != null) {
          this.mSourcesCategory.removePreference(localPreference2);
        }
      }
    }
    if (!DeviceUtils.isTelephonySupported(this.mPreferenceActivity.getApplicationContext()))
    {
      Preference localPreference1 = findPreference(ServiceConfiguration.SMS.getPreferenceKey());
      if (localPreference1 != null) {
        this.mSourcesCategory.removePreference(localPreference1);
      }
    }
    this.mMorePreferences = getStaticPersonalizerPreferences();
    AuthenticationCallback localAuthenticationCallback = new AuthenticationCallback(null);
    Iterator localIterator = this.mMorePreferences.iterator();
    while (localIterator.hasNext())
    {
      PersonalizerPreference localPersonalizerPreference = (PersonalizerPreference)localIterator.next();
      if (this.mFromInstaller) {
        localPersonalizerPreference.setFromInstaller(true);
      }
      localPersonalizerPreference.setEnabled(true);
      localPersonalizerPreference.registerCallback(localAuthenticationCallback);
    }
  }
  
  private final class AuthenticationCallback
    implements Personalizer.PersonalizerAuthenticationCallback
  {
    private AuthenticationCallback() {}
    
    public void onAuthenticationFailed(String paramString)
    {
      if (RecentPersonalizersPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) {
        RecentPersonalizersPreferenceConfiguration.this.showAuthenticationFailedToast(paramString);
      }
    }
    
    public void onAuthenticationStarted(String paramString1, String paramString2)
    {
      if ((RecentPersonalizersPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) && (RecentPersonalizersPreferenceConfiguration.this.isKnownDynamicPreference(paramString1, paramString2))) {
        RecentPersonalizersPreferenceConfiguration.this.mPersonalizerService.setPersonalizerState(RecentPersonalizersPreferenceConfiguration.this.getDynamicPreference(paramString1, paramString2).getDynamicPersonalizer().getKey(), 5, false);
      }
    }
    
    public void onAuthenticationSuccess(String paramString1, String paramString2, String paramString3, String paramString4)
    {
      Preference localPreference;
      if ((RecentPersonalizersPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) && (RecentPersonalizersPreferenceConfiguration.this.getContext() != null)) {
        if (!RecentPersonalizersPreferenceConfiguration.this.isKnownDynamicPreference(paramString1, paramString2))
        {
          String str = DynamicPersonalizerModel.generateKey(paramString1, paramString2);
          RecentPersonalizersPreferenceConfiguration.this.createNewDynamicPreference(str, paramString3, paramString4);
          if (ServiceConfiguration.getServicesByName(paramString1).isUnique())
          {
            Assert.assertNotNull("PreferenceCategory unexpectedly doesn't contain the sources", RecentPersonalizersPreferenceConfiguration.this.mSourcesCategory);
            localPreference = RecentPersonalizersPreferenceConfiguration.this.findPreference(ServiceConfiguration.getServicesByName(paramString1).getPreferenceKey());
            if (localPreference == null) {}
          }
        }
      }
      synchronized (RecentPersonalizersPreferenceConfiguration.this.mMorePreferences)
      {
        RecentPersonalizersPreferenceConfiguration.this.mMorePreferences.remove(localPreference);
        RecentPersonalizersPreferenceConfiguration.this.mSourcesCategory.removePreference(localPreference);
        RecentPersonalizersPreferenceConfiguration.this.storeDynamicPersonalizers();
        RecentPersonalizersPreferenceConfiguration.this.updatePersonaliserModel(paramString1, paramString2, paramString3, paramString4);
        return;
      }
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
      RecentPersonalizersPreferenceConfiguration.access$102(RecentPersonalizersPreferenceConfiguration.this, localLocalBinder.getService());
      RecentPersonalizersPreferenceConfiguration.this.setPreferenceState();
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName)
    {
      this.mConnected = false;
    }
  }
  
  private final class PersonalizerStatusWatcher
    implements Runnable
  {
    private Handler mWatcherHandler;
    
    private PersonalizerStatusWatcher() {}
    
    public void run()
    {
      if (RecentPersonalizersPreferenceConfiguration.this.mConnection.isConnected()) {
        RecentPersonalizersPreferenceConfiguration.this.setPreferenceState();
      }
      this.mWatcherHandler.postDelayed(this, 500L);
    }
    
    public void start()
    {
      this.mWatcherHandler = new Handler();
      this.mWatcherHandler.post(this);
    }
    
    public void stop()
    {
      this.mWatcherHandler.removeCallbacks(this);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.RecentPersonalizersPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */