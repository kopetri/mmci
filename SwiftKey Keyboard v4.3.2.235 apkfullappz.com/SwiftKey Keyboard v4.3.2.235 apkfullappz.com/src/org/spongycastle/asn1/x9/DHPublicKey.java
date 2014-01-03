package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;

public class DHPublicKey
  extends ASN1Object
{
  private ASN1Integer y;
  
  public DHPublicKey(ASN1Integer paramASN1Integer)
  {
    if (paramASN1Integer == null) {
      throw new IllegalArgumentException("'y' cannot be null");
    }
    this.y = paramASN1Integer;
  }
  
  public static DHPublicKey getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DHPublicKey))) {
      return (DHPublicKey)paramObject;
    }
    if ((paramObject instanceof ASN1Integer)) {
      return new DHPublicKey((ASN1Integer)paramObject);
    }
    throw new IllegalArgumentException("Invalid DHPublicKey: " + paramObject.getClass().getName());
  }
  
  public static DHPublicKey getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Integer.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Integer getY()
  {
    return this.y;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.y;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.DHPublicKey
 * JD-Core Version:    0.7.0.1
 */