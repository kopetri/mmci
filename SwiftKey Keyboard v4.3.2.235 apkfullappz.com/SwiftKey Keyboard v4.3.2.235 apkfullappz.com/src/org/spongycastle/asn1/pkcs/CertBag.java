package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CertBag
  extends ASN1Object
{
  private ASN1ObjectIdentifier certId;
  private ASN1Encodable certValue;
  
  public CertBag(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.certId = paramASN1ObjectIdentifier;
    this.certValue = paramASN1Encodable;
  }
  
  private CertBag(ASN1Sequence paramASN1Sequence)
  {
    this.certId = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.certValue = ((DERTaggedObject)paramASN1Sequence.getObjectAt(1)).getObject();
  }
  
  public static CertBag getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertBag)) {
      return (CertBag)paramObject;
    }
    if (paramObject != null) {
      return new CertBag(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getCertId()
  {
    return this.certId;
  }
  
  public ASN1Encodable getCertValue()
  {
    return this.certValue;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certId);
    localASN1EncodableVector.add(new DERTaggedObject(0, this.certValue));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.CertBag
 * JD-Core Version:    0.7.0.1
 */