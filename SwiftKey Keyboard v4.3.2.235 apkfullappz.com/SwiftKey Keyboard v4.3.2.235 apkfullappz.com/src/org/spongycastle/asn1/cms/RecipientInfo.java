package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class RecipientInfo
  extends ASN1Object
  implements ASN1Choice
{
  ASN1Encodable info;
  
  public RecipientInfo(ASN1Primitive paramASN1Primitive)
  {
    this.info = paramASN1Primitive;
  }
  
  public RecipientInfo(KEKRecipientInfo paramKEKRecipientInfo)
  {
    this.info = new DERTaggedObject(false, 2, paramKEKRecipientInfo);
  }
  
  public RecipientInfo(KeyAgreeRecipientInfo paramKeyAgreeRecipientInfo)
  {
    this.info = new DERTaggedObject(false, 1, paramKeyAgreeRecipientInfo);
  }
  
  public RecipientInfo(KeyTransRecipientInfo paramKeyTransRecipientInfo)
  {
    this.info = paramKeyTransRecipientInfo;
  }
  
  public RecipientInfo(OtherRecipientInfo paramOtherRecipientInfo)
  {
    this.info = new DERTaggedObject(false, 4, paramOtherRecipientInfo);
  }
  
  public RecipientInfo(PasswordRecipientInfo paramPasswordRecipientInfo)
  {
    this.info = new DERTaggedObject(false, 3, paramPasswordRecipientInfo);
  }
  
  public static RecipientInfo getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof RecipientInfo))) {
      return (RecipientInfo)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new RecipientInfo((ASN1Sequence)paramObject);
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new RecipientInfo((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  private KEKRecipientInfo getKEKInfo(ASN1TaggedObject paramASN1TaggedObject)
  {
    if (paramASN1TaggedObject.isExplicit()) {
      return KEKRecipientInfo.getInstance(paramASN1TaggedObject, true);
    }
    return KEKRecipientInfo.getInstance(paramASN1TaggedObject, false);
  }
  
  public ASN1Encodable getInfo()
  {
    if ((this.info instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)this.info;
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalStateException("unknown tag");
      case 1: 
        return KeyAgreeRecipientInfo.getInstance(localASN1TaggedObject, false);
      case 2: 
        return getKEKInfo(localASN1TaggedObject);
      case 3: 
        return PasswordRecipientInfo.getInstance(localASN1TaggedObject, false);
      }
      return OtherRecipientInfo.getInstance(localASN1TaggedObject, false);
    }
    return KeyTransRecipientInfo.getInstance(this.info);
  }
  
  public ASN1Integer getVersion()
  {
    if ((this.info instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)this.info;
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalStateException("unknown tag");
      case 1: 
        return KeyAgreeRecipientInfo.getInstance(localASN1TaggedObject, false).getVersion();
      case 2: 
        return getKEKInfo(localASN1TaggedObject).getVersion();
      case 3: 
        return PasswordRecipientInfo.getInstance(localASN1TaggedObject, false).getVersion();
      }
      return new ASN1Integer(0);
    }
    return KeyTransRecipientInfo.getInstance(this.info).getVersion();
  }
  
  public boolean isTagged()
  {
    return this.info instanceof ASN1TaggedObject;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.info.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.RecipientInfo
 * JD-Core Version:    0.7.0.1
 */