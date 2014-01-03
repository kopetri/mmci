package org.spongycastle.asn1.eac;

import java.math.BigInteger;
import org.spongycastle.asn1.ASN1Object;
import org.spongycastle.asn1.ASN1OctetString;
import org.spongycastle.asn1.ASN1Primitive;
import org.spongycastle.asn1.ASN1TaggedObject;
import org.spongycastle.asn1.DEROctetString;
import org.spongycastle.asn1.DERTaggedObject;

public class UnsignedInteger
  extends ASN1Object
{
  private int tagNo;
  private BigInteger value;
  
  public UnsignedInteger(int paramInt, BigInteger paramBigInteger)
  {
    this.tagNo = paramInt;
    this.value = paramBigInteger;
  }
  
  private UnsignedInteger(ASN1TaggedObject paramASN1TaggedObject)
  {
    this.tagNo = paramASN1TaggedObject.getTagNo();
    this.value = new BigInteger(1, ASN1OctetString.getInstance(paramASN1TaggedObject, false).getOctets());
  }
  
  private byte[] convertValue()
  {
    byte[] arrayOfByte1 = this.value.toByteArray();
    if (arrayOfByte1[0] == 0)
    {
      byte[] arrayOfByte2 = new byte[-1 + arrayOfByte1.length];
      System.arraycopy(arrayOfByte1, 1, arrayOfByte2, 0, arrayOfByte2.length);
      return arrayOfByte2;
    }
    return arrayOfByte1;
  }
  
  public static UnsignedInteger getInstance(Object paramObject)
  {
    if ((paramObject instanceof UnsignedInteger)) {
      return (UnsignedInteger)paramObject;
    }
    if (paramObject != null) {
      return new UnsignedInteger(ASN1TaggedObject.getInstance(paramObject));
    }
    return null;
  }
  
  public int getTagNo()
  {
    return this.tagNo;
  }
  
  public BigInteger getValue()
  {
    return this.value;
  }
  
  public ASN1Primitive toASN1Primitive()
  {
    return new DERTaggedObject(false, this.tagNo, new DEROctetString(convertValue()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.eac.UnsignedInteger
 * JD-Core Version:    0.7.0.1
 */