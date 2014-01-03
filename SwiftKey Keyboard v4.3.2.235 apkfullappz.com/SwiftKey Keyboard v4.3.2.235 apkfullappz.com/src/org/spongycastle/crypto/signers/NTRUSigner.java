package org.spongycastle.crypto.signers;

import java.nio.ByteBuffer;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.params.NTRUSigningParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters;
import org.spongycastle.crypto.params.NTRUSigningPrivateKeyParameters.Basis;
import org.spongycastle.crypto.params.NTRUSigningPublicKeyParameters;
import org.spongycastle.math.ntru.polynomial.IntegerPolynomial;
import org.spongycastle.math.ntru.polynomial.Polynomial;

public class NTRUSigner
{
  private Digest hashAlg;
  private NTRUSigningParameters params;
  private NTRUSigningPrivateKeyParameters signingKeyPair;
  private NTRUSigningPublicKeyParameters verificationKey;
  
  public NTRUSigner(NTRUSigningParameters paramNTRUSigningParameters)
  {
    this.params = paramNTRUSigningParameters;
  }
  
  private IntegerPolynomial sign(IntegerPolynomial paramIntegerPolynomial, NTRUSigningPrivateKeyParameters paramNTRUSigningPrivateKeyParameters)
  {
    int i = this.params.N;
    int j = this.params.q;
    int k = this.params.B;
    NTRUSigningPublicKeyParameters localNTRUSigningPublicKeyParameters = paramNTRUSigningPrivateKeyParameters.getPublicKey();
    IntegerPolynomial localIntegerPolynomial1 = new IntegerPolynomial(i);
    int m = k;
    if (m > 0)
    {
      Polynomial localPolynomial3 = paramNTRUSigningPrivateKeyParameters.getBasis(m).f;
      Polynomial localPolynomial4 = paramNTRUSigningPrivateKeyParameters.getBasis(m).fPrime;
      IntegerPolynomial localIntegerPolynomial5 = localPolynomial3.mult(paramIntegerPolynomial);
      localIntegerPolynomial5.div(j);
      IntegerPolynomial localIntegerPolynomial6 = localPolynomial4.mult(localIntegerPolynomial5);
      IntegerPolynomial localIntegerPolynomial7 = localPolynomial4.mult(paramIntegerPolynomial);
      localIntegerPolynomial7.div(j);
      localIntegerPolynomial6.sub(localPolynomial3.mult(localIntegerPolynomial7));
      localIntegerPolynomial1.add(localIntegerPolynomial6);
      IntegerPolynomial localIntegerPolynomial8 = (IntegerPolynomial)paramNTRUSigningPrivateKeyParameters.getBasis(m).h.clone();
      if (m > 1) {
        localIntegerPolynomial8.sub(paramNTRUSigningPrivateKeyParameters.getBasis(m - 1).h);
      }
      for (;;)
      {
        paramIntegerPolynomial = localIntegerPolynomial6.mult(localIntegerPolynomial8, j);
        m--;
        break;
        localIntegerPolynomial8.sub(localNTRUSigningPublicKeyParameters.h);
      }
    }
    Polynomial localPolynomial1 = paramNTRUSigningPrivateKeyParameters.getBasis(0).f;
    Polynomial localPolynomial2 = paramNTRUSigningPrivateKeyParameters.getBasis(0).fPrime;
    IntegerPolynomial localIntegerPolynomial2 = localPolynomial1.mult(paramIntegerPolynomial);
    localIntegerPolynomial2.div(j);
    IntegerPolynomial localIntegerPolynomial3 = localPolynomial2.mult(localIntegerPolynomial2);
    IntegerPolynomial localIntegerPolynomial4 = localPolynomial2.mult(paramIntegerPolynomial);
    localIntegerPolynomial4.div(j);
    localIntegerPolynomial3.sub(localPolynomial1.mult(localIntegerPolynomial4));
    localIntegerPolynomial1.add(localIntegerPolynomial3);
    localIntegerPolynomial1.modPositive(j);
    return localIntegerPolynomial1;
  }
  
  private byte[] signHash(byte[] paramArrayOfByte, NTRUSigningPrivateKeyParameters paramNTRUSigningPrivateKeyParameters)
  {
    int i = 0;
    NTRUSigningPublicKeyParameters localNTRUSigningPublicKeyParameters = paramNTRUSigningPrivateKeyParameters.getPublicKey();
    IntegerPolynomial localIntegerPolynomial1;
    IntegerPolynomial localIntegerPolynomial2;
    do
    {
      i++;
      if (i > this.params.signFailTolerance) {
        throw new IllegalStateException("Signing failed: too many retries (max=" + this.params.signFailTolerance + ")");
      }
      localIntegerPolynomial1 = createMsgRep(paramArrayOfByte, i);
      localIntegerPolynomial2 = sign(localIntegerPolynomial1, paramNTRUSigningPrivateKeyParameters);
    } while (!verify(localIntegerPolynomial1, localIntegerPolynomial2, localNTRUSigningPublicKeyParameters.h));
    byte[] arrayOfByte = localIntegerPolynomial2.toBinary(this.params.q);
    ByteBuffer localByteBuffer = ByteBuffer.allocate(4 + arrayOfByte.length);
    localByteBuffer.put(arrayOfByte);
    localByteBuffer.putInt(i);
    return localByteBuffer.array();
  }
  
