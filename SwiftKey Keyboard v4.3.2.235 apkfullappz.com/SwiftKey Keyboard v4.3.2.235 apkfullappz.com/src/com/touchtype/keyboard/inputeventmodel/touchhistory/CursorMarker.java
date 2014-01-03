package com.touchtype.keyboard.inputeventmodel.touchhistory;

import com.google.common.collect.Range;

public class CursorMarker
{
  private Range<Integer> bounds;
  private int offset = 0;
  private boolean shouldJump;
  
  private CursorMarker(boolean paramBoolean, Range<Integer> paramRange)
  {
    this.shouldJump = paramBoolean;
    this.bounds = paramRange;
  }
  
  public static CursorMarker createJumpingCursorMarker(Range<Integer> paramRange)
  {
    return new CursorMarker(true, paramRange);
  }
  
  public static CursorMarker createStationaryCursorMarker()
  {
    return new CursorMarker(false, null);
  }
  
  public int jumpTo()
  {
    if (this.bounds == null) {
      return -1;
    }
    return ((Integer)this.bounds.upperEndpoint()).intValue() + this.offset;
  }
  
  public void jumped()
  {
    this.shouldJump = false;
  }
  
  public void setOffset(int paramInt)
  {
    this.offset = paramInt;
  }
  
  public boolean shouldJump()
  {
    return this.shouldJump;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.CursorMarker
 * JD-Core Version:    0.7.0.1
 */