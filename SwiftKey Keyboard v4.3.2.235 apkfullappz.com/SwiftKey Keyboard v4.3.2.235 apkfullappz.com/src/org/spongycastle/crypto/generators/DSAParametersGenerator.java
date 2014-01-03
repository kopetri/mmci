package org.spongycastle.crypto.generators;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAValidationParameters;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;

public class DSAParametersGenerator
{
  private static final BigInteger ONE = BigInteger.valueOf(1L);
  private static final BigInteger TWO = BigInteger.valueOf(2L);
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  private int L;
  private int N;
  private int certainty;
  private SecureRandom random;
  
  private static BigInteger calculateGenerator_FIPS186_2(BigInteger paramBigInteger1, BigInteger paramBigInteger2, SecureRandom paramSecureRandom)
  {
    BigInteger localBigInteger1 = paramBigInteger1.subtract(ONE).divide(paramBigInteger2);
    BigInteger localBigInteger2 = paramBigInteger1.subtract(TWO);
    BigInteger localBigInteger3;
    do
    {
      localBigInteger3 = BigIntegers.createRandomInRange(TWO, localBigInteger2, paramSecureRandom).modPow(localBigInteger1, paramBigInteger1);
    } while (localBigInteger3.bitLength() <= 1);
    return localBigInteger3;
  }
  
