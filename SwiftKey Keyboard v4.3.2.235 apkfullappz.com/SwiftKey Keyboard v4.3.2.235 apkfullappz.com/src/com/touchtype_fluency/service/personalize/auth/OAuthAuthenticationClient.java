package com.touchtype_fluency.service.personalize.auth;

import android.os.AsyncTask;
import android.util.Pair;
import android.webkit.WebView;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import oauth.signpost.exception.OAuthException;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class OAuthAuthenticationClient
{
  private static final String OAUTH_TOKEN = "oauth_token";
  private static final String TOKEN_SECRET = "token_secret";
  private final String TAG = OAuthAuthenticationClient.class.getSimpleName();
  private OAuthAuthenticator mAuthenticator;
  private final AuthenticationClientListener mListener;
  
  public OAuthAuthenticationClient(AuthenticationClientListener paramAuthenticationClientListener, OAuthAuthenticator paramOAuthAuthenticator)
  {
    this.mListener = paramAuthenticationClientListener;
    this.mAuthenticator = paramOAuthAuthenticator;
  }
  
  public String getAuthCallback()
  {
    return this.mAuthenticator.getCallback();
  }
  
  public void obtainAccessToken(final String paramString)
  {
    new AsyncTask()
    {
      protected Pair<String, String> doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          Pair localPair = new Pair(OAuthAuthenticationClient.this.mAuthenticator.getAccessToken(paramString), OAuthAuthenticationClient.this.mAuthenticator.getTokenSecret());
          return localPair;
        }
        catch (OAuthException localOAuthException)
        {
          LogUtil.e(OAuthAuthenticationClient.this.TAG, localOAuthException.getMessage(), localOAuthException);
        }
        return null;
      }
      
      protected void onPostExecute(Pair<String, String> paramAnonymousPair)
      {
        if (paramAnonymousPair != null)
        {
          String str1 = (String)paramAnonymousPair.first;
          String str2 = (String)paramAnonymousPair.second;
          ArrayList localArrayList = new ArrayList();
          localArrayList.add(new BasicNameValuePair("oauth_token", str1));
          localArrayList.add(new BasicNameValuePair("token_secret", str2));
          OAuthAuthenticationClient.this.mListener.authenticationFinished(true, URLEncodedUtils.format(localArrayList, "UTF-8"), OAuthAuthenticationClient.this.mAuthenticator);
          return;
        }
        OAuthAuthenticationClient.this.mListener.authenticationFinished(false, null, null);
      }
    }.execute(new Void[0]);
  }
  
  public void onStartOAuthProcess(final WebView paramWebView)
  {
    paramWebView.requestFocus();
    paramWebView.stopLoading();
    new AsyncTask()
    {
      protected String doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          String str = OAuthAuthenticationClient.this.mAuthenticator.getAuthUrl();
          return str;
        }
        catch (OAuthException localOAuthException) {}
        return "";
      }
      
      protected void onPostExecute(String paramAnonymousString)
      {
        if (paramAnonymousString.equals(""))
        {
          OAuthAuthenticationClient.this.mListener.authenticationFinished(false, null, null);
          return;
        }
        paramWebView.loadUrl(paramAnonymousString);
      }
    }.execute(new Void[0]);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.OAuthAuthenticationClient
 * JD-Core Version:    0.7.0.1
 */