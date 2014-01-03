package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import java.util.List;

public abstract interface ListenerManager
  extends ListenerRegister
{
  public abstract void notifyBufferedInputListeners(boolean paramBoolean);
  
  public abstract void notifyCandidateUpdateListeners();
  
  public abstract void notifyFlowStateChangedListeners(boolean paramBoolean);
  
  public abstract void notifyInputFilterListeners(List<String> paramList);
  
  public abstract void notifyPredictionsEnabledListener(PredictionsAvailability paramPredictionsAvailability);
  
  public abstract void notifyShiftStateChangedListeners(TouchTypeSoftKeyboard.ShiftState paramShiftState);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.ListenerManager
 * JD-Core Version:    0.7.0.1
 */