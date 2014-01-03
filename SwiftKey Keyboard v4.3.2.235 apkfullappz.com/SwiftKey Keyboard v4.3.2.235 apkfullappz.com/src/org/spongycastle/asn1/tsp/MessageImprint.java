package org.spongycastle.asn1.tsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class MessageImprint
  extends ASN1Object
{
  AlgorithmIdentifier hashAlgorithm;
  byte[] hashedMessage;
  
  private MessageImprint(ASN1Sequence paramASN1Sequence)
  {
    this.hashAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.hashedMessage = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(1)).getOctets();
  }
  
  public MessageImprint(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.hashedMessage = paramArrayOfByte;
  }
  
  public static MessageImprint getInstance(Object paramObject)
  {
    if ((paramObject instanceof MessageImprint)) {
      return (MessageImprint)paramObject;
    }
    if (paramObject != null) {
      return new MessageImprint(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public byte[] getHashedMessage()
  {
    return this.hashedMessage;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.hashAlgorithm);
    localASN1EncodableVector.add(new DEROctetString(this.hashedMessage));
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.tsp.MessageImprint
 * JD-Core Version:    0.7.0.1
 */