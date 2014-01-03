package com.touchtype.keyboard.view;

public abstract interface SizeLimiter
{
  public static enum ResizeState
  {
    static
    {
      COMPACT = new ResizeState("COMPACT", 2);
      ResizeState[] arrayOfResizeState = new ResizeState[3];
      arrayOfResizeState[0] = FULL;
      arrayOfResizeState[1] = SPLIT;
      arrayOfResizeState[2] = COMPACT;
      $VALUES = arrayOfResizeState;
    }
    
    private ResizeState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.SizeLimiter
 * JD-Core Version:    0.7.0.1
 */