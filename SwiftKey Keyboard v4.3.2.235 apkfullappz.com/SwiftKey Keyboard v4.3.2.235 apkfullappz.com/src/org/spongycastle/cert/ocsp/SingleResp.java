package org.spongycastle.cert.ocsp;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ocsp.CertStatus;
import org.spongycastle.asn1.ocsp.RevokedInfo;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;

public class SingleResp
{
  private Extensions extensions;
  private SingleResponse resp;
  
  public SingleResp(SingleResponse paramSingleResponse)
  {
    this.resp = paramSingleResponse;
    this.extensions = paramSingleResponse.getSingleExtensions();
  }
  
  public CertificateID getCertID()
  {
    return new CertificateID(this.resp.getCertID());
  }
  
  public CertificateStatus getCertStatus()
  {
    CertStatus localCertStatus = this.resp.getCertStatus();
    if (localCertStatus.getTagNo() == 0) {
      return null;
    }
    if (localCertStatus.getTagNo() == 1) {
      return new RevokedStatus(RevokedInfo.getInstance(localCertStatus.getStatus()));
    }
    return new UnknownStatus();
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return OCSPUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    if (this.extensions != null) {
      return this.extensions.getExtension(paramASN1ObjectIdentifier);
    }
    return null;
  }
  
  public List getExtensionOIDs()
  {
    return OCSPUtils.getExtensionOIDs(this.extensions);
  }
  
  public Date getNextUpdate()
  {
    if (this.resp.getNextUpdate() == null) {
      return null;
    }
    return OCSPUtils.extractDate(this.resp.getNextUpdate());
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return OCSPUtils.getNonCriticalExtensionOIDs(this.extensions);
  }
  
  public Date getThisUpdate()
  {
    return OCSPUtils.extractDate(this.resp.getThisUpdate());
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.ocsp.SingleResp
 * JD-Core Version:    0.7.0.1
 */