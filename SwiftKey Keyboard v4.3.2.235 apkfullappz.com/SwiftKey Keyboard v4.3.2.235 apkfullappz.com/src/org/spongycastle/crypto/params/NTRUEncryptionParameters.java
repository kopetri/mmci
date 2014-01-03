package org.spongycastle.crypto.params;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.SHA256Digest;
import org.spongycastle.crypto.digests.SHA512Digest;

public class NTRUEncryptionParameters
  implements Cloneable
{
  public int N;
  public int bufferLenBits;
  int bufferLenTrits;
  public int c;
  public int db;
  public int df;
  public int df1;
  public int df2;
  public int df3;
  public int dg;
  public int dm0;
  public int dr;
  public int dr1;
  public int dr2;
  public int dr3;
  public boolean fastFp;
  public Digest hashAlg;
  public boolean hashSeed;
  int llen;
  public int maxMsgLenBytes;
  public int minCallsMask;
  public int minCallsR;
  public byte[] oid;
  public int pkLen;
  public int polyType;
  public int q;
  public boolean sparse;
  
  public NTRUEncryptionParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, int paramInt9, int paramInt10, boolean paramBoolean1, byte[] paramArrayOfByte, boolean paramBoolean2, boolean paramBoolean3, Digest paramDigest)
  {
    this.N = paramInt1;
    this.q = paramInt2;
    this.df1 = paramInt3;
    this.df2 = paramInt4;
    this.df3 = paramInt5;
    this.db = paramInt7;
    this.dm0 = paramInt6;
    this.c = paramInt8;
    this.minCallsR = paramInt9;
    this.minCallsMask = paramInt10;
    this.hashSeed = paramBoolean1;
    this.oid = paramArrayOfByte;
    this.sparse = paramBoolean2;
    this.fastFp = paramBoolean3;
    this.polyType = 1;
    this.hashAlg = paramDigest;
    init();
  }
  
  public NTRUEncryptionParameters(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7, int paramInt8, boolean paramBoolean1, byte[] paramArrayOfByte, boolean paramBoolean2, boolean paramBoolean3, Digest paramDigest)
  {
    this.N = paramInt1;
    this.q = paramInt2;
    this.df = paramInt3;
    this.db = paramInt5;
    this.dm0 = paramInt4;
    this.c = paramInt6;
    this.minCallsR = paramInt7;
    this.minCallsMask = paramInt8;
    this.hashSeed = paramBoolean1;
    this.oid = paramArrayOfByte;
    this.sparse = paramBoolean2;
    this.fastFp = paramBoolean3;
    this.polyType = 0;
    this.hashAlg = paramDigest;
    init();
  }
  
  public NTRUEncryptionParameters(InputStream paramInputStream)
    throws IOException
  {
    DataInputStream localDataInputStream = new DataInputStream(paramInputStream);
    this.N = localDataInputStream.readInt();
    this.q = localDataInputStream.readInt();
    this.df = localDataInputStream.readInt();
    this.df1 = localDataInputStream.readInt();
    this.df2 = localDataInputStream.readInt();
    this.df3 = localDataInputStream.readInt();
    this.db = localDataInputStream.readInt();
    this.dm0 = localDataInputStream.readInt();
    this.c = localDataInputStream.readInt();
    this.minCallsR = localDataInputStream.readInt();
    this.minCallsMask = localDataInputStream.readInt();
    this.hashSeed = localDataInputStream.readBoolean();
    this.oid = new byte[3];
    localDataInputStream.read(this.oid);
    this.sparse = localDataInputStream.readBoolean();
    this.fastFp = localDataInputStream.readBoolean();
    this.polyType = localDataInputStream.read();
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
    this.dr = this.df;
    this.dr1 = this.df1;
    this.dr2 = this.df2;
    this.dr3 = this.df3;
    this.dg = (this.N / 3);
    this.llen = 1;
    this.maxMsgLenBytes = (-1 + (3 * this.N / 2 / 8 - this.llen - this.db / 8));
    this.bufferLenBits = (1 + 8 * ((7 + 3 * this.N / 2) / 8));
    this.bufferLenTrits = (-1 + this.N);
    this.pkLen = this.db;
  }
  
  public NTRUEncryptionParameters clone()
  {
    if (this.polyType == 0) {
      return new NTRUEncryptionParameters(this.N, this.q, this.df, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, this.hashAlg);
    }
    return new NTRUEncryptionParameters(this.N, this.q, this.df1, this.df2, this.df3, this.dm0, this.db, this.c, this.minCallsR, this.minCallsMask, this.hashSeed, this.oid, this.sparse, this.fastFp, this.hashAlg);
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    NTRUEncryptionParameters localNTRUEncryptionParameters;
    do
    {
      return true;
      if (paramObject == null) {
        return false;
      }
      if (getClass() != paramObject.getClass()) {
        return false;
      }
      localNTRUEncryptionParameters = (NTRUEncryptionParameters)paramObject;
      if (this.N != localNTRUEncryptionParameters.N) {
        return false;
      }
      if (this.bufferLenBits != localNTRUEncryptionParameters.bufferLenBits) {
        return false;
      }
      if (this.bufferLenTrits != localNTRUEncryptionParameters.bufferLenTrits) {
        return false;
      }
      if (this.c != localNTRUEncryptionParameters.c) {
        return false;
      }
      if (this.db != localNTRUEncryptionParameters.db) {
        return false;
      }
      if (this.df != localNTRUEncryptionParameters.df) {
        return false;
      }
      if (this.df1 != localNTRUEncryptionParameters.df1) {
        return false;
      }
      if (this.df2 != localNTRUEncryptionParameters.df2) {
        return false;
      }
      if (this.df3 != localNTRUEncryptionParameters.df3) {
        return false;
      }
      if (this.dg != localNTRUEncryptionParameters.dg) {
        return false;
      }
      if (this.dm0 != localNTRUEncryptionParameters.dm0) {
        return false;
      }
      if (this.dr != localNTRUEncryptionParameters.dr) {
        return false;
      }
      if (this.dr1 != localNTRUEncryptionParameters.dr1) {
        return false;
      }
      if (this.dr2 != localNTRUEncryptionParameters.dr2) {
        return false;
      }
      if (this.dr3 != localNTRUEncryptionParameters.dr3) {
        return false;
      }
      if (this.fastFp != localNTRUEncryptionParameters.fastFp) {
        return false;
      }
      if (this.hashAlg == null)
      {
        if (localNTRUEncryptionParameters.hashAlg != null) {
          return false;
        }
      }
      else if (!this.hashAlg.getAlgorithmName().equals(localNTRUEncryptionParameters.hashAlg.getAlgorithmName())) {
        return false;
      }
      if (this.hashSeed != localNTRUEncryptionParameters.hashSeed) {
        return false;
      }
      if (this.llen != localNTRUEncryptionParameters.llen) {
        return false;
      }
      if (this.maxMsgLenBytes != localNTRUEncryptionParameters.maxMsgLenBytes) {
        return false;
      }
      if (this.minCallsMask != localNTRUEncryptionParameters.minCallsMask) {
        return false;
      }
      if (this.minCallsR != localNTRUEncryptionParameters.minCallsR) {
        return false;
      }
      if (!Arrays.equals(this.oid, localNTRUEncryptionParameters.oid)) {
        return false;
      }
      if (this.pkLen != localNTRUEncryptionParameters.pkLen) {
        return false;
      }
      if (this.polyType != localNTRUEncryptionParameters.polyType) {
        return false;
      }
      if (this.q != localNTRUEncryptionParameters.q) {
        return false;
      }
    } while (this.sparse == localNTRUEncryptionParameters.sparse);
    return false;
  }
  
  public int getMaxMessageLength()
  {
    return this.maxMsgLenBytes;
  }
  
  public int hashCode()
  {
    int i = 1231;
    int j = 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 + this.N) + this.bufferLenBits) + this.bufferLenTrits) + this.c) + this.db) + this.df) + this.df1) + this.df2) + this.df3) + this.dg) + this.dm0) + this.dr) + this.dr1) + this.dr2) + this.dr3);
    int k;
    int n;
    label154:
    int i2;
    label174:
    int i3;
    if (this.fastFp)
    {
      k = i;
      int m = 31 * (j + k);
      if (this.hashAlg != null) {
        break label270;
      }
      n = 0;
      int i1 = 31 * (m + n);
      if (!this.hashSeed) {
        break label287;
      }
      i2 = i;
      i3 = 31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (31 * (i1 + i2) + this.llen) + this.maxMsgLenBytes) + this.minCallsMask) + this.minCallsR) + Arrays.hashCode(this.oid)) + this.pkLen) + this.polyType) + this.q);
      if (!this.sparse) {
        break label295;
      }
    }
    for (;;)
    {
      return i3 + i;
      k = 1237;
      break;
      label270:
      n = this.hashAlg.getAlgorithmName().hashCode();
      break label154;
      label287:
      i2 = 1237;
      break label174;
      label295:
      i = 1237;
    }
  }
  
  public String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder("EncryptionParameters(N=" + this.N + " q=" + this.q);
    if (this.polyType == 0) {
      localStringBuilder.append(" polyType=SIMPLE df=" + this.df);
    }
    for (;;)
    {
      localStringBuilder.append(" dm0=" + this.dm0 + " db=" + this.db + " c=" + this.c + " minCallsR=" + this.minCallsR + " minCallsMask=" + this.minCallsMask + " hashSeed=" + this.hashSeed + " hashAlg=" + this.hashAlg + " oid=" + Arrays.toString(this.oid) + " sparse=" + this.sparse + ")");
      return localStringBuilder.toString();
      localStringBuilder.append(" polyType=PRODUCT df1=" + this.df1 + " df2=" + this.df2 + " df3=" + this.df3);
    }
  }
  
  public void writeTo(OutputStream paramOutputStream)
    throws IOException
  {
    DataOutputStream localDataOutputStream = new DataOutputStream(paramOutputStream);
    localDataOutputStream.writeInt(this.N);
    localDataOutputStream.writeInt(this.q);
    localDataOutputStream.writeInt(this.df);
    localDataOutputStream.writeInt(this.df1);
    localDataOutputStream.writeInt(this.df2);
    localDataOutputStream.writeInt(this.df3);
    localDataOutputStream.writeInt(this.db);
    localDataOutputStream.writeInt(this.dm0);
    localDataOutputStream.writeInt(this.c);
    localDataOutputStream.writeInt(this.minCallsR);
    localDataOutputStream.writeInt(this.minCallsMask);
    localDataOutputStream.writeBoolean(this.hashSeed);
    localDataOutputStream.write(this.oid);
    localDataOutputStream.writeBoolean(this.sparse);
    localDataOutputStream.writeBoolean(this.fastFp);
    localDataOutputStream.write(this.polyType);
    localDataOutputStream.writeUTF(this.hashAlg.getAlgorithmName());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.params.NTRUEncryptionParameters
 * JD-Core Version:    0.7.0.1
 */