package org.spongycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;
import java.security.cert.CRLException;
import java.security.cert.X509CRLEntry;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.DEREnumerated;
import org.spongycastle.asn1.util.ASN1Dump;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.CRLReason;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.TBSCertList.CRLEntry;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.asn1.x509.X509Extension;
import org.spongycastle.x509.extension.X509ExtensionUtil;

public class X509CRLEntryObject
  extends X509CRLEntry
{
  private TBSCertList.CRLEntry c;
  private X500Name certificateIssuer;
  private int hashValue;
  private boolean isHashValueSet;
  
  public X509CRLEntryObject(TBSCertList.CRLEntry paramCRLEntry)
  {
    this.c = paramCRLEntry;
    this.certificateIssuer = null;
  }
  
  public X509CRLEntryObject(TBSCertList.CRLEntry paramCRLEntry, boolean paramBoolean, X500Name paramX500Name)
  {
    this.c = paramCRLEntry;
    this.certificateIssuer = loadCertificateIssuer(paramBoolean, paramX500Name);
  }
  
  private Set getExtensionOIDs(boolean paramBoolean)
  {
    Extensions localExtensions = this.c.getExtensions();
    if (localExtensions != null)
    {
      localHashSet = new HashSet();
      Enumeration localEnumeration = localExtensions.oids();
      while (localEnumeration.hasMoreElements())
      {
        ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
        if (paramBoolean == localExtensions.getExtension(localASN1ObjectIdentifier).isCritical()) {
          localHashSet.add(localASN1ObjectIdentifier.getId());
        }
      }
    }
    HashSet localHashSet = null;
    return localHashSet;
  }
  
  private X500Name loadCertificateIssuer(boolean paramBoolean, X500Name paramX500Name)
  {
    if (!paramBoolean) {
      paramX500Name = null;
    }
    byte[] arrayOfByte;
    do
    {
      return paramX500Name;
      arrayOfByte = getExtensionValue(X509Extension.certificateIssuer.getId());
    } while (arrayOfByte == null);
    try
    {
      GeneralName[] arrayOfGeneralName = GeneralNames.getInstance(X509ExtensionUtil.fromExtensionValue(arrayOfByte)).getNames();
      for (int i = 0; i < arrayOfGeneralName.length; i++) {
        if (arrayOfGeneralName[i].getTagNo() == 4)
        {
          X500Name localX500Name = X500Name.getInstance(arrayOfGeneralName[i].getName());
          return localX500Name;
        }
      }
      return null;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public X500Principal getCertificateIssuer()
  {
    if (this.certificateIssuer == null) {
      return null;
    }
    try
    {
      X500Principal localX500Principal = new X500Principal(this.certificateIssuer.getEncoded());
      return localX500Principal;
    }
    catch (IOException localIOException) {}
    return null;
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return getExtensionOIDs(true);
  }
  
  public byte[] getEncoded()
    throws CRLException
  {
    try
    {
      byte[] arrayOfByte = this.c.getEncoded("DER");
      return arrayOfByte;
    }
    catch (IOException localIOException)
    {
      throw new CRLException(localIOException.toString());
    }
  }
  
  public byte[] getExtensionValue(String paramString)
  {
    Extensions localExtensions = this.c.getExtensions();
    if (localExtensions != null)
    {
      Extension localExtension = localExtensions.getExtension(new ASN1ObjectIdentifier(paramString));
      if (localExtension != null) {
        try
        {
          byte[] arrayOfByte = localExtension.getExtnValue().getEncoded();
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
  
  public Date getRevocationDate()
  {
    return this.c.getRevocationDate().getDate();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.c.getUserCertificate().getValue();
  }
  
  public boolean hasExtensions()
  {
    return this.c.getExtensions() != null;
  }
  
  public boolean hasUnsupportedCriticalExtension()
  {
    Set localSet = getCriticalExtensionOIDs();
    return (localSet != null) && (!localSet.isEmpty());
  }
  
  public int hashCode()
  {
    if (!this.isHashValueSet)
    {
      this.hashValue = super.hashCode();
      this.isHashValueSet = true;
    }
    return this.hashValue;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("      userCertificate: ").append(getSerialNumber()).append(str);
    localStringBuffer.append("       revocationDate: ").append(getRevocationDate()).append(str);
    localStringBuffer.append("       certificateIssuer: ").append(getCertificateIssuer()).append(str);
    Extensions localExtensions = this.c.getExtensions();
    if (localExtensions != null)
    {
      Enumeration localEnumeration = localExtensions.oids();
      if (localEnumeration.hasMoreElements())
      {
        localStringBuffer.append("   crlEntryExtensions:").append(str);
        while (localEnumeration.hasMoreElements())
        {
          ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localEnumeration.nextElement();
          Extension localExtension = localExtensions.getExtension(localASN1ObjectIdentifier);
          if (localExtension.getExtnValue() != null)
          {
            ASN1InputStream localASN1InputStream = new ASN1InputStream(localExtension.getExtnValue().getOctets());
            localStringBuffer.append("                       critical(").append(localExtension.isCritical()).append(") ");
            try
            {
              if (!localASN1ObjectIdentifier.equals(X509Extension.reasonCode)) {
                break label247;
              }
              localStringBuffer.append(CRLReason.getInstance(DEREnumerated.getInstance(localASN1InputStream.readObject()))).append(str);
            }
            catch (Exception localException)
            {
              localStringBuffer.append(localASN1ObjectIdentifier.getId());
              localStringBuffer.append(" value = *****").append(str);
            }
            continue;
            label247:
            if (localASN1ObjectIdentifier.equals(X509Extension.certificateIssuer))
            {
              localStringBuffer.append("Certificate issuer: ").append(GeneralNames.getInstance(localASN1InputStream.readObject())).append(str);
            }
            else
            {
              localStringBuffer.append(localASN1ObjectIdentifier.getId());
              localStringBuffer.append(" value = ").append(ASN1Dump.dumpAsString(localASN1InputStream.readObject())).append(str);
            }
          }
          else
          {
            localStringBuffer.append(str);
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.jce.provider.X509CRLEntryObject
 * JD-Core Version:    0.7.0.1
 */