package org.spongycastle.jce.interfaces;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERObjectIdentifier;

public abstract interface PKCS12BagAttributeCarrier
{
  public abstract ASN1Encodable getBagAttribute(DERObjectIdentifier paramDERObjectIdentifier);
  
  public abstract Enumeration getBagAttributeKeys();
  
  public abstract void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier
 * JD-Core Version:    0.7.0.1
 */