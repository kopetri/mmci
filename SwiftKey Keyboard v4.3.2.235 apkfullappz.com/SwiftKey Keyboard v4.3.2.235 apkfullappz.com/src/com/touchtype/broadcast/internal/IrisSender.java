package com.touchtype.broadcast.internal;

import android.content.Context;
import android.content.Intent;

public final class IrisSender
{
  private static Class<?> mStatsSendingClass;
  
  public static void sendAsync(Context paramContext, String paramString, Object paramObject, Interval paramInterval)
    throws Exception
  {
    if (mStatsSendingClass != null)
    {
      Intent localIntent = new Intent(paramContext, mStatsSendingClass);
      localIntent.putExtra("message", paramObject.toString());
      localIntent.putExtra("url", paramString);
      localIntent.putExtra("send_interval", paramInterval.name());
      paramContext.startService(localIntent);
      return;
    }
    throw new Exception("No stats sending class has been registered recently");
  }
  
  public static void setStatsSendingIntent(Class<?> paramClass)
  {
    mStatsSendingClass = paramClass;
  }
  
  public static enum Interval
  {
    static
    {
      DAILY = new Interval("DAILY", 1);
      WEEKLY = new Interval("WEEKLY", 2);
      Interval[] arrayOfInterval = new Interval[3];
      arrayOfInterval[0] = NOW;
      arrayOfInterval[1] = DAILY;
      arrayOfInterval[2] = WEEKLY;
      $VALUES = arrayOfInterval;
    }
    
    private Interval() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.internal.IrisSender
 * JD-Core Version:    0.7.0.1
 */