package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class Challenge
  extends ASN1Object
{
  private ASN1OctetString challenge;
  private AlgorithmIdentifier owf;
  private ASN1OctetString witness;
  
  private Challenge(ASN1Sequence paramASN1Sequence)
  {
    int i = paramASN1Sequence.size();
    int j = 0;
    if (i == 3)
    {
      j = 0 + 1;
      this.owf = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    }
    int k = j + 1;
    this.witness = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(j));
    this.challenge = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(k));
  }
  
  public Challenge(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this.owf = paramAlgorithmIdentifier;
    this.witness = new DEROctetString(paramArrayOfByte1);
    this.challenge = new DEROctetString(paramArrayOfByte2);
  }
  
  public Challenge(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    this(null, paramArrayOfByte1, paramArrayOfByte2);
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(paramASN1Encodable);
    }
  }
  
  public static Challenge getInstance(Object paramObject)
  {
    if ((paramObject instanceof Challenge)) {
      return (Challenge)paramObject;
    }
    if (paramObject != null) {
      return new Challenge(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getChallenge()
  {
    return this.challenge.getOctets();
  }
  
  public AlgorithmIdentifier getOwf()
  {
    return this.owf;
  }
  
  public byte[] getWitness()
  {
    return this.witness.getOctets();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    addOptional(localASN1EncodableVector, this.owf);
    localASN1EncodableVector.add(this.witness);
    localASN1EncodableVector.add(this.challenge);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.Challenge
 * JD-Core Version:    0.7.0.1
 */