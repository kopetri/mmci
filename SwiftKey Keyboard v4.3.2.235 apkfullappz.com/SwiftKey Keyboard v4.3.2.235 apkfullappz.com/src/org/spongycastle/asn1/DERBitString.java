package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.io.Streams;

public class DERBitString
  extends ASN1Primitive
  implements ASN1String
{
  private static final char[] table = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70 };
  protected byte[] data;
  protected int padBits;
  
  protected DERBitString(byte paramByte, int paramInt)
  {
    this.data = new byte[1];
    this.data[0] = paramByte;
    this.padBits = paramInt;
  }
  
  public DERBitString(ASN1Encodable paramASN1Encodable)
  {
    try
    {
      this.data = paramASN1Encodable.toASN1Primitive().getEncoded("DER");
      this.padBits = 0;
      return;
    }
    catch (IOException localIOException)
    {
      throw new IllegalArgumentException("Error processing object : " + localIOException.toString());
    }
  }
  
  public DERBitString(byte[] paramArrayOfByte)
  {
    this(paramArrayOfByte, 0);
  }
  
  public DERBitString(byte[] paramArrayOfByte, int paramInt)
  {
    this.data = paramArrayOfByte;
    this.padBits = paramInt;
  }
  
  static DERBitString fromInputStream(int paramInt, InputStream paramInputStream)
    throws IOException
  {
    if (paramInt <= 0) {
      throw new IllegalArgumentException("truncated BIT STRING detected");
    }
    int i = paramInputStream.read();
    byte[] arrayOfByte = new byte[paramInt - 1];
    if ((arrayOfByte.length != 0) && (Streams.readFully(paramInputStream, arrayOfByte) != arrayOfByte.length)) {
      throw new EOFException("EOF encountered in middle of BIT STRING");
    }
    return new DERBitString(arrayOfByte, i);
  }
  
  static DERBitString fromOctetString(byte[] paramArrayOfByte)
  {
    if (paramArrayOfByte.length <= 0) {
      throw new IllegalArgumentException("truncated BIT STRING detected");
    }
    int i = paramArrayOfByte[0];
    byte[] arrayOfByte = new byte[-1 + paramArrayOfByte.length];
    if (arrayOfByte.length != 0) {
      System.arraycopy(paramArrayOfByte, 1, arrayOfByte, 0, -1 + paramArrayOfByte.length);
    }
    return new DERBitString(arrayOfByte, i);
  }
  
  protected static byte[] getBytes(int paramInt)
  {
    int i = 4;
    for (int j = 3; (j > 0) && ((paramInt & 255 << j * 8) == 0); j--) {
      i--;
    }
    byte[] arrayOfByte = new byte[i];
    for (int k = 0; k < i; k++) {
      arrayOfByte[k] = ((byte)(0xFF & paramInt >> k * 8));
    }
    return arrayOfByte;
  }
  
  public static DERBitString getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERBitString))) {
      return (DERBitString)paramObject;
    }
    throw new IllegalArgumentException("illegal object in getInstance: " + paramObject.getClass().getName());
  }
  
  public static DERBitString getInstance(ASN1TaggedObject paramASN1TaggedObject, boolean paramBoolean)
  {
    ASN1Primitive localASN1Primitive = paramASN1TaggedObject.getObject();
    if ((paramBoolean) || ((localASN1Primitive instanceof DERBitString))) {
      return getInstance(localASN1Primitive);
    }
    return fromOctetString(((ASN1OctetString)localASN1Primitive).getOctets());
  }
  
  protected static int getPadBits(int paramInt)
  {
    int j;
    for (int i = 3;; i--)
    {
      j = 0;
      if (i >= 0)
      {
        if (i == 0) {
          break label39;
        }
        if (paramInt >> i * 8 == 0) {
          continue;
        }
      }
      for (j = 0xFF & paramInt >> i * 8;; j = paramInt & 0xFF)
      {
        if (j != 0) {
          break label58;
        }
        return 7;
        label39:
        if (paramInt == 0) {
          break;
        }
      }
    }
    label58:
    for (int k = 1;; k++)
    {
      j <<= 1;
      if ((j & 0xFF) == 0) {
        break;
      }
    }
    return 8 - k;
  }
  
  protected boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERBitString)) {}
    DERBitString localDERBitString;
    do
    {
      return false;
      localDERBitString = (DERBitString)paramASN1Primitive;
    } while ((this.padBits != localDERBitString.padBits) || (!Arrays.areEqual(this.data, localDERBitString.data)));
    return true;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    byte[] arrayOfByte = new byte[1 + getBytes().length];
    arrayOfByte[0] = ((byte)getPadBits());
    System.arraycopy(getBytes(), 0, arrayOfByte, 1, -1 + arrayOfByte.length);
    paramASN1OutputStream.writeEncoded(3, arrayOfByte);
  }
  
  int encodedLength()
  {
    return 1 + (1 + StreamUtil.calculateBodyLength(1 + this.data.length) + this.data.length);
  }
  
  public byte[] getBytes()
  {
    return this.data;
  }
  
  public int getPadBits()
  {
    return this.padBits;
  }
  
  public String getString()
  {
    localStringBuffer = new StringBuffer("#");
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    ASN1OutputStream localASN1OutputStream = new ASN1OutputStream(localByteArrayOutputStream);
    try
    {
      localASN1OutputStream.writeObject(this);
      byte[] arrayOfByte = localByteArrayOutputStream.toByteArray();
      for (int i = 0; i != arrayOfByte.length; i++)
      {
        localStringBuffer.append(table[(0xF & arrayOfByte[i] >>> 4)]);
        localStringBuffer.append(table[(0xF & arrayOfByte[i])]);
      }
      return localStringBuffer.toString();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("internal error encoding BitString");
    }
  }
  
  public int hashCode()
  {
    return this.padBits ^ Arrays.hashCode(this.data);
  }
  
  public int intValue()
  {
    int i = 0;
    for (int j = 0; (j != this.data.length) && (j != 4); j++) {
      i |= (0xFF & this.data[j]) << j * 8;
    }
    return i;
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
 * Qualified Name:     org.spongycastle.asn1.DERBitString
 * JD-Core Version:    0.7.0.1
 */