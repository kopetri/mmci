package org.spongycastle.asn1;

import java.io.IOException;
import java.io.OutputStream;

public class ASN1OutputStream
{
  private OutputStream os;
  
  public ASN1OutputStream(OutputStream paramOutputStream)
  {
    this.os = paramOutputStream;
  }
  
  public void close()
    throws IOException
  {
    this.os.close();
  }
  
  public void flush()
    throws IOException
  {
    this.os.flush();
  }
  
  ASN1OutputStream getDERSubStream()
  {
    return new DEROutputStream(this.os);
  }
  
  ASN1OutputStream getDLSubStream()
  {
    return new DLOutputStream(this.os);
  }
  
  void write(int paramInt)
    throws IOException
  {
    this.os.write(paramInt);
  }
  
  void write(byte[] paramArrayOfByte)
    throws IOException
  {
    this.os.write(paramArrayOfByte);
  }
  
  void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException
  {
    this.os.write(paramArrayOfByte, paramInt1, paramInt2);
  }
  
  void writeEncoded(int paramInt1, int paramInt2, byte[] paramArrayOfByte)
    throws IOException
  {
    writeTag(paramInt1, paramInt2);
    writeLength(paramArrayOfByte.length);
    write(paramArrayOfByte);
  }
  
  void writeEncoded(int paramInt, byte[] paramArrayOfByte)
    throws IOException
  {
    write(paramInt);
    writeLength(paramArrayOfByte.length);
    write(paramArrayOfByte);
  }
  
  void writeImplicitObject(ASN1Primitive paramASN1Primitive)
    throws IOException
  {
    if (paramASN1Primitive != null)
    {
      paramASN1Primitive.encode(new ImplicitOutputStream(this.os));
      return;
    }
    throw new IOException("null object detected");
  }
  
  void writeLength(int paramInt)
    throws IOException
  {
    if (paramInt > 127)
    {
      int i = 1;
      int j = paramInt;
      for (;;)
      {
        j >>>= 8;
        if (j == 0) {
          break;
        }
        i++;
      }
      write((byte)(i | 0x80));
      for (int k = 8 * (i - 1); k >= 0; k -= 8) {
        write((byte)(paramInt >> k));
      }
    }
    write((byte)paramInt);
  }
  
  protected void writeNull()
    throws IOException
  {
    this.os.write(5);
    this.os.write(0);
  }
  
  public void writeObject(ASN1Encodable paramASN1Encodable)
    throws IOException
  {
    if (paramASN1Encodable != null)
    {
      paramASN1Encodable.toASN1Primitive().encode(this);
      return;
    }
    throw new IOException("null object detected");
  }
  
  void writeTag(int paramInt1, int paramInt2)
    throws IOException
  {
    if (paramInt2 < 31)
    {
      write(paramInt1 | paramInt2);
      return;
    }
    write(paramInt1 | 0x1F);
    if (paramInt2 < 128)
    {
      write(paramInt2);
      return;
    }
    byte[] arrayOfByte = new byte[5];
    int i = -1 + arrayOfByte.length;
    arrayOfByte[i] = ((byte)(paramInt2 & 0x7F));
    do
    {
      paramInt2 >>= 7;
      i--;
      arrayOfByte[i] = ((byte)(0x80 | paramInt2 & 0x7F));
    } while (paramInt2 > 127);
    write(arrayOfByte, i, arrayOfByte.length - i);
  }
  
  private class ImplicitOutputStream
    extends ASN1OutputStream
  {
    private boolean first = true;
    
    public ImplicitOutputStream(OutputStream paramOutputStream)
    {
      super();
    }
    
    public void write(int paramInt)
      throws IOException
    {
      if (this.first)
      {
        this.first = false;
        return;
      }
      super.write(paramInt);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.asn1.ASN1OutputStream
 * JD-Core Version:    0.7.0.1
 */