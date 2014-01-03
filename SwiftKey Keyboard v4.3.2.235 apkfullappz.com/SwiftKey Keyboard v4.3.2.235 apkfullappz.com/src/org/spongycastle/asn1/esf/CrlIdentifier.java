package org.spongycastle.asn1.esf;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.DERUTCTime;
import org.spongycastle.asn1.x500.X500Name;

public class CrlIdentifier
  extends ASN1Object
{
  private DERUTCTime crlIssuedTime;
  private X500Name crlIssuer;
  private ASN1Integer crlNumber;
  
  private CrlIdentifier(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() < 2) || (paramASN1Sequence.size() > 3)) {
      throw new IllegalArgumentException();
    }
    this.crlIssuer = X500Name.getInstance(paramASN1Sequence.getObjectAt(0));
    this.crlIssuedTime = DERUTCTime.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() > 2) {
      this.crlNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public CrlIdentifier(X500Name paramX500Name, DERUTCTime paramDERUTCTime)
  {
    this(paramX500Name, paramDERUTCTime, null);
  }
  
  public CrlIdentifier(X500Name paramX500Name, DERUTCTime paramDERUTCTime, BigInteger paramBigInteger)
  {
    this.crlIssuer = paramX500Name;
    this.crlIssuedTime = paramDERUTCTime;
    if (paramBigInteger != null) {
      this.crlNumber = new ASN1Integer(paramBigInteger);
    }
  }
  
  public static CrlIdentifier getInstance(Object paramObject)
  {
    if ((paramObject instanceof CrlIdentifier)) {
      return (CrlIdentifier)paramObject;
    }
    if (paramObject != null) {
      return new CrlIdentifier(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public DERUTCTime getCrlIssuedTime()
  {
    return this.crlIssuedTime;
  }
  
  public X500Name getCrlIssuer()
  {
    return this.crlIssuer;
  }
  
  public BigInteger getCrlNumber()
  {
    if (this.crlNumber == null) {
      return null;
    }
    return this.crlNumber.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.crlIssuer.toASN1Primitive());
    localASN1EncodableVector.add(this.crlIssuedTime);
    if (this.crlNumber != null) {
      localASN1EncodableVector.add(this.crlNumber);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.esf.CrlIdentifier
 * JD-Core Version:    0.7.0.1
 */