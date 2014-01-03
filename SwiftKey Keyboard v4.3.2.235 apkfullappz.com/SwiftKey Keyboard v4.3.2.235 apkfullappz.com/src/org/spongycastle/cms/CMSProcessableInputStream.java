package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.util.io.Streams;

class CMSProcessableInputStream
  implements CMSProcessable, CMSReadable
{
  private InputStream input;
  private boolean used = false;
  
  public CMSProcessableInputStream(InputStream paramInputStream)
  {
    this.input = paramInputStream;
  }
  
  private void checkSingleUsage()
  {
    try
    {
      if (this.used) {
        throw new IllegalStateException("CMSProcessableInputStream can only be used once");
      }
    }
    finally {}
    this.used = true;
  }
  
  public Object getContent()
  {
    return getInputStream();
  }
  
  public InputStream getInputStream()
  {
    checkSingleUsage();
    return this.input;
  }
  
  public void write(OutputStream paramOutputStream)
    throws IOException, CMSException
  {
    checkSingleUsage();
    Streams.pipeAll(this.input, paramOutputStream);
    this.input.close();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSProcessableInputStream
 * JD-Core Version:    0.7.0.1
 */