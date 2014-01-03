package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PKIMessages
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private PKIMessages(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public PKIMessages(PKIMessage paramPKIMessage)
  {
    this.content = new DERSequence(paramPKIMessage);
  }
  
  public PKIMessages(PKIMessage[] paramArrayOfPKIMessage)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfPKIMessage.length; i++) {
      localASN1EncodableVector.add(paramArrayOfPKIMessage[i]);
    }
    this.content = new DERSequence(localASN1EncodableVector);
  }
  
  public static PKIMessages getInstance(Object paramObject)
  {
    if ((paramObject instanceof PKIMessages)) {
      return (PKIMessages)paramObject;
    }
    if (paramObject != null) {
      return new PKIMessages(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public PKIMessage[] toPKIMessageArray()
  {
    PKIMessage[] arrayOfPKIMessage = new PKIMessage[this.content.size()];
    for (int i = 0; i != arrayOfPKIMessage.length; i++) {
      arrayOfPKIMessage[i] = PKIMessage.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfPKIMessage;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PKIMessages
 * JD-Core Version:    0.7.0.1
 */