package com.touchtype_fluency.service.personalize.auth;

import com.google.common.base.Preconditions;

abstract class AccountRetriever
{
  private final String mAuthParams;
  private final OAuthAuthenticator mUserAuthenticator;
  
  public AccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
  {
    this.mAuthParams = paramString;
    this.mUserAuthenticator = paramOAuthAuthenticator;
  }
  
  protected String getUserAuthParams()
  {
    Preconditions.checkNotNull(this.mAuthParams);
    return this.mAuthParams;
  }
  
  protected OAuthAuthenticator getUserAuthenticator()
  {
    Preconditions.checkNotNull(this.mUserAuthenticator);
    return this.mUserAuthenticator;
  }
  
  public abstract void retrieveAccount(RetrieverCallback paramRetrieverCallback);
  
  public static abstract interface RetrieverCallback
  {
    public abstract void onAccountRetrieved(String paramString1, String paramString2, String paramString3);
    
    public abstract void onError();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.AccountRetriever
 * JD-Core Version:    0.7.0.1
 */