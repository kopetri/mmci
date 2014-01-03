package org.spongycastle.asn1.pkcs;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CertificationRequest
  extends ASN1Object
{
  protected CertificationRequestInfo reqInfo = null;
  protected AlgorithmIdentifier sigAlgId = null;
  protected DERBitString sigBits = null;
  
  protected CertificationRequest() {}
  
  public CertificationRequest(ASN1Sequence paramASN1Sequence)
  {
    this.reqInfo = CertificationRequestInfo.getInstance(paramASN1Sequence.getObjectAt(0));
    this.sigAlgId = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
    this.sigBits = ((DERBitString)paramASN1Sequence.getObjectAt(2));
  }
  
  public CertificationRequest(CertificationRequestInfo paramCertificationRequestInfo, AlgorithmIdentifier paramAlgorithmIdentifier, DERBitString paramDERBitString)
  {
    this.reqInfo = paramCertificationRequestInfo;
    this.sigAlgId = paramAlgorithmIdentifier;
    this.sigBits = paramDERBitString;
  }
  
  public static CertificationRequest getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertificationRequest)) {
      return (CertificationRequest)paramObject;
    }
    if (paramObject != null) {
      return new CertificationRequest(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CertificationRequestInfo getCertificationRequestInfo()
  {
    return this.reqInfo;
  }
  
  public DERBitString getSignature()
  {
    return this.sigBits;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.sigAlgId;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.reqInfo);
    localASN1EncodableVector.add(this.sigAlgId);
    localASN1EncodableVector.add(this.sigBits);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.pkcs.CertificationRequest
 * JD-Core Version:    0.7.0.1
 */