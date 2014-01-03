package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;

public class ResponseData
  extends ASN1Object
{
  private static final ASN1Integer V1 = new ASN1Integer(0);
  private DERGeneralizedTime producedAt;
  private ResponderID responderID;
  private Extensions responseExtensions;
  private ASN1Sequence responses;
  private ASN1Integer version;
  private boolean versionPresent;
  
  public ResponseData(ASN1Integer paramASN1Integer, ResponderID paramResponderID, DERGeneralizedTime paramDERGeneralizedTime, ASN1Sequence paramASN1Sequence, Extensions paramExtensions)
  {
    this.version = paramASN1Integer;
    this.responderID = paramResponderID;
    this.producedAt = paramDERGeneralizedTime;
    this.responses = paramASN1Sequence;
    this.responseExtensions = paramExtensions;
  }
  
  private ResponseData(ASN1Sequence paramASN1Sequence)
  {
    int i;
    if ((paramASN1Sequence.getObjectAt(0) instanceof ASN1TaggedObject)) {
      if (((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0)).getTagNo() == 0)
      {
        this.versionPresent = true;
        this.version = ASN1Integer.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), true);
        i = 0 + 1;
      }
    }
    for (;;)
    {
      int j = i + 1;
      this.responderID = ResponderID.getInstance(paramASN1Sequence.getObjectAt(i));
      int k = j + 1;
      this.producedAt = ((DERGeneralizedTime)paramASN1Sequence.getObjectAt(j));
      int m = k + 1;
      this.responses = ((ASN1Sequence)paramASN1Sequence.getObjectAt(k));
      if (paramASN1Sequence.size() > m) {
        this.responseExtensions = Extensions.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(m), true);
      }
      return;
      this.version = V1;
      i = 0;
      continue;
      this.version = V1;
      i = 0;
    }
  }
  
  public ResponseData(ResponderID paramResponderID, DERGeneralizedTime paramDERGeneralizedTime, ASN1Sequence paramASN1Sequence, Extensions paramExtensions)
  {
    this(V1, paramResponderID, paramDERGeneralizedTime, paramASN1Sequence, paramExtensions);
  }
  
  public ResponseData(ResponderID paramResponderID, DERGeneralizedTime paramDERGeneralizedTime, ASN1Sequence paramASN1Sequence, X509Extensions paramX509Extensions)
  {
    this(V1, paramResponderID, paramDERGeneralizedTime, paramASN1Sequence, Extensions.getInstance(paramX509Extensions));
  }
  
  public static ResponseData getInstance(Object paramObject)
  {
    if ((paramObject instanceof ResponseData)) {
      return (ResponseData)paramObject;
    }
    if (paramObject != null) {
      return new ResponseData(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static ResponseData getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public DERGeneralizedTime getProducedAt()
  {
    return this.producedAt;
  }
  
  public ResponderID getResponderID()
  {
    return this.responderID;
  }
  
  public Extensions getResponseExtensions()
  {
    return this.responseExtensions;
  }
  
  public ASN1Sequence getResponses()
  {
    return this.responses;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if ((this.versionPresent) || (!this.version.equals(V1))) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.version));
    }
    localASN1EncodableVector.add(this.responderID);
    localASN1EncodableVector.add(this.producedAt);
    localASN1EncodableVector.add(this.responses);
    if (this.responseExtensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.responseExtensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.ResponseData
 * JD-Core Version:    0.7.0.1
 */