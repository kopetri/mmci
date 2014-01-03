package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

public final class Resources
{
  public static InputSupplier<InputStream> newInputStreamSupplier(URL paramURL)
  {
    Preconditions.checkNotNull(paramURL);
    new InputSupplier()
    {
      public InputStream getInput()
        throws IOException
      {
        return this.val$url.openStream();
      }
    };
  }
  
  public static InputSupplier<InputStreamReader> newReaderSupplier(URL paramURL, Charset paramCharset)
  {
    return CharStreams.newReaderSupplier(newInputStreamSupplier(paramURL), paramCharset);
  }
  
  public static String toString(URL paramURL, Charset paramCharset)
    throws IOException
  {
    return CharStreams.toString(newReaderSupplier(paramURL, paramCharset));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.common.io.Resources
 * JD-Core Version:    0.7.0.1
 */