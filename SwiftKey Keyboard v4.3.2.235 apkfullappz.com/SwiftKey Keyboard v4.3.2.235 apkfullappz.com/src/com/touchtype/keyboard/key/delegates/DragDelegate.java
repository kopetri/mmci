package com.touchtype.keyboard.key.delegates;

import android.graphics.PointF;
import android.util.SparseArray;
import com.touchtype.keyboard.key.actions.ActionType;
import com.touchtype.keyboard.key.callbacks.DragBehaviour;
import com.touchtype.keyboard.key.callbacks.DragBehaviour.DragThreshold;
import com.touchtype.keyboard.key.callbacks.DragCallback;
import com.touchtype.keyboard.key.callbacks.DragEvent;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public final class DragDelegate
  extends TouchSubDelegate
{
  private final DragBehaviour mBehaviour;
  private final DragCallback mCallback;
  private final SparseArray<Float> mCumulativeDistances;
  private boolean mDragClicked = false;
  private boolean mIsDragging = false;
  private final PointF mLastDrag = new PointF();
  private final PointF mStartPoint = new PointF();
  private boolean mTouchStayedInKey;
  
  public DragDelegate(DragCallback paramDragCallback, DragBehaviour paramDragBehaviour)
  {
    this.mCallback = paramDragCallback;
    this.mBehaviour = paramDragBehaviour;
    this.mCumulativeDistances = new SparseArray(paramDragBehaviour.size());
  }
  
  private static double calculateDistance(Integer paramInteger, PointF paramPointF1, PointF paramPointF2)
  {
    float f1 = paramPointF2.x - paramPointF1.x;
    float f2 = paramPointF2.y - paramPointF1.y;
    return Math.cos(Math.toRadians(paramInteger.intValue()) - Math.atan2(f2, f1)) * Math.sqrt(Math.pow(f1, 2.0D) + Math.pow(f2, 2.0D));
  }
  
  private Float calculateDrag(int paramInt, float paramFloat, PointF paramPointF)
  {
    if (paramFloat < 0.0F) {}
    for (;;)
    {
      return null;
      updateCumulativeDistance(paramInt, paramPointF);
      double d1 = calculateDistance(Integer.valueOf(paramInt), this.mStartPoint, paramPointF);
      if (this.mBehaviour.shouldReportNonDrags()) {}
      for (double d2 = d1; (d1 > 0.0D) && (d2 > paramFloat); d2 = getCurrentDistance(paramInt)) {
        return Float.valueOf((float)d1);
      }
    }
  }
  
  private Float calculateDragClick(int paramInt, float paramFloat, PointF paramPointF)
  {
    double d = calculateDistance(Integer.valueOf(paramInt), this.mStartPoint, paramPointF);
    if (d > paramFloat) {
      return Float.valueOf((float)d);
    }
    return null;
  }
  
  private DragEvent createDragClickEvent(PointF paramPointF)
  {
    DragEvent localDragEvent = null;
    Iterator localIterator = this.mBehaviour.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Float localFloat = calculateDragClick(((Integer)localEntry.getKey()).intValue(), ((DragBehaviour.DragThreshold)localEntry.getValue()).mDragClickThreshold, paramPointF);
      if (localFloat != null) {
        localDragEvent = DragEvent.greater(localDragEvent, new DragEvent(((Integer)localEntry.getKey()).intValue(), localFloat.floatValue()));
      }
    }
    if ((localDragEvent == null) && (this.mBehaviour.shouldReportNonDrags())) {
      localDragEvent = new DragEvent(0, 0.0F);
    }
    return localDragEvent;
  }
  
  private DragEvent createDragEvent(PointF paramPointF)
  {
    DragEvent localDragEvent = null;
    Iterator localIterator = this.mBehaviour.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      Float localFloat = calculateDrag(((Integer)localEntry.getKey()).intValue(), ((DragBehaviour.DragThreshold)localEntry.getValue()).mDragThreshold, paramPointF);
      if (localFloat != null) {
        localDragEvent = DragEvent.greater(localDragEvent, new DragEvent(((Integer)localEntry.getKey()).intValue(), localFloat.floatValue()));
      }
    }
    if ((localDragEvent == null) && (this.mBehaviour.shouldReportNonDrags())) {
      localDragEvent = new DragEvent(0, 0.0F);
    }
    this.mLastDrag.set(paramPointF);
    return localDragEvent;
  }
  
  private float getCurrentDistance(int paramInt)
  {
    Float localFloat = (Float)this.mCumulativeDistances.get(paramInt);
    if (localFloat == null) {
      return 0.0F;
    }
    return localFloat.floatValue();
  }
  
  private void reset()
  {
    this.mIsDragging = false;
    this.mDragClicked = false;
    this.mTouchStayedInKey = false;
  }
  
  private void updateCumulativeDistance(int paramInt, PointF paramPointF)
  {
    double d = getCurrentDistance(paramInt) + Math.abs(calculateDistance(Integer.valueOf(paramInt), this.mLastDrag, paramPointF));
    this.mCumulativeDistances.put(paramInt, Float.valueOf((float)d));
  }
  
  public void cancel()
  {
    reset();
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    reset();
    this.mStartPoint.set(paramTouch.getPoint());
    this.mLastDrag.set(paramTouch.getPoint());
    this.mCumulativeDistances.clear();
    this.mTouchStayedInKey = true;
  }
  
  public boolean handleGesture(PointF paramPointF)
  {
    boolean bool = true;
    if (!this.mTouchStayedInKey) {
      bool = false;
    }
    DragEvent localDragEvent;
    do
    {
      return bool;
      localDragEvent = createDragEvent(paramPointF);
    } while (localDragEvent == null);
    this.mIsDragging = bool;
    this.mCallback.drag(localDragEvent);
    return bool;
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return handleGesture(paramTouch.getPoint());
  }
  
  public boolean hasFired(EnumSet<ActionType> paramEnumSet)
  {
    return ((paramEnumSet.contains(ActionType.DRAG)) && (this.mIsDragging)) || ((paramEnumSet.contains(ActionType.DRAG_CLICK)) && (this.mDragClicked));
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    reset();
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    reset();
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    DragEvent localDragEvent = createDragClickEvent(paramTouch.getPoint());
    if (localDragEvent != null)
    {
      this.mDragClicked = true;
      this.mCallback.dragClick(localDragEvent);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.DragDelegate
 * JD-Core Version:    0.7.0.1
 */