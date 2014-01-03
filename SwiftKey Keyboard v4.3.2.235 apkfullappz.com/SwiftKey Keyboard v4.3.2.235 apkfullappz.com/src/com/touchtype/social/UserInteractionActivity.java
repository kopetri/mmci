package com.touchtype.social;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.touchtype.broadcast.NotificationRegistrar;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;

public class UserInteractionActivity
  extends Activity
{
  private static final String TAG = UserInteractionActivity.class.getSimpleName();
  private String mAction;
  TextView mDialogTitle;
  Button mDontBotherButton;
  Button mIgnoreButton;
  private NotificationManager mNotificationManager;
  Button mShareButton;
  private TouchTypePreferences mTouchTypePreferences;
  private String mUserEventTriggeredKey;
  
  private void buildGenericButtons()
  {
    this.mIgnoreButton.setText(2131297211);
    this.mIgnoreButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserInteractionActivity.this.mTouchTypePreferences.setEventTriggered(UserInteractionActivity.this.mUserEventTriggeredKey, 3);
        UserInteractionActivity.this.removeNotification();
        UserInteractionActivity.this.setResult(0);
        UserInteractionActivity.this.finish();
      }
    });
    this.mDontBotherButton.setText(2131297212);
    this.mDontBotherButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserInteractionActivity.this.removeNotification();
        UserInteractionActivity.this.mTouchTypePreferences.setEventTriggered(UserInteractionActivity.this.mUserEventTriggeredKey, 4);
        UserInteractionActivity.this.mTouchTypePreferences.putBoolean("do_not_bother_me", true);
        UserInteractionActivity.this.mTouchTypePreferences.putBoolean(UserInteractionActivity.this.getString(2131296740), false);
        NotificationRegistrar.receivePublicityMessages(UserInteractionActivity.this.getApplicationContext(), false);
        UserInteractionActivity.this.setResult(0);
        UserInteractionActivity.this.finish();
      }
    });
  }
  
  private void fail()
  {
    LogUtil.w(TAG, "Trying to handle an unknown action");
    setResult(0);
    finish();
  }
  
  private void removeNotification()
  {
    if (this.mNotificationManager != null) {
      this.mNotificationManager.cancel(2130903121);
    }
  }
  
  public void buildShareButton()
  {
    this.mShareButton.setText(2131297210);
    this.mShareButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UserInteractionActivity.this.removeNotification();
        Intent localIntent = UserNotificationManager.shareAction(UserInteractionActivity.this.getApplicationContext(), UserInteractionActivity.this.getIntent().getStringExtra("share_body"));
        UserInteractionActivity.this.mTouchTypePreferences.setEventTriggered(UserInteractionActivity.this.mUserEventTriggeredKey, 2);
        UserInteractionActivity.this.startActivity(localIntent);
        UserInteractionActivity.this.setResult(-1);
        UserInteractionActivity.this.finish();
      }
    });
  }
  
  public void notificationActions(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return;
    case 0: 
      buildGenericButtons();
      return;
    }
    buildShareButton();
  }
  
  public void onCreate(Bundle paramBundle)
  {
    this.mNotificationManager = ((NotificationManager)getApplicationContext().getSystemService("notification"));
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.mAction = getIntent().getAction();
    this.mUserEventTriggeredKey = getIntent().getStringExtra("user_event_triggered_key");
    this.mTouchTypePreferences = TouchTypePreferences.getInstance(getApplicationContext());
    Object localObject = null;
    for (EventTriggeredUserInteractions.UserEvent localUserEvent : EventTriggeredUserInteractions.UserEvent.values()) {
      if (localUserEvent.toString().equals(this.mAction)) {
        localObject = localUserEvent;
      }
    }
    setContentView(2130903120);
    this.mDialogTitle = ((TextView)findViewById(2131230936));
    this.mShareButton = ((Button)findViewById(2131230937));
    this.mIgnoreButton = ((Button)findViewById(2131230938));
    this.mDontBotherButton = ((Button)findViewById(2131230939));
    if (localObject != null)
    {
      switch (4.$SwitchMap$com$touchtype$social$EventTriggeredUserInteractions$UserEvent[localObject.ordinal()])
      {
      default: 
        fail();
      }
      for (;;)
      {
        return;
        ArrayList localArrayList = getIntent().getIntegerArrayListExtra("actions");
        if (localArrayList != null)
        {
          String str1 = getIntent().getStringExtra("milestone_string");
          String str2 = String.format(getString(2131297216), new Object[] { str1 });
          this.mDialogTitle.setText(str2);
          for (int k = 0; k < localArrayList.size(); k++) {
            notificationActions(((Integer)localArrayList.get(k)).intValue());
          }
        }
      }
    }
    fail();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    boolean bool = false;
    if (paramInt == 4)
    {
      this.mTouchTypePreferences.setEventTriggered(this.mUserEventTriggeredKey, 3);
      removeNotification();
      setResult(0);
      finish();
      bool = true;
    }
    return bool;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.social.UserInteractionActivity
 * JD-Core Version:    0.7.0.1
 */