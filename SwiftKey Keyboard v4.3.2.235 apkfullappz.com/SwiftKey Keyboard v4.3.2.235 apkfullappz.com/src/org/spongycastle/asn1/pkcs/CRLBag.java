package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CRLBag
  extends ASN1Object
{
  private ASN1ObjectIdentifier crlId;
  private ASN1Encodable crlValue;
  
  public CRLBag(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.crlId = paramASN1ObjectIdentifier;
    this.crlValue = paramASN1Encodable;
  }
  
  private CRLBag(ASN1Sequence paramASN1Sequence)
  {
    this.crlId = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.crlValue = ((DERTaggedObject)paramASN1Sequence.getObjectAt(1)).getObject();
  }
  
  public static CRLBag getInstance(Object paramObject)
  {
    if ((paramObject instanceof CRLBag)) {
      return (CRLBag)paramObject;
    }
    if (paramObject != null) {
      return new CRLBag(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Encodable getCRLValue()
  {
    return this.crlValue;
  }
  
  public ASN1ObjectIdentifier getcrlId()
  {
    return this.crlId;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.crlId);
    localASN1EncodableVector.add(new DERTaggedObject(0, this.crlValue));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.CRLBag
 * JD-Core Version:    0.7.0.1
 */