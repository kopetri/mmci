package com.touchtype.sync.client;

import java.util.Map;

public abstract interface RequestListener
{
  public abstract void onError(SyncError paramSyncError, String paramString);
  
  public abstract void onSuccess(Map<String, String> paramMap);
  
  public static enum SyncError
  {
    static
    {
      FORBIDDEN = new SyncError("FORBIDDEN", 2);
      NOT_FOUND = new SyncError("NOT_FOUND", 3);
      TOO_MANY_REQUESTS = new SyncError("TOO_MANY_REQUESTS", 4);
      INTERNAL_SERVER_ERROR = new SyncError("INTERNAL_SERVER_ERROR", 5);
      BAD_GATEWAY = new SyncError("BAD_GATEWAY", 6);
      UNAVAILABLE = new SyncError("UNAVAILABLE", 7);
      SYNC_NOW = new SyncError("SYNC_NOW", 8);
      ERROR = new SyncError("ERROR", 9);
      CLIENT = new SyncError("CLIENT", 10);
      PULL = new SyncError("PULL", 11);
      PUSH = new SyncError("PUSH", 12);
      THROTTLE = new SyncError("THROTTLE", 13);
      INVALID_MODEL = new SyncError("INVALID_MODEL", 14);
      SyncError[] arrayOfSyncError = new SyncError[15];
      arrayOfSyncError[0] = BAD_REQUEST;
      arrayOfSyncError[1] = UNAUTHORIZED;
      arrayOfSyncError[2] = FORBIDDEN;
      arrayOfSyncError[3] = NOT_FOUND;
      arrayOfSyncError[4] = TOO_MANY_REQUESTS;
      arrayOfSyncError[5] = INTERNAL_SERVER_ERROR;
      arrayOfSyncError[6] = BAD_GATEWAY;
      arrayOfSyncError[7] = UNAVAILABLE;
      arrayOfSyncError[8] = SYNC_NOW;
      arrayOfSyncError[9] = ERROR;
      arrayOfSyncError[10] = CLIENT;
      arrayOfSyncError[11] = PULL;
      arrayOfSyncError[12] = PUSH;
      arrayOfSyncError[13] = THROTTLE;
      arrayOfSyncError[14] = INVALID_MODEL;
      a = arrayOfSyncError;
    }
    
    private SyncError() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.RequestListener
 * JD-Core Version:    0.7.0.1
 */