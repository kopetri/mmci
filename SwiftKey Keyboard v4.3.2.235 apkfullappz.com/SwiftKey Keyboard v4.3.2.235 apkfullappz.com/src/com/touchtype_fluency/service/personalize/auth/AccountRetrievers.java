package com.touchtype_fluency.service.personalize.auth;

import android.os.AsyncTask;
import android.util.Pair;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.OAuthProvider;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import oauth.signpost.http.HttpParameters;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

public enum AccountRetrievers
{
  private final String name;
  
  static
  {
    AccountRetrievers[] arrayOfAccountRetrievers = new AccountRetrievers[4];
    arrayOfAccountRetrievers[0] = FACEBOOK;
    arrayOfAccountRetrievers[1] = GMAIL;
    arrayOfAccountRetrievers[2] = TWITTER;
    arrayOfAccountRetrievers[3] = YAHOO;
    $VALUES = arrayOfAccountRetrievers;
  }
  
  private AccountRetrievers(String paramString)
  {
    this.name = paramString;
  }
  
  public static AccountRetriever getRetrieverByName(String paramString1, String paramString2, OAuthAuthenticator paramOAuthAuthenticator)
  {
    for (AccountRetrievers localAccountRetrievers : ) {
      if (localAccountRetrievers.getName().equals(paramString1)) {
        return localAccountRetrievers.createAccountRetriever(paramString2, paramOAuthAuthenticator);
      }
    }
    return null;
  }
  
