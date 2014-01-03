package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;

public class Evidence
  extends ASN1Object
  implements ASN1Choice
{
  private TimeStampTokenEvidence tstEvidence;
  
  private Evidence(ASN1TaggedObject paramASN1TaggedObject)
  {
    if (paramASN1TaggedObject.getTagNo() == 0) {
      this.tstEvidence = TimeStampTokenEvidence.getInstance(paramASN1TaggedObject, false);
    }
  }
  
  public Evidence(TimeStampTokenEvidence paramTimeStampTokenEvidence)
  {
    this.tstEvidence = paramTimeStampTokenEvidence;
  }
  
  public static Evidence getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof Evidence))) {
      return (Evidence)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new Evidence(ASN1TaggedObject.getInstance(paramObject));
    }
    throw new IllegalArgumentException("unknown object in getInstance");
  }
  
  public TimeStampTokenEvidence getTstEvidence()
  {
    return this.tstEvidence;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.tstEvidence != null) {
      return new DERTaggedObject(false, 0, this.tstEvidence);
    }
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.Evidence
 * JD-Core Version:    0.7.0.1
 */