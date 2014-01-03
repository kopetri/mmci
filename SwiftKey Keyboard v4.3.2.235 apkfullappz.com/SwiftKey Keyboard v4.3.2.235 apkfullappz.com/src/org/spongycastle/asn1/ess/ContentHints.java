package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTF8String;

public class ContentHints
  extends ASN1Object
{
  private DERUTF8String contentDescription;
  private ASN1ObjectIdentifier contentType;
  
  public ContentHints(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.contentType = paramASN1ObjectIdentifier;
    this.contentDescription = null;
  }
  
  public ContentHints(ASN1ObjectIdentifier paramASN1ObjectIdentifier, DERUTF8String paramDERUTF8String)
  {
    this.contentType = paramASN1ObjectIdentifier;
    this.contentDescription = paramDERUTF8String;
  }
  
  private ContentHints(ASN1Sequence paramASN1Sequence)
  {
    ASN1Encodable localASN1Encodable = paramASN1Sequence.getObjectAt(0);
    if ((localASN1Encodable.toASN1Primitive() instanceof DERUTF8String))
    {
      this.contentDescription = DERUTF8String.getInstance(localASN1Encodable);
      this.contentType = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
      return;
    }
    this.contentType = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
  }
  
  public ContentHints(DERObjectIdentifier paramDERObjectIdentifier)
  {
    this(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()));
  }
  
  public ContentHints(DERObjectIdentifier paramDERObjectIdentifier, DERUTF8String paramDERUTF8String)
  {
    this(new ASN1ObjectIdentifier(paramDERObjectIdentifier.getId()), paramDERUTF8String);
  }
  
  public static ContentHints getInstance(Object paramObject)
  {
    if ((paramObject instanceof ContentHints)) {
      return (ContentHints)paramObject;
    }
    if (paramObject != null) {
      return new ContentHints(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERUTF8String getContentDescription()
  {
    return this.contentDescription;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentType;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.contentDescription != null) {
      localASN1EncodableVector.add(this.contentDescription);
    }
    localASN1EncodableVector.add(this.contentType);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.ContentHints
 * JD-Core Version:    0.7.0.1
 */