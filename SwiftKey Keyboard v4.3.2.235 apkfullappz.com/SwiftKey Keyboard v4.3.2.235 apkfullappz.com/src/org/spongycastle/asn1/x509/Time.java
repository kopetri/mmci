package org.spongycastle.asn1.x509;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import org.spongycastle.asn1.ASN1Choice;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DERGeneralizedTime;
import org.spongycastle.asn1.DERUTCTime;

public class Time
  extends ASN1Object
  implements ASN1Choice
{
  ASN1Primitive time;
  
  public Time(Date paramDate)
  {
    SimpleTimeZone localSimpleTimeZone = new SimpleTimeZone(0, "Z");
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    localSimpleDateFormat.setTimeZone(localSimpleTimeZone);
    String str = localSimpleDateFormat.format(paramDate) + "Z";
    int i = Integer.parseInt(str.substring(0, 4));
    if ((i < 1950) || (i > 2049))
    {
      this.time = new DERGeneralizedTime(str);
      return;
    }
    this.time = new DERUTCTime(str.substring(2));
  }
  
  public Time(ASN1Primitive paramASN1Primitive)
  {
    if ((!(paramASN1Primitive instanceof DERUTCTime)) && (!(paramASN1Primitive instanceof DERGeneralizedTime))) {
      throw new IllegalArgumentException("unknown object passed to Time");
    }
    this.time = paramASN1Primitive;
  }
  
  public static Time getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof Time))) {
      return (Time)paramObject;
    }
    if ((paramObject instanceof DERUTCTime)) {
      return new Time((DERUTCTime)paramObject);
    }
    if ((paramObject instanceof DERGeneralizedTime)) {
      return new Time((DERGeneralizedTime)paramObject);
    }
    throw new IllegalArgumentException("unknown object in factory: " + paramObject.getClass().getName());
  }
  
  public static Time getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    return getInstance(paramASN1TaggedObject.getObject());
  }
  
  public Date getDate()
  {
    try
    {
      if ((this.time instanceof DERUTCTime)) {
        return ((DERUTCTime)this.time).getAdjustedDate();
      }
      Date localDate = ((DERGeneralizedTime)this.time).getDate();
      return localDate;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalStateException("invalid date string: " + localParseException.getMessage());
    }
  }
  
  public String getTime()
  {
    if ((this.time instanceof DERUTCTime)) {
      return ((DERUTCTime)this.time).getAdjustedTime();
    }
    return ((DERGeneralizedTime)this.time).getTime();
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return this.time;
  }
  
  public String toString()
  {
    return getTime();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.x509.Time
 * JD-Core Version:    0.7.0.1
 */