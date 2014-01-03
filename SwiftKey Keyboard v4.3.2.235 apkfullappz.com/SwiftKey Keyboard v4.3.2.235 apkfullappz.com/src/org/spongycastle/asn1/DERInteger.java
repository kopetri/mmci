package org.spongycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class DERInteger
  extends ASN1Primitive
{
  byte[] bytes;
  
  public DERInteger(int paramInt)
  {
    this.bytes = BigInteger.valueOf(paramInt).toByteArray();
  }
  
  public DERInteger(BigInteger paramBigInteger)
  {
    this.bytes = paramBigInteger.toByteArray();
  }
  
  public DERInteger(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }
  
  public static ASN1Integer getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1Integer))) {
      return (ASN1Integer)paramObject;
    }
    if ((paramObject instanceof DERInteger)) {
      return new ASN1Integer(((DERInteger)paramObject).getValue());
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1Integer getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERInteger))) {
      return getInstance(localASN1Primitive);
    }
    return new ASN1Integer(ASN1OctetString.getInstance(paramASN1TaggedObject.getObject()).getOctets());
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERInteger)) {
      return false;
    }
    DERInteger localDERInteger = (DERInteger)paramASN1Primitive;
    return Arrays.areEqual(this.bytes, localDERInteger.bytes);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(2, this.bytes);
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(this.bytes.length) + this.bytes.length;
  }
  
  public BigInteger getPositiveValue()
  {
    return new BigInteger(1, this.bytes);
  }
  
  public BigInteger getValue()
  {
    return new BigInteger(this.bytes);
  }
  
  public int hashCode()
  {
    int i = 0;
    for (int j = 0; j != this.bytes.length; j++) {
      i ^= (0xFF & this.bytes[j]) << j % 4;
    }
    return i;
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  public String toString()
  {
    return getValue().toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERInteger
 * JD-Core Version:    0.7.0.1
 */