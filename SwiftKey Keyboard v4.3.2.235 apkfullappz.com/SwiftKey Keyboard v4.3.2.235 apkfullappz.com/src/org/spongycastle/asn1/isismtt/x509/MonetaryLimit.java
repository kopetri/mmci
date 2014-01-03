package org.spongycastle.asn1.isismtt.x509;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERPrintableString;
import org.spongycastle.asn1.DERSequence;

public class MonetaryLimit
  extends ASN1Object
{
  ASN1Integer amount;
  DERPrintableString currency;
  ASN1Integer exponent;
  
  public MonetaryLimit(String paramString, int paramInt1, int paramInt2)
  {
    this.currency = new DERPrintableString(paramString, true);
    this.amount = new ASN1Integer(paramInt1);
    this.exponent = new ASN1Integer(paramInt2);
  }
  
  private MonetaryLimit(ASN1Sequence paramASN1Sequence)
  {
    if (paramASN1Sequence.size() != 3) {
      throw new IllegalArgumentException("Bad sequence size: " + paramASN1Sequence.size());
    }
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.currency = DERPrintableString.getInstance(localEnumeration.nextElement());
    this.amount = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.exponent = ASN1Integer.getInstance(localEnumeration.nextElement());
  }
  
  public static MonetaryLimit getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof MonetaryLimit))) {
      return (MonetaryLimit)paramObject;
    }
    if ((paramObject instanceof ASN1Sequence)) {
      return new MonetaryLimit(ASN1Sequence.getInstance(paramObject));
    }
    throw new IllegalArgumentException("unknown object in getInstance");
  }
  
  public BigInteger getAmount()
  {
    return this.amount.getValue();
  }
  
  public String getCurrency()
  {
    return this.currency.getString();
  }
  
  public BigInteger getExponent()
  {
    return this.exponent.getValue();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    ASN1EncodableVector localASN1EncodableVector = new ASN1EncodableVector();
    localASN1EncodableVector.add(this.currency);
    localASN1EncodableVector.add(this.amount);
    localASN1EncodableVector.add(this.exponent);
    return new DERSequence(localASN1EncodableVector);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.isismtt.x509.MonetaryLimit
 * JD-Core Version:    0.7.0.1
 */