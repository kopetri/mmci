package com.touchtype.keyboard.key.delegates;

import android.graphics.PointF;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;

public class SimpleTouchDelegate
  implements KeyTouchDelegate
{
  private final float mHoldThreshold;
  private PointF mInitialTouch = new PointF();
  protected final KeyState mState;
  private boolean mTouchStartedHere = false;
  
  public SimpleTouchDelegate(KeyState paramKeyState, float paramFloat)
  {
    this.mState = paramKeyState;
    this.mHoldThreshold = paramFloat;
  }
  
  public void cancel()
  {
    this.mState.setPressed(false);
    this.mTouchStartedHere = false;
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    this.mState.setPressed(true);
    this.mTouchStartedHere = true;
    this.mInitialTouch.set(paramTouch.getPoint());
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    boolean bool1 = this.mTouchStartedHere;
    boolean bool2 = false;
    if (bool1)
    {
      float f1 = this.mInitialTouch.x - paramTouch.getX();
      float f2 = this.mInitialTouch.y - paramTouch.getY();
      boolean bool3 = Math.sqrt(f1 * f1 + f2 * f2) < this.mHoldThreshold;
      bool2 = false;
      if (bool3) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    this.mTouchStartedHere = false;
    if (!this.mState.isFlowing()) {
      this.mState.setPressed(true);
    }
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mState.setPressed(false);
    this.mTouchStartedHere = false;
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    this.mState.setPressed(false);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.SimpleTouchDelegate
 * JD-Core Version:    0.7.0.1
 */