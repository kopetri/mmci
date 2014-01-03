package org.spongycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.crypto.Digest;

public class DigestInputStream
  extends FilterInputStream
{
  protected Digest digest;
  
  public DigestInputStream(InputStream paramInputStream, Digest paramDigest)
  {
    super(paramInputStream);
    this.digest = paramDigest;
  }
  
  public Digest getDigest()
  {
    return this.digest;
  }
  
  public int read()
    throws IOException
  {
    int i = this.in.read();
    if (i >= 0) {
      this.digest.update((byte)i);
    }
    return i;
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = this.in.read(paramArrayOfByte, paramInt1, paramInt2);
    if (i > 0) {
      this.digest.update(paramArrayOfByte, paramInt1, i);
    }
    return i;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.io.DigestInputStream
 * JD-Core Version:    0.7.0.1
 */