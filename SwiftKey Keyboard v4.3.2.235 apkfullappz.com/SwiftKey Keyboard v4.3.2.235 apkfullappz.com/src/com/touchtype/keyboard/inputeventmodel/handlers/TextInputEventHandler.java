package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.TextInputEvent;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class TextInputEventHandler
  implements ConnectionInputEventHandler
{
  private ConnectionInputEventHandler mCursorInputEventHandler;
  private KeyboardState mKeyboardState;
  
  public TextInputEventHandler(KeyboardState paramKeyboardState)
  {
    this.mKeyboardState = paramKeyboardState;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    CharSequence localCharSequence = ((TextInputEvent)paramConnectionInputEvent).getText();
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    if (localCharSequence.length() > 0)
    {
      int i = localCharSequence.charAt(-1 + localCharSequence.length());
      int j = localCharSequence.charAt(0);
      if ((this.mKeyboardState.isUrlBar()) && (j == 46))
      {
        this.mKeyboardState.setDumbInputMode(true);
        if (TouchTypeExtractedText.isSpace(localTouchTypeExtractedText.getLastCharacter()))
        {
          int m = localTouchTypeExtractedText.getSelectionEndInField() - localTouchTypeExtractedText.getSelectionStartInField();
          if (m != 0)
          {
            paramInputConnectionProxy.setSelection(localTouchTypeExtractedText.getSelectionEndInField(), localTouchTypeExtractedText.getSelectionEndInField());
            paramInputConnectionProxy.finishComposingText();
            paramInputConnectionProxy.deleteSurroundingText(m, 0);
          }
          paramInputConnectionProxy.finishComposingText();
          paramInputConnectionProxy.deleteSurroundingText(1, 0);
        }
      }
      paramInputConnectionProxy.finishComposingText();
      paramInputConnectionProxy.commitText(localCharSequence);
      if (!TouchTypeExtractedText.isSpace(i))
      {
        int k = localTouchTypeExtractedText.getSelectionStartInField();
        this.mCursorInputEventHandler.handleInput(paramInputConnectionProxy, new SelectionChangedInputEvent(-1, -1, k, k, -1, k));
      }
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mCursorInputEventHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.TextInputEventHandler
 * JD-Core Version:    0.7.0.1
 */