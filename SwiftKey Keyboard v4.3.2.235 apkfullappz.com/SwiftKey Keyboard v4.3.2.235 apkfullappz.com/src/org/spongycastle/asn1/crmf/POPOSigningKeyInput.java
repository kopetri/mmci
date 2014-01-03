package org.spongycastle.asn1.crmf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.GeneralName;
import org.spongycastle.asn1.x509.SubjectPublicKeyInfo;

public class POPOSigningKeyInput
  extends ASN1Object
{
  private SubjectPublicKeyInfo publicKey;
  private PKMACValue publicKeyMAC;
  private GeneralName sender;
  
  private POPOSigningKeyInput(ASN1Sequence paramASN1Sequence)
  {
    ASN1Encodable localASN1Encodable = paramASN1Sequence.getObjectAt(0);
    if ((localASN1Encodable instanceof ASN1TaggedObject))
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)localASN1Encodable;
      if (localASN1TaggedObject.getTagNo() != 0) {
        throw new IllegalArgumentException("Unknown authInfo tag: " + localASN1TaggedObject.getTagNo());
      }
      this.sender = GeneralName.getInstance(localASN1TaggedObject.getObject());
    }
    for (;;)
    {
      this.publicKey = SubjectPublicKeyInfo.getInstance(paramASN1Sequence.getObjectAt(1));
      return;
      this.publicKeyMAC = PKMACValue.getInstance(localASN1Encodable);
    }
  }
  
  public POPOSigningKeyInput(PKMACValue paramPKMACValue, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.publicKeyMAC = paramPKMACValue;
    this.publicKey = paramSubjectPublicKeyInfo;
  }
  
  public POPOSigningKeyInput(GeneralName paramGeneralName, SubjectPublicKeyInfo paramSubjectPublicKeyInfo)
  {
    this.sender = paramGeneralName;
    this.publicKey = paramSubjectPublicKeyInfo;
  }
  
  public static POPOSigningKeyInput getInstance(Object paramObject)
  {
    if ((paramObject instanceof POPOSigningKeyInput)) {
      return (POPOSigningKeyInput)paramObject;
    }
    if (paramObject != null) {
      return new POPOSigningKeyInput(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public SubjectPublicKeyInfo getPublicKey()
  {
    return this.publicKey;
  }
  
  public PKMACValue getPublicKeyMAC()
  {
    return this.publicKeyMAC;
  }
  
  public GeneralName getSender()
  {
    return this.sender;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.sender != null) {
      localASN1EncodableVector.add(new DERTaggedObject(false, 0, this.sender));
    }
    for (;;)
    {
      localASN1EncodableVector.add(this.publicKey);
      return new DERSequence(localASN1EncodableVector);
      localASN1EncodableVector.add(this.publicKeyMAC);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.POPOSigningKeyInput
 * JD-Core Version:    0.7.0.1
 */