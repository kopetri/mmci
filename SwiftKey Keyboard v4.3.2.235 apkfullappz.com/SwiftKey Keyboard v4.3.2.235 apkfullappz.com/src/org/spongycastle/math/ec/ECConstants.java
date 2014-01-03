package org.spongycastle.math.ec;

import java.math.BigInteger;

public abstract interface ECConstants
{
  public static final BigInteger FOUR = BigInteger.valueOf(4L);
  public static final BigInteger ONE;
  public static final BigInteger THREE;
  public static final BigInteger TWO;
  public static final BigInteger ZERO = BigInteger.valueOf(0L);
  
  static
  {
    ONE = BigInteger.valueOf(1L);
    TWO = BigInteger.valueOf(2L);
    THREE = BigInteger.valueOf(3L);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.ECConstants
 * JD-Core Version:    0.7.0.1
 */