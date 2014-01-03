package org.spongycastle.asn1.eac;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface EACObjectIdentifiers
{
  public static final ASN1ObjectIdentifier bsi_de;
  public static final ASN1ObjectIdentifier id_CA;
  public static final ASN1ObjectIdentifier id_CA_DH;
  public static final ASN1ObjectIdentifier id_CA_DH_3DES_CBC_CBC;
  public static final ASN1ObjectIdentifier id_CA_ECDH;
  public static final ASN1ObjectIdentifier id_CA_ECDH_3DES_CBC_CBC;
  public static final ASN1ObjectIdentifier id_EAC_ePassport = bsi_de.branch("3.1.2.1");
  public static final ASN1ObjectIdentifier id_PK;
  public static final ASN1ObjectIdentifier id_PK_DH;
  public static final ASN1ObjectIdentifier id_PK_ECDH;
  public static final ASN1ObjectIdentifier id_TA;
  public static final ASN1ObjectIdentifier id_TA_ECDSA;
  public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_1;
  public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_224;
  public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_256;
  public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_384;
  public static final ASN1ObjectIdentifier id_TA_ECDSA_SHA_512;
  public static final ASN1ObjectIdentifier id_TA_RSA;
  public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_1;
  public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_256;
  public static final ASN1ObjectIdentifier id_TA_RSA_PSS_SHA_512;
  public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_1;
  public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_256;
  public static final ASN1ObjectIdentifier id_TA_RSA_v1_5_SHA_512;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("0.4.0.127.0.7");
    bsi_de = localASN1ObjectIdentifier1;
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = localASN1ObjectIdentifier1.branch("2.2.1");
    id_PK = localASN1ObjectIdentifier2;
    id_PK_DH = localASN1ObjectIdentifier2.branch("1");
    id_PK_ECDH = id_PK.branch("2");
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = bsi_de.branch("2.2.3");
    id_CA = localASN1ObjectIdentifier3;
    ASN1ObjectIdentifier localASN1ObjectIdentifier4 = localASN1ObjectIdentifier3.branch("1");
    id_CA_DH = localASN1ObjectIdentifier4;
    id_CA_DH_3DES_CBC_CBC = localASN1ObjectIdentifier4.branch("1");
    ASN1ObjectIdentifier localASN1ObjectIdentifier5 = id_CA.branch("2");
    id_CA_ECDH = localASN1ObjectIdentifier5;
    id_CA_ECDH_3DES_CBC_CBC = localASN1ObjectIdentifier5.branch("1");
    ASN1ObjectIdentifier localASN1ObjectIdentifier6 = bsi_de.branch("2.2.2");
    id_TA = localASN1ObjectIdentifier6;
    ASN1ObjectIdentifier localASN1ObjectIdentifier7 = localASN1ObjectIdentifier6.branch("1");
    id_TA_RSA = localASN1ObjectIdentifier7;
    id_TA_RSA_v1_5_SHA_1 = localASN1ObjectIdentifier7.branch("1");
    id_TA_RSA_v1_5_SHA_256 = id_TA_RSA.branch("2");
    id_TA_RSA_PSS_SHA_1 = id_TA_RSA.branch("3");
    id_TA_RSA_PSS_SHA_256 = id_TA_RSA.branch("4");
    id_TA_RSA_v1_5_SHA_512 = id_TA_RSA.branch("5");
    id_TA_RSA_PSS_SHA_512 = id_TA_RSA.branch("6");
    ASN1ObjectIdentifier localASN1ObjectIdentifier8 = id_TA.branch("2");
    id_TA_ECDSA = localASN1ObjectIdentifier8;
    id_TA_ECDSA_SHA_1 = localASN1ObjectIdentifier8.branch("1");
    id_TA_ECDSA_SHA_224 = id_TA_ECDSA.branch("2");
    id_TA_ECDSA_SHA_256 = id_TA_ECDSA.branch("3");
    id_TA_ECDSA_SHA_384 = id_TA_ECDSA.branch("4");
    id_TA_ECDSA_SHA_512 = id_TA_ECDSA.branch("5");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.EACObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */