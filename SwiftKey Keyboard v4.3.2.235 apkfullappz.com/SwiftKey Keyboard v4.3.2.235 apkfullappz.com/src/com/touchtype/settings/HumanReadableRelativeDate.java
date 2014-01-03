package com.touchtype.settings;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.util.DateTimeUtils;

public final class HumanReadableRelativeDate
{
  public static String sinceLastEvent(Context paramContext, long paramLong, int paramInt)
  {
    long l = System.currentTimeMillis() - paramLong;
    if (paramLong == 0L) {
      return paramContext.getString(paramInt);
    }
    if (l < 60000L) {
      return paramContext.getString(2131296595);
    }
    if (l < 3600000L)
    {
      int k = (int)DateTimeUtils.millisToMinutes(l);
      Resources localResources3 = paramContext.getResources();
      Object[] arrayOfObject3 = new Object[1];
      arrayOfObject3[0] = Integer.valueOf(k);
      return localResources3.getQuantityString(2131689473, k, arrayOfObject3);
    }
    if (l < 86400000L)
    {
      int j = (int)DateTimeUtils.millisToHours(l);
      Resources localResources2 = paramContext.getResources();
      Object[] arrayOfObject2 = new Object[1];
      arrayOfObject2[0] = Integer.valueOf(j);
      return localResources2.getQuantityString(2131689474, j, arrayOfObject2);
    }
    int i = (int)DateTimeUtils.millisToDays(l);
    Resources localResources1 = paramContext.getResources();
    Object[] arrayOfObject1 = new Object[1];
    arrayOfObject1[0] = Integer.valueOf(i);
    return localResources1.getQuantityString(2131689475, i, arrayOfObject1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.HumanReadableRelativeDate
 * JD-Core Version:    0.7.0.1
 */