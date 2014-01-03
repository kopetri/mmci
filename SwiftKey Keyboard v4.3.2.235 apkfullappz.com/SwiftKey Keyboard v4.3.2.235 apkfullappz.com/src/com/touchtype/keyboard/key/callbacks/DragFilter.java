package com.touchtype.keyboard.key.callbacks;

public abstract interface DragFilter
{
  public abstract DragBehaviour createDragBehaviour(DragBehaviour.DragThreshold paramDragThreshold);
  
  public abstract float getDrag(DragEvent paramDragEvent, float paramFloat);
  
  public abstract Float getDrag(DragEvent paramDragEvent);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.callbacks.DragFilter
 * JD-Core Version:    0.7.0.1
 */