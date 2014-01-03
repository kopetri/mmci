package org.spongycastle.cms;

import java.io.IOException;
import java.io.InputStream;

abstract interface CMSReadable
{
  public abstract InputStream getInputStream()
    throws IOException, CMSException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSReadable
 * JD-Core Version:    0.7.0.1
 */