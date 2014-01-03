package com.touchtype.keyboard.inputeventmodel.handlers;

import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy;
import com.touchtype.keyboard.inputeventmodel.InputConnectionProxy.SelectionDeletionType;
import com.touchtype.keyboard.inputeventmodel.KeyboardState;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent.DeleteType;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.report.TouchTypeStats;
import com.touchtype_fluency.Hangul;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import com.touchtype_fluency.service.UnicodeUtils;
import java.util.EnumSet;
import java.util.Iterator;

public final class DeleteInputEventHandler
  implements ConnectionInputEventHandler
{
  private ConnectionInputEventHandler mCursorInputEventHandler;
  private KeyboardState mKeyboardState;
  private Learner mLearner;
  private TouchTypeStats mStats;
  private TouchHistoryManager mTouchHistoryManager;
  
  public DeleteInputEventHandler(TouchHistoryManager paramTouchHistoryManager, KeyboardState paramKeyboardState, TouchTypeStats paramTouchTypeStats, Learner paramLearner)
  {
    this.mTouchHistoryManager = paramTouchHistoryManager;
    this.mKeyboardState = paramKeyboardState;
    this.mStats = paramTouchTypeStats;
    this.mLearner = paramLearner;
  }
  
  private void updateBackspaceStatOnFlowedWord()
  {
    if (this.mTouchHistoryManager.getCurrentTouchHistoryMarker().getHasSample()) {
      this.mStats.incrementStatistic("stats_backspace_on_flowed_word");
    }
  }
  
  private void updateComposingRegion(InputConnectionProxy paramInputConnectionProxy, ImmutableExtractedText paramImmutableExtractedText, int paramInt, boolean paramBoolean)
  {
    ConnectionInputEventHandler localConnectionInputEventHandler = this.mCursorInputEventHandler;
    int i = paramImmutableExtractedText.getSelectionStartInField();
    int j = paramImmutableExtractedText.getSelectionEndInField();
    if (paramBoolean) {}
    for (int k = -1;; k = paramInt)
    {
      localConnectionInputEventHandler.handleInput(paramInputConnectionProxy, new SelectionChangedInputEvent(-1, -1, i, j, k, paramImmutableExtractedText.getSelectionEndInField()));
      return;
    }
  }
  
  public void handleInput(InputConnectionProxy paramInputConnectionProxy, ConnectionInputEvent paramConnectionInputEvent)
    throws UnhandledInputEventException
  {
    DeleteInputEvent localDeleteInputEvent = (DeleteInputEvent)paramConnectionInputEvent;
    TouchTypeExtractedText localTouchTypeExtractedText = paramInputConnectionProxy.getTouchTypeExtractedText(false);
    CharSequence localCharSequence = localTouchTypeExtractedText.getCurrentWord();
    int i = localTouchTypeExtractedText.getSelectionEndInField();
    if ((this.mKeyboardState.isPredictionEnabled()) && (!localTouchTypeExtractedText.textEmpty()) && (i > 0)) {}
    int j;
    boolean bool;
    switch (1.$SwitchMap$com$touchtype$keyboard$inputeventmodel$events$DeleteInputEvent$DeleteType[localDeleteInputEvent.getType().ordinal()])
    {
    default: 
      j = localTouchTypeExtractedText.getSelectionEndInField() - localCharSequence.length();
      bool = true;
      if (i <= 0)
      {
        paramInputConnectionProxy.sendDownUpKeyEvents(67);
        this.mTouchHistoryManager.resetContinuousTouchSamples();
        this.mKeyboardState.setDumbInputMode(false);
      }
      break;
    }
    Object localObject;
    for (;;)
    {
      return;
      this.mStats.incrementStatistic("stats_backspace_presses");
      break;
      Iterator localIterator = localDeleteInputEvent.getLogTypes().iterator();
      while (localIterator.hasNext())
      {
        ActionType localActionType = (ActionType)localIterator.next();
        switch (1.$SwitchMap$com$touchtype$keyboard$key$actions$ActionType[localActionType.ordinal()])
        {
        default: 
          break;
        case 1: 
        case 2: 
          this.mStats.incrementStatistic("stats_backspace_longpress_uses");
          break;
        case 3: 
          this.mStats.incrementStatistic("stats_swipeleft_uses");
        }
      }
      break;
      if (localTouchTypeExtractedText.getSelectionStartInField() != localTouchTypeExtractedText.getSelectionEndInField())
      {
        paramInputConnectionProxy.deleteSelection(localTouchTypeExtractedText, InputConnectionProxy.SelectionDeletionType.NO_REPLACEMENT);
        return;
      }
      int k = 1.$SwitchMap$com$touchtype$keyboard$inputeventmodel$events$DeleteInputEvent$DeleteType[localDeleteInputEvent.getType().ordinal()];
      localObject = null;
      switch (k)
      {
      }
      while (localObject != null)
      {
        this.mLearner.temporarilyForgetWord(localObject.toString().trim());
        return;
        if (!this.mKeyboardState.composeTextWordByWord())
        {
          if (!this.mKeyboardState.deleteKeyDeletesTwoCharacters()) {
            paramInputConnectionProxy.sendDownUpKeyEvents(67);
          }
          for (;;)
          {
            updateComposingRegion(paramInputConnectionProxy, localTouchTypeExtractedText, j, true);
            if ((!localTouchTypeExtractedText.isLastCharacterWhitespace()) && (localTouchTypeExtractedText.getLastCharacter() != 0)) {
              break;
            }
            this.mKeyboardState.setDumbInputMode(false);
            break;
            paramInputConnectionProxy.deleteSurroundingText(localTouchTypeExtractedText.lengthOfCodePointBeforeIndexInField(i), 0);
          }
        }
        int n = localTouchTypeExtractedText.getSpacesBeforeCursor();
        localObject = null;
        if (n == 1) {
          localObject = localTouchTypeExtractedText.getLastWord();
        }
        if (localCharSequence.length() == 0) {
          paramInputConnectionProxy.deleteSurroundingText(localTouchTypeExtractedText.lengthOfCodePointBeforeIndexInField(i), 0);
        }
        for (;;)
        {
          updateComposingRegion(paramInputConnectionProxy, localTouchTypeExtractedText, j, bool);
          break;
          updateBackspaceStatOnFlowedWord();
          String str = Hangul.split(localCharSequence.toString());
          int i1 = str.length();
          paramInputConnectionProxy.setComposingTextByDeletingLastCharacter(Hangul.join(str.subSequence(0, i1 - UnicodeUtils.lengthOfCodePointBeforeIndex(str, i1)).toString()), localTouchTypeExtractedText);
          bool = false;
        }
        this.mKeyboardState.setDumbInputMode(false);
        if (!this.mKeyboardState.backspaceWordDeleteToStart()) {
          break label621;
        }
        paramInputConnectionProxy.finishComposingText();
        paramInputConnectionProxy.deleteSurroundingText(i, 0);
        updateComposingRegion(paramInputConnectionProxy, localTouchTypeExtractedText, j, true);
      }
    }
    label621:
    if (localCharSequence.length() == 0) {}
    for (int m = localTouchTypeExtractedText.getLastWord().length();; m = localCharSequence.length())
    {
      localObject = localTouchTypeExtractedText.substringInField(i - m, i);
      paramInputConnectionProxy.finishComposingText();
      if (localCharSequence.length() == 0)
      {
        paramInputConnectionProxy.deleteSurroundingText(1, 0);
        m--;
      }
      updateBackspaceStatOnFlowedWord();
      if (m <= 0) {
        break;
      }
      paramInputConnectionProxy.deleteSurroundingText(m, 0);
      break;
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
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.handlers.DeleteInputEventHandler
 * JD-Core Version:    0.7.0.1
 */