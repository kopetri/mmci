package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.callbacks.DragBehaviour;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;
import java.util.List;

public abstract class ActionDecorator
  implements Action
{
  private final Action mDecorated;
  private final ActionParams mParams;
  
  public ActionDecorator(ActionParams paramActionParams, Action paramAction)
  {
    this.mParams = paramActionParams;
    this.mDecorated = paramAction;
  }
  
  private EnumSet<ActionType> combineActions(EnumSet<ActionType> paramEnumSet)
  {
    EnumSet localEnumSet = EnumSet.copyOf(this.mDecorated.getActions());
    localEnumSet.addAll(paramEnumSet);
    return localEnumSet;
  }
  
  public final void cancel()
  {
    this.mDecorated.cancel();
    onCancel();
  }
  
  public final void click(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.click(paramTouch);
    onClick(paramTouch);
  }
  
  public final void down(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.down(paramTouch);
    onDown(paramTouch);
  }
  
  public final void drag(DragEvent paramDragEvent)
  {
    this.mDecorated.drag(paramDragEvent);
    onDrag(paramDragEvent);
  }
  
  public final void dragClick(DragEvent paramDragEvent)
  {
    this.mDecorated.dragClick(paramDragEvent);
    onDragClick(paramDragEvent);
  }
  
  public final void flow(List<FlowEvent> paramList)
  {
    this.mDecorated.flow(paramList);
    onFlow(paramList);
  }
  
  public final void flowComplete(FlowEvent paramFlowEvent)
  {
    this.mDecorated.flowComplete(paramFlowEvent);
    onFlowComplete(paramFlowEvent);
  }
  
  public final void flowStarted()
  {
    this.mDecorated.flowStarted();
    onFlowStarted();
  }
  
  public final EnumSet<ActionType> getActions()
  {
    return combineActions(getUsedActions());
  }
  
  public DragBehaviour getDragBehaviour()
  {
    return DragBehaviour.combineBehaviours(this.mParams.mDragFilter.createDragBehaviour(this.mParams.mDragThreshold), this.mDecorated.getDragBehaviour());
  }
  
  protected DragFilter getDragFilter()
  {
    return this.mParams.mDragFilter;
  }
  
  public float getFlowXActivationThreshold()
  {
    return Math.max(this.mDecorated.getFlowXActivationThreshold(), this.mParams.mFlowXActivationThreshold);
  }
  
  public float getFlowYActivationThreshold()
  {
    return Math.max(this.mDecorated.getFlowYActivationThreshold(), this.mParams.mFlowYActivationThreshold);
  }
  
  public int getLongPressTimeOut()
  {
    return Math.max(this.mParams.mLongPressTimeout, this.mDecorated.getLongPressTimeOut());
  }
  
  public int getMultitapResetDelay()
  {
    return Math.min(this.mParams.mMultitapResetDelay, this.mDecorated.getMultitapResetDelay());
  }
  
  public RepeatBehaviour getRepeatBehaviour()
  {
    return this.mDecorated.getRepeatBehaviour().mergeWith(this.mParams.mRepeatBehaviour);
  }
  
  public float getSwipeMinimumXVelocity()
  {
    return Math.max(this.mDecorated.getSwipeMinimumXVelocity(), this.mParams.mSwipeMinXVelocity);
  }
  
  public float getSwipeMinimumYVelocity()
  {
    return Math.max(this.mDecorated.getSwipeMinimumYVelocity(), this.mParams.mSwipeMinYVelocity);
  }
  
  public float getSwipeXActivationThreshold()
  {
    return Math.max(this.mDecorated.getSwipeXActivationThreshold(), this.mParams.mSwipeXActivationThreshold);
  }
  
  public float getSwipeYActivationThreshold()
  {
    return Math.max(this.mDecorated.getSwipeYActivationThreshold(), this.mParams.mSwipeYActivationThreshold);
  }
  
  protected abstract EnumSet<ActionType> getUsedActions();
  
  public final void longClick(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.longClick(paramTouch);
    onLongClick(paramTouch);
  }
  
  public final void longPress()
  {
    this.mDecorated.longPress();
    onLongPress();
  }
  
  public final void multitap(TouchEvent.Touch paramTouch, int paramInt)
  {
    this.mDecorated.multitap(paramTouch, paramInt);
    onMultitap(paramTouch, paramInt);
  }
  
  protected void onCancel() {}
  
  protected void onClick(TouchEvent.Touch paramTouch) {}
  
  protected void onDown(TouchEvent.Touch paramTouch) {}
  
  protected void onDrag(DragEvent paramDragEvent) {}
  
  protected void onDragClick(DragEvent paramDragEvent) {}
  
  protected void onFlow(List<FlowEvent> paramList) {}
  
  protected void onFlowComplete(FlowEvent paramFlowEvent) {}
  
  protected void onFlowStarted() {}
  
  protected void onLongClick(TouchEvent.Touch paramTouch) {}
  
  protected void onLongPress() {}
  
  protected void onMultitap(TouchEvent.Touch paramTouch, int paramInt) {}
  
  protected void onRepeat(int paramInt) {}
  
  protected void onSlideIn(TouchEvent.Touch paramTouch) {}
  
  protected void onSlideOut(TouchEvent.Touch paramTouch) {}
  
  protected void onSwipeDown() {}
  
  protected void onSwipeLeft() {}
  
  protected void onSwipeRight() {}
  
  protected void onSwipeUp() {}
  
  protected void onUp(TouchEvent.Touch paramTouch) {}
  
  public final void repeat(int paramInt)
  {
    this.mDecorated.repeat(paramInt);
    onRepeat(paramInt);
  }
  
  public void setLoggableActions(EnumSet<ActionType> paramEnumSet)
  {
    this.mDecorated.setLoggableActions(paramEnumSet);
  }
  
  public boolean shouldBlockClicks()
  {
    return (this.mParams.mBlockClicks) && (this.mDecorated.shouldBlockClicks());
  }
  
  public final void slideIn(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.slideIn(paramTouch);
    onSlideIn(paramTouch);
  }
  
  public final void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.slideOut(paramTouch);
    onSlideOut(paramTouch);
  }
  
  public final void swipeDown()
  {
    this.mDecorated.swipeDown();
    onSwipeDown();
  }
  
  public final void swipeLeft()
  {
    this.mDecorated.swipeLeft();
    onSwipeLeft();
  }
  
  public final void swipeRight()
  {
    this.mDecorated.swipeRight();
    onSwipeRight();
  }
  
  public final void swipeUp()
  {
    this.mDecorated.swipeUp();
    onSwipeUp();
  }
  
  public final void up(TouchEvent.Touch paramTouch)
  {
    this.mDecorated.up(paramTouch);
    onUp(paramTouch);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.ActionDecorator
 * JD-Core Version:    0.7.0.1
 */