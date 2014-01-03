package com.touchtype.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.format.DateUtils;
import junit.framework.Assert;

public final class PreferencesUtil
{
  public static String buildSummary(long paramLong, String paramString1, String paramString2)
  {
    boolean bool = paramLong < 0L;
    localObject = null;
    if (bool) {}
    try
    {
      CharSequence localCharSequence = DateUtils.getRelativeTimeSpanString(paramLong, System.currentTimeMillis(), 60000L, 0);
      localObject = localCharSequence;
    }
    catch (NullPointerException localNullPointerException)
    {
      for (;;)
      {
        localObject = null;
      }
    }
    if (localObject != null)
    {
      Assert.assertTrue(paramString1.contains("%1$s"));
      return String.format(paramString1, new Object[] { localObject });
    }
    return paramString2;
  }
  
  public static void clearAllPreferences(Context paramContext, String paramString)
  {
    SharedPreferences.Editor localEditor = paramContext.getSharedPreferences(paramString, 0).edit();
    localEditor.clear();
    localEditor.commit();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.PreferencesUtil
 * JD-Core Version:    0.7.0.1
 */