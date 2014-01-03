package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class OCSPResponse
  extends ASN1Object
{
  ResponseBytes responseBytes;
  OCSPResponseStatus responseStatus;
  
  private OCSPResponse(ASN1Sequence paramASN1Sequence)
  {
    this.responseStatus = OCSPResponseStatus.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() == 2) {
      this.responseBytes = ResponseBytes.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(1), true);
    }
  }
  
  public OCSPResponse(OCSPResponseStatus paramOCSPResponseStatus, ResponseBytes paramResponseBytes)
  {
    this.responseStatus = paramOCSPResponseStatus;
    this.responseBytes = paramResponseBytes;
  }
  
  public static OCSPResponse getInstance(Object paramObject)
  {
    if ((paramObject instanceof OCSPResponse)) {
      return (OCSPResponse)paramObject;
    }
    if (paramObject != null) {
      return new OCSPResponse(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static OCSPResponse getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ResponseBytes getResponseBytes()
  {
    return this.responseBytes;
  }
  
  public OCSPResponseStatus getResponseStatus()
  {
    return this.responseStatus;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.responseStatus);
    if (this.responseBytes != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.responseBytes));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.OCSPResponse
 * JD-Core Version:    0.7.0.1
 */