package org.spongycastle.jce.provider;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.spec.DHParameterSpec;
import javax.crypto.spec.DHPrivateKeySpec;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.pkcs.DHParameter;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x9.DHDomainParameters;
import org.spongycastle.asn1.x9.X9ObjectIdentifiers;
import org.spongycastle.crypto.params.DHParameters;
import org.spongycastle.crypto.params.DHPrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;

public class JCEDHPrivateKey
  implements DHPrivateKey, PKCS12BagAttributeCarrier
{
  static final long serialVersionUID = 311058815616901812L;
  private PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();
  private DHParameterSpec dhSpec;
  private PrivateKeyInfo info;
  BigInteger x;
  
  protected JCEDHPrivateKey() {}
  
  JCEDHPrivateKey(DHPrivateKey paramDHPrivateKey)
  {
    this.x = paramDHPrivateKey.getX();
    this.dhSpec = paramDHPrivateKey.getParams();
  }
  
  JCEDHPrivateKey(DHPrivateKeySpec paramDHPrivateKeySpec)
  {
    this.x = paramDHPrivateKeySpec.getX();
    this.dhSpec = new DHParameterSpec(paramDHPrivateKeySpec.getP(), paramDHPrivateKeySpec.getG());
  }
  
  JCEDHPrivateKey(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(paramPrivateKeyInfo.getAlgorithmId().getParameters());
    ASN1Integer localASN1Integer = DERInteger.getInstance(paramPrivateKeyInfo.parsePrivateKey());
    ASN1ObjectIdentifier localASN1ObjectIdentifier = paramPrivateKeyInfo.getAlgorithmId().getAlgorithm();
    this.info = paramPrivateKeyInfo;
    this.x = localASN1Integer.getValue();
    if (localASN1ObjectIdentifier.equals(PKCSObjectIdentifiers.dhKeyAgreement))
    {
      DHParameter localDHParameter = DHParameter.getInstance(localASN1Sequence);
      if (localDHParameter.getL() != null)
      {
        this.dhSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG(), localDHParameter.getL().intValue());
        return;
      }
      this.dhSpec = new DHParameterSpec(localDHParameter.getP(), localDHParameter.getG());
      return;
    }
    if (localASN1ObjectIdentifier.equals(X9ObjectIdentifiers.dhpublicnumber))
    {
      DHDomainParameters localDHDomainParameters = DHDomainParameters.getInstance(localASN1Sequence);
      this.dhSpec = new DHParameterSpec(localDHDomainParameters.getP().getValue(), localDHDomainParameters.getG().getValue());
      return;
    }
    throw new IllegalArgumentException("unknown algorithm type: " + localASN1ObjectIdentifier);
  }
  
  JCEDHPrivateKey(DHPrivateKeyParameters paramDHPrivateKeyParameters)
  {
    this.x = paramDHPrivateKeyParameters.getX();
    this.dhSpec = new DHParameterSpec(paramDHPrivateKeyParameters.getParameters().getP(), paramDHPrivateKeyParameters.getParameters().getG(), paramDHPrivateKeyParameters.getParameters().getL());
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    this.x = ((BigInteger)paramObjectInputStream.readObject());
    this.dhSpec = new DHParameterSpec((BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject(), paramObjectInputStream.readInt());
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.writeObject(getX());
    paramObjectOutputStream.writeObject(this.dhSpec.getP());
    paramObjectOutputStream.writeObject(this.dhSpec.getG());
    paramObjectOutputStream.writeInt(this.dhSpec.getL());
  }
  
  public String getAlgorithm()
  {
    return "DH";
  }
  
  public ASN1Encodable getBagAttribute(DERObjectIdentifier paramDERObjectIdentifier)
  {
    return this.attrCarrier.getBagAttribute(paramDERObjectIdentifier);
  }
  
  public Enumeration getBagAttributeKeys()
  {
    return this.attrCarrier.getBagAttributeKeys();
  }
  
  public byte[] getEncoded()
  {
    try
    {
      if (this.info != null) {
        return this.info.getEncoded("DER");
      }
      byte[] arrayOfByte = new PrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.dhKeyAgreement, new DHParameter(this.dhSpec.getP(), this.dhSpec.getG(), this.dhSpec.getL())), new DERInteger(getX())).getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public DHParameterSpec getParams()
  {
    return this.dhSpec;
  }
  
  public BigInteger getX()
  {
    return this.x;
  }
  
  public void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.attrCarrier.setBagAttribute(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.JCEDHPrivateKey
 * JD-Core Version:    0.7.0.1
 */