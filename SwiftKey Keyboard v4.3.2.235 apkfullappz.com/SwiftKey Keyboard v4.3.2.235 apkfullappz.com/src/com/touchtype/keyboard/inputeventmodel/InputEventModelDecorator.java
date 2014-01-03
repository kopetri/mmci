package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.InputFilterListener;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnCandidatesUpdateRequestListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnFlowStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.util.PackageInfoUtil;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public abstract class InputEventModelDecorator
  implements InputEventModel
{
  public void addBufferedInputListener(BufferedInputListener paramBufferedInputListener)
  {
    getDecorated().addBufferedInputListener(paramBufferedInputListener);
  }
  
  public void addCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    getDecorated().addCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener)
  {
    getDecorated().addFlowStateChangedListener(paramOnFlowStateChangedListener);
  }
  
  public void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt)
  {
    getDecorated().addInputFilterListener(paramInputFilterListener, paramInt);
  }
  
  public void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    getDecorated().addPredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    getDecorated().addShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void addUpdatedCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt)
  {
    getDecorated().addUpdatedCandidatesListener(paramUpdatedCandidatesListener, paramInt);
  }
  
  public void autoCommitFlow(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    getDecorated().autoCommitFlow(paramCandidate, paramTouchHistoryProxy);
  }
  
  public void autoCommitUpToFailedFlow(Candidate paramCandidate)
  {
    getDecorated().autoCommitUpToFailedFlow(paramCandidate);
  }
  
  public void commitBuffer()
  {
    getDecorated().commitBuffer();
  }
  
  public void cycleCandidates()
  {
    getDecorated().cycleCandidates();
  }
  
  public void enabledLanguagePacksChanged(int paramInt)
  {
    getDecorated().enabledLanguagePacksChanged(paramInt);
  }
  
  public boolean evaluateInputShownUsedInsteadOfUpdateSelection()
  {
    return getDecorated().evaluateInputShownUsedInsteadOfUpdateSelection();
  }
  
  protected abstract InputEventModel getDecorated();
  
  public KeyPressModel getKeyPressModel()
  {
    return getDecorated().getKeyPressModel();
  }
  
  public TouchTypeSoftKeyboard.ShiftState getShiftState()
  {
    return getDecorated().getShiftState();
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    return getDecorated().getTouchTypeExtractedText(paramBoolean);
  }
  
  public void handleDeleteLastWord(int paramInt, EnumSet<ActionType> paramEnumSet)
  {
    getDecorated().handleDeleteLastWord(paramInt, paramEnumSet);
  }
  
  public void handleVoiceInput(CharSequence paramCharSequence)
  {
    getDecorated().handleVoiceInput(paramCharSequence);
  }
  
  public boolean isLayoutCyclingEnabled()
  {
    return getDecorated().isLayoutCyclingEnabled();
  }
  
  public boolean isPredictionEnabled()
  {
    return getDecorated().isPredictionEnabled();
  }
  
  public boolean isSearchField()
  {
    return getDecorated().isSearchField();
  }
  
  public void onCompletionAccepted(CompletionInfo paramCompletionInfo)
  {
    getDecorated().onCompletionAccepted(paramCompletionInfo);
  }
  
  public void onContinuousInputSample(FlowEvent paramFlowEvent)
  {
    getDecorated().onContinuousInputSample(paramFlowEvent);
  }
  
  public void onContinuousInputSamples(List<FlowEvent> paramList)
  {
    getDecorated().onContinuousInputSamples(paramList);
  }
  
  public void onCreate(Context paramContext)
  {
    getDecorated().onCreate(paramContext);
  }
  
  public void onCycle(List<String> paramList)
  {
    getDecorated().onCycle(paramList);
  }
  
  public void onDestroy(Context paramContext)
  {
    getDecorated().onDestroy(paramContext);
  }
  
  public void onEnterKey()
  {
    getDecorated().onEnterKey();
  }
  
  public void onFinishInput()
  {
    getDecorated().onFinishInput();
  }
  
  public void onFinishInputView()
  {
    getDecorated().onFinishInputView();
  }
  
  public void onFlowBegun(boolean paramBoolean)
  {
    getDecorated().onFlowBegun(paramBoolean);
  }
  
  public void onFlowComplete()
  {
    getDecorated().onFlowComplete();
  }
  
  public boolean onHardKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return getDecorated().onHardKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onHardKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return getDecorated().onHardKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onInputFilterInput(int paramInt, String paramString)
  {
    getDecorated().onInputFilterInput(paramInt, paramString);
  }
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    getDecorated().onKeyboardChanged(paramKeyboardBehaviour);
  }
  
  public void onKeyboardHidden()
  {
    getDecorated().onKeyboardHidden();
  }
  
  public void onPredictionSelected(Candidate paramCandidate)
  {
    getDecorated().onPredictionSelected(paramCandidate);
  }
  
  public void onSoftKey(KeyEvent paramKeyEvent)
  {
    getDecorated().onSoftKey(paramKeyEvent);
  }
  
  public void onStartInput(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    getDecorated().onStartInput(paramEditorInfo, paramPackageInfoUtil, paramBoolean1, paramBoolean2);
  }
  
  public void onStartInputView(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    getDecorated().onStartInputView(paramEditorInfo, paramPackageInfoUtil, paramBoolean1, paramBoolean2);
  }
  
  public void refreshPredictions(boolean paramBoolean)
  {
    getDecorated().refreshPredictions(paramBoolean);
  }
  
  public void removeCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    getDecorated().removeCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void removePredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    getDecorated().removePredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void removeShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    getDecorated().removeShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void selectionUpdated(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    getDecorated().selectionUpdated(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  protected abstract void setDecorated(InputEventModel paramInputEventModel);
  
  public void setFluencyPunctuator(Punctuator paramPunctuator)
  {
    getDecorated().setFluencyPunctuator(paramPunctuator);
  }
  
  public void setStorageAvailable(boolean paramBoolean)
  {
    getDecorated().setStorageAvailable(paramBoolean);
  }
  
  public void updateKeyPressModel(Set<String> paramSet1, Set<String> paramSet2, FluencyServiceProxy paramFluencyServiceProxy, boolean paramBoolean)
  {
    getDecorated().updateKeyPressModel(paramSet1, paramSet2, paramFluencyServiceProxy, paramBoolean);
  }
  
  public void updateShiftState()
  {
    getDecorated().updateShiftState();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventModelDecorator
 * JD-Core Version:    0.7.0.1
 */