package org.spongycastle.asn1;

import java.io.IOException;

public class BERTaggedObjectParser
  implements ASN1TaggedObjectParser
{
  private boolean _constructed;
  private ASN1StreamParser _parser;
  private int _tagNumber;
  
  BERTaggedObjectParser(boolean paramBoolean, int paramInt, ASN1StreamParser paramASN1StreamParser)
  {
    this._constructed = paramBoolean;
    this._tagNumber = paramInt;
    this._parser = paramASN1StreamParser;
  }
  
  public ASN1Primitive getLoadedObject()
    throws IOException
  {
    return this._parser.readTaggedObject(this._constructed, this._tagNumber);
  }
  
  public ASN1Encodable getObjectParser(int paramInt, boolean paramBoolean)
    throws IOException
  {
    if (paramBoolean)
    {
      if (!this._constructed) {
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
      }
      return this._parser.readObject();
    }
    return this._parser.readImplicit(this._constructed, paramInt);
  }
  
  public int getTagNo()
  {
    return this._tagNumber;
  }
  
  public boolean isConstructed()
  {
    return this._constructed;
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
      throw new ASN1ParsingException(localIOException.getMessage());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.BERTaggedObjectParser
 * JD-Core Version:    0.7.0.1
 */