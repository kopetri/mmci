package org.spongycastle.asn1.x509;

import java.io.IOException;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERTaggedObject;
import org.spongycastle.asn1.DERUTCTime;
import org.spongycastle.asn1.x500.X500Name;

public class V2TBSCertListGenerator
{
  private static final ASN1Sequence[] reasons;
  private ASN1EncodableVector crlentries = new ASN1EncodableVector();
  private Extensions extensions = null;
  private X500Name issuer;
  private Time nextUpdate = null;
  private AlgorithmIdentifier signature;
  private Time thisUpdate;
  private ASN1Integer version = new ASN1Integer(1);
  
  static
  {
    ASN1Sequence[] arrayOfASN1Sequence = new ASN1Sequence[11];
    reasons = arrayOfASN1Sequence;
    arrayOfASN1Sequence[0] = createReasonExtension(0);
    reasons[1] = createReasonExtension(1);
    reasons[2] = createReasonExtension(2);
    reasons[3] = createReasonExtension(3);
    reasons[4] = createReasonExtension(4);
    reasons[5] = createReasonExtension(5);
    reasons[6] = createReasonExtension(6);
    reasons[7] = createReasonExtension(7);
    reasons[8] = createReasonExtension(8);
    reasons[9] = createReasonExtension(9);
    reasons[10] = createReasonExtension(10);
  }
  
  private static ASN1Sequence createInvalidityDateExtension(Time paramTime)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    try
    {
      localASN1EncodableVector.add(X509Extension.invalidityDate);
      localASN1EncodableVector.add(new DEROctetString(paramTime.getEncoded()));
      return new DERSequence(localASN1EncodableVector);
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("error encoding reason: " + localIOException);
    }
  }
  
  private static ASN1Sequence createReasonExtension(int paramInt)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    CRLReason localCRLReason = CRLReason.lookup(paramInt);
    try
    {
      localASN1EncodableVector.add(X509Extension.reasonCode);
      localASN1EncodableVector.add(new DEROctetString(localCRLReason.getEncoded()));
      return new DERSequence(localASN1EncodableVector);
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("error encoding reason: " + localIOException);
    }
  }
  
  private void internalAddCRLEntry(ASN1Integer paramASN1Integer, Time paramTime, ASN1Sequence paramASN1Sequence)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1Integer);
    localASN1EncodableVector.add(paramTime);
    if (paramASN1Sequence != null) {
      localASN1EncodableVector.add(paramASN1Sequence);
    }
    addCRLEntry(new DERSequence(localASN1EncodableVector));
  }
  
  public void addCRLEntry(ASN1Integer paramASN1Integer, DERUTCTime paramDERUTCTime, int paramInt)
  {
    addCRLEntry(paramASN1Integer, new Time(paramDERUTCTime), paramInt);
  }
  
  public void addCRLEntry(ASN1Integer paramASN1Integer, Time paramTime, int paramInt)
  {
    addCRLEntry(paramASN1Integer, paramTime, paramInt, null);
  }
  
  public void addCRLEntry(ASN1Integer paramASN1Integer, Time paramTime, int paramInt, DERGeneralizedTime paramDERGeneralizedTime)
  {
    if (paramInt != 0)
    {
      ASN1EncodableVector localASN1EncodableVector1 = new ASN1EncodableVector();
      if (paramInt < reasons.length)
      {
        if (paramInt < 0) {
          throw new IllegalArgumentException("invalid reason value: " + paramInt);
        }
        localASN1EncodableVector1.add(reasons[paramInt]);
      }
      for (;;)
      {
        if (paramDERGeneralizedTime != null) {
          localASN1EncodableVector1.add(createInvalidityDateExtension(paramTime));
        }
        internalAddCRLEntry(paramASN1Integer, paramTime, new DERSequence(localASN1EncodableVector1));
        return;
        localASN1EncodableVector1.add(createReasonExtension(paramInt));
      }
    }
    if (paramDERGeneralizedTime != null)
    {
      ASN1EncodableVector localASN1EncodableVector2 = new ASN1EncodableVector();
      localASN1EncodableVector2.add(createInvalidityDateExtension(paramTime));
      internalAddCRLEntry(paramASN1Integer, paramTime, new DERSequence(localASN1EncodableVector2));
      return;
    }
    addCRLEntry(paramASN1Integer, paramTime, null);
  }
  
  public void addCRLEntry(ASN1Integer paramASN1Integer, Time paramTime, Extensions paramExtensions)
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(paramASN1Integer);
    localASN1EncodableVector.add(paramTime);
    if (paramExtensions != null) {
      localASN1EncodableVector.add(paramExtensions);
    }
    addCRLEntry(new DERSequence(localASN1EncodableVector));
  }
  
  public void addCRLEntry(ASN1Sequence paramASN1Sequence)
  {
    this.crlentries.add(paramASN1Sequence);
  }
  
  public TBSCertList generateTBSCertList()
  {
    if ((this.signature == null) || (this.issuer == null) || (this.thisUpdate == null)) {
      throw new IllegalStateException("Not all mandatory fields set in V2 TBSCertList generator.");
    }
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.version);
    localASN1EncodableVector.add(this.signature);
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.thisUpdate);
    if (this.nextUpdate != null) {
      localASN1EncodableVector.add(this.nextUpdate);
    }
    if (this.crlentries.size() != 0) {
      localASN1EncodableVector.add(new DERSequence(this.crlentries));
    }
    if (this.extensions != null) {
      localASN1EncodableVector.add(new DERTaggedObject(0, this.extensions));
    }
    return new TBSCertList(new DERSequence(localASN1EncodableVector));
  }
  
  public void setExtensions(Extensions paramExtensions)
  {
    this.extensions = paramExtensions;
  }
  
  public void setExtensions(X509Extensions paramX509Extensions)
  {
    setExtensions(Extensions.getInstance(paramX509Extensions));
  }
  
  public void setIssuer(X500Name paramX500Name)
  {
    this.issuer = paramX500Name;
  }
  
  public void setIssuer(X509Name paramX509Name)
  {
    this.issuer = X500Name.getInstance(paramX509Name.toASN1Primitive());
  }
  
  public void setNextUpdate(DERUTCTime paramDERUTCTime)
  {
    this.nextUpdate = new Time(paramDERUTCTime);
  }
  
  public void setNextUpdate(Time paramTime)
  {
    this.nextUpdate = paramTime;
  }
  
  public void setSignature(AlgorithmIdentifier paramAlgorithmIdentifier)
  {
    this.signature = paramAlgorithmIdentifier;
  }
  
  public void setThisUpdate(DERUTCTime paramDERUTCTime)
  {
    this.thisUpdate = new Time(paramDERUTCTime);
  }
  
  public void setThisUpdate(Time paramTime)
  {
    this.thisUpdate = paramTime;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.V2TBSCertListGenerator
 * JD-Core Version:    0.7.0.1
 */