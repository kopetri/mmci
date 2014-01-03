package com.touchtype.common.iris;

public enum SendInterval
{
  private final String mFilename;
  private final long mTimedelta;
  
  static
  {
    DAILY = new SendInterval("DAILY", 1, "iris_messages_cache_daily", 86400000L);
    WEEKLY = new SendInterval("WEEKLY", 2, "iris_messages_cache_weekly", 604800000L);
    SendInterval[] arrayOfSendInterval = new SendInterval[3];
    arrayOfSendInterval[0] = NOW;
    arrayOfSendInterval[1] = DAILY;
    arrayOfSendInterval[2] = WEEKLY;
    $VALUES = arrayOfSendInterval;
  }
  
  private SendInterval(String paramString, long paramLong)
  {
    this.mFilename = paramString;
    this.mTimedelta = paramLong;
  }
  
  public static SendInterval stringToSendInterval(String paramString)
  {
    try
    {
      SendInterval localSendInterval = valueOf(paramString);
      return localSendInterval;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      return NOW;
    }
    catch (NullPointerException localNullPointerException)
    {
      label8:
      break label8;
    }
  }
  
  public String getFilename()
  {
    return this.mFilename;
  }
  
  public long getTimedelta()
  {
    return this.mTimedelta;
  }
  
  public boolean requiresScheduler()
  {
    return this != NOW;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.SendInterval
 * JD-Core Version:    0.7.0.1
 */