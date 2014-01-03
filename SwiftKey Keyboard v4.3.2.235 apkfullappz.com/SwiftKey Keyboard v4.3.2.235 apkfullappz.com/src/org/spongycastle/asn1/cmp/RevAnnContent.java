package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.Extensions;

public class RevAnnContent
  extends ASN1Object
{
  private DERGeneralizedTime badSinceDate;
  private CertId certId;
  private Extensions crlDetails;
  private PKIStatus status;
  private DERGeneralizedTime willBeRevokedAt;
  
  private RevAnnContent(ASN1Sequence paramASN1Sequence)
  {
    this.status = PKIStatus.getInstance(paramASN1Sequence.getObjectAt(0));
    this.certId = CertId.getInstance(paramASN1Sequence.getObjectAt(1));
    this.willBeRevokedAt = DERGeneralizedTime.getInstance(paramASN1Sequence.getObjectAt(2));
    this.badSinceDate = DERGeneralizedTime.getInstance(paramASN1Sequence.getObjectAt(3));
    if (paramASN1Sequence.size() > 4) {
      this.crlDetails = Extensions.getInstance(paramASN1Sequence.getObjectAt(4));
    }
  }
  
  public static RevAnnContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevAnnContent)) {
      return (RevAnnContent)paramObject;
    }
    if (paramObject != null) {
      return new RevAnnContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERGeneralizedTime getBadSinceDate()
  {
    return this.badSinceDate;
  }
  
  public CertId getCertId()
  {
    return this.certId;
  }
  
  public Extensions getCrlDetails()
  {
    return this.crlDetails;
  }
  
  public PKIStatus getStatus()
  {
    return this.status;
  }
  
  public DERGeneralizedTime getWillBeRevokedAt()
  {
    return this.willBeRevokedAt;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.status);
    localASN1EncodableVector.add(this.certId);
    localASN1EncodableVector.add(this.willBeRevokedAt);
    localASN1EncodableVector.add(this.badSinceDate);
    if (this.crlDetails != null) {
      localASN1EncodableVector.add(this.crlDetails);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.RevAnnContent
 * JD-Core Version:    0.7.0.1
 */