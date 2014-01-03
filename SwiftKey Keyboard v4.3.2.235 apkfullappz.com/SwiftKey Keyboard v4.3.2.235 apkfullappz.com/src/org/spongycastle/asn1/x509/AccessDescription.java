package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class AccessDescription
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier id_ad_caIssuers = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.2");
  public static final ASN1ObjectIdentifier id_ad_ocsp = new ASN1ObjectIdentifier("1.3.6.1.5.5.7.48.1");
  GeneralName accessLocation = null;
  ASN1ObjectIdentifier accessMethod = null;
  
  public AccessDescription(ASN1ObjectIdentifier paramASN1ObjectIdentifier, GeneralName paramGeneralName)
  {
    this.accessMethod = paramASN1ObjectIdentifier;
    this.accessLocation = paramGeneralName;
  }
  
  private AccessDescription(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("wrong number of elements in sequence");
    }
    this.accessMethod = ASN1ObjectIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.accessLocation = GeneralName.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public static AccessDescription getInstance(Object paramObject)
  {
    if ((paramObject instanceof AccessDescription)) {
      return (AccessDescription)paramObject;
    }
    if (paramObject != null) {
      return new AccessDescription(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public GeneralName getAccessLocation()
  {
    return this.accessLocation;
  }
  
  public ASN1ObjectIdentifier getAccessMethod()
  {
    return this.accessMethod;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.accessMethod);
    localASN1EncodableVector.add(this.accessLocation);
    return new DERSequence(localASN1EncodableVector);
  }
  
  public String toString()
  {
    return "AccessDescription: Oid(" + this.accessMethod.getId() + ")";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.AccessDescription
 * JD-Core Version:    0.7.0.1
 */