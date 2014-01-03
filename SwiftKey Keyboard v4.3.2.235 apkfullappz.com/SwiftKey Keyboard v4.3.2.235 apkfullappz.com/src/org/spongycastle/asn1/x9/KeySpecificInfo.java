package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class KeySpecificInfo
  extends ASN1Object
{
  private ASN1ObjectIdentifier algorithm;
  private ASN1OctetString counter;
  
  public KeySpecificInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1OctetString paramASN1OctetString)
  {
    this.algorithm = paramASN1ObjectIdentifier;
    this.counter = paramASN1OctetString;
  }
  
  public KeySpecificInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.algorithm = ((ASN1ObjectIdentifier)localEnumeration.nextElement());
    this.counter = ((ASN1OctetString)localEnumeration.nextElement());
  }
  
  public ASN1ObjectIdentifier getAlgorithm()
  {
    return this.algorithm;
  }
  
  public ASN1OctetString getCounter()
  {
    return this.counter;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.algorithm);
    localASN1EncodableVector.add(this.counter);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.KeySpecificInfo
 * JD-Core Version:    0.7.0.1
 */