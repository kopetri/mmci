package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.KeyboardState.ShiftMode;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.StateChangeInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.StateChangeInputEvent.Modifier;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;

public final class StateChangeInputEventHandler
  implements ConnectionInputEventHandler
{
  private KeyboardState mKeyboardState;
  
  public StateChangeInputEventHandler(KeyboardState paramKeyboardState)
  {
    this.mKeyboardState = paramKeyboardState;
  }
  
  private void cycleShiftState()
  {
    if (this.mKeyboardState.isHardCapsLockOn()) {
      return;
    }
    TouchTypeSoftKeyboard.ShiftState localShiftState = this.mKeyboardState.getShiftState();
    switch (1.$SwitchMap$com$touchtype$keyboard$service$TouchTypeSoftKeyboard$ShiftState[localShiftState.ordinal()])
    {
    default: 
      return;
    case 1: 
      if (this.mKeyboardState.getShiftMode() == KeyboardState.ShiftMode.CHARACTERS)
      {
        this.mKeyboardState.setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
        return;
      }
      this.mKeyboardState.setShiftState(TouchTypeSoftKeyboard.ShiftState.SHIFTED);
      return;
    case 2: 
      this.mKeyboardState.setShiftState(TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
      return;
    }
    this.mKeyboardState.setShiftState(TouchTypeSoftKeyboard.ShiftState.UNSHIFTED);
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    StateChangeInputEvent localStateChangeInputEvent = (StateChangeInputEvent)paramConnectionInputEvent;
    switch (1.$SwitchMap$com$touchtype$keyboard$inputeventmodel$events$StateChangeInputEvent$Modifier[localStateChangeInputEvent.getModifierType().ordinal()])
    {
    default: 
      return;
    }
    cycleShiftState();
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.StateChangeInputEventHandler
 * JD-Core Version:    0.7.0.1
 */