package org.spongycastle.cert;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.TBSCertList.CRLEntry;
import org.spongycastle.asn1.x509.Time;

public class X509CRLEntryHolder
{
  private GeneralNames ca;
  private TBSCertList.CRLEntry entry;
  
  X509CRLEntryHolder(TBSCertList.CRLEntry paramCRLEntry, boolean paramBoolean, GeneralNames paramGeneralNames)
  {
    this.entry = paramCRLEntry;
    this.ca = paramGeneralNames;
    if ((paramBoolean) && (paramCRLEntry.hasExtensions()))
    {
      Extension localExtension = paramCRLEntry.getExtensions().getExtension(Extension.certificateIssuer);
      if (localExtension != null) {
        this.ca = GeneralNames.getInstance(localExtension.getParsedValue());
      }
    }
  }
  
  public GeneralNames getCertificateIssuer()
  {
    return this.ca;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return CertUtils.getCriticalExtensionOIDs(this.entry.getExtensions());
  }
  
  public Extension getExtension(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    Extensions localExtensions = this.entry.getExtensions();
    if (localExtensions != null) {
      return localExtensions.getExtension(paramASN1ObjectIdentifier);
    }
    return null;
  }
  
  public List getExtensionOIDs()
  {
    return CertUtils.getExtensionOIDs(this.entry.getExtensions());
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return CertUtils.getNonCriticalExtensionOIDs(this.entry.getExtensions());
  }
  
  public Date getRevocationDate()
  {
    return this.entry.getRevocationDate().getDate();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.entry.getUserCertificate().getValue();
  }
  
  public boolean hasExtensions()
  {
    return this.entry.hasExtensions();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509CRLEntryHolder
 * JD-Core Version:    0.7.0.1
 */