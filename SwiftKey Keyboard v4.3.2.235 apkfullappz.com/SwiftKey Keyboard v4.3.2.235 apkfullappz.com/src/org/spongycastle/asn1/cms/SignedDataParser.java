package org.spongycastle.asn1.cms;

import java.io.IOException;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1SequenceParser;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.ASN1SetParser;
import org.spongycastle.asn1.ASN1TaggedObjectParser;

public class SignedDataParser
{
  private boolean _certsCalled;
  private boolean _crlsCalled;
  private Object _nextObject;
  private ASN1SequenceParser _seq;
  private ASN1Integer _version;
  
  private SignedDataParser(ASN1SequenceParser paramASN1SequenceParser)
    throws IOException
  {
    this._seq = paramASN1SequenceParser;
    this._version = ((ASN1Integer)paramASN1SequenceParser.readObject());
  }
  
  public static SignedDataParser getInstance(Object paramObject)
    throws IOException
  {
    if ((paramObject instanceof ASN1Sequence)) {
      return new SignedDataParser(((ASN1Sequence)paramObject).parser());
    }
    if ((paramObject instanceof ASN1SequenceParser)) {
      return new SignedDataParser((ASN1SequenceParser)paramObject);
    }
    throw new IOException("unknown object encountered: " + paramObject.getClass().getName());
  }
  
  public ASN1SetParser getCertificates()
    throws IOException
  {
    this._certsCalled = true;
    this._nextObject = this._seq.readObject();
    if (((this._nextObject instanceof ASN1TaggedObjectParser)) && (((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 0))
    {
      ASN1SetParser localASN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
      this._nextObject = null;
      return localASN1SetParser;
    }
    return null;
  }
  
  public ASN1SetParser getCrls()
    throws IOException
  {
    if (!this._certsCalled) {
      throw new IOException("getCerts() has not been called.");
    }
    this._crlsCalled = true;
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    if (((this._nextObject instanceof ASN1TaggedObjectParser)) && (((ASN1TaggedObjectParser)this._nextObject).getTagNo() == 1))
    {
      ASN1SetParser localASN1SetParser = (ASN1SetParser)((ASN1TaggedObjectParser)this._nextObject).getObjectParser(17, false);
      this._nextObject = null;
      return localASN1SetParser;
    }
    return null;
  }
  
  public ASN1SetParser getDigestAlgorithms()
    throws IOException
  {
    ASN1Encodable localASN1Encodable = this._seq.readObject();
    if ((localASN1Encodable instanceof ASN1Set)) {
      return ((ASN1Set)localASN1Encodable).parser();
    }
    return (ASN1SetParser)localASN1Encodable;
  }
  
  public ContentInfoParser getEncapContentInfo()
    throws IOException
  {
    return new ContentInfoParser((ASN1SequenceParser)this._seq.readObject());
  }
  
  public ASN1SetParser getSignerInfos()
    throws IOException
  {
    if ((!this._certsCalled) || (!this._crlsCalled)) {
      throw new IOException("getCerts() and/or getCrls() has not been called.");
    }
    if (this._nextObject == null) {
      this._nextObject = this._seq.readObject();
    }
    return (ASN1SetParser)this._nextObject;
  }
  
  public ASN1Integer getVersion()
  {
    return this._version;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.SignedDataParser
 * JD-Core Version:    0.7.0.1
 */