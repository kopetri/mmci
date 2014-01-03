package org.spongycastle.cert.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.cmp.RevDetails;
import org.spongycastle.asn1.crmf.CertTemplate;
import org.spongycastle.asn1.x500.X500Name;

public class RevocationDetails
{
  private RevDetails revDetails;
  
  public RevocationDetails(RevDetails paramRevDetails)
  {
    this.revDetails = paramRevDetails;
  }
  
  public X500Name getIssuer()
  {
    return this.revDetails.getCertDetails().getIssuer();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.revDetails.getCertDetails().getSerialNumber().getValue();
  }
  
  public X500Name getSubject()
  {
    return this.revDetails.getCertDetails().getSubject();
  }
  
  public RevDetails toASN1Structure()
  {
    return this.revDetails;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.cmp.RevocationDetails
 * JD-Core Version:    0.7.0.1
 */