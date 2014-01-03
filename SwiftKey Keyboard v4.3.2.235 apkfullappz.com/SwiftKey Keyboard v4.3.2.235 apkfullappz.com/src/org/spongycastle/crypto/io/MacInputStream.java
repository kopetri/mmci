package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.Mac;

public class MacInputStream
  extends FilterInputStream
{
  protected Mac mac;
  
  public MacInputStream(InputStream paramInputStream, Mac paramMac)
  {
    super(paramInputStream);
    this.mac = paramMac;
  }
  
  public Mac getMac()
  {
    return this.mac;
  }
  
  public int read()
    throws IOException
  {
    int i = this.in.read();
    if (i >= 0) {
      this.mac.update((byte)i);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
    if (i >= 0) {
      this.mac.update(paramArrayOfByte, paramInt1, i);
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.MacInputStream
 * JD-Core Version:    0.7.0.1
 */