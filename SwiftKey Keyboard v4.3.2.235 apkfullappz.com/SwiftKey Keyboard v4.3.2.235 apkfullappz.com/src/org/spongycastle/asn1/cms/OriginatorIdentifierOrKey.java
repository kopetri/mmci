package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.SubjectKeyIdentifier;

public class OriginatorIdentifierOrKey
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1Encodable id;
  
  public OriginatorIdentifierOrKey(ASN1OctetString paramASN1OctetString)
  {
    this(new SubjectKeyIdentifier(paramASN1OctetString.getOctets()));
  }
  
  public OriginatorIdentifierOrKey(ASN1Primitive paramASN1Primitive)
  {
    this.id = paramASN1Primitive;
  }
  
  public OriginatorIdentifierOrKey(IssuerAndSerialNumber paramIssuerAndSerialNumber)
  {
    this.id = paramIssuerAndSerialNumber;
  }
  
  public OriginatorIdentifierOrKey(OriginatorPublicKey paramOriginatorPublicKey)
  {
    this.id = new DERTaggedObject(false, 1, paramOriginatorPublicKey);
  }
  
  public OriginatorIdentifierOrKey(SubjectKeyIdentifier paramSubjectKeyIdentifier)
  {
    this.id = new DERTaggedObject(false, 0, paramSubjectKeyIdentifier);
  }
  
  public static OriginatorIdentifierOrKey getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof OriginatorIdentifierOrKey))) {
      return (OriginatorIdentifierOrKey)paramObject;
    }
    if ((paramObject instanceof IssuerAndSerialNumber)) {
      return new OriginatorIdentifierOrKey((IssuerAndSerialNumber)paramObject);
    }
    if ((paramObject instanceof SubjectKeyIdentifier)) {
      return new OriginatorIdentifierOrKey((SubjectKeyIdentifier)paramObject);
    }
    if ((paramObject instanceof OriginatorPublicKey)) {
      return new OriginatorIdentifierOrKey((OriginatorPublicKey)paramObject);
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new OriginatorIdentifierOrKey((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("Invalid OriginatorIdentifierOrKey: " + paramObject.getClass().getName());
  }
  
  public static OriginatorIdentifierOrKey getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    if (!paramBoolean) {
      throw new IllegalArgumentException("Can't implicitly tag OriginatorIdentifierOrKey");
    }
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public ASN1Encodable getId()
  {
    return this.id;
  }
  
  public IssuerAndSerialNumber getIssuerAndSerialNumber()
  {
    if ((this.id instanceof IssuerAndSerialNumber)) {
      return (IssuerAndSerialNumber)this.id;
    }
    return null;
  }
  
  public OriginatorPublicKey getOriginatorKey()
  {
    if (((this.id instanceof ASN1TaggedObject)) && (((ASN1TaggedObject)this.id).getTagNo() == 1)) {
      return OriginatorPublicKey.getInstance((ASN1TaggedObject)this.id, false);
    }
    return null;
  }
  
  public SubjectKeyIdentifier getSubjectKeyIdentifier()
  {
    if (((this.id instanceof ASN1TaggedObject)) && (((ASN1TaggedObject)this.id).getTagNo() == 0)) {
      return SubjectKeyIdentifier.getInstance((ASN1TaggedObject)this.id, false);
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.id.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.OriginatorIdentifierOrKey
 * JD-Core Version:    0.7.0.1
 */