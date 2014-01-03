package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class PollReqContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private PollReqContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public static PollReqContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof PollReqContent)) {
      return (PollReqContent)paramObject;
    }
    if (paramObject != null) {
      return new PollReqContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  private static ASN1Integer[] sequenceToASN1IntegerArray(ASN1Sequence paramASN1Sequence)
  {
    ASN1Integer[] arrayOfASN1Integer = new ASN1Integer[paramASN1Sequence.size()];
    for (int i = 0; i != arrayOfASN1Integer.length; i++) {
      arrayOfASN1Integer[i] = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(i));
    }
    return arrayOfASN1Integer;
  }
  
  public ASN1Integer[][] getCertReqIds()
  {
    ASN1Integer[][] arrayOfASN1Integer; = new ASN1Integer[this.content.size()][];
    for (int i = 0; i != arrayOfASN1Integer;.length; i++) {
      arrayOfASN1Integer;[i] = sequenceToASN1IntegerArray((ASN1Sequence)this.content.getObjectAt(i));
    }
    return arrayOfASN1Integer;;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PollReqContent
 * JD-Core Version:    0.7.0.1
 */