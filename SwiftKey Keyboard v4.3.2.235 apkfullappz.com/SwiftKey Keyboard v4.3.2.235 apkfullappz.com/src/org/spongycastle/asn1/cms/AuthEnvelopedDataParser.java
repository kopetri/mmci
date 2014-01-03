package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;

public class AuthEnvelopedDataParser
{
  private ASN1Encodable nextObject;
  private boolean originatorInfoCalled;
  private ASN1SequenceParser seq;
  private ASN1Integer version;
  
  public AuthEnvelopedDataParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this.seq = paramASN1SequenceParser;
    this.version = ASN1Integer.getInstance(paramASN1SequenceParser.readObject());
  }
  
  public ASN1SetParser getAuthAttrs()
    throws IOException
  {
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    boolean bool = this.nextObject instanceof ASN1TaggedObjectParser;
    ASN1SetParser localASN1SetParser = null;
    if (bool)
    {
      ASN1Encodable localASN1Encodable = this.nextObject;
      this.nextObject = null;
      localASN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)localASN1Encodable).getObjectParser(17, false);
    }
    return localASN1SetParser;
  }
  
  public EncryptedContentInfoParser getAuthEncryptedContentInfo()
    throws IOException
  {
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    ASN1Encodable localASN1Encodable = this.nextObject;
    EncryptedContentInfoParser localEncryptedContentInfoParser = null;
    if (localASN1Encodable != null)
    {
      ASN1SequenceParser localASN1SequenceParser = (ASN1SequenceParser)this.nextObject;
      this.nextObject = null;
      localEncryptedContentInfoParser = new EncryptedContentInfoParser(localASN1SequenceParser);
    }
    return localEncryptedContentInfoParser;
  }
  
  public ASN1OctetString getMac()
    throws IOException
  {
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    ASN1Encodable localASN1Encodable = this.nextObject;
    this.nextObject = null;
    return ASN1OctetString.getInstance(localASN1Encodable.toASN1Primitive());
  }
  
  public OriginatorInfo getOriginatorInfo()
    throws IOException
  {
    this.originatorInfoCalled = true;
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    if (((this.nextObject instanceof ASN1TaggedObjectParser)) && (((ASN1TaggedObjectParser)this.nextObject).getTagNo() == 0))
    {
      ASN1SequenceParser localASN1SequenceParser = (ASN1SequenceParser)((ASN1TaggedObjectParser)this.nextObject).getObjectParser(16, false);
      this.nextObject = null;
      return OriginatorInfo.getInstance(localASN1SequenceParser.toASN1Primitive());
    }
    return null;
  }
  
  public ASN1SetParser getRecipientInfos()
    throws IOException
  {
    if (!this.originatorInfoCalled) {
      getOriginatorInfo();
    }
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    ASN1SetParser localASN1SetParser = (ASN1SetParser)this.nextObject;
    this.nextObject = null;
    return localASN1SetParser;
  }
  
  public ASN1SetParser getUnauthAttrs()
    throws IOException
  {
    if (this.nextObject == null) {
      this.nextObject = this.seq.readObject();
    }
    ASN1Encodable localASN1Encodable1 = this.nextObject;
    ASN1SetParser localASN1SetParser = null;
    if (localASN1Encodable1 != null)
    {
      ASN1Encodable localASN1Encodable2 = this.nextObject;
      this.nextObject = null;
      localASN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)localASN1Encodable2).getObjectParser(17, false);
    }
    return localASN1SetParser;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.AuthEnvelopedDataParser
 * JD-Core Version:    0.7.0.1
 */