package org.spongycastle.jcajce.provider.asymmetric.ecgost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.spec.EllipticCurve;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.ECGOST3410NamedCurves;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.X962Parameters;
import org.spongycastle.asn1.x9.X9ECParameters;
import org.spongycastle.asn1.x9.X9ECPoint;
import org.spongycastle.asn1.x9.X9IntegerConverter;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.ECDomainParameters;
import org.spongycastle.crypto.params.ECPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.ec.EC5Util;
import org.spongycastle.jcajce.provider.asymmetric.ec.ECUtil;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jcajce.provider.config.ProviderConfiguration;
import org.spongycastle.jce.ECGOST3410NamedCurveTable;
import org.spongycastle.jce.interfaces.ECPointEncoder;
import org.spongycastle.jce.provider.BouncyCastleProvider;
import org.spongycastle.jce.spec.ECNamedCurveParameterSpec;
import org.spongycastle.jce.spec.ECNamedCurveSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint.F2m;
import org.spongycastle.math.ec.ECPoint.Fp;

public class BCECGOST3410PublicKey
  implements java.security.interfaces.ECPublicKey, ECPointEncoder, org.spongycastle.jce.interfaces.ECPublicKey
{
  static final long serialVersionUID = 7026240464295649314L;
  private String algorithm = "ECGOST3410";
  private transient java.security.spec.ECParameterSpec ecSpec;
  private transient GOST3410PublicKeyAlgParameters gostParams;
  private transient org.spongycastle.math.ec.ECPoint q;
  private boolean withCompression;
  
  public BCECGOST3410PublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters)
  {
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    this.ecSpec = null;
  }
  
  public BCECGOST3410PublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters, java.security.spec.ECParameterSpec paramECParameterSpec)
  {
    ECDomainParameters localECDomainParameters = paramECPublicKeyParameters.getParameters();
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    if (paramECParameterSpec == null)
    {
      this.ecSpec = createSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), localECDomainParameters);
      return;
    }
    this.ecSpec = paramECParameterSpec;
  }
  
  public BCECGOST3410PublicKey(String paramString, ECPublicKeyParameters paramECPublicKeyParameters, org.spongycastle.jce.spec.ECParameterSpec paramECParameterSpec)
  {
    ECDomainParameters localECDomainParameters = paramECPublicKeyParameters.getParameters();
    this.algorithm = paramString;
    this.q = paramECPublicKeyParameters.getQ();
    if (paramECParameterSpec == null)
    {
      this.ecSpec = createSpec(EC5Util.convertCurve(localECDomainParameters.getCurve(), localECDomainParameters.getSeed()), localECDomainParameters);
      return;
    }
    this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(paramECParameterSpec.getCurve(), paramECParameterSpec.getSeed()), paramECParameterSpec);
  }
  
  public BCECGOST3410PublicKey(java.security.interfaces.ECPublicKey paramECPublicKey)
  {
    this.algorithm = paramECPublicKey.getAlgorithm();
    this.ecSpec = paramECPublicKey.getParams();
    this.q = EC5Util.convertPoint(this.ecSpec, paramECPublicKey.getW(), false);
  }
  
  public BCECGOST3410PublicKey(java.security.spec.ECPublicKeySpec paramECPublicKeySpec)
  {
    this.ecSpec = paramECPublicKeySpec.getParams();
    this.q = EC5Util.convertPoint(this.ecSpec, paramECPublicKeySpec.getW(), false);
  }
  
  BCECGOST3410PublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    populateFromPubKeyInfo(paramSubjectPublicKeyInfo);
  }
  
  public BCECGOST3410PublicKey(BCECGOST3410PublicKey paramBCECGOST3410PublicKey)
  {
    this.q = paramBCECGOST3410PublicKey.q;
    this.ecSpec = paramBCECGOST3410PublicKey.ecSpec;
    this.withCompression = paramBCECGOST3410PublicKey.withCompression;
    this.gostParams = paramBCECGOST3410PublicKey.gostParams;
  }
  
  public BCECGOST3410PublicKey(org.spongycastle.jce.spec.ECPublicKeySpec paramECPublicKeySpec)
  {
    this.q = paramECPublicKeySpec.getQ();
    if (paramECPublicKeySpec.getParams() != null)
    {
      this.ecSpec = EC5Util.convertSpec(EC5Util.convertCurve(paramECPublicKeySpec.getParams().getCurve(), paramECPublicKeySpec.getParams().getSeed()), paramECPublicKeySpec.getParams());
      return;
    }
    if (this.q.getCurve() == null) {
      this.q = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve().createPoint(this.q.getX().toBigInteger(), this.q.getY().toBigInteger(), false);
    }
    this.ecSpec = null;
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
    if (paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm().equals(CryptoProObjectIdentifiers.gostR3410_2001))
    {
      DERBitString localDERBitString = paramSubjectPublicKeyInfo.getPublicKeyData();
      this.algorithm = "ECGOST3410";
      byte[] arrayOfByte2;
      byte[] arrayOfByte3;
      byte[] arrayOfByte4;
      try
      {
        ASN1OctetString localASN1OctetString = (ASN1OctetString)ASN1Primitive.fromByteArray(localDERBitString.getBytes());
        arrayOfByte2 = localASN1OctetString.getOctets();
        arrayOfByte3 = new byte[32];
        arrayOfByte4 = new byte[32];
        for (int i = 0; i != arrayOfByte3.length; i++) {
          arrayOfByte3[i] = arrayOfByte2[(31 - i)];
        }
        j = 0;
      }
      catch (IOException localIOException2)
      {
        throw new IllegalArgumentException("error recovering public key");
      }
      int j;
      while (j != arrayOfByte4.length)
      {
        arrayOfByte4[j] = arrayOfByte2[(63 - j)];
        j++;
      }
      this.gostParams = new GOST3410PublicKeyAlgParameters((ASN1Sequence)paramSubjectPublicKeyInfo.getAlgorithm().getParameters());
      ECNamedCurveParameterSpec localECNamedCurveParameterSpec = ECGOST3410NamedCurveTable.getParameterSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()));
      ECCurve localECCurve2 = localECNamedCurveParameterSpec.getCurve();
      EllipticCurve localEllipticCurve2 = EC5Util.convertCurve(localECCurve2, localECNamedCurveParameterSpec.getSeed());
      this.q = localECCurve2.createPoint(new BigInteger(1, arrayOfByte3), new BigInteger(1, arrayOfByte4), false);
      this.ecSpec = new ECNamedCurveSpec(ECGOST3410NamedCurves.getName(this.gostParams.getPublicKeyParamSet()), localEllipticCurve2, new java.security.spec.ECPoint(localECNamedCurveParameterSpec.getG().getX().toBigInteger(), localECNamedCurveParameterSpec.getG().getY().toBigInteger()), localECNamedCurveParameterSpec.getN(), localECNamedCurveParameterSpec.getH());
      return;
    }
    X962Parameters localX962Parameters = new X962Parameters((ASN1Primitive)paramSubjectPublicKeyInfo.getAlgorithm().getParameters());
    ECCurve localECCurve1;
    if (localX962Parameters.isNamedCurve())
    {
      ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localX962Parameters.getParameters();
      X9ECParameters localX9ECParameters2 = ECUtil.getNamedCurveByOid(localASN1ObjectIdentifier);
      localECCurve1 = localX9ECParameters2.getCurve();
      EllipticCurve localEllipticCurve1 = EC5Util.convertCurve(localECCurve1, localX9ECParameters2.getSeed());
      this.ecSpec = new ECNamedCurveSpec(ECUtil.getCurveName(localASN1ObjectIdentifier), localEllipticCurve1, new java.security.spec.ECPoint(localX9ECParameters2.getG().getX().toBigInteger(), localX9ECParameters2.getG().getY().toBigInteger()), localX9ECParameters2.getN(), localX9ECParameters2.getH());
    }
    for (;;)
    {
      byte[] arrayOfByte1 = paramSubjectPublicKeyInfo.getPublicKeyData().getBytes();
      Object localObject = new DEROctetString(arrayOfByte1);
      if ((arrayOfByte1[0] == 4) && (arrayOfByte1[1] == -2 + arrayOfByte1.length) && ((arrayOfByte1[2] == 2) || (arrayOfByte1[2] == 3)) && (new X9IntegerConverter().getByteLength(localECCurve1) >= -3 + arrayOfByte1.length)) {}
      try
      {
        localObject = (ASN1OctetString)ASN1Primitive.fromByteArray(arrayOfByte1);
        this.q = new X9ECPoint(localECCurve1, (ASN1OctetString)localObject).getPoint();
        return;
      }
      catch (IOException localIOException1)
      {
        X9ECParameters localX9ECParameters1;
        throw new IllegalArgumentException("error recovering public key");
      }
      if (localX962Parameters.isImplicitlyCA())
      {
        this.ecSpec = null;
        localECCurve1 = BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa().getCurve();
      }
      else
      {
        localX9ECParameters1 = X9ECParameters.getInstance(localX962Parameters.getParameters());
        localECCurve1 = localX9ECParameters1.getCurve();
        this.ecSpec = new java.security.spec.ECParameterSpec(EC5Util.convertCurve(localECCurve1, localX9ECParameters1.getSeed()), new java.security.spec.ECPoint(localX9ECParameters1.getG().getX().toBigInteger(), localX9ECParameters1.getG().getY().toBigInteger()), localX9ECParameters1.getN(), localX9ECParameters1.getH().intValue());
      }
    }
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    populateFromPubKeyInfo(SubjectPublicKeyInfo.getInstance(ASN1Primitive.fromByteArray((byte[])paramObjectInputStream.readObject())));
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
    return BouncyCastleProvider.CONFIGURATION.getEcImplicitlyCa();
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BCECGOST3410PublicKey)) {}
    BCECGOST3410PublicKey localBCECGOST3410PublicKey;
    do
    {
      return false;
      localBCECGOST3410PublicKey = (BCECGOST3410PublicKey)paramObject;
    } while ((!engineGetQ().equals(localBCECGOST3410PublicKey.engineGetQ())) || (!engineGetSpec().equals(localBCECGOST3410PublicKey.engineGetSpec())));
    return true;
  }
  
  public String getAlgorithm()
  {
    return this.algorithm;
  }
  
  public byte[] getEncoded()
  {
    SubjectPublicKeyInfo localSubjectPublicKeyInfo;
    if (this.algorithm.equals("ECGOST3410"))
    {
      Object localObject;
      if (this.gostParams != null) {
        localObject = this.gostParams;
      }
      for (;;)
      {
        BigInteger localBigInteger1 = this.q.getX().toBigInteger();
        BigInteger localBigInteger2 = this.q.getY().toBigInteger();
        byte[] arrayOfByte = new byte[64];
        extractBytes(arrayOfByte, 0, localBigInteger1);
        extractBytes(arrayOfByte, 32, localBigInteger2);
        localSubjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_2001, (ASN1Encodable)localObject), new DEROctetString(arrayOfByte));
        return KeyUtil.getEncodedSubjectPublicKeyInfo(localSubjectPublicKeyInfo);
        if ((this.ecSpec instanceof ECNamedCurveSpec))
        {
          localObject = new GOST3410PublicKeyAlgParameters(ECGOST3410NamedCurves.getOID(((ECNamedCurveSpec)this.ecSpec).getName()), CryptoProObjectIdentifiers.gostR3411_94_CryptoProParamSet);
        }
        else
        {
          ECCurve localECCurve2 = EC5Util.convertCurve(this.ecSpec.getCurve());
          localObject = new X962Parameters(new X9ECParameters(localECCurve2, EC5Util.convertPoint(localECCurve2, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
        }
      }
    }
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
      localSubjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(X9ObjectIdentifiers.id_ecPublicKey, localX962Parameters), localASN1OctetString.getOctets());
      break;
      if (this.ecSpec == null)
      {
        localX962Parameters = new X962Parameters(DERNull.INSTANCE);
      }
      else
      {
        ECCurve localECCurve1 = EC5Util.convertCurve(this.ecSpec.getCurve());
        localX962Parameters = new X962Parameters(new X9ECParameters(localECCurve1, EC5Util.convertPoint(localECCurve1, this.ecSpec.getGenerator(), this.withCompression), this.ecSpec.getOrder(), BigInteger.valueOf(this.ecSpec.getCofactor()), this.ecSpec.getCurve().getSeed()));
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
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ecgost.BCECGOST3410PublicKey
 * JD-Core Version:    0.7.0.1
 */