package org.spongycastle.asn1.ocsp;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.AlgorithmIdentifier;

public class CertID
  extends ASN1Object
{
  AlgorithmIdentifier hashAlgorithm;
  ASN1OctetString issuerKeyHash;
  ASN1OctetString issuerNameHash;
  ASN1Integer serialNumber;
  
  private CertID(ASN1Sequence paramASN1Sequence)
  {
    this.hashAlgorithm = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(0));
    this.issuerNameHash = ((ASN1OctetString)paramASN1Sequence.getObjectAt(1));
    this.issuerKeyHash = ((ASN1OctetString)paramASN1Sequence.getObjectAt(2));
    this.serialNumber = ((ASN1Integer)paramASN1Sequence.getObjectAt(3));
  }
  
  public CertID(AlgorithmIdentifier paramAlgorithmIdentifier, ASN1OctetString paramASN1OctetString1, ASN1OctetString paramASN1OctetString2, ASN1Integer paramASN1Integer)
  {
    this.hashAlgorithm = paramAlgorithmIdentifier;
    this.issuerNameHash = paramASN1OctetString1;
    this.issuerKeyHash = paramASN1OctetString2;
    this.serialNumber = paramASN1Integer;
  }
  
  public static CertID getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertID)) {
      return (CertID)paramObject;
    }
    if (paramObject != null) {
      return new CertID(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static CertID getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public AlgorithmIdentifier getHashAlgorithm()
  {
    return this.hashAlgorithm;
  }
  
  public ASN1OctetString getIssuerKeyHash()
  {
    return this.issuerKeyHash;
  }
  
  public ASN1OctetString getIssuerNameHash()
  {
    return this.issuerNameHash;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.hashAlgorithm);
    localASN1EncodableVector.add(this.issuerNameHash);
    localASN1EncodableVector.add(this.issuerKeyHash);
    localASN1EncodableVector.add(this.serialNumber);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ocsp.CertID
 * JD-Core Version:    0.7.0.1
 */