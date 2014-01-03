package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class EncryptionScheme
  extends AlgorithmIdentifier
{
  public EncryptionScheme(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    super(paramASN1ObjectIdentifier, paramASN1Encodable);
  }
  
  EncryptionScheme(ASN1Sequence paramASN1Sequence)
  {
    this((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0), paramASN1Sequence.getObjectAt(1));
  }
  
  public static final AlgorithmIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof EncryptionScheme)) {
      return (EncryptionScheme)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new EncryptionScheme((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public ASN1Primitive getASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(getObjectId());
    localASN1EncodableVector.add(getParameters());
    return new DERSequence(localASN1EncodableVector);
  }
  
  public ASN1Primitive getObject()
  {
    return (ASN1Primitive)getParameters();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.EncryptionScheme
 * JD-Core Version:    0.7.0.1
 */