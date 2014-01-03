package com.touchtype.util;

public final class DateTimeUtils
{
  public static long millisToDays(long paramLong)
  {
    return paramLong / 86400000L;
  }
  
  public static long millisToHours(long paramLong)
  {
    return paramLong / 3600000L;
  }
  
  public static long millisToMinutes(long paramLong)
  {
    return paramLong / 60000L;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.DateTimeUtils
 * JD-Core Version:    0.7.0.1
 */