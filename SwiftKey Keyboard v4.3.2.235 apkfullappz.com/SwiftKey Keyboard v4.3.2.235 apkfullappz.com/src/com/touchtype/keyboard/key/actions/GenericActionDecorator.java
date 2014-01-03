package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public abstract class GenericActionDecorator
  extends ActionDecorator
{
  protected final EnumSet<ActionType> mTypes;
  
  public GenericActionDecorator(EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramActionParams, paramAction);
    this.mTypes = paramEnumSet;
  }
  
  protected abstract void act();
  
  protected void act(TouchEvent.Touch paramTouch)
  {
    act();
  }
  
  protected EnumSet<ActionType> getUsedActions()
  {
    return this.mTypes;
  }
  
  protected void onCancel()
  {
    if (this.mTypes.contains(ActionType.CANCEL)) {
      act();
    }
  }
  
  protected void onClick(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.CLICK)) {
      act(paramTouch);
    }
  }
  
  protected void onDown(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.DOWN)) {
      act(paramTouch);
    }
  }
  
  protected void onDrag(DragEvent paramDragEvent)
  {
    if (this.mTypes.contains(ActionType.DRAG)) {
      act();
    }
  }
  
  protected void onDragClick(DragEvent paramDragEvent)
  {
    if ((this.mTypes.contains(ActionType.DRAG_CLICK)) && (getDragFilter().getDrag(paramDragEvent) != null)) {
      act();
    }
  }
  
  protected void onLongClick(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.LONGCLICK)) {
      act(paramTouch);
    }
  }
  
  protected void onLongPress()
  {
    if (this.mTypes.contains(ActionType.LONGPRESS)) {
      act();
    }
  }
  
  protected void onMultitap(TouchEvent.Touch paramTouch, int paramInt)
  {
    if (this.mTypes.contains(ActionType.MULTITAP)) {
      act(paramTouch);
    }
  }
  
  protected void onRepeat(int paramInt)
  {
    if (this.mTypes.contains(ActionType.REPEAT)) {
      act();
    }
  }
  
  protected void onSlideIn(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.SLIDE_IN)) {
      act(paramTouch);
    }
  }
  
  protected void onSlideOut(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.SLIDE_OUT)) {
      act(paramTouch);
    }
  }
  
  protected void onSwipeDown()
  {
    if (this.mTypes.contains(ActionType.SWIPE_DOWN)) {
      act();
    }
  }
  
  protected void onSwipeLeft()
  {
    if (this.mTypes.contains(ActionType.SWIPE_LEFT)) {
      act();
    }
  }
  
  protected void onSwipeRight()
  {
    if (this.mTypes.contains(ActionType.SWIPE_RIGHT)) {
      act();
    }
  }
  
  protected void onSwipeUp()
  {
    if (this.mTypes.contains(ActionType.SWIPE_UP)) {
      act();
    }
  }
  
  protected void onUp(TouchEvent.Touch paramTouch)
  {
    if (this.mTypes.contains(ActionType.UP)) {
      act(paramTouch);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.GenericActionDecorator
 * JD-Core Version:    0.7.0.1
 */