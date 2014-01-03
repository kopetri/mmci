package org.spongycastle.crypto.tls;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;

public class TlsMac
{
  protected TlsClientContext context;
  protected Mac mac;
  protected byte[] secret;
  protected long seqNo;
  
  public TlsMac(TlsClientContext paramTlsClientContext, Digest paramDigest, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    this.context = paramTlsClientContext;
    this.seqNo = 0L;
    KeyParameter localKeyParameter = new KeyParameter(paramArrayOfByte, paramInt1, paramInt2);
    this.secret = Arrays.clone(localKeyParameter.getKey());
    int i;
    if (paramTlsClientContext.getServerVersion().getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion())
    {
      i = 1;
      if (i == 0) {
        break label96;
      }
    }
    label96:
    for (this.mac = new HMac(paramDigest);; this.mac = new SSL3Mac(paramDigest))
    {
      this.mac.init(localKeyParameter);
      return;
      i = 0;
      break;
    }
  }
  
  public byte[] calculateMac(short paramShort, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    ProtocolVersion localProtocolVersion = this.context.getServerVersion();
    int i;
    if (localProtocolVersion.getFullVersion() >= ProtocolVersion.TLSv10.getFullVersion())
    {
      i = 1;
      if (i == 0) {
        break label164;
      }
    }
    label164:
    for (int j = 13;; j = 11)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream(j);
      try
      {
        long l = this.seqNo;
        this.seqNo = (1L + l);
        TlsUtils.writeUint64(l, localByteArrayOutputStream);
        TlsUtils.writeUint8(paramShort, localByteArrayOutputStream);
        if (i != 0) {
          TlsUtils.writeVersion(localProtocolVersion, localByteArrayOutputStream);
        }
        TlsUtils.writeUint16(paramInt2, localByteArrayOutputStream);
        byte[] arrayOfByte1 = localByteArrayOutputStream.toByteArray();
        this.mac.update(arrayOfByte1, 0, arrayOfByte1.length);
        this.mac.update(paramArrayOfByte, paramInt1, paramInt2);
        byte[] arrayOfByte2 = new byte[this.mac.getMacSize()];
        this.mac.doFinal(arrayOfByte2, 0);
        return arrayOfByte2;
      }
      catch (IOException localIOException)
      {
        throw new IllegalStateException("Internal error during mac calculation");
      }
      i = 0;
      break;
    }
  }
  
  public byte[] getMACSecret()
  {
    return this.secret;
  }
  
  public long getSequenceNumber()
  {
    return this.seqNo;
  }
  
  public int getSize()
  {
    return this.mac.getMacSize();
  }
  
  public void incSequenceNumber()
  {
    this.seqNo = (1L + this.seqNo);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.crypto.tls.TlsMac
 * JD-Core Version:    0.7.0.1
 */