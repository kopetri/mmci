package org.spongycastle.crypto.signers;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERInteger;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.DSA;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Signer;
import org.spongycastle.crypto.params.AsymmetricKeyParameter;
import org.spongycastle.crypto.params.ParametersWithRandom;

public class DSADigestSigner
  implements Signer
{
  private final Digest digest;
  private final DSA dsaSigner;
  private boolean forSigning;
  
  public DSADigestSigner(DSA paramDSA, Digest paramDigest)
  {
    this.digest = paramDigest;
    this.dsaSigner = paramDSA;
  }
  
  private BigInteger[] derDecode(byte[] paramArrayOfByte)
    throws IOException
  {
    ASN1Sequence localASN1Sequence = (ASN1Sequence)ASN1Primitive.fromByteArray(paramArrayOfByte);
    BigInteger[] arrayOfBigInteger = new BigInteger[2];
    arrayOfBigInteger[0] = ((DERInteger)localASN1Sequence.getObjectAt(0)).getValue();
    arrayOfBigInteger[1] = ((DERInteger)localASN1Sequence.getObjectAt(1)).getValue();
    return arrayOfBigInteger;
  }
  
  private byte[] derEncode(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
    throws IOException
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new DERInteger(paramBigInteger1));
    localASN1EncodableVector.add(new DERInteger(paramBigInteger2));
    return new DERSequence(localASN1EncodableVector).getEncoded("DER");
  }
  
  public byte[] generateSignature()
  {
    if (!this.forSigning) {
      throw new IllegalStateException("DSADigestSigner not initialised for signature generation.");
    }
    byte[] arrayOfByte1 = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte1, 0);
    BigInteger[] arrayOfBigInteger = this.dsaSigner.generateSignature(arrayOfByte1);
    try
    {
      byte[] arrayOfByte2 = derEncode(arrayOfBigInteger[0], arrayOfBigInteger[1]);
      return arrayOfByte2;
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("unable to encode signature");
    }
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    this.forSigning = paramBoolean;
    if ((paramCipherParameters instanceof ParametersWithRandom)) {}
    for (AsymmetricKeyParameter localAsymmetricKeyParameter = (AsymmetricKeyParameter)((ParametersWithRandom)paramCipherParameters).getParameters(); (paramBoolean) && (!localAsymmetricKeyParameter.isPrivate()); localAsymmetricKeyParameter = (AsymmetricKeyParameter)paramCipherParameters) {
      throw new IllegalArgumentException("Signing Requires Private Key.");
    }
    if ((!paramBoolean) && (localAsymmetricKeyParameter.isPrivate())) {
      throw new IllegalArgumentException("Verification Requires Public Key.");
    }
    reset();
    this.dsaSigner.init(paramBoolean, paramCipherParameters);
  }
  
  public void reset()
  {
    this.digest.reset();
  }
  
  public void update(byte paramByte)
  {
    this.digest.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.digest.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    if (this.forSigning) {
      throw new IllegalStateException("DSADigestSigner not initialised for verification");
    }
    byte[] arrayOfByte = new byte[this.digest.getDigestSize()];
    this.digest.doFinal(arrayOfByte, 0);
    try
    {
      BigInteger[] arrayOfBigInteger = derDecode(paramArrayOfByte);
      boolean bool = this.dsaSigner.verifySignature(arrayOfByte, arrayOfBigInteger[0], arrayOfBigInteger[1]);
      return bool;
    }
    catch (IOException localIOException) {}
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.DSADigestSigner
 * JD-Core Version:    0.7.0.1
 */