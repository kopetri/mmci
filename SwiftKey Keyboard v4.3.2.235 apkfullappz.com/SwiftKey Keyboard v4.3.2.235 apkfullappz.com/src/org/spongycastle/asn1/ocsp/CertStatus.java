package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERTaggedObject;

public class CertStatus
  extends ASN1Object
  implements ASN1Choice
{
  private int tagNo;
  private ASN1Encodable value;
  
  public CertStatus()
  {
    this.tagNo = 0;
    this.value = new DERNull();
  }
  
  public CertStatus(int paramInt, ASN1Encodable paramASN1Encodable)
  {
    this.tagNo = paramInt;
    this.value = paramASN1Encodable;
  }
  
  public CertStatus(ASN1TaggedObject paramASN1TaggedObject)
  {
    this.tagNo = paramASN1TaggedObject.getTagNo();
    switch (paramASN1TaggedObject.getTagNo())
    {
    default: 
      return;
    case 0: 
      this.value = new DERNull();
      return;
    case 1: 
      this.value = RevokedInfo.getInstance(paramASN1TaggedObject, false);
      return;
    }
    this.value = new DERNull();
  }
  
  public CertStatus(RevokedInfo paramRevokedInfo)
  {
    this.tagNo = 1;
    this.value = paramRevokedInfo;
  }
  
  public static CertStatus getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof CertStatus))) {
      return (CertStatus)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new CertStatus((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static CertStatus getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public ASN1Encodable getStatus()
  {
    return this.value;
  }
  
  public int getTagNo()
  {
    return this.tagNo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, this.tagNo, this.value);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.CertStatus
 * JD-Core Version:    0.7.0.1
 */