  private boolean verify(IntegerPolynomial paramIntegerPolynomial1, IntegerPolynomial paramIntegerPolynomial2, IntegerPolynomial paramIntegerPolynomial3)
  {
    int i = this.params.q;
    double d1 = this.params.normBoundSq;
    double d2 = this.params.betaSq;
    IntegerPolynomial localIntegerPolynomial = paramIntegerPolynomial3.mult(paramIntegerPolynomial2, i);
    localIntegerPolynomial.sub(paramIntegerPolynomial1);
    return (paramIntegerPolynomial2.centeredNormSq(i) + d2 * localIntegerPolynomial.centeredNormSq(i)) <= d1;
  }
  
  private boolean verifyHash(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, NTRUSigningPublicKeyParameters paramNTRUSigningPublicKeyParameters)
  {
    ByteBuffer localByteBuffer = ByteBuffer.wrap(paramArrayOfByte2);
    byte[] arrayOfByte = new byte[-4 + paramArrayOfByte2.length];
    localByteBuffer.get(arrayOfByte);
    IntegerPolynomial localIntegerPolynomial = IntegerPolynomial.fromBinary(arrayOfByte, this.params.N, this.params.q);
    return verify(createMsgRep(paramArrayOfByte1, localByteBuffer.getInt()), localIntegerPolynomial, paramNTRUSigningPublicKeyParameters.h);
  }
  
  protected IntegerPolynomial createMsgRep(byte[] paramArrayOfByte, int paramInt)
  {
    int i = this.params.N;
    int j = 31 - Integer.numberOfLeadingZeros(this.params.q);
    int k = (j + 7) / 8;
    IntegerPolynomial localIntegerPolynomial = new IntegerPolynomial(i);
    ByteBuffer localByteBuffer1 = ByteBuffer.allocate(4 + paramArrayOfByte.length);
    localByteBuffer1.put(paramArrayOfByte);
    localByteBuffer1.putInt(paramInt);
    NTRUSignerPrng localNTRUSignerPrng = new NTRUSignerPrng(localByteBuffer1.array(), this.params.hashAlg);
    for (int m = 0; m < i; m++)
    {
      byte[] arrayOfByte = localNTRUSignerPrng.nextBytes(k);
      int n = arrayOfByte[(-1 + arrayOfByte.length)] >> k * 8 - j << k * 8 - j;
      arrayOfByte[(-1 + arrayOfByte.length)] = ((byte)n);
      ByteBuffer localByteBuffer2 = ByteBuffer.allocate(4);
      localByteBuffer2.put(arrayOfByte);
      localByteBuffer2.rewind();
      localIntegerPolynomial.coeffs[m] = Integer.reverseBytes(localByteBuffer2.getInt());
    }
    return localIntegerPolynomial;
  }
  
  public byte[] generateSignature()
  {
    if ((this.hashAlg == null) || (this.signingKeyPair == null)) {
      throw new IllegalStateException("Call initSign first!");
    }
    byte[] arrayOfByte = new byte[this.hashAlg.getDigestSize()];
    this.hashAlg.doFinal(arrayOfByte, 0);
    return signHash(arrayOfByte, this.signingKeyPair);
  }
  
  public void init(boolean paramBoolean, CipherParameters paramCipherParameters)
  {
    if (paramBoolean) {
      this.signingKeyPair = ((NTRUSigningPrivateKeyParameters)paramCipherParameters);
    }
    for (;;)
    {
      this.hashAlg = this.params.hashAlg;
      this.hashAlg.reset();
      return;
      this.verificationKey = ((NTRUSigningPublicKeyParameters)paramCipherParameters);
    }
  }
  
  public void update(byte paramByte)
  {
    if (this.hashAlg == null) {
      throw new IllegalStateException("Call initSign or initVerify first!");
    }
    this.hashAlg.update(paramByte);
  }
  
  public void update(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    if (this.hashAlg == null) {
      throw new IllegalStateException("Call initSign or initVerify first!");
    }
    this.hashAlg.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  public boolean verifySignature(byte[] paramArrayOfByte)
  {
    if ((this.hashAlg == null) || (this.verificationKey == null)) {
      throw new IllegalStateException("Call initVerify first!");
    }
    byte[] arrayOfByte = new byte[this.hashAlg.getDigestSize()];
    this.hashAlg.doFinal(arrayOfByte, 0);
    return verifyHash(arrayOfByte, paramArrayOfByte, this.verificationKey);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.signers.NTRUSigner
 * JD-Core Version:    0.7.0.1
 */