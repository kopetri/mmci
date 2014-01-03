package com.touchtype.keyboard.inputeventmodel;

import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.events.CompletionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.ConnectionlessInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.DeleteInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowBegunEvent;
import com.touchtype.keyboard.inputeventmodel.events.FlowCompleteEvent;
import com.touchtype.keyboard.inputeventmodel.events.PredictionInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent;
import com.touchtype.keyboard.inputeventmodel.events.UpdateShiftStateEvent;
import com.touchtype.keyboard.inputeventmodel.events.VoiceInputEvent;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.touch.FlowEvent;
import java.util.EnumSet;
import java.util.List;

public abstract interface InputEventFactory
{
  public abstract DeleteInputEvent createDeleteLastWordEvent(EnumSet<ActionType> paramEnumSet);
  
  public abstract CompletionInputEvent createEventFromCompletion(CompletionInfo paramCompletionInfo);
  
  public abstract ConnectionInputEvent createEventFromHardKey(KeyEvent paramKeyEvent, int paramInt, boolean paramBoolean);
  
  public abstract ConnectionlessInputEvent createEventFromPoint(FlowEvent paramFlowEvent);
  
  public abstract ConnectionlessInputEvent createEventFromPoints(List<FlowEvent> paramList);
  
  public abstract PredictionInputEvent createEventFromPrediction(Candidate paramCandidate);
  
  public abstract ConnectionInputEvent createEventFromSoftKey(KeyEvent paramKeyEvent);
  
  public abstract VoiceInputEvent createEventFromVoiceInput(CharSequence paramCharSequence);
  
  public abstract FlowBegunEvent createFlowBegunEvent();
  
  public abstract FlowCompleteEvent createFlowCompleteEvent();
  
  public abstract SelectionChangedInputEvent createResetComposingTextEvent(int paramInt);
  
  public abstract SelectionChangedInputEvent createSelectionChangedEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public abstract UpdateShiftStateEvent createUpdateShiftStateEvent();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventFactory
 * JD-Core Version:    0.7.0.1
 */