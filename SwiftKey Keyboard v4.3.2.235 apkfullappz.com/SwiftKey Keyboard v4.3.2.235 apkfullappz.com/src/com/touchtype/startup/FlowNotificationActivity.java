package com.touchtype.startup;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.touchtype.animation.FrameSequenceAnimator;
import com.touchtype.preferences.TouchTypePreferences;

public class FlowNotificationActivity
  extends Activity
{
  private FrameSequenceAnimator flowAnimator;
  private FrameSequenceAnimator flowBackgroundAnimator;
  private boolean mAnimationFinished = false;
  private boolean mAnimationStarted = false;
  private Button mDismissButton;
  private Button mEnableButton;
  private ImageView mFlowAnimation;
  private ImageView mFlowBackgroundAnimation;
  private TextView mFlowReplayText;
  private TextView mFlowText;
  private boolean mFromInstaller;
  private Handler mHandler;
  private boolean mNotificationComplete = false;
  private TouchTypePreferences mPreferences;
  private ImageView mReplayIcon;
  
  private void initialiseAnimatorsIfNecessary()
  {
    if (this.flowAnimator == null) {
      this.flowAnimator = new FrameSequenceAnimator(this, this.mFlowAnimation, 2130968580, true);
    }
    if (this.flowBackgroundAnimator == null) {
      this.flowBackgroundAnimator = new FrameSequenceAnimator(this, this.mFlowBackgroundAnimation, 2130968581, true);
    }
  }
  
  private void restartAnimation()
  {
    this.mNotificationComplete = false;
    this.mAnimationStarted = false;
    this.mReplayIcon.setVisibility(4);
    this.mFlowReplayText.setVisibility(8);
    this.mFlowText.setVisibility(8);
    startAnimation();
  }
  
  private void showReplay()
  {
    this.mNotificationComplete = true;
    this.mAnimationStarted = false;
    this.mReplayIcon.setImageResource(2130838207);
    this.mReplayIcon.setVisibility(0);
    this.mFlowReplayText.setVisibility(0);
    if (!this.mFromInstaller) {
      this.mFlowText.setText(getResources().getString(2131297278) + "\n" + getResources().getString(2131297279));
    }
    this.mFlowText.setVisibility(0);
    initialiseAnimatorsIfNecessary();
    this.flowAnimator.stop();
    this.flowBackgroundAnimator.stop();
    this.mFlowBackgroundAnimation.setBackgroundResource(2130837525);
    this.mFlowAnimation.setBackgroundResource(2130837963);
    this.mReplayIcon.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FlowNotificationActivity.this.mReplayIcon.setImageResource(2130838206);
        FlowNotificationActivity.access$402(FlowNotificationActivity.this, false);
        FlowNotificationActivity.this.mHandler.postDelayed(new Runnable()
        {
          public void run()
          {
            FlowNotificationActivity.this.restartAnimation();
          }
        }, 200L);
      }
    });
  }
  
  private void startAnimation()
  {
    this.mFlowAnimation.setImageBitmap(null);
    this.mFlowBackgroundAnimation.setImageBitmap(null);
    initialiseAnimatorsIfNecessary();
    long l = Math.max(this.flowAnimator.getDuration(), this.flowBackgroundAnimator.getDuration());
    this.flowAnimator.start();
    this.flowBackgroundAnimator.start();
    this.mHandler.postDelayed(new Runnable()
    {
      public void run()
      {
        FlowNotificationActivity.access$402(FlowNotificationActivity.this, true);
        FlowNotificationActivity.this.showReplay();
      }
    }, l);
    this.mAnimationStarted = true;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903075);
    String str = getIntent().getAction();
    if ((str != null) && (str.equals("com.touchtype.FLOW_NOTIFICATION_FROM_INSTALLER"))) {
      this.mFromInstaller = true;
    }
    if ((paramBundle != null) && (paramBundle.containsKey("flow_notifier_state")))
    {
      Bundle localBundle = paramBundle.getBundle("flow_notifier_state");
      this.mNotificationComplete = localBundle.getBoolean("notification_complete_key", false);
      this.mFromInstaller = localBundle.getBoolean("from_installer_key", false);
      this.mAnimationStarted = localBundle.getBoolean("animation_started_key", false);
      this.mAnimationFinished = localBundle.getBoolean("animation_finished_key", false);
    }
    this.mFlowAnimation = ((ImageView)findViewById(2131230832));
    this.mFlowBackgroundAnimation = ((ImageView)findViewById(2131230831));
    this.mReplayIcon = ((ImageView)findViewById(2131230834));
    this.mFlowText = ((TextView)findViewById(2131230833));
    this.mFlowReplayText = ((TextView)findViewById(2131230835));
    this.mEnableButton = ((Button)findViewById(2131230837));
    this.mDismissButton = ((Button)findViewById(2131230836));
    this.mPreferences = TouchTypePreferences.getInstance(getApplicationContext());
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.mHandler.removeCallbacksAndMessages(null);
  }
  
  public void onResume()
  {
    super.onResume();
    this.mHandler = new Handler();
    if (this.mEnableButton != null) {
      this.mEnableButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FlowNotificationActivity.this.mPreferences.putBoolean("pref_flow_switch_key", true);
          FlowNotificationActivity.this.setResult(-1);
          FlowNotificationActivity.this.finish();
        }
      });
    }
    if (this.mDismissButton != null) {
      this.mDismissButton.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          FlowNotificationActivity.this.setResult(-1);
          FlowNotificationActivity.this.finish();
        }
      });
    }
    if (!this.mAnimationFinished)
    {
      this.mFlowText.setVisibility(8);
      this.mReplayIcon.setVisibility(4);
      this.mHandler.postDelayed(new Runnable()
      {
        public void run()
        {
          FlowNotificationActivity.this.startAnimation();
        }
      }, 500L);
      return;
    }
    this.mFlowAnimation.setImageBitmap(null);
    this.mFlowBackgroundAnimation.setImageBitmap(null);
    initialiseAnimatorsIfNecessary();
    showReplay();
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    Bundle localBundle = new Bundle();
    localBundle.putBoolean("from_installer_key", this.mFromInstaller);
    localBundle.putBoolean("animation_started_key", this.mAnimationStarted);
    localBundle.putBoolean("animation_finished_key", this.mAnimationFinished);
    localBundle.putBoolean("notification_complete_key", this.mNotificationComplete);
    paramBundle.putBundle("flow_notifier_state", localBundle);
    super.onSaveInstanceState(paramBundle);
  }
  
  public void onWindowFocusChanged(boolean paramBoolean)
  {
    super.onWindowFocusChanged(paramBoolean);
    if (this.mAnimationStarted)
    {
      if (paramBoolean) {
        this.mFlowAnimation.post(new Runnable()
        {
          public void run()
          {
            if (FlowNotificationActivity.this.flowAnimator != null) {
              FlowNotificationActivity.this.flowAnimator.start();
            }
          }
        });
      }
    }
    else {
      return;
    }
    this.mFlowAnimation.post(new Runnable()
    {
      public void run()
      {
        if (FlowNotificationActivity.this.flowBackgroundAnimator != null) {
          FlowNotificationActivity.this.flowBackgroundAnimator.stop();
        }
      }
    });
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.startup.FlowNotificationActivity
 * JD-Core Version:    0.7.0.1
 */