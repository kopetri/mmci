package org.spongycastle.asn1.cryptopro;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;

public class GOST28147Parameters
  extends ASN1Object
{
  ASN1OctetString iv;
  ASN1ObjectIdentifier paramSet;
  
  public GOST28147Parameters(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.iv = ((ASN1OctetString)localEnumeration.nextElement());
    this.paramSet = ((ASN1ObjectIdentifier)localEnumeration.nextElement());
  }
  
  public static GOST28147Parameters getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof GOST28147Parameters))) {
      return (GOST28147Parameters)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new GOST28147Parameters((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid GOST3410Parameter: " + paramObject.getClass().getName());
  }
  
  public static GOST28147Parameters getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.iv);
    localASN1EncodableVector.add(this.paramSet);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cryptopro.GOST28147Parameters
 * JD-Core Version:    0.7.0.1
 */