package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBoolean;
import org.spongycastle.asn1.DERTaggedObject;

public class PKIArchiveOptions
  extends ASN1Object
  implements ASN1Choice
{
  public static final int archiveRemGenPrivKey = 2;
  public static final int encryptedPrivKey = 0;
  public static final int keyGenParameters = 1;
  private ASN1Encodable value;
  
  public PKIArchiveOptions(ASN1OctetString paramASN1OctetString)
  {
    this.value = paramASN1OctetString;
  }
  
  private PKIArchiveOptions(ASN1TaggedObject paramASN1TaggedObject)
  {
    switch (paramASN1TaggedObject.getTagNo())
    {
    default: 
      throw new IllegalArgumentException("unknown tag number: " + paramASN1TaggedObject.getTagNo());
    case 0: 
      this.value = EncryptedKey.getInstance(paramASN1TaggedObject.getObject());
      return;
    case 1: 
      this.value = ASN1OctetString.getInstance(paramASN1TaggedObject, false);
      return;
    }
    this.value = DERBoolean.getInstance(paramASN1TaggedObject, false);
  }
  
  public PKIArchiveOptions(EncryptedKey paramEncryptedKey)
  {
    this.value = paramEncryptedKey;
  }
  
  public PKIArchiveOptions(boolean paramBoolean)
  {
    this.value = new DERBoolean(paramBoolean);
  }
  
  public static PKIArchiveOptions getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof PKIArchiveOptions))) {
      return (PKIArchiveOptions)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new PKIArchiveOptions((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("unknown object: " + paramObject);
  }
  
  public int getType()
  {
    if ((this.value instanceof EncryptedKey)) {
      return 0;
    }
    if ((this.value instanceof ASN1OctetString)) {
      return 1;
    }
    return 2;
  }
  
  public ASN1Encodable getValue()
  {
    return this.value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if ((this.value instanceof EncryptedKey)) {
      return new DERTaggedObject(true, 0, this.value);
    }
    if ((this.value instanceof ASN1OctetString)) {
      return new DERTaggedObject(false, 1, this.value);
    }
    return new DERTaggedObject(false, 2, this.value);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.PKIArchiveOptions
 * JD-Core Version:    0.7.0.1
 */