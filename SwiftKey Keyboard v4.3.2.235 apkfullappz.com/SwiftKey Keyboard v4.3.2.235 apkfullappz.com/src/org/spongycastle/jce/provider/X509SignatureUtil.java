package org.spongycastle.jce.provider;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSASSAPSSparams;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;

class X509SignatureUtil
{
  private static final ASN1Null derNull = new DERNull();
  
  private static String getDigestAlgName(DERObjectIdentifier paramDERObjectIdentifier)
  {
    if (PKCSObjectIdentifiers.md5.equals(paramDERObjectIdentifier)) {
      return "MD5";
    }
    if (OIWObjectIdentifiers.idSHA1.equals(paramDERObjectIdentifier)) {
      return "SHA1";
    }
    if (NISTObjectIdentifiers.id_sha224.equals(paramDERObjectIdentifier)) {
      return "SHA224";
    }
    if (NISTObjectIdentifiers.id_sha256.equals(paramDERObjectIdentifier)) {
      return "SHA256";
    }
    if (NISTObjectIdentifiers.id_sha384.equals(paramDERObjectIdentifier)) {
      return "SHA384";
    }
    if (NISTObjectIdentifiers.id_sha512.equals(paramDERObjectIdentifier)) {
      return "SHA512";
    }
    if (TeleTrusTObjectIdentifiers.ripemd128.equals(paramDERObjectIdentifier)) {
      return "RIPEMD128";
    }
    if (TeleTrusTObjectIdentifiers.ripemd160.equals(paramDERObjectIdentifier)) {
      return "RIPEMD160";
    }
    if (TeleTrusTObjectIdentifiers.ripemd256.equals(paramDERObjectIdentifier)) {
      return "RIPEMD256";
    }
    if (CryptoProObjectIdentifiers.gostR3411.equals(paramDERObjectIdentifier)) {
      return "GOST3411";
    }
    return paramDERObjectIdentifier.getId();
  }
  
  static String getSignatureName(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    ASN1Encodable localASN1Encodable = paramAlgorithmIdentifier.getParameters();
    if ((localASN1Encodable != null) && (!derNull.equals(localASN1Encodable)))
    {
      if (paramAlgorithmIdentifier.getObjectId().equals(PKCSObjectIdentifiers.id_RSASSA_PSS))
      {
        RSASSAPSSparams localRSASSAPSSparams = RSASSAPSSparams.getInstance(localASN1Encodable);
        return getDigestAlgName(localRSASSAPSSparams.getHashAlgorithm().getObjectId()) + "withRSAandMGF1";
      }
      if (paramAlgorithmIdentifier.getObjectId().equals(X9ObjectIdentifiers.ecdsa_with_SHA2))
      {
        ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(localASN1Encodable);
        return getDigestAlgName((DERObjectIdentifier)localASN1Sequence.getObjectAt(0)) + "withECDSA";
      }
    }
    return paramAlgorithmIdentifier.getObjectId().getId();
  }
  
  /* Error */
  static void setSignatureParameters(java.security.Signature paramSignature, ASN1Encodable paramASN1Encodable)
    throws java.security.NoSuchAlgorithmException, java.security.SignatureException, java.security.InvalidKeyException
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnull +60 -> 61
    //   4: getstatic 15	org/spongycastle/jce/provider/X509SignatureUtil:derNull	Lorg/spongycastle/asn1/ASN1Null;
    //   7: aload_1
    //   8: invokevirtual 102	org/spongycastle/asn1/ASN1Null:equals	(Ljava/lang/Object;)Z
    //   11: ifne +50 -> 61
    //   14: aload_0
    //   15: invokevirtual 167	java/security/Signature:getAlgorithm	()Ljava/lang/String;
    //   18: aload_0
    //   19: invokevirtual 171	java/security/Signature:getProvider	()Ljava/security/Provider;
    //   22: invokestatic 176	java/security/AlgorithmParameters:getInstance	(Ljava/lang/String;Ljava/security/Provider;)Ljava/security/AlgorithmParameters;
    //   25: astore_2
    //   26: aload_2
    //   27: aload_1
    //   28: invokeinterface 182 1 0
    //   33: invokevirtual 188	org/spongycastle/asn1/ASN1Primitive:getEncoded	()[B
    //   36: invokevirtual 192	java/security/AlgorithmParameters:init	([B)V
    //   39: aload_0
    //   40: invokevirtual 167	java/security/Signature:getAlgorithm	()Ljava/lang/String;
    //   43: ldc 194
    //   45: invokevirtual 200	java/lang/String:endsWith	(Ljava/lang/String;)Z
    //   48: ifeq +13 -> 61
    //   51: aload_0
    //   52: aload_2
    //   53: ldc 202
    //   55: invokevirtual 206	java/security/AlgorithmParameters:getParameterSpec	(Ljava/lang/Class;)Ljava/security/spec/AlgorithmParameterSpec;
    //   58: invokevirtual 210	java/security/Signature:setParameter	(Ljava/security/spec/AlgorithmParameterSpec;)V
    //   61: return
    //   62: astore_3
    //   63: new 156	java/security/SignatureException
    //   66: dup
    //   67: new 117	java/lang/StringBuilder
    //   70: dup
    //   71: ldc 212
    //   73: invokespecial 215	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   76: aload_3
    //   77: invokevirtual 218	java/io/IOException:getMessage	()Ljava/lang/String;
    //   80: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   83: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   86: invokespecial 219	java/security/SignatureException:<init>	(Ljava/lang/String;)V
    //   89: athrow
    //   90: astore 4
    //   92: new 156	java/security/SignatureException
    //   95: dup
    //   96: new 117	java/lang/StringBuilder
    //   99: dup
    //   100: ldc 221
    //   102: invokespecial 215	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   105: aload 4
    //   107: invokevirtual 222	java/security/GeneralSecurityException:getMessage	()Ljava/lang/String;
    //   110: invokevirtual 128	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   113: invokevirtual 133	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   116: invokespecial 219	java/security/SignatureException:<init>	(Ljava/lang/String;)V
    //   119: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	120	0	paramSignature	java.security.Signature
    //   0	120	1	paramASN1Encodable	ASN1Encodable
    //   25	28	2	localAlgorithmParameters	java.security.AlgorithmParameters
    //   62	15	3	localIOException	java.io.IOException
    //   90	16	4	localGeneralSecurityException	java.security.GeneralSecurityException
    // Exception table:
    //   from	to	target	type
    //   26	39	62	java/io/IOException
    //   51	61	90	java/security/GeneralSecurityException
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509SignatureUtil
 * JD-Core Version:    0.7.0.1
 */