package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.spongycastle.asn1.x500.X500Name;

public class X509CertificateStructure
  extends ASN1Object
  implements PKCSObjectIdentifiers, X509ObjectIdentifiers
{
  ASN1Sequence seq;
  DERBitString sig;
  AlgorithmIdentifier sigAlgId;
  TBSCertificateStructure tbsCert;
  
  public X509CertificateStructure(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
    if (paramASN1Sequence.size() == 3)
    {
      this.tbsCert = TBSCertificateStructure.getInstance(paramASN1Sequence.getObjectAt(0));
      this.sigAlgId = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
      this.sig = DERBitString.getInstance(paramASN1Sequence.getObjectAt(2));
      return;
    }
    throw new IllegalArgumentException("sequence wrong size for a certificate");
  }
  
  public static X509CertificateStructure getInstance(Object paramObject)
  {
    if ((paramObject instanceof X509CertificateStructure)) {
      return (X509CertificateStructure)paramObject;
    }
    if (paramObject != null) {
      return new X509CertificateStructure(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static X509CertificateStructure getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public Time getEndDate()
  {
    return this.tbsCert.getEndDate();
  }
  
  public X500Name getIssuer()
  {
    return this.tbsCert.getIssuer();
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.tbsCert.getSerialNumber();
  }
  
  public DERBitString getSignature()
  {
    return this.sig;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.sigAlgId;
  }
  
  public Time getStartDate()
  {
    return this.tbsCert.getStartDate();
  }
  
  public X500Name getSubject()
  {
    return this.tbsCert.getSubject();
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.tbsCert.getSubjectPublicKeyInfo();
  }
  
  public TBSCertificateStructure getTBSCertificate()
  {
    return this.tbsCert;
  }
  
  public int getVersion()
  {
    return this.tbsCert.getVersion();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.X509CertificateStructure
 * JD-Core Version:    0.7.0.1
 */