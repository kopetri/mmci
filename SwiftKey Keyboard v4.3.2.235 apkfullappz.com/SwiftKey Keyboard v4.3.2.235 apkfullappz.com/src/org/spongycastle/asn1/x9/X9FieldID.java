package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class X9FieldID
  extends ASN1Object
  implements X9ObjectIdentifiers
{
  private ASN1ObjectIdentifier id;
  private ASN1Primitive parameters;
  
  public X9FieldID(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.id = characteristic_two_field;
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    localASN1EncodableVector1.add(new ASN1Integer(paramInt1));
    if (paramInt3 == 0)
    {
      localASN1EncodableVector1.add(tpBasis);
      localASN1EncodableVector1.add(new ASN1Integer(paramInt2));
    }
    for (;;)
    {
      this.parameters = new DERSequence(localASN1EncodableVector1);
      return;
      localASN1EncodableVector1.add(ppBasis);
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      localASN1EncodableVector2.add(new ASN1Integer(paramInt2));
      localASN1EncodableVector2.add(new ASN1Integer(paramInt3));
      localASN1EncodableVector2.add(new ASN1Integer(paramInt4));
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    }
  }
  
  public X9FieldID(BigInteger paramBigInteger)
  {
    this.id = prime_field;
    this.parameters = new ASN1Integer(paramBigInteger);
  }
  
  public X9FieldID(ASN1Sequence paramASN1Sequence)
  {
    this.id = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.parameters = ((ASN1Primitive)paramASN1Sequence.getObjectAt(1));
  }
  
  public ASN1ObjectIdentifier getIdentifier()
  {
    return this.id;
  }
  
  public ASN1Primitive getParameters()
  {
    return this.parameters;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.id);
    localASN1EncodableVector.add(this.parameters);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9FieldID
 * JD-Core Version:    0.7.0.1
 */