package org.spongycastle.jcajce.provider.asymmetric.ec;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.NullDigest;
import org.spongycastle.crypto.digests.RIPEMD160Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.digests.SHA224Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA384Digest;
import org.spongycastle.crypto.digests.SHA512Digest;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;
import org.spongycastle.crypto.signers.ECDSASigner;
import org.spongycastle.crypto.signers.ECNRSigner;
import org.spongycastle.jcajce.provider.asymmetric.util.DSABase;
import org.spongycastle.jcajce.provider.asymmetric.util.DSAEncoder;
import org.spongycastle.jce.interfaces.ECKey;
import org.spongycastle.jce.provider.BouncyCastleProvider;

public class SignatureSpi
  extends DSABase
{
  SignatureSpi(Digest paramDigest, DSA paramDSA, DSAEncoder paramDSAEncoder)
  {
    super(paramDigest, paramDSA, paramDSAEncoder);
  }
  
  protected void engineInitSign(PrivateKey paramPrivateKey)
    throws InvalidKeyException
  {
    AsymmetricKeyParameter localAsymmetricKeyParameter;
    if ((paramPrivateKey instanceof ECKey))
    {
      localAsymmetricKeyParameter = ECUtil.generatePrivateKeyParameter(paramPrivateKey);
      this.digest.reset();
      if (this.appRandom != null) {
        this.signer.init(true, new ParametersWithRandom(localAsymmetricKeyParameter, this.appRandom));
      }
    }
    else
    {
      throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
    }
    this.signer.init(true, localAsymmetricKeyParameter);
  }
  
  protected void engineInitVerify(PublicKey paramPublicKey)
    throws InvalidKeyException
  {
    AsymmetricKeyParameter localAsymmetricKeyParameter;
    if ((paramPublicKey instanceof ECPublicKey)) {
      localAsymmetricKeyParameter = ECUtil.generatePublicKeyParameter(paramPublicKey);
    }
    for (;;)
    {
      this.digest.reset();
      this.signer.init(false, localAsymmetricKeyParameter);
      return;
      try
      {
        PublicKey localPublicKey = BouncyCastleProvider.getPublicKey(SubjectPublicKeyInfo.getInstance(paramPublicKey.getEncoded()));
        if ((localPublicKey instanceof ECPublicKey))
        {
          localAsymmetricKeyParameter = ECUtil.generatePublicKeyParameter(localPublicKey);
          continue;
        }
        throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
      }
      catch (Exception localException)
      {
        throw new InvalidKeyException("can't recognise key type in ECDSA based signer");
      }
    }
  }
  
  private static class CVCDSAEncoder
    implements DSAEncoder
  {
    private byte[] makeUnsigned(BigInteger paramBigInteger)
    {
      byte[] arrayOfByte1 = paramBigInteger.toByteArray();
      if (arrayOfByte1[0] == 0)
      {
        byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];
        System.arraycopy(arrayOfByte1, 1, arrayOfByte2, 0, arrayOfByte2.length);
        return arrayOfByte2;
      }
      return arrayOfByte1;
    }
    
    public BigInteger[] decode(byte[] paramArrayOfByte)
      throws IOException
    {
      BigInteger[] arrayOfBigInteger = new BigInteger[2];
      byte[] arrayOfByte1 = new byte[paramArrayOfByte.length / 2];
      byte[] arrayOfByte2 = new byte[paramArrayOfByte.length / 2];
      System.arraycopy(paramArrayOfByte, 0, arrayOfByte1, 0, arrayOfByte1.length);
      System.arraycopy(paramArrayOfByte, arrayOfByte1.length, arrayOfByte2, 0, arrayOfByte2.length);
      arrayOfBigInteger[0] = new BigInteger(1, arrayOfByte1);
      arrayOfBigInteger[1] = new BigInteger(1, arrayOfByte2);
      return arrayOfBigInteger;
    }
    
    public byte[] encode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
      throws IOException
    {
      byte[] arrayOfByte1 = makeUnsigned(paramBigInteger1);
      byte[] arrayOfByte2 = makeUnsigned(paramBigInteger2);
      if (arrayOfByte1.length > arrayOfByte2.length) {}
      for (byte[] arrayOfByte3 = new byte[2 * arrayOfByte1.length];; arrayOfByte3 = new byte[2 * arrayOfByte2.length])
      {
        System.arraycopy(arrayOfByte1, 0, arrayOfByte3, arrayOfByte3.length / 2 - arrayOfByte1.length, arrayOfByte1.length);
        System.arraycopy(arrayOfByte2, 0, arrayOfByte3, arrayOfByte3.length - arrayOfByte2.length, arrayOfByte2.length);
        return arrayOfByte3;
      }
    }
  }
  
  private static class StdDSAEncoder
    implements DSAEncoder
  {
    public BigInteger[] decode(byte[] paramArrayOfByte)
      throws IOException
    {
      ASN1Sequence localASN1Sequence = (ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfByte);
      BigInteger[] arrayOfBigInteger = new BigInteger[2];
      arrayOfBigInteger[0] = ((DERInteger)localASN1Sequence.getObjectAt(0)).getValue();
      arrayOfBigInteger[1] = ((DERInteger)localASN1Sequence.getObjectAt(1)).getValue();
      return arrayOfBigInteger;
    }
    
    public byte[] encode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
      throws IOException
    {
      ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
      localASN1EncodableVector.add(new DERInteger(paramBigInteger1));
      localASN1EncodableVector.add(new DERInteger(paramBigInteger2));
      return new DERSequence(localASN1EncodableVector).getEncoded("DER");
    }
  }
  
  public static class ecCVCDSA
    extends SignatureSpi
  {
    public ecCVCDSA()
    {
      super(new ECDSASigner(), new SignatureSpi.CVCDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA224
    extends SignatureSpi
  {
    public ecCVCDSA224()
    {
      super(new ECDSASigner(), new SignatureSpi.CVCDSAEncoder(null));
    }
  }
  
  public static class ecCVCDSA256
    extends SignatureSpi
  {
    public ecCVCDSA256()
    {
      super(new ECDSASigner(), new SignatureSpi.CVCDSAEncoder(null));
    }
  }
  
  public static class ecDSA
    extends SignatureSpi
  {
    public ecDSA()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA224
    extends SignatureSpi
  {
    public ecDSA224()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA256
    extends SignatureSpi
  {
    public ecDSA256()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA384
    extends SignatureSpi
  {
    public ecDSA384()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSA512
    extends SignatureSpi
  {
    public ecDSA512()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSARipeMD160
    extends SignatureSpi
  {
    public ecDSARipeMD160()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecDSAnone
    extends SignatureSpi
  {
    public ecDSAnone()
    {
      super(new ECDSASigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR
    extends SignatureSpi
  {
    public ecNR()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR224
    extends SignatureSpi
  {
    public ecNR224()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR256
    extends SignatureSpi
  {
    public ecNR256()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR384
    extends SignatureSpi
  {
    public ecNR384()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
  
  public static class ecNR512
    extends SignatureSpi
  {
    public ecNR512()
    {
      super(new ECNRSigner(), new SignatureSpi.StdDSAEncoder(null));
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jcajce.provider.asymmetric.ec.SignatureSpi
 * JD-Core Version:    0.7.0.1
 */