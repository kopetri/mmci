package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.InputStream;

class TlsInputStream
  extends InputStream
{
  private byte[] buf = new byte[1];
  private TlsProtocolHandler handler = null;
  
  TlsInputStream(TlsProtocolHandler paramTlsProtocolHandler)
  {
    this.handler = paramTlsProtocolHandler;
  }
  
  public void close()
    throws IOException
  {
    this.handler.close();
  }
  
  public int read()
    throws IOException
  {
    if (read(this.buf) < 0) {
      return -1;
    }
    return 0xFF & this.buf[0];
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    return this.handler.readApplicationData(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsInputStream
 * JD-Core Version:    0.7.0.1
 */