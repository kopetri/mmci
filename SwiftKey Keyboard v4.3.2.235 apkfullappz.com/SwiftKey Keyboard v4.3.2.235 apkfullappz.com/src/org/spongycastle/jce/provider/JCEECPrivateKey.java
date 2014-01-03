package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.sec.ECPrivateKeyStructure;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.ec.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;

public class JCEECPrivateKey
  implements java.security.interfaces.ECPrivateKey, ECPointEncoder, org.spongycastle.jce.interfaces.ECPrivateKey, PKCS12BagAttributeCarrier
{
  private String algorithm = "EC";
  private PKCS12BagAttributeCarrierImpl attrCarrier = new PKCS12BagAttributeCarrierImpl();
  private BigInteger d;
  private java.security.spec.ECParameterSpec ecSpec;
  private DERBitString publicKey;
  private boolean withCompression;
  
  protected JCEECPrivateKey() {}
  
  public JCEECPrivateKey(String paramString, java.security.spec.ECPrivateKeySpec paramECPrivateKeySpec)
  {
    this.algorithm = paramString;
    this.d = paramECPrivateKeySpec.getS();
    this.ecSpec = paramECPrivateKeySpec.getParams();
  }
  
  public JCEECPrivateKey(String paramString, ECPrivateKeyParameters paramECPrivateKeyParameters)
  {
    this.algorithm = paramString;
    this.d = paramECPrivateKeyParameters.getD();
    this.ecSpec = null;
  }
  
  public JCEECPrivateKey(String paramString, ECPrivateKeyParameters paramECPrivateKeyParameters, JCEECPublicKey paramJCEECPublicKey, java.security.spec.ECParameterSpec paramECParameterSpec)
  {
    ECDomainParameters localECDomainParameters = paramECPrivateKeyParameters.getParameters();
    this.algorithm = paramString;
    this.d = paramECPrivateKeyParameters.getD();
    if (paramECParameterSpec == null) {}
    for (this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), new java.security.spec.ECPoint(localECDomainParameters.getG().getX().toBigInteger(), localECDomainParameters.getG().getY().toBigInteger()), localECDomainParameters.getN(), localECDomainParameters.getH().intValue());; this.ecSpec = paramECParameterSpec)
    {
      this.publicKey = getPublicKeyDetails(paramJCEECPublicKey);
      return;
    }
  }
  
  public JCEECPrivateKey(String paramString, ECPrivateKeyParameters paramECPrivateKeyParameters, JCEECPublicKey paramJCEECPublicKey, org.spongycastle.jce.spec.ECParameterSpec paramECParameterSpec)
  {
    ECDomainParameters localECDomainParameters = paramECPrivateKeyParameters.getParameters();
    this.algorithm = paramString;
    this.d = paramECPrivateKeyParameters.getD();
    if (paramECParameterSpec == null) {}
    for (this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), new java.security.spec.ECPoint(localECDomainParameters.getG().getX().toBigInteger(), localECDomainParameters.getG().getY().toBigInteger()), localECDomainParameters.getN(), localECDomainParameters.getH().intValue());; this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(paramECParameterSpec.getCurve(), paramECParameterSpec.getSeed()), new java.security.spec.ECPoint(paramECParameterSpec.getG().getX().toBigInteger(), paramECParameterSpec.getG().getY().toBigInteger()), paramECParameterSpec.getN(), paramECParameterSpec.getH().intValue()))
    {
      this.publicKey = getPublicKeyDetails(paramJCEECPublicKey);
      return;
    }
  }
  
  public JCEECPrivateKey(String paramString, JCEECPrivateKey paramJCEECPrivateKey)
  {
    this.algorithm = paramString;
    this.d = paramJCEECPrivateKey.d;
    this.ecSpec = paramJCEECPrivateKey.ecSpec;
    this.withCompression = paramJCEECPrivateKey.withCompression;
    this.attrCarrier = paramJCEECPrivateKey.attrCarrier;
    this.publicKey = paramJCEECPrivateKey.publicKey;
  }
  
  public JCEECPrivateKey(String paramString, org.spongycastle.jce.spec.ECPrivateKeySpec paramECPrivateKeySpec)
  {
    this.algorithm = paramString;
    this.d = paramECPrivateKeySpec.getD();
    if (paramECPrivateKeySpec.getParams() != null)
    {
      this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(paramECPrivateKeySpec.getParams().getCurve(), paramECPrivateKeySpec.getParams().getSeed()), paramECPrivateKeySpec.getParams());
      return;
    }
    this.ecSpec = null;
  }
  
  public JCEECPrivateKey(java.security.interfaces.ECPrivateKey paramECPrivateKey)
  {
    this.d = paramECPrivateKey.getS();
    this.algorithm = paramECPrivateKey.getAlgorithm();
    this.ecSpec = paramECPrivateKey.getParams();
  }
  
  JCEECPrivateKey(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    populateFromPrivKeyInfo(paramPrivateKeyInfo);
  }
  
  private DERBitString getPublicKeyDetails(JCEECPublicKey paramJCEECPublicKey)
  {
    try
    {
      DERBitString localDERBitString = SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray(paramJCEECPublicKey.getEncoded())).getPublicKeyData();
      return localDERBitString;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  private void populateFromPrivKeyInfo(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    X962Parameters localX962Parameters = new X962Parameters((ASN1Primitive)paramPrivateKeyInfo.getPrivateKeyAlgorithm().getParameters());
    ASN1ObjectIdentifier localASN1ObjectIdentifier;
    X9ECParameters localX9ECParameters2;
    if (localX962Parameters.isNamedCurve())
    {
      localASN1ObjectIdentifier = ASN1ObjectIdentifier.getInstance(localX962Parameters.getParameters());
      localX9ECParameters2 = ECUtil.getNamedCurveByOid(localASN1ObjectIdentifier);
      if (localX9ECParameters2 == null)
      {
        ECDomainParameters localECDomainParameters = ECGOST3410NamedCurves.getByOID(localASN1ObjectIdentifier);
        EllipticCurve localEllipticCurve2 = EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed());
        this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(localASN1ObjectIdentifier), localEllipticCurve2, new java.security.spec.ECPoint(localECDomainParameters.getG().getX().toBigInteger(), localECDomainParameters.getG().getY().toBigInteger()), localECDomainParameters.getN(), localECDomainParameters.getH());
      }
    }
    ASN1Encodable localASN1Encodable;
    for (;;)
    {
      localASN1Encodable = paramPrivateKeyInfo.parsePrivateKey();
      if (!(localASN1Encodable instanceof DERInteger)) {
        break;
      }
      this.d = DERInteger.getInstance(localASN1Encodable).getValue();
      return;
      EllipticCurve localEllipticCurve1 = EC5Util.convertCurve(localX9ECParameters2.getCurve(), localX9ECParameters2.getSeed());
      this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(localASN1ObjectIdentifier), localEllipticCurve1, new java.security.spec.ECPoint(localX9ECParameters2.getG().getX().toBigInteger(), localX9ECParameters2.getG().getY().toBigInteger()), localX9ECParameters2.getN(), localX9ECParameters2.getH());
      continue;
      if (localX962Parameters.isImplicitlyCA())
      {
        this.ecSpec = null;
      }
      else
      {
        X9ECParameters localX9ECParameters1 = X9ECParameters.getInstance(localX962Parameters.getParameters());
        this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(localX9ECParameters1.getCurve(), localX9ECParameters1.getSeed()), new java.security.spec.ECPoint(localX9ECParameters1.getG().getX().toBigInteger(), localX9ECParameters1.getG().getY().toBigInteger()), localX9ECParameters1.getN(), localX9ECParameters1.getH().intValue());
      }
    }
    ECPrivateKeyStructure localECPrivateKeyStructure = new ECPrivateKeyStructure((ASN1Sequence)localASN1Encodable);
    this.d = localECPrivateKeyStructure.getKey();
    this.publicKey = localECPrivateKeyStructure.getPublicKey();
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    populateFromPrivKeyInfo(PrivateKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[])paramObjectInputStream.readObject())));
    this.algorithm = ((String)paramObjectInputStream.readObject());
    this.withCompression = paramObjectInputStream.readBoolean();
    this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
    this.attrCarrier.readObject(paramObjectInputStream);
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeObject(getEncoded());
    paramObjectOutputStream.writeObject(this.algorithm);
    paramObjectOutputStream.writeBoolean(this.withCompression);
    this.attrCarrier.writeObject(paramObjectOutputStream);
  }
  
  org.spongycastle.jce.spec.ECParameterSpec engineGetSpec()
  {
    if (this.ecSpec != null) {
      return EC5Util.convertSpec(this.ecSpec, this.withCompression);
    }
    return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof JCEECPrivateKey)) {}
    JCEECPrivateKey localJCEECPrivateKey;
    do
    {
      return false;
      localJCEECPrivateKey = (JCEECPrivateKey)paramObject;
    } while ((!getD().equals(localJCEECPrivateKey.getD())) || (!engineGetSpec().equals(localJCEECPrivateKey.engineGetSpec())));
    return true;
  }
  
  public String getAlgorithm()
  {
    return this.algorithm;
  }
  
  public ASN1Encodable getBagAttribute(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return this.attrCarrier.getBagAttribute(paramDERObjectIdentifier);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return this.attrCarrier.getBagAttributeKeys();
  }
  
  public BigInteger getD()
  {
    return this.d;
  }
  
  public byte[] getEncoded()
  {
    X962Parameters localX962Parameters;
    if ((this.ecSpec instanceof ECNamedCurveSpec))
    {
      Object localObject = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
      if (localObject == null) {
        localObject = new DERObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
      }
      localX962Parameters = new X962Parameters((ASN1Primitive)localObject);
    }
    for (;;)
    {
      ECPrivateKeyStructure localECPrivateKeyStructure;
      if (this.publicKey != null) {
        localECPrivateKeyStructure = new ECPrivateKeyStructure(getS(), this.publicKey, localX962Parameters);
      }
      try
      {
        label83:
        if (this.algorithm.equals("ECGOST3410")) {}
        for (PrivateKeyInfo localPrivateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, localX962Parameters.toASN1Primitive()), localECPrivateKeyStructure.toASN1Primitive());; localPrivateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, localX962Parameters.toASN1Primitive()), localECPrivateKeyStructure.toASN1Primitive()))
        {
          byte[] arrayOfByte = localPrivateKeyInfo.getEncoded("DER");
          return arrayOfByte;
          if (this.ecSpec == null)
          {
            localX962Parameters = new X962Parameters(DERNull.INSTANCE);
            break;
          }
          ECCurve localECCurve = EC5Util.convertCurve(this.ecSpec.getCurve());
          localX962Parameters = new X962Parameters(new X9ECParameters(localECCurve, EC5Util.convertPoint(localECCurve, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
          break;
          localECPrivateKeyStructure = new ECPrivateKeyStructure(getS(), localX962Parameters);
          break label83;
        }
        return null;
      }
      catch (IOException localIOException) {}
    }
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public org.spongycastle.jce.spec.ECParameterSpec getParameters()
  {
    if (this.ecSpec == null) {
      return null;
    }
    return EC5Util.convertSpec(this.ecSpec, this.withCompression);
  }
  
  public java.security.spec.ECParameterSpec getParams()
  {
    return this.ecSpec;
  }
  
  public BigInteger getS()
  {
    return this.d;
  }
  
  public int hashCode()
  {
    return getD().hashCode() ^ engineGetSpec().hashCode();
  }
  
  public void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.attrCarrier.setBagAttribute(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
  
  public void setPointFormat(String paramString)
  {
    if (!"UNCOMPRESSED".equalsIgnoreCase(paramString)) {}
    for (boolean bool = true;; bool = false)
    {
      this.withCompression = bool;
      return;
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("EC Private Key").append(str);
    localStringBuffer.append("             S: ").append(this.d.toString(16)).append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JCEECPrivateKey
 * JD-Core Version:    0.7.0.1
 */