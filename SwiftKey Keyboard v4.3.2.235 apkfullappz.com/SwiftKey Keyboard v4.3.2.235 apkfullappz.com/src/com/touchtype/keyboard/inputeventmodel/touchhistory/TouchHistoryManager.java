package com.touchtype.keyboard.inputeventmodel.touchhistory;

import android.view.inputmethod.ExtractedText;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.service.ImmutableExtractedText;

public abstract interface TouchHistoryManager
{
  public abstract void addContinuousTouchSample(Point paramPoint);
  
  public abstract void dontGetWordsForLearningUntilResync();
  
  public abstract void flowFailed();
  
  public abstract void flushBufferedPredictionRequests();
  
  public abstract Sequence getContext();
  
  public abstract TouchHistoryMarker getCurrentTouchHistoryMarker();
  
  public abstract String getCurrentWord();
  
  public abstract CursorMarker getCursorMarker(ImmutableExtractedText paramImmutableExtractedText, int paramInt);
  
  public abstract LazySequence getWordsForLearning();
  
  public abstract boolean isHistoryTextEmpty();
  
  public abstract void resetAllHistory(ExtractedText paramExtractedText);
  
  public abstract void resetContinuousTouchSamples();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.TouchHistoryManager
 * JD-Core Version:    0.7.0.1
 */