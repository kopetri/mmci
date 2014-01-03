package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype_fluency.Point;

public abstract class KeyInputEvent
  extends TextInputEvent
{
  public KeyInputEvent(CharSequence paramCharSequence)
  {
    super(paramCharSequence);
  }
  
  public abstract char getCharacter();
  
  public abstract String getCharacterAsStr();
  
  public abstract Point getTouchPoint();
  
  public abstract boolean sendKeyPressDirectlyToInput();
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("KeyInputEvent(");
    int[] arrayOfInt = new int[1];
    arrayOfInt[0] = getCharacter();
    return new String(arrayOfInt, 0, 1) + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent
 * JD-Core Version:    0.7.0.1
 */