package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.TextUtils;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public class PredictionInputEventHandler
  implements ConnectionInputEventHandler
{
  private static final String TAG = PredictionInputEventHandler.class.getSimpleName();
  private final CommonPredictionActions mCommon;
  private final Composer mComposer;
  private ConnectionInputEventHandler mKeyInputEventHandler;
  private final KeyboardState mKeyboardState;
  private final Punctuator mPunctuator;
  private final TextUtils mTextUtils;
  
  public PredictionInputEventHandler(Punctuator paramPunctuator, KeyboardState paramKeyboardState, TextUtils paramTextUtils, Composer paramComposer, CommonPredictionActions paramCommonPredictionActions)
  {
    this.mPunctuator = paramPunctuator;
    this.mKeyboardState = paramKeyboardState;
    this.mTextUtils = paramTextUtils;
    this.mComposer = paramComposer;
    this.mCommon = paramCommonPredictionActions;
  }
  
  private boolean shouldAcceptCandidate(ImmutableExtractedText paramImmutableExtractedText, String paramString)
  {
    return (paramString.length() != 1) || (!this.mTextUtils.isWordSeparator(paramString)) || (paramImmutableExtractedText.getLastCharacter() == paramString.charAt(0));
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    if (!(paramConnectionInputEvent instanceof PredictionInputEvent)) {
      throw new UnhandledInputEventException("Unrecognised Completion InputEvent:" + paramConnectionInputEvent.toString());
    }
    PredictionInputEvent localPredictionInputEvent = (PredictionInputEvent)paramConnectionInputEvent;
    Candidate localCandidate = localPredictionInputEvent.getCandidate();
    String str = localCandidate.toString();
    this.mKeyboardState.setDumbInputMode(false);
    if (str.length() == 0) {
      LogUtil.w(TAG, "No prediction available. Ignoring...");
    }
    for (;;)
    {
      return;
      TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
      if (shouldAcceptCandidate(localTouchTypeExtractedText, str))
      {
        this.mCommon.acceptCandidate(paramInputConnectionProxy, localPredictionInputEvent, localCandidate, str, localTouchTypeExtractedText, false, false);
        boolean bool = this.mComposer.tryAndReuseSpace(paramInputConnectionProxy, localTouchTypeExtractedText);
        localSoftKeyInputEvent = null;
        if (bool) {}
      }
      for (SoftKeyInputEvent localSoftKeyInputEvent = new SoftKeyInputEvent(this.mPunctuator.getWordSeparator(localTouchTypeExtractedText).charAt(0), false); localSoftKeyInputEvent != null; localSoftKeyInputEvent = new SoftKeyInputEvent(str.charAt(0), false))
      {
        this.mKeyInputEventHandler.handleInput(paramInputConnectionProxy, localSoftKeyInputEvent.markAsInsertingCandidate(localCandidate));
        return;
        this.mCommon.removeSelectedText(paramInputConnectionProxy, localTouchTypeExtractedText);
      }
    }
  }
  
  public boolean logKeyStroke()
  {
    return true;
  }
  
  public void setDelegateHandlers(ConnectionInputEventHandler paramConnectionInputEventHandler)
  {
    this.mKeyInputEventHandler = paramConnectionInputEventHandler;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.PredictionInputEventHandler
 * JD-Core Version:    0.7.0.1
 */