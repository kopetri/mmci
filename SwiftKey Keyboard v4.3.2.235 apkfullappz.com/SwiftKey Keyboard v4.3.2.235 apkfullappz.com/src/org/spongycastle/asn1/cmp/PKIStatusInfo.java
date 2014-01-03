package org.spongycastle.asn1.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class PKIStatusInfo
  extends ASN1Object
{
  DERBitString failInfo;
  ASN1Integer status;
  PKIFreeText statusString;
  
  private PKIStatusInfo(ASN1Sequence paramASN1Sequence)
  {
    this.status = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.statusString = null;
    this.failInfo = null;
    if (paramASN1Sequence.size() > 2)
    {
      this.statusString = PKIFreeText.getInstance(paramASN1Sequence.getObjectAt(1));
      this.failInfo = DERBitString.getInstance(paramASN1Sequence.getObjectAt(2));
    }
    while (paramASN1Sequence.size() <= 1) {
      return;
    }
    ASN1Encodable localASN1Encodable = paramASN1Sequence.getObjectAt(1);
    if ((localASN1Encodable instanceof DERBitString))
    {
      this.failInfo = DERBitString.getInstance(localASN1Encodable);
      return;
    }
    this.statusString = PKIFreeText.getInstance(localASN1Encodable);
  }
  
  public PKIStatusInfo(PKIStatus paramPKIStatus)
  {
    this.status = ASN1Integer.getInstance(paramPKIStatus.toASN1Primitive());
  }
  
  public PKIStatusInfo(PKIStatus paramPKIStatus, PKIFreeText paramPKIFreeText)
  {
    this.status = ASN1Integer.getInstance(paramPKIStatus.toASN1Primitive());
    this.statusString = paramPKIFreeText;
  }
  
  public PKIStatusInfo(PKIStatus paramPKIStatus, PKIFreeText paramPKIFreeText, PKIFailureInfo paramPKIFailureInfo)
  {
    this.status = ASN1Integer.getInstance(paramPKIStatus.toASN1Primitive());
    this.statusString = paramPKIFreeText;
    this.failInfo = paramPKIFailureInfo;
  }
  
  public static PKIStatusInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKIStatusInfo)) {
      return (PKIStatusInfo)paramObject;
    }
    if (paramObject != null) {
      return new PKIStatusInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static PKIStatusInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public DERBitString getFailInfo()
  {
    return this.failInfo;
  }
  
  public BigInteger getStatus()
  {
    return this.status.getValue();
  }
  
  public PKIFreeText getStatusString()
  {
    return this.statusString;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.status);
    if (this.statusString != null) {
      localASN1EncodableVector.add(this.statusString);
    }
    if (this.failInfo != null) {
      localASN1EncodableVector.add(this.failInfo);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PKIStatusInfo
 * JD-Core Version:    0.7.0.1
 */