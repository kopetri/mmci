package com.facebook.widget;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.facebook.FacebookRequestError;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionDefaultAudience;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.android.R.color;
import com.facebook.android.R.dimen;
import com.facebook.android.R.drawable;
import com.facebook.android.R.id;
import com.facebook.android.R.layout;
import com.facebook.android.R.string;
import com.facebook.model.GraphUser;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class UserSettingsFragment
  extends FacebookFragment
{
  private static final String FIELDS = "fields";
  private static final String ID = "id";
  private static final String NAME = "name";
  private static final String PICTURE = "picture";
  private static final String REQUEST_FIELDS = TextUtils.join(",", new String[] { "id", "name", "picture" });
  private TextView connectedStateLabel;
  private LoginButton loginButton;
  private LoginButton.LoginButtonProperties loginButtonProperties = new LoginButton.LoginButtonProperties();
  private Session.StatusCallback sessionStatusCallback;
  private GraphUser user;
  private Session userInfoSession;
  private Drawable userProfilePic;
  private String userProfilePicID;
  
  private void fetchUserInfo()
  {
    final Session localSession = getSession();
    if ((localSession != null) && (localSession.isOpened()))
    {
      if (localSession != this.userInfoSession)
      {
        Request localRequest = Request.newMeRequest(localSession, new Request.GraphUserCallback()
        {
          public void onCompleted(GraphUser paramAnonymousGraphUser, Response paramAnonymousResponse)
          {
            if (localSession == UserSettingsFragment.this.getSession())
            {
              UserSettingsFragment.access$002(UserSettingsFragment.this, paramAnonymousGraphUser);
              UserSettingsFragment.this.updateUI();
            }
            if (paramAnonymousResponse.getError() != null) {
              UserSettingsFragment.this.loginButton.handleError(paramAnonymousResponse.getError().getException());
            }
          }
        });
        Bundle localBundle = new Bundle();
        localBundle.putString("fields", REQUEST_FIELDS);
        localRequest.setParameters(localBundle);
        Request.executeBatchAsync(new Request[] { localRequest });
        this.userInfoSession = localSession;
      }
      return;
    }
    this.user = null;
  }
  
  private ImageRequest getImageRequest()
  {
    try
    {
      ImageRequest localImageRequest = new ImageRequest.Builder(getActivity(), ImageRequest.getProfilePictureUrl(this.user.getId(), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height))).setCallerTag(this).setCallback(new ImageRequest.Callback()
      {
        public void onCompleted(ImageResponse paramAnonymousImageResponse)
        {
          UserSettingsFragment.this.processImageResponse(UserSettingsFragment.this.user.getId(), paramAnonymousImageResponse);
        }
      }).build();
      return localImageRequest;
    }
    catch (MalformedURLException localMalformedURLException) {}
    return null;
  }
  
  private void processImageResponse(String paramString, ImageResponse paramImageResponse)
  {
    if (paramImageResponse != null)
    {
      Bitmap localBitmap = paramImageResponse.getBitmap();
      if (localBitmap != null)
      {
        BitmapDrawable localBitmapDrawable = new BitmapDrawable(getResources(), localBitmap);
        localBitmapDrawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
        this.userProfilePic = localBitmapDrawable;
        this.userProfilePicID = paramString;
        this.connectedStateLabel.setCompoundDrawables(null, localBitmapDrawable, null, null);
        this.connectedStateLabel.setTag(paramImageResponse.getRequest().getImageUrl());
      }
    }
  }
  
  private void updateUI()
  {
    if (!isAdded()) {
      return;
    }
    if (isSessionOpen())
    {
      this.connectedStateLabel.setTextColor(getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_text_color));
      this.connectedStateLabel.setShadowLayer(1.0F, 0.0F, -1.0F, getResources().getColor(R.color.com_facebook_usersettingsfragment_connected_shadow_color));
      if (this.user != null)
      {
        ImageRequest localImageRequest = getImageRequest();
        if (localImageRequest != null)
        {
          URL localURL = localImageRequest.getImageUrl();
          if (!localURL.equals(this.connectedStateLabel.getTag()))
          {
            if (!this.user.getId().equals(this.userProfilePicID)) {
              break label149;
            }
            this.connectedStateLabel.setCompoundDrawables(null, this.userProfilePic, null, null);
            this.connectedStateLabel.setTag(localURL);
          }
        }
        for (;;)
        {
          this.connectedStateLabel.setText(this.user.getName());
          return;
          label149:
          ImageDownloader.downloadAsync(localImageRequest);
        }
      }
      this.connectedStateLabel.setText(getResources().getString(R.string.com_facebook_usersettingsfragment_logged_in));
      Drawable localDrawable = getResources().getDrawable(R.drawable.com_facebook_profile_default_icon);
      localDrawable.setBounds(0, 0, getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_width), getResources().getDimensionPixelSize(R.dimen.com_facebook_usersettingsfragment_profile_picture_height));
      this.connectedStateLabel.setCompoundDrawables(null, localDrawable, null, null);
      return;
    }
    int i = getResources().getColor(R.color.com_facebook_usersettingsfragment_not_connected_text_color);
    this.connectedStateLabel.setTextColor(i);
    this.connectedStateLabel.setShadowLayer(0.0F, 0.0F, 0.0F, i);
    this.connectedStateLabel.setText(getResources().getString(R.string.com_facebook_usersettingsfragment_not_logged_in));
    this.connectedStateLabel.setCompoundDrawables(null, null, null, null);
    this.connectedStateLabel.setTag(null);
  }
  
  public void clearPermissions()
  {
    this.loginButtonProperties.clearPermissions();
  }
  
  public SessionDefaultAudience getDefaultAudience()
  {
    return this.loginButtonProperties.getDefaultAudience();
  }
  
  public SessionLoginBehavior getLoginBehavior()
  {
    return this.loginButtonProperties.getLoginBehavior();
  }
  
  public LoginButton.OnErrorListener getOnErrorListener()
  {
    return this.loginButtonProperties.getOnErrorListener();
  }
  
  List<String> getPermissions()
  {
    return this.loginButtonProperties.getPermissions();
  }
  
  public Session.StatusCallback getSessionStatusCallback()
  {
    return this.sessionStatusCallback;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setRetainInstance(true);
  }
  
  public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle)
  {
    View localView = paramLayoutInflater.inflate(R.layout.com_facebook_usersettingsfragment, paramViewGroup, false);
    this.loginButton = ((LoginButton)localView.findViewById(R.id.com_facebook_usersettingsfragment_login_button));
    this.loginButton.setProperties(this.loginButtonProperties);
    this.loginButton.setFragment(this);
    Session localSession = getSession();
    if ((localSession != null) && (!localSession.equals(Session.getActiveSession()))) {
      this.loginButton.setSession(localSession);
    }
    this.connectedStateLabel = ((TextView)localView.findViewById(R.id.com_facebook_usersettingsfragment_profile_name));
    if (localView.getBackground() == null)
    {
      localView.setBackgroundColor(getResources().getColor(R.color.com_facebook_blue));
      return localView;
    }
    localView.getBackground().setDither(true);
    return localView;
  }
  
  public void onResume()
  {
    super.onResume();
    fetchUserInfo();
    updateUI();
  }
  
  protected void onSessionStateChange(SessionState paramSessionState, Exception paramException)
  {
    fetchUserInfo();
    updateUI();
    if (this.sessionStatusCallback != null) {
      this.sessionStatusCallback.call(getSession(), paramSessionState, paramException);
    }
  }
  
  public void setDefaultAudience(SessionDefaultAudience paramSessionDefaultAudience)
  {
    this.loginButtonProperties.setDefaultAudience(paramSessionDefaultAudience);
  }
  
  public void setLoginBehavior(SessionLoginBehavior paramSessionLoginBehavior)
  {
    this.loginButtonProperties.setLoginBehavior(paramSessionLoginBehavior);
  }
  
  public void setOnErrorListener(LoginButton.OnErrorListener paramOnErrorListener)
  {
    this.loginButtonProperties.setOnErrorListener(paramOnErrorListener);
  }
  
  public void setPublishPermissions(List<String> paramList)
  {
    this.loginButtonProperties.setPublishPermissions(paramList, getSession());
  }
  
  public void setReadPermissions(List<String> paramList)
  {
    this.loginButtonProperties.setReadPermissions(paramList, getSession());
  }
  
  public void setSession(Session paramSession)
  {
    super.setSession(paramSession);
    if (this.loginButton != null) {
      this.loginButton.setSession(paramSession);
    }
    fetchUserInfo();
    updateUI();
  }
  
  public void setSessionStatusCallback(Session.StatusCallback paramStatusCallback)
  {
    this.sessionStatusCallback = paramStatusCallback;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.UserSettingsFragment
 * JD-Core Version:    0.7.0.1
 */