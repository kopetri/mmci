package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.AsianComposingBuffer;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.CandidateStateHandler;
import com.touchtype_fluency.service.FluencyServiceProxy;
import java.util.Stack;

public final class InputEventModelDelegator
  extends InputEventModelDecorator
{
  private final CandidateStateHandler mCandidateStateHandler;
  private final FluencyServiceProxy mFluencyService;
  private final Learner mLearner;
  private final ListenerManager mListenerManager;
  private final Stack<InputEventModel> mModels = new Stack();
  
  public InputEventModelDelegator(InputEventModel paramInputEventModel, FluencyServiceProxy paramFluencyServiceProxy, CandidateStateHandler paramCandidateStateHandler, ListenerManager paramListenerManager, Learner paramLearner)
  {
    this.mModels.push(paramInputEventModel);
    this.mFluencyService = paramFluencyServiceProxy;
    this.mCandidateStateHandler = paramCandidateStateHandler;
    this.mListenerManager = paramListenerManager;
    this.mLearner = paramLearner;
  }
  
  private void addWrapper(InputEventModelDecorator paramInputEventModelDecorator)
  {
    paramInputEventModelDecorator.setDecorated((InputEventModel)this.mModels.peek());
    this.mModels.push(paramInputEventModelDecorator);
  }
  
  private void removeAllWrappers()
  {
    while (this.mModels.size() > 1) {
      this.mModels.pop();
    }
  }
  
  protected InputEventModel getDecorated()
  {
    return (InputEventModel)this.mModels.peek();
  }
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    super.onKeyboardChanged(paramKeyboardBehaviour);
    removeAllWrappers();
    if (paramKeyboardBehaviour.shouldUseAsianComposingBuffer())
    {
      AsianComposingBuffer localAsianComposingBuffer = new AsianComposingBuffer(this.mFluencyService, this.mListenerManager, this.mLearner, paramKeyboardBehaviour);
      localAsianComposingBuffer.addCandidatesListener(this.mCandidateStateHandler);
      addWrapper(localAsianComposingBuffer);
    }
    getDecorated().refreshPredictions(true);
  }
  
  protected void setDecorated(InputEventModel paramInputEventModel)
  {
    if (paramInputEventModel != null)
    {
      this.mModels.clear();
      this.mModels.push(paramInputEventModel);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventModelDelegator
 * JD-Core Version:    0.7.0.1
 */