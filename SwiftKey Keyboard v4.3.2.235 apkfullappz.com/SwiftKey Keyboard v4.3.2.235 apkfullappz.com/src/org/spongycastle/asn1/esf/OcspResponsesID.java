package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class OcspResponsesID
  extends ASN1Object
{
  private OcspIdentifier ocspIdentifier;
  private OtherHash ocspRepHash;
  
  private OcspResponsesID(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.ocspIdentifier = OcspIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.ocspRepHash = OtherHash.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public OcspResponsesID(OcspIdentifier paramOcspIdentifier)
  {
    this(paramOcspIdentifier, null);
  }
  
  public OcspResponsesID(OcspIdentifier paramOcspIdentifier, OtherHash paramOtherHash)
  {
    this.ocspIdentifier = paramOcspIdentifier;
    this.ocspRepHash = paramOtherHash;
  }
  
  public static OcspResponsesID getInstance(Object paramObject)
  {
    if ((paramObject instanceof OcspResponsesID)) {
      return (OcspResponsesID)paramObject;
    }
    if (paramObject != null) {
      return new OcspResponsesID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public OcspIdentifier getOcspIdentifier()
  {
    return this.ocspIdentifier;
  }
  
  public OtherHash getOcspRepHash()
  {
    return this.ocspRepHash;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.ocspIdentifier);
    if (this.ocspRepHash != null) {
      localASN1EncodableVector.add(this.ocspRepHash);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.OcspResponsesID
 * JD-Core Version:    0.7.0.1
 */