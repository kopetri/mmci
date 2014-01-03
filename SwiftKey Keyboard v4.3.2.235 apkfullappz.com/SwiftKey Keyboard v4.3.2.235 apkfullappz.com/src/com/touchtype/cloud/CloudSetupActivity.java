package com.touchtype.cloud;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.touchtype.cloud.ui.CloudSetupChooseAccountDialog;
import com.touchtype.cloud.ui.CloudSetupFragmentCallbacks.CloudSetupBasicDialogCallback;
import com.touchtype.cloud.ui.CloudSetupFragmentCallbacks.CloudSetupChooseGoogleAccountCallback;
import com.touchtype.cloud.ui.CloudSetupFragmentCallbacks.CloudSetupSignedInCallback;
import com.touchtype.cloud.ui.CloudSetupMessageDialog;
import com.touchtype.cloud.ui.CloudSetupSignedInDialog;
import com.touchtype.keyboard.concurrent.ThreadUtils;
import com.touchtype.preferences.SwiftKeyPreferences;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.settings.CloudPreferenceSetting.CloudPreferenceActivity;
import com.touchtype.settings.CloudPreferenceSetting.CloudPreferenceFragment;
import com.touchtype.settings.TouchTypeKeyboardSettings;
import com.touchtype.sync.client.CommonUtilities.AuthTokenType;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.ui.ProgressDialogFragment;
import com.touchtype.ui.TaskProgressDialogFragment;
import com.touchtype.ui.TaskProgressDialogFragment.TaskProgressDialogCallback;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.personalize.DynamicPersonalizerModel;
import com.touchtype_fluency.service.personalize.Personalizer;
import com.touchtype_fluency.service.personalize.Personalizer.PersonalizerAuthenticationCallback;
import com.touchtype_fluency.service.personalize.ServiceConfiguration;
import com.touchtype_fluency.service.personalize.auth.AuthenticationActivity;
import com.touchtype_fluency.service.personalize.auth.AuthenticationUtil;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

