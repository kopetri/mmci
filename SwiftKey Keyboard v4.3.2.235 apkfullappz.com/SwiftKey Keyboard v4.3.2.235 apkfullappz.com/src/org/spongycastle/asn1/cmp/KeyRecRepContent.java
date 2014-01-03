package org.spongycastle.asn1.cmp;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;

public class KeyRecRepContent
  extends ASN1Object
{
  private ASN1Sequence caCerts;
  private ASN1Sequence keyPairHist;
  private CMPCertificate newSigCert;
  private PKIStatusInfo status;
  
  private KeyRecRepContent(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.status = PKIStatusInfo.getInstance(localEnumeration.nextElement());
    while (localEnumeration.hasMoreElements())
    {
      ASN1TaggedObject localASN1TaggedObject = ASN1TaggedObject.getInstance(localEnumeration.nextElement());
      switch (localASN1TaggedObject.getTagNo())
      {
      default: 
        throw new IllegalArgumentException("unknown tag number: " + localASN1TaggedObject.getTagNo());
      case 0: 
        this.newSigCert = CMPCertificate.getInstance(localASN1TaggedObject.getObject());
        break;
      case 1: 
        this.caCerts = ASN1Sequence.getInstance(localASN1TaggedObject.getObject());
        break;
      case 2: 
        this.keyPairHist = ASN1Sequence.getInstance(localASN1TaggedObject.getObject());
      }
    }
  }
  
  private void addOptional(ASN1EncodableVector paramASN1EncodableVector, int paramInt, ASN1Encodable paramASN1Encodable)
  {
    if (paramASN1Encodable != null) {
      paramASN1EncodableVector.add(new DERTaggedObject(true, paramInt, paramASN1Encodable));
    }
  }
  
  public static KeyRecRepContent getInstance(Object paramObject)
  {
    if ((paramObject instanceof KeyRecRepContent)) {
      return (KeyRecRepContent)paramObject;
    }
    if (paramObject != null) {
      return new KeyRecRepContent(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public CMPCertificate[] getCaCerts()
  {
    CMPCertificate[] arrayOfCMPCertificate;
    if (this.caCerts == null) {
      arrayOfCMPCertificate = null;
    }
    for (;;)
    {
      return arrayOfCMPCertificate;
      arrayOfCMPCertificate = new CMPCertificate[this.caCerts.size()];
      for (int i = 0; i != arrayOfCMPCertificate.length; i++) {
        arrayOfCMPCertificate[i] = CMPCertificate.getInstance(this.caCerts.getObjectAt(i));
      }
    }
  }
  
  public CertifiedKeyPair[] getKeyPairHist()
  {
    CertifiedKeyPair[] arrayOfCertifiedKeyPair;
    if (this.keyPairHist == null) {
      arrayOfCertifiedKeyPair = null;
    }
    for (;;)
    {
      return arrayOfCertifiedKeyPair;
      arrayOfCertifiedKeyPair = new CertifiedKeyPair[this.keyPairHist.size()];
      for (int i = 0; i != arrayOfCertifiedKeyPair.length; i++) {
        arrayOfCertifiedKeyPair[i] = CertifiedKeyPair.getInstance(this.keyPairHist.getObjectAt(i));
      }
    }
  }
  
  public CMPCertificate getNewSigCert()
  {
    return this.newSigCert;
  }
  
  public PKIStatusInfo getStatus()
  {
    return this.status;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.status);
    addOptional(localASN1EncodableVector, 0, this.newSigCert);
    addOptional(localASN1EncodableVector, 1, this.caCerts);
    addOptional(localASN1EncodableVector, 2, this.keyPairHist);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.KeyRecRepContent
 * JD-Core Version:    0.7.0.1
 */