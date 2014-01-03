package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.Composer;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.KeyInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SoftKeyInputEvent;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Punctuator.Action;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class PunctuatorImpl
  implements Punctuator
{
  private static final String TAG = Punctuator.class.getSimpleName();
  private Composer mComposer;
  private DefaultPredictionProvider mDefaultPredictionProvider;
  private Candidate mDelayedPrediction;
  private com.touchtype_fluency.Punctuator mFluencyPunctuator = null;
  private KeyInputEventHandler mKeyInputEventHandler;
  private KeyboardState mKeyboardState;
  private final Learner mLearner;
  private KeyInputPredictionEventHandler mPredictionInputEventHandler;
  
  public PunctuatorImpl(KeyboardState paramKeyboardState, Composer paramComposer, DefaultPredictionProvider paramDefaultPredictionProvider, Learner paramLearner)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
    this.mComposer = paramComposer;
    this.mLearner = paramLearner;
  }
  
  private void processAction(KeyInputEvent paramKeyInputEvent, String paramString, InputConnectionProxy paramInputConnectionProxy, Punctuator.Action paramAction)
  {
    int i = 1;
    if ((paramKeyInputEvent.getEventType() == CandidatesUpdateRequestType.HARD) && (!this.mKeyboardState.isHardKBSmartPunctuationEnabled()) && (!paramString.equals(getPredictionTrigger()))) {
      i = 0;
    }
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    int j = 1.$SwitchMap$com$touchtype_fluency$Punctuator$Action[paramAction.ordinal()];
    boolean bool1 = false;
    switch (j)
    {
    }
    for (;;)
    {
      if (bool1) {
        this.mComposer.resetComposingText(paramInputConnectionProxy, localTouchTypeExtractedText);
      }
      return;
      bool1 = false;
      if (i != 0)
      {
        paramInputConnectionProxy.finishComposingText();
        paramInputConnectionProxy.deleteSurroundingText(localTouchTypeExtractedText.lengthOfCodePointBeforeIndexInField(localTouchTypeExtractedText.getSelectionStartInField()), 0);
        bool1 = true;
        continue;
        bool1 = false;
        if (i != 0)
        {
          String str = getWordSeparator(localTouchTypeExtractedText);
          bool1 = this.mComposer.insertCharStr(paramKeyInputEvent, paramInputConnectionProxy, str, localTouchTypeExtractedText);
          continue;
          bool1 = false;
          if (i != 0)
          {
            bool1 = this.mComposer.insertCharStr(paramKeyInputEvent, paramInputConnectionProxy, " ", localTouchTypeExtractedText);
            continue;
            bool1 = this.mComposer.insertCharStr(paramKeyInputEvent, paramInputConnectionProxy, paramString, localTouchTypeExtractedText);
            continue;
            boolean bool2 = this.mKeyboardState.shouldAutocomplete(paramKeyInputEvent, localTouchTypeExtractedText);
            bool1 = false;
            if (bool2)
            {
              this.mPredictionInputEventHandler.setDelayedPrediction(this.mDelayedPrediction);
              this.mPredictionInputEventHandler.handleInput(paramInputConnectionProxy, paramKeyInputEvent);
              bool1 = false;
              continue;
              this.mKeyboardState.setDumbInputMode(true);
              bool1 = false;
            }
          }
        }
      }
    }
  }
  
  private void processActionArray(KeyInputEvent paramKeyInputEvent, String paramString, InputConnectionProxy paramInputConnectionProxy, Punctuator.Action[] paramArrayOfAction)
  {
    int i = paramArrayOfAction.length;
    for (int j = 0; j < i; j++) {
      processAction(paramKeyInputEvent, paramString, paramInputConnectionProxy, paramArrayOfAction[j]);
    }
  }
  
  private void updateFluency(CharSequence paramCharSequence)
  {
    if (paramCharSequence != null) {
      this.mLearner.temporarilyLearnWord(paramCharSequence.toString());
    }
  }
  
  public String getPredictionTrigger()
  {
    return this.mFluencyPunctuator.getPredictionTrigger();
  }
  
  public String getWordSeparator(ImmutableExtractedText paramImmutableExtractedText)
  {
    String str1 = paramImmutableExtractedText.textBeforeSelectionStart(256);
    String str2 = this.mFluencyPunctuator.getWordSeparator(str1);
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((str2.equals("​")) && (!this.mKeyboardState.useZeroWidthSpace())) {
        str2 = " ";
      }
      while ((!str2.equals(" ")) || (!this.mKeyboardState.useZeroWidthSpace())) {
        return str2;
      }
      return "​";
    }
    LogUtil.e(TAG, "Punctuator returned null or empty word separator!");
    return " ";
  }
  
  public void punctuate(KeyInputEvent paramKeyInputEvent, InputConnectionProxy paramInputConnectionProxy, String paramString)
  {
    boolean bool = TouchTypeExtractedText.isSpace(paramString.charAt(0));
    Punctuator.Action localAction1 = Punctuator.Action.INS_FOCUS;
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    if (bool) {
      this.mKeyboardState.setDumbInputMode(false);
    }
    if (!this.mKeyboardState.isDumbInputMode())
    {
      if (bool)
      {
        updateFluency(localTouchTypeExtractedText.getCurrentWord());
        int j = localTouchTypeExtractedText.getSelectionStartInField();
        if ((this.mKeyboardState.isQuickPeriodOn()) && (j >= 2) && (TouchTypeExtractedText.isSpace(localTouchTypeExtractedText.getLastCharacter())) && (Character.isLetterOrDigit(localTouchTypeExtractedText.getCharAtIndexInField(j - 2))) && (paramKeyInputEvent.getEventType() == CandidatesUpdateRequestType.TAP))
        {
          SoftKeyInputEvent localSoftKeyInputEvent = new SoftKeyInputEvent('.', false);
          this.mKeyInputEventHandler.handleInput(paramInputConnectionProxy, localSoftKeyInputEvent);
          return;
        }
        processAction(paramKeyInputEvent, paramString, paramInputConnectionProxy, localAction1);
        return;
      }
      if (this.mFluencyPunctuator != null)
      {
        this.mDelayedPrediction = this.mDefaultPredictionProvider.getDefaultPrediction(true, CandidatesUpdateRequestType.DEFAULT);
        String str1 = this.mDelayedPrediction.toString();
        String str2 = localTouchTypeExtractedText.textBeforeSelectionStart(256);
        Punctuator.Action[] arrayOfAction1 = this.mFluencyPunctuator.punctuate(str2, paramString, str1);
        Punctuator.Action[] arrayOfAction2 = arrayOfAction1;
        if ((paramString.charAt(0) == '.') && (this.mKeyboardState.isUrlBar()))
        {
          Punctuator.Action localAction2 = arrayOfAction1[(-1 + arrayOfAction1.length)];
          if ((localAction2.equals(Punctuator.Action.INS_SPACE)) || (localAction2.equals(Punctuator.Action.INS_LANG_SPECIFIC_SPACE)))
          {
            arrayOfAction2 = new Punctuator.Action[-1 + arrayOfAction1.length];
            for (int i = 0; i < -1 + arrayOfAction1.length; i++) {
              arrayOfAction2[i] = arrayOfAction1[i];
            }
          }
          this.mKeyboardState.setDumbInputMode(true);
        }
        processActionArray(paramKeyInputEvent, paramString, paramInputConnectionProxy, arrayOfAction2);
        return;
      }
    }
    processAction(paramKeyInputEvent, paramString, paramInputConnectionProxy, localAction1);
  }
  
  void setDelegateHandlers(KeyInputPredictionEventHandler paramKeyInputPredictionEventHandler, KeyInputEventHandler paramKeyInputEventHandler)
  {
    this.mPredictionInputEventHandler = paramKeyInputPredictionEventHandler;
    this.mKeyInputEventHandler = paramKeyInputEventHandler;
  }
  
  public void setFluencyPunctuator(com.touchtype_fluency.Punctuator paramPunctuator)
  {
    this.mFluencyPunctuator = paramPunctuator;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.PunctuatorImpl
 * JD-Core Version:    0.7.0.1
 */