package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import com.touchtype.keyboard.KeyboardChangeListener;
import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
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

public abstract interface InputEventModel
  extends KeyboardChangeListener, ListenerRegister
{
  public abstract void addUpdatedCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt);
  
  public abstract void autoCommitFlow(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy);
  
  public abstract void autoCommitUpToFailedFlow(Candidate paramCandidate);
  
  public abstract void commitBuffer();
  
  public abstract void cycleCandidates();
  
  public abstract void enabledLanguagePacksChanged(int paramInt);
  
  public abstract boolean evaluateInputShownUsedInsteadOfUpdateSelection();
  
  public abstract KeyPressModel getKeyPressModel();
  
  public abstract TouchTypeSoftKeyboard.ShiftState getShiftState();
  
  public abstract TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean);
  
  public abstract void handleDeleteLastWord(int paramInt, EnumSet<ActionType> paramEnumSet);
  
  public abstract void handleVoiceInput(CharSequence paramCharSequence);
  
  public abstract boolean isLayoutCyclingEnabled();
  
  public abstract boolean isPredictionEnabled();
  
  public abstract boolean isSearchField();
  
  public abstract void onCompletionAccepted(CompletionInfo paramCompletionInfo);
  
  public abstract void onContinuousInputSample(FlowEvent paramFlowEvent);
  
  public abstract void onContinuousInputSamples(List<FlowEvent> paramList);
  
  public abstract void onCreate(Context paramContext);
  
  public abstract void onCycle(List<String> paramList);
  
  public abstract void onDestroy(Context paramContext);
  
  public abstract void onEnterKey();
  
  public abstract void onFinishInput();
  
  public abstract void onFinishInputView();
  
  public abstract void onFlowBegun(boolean paramBoolean);
  
  public abstract void onFlowComplete();
  
  public abstract boolean onHardKeyDown(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract boolean onHardKeyUp(int paramInt, KeyEvent paramKeyEvent);
  
  public abstract void onInputFilterInput(int paramInt, String paramString);
  
  public abstract void onKeyboardHidden();
  
  public abstract void onPredictionSelected(Candidate paramCandidate);
  
  public abstract void onSoftKey(KeyEvent paramKeyEvent);
  
  public abstract void onStartInput(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void onStartInputView(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2);
  
  public abstract void refreshPredictions(boolean paramBoolean);
  
  public abstract void selectionUpdated(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
  
  public abstract void setFluencyPunctuator(Punctuator paramPunctuator);
  
  public abstract void setStorageAvailable(boolean paramBoolean);
  
  public abstract void updateKeyPressModel(Set<String> paramSet1, Set<String> paramSet2, FluencyServiceProxy paramFluencyServiceProxy, boolean paramBoolean);
  
  public abstract void updateShiftState();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventModel
 * JD-Core Version:    0.7.0.1
 */