package org.spongycastle.asn1.x509;

import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x500.X500Name;

public class CertificateList
  extends ASN1Object
{
  DERBitString sig;
  AlgorithmIdentifier sigAlgId;
  TBSCertList tbsCertList;
  
  public CertificateList(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() == 3)
    {
      this.tbsCertList = TBSCertList.getInstance(paramASN1Sequence.getObjectAt(0));
      this.sigAlgId = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(1));
      this.sig = DERBitString.getInstance(paramASN1Sequence.getObjectAt(2));
      return;
    }
    throw new IllegalArgumentException("sequence wrong size for CertificateList");
  }
  
  public static CertificateList getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertificateList)) {
      return (CertificateList)paramObject;
    }
    if (paramObject != null) {
      return new CertificateList(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static CertificateList getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public X500Name getIssuer()
  {
    return this.tbsCertList.getIssuer();
  }
  
  public Time getNextUpdate()
  {
    return this.tbsCertList.getNextUpdate();
  }
  
  public Enumeration getRevokedCertificateEnumeration()
  {
    return this.tbsCertList.getRevokedCertificateEnumeration();
  }
  
  public TBSCertList.CRLEntry[] getRevokedCertificates()
  {
    return this.tbsCertList.getRevokedCertificates();
  }
  
  public DERBitString getSignature()
  {
    return this.sig;
  }
  
  public AlgorithmIdentifier getSignatureAlgorithm()
  {
    return this.sigAlgId;
  }
  
  public TBSCertList getTBSCertList()
  {
    return this.tbsCertList;
  }
  
  public Time getThisUpdate()
  {
    return this.tbsCertList.getThisUpdate();
  }
  
  public int getVersionNumber()
  {
    return this.tbsCertList.getVersionNumber();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.tbsCertList);
    localASN1EncodableVector.add(this.sigAlgId);
    localASN1EncodableVector.add(this.sig);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.CertificateList
 * JD-Core Version:    0.7.0.1
 */