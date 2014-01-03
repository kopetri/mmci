package org.spongycastle.asn1.gnu;

import org.spongycastle.asn1.ASN1ObjectIdentifier;

public abstract interface GNUObjectIdentifiers
{
  public static final ASN1ObjectIdentifier CRC = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.14");
  public static final ASN1ObjectIdentifier CRC32 = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.14.1");
  public static final ASN1ObjectIdentifier GNU = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.1");
  public static final ASN1ObjectIdentifier GnuPG = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.2");
  public static final ASN1ObjectIdentifier GnuRadar;
  public static final ASN1ObjectIdentifier Serpent;
  public static final ASN1ObjectIdentifier Serpent_128_CBC;
  public static final ASN1ObjectIdentifier Serpent_128_CFB;
  public static final ASN1ObjectIdentifier Serpent_128_ECB;
  public static final ASN1ObjectIdentifier Serpent_128_OFB;
  public static final ASN1ObjectIdentifier Serpent_192_CBC;
  public static final ASN1ObjectIdentifier Serpent_192_CFB;
  public static final ASN1ObjectIdentifier Serpent_192_ECB;
  public static final ASN1ObjectIdentifier Serpent_192_OFB;
  public static final ASN1ObjectIdentifier Serpent_256_CBC;
  public static final ASN1ObjectIdentifier Serpent_256_CFB;
  public static final ASN1ObjectIdentifier Serpent_256_ECB;
  public static final ASN1ObjectIdentifier Serpent_256_OFB;
  public static final ASN1ObjectIdentifier Tiger_192;
  public static final ASN1ObjectIdentifier digestAlgorithm;
  public static final ASN1ObjectIdentifier encryptionAlgorithm;
  public static final ASN1ObjectIdentifier notation = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.2.1");
  public static final ASN1ObjectIdentifier pkaAddress = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.2.1.1");
  
  static
  {
    GnuRadar = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.3");
    digestAlgorithm = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.12");
    Tiger_192 = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.12.2");
    encryptionAlgorithm = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13");
    Serpent = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2");
    Serpent_128_ECB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.1");
    Serpent_128_CBC = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.2");
    Serpent_128_OFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.3");
    Serpent_128_CFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.4");
    Serpent_192_ECB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.21");
    Serpent_192_CBC = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.22");
    Serpent_192_OFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.23");
    Serpent_192_CFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.24");
    Serpent_256_ECB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.41");
    Serpent_256_CBC = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.42");
    Serpent_256_OFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.43");
    Serpent_256_CFB = new ASN1ObjectIdentifier("1.3.6.1.4.1.11591.13.2.44");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.gnu.GNUObjectIdentifiers
 * JD-Core Version:    0.7.0.1
 */