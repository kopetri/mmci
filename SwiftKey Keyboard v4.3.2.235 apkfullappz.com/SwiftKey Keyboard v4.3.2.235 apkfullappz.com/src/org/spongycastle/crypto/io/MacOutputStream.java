package org.spongycastle.crypto.io;

import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.Mac;

public class MacOutputStream
  extends OutputStream
{
  protected Mac mac;
  
  public MacOutputStream(Mac paramMac)
  {
    this.mac = paramMac;
  }
  
  public byte[] getMac()
  {
    byte[] arrayOfByte = new byte[this.mac.getMacSize()];
    this.mac.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.mac.update((byte)paramInt);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.mac.update(paramArrayOfByte, paramInt1, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.MacOutputStream
 * JD-Core Version:    0.7.0.1
 */