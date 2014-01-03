package com.touchtype.broadcast.internal;

import java.util.Date;

public final class DateProvider
{
  public static Date testDate = null;
  
  public static final Date now()
  {
    if (testDate == null) {
      return new Date();
    }
    return testDate;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.internal.DateProvider
 * JD-Core Version:    0.7.0.1
 */