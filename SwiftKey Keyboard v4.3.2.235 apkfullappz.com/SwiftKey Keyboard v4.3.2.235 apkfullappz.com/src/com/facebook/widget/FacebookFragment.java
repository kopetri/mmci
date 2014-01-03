package com.facebook.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.Session;
import com.facebook.Session.Builder;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionLoginBehavior;
import com.facebook.SessionState;
import com.facebook.internal.SessionAuthorizationType;
import com.facebook.internal.SessionTracker;
import java.util.Date;
import java.util.List;

class FacebookFragment
  extends Fragment
{
  private SessionTracker sessionTracker;
  
  private void openSession(String paramString, List<String> paramList, SessionLoginBehavior paramSessionLoginBehavior, int paramInt, SessionAuthorizationType paramSessionAuthorizationType)
  {
    Object localObject;
    Session.OpenRequest localOpenRequest;
    if (this.sessionTracker != null)
    {
      localObject = this.sessionTracker.getSession();
      if ((localObject == null) || (((Session)localObject).getState().isClosed()))
      {
        Session localSession = new Session.Builder(getActivity()).setApplicationId(paramString).build();
        Session.setActiveSession(localSession);
        localObject = localSession;
      }
      if (!((Session)localObject).isOpened())
      {
        localOpenRequest = new Session.OpenRequest(this).setPermissions(paramList).setLoginBehavior(paramSessionLoginBehavior).setRequestCode(paramInt);
        if (!SessionAuthorizationType.PUBLISH.equals(paramSessionAuthorizationType)) {
          break label111;
        }
        ((Session)localObject).openForPublish(localOpenRequest);
      }
    }
    return;
    label111:
    ((Session)localObject).openForRead(localOpenRequest);
  }
  
  protected final void closeSession()
  {
    if (this.sessionTracker != null)
    {
      Session localSession = this.sessionTracker.getOpenSession();
      if (localSession != null) {
        localSession.close();
      }
    }
  }
  
  protected final void closeSessionAndClearTokenInformation()
  {
    if (this.sessionTracker != null)
    {
      Session localSession = this.sessionTracker.getOpenSession();
      if (localSession != null) {
        localSession.closeAndClearTokenInformation();
      }
    }
  }
  
  protected final String getAccessToken()
  {
    SessionTracker localSessionTracker = this.sessionTracker;
    String str = null;
    if (localSessionTracker != null)
    {
      Session localSession = this.sessionTracker.getOpenSession();
      str = null;
      if (localSession != null) {
        str = localSession.getAccessToken();
      }
    }
    return str;
  }
  
  protected final Date getExpirationDate()
  {
    SessionTracker localSessionTracker = this.sessionTracker;
    Date localDate = null;
    if (localSessionTracker != null)
    {
      Session localSession = this.sessionTracker.getOpenSession();
      localDate = null;
      if (localSession != null) {
        localDate = localSession.getExpirationDate();
      }
    }
    return localDate;
  }
  
  protected final Session getSession()
  {
    if (this.sessionTracker != null) {
      return this.sessionTracker.getSession();
    }
    return null;
  }
  
  protected final List<String> getSessionPermissions()
  {
    SessionTracker localSessionTracker = this.sessionTracker;
    List localList = null;
    if (localSessionTracker != null)
    {
      Session localSession = this.sessionTracker.getSession();
      localList = null;
      if (localSession != null) {
        localList = localSession.getPermissions();
      }
    }
    return localList;
  }
  
  protected final SessionState getSessionState()
  {
    SessionTracker localSessionTracker = this.sessionTracker;
    SessionState localSessionState = null;
    if (localSessionTracker != null)
    {
      Session localSession = this.sessionTracker.getSession();
      localSessionState = null;
      if (localSession != null) {
        localSessionState = localSession.getState();
      }
    }
    return localSessionState;
  }
  
  protected final boolean isSessionOpen()
  {
    SessionTracker localSessionTracker = this.sessionTracker;
    boolean bool = false;
    if (localSessionTracker != null)
    {
      Session localSession = this.sessionTracker.getOpenSession();
      bool = false;
      if (localSession != null) {
        bool = true;
      }
    }
    return bool;
  }
  
  public void onActivityCreated(Bundle paramBundle)
  {
    super.onActivityCreated(paramBundle);
    this.sessionTracker = new SessionTracker(getActivity(), new DefaultSessionStatusCallback(null));
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    this.sessionTracker.getSession().onActivityResult(getActivity(), paramInt1, paramInt2, paramIntent);
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    this.sessionTracker.stopTracking();
  }
  
  protected void onSessionStateChange(SessionState paramSessionState, Exception paramException) {}
  
  protected final void openSession()
  {
    openSessionForRead(null, null);
  }
  
  protected final void openSessionForPublish(String paramString, List<String> paramList)
  {
    openSessionForPublish(paramString, paramList, SessionLoginBehavior.SSO_WITH_FALLBACK, 64206);
  }
  
  protected final void openSessionForPublish(String paramString, List<String> paramList, SessionLoginBehavior paramSessionLoginBehavior, int paramInt)
  {
    openSession(paramString, paramList, paramSessionLoginBehavior, paramInt, SessionAuthorizationType.PUBLISH);
  }
  
  protected final void openSessionForRead(String paramString, List<String> paramList)
  {
    openSessionForRead(paramString, paramList, SessionLoginBehavior.SSO_WITH_FALLBACK, 64206);
  }
  
  protected final void openSessionForRead(String paramString, List<String> paramList, SessionLoginBehavior paramSessionLoginBehavior, int paramInt)
  {
    openSession(paramString, paramList, paramSessionLoginBehavior, paramInt, SessionAuthorizationType.READ);
  }
  
  public void setSession(Session paramSession)
  {
    if (this.sessionTracker != null) {
      this.sessionTracker.setSession(paramSession);
    }
  }
  
  private class DefaultSessionStatusCallback
    implements Session.StatusCallback
  {
    private DefaultSessionStatusCallback() {}
    
    public void call(Session paramSession, SessionState paramSessionState, Exception paramException)
    {
      FacebookFragment.this.onSessionStateChange(paramSessionState, paramException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.widget.FacebookFragment
 * JD-Core Version:    0.7.0.1
 */