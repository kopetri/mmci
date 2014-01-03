package org.spongycastle.asn1.nist;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface NISTObjectIdentifiers
{
  public static final ASN1ObjectIdentifier aes;
  public static final ASN1ObjectIdentifier dsa_with_sha224;
  public static final ASN1ObjectIdentifier dsa_with_sha256 = id_dsa_with_sha2.branch("2");
  public static final ASN1ObjectIdentifier dsa_with_sha384 = id_dsa_with_sha2.branch("3");
  public static final ASN1ObjectIdentifier dsa_with_sha512 = id_dsa_with_sha2.branch("4");
  public static final ASN1ObjectIdentifier id_aes128_CBC;
  public static final ASN1ObjectIdentifier id_aes128_CCM;
  public static final ASN1ObjectIdentifier id_aes128_CFB;
  public static final ASN1ObjectIdentifier id_aes128_ECB;
  public static final ASN1ObjectIdentifier id_aes128_GCM;
  public static final ASN1ObjectIdentifier id_aes128_OFB;
  public static final ASN1ObjectIdentifier id_aes128_wrap;
  public static final ASN1ObjectIdentifier id_aes192_CBC;
  public static final ASN1ObjectIdentifier id_aes192_CCM;
  public static final ASN1ObjectIdentifier id_aes192_CFB;
  public static final ASN1ObjectIdentifier id_aes192_ECB;
  public static final ASN1ObjectIdentifier id_aes192_GCM;
  public static final ASN1ObjectIdentifier id_aes192_OFB;
  public static final ASN1ObjectIdentifier id_aes192_wrap;
  public static final ASN1ObjectIdentifier id_aes256_CBC;
  public static final ASN1ObjectIdentifier id_aes256_CCM;
  public static final ASN1ObjectIdentifier id_aes256_CFB;
  public static final ASN1ObjectIdentifier id_aes256_ECB;
  public static final ASN1ObjectIdentifier id_aes256_GCM;
  public static final ASN1ObjectIdentifier id_aes256_OFB;
  public static final ASN1ObjectIdentifier id_aes256_wrap;
  public static final ASN1ObjectIdentifier id_dsa_with_sha2;
  public static final ASN1ObjectIdentifier id_sha224;
  public static final ASN1ObjectIdentifier id_sha256;
  public static final ASN1ObjectIdentifier id_sha384;
  public static final ASN1ObjectIdentifier id_sha512;
  public static final ASN1ObjectIdentifier nistAlgorithm;
  
  static
  {
    ASN1ObjectIdentifier localASN1ObjectIdentifier1 = new ASN1ObjectIdentifier("2.16.840.1.101.3.4");
    nistAlgorithm = localASN1ObjectIdentifier1;
    id_sha256 = localASN1ObjectIdentifier1.branch("2.1");
    id_sha384 = nistAlgorithm.branch("2.2");
    id_sha512 = nistAlgorithm.branch("2.3");
    id_sha224 = nistAlgorithm.branch("2.4");
    ASN1ObjectIdentifier localASN1ObjectIdentifier2 = nistAlgorithm.branch("1");
    aes = localASN1ObjectIdentifier2;
    id_aes128_ECB = localASN1ObjectIdentifier2.branch("1");
    id_aes128_CBC = aes.branch("2");
    id_aes128_OFB = aes.branch("3");
    id_aes128_CFB = aes.branch("4");
    id_aes128_wrap = aes.branch("5");
    id_aes128_GCM = aes.branch("6");
    id_aes128_CCM = aes.branch("7");
    id_aes192_ECB = aes.branch("21");
    id_aes192_CBC = aes.branch("22");
    id_aes192_OFB = aes.branch("23");
    id_aes192_CFB = aes.branch("24");
    id_aes192_wrap = aes.branch("25");
    id_aes192_GCM = aes.branch("26");
    id_aes192_CCM = aes.branch("27");
    id_aes256_ECB = aes.branch("41");
    id_aes256_CBC = aes.branch("42");
    id_aes256_OFB = aes.branch("43");
    id_aes256_CFB = aes.branch("44");
    id_aes256_wrap = aes.branch("45");
    id_aes256_GCM = aes.branch("46");
    id_aes256_CCM = aes.branch("47");
    ASN1ObjectIdentifier localASN1ObjectIdentifier3 = nistAlgorithm.branch("3");
    id_dsa_with_sha2 = localASN1ObjectIdentifier3;
    dsa_with_sha224 = localASN1ObjectIdentifier3.branch("1");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.nist.NISTObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */