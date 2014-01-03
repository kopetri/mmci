package com.touchtype.keyboard.key.delegates;

import android.os.Handler;
import android.os.Looper;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public class LongPressDelegate
  extends TouchSubDelegate
{
  protected static final String TAG = LongPressDelegate.class.getSimpleName();
  private final LongPressFiredCallback mCallback;
  private final Handler mKeyHoldHandler;
  private boolean mLongClickFired = false;
  private boolean mLongPressFired = false;
  private final Runnable mLongPressRunnable;
  private boolean mShouldBlockClicks = true;
  private final int mTimeout;
  private boolean mTouchStartedHere = false;
  
  public LongPressDelegate(LongPressFiredCallback paramLongPressFiredCallback, int paramInt, boolean paramBoolean)
  {
    if (paramInt > 0) {}
    for (;;)
    {
      this.mTimeout = paramInt;
      this.mCallback = paramLongPressFiredCallback;
      this.mShouldBlockClicks = paramBoolean;
      this.mKeyHoldHandler = new Handler(Looper.getMainLooper());
      this.mLongPressRunnable = new Runnable()
      {
        public void run()
        {
          LongPressDelegate.this.fireLongPress();
        }
      };
      return;
      paramInt = 100;
    }
  }
  
  private void fireLongClick(TouchEvent.Touch paramTouch)
  {
    this.mLongClickFired = true;
    this.mCallback.longClick(paramTouch);
  }
  
  private void fireLongPress()
  {
    this.mLongPressFired = true;
    this.mCallback.longPress();
  }
  
  private void reset()
  {
    this.mLongPressFired = false;
    this.mLongClickFired = false;
    this.mTouchStartedHere = false;
    this.mKeyHoldHandler.removeCallbacks(this.mLongPressRunnable);
  }
  
  public void cancel()
  {
    reset();
    this.mKeyHoldHandler.removeCallbacks(this.mLongPressRunnable);
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    reset();
    this.mTouchStartedHere = true;
    this.mKeyHoldHandler.postDelayed(this.mLongPressRunnable, this.mTimeout);
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return (this.mShouldBlockClicks) && (((paramEnumSet.contains(ActionType.LONGPRESS)) && (this.mLongPressFired)) || ((paramEnumSet.contains(ActionType.LONGCLICK)) && (this.mLongClickFired)));
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    reset();
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    reset();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    if ((this.mLongPressFired) && (this.mTouchStartedHere)) {
      fireLongClick(paramTouch);
    }
    reset();
  }
  
  public static abstract interface LongPressFiredCallback
  {
    public abstract void longClick(TouchEvent.Touch paramTouch);
    
    public abstract void longPress();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.LongPressDelegate
 * JD-Core Version:    0.7.0.1
 */