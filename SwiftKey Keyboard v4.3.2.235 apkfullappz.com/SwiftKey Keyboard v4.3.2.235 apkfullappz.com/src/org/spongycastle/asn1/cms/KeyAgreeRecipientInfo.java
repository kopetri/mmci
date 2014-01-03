package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class KeyAgreeRecipientInfo
  extends ASN1Object
{
  private AlgorithmIdentifier keyEncryptionAlgorithm;
  private OriginatorIdentifierOrKey originator;
  private ASN1Sequence recipientEncryptedKeys;
  private ASN1OctetString ukm;
  private ASN1Integer version;
  
  public KeyAgreeRecipientInfo(ASN1Sequence paramASN1Sequence)
  {
    int i = 0 + 1;
    this.version = ((ASN1Integer)paramASN1Sequence.getObjectAt(0));
    int j = i + 1;
    this.originator = OriginatorIdentifierOrKey.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(1), true);
    if ((paramASN1Sequence.getObjectAt(2) instanceof ASN1TaggedObject))
    {
      j++;
      this.ukm = ASN1OctetString.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(2), true);
    }
    int k = j + 1;
    this.keyEncryptionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(j));
    this.recipientEncryptedKeys = ((ASN1Sequence)paramASN1Sequence.getObjectAt(k));
  }
  
  public KeyAgreeRecipientInfo(OriginatorIdentifierOrKey paramOriginatorIdentifierOrKey, ASN1OctetString paramASN1OctetString, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1Sequence paramASN1Sequence)
  {
    this.version = new ASN1Integer(3);
    this.originator = paramOriginatorIdentifierOrKey;
    this.ukm = paramASN1OctetString;
    this.keyEncryptionAlgorithm = paramAlgorithmIdentifier;
    this.recipientEncryptedKeys = paramASN1Sequence;
  }
  
  public static KeyAgreeRecipientInfo getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof KeyAgreeRecipientInfo))) {
      return (KeyAgreeRecipientInfo)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new KeyAgreeRecipientInfo((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("Illegal object in KeyAgreeRecipientInfo: " + paramObject.getClass().getName());
  }
  
  public static KeyAgreeRecipientInfo getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getKeyEncryptionAlgorithm()
  {
    return this.keyEncryptionAlgorithm;
  }
  
  public OriginatorIdentifierOrKey getOriginator()
  {
    return this.originator;
  }
  
  public ASN1Sequence getRecipientEncryptedKeys()
  {
    return this.recipientEncryptedKeys;
  }
  
  public ASN1OctetString getUserKeyingMaterial()
  {
    return this.ukm;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(new DERTaggedObject(true, 0, this.originator));
    if (this.ukm != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.ukm));
    }
    localASN1EncodableVector.add(this.keyEncryptionAlgorithm);
    localASN1EncodableVector.add(this.recipientEncryptedKeys);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.KeyAgreeRecipientInfo
 * JD-Core Version:    0.7.0.1
 */