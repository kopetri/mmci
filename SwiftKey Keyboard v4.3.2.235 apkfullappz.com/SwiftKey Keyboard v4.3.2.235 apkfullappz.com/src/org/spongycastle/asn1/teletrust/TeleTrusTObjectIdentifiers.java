package org.spongycastle.asn1.teletrust;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface TeleTrusTObjectIdentifiers
{
  public static final ASN1ObjectIdentifier brainpoolP160r1;
  public static final ASN1ObjectIdentifier brainpoolP160t1 = versionOne.branch("2");
  public static final ASN1ObjectIdentifier brainpoolP192r1 = versionOne.branch("3");
  public static final ASN1ObjectIdentifier brainpoolP192t1 = versionOne.branch("4");
  public static final ASN1ObjectIdentifier brainpoolP224r1 = versionOne.branch("5");
  public static final ASN1ObjectIdentifier brainpoolP224t1 = versionOne.branch("6");
  public static final ASN1ObjectIdentifier brainpoolP256r1 = versionOne.branch("7");
  public static final ASN1ObjectIdentifier brainpoolP256t1 = versionOne.branch("8");
  public static final ASN1ObjectIdentifier brainpoolP320r1 = versionOne.branch("9");
  public static final ASN1ObjectIdentifier brainpoolP320t1 = versionOne.branch("10");
  public static final ASN1ObjectIdentifier brainpoolP384r1 = versionOne.branch("11");
  public static final ASN1ObjectIdentifier brainpoolP384t1 = versionOne.branch("12");
  public static final ASN1ObjectIdentifier brainpoolP512r1 = versionOne.branch("13");
  public static final ASN1ObjectIdentifier brainpoolP512t1 = versionOne.branch("14");
  public static final ASN1ObjectIdentifier ecSign;
  public static final ASN1ObjectIdentifier ecSignWithRipemd160;
  public static final ASN1ObjectIdentifier ecSignWithSha1;
  public static final ASN1ObjectIdentifier ecc_brainpool;
  public static final ASN1ObjectIdentifier ellipticCurve;
  public static final ASN1ObjectIdentifier ripemd128;
  public static final ASN1ObjectIdentifier ripemd160;
  public static final ASN1ObjectIdentifier ripemd256;
  public static final ASN1ObjectIdentifier rsaSignatureWithripemd128;
  public static final ASN1ObjectIdentifier rsaSignatureWithripemd160;
  public static final ASN1ObjectIdentifier rsaSignatureWithripemd256;
  public static final ASN1ObjectIdentifier teleTrusTAlgorithm;
  public static final ASN1ObjectIdentifier teleTrusTRSAsignatureAlgorithm;
  public static final ASN1ObjectIdentifier versionOne;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("1.3.36.3");
    teleTrusTAlgorithm = localASN1ObjectIdentifier1;
    ripemd160 = localASN1ObjectIdentifier1.branch("2.1");
    ripemd128 = teleTrusTAlgorithm.branch("2.2");
    ripemd256 = teleTrusTAlgorithm.branch("2.3");
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = teleTrusTAlgorithm.branch("3.1");
    teleTrusTRSAsignatureAlgorithm = localASN1ObjectIdentifier2;
    rsaSignatureWithripemd160 = localASN1ObjectIdentifier2.branch("2");
    rsaSignatureWithripemd128 = teleTrusTRSAsignatureAlgorithm.branch("3");
    rsaSignatureWithripemd256 = teleTrusTRSAsignatureAlgorithm.branch("4");
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = teleTrusTAlgorithm.branch("3.2");
    ecSign = localASN1ObjectIdentifier3;
    ecSignWithSha1 = localASN1ObjectIdentifier3.branch("1");
    ecSignWithRipemd160 = ecSign.branch("2");
    ASN1ObjectIdentifier localASN1ObjectIdentifier4 = teleTrusTAlgorithm.branch("3.2.8");
    ecc_brainpool = localASN1ObjectIdentifier4;
    ASN1ObjectIdentifier localASN1ObjectIdentifier5 = localASN1ObjectIdentifier4.branch("1");
    ellipticCurve = localASN1ObjectIdentifier5;
    ASN1ObjectIdentifier localASN1ObjectIdentifier6 = localASN1ObjectIdentifier5.branch("1");
    versionOne = localASN1ObjectIdentifier6;
    brainpoolP160r1 = localASN1ObjectIdentifier6.branch("1");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */