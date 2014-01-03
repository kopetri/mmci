package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class GenRepContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private GenRepContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public GenRepContent(InfoTypeAndValue paramInfoTypeAndValue)
  {
    this.content = new DERSequence(paramInfoTypeAndValue);
  }
  
  public GenRepContent(InfoTypeAndValue[] paramArrayOfInfoTypeAndValue)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    for (int i = 0; i < paramArrayOfInfoTypeAndValue.length; i++) {
      localASN1EncodableVector.add(paramArrayOfInfoTypeAndValue[i]);
    }
    this.content = new DERSequence(localASN1EncodableVector);
  }
  
  public static GenRepContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof GenRepContent)) {
      return (GenRepContent)paramObject;
    }
    if (paramObject != null) {
      return new GenRepContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
  
  public InfoTypeAndValue[] toInfoTypeAndValueArray()
  {
    InfoTypeAndValue[] arrayOfInfoTypeAndValue = new InfoTypeAndValue[this.content.size()];
    for (int i = 0; i != arrayOfInfoTypeAndValue.length; i++) {
      arrayOfInfoTypeAndValue[i] = InfoTypeAndValue.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfInfoTypeAndValue;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.GenRepContent
 * JD-Core Version:    0.7.0.1
 */