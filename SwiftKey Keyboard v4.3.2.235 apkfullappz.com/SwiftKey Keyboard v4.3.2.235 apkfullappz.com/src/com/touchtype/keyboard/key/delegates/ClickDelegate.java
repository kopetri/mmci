package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public class ClickDelegate
  extends TouchSubDelegate
{
  private final ClickFiredCallback mCallback;
  private boolean mClicked = false;
  private boolean mTouchStayedInKey;
  
  public ClickDelegate(ClickFiredCallback paramClickFiredCallback)
  {
    this.mCallback = paramClickFiredCallback;
  }
  
  public void cancel()
  {
    this.mTouchStayedInKey = false;
    this.mClicked = false;
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInKey = true;
    this.mClicked = false;
  }
  
  protected void fireCallback(TouchEvent.Touch paramTouch)
  {
    this.mCallback.click(paramTouch);
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return (paramEnumSet.contains(ActionType.CLICK)) && (this.mClicked);
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInKey = false;
    this.mClicked = false;
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInKey = false;
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    if (this.mTouchStayedInKey)
    {
      fireCallback(paramTouch);
      this.mClicked = true;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.ClickDelegate
 * JD-Core Version:    0.7.0.1
 */