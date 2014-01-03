package org.spongycastle.jcajce.provider.digest;

import java.security.MessageDigest;
import org.spongycastle.crypto.Digest;

public class BCMessageDigest
  extends MessageDigest
{
  protected Digest digest;
  
  protected BCMessageDigest(Digest paramDigest)
  {
    super(paramDigest.getAlgorithmName());
    this.digest = paramDigest;
  }
  
  public byte[] engineDigest()
  {
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  public void engineReset()
  {
    this.digest.reset();
  }
  
  public void engineUpdate(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.digest.BCMessageDigest
 * JD-Core Version:    0.7.0.1
 */