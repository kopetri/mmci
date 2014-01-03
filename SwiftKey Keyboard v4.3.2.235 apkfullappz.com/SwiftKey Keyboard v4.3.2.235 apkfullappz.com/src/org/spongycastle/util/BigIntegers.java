package org.spongycastle.util;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class BigIntegers
{
  private static final int MAX_ITERATIONS = 1000;
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  
  public static byte[] asUnsignedByteArray(BigInteger paramBigInteger)
  {
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    if (arrayOfByte1[0] == 0)
    {
      byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 1, arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }
  
  public static BigInteger createRandomInRange(BigInteger paramBigInteger1, BigInteger paramBigInteger2, SecureRandom paramSecureRandom)
  {
    int i = paramBigInteger1.compareTo(paramBigInteger2);
    if (i >= 0)
    {
      if (i > 0) {
        throw new IllegalArgumentException("'min' may not be greater than 'max'");
      }
    }
    else
    {
      if (paramBigInteger1.bitLength() <= paramBigInteger2.bitLength() / 2) {
        break label56;
      }
      paramBigInteger1 = createRandomInRange(ZERO, paramBigInteger2.subtract(paramBigInteger1), paramSecureRandom).add(paramBigInteger1);
    }
    return paramBigInteger1;
    label56:
    for (int j = 0; j < 1000; j++)
    {
      BigInteger localBigInteger = new BigInteger(paramBigInteger2.bitLength(), paramSecureRandom);
      if ((localBigInteger.compareTo(paramBigInteger1) >= 0) && (localBigInteger.compareTo(paramBigInteger2) <= 0)) {
        return localBigInteger;
      }
    }
    return new BigInteger(-1 + paramBigInteger2.subtract(paramBigInteger1).bitLength(), paramSecureRandom).add(paramBigInteger1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.BigIntegers
 * JD-Core Version:    0.7.0.1
 */