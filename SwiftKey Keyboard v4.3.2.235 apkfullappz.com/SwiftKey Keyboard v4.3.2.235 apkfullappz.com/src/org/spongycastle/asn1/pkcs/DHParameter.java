package org.spongycastle.asn1.pkcs;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class DHParameter
  extends ASN1Object
{
  ASN1Integer g;
  ASN1Integer l;
  ASN1Integer p;
  
  public DHParameter(BigInteger paramBigInteger1, BigInteger paramBigInteger2, int paramInt)
  {
    this.p = new ASN1Integer(paramBigInteger1);
    this.g = new ASN1Integer(paramBigInteger2);
    if (paramInt != 0)
    {
      this.l = new ASN1Integer(paramInt);
      return;
    }
    this.l = null;
  }
  
  private DHParameter(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.p = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.g = ASN1Integer.getInstance(localEnumeration.nextElement());
    if (localEnumeration.hasMoreElements())
    {
      this.l = ((ASN1Integer)localEnumeration.nextElement());
      return;
    }
    this.l = null;
  }
  
  public static DHParameter getInstance(Object paramObject)
  {
    if ((paramObject instanceof DHParameter)) {
      return (DHParameter)paramObject;
    }
    if (paramObject != null) {
      return new DHParameter(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getG()
  {
    return this.g.getPositiveValue();
  }
  
  public BigInteger getL()
  {
    if (this.l == null) {
      return null;
    }
    return this.l.getPositiveValue();
  }
  
  public BigInteger getP()
  {
    return this.p.getPositiveValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.p);
    localASN1EncodableVector.add(this.g);
    if (getL() != null) {
      localASN1EncodableVector.add(this.l);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.DHParameter
 * JD-Core Version:    0.7.0.1
 */