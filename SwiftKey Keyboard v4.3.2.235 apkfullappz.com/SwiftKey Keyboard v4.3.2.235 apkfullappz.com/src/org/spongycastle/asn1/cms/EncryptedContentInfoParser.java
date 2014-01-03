package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfoParser
{
  private AlgorithmIdentifier _contentEncryptionAlgorithm;
  private ASN1ObjectIdentifier _contentType;
  private ASN1TaggedObjectParser _encryptedContent;
  
  public EncryptedContentInfoParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this._contentType = ((ASN1ObjectIdentifier)paramASN1SequenceParser.readObject());
    this._contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1SequenceParser.readObject().toASN1Primitive());
    this._encryptedContent = ((ASN1TaggedObjectParser)paramASN1SequenceParser.readObject());
  }
  
  public AlgorithmIdentifier getContentEncryptionAlgorithm()
  {
    return this._contentEncryptionAlgorithm;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this._contentType;
  }
  
  public ASN1Encodable getEncryptedContent(int paramInt)
    throws IOException
  {
    return this._encryptedContent.getObjectParser(paramInt, false);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.EncryptedContentInfoParser
 * JD-Core Version:    0.7.0.1
 */