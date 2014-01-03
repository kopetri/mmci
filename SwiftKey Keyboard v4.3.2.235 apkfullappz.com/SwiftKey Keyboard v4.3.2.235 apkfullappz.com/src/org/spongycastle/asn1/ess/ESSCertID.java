package org.spongycastle.asn1.ess;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.IssuerSerial;

public class ESSCertID
  extends ASN1Object
{
  private ASN1OctetString certHash;
  private IssuerSerial issuerSerial;
  
  private ESSCertID(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() <= 0) || (paramASN1Sequence.size() > 2)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.certHash = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(0));
    if (paramASN1Sequence.size() > 1) {
      this.issuerSerial = IssuerSerial.getInstance(paramASN1Sequence.getObjectAt(1));
    }
  }
  
  public ESSCertID(byte[] paramArrayOfByte)
  {
    this.certHash = new DEROctetString(paramArrayOfByte);
  }
  
  public ESSCertID(byte[] paramArrayOfByte, IssuerSerial paramIssuerSerial)
  {
    this.certHash = new DEROctetString(paramArrayOfByte);
    this.issuerSerial = paramIssuerSerial;
  }
  
  public static ESSCertID getInstance(Object paramObject)
  {
    if ((paramObject instanceof ESSCertID)) {
      return (ESSCertID)paramObject;
    }
    if (paramObject != null) {
      return new ESSCertID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public byte[] getCertHash()
  {
    return this.certHash.getOctets();
  }
  
  public IssuerSerial getIssuerSerial()
  {
    return this.issuerSerial;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certHash);
    if (this.issuerSerial != null) {
      localASN1EncodableVector.add(this.issuerSerial);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ess.ESSCertID
 * JD-Core Version:    0.7.0.1
 */