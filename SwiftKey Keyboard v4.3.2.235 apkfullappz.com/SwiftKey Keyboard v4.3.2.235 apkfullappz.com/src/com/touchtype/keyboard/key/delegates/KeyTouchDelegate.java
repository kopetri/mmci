package com.touchtype.keyboard.key.delegates;

import com.touchtype.keyboard.view.touch.TouchEvent.Touch;

public abstract interface KeyTouchDelegate
  extends KeyTouches
{
  public abstract boolean handleGesture(TouchEvent.Touch paramTouch);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.KeyTouchDelegate
 * JD-Core Version:    0.7.0.1
 */