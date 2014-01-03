package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.view.touch.TouchEvent.Touch;

public abstract interface KeyTouches
{
  public abstract void cancel();
  
  public abstract void down(TouchEvent.Touch paramTouch);
  
  public abstract void slideIn(TouchEvent.Touch paramTouch);
  
  public abstract void slideOut(TouchEvent.Touch paramTouch);
  
  public abstract void up(TouchEvent.Touch paramTouch);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.KeyTouches
 * JD-Core Version:    0.7.0.1
 */