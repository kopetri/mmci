package org.spongycastle.asn1;

import java.io.IOException;

public class DERSetParser
  implements ASN1SetParser
{
  private ASN1StreamParser _parser;
  
  DERSetParser(ASN1StreamParser paramASN1StreamParser)
  {
    this._parser = paramASN1StreamParser;
  }
  
  public ASN1Primitive getLoadedObject()
    throws IOException
  {
    return new DERSet(this._parser.readVector(), false);
  }
  
  public ASN1Encodable readObject()
    throws IOException
  {
    return this._parser.readObject();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    try
    {
      ASN1Primitive localASN1Primitive = getLoadedObject();
      return localASN1Primitive;
    }
    catch (IOException localIOException)
    {
      throw new ASN1ParsingException(localIOException.getMessage(), localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERSetParser
 * JD-Core Version:    0.7.0.1
 */