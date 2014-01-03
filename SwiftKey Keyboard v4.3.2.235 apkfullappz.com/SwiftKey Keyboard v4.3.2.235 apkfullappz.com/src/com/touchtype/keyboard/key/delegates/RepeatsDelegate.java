package com.touchtype.keyboard.key.delegates;

import android.os.Handler;
import android.os.Looper;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.actions.RepeatBehaviour;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class RepeatsDelegate
  extends TouchSubDelegate
{
  private final RepeatFiredCallback mCallback;
  private final Handler mKeyHoldHandler;
  private int mKeyRepeats = 0;
  private final Runnable mRepeatEventRunnable;
  private final RepeatBehaviour mRepeatIntervalBehaviour;
  
  public RepeatsDelegate(RepeatFiredCallback paramRepeatFiredCallback, RepeatBehaviour paramRepeatBehaviour)
  {
    this.mCallback = paramRepeatFiredCallback;
    this.mRepeatIntervalBehaviour = paramRepeatBehaviour;
    this.mKeyHoldHandler = new Handler(Looper.getMainLooper());
    this.mRepeatEventRunnable = new Runnable()
    {
      public void run()
      {
        RepeatsDelegate.this.fireRepeat();
      }
    };
  }
  
  private void fireRepeat()
  {
    this.mKeyHoldHandler.postDelayed(this.mRepeatEventRunnable, this.mRepeatIntervalBehaviour.getRepeatInterval(this.mKeyRepeats));
    RepeatFiredCallback localRepeatFiredCallback = this.mCallback;
    int i = this.mKeyRepeats;
    this.mKeyRepeats = (i + 1);
    localRepeatFiredCallback.repeat(i);
  }
  
  public void cancel()
  {
    this.mKeyHoldHandler.removeCallbacks(this.mRepeatEventRunnable);
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    this.mKeyRepeats = 0;
    this.mKeyHoldHandler.postDelayed(this.mRepeatEventRunnable, this.mRepeatIntervalBehaviour.getRepeatStartDelay());
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return (paramEnumSet.contains(ActionType.REPEAT)) && (this.mKeyRepeats > 0);
  }
  
  public void slideIn(TouchEvent.Touch paramTouch) {}
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mKeyHoldHandler.removeCallbacks(this.mRepeatEventRunnable);
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    this.mKeyHoldHandler.removeCallbacks(this.mRepeatEventRunnable);
  }
  
  public static abstract interface RepeatFiredCallback
  {
    public abstract void repeat(int paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.RepeatsDelegate
 * JD-Core Version:    0.7.0.1
 */