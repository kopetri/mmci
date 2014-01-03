package com.google.android.gcm;

public final class GCMRegistrar
{
  private static String sRetryReceiverClassName;
  
  static void setRetryReceiverClassName(String paramString)
  {
    new StringBuilder("Setting the name of retry receiver class to ").append(paramString).toString();
    sRetryReceiverClassName = paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gcm.GCMRegistrar
 * JD-Core Version:    0.7.0.1
 */