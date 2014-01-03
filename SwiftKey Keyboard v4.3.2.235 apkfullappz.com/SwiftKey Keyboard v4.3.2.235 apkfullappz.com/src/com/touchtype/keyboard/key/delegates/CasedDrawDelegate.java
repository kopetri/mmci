package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;

public final class CasedDrawDelegate
  extends SimpleDrawDelegate
  implements OnShiftStateChangedListener
{
  private TouchTypeSoftKeyboard.ShiftState mShiftState;
  
  public CasedDrawDelegate(KeyStyle.StyleId paramStyleId, KeyArea paramKeyArea, KeyContent paramKeyContent, KeyState paramKeyState, InputEventModel paramInputEventModel)
  {
    super(paramStyleId, paramKeyArea, paramKeyContent, paramKeyState);
    this.mShiftState = paramInputEventModel.getShiftState();
    paramInputEventModel.addShiftStateChangedListener(this);
  }
  
  public KeyContent getContent()
  {
    return super.getContent().applyShiftState(this.mShiftState);
  }
  
  public void handleShiftStateChanged(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    this.mShiftState = paramShiftState;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.CasedDrawDelegate
 * JD-Core Version:    0.7.0.1
 */