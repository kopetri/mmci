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

public class GOST3410ParamSetParameters
  extends ASN1Object
{
  ASN1Integer a;
  int keySize;
  ASN1Integer p;
  ASN1Integer q;
  
  public GOST3410ParamSetParameters(int paramInt, BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3)
  {
    this.keySize = paramInt;
    this.p = new ASN1Integer(paramBigInteger1);
    this.q = new ASN1Integer(paramBigInteger2);
    this.a = new ASN1Integer(paramBigInteger3);
  }
  
  public GOST3410ParamSetParameters(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.keySize = ((ASN1Integer)localEnumeration.nextElement()).getValue().intValue();
    this.p = ((ASN1Integer)localEnumeration.nextElement());
    this.q = ((ASN1Integer)localEnumeration.nextElement());
    this.a = ((ASN1Integer)localEnumeration.nextElement());
  }
  
  public static GOST3410ParamSetParameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof GOST3410ParamSetParameters))) {
      return (GOST3410ParamSetParameters)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new GOST3410ParamSetParameters((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid GOST3410Parameter: " + paramObject.getClass().getName());
  }
  
  public static GOST3410ParamSetParameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BigInteger getA()
  {
    return this.a.getPositiveValue();
  }
  
  public int getKeySize()
  {
    return this.keySize;
  }
  
  public int getLKeySize()
  {
    return this.keySize;
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
    localASN1EncodableVector.add(new ASN1Integer(this.keySize));
    localASN1EncodableVector.add(this.p);
    localASN1EncodableVector.add(this.q);
    localASN1EncodableVector.add(this.a);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cryptopro.GOST3410ParamSetParameters
 * JD-Core Version:    0.7.0.1
 */