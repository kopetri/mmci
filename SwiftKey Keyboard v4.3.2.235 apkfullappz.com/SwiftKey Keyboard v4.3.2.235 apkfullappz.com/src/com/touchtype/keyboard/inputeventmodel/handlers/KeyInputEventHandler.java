package com.touchtype.keyboard.inputeventmodel.handlers;

import android.view.KeyEvent;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy.SelectionDeletionType;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.HardKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class KeyInputEventHandler
  implements ConnectionInputEventHandler
{
  private Composer mComposer;
  private KeyboardState mKeyboardState;
  private Punctuator mPunctuator;
  private boolean resetComposingText;
  
  public KeyInputEventHandler(Punctuator paramPunctuator, Composer paramComposer, KeyboardState paramKeyboardState)
  {
    this.mPunctuator = paramPunctuator;
    this.mComposer = paramComposer;
    this.mKeyboardState = paramKeyboardState;
  }
  
  private boolean isAutoCompletingSpace(KeyInputEvent paramKeyInputEvent, ImmutableExtractedText paramImmutableExtractedText)
  {
    return (TouchTypeExtractedText.isSpace(paramKeyInputEvent.getCharacter())) && (this.mKeyboardState.shouldAutocomplete(paramKeyInputEvent, paramImmutableExtractedText)) && (!paramKeyInputEvent.insertingPrediction());
  }
  
  private void punctuate(KeyInputEvent paramKeyInputEvent, ImmutableExtractedText paramImmutableExtractedText, InputConnectionProxy paramInputConnectionProxy, String paramString)
  {
    if (isAutoCompletingSpace(paramKeyInputEvent, paramImmutableExtractedText))
    {
      this.mPunctuator.punctuate(paramKeyInputEvent, paramInputConnectionProxy, this.mPunctuator.getPredictionTrigger());
      return;
    }
    this.mPunctuator.punctuate(paramKeyInputEvent, paramInputConnectionProxy, paramString);
  }
  
  private void sendCharKeyPress(CharSequence paramCharSequence, InputConnectionProxy paramInputConnectionProxy, KeyInputEvent paramKeyInputEvent)
  {
    int i = 0;
    if (i < paramCharSequence.length())
    {
      char c = paramCharSequence.charAt(i);
      if (((paramKeyInputEvent instanceof SoftKeyInputEvent)) && (Character.isDigit(c))) {
        paramInputConnectionProxy.sendDownUpKeyEvents(7 + (c - '0'));
      }
      for (;;)
      {
        i++;
        break;
        paramInputConnectionProxy.commitText(String.valueOf(paramCharSequence));
      }
    }
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    this.resetComposingText = false;
    if (!(paramConnectionInputEvent instanceof KeyInputEvent)) {
      throw new UnhandledInputEventException("Unrecognised Completion InputEvent:" + paramConnectionInputEvent.toString());
    }
    KeyInputEvent localKeyInputEvent = (KeyInputEvent)paramConnectionInputEvent;
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    if (((localKeyInputEvent instanceof HardKeyInputEvent)) && (((HardKeyInputEvent)localKeyInputEvent).isCombiningAccent())) {}
    for (int i = 1; localKeyInputEvent.getText() == null; i = 0) {
      throw new UnhandledInputEventException("KeyInputEvent has no text");
    }
    if (localKeyInputEvent.sendKeyPressDirectlyToInput())
    {
      paramInputConnectionProxy.finishComposingText();
      paramInputConnectionProxy.sendKeyChar(localKeyInputEvent.getCharacter());
    }
    while (this.resetComposingText)
    {
      this.mComposer.resetComposingText(paramInputConnectionProxy, localTouchTypeExtractedText);
      return;
      String str1 = this.mComposer.capitalise(localKeyInputEvent, localKeyInputEvent.getCharacterAsStr());
      if (localTouchTypeExtractedText.getSelectionEndInField() - localTouchTypeExtractedText.getSelectionStartInField() == 1)
      {
        char c = (char)KeyEvent.getDeadChar(localTouchTypeExtractedText.getFirstSelectedCharacter(), str1.charAt(0));
        if (c != 0) {
          str1 = Character.toString(c);
        }
      }
      if (!this.mKeyboardState.composeTextWordByWord())
      {
        String str3 = str1;
        paramInputConnectionProxy.finishComposingText();
        sendCharKeyPress(str3, paramInputConnectionProxy, localKeyInputEvent);
        if (i != 0) {
          paramInputConnectionProxy.setSelection(-1 + localTouchTypeExtractedText.getSelectionStartInField(), localTouchTypeExtractedText.getSelectionStartInField());
        }
      }
      else if (localTouchTypeExtractedText.getSelectionStartInField() != localTouchTypeExtractedText.getSelectionEndInField())
      {
        InputConnectionProxy.SelectionDeletionType localSelectionDeletionType;
        label308:
        String str2;
        if (!this.mKeyboardState.shouldReplaceSelectionWithoutDeleting())
        {
          if (isAutoCompletingSpace(localKeyInputEvent, localTouchTypeExtractedText))
          {
            localSelectionDeletionType = InputConnectionProxy.SelectionDeletionType.REPLACING_WITH_PREDICTION;
            paramInputConnectionProxy.deleteSelection(localTouchTypeExtractedText, localSelectionDeletionType);
          }
        }
        else
        {
          str2 = str1;
          if (str1.length() != 1) {
            break label418;
          }
          paramInputConnectionProxy.setComposingRegion(localTouchTypeExtractedText.getSelectionEndInField() - localTouchTypeExtractedText.getCurrentWord().length(), localTouchTypeExtractedText.getSelectionEndInField(), localTouchTypeExtractedText);
          punctuate(localKeyInputEvent, localTouchTypeExtractedText, paramInputConnectionProxy, str1);
        }
        for (;;)
        {
          if (TouchTypeExtractedText.isSpace(str2.charAt(-1 + str2.length()))) {
            break label428;
          }
          this.resetComposingText = true;
          break;
          localSelectionDeletionType = InputConnectionProxy.SelectionDeletionType.REPLACING_WITH_KEYPRESS;
          break label308;
          label418:
          paramInputConnectionProxy.commitText(str2);
        }
      }
      else
      {
        label428:
        if (i != 0)
        {
          paramInputConnectionProxy.finishComposingText();
          paramInputConnectionProxy.commitText(str1);
          paramInputConnectionProxy.setSelection(-1 + localTouchTypeExtractedText.getSelectionEndInField(), localTouchTypeExtractedText.getSelectionEndInField());
          return;
        }
        punctuate(localKeyInputEvent, localTouchTypeExtractedText, paramInputConnectionProxy, str1);
      }
    }
    this.mComposer.updateShiftState(paramInputConnectionProxy, paramConnectionInputEvent);
  }
  
  public boolean logKeyStroke()
  {
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.KeyInputEventHandler
 * JD-Core Version:    0.7.0.1
 */