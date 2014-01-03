package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PKCS5S1ParametersGenerator
  extends PBEParametersGenerator
{
  private Digest digest;
  
  public PKCS5S1ParametersGenerator(Digest paramDigest)
  {
    this.digest = paramDigest;
  }
  
  private byte[] generateDerivedKey()
  {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.update(this.password, 0, this.password.length);
    this.digest.update(this.salt, 0, this.salt.length);
    this.digest.doFinal(arrayOfByte, 0);
    for (int i = 1; i < this.iterationCount; i++)
    {
      this.digest.update(arrayOfByte, 0, arrayOfByte.length);
      this.digest.doFinal(arrayOfByte, 0);
    }
    return arrayOfByte;
  }
  
  public CipherParameters generateDerivedMacParameters(int paramInt)
  {
    return generateDerivedParameters(paramInt);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt)
  {
    int i = paramInt / 8;
    if (i > this.digest.getDigestSize()) {
      throw new IllegalArgumentException("Can't generate a derived key " + i + " bytes long.");
    }
    return new KeyParameter(generateDerivedKey(), 0, i);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt1, int paramInt2)
  {
    int i = paramInt1 / 8;
    int j = paramInt2 / 8;
    if (i + j > this.digest.getDigestSize()) {
      throw new IllegalArgumentException("Can't generate a derived key " + (i + j) + " bytes long.");
    }
    byte[] arrayOfByte = generateDerivedKey();
    return new ParametersWithIV(new KeyParameter(arrayOfByte, 0, i), arrayOfByte, i, j);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.PKCS5S1ParametersGenerator
 * JD-Core Version:    0.7.0.1
 */