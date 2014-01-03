package org.spongycastle.cms.jcajce;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.InflaterInputStream;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.operator.InputExpander;
import org.spongycastle.operator.InputExpanderProvider;
import org.spongycastle.util.io.StreamOverflowException;

public class ZlibExpanderProvider
  implements InputExpanderProvider
{
  private final long limit;
  
  public ZlibExpanderProvider()
  {
    this.limit = -1L;
  }
  
  public ZlibExpanderProvider(long paramLong)
  {
    this.limit = paramLong;
  }
  
  public InputExpander get(final AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    new InputExpander()
    {
      public AlgorithmIdentifier getAlgorithmIdentifier()
      {
        return paramAlgorithmIdentifier;
      }
      
      public InputStream getInputStream(InputStream paramAnonymousInputStream)
      {
        Object localObject = new InflaterInputStream(paramAnonymousInputStream);
        if (ZlibExpanderProvider.this.limit >= 0L) {
          localObject = new ZlibExpanderProvider.LimitedInputStream((InputStream)localObject, ZlibExpanderProvider.this.limit);
        }
        return localObject;
      }
    };
  }
  
  private static class LimitedInputStream
    extends FilterInputStream
  {
    private long remaining;
    
    public LimitedInputStream(InputStream paramInputStream, long paramLong)
    {
      super();
      this.remaining = paramLong;
    }
    
    public int read()
      throws IOException
    {
      if (this.remaining >= 0L)
      {
        int i = this.in.read();
        if (i >= 0)
        {
          long l = this.remaining - 1L;
          this.remaining = l;
          if (l < 0L) {}
        }
        else
        {
          return i;
        }
      }
      throw new StreamOverflowException("expanded byte limit exceeded");
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int j;
      if (paramInt2 <= 0)
      {
        j = super.read(paramArrayOfByte, paramInt1, paramInt2);
        return j;
      }
      if (this.remaining < 1L)
      {
        read();
        return -1;
      }
      if (this.remaining > paramInt2) {}
      for (int i = paramInt2;; i = (int)this.remaining)
      {
        j = this.in.read(paramArrayOfByte, paramInt1, i);
        if (j <= 0) {
          break;
        }
        this.remaining -= j;
        return j;
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.jcajce.ZlibExpanderProvider
 * JD-Core Version:    0.7.0.1
 */