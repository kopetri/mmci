package com.touchtype.sync.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class SyncClientImpl
  implements SyncClient
{
  private static String n = "invalid_model";
  private final LoggingListener a;
  private final e b;
  private final k c;
  private final i d;
  private final ExecutorService e;
  private final SyncStorage f;
  private final DynamicModelHandler g;
  private f h;
  private f i;
  private f j;
  private f k;
  private Future<?> l;
  private String m;
  
  public SyncClientImpl(String paramString1, String paramString2, SSLTools paramSSLTools, LoggingListener paramLoggingListener, SyncStorage paramSyncStorage, DynamicModelHandler paramDynamicModelHandler)
  {
    this.m = paramString2;
    this.a = paramLoggingListener;
    this.b = new e(paramSyncStorage, this.a);
    this.c = new k(paramSyncStorage, this.a);
    this.d = new i(paramString1, paramSSLTools, this.a);
    this.e = Executors.newCachedThreadPool();
    this.f = paramSyncStorage;
    this.g = paramDynamicModelHandler;
    this.i = new f();
    this.h = new f();
    this.j = new f();
    this.k = new f();
  }
  
  private void a(RequestListener.SyncError paramSyncError, String paramString, RequestListener paramRequestListener)
  {
    e.c(this.a, "com.touchtype.sync.client", paramString);
    if (paramRequestListener != null) {
      paramRequestListener.onError(paramSyncError, paramString);
    }
  }
  
  private void a(RequestListener.SyncError paramSyncError, String paramString, List<RequestListener> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      a(paramSyncError, paramString, (RequestListener)localIterator.next());
    }
  }
  
  private void a(RequestListener paramRequestListener, int paramInt, String paramString1, String paramString2)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      RequestListener.SyncError localSyncError = RequestListener.SyncError.ERROR;
      String str = "An unknown error has occurred";
      for (;;)
      {
        a(localSyncError, paramString1 + " : " + str, paramRequestListener);
        return;
        localSyncError = RequestListener.SyncError.BAD_REQUEST;
        str = null;
        if (paramString2 != null)
        {
          str = new JsonParser().parse(paramString2).getAsJsonObject().get("description").getAsString();
          continue;
          localSyncError = RequestListener.SyncError.UNAUTHORIZED;
          str = "Missing authentication credentials, or credentials are invalid";
          resetClient();
          continue;
          localSyncError = RequestListener.SyncError.FORBIDDEN;
          str = "Device does not have permission to access the resource";
          continue;
          localSyncError = RequestListener.SyncError.NOT_FOUND;
          str = "The requested resource does not exist";
          continue;
          localSyncError = RequestListener.SyncError.TOO_MANY_REQUESTS;
          str = "The client has made too many requests, rate limiting is applied";
          continue;
          localSyncError = RequestListener.SyncError.INTERNAL_SERVER_ERROR;
          str = "A server error has occurred";
          continue;
          localSyncError = RequestListener.SyncError.BAD_GATEWAY;
          str = "The servers are down or being upgraded";
        }
      }
    }
  }
  
  private void a(String paramString, RequestListener paramRequestListener)
  {
    a(paramString, paramRequestListener, Collections.emptyMap());
  }
  
  private void a(String paramString, RequestListener paramRequestListener, Map<String, String> paramMap)
  {
    LoggingListener localLoggingListener = this.a;
    if (localLoggingListener != null) {
      localLoggingListener.log(LoggingListener.Level.INFO, "com.touchtype.sync.client" + ": " + paramString);
    }
    if (paramRequestListener != null) {
      paramRequestListener.onSuccess(paramMap);
    }
  }
  
  private void a(String paramString, List<RequestListener> paramList)
  {
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext()) {
      a(paramString, (RequestListener)localIterator.next(), Collections.emptyMap());
    }
  }
  
  public static String getVersion()
  {
    return "0.8.0-build51";
  }
  
  public boolean addSyncListener(SyncListener paramSyncListener)
  {
    return this.k.b(paramSyncListener);
  }
  
  public void changeDeviceDescription(String paramString1, String paramString2, final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.b(this.b.b(), this.b.c(), paramString1, paramString2, new c(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when changing device description", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Change device description", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          Device localDevice = (Device)new Gson().fromJson(paramAnonymousString, Device.class);
          if (localDevice != null)
          {
            SyncClientImpl.b(SyncClientImpl.this).a(localDevice.getId(), localDevice.getDescription());
            SyncClientImpl.a(SyncClientImpl.this, "Device description successfully changed", paramRequestListener);
            return;
          }
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Device change not confirmed by server", paramRequestListener);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Device change not confirmed by server", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void changeMarketingPreferences(final boolean paramBoolean, final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.a(this.b.b(), this.b.c(), paramBoolean, new j(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when changing marketing preferences", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Change marketing preferences", paramAnonymousString);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this).a(paramBoolean);
          SyncClientImpl.a(SyncClientImpl.this, "Marketing preferences successfully changed", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void checkEmailStatusAuthentication(final RequestListener paramRequestListener)
  {
    if (this.b.e() == CommonUtilities.SyncAuthenticationState.INITIATED)
    {
      Runnable localRunnable = this.d.a(this.b.d(), new c(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred in checking authentication status", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Authentication status", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonObject localJsonObject = new JsonParser().parse(paramAnonymousString).getAsJsonObject();
          HashMap localHashMap = new HashMap();
          String[] arrayOfString = { "status" };
          localHashMap.putAll(SyncClientImpl.a(SyncClientImpl.this, localJsonObject, arrayOfString));
          if (localHashMap.containsKey("status"))
          {
            SyncClientImpl.a(SyncClientImpl.this, "Successfully retrieved authentication status", paramRequestListener, localHashMap);
            return;
          }
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "An authentication status was not returned from the server", paramRequestListener);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "An authentication status was not returned from the server", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.CLIENT, "The current authentication state does not allow this request", paramRequestListener);
  }
  
  public void deleteAccount(final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.d(this.b.b(), this.b.c(), new j(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when attempting to delete account", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Delete Account", paramAnonymousString);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.this.resetClient();
          SyncClientImpl.a(SyncClientImpl.this, "Sync server has acknowledged account deletion", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void deleteDevice(final String paramString, final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.a(this.b.b(), this.b.c(), paramString, new j(this.a)
      {
        final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when deleting a device from the sync account", paramRequestListener);
        }
        
        final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Delete device", paramAnonymousString);
        }
        
        final void a(Map<String, List<String>> paramAnonymousMap)
        {
          if (SyncClientImpl.a(SyncClientImpl.this).a().equals(paramString))
          {
            e.a(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "Current device has been deleted from server, resetting client...");
            SyncClientImpl.this.resetClient();
          }
          for (;;)
          {
            SyncClientImpl.a(SyncClientImpl.this, "Device deletion was successful", paramRequestListener);
            return;
            SyncClientImpl.b(SyncClientImpl.this).b(paramString);
          }
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void deleteRemoteData(final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.c(this.b.b(), this.b.c(), new j(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when attempting to delete remote data", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Delete Remote Data", paramAnonymousString);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, "Sync server has acknowledged remote data deletion", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void disableSubscription(CommonUtilities.Platform paramPlatform, Map<String, String> paramMap, RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      this.c.a(false);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void enableSubscription(CommonUtilities.Platform paramPlatform, Map<String, String> paramMap, final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.a(this.b.b(), this.b.c(), paramPlatform, paramMap, new j(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when enabling platform subscriptions with sync server", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Enable subscription", paramAnonymousString);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.b(SyncClientImpl.this).a(true);
          SyncClientImpl.a(SyncClientImpl.this, "Successfully subscribed to platform notifications with sync server", paramRequestListener);
        }
      });
      this.e.submit(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void facebookAuthenticate(String paramString1, String paramString2, String paramString3, String paramString4, boolean paramBoolean1, String paramString5, final boolean paramBoolean2, final RequestListener paramRequestListener)
  {
    CommonUtilities.SyncAuthenticationState localSyncAuthenticationState = this.b.e();
    if ((localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.UNAUTHENTICATED) || (localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.INITIATED))
    {
      if (this.l != null) {
        this.l.cancel(true);
      }
      Runnable localRunnable = this.d.a(paramString1, paramString2, this.m, paramString4, paramString3, paramBoolean1, paramString5, paramBoolean2, new c(this.a)
      {
        Map<String, String> a = new HashMap();
        
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred during Facebook authentication", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Facebook Authentication", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonObject localJsonObject = new JsonParser().parse(paramAnonymousString).getAsJsonObject();
          String[] arrayOfString = { "name", "existingAccount", "deviceDescription" };
          this.a.putAll(SyncClientImpl.a(SyncClientImpl.this, localJsonObject, arrayOfString));
          JsonElement localJsonElement1 = localJsonObject.get("appSecret");
          JsonElement localJsonElement2 = localJsonObject.get("appId");
          JsonElement localJsonElement3 = localJsonObject.get("deviceId");
          if ((localJsonElement1 != null) && (localJsonElement2 != null) && (localJsonElement3 != null))
          {
            e locale = SyncClientImpl.a(SyncClientImpl.this);
            String str1 = localJsonElement3.getAsString();
            String str2 = localJsonElement2.getAsString();
            String str3 = localJsonElement1.getAsString();
            if (this.a.containsKey("name")) {}
            for (String str4 = (String)this.a.get("name");; str4 = "")
            {
              locale.a(str1, str2, str3, new Credential(str4, CommonUtilities.CredentialType.FACEBOOK));
              SyncClientImpl.a(SyncClientImpl.this).a(paramBoolean2);
              SyncClientImpl.this.refreshDevices(new RequestListener()
              {
                public final void onError(RequestListener.SyncError paramAnonymous2SyncError, String paramAnonymous2String)
                {
                  e.c(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "An error occurred when retrieving devices from sync server");
                  SyncClientImpl.a(SyncClientImpl.this, "Successfully completed Facebook authentication", SyncClientImpl.2.this.b, SyncClientImpl.2.this.a);
                }
                
                public final void onSuccess(Map<String, String> paramAnonymous2Map)
                {
                  SyncClientImpl.a(SyncClientImpl.this, "Successfully completed Facebook authentication", SyncClientImpl.2.this.b, SyncClientImpl.2.this.a);
                }
              });
              return;
            }
          }
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during Facebook authentication", paramRequestListener);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during Facebook authentication", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.CLIENT, "Authentication has already completed", paramRequestListener);
  }
  
  public void finaliseEmailAuthentication(String paramString, RequestListener paramRequestListener)
  {
    if (!this.j.a(paramRequestListener))
    {
      a(RequestListener.SyncError.CLIENT, "A confirmation request is already in progress", paramRequestListener);
      return;
    }
    if (this.b.e() == CommonUtilities.SyncAuthenticationState.INITIATED)
    {
      Runnable localRunnable = this.d.a(this.b.d(), paramString, new c(this.a)
      {
        private Map<String, String> b = new HashMap();
        
        public final void a()
        {
          List localList = SyncClientImpl.d(SyncClientImpl.this).c();
          SyncClientImpl.d(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when finalising authentication", localList);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          List localList = SyncClientImpl.d(SyncClientImpl.this).c();
          SyncClientImpl.d(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, localList, paramAnonymousInt, "Finalise Authentication", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonObject localJsonObject = new JsonParser().parse(paramAnonymousString).getAsJsonObject();
          String[] arrayOfString = { "deviceDescription" };
          this.b.putAll(SyncClientImpl.a(SyncClientImpl.this, localJsonObject, arrayOfString));
          JsonElement localJsonElement1 = localJsonObject.get("appSecret");
          JsonElement localJsonElement2 = localJsonObject.get("appId");
          JsonElement localJsonElement3 = localJsonObject.get("deviceId");
          if ((localJsonElement1 != null) && (localJsonElement2 != null) && (localJsonElement3 != null))
          {
            SyncClientImpl.a(SyncClientImpl.this).a(localJsonElement3.getAsString(), localJsonElement2.getAsString(), localJsonElement1.getAsString(), null);
            SyncClientImpl.this.refreshDevices(new RequestListener()
            {
              public final void onError(RequestListener.SyncError paramAnonymous2SyncError, String paramAnonymous2String)
              {
                e.c(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "An error occurred when retrieving devices from sync server");
                List localList = SyncClientImpl.d(SyncClientImpl.this).c();
                SyncClientImpl.d(SyncClientImpl.this).a();
                SyncClientImpl.a(SyncClientImpl.this, "Successfully completed email authentication", localList);
              }
              
              public final void onSuccess(Map<String, String> paramAnonymous2Map)
              {
                List localList = SyncClientImpl.d(SyncClientImpl.this).c();
                SyncClientImpl.d(SyncClientImpl.this).a();
                SyncClientImpl.a(SyncClientImpl.this, "Successfully completed email authentication", localList);
              }
            });
            return;
          }
          e.c(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "An error occurred when retrieving credentials from sync server");
          List localList = SyncClientImpl.d(SyncClientImpl.this).c();
          SyncClientImpl.d(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during authentication finalising", localList);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          List localList = SyncClientImpl.d(SyncClientImpl.this).c();
          SyncClientImpl.d(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during authentication finalising", localList);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.CLIENT, "Sync does not allow this request in the current authentication state", paramRequestListener);
  }
  
  public Credential getAuthenticationCredential()
  {
    return this.b.g();
  }
  
  public String getAuthenticationDeviceId()
  {
    return this.b.a();
  }
  
  public CommonUtilities.SyncAuthenticationState getAuthenticationStatus()
  {
    return this.b.e();
  }
  
  public List<Device> getDevices()
  {
    return this.c.e();
  }
  
  public long getLastPullTime()
  {
    return this.c.b();
  }
  
  public long getLastPushTime()
  {
    return this.c.a();
  }
  
  public long getLastSyncTime()
  {
    return this.c.d();
  }
  
  public boolean getNotificationsEnabled()
  {
    return this.c.g();
  }
  
  public void googleAuthenticate(String paramString1, String paramString2, String paramString3, CommonUtilities.AuthTokenType paramAuthTokenType, String paramString4, String paramString5, boolean paramBoolean1, String paramString6, final boolean paramBoolean2, final RequestListener paramRequestListener)
  {
    CommonUtilities.SyncAuthenticationState localSyncAuthenticationState = this.b.e();
    if ((localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.UNAUTHENTICATED) || (localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.INITIATED))
    {
      if (this.l != null) {
        this.l.cancel(true);
      }
      Runnable localRunnable = this.d.a(paramString1, paramString2, paramString3, paramAuthTokenType, this.m, paramString5, paramString4, paramBoolean1, paramString6, paramBoolean2, new c(this.a)
      {
        Map<String, String> a = new HashMap();
        
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred during Google authentication", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Google Authentication", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonObject localJsonObject = new JsonParser().parse(paramAnonymousString).getAsJsonObject();
          JsonElement localJsonElement1 = localJsonObject.get("appSecret");
          JsonElement localJsonElement2 = localJsonObject.get("appId");
          JsonElement localJsonElement3 = localJsonObject.get("deviceId");
          String[] arrayOfString = { "existingAccount", "email", "deviceDescription" };
          this.a.putAll(SyncClientImpl.a(SyncClientImpl.this, localJsonObject, arrayOfString));
          if ((localJsonElement1 != null) && (localJsonElement3 != null) && (localJsonElement2 != null))
          {
            e locale = SyncClientImpl.a(SyncClientImpl.this);
            String str1 = localJsonElement3.getAsString();
            String str2 = localJsonElement2.getAsString();
            String str3 = localJsonElement1.getAsString();
            if (this.a.containsKey("email")) {}
            for (String str4 = (String)this.a.get("email");; str4 = "")
            {
              locale.a(str1, str2, str3, new Credential(str4, CommonUtilities.CredentialType.GOOGLE));
              SyncClientImpl.a(SyncClientImpl.this).a(paramBoolean2);
              SyncClientImpl.this.refreshDevices(new RequestListener()
              {
                public final void onError(RequestListener.SyncError paramAnonymous2SyncError, String paramAnonymous2String)
                {
                  e.c(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "An error occurred when retrieving devices from sync server");
                  SyncClientImpl.a(SyncClientImpl.this, "Successfully completed Google authentication", SyncClientImpl.18.this.b, SyncClientImpl.18.this.a);
                }
                
                public final void onSuccess(Map<String, String> paramAnonymous2Map)
                {
                  SyncClientImpl.a(SyncClientImpl.this, "Successfully completed Google authentication", SyncClientImpl.18.this.b, SyncClientImpl.18.this.a);
                }
              });
              return;
            }
          }
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during Google authentication", paramRequestListener);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Authentication credentials were not returned from server during Google authentication", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.CLIENT, "Authentication has already completed", paramRequestListener);
  }
  
  public boolean hasOptedInMarketing()
  {
    return this.b.i();
  }
  
  public void initiateEmailAuthentication(final String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, final boolean paramBoolean, final RequestListener paramRequestListener)
  {
    CommonUtilities.SyncAuthenticationState localSyncAuthenticationState = this.b.e();
    if ((localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.UNAUTHENTICATED) || (localSyncAuthenticationState == CommonUtilities.SyncAuthenticationState.INITIATED))
    {
      if (this.l != null) {
        this.l.cancel(true);
      }
      Runnable localRunnable = this.d.a(paramString1, paramString2, this.m, paramString4, paramString3, paramString5, paramBoolean, new c(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred in initalising authentication", paramRequestListener);
          SyncClientImpl.a(SyncClientImpl.this, null);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Initialise authentication", paramAnonymousString);
          SyncClientImpl.a(SyncClientImpl.this, null);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonElement localJsonElement = new JsonParser().parse(paramAnonymousString).getAsJsonObject().get("confirmationId");
          if (localJsonElement != null)
          {
            SyncClientImpl.a(SyncClientImpl.this).a(localJsonElement.getAsString(), new Credential(paramString1, CommonUtilities.CredentialType.EMAIL));
            SyncClientImpl.a(SyncClientImpl.this).a(paramBoolean);
            SyncClientImpl.a(SyncClientImpl.this, "Successfully initiated authorisation", paramRequestListener);
          }
          for (;;)
          {
            SyncClientImpl.a(SyncClientImpl.this, null);
            return;
            SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "A confirmation ID was not returned from the server during authentication", paramRequestListener);
          }
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "A confirmation ID was not returned from the server during authentication", paramRequestListener);
          SyncClientImpl.a(SyncClientImpl.this, null);
        }
      });
      this.l = this.e.submit(localRunnable);
      return;
    }
    a(RequestListener.SyncError.CLIENT, "An initial authentication request is already in process", paramRequestListener);
  }
  
  public boolean isPulling()
  {
    return this.h.b();
  }
  
  public boolean isPushing()
  {
    return this.i.b();
  }
  
  public boolean isSyncing()
  {
    return this.k.b();
  }
  
  public void pullData(boolean paramBoolean, RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      if (!this.h.a(paramRequestListener))
      {
        a(RequestListener.SyncError.PULL, "A pull request is already in progress", paramRequestListener);
        return;
      }
      if (60000L + this.c.b() > System.currentTimeMillis())
      {
        List localList = this.h.c();
        this.h.a();
        a(RequestListener.SyncError.THROTTLE, "Too many pull requests, please wait", localList);
        return;
      }
      final long l1 = System.currentTimeMillis();
      Runnable localRunnable = this.d.a(this.c.c(), this.b.b(), this.b.c(), paramBoolean, new d(this.f, this.a)
      {
        String a = null;
        
        public final void a()
        {
          SyncClientImpl.b(SyncClientImpl.this).c(l1);
          List localList = SyncClientImpl.f(SyncClientImpl.this).c();
          SyncClientImpl.f(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when pulling language model from sync server", localList);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.b(SyncClientImpl.this).c(l1);
          List localList = SyncClientImpl.f(SyncClientImpl.this).c();
          SyncClientImpl.f(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, localList, paramAnonymousInt, "Pull data", paramAnonymousString);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          e.a(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "Sync pull: nothing to pull from the server");
          if (paramAnonymousMap.containsKey("X-Last-Version"))
          {
            List localList2 = (List)paramAnonymousMap.get("X-Last-Version");
            if (!localList2.isEmpty()) {
              this.a = ((String)localList2.get(0));
            }
          }
          if (this.a != null) {
            SyncClientImpl.b(SyncClientImpl.this).a(this.a);
          }
          SyncClientImpl.b(SyncClientImpl.this).b(l1);
          List localList1 = SyncClientImpl.f(SyncClientImpl.this).c();
          SyncClientImpl.f(SyncClientImpl.this).a();
          SyncClientImpl.a(SyncClientImpl.this, "Pulling data was successful", localList1);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap, final File paramAnonymousFile)
        {
          e.b(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "Language model successfully pulled from sync server, proceeding to merge...");
          if (paramAnonymousMap.containsKey("X-Last-Version"))
          {
            List localList2 = (List)paramAnonymousMap.get("X-Last-Version");
            if (!localList2.isEmpty()) {
              this.a = ((String)localList2.get(0));
            }
          }
          String str;
          Type localType;
          if (paramAnonymousMap.containsKey("X-Stop-Words"))
          {
            List localList1 = (List)paramAnonymousMap.get("X-Stop-Words");
            if (!localList1.isEmpty())
            {
              str = (String)localList1.get(0);
              localType = new TypeToken() {}.getType();
            }
          }
          for (Collection localCollection = (Collection)new Gson().fromJson(str, localType);; localCollection = null)
          {
            SyncClientImpl.g(SyncClientImpl.this).mergeServerDelta(paramAnonymousFile, localCollection, new CompletionListener()
            {
              public final void onComplete(boolean paramAnonymous2Boolean1, boolean paramAnonymous2Boolean2)
              {
                if (paramAnonymous2Boolean2) {
                  paramAnonymousFile.delete();
                }
                if (paramAnonymous2Boolean1)
                {
                  e.b(SyncClientImpl.c(SyncClientImpl.this), "com.touchtype.sync.client", "Merging complete");
                  SyncClientImpl.b(SyncClientImpl.this).b(SyncClientImpl.4.this.b);
                  if (SyncClientImpl.4.this.a != null) {
                    SyncClientImpl.b(SyncClientImpl.this).a(SyncClientImpl.4.this.a);
                  }
                  List localList2 = SyncClientImpl.f(SyncClientImpl.this).c();
                  SyncClientImpl.f(SyncClientImpl.this).a();
                  SyncClientImpl.a(SyncClientImpl.this, "Pulling has completed", localList2);
                  return;
                }
                List localList1 = SyncClientImpl.f(SyncClientImpl.this).c();
                SyncClientImpl.f(SyncClientImpl.this).a();
                SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "Error: Merging of temporary language model failed", localList1);
              }
            });
            return;
          }
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client not authorized for pulling data", paramRequestListener);
  }
  
  public void pushBlacklist(List<String> paramList, Set<String> paramSet, final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      if (!paramSet.isEmpty())
      {
        Gson localGson = new Gson();
        String str1 = localGson.toJson(paramList);
        String str2 = localGson.toJson(paramSet);
        Runnable localRunnable = this.d.c(str2, str1, this.b.b(), this.b.c(), new j(this.a)
        {
          public final void a()
          {
            SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.PUSH, "An error occurred when pushing black list words to sync server", paramRequestListener);
          }
          
          public final void a(int paramAnonymousInt, String paramAnonymousString)
          {
            SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Push blacklist", paramAnonymousString);
          }
          
          public final void a(Map<String, List<String>> paramAnonymousMap)
          {
            SyncClientImpl.a(SyncClientImpl.this, "Successfully pushed black list words to sync server", paramRequestListener);
          }
        });
        this.e.execute(localRunnable);
        return;
      }
      a("No blacklist data to send to the sync server.", paramRequestListener);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client not authorized for pushing blacklist", paramRequestListener);
  }
  
  public void pushLM(List<String> paramList, RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      if (!this.i.a(paramRequestListener))
      {
        a(RequestListener.SyncError.PUSH, "A push request is already in progress", paramRequestListener);
        return;
      }
      if (60000L + this.c.a() > System.currentTimeMillis())
      {
        List localList2 = this.i.c();
        this.i.a();
        a(RequestListener.SyncError.THROTTLE, "Too many push requests, please wait", localList2);
        return;
      }
      File localFile = new File(this.f.getPushDeltaModelDirectory(), "dynamic.lm");
      if (localFile.exists())
      {
        final long l1 = System.currentTimeMillis();
        String str = new Gson().toJson(paramList);
        this.d.a(str, localFile.getAbsolutePath(), this.b.b(), this.b.c(), new j(this.a)
        {
          public final void a()
          {
            List localList = SyncClientImpl.h(SyncClientImpl.this).c();
            SyncClientImpl.h(SyncClientImpl.this).a();
            SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.PULL, "An error occurred when pushing language model to sync server", localList);
          }
          
          public final void a(int paramAnonymousInt, String paramAnonymousString)
          {
            if ((paramAnonymousString != null) && (new JsonParser().parse(paramAnonymousString).getAsJsonObject().get("error").getAsString().equals(SyncClientImpl.a())))
            {
              List localList2 = SyncClientImpl.h(SyncClientImpl.this).c();
              SyncClientImpl.h(SyncClientImpl.this).a();
              SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.INVALID_MODEL, "Language model received by server was invalid", localList2);
              return;
            }
            List localList1 = SyncClientImpl.h(SyncClientImpl.this).c();
            SyncClientImpl.h(SyncClientImpl.this).a();
            SyncClientImpl.a(SyncClientImpl.this, localList1, paramAnonymousInt, "Push LM", paramAnonymousString);
          }
          
          public final void a(Map<String, List<String>> paramAnonymousMap)
          {
            SyncClientImpl.b(SyncClientImpl.this).a(l1);
            List localList = SyncClientImpl.h(SyncClientImpl.this).c();
            SyncClientImpl.h(SyncClientImpl.this).a();
            SyncClientImpl.a(SyncClientImpl.this, "Successfully pushed delta language model to sync server", localList);
          }
        }).run();
        return;
      }
      this.c.a(System.currentTimeMillis());
      List localList1 = this.i.c();
      this.i.a();
      a("No language date to push to the sync server.", localList1);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client not authorized for pushing language model", paramRequestListener);
  }
  
  public void refreshDevices(final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.b(this.b.b(), this.b.c(), new c(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when retrieving devices from sync server", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Refresh devices", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          List localList = SyncClientImpl.a(SyncClientImpl.this, paramAnonymousString);
          SyncClientImpl.b(SyncClientImpl.this).a(localList);
          SyncClientImpl.a(SyncClientImpl.this, "Devices successfully retrieved from sync server", paramRequestListener);
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "No device data was returned from server", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void refreshMarketingPreferences(final RequestListener paramRequestListener)
  {
    if (this.b.h())
    {
      Runnable localRunnable = this.d.e(this.b.b(), this.b.c(), new c(this.a)
      {
        public final void a()
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.ERROR, "An error occurred when retrieving marketing preferences from sync server", paramRequestListener);
        }
        
        public final void a(int paramAnonymousInt, String paramAnonymousString)
        {
          SyncClientImpl.a(SyncClientImpl.this, paramRequestListener, paramAnonymousInt, "Marketing preferences", paramAnonymousString);
        }
        
        public final void a(String paramAnonymousString)
        {
          JsonElement localJsonElement = new JsonParser().parse(paramAnonymousString).getAsJsonObject().get("optIn");
          if (localJsonElement.isJsonNull()) {
            SyncClientImpl.a(SyncClientImpl.this).a(false);
          }
          for (;;)
          {
            SyncClientImpl.a(SyncClientImpl.this, "Marketing preferences retrieved from sync server", paramRequestListener);
            return;
            SyncClientImpl.a(SyncClientImpl.this).a(localJsonElement.getAsBoolean());
          }
        }
        
        public final void a(Map<String, List<String>> paramAnonymousMap)
        {
          SyncClientImpl.a(SyncClientImpl.this, RequestListener.SyncError.CLIENT, "No marketing preferences were returned from server", paramRequestListener);
        }
      });
      this.e.execute(localRunnable);
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client is not authorized to perform function", paramRequestListener);
  }
  
  public void resetClient()
  {
    this.c.f();
    this.b.f();
    this.i.a();
    this.h.a();
    this.j.a();
  }
  
  public void shutDown()
  {
    this.e.shutdownNow();
  }
  
  public void syncNow(final List<String> paramList, boolean paramBoolean, SyncListener paramSyncListener)
  {
    if (this.b.h())
    {
      if (!this.k.a(paramSyncListener))
      {
        a(RequestListener.SyncError.SYNC_NOW, "A sync is already in progress", paramSyncListener);
        return;
      }
      pullData(paramBoolean, new RequestListener()
      {
        public final void onError(final RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
        {
          Iterator localIterator = SyncClientImpl.e(SyncClientImpl.this).c().iterator();
          while (localIterator.hasNext()) {
            ((SyncListener)localIterator.next()).onPullError(paramAnonymousSyncError, paramAnonymousString);
          }
          SyncClientImpl.this.pushLM(paramList, new RequestListener()
          {
            public final void onError(RequestListener.SyncError paramAnonymous2SyncError, String paramAnonymous2String)
            {
              List localList = SyncClientImpl.e(SyncClientImpl.this).c();
              SyncClientImpl.e(SyncClientImpl.this).a();
              if (paramAnonymous2SyncError == RequestListener.SyncError.UNAUTHORIZED) {}
              for (RequestListener.SyncError localSyncError = RequestListener.SyncError.UNAUTHORIZED;; localSyncError = RequestListener.SyncError.SYNC_NOW)
              {
                Iterator localIterator = localList.iterator();
                while (localIterator.hasNext())
                {
                  RequestListener localRequestListener = (RequestListener)localIterator.next();
                  ((SyncListener)localRequestListener).onPushError(paramAnonymous2SyncError, paramAnonymous2String);
                  SyncClientImpl.a(SyncClientImpl.this, localSyncError, "Sync request has failed", localRequestListener);
                }
              }
            }
            
            public final void onSuccess(Map<String, String> paramAnonymous2Map)
            {
              List localList = SyncClientImpl.e(SyncClientImpl.this).c();
              SyncClientImpl.e(SyncClientImpl.this).a();
              if (paramAnonymousSyncError == RequestListener.SyncError.UNAUTHORIZED) {}
              for (RequestListener.SyncError localSyncError = RequestListener.SyncError.UNAUTHORIZED;; localSyncError = RequestListener.SyncError.SYNC_NOW)
              {
                Iterator localIterator = localList.iterator();
                while (localIterator.hasNext())
                {
                  RequestListener localRequestListener = (RequestListener)localIterator.next();
                  ((SyncListener)localRequestListener).onPushSuccess(paramAnonymous2Map);
                  SyncClientImpl.a(SyncClientImpl.this, localSyncError, "Pulling failed during sync", localRequestListener);
                }
              }
            }
          });
        }
        
        public final void onSuccess(Map<String, String> paramAnonymousMap)
        {
          Iterator localIterator = SyncClientImpl.e(SyncClientImpl.this).c().iterator();
          while (localIterator.hasNext()) {
            ((SyncListener)localIterator.next()).onPullSuccess(paramAnonymousMap);
          }
          SyncClientImpl.this.pushLM(paramList, new RequestListener()
          {
            public final void onError(RequestListener.SyncError paramAnonymous2SyncError, String paramAnonymous2String)
            {
              List localList = SyncClientImpl.e(SyncClientImpl.this).c();
              SyncClientImpl.e(SyncClientImpl.this).a();
              if (paramAnonymous2SyncError == RequestListener.SyncError.UNAUTHORIZED) {}
              for (RequestListener.SyncError localSyncError = RequestListener.SyncError.UNAUTHORIZED;; localSyncError = RequestListener.SyncError.SYNC_NOW)
              {
                Iterator localIterator = localList.iterator();
                while (localIterator.hasNext())
                {
                  RequestListener localRequestListener = (RequestListener)localIterator.next();
                  ((SyncListener)localRequestListener).onPushError(paramAnonymous2SyncError, paramAnonymous2String);
                  SyncClientImpl.a(SyncClientImpl.this, localSyncError, "Pushing failed during sync", localRequestListener);
                }
              }
            }
            
            public final void onSuccess(Map<String, String> paramAnonymous2Map)
            {
              List localList = SyncClientImpl.e(SyncClientImpl.this).c();
              SyncClientImpl.e(SyncClientImpl.this).a();
              Iterator localIterator = localList.iterator();
              while (localIterator.hasNext())
              {
                RequestListener localRequestListener = (RequestListener)localIterator.next();
                ((SyncListener)localRequestListener).onPushSuccess(paramAnonymous2Map);
                SyncClientImpl.a(SyncClientImpl.this, "Sync completed successfully", localRequestListener);
              }
            }
          });
        }
      });
      return;
    }
    a(RequestListener.SyncError.UNAUTHORIZED, "Client not authorized for syncing data", paramSyncListener);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.SyncClientImpl
 * JD-Core Version:    0.7.0.1
 */