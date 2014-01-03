package org.spongycastle.crypto.tls;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.asn1.x509.TBSCertificateStructure;
import org.spongycastle.asn1.x509.X509CertificateStructure;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.asn1.x509.X509Extensions;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.digests.MD5Digest;
import org.spongycastle.crypto.digests.SHA1Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;
import org.spongycastle.util.io.Streams;

public class TlsUtils
{
  static final byte[][] SSL3_CONST = genConst();
  static final byte[] SSL_CLIENT = { 67, 76, 78, 84 };
  static final byte[] SSL_SERVER = { 83, 82, 86, 82 };
  
  protected static byte[] PRF(byte[] paramArrayOfByte1, String paramString, byte[] paramArrayOfByte2, int paramInt)
  {
    byte[] arrayOfByte1 = Strings.toByteArray(paramString);
    int i = (1 + paramArrayOfByte1.length) / 2;
    byte[] arrayOfByte2 = new byte[i];
    byte[] arrayOfByte3 = new byte[i];
    System.arraycopy(paramArrayOfByte1, 0, arrayOfByte2, 0, i);
    System.arraycopy(paramArrayOfByte1, paramArrayOfByte1.length - i, arrayOfByte3, 0, i);
    byte[] arrayOfByte4 = concat(arrayOfByte1, paramArrayOfByte2);
    byte[] arrayOfByte5 = new byte[paramInt];
    byte[] arrayOfByte6 = new byte[paramInt];
    hmac_hash(new MD5Digest(), arrayOfByte2, arrayOfByte4, arrayOfByte6);
    hmac_hash(new SHA1Digest(), arrayOfByte3, arrayOfByte4, arrayOfByte5);
    for (int j = 0; j < paramInt; j++) {
      arrayOfByte5[j] = ((byte)(arrayOfByte5[j] ^ arrayOfByte6[j]));
    }
    return arrayOfByte5;
  }
  
  static byte[] PRF_1_2(Digest paramDigest, byte[] paramArrayOfByte1, String paramString, byte[] paramArrayOfByte2, int paramInt)
  {
    byte[] arrayOfByte1 = concat(Strings.toByteArray(paramString), paramArrayOfByte2);
    byte[] arrayOfByte2 = new byte[paramInt];
    hmac_hash(paramDigest, paramArrayOfByte1, arrayOfByte1, arrayOfByte2);
    return arrayOfByte2;
  }
  
