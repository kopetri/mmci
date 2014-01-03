package org.spongycastle.asn1.x9;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class OtherInfo
  extends ASN1Object
{
  private KeySpecificInfo keyInfo;
  private ASN1OctetString partyAInfo;
  private ASN1OctetString suppPubInfo;
  
  public OtherInfo(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.keyInfo = new KeySpecificInfo((ASN1Sequence)localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      DERTaggedObject localDERTaggedObject = (DERTaggedObject)localEnumeration.nextElement();
      if (localDERTaggedObject.getTagNo() == 0) {
        this.partyAInfo = ((ASN1OctetString)localDERTaggedObject.getObject());
      } else if (localDERTaggedObject.getTagNo() == 2) {
        this.suppPubInfo = ((ASN1OctetString)localDERTaggedObject.getObject());
      }
    }
  }
  
  public OtherInfo(KeySpecificInfo paramKeySpecificInfo, ASN1OctetString paramASN1OctetString1, ASN1OctetString paramASN1OctetString2)
  {
    this.keyInfo = paramKeySpecificInfo;
    this.partyAInfo = paramASN1OctetString1;
    this.suppPubInfo = paramASN1OctetString2;
  }
  
  public KeySpecificInfo getKeyInfo()
  {
    return this.keyInfo;
  }
  
  public ASN1OctetString getPartyAInfo()
  {
    return this.partyAInfo;
  }
  
  public ASN1OctetString getSuppPubInfo()
  {
    return this.suppPubInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.keyInfo);
    if (this.partyAInfo != null) {
      localASN1EncodableVector.add(new DERTaggedObject(0, this.partyAInfo));
    }
    localASN1EncodableVector.add(new DERTaggedObject(2, this.suppPubInfo));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x9.OtherInfo
 * JD-Core Version:    0.7.0.1
 */