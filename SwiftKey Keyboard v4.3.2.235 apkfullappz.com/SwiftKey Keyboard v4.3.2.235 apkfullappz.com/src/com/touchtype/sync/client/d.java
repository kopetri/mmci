package com.touchtype.sync.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

abstract class d
  extends j
{
  private SyncStorage a;
  
  d(SyncStorage paramSyncStorage, LoggingListener paramLoggingListener)
  {
    super(paramLoggingListener);
    this.a = paramSyncStorage;
  }
  
  final void a(InputStream paramInputStream, String paramString, int paramInt, Map<String, List<String>> paramMap)
  {
    try
    {
      if (paramString.startsWith("application/x-fluency-lm"))
      {
        localFile = File.createTempFile("dynamic", ".lm", this.a.getTempDirectory());
        arrayOfByte = new byte[4096];
      }
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      File localFile;
      byte[] arrayOfByte;
      e.c(this.d, "com.touchtype.sync.client", "Error handling sync Language Model result: " + localUnsupportedEncodingException.getMessage());
      a();
      return;
      localBufferedOutputStream.flush();
      localBufferedOutputStream.close();
      a(paramMap, localFile);
      return;
    }
    catch (IOException localIOException)
    {
      label81:
      label96:
      e.c(this.d, "com.touchtype.sync.client", "Error handling sync Language Model result: " + localIOException.getMessage());
      a();
      return;
    }
    try
    {
      localBufferedOutputStream = new BufferedOutputStream(new FileOutputStream(localFile));
      try
      {
        for (;;)
        {
          int i = paramInputStream.read(arrayOfByte);
          if (i == -1) {
            break;
          }
          localBufferedOutputStream.write(arrayOfByte, 0, i);
        }
        if (localBufferedOutputStream == null) {
          break label96;
        }
      }
      finally {}
    }
    finally
    {
      localBufferedOutputStream = null;
      break label81;
    }
    localBufferedOutputStream.flush();
    localBufferedOutputStream.close();
    throw localObject1;
    a(paramMap);
  }
  
  abstract void a(Map<String, List<String>> paramMap, File paramFile);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.d
 * JD-Core Version:    0.7.0.1
 */