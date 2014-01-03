package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.X509Extensions;

public class RevDetails
  extends ASN1Object
{
  private CertTemplate certDetails;
  private Extensions crlEntryDetails;
  
  private RevDetails(ASN1Sequence paramASN1Sequence)
  {
    this.certDetails = CertTemplate.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.crlEntryDetails = Extensions.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public RevDetails(CertTemplate paramCertTemplate)
  {
    this.certDetails = paramCertTemplate;
  }
  
  public RevDetails(CertTemplate paramCertTemplate, Extensions paramExtensions)
  {
    this.crlEntryDetails = paramExtensions;
  }
  
  public RevDetails(CertTemplate paramCertTemplate, X509Extensions paramX509Extensions)
  {
    this.crlEntryDetails = Extensions.getInstance(paramX509Extensions.toASN1Primitive());
  }
  
  public static RevDetails getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevDetails)) {
      return (RevDetails)paramObject;
    }
    if (paramObject != null) {
      return new RevDetails(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertTemplate getCertDetails()
  {
    return this.certDetails;
  }
  
  public Extensions getCrlEntryDetails()
  {
    return this.crlEntryDetails;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certDetails);
    if (this.crlEntryDetails != null) {
      localASN1EncodableVector.add(this.crlEntryDetails);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.RevDetails
 * JD-Core Version:    0.7.0.1
 */