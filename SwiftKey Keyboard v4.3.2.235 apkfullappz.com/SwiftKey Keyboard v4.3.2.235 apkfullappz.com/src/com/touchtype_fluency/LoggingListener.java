package com.touchtype_fluency;

public abstract interface LoggingListener
{
  public abstract void log(Level paramLevel, String paramString);
  
  public static enum Level
  {
    static
    {
      Level[] arrayOfLevel = new Level[3];
      arrayOfLevel[0] = DEBUG;
      arrayOfLevel[1] = INFO;
      arrayOfLevel[2] = SEVERE;
      $VALUES = arrayOfLevel;
    }
    
    private Level() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.LoggingListener
 * JD-Core Version:    0.7.0.1
 */