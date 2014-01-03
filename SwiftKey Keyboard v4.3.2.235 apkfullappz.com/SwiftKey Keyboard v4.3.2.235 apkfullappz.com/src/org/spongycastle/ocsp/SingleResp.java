package org.spongycastle.ocsp;

import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.ocsp.CertStatus;
import org.spongycastle.asn1.ocsp.RevokedInfo;
import org.spongycastle.asn1.ocsp.SingleResponse;
import org.spongycastle.asn1.x509.X509Extensions;

public class SingleResp
  implements java.security.cert.X509Extension
{
  SingleResponse resp;
  
  public SingleResp(SingleResponse paramSingleResponse)
  {
    this.resp = paramSingleResponse;
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    X509Extensions localX509Extensions = getSingleExtensions();
    if (localX509Extensions != null)
    {
      Enumeration localEnumeration = localX509Extensions.oids();
      while (localEnumeration.hasMoreElements())
      {
        DERObjectIdentifier localDERObjectIdentifier = (DERObjectIdentifier)localEnumeration.nextElement();
        if (paramBoolean == localX509Extensions.getExtension(localDERObjectIdentifier).isCritical()) {
          localHashSet.add(localDERObjectIdentifier.getId());
        }
      }
    }
    return localHashSet;
  }
  
  public CertificateID getCertID()
  {
    return new CertificateID(this.resp.getCertID());
  }
  
  public Object getCertStatus()
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
    return getExtensionOIDs(true);
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = getSingleExtensions();
    if (localX509Extensions != null)
    {
      org.spongycastle.asn1.x509.X509Extension localX509Extension = localX509Extensions.getExtension(new DERObjectIdentifier(paramString));
      if (localX509Extension != null) {
        try
        {
          byte[] arrayOfByte = localX509Extension.getValue().getEncoded("DER");
          return arrayOfByte;
        }
        catch (Exception localException)
        {
          throw new RuntimeException("error encoding " + localException.toString());
        }
      }
    }
    return null;
  }
  
  public Date getNextUpdate()
  {
    if (this.resp.getNextUpdate() == null) {
      return null;
    }
    try
    {
      Date localDate = this.resp.getNextUpdate().getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("ParseException: " + localParseException.getMessage());
    }
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public X509Extensions getSingleExtensions()
  {
    return X509Extensions.getInstance(this.resp.getSingleExtensions());
  }
  
  public Date getThisUpdate()
  {
    try
    {
      Date localDate = this.resp.getThisUpdate().getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("ParseException: " + localParseException.getMessage());
    }
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.SingleResp
 * JD-Core Version:    0.7.0.1
 */