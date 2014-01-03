package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import java.util.Hashtable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class PolicyMappings
  extends ASN1Object
{
  ASN1Sequence seq = null;
  
  public PolicyMappings(Hashtable paramHashtable)
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    Enumeration localEnumeration = paramHashtable.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = (String)paramHashtable.get(str1);
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      localASN1EncodableVector2.add(new ASN1ObjectIdentifier(str1));
      localASN1EncodableVector2.add(new ASN1ObjectIdentifier(str2));
      localASN1EncodableVector1.add(new DERSequence(localASN1EncodableVector2));
    }
    this.seq = new DERSequence(localASN1EncodableVector1);
  }
  
  private PolicyMappings(ASN1Sequence paramASN1Sequence)
  {
    this.seq = paramASN1Sequence;
  }
  
  public static PolicyMappings getInstance(Object paramObject)
  {
    if ((paramObject instanceof PolicyMappings)) {
      return (PolicyMappings)paramObject;
    }
    if (paramObject != null) {
      return new PolicyMappings(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.PolicyMappings
 * JD-Core Version:    0.7.0.1
 */