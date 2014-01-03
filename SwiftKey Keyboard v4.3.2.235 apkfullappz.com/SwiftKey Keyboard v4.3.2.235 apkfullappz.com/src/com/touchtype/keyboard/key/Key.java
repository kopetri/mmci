package com.touchtype.keyboard.key;

import com.touchtype.keyboard.key.delegates.KeyDrawDelegate;
import com.touchtype.keyboard.key.delegates.KeyTouchDelegate;

public abstract interface Key
  extends KeyDrawDelegate, KeyTouchDelegate
{
  public abstract boolean contains(float paramFloat1, float paramFloat2);
  
  public abstract KeyArea getArea();
  
  public abstract KeyState getState();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.Key
 * JD-Core Version:    0.7.0.1
 */