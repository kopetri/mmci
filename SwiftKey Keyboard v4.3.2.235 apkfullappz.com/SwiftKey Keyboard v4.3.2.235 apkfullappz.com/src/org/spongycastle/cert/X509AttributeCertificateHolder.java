package org.spongycastle.cert;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DEROutputStream;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.AttCertValidityPeriod;
import org.spongycastle.asn1.x509.Attribute;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.AttributeCertificateInfo;
import org.spongycastle.asn1.x509.Extension;
import org.spongycastle.asn1.x509.Extensions;
import org.spongycastle.asn1.x509.Holder;
import org.spongycastle.operator.ContentVerifier;
import org.spongycastle.operator.ContentVerifierProvider;

public class X509AttributeCertificateHolder
{
  private static Attribute[] EMPTY_ARRAY = new Attribute[0];
  private AttributeCertificate attrCert;
  private Extensions extensions;
  
  public X509AttributeCertificateHolder(AttributeCertificate paramAttributeCertificate)
  {
    this.attrCert = paramAttributeCertificate;
    this.extensions = paramAttributeCertificate.getAcinfo().getExtensions();
  }
  
  public X509AttributeCertificateHolder(byte[] paramArrayOfByte)
    throws IOException
  {
    this(parseBytes(paramArrayOfByte));
  }
  
  private static AttributeCertificate parseBytes(byte[] paramArrayOfByte)
    throws IOException
  {
    try
    {
      AttributeCertificate localAttributeCertificate = AttributeCertificate.getInstance(ASN1Primitive.fromByteArray(paramArrayOfByte));
      return localAttributeCertificate;
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
    if (!(paramObject instanceof X509AttributeCertificateHolder)) {
      return false;
    }
    X509AttributeCertificateHolder localX509AttributeCertificateHolder = (X509AttributeCertificateHolder)paramObject;
    return this.attrCert.equals(localX509AttributeCertificateHolder.attrCert);
  }
  
  public Attribute[] getAttributes()
  {
    ASN1Sequence localASN1Sequence = this.attrCert.getAcinfo().getAttributes();
    Attribute[] arrayOfAttribute = new Attribute[localASN1Sequence.size()];
    for (int i = 0; i != localASN1Sequence.size(); i++) {
      arrayOfAttribute[i] = Attribute.getInstance(localASN1Sequence.getObjectAt(i));
    }
    return arrayOfAttribute;
  }
  
  public Attribute[] getAttributes(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    ASN1Sequence localASN1Sequence = this.attrCert.getAcinfo().getAttributes();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i != localASN1Sequence.size(); i++)
    {
      Attribute localAttribute = Attribute.getInstance(localASN1Sequence.getObjectAt(i));
      if (localAttribute.getAttrType().equals(paramASN1ObjectIdentifier)) {
        localArrayList.add(localAttribute);
      }
    }
    if (localArrayList.size() == 0) {
      return EMPTY_ARRAY;
    }
    return (Attribute[])localArrayList.toArray(new Attribute[localArrayList.size()]);
  }
  
  public Set getCriticalExtensionOIDs()
  {
    return CertUtils.getCriticalExtensionOIDs(this.extensions);
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    return this.attrCert.getEncoded();
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
  
  public AttributeCertificateHolder getHolder()
  {
    return new AttributeCertificateHolder((ASN1Sequence)this.attrCert.getAcinfo().getHolder().toASN1Primitive());
  }
  
  public AttributeCertificateIssuer getIssuer()
  {
    return new AttributeCertificateIssuer(this.attrCert.getAcinfo().getIssuer());
  }
  
  public boolean[] getIssuerUniqueID()
  {
    return CertUtils.bitStringToBoolean(this.attrCert.getAcinfo().getIssuerUniqueID());
  }
  
  public Set getNonCriticalExtensionOIDs()
  {
    return CertUtils.getNonCriticalExtensionOIDs(this.extensions);
  }
  
  public Date getNotAfter()
  {
    return CertUtils.recoverDate(this.attrCert.getAcinfo().getAttrCertValidityPeriod().getNotAfterTime());
  }
  
  public Date getNotBefore()
  {
    return CertUtils.recoverDate(this.attrCert.getAcinfo().getAttrCertValidityPeriod().getNotBeforeTime());
  }
  
  public BigInteger getSerialNumber()
  {
    return this.attrCert.getAcinfo().getSerialNumber().getValue();
  }
  
  public byte[] getSignature()
  {
    return this.attrCert.getSignatureValue().getBytes();
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.attrCert.getSignatureAlgorithm();
  }
  
  public int getVersion()
  {
    return 1 + this.attrCert.getAcinfo().getVersion().getValue().intValue();
  }
  
  public boolean hasExtensions()
  {
    return this.extensions != null;
  }
  
  public int hashCode()
  {
    return this.attrCert.hashCode();
  }
  
  public boolean isSignatureValid(ContentVerifierProvider paramContentVerifierProvider)
    throws CertException
  {
    AttributeCertificateInfo localAttributeCertificateInfo = this.attrCert.getAcinfo();
    if (!localAttributeCertificateInfo.getSignature().equals(this.attrCert.getSignatureAlgorithm())) {
      throw new CertException("signature invalid - algorithm identifier mismatch");
    }
    try
    {
      ContentVerifier localContentVerifier = paramContentVerifierProvider.get(localAttributeCertificateInfo.getSignature());
      OutputStream localOutputStream = localContentVerifier.getOutputStream();
      new DEROutputStream(localOutputStream).writeObject(localAttributeCertificateInfo);
      localOutputStream.close();
      return localContentVerifier.verify(this.attrCert.getSignatureValue().getBytes());
    }
    catch (Exception localException)
    {
      throw new CertException("unable to process signature: " + localException.getMessage(), localException);
    }
  }
  
  public boolean isValidOn(Date paramDate)
  {
    AttCertValidityPeriod localAttCertValidityPeriod = this.attrCert.getAcinfo().getAttrCertValidityPeriod();
    return (!paramDate.before(CertUtils.recoverDate(localAttCertValidityPeriod.getNotBeforeTime()))) && (!paramDate.after(CertUtils.recoverDate(localAttCertValidityPeriod.getNotAfterTime())));
  }
  
  public AttributeCertificate toASN1Structure()
  {
    return this.attrCert;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.cert.X509AttributeCertificateHolder
 * JD-Core Version:    0.7.0.1
 */