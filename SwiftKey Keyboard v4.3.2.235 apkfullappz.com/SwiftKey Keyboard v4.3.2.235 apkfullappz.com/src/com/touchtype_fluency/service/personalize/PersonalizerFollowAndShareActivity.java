package com.touchtype_fluency.service.personalize;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.Request.Callback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.personalize.auth.AuthenticationUtil;
import com.touchtype_fluency.service.personalize.auth.FacebookTokenRetriever;
import com.touchtype_fluency.service.personalize.auth.OAuthAuthenticator;
import com.touchtype_fluency.service.personalize.auth.OAuthAuthenticatorFactory;
import com.touchtype_fluency.service.personalize.auth.TokenRetriever.TokenRetrieverListener;
import java.io.IOException;
import java.net.URLDecoder;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

public class PersonalizerFollowAndShareActivity
  extends Activity
{
  public static final String AUTH_PARAMS = "authParams";
  private static final String FACEBOOK_SHARE_URL = "me/feed";
  private static final String OAUTH_TOKEN = "oauth_token";
  public static final String PERSONALIZER_KEY = "personalizerKey";
  public static final String SERVICE_NAME = "serviceName";
  private static final String SWIFTKEY_TWITTER_ID = "113034662";
  private static final String TAG = PersonalizerFollowAndShareActivity.class.getSimpleName();
  private static final String TOKEN_SECRET = "token_secret";
  private static final String TWITTER_FOLLOW_URL = "https://api.twitter.com/1.1/friendships/create.json?user_id=";
  private String mAuthenticationParams;
  private AlertDialog mFollowShareDialog;
  private String mPersonalizerKey;
  private ProgressBar mProgressBar;
  private ServiceConfiguration mService;
  private SharedPreferences mSharedPreferences;
  
  private void facebookShareAPICall(String paramString)
  {
    this.mProgressBar.setVisibility(0);
    Session localSession = Session.getActiveSession();
    if ((localSession != null) && (localSession.isOpened()))
    {
      Bundle localBundle = new Bundle();
      localBundle.putString("message", "I've just personalized my keyboard using SwiftKey for Android - try it free at http://www.swiftkey.net");
      localBundle.putString("link", "http://www.swiftkey.net");
      localBundle.putString("description", "Personalize SwiftKey with your Facebook messages to improve the prediction experience.");
      localBundle.putString("icon", "http://s3-eu-west-1.amazonaws.com/public-resources/SK4+icon_180px.png");
      localBundle.putString("picture", "http://s3-eu-west-1.amazonaws.com/public-resources/SK4+icon_180px.png");
      Request.Callback local5 = new Request.Callback()
      {
        public void onCompleted(Response paramAnonymousResponse)
        {
          PersonalizerFollowAndShareActivity.this.storePreference();
          PersonalizerFollowAndShareActivity.this.mProgressBar.setVisibility(8);
          PersonalizerFollowAndShareActivity.this.finish();
        }
      };
      new RequestAsyncTask(new Request[] { new Request(localSession, "me/feed", localBundle, HttpMethod.POST, local5) }).execute(new Void[0]);
      return;
    }
    finish();
  }
  
  private void showFollowShareDialog()
  {
    boolean bool1 = this.mSharedPreferences.getBoolean(this.mPersonalizerKey, false);
    String str;
    if (this.mService.equals(ServiceConfiguration.FACEBOOK)) {
      str = getString(2131297129);
    }
    while (!bool1)
    {
      this.mFollowShareDialog = new AlertDialog.Builder(this).setTitle(getString(2131297127)).setMessage(str).setNegativeButton(getString(2131297130), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          PersonalizerFollowAndShareActivity.this.mFollowShareDialog.dismiss();
          PersonalizerFollowAndShareActivity.this.finish();
        }
      }).setPositiveButton(getString(2131297131), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          PersonalizerFollowAndShareActivity.this.mFollowShareDialog.dismiss();
          PersonalizerFollowAndShareActivity.this.startFollowShareAction();
        }
      }).setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          PersonalizerFollowAndShareActivity.this.mFollowShareDialog.dismiss();
          PersonalizerFollowAndShareActivity.this.finish();
        }
      }).create();
      this.mFollowShareDialog.show();
      return;
      boolean bool2 = this.mService.equals(ServiceConfiguration.TWITTER);
      str = null;
      if (bool2) {
        str = getString(2131297128);
      }
    }
    finish();
  }
  
  private void startFollowShareAction()
  {
    switch (6.$SwitchMap$com$touchtype_fluency$service$personalize$ServiceConfiguration[this.mService.ordinal()])
    {
    default: 
      finish();
      return;
    case 1: 
      new FacebookTokenRetriever(this, new PersonalizerTokenRetrieverListener(null), true).startTokenRetrieving();
      return;
    }
    twitterFollowAPICall();
  }
  
  private void storePreference()
  {
    SharedPreferences.Editor localEditor = this.mSharedPreferences.edit();
    localEditor.putBoolean(this.mPersonalizerKey, true);
    localEditor.commit();
  }
  
  private void twitterFollowAPICall()
  {
    this.mProgressBar.setVisibility(0);
    new AsyncTask()
    {
      protected String doInBackground(Void... paramAnonymousVarArgs)
      {
        try
        {
          String str1 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(PersonalizerFollowAndShareActivity.this.mAuthenticationParams, "oauth_token"), "UTF-8");
          String str2 = URLDecoder.decode(AuthenticationUtil.extractParameterValue(PersonalizerFollowAndShareActivity.this.mAuthenticationParams, "token_secret"), "UTF-8");
          OAuthAuthenticator localOAuthAuthenticator = OAuthAuthenticatorFactory.createOAuthAuthenticator(PersonalizerFollowAndShareActivity.this.mService.getName());
          HttpPost localHttpPost = new HttpPost("https://api.twitter.com/1.1/friendships/create.json?user_id=113034662");
          OAuthConsumer localOAuthConsumer = localOAuthAuthenticator.getSigner();
          localOAuthConsumer.setTokenWithSecret(str1, str2);
          localOAuthConsumer.sign(localHttpPost);
          if (new DefaultHttpClient().execute(localHttpPost).getStatusLine().getStatusCode() == 200) {
            PersonalizerFollowAndShareActivity.this.storePreference();
          }
          return null;
        }
        catch (ClientProtocolException localClientProtocolException)
        {
          for (;;)
          {
            LogUtil.e(PersonalizerFollowAndShareActivity.TAG, localClientProtocolException.getMessage(), localClientProtocolException);
          }
        }
        catch (IOException localIOException)
        {
          for (;;)
          {
            LogUtil.e(PersonalizerFollowAndShareActivity.TAG, localIOException.getMessage(), localIOException);
          }
        }
        catch (OAuthMessageSignerException localOAuthMessageSignerException)
        {
          for (;;)
          {
            LogUtil.e(PersonalizerFollowAndShareActivity.TAG, localOAuthMessageSignerException.getMessage(), localOAuthMessageSignerException);
          }
        }
        catch (OAuthExpectationFailedException localOAuthExpectationFailedException)
        {
          for (;;)
          {
            LogUtil.e(PersonalizerFollowAndShareActivity.TAG, localOAuthExpectationFailedException.getMessage(), localOAuthExpectationFailedException);
          }
        }
        catch (OAuthCommunicationException localOAuthCommunicationException)
        {
          for (;;)
          {
            LogUtil.e(PersonalizerFollowAndShareActivity.TAG, localOAuthCommunicationException.getMessage(), localOAuthCommunicationException);
          }
        }
      }
      
      protected void onPostExecute(String paramAnonymousString)
      {
        PersonalizerFollowAndShareActivity.this.mProgressBar.setVisibility(8);
        PersonalizerFollowAndShareActivity.this.finish();
      }
    }.execute(new Void[0]);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 64206)
    {
      Session localSession = Session.getActiveSession();
      if (localSession != null) {
        localSession.onActivityResult(this, paramInt1, paramInt2, paramIntent);
      }
      return;
    }
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Intent localIntent = getIntent();
    this.mSharedPreferences = getSharedPreferences("FollowAndLike", 0);
    String str = localIntent.getAction();
    if (str.equals(getString(2131297147))) {}
    for (this.mService = ServiceConfiguration.TWITTER;; this.mService = ServiceConfiguration.FACEBOOK)
    {
      this.mAuthenticationParams = localIntent.getStringExtra("authParams");
      this.mPersonalizerKey = localIntent.getStringExtra("personalizerKey");
      setContentView(2130903077);
      this.mProgressBar = ((ProgressBar)findViewById(2131230839));
      this.mProgressBar.setVisibility(4);
      return;
      if (!str.equals(getString(2131297144))) {
        break;
      }
    }
    throw new IllegalStateException("Unrecognized action for PersonalizerFollowAndShareActivity");
  }
  
  protected void onStart()
  {
    super.onStart();
    if ((this.mFollowShareDialog == null) || (!this.mFollowShareDialog.isShowing())) {
      showFollowShareDialog();
    }
  }
  
  private final class PersonalizerTokenRetrieverListener
    implements TokenRetriever.TokenRetrieverListener
  {
    private PersonalizerTokenRetrieverListener() {}
    
    public void onAuthenticationRequired() {}
    
    public void onCancel()
    {
      PersonalizerFollowAndShareActivity.this.finish();
    }
    
    public void onError(String paramString)
    {
      PersonalizerFollowAndShareActivity.this.finish();
    }
    
    public void onTokenRetrieved(String paramString1, String paramString2)
    {
      PersonalizerFollowAndShareActivity.this.facebookShareAPICall(paramString1);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PersonalizerFollowAndShareActivity
 * JD-Core Version:    0.7.0.1
 */