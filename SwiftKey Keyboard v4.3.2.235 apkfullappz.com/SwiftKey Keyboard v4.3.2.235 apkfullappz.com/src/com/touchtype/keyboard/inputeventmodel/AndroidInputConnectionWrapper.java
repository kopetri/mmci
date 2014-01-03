package com.touchtype.keyboard.inputeventmodel;

import android.annotation.TargetApi;
import android.text.SpannableStringBuilder;
import android.text.style.StyleSpan;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TokenizationProvider;
import com.touchtype_fluency.service.TouchTypeExtractedText;

public class AndroidInputConnectionWrapper
  implements InputConnectionProxy
{
  private static final String TAG = AndroidInputConnectionWrapper.class.getSimpleName();
  private int batchEditNesting = 0;
  private InputConnection mConnectionForBatchEdit = null;
  private final MinimalInputMethodService mInputMethodService;
  private final KeyboardState mKeyboardState;
  private final TokenizationProvider mTokenizationProvider;
  
  public AndroidInputConnectionWrapper(MinimalInputMethodService paramMinimalInputMethodService, KeyboardState paramKeyboardState, TokenizationProvider paramTokenizationProvider)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mInputMethodService = paramMinimalInputMethodService;
    this.mTokenizationProvider = paramTokenizationProvider;
  }
  
  private boolean beginBatchEdit(InputConnection paramInputConnection)
  {
    if ((this.mKeyboardState.shouldFixWebKitInputConnection()) && (isInputConnectionActive(paramInputConnection))) {
      this.batchEditNesting = (1 + this.batchEditNesting);
    }
    return paramInputConnection.beginBatchEdit();
  }
  
  private ExtractedText createExtractedTextFromTextAroundCursor(InputConnection paramInputConnection)
  {
    if (this.mKeyboardState.useShortTextBeforeAfterCursor()) {}
    CharSequence localCharSequence1;
    CharSequence localCharSequence2;
    CharSequence localCharSequence3;
    for (int i = 100000;; i = 1073741823)
    {
      localCharSequence1 = paramInputConnection.getTextBeforeCursor(i, 0);
      int j = this.mKeyboardState.getApiCompatibiltyLevel();
      localCharSequence2 = null;
      if (j >= 9) {
        localCharSequence2 = selectedTextGingerbreadOnwards(paramInputConnection);
      }
      localCharSequence3 = paramInputConnection.getTextAfterCursor(i, 0);
      if ((localCharSequence1 != null) || (localCharSequence2 != null) || (localCharSequence3 != null)) {
        break;
      }
      return null;
    }
    SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder();
    if (localCharSequence1 != null) {
      localSpannableStringBuilder.append(localCharSequence1);
    }
    if (localCharSequence2 != null) {
      localSpannableStringBuilder.append(localCharSequence2);
    }
    if (localCharSequence3 != null) {
      localSpannableStringBuilder.append(localCharSequence3);
    }
    ExtractedText localExtractedText = new ExtractedText();
    localExtractedText.partialEndOffset = -1;
    localExtractedText.partialStartOffset = -1;
    if (localCharSequence1 == null) {}
    for (int k = 0;; k = localCharSequence1.length())
    {
      localExtractedText.selectionStart = k;
      localExtractedText.selectionEnd = localExtractedText.selectionStart;
      if (localCharSequence2 != null) {
        localExtractedText.selectionEnd += localCharSequence2.length();
      }
      localExtractedText.startOffset = 0;
      localExtractedText.text = localSpannableStringBuilder.subSequence(0, localSpannableStringBuilder.length());
      return localExtractedText;
    }
  }
  
  private boolean deleteSelectionConventionally(ImmutableExtractedText paramImmutableExtractedText)
  {
    int i = paramImmutableExtractedText.getSelectionEndInField() - paramImmutableExtractedText.getSelectionStartInField();
    boolean bool1 = setSelection(paramImmutableExtractedText.getSelectionEndInField(), paramImmutableExtractedText.getSelectionEndInField());
    boolean bool2 = false;
    if (bool1)
    {
      boolean bool3 = deleteSurroundingText(i, 0);
      bool2 = false;
      if (bool3) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  private boolean endBatchEdit(InputConnection paramInputConnection)
  {
    boolean bool = paramInputConnection.endBatchEdit();
    if ((this.mKeyboardState.shouldFixWebKitInputConnection()) && (isInputConnectionActive(paramInputConnection))) {
      this.batchEditNesting = (-1 + this.batchEditNesting);
    }
    return bool;
  }
  
  private Action froyoSetComposingRegion(final int paramInt1, final int paramInt2, final ImmutableExtractedText paramImmutableExtractedText)
  {
    new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        int i = paramInt2 - paramInt1;
        String str = paramImmutableExtractedText.substringInField(paramInt1, paramInt2);
        paramAnonymousInputConnection.finishComposingText();
        if (i > 0) {
          paramAnonymousInputConnection.deleteSurroundingText(i, 0);
        }
        return paramAnonymousInputConnection.setComposingText(str, 1);
      }
    };
  }
  
  @TargetApi(9)
  private Action gingerbreadSetComposingRegion(final int paramInt1, final int paramInt2)
  {
    new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        boolean bool = paramAnonymousInputConnection.setComposingRegion(paramInt1, paramInt2);
        if ((bool) && (paramInt1 == paramInt2)) {
          bool = paramAnonymousInputConnection.finishComposingText();
        }
        return bool;
      }
    };
  }
  
  private boolean isInputConnectionActive(InputConnection paramInputConnection)
  {
    return paramInputConnection.getTextAfterCursor(0, 1) != null;
  }
  
  private boolean withInputConnection(Action paramAction)
  {
    if (this.mConnectionForBatchEdit != null) {
      return paramAction.doWith(this.mConnectionForBatchEdit);
    }
    InputConnection localInputConnection = this.mInputMethodService.getCurrentInputConnection();
    if (localInputConnection != null) {
      return paramAction.doWith(localInputConnection);
    }
    throw new EventAbortedException("Input Connection Unavailable.");
  }
  
  public boolean attemptToFixInputConnection()
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        for (int i = AndroidInputConnectionWrapper.this.batchEditNesting; i > 0; i--) {
          AndroidInputConnectionWrapper.this.endBatchEdit(paramAnonymousInputConnection);
        }
        return AndroidInputConnectionWrapper.this.batchEditNesting == 0;
      }
    });
  }
  
  public boolean beginBatchEdit(final boolean paramBoolean)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        if ((!paramBoolean) || (AndroidInputConnectionWrapper.this.mKeyboardState.useTransactionsForSelectionEvents()))
        {
          if (AndroidInputConnectionWrapper.this.beginBatchEdit(paramAnonymousInputConnection)) {
            AndroidInputConnectionWrapper.access$202(AndroidInputConnectionWrapper.this, paramAnonymousInputConnection);
          }
        }
        else {
          return true;
        }
        return false;
      }
    });
  }
  
  public boolean clearMetaKeyStates(final int paramInt)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.clearMetaKeyStates(paramInt);
      }
    });
  }
  
  public boolean commitCompletion(final CompletionInfo paramCompletionInfo)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.commitCompletion(paramCompletionInfo);
      }
    });
  }
  
  @TargetApi(9)
  public boolean commitCorrection(Candidate paramCandidate, boolean paramBoolean, ImmutableExtractedText paramImmutableExtractedText)
  {
    return commitCorrection(paramCandidate.toString(), paramImmutableExtractedText);
  }
  
  public boolean commitCorrection(final String paramString, final ImmutableExtractedText paramImmutableExtractedText)
  {
    withInputConnection(new Action()
    {
      @TargetApi(11)
      private boolean commitCorrectionHoneycombOnwards(String paramAnonymousString, InputConnection paramAnonymousInputConnection)
      {
        CharSequence localCharSequence = paramImmutableExtractedText.getCurrentWord();
        return (paramAnonymousInputConnection.commitCorrection(new CorrectionInfo(paramImmutableExtractedText.getSelectionStartInField() - localCharSequence.length(), localCharSequence, paramAnonymousString))) && (paramAnonymousInputConnection.commitText(paramAnonymousString, 1));
      }
      
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        CharSequence localCharSequence = paramImmutableExtractedText.getCurrentWord();
        if (paramString.equals(localCharSequence.toString())) {
          return true;
        }
        if (AndroidInputConnectionWrapper.this.mKeyboardState.neverSetComposingRegion())
        {
          int i = localCharSequence.length();
          if ((i > 0) && (!paramAnonymousInputConnection.deleteSurroundingText(i, 0))) {
            return false;
          }
        }
        else if ((AndroidInputConnectionWrapper.this.mKeyboardState.setComposingRegionOnlyBeforeEdits()) && (!paramAnonymousInputConnection.setComposingRegion(paramImmutableExtractedText.getSelectionStartInField() - localCharSequence.length(), paramImmutableExtractedText.getSelectionStartInField())))
        {
          return false;
        }
        if ((this.val$apiLevel >= 11) && (!AndroidInputConnectionWrapper.this.mKeyboardState.neverSetComposingRegion())) {
          return commitCorrectionHoneycombOnwards(paramString, paramAnonymousInputConnection);
        }
        return paramAnonymousInputConnection.commitText(paramString, 1);
      }
    });
  }
  
  public boolean commitCorrectionFromAutoCommit(String paramString, ImmutableExtractedText paramImmutableExtractedText)
  {
    return commitCorrection(paramString, paramImmutableExtractedText);
  }
  
  public boolean commitText(final CharSequence paramCharSequence)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.commitText(paramCharSequence, 1);
      }
    });
  }
  
  public boolean deleteSelection(ImmutableExtractedText paramImmutableExtractedText, InputConnectionProxy.SelectionDeletionType paramSelectionDeletionType)
  {
    boolean bool = true;
    if (this.mKeyboardState.doesntSupportMoveCursor())
    {
      switch (14.$SwitchMap$com$touchtype$keyboard$inputeventmodel$InputConnectionProxy$SelectionDeletionType[paramSelectionDeletionType.ordinal()])
      {
      default: 
        bool = false;
      }
      do
      {
        return bool;
        sendDownUpKeyEvents(67);
        return bool;
      } while (!this.mKeyboardState.allowMoveCursorForInsertingPredictions());
      return deleteSelectionConventionally(paramImmutableExtractedText);
    }
    return deleteSelectionConventionally(paramImmutableExtractedText);
  }
  
  public boolean deleteSurroundingText(final int paramInt1, final int paramInt2)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.deleteSurroundingText(paramInt1, paramInt2);
      }
    });
  }
  
  public boolean endBatchEdit(final boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        if ((!paramBoolean) || (AndroidInputConnectionWrapper.this.mKeyboardState.useTransactionsForSelectionEvents()))
        {
          AndroidInputConnectionWrapper.access$202(AndroidInputConnectionWrapper.this, null);
          return AndroidInputConnectionWrapper.this.endBatchEdit(paramAnonymousInputConnection);
        }
        return true;
      }
    });
  }
  
  public boolean extractedTextWorks()
  {
    InputConnection localInputConnection = this.mInputMethodService.getCurrentInputConnection();
    boolean bool = false;
    if (localInputConnection != null)
    {
      ExtractedText localExtractedText = localInputConnection.getExtractedText(new ExtractedTextRequest(), 0);
      bool = false;
      if (localExtractedText != null) {
        bool = true;
      }
    }
    return bool;
  }
  
  public boolean finishComposingText()
  {
    if ((this.mKeyboardState.setComposingRegionOnlyBeforeEdits()) || (this.mKeyboardState.neverSetComposingRegion())) {
      return true;
    }
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.finishComposingText();
      }
    });
  }
  
  public void fixWebKitInputConnection(boolean paramBoolean)
  {
    if ((paramBoolean) && (this.batchEditNesting > 0))
    {
      if (attemptToFixInputConnection()) {}
      return;
    }
    this.batchEditNesting = 0;
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    InputConnection localInputConnection = this.mInputMethodService.getCurrentInputConnection();
    if (localInputConnection != null)
    {
      ExtractedTextRequest localExtractedTextRequest = new ExtractedTextRequest();
      if (paramBoolean) {
        localExtractedTextRequest.flags = (0x1 & localExtractedTextRequest.flags);
      }
      localExtractedTextRequest.hintMaxChars = 140;
      ExtractedText localExtractedText = localInputConnection.getExtractedText(localExtractedTextRequest, 0);
      if (localExtractedText == null) {
        localExtractedText = createExtractedTextFromTextAroundCursor(localInputConnection);
      }
      if (localExtractedText == null) {
        return null;
      }
      return new TouchTypeExtractedText(localExtractedText, this.mTokenizationProvider);
    }
    throw new EventAbortedException("Unable to get TouchTypeExtractedText from InputConnection.");
  }
  
  public boolean nonZeroLengthSelectionMade(int paramInt1, int paramInt2)
  {
    return true;
  }
  
  public boolean resync()
  {
    return false;
  }
  
  @TargetApi(9)
  CharSequence selectedTextGingerbreadOnwards(InputConnection paramInputConnection)
  {
    return paramInputConnection.getSelectedText(0);
  }
  
  public void sendDownUpKeyEvents(int paramInt)
  {
    this.mInputMethodService.sendDownUpKeyEvents(paramInt);
  }
  
  public void sendKeyChar(char paramChar)
  {
    this.mInputMethodService.sendKeyChar(paramChar);
  }
  
  public boolean setComposingRegion(int paramInt1, int paramInt2, ImmutableExtractedText paramImmutableExtractedText)
  {
    if ((this.mKeyboardState.setComposingRegionOnlyBeforeEdits()) || (this.mKeyboardState.neverSetComposingRegion())) {
      return true;
    }
    if (this.mKeyboardState.getApiCompatibiltyLevel() >= 9) {
      return withInputConnection(gingerbreadSetComposingRegion(paramInt1, paramInt2));
    }
    return withInputConnection(froyoSetComposingRegion(paramInt1, paramInt2, paramImmutableExtractedText));
  }
  
  public boolean setComposingText(final CharSequence paramCharSequence, final ImmutableExtractedText paramImmutableExtractedText)
  {
    withInputConnection(new Action()
    {
      private boolean neverSetComposingRegion(CharSequence paramAnonymousCharSequence, ImmutableExtractedText paramAnonymousImmutableExtractedText, InputConnection paramAnonymousInputConnection)
      {
        int i = 1;
        CharSequence localCharSequence = paramAnonymousImmutableExtractedText.getCurrentWord();
        int j = paramAnonymousImmutableExtractedText.getCurrentWord().length();
        int k = paramAnonymousCharSequence.length();
        if ((k < j) && ((localCharSequence.subSequence(0, k).equals(paramAnonymousCharSequence)) || (k == 0))) {
          i = paramAnonymousInputConnection.deleteSurroundingText(j - k, 0);
        }
        do
        {
          return i;
          if ((j < k) && ((paramAnonymousCharSequence.subSequence(0, j).equals(localCharSequence)) || (j == 0))) {
            return paramAnonymousInputConnection.commitText(paramAnonymousCharSequence.subSequence(j, k), i);
          }
        } while ((localCharSequence.toString().equals(paramAnonymousCharSequence.toString())) || ((paramAnonymousInputConnection.deleteSurroundingText(j, 0)) && (paramAnonymousInputConnection.commitText(paramAnonymousCharSequence, i))));
        return false;
      }
      
      @TargetApi(9)
      private boolean setComposingRegionOnlyBeforeEdits(CharSequence paramAnonymousCharSequence, ImmutableExtractedText paramAnonymousImmutableExtractedText, InputConnection paramAnonymousInputConnection)
      {
        int i = 1;
        CharSequence localCharSequence = paramAnonymousImmutableExtractedText.getCurrentWord();
        int j = paramAnonymousImmutableExtractedText.getCurrentWord().length();
        int k = paramAnonymousCharSequence.length();
        if ((k < j) && ((localCharSequence.subSequence(0, k).equals(paramAnonymousCharSequence)) || (k == 0))) {
          i = paramAnonymousInputConnection.deleteSurroundingText(j - k, 0);
        }
        do
        {
          return i;
          if ((j < k) && ((paramAnonymousCharSequence.subSequence(0, j).equals(localCharSequence)) || (j == 0))) {
            return paramAnonymousInputConnection.commitText(paramAnonymousCharSequence.subSequence(j, k), i);
          }
        } while ((localCharSequence.toString().equals(paramAnonymousCharSequence.toString())) || ((paramAnonymousInputConnection.setComposingRegion(paramAnonymousImmutableExtractedText.getSelectionStartInField() - j, paramAnonymousImmutableExtractedText.getSelectionStartInField())) && (paramAnonymousInputConnection.setComposingText(paramAnonymousCharSequence, i)) && (paramAnonymousInputConnection.finishComposingText())));
        return false;
      }
      
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        if (AndroidInputConnectionWrapper.this.mKeyboardState.neverSetComposingRegion()) {
          return neverSetComposingRegion(paramCharSequence, paramImmutableExtractedText, paramAnonymousInputConnection);
        }
        if (AndroidInputConnectionWrapper.this.mKeyboardState.setComposingRegionOnlyBeforeEdits()) {
          return setComposingRegionOnlyBeforeEdits(paramCharSequence, paramImmutableExtractedText, paramAnonymousInputConnection);
        }
        return paramAnonymousInputConnection.setComposingText(paramCharSequence, 1);
      }
    });
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
  
  public boolean setSelection(final int paramInt1, final int paramInt2)
  {
    withInputConnection(new Action()
    {
      public boolean doWith(InputConnection paramAnonymousInputConnection)
      {
        return paramAnonymousInputConnection.setSelection(paramInt1, paramInt2);
      }
    });
  }
  
  public boolean textBeforeCursorWorks()
  {
    InputConnection localInputConnection = this.mInputMethodService.getCurrentInputConnection();
    return (localInputConnection != null) && (localInputConnection.getTextBeforeCursor(1, 0) != null);
  }
  
  private static abstract interface Action
  {
    public abstract boolean doWith(InputConnection paramInputConnection);
  }
  
  public static class TTTestSpan
    extends StyleSpan
  {
    public int getSpanTypeId()
    {
      return -34934;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.AndroidInputConnectionWrapper
 * JD-Core Version:    0.7.0.1
 */