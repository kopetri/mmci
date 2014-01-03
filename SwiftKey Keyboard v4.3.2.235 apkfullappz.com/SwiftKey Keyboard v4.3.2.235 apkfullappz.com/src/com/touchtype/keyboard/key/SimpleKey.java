package com.touchtype.keyboard.key;

import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.delegates.KeyDrawDelegate;
import com.touchtype.keyboard.key.delegates.KeyTouchDelegate;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;

public class SimpleKey
  implements Key
{
  protected final KeyArea mArea;
  protected final KeyDrawDelegate mDrawDelegate;
  protected final KeyState mState;
  protected final KeyTouchDelegate mTouchDelegate;
  
  public SimpleKey(KeyArea paramKeyArea, KeyState paramKeyState, KeyDrawDelegate paramKeyDrawDelegate, KeyTouchDelegate paramKeyTouchDelegate)
  {
    this.mArea = paramKeyArea;
    this.mState = paramKeyState;
    this.mDrawDelegate = paramKeyDrawDelegate;
    this.mTouchDelegate = paramKeyTouchDelegate;
  }
  
  public void cancel()
  {
    this.mTouchDelegate.cancel();
  }
  
  public boolean contains(float paramFloat1, float paramFloat2)
  {
    return this.mArea.contains(paramFloat1, paramFloat2);
  }
  
  public void down(TouchEvent.Touch paramTouch)
  {
    this.mTouchDelegate.down(paramTouch);
  }
  
  public KeyArea getArea()
  {
    return this.mArea;
  }
  
  public KeyContent getContent()
  {
    return this.mDrawDelegate.getContent();
  }
  
  public Drawable getKeyDrawable(Theme paramTheme)
  {
    return this.mDrawDelegate.getKeyDrawable(paramTheme);
  }
  
  public KeyState getState()
  {
    return this.mState;
  }
  
  public boolean handleGesture(TouchEvent.Touch paramTouch)
  {
    return this.mTouchDelegate.handleGesture(paramTouch);
  }
  
  public void slideIn(TouchEvent.Touch paramTouch)
  {
    this.mTouchDelegate.slideIn(paramTouch);
  }
  
  public void slideOut(TouchEvent.Touch paramTouch)
  {
    this.mTouchDelegate.slideOut(paramTouch);
  }
  
  public String toString()
  {
    return "{ Content: " + getContent() + ", Area: " + this.mArea + " }";
  }
  
  public void up(TouchEvent.Touch paramTouch)
  {
    this.mTouchDelegate.up(paramTouch);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.SimpleKey
 * JD-Core Version:    0.7.0.1
 */