package org.spongycastle.crypto.tls;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;

class CombinedHash
  implements Digest
{
  protected TlsClientContext context;
  protected MD5Digest md5;
  protected SHA1Digest sha1;
  
  CombinedHash()
  {
    this.md5 = new MD5Digest();
    this.sha1 = new SHA1Digest();
  }
  
  CombinedHash(CombinedHash paramCombinedHash)
  {
    this.context = paramCombinedHash.context;
    this.md5 = new MD5Digest(paramCombinedHash.md5);
    this.sha1 = new SHA1Digest(paramCombinedHash.sha1);
  }
  
  CombinedHash(TlsClientContext paramTlsClientContext)
  {
    this.context = paramTlsClientContext;
    this.md5 = new MD5Digest();
    this.sha1 = new SHA1Digest();
  }
  
  public int doFinal(byte[] paramArrayOfByte, int paramInt)
  {
    if (this.context != null) {
      if (this.context.getServerVersion().getFullVersion() < ProtocolVersion.TLSv10.getFullVersion()) {
        break label85;
      }
    }
    label85:
    for (int i = 1;; i = 0)
    {
      if (i == 0)
      {
        ssl3Complete(this.md5, SSL3Mac.MD5_IPAD, SSL3Mac.MD5_OPAD);
        ssl3Complete(this.sha1, SSL3Mac.SHA1_IPAD, SSL3Mac.SHA1_OPAD);
      }
      return this.md5.doFinal(paramArrayOfByte, paramInt) + this.sha1.doFinal(paramArrayOfByte, paramInt + 16);
    }
  }
  
  public String getAlgorithmName()
  {
    return this.md5.getAlgorithmName() + " and " + this.sha1.getAlgorithmName();
  }
  
  public int getDigestSize()
  {
    return 36;
  }
  
  public void reset()
  {
    this.md5.reset();
    this.sha1.reset();
  }
  
  protected void ssl3Complete(Digest paramDigest, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte1 = this.context.getSecurityParameters().masterSecret;
    paramDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
    paramDigest.update(paramArrayOfByte1, 0, paramArrayOfByte1.length);
    byte[] arrayOfByte2 = new byte[paramDigest.getDigestSize()];
    paramDigest.doFinal(arrayOfByte2, 0);
    paramDigest.update(arrayOfByte1, 0, arrayOfByte1.length);
    paramDigest.update(paramArrayOfByte2, 0, paramArrayOfByte2.length);
    paramDigest.update(arrayOfByte2, 0, arrayOfByte2.length);
  }
  
  public void update(byte paramByte)
  {
    this.md5.update(paramByte);
    this.sha1.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.md5.update(paramArrayOfByte, paramInt1, paramInt2);
    this.sha1.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.CombinedHash
 * JD-Core Version:    0.7.0.1
 */