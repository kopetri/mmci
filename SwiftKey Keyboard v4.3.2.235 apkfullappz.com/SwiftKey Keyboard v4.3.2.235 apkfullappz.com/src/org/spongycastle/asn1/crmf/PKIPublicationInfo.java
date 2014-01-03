package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PKIPublicationInfo
  extends ASN1Object
{
  private ASN1Integer action;
  private ASN1Sequence pubInfos;
  
  private PKIPublicationInfo(ASN1Sequence paramASN1Sequence)
  {
    this.action = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    this.pubInfos = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static PKIPublicationInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKIPublicationInfo)) {
      return (PKIPublicationInfo)paramObject;
    }
    if (paramObject != null) {
      return new PKIPublicationInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer getAction()
  {
    return this.action;
  }
  
  public SinglePubInfo[] getPubInfos()
  {
    SinglePubInfo[] arrayOfSinglePubInfo;
    if (this.pubInfos == null) {
      arrayOfSinglePubInfo = null;
    }
    for (;;)
    {
      return arrayOfSinglePubInfo;
      arrayOfSinglePubInfo = new SinglePubInfo[this.pubInfos.size()];
      for (int i = 0; i != arrayOfSinglePubInfo.length; i++) {
        arrayOfSinglePubInfo[i] = SinglePubInfo.getInstance(this.pubInfos.getObjectAt(i));
      }
    }
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.action);
    localASN1EncodableVector.add(this.pubInfos);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.PKIPublicationInfo
 * JD-Core Version:    0.7.0.1
 */