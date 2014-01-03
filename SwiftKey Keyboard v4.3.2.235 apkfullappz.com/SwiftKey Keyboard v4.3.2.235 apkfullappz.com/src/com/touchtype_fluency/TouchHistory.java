package com.touchtype_fluency;

public final class TouchHistory
{
  private long peer;
  
  static {}
  
  public TouchHistory()
  {
    createPeer();
  }
  
  private TouchHistory(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public TouchHistory(String paramString)
  {
    createPeer();
    int j;
    for (int i = 0; i < paramString.length(); i = j)
    {
      j = paramString.offsetByCodePoints(i, 1);
      addMultiCharacter(paramString.substring(i, j));
    }
  }
  
  private static native boolean areEqual(TouchHistory paramTouchHistory1, TouchHistory paramTouchHistory2);
  
  private native void createPeer();
  
  private native void destroyPeer();
  
  static native void initIDs();
  
  public void addCharacter(Character paramCharacter)
  {
    addCharacter(paramCharacter, 0L);
  }
  
  public native void addCharacter(Character paramCharacter, long paramLong);
  
  public native void addKeyPressOptions(KeyPress[] paramArrayOfKeyPress);
  
  public void addMultiCharacter(String paramString)
  {
    addMultiCharacter(paramString, 0L);
  }
  
  public native void addMultiCharacter(String paramString, long paramLong);
  
  public void addPress(Point paramPoint, ShiftState paramShiftState)
  {
    addPress(paramPoint, paramShiftState, 0L);
  }
  
  public native void addPress(Point paramPoint, ShiftState paramShiftState, long paramLong);
  
  public native void appendHistory(TouchHistory paramTouchHistory);
  
  public void appendSample(Point paramPoint)
  {
    appendSample(paramPoint, 0L);
  }
  
  public native void appendSample(Point paramPoint, long paramLong);
  
  public native TouchHistory dropFirst(int paramInt);
  
  public native TouchHistory dropFirstTerms(Prediction paramPrediction, int paramInt);
  
  public native TouchHistory dropLast(int paramInt);
  
  public boolean equals(Object paramObject)
  {
    return ((paramObject instanceof TouchHistory)) && (areEqual(this, (TouchHistory)paramObject));
  }
  
  protected void finalize()
  {
    destroyPeer();
  }
  
  public native int hashCode();
  
  public native int size();
  
  public native TouchHistory takeFirst(int paramInt);
  
  public native TouchHistory takeLast(int paramInt);
  
  public native String toString();
  
  public static enum ShiftState
  {
    static
    {
      SHIFTED = new ShiftState("SHIFTED", 1);
      ShiftState[] arrayOfShiftState = new ShiftState[2];
      arrayOfShiftState[0] = UNSHIFTED;
      arrayOfShiftState[1] = SHIFTED;
      $VALUES = arrayOfShiftState;
    }
    
    private ShiftState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.TouchHistory
 * JD-Core Version:    0.7.0.1
 */