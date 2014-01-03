package org.spongycastle.cert;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1InputStream;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.CertificateList;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.GeneralNames;
import org.spongycastle.asn1.x509.IssuingDistributionPoint;
import org.spongycastle.asn1.x509.TBSCertList;
import org.spongycastle.asn1.x509.TBSCertList.CRLEntry;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class X509CRLHolder
{
  private Extensions extensions;
  private boolean isIndirect;
  private GeneralNames issuerName;
  private CertificateList x509CRL;
  
  public X509CRLHolder(InputStream paramInputStream)
    throws IOException
  {
    this(parseStream(paramInputStream));
  }
  
  public X509CRLHolder(CertificateList paramCertificateList)
  {
    this.x509CRL = paramCertificateList;
    this.extensions = paramCertificateList.getTBSCertList().getExtensions();
    this.isIndirect = isIndirectCRL(this.extensions);
    this.issuerName = new GeneralNames(new GeneralName(paramCertificateList.getIssuer()));
  }
  
  public X509CRLHolder(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseStream(new ByteArrayInputStream(paramArrayOfByte)));
  }
  
  private static boolean isIndirectCRL(Extensions paramExtensions)
  {
    if (paramExtensions == null) {}
    Extension localExtension;
    do
    {
      return false;
      localExtension = paramExtensions.getExtension(Extension.issuingDistributionPoint);
    } while ((localExtension == null) || (!IssuingDistributionPoint.getInstance(localExtension.getParsedValue()).isIndirectCRL()));
    return true;
  }
  
  private static CertificateList parseStream(InputStream paramInputStream)
    throws IOException
  {
    try
    {
      CertificateList localCertificateList = CertificateList.getInstance(new ASN1InputStream(paramInputStream, true).readObject());
      return localCertificateList;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new CertIOException("malformed data: " + localClassCastException.getMessage(), localClassCastException);
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      throw new CertIOException("malformed data: " + localIllegalArgumentException.getMessage(), localIllegalArgumentException);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == this) {
      return true;
    }
    if (!(paramObject instanceof X509CRLHolder)) {
      return false;
    }
    X509CRLHolder localX509CRLHolder = (X509CRLHolder)paramObject;
    return this.x509CRL.equals(localX509CRLHolder.x509CRL);
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return CertUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.x509CRL.getEncoded();
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
    return CertUtils.getExtensionOIDs(this.extensions);
  }
  
  public X500Name getIssuer()
  {
    return X500Name.getInstance(this.x509CRL.getIssuer());
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return CertUtils.getNonCriticalExtensionOIDs(this.extensions);
  }
  
  public X509CRLEntryHolder getRevokedCertificate(BigInteger paramBigInteger)
  {
    GeneralNames localGeneralNames = this.issuerName;
    Enumeration localEnumeration = this.x509CRL.getRevokedCertificateEnumeration();
    while (localEnumeration.hasMoreElements())
    {
      TBSCertList.CRLEntry localCRLEntry = (TBSCertList.CRLEntry)localEnumeration.nextElement();
      if (localCRLEntry.getUserCertificate().getValue().equals(paramBigInteger)) {
        return new X509CRLEntryHolder(localCRLEntry, this.isIndirect, localGeneralNames);
      }
      if ((this.isIndirect) && (localCRLEntry.hasExtensions()))
      {
        Extension localExtension = localCRLEntry.getExtensions().getExtension(Extension.certificateIssuer);
        if (localExtension != null) {
          localGeneralNames = GeneralNames.getInstance(localExtension.getParsedValue());
        }
      }
    }
    return null;
  }
  
  public Collection getRevokedCertificates()
  {
    ArrayList localArrayList = new ArrayList(this.x509CRL.getRevokedCertificates().length);
    GeneralNames localGeneralNames = this.issuerName;
    Enumeration localEnumeration = this.x509CRL.getRevokedCertificateEnumeration();
    while (localEnumeration.hasMoreElements())
    {
      X509CRLEntryHolder localX509CRLEntryHolder = new X509CRLEntryHolder((TBSCertList.CRLEntry)localEnumeration.nextElement(), this.isIndirect, localGeneralNames);
      localArrayList.add(localX509CRLEntryHolder);
      localGeneralNames = localX509CRLEntryHolder.getCertificateIssuer();
    }
    return localArrayList;
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public int hashCode()
  {
    return this.x509CRL.hashCode();
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws CertException
  {
    TBSCertList localTBSCertList = this.x509CRL.getTBSCertList();
    if (!localTBSCertList.getSignature().equals(this.x509CRL.getSignatureAlgorithm())) {
      throw new CertException("signature invalid - algorithm identifier mismatch");
    }
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(localTBSCertList.getSignature());
      OutputStream localOutputStream = localContentVerifier.getOutputStream();
      new DEROutputStream(localOutputStream).writeObject(localTBSCertList);
      localOutputStream.close();
      return localContentVerifier.verify(this.x509CRL.getSignature().getBytes());
    }
    catch (Exception localException)
    {
      throw new CertException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
  
  public CertificateList toASN1Structure()
  {
    return this.x509CRL;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509CRLHolder
 * JD-Core Version:    0.7.0.1
 */