package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;
import org.spongycastle.asn1.x509.X509Name;

public class CertificationRequestInfo
  extends ASN1Object
{
  ASN1Set attributes = null;
  X500Name subject;
  SubjectPublicKeyInfo subjectPKInfo;
  ASN1Integer version = new ASN1Integer(0);
  
  public CertificationRequestInfo(ASN1Sequence paramASN1Sequence)
  {
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    this.subject = X500Name.getInstance(paramASN1Sequence.getObjectAt(1));
    this.subjectPKInfo = SubjectPublicKeyInfo.getInstance(paramASN1Sequence.getObjectAt(2));
    if (paramASN1Sequence.size() > 3) {
      this.attributes = ASN1Set.getInstance((DERTaggedObject)paramASN1Sequence.getObjectAt(3), false);
    }
    if ((this.subject == null) || (this.version == null) || (this.subjectPKInfo == null)) {
      throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
    }
  }
  
  public CertificationRequestInfo(X500Name paramX500Name, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1Set paramASN1Set)
  {
    this.subject = paramX500Name;
    this.subjectPKInfo = paramSubjectPublicKeyInfo;
    this.attributes = paramASN1Set;
    if ((paramX500Name == null) || (this.version == null) || (this.subjectPKInfo == null)) {
      throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
    }
  }
  
  public CertificationRequestInfo(X509Name paramX509Name, SubjectPublicKeyInfo paramSubjectPublicKeyInfo, ASN1Set paramASN1Set)
  {
    this.subject = X500Name.getInstance(paramX509Name.toASN1Primitive());
    this.subjectPKInfo = paramSubjectPublicKeyInfo;
    this.attributes = paramASN1Set;
    if ((paramX509Name == null) || (this.version == null) || (this.subjectPKInfo == null)) {
      throw new IllegalArgumentException("Not all mandatory fields set in CertificationRequestInfo generator.");
    }
  }
  
  public static CertificationRequestInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertificationRequestInfo)) {
      return (CertificationRequestInfo)paramObject;
    }
    if (paramObject != null) {
      return new CertificationRequestInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Set getAttributes()
  {
    return this.attributes;
  }
  
  public X500Name getSubject()
  {
    return this.subject;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.subjectPKInfo;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.subject);
    localASN1EncodableVector.add(this.subjectPKInfo);
    if (this.attributes != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.attributes));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.CertificationRequestInfo
 * JD-Core Version:    0.7.0.1
 */