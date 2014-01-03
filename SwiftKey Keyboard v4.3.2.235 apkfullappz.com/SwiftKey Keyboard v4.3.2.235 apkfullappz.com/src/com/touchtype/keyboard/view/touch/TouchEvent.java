package com.touchtype.keyboard.view.touch;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.view.MotionEvent;

public final class TouchEvent
{
  final Matrix mMatrix;
  final MotionEvent mMotionEvent;
  
  private TouchEvent(MotionEvent paramMotionEvent, Matrix paramMatrix)
  {
    this.mMotionEvent = paramMotionEvent;
    this.mMatrix = paramMatrix;
  }
  
  public static TouchEvent createDummyTouchEvent(PointF paramPointF, int paramInt, Matrix paramMatrix)
  {
    Matrix localMatrix = new Matrix();
    paramMatrix.invert(localMatrix);
    PointF localPointF = transformPoint(paramPointF, localMatrix);
    return createTouchEvent(MotionEvent.obtain(System.currentTimeMillis(), System.currentTimeMillis(), paramInt, localPointF.x, localPointF.y, 0), paramMatrix);
  }
  
  public static TouchEvent createTouchEvent(MotionEvent paramMotionEvent, Matrix paramMatrix)
  {
    return new TouchEvent(paramMotionEvent, paramMatrix);
  }
  
  private PointF rawHistoricalPoint(int paramInt1, int paramInt2)
  {
    return new PointF(this.mMotionEvent.getHistoricalX(paramInt1, paramInt2), this.mMotionEvent.getHistoricalY(paramInt1, paramInt2));
  }
  
  private PointF rawPoint(int paramInt)
  {
    return new PointF(this.mMotionEvent.getX(paramInt), this.mMotionEvent.getY(paramInt));
  }
  
  public static TouchEvent replace(TouchEvent paramTouchEvent, Matrix paramMatrix)
  {
    return new TouchEvent(paramTouchEvent.mMotionEvent, paramMatrix);
  }
  
  private static PointF transformPoint(PointF paramPointF, Matrix paramMatrix)
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramPointF.x;
    arrayOfFloat[1] = paramPointF.y;
    paramMatrix.mapPoints(arrayOfFloat);
    return new PointF(arrayOfFloat[0], arrayOfFloat[1]);
  }
  
  public Touch extractCurrentTouch(int paramInt)
  {
    return new CurrentTouch(paramInt, null);
  }
  
  public Touch extractHistoricalTouch(int paramInt1, int paramInt2)
  {
    return new HistoricalTouch(paramInt1, paramInt2, null);
  }
  
  public int getActionIndex()
  {
    return this.mMotionEvent.getActionIndex();
  }
  
  public int getActionMasked()
  {
    return this.mMotionEvent.getActionMasked();
  }
  
  public long getEventTime()
  {
    return this.mMotionEvent.getEventTime();
  }
  
  public long getHistoricalEventTime(int paramInt)
  {
    return this.mMotionEvent.getHistoricalEventTime(paramInt);
  }
  
  public PointF getHistoricalPoint(int paramInt1, int paramInt2)
  {
    return transformPoint(rawHistoricalPoint(paramInt1, paramInt2), this.mMatrix);
  }
  
  public float getHistoricalX(int paramInt1, int paramInt2)
  {
    return getHistoricalPoint(paramInt1, paramInt2).x;
  }
  
  public float getHistoricalY(int paramInt1, int paramInt2)
  {
    return getHistoricalPoint(paramInt1, paramInt2).y;
  }
  
  public int getHistorySize()
  {
    return this.mMotionEvent.getHistorySize();
  }
  
  public MotionEvent getMotionEvent()
  {
    return this.mMotionEvent;
  }
  
  public PointF getPoint()
  {
    return getPoint(0);
  }
  
  public PointF getPoint(int paramInt)
  {
    return transformPoint(rawPoint(paramInt), this.mMatrix);
  }
  
  public int getPointerCount()
  {
    return this.mMotionEvent.getPointerCount();
  }
  
  public int getPointerId(int paramInt)
  {
    return this.mMotionEvent.getPointerId(paramInt);
  }
  
  public float getX()
  {
    return getPoint().x;
  }
  
  public float getX(int paramInt)
  {
    return getPoint(paramInt).x;
  }
  
  public float getY()
  {
    return getPoint().y;
  }
  
  public float getY(int paramInt)
  {
    return getPoint(paramInt).y;
  }
  
  public final class CurrentTouch
    implements TouchEvent.Touch
  {
    private final int mPointerIndex;
    
    private CurrentTouch(int paramInt)
    {
      this.mPointerIndex = paramInt;
    }
    
    public MotionEvent getMotionEvent()
    {
      return TouchEvent.this.getMotionEvent();
    }
    
    public PointF getPoint()
    {
      return TouchEvent.this.getPoint(this.mPointerIndex);
    }
    
    public int getPointerIndex()
    {
      return this.mPointerIndex;
    }
    
    public long getTime()
    {
      return TouchEvent.this.getEventTime();
    }
    
    public TouchEvent getTouchEvent()
    {
      return TouchEvent.this;
    }
    
    public float getX()
    {
      return TouchEvent.this.getX(this.mPointerIndex);
    }
    
    public float getY()
    {
      return TouchEvent.this.getY(this.mPointerIndex);
    }
  }
  
  public final class HistoricalTouch
    implements TouchEvent.Touch
  {
    private final int mHistoricalIndex;
    private PointF mPoint;
    private final int mPointerIndex;
    
    private HistoricalTouch(int paramInt1, int paramInt2)
    {
      this.mPointerIndex = paramInt1;
      this.mHistoricalIndex = paramInt2;
      this.mPoint = TouchEvent.this.getHistoricalPoint(this.mPointerIndex, this.mHistoricalIndex);
    }
    
    public MotionEvent getMotionEvent()
    {
      return TouchEvent.this.getMotionEvent();
    }
    
    public PointF getPoint()
    {
      return this.mPoint;
    }
    
    public int getPointerIndex()
    {
      return this.mPointerIndex;
    }
    
    public long getTime()
    {
      return TouchEvent.this.getHistoricalEventTime(this.mHistoricalIndex);
    }
    
    public TouchEvent getTouchEvent()
    {
      return TouchEvent.this;
    }
    
    public float getX()
    {
      return TouchEvent.this.getHistoricalX(this.mPointerIndex, this.mHistoricalIndex);
    }
    
    public float getY()
    {
      return TouchEvent.this.getHistoricalY(this.mPointerIndex, this.mHistoricalIndex);
    }
  }
  
  public static abstract interface Touch
  {
    public abstract MotionEvent getMotionEvent();
    
    public abstract PointF getPoint();
    
    public abstract int getPointerIndex();
    
    public abstract long getTime();
    
    public abstract TouchEvent getTouchEvent();
    
    public abstract float getX();
    
    public abstract float getY();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.TouchEvent
 * JD-Core Version:    0.7.0.1
 */