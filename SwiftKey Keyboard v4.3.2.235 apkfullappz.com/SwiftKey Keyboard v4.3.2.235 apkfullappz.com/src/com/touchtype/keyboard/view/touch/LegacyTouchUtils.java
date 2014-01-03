package com.touchtype.keyboard.view.touch;

import android.graphics.Matrix;
import android.graphics.PointF;
import com.touchtype_fluency.KeyShape;
import com.touchtype_fluency.Point;

public final class LegacyTouchUtils
{
  public static TransformableKeyShape lineKey(PointF paramPointF1, PointF paramPointF2, float paramFloat1, float paramFloat2)
  {
    return new LineKeyShape(paramPointF1, paramPointF2, paramFloat1, paramFloat2, null);
  }
  
  public static TransformableKeyShape pointKey(PointF paramPointF)
  {
    return new PointKeyShape(paramPointF, null);
  }
  
  private static PointF transform(PointF paramPointF, Matrix paramMatrix)
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramPointF.x;
    arrayOfFloat[1] = paramPointF.y;
    paramMatrix.mapPoints(arrayOfFloat);
    return new PointF(arrayOfFloat[0], arrayOfFloat[1]);
  }
  
  private static Point transformPointFToPoint(PointF paramPointF, Matrix paramMatrix)
  {
    float[] arrayOfFloat = new float[2];
    arrayOfFloat[0] = paramPointF.x;
    arrayOfFloat[1] = paramPointF.y;
    paramMatrix.mapPoints(arrayOfFloat);
    return new Point(arrayOfFloat[0], arrayOfFloat[1]);
  }
  
  public FlowEvent transformApproxAspectRatio(FlowEvent paramFlowEvent)
  {
    return paramFlowEvent.transform(paramFlowEvent, 1.0F, 1.0F);
  }
  
  public Point transformApproxAspectRatio(Point paramPoint)
  {
    return new Point(paramPoint.getX(), paramPoint.getY());
  }
  
  private static final class LineKeyShape
    implements LegacyTouchUtils.TransformableKeyShape
  {
    private final PointF end;
    private final float featureThresholdMultiplier;
    private final float initialScaleMultiplier;
    private final PointF start;
    
    private LineKeyShape(PointF paramPointF1, PointF paramPointF2, float paramFloat1, float paramFloat2)
    {
      this.start = paramPointF1;
      this.end = paramPointF2;
      this.initialScaleMultiplier = paramFloat1;
      this.featureThresholdMultiplier = paramFloat2;
    }
    
    public int hashCode()
    {
      return this.start.hashCode() + 37 * this.end.hashCode();
    }
    
    public KeyShape toKeyShape(Matrix paramMatrix)
    {
      return KeyShape.lineKey(LegacyTouchUtils.transformPointFToPoint(this.start, paramMatrix), LegacyTouchUtils.transformPointFToPoint(this.end, paramMatrix), this.initialScaleMultiplier, this.featureThresholdMultiplier);
    }
    
    public LegacyTouchUtils.TransformableKeyShape transformed(Matrix paramMatrix)
    {
      return new LineKeyShape(LegacyTouchUtils.transform(this.start, paramMatrix), LegacyTouchUtils.transform(this.end, paramMatrix), this.initialScaleMultiplier, this.featureThresholdMultiplier);
    }
  }
  
  private static final class PointKeyShape
    implements LegacyTouchUtils.TransformableKeyShape
  {
    private final PointF point;
    
    private PointKeyShape(PointF paramPointF)
    {
      this.point = paramPointF;
    }
    
    public int hashCode()
    {
      return this.point.hashCode();
    }
    
    public KeyShape toKeyShape(Matrix paramMatrix)
    {
      return KeyShape.pointKey(LegacyTouchUtils.transformPointFToPoint(this.point, paramMatrix));
    }
    
    public LegacyTouchUtils.TransformableKeyShape transformed(Matrix paramMatrix)
    {
      return new PointKeyShape(LegacyTouchUtils.transform(this.point, paramMatrix));
    }
  }
  
  public static abstract interface TransformableKeyShape
  {
    public abstract KeyShape toKeyShape(Matrix paramMatrix);
    
    public abstract TransformableKeyShape transformed(Matrix paramMatrix);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.LegacyTouchUtils
 * JD-Core Version:    0.7.0.1
 */