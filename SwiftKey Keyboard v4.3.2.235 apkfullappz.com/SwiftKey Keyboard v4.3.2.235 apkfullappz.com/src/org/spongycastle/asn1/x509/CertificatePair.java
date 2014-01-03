package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CertificatePair
  extends ASN1Object
{
  private X509CertificateStructure forward;
  private X509CertificateStructure reverse;
  
  private CertificatePair(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() != 1) && (paramASN1Sequence.size() != 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      if (localASN1TaggedObject.getTagNo() == 0) {
        this.forward = X509CertificateStructure.getInstance(localASN1TaggedObject, true);
      } else if (localASN1TaggedObject.getTagNo() == 1) {
        this.reverse = X509CertificateStructure.getInstance(localASN1TaggedObject, true);
      } else {
        throw new IllegalArgumentException("Bad tag number: " + localASN1TaggedObject.getTagNo());
      }
    }
  }
  
  public CertificatePair(X509CertificateStructure paramX509CertificateStructure1, X509CertificateStructure paramX509CertificateStructure2)
  {
    this.forward = paramX509CertificateStructure1;
    this.reverse = paramX509CertificateStructure2;
  }
  
  public static CertificatePair getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof CertificatePair))) {
      return (CertificatePair)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new CertificatePair((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public X509CertificateStructure getForward()
  {
    return this.forward;
  }
  
  public X509CertificateStructure getReverse()
  {
    return this.reverse;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.forward != null) {
      localASN1EncodableVector.add(new DERTaggedObject(0, this.forward));
    }
    if (this.reverse != null) {
      localASN1EncodableVector.add(new DERTaggedObject(1, this.reverse));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.CertificatePair
 * JD-Core Version:    0.7.0.1
 */