package com.touchtype.keyboard.view;

import android.graphics.PointF;
import com.touchtype.keyboard.view.touch.TouchEvent;
import com.touchtype.keyboard.view.touch.TouchEventVelocityTracker;

public final class SwipeGestureDetector
{
  private SwipeGestureListener mActionListener;
  private boolean mCancelled = false;
  private boolean mHasFired = false;
  private PointF mInitiatingTouch;
  private final float mMinXDistance;
  private final float mMinXVelocity;
  private final float mMinYDistance;
  private final float mMinYVelocity;
  private int mSamples = 0;
  private TouchEventVelocityTracker mVelocityTracker;
  
  public SwipeGestureDetector(SwipeGestureListener paramSwipeGestureListener, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.mActionListener = paramSwipeGestureListener;
    this.mMinXVelocity = paramFloat3;
    this.mMinYVelocity = paramFloat4;
    this.mMinXDistance = paramFloat1;
    this.mMinYDistance = paramFloat2;
  }
  
  public boolean hasFired()
  {
    return this.mHasFired;
  }
  
  protected boolean notifyListener(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    boolean bool = true;
    float f1 = Math.abs(paramFloat3);
    float f2 = Math.abs(paramFloat4);
    if ((f1 < this.mMinXVelocity) && (f2 < this.mMinYVelocity))
    {
      this.mCancelled = bool;
      bool = false;
    }
    float f4;
    do
    {
      float f3;
      do
      {
        return bool;
        f3 = Math.abs(this.mInitiatingTouch.x - paramFloat1);
        f4 = Math.abs(this.mInitiatingTouch.y - paramFloat2);
        if (2.0F * f1 <= f2) {
          break;
        }
        if ((paramFloat3 > this.mMinXVelocity) && (f3 > this.mMinXDistance))
        {
          this.mHasFired = bool;
          this.mActionListener.swipeRight();
          return bool;
        }
      } while ((paramFloat3 >= -this.mMinXVelocity) || (f3 <= this.mMinXDistance));
      this.mHasFired = bool;
      this.mActionListener.swipeLeft();
      return bool;
      if ((paramFloat4 > this.mMinYVelocity) && (f4 > this.mMinYDistance))
      {
        this.mHasFired = bool;
        this.mActionListener.swipeDown();
        return bool;
      }
    } while ((paramFloat4 >= -this.mMinYVelocity) || (f4 <= this.mMinYDistance));
    this.mHasFired = bool;
    this.mActionListener.swipeUp();
    return bool;
  }
  
  public boolean onTouchEvent(TouchEvent paramTouchEvent, int paramInt)
  {
    if (this.mHasFired) {}
    do
    {
      return true;
      if (this.mCancelled) {
        return false;
      }
      if (paramTouchEvent.getPointerId(paramInt) != 0) {
        return false;
      }
      if (paramTouchEvent.getPointerCount() != 1) {
        return false;
      }
      if ((this.mInitiatingTouch == null) || (this.mVelocityTracker == null)) {
        throw new RuntimeException("SwipeDetector onTouchEvent should not be called before calling startDetecting to initialise the touch");
      }
      this.mVelocityTracker.addMovement(paramTouchEvent);
      this.mSamples += 1 + paramTouchEvent.getHistorySize();
    } while (this.mSamples < 3);
    this.mVelocityTracker.computeCurrentVelocity(1000);
    float f1 = this.mVelocityTracker.getXVelocity();
    float f2 = this.mVelocityTracker.getYVelocity();
    return notifyListener(paramTouchEvent.getX(), paramTouchEvent.getY(), f1, f2);
  }
  
  public void startDetecting(float paramFloat1, float paramFloat2)
  {
    this.mVelocityTracker = new TouchEventVelocityTracker();
    this.mHasFired = false;
    this.mCancelled = false;
    this.mInitiatingTouch = new PointF(paramFloat1, paramFloat2);
    this.mSamples = 1;
  }
  
  public void stopDetecting()
  {
    this.mHasFired = false;
    this.mCancelled = true;
    this.mInitiatingTouch = null;
    this.mSamples = 0;
    if (this.mVelocityTracker != null)
    {
      this.mVelocityTracker.recycle();
      this.mVelocityTracker = null;
    }
  }
  
  public static abstract interface SwipeGestureListener
  {
    public abstract void swipeDown();
    
    public abstract void swipeLeft();
    
    public abstract void swipeRight();
    
    public abstract void swipeUp();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.SwipeGestureDetector
 * JD-Core Version:    0.7.0.1
 */