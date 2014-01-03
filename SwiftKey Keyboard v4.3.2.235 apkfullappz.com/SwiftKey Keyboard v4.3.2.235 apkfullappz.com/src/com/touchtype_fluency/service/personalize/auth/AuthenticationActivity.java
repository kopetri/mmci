package com.touchtype_fluency.service.personalize.auth;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.widget.ProgressBar;
import com.google.common.base.Preconditions;
import com.touchtype.util.LogUtil;

public class AuthenticationActivity
  extends Activity
{
  public static final int API_FAIL = 1;
  public static final String CALLER_SERVICE = "service";
  private static final String TAG = AuthenticationActivity.class.getSimpleName();
  public static final String TITLE = "title";
  private AccountRetriever.RetrieverCallback accountRetrieverCallback = new AccountRetriever.RetrieverCallback()
  {
    public void onAccountRetrieved(String paramAnonymousString1, String paramAnonymousString2, String paramAnonymousString3)
    {
      AuthenticationActivity.this.authenticationSuccess(paramAnonymousString1, paramAnonymousString2, paramAnonymousString3);
    }
    
    public void onError()
    {
      AuthenticationActivity.this.authenticationFailed();
    }
  };
  private AuthenticationClientListener listener = new AuthenticationClientListener()
  {
    public void authenticationFinished(boolean paramAnonymousBoolean, String paramAnonymousString, OAuthAuthenticator paramAnonymousOAuthAuthenticator)
    {
      if (paramAnonymousBoolean)
      {
        AuthenticationActivity.this.retrieveAccountId(paramAnonymousString, paramAnonymousOAuthAuthenticator);
        return;
      }
      AuthenticationActivity.this.authenticationFailed();
    }
  };
  private ProgressBar mProgressBar;
  private String mServiceName;
  private AuthenticationWebView webView;
  
  private void authenticationFailed()
  {
    setResult(2);
    finish();
  }
  
  private void authenticationSuccess(String paramString1, String paramString2, String paramString3)
  {
    Intent localIntent = new Intent();
    localIntent.putExtra("account_name", paramString1);
    localIntent.putExtra("params", paramString2);
    localIntent.putExtra("session", paramString3);
    setResult(-1, localIntent);
    finish();
  }
  
  private void retrieveAccountId(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
  {
    AccountRetriever localAccountRetriever = AccountRetrievers.getRetrieverByName(this.mServiceName, paramString, paramOAuthAuthenticator);
    Preconditions.checkNotNull(localAccountRetriever);
    localAccountRetriever.retrieveAccount(this.accountRetrieverCallback);
  }
  
  private void start(Intent paramIntent)
  {
    this.mServiceName = paramIntent.getStringExtra("service");
    if (this.mServiceName != null) {
      try
      {
        this.webView = WebViewConfig.applyConfiguration(this.webView, this.mServiceName);
        Bundle localBundle = paramIntent.getExtras();
        OAuthWebViewClient localOAuthWebViewClient = OAuthWebClients.createClientByService(this.mServiceName, this.mProgressBar, this.listener, localBundle);
        this.webView.setWebViewClient(localOAuthWebViewClient);
        this.webView.getSettings().setUseWideViewPort(true);
        this.webView.onStartAuthentication();
        return;
      }
      catch (AuthenticationException localAuthenticationException)
      {
        LogUtil.w(TAG, localAuthenticationException.getMessage(), localAuthenticationException);
        authenticationFailed();
        return;
      }
    }
    LogUtil.w(TAG, "Caller source not found in authentication activity");
    authenticationFailed();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903124);
    getWindow().setLayout(-2, -2);
    Intent localIntent = getIntent();
    String str = localIntent.getStringExtra("title");
    if (str != null) {}
    for (;;)
    {
      setTitle(str);
      this.webView = ((AuthenticationWebView)findViewById(2131230944));
      this.mProgressBar = ((ProgressBar)findViewById(2131230839));
      start(localIntent);
      return;
      str = getString(2131296938);
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    try
    {
      CookieManager.getInstance().removeAllCookie();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      LogUtil.w(TAG, "CookieManager not initialised. onDestroy called before onCreate?", localIllegalStateException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.AuthenticationActivity
 * JD-Core Version:    0.7.0.1
 */