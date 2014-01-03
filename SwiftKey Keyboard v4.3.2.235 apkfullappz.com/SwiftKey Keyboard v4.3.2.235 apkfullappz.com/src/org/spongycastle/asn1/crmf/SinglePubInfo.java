package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class SinglePubInfo
  extends ASN1Object
{
  private GeneralName pubLocation;
  private ASN1Integer pubMethod;
  
  private SinglePubInfo(ASN1Sequence paramASN1Sequence)
  {
    this.pubMethod = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() == 2) {
      this.pubLocation = GeneralName.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public static SinglePubInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof SinglePubInfo)) {
      return (SinglePubInfo)paramObject;
    }
    if (paramObject != null) {
      return new SinglePubInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public GeneralName getPubLocation()
  {
    return this.pubLocation;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.pubMethod);
    if (this.pubLocation != null) {
      localASN1EncodableVector.add(this.pubLocation);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.SinglePubInfo
 * JD-Core Version:    0.7.0.1
 */