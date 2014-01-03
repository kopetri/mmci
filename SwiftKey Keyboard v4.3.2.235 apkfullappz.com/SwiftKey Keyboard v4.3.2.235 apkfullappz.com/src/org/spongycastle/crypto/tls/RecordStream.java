package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.spongycastle.crypto.Digest;

class RecordStream
{
  private ByteArrayOutputStream buffer = new ByteArrayOutputStream();
  private TlsClientContext context = null;
  private TlsProtocolHandler handler;
  private CombinedHash hash = null;
  private InputStream is;
  private OutputStream os;
  private TlsCipher readCipher = null;
  private TlsCompression readCompression = null;
  private TlsCipher writeCipher = null;
  private TlsCompression writeCompression = null;
  
  RecordStream(TlsProtocolHandler paramTlsProtocolHandler, InputStream paramInputStream, OutputStream paramOutputStream)
  {
    this.handler = paramTlsProtocolHandler;
    this.is = paramInputStream;
    this.os = paramOutputStream;
    this.readCompression = new TlsNullCompression();
    this.writeCompression = this.readCompression;
    this.readCipher = new TlsNullCipher();
    this.writeCipher = this.readCipher;
  }
  
  private static byte[] doFinal(Digest paramDigest)
  {
    byte[] arrayOfByte = new byte[paramDigest.getDigestSize()];
    paramDigest.doFinal(arrayOfByte, 0);
    return arrayOfByte;
  }
  
  private byte[] getBufferContents()
  {
    byte[] arrayOfByte = this.buffer.toByteArray();
    this.buffer.reset();
    return arrayOfByte;
  }
  
  void clientCipherSpecDecided(TlsCompression paramTlsCompression, TlsCipher paramTlsCipher)
  {
    this.writeCompression = paramTlsCompression;
    this.writeCipher = paramTlsCipher;
  }
  
  protected void close()
    throws IOException
  {
    Object localObject = null;
    try
    {
      this.is.close();
    }
    catch (IOException localIOException1)
    {
      try
      {
        for (;;)
        {
          this.os.close();
          label16:
          if (localObject == null) {
            break;
          }
          throw localObject;
          localIOException1 = localIOException1;
        }
      }
      catch (IOException localIOException2)
      {
        break label16;
      }
    }
  }
  
  protected byte[] decodeAndVerify(short paramShort, InputStream paramInputStream, int paramInt)
    throws IOException
  {
    byte[] arrayOfByte1 = new byte[paramInt];
    TlsUtils.readFully(arrayOfByte1, paramInputStream);
    byte[] arrayOfByte2 = this.readCipher.decodeCiphertext(paramShort, arrayOfByte1, 0, arrayOfByte1.length);
    OutputStream localOutputStream = this.readCompression.decompress(this.buffer);
    if (localOutputStream == this.buffer) {
      return arrayOfByte2;
    }
    localOutputStream.write(arrayOfByte2, 0, arrayOfByte2.length);
    localOutputStream.flush();
    return getBufferContents();
  }
  
  protected void flush()
    throws IOException
  {
    this.os.flush();
  }
  
  byte[] getCurrentHash(byte[] paramArrayOfByte)
  {
    CombinedHash localCombinedHash = new CombinedHash(this.hash);
    if (this.context.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion()) {}
    for (int i = 1;; i = 0)
    {
      if ((i == 0) && (paramArrayOfByte != null)) {
        localCombinedHash.update(paramArrayOfByte, 0, paramArrayOfByte.length);
      }
      return doFinal(localCombinedHash);
    }
  }
  
  void init(TlsClientContext paramTlsClientContext)
  {
    this.context = paramTlsClientContext;
    this.hash = new CombinedHash(paramTlsClientContext);
  }
  
  public void readData()
    throws IOException
  {
    short s = TlsUtils.readUint8(this.is);
    if (!ProtocolVersion.TLSv10.equals(TlsUtils.readVersion(this.is))) {
      throw new TlsFatalAlert((short)47);
    }
    int i = TlsUtils.readUint16(this.is);
    byte[] arrayOfByte = decodeAndVerify(s, this.is, i);
    this.handler.processData(s, arrayOfByte, 0, arrayOfByte.length);
  }
  
  void serverClientSpecReceived()
  {
    this.readCompression = this.writeCompression;
    this.readCipher = this.writeCipher;
  }
  
  void updateHandshakeData(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.hash.update(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  protected void writeMessage(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramShort == 22) {
      updateHandshakeData(paramArrayOfByte, paramInt1, paramInt2);
    }
    OutputStream localOutputStream = this.writeCompression.compress(this.buffer);
    if (localOutputStream == this.buffer) {}
    byte[] arrayOfByte1;
    for (byte[] arrayOfByte2 = this.writeCipher.encodePlaintext(paramShort, paramArrayOfByte, paramInt1, paramInt2);; arrayOfByte2 = this.writeCipher.encodePlaintext(paramShort, arrayOfByte1, 0, arrayOfByte1.length))
    {
      byte[] arrayOfByte3 = new byte[5 + arrayOfByte2.length];
      TlsUtils.writeUint8(paramShort, arrayOfByte3, 0);
      TlsUtils.writeVersion(ProtocolVersion.TLSv10, arrayOfByte3, 1);
      TlsUtils.writeUint16(arrayOfByte2.length, arrayOfByte3, 3);
      System.arraycopy(arrayOfByte2, 0, arrayOfByte3, 5, arrayOfByte2.length);
      this.os.write(arrayOfByte3);
      this.os.flush();
      return;
      localOutputStream.write(paramArrayOfByte, paramInt1, paramInt2);
      localOutputStream.flush();
      arrayOfByte1 = getBufferContents();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.RecordStream
 * JD-Core Version:    0.7.0.1
 */