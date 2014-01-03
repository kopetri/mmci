package org.spongycastle.ocsp;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DERObjectIdentifier;
import org.spongycastle.asn1.ocsp.Request;
import org.spongycastle.asn1.x509.X509Extensions;

public class Req
  implements java.security.cert.X509Extension
{
  private Request req;
  
  public Req(Request paramRequest)
  {
    this.req = paramRequest;
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    HashSet localHashSet = new HashSet();
    X509Extensions localX509Extensions = getSingleRequestExtensions();
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
    return new CertificateID(this.req.getReqCert());
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    X509Extensions localX509Extensions = getSingleRequestExtensions();
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
  
  public Set getNonCriticalExtensionOIDs()
  {
    return getExtensionOIDs(false);
  }
  
  public X509Extensions getSingleRequestExtensions()
  {
    return X509Extensions.getInstance(this.req.getSingleRequestExtensions());
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.ocsp.Req
 * JD-Core Version:    0.7.0.1
 */