package com.touchtype.sync.client;

public final class CommonUtilities
{
  public static final String TAG = "com.touchtype.sync.client";
  
  public static enum AuthTokenType
  {
    private final String a;
    
    static
    {
      AuthTokenType[] arrayOfAuthTokenType = new AuthTokenType[2];
      arrayOfAuthTokenType[0] = ACCESS_TOKEN;
      arrayOfAuthTokenType[1] = ID_TOKEN;
      b = arrayOfAuthTokenType;
    }
    
    private AuthTokenType(String paramString)
    {
      this.a = paramString;
    }
    
    public final String toString()
    {
      return this.a;
    }
  }
  
  public static enum CredentialType
  {
    private final String a;
    
    static
    {
      EMAIL = new CredentialType("EMAIL", 2, "Email");
      CredentialType[] arrayOfCredentialType = new CredentialType[3];
      arrayOfCredentialType[0] = FACEBOOK;
      arrayOfCredentialType[1] = GOOGLE;
      arrayOfCredentialType[2] = EMAIL;
      b = arrayOfCredentialType;
    }
    
    private CredentialType(String paramString)
    {
      this.a = paramString;
    }
    
    public final String toString()
    {
      return this.a;
    }
  }
  
  public static enum Platform
  {
    private final String a;
    
    static
    {
      Platform[] arrayOfPlatform = new Platform[1];
      arrayOfPlatform[0] = ANDROID;
      b = arrayOfPlatform;
    }
    
    private Platform(String paramString)
    {
      this.a = paramString;
    }
    
    public final String toString()
    {
      return this.a;
    }
  }
  
  public static enum SyncAuthenticationState
  {
    static
    {
      INITIATED = new SyncAuthenticationState("INITIATED", 1);
      CONFIRMED = new SyncAuthenticationState("CONFIRMED", 2);
      SyncAuthenticationState[] arrayOfSyncAuthenticationState = new SyncAuthenticationState[3];
      arrayOfSyncAuthenticationState[0] = UNAUTHENTICATED;
      arrayOfSyncAuthenticationState[1] = INITIATED;
      arrayOfSyncAuthenticationState[2] = CONFIRMED;
      a = arrayOfSyncAuthenticationState;
    }
    
    private SyncAuthenticationState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.CommonUtilities
 * JD-Core Version:    0.7.0.1
 */