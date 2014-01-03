package com.touchtype.keyboard.inputeventmodel;

import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManagerImpl;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.service.ImmutableExtractedText;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.Iterator;
import java.util.Set;

public class InputConnectionDelegateImpl
  implements InputConnectionProxy
{
  private static final String TAG = InputConnectionDelegateImpl.class.getSimpleName();
  private final InputConnectionProxy mAndroidInputConnection;
  private boolean mBatchEditing = false;
  private final Set<InputConnectionProxy> mConnections;
  private final KeyboardState mKeyboardState;
  private final TouchHistoryManagerImpl mTouchHistoryConnection;
  
  public InputConnectionDelegateImpl(InputConnectionProxy paramInputConnectionProxy, TouchHistoryManagerImpl paramTouchHistoryManagerImpl, Set<InputConnectionProxy> paramSet, KeyboardState paramKeyboardState)
  {
    this.mAndroidInputConnection = paramInputConnectionProxy;
    this.mTouchHistoryConnection = paramTouchHistoryManagerImpl;
    this.mConnections = paramSet;
    this.mKeyboardState = paramKeyboardState;
  }
  
  private boolean runOperation(InputConnectionOperation paramInputConnectionOperation)
  {
    if (!this.mBatchEditing) {
      LogUtil.w(TAG, "beginBatchEdit not called!");
    }
    boolean bool;
    do
    {
      return false;
      if (!paramInputConnectionOperation.run(this.mAndroidInputConnection)) {
        break;
      }
      bool = true;
      if (this.mKeyboardState.isPredictionEnabled())
      {
        Iterator localIterator = this.mConnections.iterator();
        while (localIterator.hasNext()) {
          bool &= paramInputConnectionOperation.run((InputConnectionProxy)localIterator.next());
        }
      }
    } while ((!bool) || (!paramInputConnectionOperation.run(this.mTouchHistoryConnection)));
    return true;
    throw new InputConnectionUnavailableException();
  }
  
  private boolean runOperationWithoutBatchEditCheck(InputConnectionOperation paramInputConnectionOperation)
  {
    if (paramInputConnectionOperation.run(this.mAndroidInputConnection))
    {
      boolean bool = true;
      if (this.mKeyboardState.isPredictionEnabled())
      {
        Iterator localIterator = this.mConnections.iterator();
        while (localIterator.hasNext()) {
          bool &= paramInputConnectionOperation.run((InputConnectionProxy)localIterator.next());
        }
      }
      return (bool) && (paramInputConnectionOperation.run(this.mTouchHistoryConnection));
    }
    throw new InputConnectionUnavailableException();
  }
  
  public boolean beginBatchEdit(boolean paramBoolean)
    throws BatchEditInProgressException
  {
    if (this.mBatchEditing)
    {
      LogUtil.w(TAG, "beginBatchEdit: re-entrant");
      throw new BatchEditInProgressException();
    }
    if (this.mAndroidInputConnection.beginBatchEdit(paramBoolean))
    {
      this.mBatchEditing = true;
      boolean bool1 = true;
      TouchTypeExtractedText localTouchTypeExtractedText;
      try
      {
        localTouchTypeExtractedText = this.mAndroidInputConnection.getTouchTypeExtractedText(false);
        if (localTouchTypeExtractedText == null) {
          throw new ExtractedTextUnavailableException("Could not obtain extracted text");
        }
      }
      finally
      {
        if ((!bool1) || (0 == 0)) {
          endBatchEdit(paramBoolean, CandidatesUpdateRequestType.DEFAULT);
        }
      }
      this.mTouchHistoryConnection.syncTextHistoryWith(localTouchTypeExtractedText);
      bool1 = this.mTouchHistoryConnection.beginBatchEdit(paramBoolean);
      if ((bool1) && (this.mKeyboardState.isPredictionEnabled()))
      {
        Iterator localIterator = this.mConnections.iterator();
        while (localIterator.hasNext())
        {
          boolean bool2 = ((InputConnectionProxy)localIterator.next()).beginBatchEdit(paramBoolean);
          bool1 &= bool2;
        }
      }
      if (!bool1) {
        endBatchEdit(paramBoolean, CandidatesUpdateRequestType.DEFAULT);
      }
      return bool1;
    }
    throw new InputConnectionUnavailableException();
  }
  
  public boolean clearMetaKeyStates(int paramInt)
  {
    boolean bool;
    if (this.mAndroidInputConnection.clearMetaKeyStates(paramInt))
    {
      bool = true;
      Iterator localIterator = this.mConnections.iterator();
      while (localIterator.hasNext()) {
        bool &= ((InputConnectionProxy)localIterator.next()).clearMetaKeyStates(paramInt);
      }
    }
    throw new InputConnectionUnavailableException();
    return bool;
  }
  
  public boolean commitCompletion(final CompletionInfo paramCompletionInfo)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.commitCompletion(paramCompletionInfo);
      }
    });
  }
  
  public boolean commitCorrection(final Candidate paramCandidate, final boolean paramBoolean, final ImmutableExtractedText paramImmutableExtractedText)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.commitCorrection(paramCandidate, paramBoolean, paramImmutableExtractedText);
      }
    });
  }
  
  public boolean commitCorrectionFromAutoCommit(final String paramString, final ImmutableExtractedText paramImmutableExtractedText)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.commitCorrectionFromAutoCommit(paramString, paramImmutableExtractedText);
      }
    });
  }
  
  public boolean commitText(final CharSequence paramCharSequence)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.commitText(paramCharSequence);
      }
    });
  }
  
  public boolean deleteSelection(final ImmutableExtractedText paramImmutableExtractedText, final InputConnectionProxy.SelectionDeletionType paramSelectionDeletionType)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.deleteSelection(paramImmutableExtractedText, paramSelectionDeletionType);
      }
    });
  }
  
  public boolean deleteSurroundingText(final int paramInt1, final int paramInt2)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.deleteSurroundingText(paramInt1, paramInt2);
      }
    });
  }
  
  public boolean endBatchEdit(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (!this.mBatchEditing)
    {
      LogUtil.w(TAG, "endBatchEdit: No batchEdit to end");
      return false;
    }
    TouchTypeExtractedText localTouchTypeExtractedText;
    try
    {
      if (!this.mAndroidInputConnection.endBatchEdit(paramBoolean, paramCandidatesUpdateRequestType)) {
        break label167;
      }
      if (!this.mTouchHistoryConnection.resyncRequired()) {
        break label86;
      }
      localTouchTypeExtractedText = this.mAndroidInputConnection.getTouchTypeExtractedText(false);
      if (localTouchTypeExtractedText == null) {
        throw new ExtractedTextUnavailableException("Could not obtain extracted text");
      }
    }
    finally
    {
      this.mBatchEditing = false;
    }
    this.mTouchHistoryConnection.syncTextHistoryWith(localTouchTypeExtractedText);
    label86:
    boolean bool1 = this.mTouchHistoryConnection.endBatchEdit(paramBoolean, paramCandidatesUpdateRequestType);
    if (this.mKeyboardState.isPredictionEnabled())
    {
      Iterator localIterator = this.mConnections.iterator();
      while (localIterator.hasNext())
      {
        boolean bool2 = ((InputConnectionProxy)localIterator.next()).endBatchEdit(paramBoolean, paramCandidatesUpdateRequestType);
        bool1 &= bool2;
      }
    }
    this.mBatchEditing = false;
    return bool1;
    label167:
    this.mBatchEditing = false;
    throw new InputConnectionUnavailableException();
  }
  
  public boolean extractedTextWorks()
  {
    return this.mAndroidInputConnection.extractedTextWorks();
  }
  
  public boolean finishComposingText()
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.finishComposingText();
      }
    });
  }
  
  public void fixWebKitInputConnection(boolean paramBoolean)
  {
    this.mAndroidInputConnection.fixWebKitInputConnection(paramBoolean);
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    if (this.mBatchEditing) {
      return this.mTouchHistoryConnection.getTouchTypeExtractedText(paramBoolean);
    }
    return this.mAndroidInputConnection.getTouchTypeExtractedText(paramBoolean);
  }
  
  public boolean nonZeroLengthSelectionMade(final int paramInt1, final int paramInt2)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.nonZeroLengthSelectionMade(paramInt1, paramInt2);
      }
    });
  }
  
  public boolean resync()
  {
    TouchTypeExtractedText localTouchTypeExtractedText = this.mAndroidInputConnection.getTouchTypeExtractedText(false);
    if (localTouchTypeExtractedText == null) {
      throw new ExtractedTextUnavailableException("could not obtain extracted text");
    }
    return this.mTouchHistoryConnection.syncTextHistoryWith(localTouchTypeExtractedText);
  }
  
  public void sendDownUpKeyEvents(final int paramInt)
  {
    runOperationWithoutBatchEditCheck(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        paramAnonymousInputConnectionProxy.sendDownUpKeyEvents(paramInt);
        return true;
      }
    });
  }
  
  public void sendKeyChar(char paramChar)
  {
    this.mAndroidInputConnection.sendKeyChar(paramChar);
  }
  
  public boolean setComposingRegion(final int paramInt1, final int paramInt2, final ImmutableExtractedText paramImmutableExtractedText)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingRegion(paramInt1, paramInt2, paramImmutableExtractedText);
      }
    });
  }
  
  public boolean setComposingTextByAppendingCharacter(final CharSequence paramCharSequence, final ImmutableExtractedText paramImmutableExtractedText, final char paramChar)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingTextByAppendingCharacter(paramCharSequence, paramImmutableExtractedText, paramChar);
      }
    });
  }
  
  public boolean setComposingTextByAppendingCharacter(final CharSequence paramCharSequence, final ImmutableExtractedText paramImmutableExtractedText, final Point paramPoint)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingTextByAppendingCharacter(paramCharSequence, paramImmutableExtractedText, paramPoint);
      }
    });
  }
  
  public boolean setComposingTextByDeletingLastCharacter(final CharSequence paramCharSequence, final ImmutableExtractedText paramImmutableExtractedText)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingTextByDeletingLastCharacter(paramCharSequence, paramImmutableExtractedText);
      }
    });
  }
  
  public boolean setComposingTextFromAutoCommit(final CharSequence paramCharSequence, final ImmutableExtractedText paramImmutableExtractedText, final TouchHistoryMarker paramTouchHistoryMarker)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingTextFromAutoCommit(paramCharSequence, paramImmutableExtractedText, paramTouchHistoryMarker);
      }
    });
  }
  
  public boolean setComposingTextWithCandidate(final Candidate paramCandidate, final boolean paramBoolean, final ImmutableExtractedText paramImmutableExtractedText)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setComposingTextWithCandidate(paramCandidate, paramBoolean, paramImmutableExtractedText);
      }
    });
  }
  
  public boolean setSelection(final int paramInt1, final int paramInt2)
  {
    runOperation(new InputConnectionOperation()
    {
      public boolean run(InputConnectionProxy paramAnonymousInputConnectionProxy)
      {
        return paramAnonymousInputConnectionProxy.setSelection(paramInt1, paramInt2);
      }
    });
  }
  
  public boolean textBeforeCursorWorks()
  {
    return this.mAndroidInputConnection.textBeforeCursorWorks();
  }
  
  private static abstract interface InputConnectionOperation
  {
    public abstract boolean run(InputConnectionProxy paramInputConnectionProxy);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputConnectionDelegateImpl
 * JD-Core Version:    0.7.0.1
 */