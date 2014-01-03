package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CompressedDataParser
{
  private AlgorithmIdentifier _compressionAlgorithm;
  private ContentInfoParser _encapContentInfo;
  private ASN1Integer _version;
  
  public CompressedDataParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this._version = ((ASN1Integer)paramASN1SequenceParser.readObject());
    this._compressionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1SequenceParser.readObject().toASN1Primitive());
    this._encapContentInfo = new ContentInfoParser((ASN1SequenceParser)paramASN1SequenceParser.readObject());
  }
  
  public AlgorithmIdentifier getCompressionAlgorithmIdentifier()
  {
    return this._compressionAlgorithm;
  }
  
  public ContentInfoParser getEncapContentInfo()
  {
    return this._encapContentInfo;
  }
  
  public ASN1Integer getVersion()
  {
    return this._version;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.CompressedDataParser
 * JD-Core Version:    0.7.0.1
 */