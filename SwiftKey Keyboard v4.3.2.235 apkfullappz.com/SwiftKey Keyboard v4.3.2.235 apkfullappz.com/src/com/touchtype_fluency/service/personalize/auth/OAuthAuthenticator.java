package com.touchtype_fluency.service.personalize.auth;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;
import oauth.signpost.commonshttp.CommonsHttpOAuthProvider;
import oauth.signpost.exception.OAuthException;

public class OAuthAuthenticator
{
  private static final String TAG = OAuthAuthenticator.class.getSimpleName();
  private final String mCallbackUrl;
  private final OAuthConsumer mConsumer;
  private final OAuthProvider mProvider;
  
  public OAuthAuthenticator(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6)
  {
    this.mConsumer = new CommonsHttpOAuthConsumer(paramString1, paramString2);
    this.mProvider = new CommonsHttpOAuthProvider(paramString3, paramString4, paramString5);
    this.mCallbackUrl = paramString6;
  }
  
  String getAccessToken(String paramString)
    throws OAuthException
  {
    this.mProvider.retrieveAccessToken(this.mConsumer, paramString);
    return this.mConsumer.getToken();
  }
  
  String getAuthUrl()
    throws OAuthException
  {
    return this.mProvider.retrieveRequestToken(this.mConsumer, this.mCallbackUrl);
  }
  
  String getCallback()
  {
    return this.mCallbackUrl;
  }
  
  public OAuthProvider getProvider()
  {
    return this.mProvider;
  }
  
  public OAuthConsumer getSigner()
  {
    return this.mConsumer;
  }
  
  String getTokenSecret()
    throws OAuthException
  {
    return this.mConsumer.getTokenSecret();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.OAuthAuthenticator
 * JD-Core Version:    0.7.0.1
 */