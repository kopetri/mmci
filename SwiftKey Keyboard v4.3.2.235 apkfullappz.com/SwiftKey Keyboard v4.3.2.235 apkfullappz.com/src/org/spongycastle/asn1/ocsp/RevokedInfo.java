package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.CRLReason;

public class RevokedInfo
  extends ASN1Object
{
  private CRLReason revocationReason;
  private DERGeneralizedTime revocationTime;
  
  private RevokedInfo(ASN1Sequence paramASN1Sequence)
  {
    this.revocationTime = ((DERGeneralizedTime)paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.revocationReason = CRLReason.getInstance(DEREnumerated.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(1), true));
    }
  }
  
  public RevokedInfo(DERGeneralizedTime paramDERGeneralizedTime, CRLReason paramCRLReason)
  {
    this.revocationTime = paramDERGeneralizedTime;
    this.revocationReason = paramCRLReason;
  }
  
  public static RevokedInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevokedInfo)) {
      return (RevokedInfo)paramObject;
    }
    if (paramObject != null) {
      return new RevokedInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static RevokedInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public CRLReason getRevocationReason()
  {
    return this.revocationReason;
  }
  
  public DERGeneralizedTime getRevocationTime()
  {
    return this.revocationTime;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.revocationTime);
    if (this.revocationReason != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.revocationReason));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.RevokedInfo
 * JD-Core Version:    0.7.0.1
 */