package org.spongycastle.asn1;

import java.io.IOException;

public abstract interface InMemoryRepresentable
{
  public abstract ASN1Primitive getLoadedObject()
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.InMemoryRepresentable
 * JD-Core Version:    0.7.0.1
 */