  abstract AccountRetriever createAccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator);
  
  public String getName()
  {
    return this.name;
  }
  
  class FacebookAccountRetriever
    extends AccountRetriever
  {
    private static final String ACCOUNT_NAME = "name";
    private static final String FACEBOOK_ME_URL = "https://graph.facebook.com/me?fields=name";
    private final String TAG = FacebookAccountRetriever.class.getSimpleName();
    
    public FacebookAccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
    {
      super(paramOAuthAuthenticator);
    }
    
    public void retrieveAccount(final AccountRetriever.RetrieverCallback paramRetrieverCallback)
    {
      new AsyncTask()
      {
        protected String doInBackground(String... paramAnonymousVarArgs)
        {
          try
          {
            HttpGet localHttpGet = new HttpGet("https://graph.facebook.com/me?fields=name&" + AccountRetrievers.FacebookAccountRetriever.this.getUserAuthParams());
            String str = new JSONObject(EntityUtils.toString(new DefaultHttpClient().execute(localHttpGet).getEntity())).getString("name");
            return str;
          }
          catch (ClientProtocolException localClientProtocolException)
          {
            LogUtil.e(AccountRetrievers.FacebookAccountRetriever.this.TAG, localClientProtocolException.getMessage(), localClientProtocolException);
            return null;
          }
          catch (IOException localIOException)
          {
            LogUtil.e(AccountRetrievers.FacebookAccountRetriever.this.TAG, localIOException.getMessage(), localIOException);
            return null;
          }
          catch (JSONException localJSONException)
          {
            LogUtil.e(AccountRetrievers.FacebookAccountRetriever.this.TAG, localJSONException.getMessage(), localJSONException);
          }
          return null;
        }
        
        protected void onPostExecute(String paramAnonymousString)
        {
          if (paramAnonymousString != null)
          {
            paramRetrieverCallback.onAccountRetrieved(paramAnonymousString, AccountRetrievers.FacebookAccountRetriever.this.getUserAuthParams(), null);
            return;
          }
          paramRetrieverCallback.onError();
        }
      }.execute(new String[0]);
    }
  }
  
  class GoogleAccountRetriever
    extends AccountRetriever
  {
    private static final String ACCOUNT_ID = "email";
    private static final String GOOGLE_PROFILE_URL = "https://www.googleapis.com/oauth2/v1/userinfo?alt=json";
    private final String TAG = AccountRetrievers.FacebookAccountRetriever.class.getSimpleName();
    
    public GoogleAccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
    {
      super(paramOAuthAuthenticator);
    }
    
    public void retrieveAccount(final AccountRetriever.RetrieverCallback paramRetrieverCallback)
    {
      new AsyncTask()
      {
        protected String doInBackground(Void... paramAnonymousVarArgs)
        {
          try
          {
            HttpGet localHttpGet = new HttpGet("https://www.googleapis.com/oauth2/v1/userinfo?alt=json&" + AccountRetrievers.GoogleAccountRetriever.this.getUserAuthParams());
            String str = new JSONObject(EntityUtils.toString(new DefaultHttpClient().execute(localHttpGet).getEntity())).getString("email");
            return str;
          }
          catch (ClientProtocolException localClientProtocolException)
          {
            LogUtil.e(AccountRetrievers.GoogleAccountRetriever.this.TAG, localClientProtocolException.getMessage(), localClientProtocolException);
            return null;
          }
          catch (IOException localIOException)
          {
            LogUtil.e(AccountRetrievers.GoogleAccountRetriever.this.TAG, localIOException.getMessage(), localIOException);
            return null;
          }
          catch (JSONException localJSONException)
          {
            LogUtil.e(AccountRetrievers.GoogleAccountRetriever.this.TAG, localJSONException.getMessage(), localJSONException);
          }
          return null;
        }
        
        protected void onPostExecute(String paramAnonymousString)
        {
          if (paramAnonymousString != null)
          {
            paramRetrieverCallback.onAccountRetrieved(paramAnonymousString, AccountRetrievers.GoogleAccountRetriever.this.getUserAuthParams(), null);
            return;
          }
          paramRetrieverCallback.onError();
        }
      }.execute(new Void[0]);
    }
  }
  
  class TwitterAccountRetriever
    extends AccountRetriever
  {
    private static final String SCREEN_NAME = "screen_name";
    
    public TwitterAccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
    {
      super(paramOAuthAuthenticator);
    }
    
    public void retrieveAccount(AccountRetriever.RetrieverCallback paramRetrieverCallback)
    {
      String str = getUserAuthenticator().getProvider().getResponseParameters().getFirst("screen_name");
      if (str != null)
      {
        paramRetrieverCallback.onAccountRetrieved("@" + str, getUserAuthParams(), null);
        return;
      }
      paramRetrieverCallback.onError();
    }
  }
  
  class YahooAccountRetriever
    extends AccountRetriever
  {
    private static final String GUID = "xoauth_yahoo_guid";
    private static final String NICKNAME = "nickname";
    private static final String PROFILE = "profile";
    private static final String SESSION = "oauth_session_handle";
    private final String TAG = YahooAccountRetriever.class.getSimpleName();
    
    public YahooAccountRetriever(String paramString, OAuthAuthenticator paramOAuthAuthenticator)
    {
      super(paramOAuthAuthenticator);
    }
    
    public void retrieveAccount(final AccountRetriever.RetrieverCallback paramRetrieverCallback)
    {
      new AsyncTask()
      {
        protected Pair<String, String> doInBackground(String... paramAnonymousVarArgs)
        {
          String str1 = null;
          for (;;)
          {
            try
            {
              OAuthAuthenticator localOAuthAuthenticator = AccountRetrievers.YahooAccountRetriever.this.getUserAuthenticator();
              OAuthProvider localOAuthProvider = localOAuthAuthenticator.getProvider();
              String str3 = localOAuthProvider.getResponseParameters().getFirst("xoauth_yahoo_guid");
              str1 = localOAuthProvider.getResponseParameters().getFirst("oauth_session_handle");
              HttpGet localHttpGet = new HttpGet("http://social.yahooapis.com/v1/user/" + str3 + "/profile?format=json");
              localOAuthAuthenticator.getSigner().sign(localHttpGet);
              JSONObject localJSONObject = new JSONObject(EntityUtils.toString(new DefaultHttpClient().execute(localHttpGet).getEntity())).getJSONObject("profile");
              if (localJSONObject == null) {
                continue;
              }
              String str4 = localJSONObject.getString("nickname");
              str2 = str4;
            }
            catch (ClientProtocolException localClientProtocolException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localClientProtocolException.getMessage(), localClientProtocolException);
              str2 = null;
              continue;
            }
            catch (IOException localIOException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localIOException.getMessage(), localIOException);
              str2 = null;
              continue;
            }
            catch (OAuthMessageSignerException localOAuthMessageSignerException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localOAuthMessageSignerException.getMessage(), localOAuthMessageSignerException);
              str2 = null;
              continue;
            }
            catch (OAuthExpectationFailedException localOAuthExpectationFailedException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localOAuthExpectationFailedException.getMessage(), localOAuthExpectationFailedException);
              str2 = null;
              continue;
            }
            catch (OAuthCommunicationException localOAuthCommunicationException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localOAuthCommunicationException.getMessage(), localOAuthCommunicationException);
              str2 = null;
              continue;
            }
            catch (JSONException localJSONException)
            {
              LogUtil.e(AccountRetrievers.YahooAccountRetriever.this.TAG, localJSONException.getMessage(), localJSONException);
              String str2 = null;
              continue;
            }
            return new Pair(str2, str1);
            str2 = null;
          }
        }
        
        protected void onPostExecute(Pair<String, String> paramAnonymousPair)
        {
          String str1 = (String)paramAnonymousPair.first;
          String str2 = (String)paramAnonymousPair.second;
          if (str1 != null)
          {
            paramRetrieverCallback.onAccountRetrieved(str1, AccountRetrievers.YahooAccountRetriever.this.getUserAuthParams(), str2);
            return;
          }
          paramRetrieverCallback.onError();
        }
      }.execute(new String[0]);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.AccountRetrievers
 * JD-Core Version:    0.7.0.1
 */