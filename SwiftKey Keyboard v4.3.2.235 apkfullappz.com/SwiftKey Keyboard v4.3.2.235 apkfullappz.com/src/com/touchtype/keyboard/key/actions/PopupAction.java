package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.key.PopupContent;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.key.callbacks.DragFilter;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class PopupAction
  extends ActionDecorator
  implements KeyStateListener
{
  private final PopupContent mContent;
  private final EnumSet<ActionType> mHideTypes;
  private boolean mIsFlowing = false;
  private final EnumSet<ActionType> mShowTypes;
  private final KeyState mState;
  
  public PopupAction(EnumSet<ActionType> paramEnumSet1, EnumSet<ActionType> paramEnumSet2, KeyState paramKeyState, PopupContent paramPopupContent, ActionParams paramActionParams, Action paramAction)
  {
    super(paramActionParams, paramAction);
    this.mShowTypes = paramEnumSet1;
    this.mHideTypes = paramEnumSet2;
    this.mState = paramKeyState;
    this.mContent = paramPopupContent;
    this.mState.addListener(KeyState.StateType.FLOW, this);
  }
  
  private void hide()
  {
    this.mState.setPopupContent(PopupContent.EMPTY_CONTENT);
  }
  
  private void show()
  {
    if (!this.mIsFlowing) {
      this.mState.setPopupContent(this.mContent);
    }
  }
  
  public EnumSet<ActionType> getUsedActions()
  {
    EnumSet localEnumSet = EnumSet.copyOf(this.mShowTypes);
    localEnumSet.addAll(this.mHideTypes);
    return localEnumSet;
  }
  
  protected void onCancel()
  {
    if (this.mShowTypes.contains(ActionType.CANCEL)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.CANCEL)) {
      hide();
    }
  }
  
  protected void onClick(TouchEvent.Touch paramTouch)
  {
    if (this.mShowTypes.contains(ActionType.CLICK)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.CLICK)) {
      hide();
    }
  }
  
  protected void onDown(TouchEvent.Touch paramTouch)
  {
    if (this.mShowTypes.contains(ActionType.DOWN)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.DOWN)) {
      hide();
    }
  }
  
  protected void onDrag(DragEvent paramDragEvent)
  {
    if ((this.mShowTypes.contains(ActionType.DRAG)) && (getDragFilter().getDrag(paramDragEvent) != null)) {
      show();
    }
    if ((this.mHideTypes.contains(ActionType.DRAG)) && (getDragFilter().getDrag(paramDragEvent) != null)) {
      hide();
    }
  }
  
  public void onKeyStateChanged(KeyState paramKeyState)
  {
    this.mIsFlowing = paramKeyState.isFlowing();
  }
  
  protected void onLongPress()
  {
    if (this.mShowTypes.contains(ActionType.LONGPRESS)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.LONGPRESS)) {
      hide();
    }
  }
  
  protected void onRepeat(int paramInt)
  {
    if (this.mShowTypes.contains(ActionType.REPEAT)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.REPEAT)) {
      hide();
    }
  }
  
  protected void onSlideIn(TouchEvent.Touch paramTouch)
  {
    if (this.mShowTypes.contains(ActionType.SLIDE_IN)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SLIDE_IN)) {
      hide();
    }
  }
  
  protected void onSlideOut(TouchEvent.Touch paramTouch)
  {
    if (this.mShowTypes.contains(ActionType.SLIDE_OUT)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SLIDE_OUT)) {
      hide();
    }
  }
  
  protected void onSwipeDown()
  {
    if (this.mShowTypes.contains(ActionType.SWIPE_DOWN)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SWIPE_DOWN)) {
      hide();
    }
  }
  
  protected void onSwipeLeft()
  {
    if (this.mShowTypes.contains(ActionType.SWIPE_LEFT)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SWIPE_LEFT)) {
      hide();
    }
  }
  
  protected void onSwipeRight()
  {
    if (this.mShowTypes.contains(ActionType.SWIPE_RIGHT)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SWIPE_RIGHT)) {
      hide();
    }
  }
  
  protected void onSwipeUp()
  {
    if (this.mShowTypes.contains(ActionType.SWIPE_UP)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.SWIPE_UP)) {
      hide();
    }
  }
  
  protected void onUp(TouchEvent.Touch paramTouch)
  {
    if (this.mShowTypes.contains(ActionType.UP)) {
      show();
    }
    if (this.mHideTypes.contains(ActionType.UP)) {
      hide();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.PopupAction
 * JD-Core Version:    0.7.0.1
 */