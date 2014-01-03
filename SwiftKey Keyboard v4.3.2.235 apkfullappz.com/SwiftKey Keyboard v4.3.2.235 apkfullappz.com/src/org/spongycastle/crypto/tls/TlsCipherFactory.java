package org.spongycastle.crypto.tls;

import java.io.IOException;

public abstract interface TlsCipherFactory
{
  public abstract TlsCipher createCipher(TlsClientContext paramTlsClientContext, int paramInt1, int paramInt2)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsCipherFactory
 * JD-Core Version:    0.7.0.1
 */