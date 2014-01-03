package com.touchtype.social;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.preferences.TouchTypePreferences;
import java.util.Date;

public final class DayXMLLoaderUtilities
{
  protected static boolean alreadyShown(Context paramContext, int paramInt)
  {
    return TouchTypePreferences.getInstance(paramContext).getInt("day_tip_shown", -1) == paramInt;
  }
  
  protected static int daysSinceInstall(long paramLong)
  {
    return (int)((new Date().getTime() - paramLong) / 86400000L);
  }
  
  protected static String valueAtIndex(Resources paramResources, int paramInt1, int paramInt2)
  {
    return paramResources.getStringArray(paramInt1)[paramInt2];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.DayXMLLoaderUtilities
 * JD-Core Version:    0.7.0.1
 */