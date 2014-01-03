package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface X509ObjectIdentifiers
{
  public static final ASN1ObjectIdentifier commonName = new ASN1ObjectIdentifier("2.5.4.3");
  public static final ASN1ObjectIdentifier countryName = new ASN1ObjectIdentifier("2.5.4.6");
  public static final ASN1ObjectIdentifier crlAccessMethod = id_ad_caIssuers;
  public static final String id = "2.5.4";
  public static final ASN1ObjectIdentifier id_SHA1;
  public static final ASN1ObjectIdentifier id_ad;
  public static final ASN1ObjectIdentifier id_ad_caIssuers;
  public static final ASN1ObjectIdentifier id_ad_ocsp;
  public static final ASN1ObjectIdentifier id_at_name;
  public static final ASN1ObjectIdentifier id_at_telephoneNumber;
  public static final ASN1ObjectIdentifier id_ce;
  public static final ASN1ObjectIdentifier id_ea_rsa;
  public static final ASN1ObjectIdentifier id_pe;
  public static final ASN1ObjectIdentifier id_pkix;
  public static final ASN1ObjectIdentifier localityName = new ASN1ObjectIdentifier("2.5.4.7");
  public static final ASN1ObjectIdentifier ocspAccessMethod;
  public static final ASN1ObjectIdentifier organization;
  public static final ASN1ObjectIdentifier organizationalUnitName;
  public static final ASN1ObjectIdentifier ripemd160;
  public static final ASN1ObjectIdentifier ripemd160WithRSAEncryption;
  public static final ASN1ObjectIdentifier stateOrProvinceName = new ASN1ObjectIdentifier("2.5.4.8");
  
  static
  {
    organization = new ASN1ObjectIdentifier("2.5.4.10");
    organizationalUnitName = new ASN1ObjectIdentifier("2.5.4.11");
    id_at_telephoneNumber = new ASN1ObjectIdentifier("2.5.4.20");
    id_at_name = new ASN1ObjectIdentifier("2.5.4.41");
    id_SHA1 = new ASN1ObjectIdentifier("1.3.14.3.2.26");
    ripemd160 = new ASN1ObjectIdentifier("1.3.36.3.2.1");
    ripemd160WithRSAEncryption = new ASN1ObjectIdentifier("1.3.36.3.3.1.2");
    id_ea_rsa = new ASN1ObjectIdentifier("2.5.8.1.1");
    id_pkix = new ASN1ObjectIdentifier("1.3.6.1.5.5.7");
    id_pe = new ASN1ObjectIdentifier(id_pkix + ".1");
    id_ce = new ASN1ObjectIdentifier("2.5.29");
    id_ad = new ASN1ObjectIdentifier(id_pkix + ".48");
    id_ad_caIssuers = new ASN1ObjectIdentifier(id_ad + ".2");
    ASN1ObjectIdentifier localASN1ObjectIdentifier = new ASN1ObjectIdentifier(id_ad + ".1");
    id_ad_ocsp = localASN1ObjectIdentifier;
    ocspAccessMethod = localASN1ObjectIdentifier;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509ObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */