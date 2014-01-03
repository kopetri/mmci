package org.apache.commons.io.input;

import java.io.IOException;
import java.io.InputStream;

public class CountingInputStream
  extends ProxyInputStream
{
  private long count;
  
  public CountingInputStream(InputStream paramInputStream)
  {
    super(paramInputStream);
  }
  
  public long getByteCount()
  {
    try
    {
      long l = this.count;
      return l;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }
  
  public int getCount()
  {
    long l;
    try
    {
      l = getByteCount();
      if (l > 2147483647L) {
        throw new ArithmeticException("The byte count " + l + " is too large to be converted to an int");
      }
    }
    finally {}
    int i = (int)l;
    return i;
  }
  
  public int read()
    throws IOException
  {
    int i = super.read();
    long l1 = this.count;
    if (i >= 0) {}
    for (long l2 = 1L;; l2 = 0L)
    {
      this.count = (l2 + l1);
      return i;
    }
  }
  
  public int read(byte[] paramArrayOfByte)
    throws IOException
  {
    int i = super.read(paramArrayOfByte);
    long l1 = this.count;
    if (i >= 0) {}
    for (long l2 = i;; l2 = 0L)
    {
      this.count = (l2 + l1);
      return i;
    }
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    int i = super.read(paramArrayOfByte, paramInt1, paramInt2);
    long l1 = this.count;
    if (i >= 0) {}
    for (long l2 = i;; l2 = 0L)
    {
      this.count = (l2 + l1);
      return i;
    }
  }
  
  public long skip(long paramLong)
    throws IOException
  {
    long l = super.skip(paramLong);
    this.count = (l + this.count);
    return l;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.io.input.CountingInputStream
 * JD-Core Version:    0.7.0.1
 */