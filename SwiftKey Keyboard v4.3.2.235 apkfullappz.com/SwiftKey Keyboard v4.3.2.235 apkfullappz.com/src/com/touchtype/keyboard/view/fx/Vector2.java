package com.touchtype.keyboard.view.fx;

import android.annotation.SuppressLint;
import android.util.FloatMath;

public final class Vector2
{
  public float x;
  public float y;
  
  public Vector2(float paramFloat1, float paramFloat2)
  {
    this.x = paramFloat1;
    this.y = paramFloat2;
  }
  
  public Vector2(Vector2 paramVector2)
  {
    this.x = paramVector2.x;
    this.y = paramVector2.y;
  }
  
  public static float dotProduct(Vector2 paramVector21, Vector2 paramVector22)
  {
    return paramVector21.x * paramVector22.x + paramVector21.y * paramVector22.y;
  }
  
  public static Vector2 normalised(Vector2 paramVector2)
  {
    return scaled(paramVector2, 1.0F / paramVector2.length());
  }
  
  public static Vector2 perpendicularCW(Vector2 paramVector2)
  {
    return new Vector2(paramVector2.y, -paramVector2.x);
  }
  
  public static Vector2 scaled(Vector2 paramVector2, float paramFloat)
  {
    return new Vector2(paramFloat * paramVector2.x, paramFloat * paramVector2.y);
  }
  
  public static Vector2 subtract(Vector2 paramVector21, Vector2 paramVector22)
  {
    return new Vector2(paramVector21.x - paramVector22.x, paramVector21.y - paramVector22.y);
  }
  
  public Vector2 add(Vector2 paramVector2)
  {
    this.x += paramVector2.x;
    this.y += paramVector2.y;
    return this;
  }
  
  @SuppressLint({"FloatMath"})
  public float length()
  {
    return FloatMath.sqrt(this.x * this.x + this.y * this.y);
  }
  
  public Vector2 normalise()
  {
    return scale(1.0F / length());
  }
  
  public Vector2 scale(float paramFloat)
  {
    this.x = (paramFloat * this.x);
    this.y = (paramFloat * this.y);
    return this;
  }
  
  public Vector2 subtract(Vector2 paramVector2)
  {
    this.x -= paramVector2.x;
    this.y -= paramVector2.y;
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.fx.Vector2
 * JD-Core Version:    0.7.0.1
 */