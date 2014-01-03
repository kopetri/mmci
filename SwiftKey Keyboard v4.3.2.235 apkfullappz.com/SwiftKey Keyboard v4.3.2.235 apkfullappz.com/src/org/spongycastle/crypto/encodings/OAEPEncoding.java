package org.spongycastle.crypto.encodings;

import java.security.SecureRandom;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.InvalidCipherTextException;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class OAEPEncoding
  implements AsymmetricBlockCipher
{
  private byte[] defHash;
  private AsymmetricBlockCipher engine;
  private boolean forEncryption;
  private Digest hash;
  private Digest mgf1Hash;
  private SecureRandom random;
  
  public OAEPEncoding(AsymmetricBlockCipher paramAsymmetricBlockCipher)
  {
    this(paramAsymmetricBlockCipher, new SHA1Digest(), null);
  }
  
  public OAEPEncoding(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest)
  {
    this(paramAsymmetricBlockCipher, paramDigest, null);
  }
  
  public OAEPEncoding(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest1, Digest paramDigest2, byte[] paramArrayOfByte)
  {
    this.engine = paramAsymmetricBlockCipher;
    this.hash = paramDigest1;
    this.mgf1Hash = paramDigest2;
    this.defHash = new byte[paramDigest1.getDigestSize()];
    if (paramArrayOfByte != null) {
      paramDigest1.update(paramArrayOfByte, 0, paramArrayOfByte.length);
    }
    paramDigest1.doFinal(this.defHash, 0);
  }
  
  public OAEPEncoding(AsymmetricBlockCipher paramAsymmetricBlockCipher, Digest paramDigest, byte[] paramArrayOfByte)
  {
    this(paramAsymmetricBlockCipher, paramDigest, paramDigest, paramArrayOfByte);
  }
  
  private void ItoOSP(int paramInt, byte[] paramArrayOfByte)
  {
    paramArrayOfByte[0] = ((byte)(paramInt >>> 24));
    paramArrayOfByte[1] = ((byte)(paramInt >>> 16));
    paramArrayOfByte[2] = ((byte)(paramInt >>> 8));
    paramArrayOfByte[3] = ((byte)(paramInt >>> 0));
  }
  
  private byte[] maskGeneratorFunction1(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3)
  {
    byte[] arrayOfByte1 = new byte[paramInt3];
    byte[] arrayOfByte2 = new byte[this.mgf1Hash.getDigestSize()];
    byte[] arrayOfByte3 = new byte[4];
    int i = 0;
    this.hash.reset();
    do
    {
      ItoOSP(i, arrayOfByte3);
      this.mgf1Hash.update(paramArrayOfByte, paramInt1, paramInt2);
      this.mgf1Hash.update(arrayOfByte3, 0, arrayOfByte3.length);
      this.mgf1Hash.doFinal(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i * arrayOfByte2.length, arrayOfByte2.length);
      i++;
    } while (i < paramInt3 / arrayOfByte2.length);
    if (i * arrayOfByte2.length < paramInt3)
    {
      ItoOSP(i, arrayOfByte3);
      this.mgf1Hash.update(paramArrayOfByte, paramInt1, paramInt2);
      this.mgf1Hash.update(arrayOfByte3, 0, arrayOfByte3.length);
      this.mgf1Hash.doFinal(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte1, i * arrayOfByte2.length, arrayOfByte1.length - i * arrayOfByte2.length);
    }
    return arrayOfByte1;
  }
  
  public byte[] decodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte1 = this.engine.processBlock(paramArrayOfByte, paramInt1, paramInt2);
    byte[] arrayOfByte2;
    if (arrayOfByte1.length < this.engine.getOutputBlockSize())
    {
      arrayOfByte2 = new byte[this.engine.getOutputBlockSize()];
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, arrayOfByte2.length - arrayOfByte1.length, arrayOfByte1.length);
    }
    while (arrayOfByte2.length < 1 + 2 * this.defHash.length)
    {
      throw new InvalidCipherTextException("data too short");
      arrayOfByte2 = arrayOfByte1;
    }
    byte[] arrayOfByte3 = maskGeneratorFunction1(arrayOfByte2, this.defHash.length, arrayOfByte2.length - this.defHash.length, this.defHash.length);
    for (int i = 0; i != this.defHash.length; i++) {
      arrayOfByte2[i] = ((byte)(arrayOfByte2[i] ^ arrayOfByte3[i]));
    }
    byte[] arrayOfByte4 = maskGeneratorFunction1(arrayOfByte2, 0, this.defHash.length, arrayOfByte2.length - this.defHash.length);
    for (int j = this.defHash.length; j != arrayOfByte2.length; j++) {
      arrayOfByte2[j] = ((byte)(arrayOfByte2[j] ^ arrayOfByte4[(j - this.defHash.length)]));
    }
    for (int k = 0; k != this.defHash.length; k++) {
      if (this.defHash[k] != arrayOfByte2[(k + this.defHash.length)]) {
        throw new InvalidCipherTextException("data hash wrong");
      }
    }
    for (int m = 2 * this.defHash.length; (m != arrayOfByte2.length) && (arrayOfByte2[m] == 0); m++) {}
    if ((m >= -1 + arrayOfByte2.length) || (arrayOfByte2[m] != 1)) {
      throw new InvalidCipherTextException("data start wrong " + m);
    }
    int n = m + 1;
    byte[] arrayOfByte5 = new byte[arrayOfByte2.length - n];
    System.arraycopy(arrayOfByte2, n, arrayOfByte5, 0, arrayOfByte5.length);
    return arrayOfByte5;
  }
  
  public byte[] encodeBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    byte[] arrayOfByte1 = new byte[1 + getInputBlockSize() + 2 * this.defHash.length];
    System.arraycopy(paramArrayOfByte, paramInt1, arrayOfByte1, arrayOfByte1.length - paramInt2, paramInt2);
    arrayOfByte1[(-1 + (arrayOfByte1.length - paramInt2))] = 1;
    System.arraycopy(this.defHash, 0, arrayOfByte1, this.defHash.length, this.defHash.length);
    byte[] arrayOfByte2 = new byte[this.defHash.length];
    this.random.nextBytes(arrayOfByte2);
    byte[] arrayOfByte3 = maskGeneratorFunction1(arrayOfByte2, 0, arrayOfByte2.length, arrayOfByte1.length - this.defHash.length);
    for (int i = this.defHash.length; i != arrayOfByte1.length; i++) {
      arrayOfByte1[i] = ((byte)(arrayOfByte1[i] ^ arrayOfByte3[(i - this.defHash.length)]));
    }
    System.arraycopy(arrayOfByte2, 0, arrayOfByte1, 0, this.defHash.length);
    byte[] arrayOfByte4 = maskGeneratorFunction1(arrayOfByte1, this.defHash.length, arrayOfByte1.length - this.defHash.length, this.defHash.length);
    for (int j = 0; j != this.defHash.length; j++) {
      arrayOfByte1[j] = ((byte)(arrayOfByte1[j] ^ arrayOfByte4[j]));
    }
    return this.engine.processBlock(arrayOfByte1, 0, arrayOfByte1.length);
  }
  
  public int getInputBlockSize()
  {
    int i = this.engine.getInputBlockSize();
    if (this.forEncryption) {
      i = i - 1 - 2 * this.defHash.length;
    }
    return i;
  }
  
  public int getOutputBlockSize()
  {
    int i = this.engine.getOutputBlockSize();
    if (this.forEncryption) {
      return i;
    }
    return i - 1 - 2 * this.defHash.length;
  }
  
  public AsymmetricBlockCipher getUnderlyingCipher()
  {
    return this.engine;
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (this.random = ((ParametersWithRandom)paramCipherParameters).getRandom();; this.random = new SecureRandom())
    {
      this.engine.init(paramBoolean, paramCipherParameters);
      this.forEncryption = paramBoolean;
      return;
    }
  }
  
  public byte[] processBlock(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws InvalidCipherTextException
  {
    if (this.forEncryption) {
      return encodeBlock(paramArrayOfByte, paramInt1, paramInt2);
    }
    return decodeBlock(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.encodings.OAEPEncoding
 * JD-Core Version:    0.7.0.1
 */