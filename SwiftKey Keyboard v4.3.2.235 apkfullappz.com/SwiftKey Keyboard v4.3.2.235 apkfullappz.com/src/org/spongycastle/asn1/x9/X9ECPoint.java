package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;

public class X9ECPoint
  extends ASN1Object
{
  ECPoint p;
  
  public X9ECPoint(ECCurve paramECCurve, ASN1OctetString paramASN1OctetString)
  {
    this.p = paramECCurve.decodePoint(paramASN1OctetString.getOctets());
  }
  
  public X9ECPoint(ECPoint paramECPoint)
  {
    this.p = paramECPoint;
  }
  
  public ECPoint getPoint()
  {
    return this.p;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DEROctetString(this.p.getEncoded());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9ECPoint
 * JD-Core Version:    0.7.0.1
 */