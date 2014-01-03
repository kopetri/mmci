package org.spongycastle.crypto.io;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.spongycastle.crypto.BufferedBlockCipher;
import org.spongycastle.crypto.StreamCipher;

public class CipherOutputStream
  extends FilterOutputStream
{
  private byte[] buf;
  private BufferedBlockCipher bufferedBlockCipher;
  private byte[] oneByte = new byte[1];
  private StreamCipher streamCipher;
  
  public CipherOutputStream(OutputStream paramOutputStream, BufferedBlockCipher paramBufferedBlockCipher)
  {
    super(paramOutputStream);
    this.bufferedBlockCipher = paramBufferedBlockCipher;
    this.buf = new byte[paramBufferedBlockCipher.getBlockSize()];
  }
  
  public CipherOutputStream(OutputStream paramOutputStream, StreamCipher paramStreamCipher)
  {
    super(paramOutputStream);
    this.streamCipher = paramStreamCipher;
  }
  
  public void close()
    throws IOException
  {
    try
    {
      if (this.bufferedBlockCipher != null)
      {
        byte[] arrayOfByte = new byte[this.bufferedBlockCipher.getOutputSize(0)];
        int i = this.bufferedBlockCipher.doFinal(arrayOfByte, 0);
        if (i != 0) {
          this.out.write(arrayOfByte, 0, i);
        }
      }
      flush();
      super.close();
      return;
    }
    catch (Exception localException)
    {
      throw new IOException("Error closing stream: " + localException.toString());
    }
  }
  
  public void flush()
    throws IOException
  {
    super.flush();
  }
  
  public void write(int paramInt)
    throws IOException
  {
    this.oneByte[0] = ((byte)paramInt);
    if (this.bufferedBlockCipher != null)
    {
      int i = this.bufferedBlockCipher.processBytes(this.oneByte, 0, 1, this.buf, 0);
      if (i != 0) {
        this.out.write(this.buf, 0, i);
      }
      return;
    }
    this.out.write(this.streamCipher.returnByte((byte)paramInt));
  }
  
  public void write(byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this.bufferedBlockCipher != null)
    {
      byte[] arrayOfByte2 = new byte[this.bufferedBlockCipher.getOutputSize(paramInt2)];
      int i = this.bufferedBlockCipher.processBytes(paramArrayOfByte, paramInt1, paramInt2, arrayOfByte2, 0);
      if (i != 0) {
        this.out.write(arrayOfByte2, 0, i);
      }
      return;
    }
    byte[] arrayOfByte1 = new byte[paramInt2];
    this.streamCipher.processBytes(paramArrayOfByte, paramInt1, paramInt2, arrayOfByte1, 0);
    this.out.write(arrayOfByte1, 0, paramInt2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.CipherOutputStream
 * JD-Core Version:    0.7.0.1
 */