  static byte[] calculateKeyBlock(TlsClientContext paramTlsClientContext, int paramInt)
  {
    ProtocolVersion localProtocolVersion = paramTlsClientContext.getServerVersion();
    SecurityParameters localSecurityParameters = paramTlsClientContext.getSecurityParameters();
    byte[] arrayOfByte1 = concat(localSecurityParameters.serverRandom, localSecurityParameters.clientRandom);
    if (localProtocolVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {}
    for (int i = 1; i != 0; i = 0) {
      return PRF(localSecurityParameters.masterSecret, "key expansion", arrayOfByte1, paramInt);
    }
    MD5Digest localMD5Digest = new MD5Digest();
    SHA1Digest localSHA1Digest = new SHA1Digest();
    int j = localMD5Digest.getDigestSize();
    byte[] arrayOfByte2 = new byte[localSHA1Digest.getDigestSize()];
    byte[] arrayOfByte3 = new byte[paramInt + j];
    int k = 0;
    int m = 0;
    while (m < paramInt)
    {
      byte[] arrayOfByte5 = SSL3_CONST[k];
      localSHA1Digest.update(arrayOfByte5, 0, arrayOfByte5.length);
      localSHA1Digest.update(localSecurityParameters.masterSecret, 0, localSecurityParameters.masterSecret.length);
      localSHA1Digest.update(arrayOfByte1, 0, arrayOfByte1.length);
      localSHA1Digest.doFinal(arrayOfByte2, 0);
      localMD5Digest.update(localSecurityParameters.masterSecret, 0, localSecurityParameters.masterSecret.length);
      localMD5Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
      localMD5Digest.doFinal(arrayOfByte3, m);
      m += j;
      k++;
    }
    byte[] arrayOfByte4 = new byte[paramInt];
    System.arraycopy(arrayOfByte3, 0, arrayOfByte4, 0, paramInt);
    return arrayOfByte4;
  }
  
  static byte[] calculateMasterSecret(TlsClientContext paramTlsClientContext, byte[] paramArrayOfByte)
  {
    ProtocolVersion localProtocolVersion = paramTlsClientContext.getServerVersion();
    SecurityParameters localSecurityParameters = paramTlsClientContext.getSecurityParameters();
    byte[] arrayOfByte1 = concat(localSecurityParameters.clientRandom, localSecurityParameters.serverRandom);
    int i;
    byte[] arrayOfByte3;
    if (localProtocolVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion())
    {
      i = 1;
      if (i == 0) {
        break label69;
      }
      arrayOfByte3 = PRF(paramArrayOfByte, "master secret", arrayOfByte1, 48);
    }
    for (;;)
    {
      return arrayOfByte3;
      i = 0;
      break;
      label69:
      MD5Digest localMD5Digest = new MD5Digest();
      SHA1Digest localSHA1Digest = new SHA1Digest();
      int j = localMD5Digest.getDigestSize();
      byte[] arrayOfByte2 = new byte[localSHA1Digest.getDigestSize()];
      arrayOfByte3 = new byte[j * 3];
      int k = 0;
      for (int m = 0; m < 3; m++)
      {
        byte[] arrayOfByte4 = SSL3_CONST[m];
        localSHA1Digest.update(arrayOfByte4, 0, arrayOfByte4.length);
        localSHA1Digest.update(paramArrayOfByte, 0, paramArrayOfByte.length);
        localSHA1Digest.update(arrayOfByte1, 0, arrayOfByte1.length);
        localSHA1Digest.doFinal(arrayOfByte2, 0);
        localMD5Digest.update(paramArrayOfByte, 0, paramArrayOfByte.length);
        localMD5Digest.update(arrayOfByte2, 0, arrayOfByte2.length);
        localMD5Digest.doFinal(arrayOfByte3, k);
        k += j;
      }
    }
  }
  
  static byte[] calculateVerifyData(TlsClientContext paramTlsClientContext, String paramString, byte[] paramArrayOfByte)
  {
    ProtocolVersion localProtocolVersion = paramTlsClientContext.getServerVersion();
    SecurityParameters localSecurityParameters = paramTlsClientContext.getSecurityParameters();
    if (localProtocolVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {}
    for (int i = 1;; i = 0)
    {
      if (i != 0) {
        paramArrayOfByte = PRF(localSecurityParameters.masterSecret, paramString, paramArrayOfByte, 12);
      }
      return paramArrayOfByte;
    }
  }
  
  static byte[] concat(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    byte[] arrayOfByte = new byte[paramArrayOfByte1.length + paramArrayOfByte2.length];
    System.arraycopy(paramArrayOfByte1, 0, arrayOfByte, 0, paramArrayOfByte1.length);
    System.arraycopy(paramArrayOfByte2, 0, arrayOfByte, paramArrayOfByte1.length, paramArrayOfByte2.length);
    return arrayOfByte;
  }
  
  private static byte[][] genConst()
  {
    byte[][] arrayOfByte = new byte[10][];
    for (int i = 0; i < 10; i++)
    {
      byte[] arrayOfByte1 = new byte[i + 1];
      Arrays.fill(arrayOfByte1, (byte)(i + 65));
      arrayOfByte[i] = arrayOfByte1;
    }
    return arrayOfByte;
  }
  
  private static void hmac_hash(Digest paramDigest, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, byte[] paramArrayOfByte3)
  {
    HMac localHMac = new HMac(paramDigest);
    KeyParameter localKeyParameter = new KeyParameter(paramArrayOfByte1);
    Object localObject = paramArrayOfByte2;
    int i = paramDigest.getDigestSize();
    int j = (-1 + (i + paramArrayOfByte3.length)) / i;
    byte[] arrayOfByte1 = new byte[localHMac.getMacSize()];
    byte[] arrayOfByte2 = new byte[localHMac.getMacSize()];
    for (int k = 0; k < j; k++)
    {
      localHMac.init(localKeyParameter);
      localHMac.update((byte[])localObject, 0, localObject.length);
      localHMac.doFinal(arrayOfByte1, 0);
      localObject = arrayOfByte1;
      localHMac.init(localKeyParameter);
      localHMac.update((byte[])localObject, 0, localObject.length);
      localHMac.update(paramArrayOfByte2, 0, paramArrayOfByte2.length);
      localHMac.doFinal(arrayOfByte2, 0);
      System.arraycopy(arrayOfByte2, 0, paramArrayOfByte3, i * k, Math.min(i, paramArrayOfByte3.length - i * k));
    }
  }
  
  protected static void readFully(byte[] paramArrayOfByte, InputStream paramInputStream)
    throws IOException
  {
    if (Streams.readFully(paramInputStream, paramArrayOfByte) != paramArrayOfByte.length) {
      throw new EOFException();
    }
  }
  
  protected static byte[] readOpaque16(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[readUint16(paramInputStream)];
    readFully(arrayOfByte, paramInputStream);
    return arrayOfByte;
  }
  
  protected static byte[] readOpaque8(InputStream paramInputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[readUint8(paramInputStream)];
    readFully(arrayOfByte, paramInputStream);
    return arrayOfByte;
  }
  
  protected static int readUint16(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    int j = paramInputStream.read();
    if ((i | j) < 0) {
      throw new EOFException();
    }
    return j | i << 8;
  }
  
  protected static int readUint24(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    int j = paramInputStream.read();
    int k = paramInputStream.read();
    if ((k | i | j) < 0) {
      throw new EOFException();
    }
    return k | i << 16 | j << 8;
  }
  
  protected static long readUint32(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    int j = paramInputStream.read();
    int k = paramInputStream.read();
    int m = paramInputStream.read();
    if ((m | k | i | j) < 0) {
      throw new EOFException();
    }
    return i << 24 | j << 16 | k << 8 | m;
  }
  
  protected static short readUint8(InputStream paramInputStream)
    throws IOException
  {
    int i = paramInputStream.read();
    if (i == -1) {
      throw new EOFException();
    }
    return (short)i;
  }
  
  static ProtocolVersion readVersion(InputStream paramInputStream)
    throws IOException
  {
    return ProtocolVersion.get(paramInputStream.read(), paramInputStream.read());
  }
  
  static ProtocolVersion readVersion(byte[] paramArrayOfByte)
    throws IOException
  {
    return ProtocolVersion.get(paramArrayOfByte[0], paramArrayOfByte[1]);
  }
  
  static void validateKeyUsage(X509CertificateStructure paramX509CertificateStructure, int paramInt)
    throws IOException
  {
    X509Extensions localX509Extensions = paramX509CertificateStructure.getTBSCertificate().getExtensions();
    if (localX509Extensions != null)
    {
      X509Extension localX509Extension = localX509Extensions.getExtension(X509Extension.keyUsage);
      if ((localX509Extension != null) && ((paramInt & 0xFF & org.spongycastle.asn1.x509.KeyUsage.getInstance(localX509Extension).getBytes()[0]) != paramInt)) {
        throw new TlsFatalAlert((short)46);
      }
    }
  }
  
  protected static void writeGMTUnixTime(byte[] paramArrayOfByte, int paramInt)
  {
    int i = (int)(System.currentTimeMillis() / 1000L);
    paramArrayOfByte[paramInt] = ((byte)(i >> 24));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(i >> 16));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(i >> 8));
    paramArrayOfByte[(paramInt + 3)] = ((byte)i);
  }
  
