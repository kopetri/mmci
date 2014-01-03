package com.touchtype.resources;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;

public final class ProductConfiguration
{
  public static List<String> buildAvailablePackageList(Context paramContext)
  {
    String[] arrayOfString = paramContext.getResources().getStringArray(2131623937);
    ArrayList localArrayList = Lists.newArrayList();
    PackageManager localPackageManager = paramContext.getPackageManager();
    int i = arrayOfString.length;
    int j = 0;
    for (;;)
    {
      String str;
      if (j < i) {
        str = arrayOfString[j];
      }
      try
      {
        localPackageManager.getPackageInfo(str, 1);
        localArrayList.add(str);
        label56:
        j++;
        continue;
        return localArrayList;
      }
      catch (PackageManager.NameNotFoundException localNameNotFoundException)
      {
        break label56;
      }
    }
  }
  
  public static String getBundledLanguagePacks(Context paramContext)
  {
    return paramContext.getString(2131296317);
  }
  
  public static String getMarketName(Context paramContext)
  {
    return paramContext.getString(2131296891);
  }
  
  public static String getProductName(Context paramContext)
  {
    return paramContext.getString(2131296303);
  }
  
  public static boolean getSDKParameterLearning(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492906);
  }
  
  public static String getSDKParameters(Context paramContext)
  {
    return paramContext.getResources().getString(2131296342);
  }
  
  public static boolean hasBundledLanguagePacks(Context paramContext)
  {
    return getBundledLanguagePacks(paramContext).length() > 0;
  }
  
  public static boolean isCloudBuild(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492889);
  }
  
  public static boolean isFlowBuild(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492922);
  }
  
  public static boolean isPreinstalled(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492924);
  }
  
  public static boolean isWatchBuild(Context paramContext)
  {
    return paramContext.getResources().getBoolean(2131492901);
  }
  
  public static boolean isWatchOnLargeBuild(Context paramContext)
  {
    return (paramContext.getResources().getBoolean(2131492901)) && (paramContext.getResources().getBoolean(2131492902));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.resources.ProductConfiguration
 * JD-Core Version:    0.7.0.1
 */