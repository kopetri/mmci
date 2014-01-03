package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERNull;

public class PKIConfirmContent
  extends ASN1Object
{
  private ASN1Null val;
  
  public PKIConfirmContent()
  {
    this.val = DERNull.INSTANCE;
  }
  
  private PKIConfirmContent(ASN1Null paramASN1Null)
  {
    this.val = paramASN1Null;
  }
  
  public static PKIConfirmContent getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof PKIConfirmContent))) {
      return (PKIConfirmContent)paramObject;
    }
    if ((paramObject instanceof ASN1Null)) {
      return new PKIConfirmContent((ASN1Null)paramObject);
    }
    throw new IllegalArgumentException("Invalid object: " + paramObject.getClass().getName());
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.val;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.PKIConfirmContent
 * JD-Core Version:    0.7.0.1
 */