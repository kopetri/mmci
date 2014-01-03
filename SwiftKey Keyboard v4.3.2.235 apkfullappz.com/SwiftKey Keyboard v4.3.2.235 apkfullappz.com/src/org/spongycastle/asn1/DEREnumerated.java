package org.spongycastle.asn1;

import java.io.IOException;
import java.math.BigInteger;
import org.spongycastle.util.Arrays;

public class DEREnumerated
  extends ASN1Primitive
{
  private static ASN1Enumerated[] cache = new ASN1Enumerated[12];
  byte[] bytes;
  
  public DEREnumerated(int paramInt)
  {
    this.bytes = BigInteger.valueOf(paramInt).toByteArray();
  }
  
  public DEREnumerated(BigInteger paramBigInteger)
  {
    this.bytes = paramBigInteger.toByteArray();
  }
  
  public DEREnumerated(byte[] paramArrayOfByte)
  {
    this.bytes = paramArrayOfByte;
  }
  
  static ASN1Enumerated fromOctetString(byte[] paramArrayOfByte)
  {
    ASN1Enumerated localASN1Enumerated1;
    if (paramArrayOfByte.length > 1) {
      localASN1Enumerated1 = new ASN1Enumerated(Arrays.clone(paramArrayOfByte));
    }
    int i;
    do
    {
      return localASN1Enumerated1;
      if (paramArrayOfByte.length == 0) {
        throw new IllegalArgumentException("ENUMERATED has zero length");
      }
      i = 0xFF & paramArrayOfByte[0];
      if (i >= cache.length) {
        return new ASN1Enumerated(Arrays.clone(paramArrayOfByte));
      }
      localASN1Enumerated1 = cache[i];
    } while (localASN1Enumerated1 != null);
    ASN1Enumerated[] arrayOfASN1Enumerated = cache;
    ASN1Enumerated localASN1Enumerated2 = new ASN1Enumerated(Arrays.clone(paramArrayOfByte));
    arrayOfASN1Enumerated[i] = localASN1Enumerated2;
    return localASN1Enumerated2;
  }
  
  public static ASN1Enumerated getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1Enumerated))) {
      return (ASN1Enumerated)paramObject;
    }
    if ((paramObject instanceof DEREnumerated)) {
      return new ASN1Enumerated(((DEREnumerated)paramObject).getValue());
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DEREnumerated getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DEREnumerated))) {
      return getInstance(localASN1Primitive);
    }
    return fromOctetString(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DEREnumerated)) {
      return false;
    }
    DEREnumerated localDEREnumerated = (DEREnumerated)paramASN1Primitive;
    return Arrays.areEqual(this.bytes, localDEREnumerated.bytes);
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(10, this.bytes);
  }
  
  int encodedLength()
  {
    return 1 + StreamUtil.calculateBodyLength(this.bytes.length) + this.bytes.length;
  }
  
  public BigInteger getValue()
  {
    return new BigInteger(this.bytes);
  }
  
  public int hashCode()
  {
    return Arrays.hashCode(this.bytes);
  }
  
  boolean isConstructed()
  {
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DEREnumerated
 * JD-Core Version:    0.7.0.1
 */