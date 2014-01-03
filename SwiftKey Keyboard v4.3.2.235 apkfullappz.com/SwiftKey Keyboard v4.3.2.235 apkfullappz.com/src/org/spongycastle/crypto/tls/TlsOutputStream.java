package org.spongycastle.crypto.tls;

import java.io.IOException;
import java.io.OutputStream;

class TlsOutputStream
  extends OutputStream
{
  private byte[] buf = new byte[1];
  private TlsProtocolHandler handler;
  
  TlsOutputStream(TlsProtocolHandler paramTlsProtocolHandler)
  {
    this.handler = paramTlsProtocolHandler;
  }
  
  public void close()
    throws IOException
  {
    this.handler.close();
  }
  
  public void flush()
    throws IOException
  {
    this.handler.flush();
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.buf[0] = ((byte)paramInt);
    write(this.buf, 0, 1);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.handler.writeData(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsOutputStream
 * JD-Core Version:    0.7.0.1
 */