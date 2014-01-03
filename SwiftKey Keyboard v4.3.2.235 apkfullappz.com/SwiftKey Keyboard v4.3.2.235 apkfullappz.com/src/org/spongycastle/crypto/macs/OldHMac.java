package org.spongycastle.crypto.macs;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;

public class OldHMac
  implements Mac
{
  private static final int BLOCK_LENGTH = 64;
  private static final byte IPAD = 54;
  private static final byte OPAD = 92;
  private Digest digest;
  private int digestSize;
  private byte[] inputPad = new byte[64];
  private byte[] outputPad = new byte[64];
  
  public OldHMac(Digest paramDigest)
  {
    this.digest = paramDigest;
    this.digestSize = paramDigest.getDigestSize();
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[this.digestSize];
    this.digest.doFinal(arrayOfByte, 0);
    this.digest.update(this.outputPad, 0, this.outputPad.length);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    int i = this.digest.doFinal(paramArrayOfByte, paramInt);
    reset();
    return i;
  }
  
  public String getAlgorithmName()
  {
    return this.digest.getAlgorithmName() + "/HMAC";
  }
  
  public int getMacSize()
  {
    return this.digestSize;
  }
  
  public Digest getUnderlyingDigest()
  {
    return this.digest;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    this.digest.reset();
    byte[] arrayOfByte1 = ((KeyParameter)paramCipherParameters).getKey();
    if (arrayOfByte1.length > 64)
    {
      this.digest.update(arrayOfByte1, 0, arrayOfByte1.length);
      this.digest.doFinal(this.inputPad, 0);
      for (int m = this.digestSize; m < this.inputPad.length; m++) {
        this.inputPad[m] = 0;
      }
    }
    System.arraycopy(arrayOfByte1, 0, this.inputPad, 0, arrayOfByte1.length);
    for (int i = arrayOfByte1.length; i < this.inputPad.length; i++) {
      this.inputPad[i] = 0;
    }
    this.outputPad = new byte[this.inputPad.length];
    System.arraycopy(this.inputPad, 0, this.outputPad, 0, this.inputPad.length);
    for (int j = 0; j < this.inputPad.length; j++)
    {
      byte[] arrayOfByte3 = this.inputPad;
      arrayOfByte3[j] = ((byte)(0x36 ^ arrayOfByte3[j]));
    }
    for (int k = 0; k < this.outputPad.length; k++)
    {
      byte[] arrayOfByte2 = this.outputPad;
      arrayOfByte2[k] = ((byte)(0x5C ^ arrayOfByte2[k]));
    }
    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }
  
  public void reset()
  {
    this.digest.reset();
    this.digest.update(this.inputPad, 0, this.inputPad.length);
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.macs.OldHMac
 * JD-Core Version:    0.7.0.1
 */