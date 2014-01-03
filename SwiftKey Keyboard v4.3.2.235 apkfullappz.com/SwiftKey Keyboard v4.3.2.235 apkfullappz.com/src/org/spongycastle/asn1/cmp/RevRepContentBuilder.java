package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.CertificateList;

public class RevRepContentBuilder
{
  private ASN1EncodableVector crls = new ASN1EncodableVector();
  private ASN1EncodableVector revCerts = new ASN1EncodableVector();
  private ASN1EncodableVector status = new ASN1EncodableVector();
  
  public RevRepContentBuilder add(PKIStatusInfo paramPKIStatusInfo)
  {
    this.status.add(paramPKIStatusInfo);
    return this;
  }
  
  public RevRepContentBuilder add(PKIStatusInfo paramPKIStatusInfo, CertId paramCertId)
  {
    if (this.status.size() != this.revCerts.size()) {
      throw new IllegalStateException("status and revCerts sequence must be in common order");
    }
    this.status.add(paramPKIStatusInfo);
    this.revCerts.add(paramCertId);
    return this;
  }
  
  public RevRepContentBuilder addCrl(CertificateList paramCertificateList)
  {
    this.crls.add(paramCertificateList);
    return this;
  }
  
  public RevRepContent build()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new DERSequence(this.status));
    if (this.revCerts.size() != 0) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, new DERSequence(this.revCerts)));
    }
    if (this.crls.size() != 0) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, new DERSequence(this.crls)));
    }
    return RevRepContent.getInstance(new DERSequence(localASN1EncodableVector));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.RevRepContentBuilder
 * JD-Core Version:    0.7.0.1
 */