package org.spongycastle.jcajce.provider.asymmetric.dsa;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.DSAKey;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509ObjectIdentifiers;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DSASigner
  extends SignatureSpi
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers
{
  private Digest digest;
  private SecureRandom random;
  private DSA signer;
  
  protected DSASigner(Digest paramDigest, DSA paramDSA)
  {
    this.digest = paramDigest;
    this.signer = paramDSA;
  }
  
  private BigInteger[] derDecode(byte[] paramArrayOfByte)
    throws IOException
  {
    ASN1Sequence localASN1Sequence = (ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfByte);
    BigInteger[] arrayOfBigInteger = new BigInteger[2];
    arrayOfBigInteger[0] = ((ASN1Integer)localASN1Sequence.getObjectAt(0)).getValue();
    arrayOfBigInteger[1] = ((ASN1Integer)localASN1Sequence.getObjectAt(1)).getValue();
    return arrayOfBigInteger;
  }
  
  private byte[] derEncode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
    throws IOException
  {
    ASN1Integer[] arrayOfASN1Integer = new ASN1Integer[2];
    arrayOfASN1Integer[0] = new ASN1Integer(paramBigInteger1);
    arrayOfASN1Integer[1] = new ASN1Integer(paramBigInteger2);
    return new DERSequence(arrayOfASN1Integer).getEncoded("DER");
  }
  
  protected Object engineGetParameter(String paramString)
  {
    throw new UnsupportedOperationException("engineSetParameter unsupported");
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    Object localObject = DSAUtil.generatePrivateKeyParameter(paramPrivateKey);
    if (this.random != null) {
      localObject = new ParametersWithRandom((CipherParameters)localObject, this.random);
    }
    this.digest.reset();
    this.signer.init(true, (CipherParameters)localObject);
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey, SecureRandom paramSecureRandom)
    throws InvalidKeyException
  {
    this.random = paramSecureRandom;
    engineInitSign(paramPrivateKey);
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    Object localObject;
    if ((paramPublicKey instanceof DSAKey)) {
      localObject = DSAUtil.generatePublicKeyParameter(paramPublicKey);
    }
    for (;;)
    {
      this.digest.reset();
      this.signer.init(false, (CipherParameters)localObject);
      return;
      try
      {
        AsymmetricKeyParameter localAsymmetricKeyParameter = DSAUtil.generatePublicKeyParameter(new BCDSAPublicKey(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded())));
        localObject = localAsymmetricKeyParameter;
      }
      catch (Exception localException)
      {
        throw new InvalidKeyException("can't recognise key type in DSA based signer");
      }
    }
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
      BigInteger[] arrayOfBigInteger = this.signer.generateSignature(arrayOfByte1);
      byte[] arrayOfByte2 = derEncode(arrayOfBigInteger[0], arrayOfBigInteger[1]);
      return arrayOfByte2;
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
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    try
    {
      BigInteger[] arrayOfBigInteger = derDecode(paramArrayOfByte);
      return this.signer.verifySignature(arrayOfByte, arrayOfBigInteger[0], arrayOfBigInteger[1]);
    }
    catch (Exception localException)
    {
      throw new SignatureException("error decoding signature bytes.");
    }
  }
  
  public static class dsa224
    extends DSASigner
  {
    public dsa224()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
  
  public static class dsa256
    extends DSASigner
  {
    public dsa256()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
  
  public static class dsa384
    extends DSASigner
  {
    public dsa384()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
  
  public static class dsa512
    extends DSASigner
  {
    public dsa512()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
  
  public static class noneDSA
    extends DSASigner
  {
    public noneDSA()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
  
  public static class stdDSA
    extends DSASigner
  {
    public stdDSA()
    {
      super(new org.spongycastle.crypto.signers.DSASigner());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.dsa.DSASigner
 * JD-Core Version:    0.7.0.1
 */