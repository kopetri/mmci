package com.touchtype.keyboard.inputeventmodel;

import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public final class StatsLoggerImpl
  implements InputConnectionProxy
{
  private static String TAG = "StatsLoggerImpl";
  private TouchTypeStats mStats;
  private boolean mTransaction = false;
  private int mTransactionCharactersEntered = 0;
  
  public StatsLoggerImpl(TouchTypeStats paramTouchTypeStats)
  {
    this.mStats = paramTouchTypeStats;
  }
  
  private void charactersEntered(int paramInt)
  {
    this.mStats.incrementStatisticBy("stats_entered_characters", paramInt);
  }
  
  public boolean beginBatchEdit(boolean paramBoolean)
  {
    if (this.mTransaction)
    {
      LogUtil.w(TAG, "beginBatchEdit() called while in transaction. Ignored.");
      return false;
    }
    this.mTransaction = true;
    this.mTransactionCharactersEntered = 0;
    return true;
  }
  
  public boolean clearMetaKeyStates(int paramInt)
  {
    return true;
  }
  
  public boolean commitCompletion(CompletionInfo paramCompletionInfo)
  {
    return true;
  }
  
  public boolean commitCorrection(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText)
  {
    return commitText(paramCandidate.toString());
  }
  
  public boolean commitCorrectionFromAutoCommit(String paramString, ImmutableExtractedText paramImmutableExtractedText)
  {
    return commitText(paramString);
  }
  
  public boolean commitText(CharSequence paramCharSequence)
  {
    this.mTransactionCharactersEntered += paramCharSequence.length();
    return true;
  }
  
  public boolean deleteSelection(ImmutableExtractedText paramImmutableExtractedText, InputConnectionProxy.SelectionDeletionType paramSelectionDeletionType)
  {
    return deleteSurroundingText(paramImmutableExtractedText.getSelectionEndInField() - paramImmutableExtractedText.getSelectionStartInField(), 0);
  }
  
  public boolean deleteSurroundingText(int paramInt1, int paramInt2)
  {
    this.mTransactionCharactersEntered = Math.max(0, this.mTransactionCharactersEntered - (paramInt1 + paramInt2));
    return true;
  }
  
  public boolean endBatchEdit(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (this.mTransaction)
    {
      switch (1.$SwitchMap$com$touchtype$keyboard$candidates$CandidatesUpdateRequestType[paramCandidatesUpdateRequestType.ordinal()])
      {
      default: 
        charactersEntered(this.mTransactionCharactersEntered);
      }
      this.mTransaction = false;
      return true;
    }
    LogUtil.w(TAG, "endBatchEdit() called without transaction. Ignored.");
    return false;
  }
  
  public boolean extractedTextWorks()
  {
    return false;
  }
  
  public boolean finishComposingText()
  {
    return true;
  }
  
  public void fixWebKitInputConnection(boolean paramBoolean) {}
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    return null;
  }
  
  public boolean nonZeroLengthSelectionMade(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public boolean resync()
  {
    return false;
  }
  
  public void sendDownUpKeyEvents(int paramInt) {}
  
  public void sendKeyChar(char paramChar) {}
  
  public boolean setComposingRegion(int paramInt1, int paramInt2, ImmutableExtractedText paramImmutableExtractedText)
  {
    return true;
  }
  
  public boolean setComposingText(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText)
  {
    this.mTransactionCharactersEntered += Math.max(1, paramCharSequence.length() - paramImmutableExtractedText.getCurrentWord().length());
    return true;
  }
  
  public boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, char paramChar)
  {
    return setComposingText(paramCharSequence, paramImmutableExtractedText);
  }
  
  public boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, Point paramPoint)
  {
    return setComposingText(paramCharSequence, paramImmutableExtractedText);
  }
  
  public boolean setComposingTextByDeletingLastCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText)
  {
    return setComposingText(paramCharSequence, paramImmutableExtractedText);
  }
  
  public boolean setComposingTextFromAutoCommit(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, TouchHistoryMarker paramTouchHistoryMarker)
  {
    return setComposingText(paramCharSequence, paramImmutableExtractedText);
  }
  
  public boolean setComposingTextWithCandidate(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText)
  {
    return setComposingText(paramCandidate.toString(), paramImmutableExtractedText);
  }
  
  public boolean setSelection(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public boolean textBeforeCursorWorks()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.StatsLoggerImpl
 * JD-Core Version:    0.7.0.1
 */