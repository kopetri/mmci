package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class RevReqContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private RevReqContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public RevReqContent(RevDetails paramRevDetails)
  {
    this.content = new DERSequence(paramRevDetails);
  }
  
  public RevReqContent(RevDetails[] paramArrayOfRevDetails)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i != paramArrayOfRevDetails.length; i++) {
      localASN1EncodableVector.add(paramArrayOfRevDetails[i]);
    }
    this.content = new DERSequence(localASN1EncodableVector);
  }
  
  public static RevReqContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof RevReqContent)) {
      return (RevReqContent)paramObject;
    }
    if (paramObject != null) {
      return new RevReqContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public RevDetails[] toRevDetailsArray()
  {
    RevDetails[] arrayOfRevDetails = new RevDetails[this.content.size()];
    for (int i = 0; i != arrayOfRevDetails.length; i++) {
      arrayOfRevDetails[i] = RevDetails.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfRevDetails;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.RevReqContent
 * JD-Core Version:    0.7.0.1
 */