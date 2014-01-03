package com.touchtype.sync.client;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

public abstract interface SSLTools
{
  public abstract SSLContext getContext();
  
  public abstract HostnameVerifier getHostnameVerifier();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.SSLTools
 * JD-Core Version:    0.7.0.1
 */