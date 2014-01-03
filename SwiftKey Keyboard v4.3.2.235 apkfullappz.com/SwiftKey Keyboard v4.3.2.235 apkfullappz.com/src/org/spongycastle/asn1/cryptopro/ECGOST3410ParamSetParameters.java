package org.spongycastle.asn1.cryptopro;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class ECGOST3410ParamSetParameters
  extends ASN1Object
{
  ASN1Integer a;
  ASN1Integer b;
  ASN1Integer p;
  ASN1Integer q;
  ASN1Integer x;
  ASN1Integer y;
  
  public ECGOST3410ParamSetParameters(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3, BigInteger paramBigInteger4, int paramInt, BigInteger paramBigInteger5)
  {
    this.a = new ASN1Integer(paramBigInteger1);
    this.b = new ASN1Integer(paramBigInteger2);
    this.p = new ASN1Integer(paramBigInteger3);
    this.q = new ASN1Integer(paramBigInteger4);
    this.x = new ASN1Integer(paramInt);
    this.y = new ASN1Integer(paramBigInteger5);
  }
  
  public ECGOST3410ParamSetParameters(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.a = ((ASN1Integer)localEnumeration.nextElement());
    this.b = ((ASN1Integer)localEnumeration.nextElement());
    this.p = ((ASN1Integer)localEnumeration.nextElement());
    this.q = ((ASN1Integer)localEnumeration.nextElement());
    this.x = ((ASN1Integer)localEnumeration.nextElement());
    this.y = ((ASN1Integer)localEnumeration.nextElement());
  }
  
  public static ECGOST3410ParamSetParameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ECGOST3410ParamSetParameters))) {
      return (ECGOST3410ParamSetParameters)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new ECGOST3410ParamSetParameters((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid GOST3410Parameter: " + paramObject.getClass().getName());
  }
  
  public static ECGOST3410ParamSetParameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BigInteger getA()
  {
    return this.a.getPositiveValue();
  }
  
  public BigInteger getP()
  {
    return this.p.getPositiveValue();
  }
  
  public BigInteger getQ()
  {
    return this.q.getPositiveValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.a);
    localASN1EncodableVector.add(this.b);
    localASN1EncodableVector.add(this.p);
    localASN1EncodableVector.add(this.q);
    localASN1EncodableVector.add(this.x);
    localASN1EncodableVector.add(this.y);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cryptopro.ECGOST3410ParamSetParameters
 * JD-Core Version:    0.7.0.1
 */