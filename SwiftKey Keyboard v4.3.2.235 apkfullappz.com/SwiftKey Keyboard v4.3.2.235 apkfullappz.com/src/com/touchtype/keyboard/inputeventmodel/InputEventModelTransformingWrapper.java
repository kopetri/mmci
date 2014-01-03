package com.touchtype.keyboard.inputeventmodel;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import com.touchtype.keyboard.BufferedInputListener;
import com.touchtype.keyboard.ExtendedKeyEvent;
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
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.EnumSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

public final class InputEventModelTransformingWrapper
  implements InputEventModel
{
  private Rect mBoundsOnScreen;
  protected final Matrix mOffsetMatrix = new Matrix();
  private InputEventModel mWrapped;
  
  public InputEventModelTransformingWrapper(InputEventModel paramInputEventModel)
  {
    this.mWrapped = paramInputEventModel;
    this.mOffsetMatrix.reset();
  }
  
  private Point transformedPoint(Point paramPoint)
  {
    this.mOffsetMatrix.setTranslate(this.mBoundsOnScreen.left + paramPoint.getX() * this.mBoundsOnScreen.width(), this.mBoundsOnScreen.top + paramPoint.getY() * this.mBoundsOnScreen.height());
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramPoint.getX();
    arrayOfFloat[1] = paramPoint.getY();
    this.mOffsetMatrix.mapPoints(arrayOfFloat);
    return new Point(arrayOfFloat[0], arrayOfFloat[1]);
  }
  
  private FlowEvent transformedSample(FlowEvent paramFlowEvent)
  {
    this.mOffsetMatrix.setTranslate(this.mBoundsOnScreen.left + paramFlowEvent.point.x * this.mBoundsOnScreen.width(), this.mBoundsOnScreen.top + paramFlowEvent.point.y * this.mBoundsOnScreen.height());
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramFlowEvent.point.x;
    arrayOfFloat[1] = paramFlowEvent.point.y;
    this.mOffsetMatrix.mapPoints(arrayOfFloat);
    return new FlowEvent(paramFlowEvent, new PointF(arrayOfFloat[0], arrayOfFloat[1]));
  }
  
  public void addBufferedInputListener(BufferedInputListener paramBufferedInputListener)
  {
    this.mWrapped.addBufferedInputListener(paramBufferedInputListener);
  }
  
  public void addCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mWrapped.addCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void addFlowStateChangedListener(OnFlowStateChangedListener paramOnFlowStateChangedListener)
  {
    this.mWrapped.addFlowStateChangedListener(paramOnFlowStateChangedListener);
  }
  
  public void addInputFilterListener(InputFilterListener paramInputFilterListener, int paramInt)
  {
    this.mWrapped.addInputFilterListener(paramInputFilterListener, paramInt);
  }
  
  public void addPredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mWrapped.addPredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void addShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mWrapped.addShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void addUpdatedCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt)
  {
    this.mWrapped.addUpdatedCandidatesListener(paramUpdatedCandidatesListener, paramInt);
  }
  
  public void autoCommitFlow(Candidate paramCandidate, TouchHistoryProxy paramTouchHistoryProxy)
  {
    this.mWrapped.autoCommitFlow(paramCandidate, paramTouchHistoryProxy);
  }
  
  public void autoCommitUpToFailedFlow(Candidate paramCandidate)
  {
    this.mWrapped.autoCommitUpToFailedFlow(paramCandidate);
  }
  
  public void commitBuffer()
  {
    this.mWrapped.commitBuffer();
  }
  
  public void cycleCandidates()
  {
    this.mWrapped.cycleCandidates();
  }
  
  public void enabledLanguagePacksChanged(int paramInt)
  {
    this.mWrapped.enabledLanguagePacksChanged(paramInt);
  }
  
  public boolean evaluateInputShownUsedInsteadOfUpdateSelection()
  {
    return this.mWrapped.evaluateInputShownUsedInsteadOfUpdateSelection();
  }
  
  public KeyPressModel getKeyPressModel()
  {
    return this.mWrapped.getKeyPressModel();
  }
  
  public TouchTypeSoftKeyboard.ShiftState getShiftState()
  {
    return this.mWrapped.getShiftState();
  }
  
  public TouchTypeExtractedText getTouchTypeExtractedText(boolean paramBoolean)
  {
    return this.mWrapped.getTouchTypeExtractedText(paramBoolean);
  }
  
  public void handleDeleteLastWord(int paramInt, EnumSet<ActionType> paramEnumSet)
  {
    this.mWrapped.handleDeleteLastWord(paramInt, paramEnumSet);
  }
  
  public void handleVoiceInput(CharSequence paramCharSequence)
  {
    this.mWrapped.handleVoiceInput(paramCharSequence);
  }
  
  public boolean isLayoutCyclingEnabled()
  {
    return this.mWrapped.isLayoutCyclingEnabled();
  }
  
  public boolean isPredictionEnabled()
  {
    return this.mWrapped.isPredictionEnabled();
  }
  
  public boolean isSearchField()
  {
    return this.mWrapped.isSearchField();
  }
  
  public void newBoundsOnScreen(Rect paramRect)
  {
    this.mBoundsOnScreen = paramRect;
  }
  
  public void onCompletionAccepted(CompletionInfo paramCompletionInfo)
  {
    this.mWrapped.onCompletionAccepted(paramCompletionInfo);
  }
  
  public void onContinuousInputSample(FlowEvent paramFlowEvent)
  {
    if (this.mBoundsOnScreen != null)
    {
      this.mWrapped.onContinuousInputSample(transformedSample(paramFlowEvent));
      return;
    }
    this.mWrapped.onContinuousInputSample(paramFlowEvent);
  }
  
  public void onContinuousInputSamples(List<FlowEvent> paramList)
  {
    if ((this.mBoundsOnScreen != null) && (this.mBoundsOnScreen != null))
    {
      ListIterator localListIterator = paramList.listIterator();
      while (localListIterator.hasNext()) {
        localListIterator.set(transformedSample((FlowEvent)localListIterator.next()));
      }
    }
    this.mWrapped.onContinuousInputSamples(paramList);
  }
  
  public void onCreate(Context paramContext)
  {
    this.mWrapped.onCreate(paramContext);
  }
  
  public void onCycle(List<String> paramList)
  {
    this.mWrapped.onCycle(paramList);
  }
  
  public void onDestroy(Context paramContext)
  {
    this.mWrapped.onDestroy(paramContext);
  }
  
  public void onEnterKey()
  {
    this.mWrapped.onEnterKey();
  }
  
  public void onFinishInput()
  {
    this.mWrapped.onFinishInput();
  }
  
  public void onFinishInputView()
  {
    this.mWrapped.onFinishInputView();
  }
  
  public void onFlowBegun(boolean paramBoolean)
  {
    this.mWrapped.onFlowBegun(paramBoolean);
  }
  
  public void onFlowComplete()
  {
    this.mWrapped.onFlowComplete();
  }
  
  public boolean onHardKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mWrapped.onHardKeyDown(paramInt, paramKeyEvent);
  }
  
  public boolean onHardKeyUp(int paramInt, KeyEvent paramKeyEvent)
  {
    return this.mWrapped.onHardKeyUp(paramInt, paramKeyEvent);
  }
  
  public void onInputFilterInput(int paramInt, String paramString)
  {
    this.mWrapped.onInputFilterInput(paramInt, paramString);
  }
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    this.mWrapped.onKeyboardChanged(paramKeyboardBehaviour);
  }
  
  public void onKeyboardHidden()
  {
    this.mWrapped.onKeyboardHidden();
  }
  
  public void onPredictionSelected(Candidate paramCandidate)
  {
    this.mWrapped.onPredictionSelected(paramCandidate);
  }
  
  public void onSoftKey(KeyEvent paramKeyEvent)
  {
    if ((this.mBoundsOnScreen != null) && ((paramKeyEvent instanceof ExtendedKeyEvent)))
    {
      Point localPoint = ((ExtendedKeyEvent)paramKeyEvent).getTouchPoint();
      if (localPoint != null) {
        ((ExtendedKeyEvent)paramKeyEvent).setTouchPoint(transformedPoint(localPoint));
      }
    }
    this.mWrapped.onSoftKey(paramKeyEvent);
  }
  
  public void onStartInput(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mWrapped.onStartInput(paramEditorInfo, paramPackageInfoUtil, paramBoolean1, paramBoolean2);
  }
  
  public void onStartInputView(EditorInfo paramEditorInfo, PackageInfoUtil paramPackageInfoUtil, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mWrapped.onStartInputView(paramEditorInfo, paramPackageInfoUtil, paramBoolean1, paramBoolean2);
  }
  
  public void refreshPredictions(boolean paramBoolean)
  {
    this.mWrapped.refreshPredictions(paramBoolean);
  }
  
  public void removeCandidateUpdateListener(OnCandidatesUpdateRequestListener paramOnCandidatesUpdateRequestListener)
  {
    this.mWrapped.removeCandidateUpdateListener(paramOnCandidatesUpdateRequestListener);
  }
  
  public void removePredictionsEnabledListener(PredictionsAvailabilityListener paramPredictionsAvailabilityListener)
  {
    this.mWrapped.removePredictionsEnabledListener(paramPredictionsAvailabilityListener);
  }
  
  public void removeShiftStateChangedListener(OnShiftStateChangedListener paramOnShiftStateChangedListener)
  {
    this.mWrapped.removeShiftStateChangedListener(paramOnShiftStateChangedListener);
  }
  
  public void selectionUpdated(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.mWrapped.selectionUpdated(paramInt1, paramInt2, paramInt3, paramInt4, paramInt5, paramInt6);
  }
  
  public void setFluencyPunctuator(Punctuator paramPunctuator)
  {
    this.mWrapped.setFluencyPunctuator(paramPunctuator);
  }
  
  public void setStorageAvailable(boolean paramBoolean)
  {
    this.mWrapped.setStorageAvailable(paramBoolean);
  }
  
  public void updateKeyPressModel(Set<String> paramSet1, Set<String> paramSet2, FluencyServiceProxy paramFluencyServiceProxy, boolean paramBoolean)
  {
    this.mWrapped.updateKeyPressModel(paramSet1, paramSet2, paramFluencyServiceProxy, paramBoolean);
  }
  
  public void updateShiftState()
  {
    this.mWrapped.updateShiftState();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper
 * JD-Core Version:    0.7.0.1
 */