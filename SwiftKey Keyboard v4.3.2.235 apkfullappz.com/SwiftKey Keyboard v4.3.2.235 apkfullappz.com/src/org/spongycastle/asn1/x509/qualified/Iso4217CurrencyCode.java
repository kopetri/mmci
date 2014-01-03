package org.spongycastle.asn1.x509.qualified;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Encodable;
import org.spongycastle.asn1.ASN1Integer;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.DERPrintableString;

public class Iso4217CurrencyCode
  extends ASN1Object
  implements ASN1Choice
{
  final int ALPHABETIC_MAXSIZE = 3;
  final int NUMERIC_MAXSIZE = 999;
  final int NUMERIC_MINSIZE = 1;
  int numeric;
  ASN1Encodable obj;
  
  public Iso4217CurrencyCode(int paramInt)
  {
    if ((paramInt > 999) || (paramInt <= 0)) {
      throw new IllegalArgumentException("wrong size in numeric code : not in (1..999)");
    }
    this.obj = new ASN1Integer(paramInt);
  }
  
  public Iso4217CurrencyCode(String paramString)
  {
    if (paramString.length() > 3) {
      throw new IllegalArgumentException("wrong size in alphabetic code : max size is 3");
    }
    this.obj = new DERPrintableString(paramString);
  }
  
  public static Iso4217CurrencyCode getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof Iso4217CurrencyCode))) {
      return (Iso4217CurrencyCode)paramObject;
    }
    if ((paramObject instanceof ASN1Integer)) {
      return new Iso4217CurrencyCode(ASN1Integer.getInstance(paramObject).getValue().intValue());
    }
    if ((paramObject instanceof DERPrintableString)) {
      return new Iso4217CurrencyCode(DERPrintableString.getInstance(paramObject).getString());
    }
    throw new IllegalArgumentException("unknown object in getInstance");
  }
  
  public String getAlphabetic()
  {
    return ((DERPrintableString)this.obj).getString();
  }
  
  public int getNumeric()
  {
    return ((ASN1Integer)this.obj).getValue().intValue();
  }
  
  public boolean isAlphabetic()
  {
    return this.obj instanceof DERPrintableString;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.obj.toASN1Primitive();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.qualified.Iso4217CurrencyCode
 * JD-Core Version:    0.7.0.1
 */