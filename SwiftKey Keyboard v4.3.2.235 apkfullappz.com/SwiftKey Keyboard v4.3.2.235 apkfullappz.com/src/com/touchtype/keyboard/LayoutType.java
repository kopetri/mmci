package com.touchtype.keyboard;

public enum LayoutType
{
  private final int value;
  
  static
  {
    SMILEYS = new LayoutType("SMILEYS", 3, 3);
    LayoutType[] arrayOfLayoutType = new LayoutType[4];
    arrayOfLayoutType[0] = STANDARD;
    arrayOfLayoutType[1] = SYMBOLS;
    arrayOfLayoutType[2] = SYMBOLS_ALT;
    arrayOfLayoutType[3] = SMILEYS;
    $VALUES = arrayOfLayoutType;
  }
  
  private LayoutType(int paramInt)
  {
    this.value = paramInt;
  }
  
  public static LayoutType forValue(int paramInt)
  {
    for (LayoutType localLayoutType : ) {
      if (localLayoutType.getValue() == paramInt) {
        return localLayoutType;
      }
    }
    throw new IllegalArgumentException("There is no LayoutType for value " + paramInt);
  }
  
  public int getValue()
  {
    return this.value;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.LayoutType
 * JD-Core Version:    0.7.0.1
 */