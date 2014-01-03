package com.touchtype.keyboard.inputeventmodel;

import android.os.Handler;
import com.touchtype.keyboard.candidates.CandidatesUpdateRequestType;
import com.touchtype.keyboard.candidates.CandidatesUpdater;
import com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker;
import com.touchtype_fluency.Sequence;

public final class PredictionRequestManagerImpl
  implements PredictionRequestManager
{
  private final Handler mBufferedRequestHandler = new Handler();
  private final Runnable mBufferedRequestRunnable = new Runnable()
  {
    public void run()
    {
      PredictionRequestManagerImpl.this.doBufferedRequest();
    }
  };
  private boolean mBufferingPredictions = true;
  private final KeyboardState mKeyboardState;
  private TouchHistoryMarker mLastBufferedProxy = null;
  private Sequence mLastBufferedSequence = null;
  private CandidatesUpdateRequestType mLastBufferedType = null;
  private String mLastBufferedVerbatimText = null;
  private final ListenerManager mListenerManager;
  private boolean mPendingBufferedPrediction = false;
  private final CandidatesUpdater mUpdater;
  
  public PredictionRequestManagerImpl(ListenerManager paramListenerManager, CandidatesUpdater paramCandidatesUpdater, KeyboardState paramKeyboardState)
  {
    this.mListenerManager = paramListenerManager;
    this.mUpdater = paramCandidatesUpdater;
    this.mKeyboardState = paramKeyboardState;
  }
  
  private void doBufferedRequest()
  {
    if (this.mPendingBufferedPrediction)
    {
      requestPrediction(this.mLastBufferedType, this.mLastBufferedProxy, this.mLastBufferedSequence, this.mLastBufferedVerbatimText);
      this.mPendingBufferedPrediction = false;
      this.mBufferedRequestHandler.postDelayed(this.mBufferedRequestRunnable, 140L);
      return;
    }
    this.mBufferingPredictions = false;
  }
  
  private void requestPrediction(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryMarker paramTouchHistoryMarker, Sequence paramSequence, String paramString)
  {
    this.mListenerManager.notifyCandidateUpdateListeners();
    paramSequence.setFieldHint(this.mKeyboardState.hintForCurrentField());
    this.mUpdater.refresh(false, paramCandidatesUpdateRequestType, DefaultCandidatesUpdateRequestFactory.createRequest(paramSequence, paramString, paramTouchHistoryMarker, this.mKeyboardState));
  }
  
  public void flushBufferedPredictionRequests()
  {
    if (this.mBufferingPredictions)
    {
      if (this.mPendingBufferedPrediction)
      {
        requestPrediction(this.mLastBufferedType, this.mLastBufferedProxy, this.mLastBufferedSequence, this.mLastBufferedVerbatimText);
        this.mPendingBufferedPrediction = false;
      }
      this.mBufferingPredictions = false;
    }
  }
  
  public void requestBufferedPrediction(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryMarker paramTouchHistoryMarker, Sequence paramSequence, String paramString)
  {
    this.mLastBufferedType = paramCandidatesUpdateRequestType;
    this.mLastBufferedProxy = paramTouchHistoryMarker;
    this.mLastBufferedSequence = paramSequence;
    this.mLastBufferedVerbatimText = paramString;
    this.mPendingBufferedPrediction = true;
    if (!this.mBufferingPredictions)
    {
      this.mBufferingPredictions = true;
      doBufferedRequest();
    }
  }
  
  public void requestImmediatePrediction(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryMarker paramTouchHistoryMarker, Sequence paramSequence, String paramString)
  {
    if (this.mBufferingPredictions)
    {
      this.mBufferedRequestHandler.removeCallbacks(this.mBufferedRequestRunnable);
      this.mBufferingPredictions = false;
    }
    requestPrediction(paramCandidatesUpdateRequestType, paramTouchHistoryMarker, paramSequence, paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.PredictionRequestManagerImpl
 * JD-Core Version:    0.7.0.1
 */