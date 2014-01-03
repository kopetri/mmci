package com.touchtype_fluency.service.personalize.auth;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class AuthenticationWebView
  extends WebView
{
  private OAuthWebViewClient mWebViewClient;
  
  public AuthenticationWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public void onStartAuthentication()
  {
    if (this.mWebViewClient != null)
    {
      this.mWebViewClient.startAuthentication(this);
      return;
    }
    throw new IllegalStateException("Authentication Web View can't start authentication without a webViewClient");
  }
  
  public void setWebViewClient(WebViewClient paramWebViewClient)
  {
    super.setWebViewClient(paramWebViewClient);
    if ((paramWebViewClient instanceof OAuthWebViewClient))
    {
      this.mWebViewClient = ((OAuthWebViewClient)paramWebViewClient);
      return;
    }
    throw new IllegalStateException("Authentication Web View needs an OAuthWebViewClient instance to authenticate");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.AuthenticationWebView
 * JD-Core Version:    0.7.0.1
 */