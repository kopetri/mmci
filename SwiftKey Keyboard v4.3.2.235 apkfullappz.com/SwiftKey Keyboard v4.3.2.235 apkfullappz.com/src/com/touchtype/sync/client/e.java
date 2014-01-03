package com.touchtype.sync.client;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.IOException;

class e
{
  private SyncStorage a;
  private AuthFields b;
  private final LoggingListener c;
  
  e(SyncStorage paramSyncStorage, LoggingListener paramLoggingListener)
  {
    this.c = paramLoggingListener;
    this.a = paramSyncStorage;
    File localFile = new File(this.a.getSyncFilesDirectory(), "sync_authentication.json");
    Gson localGson = new Gson();
    try
    {
      this.b = ((AuthFields)localGson.fromJson(Files.toString(localFile, Charsets.UTF_8), AuthFields.class));
      if ((this.b.mDeviceSecret != null) && (this.b.mAppSecret == null))
      {
        this.b.mAppSecret = this.b.mDeviceSecret;
        this.b.mDeviceSecret = null;
        j();
      }
      return;
    }
    catch (IOException localIOException)
    {
      a(this.c, "com.touchtype.sync.client", "Unable to read sync authorisation data: " + localIOException);
      this.b = new AuthFields();
    }
  }
  
  public static void a(LoggingListener paramLoggingListener, String paramString1, String paramString2)
  {
    if (paramLoggingListener != null) {
      paramLoggingListener.log(LoggingListener.Level.DEBUG, paramString1 + ": " + paramString2);
    }
  }
  
  public static void b(LoggingListener paramLoggingListener, String paramString1, String paramString2)
  {
    if (paramLoggingListener != null) {
      paramLoggingListener.log(LoggingListener.Level.WARN, paramString1 + ": " + paramString2);
    }
  }
  
  public static void c(LoggingListener paramLoggingListener, String paramString1, String paramString2)
  {
    if (paramLoggingListener != null) {
      paramLoggingListener.log(LoggingListener.Level.ERROR, paramString1 + ": " + paramString2);
    }
  }
  
  private void j()
  {
    Gson localGson = new GsonBuilder().create();
    File localFile = new File(this.a.getSyncFilesDirectory(), "sync_authentication.json");
    String str = localGson.toJson(this.b, AuthFields.class);
    try
    {
      Files.write(str.getBytes(), localFile);
      return;
    }
    catch (IOException localIOException)
    {
      c(this.c, "com.touchtype.sync.client", "Error when writing sync authorisation data: " + localIOException);
    }
  }
  
