package com.touchtype.keyboard.inputeventmodel.events;

import android.view.KeyEvent;
import com.touchtype.keyboard.ExtendedKeyEvent;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.view.touch.LegacyTouchUtils;
import com.touchtype_fluency.Point;

public final class SoftKeyInputEvent
  extends KeyInputEvent
{
  private final int mKeyCode;
  private final String mKeyText;
  private final LegacyTouchUtils mLTUs;
  private final boolean mSendKeyPressDirectlyToInput;
  private final Point mTouchPoint;
  
  public SoftKeyInputEvent(char paramChar, boolean paramBoolean)
  {
    super(String.valueOf(paramChar));
    this.mKeyCode = paramChar;
    this.mTouchPoint = null;
    this.mKeyText = null;
    this.mLTUs = null;
    this.mSendKeyPressDirectlyToInput = paramBoolean;
  }
  
  public SoftKeyInputEvent(KeyEvent paramKeyEvent, LegacyTouchUtils paramLegacyTouchUtils)
  {
    super(new String(arrayOfInt, 0, 1));
    this.mLTUs = paramLegacyTouchUtils;
    this.mKeyCode = paramKeyEvent.getKeyCode();
    if ((paramKeyEvent instanceof ExtendedKeyEvent)) {
      this.mTouchPoint = ((ExtendedKeyEvent)paramKeyEvent).getTouchPoint();
    }
    for (this.mKeyText = ((ExtendedKeyEvent)paramKeyEvent).getKeyText();; this.mKeyText = null)
    {
      this.mSendKeyPressDirectlyToInput = false;
      return;
      this.mTouchPoint = null;
    }
  }
  
  public char getCharacter()
  {
    return (char)this.mKeyCode;
  }
  
  public String getCharacterAsStr()
  {
    if (this.mKeyText != null) {
      return this.mKeyText;
    }
    return new String(Character.toChars(this.mKeyCode));
  }
  
  public CandidatesUpdateRequestType getEventType()
  {
    return CandidatesUpdateRequestType.TAP;
  }
  
  public Point getTouchPoint()
  {
    if (this.mTouchPoint != null) {
      return this.mLTUs.transformApproxAspectRatio(this.mTouchPoint);
    }
    return null;
  }
  
  public boolean sendKeyPressDirectlyToInput()
  {
    return this.mSendKeyPressDirectlyToInput;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent
 * JD-Core Version:    0.7.0.1
 */