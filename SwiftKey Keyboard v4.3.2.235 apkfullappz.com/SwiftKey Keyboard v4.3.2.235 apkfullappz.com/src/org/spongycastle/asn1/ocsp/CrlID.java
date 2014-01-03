package org.spongycastle.asn1.ocsp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1GeneralizedTime;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERIA5String;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CrlID
  extends ASN1Object
{
  private ASN1Integer crlNum;
  private ASN1GeneralizedTime crlTime;
  private DERIA5String crlUrl;
  
  private CrlID(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("unknown tag number: " + localASN1TaggedObject.getTagNo());
      case 0: 
        this.crlUrl = DERIA5String.getInstance(localASN1TaggedObject, true);
        break;
      case 1: 
        this.crlNum = ASN1Integer.getInstance(localASN1TaggedObject, true);
        break;
      case 2: 
        this.crlTime = DERGeneralizedTime.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  public static CrlID getInstance(Object paramObject)
  {
    if ((paramObject instanceof CrlID)) {
      return (CrlID)paramObject;
    }
    if (paramObject != null) {
      return new CrlID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getCrlNum()
  {
    return this.crlNum;
  }
  
  public ASN1GeneralizedTime getCrlTime()
  {
    return this.crlTime;
  }
  
  public DERIA5String getCrlUrl()
  {
    return this.crlUrl;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.crlUrl != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.crlUrl));
    }
    if (this.crlNum != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.crlNum));
    }
    if (this.crlTime != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 2, this.crlTime));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.CrlID
 * JD-Core Version:    0.7.0.1
 */