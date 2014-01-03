package com.touchtype.sync.client;

import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract interface SyncClient
{
  public abstract boolean addSyncListener(SyncListener paramSyncListener);
  
  public abstract void changeDeviceDescription(String paramString1, String paramString2, RequestListener paramRequestListener);
  
  public abstract void changeMarketingPreferences(boolean paramBoolean, RequestListener paramRequestListener);
  
  public abstract void checkEmailStatusAuthentication(RequestListener paramRequestListener);
  
  public abstract void deleteAccount(RequestListener paramRequestListener);
  
  public abstract void deleteDevice(String paramString, RequestListener paramRequestListener);
  
  public abstract void deleteRemoteData(RequestListener paramRequestListener);
  
  public abstract void disableSubscription(CommonUtilities.Platform paramPlatform, Map<String, String> paramMap, RequestListener paramRequestListener);
  
  public abstract void enableSubscription(CommonUtilities.Platform paramPlatform, Map<String, String> paramMap, RequestListener paramRequestListener);
  
  public abstract void facebookAuthenticate(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, String paramString5, boolean paramBoolean2, RequestListener paramRequestListener);
  
  public abstract void finaliseEmailAuthentication(String paramString, RequestListener paramRequestListener);
  
  public abstract Credential getAuthenticationCredential();
  
  public abstract String getAuthenticationDeviceId();
  
  public abstract CommonUtilities.SyncAuthenticationState getAuthenticationStatus();
  
  public abstract List<Device> getDevices();
  
  public abstract long getLastPullTime();
  
  public abstract long getLastPushTime();
  
  public abstract long getLastSyncTime();
  
  public abstract boolean getNotificationsEnabled();
  
  public abstract void googleAuthenticate(String paramString1, String paramString2, String paramString3, CommonUtilities.AuthTokenType paramAuthTokenType, String paramString4, String paramString5, boolean paramBoolean1, String paramString6, boolean paramBoolean2, RequestListener paramRequestListener);
  
  public abstract boolean hasOptedInMarketing();
  
  public abstract void initiateEmailAuthentication(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, boolean paramBoolean, RequestListener paramRequestListener);
  
  public abstract boolean isPulling();
  
  public abstract boolean isPushing();
  
  public abstract boolean isSyncing();
  
  public abstract void pullData(boolean paramBoolean, RequestListener paramRequestListener);
  
  public abstract void pushBlacklist(List<String> paramList, Set<String> paramSet, RequestListener paramRequestListener);
  
  public abstract void pushLM(List<String> paramList, RequestListener paramRequestListener);
  
  public abstract void refreshDevices(RequestListener paramRequestListener);
  
  public abstract void refreshMarketingPreferences(RequestListener paramRequestListener);
  
  public abstract void resetClient();
  
  public abstract void shutDown();
  
  public abstract void syncNow(List<String> paramList, boolean paramBoolean, SyncListener paramSyncListener);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.SyncClient
 * JD-Core Version:    0.7.0.1
 */