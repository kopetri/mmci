package org.spongycastle.asn1.esf;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1ObjectIdentifier;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class CommitmentTypeIndication
  extends ASN1Object
{
  private ASN1ObjectIdentifier commitmentTypeId;
  private ASN1Sequence commitmentTypeQualifier;
  
  public CommitmentTypeIndication(ASN1ObjectIdentifier paramASN1ObjectIdentifier)
  {
    this.commitmentTypeId = paramASN1ObjectIdentifier;
  }
  
  public CommitmentTypeIndication(ASN1ObjectIdentifier paramASN1ObjectIdentifier, ASN1Sequence paramASN1Sequence)
  {
    this.commitmentTypeId = paramASN1ObjectIdentifier;
    this.commitmentTypeQualifier = paramASN1Sequence;
  }
  
  private CommitmentTypeIndication(ASN1Sequence paramASN1Sequence)
  {
    this.commitmentTypeId = ((ASN1ObjectIdentifier)paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.commitmentTypeQualifier = ((ASN1Sequence)paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public static CommitmentTypeIndication getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof CommitmentTypeIndication))) {
      return (CommitmentTypeIndication)paramObject;
    }
    return new CommitmentTypeIndication(ASN1Sequence.getInstance(paramObject));
  }
  
  public ASN1ObjectIdentifier getCommitmentTypeId()
  {
    return this.commitmentTypeId;
  }
  
  public ASN1Sequence getCommitmentTypeQualifier()
  {
    return this.commitmentTypeQualifier;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.commitmentTypeId);
    if (this.commitmentTypeQualifier != null) {
      localASN1EncodableVector.add(this.commitmentTypeQualifier);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CommitmentTypeIndication
 * JD-Core Version:    0.7.0.1
 */