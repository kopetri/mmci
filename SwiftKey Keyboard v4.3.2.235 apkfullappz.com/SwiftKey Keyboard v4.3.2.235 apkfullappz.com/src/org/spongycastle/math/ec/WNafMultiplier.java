package org.spongycastle.math.ec;

import java.math.BigInteger;

class WNafMultiplier
  implements ECMultiplier
{
  public ECPoint multiply(ECPoint paramECPoint, BigInteger paramBigInteger, PreCompInfo paramPreCompInfo)
  {
    WNafPreCompInfo localWNafPreCompInfo;
    int i;
    byte b;
    int j;
    label36:
    int k;
    ECPoint[] arrayOfECPoint1;
    ECPoint localECPoint1;
    if ((paramPreCompInfo != null) && ((paramPreCompInfo instanceof WNafPreCompInfo)))
    {
      localWNafPreCompInfo = (WNafPreCompInfo)paramPreCompInfo;
      i = paramBigInteger.bitLength();
      if (i >= 13) {
        break label154;
      }
      b = 2;
      j = 1;
      k = 1;
      arrayOfECPoint1 = localWNafPreCompInfo.getPreComp();
      localECPoint1 = localWNafPreCompInfo.getTwiceP();
      if (arrayOfECPoint1 != null) {
        break label253;
      }
      arrayOfECPoint1 = new ECPoint[] { paramECPoint };
    }
    for (;;)
    {
      if (localECPoint1 == null) {
        localECPoint1 = paramECPoint.twice();
      }
      if (k >= j) {
        break label261;
      }
      ECPoint[] arrayOfECPoint2 = arrayOfECPoint1;
      arrayOfECPoint1 = new ECPoint[j];
      System.arraycopy(arrayOfECPoint2, 0, arrayOfECPoint1, 0, k);
      for (int i1 = k; i1 < j; i1++) {
        arrayOfECPoint1[i1] = localECPoint1.add(arrayOfECPoint1[(i1 - 1)]);
      }
      localWNafPreCompInfo = new WNafPreCompInfo();
      break;
      label154:
      if (i < 41)
      {
        b = 3;
        j = 2;
        break label36;
      }
      if (i < 121)
      {
        b = 4;
        j = 4;
        break label36;
      }
      if (i < 337)
      {
        b = 5;
        j = 8;
        break label36;
      }
      if (i < 897)
      {
        b = 6;
        j = 16;
        break label36;
      }
      if (i < 2305)
      {
        b = 7;
        j = 32;
        break label36;
      }
      b = 8;
      j = 127;
      break label36;
      label253:
      k = arrayOfECPoint1.length;
    }
    label261:
    byte[] arrayOfByte = windowNaf(b, paramBigInteger);
    int m = arrayOfByte.length;
    ECPoint localECPoint2 = paramECPoint.getCurve().getInfinity();
    int n = m - 1;
    if (n >= 0)
    {
      localECPoint2 = localECPoint2.twice();
      if (arrayOfByte[n] != 0) {
        if (arrayOfByte[n] <= 0) {
          break label343;
        }
      }
      label343:
      for (localECPoint2 = localECPoint2.add(arrayOfECPoint1[((-1 + arrayOfByte[n]) / 2)]);; localECPoint2 = localECPoint2.subtract(arrayOfECPoint1[((-1 + -arrayOfByte[n]) / 2)]))
      {
        n--;
        break;
      }
    }
    localWNafPreCompInfo.setPreComp(arrayOfECPoint1);
    localWNafPreCompInfo.setTwiceP(localECPoint1);
    paramECPoint.setPreCompInfo(localWNafPreCompInfo);
    return localECPoint2;
  }
  
  public byte[] windowNaf(byte paramByte, BigInteger paramBigInteger)
  {
    byte[] arrayOfByte1 = new byte[1 + paramBigInteger.bitLength()];
    int i = (short)(1 << paramByte);
    BigInteger localBigInteger1 = BigInteger.valueOf(i);
    int j = 0;
    int k = 0;
    if (paramBigInteger.signum() > 0)
    {
      BigInteger localBigInteger2;
      if (paramBigInteger.testBit(0))
      {
        localBigInteger2 = paramBigInteger.mod(localBigInteger1);
        if (localBigInteger2.testBit(paramByte - 1))
        {
          arrayOfByte1[j] = ((byte)(localBigInteger2.intValue() - i));
          label76:
          paramBigInteger = paramBigInteger.subtract(BigInteger.valueOf(arrayOfByte1[j]));
          k = j;
        }
      }
      for (;;)
      {
        paramBigInteger = paramBigInteger.shiftRight(1);
        j++;
        break;
        arrayOfByte1[j] = ((byte)localBigInteger2.intValue());
        break label76;
        arrayOfByte1[j] = 0;
      }
    }
    int m = k + 1;
    byte[] arrayOfByte2 = new byte[m];
    System.arraycopy(arrayOfByte1, 0, arrayOfByte2, 0, m);
    return arrayOfByte2;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.math.ec.WNafMultiplier
 * JD-Core Version:    0.7.0.1
 */