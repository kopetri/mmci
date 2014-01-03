package org.spongycastle.asn1;

import java.io.IOException;

public class DERExternalParser
  implements ASN1Encodable, InMemoryRepresentable
{
  private ASN1StreamParser _parser;
  
  public DERExternalParser(ASN1StreamParser paramASN1StreamParser)
  {
    this._parser = paramASN1StreamParser;
  }
  
  public ASN1Primitive getLoadedObject()
    throws IOException
  {
    try
    {
      DERExternal localDERExternal = new DERExternal(this._parser.readVector());
      return localDERExternal;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ASN1Exception(localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
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
      throw new ASN1ParsingException("unable to get DER object", localIOException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new ASN1ParsingException("unable to get DER object", localIllegalArgumentException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERExternalParser
 * JD-Core Version:    0.7.0.1
 */