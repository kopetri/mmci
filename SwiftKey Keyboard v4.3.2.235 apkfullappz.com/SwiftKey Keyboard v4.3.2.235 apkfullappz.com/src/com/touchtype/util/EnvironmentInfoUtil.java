package com.touchtype.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import java.util.Locale;

public final class EnvironmentInfoUtil
{
  public static final int MANUFACTURER_HTC_HASHCODE = "HTC".hashCode();
  private static final String TAG = EnvironmentInfoUtil.class.getSimpleName();
  
  public static String getAndroidVersion()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Build.VERSION.RELEASE;
    return String.format("Android version: %s", arrayOfObject);
  }
  
  public static String getApplicationInfo(Context paramContext)
  {
    Object[] arrayOfObject = new Object[10];
    arrayOfObject[0] = getCountry(paramContext);
    arrayOfObject[1] = getBrandInfo();
    arrayOfObject[2] = getModelInfo();
    arrayOfObject[3] = getDeviceInfo();
    arrayOfObject[4] = getVersionInfo(paramContext);
    arrayOfObject[5] = getLocale(paramContext);
    arrayOfObject[6] = getAndroidVersion();
    arrayOfObject[7] = getSupportTAG(paramContext);
    arrayOfObject[8] = getPackageName(paramContext);
    arrayOfObject[9] = getScreenSize(paramContext);
    return String.format("%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n%s\n", arrayOfObject);
  }
  
  public static String getBrandInfo()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Build.BRAND;
    return String.format("Brand: %s", arrayOfObject);
  }
  
  public static String getCountry(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localTelephonyManager.getNetworkCountryIso();
    return String.format("Country: %s", arrayOfObject);
  }
  
  public static String getDeviceInfo()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Build.DEVICE;
    return String.format("Device: %s", arrayOfObject);
  }
  
  public static String getLocale(Context paramContext)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getResources().getConfiguration().locale.getDisplayName();
    return String.format("Locale: %s", arrayOfObject);
  }
  
  public static String getManufacturer()
  {
    return Build.MANUFACTURER;
  }
  
  public static String getModelInfo()
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = Build.MODEL;
    return String.format("Model: %s", arrayOfObject);
  }
  
  public static PackageInfo getPackageInfo(Context paramContext)
  {
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      return localPackageInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      LogUtil.e(TAG, "Failed to locate package information.", localNameNotFoundException);
    }
    return null;
  }
  
  public static String getPackageName(Context paramContext)
  {
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramContext.getPackageName();
    return String.format("Package name: %s", arrayOfObject);
  }
  
  public static String getScreenSize(Context paramContext)
  {
    int i = 0xF & paramContext.getResources().getConfiguration().screenLayout;
    String str = "unknown";
    switch (i)
    {
    }
    for (;;)
    {
      return String.format("Screen size: %s", new Object[] { str });
      str = "small";
      continue;
      str = "normal";
      continue;
      str = "large";
      continue;
      str = "x-large";
    }
  }
  
  public static String getSupportTAG(Context paramContext)
  {
    String str = InstallationId.getId(paramContext);
    if (str == null) {
      return "";
    }
    return String.format("Support TAG: %s", new Object[] { str });
  }
  
  public static String getVersionInfo(Context paramContext)
  {
    PackageInfo localPackageInfo = getPackageInfo(paramContext);
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = localPackageInfo.versionName;
    arrayOfObject[1] = Integer.valueOf(localPackageInfo.versionCode);
    return String.format("Version: %s (release %s)", arrayOfObject);
  }
  
  public static boolean shouldSupportSSL()
  {
    return Build.VERSION.SDK_INT > 10;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.EnvironmentInfoUtil
 * JD-Core Version:    0.7.0.1
 */