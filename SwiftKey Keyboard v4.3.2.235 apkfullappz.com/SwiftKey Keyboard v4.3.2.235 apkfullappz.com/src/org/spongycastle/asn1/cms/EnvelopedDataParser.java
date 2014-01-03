package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;

public class EnvelopedDataParser
{
  private ASN1Encodable _nextObject;
  private boolean _originatorInfoCalled;
  private ASN1SequenceParser _seq;
  private ASN1Integer _version;
  
  public EnvelopedDataParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this._seq = paramASN1SequenceParser;
    this._version = ASN1Integer.getInstance(paramASN1SequenceParser.readObject());
  }
  
  public EncryptedContentInfoParser getEncryptedContentInfo()
    throws IOException
  {
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    ASN1Encodable localASN1Encodable = this._nextObject;
    EncryptedContentInfoParser localEncryptedContentInfoParser = null;
    if (localASN1Encodable != null)
    {
      ASN1SequenceParser localASN1SequenceParser = (ASN1SequenceParser)this._nextObject;
      this._nextObject = null;
      localEncryptedContentInfoParser = new EncryptedContentInfoParser(localASN1SequenceParser);
    }
    return localEncryptedContentInfoParser;
  }
  
  public OriginatorInfo getOriginatorInfo()
    throws IOException
  {
    this._originatorInfoCalled = true;
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    if (((this._nextObject instanceof ASN1TaggedObjectParser)) && (((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0))
    {
      ASN1SequenceParser localASN1SequenceParser = (ASN1SequenceParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(16, false);
      this._nextObject = null;
      return OriginatorInfo.getInstance(localASN1SequenceParser.toASN1Primitive());
    }
    return null;
  }
  
  public ASN1SetParser getRecipientInfos()
    throws IOException
  {
    if (!this._originatorInfoCalled) {
      getOriginatorInfo();
    }
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    ASN1SetParser localASN1SetParser = (ASN1SetParser)this._nextObject;
    this._nextObject = null;
    return localASN1SetParser;
  }
  
  public ASN1SetParser getUnprotectedAttrs()
    throws IOException
  {
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    ASN1Encodable localASN1Encodable1 = this._nextObject;
    ASN1SetParser localASN1SetParser = null;
    if (localASN1Encodable1 != null)
    {
      ASN1Encodable localASN1Encodable2 = this._nextObject;
      this._nextObject = null;
      localASN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)localASN1Encodable2).getObjectParser(17, false);
    }
    return localASN1SetParser;
  }
  
  public ASN1Integer getVersion()
  {
    return this._version;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.EnvelopedDataParser
 * JD-Core Version:    0.7.0.1
 */