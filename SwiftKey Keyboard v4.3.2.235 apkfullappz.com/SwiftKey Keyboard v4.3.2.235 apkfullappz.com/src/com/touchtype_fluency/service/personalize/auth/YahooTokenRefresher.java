package com.touchtype_fluency.service.personalize.auth;

import android.os.AsyncTask;
import android.util.Pair;
import com.google.common.base.Preconditions;
import com.touchtype.util.LogUtil;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import oauth.signpost.OAuthConsumer;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class YahooTokenRefresher
{
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String OAUTH_TOKEN = "oauth_token";
  private static final String OAUTH_TOKEN_SECRET = "oauth_token_secret";
  private static final String SESSION_HANDLE = "oauth_session_handle";
  private static final String TOKEN_SECRET = "token_secret";
  private static final String YAHOO_REFRESH_URL = "https://api.login.yahoo.com/oauth/v2/get_token";
  private static final String YAHOO_SERVICE = "Yahoo";
  private final String TAG = YahooTokenRefresher.class.getSimpleName();
  private final String mAccessParams;
  private final ParamsRefresherCallback mCallback;
  private final String mSessionHandler;
  
  public YahooTokenRefresher(String paramString1, String paramString2, ParamsRefresherCallback paramParamsRefresherCallback)
  {
    this.mAccessParams = paramString1;
    Preconditions.checkNotNull(paramString1);
    this.mSessionHandler = paramString2;
    Preconditions.checkNotNull(paramString2);
    this.mCallback = paramParamsRefresherCallback;
  }
  
  private String buildAuthorizationHeader(String paramString1, String paramString2, String paramString3, String paramString4)
    throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException
  {
    String str1 = new BigInteger(130, new SecureRandom()).toString(32);
    long l = System.currentTimeMillis() / 1000L;
    String str2 = buildOauthSignature(paramString1, paramString2, paramString3, paramString4, str1, l);
    return "OAuth oauth_consumer_key=\"" + paramString1 + "\",oauth_nonce=\"" + str1 + "\",oauth_session_handle=\"" + URLEncoder.encode(this.mSessionHandler, "UTF-8") + "\",oauth_signature=\"" + URLEncoder.encode(str2, "UTF-8") + "\",oauth_signature_method=\"HMAC-SHA1\",oauth_timestamp=\"" + l + "\",oauth_token=\"" + URLEncoder.encode(paramString3, "UTF-8") + "\",oauth_version=\"1.0\"";
  }
  
  private String buildOauthSignature(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, long paramLong)
    throws UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException
  {
    String str = "oauth_consumer_key=" + paramString1 + "&oauth_nonce=" + paramString5 + "&oauth_session_handle=" + this.mSessionHandler + "&oauth_signature_method=HMAC-SHA1&oauth_timestamp=" + paramLong + "&oauth_token=" + URLEncoder.encode(paramString3, "UTF-8") + "&oauth_version=1.0";
    return AuthenticationUtil.computeSHA1Signature("POST&" + URLEncoder.encode("https://api.login.yahoo.com/oauth/v2/get_token", "UTF-8") + "&" + URLEncoder.encode(str, "UTF-8"), paramString2 + "&" + paramString4);
  }
  
  public void refreshToken()
  {
    new AsyncTask()
    {
      protected Pair<String, String> doInBackground(Void... paramAnonymousVarArgs)
      {
        for (String str1 = null;; str1 = null)
        {
          try
          {
            OAuthConsumer localOAuthConsumer = this.val$authenticator.getSigner();
            String str3 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(YahooTokenRefresher.this.mAccessParams, "oauth_token"), "UTF-8");
            String str4 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(YahooTokenRefresher.this.mAccessParams, "token_secret"), "UTF-8");
            String str5 = YahooTokenRefresher.this.buildAuthorizationHeader(localOAuthConsumer.getConsumerKey(), localOAuthConsumer.getConsumerSecret(), str3, str4);
            HttpPost localHttpPost = new HttpPost("https://api.login.yahoo.com/oauth/v2/get_token");
            localHttpPost.setHeader("Authorization", str5);
            String str6 = EntityUtils.toString(new DefaultHttpClient().execute(localHttpPost).getEntity());
            if ((!str6.contains("oauth_token")) || (!str6.contains("oauth_token_secret"))) {
              break label250;
            }
            String str7 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(str6, "oauth_token"), "UTF-8");
            String str8 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(str6, "oauth_token_secret"), "UTF-8");
            str1 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(str6, "oauth_session_handle"), "UTF-8");
            ArrayList localArrayList = new ArrayList();
            localArrayList.add(new BasicNameValuePair("oauth_token", str7));
            localArrayList.add(new BasicNameValuePair("token_secret", str8));
            String str9 = URLEncodedUtils.format(localArrayList, "UTF-8");
            str2 = str9;
          }
          catch (ClientProtocolException localClientProtocolException)
          {
            for (;;)
            {
              LogUtil.e(YahooTokenRefresher.this.TAG, localClientProtocolException.getMessage(), localClientProtocolException);
              str2 = null;
            }
          }
          catch (IOException localIOException)
          {
            for (;;)
            {
              LogUtil.e(YahooTokenRefresher.this.TAG, localIOException.getMessage(), localIOException);
              str2 = null;
            }
          }
          catch (IllegalStateException localIllegalStateException)
          {
            for (;;)
            {
              LogUtil.e(YahooTokenRefresher.this.TAG, localIllegalStateException.getMessage(), localIllegalStateException);
              str2 = null;
            }
          }
          catch (InvalidKeyException localInvalidKeyException)
          {
            for (;;)
            {
              LogUtil.e(YahooTokenRefresher.this.TAG, localInvalidKeyException.getMessage(), localInvalidKeyException);
              str2 = null;
            }
          }
          catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
          {
            for (;;)
            {
              label250:
              LogUtil.e(YahooTokenRefresher.this.TAG, localNoSuchAlgorithmException.getMessage(), localNoSuchAlgorithmException);
              String str2 = null;
            }
          }
          return new Pair(str2, str1);
          LogUtil.w(YahooTokenRefresher.this.TAG, "Yahoo session expired, need user authentication");
          str2 = null;
        }
      }
      
      protected void onPostExecute(Pair<String, String> paramAnonymousPair)
      {
        String str1 = (String)paramAnonymousPair.first;
        String str2 = (String)paramAnonymousPair.second;
        if ((str1 != null) && (str2 != null))
        {
          YahooTokenRefresher.this.mCallback.onParamsRefreshed(str1, str2);
          return;
        }
        YahooTokenRefresher.this.mCallback.onError();
      }
    }.execute(new Void[0]);
  }
  
  public static abstract interface ParamsRefresherCallback
  {
    public abstract void onError();
    
    public abstract void onParamsRefreshed(String paramString1, String paramString2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.YahooTokenRefresher
 * JD-Core Version:    0.7.0.1
 */