package org.spongycastle.tsp;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;

public abstract interface TSPAlgorithms
{
  public static final Set ALLOWED;
  public static final ASN1ObjectIdentifier GOST3411;
  public static final ASN1ObjectIdentifier MD5 = PKCSObjectIdentifiers.md5;
  public static final ASN1ObjectIdentifier RIPEMD128;
  public static final ASN1ObjectIdentifier RIPEMD160;
  public static final ASN1ObjectIdentifier RIPEMD256;
  public static final ASN1ObjectIdentifier SHA1 = OIWObjectIdentifiers.idSHA1;
  public static final ASN1ObjectIdentifier SHA224 = NISTObjectIdentifiers.id_sha224;
  public static final ASN1ObjectIdentifier SHA256 = NISTObjectIdentifiers.id_sha256;
  public static final ASN1ObjectIdentifier SHA384 = NISTObjectIdentifiers.id_sha384;
  public static final ASN1ObjectIdentifier SHA512 = NISTObjectIdentifiers.id_sha512;
  
  static
  {
    RIPEMD128 = TeleTrusTObjectIdentifiers.ripemd128;
    RIPEMD160 = TeleTrusTObjectIdentifiers.ripemd160;
    RIPEMD256 = TeleTrusTObjectIdentifiers.ripemd256;
    GOST3411 = CryptoProObjectIdentifiers.gostR3411;
    ASN1ObjectIdentifier[] arrayOfASN1ObjectIdentifier = new ASN1ObjectIdentifier[10];
    arrayOfASN1ObjectIdentifier[0] = GOST3411;
    arrayOfASN1ObjectIdentifier[1] = MD5;
    arrayOfASN1ObjectIdentifier[2] = SHA1;
    arrayOfASN1ObjectIdentifier[3] = SHA224;
    arrayOfASN1ObjectIdentifier[4] = SHA256;
    arrayOfASN1ObjectIdentifier[5] = SHA384;
    arrayOfASN1ObjectIdentifier[6] = SHA512;
    arrayOfASN1ObjectIdentifier[7] = RIPEMD128;
    arrayOfASN1ObjectIdentifier[8] = RIPEMD160;
    arrayOfASN1ObjectIdentifier[9] = RIPEMD256;
    ALLOWED = new HashSet(Arrays.asList(arrayOfASN1ObjectIdentifier));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TSPAlgorithms
 * JD-Core Version:    0.7.0.1
 */