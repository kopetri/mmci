package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.GOST3410PrivateKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.PKCS12BagAttributeCarrierImpl;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PrivateKey;
import org.spongycastle.jce.interfaces.PKCS12BagAttributeCarrier;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PrivateKeySpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;

public class BCGOST3410PrivateKey
  implements GOST3410PrivateKey, PKCS12BagAttributeCarrier
{
  static final long serialVersionUID = 8581661527592305464L;
  private transient PKCS12BagAttributeCarrier attrCarrier = new PKCS12BagAttributeCarrierImpl();
  private transient GOST3410Params gost3410Spec;
  private BigInteger x;
  
  protected BCGOST3410PrivateKey() {}
  
  BCGOST3410PrivateKey(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    GOST3410PublicKeyAlgParameters localGOST3410PublicKeyAlgParameters = new GOST3410PublicKeyAlgParameters((ASN1Sequence)paramPrivateKeyInfo.getAlgorithmId().getParameters());
    byte[] arrayOfByte1 = ASN1OctetString.getInstance(paramPrivateKeyInfo.parsePrivateKey()).getOctets();
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
    for (int i = 0; i != arrayOfByte1.length; i++) {
      arrayOfByte2[i] = arrayOfByte1[(-1 + arrayOfByte1.length - i)];
    }
    this.x = new BigInteger(1, arrayOfByte2);
    this.gost3410Spec = GOST3410ParameterSpec.fromPublicKeyAlg(localGOST3410PublicKeyAlgParameters);
  }
  
  BCGOST3410PrivateKey(GOST3410PrivateKeyParameters paramGOST3410PrivateKeyParameters, GOST3410ParameterSpec paramGOST3410ParameterSpec)
  {
    this.x = paramGOST3410PrivateKeyParameters.getX();
    this.gost3410Spec = paramGOST3410ParameterSpec;
    if (paramGOST3410ParameterSpec == null) {
      throw new IllegalArgumentException("spec is null");
    }
  }
  
  BCGOST3410PrivateKey(GOST3410PrivateKey paramGOST3410PrivateKey)
  {
    this.x = paramGOST3410PrivateKey.getX();
    this.gost3410Spec = paramGOST3410PrivateKey.getParameters();
  }
  
  BCGOST3410PrivateKey(GOST3410PrivateKeySpec paramGOST3410PrivateKeySpec)
  {
    this.x = paramGOST3410PrivateKeySpec.getX();
    this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(paramGOST3410PrivateKeySpec.getP(), paramGOST3410PrivateKeySpec.getQ(), paramGOST3410PrivateKeySpec.getA()));
  }
  
  private boolean compareObj(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if (paramObject1 == null) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    String str = (String)paramObjectInputStream.readObject();
    if (str != null) {
      this.gost3410Spec = new GOST3410ParameterSpec(str, (String)paramObjectInputStream.readObject(), (String)paramObjectInputStream.readObject());
    }
    for (;;)
    {
      this.attrCarrier = new PKCS12BagAttributeCarrierImpl();
      return;
      this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec((BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject()));
      paramObjectInputStream.readObject();
      paramObjectInputStream.readObject();
    }
  }
  
  private void writeObject(ObjectOutputStream paramObjectOutputStream)
    throws IOException
  {
    paramObjectOutputStream.defaultWriteObject();
    if (this.gost3410Spec.getPublicKeyParamSetOID() != null)
    {
      paramObjectOutputStream.writeObject(this.gost3410Spec.getPublicKeyParamSetOID());
      paramObjectOutputStream.writeObject(this.gost3410Spec.getDigestParamSetOID());
      paramObjectOutputStream.writeObject(this.gost3410Spec.getEncryptionParamSetOID());
      return;
    }
    paramObjectOutputStream.writeObject(null);
    paramObjectOutputStream.writeObject(this.gost3410Spec.getPublicKeyParameters().getP());
    paramObjectOutputStream.writeObject(this.gost3410Spec.getPublicKeyParameters().getQ());
    paramObjectOutputStream.writeObject(this.gost3410Spec.getPublicKeyParameters().getA());
    paramObjectOutputStream.writeObject(this.gost3410Spec.getDigestParamSetOID());
    paramObjectOutputStream.writeObject(this.gost3410Spec.getEncryptionParamSetOID());
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof GOST3410PrivateKey)) {}
    GOST3410PrivateKey localGOST3410PrivateKey;
    do
    {
      return false;
      localGOST3410PrivateKey = (GOST3410PrivateKey)paramObject;
    } while ((!getX().equals(localGOST3410PrivateKey.getX())) || (!getParameters().getPublicKeyParameters().equals(localGOST3410PrivateKey.getParameters().getPublicKeyParameters())) || (!getParameters().getDigestParamSetOID().equals(localGOST3410PrivateKey.getParameters().getDigestParamSetOID())) || (!compareObj(getParameters().getEncryptionParamSetOID(), localGOST3410PrivateKey.getParameters().getEncryptionParamSetOID())));
    return true;
  }
  
  public String getAlgorithm()
  {
    return "GOST3410";
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
    byte[] arrayOfByte1 = getX().toByteArray();
    if (arrayOfByte1[0] == 0) {}
    for (byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];; arrayOfByte2 = new byte[arrayOfByte1.length]) {
      for (int i = 0; i != arrayOfByte2.length; i++) {
        arrayOfByte2[i] = arrayOfByte1[(-1 + arrayOfByte1.length - i)];
      }
    }
    try
    {
      if ((this.gost3410Spec instanceof GOST3410ParameterSpec)) {}
      for (PrivateKeyInfo localPrivateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.gost3410Spec.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.gost3410Spec.getDigestParamSetOID()))), new DEROctetString(arrayOfByte2));; localPrivateKeyInfo = new PrivateKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94), new DEROctetString(arrayOfByte2))) {
        return localPrivateKeyInfo.getEncoded("DER");
      }
      return null;
    }
    catch (IOException localIOException) {}
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public GOST3410Params getParameters()
  {
    return this.gost3410Spec;
  }
  
  public BigInteger getX()
  {
    return this.x;
  }
  
  public int hashCode()
  {
    return getX().hashCode() ^ this.gost3410Spec.hashCode();
  }
  
  public void setBagAttribute(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.attrCarrier.setBagAttribute(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.gost.BCGOST3410PrivateKey
 * JD-Core Version:    0.7.0.1
 */