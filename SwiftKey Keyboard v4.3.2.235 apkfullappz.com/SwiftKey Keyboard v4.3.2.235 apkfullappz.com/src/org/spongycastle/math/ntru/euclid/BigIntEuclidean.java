package org.spongycastle.math.ntru.euclid;

import java.math.BigInteger;

public class BigIntEuclidean
{
  public BigInteger gcd;
  public BigInteger x;
  public BigInteger y;
  
  public static BigIntEuclidean calculate(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    BigInteger localBigInteger1 = BigInteger.ZERO;
    Object localObject1 = BigInteger.ONE;
    BigInteger localBigInteger2 = BigInteger.ONE;
    BigInteger localBigInteger5;
    for (Object localObject2 = BigInteger.ZERO; !paramBigInteger2.equals(BigInteger.ZERO); localObject2 = localBigInteger5)
    {
      BigInteger[] arrayOfBigInteger = paramBigInteger1.divideAndRemainder(paramBigInteger2);
      BigInteger localBigInteger3 = arrayOfBigInteger[0];
      paramBigInteger1 = paramBigInteger2;
      paramBigInteger2 = arrayOfBigInteger[1];
      BigInteger localBigInteger4 = localBigInteger1;
      localBigInteger1 = ((BigInteger)localObject1).subtract(localBigInteger3.multiply(localBigInteger1));
      localObject1 = localBigInteger4;
      localBigInteger5 = localBigInteger2;
      localBigInteger2 = ((BigInteger)localObject2).subtract(localBigInteger3.multiply(localBigInteger2));
    }
    BigIntEuclidean localBigIntEuclidean = new BigIntEuclidean();
    localBigIntEuclidean.x = ((BigInteger)localObject1);
    localBigIntEuclidean.y = ((BigInteger)localObject2);
    localBigIntEuclidean.gcd = paramBigInteger1;
    return localBigIntEuclidean;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ntru.euclid.BigIntEuclidean
 * JD-Core Version:    0.7.0.1
 */