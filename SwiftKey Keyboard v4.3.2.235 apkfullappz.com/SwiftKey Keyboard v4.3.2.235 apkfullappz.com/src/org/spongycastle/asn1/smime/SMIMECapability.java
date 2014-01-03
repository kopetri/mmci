package org.spongycastle.asn1.smime;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.nist.NISTObjectIdentifiers;
import org.spongycastle.asn1.pkcs.PKCSObjectIdentifiers;

public class SMIMECapability
  extends ASN1Object
{
  public static final ASN1ObjectIdentifier aES128_CBC = NISTObjectIdentifiers.id_aes128_CBC;
  public static final ASN1ObjectIdentifier aES192_CBC = NISTObjectIdentifiers.id_aes192_CBC;
  public static final ASN1ObjectIdentifier aES256_CBC = NISTObjectIdentifiers.id_aes256_CBC;
  public static final ASN1ObjectIdentifier canNotDecryptAny;
  public static final ASN1ObjectIdentifier dES_CBC;
  public static final ASN1ObjectIdentifier dES_EDE3_CBC;
  public static final ASN1ObjectIdentifier preferSignedData = PKCSObjectIdentifiers.preferSignedData;
  public static final ASN1ObjectIdentifier rC2_CBC;
  public static final ASN1ObjectIdentifier sMIMECapabilitiesVersions;
  private ASN1ObjectIdentifier capabilityID;
  private ASN1Encodable parameters;
  
  static
  {
    canNotDecryptAny = PKCSObjectIdentifiers.canNotDecryptAny;
    sMIMECapabilitiesVersions = PKCSObjectIdentifiers.sMIMECapabilitiesVersions;
    dES_CBC = new ASN1ObjectIdentifier("1.3.14.3.2.7");
    dES_EDE3_CBC = PKCSObjectIdentifiers.des_EDE3_CBC;
    rC2_CBC = PKCSObjectIdentifiers.RC2_CBC;
  }
  
  public SMIMECapability(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.capabilityID = paramASN1ObjectIdentifier;
    this.parameters = paramASN1Encodable;
  }
  
  public SMIMECapability(ASN1Sequence paramASN1Sequence)
  {
    this.capabilityID = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.parameters = ((ASN1Primitive)paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public static SMIMECapability getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof SMIMECapability))) {
      return (SMIMECapability)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new SMIMECapability((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Invalid SMIMECapability");
  }
  
  public ASN1ObjectIdentifier getCapabilityID()
  {
    return this.capabilityID;
  }
  
  public ASN1Encodable getParameters()
  {
    return this.parameters;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.capabilityID);
    if (this.parameters != null) {
      localASN1EncodableVector.add(this.parameters);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.smime.SMIMECapability
 * JD-Core Version:    0.7.0.1
 */