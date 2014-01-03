package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class ASN1Object
  implements ASN1Encodable
{
  protected static boolean hasEncodedTagValue(Object paramObject, int paramInt)
  {
    boolean bool1 = paramObject instanceof byte[];
    boolean bool2 = false;
    if (bool1)
    {
      int i = ((byte[])(byte[])paramObject)[0];
      bool2 = false;
      if (i == paramInt) {
        bool2 = true;
      }
    }
    return bool2;
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ASN1Encodable)) {
      return false;
    }
    ASN1Encodable localASN1Encodable = (ASN1Encodable)paramObject;
    return toASN1Primitive().equals(localASN1Encodable.toASN1Primitive());
  }
  
  public byte[] getEncoded()
    throws IOException
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    new ASN1OutputStream(localByteArrayOutputStream).writeObject(this);
    return localByteArrayOutputStream.toByteArray();
  }
  
  public byte[] getEncoded(String paramString)
    throws IOException
  {
    if (paramString.equals("DER"))
    {
      ByteArrayOutputStream localByteArrayOutputStream1 = new ByteArrayOutputStream();
      new DEROutputStream(localByteArrayOutputStream1).writeObject(this);
      return localByteArrayOutputStream1.toByteArray();
    }
    if (paramString.equals("DL"))
    {
      ByteArrayOutputStream localByteArrayOutputStream2 = new ByteArrayOutputStream();
      new DLOutputStream(localByteArrayOutputStream2).writeObject(this);
      return localByteArrayOutputStream2.toByteArray();
    }
    return getEncoded();
  }
  
  public int hashCode()
  {
    return toASN1Primitive().hashCode();
  }
  
  public ASN1Primitive toASN1Object()
  {
    return toASN1Primitive();
  }
  
  public abstract ASN1Primitive toASN1Primitive();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Object
 * JD-Core Version:    0.7.0.1
 */