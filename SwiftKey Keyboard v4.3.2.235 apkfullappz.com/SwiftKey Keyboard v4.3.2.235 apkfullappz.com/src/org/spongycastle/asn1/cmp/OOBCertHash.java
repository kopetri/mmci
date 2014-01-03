package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.crmf.CertId;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class OOBCertHash
  extends ASN1Object
{
  private CertId certId;
  private AlgorithmIdentifier hashAlg;
  private DERBitString hashVal;
  
  private OOBCertHash(ASN1Sequence paramASN1Sequence)
  {
    int i = -1 + paramASN1Sequence.size();
    int j = i - 1;
    this.hashVal = DERBitString.getInstance(paramASN1Sequence.getObjectAt(i));
    int k = j;
    if (k >= 0)
    {
      ASN1TaggedObject localASN1TaggedObject = (ASN1TaggedObject)paramASN1Sequence.getObjectAt(k);
      if (localASN1TaggedObject.getTagNo() == 0) {
        this.hashAlg = AlgorithmIdentifier.getInstance(localASN1TaggedObject, true);
      }
      for (;;)
      {
        k--;
        break;
        this.certId = CertId.getInstance(localASN1TaggedObject, true);
      }
    }
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(new DERTaggedObject(true, paramInt, paramASN1Encodable));
    }
  }
  
  public static OOBCertHash getInstance(Object paramObject)
  {
    if ((paramObject instanceof OOBCertHash)) {
      return (OOBCertHash)paramObject;
    }
    if (paramObject != null) {
      return new OOBCertHash(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertId getCertId()
  {
    return this.certId;
  }
  
  public AlgorithmIdentifier getHashAlg()
  {
    return this.hashAlg;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    addOptional(localASN1EncodableVector, 0, this.hashAlg);
    addOptional(localASN1EncodableVector, 1, this.certId);
    localASN1EncodableVector.add(this.hashVal);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.OOBCertHash
 * JD-Core Version:    0.7.0.1
 */