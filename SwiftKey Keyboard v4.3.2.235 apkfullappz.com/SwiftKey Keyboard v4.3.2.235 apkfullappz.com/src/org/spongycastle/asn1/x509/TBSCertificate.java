package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.x500.X500Name;

public class TBSCertificate
  extends ASN1Object
{
  Time endDate;
  Extensions extensions;
  X500Name issuer;
  DERBitString issuerUniqueId;
  ASN1Sequence seq;
  ASN1Integer serialNumber;
  AlgorithmIdentifier signature;
  Time startDate;
  X500Name subject;
  SubjectPublicKeyInfo subjectPublicKeyInfo;
  DERBitString subjectUniqueId;
  ASN1Integer version;
  
  private TBSCertificate(ASN1Sequence paramASN1Sequence)
  {
    int i = 0;
    this.seq = paramASN1Sequence;
    int j;
    label157:
    DERTaggedObject localDERTaggedObject;
    if ((paramASN1Sequence.getObjectAt(0) instanceof DERTaggedObject))
    {
      this.version = ASN1Integer.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(0), true);
      this.serialNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(i + 1));
      this.signature = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(i + 2));
      this.issuer = X500Name.getInstance(paramASN1Sequence.getObjectAt(i + 3));
      ASN1Sequence localASN1Sequence = (ASN1Sequence)paramASN1Sequence.getObjectAt(i + 4);
      this.startDate = Time.getInstance(localASN1Sequence.getObjectAt(0));
      this.endDate = Time.getInstance(localASN1Sequence.getObjectAt(1));
      this.subject = X500Name.getInstance(paramASN1Sequence.getObjectAt(i + 5));
      this.subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(paramASN1Sequence.getObjectAt(i + 6));
      j = -1 + (paramASN1Sequence.size() - (i + 6));
      if (j <= 0) {
        return;
      }
      localDERTaggedObject = (DERTaggedObject)paramASN1Sequence.getObjectAt(j + (i + 6));
      switch (localDERTaggedObject.getTagNo())
      {
      }
    }
    for (;;)
    {
      j--;
      break label157;
      i = -1;
      this.version = new ASN1Integer(0);
      break;
      this.issuerUniqueId = DERBitString.getInstance(localDERTaggedObject, false);
      continue;
      this.subjectUniqueId = DERBitString.getInstance(localDERTaggedObject, false);
      continue;
      this.extensions = Extensions.getInstance(ASN1Sequence.getInstance(localDERTaggedObject, true));
    }
  }
  
  public static TBSCertificate getInstance(Object paramObject)
  {
    if ((paramObject instanceof TBSCertificate)) {
      return (TBSCertificate)paramObject;
    }
    if (paramObject != null) {
      return new TBSCertificate(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static TBSCertificate getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public Time getEndDate()
  {
    return this.endDate;
  }
  
  public Extensions getExtensions()
  {
    return this.extensions;
  }
  
  public X500Name getIssuer()
  {
    return this.issuer;
  }
  
  public DERBitString getIssuerUniqueId()
  {
    return this.issuerUniqueId;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public AlgorithmIdentifier getSignature()
  {
    return this.signature;
  }
  
  public Time getStartDate()
  {
    return this.startDate;
  }
  
  public X500Name getSubject()
  {
    return this.subject;
  }
  
  public SubjectPublicKeyInfo getSubjectPublicKeyInfo()
  {
    return this.subjectPublicKeyInfo;
  }
  
  public DERBitString getSubjectUniqueId()
  {
    return this.subjectUniqueId;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public int getVersionNumber()
  {
    return 1 + this.version.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.seq;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.TBSCertificate
 * JD-Core Version:    0.7.0.1
 */