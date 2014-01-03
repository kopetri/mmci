package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Strings;

public class DERT61String
  extends ASN1Primitive
  implements ASN1String
{
  private byte[] string;
  
  public DERT61String(String paramString)
  {
    this.string = Strings.toByteArray(paramString);
  }
  
  DERT61String(byte[] paramArrayOfByte)
  {
    this.string = paramArrayOfByte;
  }
  
  public static DERT61String getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERT61String))) {
      return (DERT61String)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERT61String getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERT61String))) {
      return getInstance(localASN1Primitive);
    }
    return new DERT61String(ASN1OctetString.getInstance(localASN1Primitive).getOctets());
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERT61String)) {
      return false;
    }
    return Arrays.areEqual(this.string, ((DERT61String)paramASN1Primitive).string);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(20, this.string);
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
 * Qualified Name:     org.spongycastle.asn1.DERT61String
 * JD-Core Version:    0.7.0.1
 */