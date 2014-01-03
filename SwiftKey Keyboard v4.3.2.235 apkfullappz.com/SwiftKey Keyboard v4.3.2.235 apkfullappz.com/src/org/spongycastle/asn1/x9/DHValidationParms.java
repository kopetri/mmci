package org.spongycastle.asn1.x9;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class DHValidationParms
  extends ASN1Object
{
  private ASN1Integer pgenCounter;
  private DERBitString seed;
  
  private DHValidationParms(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.seed = DERBitString.getInstance(paramASN1Sequence.getObjectAt(0));
    this.pgenCounter = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public DHValidationParms(DERBitString paramDERBitString, ASN1Integer paramASN1Integer)
  {
    if (paramDERBitString == null) {
      throw new IllegalArgumentException("'seed' cannot be null");
    }
    if (paramASN1Integer == null) {
      throw new IllegalArgumentException("'pgenCounter' cannot be null");
    }
    this.seed = paramDERBitString;
    this.pgenCounter = paramASN1Integer;
  }
  
  public static DHValidationParms getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DHDomainParameters))) {
      return (DHValidationParms)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new DHValidationParms((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid DHValidationParms: " + paramObject.getClass().getName());
  }
  
  public static DHValidationParms getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Integer getPgenCounter()
  {
    return this.pgenCounter;
  }
  
  public DERBitString getSeed()
  {
    return this.seed;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.seed);
    localASN1EncodableVector.add(this.pgenCounter);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.DHValidationParms
 * JD-Core Version:    0.7.0.1
 */