package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.TextUtils;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class KeyInputPredictionEventHandler
  implements ConnectionInputEventHandler
{
  private CommonPredictionActions mCommon;
  private Composer mComposer;
  private DefaultPredictionProvider mDefaultPredictionProvider;
  private Candidate mDelayedPrediction = null;
  private TextUtils mTextUtils;
  
  public KeyInputPredictionEventHandler(DefaultPredictionProvider paramDefaultPredictionProvider, TextUtils paramTextUtils, Composer paramComposer, CommonPredictionActions paramCommonPredictionActions)
  {
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
    this.mTextUtils = paramTextUtils;
    this.mComposer = paramComposer;
    this.mCommon = paramCommonPredictionActions;
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    boolean bool1 = true;
    try
    {
      if (!(paramConnectionInputEvent instanceof KeyInputEvent)) {
        throw new UnhandledInputEventException("Unrecognised Completion InputEvent:" + paramConnectionInputEvent.toString());
      }
    }
    finally
    {
      if (this.mDelayedPrediction != null) {
        this.mDelayedPrediction = null;
      }
    }
    KeyInputEvent localKeyInputEvent = (KeyInputEvent)paramConnectionInputEvent;
    int i = 1;
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    Candidate localCandidate = this.mDelayedPrediction;
    if (localCandidate == null) {
      localCandidate = this.mDefaultPredictionProvider.getDefaultPrediction(true, CandidatesUpdateRequestType.DEFAULT);
    }
    String str = localCandidate.toString();
    CommonPredictionActions localCommonPredictionActions;
    switch (str.length())
    {
    default: 
      if (i != 0)
      {
        localCommonPredictionActions = this.mCommon;
        if (str.equals(localTouchTypeExtractedText.getCurrentWord())) {
          break label322;
        }
      }
      break;
    }
    for (;;)
    {
      localCommonPredictionActions.acceptCandidate(paramInputConnectionProxy, localKeyInputEvent, localCandidate, str, localTouchTypeExtractedText, bool1, false);
      this.mComposer.resetComposingText(paramInputConnectionProxy, localTouchTypeExtractedText);
      for (;;)
      {
        if (this.mDelayedPrediction != null) {
          this.mDelayedPrediction = null;
        }
        return;
        LogUtil.w("KeyInputPredictionHandler", "No prediction available. Not auto-completing...");
        i = 0;
        break;
        if (!this.mTextUtils.isWordSeparator(str)) {
          break;
        }
        boolean bool2 = TouchTypeExtractedText.isSpace(localKeyInputEvent.getCharacter());
        i = 0;
        if (!bool2) {
          break;
        }
        int j = localTouchTypeExtractedText.getLastCharacter();
        int k = str.charAt(0);
        i = 0;
        if (j == k) {
          break;
        }
        boolean bool3 = this.mComposer.insertCharStr(localKeyInputEvent, paramInputConnectionProxy, str, localTouchTypeExtractedText);
        i = 0;
        if (!bool3) {
          break;
        }
        this.mComposer.resetComposingText(paramInputConnectionProxy, localTouchTypeExtractedText);
        i = 0;
        break;
        this.mCommon.removeSelectedText(paramInputConnectionProxy, localTouchTypeExtractedText);
      }
      label322:
      bool1 = false;
    }
  }
  
  public boolean logKeyStroke()
  {
    return false;
  }
  
  public void setDelayedPrediction(Candidate paramCandidate)
  {
    this.mDelayedPrediction = paramCandidate;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.KeyInputPredictionEventHandler
 * JD-Core Version:    0.7.0.1
 */