package org.spongycastle.asn1.cms;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.BERSequence;
import org.spongycastle.asn1.BERTaggedObject;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptedContentInfo
  extends ASN1Object
{
  private AlgorithmIdentifier contentEncryptionAlgorithm;
  private ASN1ObjectIdentifier contentType;
  private ASN1OctetString encryptedContent;
  
  public EncryptedContentInfo(ASN1ObjectIdentifier paramASN1ObjectIdentifier, AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString)
  {
    this.contentType = paramASN1ObjectIdentifier;
    this.contentEncryptionAlgorithm = paramAlgorithmIdentifier;
    this.encryptedContent = paramASN1OctetString;
  }
  
  private EncryptedContentInfo(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() < 2) {
      throw new IllegalArgumentException("Truncated Sequence Found");
    }
    this.contentType = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    this.contentEncryptionAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.encryptedContent = ASN1OctetString.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(2), false);
    }
  }
  
  public static EncryptedContentInfo getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptedContentInfo)) {
      return (EncryptedContentInfo)paramObject;
    }
    if (paramObject != null) {
      return new EncryptedContentInfo(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AlgorithmIdentifier getContentEncryptionAlgorithm()
  {
    return this.contentEncryptionAlgorithm;
  }
  
  public ASN1ObjectIdentifier getContentType()
  {
    return this.contentType;
  }
  
  public ASN1OctetString getEncryptedContent()
  {
    return this.encryptedContent;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.contentType);
    localASN1EncodableVector.add(this.contentEncryptionAlgorithm);
    if (this.encryptedContent != null) {
      localASN1EncodableVector.add(new BERTaggedObject(false, 0, this.encryptedContent));
    }
    return new BERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cms.EncryptedContentInfo
 * JD-Core Version:    0.7.0.1
 */