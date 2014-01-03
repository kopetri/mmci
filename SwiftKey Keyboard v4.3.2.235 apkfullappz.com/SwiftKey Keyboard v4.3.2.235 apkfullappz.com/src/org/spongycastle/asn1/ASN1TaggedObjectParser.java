package org.spongycastle.asn1;

import java.io.IOException;

public abstract interface ASN1TaggedObjectParser
  extends ASN1Encodable, InMemoryRepresentable
{
  public abstract ASN1Encodable getObjectParser(int paramInt, boolean paramBoolean)
    throws IOException;
  
  public abstract int getTagNo();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1TaggedObjectParser
 * JD-Core Version:    0.7.0.1
 */