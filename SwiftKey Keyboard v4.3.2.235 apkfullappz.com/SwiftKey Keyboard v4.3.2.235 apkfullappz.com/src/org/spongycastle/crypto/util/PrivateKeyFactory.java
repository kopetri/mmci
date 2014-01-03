package org.spongycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.sec.ECPrivateKey;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPrivateKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;

public class PrivateKeyFactory
{
  public static AsymmetricKeyParameter createKey(InputStream paramInputStream)
    throws IOException
  {
    return createKey(PrivateKeyInfo.getInstance(new ASN1InputStream(paramInputStream).readObject()));
  }
  
  public static AsymmetricKeyParameter createKey(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    AlgorithmIdentifier localAlgorithmIdentifier = paramPrivateKeyInfo.getPrivateKeyAlgorithm();
    if (localAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.rsaEncryption))
    {
      RSAPrivateKey localRSAPrivateKey = RSAPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey());
      return new RSAPrivateCrtKeyParameters(localRSAPrivateKey.getModulus(), localRSAPrivateKey.getPublicExponent(), localRSAPrivateKey.getPrivateExponent(), localRSAPrivateKey.getPrime1(), localRSAPrivateKey.getPrime2(), localRSAPrivateKey.getExponent1(), localRSAPrivateKey.getExponent2(), localRSAPrivateKey.getCoefficient());
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.dhKeyAgreement))
    {
      DHParameter localDHParameter = DHParameter.getInstance(localAlgorithmIdentifier.getParameters());
      DERInteger localDERInteger2 = (DERInteger)paramPrivateKeyInfo.parsePrivateKey();
      BigInteger localBigInteger4 = localDHParameter.getL();
      if (localBigInteger4 == null) {}
      for (int i = 0;; i = localBigInteger4.intValue())
      {
        DHParameters localDHParameters = new DHParameters(localDHParameter.getP(), localDHParameter.getG(), null, i);
        return new DHPrivateKeyParameters(localDERInteger2.getValue(), localDHParameters);
      }
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(OIWObjectIdentifiers.elGamalAlgorithm))
    {
      ElGamalParameter localElGamalParameter = new ElGamalParameter((ASN1Sequence)localAlgorithmIdentifier.getParameters());
      return new ElGamalPrivateKeyParameters(((DERInteger)paramPrivateKeyInfo.parsePrivateKey()).getValue(), new ElGamalParameters(localElGamalParameter.getP(), localElGamalParameter.getG()));
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.id_dsa))
    {
      DERInteger localDERInteger1 = (DERInteger)paramPrivateKeyInfo.parsePrivateKey();
      ASN1Encodable localASN1Encodable = localAlgorithmIdentifier.getParameters();
      DSAParameters localDSAParameters = null;
      if (localASN1Encodable != null)
      {
        DSAParameter localDSAParameter = DSAParameter.getInstance(localASN1Encodable.toASN1Primitive());
        BigInteger localBigInteger1 = localDSAParameter.getP();
        BigInteger localBigInteger2 = localDSAParameter.getQ();
        BigInteger localBigInteger3 = localDSAParameter.getG();
        localDSAParameters = new DSAParameters(localBigInteger1, localBigInteger2, localBigInteger3);
      }
      return new DSAPrivateKeyParameters(localDERInteger1.getValue(), localDSAParameters);
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.id_ecPublicKey))
    {
      X962Parameters localX962Parameters = new X962Parameters((ASN1Primitive)localAlgorithmIdentifier.getParameters());
      ASN1ObjectIdentifier localASN1ObjectIdentifier;
      if (localX962Parameters.isNamedCurve())
      {
        localASN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(localX962Parameters.getParameters());
        localX9ECParameters = X962NamedCurves.getByOID(localASN1ObjectIdentifier);
        if (localX9ECParameters == null)
        {
          localX9ECParameters = SECNamedCurves.getByOID(localASN1ObjectIdentifier);
          if (localX9ECParameters == null)
          {
            localX9ECParameters = NISTNamedCurves.getByOID(localASN1ObjectIdentifier);
            if (localX9ECParameters != null) {}
          }
        }
      }
      for (X9ECParameters localX9ECParameters = TeleTrusTNamedCurves.getByOID(localASN1ObjectIdentifier);; localX9ECParameters = X9ECParameters.getInstance(localX962Parameters.getParameters())) {
        return new ECPrivateKeyParameters(ECPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getKey(), new ECDomainParameters(localX9ECParameters.getCurve(), localX9ECParameters.getG(), localX9ECParameters.getN(), localX9ECParameters.getH(), localX9ECParameters.getSeed()));
      }
    }
    throw new RuntimeException("algorithm identifier in key not recognised");
  }
  
  public static AsymmetricKeyParameter createKey(byte[] paramArrayOfByte)
    throws IOException
  {
    return createKey(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte)));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.util.PrivateKeyFactory
 * JD-Core Version:    0.7.0.1
 */