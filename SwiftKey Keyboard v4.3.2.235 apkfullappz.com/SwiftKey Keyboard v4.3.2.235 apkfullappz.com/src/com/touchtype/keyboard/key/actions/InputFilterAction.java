package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import java.util.EnumSet;

public final class InputFilterAction
  extends GenericActionDecorator
{
  private final String mDefaultText;
  private final InputEventModel mInputEventModel;
  private final int mInputFilterIndex;
  
  public InputFilterAction(EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction, String paramString, InputEventModel paramInputEventModel, int paramInt)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mDefaultText = paramString;
    this.mInputEventModel = paramInputEventModel;
    this.mInputFilterIndex = paramInt;
  }
  
  protected void act()
  {
    this.mInputEventModel.onInputFilterInput(this.mInputFilterIndex, this.mDefaultText);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.InputFilterAction
 * JD-Core Version:    0.7.0.1
 */