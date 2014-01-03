package com.touchtype_fluency.service.personalize.auth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.NewPermissionsRequest;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.model.GraphUser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FacebookTokenRetriever
  extends TokenRetriever
{
  private static final String TAG = FacebookTokenRetriever.class.getSimpleName();
  private static List<String> readPermissions = Arrays.asList(new String[] { "user_about_me", "user_notes", "user_status", "user_events", "read_mailbox" });
  private static String writePermission = "publish_actions";
  private String mCurrentAccountSelected;
  private final boolean mPostConsent;
  private FbStatusCallback mStatusCallback;
  
  public FacebookTokenRetriever(Activity paramActivity, TokenRetriever.TokenRetrieverListener paramTokenRetrieverListener)
  {
    this(paramActivity, paramTokenRetrieverListener, false);
  }
  
  public FacebookTokenRetriever(Activity paramActivity, TokenRetriever.TokenRetrieverListener paramTokenRetrieverListener, boolean paramBoolean)
  {
    super(paramActivity, paramTokenRetrieverListener);
    this.mPostConsent = paramBoolean;
  }
  
  private void makeMeRequest(final Session paramSession)
  {
    Request.newMeRequest(paramSession, new Request.GraphUserCallback()
    {
      public void onCompleted(GraphUser paramAnonymousGraphUser, Response paramAnonymousResponse)
      {
        if ((paramSession == Session.getActiveSession()) && (paramAnonymousGraphUser != null)) {
          FacebookTokenRetriever.this.onUserInfoRetrieved(paramSession, paramAnonymousGraphUser);
        }
        if (paramAnonymousResponse.getError() != null) {
          FacebookTokenRetriever.this.mListener.onError("Access Token Retrieving failed");
        }
        paramSession.removeCallback(FacebookTokenRetriever.this.mStatusCallback);
      }
    }).executeAsync();
  }
  
  private void onUserInfoRetrieved(Session paramSession, GraphUser paramGraphUser)
  {
    if ((this.mCurrentAccountSelected == null) || (paramGraphUser.getName().equals(this.mCurrentAccountSelected)))
    {
      this.mListener.onTokenRetrieved(paramSession.getAccessToken(), paramGraphUser.getName());
      return;
    }
    showSessionUpdatedDialog(paramSession, paramGraphUser);
  }
  
  private void showSessionUpdatedDialog(final Session paramSession, final GraphUser paramGraphUser)
  {
    new AlertDialog.Builder(this.mCallerActivity).setTitle(this.mCallerActivity.getString(2131296927)).setMessage(this.mCallerActivity.getString(2131297141)).setNegativeButton(this.mCallerActivity.getString(2131297142), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        FacebookTokenRetriever.this.mListener.onCancel();
      }
    }).setPositiveButton(this.mCallerActivity.getString(2131297143), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        FacebookTokenRetriever.this.mListener.onTokenRetrieved(paramSession.getAccessToken(), paramGraphUser.getName());
      }
    }).setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        FacebookTokenRetriever.this.mListener.onCancel();
      }
    }).create().show();
  }
  
  public void refreshCredentials(String paramString)
  {
    this.mCurrentAccountSelected = paramString;
    startTokenRetrieving();
  }
  
  public void startTokenRetrieving(List<String> paramList)
  {
    this.mStatusCallback = new FbStatusCallback(null);
    Session localSession = Session.getActiveSession();
    if (localSession == null)
    {
      localSession = new Session.Builder(this.mCallerActivity).setApplicationId("175568195800784").build();
      Session.setActiveSession(localSession);
    }
    if (localSession.isOpened())
    {
      localList = localSession.getPermissions();
      if ((this.mPostConsent) && (!localList.contains(writePermission)))
      {
        localSession.addCallback(this.mStatusCallback);
        localActivity = this.mCallerActivity;
        arrayOfString = new String[1];
        arrayOfString[0] = writePermission;
        localSession.requestNewPublishPermissions(new Session.NewPermissionsRequest(localActivity, Arrays.asList(arrayOfString)));
      }
    }
    while ((!localSession.getState().equals(SessionState.CREATED)) && (!localSession.getState().equals(SessionState.CREATED_TOKEN_LOADED)))
    {
      List localList;
      Activity localActivity;
      String[] arrayOfString;
      return;
      if (!localList.containsAll(readPermissions))
      {
        localSession.addCallback(this.mStatusCallback);
        localSession.requestNewReadPermissions(new Session.NewPermissionsRequest(this.mCallerActivity, readPermissions));
        return;
      }
      makeMeRequest(localSession);
      return;
    }
    if (!this.mPostConsent)
    {
      localSession.openForRead(new Session.OpenRequest(this.mCallerActivity).setPermissions(readPermissions).setCallback(this.mStatusCallback));
      return;
    }
    ArrayList localArrayList = new ArrayList();
    localArrayList.addAll(readPermissions);
    localArrayList.add(writePermission);
    localSession.openForPublish(new Session.OpenRequest(this.mCallerActivity).setPermissions(localArrayList).setCallback(this.mStatusCallback));
  }
  
  private final class FbStatusCallback
    implements Session.StatusCallback
  {
    private FbStatusCallback() {}
    
    public void call(Session paramSession, SessionState paramSessionState, Exception paramException)
    {
      if ((paramSessionState == SessionState.CLOSED_LOGIN_FAILED) || (paramSessionState == SessionState.CLOSED))
      {
        Session.setActiveSession(null);
        if ((paramException != null) && (!(paramException instanceof FacebookOperationCanceledException))) {
          FacebookTokenRetriever.this.mListener.onError("Access Token Retrieving failed");
        }
      }
      while (!paramSession.isOpened())
      {
        return;
        FacebookTokenRetriever.this.mListener.onCancel();
        return;
      }
      FacebookTokenRetriever.this.makeMeRequest(paramSession);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.auth.FacebookTokenRetriever
 * JD-Core Version:    0.7.0.1
 */