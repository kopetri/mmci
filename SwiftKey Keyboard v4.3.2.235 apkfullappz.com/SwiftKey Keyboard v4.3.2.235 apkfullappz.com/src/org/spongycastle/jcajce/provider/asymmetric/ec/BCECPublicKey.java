package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint.F2m;
import org.spongycastle.math.ec.ECPoint.Fp;

public class BCECPublicKey
  implements java.security.interfaces.ECPublicKey, ECPointEncoder, org.spongycastle.jce.interfaces.ECPublicKey
{
  static final long serialVersionUID = 2422789860422731812L;
  private String algorithm = "EC";
  private transient ProviderConfiguration configuration;
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient org.spongycastle.math.ec.ECPoint q;
  private boolean withCompression;
  
  public BCECPublicKey(String paramString, java.security.spec.ECPublicKeySpec paramECPublicKeySpec, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramString;
    this.ecSpec = paramECPublicKeySpec.getParams();
    this.q = EC5Util.convertPoint(this.ecSpec, paramECPublicKeySpec.getW(), false);
    this.configuration = paramProviderConfiguration;
  }
  
  BCECPublicKey(String paramString, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramString;
    this.configuration = paramProviderConfiguration;
    populateFromPubKeyInfo(paramSubjectPublicKeyInfo);
  }
  
  public BCECPublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters, java.security.spec.ECParameterSpec paramECParameterSpec, ProviderConfiguration paramProviderConfiguration)
  {
    ECDomainParameters localECDomainParameters = paramECPublicKeyParameters.getParameters();
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    if (paramECParameterSpec == null) {}
    for (this.ecSpec = createSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), localECDomainParameters);; this.ecSpec = paramECParameterSpec)
    {
      this.configuration = paramProviderConfiguration;
      return;
    }
  }
  
  public BCECPublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    this.ecSpec = null;
    this.configuration = paramProviderConfiguration;
  }
  
  public BCECPublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters, org.spongycastle.jce.spec.ECParameterSpec paramECParameterSpec, ProviderConfiguration paramProviderConfiguration)
  {
    ECDomainParameters localECDomainParameters = paramECPublicKeyParameters.getParameters();
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    if (paramECParameterSpec == null) {}
    for (this.ecSpec = createSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), localECDomainParameters);; this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(paramECParameterSpec.getCurve(), paramECParameterSpec.getSeed()), paramECParameterSpec))
    {
      this.configuration = paramProviderConfiguration;
      return;
    }
  }
  
  public BCECPublicKey(String paramString, BCECPublicKey paramBCECPublicKey)
  {
    this.algorithm = paramString;
    this.q = paramBCECPublicKey.q;
    this.ecSpec = paramBCECPublicKey.ecSpec;
    this.withCompression = paramBCECPublicKey.withCompression;
    this.configuration = paramBCECPublicKey.configuration;
  }
  
  public BCECPublicKey(String paramString, org.spongycastle.jce.spec.ECPublicKeySpec paramECPublicKeySpec, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramString;
    this.q = paramECPublicKeySpec.getQ();
    if (paramECPublicKeySpec.getParams() != null) {}
    for (this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(paramECPublicKeySpec.getParams().getCurve(), paramECPublicKeySpec.getParams().getSeed()), paramECPublicKeySpec.getParams());; this.ecSpec = null)
    {
      this.configuration = paramProviderConfiguration;
      return;
      if (this.q.getCurve() == null) {
        this.q = paramProviderConfiguration.getEcImplicitlyCa().getCurve().createPoint(this.q.getX().toBigInteger(), this.q.getY().toBigInteger(), false);
      }
    }
  }
  
  public BCECPublicKey(java.security.interfaces.ECPublicKey paramECPublicKey, ProviderConfiguration paramProviderConfiguration)
  {
    this.algorithm = paramECPublicKey.getAlgorithm();
    this.ecSpec = paramECPublicKey.getParams();
    this.q = EC5Util.convertPoint(this.ecSpec, paramECPublicKey.getW(), false);
  }
  
  private java.security.spec.ECParameterSpec createSpec(EllipticCurve paramEllipticCurve, ECDomainParameters paramECDomainParameters)
  {
    return new java.security.spec.ECParameterSpec(paramEllipticCurve, new java.security.spec.ECPoint(paramECDomainParameters.getG().getX().toBigInteger(), paramECDomainParameters.getG().getY().toBigInteger()), paramECDomainParameters.getN(), paramECDomainParameters.getH().intValue());
  }
  
  private void extractBytes(byte[] paramArrayOfByte, int paramInt, BigInteger paramBigInteger)
  {
    Object localObject = paramBigInteger.toByteArray();
    if (localObject.length < 32)
    {
      byte[] arrayOfByte = new byte[32];
      System.arraycopy(localObject, 0, arrayOfByte, arrayOfByte.length - localObject.length, localObject.length);
      localObject = arrayOfByte;
    }
    for (int i = 0; i != 32; i++) {
      paramArrayOfByte[(paramInt + i)] = localObject[(-1 + localObject.length - i)];
    }
  }
  
  private void populateFromPubKeyInfo(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    X962Parameters localX962Parameters = new X962Parameters((ASN1Primitive)paramSubjectPublicKeyInfo.getAlgorithm().getParameters());
    ECCurve localECCurve;
    if (localX962Parameters.isNamedCurve())
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localX962Parameters.getParameters();
      X9ECParameters localX9ECParameters2 = ECUtil.getNamedCurveByOid(localASN1ObjectIdentifier);
      localECCurve = localX9ECParameters2.getCurve();
      EllipticCurve localEllipticCurve = EC5Util.convertCurve(localECCurve, localX9ECParameters2.getSeed());
      this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(localASN1ObjectIdentifier), localEllipticCurve, new java.security.spec.ECPoint(localX9ECParameters2.getG().getX().toBigInteger(), localX9ECParameters2.getG().getY().toBigInteger()), localX9ECParameters2.getN(), localX9ECParameters2.getH());
    }
    for (;;)
    {
      byte[] arrayOfByte = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
      Object localObject = new DEROctetString(arrayOfByte);
      if ((arrayOfByte[0] == 4) && (arrayOfByte[1] == -2 + arrayOfByte.length) && ((arrayOfByte[2] == 2) || (arrayOfByte[2] == 3)) && (new X9IntegerConverter().getByteLength(localECCurve) >= -3 + arrayOfByte.length)) {}
      try
      {
        localObject = (ASN1OctetString)ASN1Primitive.fromByteArray(arrayOfByte);
        this.q = new X9ECPoint(localECCurve, (ASN1OctetString)localObject).getPoint();
        return;
      }
      catch (IOException localIOException)
      {
        X9ECParameters localX9ECParameters1;
        throw new IllegalArgumentException("error recovering public key");
      }
      if (localX962Parameters.isImplicitlyCA())
      {
        this.ecSpec = null;
        localECCurve = this.configuration.getEcImplicitlyCa().getCurve();
      }
      else
      {
        localX9ECParameters1 = X9ECParameters.getInstance(localX962Parameters.getParameters());
        localECCurve = localX9ECParameters1.getCurve();
        this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(localECCurve, localX9ECParameters1.getSeed()), new java.security.spec.ECPoint(localX9ECParameters1.getG().getX().toBigInteger(), localX9ECParameters1.getG().getY().toBigInteger()), localX9ECParameters1.getN(), localX9ECParameters1.getH().intValue());
      }
    }
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[])paramObjectInputStream.readObject())));
    this.configuration = BouncyCastleProvider.CONFIGURATION;
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(getEncoded());
  }
  
  public org.spongycastle.math.ec.ECPoint engineGetQ()
  {
    return this.q;
  }
  
  org.spongycastle.jce.spec.ECParameterSpec engineGetSpec()
  {
    if (this.ecSpec != null) {
      return EC5Util.convertSpec(this.ecSpec, this.withCompression);
    }
    return this.configuration.getEcImplicitlyCa();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BCECPublicKey)) {}
    BCECPublicKey localBCECPublicKey;
    do
    {
      return false;
      localBCECPublicKey = (BCECPublicKey)paramObject;
    } while ((!engineGetQ().equals(localBCECPublicKey.engineGetQ())) || (!engineGetSpec().equals(localBCECPublicKey.engineGetSpec())));
    return true;
  }
  
  public String getAlgorithm()
  {
    return this.algorithm;
  }
  
  public byte[] getEncoded()
  {
    X962Parameters localX962Parameters;
    if ((this.ecSpec instanceof ECNamedCurveSpec))
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = ECUtil.getNamedCurveOid(((ECNamedCurveSpec)this.ecSpec).getName());
      if (localASN1ObjectIdentifier == null) {
        localASN1ObjectIdentifier = new ASN1ObjectIdentifier(((ECNamedCurveSpec)this.ecSpec).getName());
      }
      localX962Parameters = new X962Parameters(localASN1ObjectIdentifier);
    }
    for (;;)
    {
      ASN1OctetString localASN1OctetString = (ASN1OctetString)new X9ECPoint(engineGetQ().getCurve().createPoint(getQ().getX().toBigInteger(), getQ().getY().toBigInteger(), this.withCompression)).toASN1Primitive();
      return KeyUtil.getEncodedSubjectPublicKeyInfo(new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, localX962Parameters), localASN1OctetString.getOctets()));
      if (this.ecSpec == null)
      {
        localX962Parameters = new X962Parameters(DERNull.INSTANCE);
      }
      else
      {
        ECCurve localECCurve = EC5Util.convertCurve(this.ecSpec.getCurve());
        localX962Parameters = new X962Parameters(new X9ECParameters(localECCurve, EC5Util.convertPoint(localECCurve, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
      }
    }
  }
  
  public String getFormat()
  {
    return "X.509";
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
  
  public org.spongycastle.math.ec.ECPoint getQ()
  {
    if (this.ecSpec == null)
    {
      if ((this.q instanceof ECPoint.Fp)) {
        return new ECPoint.Fp(null, this.q.getX(), this.q.getY());
      }
      return new ECPoint.F2m(null, this.q.getX(), this.q.getY());
    }
    return this.q;
  }
  
  public java.security.spec.ECPoint getW()
  {
    return new java.security.spec.ECPoint(this.q.getX().toBigInteger(), this.q.getY().toBigInteger());
  }
  
  public int hashCode()
  {
    return engineGetQ().hashCode() ^ engineGetSpec().hashCode();
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
    localStringBuffer.append("EC Public Key").append(str);
    localStringBuffer.append("            X: ").append(this.q.getX().toBigInteger().toString(16)).append(str);
    localStringBuffer.append("            Y: ").append(this.q.getY().toBigInteger().toString(16)).append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.BCECPublicKey
 * JD-Core Version:    0.7.0.1
 */