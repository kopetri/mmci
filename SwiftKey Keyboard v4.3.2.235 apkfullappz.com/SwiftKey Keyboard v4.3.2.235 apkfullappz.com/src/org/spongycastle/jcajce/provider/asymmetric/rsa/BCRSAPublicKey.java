package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.spec.RSAPublicKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.params.RSAKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCRSAPublicKey
  implements java.security.interfaces.RSAPublicKey
{
  static final long serialVersionUID = 2675817738516720772L;
  private BigInteger modulus;
  private BigInteger publicExponent;
  
  BCRSAPublicKey(java.security.interfaces.RSAPublicKey paramRSAPublicKey)
  {
    this.modulus = paramRSAPublicKey.getModulus();
    this.publicExponent = paramRSAPublicKey.getPublicExponent();
  }
  
  BCRSAPublicKey(RSAPublicKeySpec paramRSAPublicKeySpec)
  {
    this.modulus = paramRSAPublicKeySpec.getModulus();
    this.publicExponent = paramRSAPublicKeySpec.getPublicExponent();
  }
  
  BCRSAPublicKey(SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    try
    {
      org.spongycastle.asn1.pkcs.RSAPublicKey localRSAPublicKey = org.spongycastle.asn1.pkcs.RSAPublicKey.getInstance(paramSubjectPublicKeyInfo.parsePublicKey());
      this.modulus = localRSAPublicKey.getModulus();
      this.publicExponent = localRSAPublicKey.getPublicExponent();
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("invalid info structure in RSA public key");
    }
  }
  
  BCRSAPublicKey(RSAKeyParameters paramRSAKeyParameters)
  {
    this.modulus = paramRSAKeyParameters.getModulus();
    this.publicExponent = paramRSAKeyParameters.getExponent();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    java.security.interfaces.RSAPublicKey localRSAPublicKey;
    do
    {
      return true;
      if (!(paramObject instanceof java.security.interfaces.RSAPublicKey)) {
        return false;
      }
      localRSAPublicKey = (java.security.interfaces.RSAPublicKey)paramObject;
    } while ((getModulus().equals(localRSAPublicKey.getModulus())) && (getPublicExponent().equals(localRSAPublicKey.getPublicExponent())));
    return false;
  }
  
  public String getAlgorithm()
  {
    return "RSA";
  }
  
  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedSubjectPublicKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, new DERNull()), new org.spongycastle.asn1.pkcs.RSAPublicKey(getModulus(), getPublicExponent()));
  }
  
  public String getFormat()
  {
    return "X.509";
  }
  
  public BigInteger getModulus()
  {
    return this.modulus;
  }
  
  public BigInteger getPublicExponent()
  {
    return this.publicExponent;
  }
  
  public int hashCode()
  {
    return getModulus().hashCode() ^ getPublicExponent().hashCode();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("RSA Public Key").append(str);
    localStringBuffer.append("            modulus: ").append(getModulus().toString(16)).append(str);
    localStringBuffer.append("    public exponent: ").append(getPublicExponent().toString(16)).append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.BCRSAPublicKey
 * JD-Core Version:    0.7.0.1
 */