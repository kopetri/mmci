package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.CertificateList;

public class CRLAnnContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private CRLAnnContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public CRLAnnContent(CertificateList paramCertificateList)
  {
    this.content = new DERSequence(paramCertificateList);
  }
  
  public static CRLAnnContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof CRLAnnContent)) {
      return (CRLAnnContent)paramObject;
    }
    if (paramObject != null) {
      return new CRLAnnContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertificateList[] getCertificateLists()
  {
    CertificateList[] arrayOfCertificateList = new CertificateList[this.content.size()];
    for (int i = 0; i != arrayOfCertificateList.length; i++) {
      arrayOfCertificateList[i] = CertificateList.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfCertificateList;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CRLAnnContent
 * JD-Core Version:    0.7.0.1
 */