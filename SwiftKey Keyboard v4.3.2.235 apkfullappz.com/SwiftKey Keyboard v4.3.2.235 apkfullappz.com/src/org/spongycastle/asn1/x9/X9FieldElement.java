package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECFieldElement.F2m;
import org.spongycastle.math.ec.ECFieldElement.Fp;

public class X9FieldElement
  extends ASN1Object
{
  private static X9IntegerConverter converter = new X9IntegerConverter();
  protected ECFieldElement f;
  
  public X9FieldElement(int paramInt1, int paramInt2, int paramInt3, int paramInt4, ASN1OctetString paramASN1OctetString)
  {
    this(new ECFieldElement.F2m(paramInt1, paramInt2, paramInt3, paramInt4, new BigInteger(1, paramASN1OctetString.getOctets())));
  }
  
  public X9FieldElement(BigInteger paramBigInteger, ASN1OctetString paramASN1OctetString)
  {
    this(new ECFieldElement.Fp(paramBigInteger, new BigInteger(1, paramASN1OctetString.getOctets())));
  }
  
  public X9FieldElement(ECFieldElement paramECFieldElement)
  {
    this.f = paramECFieldElement;
  }
  
  public ECFieldElement getValue()
  {
    return this.f;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    int i = converter.getByteLength(this.f);
    return new DEROctetString(converter.integerToBytes(this.f.toBigInteger(), i));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9FieldElement
 * JD-Core Version:    0.7.0.1
 */