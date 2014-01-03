package org.spongycastle.asn1.bc;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface BCObjectIdentifiers
{
  public static final ASN1ObjectIdentifier bc = new ASN1ObjectIdentifier("1.3.6.1.4.1.22554");
  public static final ASN1ObjectIdentifier bc_pbe = new ASN1ObjectIdentifier(bc.getId() + ".1");
  public static final ASN1ObjectIdentifier bc_pbe_sha1 = new ASN1ObjectIdentifier(bc_pbe.getId() + ".1");
  public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12;
  public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes128_cbc = new ASN1ObjectIdentifier(bc_pbe_sha1_pkcs12.getId() + ".1.2");
  public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes192_cbc = new ASN1ObjectIdentifier(bc_pbe_sha1_pkcs12.getId() + ".1.22");
  public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs12_aes256_cbc = new ASN1ObjectIdentifier(bc_pbe_sha1_pkcs12.getId() + ".1.42");
  public static final ASN1ObjectIdentifier bc_pbe_sha1_pkcs5;
  public static final ASN1ObjectIdentifier bc_pbe_sha224;
  public static final ASN1ObjectIdentifier bc_pbe_sha256 = new ASN1ObjectIdentifier(bc_pbe.getId() + ".2.1");
  public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12;
  public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes128_cbc = new ASN1ObjectIdentifier(bc_pbe_sha256_pkcs12.getId() + ".1.2");
  public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes192_cbc = new ASN1ObjectIdentifier(bc_pbe_sha256_pkcs12.getId() + ".1.22");
  public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs12_aes256_cbc = new ASN1ObjectIdentifier(bc_pbe_sha256_pkcs12.getId() + ".1.42");
  public static final ASN1ObjectIdentifier bc_pbe_sha256_pkcs5;
  public static final ASN1ObjectIdentifier bc_pbe_sha384 = new ASN1ObjectIdentifier(bc_pbe.getId() + ".2.2");
  public static final ASN1ObjectIdentifier bc_pbe_sha512 = new ASN1ObjectIdentifier(bc_pbe.getId() + ".2.3");
  
  static
  {
    bc_pbe_sha224 = new ASN1ObjectIdentifier(bc_pbe.getId() + ".2.4");
    bc_pbe_sha1_pkcs5 = new ASN1ObjectIdentifier(bc_pbe_sha1.getId() + ".1");
    bc_pbe_sha1_pkcs12 = new ASN1ObjectIdentifier(bc_pbe_sha1.getId() + ".2");
    bc_pbe_sha256_pkcs5 = new ASN1ObjectIdentifier(bc_pbe_sha256.getId() + ".1");
    bc_pbe_sha256_pkcs12 = new ASN1ObjectIdentifier(bc_pbe_sha256.getId() + ".2");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.bc.BCObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */