package org.spongycastle.crypto.util;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.nist.NISTNamedCurves;
import org.spongycastle.asn1.oiw.ElGamalParameter;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.RSAPublicKey;
import org.spongycastle.asn1.sec.SECNamedCurves;
import org.spongycastle.asn1.teletrust.TeleTrusTNamedCurves;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DSAParameter;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.asn1.x9.DHDomainParameters;
import org.spongycastle.asn1.x9.DHPublicKey;
import org.spongycastle.asn1.x9.DHValidationParms;
import org.spongycastle.asn1.x9.X962NamedCurves;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.crypto.params.DHValidationParameters;
import org.spongycastle.crypto.params.DSAParameters;
import org.spongycastle.crypto.params.DSAPublicKeyParameters;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.crypto.params.ElGamalParameters;
import org.spongycastle.crypto.params.ElGamalPublicKeyParameters;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class PublicKeyFactory
{
  public static AsymmetricKeyParameter createKey(InputStream paramInputStream)
    throws IOException
  {
    return createKey(SubjectPublicKeyInfo.getInstance(new ASN1InputStream(paramInputStream).readObject()));
  }
  
  public static AsymmetricKeyParameter createKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
    throws IOException
  {
    AlgorithmIdentifier localAlgorithmIdentifier = paramSubjectPublicKeyInfo.getAlgorithm();
    if ((localAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.rsaEncryption)) || (localAlgorithmIdentifier.getAlgorithm().equals(X509ObjectIdentifiers.id_ea_rsa)))
    {
      RSAPublicKey localRSAPublicKey = RSAPublicKey.getInstance(paramSubjectPublicKeyInfo.parsePublicKey());
      return new RSAKeyParameters(false, localRSAPublicKey.getModulus(), localRSAPublicKey.getPublicExponent());
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.dhpublicnumber))
    {
      BigInteger localBigInteger5 = DHPublicKey.getInstance(paramSubjectPublicKeyInfo.parsePublicKey()).getY().getValue();
      DHDomainParameters localDHDomainParameters = DHDomainParameters.getInstance(localAlgorithmIdentifier.getParameters());
      BigInteger localBigInteger6 = localDHDomainParameters.getP().getValue();
      BigInteger localBigInteger7 = localDHDomainParameters.getG().getValue();
      BigInteger localBigInteger8 = localDHDomainParameters.getQ().getValue();
      ASN1Integer localASN1Integer = localDHDomainParameters.getJ();
      BigInteger localBigInteger9 = null;
      if (localASN1Integer != null) {
        localBigInteger9 = localDHDomainParameters.getJ().getValue();
      }
      DHValidationParms localDHValidationParms = localDHDomainParameters.getValidationParms();
      DHValidationParameters localDHValidationParameters = null;
      if (localDHValidationParms != null) {
        localDHValidationParameters = new DHValidationParameters(localDHValidationParms.getSeed().getBytes(), localDHValidationParms.getPgenCounter().getValue().intValue());
      }
      return new DHPublicKeyParameters(localBigInteger5, new DHParameters(localBigInteger6, localBigInteger7, localBigInteger8, localBigInteger9, localDHValidationParameters));
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(PKCSObjectIdentifiers.dhKeyAgreement))
    {
      DHParameter localDHParameter = DHParameter.getInstance(localAlgorithmIdentifier.getParameters());
      DERInteger localDERInteger2 = (DERInteger)paramSubjectPublicKeyInfo.parsePublicKey();
      BigInteger localBigInteger4 = localDHParameter.getL();
      if (localBigInteger4 == null) {}
      for (int i = 0;; i = localBigInteger4.intValue())
      {
        DHParameters localDHParameters = new DHParameters(localDHParameter.getP(), localDHParameter.getG(), null, i);
        return new DHPublicKeyParameters(localDERInteger2.getValue(), localDHParameters);
      }
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(OIWObjectIdentifiers.elGamalAlgorithm))
    {
      ElGamalParameter localElGamalParameter = new ElGamalParameter((ASN1Sequence)localAlgorithmIdentifier.getParameters());
      return new ElGamalPublicKeyParameters(((DERInteger)paramSubjectPublicKeyInfo.parsePublicKey()).getValue(), new ElGamalParameters(localElGamalParameter.getP(), localElGamalParameter.getG()));
    }
    if ((localAlgorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.id_dsa)) || (localAlgorithmIdentifier.getAlgorithm().equals(OIWObjectIdentifiers.dsaWithSHA1)))
    {
      DERInteger localDERInteger1 = (DERInteger)paramSubjectPublicKeyInfo.parsePublicKey();
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
      return new DSAPublicKeyParameters(localDERInteger1.getValue(), localDSAParameters);
    }
    if (localAlgorithmIdentifier.getAlgorithm().equals(X9ObjectIdentifiers.id_ecPublicKey))
    {
      X962Parameters localX962Parameters = new X962Parameters((ASN1Primitive)localAlgorithmIdentifier.getParameters());
      ASN1ObjectIdentifier localASN1ObjectIdentifier;
      if (localX962Parameters.isNamedCurve())
      {
        localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localX962Parameters.getParameters();
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
      for (X9ECParameters localX9ECParameters = TeleTrusTNamedCurves.getByOID(localASN1ObjectIdentifier);; localX9ECParameters = X9ECParameters.getInstance(localX962Parameters.getParameters()))
      {
        DEROctetString localDEROctetString = new DEROctetString(paramSubjectPublicKeyInfo.getPublicKeyData().getBytes());
        X9ECPoint localX9ECPoint = new X9ECPoint(localX9ECParameters.getCurve(), localDEROctetString);
        ECDomainParameters localECDomainParameters = new ECDomainParameters(localX9ECParameters.getCurve(), localX9ECParameters.getG(), localX9ECParameters.getN(), localX9ECParameters.getH(), localX9ECParameters.getSeed());
        return new ECPublicKeyParameters(localX9ECPoint.getPoint(), localECDomainParameters);
      }
    }
    throw new RuntimeException("algorithm identifier in key not recognised");
  }
  
  public static AsymmetricKeyParameter createKey(byte[] paramArrayOfByte)
    throws IOException
  {
    return createKey(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte)));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.util.PublicKeyFactory
 * JD-Core Version:    0.7.0.1
 */