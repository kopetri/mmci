package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;

public class POPODecKeyRespContent
  extends ASN1Object
{
  private ASN1Sequence content;
  
  private POPODecKeyRespContent(ASN1Sequence paramASN1Sequence)
  {
    this.content = paramASN1Sequence;
  }
  
  public static POPODecKeyRespContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof POPODecKeyRespContent)) {
      return (POPODecKeyRespContent)paramObject;
    }
    if (paramObject != null) {
      return new POPODecKeyRespContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Integer[] toASN1IntegerArray()
  {
    ASN1Integer[] arrayOfASN1Integer = new ASN1Integer[this.content.size()];
    for (int i = 0; i != arrayOfASN1Integer.length; i++) {
      arrayOfASN1Integer[i] = ASN1Integer.getInstance(this.content.getObjectAt(i));
    }
    return arrayOfASN1Integer;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.content;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.POPODecKeyRespContent
 * JD-Core Version:    0.7.0.1
 */