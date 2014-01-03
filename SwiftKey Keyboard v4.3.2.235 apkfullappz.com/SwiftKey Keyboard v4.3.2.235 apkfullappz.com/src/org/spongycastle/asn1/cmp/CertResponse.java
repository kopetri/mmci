package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CertResponse
  extends ASN1Object
{
  private ASN1Integer certReqId;
  private CertifiedKeyPair certifiedKeyPair;
  private ASN1OctetString rspInfo;
  private PKIStatusInfo status;
  
  public CertResponse(ASN1Integer paramASN1Integer, PKIStatusInfo paramPKIStatusInfo)
  {
    this(paramASN1Integer, paramPKIStatusInfo, null, null);
  }
  
  public CertResponse(ASN1Integer paramASN1Integer, PKIStatusInfo paramPKIStatusInfo, CertifiedKeyPair paramCertifiedKeyPair, ASN1OctetString paramASN1OctetString)
  {
    if (paramASN1Integer == null) {
      throw new IllegalArgumentException("'certReqId' cannot be null");
    }
    if (paramPKIStatusInfo == null) {
      throw new IllegalArgumentException("'status' cannot be null");
    }
    this.certReqId = paramASN1Integer;
    this.status = paramPKIStatusInfo;
    this.certifiedKeyPair = paramCertifiedKeyPair;
    this.rspInfo = paramASN1OctetString;
  }
  
  private CertResponse(ASN1Sequence paramASN1Sequence)
  {
    this.certReqId = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.status = PKIStatusInfo.getInstance(paramASN1Sequence.getObjectAt(1));
    ASN1Encodable localASN1Encodable;
    if (paramASN1Sequence.size() >= 3)
    {
      if (paramASN1Sequence.size() != 3) {
        break label75;
      }
      localASN1Encodable = paramASN1Sequence.getObjectAt(2);
      if ((localASN1Encodable instanceof ASN1OctetString)) {
        this.rspInfo = ASN1OctetString.getInstance(localASN1Encodable);
      }
    }
    else
    {
      return;
    }
    this.certifiedKeyPair = CertifiedKeyPair.getInstance(localASN1Encodable);
    return;
    label75:
    this.certifiedKeyPair = CertifiedKeyPair.getInstance(paramASN1Sequence.getObjectAt(2));
    this.rspInfo = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(3));
  }
  
  public static CertResponse getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertResponse)) {
      return (CertResponse)paramObject;
    }
    if (paramObject != null) {
      return new CertResponse(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getCertReqId()
  {
    return this.certReqId;
  }
  
  public CertifiedKeyPair getCertifiedKeyPair()
  {
    return this.certifiedKeyPair;
  }
  
  public PKIStatusInfo getStatus()
  {
    return this.status;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certReqId);
    localASN1EncodableVector.add(this.status);
    if (this.certifiedKeyPair != null) {
      localASN1EncodableVector.add(this.certifiedKeyPair);
    }
    if (this.rspInfo != null) {
      localASN1EncodableVector.add(this.rspInfo);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CertResponse
 * JD-Core Version:    0.7.0.1
 */