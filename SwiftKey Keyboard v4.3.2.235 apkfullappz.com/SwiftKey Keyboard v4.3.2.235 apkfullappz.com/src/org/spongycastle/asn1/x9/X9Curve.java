package org.spongycastle.asn1.x9;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.F2m;
import org.spongycastle.math.ec.ECCurve.Fp;
import org.spongycastle.math.ec.ECFieldElement;

public class X9Curve
  extends ASN1Object
  implements X9ObjectIdentifiers
{
  private ECCurve curve;
  private ASN1ObjectIdentifier fieldIdentifier = null;
  private byte[] seed;
  
  public X9Curve(X9FieldID paramX9FieldID, ASN1Sequence paramASN1Sequence)
  {
    this.fieldIdentifier = paramX9FieldID.getIdentifier();
    if (this.fieldIdentifier.equals(prime_field))
    {
      localBigInteger3 = ((ASN1Integer)paramX9FieldID.getParameters()).getValue();
      localX9FieldElement3 = new X9FieldElement(localBigInteger3, (ASN1OctetString)paramASN1Sequence.getObjectAt(0));
      localX9FieldElement4 = new X9FieldElement(localBigInteger3, (ASN1OctetString)paramASN1Sequence.getObjectAt(1));
      this.curve = new ECCurve.Fp(localBigInteger3, localX9FieldElement3.getValue().toBigInteger(), localX9FieldElement4.getValue().toBigInteger());
    }
    while (!this.fieldIdentifier.equals(characteristic_two_field))
    {
      BigInteger localBigInteger3;
      X9FieldElement localX9FieldElement3;
      X9FieldElement localX9FieldElement4;
      if (paramASN1Sequence.size() == 3) {
        this.seed = ((DERBitString)paramASN1Sequence.getObjectAt(2)).getBytes();
      }
      return;
    }
    ASN1Sequence localASN1Sequence = ASN1Sequence.getInstance(paramX9FieldID.getParameters());
    int i = ((ASN1Integer)localASN1Sequence.getObjectAt(0)).getValue().intValue();
    ASN1ObjectIdentifier localASN1ObjectIdentifier = (ASN1ObjectIdentifier)localASN1Sequence.getObjectAt(1);
    int j = 0;
    int k = 0;
    int m;
    if (localASN1ObjectIdentifier.equals(tpBasis)) {
      m = ((ASN1Integer)localASN1Sequence.getObjectAt(2)).getValue().intValue();
    }
    for (;;)
    {
      X9FieldElement localX9FieldElement1 = new X9FieldElement(i, m, j, k, (ASN1OctetString)paramASN1Sequence.getObjectAt(0));
      ASN1OctetString localASN1OctetString = (ASN1OctetString)paramASN1Sequence.getObjectAt(1);
      X9FieldElement localX9FieldElement2 = new X9FieldElement(i, m, j, k, localASN1OctetString);
      BigInteger localBigInteger1 = localX9FieldElement1.getValue().toBigInteger();
      BigInteger localBigInteger2 = localX9FieldElement2.getValue().toBigInteger();
      this.curve = new ECCurve.F2m(i, m, j, k, localBigInteger1, localBigInteger2);
      break;
      DERSequence localDERSequence = (DERSequence)localASN1Sequence.getObjectAt(2);
      m = ((ASN1Integer)localDERSequence.getObjectAt(0)).getValue().intValue();
      j = ((ASN1Integer)localDERSequence.getObjectAt(1)).getValue().intValue();
      k = ((ASN1Integer)localDERSequence.getObjectAt(2)).getValue().intValue();
    }
  }
  
  public X9Curve(ECCurve paramECCurve)
  {
    this.curve = paramECCurve;
    this.seed = null;
    setFieldIdentifier();
  }
  
  public X9Curve(ECCurve paramECCurve, byte[] paramArrayOfByte)
  {
    this.curve = paramECCurve;
    this.seed = paramArrayOfByte;
    setFieldIdentifier();
  }
  
  private void setFieldIdentifier()
  {
    if ((this.curve instanceof ECCurve.Fp))
    {
      this.fieldIdentifier = prime_field;
      return;
    }
    if ((this.curve instanceof ECCurve.F2m))
    {
      this.fieldIdentifier = characteristic_two_field;
      return;
    }
    throw new IllegalArgumentException("This type of ECCurve is not implemented");
  }
  
  public ECCurve getCurve()
  {
    return this.curve;
  }
  
  public byte[] getSeed()
  {
    return this.seed;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.fieldIdentifier.equals(prime_field))
    {
      localASN1EncodableVector.add(new X9FieldElement(this.curve.getA()).toASN1Primitive());
      localASN1EncodableVector.add(new X9FieldElement(this.curve.getB()).toASN1Primitive());
    }
    for (;;)
    {
      if (this.seed != null) {
        localASN1EncodableVector.add(new DERBitString(this.seed));
      }
      return new DERSequence(localASN1EncodableVector);
      if (this.fieldIdentifier.equals(characteristic_two_field))
      {
        localASN1EncodableVector.add(new X9FieldElement(this.curve.getA()).toASN1Primitive());
        localASN1EncodableVector.add(new X9FieldElement(this.curve.getB()).toASN1Primitive());
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.X9Curve
 * JD-Core Version:    0.7.0.1
 */