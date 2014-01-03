package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.ocsp.ResponderID;

public class OcspIdentifier
  extends ASN1Object
{
  private ResponderID ocspResponderID;
  private DERGeneralizedTime producedAt;
  
  private OcspIdentifier(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.ocspResponderID = ResponderID.getInstance(paramASN1Sequence.getObjectAt(0));
    this.producedAt = ((DERGeneralizedTime)paramASN1Sequence.getObjectAt(1));
  }
  
  public OcspIdentifier(ResponderID paramResponderID, DERGeneralizedTime paramDERGeneralizedTime)
  {
    this.ocspResponderID = paramResponderID;
    this.producedAt = paramDERGeneralizedTime;
  }
  
  public static OcspIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof OcspIdentifier)) {
      return (OcspIdentifier)paramObject;
    }
    if (paramObject != null) {
      return new OcspIdentifier(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ResponderID getOcspResponderID()
  {
    return this.ocspResponderID;
  }
  
  public DERGeneralizedTime getProducedAt()
  {
    return this.producedAt;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.ocspResponderID);
    localASN1EncodableVector.add(this.producedAt);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OcspIdentifier
 * JD-Core Version:    0.7.0.1
 */