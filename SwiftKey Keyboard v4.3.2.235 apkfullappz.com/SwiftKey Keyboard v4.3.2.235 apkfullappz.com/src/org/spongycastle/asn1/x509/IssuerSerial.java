package org.spongycastle.asn1.x509;

import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERBitString;
import org.spongycastle.asn1.DERSequence;

public class IssuerSerial
  extends ASN1Object
{
  GeneralNames issuer;
  DERBitString issuerUID;
  ASN1Integer serial;
  
  public IssuerSerial(ASN1Sequence paramASN1Sequence)
  {
    if ((paramASN1Sequence.size() != 2) && (paramASN1Sequence.size() != 3)) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    this.issuer = GeneralNames.getInstance(paramASN1Sequence.getObjectAt(0));
    this.serial = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
    if (paramASN1Sequence.size() == 3) {
      this.issuerUID = DERBitString.getInstance(paramASN1Sequence.getObjectAt(2));
    }
  }
  
  public IssuerSerial(GeneralNames paramGeneralNames, ASN1Integer paramASN1Integer)
  {
    this.issuer = paramGeneralNames;
    this.serial = paramASN1Integer;
  }
  
  public static IssuerSerial getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof IssuerSerial))) {
      return (IssuerSerial)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new IssuerSerial((ASN1Sequence)paramObject);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static IssuerSerial getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public GeneralNames getIssuer()
  {
    return this.issuer;
  }
  
  public DERBitString getIssuerUID()
  {
    return this.issuerUID;
  }
  
  public ASN1Integer getSerial()
  {
    return this.serial;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.serial);
    if (this.issuerUID != null) {
      localASN1EncodableVector.add(this.issuerUID);
    }
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.IssuerSerial
 * JD-Core Version:    0.7.0.1
 */