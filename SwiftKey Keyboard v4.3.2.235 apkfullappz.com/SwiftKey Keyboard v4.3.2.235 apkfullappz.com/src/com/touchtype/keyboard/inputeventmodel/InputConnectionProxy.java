package com.touchtype.keyboard.inputeventmodel;

import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public abstract interface InputConnectionProxy
{
  public abstract boolean beginBatchEdit(boolean paramBoolean)
    throws BatchEditInProgressException;
  
  public abstract boolean clearMetaKeyStates(int paramInt);
  
  public abstract boolean commitCompletion(CompletionInfo paramCompletionInfo);
  
  public abstract boolean commitCorrection(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean commitCorrectionFromAutoCommit(String paramString, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean commitText(CharSequence paramCharSequence);
  
  public abstract boolean deleteSelection(ImmutableExtractedText paramImmutableExtractedText, SelectionDeletionType paramSelectionDeletionType);
  
  public abstract boolean deleteSurroundingText(int paramInt1, int paramInt2);
  
  public abstract boolean endBatchEdit(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType);
  
  public abstract boolean extractedTextWorks();
  
  public abstract boolean finishComposingText();
  
  public abstract void fixWebKitInputConnection(boolean paramBoolean);
  
  public abstract TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean);
  
  public abstract boolean nonZeroLengthSelectionMade(int paramInt1, int paramInt2);
  
  public abstract boolean resync();
  
  public abstract void sendDownUpKeyEvents(int paramInt);
  
  public abstract void sendKeyChar(char paramChar);
  
  public abstract boolean setComposingRegion(int paramInt1, int paramInt2, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, char paramChar);
  
  public abstract boolean setComposingTextByAppendingCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, Point paramPoint);
  
  public abstract boolean setComposingTextByDeletingLastCharacter(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean setComposingTextFromAutoCommit(CharSequence paramCharSequence, ImmutableExtractedText paramImmutableExtractedText, TouchHistoryMarker paramTouchHistoryMarker);
  
  public abstract boolean setComposingTextWithCandidate(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText);
  
  public abstract boolean setSelection(int paramInt1, int paramInt2);
  
  public abstract boolean textBeforeCursorWorks();
  
  public static enum SelectionDeletionType
  {
    static
    {
      SelectionDeletionType[] arrayOfSelectionDeletionType = new SelectionDeletionType[3];
      arrayOfSelectionDeletionType[0] = NO_REPLACEMENT;
      arrayOfSelectionDeletionType[1] = REPLACING_WITH_KEYPRESS;
      arrayOfSelectionDeletionType[2] = REPLACING_WITH_PREDICTION;
      $VALUES = arrayOfSelectionDeletionType;
    }
    
    private SelectionDeletionType() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputConnectionProxy
 * JD-Core Version:    0.7.0.1
 */