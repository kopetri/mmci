package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class PrivateKeyUsagePeriod
  extends ASN1Object
{
  private DERGeneralizedTime _notAfter;
  private DERGeneralizedTime _notBefore;
  
  private PrivateKeyUsagePeriod(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localEnumeration.nextElement();
      if (localASN1TaggedObject.getTagNo() == 0) {
        this._notBefore = DERGeneralizedTime.getInstance(localASN1TaggedObject, false);
      } else if (localASN1TaggedObject.getTagNo() == 1) {
        this._notAfter = DERGeneralizedTime.getInstance(localASN1TaggedObject, false);
      }
    }
  }
  
  public static PrivateKeyUsagePeriod getInstance(Object paramObject)
  {
    if ((paramObject instanceof PrivateKeyUsagePeriod)) {
      return (PrivateKeyUsagePeriod)paramObject;
    }
    if (paramObject != null) {
      return new PrivateKeyUsagePeriod(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERGeneralizedTime getNotAfter()
  {
    return this._notAfter;
  }
  
  public DERGeneralizedTime getNotBefore()
  {
    return this._notBefore;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this._notBefore != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this._notBefore));
    }
    if (this._notAfter != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this._notAfter));
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.PrivateKeyUsagePeriod
 * JD-Core Version:    0.7.0.1
 */