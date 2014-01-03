package com.touchtype.sync.client;

import java.util.Map;

public abstract interface SyncListener
  extends RequestListener
{
  public abstract void onPullError(RequestListener.SyncError paramSyncError, String paramString);
  
  public abstract void onPullSuccess(Map<String, String> paramMap);
  
  public abstract void onPushError(RequestListener.SyncError paramSyncError, String paramString);
  
  public abstract void onPushSuccess(Map<String, String> paramMap);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.SyncListener
 * JD-Core Version:    0.7.0.1
 */