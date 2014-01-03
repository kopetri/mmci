package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class OtherHashAlgAndValue
  extends ASN1Object
{
  private AlgorithmIdentifier hashAlgorithm;
  private ASN1OctetString hashValue;
  
  private OtherHashAlgAndValue(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.hashAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.hashValue = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public OtherHashAlgAndValue(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString)
  {
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.hashValue = paramASN1OctetString;
  }
  
  public static OtherHashAlgAndValue getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherHashAlgAndValue)) {
      return (OtherHashAlgAndValue)paramObject;
    }
    if (paramObject != null) {
      return new OtherHashAlgAndValue(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public ASN1OctetString getHashValue()
  {
    return this.hashValue;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.hashAlgorithm);
    localASN1EncodableVector.add(this.hashValue);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OtherHashAlgAndValue
 * JD-Core Version:    0.7.0.1
 */