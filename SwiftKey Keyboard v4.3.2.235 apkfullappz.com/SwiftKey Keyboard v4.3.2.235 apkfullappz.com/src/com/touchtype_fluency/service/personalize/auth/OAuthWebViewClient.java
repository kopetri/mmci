package com.touchtype_fluency.service.personalize.auth;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class OAuthWebViewClient
  extends WebViewClient
{
  private OAuthAuthenticationClient mAuthClient;
  
  public OAuthWebViewClient(OAuthAuthenticationClient paramOAuthAuthenticationClient)
  {
    this.mAuthClient = paramOAuthAuthenticationClient;
  }
  
  protected OAuthAuthenticationClient getAuthClient()
  {
    return this.mAuthClient;
  }
  
  public void startAuthentication(WebView paramWebView)
  {
    this.mAuthClient.onStartOAuthProcess(paramWebView);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.OAuthWebViewClient
 * JD-Core Version:    0.7.0.1
 */