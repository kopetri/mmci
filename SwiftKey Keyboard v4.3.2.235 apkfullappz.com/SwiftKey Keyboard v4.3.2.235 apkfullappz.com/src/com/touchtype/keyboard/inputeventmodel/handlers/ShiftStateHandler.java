package com.touchtype.keyboard.inputeventmodel.handlers;

import android.os.Build.VERSION;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.KeyboardState.ShiftMode;
import com.touchtype.keyboard.inputeventmodel.TextUtils;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class ShiftStateHandler
  implements ConnectionInputEventHandler
{
  private KeyboardState mKeyboardState;
  private TextUtils mTextUtils;
  
  public ShiftStateHandler(KeyboardState paramKeyboardState, TextUtils paramTextUtils)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mTextUtils = paramTextUtils;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    TouchTypeSoftKeyboard.ShiftState localShiftState1 = this.mKeyboardState.getShiftState();
    TouchTypeSoftKeyboard.ShiftState localShiftState2 = localShiftState1;
    if ((Build.VERSION.SDK_INT >= 11) && (this.mKeyboardState.isHardCapsLockOn())) {
      localShiftState2 = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
    }
    for (;;)
    {
      if (localShiftState2 != localShiftState1) {
        this.mKeyboardState.setShiftState(localShiftState2);
      }
      return;
      KeyboardState.ShiftMode localShiftMode = this.mKeyboardState.getShiftMode();
      int i;
      switch (1.$SwitchMap$com$touchtype$keyboard$inputeventmodel$KeyboardState$ShiftMode[localShiftMode.ordinal()])
      {
      default: 
        i = localTouchTypeExtractedText.getLastCharacter();
        if (i == 0) {
          localShiftState2 = this.mKeyboardState.getInitialShiftState();
        }
        break;
      case 1: 
        if (localShiftState1 == TouchTypeSoftKeyboard.ShiftState.SHIFTED) {
          localShiftState2 = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
        }
        break;
      case 2: 
        if (localShiftState1 != TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED)
        {
          localShiftState2 = TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED;
          continue;
          if ((i == 10) || ((TouchTypeExtractedText.isSpace(i)) && ((localShiftMode == KeyboardState.ShiftMode.WORDS) || (this.mTextUtils.isSentenceSeparator(Character.toString(localTouchTypeExtractedText.getLastNonSpaceCharacter()))))))
          {
            if (localShiftState1 == TouchTypeSoftKeyboard.ShiftState.UNSHIFTED) {
              localShiftState2 = TouchTypeSoftKeyboard.ShiftState.SHIFTED;
            }
          }
          else if (localShiftState1 == TouchTypeSoftKeyboard.ShiftState.SHIFTED) {
            localShiftState2 = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
          }
        }
        break;
      }
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.ShiftStateHandler
 * JD-Core Version:    0.7.0.1
 */