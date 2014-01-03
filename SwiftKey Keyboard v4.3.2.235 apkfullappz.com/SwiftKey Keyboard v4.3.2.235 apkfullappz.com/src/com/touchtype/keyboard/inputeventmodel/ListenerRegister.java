package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.InputFilterListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnCandidatesUpdateRequestListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;

public abstract interface ListenerRegister
{
  public abstract void addBufferedInputListener(BufferedInputListener paramBufferedInputListener);
  
  public abstract void addCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener);
  
  public abstract void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener);
  
  public abstract void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt);
  
  public abstract void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener);
  
  public abstract void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener);
  
  public abstract void removeCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener);
  
  public abstract void removePredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener);
  
  public abstract void removeShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.ListenerRegister
 * JD-Core Version:    0.7.0.1
 */