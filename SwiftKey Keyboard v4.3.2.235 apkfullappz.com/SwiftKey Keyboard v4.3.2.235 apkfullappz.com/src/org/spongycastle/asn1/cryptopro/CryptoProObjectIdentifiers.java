package org.spongycastle.asn1.cryptopro;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface CryptoProObjectIdentifiers
{
  public static final String GOST_id = "1.2.643.2.2";
  public static final ASN1ObjectIdentifier gostR28147_cbc;
  public static final ASN1ObjectIdentifier gostR3410_2001;
  public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_A;
  public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_B;
  public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_C;
  public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_XchA;
  public static final ASN1ObjectIdentifier gostR3410_2001_CryptoPro_XchB;
  public static final ASN1ObjectIdentifier gostR3410_94;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_A;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_B;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_C;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_D;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchA;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchB;
  public static final ASN1ObjectIdentifier gostR3410_94_CryptoPro_XchC;
  public static final ASN1ObjectIdentifier gostR3411 = new ASN1ObjectIdentifier("1.2.643.2.2.9");
  public static final ASN1ObjectIdentifier gostR3411_94_CryptoProParamSet;
  public static final ASN1ObjectIdentifier gostR3411_94_with_gostR3410_2001;
  public static final ASN1ObjectIdentifier gostR3411_94_with_gostR3410_94;
  public static final ASN1ObjectIdentifier gost_ElSgDH3410_1 = new ASN1ObjectIdentifier("1.2.643.2.2.36.1");
  public static final ASN1ObjectIdentifier gost_ElSgDH3410_default;
  
  static
  {
    gostR28147_cbc = new ASN1ObjectIdentifier("1.2.643.2.2.21");
    gostR3410_94 = new ASN1ObjectIdentifier("1.2.643.2.2.20");
    gostR3410_2001 = new ASN1ObjectIdentifier("1.2.643.2.2.19");
    gostR3411_94_with_gostR3410_94 = new ASN1ObjectIdentifier("1.2.643.2.2.4");
    gostR3411_94_with_gostR3410_2001 = new ASN1ObjectIdentifier("1.2.643.2.2.3");
    gostR3411_94_CryptoProParamSet = new ASN1ObjectIdentifier("1.2.643.2.2.30.1");
    gostR3410_94_CryptoPro_A = new ASN1ObjectIdentifier("1.2.643.2.2.32.2");
    gostR3410_94_CryptoPro_B = new ASN1ObjectIdentifier("1.2.643.2.2.32.3");
    gostR3410_94_CryptoPro_C = new ASN1ObjectIdentifier("1.2.643.2.2.32.4");
    gostR3410_94_CryptoPro_D = new ASN1ObjectIdentifier("1.2.643.2.2.32.5");
    gostR3410_94_CryptoPro_XchA = new ASN1ObjectIdentifier("1.2.643.2.2.33.1");
    gostR3410_94_CryptoPro_XchB = new ASN1ObjectIdentifier("1.2.643.2.2.33.2");
    gostR3410_94_CryptoPro_XchC = new ASN1ObjectIdentifier("1.2.643.2.2.33.3");
    gostR3410_2001_CryptoPro_A = new ASN1ObjectIdentifier("1.2.643.2.2.35.1");
    gostR3410_2001_CryptoPro_B = new ASN1ObjectIdentifier("1.2.643.2.2.35.2");
    gostR3410_2001_CryptoPro_C = new ASN1ObjectIdentifier("1.2.643.2.2.35.3");
    gostR3410_2001_CryptoPro_XchA = new ASN1ObjectIdentifier("1.2.643.2.2.36.0");
    gostR3410_2001_CryptoPro_XchB = new ASN1ObjectIdentifier("1.2.643.2.2.36.1");
    gost_ElSgDH3410_default = new ASN1ObjectIdentifier("1.2.643.2.2.36.0");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */