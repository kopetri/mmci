package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class DistributionPoint
  extends ASN1Object
{
  GeneralNames cRLIssuer;
  DistributionPointName distributionPoint;
  ReasonFlags reasons;
  
  public DistributionPoint(ASN1Sequence paramASN1Sequence)
  {
    int i = 0;
    if (i != paramASN1Sequence.size())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(paramASN1Sequence.getObjectAt(i));
      switch (localASN1TaggedObject.getTagNo())
      {
      }
      for (;;)
      {
        i++;
        break;
        this.distributionPoint = DistributionPointName.getInstance(localASN1TaggedObject, true);
        continue;
        this.reasons = new ReasonFlags(DERBitString.getInstance(localASN1TaggedObject, false));
        continue;
        this.cRLIssuer = GeneralNames.getInstance(localASN1TaggedObject, false);
      }
    }
  }
  
  public DistributionPoint(DistributionPointName paramDistributionPointName, ReasonFlags paramReasonFlags, GeneralNames paramGeneralNames)
  {
    this.distributionPoint = paramDistributionPointName;
    this.reasons = paramReasonFlags;
    this.cRLIssuer = paramGeneralNames;
  }
  
  private void appendObject(StringBuffer paramStringBuffer, String paramString1, String paramString2, String paramString3)
  {
    paramStringBuffer.append("    ");
    paramStringBuffer.append(paramString2);
    paramStringBuffer.append(":");
    paramStringBuffer.append(paramString1);
    paramStringBuffer.append("    ");
    paramStringBuffer.append("    ");
    paramStringBuffer.append(paramString3);
    paramStringBuffer.append(paramString1);
  }
  
  public static DistributionPoint getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DistributionPoint))) {
      return (DistributionPoint)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new DistributionPoint((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid DistributionPoint: " + paramObject.getClass().getName());
  }
  
  public static DistributionPoint getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public GeneralNames getCRLIssuer()
  {
    return this.cRLIssuer;
  }
  
  public DistributionPointName getDistributionPoint()
  {
    return this.distributionPoint;
  }
  
  public ReasonFlags getReasons()
  {
    return this.reasons;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.distributionPoint != null) {
      localASN1EncodableVector.add(new DERTaggedObject(0, this.distributionPoint));
    }
    if (this.reasons != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 1, this.reasons));
    }
    if (this.cRLIssuer != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 2, this.cRLIssuer));
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("DistributionPoint: [");
    localStringBuffer.append(str);
    if (this.distributionPoint != null) {
      appendObject(localStringBuffer, str, "distributionPoint", this.distributionPoint.toString());
    }
    if (this.reasons != null) {
      appendObject(localStringBuffer, str, "reasons", this.reasons.toString());
    }
    if (this.cRLIssuer != null) {
      appendObject(localStringBuffer, str, "cRLIssuer", this.cRLIssuer.toString());
    }
    localStringBuffer.append("]");
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.DistributionPoint
 * JD-Core Version:    0.7.0.1
 */