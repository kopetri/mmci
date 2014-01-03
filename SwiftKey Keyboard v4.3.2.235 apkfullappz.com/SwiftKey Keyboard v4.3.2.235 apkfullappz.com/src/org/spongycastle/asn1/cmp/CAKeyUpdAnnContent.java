package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CAKeyUpdAnnContent
  extends ASN1Object
{
  private CMPCertificate newWithNew;
  private CMPCertificate newWithOld;
  private CMPCertificate oldWithNew;
  
  private CAKeyUpdAnnContent(ASN1Sequence paramASN1Sequence)
  {
    this.oldWithNew = CMPCertificate.getInstance(paramASN1Sequence.getObjectAt(0));
    this.newWithOld = CMPCertificate.getInstance(paramASN1Sequence.getObjectAt(1));
    this.newWithNew = CMPCertificate.getInstance(paramASN1Sequence.getObjectAt(2));
  }
  
  public CAKeyUpdAnnContent(CMPCertificate paramCMPCertificate1, CMPCertificate paramCMPCertificate2, CMPCertificate paramCMPCertificate3)
  {
    this.oldWithNew = paramCMPCertificate1;
    this.newWithOld = paramCMPCertificate2;
    this.newWithNew = paramCMPCertificate3;
  }
  
  public static CAKeyUpdAnnContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof CAKeyUpdAnnContent)) {
      return (CAKeyUpdAnnContent)paramObject;
    }
    if (paramObject != null) {
      return new CAKeyUpdAnnContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CMPCertificate getNewWithNew()
  {
    return this.newWithNew;
  }
  
  public CMPCertificate getNewWithOld()
  {
    return this.newWithOld;
  }
  
  public CMPCertificate getOldWithNew()
  {
    return this.oldWithNew;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.oldWithNew);
    localASN1EncodableVector.add(this.newWithOld);
    localASN1EncodableVector.add(this.newWithNew);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CAKeyUpdAnnContent
 * JD-Core Version:    0.7.0.1
 */