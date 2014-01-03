package org.spongycastle.jcajce.provider.asymmetric.gost;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.spongycastle.asn1.cryptopro.GOST3410PublicKeyAlgParameters;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.GOST3410PublicKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;
import org.spongycastle.jce.interfaces.GOST3410Params;
import org.spongycastle.jce.interfaces.GOST3410PublicKey;
import org.spongycastle.jce.spec.GOST3410ParameterSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeyParameterSetSpec;
import org.spongycastle.jce.spec.GOST3410PublicKeySpec;

public class BCGOST3410PublicKey
  implements GOST3410PublicKey
{
  static final long serialVersionUID = -6251023343619275990L;
  private transient GOST3410Params gost3410Spec;
  private BigInteger y;
  
  BCGOST3410PublicKey(BigInteger paramBigInteger, GOST3410ParameterSpec paramGOST3410ParameterSpec)
  {
    this.y = paramBigInteger;
    this.gost3410Spec = paramGOST3410ParameterSpec;
  }
  
  BCGOST3410PublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    GOST3410PublicKeyAlgParameters localGOST3410PublicKeyAlgParameters = new GOST3410PublicKeyAlgParameters((ASN1Sequence)paramSubjectPublicKeyInfo.getAlgorithmId().getParameters());
    try
    {
      byte[] arrayOfByte1 = ((DEROctetString)paramSubjectPublicKeyInfo.parsePublicKey()).getOctets();
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
      for (int i = 0; i != arrayOfByte1.length; i++) {
        arrayOfByte2[i] = arrayOfByte1[(-1 + arrayOfByte1.length - i)];
      }
      this.y = new BigInteger(1, arrayOfByte2);
      this.gost3410Spec = GOST3410ParameterSpec.fromPublicKeyAlg(localGOST3410PublicKeyAlgParameters);
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("invalid info structure in GOST3410 public key");
    }
  }
  
  BCGOST3410PublicKey(GOST3410PublicKeyParameters paramGOST3410PublicKeyParameters, GOST3410ParameterSpec paramGOST3410ParameterSpec)
  {
    this.y = paramGOST3410PublicKeyParameters.getY();
    this.gost3410Spec = paramGOST3410ParameterSpec;
  }
  
  BCGOST3410PublicKey(GOST3410PublicKey paramGOST3410PublicKey)
  {
    this.y = paramGOST3410PublicKey.getY();
    this.gost3410Spec = paramGOST3410PublicKey.getParameters();
  }
  
  BCGOST3410PublicKey(GOST3410PublicKeySpec paramGOST3410PublicKeySpec)
  {
    this.y = paramGOST3410PublicKeySpec.getY();
    this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec(paramGOST3410PublicKeySpec.getP(), paramGOST3410PublicKeySpec.getQ(), paramGOST3410PublicKeySpec.getA()));
  }
  
  private void readObject(ObjectInputStream paramObjectInputStream)
    throws IOException, ClassNotFoundException
  {
    paramObjectInputStream.defaultReadObject();
    String str = (String)paramObjectInputStream.readObject();
    if (str != null)
    {
      this.gost3410Spec = new GOST3410ParameterSpec(str, (String)paramObjectInputStream.readObject(), (String)paramObjectInputStream.readObject());
      return;
    }
    this.gost3410Spec = new GOST3410ParameterSpec(new GOST3410PublicKeyParameterSetSpec((BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject(), (BigInteger)paramObjectInputStream.readObject()));
    paramObjectInputStream.readObject();
    paramObjectInputStream.readObject();
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
    boolean bool1 = paramObject instanceof BCGOST3410PublicKey;
    boolean bool2 = false;
    if (bool1)
    {
      BCGOST3410PublicKey localBCGOST3410PublicKey = (BCGOST3410PublicKey)paramObject;
      boolean bool3 = this.y.equals(localBCGOST3410PublicKey.y);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.gost3410Spec.equals(localBCGOST3410PublicKey.gost3410Spec);
        bool2 = false;
        if (bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public String getAlgorithm()
  {
    return "GOST3410";
  }
  
  public byte[] getEncoded()
  {
    byte[] arrayOfByte1 = getY().toByteArray();
    if (arrayOfByte1[0] == 0) {}
    for (byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];; arrayOfByte2 = new byte[arrayOfByte1.length]) {
      for (int i = 0; i != arrayOfByte2.length; i++) {
        arrayOfByte2[i] = arrayOfByte1[(-1 + arrayOfByte1.length - i)];
      }
    }
    SubjectPublicKeyInfo localSubjectPublicKeyInfo;
    if ((this.gost3410Spec instanceof GOST3410ParameterSpec)) {
      if (this.gost3410Spec.getEncryptionParamSetOID() != null) {
        localSubjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.gost3410Spec.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.gost3410Spec.getDigestParamSetOID()), new ASN1ObjectIdentifier(this.gost3410Spec.getEncryptionParamSetOID()))), new DEROctetString(arrayOfByte2));
      }
    }
    for (;;)
    {
      return KeyUtil.getEncodedSubjectPublicKeyInfo(localSubjectPublicKeyInfo);
      localSubjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94, new GOST3410PublicKeyAlgParameters(new ASN1ObjectIdentifier(this.gost3410Spec.getPublicKeyParamSetOID()), new ASN1ObjectIdentifier(this.gost3410Spec.getDigestParamSetOID()))), new DEROctetString(arrayOfByte2));
      continue;
      localSubjectPublicKeyInfo = new SubjectPublicKeyInfo(new AlgorithmIdentifier(CryptoProObjectIdentifiers.gostR3410_94), new DEROctetString(arrayOfByte2));
    }
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public GOST3410Params getParameters()
  {
    return this.gost3410Spec;
  }
  
  public BigInteger getY()
  {
    return this.y;
  }
  
  public int hashCode()
  {
    return this.y.hashCode() ^ this.gost3410Spec.hashCode();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("GOST3410 Public Key").append(str);
    localStringBuffer.append("            y: ").append(getY().toString(16)).append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.gost.BCGOST3410PublicKey
 * JD-Core Version:    0.7.0.1
 */