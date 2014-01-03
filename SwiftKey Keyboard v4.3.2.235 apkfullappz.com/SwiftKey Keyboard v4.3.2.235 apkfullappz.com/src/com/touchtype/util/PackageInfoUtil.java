package com.touchtype.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public class PackageInfoUtil
{
  private static final String TAG = PackageInfoUtil.class.getSimpleName();
  private Context mContext;
  
  public PackageInfoUtil(Context paramContext)
  {
    this.mContext = paramContext;
  }
  
  public PackageInfo getPackageInfo(String paramString)
  {
    try
    {
      PackageInfo localPackageInfo = this.mContext.getPackageManager().getPackageInfo(paramString, 0);
      return localPackageInfo;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      LogUtil.e(TAG, "Failed to locate package information");
    }
    return null;
  }
  
  public boolean isPackageInstalled(String paramString)
  {
    try
    {
      this.mContext.getPackageManager().getPackageInfo(paramString, 0);
      return true;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException) {}
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.PackageInfoUtil
 * JD-Core Version:    0.7.0.1
 */