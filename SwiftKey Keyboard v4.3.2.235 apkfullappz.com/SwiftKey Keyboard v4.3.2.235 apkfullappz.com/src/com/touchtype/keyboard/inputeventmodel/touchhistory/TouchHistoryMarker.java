package com.touchtype.keyboard.inputeventmodel.touchhistory;

import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;

public class TouchHistoryMarker
{
  private DeletionPerformed mDeletionPerformedAfterModification = DeletionPerformed.NONE;
  private final BackgroundExecutor mExecutor;
  private boolean mHasSample = false;
  private final int mKeyPressModelId;
  private boolean mModifiedByPredictionButNotYetCharacterised = false;
  private TouchHistoryProxy mTouchHistoryProxy;
  private ResultsFilter.CapitalizationHint mUnmodifiedCapsHint = null;
  private String mUnmodifiedText = null;
  
  public TouchHistoryMarker(CharSequence paramCharSequence, int paramInt, BackgroundExecutor paramBackgroundExecutor)
  {
    this(paramCharSequence, null, DeletionPerformed.NONE, paramInt, paramBackgroundExecutor);
  }
  
  private TouchHistoryMarker(CharSequence paramCharSequence, String paramString, DeletionPerformed paramDeletionPerformed, int paramInt, BackgroundExecutor paramBackgroundExecutor)
  {
    this.mTouchHistoryProxy = new TouchHistoryProxy(paramCharSequence.toString(), paramBackgroundExecutor);
    this.mUnmodifiedText = paramString;
    this.mDeletionPerformedAfterModification = paramDeletionPerformed;
    this.mKeyPressModelId = paramInt;
    this.mExecutor = paramBackgroundExecutor;
  }
  
  public TouchHistoryMarker appendHistory(TouchHistoryMarker paramTouchHistoryMarker)
  {
    if (this.mHasSample) {
      dropSamples();
    }
    this.mTouchHistoryProxy.appendHistory(paramTouchHistoryMarker.getTouchHistory());
    this.mHasSample = paramTouchHistoryMarker.getHasSample();
    return this;
  }
  
  public TouchHistoryMarker appendSample(Point paramPoint)
  {
    this.mTouchHistoryProxy.appendSample(paramPoint);
    this.mHasSample = true;
    return this;
  }
  
  public TouchHistoryMarker characterise(CharSequence paramCharSequence)
  {
    return new TouchHistoryMarker(paramCharSequence, this.mUnmodifiedText, this.mDeletionPerformedAfterModification, this.mKeyPressModelId, this.mExecutor);
  }
  
  public DeletionPerformed deletionPerformedAfterModification()
  {
    return this.mDeletionPerformedAfterModification;
  }
  
  public TouchHistoryMarker dropFirstTerms(Prediction paramPrediction, int paramInt)
  {
    this.mTouchHistoryProxy = this.mTouchHistoryProxy.dropFirstTerms(paramPrediction, paramInt);
    return this;
  }
  
  public TouchHistoryMarker dropLastPress()
  {
    if (this.mHasSample) {
      dropSamples();
    }
    this.mTouchHistoryProxy = this.mTouchHistoryProxy.dropLast(1);
    return this;
  }
  
  public TouchHistoryMarker dropSamples()
  {
    if (this.mHasSample)
    {
      this.mTouchHistoryProxy = this.mTouchHistoryProxy.dropLast(1);
      this.mHasSample = false;
    }
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    return super.equals(paramObject);
  }
  
  public boolean getHasSample()
  {
    return this.mHasSample;
  }
  
  public int getKeyPressModelId()
  {
    return this.mKeyPressModelId;
  }
  
  public TouchHistoryProxy getTouchHistory()
  {
    return this.mTouchHistoryProxy;
  }
  
  public int hashCode()
  {
    return super.hashCode();
  }
  
  public boolean modifiedByPredictionButNotYetCharacterised()
  {
    return this.mModifiedByPredictionButNotYetCharacterised;
  }
  
  public void setDeletionPerformedAfterModification(DeletionPerformed paramDeletionPerformed)
  {
    if (this.mUnmodifiedText != null) {
      this.mDeletionPerformedAfterModification = paramDeletionPerformed;
    }
  }
  
  public void setModifiedByPrediction(String paramString, ResultsFilter.CapitalizationHint paramCapitalizationHint)
  {
    this.mModifiedByPredictionButNotYetCharacterised = true;
    this.mUnmodifiedCapsHint = paramCapitalizationHint;
    this.mUnmodifiedText = paramString;
    this.mDeletionPerformedAfterModification = DeletionPerformed.NONE;
  }
  
  public void setTouchHistory(CharSequence paramCharSequence)
  {
    this.mTouchHistoryProxy = new TouchHistoryProxy(paramCharSequence.toString(), this.mExecutor);
    this.mHasSample = false;
  }
  
  public ResultsFilter.CapitalizationHint unmodifiedCapsHint()
  {
    return this.mUnmodifiedCapsHint;
  }
  
  public String unmodifiedText()
  {
    return this.mUnmodifiedText;
  }
  
  public static enum DeletionPerformed
  {
    static
    {
      FULL = new DeletionPerformed("FULL", 2);
      DeletionPerformed[] arrayOfDeletionPerformed = new DeletionPerformed[3];
      arrayOfDeletionPerformed[0] = NONE;
      arrayOfDeletionPerformed[1] = PARTIAL;
      arrayOfDeletionPerformed[2] = FULL;
      $VALUES = arrayOfDeletionPerformed;
    }
    
    private DeletionPerformed() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryMarker
 * JD-Core Version:    0.7.0.1
 */