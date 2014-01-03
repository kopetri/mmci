package com.touchtype_fluency;

public class Point
  implements Comparable<Point>
{
  private float x;
  private float y;
  
  public Point()
  {
    this.x = 0.0F;
    this.y = 0.0F;
  }
  
  public Point(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }
  
  public int compareTo(Point paramPoint)
  {
    if (this.y < paramPoint.y) {}
    do
    {
      return -1;
      if (paramPoint.y < this.y) {
        return 1;
      }
    } while (this.x < paramPoint.x);
    if (paramPoint.x < this.x) {
      return 1;
    }
    return 0;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Point;
    boolean bool2 = false;
    if (bool1)
    {
      Point localPoint = (Point)paramObject;
      boolean bool3 = this.x < localPoint.x;
      bool2 = false;
      if (!bool3)
      {
        boolean bool4 = this.y < localPoint.y;
        bool2 = false;
        if (!bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public float getX()
  {
    return this.x;
  }
  
  public float getY()
  {
    return this.y;
  }
  
  public int hashCode()
  {
    return 149 * (149 + (new Float(this.x).hashCode() + 149 * new Float(this.y).hashCode()));
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Float.valueOf(this.x);
    arrayOfObject[1] = Float.valueOf(this.y);
    return String.format("%f,%f", arrayOfObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Point
 * JD-Core Version:    0.7.0.1
 */