package org.spongycastle.cert.cmp;

import org.spongycastle.asn1.cmp.CertConfirmContent;
import org.spongycastle.asn1.cmp.CertStatus;
import org.spongycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.spongycastle.operator.DigestAlgorithmIdentifierFinder;

public class CertificateConfirmationContent
{
  private CertConfirmContent content;
  private DigestAlgorithmIdentifierFinder digestAlgFinder;
  
  public CertificateConfirmationContent(CertConfirmContent paramCertConfirmContent)
  {
    this(paramCertConfirmContent, new DefaultDigestAlgorithmIdentifierFinder());
  }
  
  public CertificateConfirmationContent(CertConfirmContent paramCertConfirmContent, DigestAlgorithmIdentifierFinder paramDigestAlgorithmIdentifierFinder)
  {
    this.digestAlgFinder = paramDigestAlgorithmIdentifierFinder;
    this.content = paramCertConfirmContent;
  }
  
  public CertificateStatus[] getStatusMessages()
  {
    CertStatus[] arrayOfCertStatus = this.content.toCertStatusArray();
    CertificateStatus[] arrayOfCertificateStatus = new CertificateStatus[arrayOfCertStatus.length];
    for (int i = 0; i != arrayOfCertificateStatus.length; i++) {
      arrayOfCertificateStatus[i] = new CertificateStatus(this.digestAlgFinder, arrayOfCertStatus[i]);
    }
    return arrayOfCertificateStatus;
  }
  
  public CertConfirmContent toASN1Structure()
  {
    return this.content;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.CertificateConfirmationContent
 * JD-Core Version:    0.7.0.1
 */