package org.spongycastle.x509;

import java.io.InputStream;
import java.util.Collection;
import org.spongycastle.x509.util.StreamParsingException;

public abstract class X509StreamParserSpi
{
  public abstract void engineInit(InputStream paramInputStream);
  
  public abstract Object engineRead()
    throws StreamParsingException;
  
  public abstract Collection engineReadAll()
    throws StreamParsingException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.x509.X509StreamParserSpi
 * JD-Core Version:    0.7.0.1
 */