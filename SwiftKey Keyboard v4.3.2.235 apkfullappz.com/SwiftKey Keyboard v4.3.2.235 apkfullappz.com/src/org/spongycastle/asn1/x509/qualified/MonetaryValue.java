package org.spongycastle.asn1.x509.qualified;

import java.math.BigInteger;
import java.util.Enumeration;
import org.spongycastle.asn1.ASN1EncodableVector;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1Sequence;
import org.spongycastle.asn1.DERSequence;

public class MonetaryValue
  extends ASN1Object
{
  private ASN1Integer amount;
  private Iso4217CurrencyCode currency;
  private ASN1Integer exponent;
  
  private MonetaryValue(ASN1Sequence paramASN1Sequence)
  {
    Enumeration localEnumeration = paramASN1Sequence.getObjects();
    this.currency = Iso4217CurrencyCode.getInstance(localEnumeration.nextElement());
    this.amount = ASN1Integer.getInstance(localEnumeration.nextElement());
    this.exponent = ASN1Integer.getInstance(localEnumeration.nextElement());
  }
  
  public MonetaryValue(Iso4217CurrencyCode paramIso4217CurrencyCode, int paramInt1, int paramInt2)
  {
    this.currency = paramIso4217CurrencyCode;
    this.amount = new ASN1Integer(paramInt1);
    this.exponent = new ASN1Integer(paramInt2);
  }
  
  public static MonetaryValue getInstance(Object paramObject)
  {
    if ((paramObject instanceof MonetaryValue)) {
      return (MonetaryValue)paramObject;
    }
    if (paramObject != null) {
      return new MonetaryValue(ASN1Sequence.getInstance(paramObject));
    }
    return null;
  }
  
  public BigInteger getAmount()
  {
    return this.amount.getValue();
  }
  
  public Iso4217CurrencyCode getCurrency()
  {
    return this.currency;
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
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.MonetaryValue
 * JD-Core Version:    0.7.0.1
 */