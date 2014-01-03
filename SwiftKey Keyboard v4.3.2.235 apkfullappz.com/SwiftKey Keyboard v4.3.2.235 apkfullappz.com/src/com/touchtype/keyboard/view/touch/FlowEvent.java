package com.touchtype.keyboard.view.touch;

import android.graphics.PointF;
import android.view.MotionEvent;
import com.touchtype_fluency.Point;

public final class FlowEvent
{
  public final ActionType action;
  public final PointF point;
  public final Point rawPoint;
  public final long time;
  private final float xScale;
  private final float yScale;
  
  public FlowEvent(FlowEvent paramFlowEvent, float paramFloat1, float paramFloat2)
  {
    this.action = paramFlowEvent.action;
    this.point = paramFlowEvent.point;
    this.time = paramFlowEvent.time;
    this.rawPoint = paramFlowEvent.rawPoint;
    this.xScale = paramFloat1;
    this.yScale = paramFloat2;
  }
  
  public FlowEvent(FlowEvent paramFlowEvent, PointF paramPointF)
  {
    this.action = paramFlowEvent.action;
    this.point = paramPointF;
    this.time = paramFlowEvent.time;
    this.rawPoint = paramFlowEvent.rawPoint;
    this.xScale = paramFlowEvent.xScale;
    this.yScale = paramFlowEvent.yScale;
  }
  
  public FlowEvent(TouchEvent.Touch paramTouch, ActionType paramActionType)
  {
    this.action = paramActionType;
    this.point = paramTouch.getPoint();
    this.time = paramTouch.getTime();
    MotionEvent localMotionEvent = paramTouch.getMotionEvent();
    this.rawPoint = new Point(localMotionEvent.getX(), localMotionEvent.getY());
    this.xScale = 1.0F;
    this.yScale = 1.0F;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    FlowEvent localFlowEvent;
    do
    {
      return true;
      if (!(paramObject instanceof FlowEvent)) {
        return false;
      }
      localFlowEvent = (FlowEvent)paramObject;
    } while ((localFlowEvent.toPoint().equals(toPoint())) && (localFlowEvent.rawPoint.equals(this.rawPoint)) && (localFlowEvent.time == this.time) && (localFlowEvent.action.equals(this.action)));
    return false;
  }
  
  public Point getRawPoint()
  {
    return this.rawPoint;
  }
  
  public int hashCode()
  {
    return (int)(31.0F * (23.0F * (13.0F * (17.0F + toPoint().hashCode()) + this.rawPoint.hashCode()) + (float)this.time) + this.action.hashCode());
  }
  
  public Point toPoint()
  {
    return new Point(this.point.x * this.xScale, this.point.y * this.yScale);
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[4];
    arrayOfObject[0] = Float.valueOf(this.point.x);
    arrayOfObject[1] = Float.valueOf(this.point.y);
    arrayOfObject[2] = Long.valueOf(this.time);
    arrayOfObject[3] = this.action.toString();
    return String.format("FlowEvent[x=%1.0f, y=%1.0f, time=%d, action=%s]", arrayOfObject);
  }
  
  public FlowEvent transform(FlowEvent paramFlowEvent, float paramFloat1, float paramFloat2)
  {
    return new FlowEvent(paramFlowEvent, paramFloat1 * paramFlowEvent.xScale, paramFloat2 * paramFlowEvent.yScale);
  }
  
  public static enum ActionType
  {
    static
    {
      ActionType[] arrayOfActionType = new ActionType[3];
      arrayOfActionType[0] = DOWN;
      arrayOfActionType[1] = DRAG;
      arrayOfActionType[2] = UP;
      $VALUES = arrayOfActionType;
    }
    
    private ActionType() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.FlowEvent
 * JD-Core Version:    0.7.0.1
 */