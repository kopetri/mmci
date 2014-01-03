package com.touchtype.report.json;

import com.google.gson.annotations.SerializedName;

final class KeyboardUse
{
  @SerializedName("end")
  private long mKeyboardCloseTime;
  @SerializedName("begin")
  private long mKeyboardOpenTime;
  
  private static void checkArgs(long paramLong1, long paramLong2)
  {
    if (paramLong1 < 0L) {
      throw new IllegalArgumentException("Begin timestamp cannot be negative! " + paramLong1);
    }
    if (paramLong2 < 0L) {
      throw new IllegalArgumentException("End timestamp cannot be negative! " + paramLong2);
    }
    if (paramLong1 > paramLong2) {
      throw new IllegalArgumentException("Begin timestamp must preced End! (" + paramLong1 + " > " + paramLong2 + ")");
    }
  }
  
  public static KeyboardUse newInstance(long paramLong1, long paramLong2)
  {
    checkArgs(paramLong1, paramLong2);
    KeyboardUse localKeyboardUse = new KeyboardUse();
    localKeyboardUse.mKeyboardOpenTime = paramLong1;
    localKeyboardUse.mKeyboardCloseTime = paramLong2;
    return localKeyboardUse;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.KeyboardUse
 * JD-Core Version:    0.7.0.1
 */