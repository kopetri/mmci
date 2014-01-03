package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;

public class ResponderID
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1Encodable value;
  
  public ResponderID(ASN1OctetString paramASN1OctetString)
  {
    this.value = paramASN1OctetString;
  }
  
  public ResponderID(X500Name paramX500Name)
  {
    this.value = paramX500Name;
  }
  
  public static ResponderID getInstance(Object paramObject)
  {
    if ((paramObject instanceof ResponderID)) {
      return (ResponderID)paramObject;
    }
    if ((paramObject instanceof DEROctetString)) {
      return new ResponderID((DEROctetString)paramObject);
    }
    if ((paramObject instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramObject;
      if (localASN1TaggedObject.getTagNo() == 1) {
        return new ResponderID(X500Name.getInstance(localASN1TaggedObject, true));
      }
      return new ResponderID(ASN1OctetString.getInstance(localASN1TaggedObject, true));
    }
    return new ResponderID(X500Name.getInstance(paramObject));
  }
  
  public static ResponderID getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public byte[] getKeyHash()
  {
    if ((this.value instanceof ASN1OctetString)) {
      return ((ASN1OctetString)this.value).getOctets();
    }
    return null;
  }
  
  public X500Name getName()
  {
    if ((this.value instanceof ASN1OctetString)) {
      return null;
    }
    return X500Name.getInstance(this.value);
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if ((this.value instanceof ASN1OctetString)) {
      return new DERTaggedObject(true, 2, this.value);
    }
    return new DERTaggedObject(true, 1, this.value);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.ResponderID
 * JD-Core Version:    0.7.0.1
 */