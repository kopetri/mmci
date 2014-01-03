package org.spongycastle.asn1.eac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;
import org.spongycastle.util.Arrays;

public class PackedDate
{
  private byte[] time;
  
  public PackedDate(String paramString)
  {
    this.time = convert(paramString);
  }
  
  public PackedDate(Date paramDate)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMdd'Z'");
    localSimpleDateFormat.setTimeZone(new SimpleTimeZone(0, "Z"));
    this.time = convert(localSimpleDateFormat.format(paramDate));
  }
  
  PackedDate(byte[] paramArrayOfByte)
  {
    this.time = paramArrayOfByte;
  }
  
  private byte[] convert(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    byte[] arrayOfByte = new byte[6];
    for (int i = 0; i != 6; i++) {
      arrayOfByte[i] = ((byte)('￐' + arrayOfChar[i]));
    }
    return arrayOfByte;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PackedDate)) {
      return false;
    }
    PackedDate localPackedDate = (PackedDate)paramObject;
    return Arrays.areEqual(this.time, localPackedDate.time);
  }
  
  public Date getDate()
    throws ParseException
  {
    return new SimpleDateFormat("yyyyMMdd").parse("20" + toString());
  }
  
  public byte[] getEncoding()
  {
    return this.time;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.time);
  }
  
  public String toString()
  {
    char[] arrayOfChar = new char[this.time.length];
    for (int i = 0; i != arrayOfChar.length; i++) {
      arrayOfChar[i] = ((char)(48 + (0xFF & this.time[i])));
    }
    return new String(arrayOfChar);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.PackedDate
 * JD-Core Version:    0.7.0.1
 */