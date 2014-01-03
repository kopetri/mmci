package org.spongycastle.crypto.tls;

import java.io.OutputStream;

public abstract interface TlsCompression
{
  public abstract OutputStream compress(OutputStream paramOutputStream);
  
  public abstract OutputStream decompress(OutputStream paramOutputStream);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsCompression
 * JD-Core Version:    0.7.0.1
 */