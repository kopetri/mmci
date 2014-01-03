package org.spongycastle.asn1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.spongycastle.util.Arrays;

public class DERApplicationSpecific
  extends ASN1Primitive
{
  private final boolean isConstructed;
  private final byte[] octets;
  private final int tag;
  
  public DERApplicationSpecific(int paramInt, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    this(true, paramInt, paramASN1Encodable);
  }
  
  public DERApplicationSpecific(int paramInt, ASN1EncodableVector paramASN1EncodableVector)
  {
    this.tag = paramInt;
    this.isConstructed = true;
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    int i = 0;
    while (i != paramASN1EncodableVector.size()) {
      try
      {
        localByteArrayOutputStream.write(((ASN1Object)paramASN1EncodableVector.get(i)).getEncoded("DER"));
        i++;
      }
      catch (IOException localIOException)
      {
        throw new ASN1ParsingException("malformed object: " + localIOException, localIOException);
      }
    }
    this.octets = localByteArrayOutputStream.toByteArray();
  }
  
  public DERApplicationSpecific(int paramInt, byte[] paramArrayOfByte)
  {
    this(false, paramInt, paramArrayOfByte);
  }
  
  public DERApplicationSpecific(boolean paramBoolean, int paramInt, ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    ASN1Primitive localASN1Primitive = paramASN1Encodable.toASN1Primitive();
    byte[] arrayOfByte1 = localASN1Primitive.getEncoded("DER");
    if ((paramBoolean) || ((localASN1Primitive instanceof ASN1Set)) || ((localASN1Primitive instanceof ASN1Sequence))) {}
    for (boolean bool = true;; bool = false)
    {
      this.isConstructed = bool;
      this.tag = paramInt;
      if (!paramBoolean) {
        break;
      }
      this.octets = arrayOfByte1;
      return;
    }
    int i = getLengthOfHeader(arrayOfByte1);
    byte[] arrayOfByte2 = new byte[arrayOfByte1.length - i];
    System.arraycopy(arrayOfByte1, i, arrayOfByte2, 0, arrayOfByte2.length);
    this.octets = arrayOfByte2;
  }
  
  DERApplicationSpecific(boolean paramBoolean, int paramInt, byte[] paramArrayOfByte)
  {
    this.isConstructed = paramBoolean;
    this.tag = paramInt;
    this.octets = paramArrayOfByte;
  }
  
  public static DERApplicationSpecific getInstance(Object paramObject)
  {
    if ((paramObject == null) || ((paramObject instanceof DERApplicationSpecific))) {
      return (DERApplicationSpecific)paramObject;
    }
    if ((paramObject instanceof byte[])) {
      try
      {
        DERApplicationSpecific localDERApplicationSpecific = getInstance(ASN1Primitive.fromByteArray((byte[])paramObject));
        return localDERApplicationSpecific;
      }
      catch (IOException localIOException)
      {
        throw new IllegalArgumentException("failed to construct object from byte[]: " + localIOException.getMessage());
      }
    }
    if ((paramObject instanceof ASN1Encodable))
    {
      ASN1Primitive localASN1Primitive = ((ASN1Encodable)paramObject).toASN1Primitive();
      if ((localASN1Primitive instanceof ASN1Sequence)) {
        return (DERApplicationSpecific)localASN1Primitive;
      }
    }
    throw new IllegalArgumentException("unknown object in getInstance: " + paramObject.getClass().getName());
  }
  
  private int getLengthOfHeader(byte[] paramArrayOfByte)
  {
    int i = 0xFF & paramArrayOfByte[1];
    if (i == 128) {}
    while (i <= 127) {
      return 2;
    }
    int j = i & 0x7F;
    if (j > 4) {
      throw new IllegalStateException("DER length more than 4 bytes: " + j);
    }
    return j + 2;
  }
  
  private byte[] replaceTagNumber(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    int i = 0x1F & paramArrayOfByte[0];
    int j = 1;
    int k;
    int m;
    if (i == 31)
    {
      k = j + 1;
      m = 0xFF & paramArrayOfByte[1];
      if ((m & 0x7F) != 0) {
        break label124;
      }
      throw new ASN1ParsingException("corrupted stream - invalid high tag number found");
    }
    for (;;)
    {
      int n;
      if ((m >= 0) && ((m & 0x80) != 0))
      {
        int i1 = n + 1;
        m = 0xFF & paramArrayOfByte[n];
        n = i1;
      }
      else
      {
        j = n;
        byte[] arrayOfByte = new byte[1 + (paramArrayOfByte.length - j)];
        System.arraycopy(paramArrayOfByte, j, arrayOfByte, 1, -1 + arrayOfByte.length);
        arrayOfByte[0] = ((byte)paramInt);
        return arrayOfByte;
        label124:
        n = k;
      }
    }
  }
  
  boolean asn1Equals(ASN1Primitive paramASN1Primitive)
  {
    if (!(paramASN1Primitive instanceof DERApplicationSpecific)) {}
    DERApplicationSpecific localDERApplicationSpecific;
    do
    {
      return false;
      localDERApplicationSpecific = (DERApplicationSpecific)paramASN1Primitive;
    } while ((this.isConstructed != localDERApplicationSpecific.isConstructed) || (this.tag != localDERApplicationSpecific.tag) || (!Arrays.areEqual(this.octets, localDERApplicationSpecific.octets)));
    return true;
  }
  
  void encode(ASN1OutputStream paramASN1OutputStream)
    throws IOException
  {
    int i = 64;
    if (this.isConstructed) {
      i = 96;
    }
    paramASN1OutputStream.writeEncoded(i, this.tag, this.octets);
  }
  
  int encodedLength()
    throws IOException
  {
    return StreamUtil.calculateTagLength(this.tag) + StreamUtil.calculateBodyLength(this.octets.length) + this.octets.length;
  }
  
  public int getApplicationTag()
  {
    return this.tag;
  }
  
  public byte[] getContents()
  {
    return this.octets;
  }
  
  public ASN1Primitive getObject()
    throws IOException
  {
    return new ASN1InputStream(getContents()).readObject();
  }
  
  public ASN1Primitive getObject(int paramInt)
    throws IOException
  {
    if (paramInt >= 31) {
      throw new IOException("unsupported tag number");
    }
    byte[] arrayOfByte1 = getEncoded();
    byte[] arrayOfByte2 = replaceTagNumber(paramInt, arrayOfByte1);
    if ((0x20 & arrayOfByte1[0]) != 0) {
      arrayOfByte2[0] = ((byte)(0x20 | arrayOfByte2[0]));
    }
    return new ASN1InputStream(arrayOfByte2).readObject();
  }
  
  public int hashCode()
  {
    if (this.isConstructed) {}
    for (int i = 1;; i = 0) {
      return i ^ this.tag ^ Arrays.hashCode(this.octets);
    }
  }
  
  public boolean isConstructed()
  {
    return this.isConstructed;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.DERApplicationSpecific
 * JD-Core Version:    0.7.0.1
 */