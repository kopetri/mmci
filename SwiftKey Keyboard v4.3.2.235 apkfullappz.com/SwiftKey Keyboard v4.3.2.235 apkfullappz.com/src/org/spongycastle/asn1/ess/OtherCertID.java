package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;
import org.spongycastle.asn1.x509.DigestInfo;
import org.spongycastle.asn1.x509.IssuerSerial;

public class OtherCertID
  extends ASN1Object
{
  private IssuerSerial issuerSerial;
  private ASN1Encodable otherCertHash;
  
  private OtherCertID(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    if ((paramASN1Sequence.getObjectAt(0).toASN1Primitive() instanceof ASN1OctetString)) {}
    for (this.otherCertHash = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(0));; this.otherCertHash = DigestInfo.getInstance(paramASN1Sequence.getObjectAt(0)))
    {
      if (paramASN1Sequence.size() > 1) {
        this.issuerSerial = new IssuerSerial(ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(1)));
      }
      return;
    }
  }
  
  public OtherCertID(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte)
  {
    this.otherCertHash = new DigestInfo(paramAlgorithmIdentifier, paramArrayOfByte);
  }
  
  public OtherCertID(AlgorithmIdentifier paramAlgorithmIdentifier, byte[] paramArrayOfByte, IssuerSerial paramIssuerSerial)
  {
    this.otherCertHash = new DigestInfo(paramAlgorithmIdentifier, paramArrayOfByte);
    this.issuerSerial = paramIssuerSerial;
  }
  
  public static OtherCertID getInstance(Object paramObject)
  {
    if ((paramObject instanceof OtherCertID)) {
      return (OtherCertID)paramObject;
    }
    if (paramObject != null) {
      return new OtherCertID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public AlgorithmIdentifier getAlgorithmHash()
  {
    if ((this.otherCertHash.toASN1Primitive() instanceof ASN1OctetString)) {
      return new AlgorithmIdentifier("1.3.14.3.2.26");
    }
    return DigestInfo.getInstance(this.otherCertHash).getAlgorithmId();
  }
  
  public byte[] getCertHash()
  {
    if ((this.otherCertHash.toASN1Primitive() instanceof ASN1OctetString)) {
      return ((ASN1OctetString)this.otherCertHash.toASN1Primitive()).getOctets();
    }
    return DigestInfo.getInstance(this.otherCertHash).getDigest();
  }
  
  public IssuerSerial getIssuerSerial()
  {
    return this.issuerSerial;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.otherCertHash);
    if (this.issuerSerial != null) {
      localASN1EncodableVector.add(this.issuerSerial);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.OtherCertID
 * JD-Core Version:    0.7.0.1
 */