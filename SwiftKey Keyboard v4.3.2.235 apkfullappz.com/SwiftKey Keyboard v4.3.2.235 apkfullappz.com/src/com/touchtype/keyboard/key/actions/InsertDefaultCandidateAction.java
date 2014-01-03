package com.touchtype.keyboard.key.actions;

import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import java.util.EnumSet;

public final class InsertDefaultCandidateAction
  extends GenericActionDecorator
{
  private final DefaultPredictionProvider mDefaultPredictionProvider;
  private final InputEventModel mIem;
  
  public InsertDefaultCandidateAction(InputEventModel paramInputEventModel, DefaultPredictionProvider paramDefaultPredictionProvider, EnumSet<ActionType> paramEnumSet, ActionParams paramActionParams, Action paramAction)
  {
    super(paramEnumSet, paramActionParams, paramAction);
    this.mIem = paramInputEventModel;
    this.mDefaultPredictionProvider = paramDefaultPredictionProvider;
  }
  
  protected void act()
  {
    this.mIem.onPredictionSelected(this.mDefaultPredictionProvider.getDefaultPrediction(false, CandidatesUpdateRequestType.DEFAULT));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.actions.InsertDefaultCandidateAction
 * JD-Core Version:    0.7.0.1
 */