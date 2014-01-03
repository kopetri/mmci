package com.touchtype.keyboard.candidates.model;

import android.util.Pair;
import android.view.View;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;

public abstract interface CandidateModelInterface
{
  public abstract void candidateSelected();
  
  public abstract boolean cyclePages();
  
  public abstract View getCandidateView(Learner paramLearner, InputEventModel paramInputEventModel);
  
  public abstract Candidate getDefaultCandidate();
  
  public abstract Pair<View, View> getDualCandidateViews(Learner paramLearner, InputEventModel paramInputEventModel);
  
  public abstract int getNumCandsRequired();
  
  public abstract int getNumberOfPages();
  
  public abstract void setCandidates(CandidateContainer paramCandidateContainer);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.model.CandidateModelInterface
 * JD-Core Version:    0.7.0.1
 */