package com.touchtype.keyboard.key;

import com.touchtype.keyboard.theme.KeyStyle.StyleId;

public enum KeySpecialStyle
{
  private KeyStyle.StyleId mId;
  private String mStyleName;
  private int mValue;
  
  static
  {
    Function = new KeySpecialStyle("Function", 2, "style_Function", 3, KeyStyle.StyleId.FUNCTION);
    KeySpecialStyle[] arrayOfKeySpecialStyle = new KeySpecialStyle[3];
    arrayOfKeySpecialStyle[0] = Base;
    arrayOfKeySpecialStyle[1] = Special;
    arrayOfKeySpecialStyle[2] = Function;
    $VALUES = arrayOfKeySpecialStyle;
  }
  
  private KeySpecialStyle(String paramString, int paramInt, KeyStyle.StyleId paramStyleId)
  {
    this.mStyleName = paramString;
    this.mValue = paramInt;
    this.mId = paramStyleId;
  }
  
  public static KeyStyle.StyleId getIDFromValue(int paramInt)
  {
    for (KeySpecialStyle localKeySpecialStyle : ) {
      if (localKeySpecialStyle.getValue() == paramInt) {
        return localKeySpecialStyle.getStyleId();
      }
    }
    return KeyStyle.StyleId.BASE;
  }
  
  public KeyStyle.StyleId getStyleId()
  {
    return this.mId;
  }
  
  public int getValue()
  {
    return this.mValue;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeySpecialStyle
 * JD-Core Version:    0.7.0.1
 */