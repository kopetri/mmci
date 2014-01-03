package org.spongycastle.cms;

import java.io.BufferedInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.util.io.Streams;

public class CMSTypedStream
{
  private static final int BUF_SIZ = 32768;
  private final InputStream _in;
  private final ASN1ObjectIdentifier _oid;
  
  public CMSTypedStream(InputStream paramInputStream)
  {
    this(PKCSObjectIdentifiers.data.getId(), paramInputStream, 32768);
  }
  
  public CMSTypedStream(String paramString, InputStream paramInputStream)
  {
    this(new ASN1ObjectIdentifier(paramString), paramInputStream, 32768);
  }
  
  public CMSTypedStream(String paramString, InputStream paramInputStream, int paramInt)
  {
    this(new ASN1ObjectIdentifier(paramString), paramInputStream, paramInt);
  }
  
  public CMSTypedStream(ASN1ObjectIdentifier paramASN1ObjectIdentifier, InputStream paramInputStream)
  {
    this(paramASN1ObjectIdentifier, paramInputStream, 32768);
  }
  
  public CMSTypedStream(ASN1ObjectIdentifier paramASN1ObjectIdentifier, InputStream paramInputStream, int paramInt)
  {
    this._oid = paramASN1ObjectIdentifier;
    this._in = new FullReaderStream(new BufferedInputStream(paramInputStream, paramInt));
  }
  
  public void drain()
    throws IOException
  {
    Streams.drain(this._in);
    this._in.close();
  }
  
  public InputStream getContentStream()
  {
    return this._in;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this._oid;
  }
  
  private static class FullReaderStream
    extends FilterInputStream
  {
    FullReaderStream(InputStream paramInputStream)
    {
      super();
    }
    
    public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
      throws IOException
    {
      int i = Streams.readFully(this.in, paramArrayOfByte, paramInt1, paramInt2);
      if (i > 0) {
        return i;
      }
      return -1;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cms.CMSTypedStream
 * JD-Core Version:    0.7.0.1
 */