  String a()
  {
    try
    {
      String str = this.b.mDeviceId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  void a(String paramString, Credential paramCredential)
  {
    try
    {
      this.b.mStatus = CommonUtilities.SyncAuthenticationState.INITIATED;
      this.b.mConfirmationId = paramString;
      if (paramCredential != null) {
        this.b.mAuthCredential = paramCredential;
      }
      j();
      return;
    }
    finally {}
  }
  
  void a(String paramString1, String paramString2, String paramString3, Credential paramCredential)
  {
    try
    {
      this.b.mStatus = CommonUtilities.SyncAuthenticationState.CONFIRMED;
      this.b.mDeviceId = paramString1;
      this.b.mAppId = paramString2;
      this.b.mAppSecret = paramString3;
      this.b.mConfirmationId = null;
      if (paramCredential != null) {
        this.b.mAuthCredential = paramCredential;
      }
      j();
      return;
    }
    finally {}
  }
  
  void a(boolean paramBoolean)
  {
    try
    {
      this.b.mOptIn = paramBoolean;
      j();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  String b()
  {
    try
    {
      String str = this.b.mAppId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  String c()
  {
    try
    {
      String str = this.b.mAppSecret;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  String d()
  {
    try
    {
      String str = this.b.mConfirmationId;
      return str;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  CommonUtilities.SyncAuthenticationState e()
  {
    try
    {
      CommonUtilities.SyncAuthenticationState localSyncAuthenticationState = this.b.mStatus;
      return localSyncAuthenticationState;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  void f()
  {
    try
    {
      this.b.mStatus = CommonUtilities.SyncAuthenticationState.UNAUTHENTICATED;
      this.b.mDeviceId = null;
      this.b.mDeviceSecret = null;
      this.b.mAppId = null;
      this.b.mAppSecret = null;
      this.b.mConfirmationId = null;
      this.b.mAuthCredential = null;
      j();
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  /* Error */
  Credential g()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/touchtype/sync/client/e:b	Lcom/touchtype/sync/client/AuthFields;
    //   6: getfield 156	com/touchtype/sync/client/AuthFields:mAuthCredential	Lcom/touchtype/sync/client/Credential;
    //   9: ifnull +35 -> 44
    //   12: new 179	com/touchtype/sync/client/Credential
    //   15: dup
    //   16: aload_0
    //   17: getfield 57	com/touchtype/sync/client/e:b	Lcom/touchtype/sync/client/AuthFields;
    //   20: getfield 156	com/touchtype/sync/client/AuthFields:mAuthCredential	Lcom/touchtype/sync/client/Credential;
    //   23: invokevirtual 182	com/touchtype/sync/client/Credential:getIdentifier	()Ljava/lang/String;
    //   26: aload_0
    //   27: getfield 57	com/touchtype/sync/client/e:b	Lcom/touchtype/sync/client/AuthFields;
    //   30: getfield 156	com/touchtype/sync/client/AuthFields:mAuthCredential	Lcom/touchtype/sync/client/Credential;
    //   33: invokevirtual 186	com/touchtype/sync/client/Credential:getType	()Lcom/touchtype/sync/client/CommonUtilities$CredentialType;
    //   36: invokespecial 189	com/touchtype/sync/client/Credential:<init>	(Ljava/lang/String;Lcom/touchtype/sync/client/CommonUtilities$CredentialType;)V
    //   39: astore_2
    //   40: aload_0
    //   41: monitorexit
    //   42: aload_2
    //   43: areturn
    //   44: aconst_null
    //   45: astore_2
    //   46: goto -6 -> 40
    //   49: astore_1
    //   50: aload_0
    //   51: monitorexit
    //   52: aload_1
    //   53: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	54	0	this	e
    //   49	4	1	localObject	Object
    //   39	7	2	localCredential	Credential
    // Exception table:
    //   from	to	target	type
    //   2	40	49	finally
  }
  
  /* Error */
  boolean h()
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: aload_0
    //   3: getfield 57	com/touchtype/sync/client/e:b	Lcom/touchtype/sync/client/AuthFields;
    //   6: getfield 149	com/touchtype/sync/client/AuthFields:mStatus	Lcom/touchtype/sync/client/CommonUtilities$SyncAuthenticationState;
    //   9: astore_2
    //   10: getstatic 160	com/touchtype/sync/client/CommonUtilities$SyncAuthenticationState:CONFIRMED	Lcom/touchtype/sync/client/CommonUtilities$SyncAuthenticationState;
    //   13: astore_3
    //   14: aload_2
    //   15: aload_3
    //   16: if_acmpne +11 -> 27
    //   19: iconst_1
    //   20: istore 4
    //   22: aload_0
    //   23: monitorexit
    //   24: iload 4
    //   26: ireturn
    //   27: iconst_0
    //   28: istore 4
    //   30: goto -8 -> 22
    //   33: astore_1
    //   34: aload_0
    //   35: monitorexit
    //   36: aload_1
    //   37: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	38	0	this	e
    //   33	4	1	localObject	Object
    //   9	6	2	localSyncAuthenticationState1	CommonUtilities.SyncAuthenticationState
    //   13	3	3	localSyncAuthenticationState2	CommonUtilities.SyncAuthenticationState
    //   20	9	4	bool	boolean
    // Exception table:
    //   from	to	target	type
    //   2	14	33	finally
  }
  
  boolean i()
  {
    try
    {
      boolean bool = this.b.mOptIn;
      return bool;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.e
 * JD-Core Version:    0.7.0.1
 */