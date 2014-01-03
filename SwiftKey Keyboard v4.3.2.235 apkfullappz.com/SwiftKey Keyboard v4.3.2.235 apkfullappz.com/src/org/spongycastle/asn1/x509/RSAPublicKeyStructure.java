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

public class RSAPublicKeyStructure
  extends ASN1Object
{
  private BigInteger modulus;
  private BigInteger publicExponent;
  
  public RSAPublicKeyStructure(BigInteger paramBigInteger1, BigInteger paramBigInteger2)
  {
    this.modulus = paramBigInteger1;
    this.publicExponent = paramBigInteger2;
  }
  
  public RSAPublicKeyStructure(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.modulus = ASN1Integer.getInstance(localEnumeration.nextElement()).getPositiveValue();
    this.publicExponent = ASN1Integer.getInstance(localEnumeration.nextElement()).getPositiveValue();
  }
  
  public static RSAPublicKeyStructure getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof RSAPublicKeyStructure))) {
      return (RSAPublicKeyStructure)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new RSAPublicKeyStructure((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid RSAPublicKeyStructure: " + paramObject.getClass().getName());
  }
  
  public static RSAPublicKeyStructure getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public BigInteger getModulus()
  {
    return this.modulus;
  }
  
  public BigInteger getPublicExponent()
  {
    return this.publicExponent;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(new ASN1Integer(getModulus()));
    localASN1EncodableVector.add(new ASN1Integer(getPublicExponent()));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.RSAPublicKeyStructure
 * JD-Core Version:    0.7.0.1
 */