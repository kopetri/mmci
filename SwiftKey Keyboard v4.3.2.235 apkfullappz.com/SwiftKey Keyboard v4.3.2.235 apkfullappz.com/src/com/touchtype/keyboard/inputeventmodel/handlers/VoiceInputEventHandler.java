package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.TextUtils;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.TextInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.VoiceInputEvent;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class VoiceInputEventHandler
  implements ConnectionInputEventHandler
{
  private final Punctuator mPunctuator;
  private ConnectionInputEventHandler mTextInputEventHandler;
  private final TextUtils mTextUtils;
  
  public VoiceInputEventHandler(TextUtils paramTextUtils, Punctuator paramPunctuator)
  {
    this.mTextUtils = paramTextUtils;
    this.mPunctuator = paramPunctuator;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    CharSequence localCharSequence = localTouchTypeExtractedText.getCurrentWord();
    Sequence localSequence = localTouchTypeExtractedText.getContext();
    StringBuilder localStringBuilder = new StringBuilder(((VoiceInputEvent)paramConnectionInputEvent).getText());
    if (localStringBuilder.length() == 0) {
      return;
    }
    int i = localSequence.size();
    String str1 = null;
    if (i > 0) {
      str1 = localSequence.termAt(-1 + localSequence.size());
    }
    char c;
    if (localCharSequence.length() > 0)
    {
      c = localCharSequence.charAt(-1 + localCharSequence.length());
      if (c != 0) {
        break label266;
      }
    }
    label266:
    for (int j = 1;; j = 0)
    {
      boolean bool = this.mTextUtils.isSentenceSeparator(Character.toString(c));
      if ((j != 0) || (bool)) {
        localStringBuilder.replace(0, 1, Character.toString(Character.toUpperCase(localStringBuilder.charAt(0))));
      }
      String str2 = this.mPunctuator.getWordSeparator(localTouchTypeExtractedText);
      if ((j == 0) && (!localTouchTypeExtractedText.isLastCharacterWhitespace())) {
        localStringBuilder.insert(0, str2.charAt(0));
      }
      this.mTextInputEventHandler.handleInput(paramInputConnectionProxy, new TextInputEvent(localStringBuilder.toString()));
      this.mTextInputEventHandler.handleInput(paramInputConnectionProxy, new TextInputEvent(str2));
      return;
      c = '\000';
      if (str1 == null) {
        break;
      }
      c = str1.charAt(-1 + str1.length());
      break;
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mTextInputEventHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.VoiceInputEventHandler
 * JD-Core Version:    0.7.0.1
 */