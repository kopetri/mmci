package org.spongycastle.asn1;

import java.io.IOException;

public abstract class ASN1Null
  extends ASN1Primitive
{
  public static ASN1Null getInstance(Object paramObject)
  {
    if ((paramObject instanceof ASN1Null)) {
      return (ASN1Null)paramObject;
    }
    if (paramObject != null) {
      try
      {
        ASN1Null localASN1Null = getInstance(ASN1Primitive.fromByteArray((byte[])paramObject));
        return localASN1Null;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct NULL from byte[]: " + localIOException.getMessage());
      }
      catch (ClassCastException localClassCastException)
      {
        throw new IllegalArgumentException("unknown object in getInstance(): " + paramObject.getClass().getName());
      }
    }
    return null;
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    return (paramASN1Primitive instanceof ASN1Null);
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public int hashCode()
  {
    return -1;
  }
  
  public String toString()
  {
    return "NULL";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1Null
 * JD-Core Version:    0.7.0.1
 */