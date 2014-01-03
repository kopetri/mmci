package org.spongycastle.asn1.x509;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;

public class CRLNumber
  extends ASN1Object
{
  private BigInteger number;
  
  public CRLNumber(BigInteger paramBigInteger)
  {
    this.number = paramBigInteger;
  }
  
  public static CRLNumber getInstance(Object paramObject)
  {
    if ((paramObject instanceof CRLNumber)) {
      return (CRLNumber)paramObject;
    }
    if (paramObject != null) {
      return new CRLNumber(ASN1Integer.getInstance(paramObject).getValue());
    }
    return null;
  }
  
  public BigInteger getCRLNumber()
  {
    return this.number;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new ASN1Integer(this.number);
  }
  
  public String toString()
  {
    return "CRLNumber: " + getCRLNumber();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.CRLNumber
 * JD-Core Version:    0.7.0.1
 */