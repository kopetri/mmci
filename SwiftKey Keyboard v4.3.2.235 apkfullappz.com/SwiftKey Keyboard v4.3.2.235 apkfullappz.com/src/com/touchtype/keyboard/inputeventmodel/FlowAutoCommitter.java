package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype_fluency.Prediction;
import java.util.List;

public final class FlowAutoCommitter
  implements UpdatedCandidatesListener
{
  private final BackgroundExecutor mExecutor;
  private final InputEventModel mInputEventModel;
  private boolean mPendingAutoCommit = false;
  
  public FlowAutoCommitter(InputEventModel paramInputEventModel, BackgroundExecutor paramBackgroundExecutor)
  {
    this.mInputEventModel = paramInputEventModel;
    this.mExecutor = paramBackgroundExecutor;
  }
  
  public void onCandidatesUpdated(CandidateContainer paramCandidateContainer)
  {
    List localList = paramCandidateContainer.getCandidates();
    if ((!this.mPendingAutoCommit) && (paramCandidateContainer.getEventType() == CandidatesUpdateRequestType.FLOW) && (!localList.isEmpty()))
    {
      Candidate localCandidate = (Candidate)localList.get(0);
      if (localCandidate.getPrediction().size() >= 5)
      {
        this.mInputEventModel.autoCommitFlow(localCandidate, paramCandidateContainer.getTouchHistory());
        this.mPendingAutoCommit = true;
        this.mExecutor.runInForegroundWhenPriorTasksComplete(new Runnable()
        {
          public void run()
          {
            FlowAutoCommitter.access$002(FlowAutoCommitter.this, false);
          }
        });
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.FlowAutoCommitter
 * JD-Core Version:    0.7.0.1
 */