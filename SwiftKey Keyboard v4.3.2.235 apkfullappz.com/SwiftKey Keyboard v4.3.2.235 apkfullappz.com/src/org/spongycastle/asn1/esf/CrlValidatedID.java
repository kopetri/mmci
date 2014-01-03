package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CrlValidatedID
  extends ASN1Object
{
  private OtherHash crlHash;
  private CrlIdentifier crlIdentifier;
  
  private CrlValidatedID(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.crlHash = OtherHash.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.crlIdentifier = CrlIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public CrlValidatedID(OtherHash paramOtherHash)
  {
    this(paramOtherHash, null);
  }
  
  public CrlValidatedID(OtherHash paramOtherHash, CrlIdentifier paramCrlIdentifier)
  {
    this.crlHash = paramOtherHash;
    this.crlIdentifier = paramCrlIdentifier;
  }
  
  public static CrlValidatedID getInstance(Object paramObject)
  {
    if ((paramObject instanceof CrlValidatedID)) {
      return (CrlValidatedID)paramObject;
    }
    if (paramObject != null) {
      return new CrlValidatedID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public OtherHash getCrlHash()
  {
    return this.crlHash;
  }
  
  public CrlIdentifier getCrlIdentifier()
  {
    return this.crlIdentifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.crlHash.toASN1Primitive());
    if (this.crlIdentifier != null) {
      localASN1EncodableVector.add(this.crlIdentifier.toASN1Primitive());
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CrlValidatedID
 * JD-Core Version:    0.7.0.1
 */