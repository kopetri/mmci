package org.spongycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.Certificate;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.TBSCertificate;
import org.spongycastle.asn1.x509.Time;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class X509CertificateHolder
{
  private Extensions extensions;
  private Certificate x509Certificate;
  
  public X509CertificateHolder(Certificate paramCertificate)
  {
    this.x509Certificate = paramCertificate;
    this.extensions = paramCertificate.getTBSCertificate().getExtensions();
  }
  
  public X509CertificateHolder(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private static Certificate parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      Certificate localCertificate = Certificate.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
      return localCertificate;
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
    if (!(paramObject instanceof X509CertificateHolder)) {
      return false;
    }
    X509CertificateHolder localX509CertificateHolder = (X509CertificateHolder)paramObject;
    return this.x509Certificate.equals(localX509CertificateHolder.x509Certificate);
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return CertUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.x509Certificate.getEncoded();
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
    return X500Name.getInstance(this.x509Certificate.getIssuer());
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return CertUtils.getNonCriticalExtensionOIDs(this.extensions);
  }
  
  public Date getNotAfter()
  {
    return this.x509Certificate.getEndDate().getDate();
  }
  
  public Date getNotBefore()
  {
    return this.x509Certificate.getStartDate().getDate();
  }
  
  public BigInteger getSerialNumber()
  {
    return this.x509Certificate.getSerialNumber().getValue();
  }
  
  public byte[] getSignature()
  {
    return this.x509Certificate.getSignature().getBytes();
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.x509Certificate.getSignatureAlgorithm();
  }
  
  public X500Name getSubject()
  {
    return X500Name.getInstance(this.x509Certificate.getSubject());
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.x509Certificate.getSubjectPublicKeyInfo();
  }
  
  public int getVersion()
  {
    return this.x509Certificate.getVersionNumber();
  }
  
  public int getVersionNumber()
  {
    return this.x509Certificate.getVersionNumber();
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public int hashCode()
  {
    return this.x509Certificate.hashCode();
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws CertException
  {
    TBSCertificate localTBSCertificate = this.x509Certificate.getTBSCertificate();
    if (!localTBSCertificate.getSignature().equals(this.x509Certificate.getSignatureAlgorithm())) {
      throw new CertException("signature invalid - algorithm identifier mismatch");
    }
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(localTBSCertificate.getSignature());
      OutputStream localOutputStream = localContentVerifier.getOutputStream();
      new DEROutputStream(localOutputStream).writeObject(localTBSCertificate);
      localOutputStream.close();
      return localContentVerifier.verify(this.x509Certificate.getSignature().getBytes());
    }
    catch (Exception localException)
    {
      throw new CertException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
  
  public boolean isValidOn(Date paramDate)
  {
    return (!paramDate.before(this.x509Certificate.getStartDate().getDate())) && (!paramDate.after(this.x509Certificate.getEndDate().getDate()));
  }
  
  public Certificate toASN1Structure()
  {
    return this.x509Certificate;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509CertificateHolder
 * JD-Core Version:    0.7.0.1
 */