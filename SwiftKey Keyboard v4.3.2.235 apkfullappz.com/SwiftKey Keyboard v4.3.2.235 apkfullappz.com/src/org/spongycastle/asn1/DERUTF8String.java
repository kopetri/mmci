package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERUTF8String
  extends ASN1Primitive
  implements ASN1String
{
  private byte[] string;
  
  public DERUTF8String(String paramString)
  {
    this.string = Strings.toUTF8ByteArray(paramString);
  }
  
  DERUTF8String(byte[] paramArrayOfByte)
  {
    this.string = paramArrayOfByte;
  }
  
  public static DERUTF8String getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERUTF8String))) {
      return (DERUTF8String)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERUTF8String getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERUTF8String))) {
      return getInstance(localASN1Primitive);
    }
    return new DERUTF8String(ASN1OctetString.getInstance(localASN1Primitive).getOctets());
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERUTF8String)) {
      return false;
    }
    DERUTF8String localDERUTF8String = (DERUTF8String)paramASN1Primitive;
    return Arrays.areEqual(this.string, localDERUTF8String.string);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(12, this.string);
  }
  
  int encodedLength()
    throws IOException
  {
    return 1 + StreamUtil.calculateBodyLength(this.string.length) + this.string.length;
  }
  
  public String getString()
  {
    return Strings.fromUTF8ByteArray(this.string);
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
 * Qualified Name:     org.spongycastle.asn1.DERUTF8String
 * JD-Core Version:    0.7.0.1
 */