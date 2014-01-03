package org.spongycastle.crypto.engines;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

class RSACoreEngine
{
  private boolean forEncryption;
  private RSAKeyParameters key;
  
  public BigInteger convertInput(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (paramInt2 > 1 + getInputBlockSize()) {
      throw new DataLengthException("input too large for RSA cipher.");
    }
    if ((paramInt2 == 1 + getInputBlockSize()) && (!this.forEncryption)) {
      throw new DataLengthException("input too large for RSA cipher.");
    }
    byte[] arrayOfByte;
    if ((paramInt1 != 0) || (paramInt2 != paramArrayOfByte.length))
    {
      arrayOfByte = new byte[paramInt2];
      System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    }
    BigInteger localBigInteger;
    for (;;)
    {
      localBigInteger = new BigInteger(1, arrayOfByte);
      if (localBigInteger.compareTo(this.key.getModulus()) < 0) {
        break;
      }
      throw new DataLengthException("input too large for RSA cipher.");
      arrayOfByte = paramArrayOfByte;
    }
    return localBigInteger;
  }
  
  public byte[] convertOutput(BigInteger paramBigInteger)
  {
    byte[] arrayOfByte1 = paramBigInteger.toByteArray();
    if (this.forEncryption)
    {
      if ((arrayOfByte1[0] == 0) && (arrayOfByte1.length > getOutputBlockSize()))
      {
        byte[] arrayOfByte4 = new byte[-1 + arrayOfByte1.length];
        System.arraycopy(arrayOfByte1, 1, arrayOfByte4, 0, arrayOfByte4.length);
        return arrayOfByte4;
      }
      if (arrayOfByte1.length < getOutputBlockSize())
      {
        byte[] arrayOfByte3 = new byte[getOutputBlockSize()];
        System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte3.length - arrayOfByte1.length, arrayOfByte1.length);
        return arrayOfByte3;
      }
    }
    else if (arrayOfByte1[0] == 0)
    {
      byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 1, arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }
  
  public int getInputBlockSize()
  {
    int i = this.key.getModulus().bitLength();
    if (this.forEncryption) {
      return -1 + (i + 7) / 8;
    }
    return (i + 7) / 8;
  }
  
  public int getOutputBlockSize()
  {
    int i = this.key.getModulus().bitLength();
    if (this.forEncryption) {
      return (i + 7) / 8;
    }
    return -1 + (i + 7) / 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (this.key = ((RSAKeyParameters)((ParametersWithRandom)paramCipherParameters).getParameters());; this.key = ((RSAKeyParameters)paramCipherParameters))
    {
      this.forEncryption = paramBoolean;
      return;
    }
  }
  
  public BigInteger processBlock(BigInteger paramBigInteger)
  {
    if ((this.key instanceof RSAPrivateCrtKeyParameters))
    {
      RSAPrivateCrtKeyParameters localRSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters)this.key;
      BigInteger localBigInteger1 = localRSAPrivateCrtKeyParameters.getP();
      BigInteger localBigInteger2 = localRSAPrivateCrtKeyParameters.getQ();
      BigInteger localBigInteger3 = localRSAPrivateCrtKeyParameters.getDP();
      BigInteger localBigInteger4 = localRSAPrivateCrtKeyParameters.getDQ();
      BigInteger localBigInteger5 = localRSAPrivateCrtKeyParameters.getQInv();
      BigInteger localBigInteger6 = paramBigInteger.remainder(localBigInteger1).modPow(localBigInteger3, localBigInteger1);
      BigInteger localBigInteger7 = paramBigInteger.remainder(localBigInteger2).modPow(localBigInteger4, localBigInteger2);
      return localBigInteger6.subtract(localBigInteger7).multiply(localBigInteger5).mod(localBigInteger1).multiply(localBigInteger2).add(localBigInteger7);
    }
    return paramBigInteger.modPow(this.key.getExponent(), this.key.getModulus());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.RSACoreEngine
 * JD-Core Version:    0.7.0.1
 */