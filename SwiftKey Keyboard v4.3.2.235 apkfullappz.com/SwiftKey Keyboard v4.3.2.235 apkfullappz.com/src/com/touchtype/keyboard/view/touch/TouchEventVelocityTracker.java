package com.touchtype.keyboard.view.touch;

import android.graphics.Matrix;
import android.view.VelocityTracker;

public final class TouchEventVelocityTracker
{
  private Matrix mLatestMatrix;
  private VelocityTracker mTracker = VelocityTracker.obtain();
  
  public void addMovement(TouchEvent paramTouchEvent)
  {
    this.mLatestMatrix = paramTouchEvent.mMatrix;
    this.mTracker.addMovement(paramTouchEvent.mMotionEvent);
  }
  
  public void computeCurrentVelocity(int paramInt)
  {
    this.mTracker.computeCurrentVelocity(paramInt);
  }
  
  public float getXVelocity()
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = this.mTracker.getXVelocity();
    arrayOfFloat[1] = 0.0F;
    this.mLatestMatrix.mapPoints(arrayOfFloat);
    return arrayOfFloat[0];
  }
  
  public float getYVelocity()
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = 0.0F;
    arrayOfFloat[1] = this.mTracker.getYVelocity();
    this.mLatestMatrix.mapPoints(arrayOfFloat);
    return arrayOfFloat[1];
  }
  
  public void recycle()
  {
    this.mTracker.recycle();
    this.mTracker = null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.TouchEventVelocityTracker
 * JD-Core Version:    0.7.0.1
 */