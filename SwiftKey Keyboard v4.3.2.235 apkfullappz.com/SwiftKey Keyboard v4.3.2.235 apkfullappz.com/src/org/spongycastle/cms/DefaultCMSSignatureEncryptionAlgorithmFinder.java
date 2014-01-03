package org.spongycastle.cms;

import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class DefaultCMSSignatureEncryptionAlgorithmFinder
  implements CMSSignatureEncryptionAlgorithmFinder
{
  private static final Set RSA_PKCS1d5;
  
  static
  {
    HashSet localHashSet = new HashSet();
    RSA_PKCS1d5 = localHashSet;
    localHashSet.add(PKCSObjectIdentifiers.md2WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.md4WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.md5WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha1WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha224WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha256WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha384WithRSAEncryption);
    RSA_PKCS1d5.add(PKCSObjectIdentifiers.sha512WithRSAEncryption);
    RSA_PKCS1d5.add(OIWObjectIdentifiers.md4WithRSAEncryption);
    RSA_PKCS1d5.add(OIWObjectIdentifiers.md4WithRSA);
    RSA_PKCS1d5.add(OIWObjectIdentifiers.md5WithRSA);
    RSA_PKCS1d5.add(OIWObjectIdentifiers.sha1WithRSA);
    RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128);
    RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160);
    RSA_PKCS1d5.add(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256);
  }
  
  public AlgorithmIdentifier findEncryptionAlgorithm(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    if (RSA_PKCS1d5.contains(paramAlgorithmIdentifier.getAlgorithm())) {
      paramAlgorithmIdentifier = new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, DERNull.INSTANCE);
    }
    return paramAlgorithmIdentifier;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.DefaultCMSSignatureEncryptionAlgorithmFinder
 * JD-Core Version:    0.7.0.1
 */