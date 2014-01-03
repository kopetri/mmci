package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype_fluency.Prediction;

public class FlowFailedCommitter
  implements UpdatedCandidatesListener
{
  private static final String TAG = FlowFailedCommitter.class.getSimpleName();
  private Candidate mCachedPrediction;
  private final InputEventModel mInputEventModel;
  
  public FlowFailedCommitter(InputEventModel paramInputEventModel)
  {
    this.mInputEventModel = paramInputEventModel;
  }
  
  private void clearCache()
  {
    if (this.mCachedPrediction != null) {
      this.mCachedPrediction = null;
    }
  }
  
  public void onCandidatesUpdated(CandidateContainer paramCandidateContainer)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$candidates$CandidatesUpdateRequestType[paramCandidateContainer.getEventType().ordinal()])
    {
    default: 
      clearCache();
    }
    do
    {
      Candidate localCandidate2;
      do
      {
        return;
        localCandidate2 = paramCandidateContainer.getTopCandidate();
      } while ((localCandidate2 == null) || (localCandidate2 == Candidate.empty()) || (localCandidate2.getPrediction() == null) || (localCandidate2.getPrediction().size() <= 1));
      this.mCachedPrediction = localCandidate2;
      return;
    } while (this.mCachedPrediction == null);
    String str = this.mCachedPrediction.toString();
    CharSequence localCharSequence = str.subSequence(0, str.lastIndexOf(this.mCachedPrediction.getSeparator()));
    if ((localCharSequence != null) && (localCharSequence.length() > 0))
    {
      Candidate localCandidate1 = Candidate.verbatim(localCharSequence);
      this.mInputEventModel.autoCommitUpToFailedFlow(localCandidate1);
    }
    clearCache();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.FlowFailedCommitter
 * JD-Core Version:    0.7.0.1
 */