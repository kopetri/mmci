package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.SwipeGestureDetector;
import com.touchtype.keyboard.view.SwipeGestureDetector.SwipeGestureListener;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.Collections;
import java.util.EnumSet;

public final class SwipeGesturingDelegate
  extends TouchSubDelegate
{
  private boolean mGesturesActive = false;
  private final SwipeGestureDetector mSwipeGestureDetector;
  
  public SwipeGesturingDelegate(SwipeGestureDetector.SwipeGestureListener paramSwipeGestureListener, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.mSwipeGestureDetector = new SwipeGestureDetector(paramSwipeGestureListener, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  private void activateGestures(TouchEvent.Touch paramTouch)
  {
    this.mSwipeGestureDetector.startDetecting(paramTouch.getX(), paramTouch.getY());
    this.mGesturesActive = true;
  }
  
  private void deactivateGestures()
  {
    this.mGesturesActive = false;
    this.mSwipeGestureDetector.stopDetecting();
  }
  
  public void cancel()
  {
    deactivateGestures();
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    activateGestures(paramTouch);
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    if (this.mGesturesActive) {
      return this.mSwipeGestureDetector.onTouchEvent(paramTouch.getTouchEvent(), paramTouch.getPointerIndex());
    }
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return (!Collections.disjoint(paramEnumSet, ActionType.SWIPES)) && (this.mSwipeGestureDetector.hasFired());
  }
  
  public void slideIn(TouchEvent.Touch paramTouch) {}
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    deactivateGestures();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    deactivateGestures();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.SwipeGesturingDelegate
 * JD-Core Version:    0.7.0.1
 */