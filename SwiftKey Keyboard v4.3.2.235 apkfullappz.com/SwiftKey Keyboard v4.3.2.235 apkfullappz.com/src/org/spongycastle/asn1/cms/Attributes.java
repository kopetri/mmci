package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.BERSet;

public class Attributes
  extends ASN1Object
{
  private ASN1Set attributes;
  
  public Attributes(ASN1EncodableVector paramASN1EncodableVector)
  {
    this.attributes = new BERSet(paramASN1EncodableVector);
  }
  
  private Attributes(ASN1Set paramASN1Set)
  {
    this.attributes = paramASN1Set;
  }
  
  public static Attributes getInstance(Object paramObject)
  {
    if ((paramObject instanceof Attributes)) {
      return (Attributes)paramObject;
    }
    if (paramObject != null) {
      return new Attributes(ASN1Set.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.attributes;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.Attributes
 * JD-Core Version:    0.7.0.1
 */