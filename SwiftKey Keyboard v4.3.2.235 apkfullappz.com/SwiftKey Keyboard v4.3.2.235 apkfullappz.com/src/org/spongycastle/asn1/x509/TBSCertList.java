package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.DERUTCTime;
import org.spongycastle.asn1.x500.X500Name;

public class TBSCertList
  extends ASN1Object
{
  Extensions crlExtensions;
  X500Name issuer;
  Time nextUpdate;
  ASN1Sequence revokedCertificates;
  AlgorithmIdentifier signature;
  Time thisUpdate;
  ASN1Integer version;
  
  public TBSCertList(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() < 3) || (paramASN1Sequence.size() > 7)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    int i;
    int m;
    int n;
    if ((paramASN1Sequence.getObjectAt(0) instanceof ASN1Integer))
    {
      i = 0 + 1;
      this.version = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(0));
      int j = i + 1;
      this.signature = AlgorithmIdentifier.getInstance(paramASN1Sequence.getObjectAt(i));
      int k = j + 1;
      this.issuer = X500Name.getInstance(paramASN1Sequence.getObjectAt(j));
      m = k + 1;
      this.thisUpdate = Time.getInstance(paramASN1Sequence.getObjectAt(k));
      if ((m >= paramASN1Sequence.size()) || ((!(paramASN1Sequence.getObjectAt(m) instanceof DERUTCTime)) && (!(paramASN1Sequence.getObjectAt(m) instanceof DERGeneralizedTime)) && (!(paramASN1Sequence.getObjectAt(m) instanceof Time)))) {
        break label287;
      }
      n = m + 1;
      this.nextUpdate = Time.getInstance(paramASN1Sequence.getObjectAt(m));
    }
    for (;;)
    {
      if ((n < paramASN1Sequence.size()) && (!(paramASN1Sequence.getObjectAt(n) instanceof DERTaggedObject)))
      {
        int i1 = n + 1;
        this.revokedCertificates = ASN1Sequence.getInstance(paramASN1Sequence.getObjectAt(n));
        n = i1;
      }
      if ((n < paramASN1Sequence.size()) && ((paramASN1Sequence.getObjectAt(n) instanceof DERTaggedObject))) {
        this.crlExtensions = Extensions.getInstance(ASN1Sequence.getInstance((ASN1TaggedObject)paramASN1Sequence.getObjectAt(n), true));
      }
      return;
      this.version = null;
      i = 0;
      break;
      label287:
      n = m;
    }
  }
  
  public static TBSCertList getInstance(Object paramObject)
  {
    if ((paramObject instanceof TBSCertList)) {
      return (TBSCertList)paramObject;
    }
    if (paramObject != null) {
      return new TBSCertList(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static TBSCertList getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public Extensions getExtensions()
  {
    return this.crlExtensions;
  }
  
  public X500Name getIssuer()
  {
    return this.issuer;
  }
  
  public Time getNextUpdate()
  {
    return this.nextUpdate;
  }
  
  public Enumeration getRevokedCertificateEnumeration()
  {
    if (this.revokedCertificates == null) {
      return new EmptyEnumeration(null);
    }
    return new RevokedCertificatesEnumeration(this.revokedCertificates.getObjects());
  }
  
  public CRLEntry[] getRevokedCertificates()
  {
    CRLEntry[] arrayOfCRLEntry;
    if (this.revokedCertificates == null) {
      arrayOfCRLEntry = new CRLEntry[0];
    }
    for (;;)
    {
      return arrayOfCRLEntry;
      arrayOfCRLEntry = new CRLEntry[this.revokedCertificates.size()];
      for (int i = 0; i < arrayOfCRLEntry.length; i++) {
        arrayOfCRLEntry[i] = CRLEntry.getInstance(this.revokedCertificates.getObjectAt(i));
      }
    }
  }
  
  public AlgorithmIdentifier getSignature()
  {
    return this.signature;
  }
  
  public Time getThisUpdate()
  {
    return this.thisUpdate;
  }
  
  public ASN1Integer getVersion()
  {
    return this.version;
  }
  
  public int getVersionNumber()
  {
    if (this.version == null) {
      return 1;
    }
    return 1 + this.version.getValue().intValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    if (this.version != null) {
      localASN1EncodableVector.add(this.version);
    }
    localASN1EncodableVector.add(this.signature);
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.thisUpdate);
    if (this.nextUpdate != null) {
      localASN1EncodableVector.add(this.nextUpdate);
    }
    if (this.revokedCertificates != null) {
      localASN1EncodableVector.add(this.revokedCertificates);
    }
    if (this.crlExtensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(0, this.crlExtensions));
    }
    return new DERSequence(localASN1EncodableVector);
  }
  
  public static class CRLEntry
    extends ASN1Object
  {
    Extensions crlEntryExtensions;
    ASN1Sequence seq;
    
    private CRLEntry(ASN1Sequence paramASN1Sequence)
    {
      if ((paramASN1Sequence.size() < 2) || (paramASN1Sequence.size() > 3)) {
        throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
      }
      this.seq = paramASN1Sequence;
    }
    
    public static CRLEntry getInstance(Object paramObject)
    {
      if ((paramObject instanceof CRLEntry)) {
        return (CRLEntry)paramObject;
      }
      if (paramObject != null) {
        return new CRLEntry(ASN1Sequence.getInstance(paramObject));
      }
      return null;
    }
    
    public Extensions getExtensions()
    {
      if ((this.crlEntryExtensions == null) && (this.seq.size() == 3)) {
        this.crlEntryExtensions = Extensions.getInstance(this.seq.getObjectAt(2));
      }
      return this.crlEntryExtensions;
    }
    
    public Time getRevocationDate()
    {
      return Time.getInstance(this.seq.getObjectAt(1));
    }
    
    public ASN1Integer getUserCertificate()
    {
      return ASN1Integer.getInstance(this.seq.getObjectAt(0));
    }
    
    public boolean hasExtensions()
    {
      return this.seq.size() == 3;
    }
    
    public ASN1Primitive toASN1Primitive()
    {
      return this.seq;
    }
  }
  
  private class EmptyEnumeration
    implements Enumeration
  {
    private EmptyEnumeration() {}
    
    public boolean hasMoreElements()
    {
      return false;
    }
    
    public Object nextElement()
    {
      return null;
    }
  }
  
  private class RevokedCertificatesEnumeration
    implements Enumeration
  {
    private final Enumeration en;
    
    RevokedCertificatesEnumeration(Enumeration paramEnumeration)
    {
      this.en = paramEnumeration;
    }
    
    public boolean hasMoreElements()
    {
      return this.en.hasMoreElements();
    }
    
    public Object nextElement()
    {
      return TBSCertList.CRLEntry.getInstance(this.en.nextElement());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.TBSCertList
 * JD-Core Version:    0.7.0.1
 */