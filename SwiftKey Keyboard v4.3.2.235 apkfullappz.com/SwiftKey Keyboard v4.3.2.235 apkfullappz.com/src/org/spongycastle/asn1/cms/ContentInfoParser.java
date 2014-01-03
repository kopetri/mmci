package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;

public class ContentInfoParser
{
  private ASN1TaggedObjectParser content;
  private ASN1ObjectIdentifier contentType;
  
  public ContentInfoParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this.contentType = ((ASN1ObjectIdentifier)paramASN1SequenceParser.readObject());
    this.content = ((ASN1TaggedObjectParser)paramASN1SequenceParser.readObject());
  }
  
  public ASN1Encodable getContent(int paramInt)
    throws IOException
  {
    if (this.content != null) {
      return this.content.getObjectParser(paramInt, true);
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentType;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.ContentInfoParser
 * JD-Core Version:    0.7.0.1
 */