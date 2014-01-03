package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;

class StreamUtil
{
  private static final long MAX_MEMORY = Runtime.getRuntime().maxMemory();
  
  static int calculateBodyLength(int paramInt)
  {
    int i = 1;
    if (paramInt > 127)
    {
      int j = 1;
      int k = paramInt;
      for (;;)
      {
        k >>>= 8;
        if (k == 0) {
          break;
        }
        j++;
      }
      for (int m = 8 * (j - 1); m >= 0; m -= 8) {
        i++;
      }
    }
    return i;
  }
  
  static int calculateTagLength(int paramInt)
    throws IOException
  {
    int i = 1;
    if (paramInt >= 31)
    {
      if (paramInt < 128) {
        i++;
      }
    }
    else {
      return i;
    }
    byte[] arrayOfByte = new byte[5];
    int j = -1 + arrayOfByte.length;
    arrayOfByte[j] = ((byte)(paramInt & 0x7F));
    do
    {
      paramInt >>= 7;
      j--;
      arrayOfByte[j] = ((byte)(0x80 | paramInt & 0x7F));
    } while (paramInt > 127);
    return 1 + (arrayOfByte.length - j);
  }
  
  static int findLimit(InputStream paramInputStream)
  {
    if ((paramInputStream instanceof LimitedInputStream)) {
      return ((LimitedInputStream)paramInputStream).getRemaining();
    }
    if ((paramInputStream instanceof ASN1InputStream)) {
      return ((ASN1InputStream)paramInputStream).getLimit();
    }
    if ((paramInputStream instanceof ByteArrayInputStream)) {
      return ((ByteArrayInputStream)paramInputStream).available();
    }
    if ((paramInputStream instanceof FileInputStream)) {
      try
      {
        long l = ((FileInputStream)paramInputStream).getChannel().size();
        if (l < 2147483647L) {
          return (int)l;
        }
      }
      catch (IOException localIOException) {}
    }
    if (MAX_MEMORY > 2147483647L) {
      return 2147483647;
    }
    return (int)MAX_MEMORY;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.StreamUtil
 * JD-Core Version:    0.7.0.1
 */