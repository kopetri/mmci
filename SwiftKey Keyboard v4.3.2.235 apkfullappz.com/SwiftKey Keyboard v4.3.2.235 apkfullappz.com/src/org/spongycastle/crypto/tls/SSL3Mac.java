package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class SSL3Mac
  implements Mac
{
  private static final byte IPAD = 54;
  static final byte[] MD5_IPAD = genPad(, 48);
  static final byte[] MD5_OPAD = genPad((byte)92, 48);
  private static final byte OPAD = 92;
  static final byte[] SHA1_IPAD = genPad((byte)54, 40);
  static final byte[] SHA1_OPAD = genPad((byte)92, 40);
  private Digest digest;
  private byte[] ipad;
  private byte[] opad;
  private byte[] secret;
  
  public SSL3Mac(Digest paramDigest)
  {
    this.digest = paramDigest;
    if (paramDigest.getDigestSize() == 20)
    {
      this.ipad = SHA1_IPAD;
      this.opad = SHA1_OPAD;
      return;
    }
    this.ipad = MD5_IPAD;
    this.opad = MD5_OPAD;
  }
  
  private static byte[] genPad(byte paramByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[paramInt];
    Arrays.fill(arrayOfByte, paramByte);
    return arrayOfByte;
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    this.digest.update(this.secret, 0, this.secret.length);
    this.digest.update(this.opad, 0, this.opad.length);
    this.digest.update(arrayOfByte, 0, arrayOfByte.length);
    int i = this.digest.doFinal(paramArrayOfByte, paramInt);
    reset();
    return i;
  }
  
  public String getAlgorithmName()
  {
    return this.digest.getAlgorithmName() + "/SSL3MAC";
  }
  
  public int getMacSize()
  {
    return this.digest.getDigestSize();
  }
  
  public Digest getUnderlyingDigest()
  {
    return this.digest;
  }
  
  public void init(CipherParameters paramCipherParameters)
  {
    this.secret = Arrays.clone(((KeyParameter)paramCipherParameters).getKey());
    reset();
  }
  
  public void reset()
  {
    this.digest.reset();
    this.digest.update(this.secret, 0, this.secret.length);
    this.digest.update(this.ipad, 0, this.ipad.length);
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
 * Qualified Name:     org.spongycastle.crypto.tls.SSL3Mac
 * JD-Core Version:    0.7.0.1
 */