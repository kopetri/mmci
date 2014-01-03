package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.CandidateUtil.VerbatimCandidatePosition;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequest;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestFactory;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker.DeletionPerformed;
import com.touchtype.preferences.AutoCompleteMode;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.Sequence;

public final class DefaultCandidatesUpdateRequestFactory
  implements CandidatesUpdateRequestFactory
{
  private static boolean mIsWatch;
  private final KeyboardState mKeyboardState;
  private final TouchHistoryManager mTouchHistoryManager;
  
  public DefaultCandidatesUpdateRequestFactory(KeyboardState paramKeyboardState, TouchHistoryManager paramTouchHistoryManager, boolean paramBoolean)
  {
    this.mKeyboardState = paramKeyboardState;
    this.mTouchHistoryManager = paramTouchHistoryManager;
    mIsWatch = paramBoolean;
  }
  
  public static CandidatesUpdateRequest createRequest(Sequence paramSequence, String paramString, TouchHistoryMarker paramTouchHistoryMarker, KeyboardState paramKeyboardState)
  {
    TouchHistoryProxy localTouchHistoryProxy;
    String str;
    ResultsFilter.CapitalizationHint localCapitalizationHint;
    TouchHistoryMarker.DeletionPerformed localDeletionPerformed;
    CandidateUtil.VerbatimCandidatePosition localVerbatimCandidatePosition;
    if (paramTouchHistoryMarker != null)
    {
      localTouchHistoryProxy = paramTouchHistoryMarker.getTouchHistory();
      if (paramTouchHistoryMarker.modifiedByPredictionButNotYetCharacterised())
      {
        str = paramTouchHistoryMarker.unmodifiedText();
        if (str.length() > 0)
        {
          localCapitalizationHint = paramKeyboardState.getCapitalisationState(str);
          localDeletionPerformed = paramTouchHistoryMarker.deletionPerformedAfterModification();
          if (((!paramTouchHistoryMarker.getHasSample()) && (paramKeyboardState.getAutocompleteMode() != AutoCompleteMode.AUTOCOMPLETEMODE_DISABLED)) || (mIsWatch)) {
            break label137;
          }
          localVerbatimCandidatePosition = CandidateUtil.VerbatimCandidatePosition.NO_VERBATIM;
        }
      }
    }
    for (;;)
    {
      return new CandidatesUpdateRequest(paramSequence, str, localVerbatimCandidatePosition, localTouchHistoryProxy, localCapitalizationHint, paramKeyboardState.isDumbInputMode(), paramKeyboardState.getSearchType());
      localCapitalizationHint = paramKeyboardState.getCapitalisationState(paramTouchHistoryMarker.unmodifiedCapsHint());
      break;
      str = paramString;
      localCapitalizationHint = paramKeyboardState.getCapitalisationState(str);
      break;
      label137:
      if ((localDeletionPerformed == TouchHistoryMarker.DeletionPerformed.PARTIAL) || ((localDeletionPerformed == TouchHistoryMarker.DeletionPerformed.FULL) && (paramString.equals(paramTouchHistoryMarker.unmodifiedText()))))
      {
        localVerbatimCandidatePosition = CandidateUtil.VerbatimCandidatePosition.VERBATIM_FIRST;
      }
      else
      {
        localVerbatimCandidatePosition = CandidateUtil.VerbatimCandidatePosition.VERBATIM_SECOND;
        continue;
        localCapitalizationHint = ResultsFilter.CapitalizationHint.LOWER_CASE;
        str = paramString;
        if ((paramKeyboardState.getAutocompleteMode() == AutoCompleteMode.AUTOCOMPLETEMODE_DISABLED) && (!mIsWatch))
        {
          localVerbatimCandidatePosition = CandidateUtil.VerbatimCandidatePosition.NO_VERBATIM;
          localTouchHistoryProxy = null;
        }
        else
        {
          localVerbatimCandidatePosition = CandidateUtil.VerbatimCandidatePosition.VERBATIM_SECOND;
          localTouchHistoryProxy = null;
        }
      }
    }
  }
  
  public CandidatesUpdateRequest createRequest()
  {
    return createRequest(this.mTouchHistoryManager.getContext(), this.mTouchHistoryManager.getCurrentWord(), this.mTouchHistoryManager.getCurrentTouchHistoryMarker(), this.mKeyboardState);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.DefaultCandidatesUpdateRequestFactory
 * JD-Core Version:    0.7.0.1
 */