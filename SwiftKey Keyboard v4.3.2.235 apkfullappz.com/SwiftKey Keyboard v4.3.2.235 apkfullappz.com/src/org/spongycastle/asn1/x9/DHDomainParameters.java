package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class DHDomainParameters
  extends ASN1Object
{
  private ASN1Integer g;
  private ASN1Integer j;
  private ASN1Integer p;
  private ASN1Integer q;
  private DHValidationParms validationParms;
  
  public DHDomainParameters(ASN1Integer paramASN1Integer1, ASN1Integer paramASN1Integer2, ASN1Integer paramASN1Integer3, ASN1Integer paramASN1Integer4, DHValidationParms paramDHValidationParms)
  {
    if (paramASN1Integer1 == null) {
      throw new IllegalArgumentException("'p' cannot be null");
    }
    if (paramASN1Integer2 == null) {
      throw new IllegalArgumentException("'g' cannot be null");
    }
    if (paramASN1Integer3 == null) {
      throw new IllegalArgumentException("'q' cannot be null");
    }
    this.p = paramASN1Integer1;
    this.g = paramASN1Integer2;
    this.q = paramASN1Integer3;
    this.j = paramASN1Integer4;
    this.validationParms = paramDHValidationParms;
  }
  
  private DHDomainParameters(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() < 3) || (paramASN1Sequence.size() > 5)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.p = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.g = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.q = ASN1Integer.getInstance(localEnumeration.nextElement());
    ASN1Encodable localASN1Encodable = getNext(localEnumeration);
    if ((localASN1Encodable != null) && ((localASN1Encodable instanceof ASN1Integer)))
    {
      this.j = ASN1Integer.getInstance(localASN1Encodable);
      localASN1Encodable = getNext(localEnumeration);
    }
    if (localASN1Encodable != null) {
      this.validationParms = DHValidationParms.getInstance(localASN1Encodable.toASN1Primitive());
    }
  }
  
  public static DHDomainParameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DHDomainParameters))) {
      return (DHDomainParameters)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new DHDomainParameters((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid DHDomainParameters: " + paramObject.getClass().getName());
  }
  
  public static DHDomainParameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  private static ASN1Encodable getNext(Enumeration paramEnumeration)
  {
    if (paramEnumeration.hasMoreElements()) {
      return (ASN1Encodable)paramEnumeration.nextElement();
    }
    return null;
  }
  
  public ASN1Integer getG()
  {
    return this.g;
  }
  
  public ASN1Integer getJ()
  {
    return this.j;
  }
  
  public ASN1Integer getP()
  {
    return this.p;
  }
  
  public ASN1Integer getQ()
  {
    return this.q;
  }
  
  public DHValidationParms getValidationParms()
  {
    return this.validationParms;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.p);
    localASN1EncodableVector.add(this.g);
    localASN1EncodableVector.add(this.q);
    if (this.j != null) {
      localASN1EncodableVector.add(this.j);
    }
    if (this.validationParms != null) {
      localASN1EncodableVector.add(this.validationParms);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.DHDomainParameters
 * JD-Core Version:    0.7.0.1
 */