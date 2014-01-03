package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.Extensions;

public class Request
  extends ASN1Object
{
  CertID reqCert;
  Extensions singleRequestExtensions;
  
  private Request(ASN1Sequence paramASN1Sequence)
  {
    this.reqCert = CertID.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() == 2) {
      this.singleRequestExtensions = Extensions.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(1), true);
    }
  }
  
  public Request(CertID paramCertID, Extensions paramExtensions)
  {
    this.reqCert = paramCertID;
    this.singleRequestExtensions = paramExtensions;
  }
  
  public static Request getInstance(Object paramObject)
  {
    if ((paramObject instanceof Request)) {
      return (Request)paramObject;
    }
    if (paramObject != null) {
      return new Request(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static Request getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public CertID getReqCert()
  {
    return this.reqCert;
  }
  
  public Extensions getSingleRequestExtensions()
  {
    return this.singleRequestExtensions;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.reqCert);
    if (this.singleRequestExtensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.singleRequestExtensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.Request
 * JD-Core Version:    0.7.0.1
 */