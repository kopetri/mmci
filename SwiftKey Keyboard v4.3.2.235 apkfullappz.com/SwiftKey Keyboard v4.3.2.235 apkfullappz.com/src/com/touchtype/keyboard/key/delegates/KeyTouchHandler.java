package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.actions.Action;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.KeyCallbacks;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public final class KeyTouchHandler
  extends SimpleTouchDelegate
  implements KeyCallbacks
{
  private final Action mAction;
  private final List<TouchSubDelegate> mDelegates;
  
  public KeyTouchHandler(KeyState paramKeyState, Action paramAction, float paramFloat)
  {
    super(paramKeyState, paramFloat);
    this.mAction = paramAction;
    EnumSet localEnumSet = this.mAction.getActions();
    this.mAction.setLoggableActions(localEnumSet);
    this.mDelegates = TouchDelegateFactory.createDelegates(this, localEnumSet, paramAction);
  }
  
  private boolean anyHaveFired(EnumSet<ActionType> paramEnumSet)
  {
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      if (((TouchSubDelegate)localIterator.next()).hasFired(paramEnumSet)) {
        return true;
      }
    }
    return false;
  }
  
  private boolean hasFired(ActionType paramActionType)
  {
    return anyHaveFired(EnumSet.of(paramActionType));
  }
  
  public void cancel()
  {
    super.cancel();
    this.mAction.cancel();
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      ((TouchSubDelegate)localIterator.next()).cancel();
    }
  }
  
  public void click(TouchEvent.Touch paramTouch)
  {
    if ((!anyHaveFired(EnumSet.of(ActionType.LONGPRESS, ActionType.DRAG, ActionType.DRAG_CLICK))) && (!anyHaveFired(ActionType.SWIPES))) {
      this.mAction.click(paramTouch);
    }
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    super.down(paramTouch);
    this.mAction.down(paramTouch);
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      ((TouchSubDelegate)localIterator.next()).down(paramTouch);
    }
  }
  
  public void drag(DragEvent paramDragEvent)
  {
    if (!this.mState.isFlowing()) {
      this.mAction.drag(paramDragEvent);
    }
  }
  
  public void dragClick(DragEvent paramDragEvent)
  {
    if (!this.mState.isFlowing()) {
      this.mAction.dragClick(paramDragEvent);
    }
  }
  
  public void flow(List<FlowEvent> paramList)
  {
    if (!hasFired(ActionType.DRAG)) {
      this.mAction.flow(paramList);
    }
  }
  
  public void flowComplete(FlowEvent paramFlowEvent)
  {
    if (!anyHaveFired(EnumSet.of(ActionType.LONGCLICK, ActionType.CLICK, ActionType.DRAG))) {
      this.mAction.flowComplete(paramFlowEvent);
    }
  }
  
  public void flowStarted()
  {
    if (!hasFired(ActionType.DRAG)) {
      this.mAction.flowStarted();
    }
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    boolean bool = super.handleGesture(paramTouch);
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      bool |= ((TouchSubDelegate)localIterator.next()).handleGesture(paramTouch);
    }
    return bool;
  }
  
  public void longClick(TouchEvent.Touch paramTouch)
  {
    if ((!anyHaveFired(ActionType.SWIPES)) && (!hasFired(ActionType.DRAG_CLICK))) {
      this.mAction.longClick(paramTouch);
    }
  }
  
  public void longPress()
  {
    this.mAction.longPress();
  }
  
  public void multitap(TouchEvent.Touch paramTouch, int paramInt)
  {
    this.mAction.multitap(paramTouch, paramInt);
  }
  
  public void repeat(int paramInt)
  {
    this.mAction.repeat(paramInt);
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    super.slideIn(paramTouch);
    this.mAction.slideIn(paramTouch);
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      ((TouchSubDelegate)localIterator.next()).slideIn(paramTouch);
    }
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    super.slideOut(paramTouch);
    this.mAction.slideOut(paramTouch);
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      ((TouchSubDelegate)localIterator.next()).slideOut(paramTouch);
    }
  }
  
  public void swipeDown()
  {
    this.mAction.swipeDown();
  }
  
  public void swipeLeft()
  {
    this.mAction.swipeLeft();
  }
  
  public void swipeRight()
  {
    this.mAction.swipeRight();
  }
  
  public void swipeUp()
  {
    this.mAction.swipeUp();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    super.up(paramTouch);
    this.mAction.up(paramTouch);
    Iterator localIterator = this.mDelegates.iterator();
    while (localIterator.hasNext()) {
      ((TouchSubDelegate)localIterator.next()).up(paramTouch);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.KeyTouchHandler
 * JD-Core Version:    0.7.0.1
 */