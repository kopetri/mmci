package com.touchtype.keyboard;

import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype_fluency.Point;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.TouchHistory.ShiftState;

public final class TouchHistoryProxy
{
  private final BackgroundExecutor executor;
  private TouchHistory touchHistory;
  
  private TouchHistoryProxy(TouchHistory paramTouchHistory, BackgroundExecutor paramBackgroundExecutor)
  {
    this.touchHistory = paramTouchHistory;
    this.executor = paramBackgroundExecutor;
  }
  
  public TouchHistoryProxy(String paramString, BackgroundExecutor paramBackgroundExecutor)
  {
    this.touchHistory = new TouchHistory(paramString);
    this.executor = paramBackgroundExecutor;
  }
  
  public void addCharacter(final Character paramCharacter)
  {
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.this.touchHistory.addCharacter(paramCharacter);
      }
    });
  }
  
  public void addPress(final Point paramPoint, final TouchHistory.ShiftState paramShiftState)
  {
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.this.touchHistory.addPress(paramPoint, paramShiftState);
      }
    });
  }
  
  public void appendHistory(final TouchHistoryProxy paramTouchHistoryProxy)
  {
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.this.touchHistory.appendHistory(paramTouchHistoryProxy.getTouchHistory());
      }
    });
  }
  
  public void appendSample(final Point paramPoint)
  {
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.this.touchHistory.appendSample(paramPoint);
      }
    });
  }
  
  public TouchHistoryProxy dropFirstTerms(final Prediction paramPrediction, final int paramInt)
  {
    final TouchHistoryProxy localTouchHistoryProxy = new TouchHistoryProxy(null, this.executor);
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.access$002(localTouchHistoryProxy, TouchHistoryProxy.this.touchHistory.dropFirstTerms(paramPrediction, paramInt));
      }
    });
    return localTouchHistoryProxy;
  }
  
  public TouchHistoryProxy dropLast(final int paramInt)
  {
    final TouchHistoryProxy localTouchHistoryProxy = new TouchHistoryProxy(null, this.executor);
    this.executor.submit(new Runnable()
    {
      public void run()
      {
        TouchHistoryProxy.access$002(localTouchHistoryProxy, TouchHistoryProxy.this.touchHistory.dropLast(paramInt));
      }
    });
    return localTouchHistoryProxy;
  }
  
  public TouchHistory getTouchHistory()
  {
    return this.touchHistory;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.TouchHistoryProxy
 * JD-Core Version:    0.7.0.1
 */