package org.spongycastle.asn1;

import java.io.IOException;

public abstract class ASN1Primitive
  extends ASN1Object
{
  public static ASN1Primitive fromByteArray(byte[] paramArrayOfByte)
    throws IOException
  {
    ASN1InputStream localASN1InputStream = new ASN1InputStream(paramArrayOfByte);
    try
    {
      ASN1Primitive localASN1Primitive = localASN1InputStream.readObject();
      return localASN1Primitive;
    }
    catch (ClassCastException localClassCastException)
    {
      throw new IOException("cannot recognise object in stream");
    }
  }
  
  abstract boolean asn1Equals(ASN1Primitive paramASN1Primitive);
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  abstract int encodedLength()
    throws IOException;
  
  public final boolean equals(Object paramObject)
  {
    if (this == paramObject) {}
    while (((paramObject instanceof ASN1Encodable)) && (asn1Equals(((ASN1Encodable)paramObject).toASN1Primitive()))) {
      return true;
    }
    return false;
  }
  
  public abstract int hashCode();
  
  abstract boolean isConstructed();
  
  public ASN1Primitive toASN1Primitive()
  {
    return this;
  }
  
  ASN1Primitive toDERObject()
  {
    return this;
  }
  
  ASN1Primitive toDLObject()
  {
    return this;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Primitive
 * JD-Core Version:    0.7.0.1
 */