  protected static void writeOpaque16(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    writeUint16(paramArrayOfByte.length, paramOutputStream);
    paramOutputStream.write(paramArrayOfByte);
  }
  
  protected static void writeOpaque24(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    writeUint24(paramArrayOfByte.length, paramOutputStream);
    paramOutputStream.write(paramArrayOfByte);
  }
  
  protected static void writeOpaque8(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    writeUint8((short)paramArrayOfByte.length, paramOutputStream);
    paramOutputStream.write(paramArrayOfByte);
  }
  
  protected static void writeUint16(int paramInt, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(paramInt >> 8);
    paramOutputStream.write(paramInt);
  }
  
  protected static void writeUint16(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[paramInt2] = ((byte)(paramInt1 >> 8));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)paramInt1);
  }
  
  protected static void writeUint16Array(int[] paramArrayOfInt, OutputStream paramOutputStream)
    throws IOException
  {
    for (int i = 0; i < paramArrayOfInt.length; i++) {
      writeUint16(paramArrayOfInt[i], paramOutputStream);
    }
  }
  
  protected static void writeUint24(int paramInt, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(paramInt >> 16);
    paramOutputStream.write(paramInt >> 8);
    paramOutputStream.write(paramInt);
  }
  
  protected static void writeUint24(int paramInt1, byte[] paramArrayOfByte, int paramInt2)
  {
    paramArrayOfByte[paramInt2] = ((byte)(paramInt1 >> 16));
    paramArrayOfByte[(paramInt2 + 1)] = ((byte)(paramInt1 >> 8));
    paramArrayOfByte[(paramInt2 + 2)] = ((byte)paramInt1);
  }
  
  protected static void writeUint32(long paramLong, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write((int)(paramLong >> 24));
    paramOutputStream.write((int)(paramLong >> 16));
    paramOutputStream.write((int)(paramLong >> 8));
    paramOutputStream.write((int)paramLong);
  }
  
  protected static void writeUint32(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[paramInt] = ((byte)(int)(paramLong >> 24));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >> 16));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >> 8));
    paramArrayOfByte[(paramInt + 3)] = ((byte)(int)paramLong);
  }
  
  protected static void writeUint64(long paramLong, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write((int)(paramLong >> 56));
    paramOutputStream.write((int)(paramLong >> 48));
    paramOutputStream.write((int)(paramLong >> 40));
    paramOutputStream.write((int)(paramLong >> 32));
    paramOutputStream.write((int)(paramLong >> 24));
    paramOutputStream.write((int)(paramLong >> 16));
    paramOutputStream.write((int)(paramLong >> 8));
    paramOutputStream.write((int)paramLong);
  }
  
  protected static void writeUint64(long paramLong, byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[paramInt] = ((byte)(int)(paramLong >> 56));
    paramArrayOfByte[(paramInt + 1)] = ((byte)(int)(paramLong >> 48));
    paramArrayOfByte[(paramInt + 2)] = ((byte)(int)(paramLong >> 40));
    paramArrayOfByte[(paramInt + 3)] = ((byte)(int)(paramLong >> 32));
    paramArrayOfByte[(paramInt + 4)] = ((byte)(int)(paramLong >> 24));
    paramArrayOfByte[(paramInt + 5)] = ((byte)(int)(paramLong >> 16));
    paramArrayOfByte[(paramInt + 6)] = ((byte)(int)(paramLong >> 8));
    paramArrayOfByte[(paramInt + 7)] = ((byte)(int)paramLong);
  }
  
  protected static void writeUint8(short paramShort, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(paramShort);
  }
  
  protected static void writeUint8(short paramShort, byte[] paramArrayOfByte, int paramInt)
  {
    paramArrayOfByte[paramInt] = ((byte)paramShort);
  }
  
  protected static void writeUint8Array(short[] paramArrayOfShort, OutputStream paramOutputStream)
    throws IOException
  {
    for (int i = 0; i < paramArrayOfShort.length; i++) {
      writeUint8(paramArrayOfShort[i], paramOutputStream);
    }
  }
  
  static void writeVersion(ProtocolVersion paramProtocolVersion, OutputStream paramOutputStream)
    throws IOException
  {
    paramOutputStream.write(paramProtocolVersion.getMajorVersion());
    paramOutputStream.write(paramProtocolVersion.getMinorVersion());
  }
  
  static void writeVersion(ProtocolVersion paramProtocolVersion, byte[] paramArrayOfByte, int paramInt)
    throws IOException
  {
    paramArrayOfByte[paramInt] = ((byte)paramProtocolVersion.getMajorVersion());
    paramArrayOfByte[(paramInt + 1)] = ((byte)paramProtocolVersion.getMinorVersion());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsUtils
 * JD-Core Version:    0.7.0.1
 */