package com.facebook;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

public class UiLifecycleHelper
{
  private static final String ACTIVITY_NULL_MESSAGE = "activity cannot be null";
  private final Activity activity;
  private final LocalBroadcastManager broadcastManager;
  private final Session.StatusCallback callback;
  private final BroadcastReceiver receiver;
  
  public UiLifecycleHelper(Activity paramActivity, Session.StatusCallback paramStatusCallback)
  {
    if (paramActivity == null) {
      throw new IllegalArgumentException("activity cannot be null");
    }
    this.activity = paramActivity;
    this.callback = paramStatusCallback;
    this.receiver = new ActiveSessionBroadcastReceiver(null);
    this.broadcastManager = LocalBroadcastManager.getInstance(paramActivity);
  }
  
  public void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    Session localSession = Session.getActiveSession();
    if (localSession != null) {
      localSession.onActivityResult(this.activity, paramInt1, paramInt2, paramIntent);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    Session localSession = Session.getActiveSession();
    if (localSession == null)
    {
      if (paramBundle != null) {
        localSession = Session.restoreSession(this.activity, null, this.callback, paramBundle);
      }
      if (localSession == null) {
        localSession = new Session(this.activity);
      }
      Session.setActiveSession(localSession);
    }
  }
  
  public void onDestroy() {}
  
  public void onPause()
  {
    this.broadcastManager.unregisterReceiver(this.receiver);
    if (this.callback != null)
    {
      Session localSession = Session.getActiveSession();
      if (localSession != null) {
        localSession.removeCallback(this.callback);
      }
    }
  }
  
  public void onResume()
  {
    Session localSession = Session.getActiveSession();
    if (localSession != null)
    {
      if (this.callback != null) {
        localSession.addCallback(this.callback);
      }
      if (SessionState.CREATED_TOKEN_LOADED.equals(localSession.getState())) {
        localSession.openForRead(null);
      }
    }
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("com.facebook.sdk.ACTIVE_SESSION_SET");
    localIntentFilter.addAction("com.facebook.sdk.ACTIVE_SESSION_UNSET");
    this.broadcastManager.registerReceiver(this.receiver, localIntentFilter);
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    Session.saveSession(Session.getActiveSession(), paramBundle);
  }
  
  private class ActiveSessionBroadcastReceiver
    extends BroadcastReceiver
  {
    private ActiveSessionBroadcastReceiver() {}
    
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      if ("com.facebook.sdk.ACTIVE_SESSION_SET".equals(paramIntent.getAction()))
      {
        Session localSession2 = Session.getActiveSession();
        if ((localSession2 != null) && (UiLifecycleHelper.this.callback != null)) {
          localSession2.addCallback(UiLifecycleHelper.this.callback);
        }
      }
      Session localSession1;
      do
      {
        do
        {
          return;
        } while (!"com.facebook.sdk.ACTIVE_SESSION_UNSET".equals(paramIntent.getAction()));
        localSession1 = Session.getActiveSession();
      } while ((localSession1 == null) || (UiLifecycleHelper.this.callback == null));
      localSession1.removeCallback(UiLifecycleHelper.this.callback);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.UiLifecycleHelper
 * JD-Core Version:    0.7.0.1
 */