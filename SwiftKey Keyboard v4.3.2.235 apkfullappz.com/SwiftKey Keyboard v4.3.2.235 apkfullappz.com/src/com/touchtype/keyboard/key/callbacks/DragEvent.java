package com.touchtype.keyboard.key.callbacks;

public final class DragEvent
{
  final int mAngle;
  final float mDrag;
  
  public DragEvent(int paramInt, float paramFloat)
  {
    this.mAngle = paramInt;
    this.mDrag = paramFloat;
  }
  
  public static DragEvent greater(DragEvent paramDragEvent1, DragEvent paramDragEvent2)
  {
    if (paramDragEvent1 == null) {}
    do
    {
      return paramDragEvent2;
      if (paramDragEvent2 == null) {
        return paramDragEvent1;
      }
    } while (paramDragEvent1.mDrag <= paramDragEvent2.mDrag);
    return paramDragEvent1;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DragEvent)) {}
    DragEvent localDragEvent;
    do
    {
      return false;
      localDragEvent = (DragEvent)paramObject;
    } while ((this.mDrag != localDragEvent.mDrag) || (this.mAngle != localDragEvent.mAngle));
    return true;
  }
  
  public String toString()
  {
    return "DragEvent - Angle: " + this.mAngle + ", Drag distance: " + this.mDrag;
  }
  
  public static enum Direction
  {
    final int mAngle;
    
    static
    {
      DOWN_RIGHT = new Direction("DOWN_RIGHT", 1, 45);
      DOWN = new Direction("DOWN", 2, 90);
      DOWN_LEFT = new Direction("DOWN_LEFT", 3, 135);
      LEFT = new Direction("LEFT", 4, 180);
      UP_LEFT = new Direction("UP_LEFT", 5, 225);
      UP = new Direction("UP", 6, 270);
      UP_RIGHT = new Direction("UP_RIGHT", 7, 315);
      Direction[] arrayOfDirection = new Direction[8];
      arrayOfDirection[0] = RIGHT;
      arrayOfDirection[1] = DOWN_RIGHT;
      arrayOfDirection[2] = DOWN;
      arrayOfDirection[3] = DOWN_LEFT;
      arrayOfDirection[4] = LEFT;
      arrayOfDirection[5] = UP_LEFT;
      arrayOfDirection[6] = UP;
      arrayOfDirection[7] = UP_RIGHT;
      $VALUES = arrayOfDirection;
    }
    
    private Direction(int paramInt)
    {
      this.mAngle = paramInt;
    }
  }
  
  public static enum Range
  {
    final DragEvent.Direction mNegativeHalf;
    final DragEvent.Direction mPositiveHalf;
    
    static
    {
      LEFT_RIGHT = new Range("LEFT_RIGHT", 1, DragEvent.Direction.LEFT, DragEvent.Direction.RIGHT);
      Range[] arrayOfRange = new Range[2];
      arrayOfRange[0] = UP_DOWN;
      arrayOfRange[1] = LEFT_RIGHT;
      $VALUES = arrayOfRange;
    }
    
    private Range(DragEvent.Direction paramDirection1, DragEvent.Direction paramDirection2)
    {
      this.mNegativeHalf = paramDirection1;
      this.mPositiveHalf = paramDirection2;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.callbacks.DragEvent
 * JD-Core Version:    0.7.0.1
 */