package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERNull;
import org.spongycastle.asn1.DERTaggedObject;

public class ProofOfPossession
  extends ASN1Object
  implements ASN1Choice
{
  public static final int TYPE_KEY_AGREEMENT = 3;
  public static final int TYPE_KEY_ENCIPHERMENT = 2;
  public static final int TYPE_RA_VERIFIED = 0;
  public static final int TYPE_SIGNING_KEY = 1;
  private ASN1Encodable obj;
  private int tagNo;
  
  public ProofOfPossession()
  {
    this.tagNo = 0;
    this.obj = DERNull.INSTANCE;
  }
  
  public ProofOfPossession(int paramInt, POPOPrivKey paramPOPOPrivKey)
  {
    this.tagNo = paramInt;
    this.obj = paramPOPOPrivKey;
  }
  
  private ProofOfPossession(ASN1TaggedObject paramASN1TaggedObject)
  {
    this.tagNo = paramASN1TaggedObject.getTagNo();
    switch (this.tagNo)
    {
    default: 
      throw new IllegalArgumentException("unknown tag: " + this.tagNo);
    case 0: 
      this.obj = DERNull.INSTANCE;
      return;
    case 1: 
      this.obj = POPOSigningKey.getInstance(paramASN1TaggedObject, false);
      return;
    }
    this.obj = POPOPrivKey.getInstance(paramASN1TaggedObject, false);
  }
  
  public ProofOfPossession(POPOSigningKey paramPOPOSigningKey)
  {
    this.tagNo = 1;
    this.obj = paramPOPOSigningKey;
  }
  
  public static ProofOfPossession getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ProofOfPossession))) {
      return (ProofOfPossession)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new ProofOfPossession((ASN1TaggedObject)paramObject);
    }
    throw new IllegalArgumentException("Invalid object: " + paramObject.getClass().getName());
  }
  
  public ASN1Encodable getObject()
  {
    return this.obj;
  }
  
  public int getType()
  {
    return this.tagNo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, this.tagNo, this.obj);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.ProofOfPossession
 * JD-Core Version:    0.7.0.1
 */