  private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger paramBigInteger1, BigInteger paramBigInteger2, SecureRandom paramSecureRandom)
  {
    return calculateGenerator_FIPS186_2(paramBigInteger1, paramBigInteger2, paramSecureRandom);
  }
  
  private DSAParameters generateParameters_FIPS186_2()
  {
    byte[] arrayOfByte1 = new byte[20];
    byte[] arrayOfByte2 = new byte[20];
    byte[] arrayOfByte3 = new byte[20];
    byte[] arrayOfByte4 = new byte[20];
    SHA1Digest localSHA1Digest = new SHA1Digest();
    int i = (-1 + this.L) / 160;
    byte[] arrayOfByte5 = new byte[this.L / 8];
    for (;;)
    {
      this.random.nextBytes(arrayOfByte1);
      hash(localSHA1Digest, arrayOfByte1, arrayOfByte2);
      System.arraycopy(arrayOfByte1, 0, arrayOfByte3, 0, arrayOfByte1.length);
      inc(arrayOfByte3);
      hash(localSHA1Digest, arrayOfByte3, arrayOfByte3);
      for (int j = 0; j != arrayOfByte4.length; j++) {
        arrayOfByte4[j] = ((byte)(arrayOfByte2[j] ^ arrayOfByte3[j]));
      }
      arrayOfByte4[0] = ((byte)(0xFFFFFF80 | arrayOfByte4[0]));
      arrayOfByte4[19] = ((byte)(0x1 | arrayOfByte4[19]));
      BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte4);
      if (localBigInteger1.isProbablePrime(this.certainty))
      {
        byte[] arrayOfByte6 = Arrays.clone(arrayOfByte1);
        inc(arrayOfByte6);
        for (int k = 0; k < 4096; k++)
        {
          for (int m = 0; m < i; m++)
          {
            inc(arrayOfByte6);
            hash(localSHA1Digest, arrayOfByte6, arrayOfByte2);
            System.arraycopy(arrayOfByte2, 0, arrayOfByte5, arrayOfByte5.length - (m + 1) * arrayOfByte2.length, arrayOfByte2.length);
          }
          inc(arrayOfByte6);
          hash(localSHA1Digest, arrayOfByte6, arrayOfByte2);
          System.arraycopy(arrayOfByte2, arrayOfByte2.length - (arrayOfByte5.length - i * arrayOfByte2.length), arrayOfByte5, 0, arrayOfByte5.length - i * arrayOfByte2.length);
          arrayOfByte5[0] = ((byte)(0xFFFFFF80 | arrayOfByte5[0]));
          BigInteger localBigInteger2 = new BigInteger(1, arrayOfByte5);
          BigInteger localBigInteger3 = localBigInteger2.subtract(localBigInteger2.mod(localBigInteger1.shiftLeft(1)).subtract(ONE));
          if ((localBigInteger3.bitLength() == this.L) && (localBigInteger3.isProbablePrime(this.certainty)))
          {
            BigInteger localBigInteger4 = calculateGenerator_FIPS186_2(localBigInteger3, localBigInteger1, this.random);
            DSAValidationParameters localDSAValidationParameters = new DSAValidationParameters(arrayOfByte1, k);
            DSAParameters localDSAParameters = new DSAParameters(localBigInteger3, localBigInteger1, localBigInteger4, localDSAValidationParameters);
            return localDSAParameters;
          }
        }
      }
    }
  }
  
  private DSAParameters generateParameters_FIPS186_3()
  {
    SHA256Digest localSHA256Digest = new SHA256Digest();
    int i = 8 * localSHA256Digest.getDigestSize();
    byte[] arrayOfByte1 = new byte[this.N / 8];
    int j = (-1 + this.L) / i;
    int k = (-1 + this.L) % i;
    byte[] arrayOfByte2 = new byte[localSHA256Digest.getDigestSize()];
    for (;;)
    {
      this.random.nextBytes(arrayOfByte1);
      hash(localSHA256Digest, arrayOfByte1, arrayOfByte2);
      BigInteger localBigInteger1 = new BigInteger(1, arrayOfByte2);
      BigInteger localBigInteger2 = localBigInteger1.mod(ONE.shiftLeft(-1 + this.N));
      BigInteger localBigInteger3 = ONE.shiftLeft(-1 + this.N).add(localBigInteger2).add(ONE).subtract(localBigInteger2.mod(TWO));
      if (localBigInteger3.isProbablePrime(this.certainty))
      {
        byte[] arrayOfByte3 = Arrays.clone(arrayOfByte1);
        int m = 4 * this.L;
        for (int n = 0; n < m; n++)
        {
          BigInteger localBigInteger4 = ZERO;
          int i1 = 0;
          int i2 = 0;
          while (i1 <= j)
          {
            inc(arrayOfByte3);
            hash(localSHA256Digest, arrayOfByte3, arrayOfByte2);
            BigInteger localBigInteger8 = new BigInteger(1, arrayOfByte2);
            if (i1 == j) {
              localBigInteger8 = localBigInteger8.mod(ONE.shiftLeft(k));
            }
            localBigInteger4 = localBigInteger4.add(localBigInteger8.shiftLeft(i2));
            i1++;
            i2 += i;
          }
          BigInteger localBigInteger5 = localBigInteger4.add(ONE.shiftLeft(-1 + this.L));
          BigInteger localBigInteger6 = localBigInteger5.subtract(localBigInteger5.mod(localBigInteger3.shiftLeft(1)).subtract(ONE));
          if ((localBigInteger6.bitLength() == this.L) && (localBigInteger6.isProbablePrime(this.certainty)))
          {
            BigInteger localBigInteger7 = calculateGenerator_FIPS186_3_Unverifiable(localBigInteger6, localBigInteger3, this.random);
            DSAValidationParameters localDSAValidationParameters = new DSAValidationParameters(arrayOfByte1, n);
            DSAParameters localDSAParameters = new DSAParameters(localBigInteger6, localBigInteger3, localBigInteger7, localDSAValidationParameters);
            return localDSAParameters;
          }
        }
      }
    }
  }
  
  private static int getDefaultN(int paramInt)
  {
    if (paramInt > 1024) {
      return 256;
    }
    return 160;
  }
  
  private static void hash(Digest paramDigest, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    paramDigest.update(paramArrayOfByte1, 0, paramArrayOfByte1.length);
    paramDigest.doFinal(paramArrayOfByte2, 0);
  }
  
  private static void inc(byte[] paramArrayOfByte)
  {
    for (int i = -1 + paramArrayOfByte.length; i >= 0; i--)
    {
      int j = (byte)(0xFF & 1 + paramArrayOfByte[i]);
      paramArrayOfByte[i] = j;
      if (j != 0) {
        break;
      }
    }
  }
  
  private void init(int paramInt1, int paramInt2, int paramInt3, SecureRandom paramSecureRandom)
  {
    this.L = paramInt1;
    this.N = paramInt2;
    this.certainty = paramInt3;
    this.random = paramSecureRandom;
  }
  
  public DSAParameters generateParameters()
  {
    if (this.L > 1024) {
      return generateParameters_FIPS186_3();
    }
    return generateParameters_FIPS186_2();
  }
  
  public void init(int paramInt1, int paramInt2, SecureRandom paramSecureRandom)
  {
    init(paramInt1, getDefaultN(paramInt1), paramInt2, paramSecureRandom);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.DSAParametersGenerator
 * JD-Core Version:    0.7.0.1
 */