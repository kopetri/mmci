package com.touchtype.keyboard;

import android.os.SystemClock;
import android.view.KeyEvent;
import com.touchtype_fluency.Point;

public class ExtendedKeyEvent
  extends KeyEvent
{
  private final boolean mBypassBuffers;
  private final String mKeyText;
  private Point mTouchPoint;
  
  public ExtendedKeyEvent(int paramInt1, float paramFloat1, float paramFloat2, int paramInt2)
  {
    this(paramInt1, "", true, paramFloat1, paramFloat2, paramInt2);
  }
  
  public ExtendedKeyEvent(int paramInt1, String paramString, boolean paramBoolean, float paramFloat1, float paramFloat2, int paramInt2)
  {
    this(paramInt1, paramString, paramBoolean, new Point(paramFloat1, paramFloat2), paramInt2);
  }
  
  public ExtendedKeyEvent(int paramInt1, String paramString, boolean paramBoolean, Point paramPoint, int paramInt2)
  {
    super(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), paramInt2, paramInt1, 0, 0, 0, 0, 2);
    this.mTouchPoint = paramPoint;
    this.mKeyText = paramString;
    this.mBypassBuffers = paramBoolean;
  }
  
  private ExtendedKeyEvent(String paramString, boolean paramBoolean, Point paramPoint)
  {
    super(SystemClock.uptimeMillis(), paramString, 0, 2);
    this.mTouchPoint = paramPoint;
    this.mKeyText = paramString;
    this.mBypassBuffers = paramBoolean;
  }
  
  public static KeyEvent createKeyEventFromText(String paramString, float paramFloat1, float paramFloat2)
  {
    return createKeyEventFromText(paramString, false, paramFloat1, paramFloat2);
  }
  
  public static KeyEvent createKeyEventFromText(String paramString, boolean paramBoolean)
  {
    if (Character.codePointCount(paramString, 0, paramString.length()) == 1) {
      return new ExtendedKeyEvent(Character.codePointAt(paramString, 0), paramString, paramBoolean, null, 1);
    }
    return new ExtendedKeyEvent(paramString, paramBoolean, null);
  }
  
  public static KeyEvent createKeyEventFromText(String paramString, boolean paramBoolean, float paramFloat1, float paramFloat2)
  {
    if (Character.codePointCount(paramString, 0, paramString.length()) == 1) {
      return new ExtendedKeyEvent(paramString.codePointAt(0), paramString, paramBoolean, paramFloat1, paramFloat2, 1);
    }
    return new ExtendedKeyEvent(paramString, paramBoolean, new Point(paramFloat1, paramFloat2));
  }
  
  public String getKeyText()
  {
    return this.mKeyText;
  }
  
  public Point getTouchPoint()
  {
    return this.mTouchPoint;
  }
  
  public void setTouchPoint(Point paramPoint)
  {
    this.mTouchPoint = paramPoint;
  }
  
  public boolean shouldBypassBuffers()
  {
    return this.mBypassBuffers;
  }
  
  public String toString()
  {
    return "KeyEvent: " + getKeyCode() + this.mTouchPoint;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.ExtendedKeyEvent
 * JD-Core Version:    0.7.0.1
 */