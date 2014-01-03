package org.spongycastle.asn1;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import java.util.TimeZone;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERGeneralizedTime
  extends ASN1Primitive
{
  private byte[] time;
  
  public DERGeneralizedTime(String paramString)
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
  
  public DERGeneralizedTime(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'");
    localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
    this.time = Strings.toByteArray(localSimpleDateFormat.format(paramDate));
  }
  
  DERGeneralizedTime(byte[] paramArrayOfByte)
  {
    this.time = paramArrayOfByte;
  }
  
  private String calculateGMTOffset()
  {
    String str = "+";
    TimeZone localTimeZone = TimeZone.getDefault();
    int i = localTimeZone.getRawOffset();
    if (i < 0)
    {
      str = "-";
      i = -i;
    }
    int j = i / 3600000;
    int k = (i - 1000 * (60 * (j * 60))) / 60000;
    for (;;)
    {
      try
      {
        if ((localTimeZone.useDaylightTime()) && (localTimeZone.inDaylightTime(getDate())))
        {
          boolean bool = str.equals("+");
          if (!bool) {
            continue;
          }
          m = 1;
          j += m;
        }
      }
      catch (ParseException localParseException)
      {
        int m;
        continue;
      }
      return "GMT" + str + convert(j) + ":" + convert(k);
      m = -1;
    }
  }
  
  private String convert(int paramInt)
  {
    if (paramInt < 10) {
      return "0" + paramInt;
    }
    return Integer.toString(paramInt);
  }
  
  public static ASN1GeneralizedTime getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1GeneralizedTime))) {
      return (ASN1GeneralizedTime)paramObject;
    }
    if ((paramObject instanceof DERGeneralizedTime)) {
      return new ASN1GeneralizedTime(((DERGeneralizedTime)paramObject).time);
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1GeneralizedTime getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERGeneralizedTime))) {
      return getInstance(localASN1Primitive);
    }
    return new ASN1GeneralizedTime(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  private boolean hasFractionalSeconds()
  {
    for (int i = 0; i != this.time.length; i++) {
      if ((this.time[i] == 46) && (i == 14)) {
        return true;
      }
    }
    return false;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERGeneralizedTime)) {
      return false;
    }
    return Arrays.areEqual(this.time, ((DERGeneralizedTime)paramASN1Primitive).time);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(24, this.time);
  }
  
  int encodedLength()
  {
    int i = this.time.length;
    return i + (1 + StreamUtil.calculateBodyLength(i));
  }
  
  public Date getDate()
    throws ParseException
  {
    String str1 = Strings.fromByteArray(this.time);
    String str2 = str1;
    String str3;
    int i;
    if (str1.endsWith("Z"))
    {
      if (hasFractionalSeconds()) {}
      for (localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS'Z'");; localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss'Z'"))
      {
        localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        if (!hasFractionalSeconds()) {
          break label307;
        }
        str3 = str2.substring(14);
        for (i = 1; i < str3.length(); i++)
        {
          int j = str3.charAt(i);
          if ((48 > j) || (j > 57)) {
            break;
          }
        }
      }
    }
    if ((str1.indexOf('-') > 0) || (str1.indexOf('+') > 0))
    {
      str2 = getTime();
      if (hasFractionalSeconds()) {}
      for (localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSSz");; localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssz"))
      {
        localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
        break;
      }
    }
    if (hasFractionalSeconds()) {}
    for (SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss.SSS");; localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss"))
    {
      localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, TimeZone.getDefault().getID()));
      break;
    }
    if (i - 1 > 3)
    {
      String str6 = str3.substring(0, 4) + str3.substring(i);
      str2 = str2.substring(0, 14) + str6;
    }
    for (;;)
    {
      label307:
      return localSimpleDateFormat.parse(str2);
      if (i - 1 == 1)
      {
        String str5 = str3.substring(0, i) + "00" + str3.substring(i);
        str2 = str2.substring(0, 14) + str5;
      }
      else if (i - 1 == 2)
      {
        String str4 = str3.substring(0, i) + "0" + str3.substring(i);
        str2 = str2.substring(0, 14) + str4;
      }
    }
  }
  
  public String getTime()
  {
    String str = Strings.fromByteArray(this.time);
    if (str.charAt(-1 + str.length()) == 'Z') {
      return str.substring(0, -1 + str.length()) + "GMT+00:00";
    }
    int i = -5 + str.length();
    int j = str.charAt(i);
    if ((j == 45) || (j == 43)) {
      return str.substring(0, i) + "GMT" + str.substring(i, i + 3) + ":" + str.substring(i + 3);
    }
    int k = -3 + str.length();
    int m = str.charAt(k);
    if ((m == 45) || (m == 43)) {
      return str.substring(0, k) + "GMT" + str.substring(k) + ":00";
    }
    return str + calculateGMTOffset();
  }
  
  public String getTimeString()
  {
    return Strings.fromByteArray(this.time);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.time);
  }
  
  boolean isConstructed()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERGeneralizedTime
 * JD-Core Version:    0.7.0.1
 */