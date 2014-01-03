package org.spongycastle.cms;

import java.io.IOException;
import java.io.OutputStream;

public abstract interface CMSProcessable
{
  public abstract Object getContent();
  
  public abstract void write(OutputStream paramOutputStream)
    throws IOException, CMSException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSProcessable
 * JD-Core Version:    0.7.0.1
 */