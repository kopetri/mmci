package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyTransRecipientInfo
  extends ASN1Object
{
  private ASN1OctetString encryptedKey;
  private AlgorithmIdentifier keyEncryptionAlgorithm;
  private RecipientIdentifier rid;
  private ASN1Integer version;
  
  public KeyTransRecipientInfo(ASN1Sequence paramASN1Sequence)
  {
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    this.rid = RecipientIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
    this.keyEncryptionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(2));
    this.encryptedKey = ((ASN1OctetString)paramASN1Sequence.getObjectAt(3));
  }
  
  public KeyTransRecipientInfo(RecipientIdentifier paramRecipientIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString)
  {
    if ((paramRecipientIdentifier.toASN1Primitive() instanceof ASN1TaggedObject)) {}
    for (this.version = new ASN1Integer(2);; this.version = new ASN1Integer(0))
    {
      this.rid = paramRecipientIdentifier;
      this.keyEncryptionAlgorithm = paramAlgorithmIdentifier;
      this.encryptedKey = paramASN1OctetString;
      return;
    }
  }
  
  public static KeyTransRecipientInfo getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof KeyTransRecipientInfo))) {
      return (KeyTransRecipientInfo)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new KeyTransRecipientInfo((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Illegal object in KeyTransRecipientInfo: " + paramObject.getClass().getName());
  }
  
  public ASN1OctetString getEncryptedKey()
  {
    return this.encryptedKey;
  }
  
  public AlgorithmIdentifier getKeyEncryptionAlgorithm()
  {
    return this.keyEncryptionAlgorithm;
  }
  
  public RecipientIdentifier getRecipientIdentifier()
  {
    return this.rid;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.rid);
    localASN1EncodableVector.add(this.keyEncryptionAlgorithm);
    localASN1EncodableVector.add(this.encryptedKey);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.KeyTransRecipientInfo
 * JD-Core Version:    0.7.0.1
 */