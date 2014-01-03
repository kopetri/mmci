package org.spongycastle.asn1.cmp;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;

public class CertStatus
  extends ASN1Object
{
  private ASN1OctetString certHash;
  private ASN1Integer certReqId;
  private PKIStatusInfo statusInfo;
  
  private CertStatus(ASN1Sequence paramASN1Sequence)
  {
    this.certHash = ASN1OctetString.getInstance(paramASN1Sequence.getObjectAt(0));
    this.certReqId = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.statusInfo = PKIStatusInfo.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public CertStatus(byte[] paramArrayOfByte, BigInteger paramBigInteger)
  {
    this.certHash = new DEROctetString(paramArrayOfByte);
    this.certReqId = new ASN1Integer(paramBigInteger);
  }
  
  public CertStatus(byte[] paramArrayOfByte, BigInteger paramBigInteger, PKIStatusInfo paramPKIStatusInfo)
  {
    this.certHash = new DEROctetString(paramArrayOfByte);
    this.certReqId = new ASN1Integer(paramBigInteger);
    this.statusInfo = paramPKIStatusInfo;
  }
  
  public static CertStatus getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertStatus)) {
      return (CertStatus)paramObject;
    }
    if (paramObject != null) {
      return new CertStatus(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public ASN1OctetString getCertHash()
  {
    return this.certHash;
  }
  
  public ASN1Integer getCertReqId()
  {
    return this.certReqId;
  }
  
  public PKIStatusInfo getStatusInfo()
  {
    return this.statusInfo;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.certHash);
    localASN1EncodableVector.add(this.certReqId);
    if (this.statusInfo != null) {
      localASN1EncodableVector.add(this.statusInfo);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.cmp.CertStatus
 * JD-Core Version:    0.7.0.1
 */