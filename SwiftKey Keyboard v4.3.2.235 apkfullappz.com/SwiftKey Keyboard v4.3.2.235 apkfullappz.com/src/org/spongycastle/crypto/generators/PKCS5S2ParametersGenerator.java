package org.spongycastle.crypto.generators;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.PBEParametersGenerator;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.params.ParametersWithIV;

public class PKCS5S2ParametersGenerator
  extends PBEParametersGenerator
{
  private Mac hMac;
  
  public PKCS5S2ParametersGenerator()
  {
    this(new SHA1Digest());
  }
  
  public PKCS5S2ParametersGenerator(Digest paramDigest)
  {
    this.hMac = new HMac(paramDigest);
  }
  
  private void F(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, byte[] paramArrayOfByte3, byte[] paramArrayOfByte4, int paramInt2)
  {
    byte[] arrayOfByte = new byte[this.hMac.getMacSize()];
    KeyParameter localKeyParameter = new KeyParameter(paramArrayOfByte1);
    this.hMac.init(localKeyParameter);
    if (paramArrayOfByte2 != null) {
      this.hMac.update(paramArrayOfByte2, 0, paramArrayOfByte2.length);
    }
    this.hMac.update(paramArrayOfByte3, 0, paramArrayOfByte3.length);
    this.hMac.doFinal(arrayOfByte, 0);
    System.arraycopy(arrayOfByte, 0, paramArrayOfByte4, paramInt2, arrayOfByte.length);
    if (paramInt1 == 0) {
      throw new IllegalArgumentException("iteration count must be at least 1.");
    }
    for (int i = 1; i < paramInt1; i++)
    {
      this.hMac.init(localKeyParameter);
      this.hMac.update(arrayOfByte, 0, arrayOfByte.length);
      this.hMac.doFinal(arrayOfByte, 0);
      for (int j = 0; j != arrayOfByte.length; j++)
      {
        int k = paramInt2 + j;
        paramArrayOfByte4[k] = ((byte)(paramArrayOfByte4[k] ^ arrayOfByte[j]));
      }
    }
  }
  
  private byte[] generateDerivedKey(int paramInt)
  {
    int i = this.hMac.getMacSize();
    int j = (-1 + (paramInt + i)) / i;
    byte[] arrayOfByte1 = new byte[4];
    byte[] arrayOfByte2 = new byte[j * i];
    for (int k = 1; k <= j; k++)
    {
      intToOctet(arrayOfByte1, k);
      F(this.password, this.salt, this.iterationCount, arrayOfByte1, arrayOfByte2, i * (k - 1));
    }
    return arrayOfByte2;
  }
  
  private void intToOctet(byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[0] = ((byte)(paramInt >>> 24));
    paramArrayOfByte[1] = ((byte)(paramInt >>> 16));
    paramArrayOfByte[2] = ((byte)(paramInt >>> 8));
    paramArrayOfByte[3] = ((byte)paramInt);
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
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.generators.PKCS5S2ParametersGenerator
 * JD-Core Version:    0.7.0.1
 */