package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;

public class SingleResponse
  extends ASN1Object
{
  private CertID certID;
  private CertStatus certStatus;
  private DERGeneralizedTime nextUpdate;
  private Extensions singleExtensions;
  private DERGeneralizedTime thisUpdate;
  
  private SingleResponse(ASN1Sequence paramASN1Sequence)
  {
    this.certID = CertID.getInstance(paramASN1Sequence.getObjectAt(0));
    this.certStatus = CertStatus.getInstance(paramASN1Sequence.getObjectAt(1));
    this.thisUpdate = ((DERGeneralizedTime)paramASN1Sequence.getObjectAt(2));
    if (paramASN1Sequence.size() > 4)
    {
      this.nextUpdate = DERGeneralizedTime.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(3), true);
      this.singleExtensions = Extensions.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(4), true);
    }
    while (paramASN1Sequence.size() <= 3) {
      return;
    }
    ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(3);
    if (localASN1TaggedObject.getTagNo() == 0)
    {
      this.nextUpdate = DERGeneralizedTime.getInstance(localASN1TaggedObject, true);
      return;
    }
    this.singleExtensions = Extensions.getInstance(localASN1TaggedObject, true);
  }
  
  public SingleResponse(CertID paramCertID, CertStatus paramCertStatus, DERGeneralizedTime paramDERGeneralizedTime1, DERGeneralizedTime paramDERGeneralizedTime2, Extensions paramExtensions)
  {
    this.certID = paramCertID;
    this.certStatus = paramCertStatus;
    this.thisUpdate = paramDERGeneralizedTime1;
    this.nextUpdate = paramDERGeneralizedTime2;
    this.singleExtensions = paramExtensions;
  }
  
  public SingleResponse(CertID paramCertID, CertStatus paramCertStatus, DERGeneralizedTime paramDERGeneralizedTime1, DERGeneralizedTime paramDERGeneralizedTime2, X509Extensions paramX509Extensions)
  {
    this(paramCertID, paramCertStatus, paramDERGeneralizedTime1, paramDERGeneralizedTime2, Extensions.getInstance(paramX509Extensions));
  }
  
  public static SingleResponse getInstance(Object paramObject)
  {
    if ((paramObject instanceof SingleResponse)) {
      return (SingleResponse)paramObject;
    }
    if (paramObject != null) {
      return new SingleResponse(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static SingleResponse getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public CertID getCertID()
  {
    return this.certID;
  }
  
  public CertStatus getCertStatus()
  {
    return this.certStatus;
  }
  
  public DERGeneralizedTime getNextUpdate()
  {
    return this.nextUpdate;
  }
  
  public Extensions getSingleExtensions()
  {
    return this.singleExtensions;
  }
  
  public DERGeneralizedTime getThisUpdate()
  {
    return this.thisUpdate;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certID);
    localASN1EncodableVector.add(this.certStatus);
    localASN1EncodableVector.add(this.thisUpdate);
    if (this.nextUpdate != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.nextUpdate));
    }
    if (this.singleExtensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.singleExtensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.SingleResponse
 * JD-Core Version:    0.7.0.1
 */