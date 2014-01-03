package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.PopupContent;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragEvent.Range;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.key.callbacks.DragFilterFactory;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class SlidingPopupAction
  extends ActionDecorator
{
  private final PopupContent mContent;
  private final KeyState mState;
  
  public SlidingPopupAction(KeyState paramKeyState, PopupContent paramPopupContent, int paramInt, boolean paramBoolean, float paramFloat, Action paramAction)
  {
    super(createActionParams(paramInt, paramFloat, paramBoolean), paramAction);
    this.mState = paramKeyState;
    this.mContent = paramPopupContent;
  }
  
  private static ActionParams createActionParams(int paramInt, float paramFloat, boolean paramBoolean)
  {
    return new ActionParams.ActionParamsBuilder().dragBehaviour(DragFilterFactory.makeFilter(DragEvent.Range.LEFT_RIGHT), paramFloat, paramFloat).longPressTimeout(paramInt).blockClicksOnLongpress(paramBoolean).build();
  }
  
  public EnumSet<ActionType> getUsedActions()
  {
    return EnumSet.of(ActionType.DRAG, ActionType.LONGPRESS, ActionType.DOWN, ActionType.UP, ActionType.CANCEL);
  }
  
  protected void onCancel()
  {
    this.mState.setPopupContent(PopupContent.EMPTY_CONTENT);
  }
  
  protected void onDrag(DragEvent paramDragEvent)
  {
    if (getDragFilter().getDrag(paramDragEvent) != null) {
      this.mState.setPopupContent(this.mContent);
    }
    this.mContent.setDrag(paramDragEvent);
  }
  
  protected void onLongPress()
  {
    this.mState.setPopupContent(this.mContent);
  }
  
  protected void onUp(TouchEvent.Touch paramTouch)
  {
    this.mState.setPopupContent(PopupContent.EMPTY_CONTENT);
    this.mContent.setDrag(null);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.SlidingPopupAction
 * JD-Core Version:    0.7.0.1
 */