package com.touchtype.report.json;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.google.gson.annotations.SerializedName;
import com.touchtype.util.DeviceUtils;
import java.util.Locale;

final class DeviceInfo
{
  @SerializedName("arch")
  private String mArch;
  @SerializedName("cpus")
  private int mCpus;
  @SerializedName("hardKeyboard")
  private String mHardKeyboard;
  @SerializedName("language")
  private String mLanguage;
  @SerializedName("locale")
  private String mLocale;
  @SerializedName("manufacturer")
  private String mManufacturer;
  @SerializedName("model")
  private String mModel;
  @SerializedName("os")
  private OperatingSystem mOperatingSystem;
  @SerializedName("operator")
  private Operator mOperator;
  @SerializedName("screen")
  private Screen mScreen;
  
  static String getHardKeyboardType(Context paramContext)
  {
    switch (paramContext.getResources().getConfiguration().keyboard)
    {
    default: 
      return "none";
    case 2: 
      return "qwerty";
    }
    return "12key";
  }
  
  public static DeviceInfo newInstance(Context paramContext)
  {
    DeviceInfo localDeviceInfo = new DeviceInfo();
    localDeviceInfo.mArch = Build.CPU_ABI;
    localDeviceInfo.mCpus = DeviceUtils.getNumCores();
    localDeviceInfo.mModel = Build.MODEL;
    localDeviceInfo.mManufacturer = Build.MANUFACTURER;
    localDeviceInfo.mLocale = Locale.getDefault().toString();
    localDeviceInfo.mLanguage = Locale.getDefault().getLanguage();
    localDeviceInfo.mHardKeyboard = getHardKeyboardType(paramContext);
    localDeviceInfo.mOperatingSystem = OperatingSystem.newInstance();
    localDeviceInfo.mScreen = Screen.newInstance(DeviceUtils.getDisplayMetrics(paramContext));
    TelephonyManager localTelephonyManager = (TelephonyManager)paramContext.getSystemService("phone");
    if (localTelephonyManager == null) {}
    for (Operator localOperator = null;; localOperator = Operator.newInstance(localTelephonyManager))
    {
      localDeviceInfo.mOperator = localOperator;
      return localDeviceInfo;
    }
  }
  
  static final class OperatingSystem
  {
    @SerializedName("name")
    private String mName;
    @SerializedName("version")
    private String mVersion;
    
    public static OperatingSystem newInstance()
    {
      OperatingSystem localOperatingSystem = new OperatingSystem();
      localOperatingSystem.mName = "Android";
      localOperatingSystem.mVersion = Build.VERSION.RELEASE;
      return localOperatingSystem;
    }
  }
  
  static final class Operator
  {
    @SerializedName("country")
    private String mCountry;
    @SerializedName("name")
    private String mName;
    
    public static Operator newInstance(TelephonyManager paramTelephonyManager)
    {
      Operator localOperator = new Operator();
      localOperator.mName = paramTelephonyManager.getNetworkOperatorName();
      localOperator.mCountry = paramTelephonyManager.getNetworkCountryIso();
      return localOperator;
    }
  }
  
  static final class Screen
  {
    @SerializedName("density")
    private int mDensity;
    @SerializedName("height")
    private int mHeight;
    @SerializedName("width")
    private int mWidth;
    
    public static Screen newInstance(DisplayMetrics paramDisplayMetrics)
    {
      Screen localScreen = new Screen();
      localScreen.mDensity = paramDisplayMetrics.densityDpi;
      localScreen.mWidth = paramDisplayMetrics.widthPixels;
      localScreen.mHeight = paramDisplayMetrics.heightPixels;
      return localScreen;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.DeviceInfo
 * JD-Core Version:    0.7.0.1
 */