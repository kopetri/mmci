package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AttributeCertificate;
import org.spongycastle.asn1.x509.Certificate;

public class CMPCertificate
  extends ASN1Object
  implements ASN1Choice
{
  private AttributeCertificate x509v2AttrCert;
  private Certificate x509v3PKCert;
  
  public CMPCertificate(AttributeCertificate paramAttributeCertificate)
  {
    this.x509v2AttrCert = paramAttributeCertificate;
  }
  
  public CMPCertificate(Certificate paramCertificate)
  {
    if (paramCertificate.getVersionNumber() != 3) {
      throw new IllegalArgumentException("only version 3 certificates allowed");
    }
    this.x509v3PKCert = paramCertificate;
  }
  
  public static CMPCertificate getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof CMPCertificate))) {
      return (CMPCertificate)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new CMPCertificate(Certificate.getInstance(paramObject));
    }
    if ((paramObject instanceof ASN1TaggedObject)) {
      return new CMPCertificate(AttributeCertificate.getInstance(((ASN1TaggedObject)paramObject).getObject()));
    }
    throw new IllegalArgumentException("Invalid object: " + paramObject.getClass().getName());
  }
  
  public AttributeCertificate getX509v2AttrCert()
  {
    return this.x509v2AttrCert;
  }
  
  public Certificate getX509v3PKCert()
  {
    return this.x509v3PKCert;
  }
  
  public boolean isX509v3PKCert()
  {
    return this.x509v3PKCert != null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    if (this.x509v2AttrCert != null) {
      return new DERTaggedObject(true, 1, this.x509v2AttrCert);
    }
    return this.x509v3PKCert.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CMPCertificate
 * JD-Core Version:    0.7.0.1
 */