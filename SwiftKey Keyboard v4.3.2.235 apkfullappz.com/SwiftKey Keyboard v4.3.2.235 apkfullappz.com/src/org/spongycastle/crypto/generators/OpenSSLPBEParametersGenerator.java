package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class OpenSSLPBEParametersGenerator
  extends PBEParametersGenerator
{
  private Digest digest = new MD5Digest();
  
  private byte[] generateDerivedKey(int paramInt)
  {
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    byte[] arrayOfByte2 = new byte[paramInt];
    int i = 0;
    this.digest.update(this.password, 0, this.password.length);
    this.digest.update(this.salt, 0, this.salt.length);
    this.digest.doFinal(arrayOfByte1, 0);
    if (paramInt > arrayOfByte1.length) {}
    for (int j = arrayOfByte1.length;; j = paramInt)
    {
      System.arraycopy(arrayOfByte1, 0, arrayOfByte2, i, j);
      i += j;
      paramInt -= j;
      if (paramInt == 0) {
        return arrayOfByte2;
      }
      this.digest.reset();
      this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
      break;
    }
    return arrayOfByte2;
  }
  
  public CipherParameters generateDerivedMacParameters(int paramInt)
  {
    return generateDerivedParameters(paramInt);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt)
  {
    int i = paramInt / 8;
    return new KeyParameter(generateDerivedKey(i), 0, i);
  }
  
  public CipherParameters generateDerivedParameters(int paramInt1, int paramInt2)
  {
    int i = paramInt1 / 8;
    int j = paramInt2 / 8;
    byte[] arrayOfByte = generateDerivedKey(i + j);
    return new ParametersWithIV(new KeyParameter(arrayOfByte, 0, i), arrayOfByte, i, j);
  }
  
  public void init(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    super.init(paramArrayOfByte1, paramArrayOfByte2, 1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.OpenSSLPBEParametersGenerator
 * JD-Core Version:    0.7.0.1
 */