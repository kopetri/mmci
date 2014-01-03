package com.facebook;

public enum LoggingBehavior
{
  static
  {
    INCLUDE_ACCESS_TOKENS = new LoggingBehavior("INCLUDE_ACCESS_TOKENS", 1);
    INCLUDE_RAW_RESPONSES = new LoggingBehavior("INCLUDE_RAW_RESPONSES", 2);
    CACHE = new LoggingBehavior("CACHE", 3);
    DEVELOPER_ERRORS = new LoggingBehavior("DEVELOPER_ERRORS", 4);
    LoggingBehavior[] arrayOfLoggingBehavior = new LoggingBehavior[5];
    arrayOfLoggingBehavior[0] = REQUESTS;
    arrayOfLoggingBehavior[1] = INCLUDE_ACCESS_TOKENS;
    arrayOfLoggingBehavior[2] = INCLUDE_RAW_RESPONSES;
    arrayOfLoggingBehavior[3] = CACHE;
    arrayOfLoggingBehavior[4] = DEVELOPER_ERRORS;
    $VALUES = arrayOfLoggingBehavior;
  }
  
  private LoggingBehavior() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.LoggingBehavior
 * JD-Core Version:    0.7.0.1
 */