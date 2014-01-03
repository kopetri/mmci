package org.spongycastle.crypto.params;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUSigningParameters
  implements Cloneable
{
  public int B;
  public int N;
  double beta;
  public double betaSq;
  int bitsF = 6;
  public int d;
  public int d1;
  public int d2;
  public int d3;
  public Digest hashAlg;
  double normBound;
  public double normBoundSq;
  public int q;
  public int signFailTolerance = 100;
  
  public NTRUSigningParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, double paramDouble1, double paramDouble2, Digest paramDigest)
  {
    this.N = paramInt1;
    this.q = paramInt2;
    this.d = paramInt3;
    this.B = paramInt4;
    this.beta = paramDouble1;
    this.normBound = paramDouble2;
    this.hashAlg = paramDigest;
    init();
  }
  
  public NTRUSigningParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, double paramDouble1, double paramDouble2, double paramDouble3, Digest paramDigest)
  {
    this.N = paramInt1;
    this.q = paramInt2;
    this.d1 = paramInt3;
    this.d2 = paramInt4;
    this.d3 = paramInt5;
    this.B = paramInt6;
    this.beta = paramDouble1;
    this.normBound = paramDouble2;
    this.hashAlg = paramDigest;
    init();
  }
  
  public NTRUSigningParameters(InputStream paramInputStream)
    throws IOException
  {
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    this.N = localDataInputStream.readInt();
    this.q = localDataInputStream.readInt();
    this.d = localDataInputStream.readInt();
    this.d1 = localDataInputStream.readInt();
    this.d2 = localDataInputStream.readInt();
    this.d3 = localDataInputStream.readInt();
    this.B = localDataInputStream.readInt();
    this.beta = localDataInputStream.readDouble();
    this.normBound = localDataInputStream.readDouble();
    this.signFailTolerance = localDataInputStream.readInt();
    this.bitsF = localDataInputStream.readInt();
    String str = localDataInputStream.readUTF();
    if ("SHA-512".equals(str)) {
      this.hashAlg = new SHA512Digest();
    }
    for (;;)
    {
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
  }
  
  public NTRUSigningParameters clone()
  {
    return new NTRUSigningParameters(this.N, this.q, this.d, this.B, this.beta, this.normBound, this.hashAlg);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUSigningParameters localNTRUSigningParameters;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (!(paramObject instanceof NTRUSigningParameters)) {
        return false;
      }
      localNTRUSigningParameters = (NTRUSigningParameters)paramObject;
      if (this.B != localNTRUSigningParameters.B) {
        return false;
      }
      if (this.N != localNTRUSigningParameters.N) {
        return false;
      }
      if (Double.doubleToLongBits(this.beta) != Double.doubleToLongBits(localNTRUSigningParameters.beta)) {
        return false;
      }
      if (Double.doubleToLongBits(this.betaSq) != Double.doubleToLongBits(localNTRUSigningParameters.betaSq)) {
        return false;
      }
      if (this.bitsF != localNTRUSigningParameters.bitsF) {
        return false;
      }
      if (this.d != localNTRUSigningParameters.d) {
        return false;
      }
      if (this.d1 != localNTRUSigningParameters.d1) {
        return false;
      }
      if (this.d2 != localNTRUSigningParameters.d2) {
        return false;
      }
      if (this.d3 != localNTRUSigningParameters.d3) {
        return false;
      }
      if (this.hashAlg == null)
      {
        if (localNTRUSigningParameters.hashAlg != null) {
          return false;
        }
      }
      else if (!this.hashAlg.getAlgorithmName().equals(localNTRUSigningParameters.hashAlg.getAlgorithmName())) {
        return false;
      }
      if (Double.doubleToLongBits(this.normBound) != Double.doubleToLongBits(localNTRUSigningParameters.normBound)) {
        return false;
      }
      if (Double.doubleToLongBits(this.normBoundSq) != Double.doubleToLongBits(localNTRUSigningParameters.normBoundSq)) {
        return false;
      }
      if (this.q != localNTRUSigningParameters.q) {
        return false;
      }
    } while (this.signFailTolerance == localNTRUSigningParameters.signFailTolerance);
    return false;
  }
  
  public int hashCode()
  {
    int i = 31 * (31 + this.B) + this.N;
    long l1 = Double.doubleToLongBits(this.beta);
    int j = i * 31 + (int)(l1 ^ l1 >>> 32);
    long l2 = Double.doubleToLongBits(this.betaSq);
    int k = 31 * (31 * (31 * (31 * (31 * (31 * (j * 31 + (int)(l2 ^ l2 >>> 32)) + this.bitsF) + this.d) + this.d1) + this.d2) + this.d3);
    if (this.hashAlg == null) {}
    for (int m = 0;; m = this.hashAlg.getAlgorithmName().hashCode())
    {
      int n = k + m;
      long l3 = Double.doubleToLongBits(this.normBound);
      int i1 = n * 31 + (int)(l3 ^ l3 >>> 32);
      long l4 = Double.doubleToLongBits(this.normBoundSq);
      return 31 * (31 * (i1 * 31 + (int)(l4 ^ l4 >>> 32)) + this.q) + this.signFailTolerance;
    }
  }
  
  public String toString()
  {
    DecimalFormat localDecimalFormat = new DecimalFormat("0.00");
    StringBuilder localStringBuilder = new StringBuilder("SignatureParameters(N=" + this.N + " q=" + this.q);
    localStringBuilder.append(" B=" + this.B + " beta=" + localDecimalFormat.format(this.beta) + " normBound=" + localDecimalFormat.format(this.normBound) + " hashAlg=" + this.hashAlg + ")");
    return localStringBuilder.toString();
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
    localDataOutputStream.writeDouble(this.beta);
    localDataOutputStream.writeDouble(this.normBound);
    localDataOutputStream.writeInt(this.signFailTolerance);
    localDataOutputStream.writeInt(this.bitsF);
    localDataOutputStream.writeUTF(this.hashAlg.getAlgorithmName());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUSigningParameters
 * JD-Core Version:    0.7.0.1
 */