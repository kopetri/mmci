package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.RSAPrivateCrtKeySpec;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PrivateKeyInfo;
import org.spongycastle.asn1.pkcs.RSAPrivateKey;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.spongycastle.jcajce.provider.asymmetric.util.KeyUtil;

public class BCRSAPrivateCrtKey
  extends BCRSAPrivateKey
  implements RSAPrivateCrtKey
{
  static final long serialVersionUID = 7834723820638524718L;
  private BigInteger crtCoefficient;
  private BigInteger primeExponentP;
  private BigInteger primeExponentQ;
  private BigInteger primeP;
  private BigInteger primeQ;
  private BigInteger publicExponent;
  
  BCRSAPrivateCrtKey(RSAPrivateCrtKey paramRSAPrivateCrtKey)
  {
    this.modulus = paramRSAPrivateCrtKey.getModulus();
    this.publicExponent = paramRSAPrivateCrtKey.getPublicExponent();
    this.privateExponent = paramRSAPrivateCrtKey.getPrivateExponent();
    this.primeP = paramRSAPrivateCrtKey.getPrimeP();
    this.primeQ = paramRSAPrivateCrtKey.getPrimeQ();
    this.primeExponentP = paramRSAPrivateCrtKey.getPrimeExponentP();
    this.primeExponentQ = paramRSAPrivateCrtKey.getPrimeExponentQ();
    this.crtCoefficient = paramRSAPrivateCrtKey.getCrtCoefficient();
  }
  
  BCRSAPrivateCrtKey(RSAPrivateCrtKeySpec paramRSAPrivateCrtKeySpec)
  {
    this.modulus = paramRSAPrivateCrtKeySpec.getModulus();
    this.publicExponent = paramRSAPrivateCrtKeySpec.getPublicExponent();
    this.privateExponent = paramRSAPrivateCrtKeySpec.getPrivateExponent();
    this.primeP = paramRSAPrivateCrtKeySpec.getPrimeP();
    this.primeQ = paramRSAPrivateCrtKeySpec.getPrimeQ();
    this.primeExponentP = paramRSAPrivateCrtKeySpec.getPrimeExponentP();
    this.primeExponentQ = paramRSAPrivateCrtKeySpec.getPrimeExponentQ();
    this.crtCoefficient = paramRSAPrivateCrtKeySpec.getCrtCoefficient();
  }
  
  BCRSAPrivateCrtKey(PrivateKeyInfo paramPrivateKeyInfo)
    throws IOException
  {
    this(RSAPrivateKey.getInstance(paramPrivateKeyInfo.parsePrivateKey()));
  }
  
  BCRSAPrivateCrtKey(RSAPrivateKey paramRSAPrivateKey)
  {
    this.modulus = paramRSAPrivateKey.getModulus();
    this.publicExponent = paramRSAPrivateKey.getPublicExponent();
    this.privateExponent = paramRSAPrivateKey.getPrivateExponent();
    this.primeP = paramRSAPrivateKey.getPrime1();
    this.primeQ = paramRSAPrivateKey.getPrime2();
    this.primeExponentP = paramRSAPrivateKey.getExponent1();
    this.primeExponentQ = paramRSAPrivateKey.getExponent2();
    this.crtCoefficient = paramRSAPrivateKey.getCoefficient();
  }
  
  BCRSAPrivateCrtKey(RSAPrivateCrtKeyParameters paramRSAPrivateCrtKeyParameters)
  {
    super(paramRSAPrivateCrtKeyParameters);
    this.publicExponent = paramRSAPrivateCrtKeyParameters.getPublicExponent();
    this.primeP = paramRSAPrivateCrtKeyParameters.getP();
    this.primeQ = paramRSAPrivateCrtKeyParameters.getQ();
    this.primeExponentP = paramRSAPrivateCrtKeyParameters.getDP();
    this.primeExponentQ = paramRSAPrivateCrtKeyParameters.getDQ();
    this.crtCoefficient = paramRSAPrivateCrtKeyParameters.getQInv();
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {}
    RSAPrivateCrtKey localRSAPrivateCrtKey;
    do
    {
      return true;
      if (!(paramObject instanceof RSAPrivateCrtKey)) {
        return false;
      }
      localRSAPrivateCrtKey = (RSAPrivateCrtKey)paramObject;
    } while ((getModulus().equals(localRSAPrivateCrtKey.getModulus())) && (getPublicExponent().equals(localRSAPrivateCrtKey.getPublicExponent())) && (getPrivateExponent().equals(localRSAPrivateCrtKey.getPrivateExponent())) && (getPrimeP().equals(localRSAPrivateCrtKey.getPrimeP())) && (getPrimeQ().equals(localRSAPrivateCrtKey.getPrimeQ())) && (getPrimeExponentP().equals(localRSAPrivateCrtKey.getPrimeExponentP())) && (getPrimeExponentQ().equals(localRSAPrivateCrtKey.getPrimeExponentQ())) && (getCrtCoefficient().equals(localRSAPrivateCrtKey.getCrtCoefficient())));
    return false;
  }
  
  public BigInteger getCrtCoefficient()
  {
    return this.crtCoefficient;
  }
  
  public byte[] getEncoded()
  {
    return KeyUtil.getEncodedPrivateKeyInfo(new AlgorithmIdentifier(PKCSObjectIdentifiers.rsaEncryption, new DERNull()), new RSAPrivateKey(getModulus(), getPublicExponent(), getPrivateExponent(), getPrimeP(), getPrimeQ(), getPrimeExponentP(), getPrimeExponentQ(), getCrtCoefficient()));
  }
  
  public String getFormat()
  {
    return "PKCS#8";
  }
  
  public BigInteger getPrimeExponentP()
  {
    return this.primeExponentP;
  }
  
  public BigInteger getPrimeExponentQ()
  {
    return this.primeExponentQ;
  }
  
  public BigInteger getPrimeP()
  {
    return this.primeP;
  }
  
  public BigInteger getPrimeQ()
  {
    return this.primeQ;
  }
  
  public BigInteger getPublicExponent()
  {
    return this.publicExponent;
  }
  
  public int hashCode()
  {
    return getModulus().hashCode() ^ getPublicExponent().hashCode() ^ getPrivateExponent().hashCode();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("RSA Private CRT Key").append(str);
    localStringBuffer.append("            modulus: ").append(getModulus().toString(16)).append(str);
    localStringBuffer.append("    public exponent: ").append(getPublicExponent().toString(16)).append(str);
    localStringBuffer.append("   private exponent: ").append(getPrivateExponent().toString(16)).append(str);
    localStringBuffer.append("             primeP: ").append(getPrimeP().toString(16)).append(str);
    localStringBuffer.append("             primeQ: ").append(getPrimeQ().toString(16)).append(str);
    localStringBuffer.append("     primeExponentP: ").append(getPrimeExponentP().toString(16)).append(str);
    localStringBuffer.append("     primeExponentQ: ").append(getPrimeExponentQ().toString(16)).append(str);
    localStringBuffer.append("     crtCoefficient: ").append(getCrtCoefficient().toString(16)).append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.BCRSAPrivateCrtKey
 * JD-Core Version:    0.7.0.1
 */