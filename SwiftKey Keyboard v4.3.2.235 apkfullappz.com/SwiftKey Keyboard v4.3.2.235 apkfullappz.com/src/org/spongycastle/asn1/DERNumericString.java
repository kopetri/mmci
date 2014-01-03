package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERNumericString
  extends ASN1Primitive
  implements ASN1String
{
  private byte[] string;
  
  public DERNumericString(String paramString)
  {
    this(paramString, false);
  }
  
  public DERNumericString(String paramString, boolean paramBoolean)
  {
    if ((paramBoolean) && (!isNumericString(paramString))) {
      throw new IllegalArgumentException("string contains illegal characters");
    }
    this.string = Strings.toByteArray(paramString);
  }
  
  DERNumericString(byte[] paramArrayOfByte)
  {
    this.string = paramArrayOfByte;
  }
  
  public static DERNumericString getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERNumericString))) {
      return (DERNumericString)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERNumericString getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERNumericString))) {
      return getInstance(localASN1Primitive);
    }
    return new DERNumericString(ASN1OctetString.getInstance(localASN1Primitive).getOctets());
  }
  
  public static boolean isNumericString(String paramString)
  {
    for (int i = -1 + paramString.length(); i >= 0; i--)
    {
      int j = paramString.charAt(i);
      if (j > 127) {}
      while (((48 > j) || (j > 57)) && (j != 32)) {
        return false;
      }
    }
    return true;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERNumericString)) {
      return false;
    }
    DERNumericString localDERNumericString = (DERNumericString)paramASN1Primitive;
    return Arrays.areEqual(this.string, localDERNumericString.string);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(18, this.string);
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
  }
  
  public byte[] getOctets()
  {
    return Arrays.clone(this.string);
  }
  
  public String getString()
  {
    return Strings.fromByteArray(this.string);
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
 * Qualified Name:     org.spongycastle.asn1.DERNumericString
 * JD-Core Version:    0.7.0.1
 */