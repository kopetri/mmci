package com.touchtype_fluency;

import java.util.Arrays;

public class KeyShape
  implements Comparable<KeyShape>
{
  private float featureThresholdMultiplier;
  private float initialScaleMultiplier;
  private Point[] points;
  
  @Deprecated
  public KeyShape()
  {
    this(1);
  }
  
  private KeyShape(int paramInt)
  {
    this.points = new Point[paramInt];
  }
  
  public static KeyShape lineKey(Point paramPoint1, Point paramPoint2)
  {
    KeyShape localKeyShape = new KeyShape(2);
    localKeyShape.points[0] = paramPoint1;
    localKeyShape.points[1] = paramPoint2;
    localKeyShape.initialScaleMultiplier = 1.0F;
    localKeyShape.featureThresholdMultiplier = 1.0F;
    return localKeyShape;
  }
  
  public static KeyShape lineKey(Point paramPoint1, Point paramPoint2, float paramFloat1, float paramFloat2)
  {
    KeyShape localKeyShape = new KeyShape(2);
    localKeyShape.points[0] = paramPoint1;
    localKeyShape.points[1] = paramPoint2;
    localKeyShape.initialScaleMultiplier = paramFloat1;
    localKeyShape.featureThresholdMultiplier = paramFloat2;
    return localKeyShape;
  }
  
  public static KeyShape pointKey(Point paramPoint)
  {
    KeyShape localKeyShape = new KeyShape(1);
    localKeyShape.points[0] = paramPoint;
    localKeyShape.initialScaleMultiplier = 1.0F;
    localKeyShape.featureThresholdMultiplier = 1.0F;
    return localKeyShape;
  }
  
  public static KeyShape pointKey(Point paramPoint, float paramFloat1, float paramFloat2)
  {
    KeyShape localKeyShape = new KeyShape(1);
    localKeyShape.points[0] = paramPoint;
    localKeyShape.initialScaleMultiplier = paramFloat1;
    localKeyShape.featureThresholdMultiplier = paramFloat2;
    return localKeyShape;
  }
  
  public int compareTo(KeyShape paramKeyShape)
  {
    int i = 0;
    int m;
    if (i < Math.min(this.points.length, paramKeyShape.points.length))
    {
      int n = this.points[i].compareTo(paramKeyShape.points[i]);
      if (n != 0) {
        m = n;
      }
    }
    int j;
    int k;
    do
    {
      return m;
      i++;
      break;
      if (this.points.length < paramKeyShape.points.length) {
        return -1;
      }
      j = this.points.length;
      k = paramKeyShape.points.length;
      m = 0;
    } while (j <= k);
    return 1;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject instanceof KeyShape))
    {
      KeyShape localKeyShape = (KeyShape)paramObject;
      return Arrays.equals(this.points, localKeyShape.points);
    }
    return false;
  }
  
  public float getFeatureThresholdMultiplier()
  {
    return this.featureThresholdMultiplier;
  }
  
  public float getInitialScaleMultiplier()
  {
    return this.initialScaleMultiplier;
  }
  
  public Point[] getPoints()
  {
    return this.points;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.points);
  }
  
  public String toString()
  {
    String str = "";
    if (this.points.length != 0)
    {
      for (int i = 0; i < -1 + this.points.length; i++) {
        str = str + this.points[i].toString() + ", ";
      }
      str = str + this.points[(-1 + this.points.length)].toString();
    }
    return str;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.KeyShape
 * JD-Core Version:    0.7.0.1
 */