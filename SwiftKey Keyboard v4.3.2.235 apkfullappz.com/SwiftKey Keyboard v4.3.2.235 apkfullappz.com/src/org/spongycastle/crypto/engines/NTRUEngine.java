package org.spongycastle.crypto.engines;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DataLengthException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.params.NTRUEncryptionParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUEncryptionPublicKeyParameters;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.math.ntru.polynomial.DenseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;
import org.spongycastle.math.ntru.polynomial.ProductFormPolynomial;
import org.spongycastle.math.ntru.polynomial.SparseTernaryPolynomial;
import org.spongycastle.math.ntru.polynomial.TernaryPolynomial;
import org.spongycastle.util.Arrays;

public class NTRUEngine
  implements AsymmetricBlockCipher
{
  private boolean forEncryption;
  private NTRUEncryptionParameters params;
  private NTRUEncryptionPrivateKeyParameters privKey;
  private NTRUEncryptionPublicKeyParameters pubKey;
  private SecureRandom random;
  
  private IntegerPolynomial MGF(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    Digest localDigest = this.params.hashAlg;
    int i = localDigest.getDigestSize();
    byte[] arrayOfByte1 = new byte[paramInt2 * i];
    if (paramBoolean) {}
    int j;
    for (byte[] arrayOfByte2 = calcHash(localDigest, paramArrayOfByte);; arrayOfByte2 = paramArrayOfByte) {
      for (j = 0; j < paramInt2; j++)
      {
        localDigest.update(arrayOfByte2, 0, arrayOfByte2.length);
        putInt(localDigest, j);
        System.arraycopy(calcHash(localDigest), 0, arrayOfByte1, j * i, i);
      }
    }
    IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(paramInt1);
    for (;;)
    {
      int k = 0;
      int m = 0;
      if (m != arrayOfByte1.length)
      {
        n = 0xFF & arrayOfByte1[m];
        if (n < 243)
        {
          i1 = 0;
          if (i1 < 4)
          {
            i2 = n % 3;
            localIntegerPolynomial.coeffs[k] = (i2 - 1);
            k++;
            if (k != paramInt1) {}
          }
        }
      }
      while (k >= paramInt1)
      {
        do
        {
          int n;
          for (;;)
          {
            int i1;
            int i2;
            return localIntegerPolynomial;
            n = (n - i2) / 3;
            i1++;
          }
          localIntegerPolynomial.coeffs[k] = (n - 1);
          k++;
        } while (k == paramInt1);
        m++;
        break;
      }
      localDigest.update(arrayOfByte2, 0, arrayOfByte2.length);
      putInt(localDigest, j);
      arrayOfByte1 = calcHash(localDigest);
      j++;
    }
  }
  
  private byte[] buildSData(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4)
  {
    byte[] arrayOfByte = new byte[paramInt + paramArrayOfByte1.length + paramArrayOfByte3.length + paramArrayOfByte4.length];
    System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
    System.arraycopy(paramArrayOfByte3, 0, arrayOfByte, paramArrayOfByte1.length + paramArrayOfByte2.length, paramArrayOfByte3.length);
    System.arraycopy(paramArrayOfByte4, 0, arrayOfByte, paramArrayOfByte1.length + paramArrayOfByte2.length + paramArrayOfByte3.length, paramArrayOfByte4.length);
    return arrayOfByte;
  }
  
  private byte[] calcHash(Digest paramDigest)
  {
    byte[] arrayOfByte = new byte[paramDigest.getDigestSize()];
    paramDigest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  private byte[] calcHash(Digest paramDigest, byte[] paramArrayOfByte)
  {
    byte[] arrayOfByte = new byte[paramDigest.getDigestSize()];
    paramDigest.update(paramArrayOfByte, 0, paramArrayOfByte.length);
    paramDigest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  private byte[] copyOf(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    if (paramInt < paramArrayOfByte.length) {}
    for (;;)
    {
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte, 0, paramInt);
      return arrayOfByte;
      paramInt = paramArrayOfByte.length;
    }
  }
  
  private byte[] decrypt(byte[] paramArrayOfByte, NTRUEncryptionPrivateKeyParameters paramNTRUEncryptionPrivateKeyParameters)
    throws InvalidCipherTextException
  {
    Polynomial localPolynomial = paramNTRUEncryptionPrivateKeyParameters.t;
    IntegerPolynomial localIntegerPolynomial1 = paramNTRUEncryptionPrivateKeyParameters.fp;
    IntegerPolynomial localIntegerPolynomial2 = paramNTRUEncryptionPrivateKeyParameters.h;
    int i = this.params.N;
    int j = this.params.q;
    int k = this.params.db;
    int m = this.params.maxMsgLenBytes;
    int n = this.params.dm0;
    int i1 = this.params.pkLen;
    int i2 = this.params.minCallsMask;
    boolean bool = this.params.hashSeed;
    byte[] arrayOfByte1 = this.params.oid;
    if (m > 255) {
      throw new DataLengthException("maxMsgLenBytes values bigger than 255 are not supported");
    }
    int i3 = k / 8;
    IntegerPolynomial localIntegerPolynomial3 = IntegerPolynomial.fromBinary(paramArrayOfByte, i, j);
    IntegerPolynomial localIntegerPolynomial4 = decrypt(localIntegerPolynomial3, localPolynomial, localIntegerPolynomial1);
    if (localIntegerPolynomial4.count(-1) < n) {
      throw new InvalidCipherTextException("Less than dm0 coefficients equal -1");
    }
    if (localIntegerPolynomial4.count(0) < n) {
      throw new InvalidCipherTextException("Less than dm0 coefficients equal 0");
    }
    if (localIntegerPolynomial4.count(1) < n) {
      throw new InvalidCipherTextException("Less than dm0 coefficients equal 1");
    }
    IntegerPolynomial localIntegerPolynomial5 = (IntegerPolynomial)localIntegerPolynomial3.clone();
    localIntegerPolynomial5.sub(localIntegerPolynomial4);
    localIntegerPolynomial5.modPositive(j);
    IntegerPolynomial localIntegerPolynomial6 = (IntegerPolynomial)localIntegerPolynomial5.clone();
    localIntegerPolynomial6.modPositive(4);
    localIntegerPolynomial4.sub(MGF(localIntegerPolynomial6.toBinary(4), i, i2, bool));
    localIntegerPolynomial4.mod3();
    byte[] arrayOfByte2 = localIntegerPolynomial4.toBinary3Sves();
    byte[] arrayOfByte3 = new byte[i3];
    System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 0, i3);
    int i4 = 0xFF & arrayOfByte2[i3];
    if (i4 > m) {
      throw new InvalidCipherTextException("Message too long: " + i4 + ">" + m);
    }
    byte[] arrayOfByte4 = new byte[i4];
    System.arraycopy(arrayOfByte2, i3 + 1, arrayOfByte4, 0, i4);
    byte[] arrayOfByte5 = new byte[arrayOfByte2.length - (i4 + (i3 + 1))];
    System.arraycopy(arrayOfByte2, i4 + (i3 + 1), arrayOfByte5, 0, arrayOfByte5.length);
    if (!Arrays.areEqual(arrayOfByte5, new byte[arrayOfByte5.length])) {
      throw new InvalidCipherTextException("The message is not followed by zeroes");
    }
    IntegerPolynomial localIntegerPolynomial7 = generateBlindingPoly(buildSData(arrayOfByte1, arrayOfByte4, i4, arrayOfByte3, copyOf(localIntegerPolynomial2.toBinary(j), i1 / 8)), arrayOfByte4).mult(localIntegerPolynomial2);
    localIntegerPolynomial7.modPositive(j);
    if (!localIntegerPolynomial7.equals(localIntegerPolynomial5)) {
      throw new InvalidCipherTextException("Invalid message encoding");
    }
    return arrayOfByte4;
  }
  
  private byte[] encrypt(byte[] paramArrayOfByte, NTRUEncryptionPublicKeyParameters paramNTRUEncryptionPublicKeyParameters)
  {
    IntegerPolynomial localIntegerPolynomial1 = paramNTRUEncryptionPublicKeyParameters.h;
    int i = this.params.N;
    int j = this.params.q;
    int k = this.params.maxMsgLenBytes;
    int m = this.params.db;
    int n = this.params.bufferLenBits;
    int i1 = this.params.dm0;
    int i2 = this.params.pkLen;
    int i3 = this.params.minCallsMask;
    boolean bool = this.params.hashSeed;
    byte[] arrayOfByte1 = this.params.oid;
    int i4 = paramArrayOfByte.length;
    if (k > 255) {
      throw new IllegalArgumentException("llen values bigger than 1 are not supported");
    }
    if (i4 > k) {
      throw new DataLengthException("Message too long: " + i4 + ">" + k);
    }
    IntegerPolynomial localIntegerPolynomial2;
    IntegerPolynomial localIntegerPolynomial3;
    do
    {
      byte[] arrayOfByte2 = new byte[m / 8];
      this.random.nextBytes(arrayOfByte2);
      byte[] arrayOfByte3 = new byte[k + 1 - i4];
      byte[] arrayOfByte4 = new byte[n / 8];
      System.arraycopy(arrayOfByte2, 0, arrayOfByte4, 0, arrayOfByte2.length);
      arrayOfByte4[arrayOfByte2.length] = ((byte)i4);
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte4, 1 + arrayOfByte2.length, paramArrayOfByte.length);
      System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 1 + arrayOfByte2.length + paramArrayOfByte.length, arrayOfByte3.length);
      localIntegerPolynomial2 = IntegerPolynomial.fromBinary3Sves(arrayOfByte4, i);
      localIntegerPolynomial3 = generateBlindingPoly(buildSData(arrayOfByte1, paramArrayOfByte, i4, arrayOfByte2, copyOf(localIntegerPolynomial1.toBinary(j), i2 / 8)), arrayOfByte4).mult(localIntegerPolynomial1, j);
      IntegerPolynomial localIntegerPolynomial4 = (IntegerPolynomial)localIntegerPolynomial3.clone();
      localIntegerPolynomial4.modPositive(4);
      localIntegerPolynomial2.add(MGF(localIntegerPolynomial4.toBinary(4), i, i3, bool));
      localIntegerPolynomial2.mod3();
    } while ((localIntegerPolynomial2.count(-1) < i1) || (localIntegerPolynomial2.count(0) < i1) || (localIntegerPolynomial2.count(1) < i1));
    localIntegerPolynomial3.add(localIntegerPolynomial2, j);
    localIntegerPolynomial3.ensurePositive(j);
    return localIntegerPolynomial3.toBinary(j);
  }
  
  private int[] generateBlindingCoeffs(IndexGenerator paramIndexGenerator, int paramInt)
  {
    int[] arrayOfInt = new int[this.params.N];
    for (int i = -1; i <= 1; i += 2)
    {
      int j = 0;
      while (j < paramInt)
      {
        int k = paramIndexGenerator.nextIndex();
        if (arrayOfInt[k] == 0)
        {
          arrayOfInt[k] = i;
          j++;
        }
      }
    }
    return arrayOfInt;
  }
  
  private Polynomial generateBlindingPoly(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    IndexGenerator localIndexGenerator = new IndexGenerator(paramArrayOfByte1, this.params);
    if (this.params.polyType == 1) {
      return new ProductFormPolynomial(new SparseTernaryPolynomial(generateBlindingCoeffs(localIndexGenerator, this.params.dr1)), new SparseTernaryPolynomial(generateBlindingCoeffs(localIndexGenerator, this.params.dr2)), new SparseTernaryPolynomial(generateBlindingCoeffs(localIndexGenerator, this.params.dr3)));
    }
    int i = this.params.dr;
    boolean bool = this.params.sparse;
    int[] arrayOfInt = generateBlindingCoeffs(localIndexGenerator, i);
    if (bool) {
      return new SparseTernaryPolynomial(arrayOfInt);
    }
    return new DenseTernaryPolynomial(arrayOfInt);
  }
  
  private int log2(int paramInt)
  {
    if (paramInt == 2048) {
      return 11;
    }
    throw new IllegalStateException("log2 not fully implemented");
  }
  
  private void putInt(Digest paramDigest, int paramInt)
  {
    paramDigest.update((byte)(paramInt >> 24));
    paramDigest.update((byte)(paramInt >> 16));
    paramDigest.update((byte)(paramInt >> 8));
    paramDigest.update((byte)paramInt);
  }
  
  protected IntegerPolynomial decrypt(IntegerPolynomial paramIntegerPolynomial1, Polynomial paramPolynomial, IntegerPolynomial paramIntegerPolynomial2)
  {
    IntegerPolynomial localIntegerPolynomial1;
    if (this.params.fastFp)
    {
      localIntegerPolynomial1 = paramPolynomial.mult(paramIntegerPolynomial1, this.params.q);
      localIntegerPolynomial1.mult(3);
      localIntegerPolynomial1.add(paramIntegerPolynomial1);
      localIntegerPolynomial1.center0(this.params.q);
      localIntegerPolynomial1.mod3();
      if (!this.params.fastFp) {
        break label97;
      }
    }
    label97:
    for (IntegerPolynomial localIntegerPolynomial2 = localIntegerPolynomial1;; localIntegerPolynomial2 = new DenseTernaryPolynomial(localIntegerPolynomial1).mult(paramIntegerPolynomial2, 3))
    {
      localIntegerPolynomial2.center0(3);
      return localIntegerPolynomial2;
      localIntegerPolynomial1 = paramPolynomial.mult(paramIntegerPolynomial1, this.params.q);
      break;
    }
  }
  
  protected IntegerPolynomial encrypt(IntegerPolynomial paramIntegerPolynomial1, TernaryPolynomial paramTernaryPolynomial, IntegerPolynomial paramIntegerPolynomial2)
  {
    IntegerPolynomial localIntegerPolynomial = paramTernaryPolynomial.mult(paramIntegerPolynomial2, this.params.q);
    localIntegerPolynomial.add(paramIntegerPolynomial1, this.params.q);
    localIntegerPolynomial.ensurePositive(this.params.q);
    return localIntegerPolynomial;
  }
  
  public int getInputBlockSize()
  {
    return this.params.maxMsgLenBytes;
  }
  
  public int getOutputBlockSize()
  {
    return (7 + this.params.N * log2(this.params.q)) / 8;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forEncryption = paramBoolean;
    if (paramBoolean)
    {
      ParametersWithRandom localParametersWithRandom;
      if ((paramCipherParameters instanceof ParametersWithRandom))
      {
        localParametersWithRandom = (ParametersWithRandom)paramCipherParameters;
        this.random = localParametersWithRandom.getRandom();
      }
      for (this.pubKey = ((NTRUEncryptionPublicKeyParameters)localParametersWithRandom.getParameters());; this.pubKey = ((NTRUEncryptionPublicKeyParameters)paramCipherParameters))
      {
        this.params = this.pubKey.getParameters();
        return;
        this.random = new SecureRandom();
      }
    }
    this.privKey = ((NTRUEncryptionPrivateKeyParameters)paramCipherParameters);
    this.params = this.privKey.getParameters();
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte = new byte[paramInt2];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte, 0, paramInt2);
    if (this.forEncryption) {
      return encrypt(arrayOfByte, this.pubKey);
    }
    return decrypt(arrayOfByte, this.privKey);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.engines.NTRUEngine
 * JD-Core Version:    0.7.0.1
 */