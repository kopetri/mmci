package org.spongycastle.asn1;

import java.io.IOException;
import org.spongycastle.util.Arrays;

public class DERBoolean
  extends ASN1Primitive
{
  public static final ASN1Boolean FALSE = new ASN1Boolean(false);
  private static final byte[] FALSE_VALUE;
  public static final ASN1Boolean TRUE = new ASN1Boolean(true);
  private static final byte[] TRUE_VALUE = { -1 };
  private byte[] value;
  
  static
  {
    FALSE_VALUE = new byte[] { 0 };
  }
  
  public DERBoolean(boolean paramBoolean)
  {
    if (paramBoolean) {}
    for (byte[] arrayOfByte = TRUE_VALUE;; arrayOfByte = FALSE_VALUE)
    {
      this.value = arrayOfByte;
      return;
    }
  }
  
  DERBoolean(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 1) {
      throw new IllegalArgumentException("byte value should have 1 byte in it");
    }
    if (paramArrayOfByte[0] == 0)
    {
      this.value = FALSE_VALUE;
      return;
    }
    if (paramArrayOfByte[0] == 255)
    {
      this.value = TRUE_VALUE;
      return;
    }
    this.value = Arrays.clone(paramArrayOfByte);
  }
  
  static ASN1Boolean fromOctetString(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length != 1) {
      throw new IllegalArgumentException("byte value should have 1 byte in it");
    }
    if (paramArrayOfByte[0] == 0) {
      return FALSE;
    }
    if (paramArrayOfByte[0] == 255) {
      return TRUE;
    }
    return new ASN1Boolean(paramArrayOfByte);
  }
  
  public static ASN1Boolean getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof ASN1Boolean))) {
      return (ASN1Boolean)paramObject;
    }
    if ((paramObject instanceof DERBoolean))
    {
      if (((DERBoolean)paramObject).isTrue()) {
        return TRUE;
      }
      return FALSE;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static ASN1Boolean getInstance(boolean paramBoolean)
  {
    if (paramBoolean) {
      return TRUE;
    }
    return FALSE;
  }
  
  public static DERBoolean getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERBoolean))) {
      return getInstance(localASN1Primitive);
    }
    return ASN1Boolean.fromOctetString(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  protected boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if ((paramASN1Primitive == null) || (!(paramASN1Primitive instanceof DERBoolean))) {}
    while (this.value[0] != ((DERBoolean)paramASN1Primitive).value[0]) {
      return false;
    }
    return true;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    paramASN1OutputStream.writeEncoded(1, this.value);
  }
  
  int encodedLength()
  {
    return 3;
  }
  
  public int hashCode()
  {
    return this.value[0];
  }
  
  boolean isConstructed()
  {
    return false;
  }
  
  public boolean isTrue()
  {
    int i = this.value[0];
    boolean bool = false;
    if (i != 0) {
      bool = true;
    }
    return bool;
  }
  
  public String toString()
  {
    if (this.value[0] != 0) {
      return "TRUE";
    }
    return "FALSE";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERBoolean
 * JD-Core Version:    0.7.0.1
 */