package org.spongycastle.asn1.crmf;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERSequence;
import org.spongycastle.asn1.x509.GeneralName;

public class CertId
  extends ASN1Object
{
  private GeneralName issuer;
  private ASN1Integer serialNumber;
  
  private CertId(ASN1Sequence paramASN1Sequence)
  {
    this.issuer = GeneralName.getInstance(paramASN1Sequence.getObjectAt(0));
    this.serialNumber = ASN1Integer.getInstance(paramASN1Sequence.getObjectAt(1));
  }
  
  public CertId(GeneralName paramGeneralName, BigInteger paramBigInteger)
  {
    this(paramGeneralName, new ASN1Integer(paramBigInteger));
  }
  
  public CertId(GeneralName paramGeneralName, ASN1Integer paramASN1Integer)
  {
    this.issuer = paramGeneralName;
    this.serialNumber = paramASN1Integer;
  }
  
  public static CertId getInstance(Object paramObject)
  {
    if ((paramObject instanceof CertId)) {
      return (CertId)paramObject;
    }
    if (paramObject != null) {
      return new CertId(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public static CertId getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(ASN1Sequence.getInstance(paramASN1TaggedObject, paramBoolean));
  }
  
  public GeneralName getIssuer()
  {
    return this.issuer;
  }
  
  public ASN1Integer getSerialNumber()
  {
    return this.serialNumber;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.issuer);
    localASN1EncodableVector.add(this.serialNumber);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.crmf.CertId
 * JD-Core Version:    0.7.0.1
 */