package com.touchtype.keyboard.inputeventmodel.events;

import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class VoiceInputEvent
  extends TextInputEvent
{
  public VoiceInputEvent(TouchTypeExtractedText paramTouchTypeExtractedText, CharSequence paramCharSequence)
  {
    super(paramCharSequence);
  }
  
  public String toString()
  {
    return "Voice(\"" + getText() + "\")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.VoiceInputEvent
 * JD-Core Version:    0.7.0.1
 */