package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.EnvelopedData;

public class EncryptedKey
  extends ASN1Object
  implements ASN1Choice
{
  private EncryptedValue encryptedValue;
  private EnvelopedData envelopedData;
  
  public EncryptedKey(EnvelopedData paramEnvelopedData)
  {
    this.envelopedData = paramEnvelopedData;
  }
  
  public EncryptedKey(EncryptedValue paramEncryptedValue)
  {
    this.encryptedValue = paramEncryptedValue;
  }
  
  public static EncryptedKey getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptedKey)) {
      return (EncryptedKey)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new EncryptedKey(EnvelopedData.getInstance((ASN1TaggedObject)paramObject, false));
    }
    if ((paramObject instanceof EncryptedValue)) {
      return new EncryptedKey((EncryptedValue)paramObject);
    }
    return new EncryptedKey(EncryptedValue.getInstance(paramObject));
  }
  
  public ASN1Encodable getValue()
  {
    if (this.encryptedValue != null) {
      return this.encryptedValue;
    }
    return this.envelopedData;
  }
  
  public boolean isEncryptedValue()
  {
    return this.encryptedValue != null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.encryptedValue != null) {
      return this.encryptedValue.toASN1Primitive();
    }
    return new DERTaggedObject(false, 0, this.envelopedData);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.EncryptedKey
 * JD-Core Version:    0.7.0.1
 */