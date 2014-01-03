package org.spongycastle.asn1;

import java.io.IOException;
import java.io.InputStream;

class ConstructedOctetStream
  extends InputStream
{
  private InputStream _currentStream;
  private boolean _first = true;
  private final ASN1StreamParser _parser;
  
  ConstructedOctetStream(ASN1StreamParser paramASN1StreamParser)
  {
    this._parser = paramASN1StreamParser;
  }
  
  public int read()
    throws IOException
  {
    int i;
    ASN1OctetStringParser localASN1OctetStringParser2;
    if (this._currentStream == null)
    {
      if (!this._first)
      {
        i = -1;
        return i;
      }
      localASN1OctetStringParser2 = (ASN1OctetStringParser)this._parser.readObject();
      if (localASN1OctetStringParser2 == null) {
        return -1;
      }
      this._first = false;
    }
    ASN1OctetStringParser localASN1OctetStringParser1;
    for (this._currentStream = localASN1OctetStringParser2.getOctetStream();; this._currentStream = localASN1OctetStringParser1.getOctetStream())
    {
      i = this._currentStream.read();
      if (i >= 0) {
        break;
      }
      localASN1OctetStringParser1 = (ASN1OctetStringParser)this._parser.readObject();
      if (localASN1OctetStringParser1 == null)
      {
        this._currentStream = null;
        return -1;
      }
    }
  }
  
  public int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    if (this._currentStream == null)
    {
      if (!this._first)
      {
        i = -1;
        return i;
      }
      ASN1OctetStringParser localASN1OctetStringParser2 = (ASN1OctetStringParser)this._parser.readObject();
      if (localASN1OctetStringParser2 == null) {
        return -1;
      }
      this._first = false;
      this._currentStream = localASN1OctetStringParser2.getOctetStream();
    }
    int i = 0;
    for (;;)
    {
      int j = this._currentStream.read(paramArrayOfByte, paramInt1 + i, paramInt2 - i);
      if (j >= 0)
      {
        i += j;
        if (i == paramInt2) {
          return i;
        }
      }
      else
      {
        ASN1OctetStringParser localASN1OctetStringParser1 = (ASN1OctetStringParser)this._parser.readObject();
        if (localASN1OctetStringParser1 == null)
        {
          this._currentStream = null;
          if (i > 0) {
            break;
          }
          return -1;
        }
        this._currentStream = localASN1OctetStringParser1.getOctetStream();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ConstructedOctetStream
 * JD-Core Version:    0.7.0.1
 */