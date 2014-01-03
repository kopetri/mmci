package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype_fluency.Point;

public final class HardKeyInputEvent
  extends KeyInputEvent
{
  private boolean mIsCombiningAccent;
  private boolean mIsFullKeyboard;
  private final int mUnicodeCharacter;
  
  public HardKeyInputEvent(int paramInt, boolean paramBoolean)
  {
    super(new String(arrayOfInt, 0, 1));
    if ((0x80000000 & paramInt) != 0)
    {
      this.mIsCombiningAccent = true;
      paramInt &= 0x7FFFFFFF;
    }
    for (;;)
    {
      this.mUnicodeCharacter = paramInt;
      this.mIsFullKeyboard = paramBoolean;
      return;
      this.mIsCombiningAccent = false;
    }
  }
  
  public char getCharacter()
  {
    return (char)this.mUnicodeCharacter;
  }
  
  public String getCharacterAsStr()
  {
    return new String(Character.toChars(this.mUnicodeCharacter));
  }
  
  public CandidatesUpdateRequestType getEventType()
  {
    return CandidatesUpdateRequestType.HARD;
  }
  
  public Point getTouchPoint()
  {
    return null;
  }
  
  public boolean isCombiningAccent()
  {
    return this.mIsCombiningAccent;
  }
  
  public boolean sendKeyPressDirectlyToInput()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.HardKeyInputEvent
 * JD-Core Version:    0.7.0.1
 */