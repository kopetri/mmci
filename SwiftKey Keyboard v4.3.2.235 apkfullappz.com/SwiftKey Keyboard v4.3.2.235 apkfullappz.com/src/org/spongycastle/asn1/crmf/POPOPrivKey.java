package org.spongycastle.asn1.crmf;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.cms.EnvelopedData;

public class POPOPrivKey
  extends ASN1Object
  implements ASN1Choice
{
  public static final int agreeMAC = 3;
  public static final int dhMAC = 2;
  public static final int encryptedKey = 4;
  public static final int subsequentMessage = 1;
  public static final int thisMessage;
  private ASN1Encodable obj;
  private int tagNo;
  
  private POPOPrivKey(ASN1TaggedObject paramASN1TaggedObject)
  {
    this.tagNo = paramASN1TaggedObject.getTagNo();
    switch (this.tagNo)
    {
    default: 
      throw new IllegalArgumentException("unknown tag in POPOPrivKey");
    case 0: 
      this.obj = DERBitString.getInstance(paramASN1TaggedObject, false);
      return;
    case 1: 
      this.obj = SubsequentMessage.valueOf(ASN1Integer.getInstance(paramASN1TaggedObject, false).getValue().intValue());
      return;
    case 2: 
      this.obj = DERBitString.getInstance(paramASN1TaggedObject, false);
      return;
    case 3: 
      this.obj = PKMACValue.getInstance(paramASN1TaggedObject, false);
      return;
    }
    this.obj = EnvelopedData.getInstance(paramASN1TaggedObject, false);
  }
  
  public POPOPrivKey(SubsequentMessage paramSubsequentMessage)
  {
    this.tagNo = 1;
    this.obj = paramSubsequentMessage;
  }
  
  public static POPOPrivKey getInstance(Object paramObject)
  {
    if ((paramObject instanceof POPOPrivKey)) {
      return (POPOPrivKey)paramObject;
    }
    if (paramObject != null) {
      return new POPOPrivKey(ASN1TaggedObject.getInstance(paramObject));
    }
    return null;
  }
  
  public static POPOPrivKey getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1TaggedObject.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public int getType()
  {
    return this.tagNo;
  }
  
  public ASN1Encodable getValue()
  {
    return this.obj;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, this.tagNo, this.obj);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.POPOPrivKey
 * JD-Core Version:    0.7.0.1
 */