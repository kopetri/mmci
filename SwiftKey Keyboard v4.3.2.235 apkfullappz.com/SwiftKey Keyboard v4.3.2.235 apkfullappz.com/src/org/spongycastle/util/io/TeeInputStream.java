package org.spongycastle.util.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class TeeInputStream
  extends InputStream
{
  private final InputStream input;
  private final OutputStream output;
  
  public TeeInputStream(InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this.input = paramInputStream;
    this.output = paramOutputStream;
  }
  
  public void close()
    throws IOException
  {
    this.input.close();
    this.output.close();
  }
  
  public OutputStream getOutputStream()
  {
    return this.output;
  }
  
  public int read()
    throws IOException
  {
    int i = this.input.read();
    if (i >= 0) {
      this.output.write(i);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    return read(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.input.read(paramArrayOfByte, paramInt1, paramInt2);
    if (i > 0) {
      this.output.write(paramArrayOfByte, paramInt1, i);
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.io.TeeInputStream
 * JD-Core Version:    0.7.0.1
 */