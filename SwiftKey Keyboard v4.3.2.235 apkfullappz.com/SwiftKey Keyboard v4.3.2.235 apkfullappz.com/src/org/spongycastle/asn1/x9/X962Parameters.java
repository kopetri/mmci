package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Null;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;

public class X962Parameters
  extends ASN1Object
  implements ASN1Choice
{
  private ASN1Primitive params = null;
  
  public X962Parameters(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.params = paramASN1ObjectIdentifier;
  }
  
  public X962Parameters(ASN1Primitive paramASN1Primitive)
  {
    this.params = paramASN1Primitive;
  }
  
  public X962Parameters(X9ECParameters paramX9ECParameters)
  {
    this.params = paramX9ECParameters.toASN1Primitive();
  }
  
  public static X962Parameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof X962Parameters))) {
      return (X962Parameters)paramObject;
    }
    if ((paramObject instanceof ASN1Primitive)) {
      return new X962Parameters((ASN1Primitive)paramObject);
    }
    throw new IllegalArgumentException("unknown object in getInstance()");
  }
  
  public static X962Parameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public ASN1Primitive getParameters()
  {
    return this.params;
  }
  
  public boolean isImplicitlyCA()
  {
    return this.params instanceof ASN1Null;
  }
  
  public boolean isNamedCurve()
  {
    return this.params instanceof ASN1ObjectIdentifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.params;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X962Parameters
 * JD-Core Version:    0.7.0.1
 */