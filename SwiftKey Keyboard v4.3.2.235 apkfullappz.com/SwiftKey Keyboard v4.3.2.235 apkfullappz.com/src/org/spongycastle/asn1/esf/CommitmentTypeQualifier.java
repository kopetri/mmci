package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CommitmentTypeQualifier
  extends ASN1Object
{
  private ASN1ObjectIdentifier commitmentTypeIdentifier;
  private ASN1Encodable qualifier;
  
  public CommitmentTypeQualifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this(paramASN1ObjectIdentifier, null);
  }
  
  public CommitmentTypeQualifier(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Encodable paramASN1Encodable)
  {
    this.commitmentTypeIdentifier = paramASN1ObjectIdentifier;
    this.qualifier = paramASN1Encodable;
  }
  
  private CommitmentTypeQualifier(ASN1Sequence paramASN1Sequence)
  {
    this.commitmentTypeIdentifier = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.qualifier = paramASN1Sequence.getObjectAt(1);
    }
  }
  
  public static CommitmentTypeQualifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof CommitmentTypeQualifier)) {
      return (CommitmentTypeQualifier)paramObject;
    }
    if (paramObject != null) {
      return new CommitmentTypeQualifier(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1ObjectIdentifier getCommitmentTypeIdentifier()
  {
    return this.commitmentTypeIdentifier;
  }
  
  public ASN1Encodable getQualifier()
  {
    return this.qualifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.commitmentTypeIdentifier);
    if (this.qualifier != null) {
      localASN1EncodableVector.add(this.qualifier);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CommitmentTypeQualifier
 * JD-Core Version:    0.7.0.1
 */