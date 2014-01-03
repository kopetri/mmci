package org.spongycastle.asn1.cmp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class CertRepMessage
  extends ASN1Object
{
  private ASN1Sequence caPubs;
  private ASN1Sequence response;
  
  private CertRepMessage(ASN1Sequence paramASN1Sequence)
  {
    int i = paramASN1Sequence.size();
    int j = 0;
    if (i > 1)
    {
      j = 0 + 1;
      this.caPubs = ASN1Sequence.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), true);
    }
    this.response = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(j));
  }
  
  public CertRepMessage(CMPCertificate[] paramArrayOfCMPCertificate, CertResponse[] paramArrayOfCertResponse)
  {
    if (paramArrayOfCertResponse == null) {
      throw new IllegalArgumentException("'response' cannot be null");
    }
    if (paramArrayOfCMPCertificate != null)
    {
      ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
      for (int i = 0; i < paramArrayOfCMPCertificate.length; i++) {
        localASN1EncodableVector1.add(paramArrayOfCMPCertificate[i]);
      }
      this.caPubs = new DERSequence(localASN1EncodableVector1);
    }
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    for (int j = 0; j < paramArrayOfCertResponse.length; j++) {
      localASN1EncodableVector2.add(paramArrayOfCertResponse[j]);
    }
    this.response = new DERSequence(localASN1EncodableVector2);
  }
  
  public static CertRepMessage getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertRepMessage)) {
      return (CertRepMessage)paramObject;
    }
    if (paramObject != null) {
      return new CertRepMessage(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CMPCertificate[] getCaPubs()
  {
    CMPCertificate[] arrayOfCMPCertificate;
    if (this.caPubs == null) {
      arrayOfCMPCertificate = null;
    }
    for (;;)
    {
      return arrayOfCMPCertificate;
      arrayOfCMPCertificate = new CMPCertificate[this.caPubs.size()];
      for (int i = 0; i != arrayOfCMPCertificate.length; i++) {
        arrayOfCMPCertificate[i] = CMPCertificate.getInstance(this.caPubs.getObjectAt(i));
      }
    }
  }
  
  public CertResponse[] getResponse()
  {
    CertResponse[] arrayOfCertResponse = new CertResponse[this.response.size()];
    for (int i = 0; i != arrayOfCertResponse.length; i++) {
      arrayOfCertResponse[i] = CertResponse.getInstance(this.response.getObjectAt(i));
    }
    return arrayOfCertResponse;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.caPubs != null) {
      localASN1EncodableVector.add(new DERTaggedObject(true, 1, this.caPubs));
    }
    localASN1EncodableVector.add(this.response);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CertRepMessage
 * JD-Core Version:    0.7.0.1
 */