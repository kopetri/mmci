package com.touchtype.broadcast.internal;

import android.os.Bundle;
import android.util.Log;

public final class BundleUtil
{
  public static final int getInt(Bundle paramBundle, String paramString, int paramInt)
  {
    try
    {
      String str = paramBundle.getString(paramString);
      if (str != null)
      {
        int i = Integer.parseInt(str);
        paramInt = i;
      }
      return paramInt;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      Log.e("com.touchtype.broadcast.internal.BundleUtil", "Received a bad expiry date in a cloud notification", localNumberFormatException);
    }
    return paramInt;
  }
  
  public static final String getString(Bundle paramBundle, String paramString1, String paramString2)
  {
    String str = paramBundle.getString(paramString1);
    if (str != null) {
      paramString2 = str;
    }
    return paramString2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.internal.BundleUtil
 * JD-Core Version:    0.7.0.1
 */