public class CloudSetupActivity
  extends FragmentActivity
{
  private CheckBox marketingOption;
  private int maxMoreInfoViewHeight = 0;
  private boolean moreInfoShowing = false;
  private boolean moreInfoViewInitialised = false;
  private SwiftKeyPreferences prefs;
  private CloudSetupState state;
  private CloudService syncService;
  private ServiceConnection syncServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      synchronized (CloudSetupActivity.this.syncServiceToken)
      {
        CloudSetupActivity.access$102(CloudSetupActivity.this, ((CloudService.LocalBinder)paramAnonymousIBinder).getService());
        CloudSetupActivity.this.syncServiceToken.notify();
        return;
      }
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName) {}
  };
  private Object syncServiceToken = new Object();
  private boolean usingLandscapeLayout = false;
  
  private void addDynamicPersonalizersForCloudAccount()
  {
    if (getResources().getBoolean(2131492891))
    {
      SharedPreferences.Editor localEditor = getSharedPreferences("DynamicPersonalizers", 0).edit();
      boolean bool = this.prefs.getBoolean("cloud_personalised_gmail", false);
      int i = 0;
      if (bool)
      {
        String str3 = new DynamicPersonalizerModel(DynamicPersonalizerModel.generateKey(ServiceConfiguration.GMAIL.getName(), this.state.accountUsername), null, null).getKey();
        localEditor.putString(DynamicPersonalizerModel.createPersonalizerKey(str3), str3);
        localEditor.putInt("dynamic_order_" + str3, 0);
        i = 0 + 1;
      }
      if (getResources().getBoolean(2131492904))
      {
        String str2 = new DynamicPersonalizerModel(DynamicPersonalizerModel.generateKey(ServiceConfiguration.CONTACTS.getName(), null), null, null).getKey();
        localEditor.putString(DynamicPersonalizerModel.createPersonalizerKey(str2), str2);
        localEditor.putInt("dynamic_order_" + str2, i);
        i++;
      }
      if (DeviceUtils.isTelephonySupported(getApplicationContext()))
      {
        String str1 = new DynamicPersonalizerModel(DynamicPersonalizerModel.generateKey(ServiceConfiguration.SMS.getName(), null), null, null).getKey();
        localEditor.putString(DynamicPersonalizerModel.createPersonalizerKey(str1), str1);
        localEditor.putInt("dynamic_order_" + str1, i);
      }
      localEditor.commit();
    }
  }
  
  public static void animateSizeChange(final View paramView, int paramInt1, int paramInt2)
  {
    final int i = paramInt2 - paramInt1;
    int j = (int)(Math.abs(i) / paramView.getContext().getResources().getDisplayMetrics().density);
    paramView.setVisibility(0);
    Animation local7 = new Animation()
    {
      protected void applyTransformation(float paramAnonymousFloat, Transformation paramAnonymousTransformation)
      {
        int i = this.val$start + (int)(paramAnonymousFloat * i);
        paramView.getLayoutParams().height = i;
        paramView.requestLayout();
        if ((paramAnonymousFloat == 1.0F) && (i == 0)) {
          paramView.setVisibility(8);
        }
      }
      
      public boolean willChangeBounds()
      {
        return true;
      }
    };
    local7.setDuration(j);
    paramView.startAnimation(local7);
  }
  
  private void beginGoogleAuth()
  {
    if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(this) == 0) {}
    String[] arrayOfString;
    for (int i = 1;; i = 0)
    {
      if (i == 0) {
        LogUtil.e("CloudSetupActivity", "GooglePlayServices NOT available: ");
      }
      arrayOfString = CloudSetupTasks.getGoogleAccounts(this);
      if ((i == 0) || (arrayOfString.length <= 0)) {
        break label68;
      }
      if (arrayOfString.length != 1) {
        break;
      }
      verifyGoogleAccount(arrayOfString[0]);
      return;
    }
    CloudSetupChooseAccountDialog.newInstance(arrayOfString).show(getSupportFragmentManager(), null);
    return;
    label68:
    showGoogleAuthWebView();
  }
  
  private Personalizer.PersonalizerAuthenticationCallback createPersonalizerAuthenticationCallback()
  {
    new Personalizer.PersonalizerAuthenticationCallback()
    {
      public void onAuthenticationFailed(String paramAnonymousString)
      {
        CloudSetupActivity.this.setupComplete();
      }
      
      public void onAuthenticationStarted(String paramAnonymousString1, String paramAnonymousString2) {}
      
      public void onAuthenticationSuccess(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3, String paramAnonymousString4)
      {
        CloudSetupActivity.this.setupComplete();
      }
    };
  }
  
  private void detachActiveProgressDialog()
  {
    ProgressDialogFragment localProgressDialogFragment = (ProgressDialogFragment)getSupportFragmentManager().findFragmentByTag(this.state.activeTaskFragmentTag);
    if (localProgressDialogFragment != null)
    {
      if (!this.state.activeTaskFragmentTag.equals("progressDialogGetGoogleAccessToken")) {
        break label47;
      }
      ((TaskProgressDialogFragment)localProgressDialogFragment).setCallback(null);
    }
    label47:
    while (!this.state.activeTaskFragmentTag.equals("progressDialogGoogleSignIn")) {
      return;
    }
    ((CloudRequestProgressDialogFragment)localProgressDialogFragment).setExternalListener(null);
  }
  
  private boolean getExistingAccountParameter(Map<String, String> paramMap)
  {
    String str = (String)paramMap.get("existingAccount");
    if (TextUtils.isEmpty(str)) {
      str = "false";
    }
    return Boolean.parseBoolean(str);
  }
  
  private TaskProgressDialogFragment.TaskProgressDialogCallback<CloudSetupTasks.GetGoogleAccessTokenResult> getGoogleAccessTokenCallback()
  {
    new TaskProgressDialogFragment.TaskProgressDialogCallback()
    {
      public void onPostExecute(CloudSetupTasks.GetGoogleAccessTokenResult paramAnonymousGetGoogleAccessTokenResult)
      {
        CloudSetupActivity.CloudSetupState.access$502(CloudSetupActivity.this.state, null);
        if ((TextUtils.isEmpty(paramAnonymousGetGoogleAccessTokenResult.token)) && (!paramAnonymousGetGoogleAccessTokenResult.shouldRetryIfTokenMissing))
        {
          Toast.makeText(CloudSetupActivity.this, 2131297266, 0).show();
          CloudSetupActivity.this.showGoogleAuthWebView();
          return;
        }
        CloudSetupActivity.this.onGoogleAccessTokenRetrieved(paramAnonymousGetGoogleAccessTokenResult.token, CommonUtilities.AuthTokenType.ID_TOKEN);
      }
    };
  }
  
  private RequestListener getGoogleSignInListener()
  {
    new RequestListener()
    {
      public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
      {
        CloudSetupActivity.this.handleFailedAuthentication(paramAnonymousSyncError);
      }
      
      public void onSuccess(Map<String, String> paramAnonymousMap)
      {
        CloudSetupActivity.CloudSetupState.access$802(CloudSetupActivity.this.state, (String)paramAnonymousMap.get("email"));
        CloudSetupActivity.this.onAccountAuthenticated(CloudSetupActivity.access$1300(CloudSetupActivity.this, paramAnonymousMap));
      }
    };
  }
  
  private int getSignedInDialogStyle(boolean paramBoolean1, boolean paramBoolean2)
  {
    if ((!paramBoolean1) && (!paramBoolean2)) {
      return 2;
    }
    if (paramBoolean1) {
      return 1;
    }
    return 0;
  }
  
  private CloudSetupState getStateFromBundle(Bundle paramBundle)
  {
    CloudSetupState localCloudSetupState = new CloudSetupState(null);
    CloudSetupState.access$302(localCloudSetupState, paramBundle.getBoolean("fromInstaller"));
    CloudSetupState.access$402(localCloudSetupState, paramBundle.getBoolean("fromUpdate"));
    CloudSetupState.access$902(localCloudSetupState, paramBundle.getBoolean("webviewAuth"));
    CloudSetupState.access$802(localCloudSetupState, paramBundle.getString("accountUsername"));
    CloudSetupState.access$502(localCloudSetupState, paramBundle.getString("activeTaskFragmentTag"));
    return localCloudSetupState;
  }
  
  private void handleFailedAuthentication(RequestListener.SyncError paramSyncError)
  {
    if (paramSyncError == RequestListener.SyncError.ERROR)
    {
      showNetworkErrorDialog();
      return;
    }
    if (paramSyncError == RequestListener.SyncError.CLIENT)
    {
      LogUtil.w("CloudSetupActivity", "Client error during authentication - resetting client sync state");
      if (waitForSyncServiceIfNecessary()) {
        this.syncService.resetCloudState();
      }
      showErrorDialog(2131297274);
      return;
    }
    showErrorDialog(2131297274);
  }
  
  private void initialiseView()
  {
    this.marketingOption = ((CheckBox)findViewById(2131230784));
    findViewById(2131230782).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CloudSetupActivity.this.beginGoogleAuth();
      }
    });
    findViewById(2131230779).setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        CloudSetupActivity.this.moreInfo((ImageView)paramAnonymousView);
      }
    });
    View localView1 = findViewById(2131230783);
    if ((this.state.fromInstaller) || (this.state.fromUpdate))
    {
      localView1.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          CloudSetupActivity.this.finish();
        }
      });
      ((TextView)findViewById(2131230785)).setMovementMethod(LinkMovementMethod.getInstance());
      ((TextView)findViewById(2131230773)).setMovementMethod(LinkMovementMethod.getInstance());
      if (findViewById(2131230774) != null) {
        break label167;
      }
    }
    label167:
    for (boolean bool = true;; bool = false)
    {
      this.usingLandscapeLayout = bool;
      return;
      localView1.setVisibility(8);
      View localView2 = findViewById(2131230791);
      if (localView2 == null) {
        break;
      }
      localView2.setVisibility(8);
      break;
    }
  }
  
  private void moreInfo(ImageView paramImageView)
  {
    if (!this.moreInfoViewInitialised) {
      setMoreInfoHeightAndColor();
    }
    View localView = findViewById(2131230781);
    if (this.moreInfoShowing)
    {
      animateSizeChange(localView, this.maxMoreInfoViewHeight, 0);
      paramImageView.setBackgroundResource(2130838232);
      this.moreInfoShowing = false;
      localView.requestLayout();
      return;
    }
    animateSizeChange(localView, 0, this.maxMoreInfoViewHeight);
    if (this.usingLandscapeLayout) {}
    for (int i = 2130838082;; i = 2130838081)
    {
      paramImageView.setBackgroundResource(i);
      this.moreInfoShowing = true;
      break;
    }
  }
  
  private void onAccountAuthenticated(boolean paramBoolean)
  {
    if (waitForSyncServiceIfNecessary())
    {
      this.prefs.setCloudAccountIsSetup(true);
      this.prefs.setSyncEnabled(true);
      this.prefs.setSyncFrequency(2);
      this.prefs.setSyncWifiOnly(false);
      this.prefs.setCloudDeviceId(this.syncService.getAuthenticationDeviceId());
      this.prefs.setCloudAccountIdentifier(this.state.accountUsername);
      this.prefs.setLiveLanguagesEnabled(true);
      this.prefs.putBoolean("cloud_account_setup_from_update", this.state.fromUpdate);
      this.syncService.initialiseSync();
      CloudSetupSignedInDialog localCloudSetupSignedInDialog = CloudSetupSignedInDialog.newInstance(getSignedInDialogStyle(paramBoolean, this.state.webviewAuth), this.state.accountUsername, this.syncService.getDevices().size());
      localCloudSetupSignedInDialog.setCancelable(false);
      localCloudSetupSignedInDialog.setStyle(1, 2131427482);
      localCloudSetupSignedInDialog.show(getSupportFragmentManager(), null);
      return;
    }
    showErrorDialog(2131297274);
  }
  
  private void onGoogleAccessTokenRetrieved(String paramString, CommonUtilities.AuthTokenType paramAuthTokenType)
  {
    if (!TextUtils.isEmpty(paramString))
    {
      if (waitForSyncServiceIfNecessary())
      {
        CloudRequestProgressDialogFragment localCloudRequestProgressDialogFragment = CloudSetupTasks.createProgressDialogFragment(getGoogleSignInListener(), 2131297270);
        showProgressDialog(localCloudRequestProgressDialogFragment, "progressDialogGoogleSignIn");
        CheckBox localCheckBox = this.marketingOption;
        boolean bool = false;
        if (localCheckBox != null)
        {
          bool = this.marketingOption.isChecked();
          this.prefs.setCloudMarketingAllowed(bool);
        }
        this.syncService.googleAuthenticate(this.state.accountUsername, CloudUtils.getDefaultDeviceName(), paramString, paramAuthTokenType, bool, localCloudRequestProgressDialogFragment.getPersistentListener());
        return;
      }
      showErrorDialog(2131297273);
      return;
    }
    showErrorDialog(2131297273);
  }
  
  private void removeSetupFlag()
  {
    this.prefs.putBoolean("during_cloud_account_setup", false);
  }
  
  private void restoreActiveProgressDialog()
  {
    ProgressDialogFragment localProgressDialogFragment = (ProgressDialogFragment)getSupportFragmentManager().findFragmentByTag(this.state.activeTaskFragmentTag);
    int i = 0;
    if (localProgressDialogFragment != null)
    {
      if (localProgressDialogFragment.wasRecreated()) {
        localProgressDialogFragment.dismiss();
      }
    }
    else
    {
      if (i == 0) {
        CloudSetupState.access$502(this.state, null);
      }
      return;
    }
    if (this.state.activeTaskFragmentTag.equals("progressDialogGetGoogleAccessToken")) {
      ((TaskProgressDialogFragment)localProgressDialogFragment).setCallback(getGoogleAccessTokenCallback());
    }
    for (;;)
    {
      i = 1;
      break;
      if (this.state.activeTaskFragmentTag.equals("progressDialogGoogleSignIn")) {
        ((CloudRequestProgressDialogFragment)localProgressDialogFragment).setExternalListener(getGoogleSignInListener());
      }
    }
  }
  
  private void runGmailPersonalization()
  {
    DynamicPersonalizerModel localDynamicPersonalizerModel = new DynamicPersonalizerModel(DynamicPersonalizerModel.generateKey(ServiceConfiguration.GMAIL.getName(), this.state.accountUsername), null, null);
    Personalizer localPersonalizer = new Personalizer(getApplicationContext(), ServiceConfiguration.GMAIL, createPersonalizerAuthenticationCallback());
    localPersonalizer.setFromInstaller(this.state.fromInstaller);
    localPersonalizer.startPersonalization(this, localDynamicPersonalizerModel);
  }
  
  private void saveStateToBundle(Bundle paramBundle)
  {
    paramBundle.putBoolean("fromInstaller", this.state.fromInstaller);
    paramBundle.putBoolean("fromUpdate", this.state.fromUpdate);
    paramBundle.putBoolean("webviewAuth", this.state.webviewAuth);
    paramBundle.putString("accountUsername", this.state.accountUsername);
    paramBundle.putString("activeTaskFragmentTag", this.state.activeTaskFragmentTag);
  }
  
  private void setBackground(View paramView, Drawable paramDrawable)
  {
    if (Build.VERSION.SDK_INT >= 16)
    {
      paramView.setBackground(paramDrawable);
      return;
    }
    paramView.setBackgroundDrawable(paramDrawable);
  }
  
  private void setGradientBackground(View paramView, int[] paramArrayOfInt1, int[] paramArrayOfInt2)
  {
    paramView.getRootView().setDrawingCacheEnabled(true);
    int[] arrayOfInt = new int[2];
    arrayOfInt[0] = paramView.getRootView().getDrawingCache().getPixel(paramArrayOfInt1[0], paramArrayOfInt1[1]);
    arrayOfInt[1] = paramView.getRootView().getDrawingCache().getPixel(paramArrayOfInt2[0], paramArrayOfInt2[1]);
    paramView.getRootView().setDrawingCacheEnabled(false);
    setBackground(paramView, new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, arrayOfInt));
  }
  
  private void setMoreInfoHeightAndColor()
  {
    View localView1 = findViewById(2131230781);
    View localView2 = findViewById(2131230782);
    int[] arrayOfInt1 = new int[2];
    int[] arrayOfInt2 = new int[2];
    localView2.getLocationInWindow(arrayOfInt2);
    if (this.usingLandscapeLayout)
    {
      arrayOfInt1[0] = arrayOfInt2[0];
      arrayOfInt1[1] = 0;
    }
    for (;;)
    {
      this.maxMoreInfoViewHeight = (arrayOfInt2[1] - arrayOfInt1[1]);
      setGradientBackground(localView1, arrayOfInt1, arrayOfInt2);
      this.moreInfoViewInitialised = true;
      return;
      localView1.getLocationInWindow(arrayOfInt1);
    }
  }
  
  private void setupComplete()
  {
    if (this.state.fromInstaller)
    {
      Intent localIntent1 = new Intent();
      localIntent1.putExtra("CloudSetupActivity.SignedInUsername", this.state.accountUsername);
      setResult(-1, localIntent1);
      finish();
      return;
    }
    Intent localIntent2;
    if (Build.VERSION.SDK_INT >= 11)
    {
      localIntent2 = new Intent(this, TouchTypeKeyboardSettings.class);
      localIntent2.addFlags(67108864);
      localIntent2.putExtra(":android:show_fragment", CloudPreferenceSetting.CloudPreferenceFragment.class.getName());
      localIntent2.putExtra(getString(2131296460), getString(2131296524));
    }
    for (;;)
    {
      startActivity(localIntent2);
      break;
      localIntent2 = new Intent(this, CloudPreferenceSetting.CloudPreferenceActivity.class);
    }
  }
  
  private void showErrorDialog(int paramInt)
  {
    showMessageDialog(2131297271, paramInt);
  }
  
  private void showGoogleAuthWebView()
  {
    CloudSetupState.access$902(this.state, true);
    Intent localIntent = new Intent(this, AuthenticationActivity.class);
    localIntent.putExtra("service", ServiceConfiguration.GMAIL.getName());
    localIntent.putExtra("title", getString(2131297263));
    localIntent.putExtra("Caller Id", CloudUtils.getGoogleAuthClientId(this));
    localIntent.putExtra("Caller Callback", getString(2131296328));
    localIntent.putExtra("Caller Scopes", "https://www.googleapis.com/auth/userinfo.email");
    startActivityForResult(localIntent, 100);
  }
  
  private void showMessageDialog(int paramInt1, int paramInt2)
  {
    showMessageDialog(null, paramInt1, paramInt2, 2131297268, true);
  }
  
  private void showMessageDialog(String paramString, int paramInt1, int paramInt2, int paramInt3, boolean paramBoolean)
  {
    CloudSetupMessageDialog localCloudSetupMessageDialog = CloudSetupMessageDialog.newInstance(getString(paramInt1), getString(paramInt2), getString(paramInt3), paramBoolean);
    getSupportFragmentManager().beginTransaction().add(localCloudSetupMessageDialog, paramString).commitAllowingStateLoss();
  }
  
  private void showNetworkErrorDialog()
  {
    showErrorDialog(2131297272);
  }
  
  private void showProgressDialog(ProgressDialogFragment paramProgressDialogFragment, String paramString)
  {
    getSupportFragmentManager().beginTransaction().add(paramProgressDialogFragment, paramString).commitAllowingStateLoss();
    CloudSetupState.access$502(this.state, paramString);
  }
  
  private void verifyGoogleAccount(String paramString)
  {
    CloudSetupState.access$902(this.state, false);
    CloudSetupState.access$802(this.state, paramString);
    showProgressDialog(CloudSetupTasks.newGetGoogleAccessTokenTaskFragment(getApplicationContext(), paramString, getGoogleAccessTokenCallback()), "progressDialogGetGoogleAccessToken");
  }
  
  private boolean waitForSyncServiceIfNecessary()
  {
    if (!ThreadUtils.onMainThread()) {}
    for (;;)
    {
      synchronized (this.syncServiceToken)
      {
        CloudService localCloudService = this.syncService;
        if (localCloudService == null) {}
        try
        {
          this.syncServiceToken.wait(10000L);
          if (this.syncService == null) {
            break;
          }
          return true;
        }
        catch (InterruptedException localInterruptedException)
        {
          LogUtil.e("CloudSetupActivity", "Interrupted whilst waiting for syncService to bind");
          continue;
        }
      }
      LogUtil.w("CloudSetupActivity", "waitForSyncServiceIfNecessary called from main thread");
    }
    return false;
  }
  
  public CloudSetupFragmentCallbacks.CloudSetupSignedInCallback createSignedInDialogCallback()
  {
    new CloudSetupFragmentCallbacks.CloudSetupSignedInCallback()
    {
      public void onPressedNo()
      {
        CloudSetupActivity.this.prefs.putBoolean("cloud_personalised_gmail", false);
        CloudSetupActivity.this.addDynamicPersonalizersForCloudAccount();
        CloudSetupActivity.this.setupComplete();
      }
      
      public void onPressedOk()
      {
        CloudSetupActivity.this.addDynamicPersonalizersForCloudAccount();
        CloudSetupActivity.this.setupComplete();
      }
      
      public void onPressedYes()
      {
        CloudSetupActivity.this.prefs.putBoolean("cloud_personalised_gmail", true);
        CloudSetupActivity.this.addDynamicPersonalizersForCloudAccount();
        CloudSetupActivity.this.runGmailPersonalization();
      }
    };
  }
  
  public CloudSetupFragmentCallbacks.CloudSetupChooseGoogleAccountCallback getChooseAccountCallback()
  {
    new CloudSetupFragmentCallbacks.CloudSetupChooseGoogleAccountCallback()
    {
      public void onChoseAccount(String paramAnonymousString)
      {
        CloudSetupActivity.this.verifyGoogleAccount(paramAnonymousString);
      }
    };
  }
  
  public CloudSetupFragmentCallbacks.CloudSetupBasicDialogCallback getMessageDialogCallback()
  {
    new CloudSetupFragmentCallbacks.CloudSetupBasicDialogCallback() {};
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    if (paramInt1 == 100)
    {
      if (paramIntent != null) {}
      try
      {
        String str2 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(paramIntent.getStringExtra("params"), "access_token"), "UTF-8");
        str1 = str2;
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
        for (;;)
        {
          Log.e("CloudSetupActivity", "Unable to decode the token returned by the Google sign in webview");
          String str1 = null;
        }
      }
      onGoogleAccessTokenRetrieved(str1, CommonUtilities.AuthTokenType.ACCESS_TOKEN);
    }
    while (paramInt1 != 1001) {
      return;
    }
    if (paramInt2 == -1) {
      runGmailPersonalization();
    }
    setupComplete();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    int i = 1;
    super.onCreate(paramBundle);
    bindService(new Intent(this, CloudService.class), this.syncServiceConnection, i);
    requestWindowFeature(i);
    setContentView(2130903047);
    this.prefs = TouchTypePreferences.getInstance(getApplicationContext());
    this.prefs.putBoolean("cloud_notification_shown", i);
    if (paramBundle == null)
    {
      this.state = new CloudSetupState(null);
      Bundle localBundle = getIntent().getExtras();
      CloudSetupState localCloudSetupState1 = this.state;
      if ((localBundle != null) && (localBundle.getBoolean("CloudSetupActivity.FromInstaller", false)))
      {
        int k = i;
        CloudSetupState.access$302(localCloudSetupState1, k);
        CloudSetupState localCloudSetupState2 = this.state;
        if ((localBundle == null) || (!localBundle.getBoolean("CloudSetupActivity.FromUpdate", false))) {
          break label167;
        }
        label149:
        CloudSetupState.access$402(localCloudSetupState2, i);
      }
    }
    for (;;)
    {
      initialiseView();
      return;
      int m = 0;
      break;
      label167:
      int j = 0;
      break label149;
      this.state = getStateFromBundle(paramBundle);
      if (this.state.activeTaskFragmentTag != null) {
        restoreActiveProgressDialog();
      }
    }
  }
  
  protected void onDestroy()
  {
    unbindService(this.syncServiceConnection);
    if (this.state.activeTaskFragmentTag != null) {
      detachActiveProgressDialog();
    }
    super.onDestroy();
  }
  
  protected void onSaveInstanceState(Bundle paramBundle)
  {
    super.onSaveInstanceState(paramBundle);
    saveStateToBundle(paramBundle);
  }
  
  protected void onStart()
  {
    super.onStart();
    if (this.state.fromInstaller) {
      this.prefs.putBoolean("during_cloud_account_setup", true);
    }
  }
  
  protected void onStop()
  {
    super.onStop();
    removeSetupFlag();
  }
  
  private final class CloudSetupState
  {
    private String accountUsername = null;
    private String activeTaskFragmentTag = null;
    private boolean fromInstaller = false;
    private boolean fromUpdate = false;
    private boolean webviewAuth = false;
    
    private CloudSetupState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudSetupActivity
 * JD-Core Version:    0.7.0.1
 */