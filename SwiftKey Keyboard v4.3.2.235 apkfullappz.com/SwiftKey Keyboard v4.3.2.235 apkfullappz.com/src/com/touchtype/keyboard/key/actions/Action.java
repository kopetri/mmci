package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.callbacks.DragBehaviour;
import com.touchtype.keyboard.key.callbacks.KeyCallbacks;
import java.util.EnumSet;

public abstract interface Action
  extends KeyCallbacks
{
  public abstract EnumSet<ActionType> getActions();
  
  public abstract DragBehaviour getDragBehaviour();
  
  public abstract float getFlowXActivationThreshold();
  
  public abstract float getFlowYActivationThreshold();
  
  public abstract int getLongPressTimeOut();
  
  public abstract int getMultitapResetDelay();
  
  public abstract RepeatBehaviour getRepeatBehaviour();
  
  public abstract float getSwipeMinimumXVelocity();
  
  public abstract float getSwipeMinimumYVelocity();
  
  public abstract float getSwipeXActivationThreshold();
  
  public abstract float getSwipeYActivationThreshold();
  
  public abstract void setLoggableActions(EnumSet<ActionType> paramEnumSet);
  
  public abstract boolean shouldBlockClicks();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.Action
 * JD-Core Version:    0.7.0.1
 */