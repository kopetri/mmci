package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CertRequest
  extends ASN1Object
{
  private ASN1Integer certReqId;
  private CertTemplate certTemplate;
  private Controls controls;
  
  public CertRequest(int paramInt, CertTemplate paramCertTemplate, Controls paramControls)
  {
    this(new ASN1Integer(paramInt), paramCertTemplate, paramControls);
  }
  
  public CertRequest(ASN1Integer paramASN1Integer, CertTemplate paramCertTemplate, Controls paramControls)
  {
    this.certReqId = paramASN1Integer;
    this.certTemplate = paramCertTemplate;
    this.controls = paramControls;
  }
  
  private CertRequest(ASN1Sequence paramASN1Sequence)
  {
    this.certReqId = new ASN1Integer(ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0)).getValue());
    this.certTemplate = CertTemplate.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.controls = Controls.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public static CertRequest getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertRequest)) {
      return (CertRequest)paramObject;
    }
    if (paramObject != null) {
      return new CertRequest(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getCertReqId()
  {
    return this.certReqId;
  }
  
  public CertTemplate getCertTemplate()
  {
    return this.certTemplate;
  }
  
  public Controls getControls()
  {
    return this.controls;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certReqId);
    localASN1EncodableVector.add(this.certTemplate);
    if (this.controls != null) {
      localASN1EncodableVector.add(this.controls);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CertRequest
 * JD-Core Version:    0.7.0.1
 */