package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.handlers.CursorInputEventHandler;
import com.touchtype.keyboard.inputeventmodel.handlers.ShiftStateHandler;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype_fluency.Hangul;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Telex;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class Composer
{
  private CursorInputEventHandler mCursorInputEventHandler;
  private KeyboardState mKeyboardState;
  private ShiftStateHandler mShiftStateInputEventHandler;
  private TextUtils mTextUtils;
  
  public Composer(KeyboardState paramKeyboardState, TextUtils paramTextUtils)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mTextUtils = paramTextUtils;
  }
  
  public String capitalise(KeyInputEvent paramKeyInputEvent, String paramString)
  {
    if ((this.mKeyboardState.getShiftState() != TouchTypeSoftKeyboard.ShiftState.UNSHIFTED) && (!(paramKeyInputEvent instanceof SoftKeyInputEvent))) {
      paramString = paramString.toUpperCase();
    }
    return paramString;
  }
  
  public boolean insertCharStr(KeyInputEvent paramKeyInputEvent, InputConnectionProxy paramInputConnectionProxy, String paramString, ImmutableExtractedText paramImmutableExtractedText)
  {
    CharSequence localCharSequence;
    String str;
    char c;
    if ((!TouchTypeExtractedText.isSpace(paramString.charAt(0))) && (!this.mTextUtils.isWordSeparator(paramString.toString())))
    {
      localCharSequence = paramImmutableExtractedText.getCurrentWord();
      if (this.mKeyboardState.useTelexConversionLayer())
      {
        str = Telex.join(localCharSequence.toString() + paramString);
        c = paramKeyInputEvent.getCharacter();
        Point localPoint = paramKeyInputEvent.getTouchPoint();
        if ((localPoint == null) || (!KeyPressModelImpl.canUseTouchLocations(c))) {
          break label178;
        }
        paramInputConnectionProxy.setComposingTextByAppendingCharacter(str, paramImmutableExtractedText, localPoint);
        label115:
        if (paramImmutableExtractedText.getCurrentWord().length() == str.length()) {
          break label257;
        }
      }
    }
    label178:
    do
    {
      return true;
      CharSequence[] arrayOfCharSequence = new CharSequence[2];
      arrayOfCharSequence[0] = Hangul.split(localCharSequence.toString());
      arrayOfCharSequence[1] = paramString;
      str = Hangul.join(android.text.TextUtils.concat(arrayOfCharSequence).toString());
      break;
      paramInputConnectionProxy.setComposingTextByAppendingCharacter(str, paramImmutableExtractedText, c);
      break label115;
      if ((!TouchTypeExtractedText.isSpace(paramKeyInputEvent.getCharacter())) && (TouchTypeExtractedText.isSpace(paramString.charAt(0))) && (tryAndReuseSpace(paramInputConnectionProxy, paramImmutableExtractedText))) {
        return false;
      }
      paramInputConnectionProxy.finishComposingText();
      paramInputConnectionProxy.commitText(paramString);
    } while (!TouchTypeExtractedText.isSpace(paramString.charAt(0)));
    label257:
    return false;
  }
  
  public void resetComposingText(InputConnectionProxy paramInputConnectionProxy, ImmutableExtractedText paramImmutableExtractedText)
  {
    int i = paramImmutableExtractedText.getSelectionEndInField();
    this.mCursorInputEventHandler.handleInput(paramInputConnectionProxy, new SelectionChangedInputEvent(-1, -1, i, i, -1, i));
  }
  
  public void setDelegateHandlers(CursorInputEventHandler paramCursorInputEventHandler, ShiftStateHandler paramShiftStateHandler)
  {
    this.mCursorInputEventHandler = paramCursorInputEventHandler;
    this.mShiftStateInputEventHandler = paramShiftStateHandler;
  }
  
  public boolean tryAndReuseSpace(InputConnectionProxy paramInputConnectionProxy, ImmutableExtractedText paramImmutableExtractedText)
  {
    if ((TouchTypeExtractedText.isSpace(paramImmutableExtractedText.getNextCharacter())) && (!this.mKeyboardState.isEditSpacingAssistanceDisabled()))
    {
      paramInputConnectionProxy.finishComposingText();
      if (this.mKeyboardState.movingOverTrailingSpaceDoesntWork()) {
        paramInputConnectionProxy.deleteSurroundingText(0, 1);
      }
    }
    else
    {
      return false;
    }
    paramInputConnectionProxy.setSelection(1 + paramImmutableExtractedText.getSelectionEndInField(), 1 + paramImmutableExtractedText.getSelectionEndInField());
    return true;
  }
  
  public void updateShiftState(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
  {
    if (!this.mKeyboardState.isSoftShiftPressed()) {
      this.mShiftStateInputEventHandler.handleInput(paramInputConnectionProxy, paramConnectionInputEvent);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.Composer
 * JD-Core Version:    0.7.0.1
 */