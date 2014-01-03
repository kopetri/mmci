package org.spongycastle.jcajce.provider.asymmetric.rsa;

import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.oiw.OIWObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.crypto.AsymmetricBlockCipher;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD2Digest;
import org.spongycastle.crypto.digests.MD4Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD128Digest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.RIPEMD256Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.encodings.PKCS1Encoding;
import org.spongycastle.crypto.engines.RSABlindedEngine;
import org.spongycastle.crypto.params.RSAKeyParameters;

public class DigestSignatureSpi
  extends SignatureSpi
{
  private AlgorithmIdentifier algId;
  private AsymmetricBlockCipher cipher;
  private Digest digest;
  
  protected DigestSignatureSpi(ASN1ObjectIdentifier paramASN1ObjectIdentifier, Digest paramDigest, AsymmetricBlockCipher paramAsymmetricBlockCipher)
  {
    this.digest = paramDigest;
    this.cipher = paramAsymmetricBlockCipher;
    this.algId = new AlgorithmIdentifier(paramASN1ObjectIdentifier, DERNull.INSTANCE);
  }
  
  protected DigestSignatureSpi(Digest paramDigest, AsymmetricBlockCipher paramAsymmetricBlockCipher)
  {
    this.digest = paramDigest;
    this.cipher = paramAsymmetricBlockCipher;
    this.algId = null;
  }
  
  private byte[] derEncode(byte[] paramArrayOfByte)
    throws IOException
  {
    if (this.algId == null) {
      return paramArrayOfByte;
    }
    return new DigestInfo(this.algId, paramArrayOfByte).getEncoded("DER");
  }
  
  private String getType(Object paramObject)
  {
    if (paramObject == null) {
      return null;
    }
    return paramObject.getClass().getName();
  }
  
  protected Object engineGetParameter(String paramString)
  {
    return null;
  }
  
  protected AlgorithmParameters engineGetParameters()
  {
    return null;
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    if (!(paramPrivateKey instanceof RSAPrivateKey)) {
      throw new InvalidKeyException("Supplied key (" + getType(paramPrivateKey) + ") is not a RSAPrivateKey instance");
    }
    RSAKeyParameters localRSAKeyParameters = RSAUtil.generatePrivateKeyParameter((RSAPrivateKey)paramPrivateKey);
    this.digest.reset();
    this.cipher.init(true, localRSAKeyParameters);
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    if (!(paramPublicKey instanceof RSAPublicKey)) {
      throw new InvalidKeyException("Supplied key (" + getType(paramPublicKey) + ") is not a RSAPublicKey instance");
    }
    RSAKeyParameters localRSAKeyParameters = RSAUtil.generatePublicKeyParameter((RSAPublicKey)paramPublicKey);
    this.digest.reset();
    this.cipher.init(false, localRSAKeyParameters);
  }
  
  protected void engineSetParameter(String paramString, Object paramObject)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected void engineSetParameter(AlgorithmParameterSpec paramAlgorithmParameterSpec)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected byte[] engineSign()
    throws SignatureException
  {
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    try
    {
      byte[] arrayOfByte2 = derEncode(arrayOfByte1);
      byte[] arrayOfByte3 = this.cipher.processBlock(arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte3;
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      throw new SignatureException("key too small for signature type");
    }
    catch (Exception localException)
    {
      throw new SignatureException(localException.toString());
    }
  }
  
  protected void engineUpdate(byte paramByte)
    throws SignatureException
  {
    this.digest.update(paramByte);
  }
  
  protected void engineUpdate(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws SignatureException
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  protected boolean engineVerify(byte[] paramArrayOfByte)
    throws SignatureException
  {
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    try
    {
      byte[] arrayOfByte2 = this.cipher.processBlock(paramArrayOfByte, 0, paramArrayOfByte.length);
      byte[] arrayOfByte3 = derEncode(arrayOfByte1);
      if (arrayOfByte2.length == arrayOfByte3.length)
      {
        n = 0;
        if (n >= arrayOfByte2.length) {
          break label209;
        }
        if (arrayOfByte2[n] == arrayOfByte3[n]) {}
      }
      while (arrayOfByte2.length != -2 + arrayOfByte3.length) {
        for (;;)
        {
          int n;
          return false;
          n++;
        }
      }
      int i = -2 + (arrayOfByte2.length - arrayOfByte1.length);
      int j = -2 + (arrayOfByte3.length - arrayOfByte1.length);
      arrayOfByte3[1] = ((byte)(-2 + arrayOfByte3[1]));
      arrayOfByte3[3] = ((byte)(-2 + arrayOfByte3[3]));
      for (int k = 0;; k++)
      {
        if (k >= arrayOfByte1.length) {
          break label180;
        }
        if (arrayOfByte2[(i + k)] != arrayOfByte3[(j + k)]) {
          break;
        }
      }
      label180:
      for (int m = 0;; m++)
      {
        if (m >= i) {
          break label209;
        }
        if (arrayOfByte2[m] != arrayOfByte3[m]) {
          break;
        }
      }
      label209:
      return true;
    }
    catch (Exception localException) {}
    return false;
  }
  
  public static class MD2
    extends DigestSignatureSpi
  {
    public MD2()
    {
      super(new MD2Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class MD4
    extends DigestSignatureSpi
  {
    public MD4()
    {
      super(new MD4Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class MD5
    extends DigestSignatureSpi
  {
    public MD5()
    {
      super(new MD5Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD128
    extends DigestSignatureSpi
  {
    public RIPEMD128()
    {
      super(new RIPEMD128Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD160
    extends DigestSignatureSpi
  {
    public RIPEMD160()
    {
      super(new RIPEMD160Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class RIPEMD256
    extends DigestSignatureSpi
  {
    public RIPEMD256()
    {
      super(new RIPEMD256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA1
    extends DigestSignatureSpi
  {
    public SHA1()
    {
      super(new SHA1Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA224
    extends DigestSignatureSpi
  {
    public SHA224()
    {
      super(new SHA224Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA256
    extends DigestSignatureSpi
  {
    public SHA256()
    {
      super(new SHA256Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA384
    extends DigestSignatureSpi
  {
    public SHA384()
    {
      super(new SHA384Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class SHA512
    extends DigestSignatureSpi
  {
    public SHA512()
    {
      super(new SHA512Digest(), new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
  
  public static class noneRSA
    extends DigestSignatureSpi
  {
    public noneRSA()
    {
      super(new PKCS1Encoding(new RSABlindedEngine()));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.rsa.DigestSignatureSpi
 * JD-Core Version:    0.7.0.1
 */