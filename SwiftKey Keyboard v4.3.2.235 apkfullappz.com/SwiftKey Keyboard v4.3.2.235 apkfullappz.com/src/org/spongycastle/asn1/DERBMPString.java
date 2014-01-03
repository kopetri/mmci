package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;

public class DERBMPString
  extends ASN1Primitive
  implements ASN1String
{
  private char[] string;
  
  public DERBMPString(String paramString)
  {
    this.string = paramString.toCharArray();
  }
  
  DERBMPString(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length / 2];
    for (int i = 0; i != arrayOfChar.length; i++) {
      arrayOfChar[i] = ((char)(paramArrayOfByte[(i * 2)] << 8 | 0xFF & paramArrayOfByte[(1 + i * 2)]));
    }
    this.string = arrayOfChar;
  }
  
  DERBMPString(char[] paramArrayOfChar)
  {
    this.string = paramArrayOfChar;
  }
  
  public static DERBMPString getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERBMPString))) {
      return (DERBMPString)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERBMPString getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERBMPString))) {
      return getInstance(localASN1Primitive);
    }
    return new DERBMPString(ASN1OctetString.getInstance(localASN1Primitive).getOctets());
  }
  
  protected boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERBMPString)) {
      return false;
    }
    DERBMPString localDERBMPString = (DERBMPString)paramASN1Primitive;
    return Arrays.areEqual(this.string, localDERBMPString.string);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.write(30);
    paramASN1OutputStream.writeLength(2 * this.string.length);
    for (int i = 0; i != this.string.length; i++)
    {
      int j = this.string[i];
      paramASN1OutputStream.write((byte)(j >> 8));
      paramASN1OutputStream.write((byte)j);
    }
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(2 * this.string.length) + 2 * this.string.length;
  }
  
  public String getString()
  {
    return new String(this.string);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.string);
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  public String toString()
  {
    return getString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERBMPString
 * JD-Core Version:    0.7.0.1
 */