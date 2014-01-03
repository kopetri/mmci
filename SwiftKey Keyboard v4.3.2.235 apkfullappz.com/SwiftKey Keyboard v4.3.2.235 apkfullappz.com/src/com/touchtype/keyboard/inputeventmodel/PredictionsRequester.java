package com.touchtype.keyboard.inputeventmodel;

import com.touchtype.keyboard.candidates.CandidatesUpdateRequest;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestFactory;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.candidates.CandidatesUpdater;
import com.touchtype.keyboard.candidates.UpdatedCandidatesListener;
import com.touchtype.keyboard.inputeventmodel.listeners.OnShiftStateChangedListener;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;

public final class PredictionsRequester
  implements OnShiftStateChangedListener, PredictionsAvailabilityListener
{
  private CandidatesUpdater mCandidatesUpdater;
  private ListenerManager mListenerManager;
  private PredictionsAvailability mPredictionsAvailability;
  private CandidatesUpdateRequestFactory mRequestFactory;
  private TouchTypeSoftKeyboard.ShiftState mShiftState = TouchTypeSoftKeyboard.ShiftState.UNSHIFTED;
  
  public PredictionsRequester(CandidatesUpdater paramCandidatesUpdater, CandidatesUpdateRequestFactory paramCandidatesUpdateRequestFactory, ListenerManager paramListenerManager)
  {
    this.mCandidatesUpdater = paramCandidatesUpdater;
    this.mRequestFactory = paramCandidatesUpdateRequestFactory;
    this.mListenerManager = paramListenerManager;
  }
  
  private void refreshPredictions(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (this.mPredictionsAvailability == PredictionsAvailability.ENABLED)
    {
      CandidatesUpdateRequest localCandidatesUpdateRequest = this.mRequestFactory.createRequest();
      this.mListenerManager.notifyCandidateUpdateListeners();
      this.mCandidatesUpdater.refresh(paramBoolean, paramCandidatesUpdateRequestType, localCandidatesUpdateRequest);
    }
  }
  
  public void addUpdatedCandidatesListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt)
  {
    this.mCandidatesUpdater.addListener(paramUpdatedCandidatesListener, paramInt, this.mRequestFactory.createRequest());
  }
  
  public void handleShiftStateChanged(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    if (paramShiftState != this.mShiftState) {
      refreshPredictions(false, CandidatesUpdateRequestType.DEFAULT);
    }
    this.mShiftState = paramShiftState;
  }
  
  public void invalidateCandidates(boolean paramBoolean)
  {
    refreshPredictions(paramBoolean, CandidatesUpdateRequestType.DEFAULT);
  }
  
  public void onPredictionsAvailabilityUpdate(PredictionsAvailability paramPredictionsAvailability)
  {
    if (this.mPredictionsAvailability != paramPredictionsAvailability) {
      setPredictionsAvailability(paramPredictionsAvailability);
    }
  }
  
  public void setPredictionsAvailability(PredictionsAvailability paramPredictionsAvailability)
  {
    this.mPredictionsAvailability = paramPredictionsAvailability;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.PredictionsRequester
 * JD-Core Version:    0.7.0.1
 */