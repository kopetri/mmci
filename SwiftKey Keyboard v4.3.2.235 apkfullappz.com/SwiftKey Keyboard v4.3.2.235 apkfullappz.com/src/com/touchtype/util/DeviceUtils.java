package com.touchtype.util;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.google.common.base.Strings;
import java.io.File;
import java.io.FileFilter;
import java.util.regex.Pattern;

public final class DeviceUtils
{
  @TargetApi(9)
  private static String buildSerial()
  {
    if (Build.VERSION.SDK_INT >= 9) {
      return Build.SERIAL + "-";
    }
    return "";
  }
  
  public static String getDeviceId(Context paramContext)
  {
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    StringBuilder localStringBuilder = new StringBuilder();
    localStringBuilder.append(buildSerial());
    String str = localTelephonyManager.getDeviceId();
    if (!Strings.isNullOrEmpty(str))
    {
      localStringBuilder.append(str);
      localStringBuilder.append("-");
    }
    localStringBuilder.append(Settings.Secure.getString(paramContext.getContentResolver(), "android_id"));
    return localStringBuilder.toString();
  }
  
  public static DisplayMetrics getDisplayMetrics(Context paramContext)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    ((WindowManager)paramContext.getSystemService("window")).getDefaultDisplay().getMetrics(localDisplayMetrics);
    return localDisplayMetrics;
  }
  
  public static int getNumCores()
  {
    File[] arrayOfFile = new File("/sys/devices/system/cpu/").listFiles(new FileFilter()
    {
      public boolean accept(File paramAnonymousFile)
      {
        return Pattern.matches("cpu[0-9]+", paramAnonymousFile.getName());
      }
    });
    if (arrayOfFile == null) {
      return Runtime.getRuntime().availableProcessors();
    }
    return arrayOfFile.length;
  }
  
  public static int getStatusBarHeightPx(Context paramContext)
  {
    int i = paramContext.getResources().getIdentifier("status_bar_height", "dimen", "android");
    int j = 0;
    if (i > 0) {
      j = paramContext.getResources().getDimensionPixelSize(i);
    }
    return j;
  }
  
  public static boolean isDeviceInLandscape(Context paramContext)
  {
    return paramContext.getResources().getConfiguration().orientation == 2;
  }
  
  public static boolean isDeviceTablet(Context paramContext)
  {
    Configuration localConfiguration = paramContext.getResources().getConfiguration();
    return ((0xF & localConfiguration.screenLayout) == 3) || ((0xF & localConfiguration.screenLayout) == 4);
  }
  
  public static boolean isScreenLocked(Context paramContext)
  {
    return ((KeyguardManager)paramContext.getSystemService("keyguard")).inKeyguardRestrictedInputMode();
  }
  
  public static boolean isTelephonySupported(Context paramContext)
  {
    return paramContext.getPackageManager().hasSystemFeature("android.hardware.telephony");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.DeviceUtils
 * JD-Core Version:    0.7.0.1
 */