package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class DSAParameter
  extends ASN1Object
{
  ASN1Integer g;
  ASN1Integer p;
  ASN1Integer q;
  
  public DSAParameter(BigInteger paramBigInteger1, BigInteger paramBigInteger2, BigInteger paramBigInteger3)
  {
    this.p = new ASN1Integer(paramBigInteger1);
    this.q = new ASN1Integer(paramBigInteger2);
    this.g = new ASN1Integer(paramBigInteger3);
  }
  
  public DSAParameter(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.p = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.q = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.g = ASN1Integer.getInstance(localEnumeration.nextElement());
  }
  
  public static DSAParameter getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DSAParameter))) {
      return (DSAParameter)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new DSAParameter((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid DSAParameter: " + paramObject.getClass().getName());
  }
  
  public static DSAParameter getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BigInteger getG()
  {
    return this.g.getPositiveValue();
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
    localASN1EncodableVector.add(this.p);
    localASN1EncodableVector.add(this.q);
    localASN1EncodableVector.add(this.g);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.DSAParameter
 * JD-Core Version:    0.7.0.1
 */