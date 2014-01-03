package org.spongycastle.operator.bc;

import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.GOST3411Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.RIPEMD256Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.operator.OperatorCreationException;

class BcUtil
{
  static Digest createDigest(AlgorithmIdentifier paramAlgorithmIdentifier)
    throws OperatorCreationException
  {
    if (paramAlgorithmIdentifier.getAlgorithm().equals(OIWObjectIdentifiers.idSHA1)) {
      return new SHA1Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(NISTObjectIdentifiers.id_sha224)) {
      return new SHA224Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(NISTObjectIdentifiers.id_sha256)) {
      return new SHA256Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(NISTObjectIdentifiers.id_sha384)) {
      return new SHA384Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(NISTObjectIdentifiers.id_sha512)) {
      return new SHA512Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.md5)) {
      return new MD5Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.md4)) {
      return new MD4Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.md2)) {
      return new MD2Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3411)) {
      return new GOST3411Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(TeleTrusTObjectIdentifiers.ripemd128)) {
      return new RIPEMD128Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(TeleTrusTObjectIdentifiers.ripemd160)) {
      return new RIPEMD160Digest();
    }
    if (paramAlgorithmIdentifier.getAlgorithm().equals(TeleTrusTObjectIdentifiers.ripemd256)) {
      return new RIPEMD256Digest();
    }
    throw new OperatorCreationException("cannot recognise digest");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.operator.bc.BcUtil
 * JD-Core Version:    0.7.0.1
 */