package org.spongycastle.jcajce.provider.asymmetric.dh;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPublicKeySpec;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x9.DHDomainParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCDHPublicKey
  implements DHPublicKey
{
  static final long serialVersionUID = -216691575254424324L;
  private transient DHParameterSpec dhSpec;
  private transient SubjectPublicKeyInfo info;
  private BigInteger y;
  
  BCDHPublicKey(BigInteger paramBigInteger, DHParameterSpec paramDHParameterSpec)
  {
    this.y = paramBigInteger;
    this.dhSpec = paramDHParameterSpec;
  }
  
  BCDHPublicKey(DHPublicKey paramDHPublicKey)
  {
    this.y = paramDHPublicKey.getY();
    this.dhSpec = paramDHPublicKey.getParams();
  }
  
  BCDHPublicKey(DHPublicKeySpec paramDHPublicKeySpec)
  {
    this.y = paramDHPublicKeySpec.getY();
    this.dhSpec = new DHParameterSpec(paramDHPublicKeySpec.getP(), paramDHPublicKeySpec.getG());
  }
  
  public BCDHPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.info = paramSubjectPublicKeyInfo;
    ASN1Sequence localASN1Sequence;
    ASN1ObjectIdentifier localASN1ObjectIdentifier;
    DHParameter localDHParameter;
    try
    {
      ASN1Integer localASN1Integer = (ASN1Integer)paramSubjectPublicKeyInfo.parsePublicKey();
      this.y = localASN1Integer.getValue();
      localASN1Sequence = ASN1Sequence.getInstance(paramSubjectPublicKeyInfo.getAlgorithm().getParameters());
      localASN1ObjectIdentifier = paramSubjectPublicKeyInfo.getAlgorithm().getAlgorithm();
      if ((!localASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement)) && (!isPKCSParam(localASN1Sequence))) {
        break label144;
      }
      localDHParameter = DHParameter.getInstance(localASN1Sequence);
      if (localDHParameter.getL() != null)
      {
        this.dhSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG(), localDHParameter.getL().intValue());
        return;
      }
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("invalid info structure in DH public key");
    }
    this.dhSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG());
    return;
    label144:
    if (localASN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber))
    {
      DHDomainParameters localDHDomainParameters = DHDomainParameters.getInstance(localASN1Sequence);
      this.dhSpec = new DHParameterSpec(localDHDomainParameters.getP().getValue(), localDHDomainParameters.getG().getValue());
      return;
    }
    throw new IllegalArgumentException("unknown algorithm type: " + localASN1ObjectIdentifier);
  }
  
  BCDHPublicKey(DHPublicKeyParameters paramDHPublicKeyParameters)
  {
    this.y = paramDHPublicKeyParameters.getY();
    this.dhSpec = new DHParameterSpec(paramDHPublicKeyParameters.getParameters().getP(), paramDHPublicKeyParameters.getParameters().getG(), paramDHPublicKeyParameters.getParameters().getL());
  }
  
  private boolean isPKCSParam(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 2) {}
    ASN1Integer localASN1Integer1;
    ASN1Integer localASN1Integer2;
    do
    {
      return true;
      if (paramASN1Sequence.size() > 3) {
        return false;
      }
      localASN1Integer1 = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(2));
      localASN1Integer2 = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    } while (localASN1Integer1.getValue().compareTo(BigInteger.valueOf(localASN1Integer2.getValue().bitLength())) <= 0);
    return false;
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    this.dhSpec = new DHParameterSpec((BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject(), paramObjectInputStream.readInt());
    this.info = null;
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    paramObjectOutputStream.writeObject(this.dhSpec.getP());
    paramObjectOutputStream.writeObject(this.dhSpec.getG());
    paramObjectOutputStream.writeInt(this.dhSpec.getL());
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof DHPublicKey)) {}
    DHPublicKey localDHPublicKey;
    do
    {
      return false;
      localDHPublicKey = (DHPublicKey)paramObject;
    } while ((!getY().equals(localDHPublicKey.getY())) || (!getParams().getG().equals(localDHPublicKey.getParams().getG())) || (!getParams().getP().equals(localDHPublicKey.getParams().getP())) || (getParams().getL() != localDHPublicKey.getParams().getL()));
    return true;
  }
  
  public String getAlgorithm()
  {
    return "DH";
  }
  
  public byte[] getEncoded()
  {
    if (this.info != null) {
      return KeyUtil.getEncodedSubjectPublicKeyInfo(this.info);
    }
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL()).toASN1Primitive()), new ASN1Integer(this.y));
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public DHParameterSpec getParams()
  {
    return this.dhSpec;
  }
  
  public BigInteger getY()
  {
    return this.y;
  }
  
  public int hashCode()
  {
    return getY().hashCode() ^ getParams().getG().hashCode() ^ getParams().getP().hashCode() ^ getParams().getL();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dh.BCDHPublicKey
 * JD-Core Version:    0.7.0.1
 */