package com.touchtype;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build.VERSION;
import android.widget.Toast;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.referral.ReferralSource;
import com.touchtype.referral.ReferrerInfoInserter;
import com.touchtype.referral.ReferrerInfoInserterFactory;
import com.touchtype.resources.ApplicationResources;
import com.touchtype.settings.CloudPreferenceSetting.CloudPreferenceFragment;
import com.touchtype.settings.RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceActivity;
import com.touchtype.settings.RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceFragment;
import com.touchtype.settings.TouchTypeKeyboardSettings;
import com.touchtype.util.LogUtil;

public class Launcher
{
  private static final String TAG = Launcher.class.getSimpleName();
  
  public static Uri getMarketUri(Context paramContext, String paramString, ReferralSource paramReferralSource)
  {
    Uri localUri1 = Uri.parse(String.format(paramContext.getResources().getString(2131296871), new Object[] { paramString }));
    Uri localUri2 = referrerInfoInserter(paramContext).insertInfoIntoUri(localUri1, paramReferralSource);
    LogUtil.w(TAG, "Final market uri: " + localUri2.toString());
    return localUri2;
  }
  
  private static Intent getNewInstallerExtrasIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramContext, "com.touchtype.InstallerExtras");
    localIntent.setFlags(0x10000000 | localIntent.getFlags());
    return localIntent;
  }
  
  private static Intent getNewInstallerIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramContext, "com.touchtype.Installer");
    localIntent.setFlags(0x10000000 | localIntent.getFlags());
    return localIntent;
  }
  
  private static Intent getNewPersonalizationSettingsIntent(Context paramContext)
  {
    Intent localIntent;
    if (TouchTypePreferences.getInstance(paramContext).isCloudAccountSetup()) {
      if (Build.VERSION.SDK_INT >= 11)
      {
        localIntent = new Intent(paramContext, TouchTypeKeyboardSettings.class);
        localIntent.putExtra(":android:show_fragment", RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceFragment.class.getName());
        localIntent.putExtra(paramContext.getString(2131296459), CloudPreferenceSetting.CloudPreferenceFragment.class.getName());
        localIntent.putExtra(paramContext.getString(2131296460), paramContext.getString(2131296543));
      }
    }
    for (;;)
    {
      localIntent.addFlags(268435456);
      return localIntent;
      localIntent = new Intent(paramContext, RecentPersonalizersPreferenceSetting.RecentPersonalizersPreferenceActivity.class);
      continue;
      localIntent = new Intent(paramContext, CloudSetupActivity.class);
    }
  }
  
  private static Intent getNewSettingsIntent(Context paramContext)
  {
    Intent localIntent = new Intent();
    localIntent.setClassName(paramContext, "com.touchtype.settings.TouchTypeKeyboardSettings");
    localIntent.setFlags(0x10000000 | localIntent.getFlags());
    return localIntent;
  }
  
  public static Uri getWebViewUri(Context paramContext, String paramString, ReferralSource paramReferralSource)
  {
    Uri localUri1 = Uri.parse(String.format(paramContext.getResources().getString(2131296872), new Object[] { paramString }));
    Uri localUri2 = referrerInfoInserter(paramContext).insertInfoIntoUri(localUri1, paramReferralSource);
    LogUtil.w(TAG, "Final webview uri: " + localUri2.toString());
    return localUri2;
  }
  
  public static void launchAndroidMarket(Activity paramActivity, ReferralSource paramReferralSource)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", getMarketUri(paramActivity, ApplicationResources.getUpgradePackageName(paramActivity), paramReferralSource));
    localIntent.setFlags(268435456);
    launchMarketActivity(paramActivity, localIntent);
  }
  
  public static void launchAndroidMarketVoiceSearch(Activity paramActivity)
  {
    Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.google.android.voicesearch"));
    localIntent.setFlags(268435456);
    launchMarketActivity(paramActivity, localIntent);
  }
  
  public static void launchInstaller(Context paramContext, int paramInt, String... paramVarArgs)
  {
    Intent localIntent = getNewInstallerIntent(paramContext);
    localIntent.setFlags(paramInt | localIntent.getFlags());
    int i = paramVarArgs.length;
    for (int j = 0; j < i; j++) {
      localIntent.putExtra(paramVarArgs[j], true);
    }
    paramContext.startActivity(localIntent);
  }
  
  public static void launchInstallerExtras(Context paramContext, String paramString)
  {
    Intent localIntent = getNewInstallerExtrasIntent(paramContext);
    localIntent.setAction(paramString);
    paramContext.startActivity(localIntent);
  }
  
  private static void launchMarketActivity(Activity paramActivity, Intent paramIntent)
  {
    try
    {
      paramActivity.startActivity(paramIntent);
      return;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      LogUtil.e(TAG, "Android Market activity not supported on this device");
      Toast.makeText(paramActivity.getApplicationContext(), 2131297081, 1).show();
    }
  }
  
  public static void launchPersonalizationSettings(Context paramContext)
  {
    paramContext.startActivity(getNewPersonalizationSettingsIntent(paramContext));
  }
  
  public static void launchSettings(Context paramContext)
  {
    paramContext.startActivity(getNewSettingsIntent(paramContext));
  }
  
  public static void launchUpgradeTrial(Activity paramActivity)
  {
    Intent localIntent = getNewInstallerExtrasIntent(paramActivity);
    localIntent.setAction("com.touchtype.installer.LAUNCH_UPGRADE");
    paramActivity.startActivityForResult(localIntent, 5);
  }
  
  private static ReferrerInfoInserter referrerInfoInserter(Context paramContext)
  {
    String str = paramContext.getResources().getString(2131296873);
    paramContext.getApplicationContext().getPackageName();
    return ReferrerInfoInserterFactory.inserter(paramContext, str, paramContext.getResources().getString(2131296874));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.Launcher
 * JD-Core Version:    0.7.0.1
 */