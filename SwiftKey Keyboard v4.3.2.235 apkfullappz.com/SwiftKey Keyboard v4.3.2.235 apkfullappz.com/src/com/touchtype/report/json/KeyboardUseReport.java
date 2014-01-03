package com.touchtype.report.json;

import com.google.gson.annotations.SerializedName;

public final class KeyboardUseReport
{
  @SerializedName("keyboardUse")
  private final KeyboardUse mKeyboardUse;
  
  private KeyboardUseReport()
  {
    this.mKeyboardUse = null;
  }
  
  public KeyboardUseReport(long paramLong1, long paramLong2)
  {
    this.mKeyboardUse = KeyboardUse.newInstance(paramLong1, paramLong2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.KeyboardUseReport
 * JD-Core Version:    0.7.0.1
 */