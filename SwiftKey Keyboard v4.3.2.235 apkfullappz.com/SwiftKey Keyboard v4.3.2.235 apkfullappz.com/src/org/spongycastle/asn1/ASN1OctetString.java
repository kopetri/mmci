package org.spongycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.encoders.Hex;

public abstract class ASN1OctetString
  extends ASN1Primitive
  implements ASN1OctetStringParser
{
  byte[] string;
  
  public ASN1OctetString(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte == null) {
      throw new NullPointerException("string cannot be null");
    }
    this.string = paramArrayOfByte;
  }
  
  public static ASN1OctetString getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1OctetString))) {
      return (ASN1OctetString)paramObject;
    }
    if ((paramObject instanceof byte[])) {
      try
      {
        ASN1OctetString localASN1OctetString = getInstance(ASN1Primitive.fromByteArray((byte[])paramObject));
        return localASN1OctetString;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct OCTET STRING from byte[]: " + localIOException.getMessage());
      }
    }
    if ((paramObject instanceof ASN1Encodable))
    {
      ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
      if ((localASN1Primitive instanceof ASN1OctetString)) {
        return (ASN1OctetString)localASN1Primitive;
      }
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1OctetString getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof ASN1OctetString))) {
      return getInstance(localASN1Primitive);
    }
    return BEROctetString.fromSequence(ASN1Sequence.getInstance(localASN1Primitive));
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof ASN1OctetString)) {
      return false;
    }
    ASN1OctetString localASN1OctetString = (ASN1OctetString)paramASN1Primitive;
    return Arrays.areEqual(this.string, localASN1OctetString.string);
  }
  
  abstract void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException;
  
  public ASN1Primitive getLoadedObject()
  {
    return toASN1Primitive();
  }
  
  public InputStream getOctetStream()
  {
    return new ByteArrayInputStream(this.string);
  }
  
  public byte[] getOctets()
  {
    return this.string;
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(getOctets());
  }
  
  public ASN1OctetStringParser parser()
  {
    return this;
  }
  
  ASN1Primitive toDERObject()
  {
    return new DEROctetString(this.string);
  }
  
  ASN1Primitive toDLObject()
  {
    return new DEROctetString(this.string);
  }
  
  public String toString()
  {
    return "#" + new String(Hex.encode(this.string));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1OctetString
 * JD-Core Version:    0.7.0.1
 */