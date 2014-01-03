package org.spongycastle.asn1.icao;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1Set;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERSet;
import org.spongycastle.asn1.x509.X509CertificateStructure;

public class CscaMasterList
  extends ASN1Object
{
  private X509CertificateStructure[] certList;
  private ASN1Integer version = new ASN1Integer(0);
  
  private CscaMasterList(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence == null) || (paramASN1Sequence.size() == 0)) {
      throw new IllegalArgumentException("null or empty sequence passed.");
    }
    if (paramASN1Sequence.size() != 2) {
      throw new IllegalArgumentException("Incorrect sequence size: " + paramASN1Sequence.size());
    }
    this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
    ASN1Set localASN1Set = ASN1Set.getInstance(paramASN1Sequence.getObjectAt(1));
    this.certList = new X509CertificateStructure[localASN1Set.size()];
    for (int i = 0; i < this.certList.length; i++) {
      this.certList[i] = X509CertificateStructure.getInstance(localASN1Set.getObjectAt(i));
    }
  }
  
  public CscaMasterList(X509CertificateStructure[] paramArrayOfX509CertificateStructure)
  {
    this.certList = copyCertList(paramArrayOfX509CertificateStructure);
  }
  
  private X509CertificateStructure[] copyCertList(X509CertificateStructure[] paramArrayOfX509CertificateStructure)
  {
    X509CertificateStructure[] arrayOfX509CertificateStructure = new X509CertificateStructure[paramArrayOfX509CertificateStructure.length];
    for (int i = 0; i != arrayOfX509CertificateStructure.length; i++) {
      arrayOfX509CertificateStructure[i] = paramArrayOfX509CertificateStructure[i];
    }
    return arrayOfX509CertificateStructure;
  }
  
  public static CscaMasterList getInstance(Object paramObject)
  {
    if ((paramObject instanceof CscaMasterList)) {
      return (CscaMasterList)paramObject;
    }
    if (paramObject != null) {
      return new CscaMasterList(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public X509CertificateStructure[] getCertStructs()
  {
    return copyCertList(this.certList);
  }
  
  public int getVersion()
  {
    return this.version.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
    localASN1EncodableVector1.add(this.version);
    ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
    for (int i = 0; i < this.certList.length; i++) {
      localASN1EncodableVector2.add(this.certList[i]);
    }
    localASN1EncodableVector1.add(new DERSet(localASN1EncodableVector2));
    return new DERSequence(localASN1EncodableVector1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.icao.CscaMasterList
 * JD-Core Version:    0.7.0.1
 */