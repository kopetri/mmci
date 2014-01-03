package org.spongycastle.crypto.params;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.KeyGenerationParameters;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUSigningKeyGenerationParameters
  extends KeyGenerationParameters
  implements Cloneable
{
  public static final NTRUSigningKeyGenerationParameters APR2011_439 = new NTRUSigningKeyGenerationParameters(439, 2048, 146, 1, 1, 0.165D, 400.0D, 280.0D, false, true, 0, new SHA256Digest());
  public static final NTRUSigningKeyGenerationParameters APR2011_439_PROD = new NTRUSigningKeyGenerationParameters(439, 2048, 9, 8, 5, 1, 1, 0.165D, 400.0D, 280.0D, false, true, 0, new SHA256Digest());
  public static final NTRUSigningKeyGenerationParameters APR2011_743 = new NTRUSigningKeyGenerationParameters(743, 2048, 248, 1, 1, 0.127D, 405.0D, 360.0D, true, false, 0, new SHA512Digest());
  public static final NTRUSigningKeyGenerationParameters APR2011_743_PROD = new NTRUSigningKeyGenerationParameters(743, 2048, 11, 11, 15, 1, 1, 0.127D, 405.0D, 360.0D, true, false, 0, new SHA512Digest());
  public static final int BASIS_TYPE_STANDARD = 0;
  public static final int BASIS_TYPE_TRANSPOSE = 1;
  public static final int KEY_GEN_ALG_FLOAT = 1;
  public static final int KEY_GEN_ALG_RESULTANT;
  public static final NTRUSigningKeyGenerationParameters TEST157 = new NTRUSigningKeyGenerationParameters(157, 256, 29, 1, 1, 0.38D, 200.0D, 80.0D, false, false, 0, new SHA256Digest());
  public static final NTRUSigningKeyGenerationParameters TEST157_PROD = new NTRUSigningKeyGenerationParameters(157, 256, 5, 5, 8, 1, 1, 0.38D, 200.0D, 80.0D, false, false, 0, new SHA256Digest());
  public int B;
  public int N;
  public int basisType;
  double beta;
  public double betaSq;
  int bitsF = 6;
  public int d;
  public int d1;
  public int d2;
  public int d3;
  public Digest hashAlg;
  public int keyGenAlg;
  double keyNormBound;
  public double keyNormBoundSq;
  double normBound;
  public double normBoundSq;
  public int polyType;
  public boolean primeCheck;
  public int q;
  public int signFailTolerance = 100;
  public boolean sparse;
  
  public NTRUSigningKeyGenerationParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, boolean paramBoolean2, int paramInt6, Digest paramDigest)
  {
    super(new SecureRandom(), paramInt1);
    this.N = paramInt1;
    this.q = paramInt2;
    this.d = paramInt3;
    this.B = paramInt4;
    this.basisType = paramInt5;
    this.beta = paramDouble1;
    this.normBound = paramDouble2;
    this.keyNormBound = paramDouble3;
    this.primeCheck = paramBoolean1;
    this.sparse = paramBoolean2;
    this.keyGenAlg = paramInt6;
    this.hashAlg = paramDigest;
    this.polyType = 0;
    init();
  }
  
  public NTRUSigningKeyGenerationParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, double paramDouble1, double paramDouble2, double paramDouble3, boolean paramBoolean1, boolean paramBoolean2, int paramInt8, Digest paramDigest)
  {
    super(new SecureRandom(), paramInt1);
    this.N = paramInt1;
    this.q = paramInt2;
    this.d1 = paramInt3;
    this.d2 = paramInt4;
    this.d3 = paramInt5;
    this.B = paramInt6;
    this.basisType = paramInt7;
    this.beta = paramDouble1;
    this.normBound = paramDouble2;
    this.keyNormBound = paramDouble3;
    this.primeCheck = paramBoolean1;
    this.sparse = paramBoolean2;
    this.keyGenAlg = paramInt8;
    this.hashAlg = paramDigest;
    this.polyType = 1;
    init();
  }
  
  public NTRUSigningKeyGenerationParameters(InputStream paramInputStream)
    throws IOException
  {
    super(new SecureRandom(), 0);
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    this.N = localDataInputStream.readInt();
    this.q = localDataInputStream.readInt();
    this.d = localDataInputStream.readInt();
    this.d1 = localDataInputStream.readInt();
    this.d2 = localDataInputStream.readInt();
    this.d3 = localDataInputStream.readInt();
    this.B = localDataInputStream.readInt();
    this.basisType = localDataInputStream.readInt();
    this.beta = localDataInputStream.readDouble();
    this.normBound = localDataInputStream.readDouble();
    this.keyNormBound = localDataInputStream.readDouble();
    this.signFailTolerance = localDataInputStream.readInt();
    this.primeCheck = localDataInputStream.readBoolean();
    this.sparse = localDataInputStream.readBoolean();
    this.bitsF = localDataInputStream.readInt();
    this.keyGenAlg = localDataInputStream.read();
    String str = localDataInputStream.readUTF();
    if ("SHA-512".equals(str)) {
      this.hashAlg = new SHA512Digest();
    }
    for (;;)
    {
      this.polyType = localDataInputStream.read();
      init();
      return;
      if ("SHA-256".equals(str)) {
        this.hashAlg = new SHA256Digest();
      }
    }
  }
  
  private void init()
  {
    this.betaSq = (this.beta * this.beta);
    this.normBoundSq = (this.normBound * this.normBound);
    this.keyNormBoundSq = (this.keyNormBound * this.keyNormBound);
  }
  
  public NTRUSigningKeyGenerationParameters clone()
  {
    if (this.polyType == 0) {
      return new NTRUSigningKeyGenerationParameters(this.N, this.q, this.d, this.B, this.basisType, this.beta, this.normBound, this.keyNormBound, this.primeCheck, this.sparse, this.keyGenAlg, this.hashAlg);
    }
    return new NTRUSigningKeyGenerationParameters(this.N, this.q, this.d1, this.d2, this.d3, this.B, this.basisType, this.beta, this.normBound, this.keyNormBound, this.primeCheck, this.sparse, this.keyGenAlg, this.hashAlg);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUSigningKeyGenerationParameters localNTRUSigningKeyGenerationParameters;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof NTRUSigningKeyGenerationParameters)) {
        return false;
      }
      localNTRUSigningKeyGenerationParameters = (NTRUSigningKeyGenerationParameters)paramObject;
      if (this.B != localNTRUSigningKeyGenerationParameters.B) {
        return false;
      }
      if (this.N != localNTRUSigningKeyGenerationParameters.N) {
        return false;
      }
      if (this.basisType != localNTRUSigningKeyGenerationParameters.basisType) {
        return false;
      }
      if (Double.doubleToLongBits(this.beta) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.beta)) {
        return false;
      }
      if (Double.doubleToLongBits(this.betaSq) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.betaSq)) {
        return false;
      }
      if (this.bitsF != localNTRUSigningKeyGenerationParameters.bitsF) {
        return false;
      }
      if (this.d != localNTRUSigningKeyGenerationParameters.d) {
        return false;
      }
      if (this.d1 != localNTRUSigningKeyGenerationParameters.d1) {
        return false;
      }
      if (this.d2 != localNTRUSigningKeyGenerationParameters.d2) {
        return false;
      }
      if (this.d3 != localNTRUSigningKeyGenerationParameters.d3) {
        return false;
      }
      if (this.hashAlg == null)
      {
        if (localNTRUSigningKeyGenerationParameters.hashAlg != null) {
          return false;
        }
      }
      else if (!this.hashAlg.getAlgorithmName().equals(localNTRUSigningKeyGenerationParameters.hashAlg.getAlgorithmName())) {
        return false;
      }
      if (this.keyGenAlg != localNTRUSigningKeyGenerationParameters.keyGenAlg) {
        return false;
      }
      if (Double.doubleToLongBits(this.keyNormBound) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.keyNormBound)) {
        return false;
      }
      if (Double.doubleToLongBits(this.keyNormBoundSq) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.keyNormBoundSq)) {
        return false;
      }
      if (Double.doubleToLongBits(this.normBound) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.normBound)) {
        return false;
      }
      if (Double.doubleToLongBits(this.normBoundSq) != Double.doubleToLongBits(localNTRUSigningKeyGenerationParameters.normBoundSq)) {
        return false;
      }
      if (this.polyType != localNTRUSigningKeyGenerationParameters.polyType) {
        return false;
      }
      if (this.primeCheck != localNTRUSigningKeyGenerationParameters.primeCheck) {
        return false;
      }
      if (this.q != localNTRUSigningKeyGenerationParameters.q) {
        return false;
      }
      if (this.signFailTolerance != localNTRUSigningKeyGenerationParameters.signFailTolerance) {
        return false;
      }
    } while (this.sparse == localNTRUSigningKeyGenerationParameters.sparse);
    return false;
  }
  
  public NTRUSigningParameters getSigningParameters()
  {
    return new NTRUSigningParameters(this.N, this.q, this.d, this.B, this.beta, this.normBound, this.hashAlg);
  }
  
  public int hashCode()
  {
    int i = 1231;
    int j = 31 * (31 * (31 + this.B) + this.N) + this.basisType;
    long l1 = Double.doubleToLongBits(this.beta);
    int k = j * 31 + (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.betaSq);
    int m = 31 * (31 * (31 * (31 * (31 * (31 * (k * 31 + (int)(l2 ^ l2 >>> 32)) + this.bitsF) + this.d) + this.d1) + this.d2) + this.d3);
    int n;
    int i6;
    label269:
    int i7;
    if (this.hashAlg == null)
    {
      n = 0;
      int i1 = 31 * (m + n) + this.keyGenAlg;
      long l3 = Double.doubleToLongBits(this.keyNormBound);
      int i2 = i1 * 31 + (int)(l3 ^ l3 >>> 32);
      long l4 = Double.doubleToLongBits(this.keyNormBoundSq);
      int i3 = i2 * 31 + (int)(l4 ^ l4 >>> 32);
      long l5 = Double.doubleToLongBits(this.normBound);
      int i4 = i3 * 31 + (int)(l5 ^ l5 >>> 32);
      long l6 = Double.doubleToLongBits(this.normBoundSq);
      int i5 = 31 * (31 * (i4 * 31 + (int)(l6 ^ l6 >>> 32)) + this.polyType);
      if (!this.primeCheck) {
        break label324;
      }
      i6 = i;
      i7 = 31 * (31 * (31 * (i5 + i6) + this.q) + this.signFailTolerance);
      if (!this.sparse) {
        break label332;
      }
    }
    for (;;)
    {
      return i7 + i;
      n = this.hashAlg.getAlgorithmName().hashCode();
      break;
      label324:
      i6 = 1237;
      break label269;
      label332:
      i = 1237;
    }
  }
  
  public String toString()
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("0.00");
    StringBuilder localStringBuilder = new StringBuilder("SignatureParameters(N=" + this.N + " q=" + this.q);
    if (this.polyType == 0) {
      localStringBuilder.append(" polyType=SIMPLE d=" + this.d);
    }
    for (;;)
    {
      localStringBuilder.append(" B=" + this.B + " basisType=" + this.basisType + " beta=" + localDecimalFormat.format(this.beta) + " normBound=" + localDecimalFormat.format(this.normBound) + " keyNormBound=" + localDecimalFormat.format(this.keyNormBound) + " prime=" + this.primeCheck + " sparse=" + this.sparse + " keyGenAlg=" + this.keyGenAlg + " hashAlg=" + this.hashAlg + ")");
      return localStringBuilder.toString();
      localStringBuilder.append(" polyType=PRODUCT d1=" + this.d1 + " d2=" + this.d2 + " d3=" + this.d3);
    }
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    localDataOutputStream.writeInt(this.N);
    localDataOutputStream.writeInt(this.q);
    localDataOutputStream.writeInt(this.d);
    localDataOutputStream.writeInt(this.d1);
    localDataOutputStream.writeInt(this.d2);
    localDataOutputStream.writeInt(this.d3);
    localDataOutputStream.writeInt(this.B);
    localDataOutputStream.writeInt(this.basisType);
    localDataOutputStream.writeDouble(this.beta);
    localDataOutputStream.writeDouble(this.normBound);
    localDataOutputStream.writeDouble(this.keyNormBound);
    localDataOutputStream.writeInt(this.signFailTolerance);
    localDataOutputStream.writeBoolean(this.primeCheck);
    localDataOutputStream.writeBoolean(this.sparse);
    localDataOutputStream.writeInt(this.bitsF);
    localDataOutputStream.write(this.keyGenAlg);
    localDataOutputStream.writeUTF(this.hashAlg.getAlgorithmName());
    localDataOutputStream.write(this.polyType);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUSigningKeyGenerationParameters
 * JD-Core Version:    0.7.0.1
 */