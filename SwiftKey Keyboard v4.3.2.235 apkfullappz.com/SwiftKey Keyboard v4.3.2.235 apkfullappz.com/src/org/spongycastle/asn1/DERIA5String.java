package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERIA5String
  extends ASN1Primitive
  implements ASN1String
{
  private byte[] string;
  
  public DERIA5String(String paramString)
  {
    this(paramString, false);
  }
  
  public DERIA5String(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      throw new NullPointerException("string cannot be null");
    }
    if ((paramBoolean) && (!isIA5String(paramString))) {
      throw new IllegalArgumentException("string contains illegal characters");
    }
    this.string = Strings.toByteArray(paramString);
  }
  
  DERIA5String(byte[] paramArrayOfByte)
  {
    this.string = paramArrayOfByte;
  }
  
  public static DERIA5String getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERIA5String))) {
      return (DERIA5String)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERIA5String getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERIA5String))) {
      return getInstance(localASN1Primitive);
    }
    return new DERIA5String(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  public static boolean isIA5String(String paramString)
  {
    for (int i = -1 + paramString.length(); i >= 0; i--) {
      if (paramString.charAt(i) > '') {
        return false;
      }
    }
    return true;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERIA5String)) {
      return false;
    }
    DERIA5String localDERIA5String = (DERIA5String)paramASN1Primitive;
    return Arrays.areEqual(this.string, localDERIA5String.string);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(22, this.string);
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
 * Qualified Name:     org.spongycastle.asn1.DERIA5String
 * JD-Core Version:    0.7.0.1
 */