package com.touchtype.keyboard.inputeventmodel.events;

public final class SelectionChangedInputEvent
  extends ConnectionInputEvent
{
  private final int mComposingRegionEnd;
  private final int mComposingRegionStart;
  private final int mNewEnd;
  private final int mNewStart;
  private final int mOldEnd;
  private final int mOldStart;
  
  public SelectionChangedInputEvent(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6)
  {
    this.mOldStart = paramInt1;
    this.mOldEnd = paramInt2;
    this.mNewStart = paramInt3;
    this.mNewEnd = paramInt4;
    this.mComposingRegionStart = paramInt5;
    this.mComposingRegionEnd = paramInt6;
  }
  
  public int getComposingRegionEnd()
  {
    return this.mComposingRegionEnd;
  }
  
  public int getComposingRegionStart()
  {
    return this.mComposingRegionStart;
  }
  
  public int getEnd()
  {
    return this.mNewEnd;
  }
  
  public int getOldEnd()
  {
    return this.mOldEnd;
  }
  
  public int getStart()
  {
    return this.mNewStart;
  }
  
  public boolean hasMoved()
  {
    return (this.mNewEnd != this.mOldEnd) || (this.mNewStart != this.mOldStart);
  }
  
  public boolean hasMovedAbruptly()
  {
    return (this.mOldStart != -1) && (this.mOldEnd != -1) && ((Math.abs(this.mNewEnd - this.mOldEnd) > 1) || (Math.abs(this.mNewStart - this.mOldStart) > 1));
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = Integer.valueOf(this.mOldStart);
    arrayOfObject[1] = Integer.valueOf(this.mOldEnd);
    arrayOfObject[2] = Integer.valueOf(this.mNewStart);
    arrayOfObject[3] = Integer.valueOf(this.mNewEnd);
    arrayOfObject[4] = Integer.valueOf(this.mComposingRegionStart);
    arrayOfObject[5] = Integer.valueOf(this.mComposingRegionEnd);
    return String.format("SelectionChanged((%d, %d) -> (%d, %d) [%d, %d])", arrayOfObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.events.SelectionChangedInputEvent
 * JD-Core Version:    0.7.0.1
 */