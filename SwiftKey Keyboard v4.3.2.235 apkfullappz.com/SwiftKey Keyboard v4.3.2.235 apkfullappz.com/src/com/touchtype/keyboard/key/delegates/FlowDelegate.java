package com.touchtype.keyboard.key.delegates;

import android.graphics.PointF;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.view.fx.FlowEventBroadcaster;
import com.touchtype.keyboard.view.touch.FlowEvent;
import com.touchtype.keyboard.view.touch.FlowEvent.ActionType;
import com.touchtype.keyboard.view.touch.TouchEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public class FlowDelegate
  extends TouchSubDelegate
{
  private static final String TAG = FlowDelegate.class.getSimpleName();
  private final FlowCallback mCallback;
  private PointF mInitialPoint = null;
  private InputSampleBuffer mSampleBuffer = new InputSampleBuffer(this);
  private boolean mTouchStayedInThisKey;
  private final float mXThreshold;
  private final float mYThreshold;
  
  public FlowDelegate(FlowCallback paramFlowCallback, float paramFloat1, float paramFloat2)
  {
    this.mCallback = paramFlowCallback;
    this.mXThreshold = paramFloat1;
    this.mYThreshold = paramFloat2;
  }
  
  private void clearInitialPoint()
  {
    this.mInitialPoint = null;
  }
  
  private boolean isNearInitialPoint(float paramFloat1, float paramFloat2)
  {
    return (this.mInitialPoint != null) && (Math.abs(this.mInitialPoint.x - paramFloat1) < this.mXThreshold) && (Math.abs(this.mInitialPoint.y - paramFloat2) < this.mYThreshold);
  }
  
  private FlowEvent makeHistoricalFlowEvent(TouchEvent.Touch paramTouch, int paramInt)
  {
    return new FlowEvent(paramTouch.getTouchEvent().extractHistoricalTouch(paramTouch.getPointerIndex(), paramInt), FlowEvent.ActionType.DRAG);
  }
  
  private void setInitialPoint(PointF paramPointF)
  {
    this.mInitialPoint = paramPointF;
  }
  
  public void cancel()
  {
    this.mTouchStayedInThisKey = false;
    this.mSampleBuffer.clear();
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInThisKey = true;
    setInitialPoint(paramTouch.getPoint());
    this.mSampleBuffer.clear();
    this.mSampleBuffer.addToBuffer(makeFlowEvent(paramTouch, FlowEvent.ActionType.DOWN));
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    TouchEvent localTouchEvent = paramTouch.getTouchEvent();
    int i = paramTouch.getPointerIndex();
    int j = localTouchEvent.getHistorySize();
    if (this.mTouchStayedInThisKey)
    {
      for (int m = 0; m < j; m++) {
        this.mSampleBuffer.addToBuffer(makeHistoricalFlowEvent(paramTouch, m));
      }
      this.mSampleBuffer.addToBuffer(makeFlowEvent(paramTouch));
    }
    while ((this.mTouchStayedInThisKey) && (isNearInitialPoint(localTouchEvent.getX(i), localTouchEvent.getY(i))))
    {
      return true;
      for (int k = 0; k < j; k++) {
        this.mSampleBuffer.doWrite(makeHistoricalFlowEvent(paramTouch, k));
      }
      this.mSampleBuffer.doWrite(makeFlowEvent(paramTouch));
    }
    return false;
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return false;
  }
  
  FlowEvent makeFlowEvent(TouchEvent.Touch paramTouch)
  {
    return new FlowEvent(paramTouch, FlowEvent.ActionType.DRAG);
  }
  
  FlowEvent makeFlowEvent(TouchEvent.Touch paramTouch, FlowEvent.ActionType paramActionType)
  {
    return new FlowEvent(paramTouch, paramActionType);
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInThisKey = false;
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mTouchStayedInThisKey = false;
    if (this.mInitialPoint != null) {
      this.mCallback.flowStarted();
    }
    this.mSampleBuffer.writeAll();
    clearInitialPoint();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    clearInitialPoint();
    this.mSampleBuffer.clear();
    this.mCallback.flowComplete(makeFlowEvent(paramTouch, FlowEvent.ActionType.UP));
  }
  
  public static abstract interface FlowCallback
  {
    public abstract void flow(List<FlowEvent> paramList);
    
    public abstract void flowComplete(FlowEvent paramFlowEvent);
    
    public abstract void flowStarted();
  }
  
  private static final class InputSampleBuffer
  {
    private final FlowDelegate delegate;
    private final List<FlowEvent> mEventBuffer = new ArrayList(32);
    
    public InputSampleBuffer(FlowDelegate paramFlowDelegate)
    {
      this.delegate = paramFlowDelegate;
    }
    
    public void addToBuffer(FlowEvent paramFlowEvent)
    {
      this.mEventBuffer.add(paramFlowEvent);
    }
    
    public void clear()
    {
      this.mEventBuffer.clear();
    }
    
    public void doWrite(FlowEvent paramFlowEvent)
    {
      FlowEventBroadcaster.get().broadcastEvent(paramFlowEvent);
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramFlowEvent);
      this.delegate.mCallback.flow(localArrayList);
    }
    
    public void writeAll()
    {
      Iterator localIterator = this.mEventBuffer.iterator();
      while (localIterator.hasNext()) {
        doWrite((FlowEvent)localIterator.next());
      }
      clear();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.FlowDelegate
 * JD-Core Version:    0.7.0.1
 */