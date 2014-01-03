package org.spongycastle.asn1.iana;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface IANAObjectIdentifiers
{
  public static final ASN1ObjectIdentifier hmacMD5;
  public static final ASN1ObjectIdentifier hmacRIPEMD160 = new ASN1ObjectIdentifier(isakmpOakley + ".4");
  public static final ASN1ObjectIdentifier hmacSHA1;
  public static final ASN1ObjectIdentifier hmacTIGER;
  public static final ASN1ObjectIdentifier isakmpOakley = new ASN1ObjectIdentifier("1.3.6.1.5.5.8.1");
  
  static
  {
    hmacMD5 = new ASN1ObjectIdentifier(isakmpOakley + ".1");
    hmacSHA1 = new ASN1ObjectIdentifier(isakmpOakley + ".2");
    hmacTIGER = new ASN1ObjectIdentifier(isakmpOakley + ".3");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.iana.IANAObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */