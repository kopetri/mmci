package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.EncryptedValue;

public class CertOrEncCert
  extends ASN1Object
  implements ASN1Choice
{
  private CMPCertificate certificate;
  private EncryptedValue encryptedCert;
  
  private CertOrEncCert(ASN1TaggedObject paramASN1TaggedObject)
  {
    if (paramASN1TaggedObject.getTagNo() == 0)
    {
      this.certificate = CMPCertificate.getInstance(paramASN1TaggedObject.getObject());
      return;
    }
    if (paramASN1TaggedObject.getTagNo() == 1)
    {
      this.encryptedCert = EncryptedValue.getInstance(paramASN1TaggedObject.getObject());
      return;
    }
    throw new IllegalArgumentException("unknown tag: " + paramASN1TaggedObject.getTagNo());
  }
  
  public CertOrEncCert(CMPCertificate paramCMPCertificate)
  {
    if (paramCMPCertificate == null) {
      throw new IllegalArgumentException("'certificate' cannot be null");
    }
    this.certificate = paramCMPCertificate;
  }
  
  public CertOrEncCert(EncryptedValue paramEncryptedValue)
  {
    if (paramEncryptedValue == null) {
      throw new IllegalArgumentException("'encryptedCert' cannot be null");
    }
    this.encryptedCert = paramEncryptedValue;
  }
  
  public static CertOrEncCert getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertOrEncCert)) {
      return (CertOrEncCert)paramObject;
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new CertOrEncCert((ASN1TaggedObject)paramObject);
    }
    return null;
  }
  
  public CMPCertificate getCertificate()
  {
    return this.certificate;
  }
  
  public EncryptedValue getEncryptedCert()
  {
    return this.encryptedCert;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.certificate != null) {
      return new DERTaggedObject(true, 0, this.certificate);
    }
    return new DERTaggedObject(true, 1, this.encryptedCert);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CertOrEncCert
 * JD-Core Version:    0.7.0.1
 */