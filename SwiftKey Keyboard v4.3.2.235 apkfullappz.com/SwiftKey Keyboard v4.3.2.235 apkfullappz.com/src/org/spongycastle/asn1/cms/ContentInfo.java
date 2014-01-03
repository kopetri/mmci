package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;

public class ContentInfo
  extends ASN1Object
  implements CMSObjectIdentifiers
{
  private ASN1Encodable content;
  private ASN1ObjectIdentifier contentType;
  
  public ContentInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.contentType = paramASN1ObjectIdentifier;
    this.content = paramASN1Encodable;
  }
  
  public ContentInfo(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.contentType = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1)
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(1);
      if ((!localASN1TaggedObject.isExplicit()) || (localASN1TaggedObject.getTagNo() != 0)) {
        throw new IllegalArgumentException("Bad tag for 'content'");
      }
      this.content = localASN1TaggedObject.getObject();
    }
  }
  
  public static ContentInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof ContentInfo)) {
      return (ContentInfo)paramObject;
    }
    if (paramObject != null) {
      return new ContentInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Encodable getContent()
  {
    return this.content;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentType;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.contentType);
    if (this.content != null) {
      localASN1EncodableVector.add(new BERTaggedObject(0, this.content));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.ContentInfo
 * JD-Core Version:    0.7.0.1
 */