package org.spongycastle.crypto.tls;

import java.io.IOException;
import org.spongycastle.crypto.BlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.engines.AESFastEngine;
import org.spongycastle.crypto.engines.DESedeEngine;
import org.spongycastle.crypto.modes.CBCBlockCipher;

public class DefaultTlsCipherFactory
  implements TlsCipherFactory
{
  protected BlockCipher createAESBlockCipher()
  {
    return new CBCBlockCipher(new AESFastEngine());
  }
  
  protected TlsCipher createAESCipher(TlsClientContext paramTlsClientContext, int paramInt1, int paramInt2)
    throws IOException
  {
    return new TlsBlockCipher(paramTlsClientContext, createAESBlockCipher(), createAESBlockCipher(), createDigest(paramInt2), createDigest(paramInt2), paramInt1);
  }
  
  public TlsCipher createCipher(TlsClientContext paramTlsClientContext, int paramInt1, int paramInt2)
    throws IOException
  {
    switch (paramInt1)
    {
    default: 
      throw new TlsFatalAlert((short)80);
    case 7: 
      return createDESedeCipher(paramTlsClientContext, 24, paramInt2);
    case 8: 
      return createAESCipher(paramTlsClientContext, 16, paramInt2);
    }
    return createAESCipher(paramTlsClientContext, 32, paramInt2);
  }
  
  protected BlockCipher createDESedeBlockCipher()
  {
    return new CBCBlockCipher(new DESedeEngine());
  }
  
  protected TlsCipher createDESedeCipher(TlsClientContext paramTlsClientContext, int paramInt1, int paramInt2)
    throws IOException
  {
    return new TlsBlockCipher(paramTlsClientContext, createDESedeBlockCipher(), createDESedeBlockCipher(), createDigest(paramInt2), createDigest(paramInt2), paramInt1);
  }
  
  protected Digest createDigest(int paramInt)
    throws IOException
  {
    switch (paramInt)
    {
    default: 
      throw new TlsFatalAlert((short)80);
    case 1: 
      return new MD5Digest();
    case 2: 
      return new SHA1Digest();
    case 3: 
      return new SHA256Digest();
    }
    return new SHA384Digest();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.DefaultTlsCipherFactory
 * JD-Core Version:    0.7.0.1
 */