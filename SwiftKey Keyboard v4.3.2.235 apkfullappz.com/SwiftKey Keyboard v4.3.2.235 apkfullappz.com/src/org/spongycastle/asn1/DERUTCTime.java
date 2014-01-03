package org.spongycastle.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERUTCTime
  extends ASN1Primitive
{
  private byte[] time;
  
  public DERUTCTime(String paramString)
  {
    this.time = Strings.toByteArray(paramString);
    try
    {
      getDate();
      return;
    }
    catch (ParseException localParseException)
    {
      throw new IllegalArgumentException("invalid date string: " + localParseException.getMessage());
    }
  }
  
  public DERUTCTime(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmmss'Z'");
    localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
    this.time = Strings.toByteArray(localSimpleDateFormat.format(paramDate));
  }
  
  DERUTCTime(byte[] paramArrayOfByte)
  {
    this.time = paramArrayOfByte;
  }
  
  public static ASN1UTCTime getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1UTCTime))) {
      return (ASN1UTCTime)paramObject;
    }
    if ((paramObject instanceof DERUTCTime)) {
      return new ASN1UTCTime(((DERUTCTime)paramObject).time);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1UTCTime getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof ASN1UTCTime))) {
      return getInstance(localASN1Primitive);
    }
    return new ASN1UTCTime(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERUTCTime)) {
      return false;
    }
    return Arrays.areEqual(this.time, ((DERUTCTime)paramASN1Primitive).time);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.write(23);
    int i = this.time.length;
    paramASN1OutputStream.writeLength(i);
    for (int j = 0; j != i; j++) {
      paramASN1OutputStream.write(this.time[j]);
    }
  }
  
  int encodedLength()
  {
    int i = this.time.length;
    return i + (1 + StreamUtil.calculateBodyLength(i));
  }
  
  public Date getAdjustedDate()
    throws ParseException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz");
    localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
    return localSimpleDateFormat.parse(getAdjustedTime());
  }
  
  public String getAdjustedTime()
  {
    String str = getTime();
    if (str.charAt(0) < '5') {
      return "20" + str;
    }
    return "19" + str;
  }
  
  public Date getDate()
    throws ParseException
  {
    return new SimpleDateFormat("yyMMddHHmmssz").parse(getTime());
  }
  
  public String getTime()
  {
    String str1 = Strings.fromByteArray(this.time);
    if ((str1.indexOf('-') < 0) && (str1.indexOf('+') < 0))
    {
      if (str1.length() == 11) {
        return str1.substring(0, 10) + "00GMT+00:00";
      }
      return str1.substring(0, 12) + "GMT+00:00";
    }
    int i = str1.indexOf('-');
    if (i < 0) {
      i = str1.indexOf('+');
    }
    String str2 = str1;
    if (i == -3 + str1.length()) {
      str2 = str2 + "00";
    }
    if (i == 10) {
      return str2.substring(0, 10) + "00GMT" + str2.substring(10, 13) + ":" + str2.substring(13, 15);
    }
    return str2.substring(0, 12) + "GMT" + str2.substring(12, 15) + ":" + str2.substring(15, 17);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.time);
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  public String toString()
  {
    return Strings.fromByteArray(this.time);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERUTCTime
 * JD-Core Version:    0.7.0.1
 */