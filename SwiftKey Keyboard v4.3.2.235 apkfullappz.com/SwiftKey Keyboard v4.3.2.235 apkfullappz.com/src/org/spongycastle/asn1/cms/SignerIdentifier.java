package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class SignerIdentifier
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1Encodable id;
  
  public SignerIdentifier(ASN1OctetString paramASN1OctetString)
  {
    this.id = new DERTaggedObject(false, 0, paramASN1OctetString);
  }
  
  public SignerIdentifier(ASN1Primitive paramASN1Primitive)
  {
    this.id = paramASN1Primitive;
  }
  
  public SignerIdentifier(IssuerAndSerialNumber paramIssuerAndSerialNumber)
  {
    this.id = paramIssuerAndSerialNumber;
  }
  
  public static SignerIdentifier getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof SignerIdentifier))) {
      return (SignerIdentifier)paramObject;
    }
    if ((paramObject instanceof IssuerAndSerialNumber)) {
      return new SignerIdentifier((IssuerAndSerialNumber)paramObject);
    }
    if ((paramObject instanceof ASN1OctetString)) {
      return new SignerIdentifier((ASN1OctetString)paramObject);
    }
    if ((paramObject instanceof ASN1Primitive)) {
      return new SignerIdentifier((ASN1Primitive)paramObject);
    }
    throw new IllegalArgumentException("Illegal object in SignerIdentifier: " + paramObject.getClass().getName());
  }
  
  public ASN1Encodable getId()
  {
    if ((this.id instanceof ASN1TaggedObject)) {
      return ASN1OctetString.getInstance((ASN1TaggedObject)this.id, false);
    }
    return this.id;
  }
  
  public boolean isTagged()
  {
    return this.id instanceof ASN1TaggedObject;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.id.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.SignerIdentifier
 * JD-Core Version:    0.7.0.1
 */