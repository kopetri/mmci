package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.events.CompletionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class CompletionInputEventHandler
  implements ConnectionInputEventHandler
{
  private final Punctuator mPunctuator;
  
  public CompletionInputEventHandler(Punctuator paramPunctuator)
  {
    this.mPunctuator = paramPunctuator;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    if (!(paramConnectionInputEvent instanceof CompletionInputEvent)) {
      throw new UnhandledInputEventException("CompletionInputEventHandler received non-CompletionInputEvent.");
    }
    int i = paramInputConnectionProxy.getTouchTypeExtractedText(false).getText().length();
    paramInputConnectionProxy.commitCompletion(((CompletionInputEvent)paramConnectionInputEvent).getCompletion());
    if (paramInputConnectionProxy.getTouchTypeExtractedText(false).getText().length() != i) {
      paramInputConnectionProxy.commitText(this.mPunctuator.getWordSeparator(paramInputConnectionProxy.getTouchTypeExtractedText(false)));
    }
    if (paramInputConnectionProxy.getTouchTypeExtractedText(false).getText().length() == i + 1) {
      paramInputConnectionProxy.deleteSurroundingText(1, 0);
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.CompletionInputEventHandler
 * JD-Core Version:    0.7.0.1
 */