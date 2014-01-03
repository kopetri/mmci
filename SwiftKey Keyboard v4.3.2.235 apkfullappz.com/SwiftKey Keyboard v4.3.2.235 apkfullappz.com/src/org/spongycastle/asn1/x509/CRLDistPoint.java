package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class CRLDistPoint
  extends ASN1Object
{
  ASN1Sequence seq = null;
  
  private CRLDistPoint(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
  }
  
  public CRLDistPoint(DistributionPoint[] paramArrayOfDistributionPoint)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != paramArrayOfDistributionPoint.length; i++) {
      localASN1EncodableVector.add(paramArrayOfDistributionPoint[i]);
    }
    this.seq = new DERSequence(localASN1EncodableVector);
  }
  
  public static CRLDistPoint getInstance(Object paramObject)
  {
    if ((paramObject instanceof CRLDistPoint)) {
      return (CRLDistPoint)paramObject;
    }
    if (paramObject != null) {
      return new CRLDistPoint(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static CRLDistPoint getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public DistributionPoint[] getDistributionPoints()
  {
    DistributionPoint[] arrayOfDistributionPoint = new DistributionPoint[this.seq.size()];
    for (int i = 0; i != this.seq.size(); i++) {
      arrayOfDistributionPoint[i] = DistributionPoint.getInstance(this.seq.getObjectAt(i));
    }
    return arrayOfDistributionPoint;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = System.getProperty("line.separator");
    localStringBuffer.append("CRLDistPoint:");
    localStringBuffer.append(str);
    DistributionPoint[] arrayOfDistributionPoint = getDistributionPoints();
    for (int i = 0; i != arrayOfDistributionPoint.length; i++)
    {
      localStringBuffer.append("    ");
      localStringBuffer.append(arrayOfDistributionPoint[i]);
      localStringBuffer.append(str);
    }
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.CRLDistPoint
 * JD-Core Version:    0.7.0.1
 */