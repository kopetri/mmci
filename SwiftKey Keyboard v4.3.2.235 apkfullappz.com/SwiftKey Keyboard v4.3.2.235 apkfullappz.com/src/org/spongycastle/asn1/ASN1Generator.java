package org.spongycastle.asn1;

import java.io.OutputStream;

public abstract class ASN1Generator
{
  protected OutputStream _out;
  
  public ASN1Generator(OutputStream paramOutputStream)
  {
    this._out = paramOutputStream;
  }
  
  public abstract OutputStream getRawOutputStream();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Generator
 * JD-Core Version:    0.7.0.1
 */