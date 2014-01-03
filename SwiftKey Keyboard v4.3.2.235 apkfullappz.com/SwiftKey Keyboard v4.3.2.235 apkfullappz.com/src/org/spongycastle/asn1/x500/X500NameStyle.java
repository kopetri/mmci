package org.spongycastle.asn1.x500;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface X500NameStyle
{
  public abstract boolean areEqual(X500Name paramX500Name1, X500Name paramX500Name2);
  
  public abstract ASN1ObjectIdentifier attrNameToOID(String paramString);
  
  public abstract int calculateHashCode(X500Name paramX500Name);
  
  public abstract RDN[] fromString(String paramString);
  
  public abstract ASN1Encodable stringToValue(ASN1ObjectIdentifier paramASN1ObjectIdentifier, String paramString);
  
  public abstract String toString(X500Name paramX500Name);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x500.X500NameStyle
 * JD-Core Version:    0.7.0.1
 */