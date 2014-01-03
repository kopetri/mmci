package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.ExtendedKeyEvent;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;

public final class TextAction
  extends GenericActionDecorator
{
  private final boolean mBypassBuffers;
  private final boolean mIncludeCoords;
  private final InputEventModel mInputEventModel;
  private final String mText;
  
  public TextAction(InputEventModel paramInputEventModel, String paramString, Action paramAction)
  {
    this(EnumSet.of(ActionType.CLICK), paramInputEventModel, paramString, false, false, ActionParams.EMPTY_PARAMS, paramAction);
  }
  
  public TextAction(EnumSet<ActionType> paramEnumSet, InputEventModel paramInputEventModel, String paramString, Action paramAction)
  {
    this(paramEnumSet, paramInputEventModel, paramString, false, false, ActionParams.EMPTY_PARAMS, paramAction);
  }
  
  public TextAction(EnumSet<ActionType> paramEnumSet, InputEventModel paramInputEventModel, String paramString, boolean paramBoolean1, boolean paramBoolean2, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mInputEventModel = paramInputEventModel;
    this.mText = paramString;
    this.mIncludeCoords = paramBoolean1;
    this.mBypassBuffers = paramBoolean2;
  }
  
  protected void act()
  {
    this.mInputEventModel.onSoftKey(ExtendedKeyEvent.createKeyEventFromText(this.mText, this.mBypassBuffers));
  }
  
  protected void act(TouchEvent.Touch paramTouch)
  {
    if (this.mIncludeCoords)
    {
      this.mInputEventModel.onSoftKey(ExtendedKeyEvent.createKeyEventFromText(this.mText, this.mBypassBuffers, paramTouch.getX(), paramTouch.getY()));
      return;
    }
    act();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.TextAction
 * JD-Core Version:    0.7.0.1
 */