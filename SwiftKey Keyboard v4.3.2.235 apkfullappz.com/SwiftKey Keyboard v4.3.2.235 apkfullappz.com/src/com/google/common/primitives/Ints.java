package com.google.common.primitives;

public final class Ints
{
  public static int saturatedCast(long paramLong)
  {
    if (paramLong > 2147483647L) {
      return 2147483647;
    }
    if (paramLong < -2147483648L) {
      return -2147483648;
    }
    return (int)paramLong;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.primitives.Ints
 * JD-Core Version:    0.7.0.1
 */