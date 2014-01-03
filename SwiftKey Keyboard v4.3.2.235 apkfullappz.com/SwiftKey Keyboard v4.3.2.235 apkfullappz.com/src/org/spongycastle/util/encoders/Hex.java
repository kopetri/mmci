package org.spongycastle.util.encoders;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Hex
{
  private static final Encoder encoder = new HexEncoder();
  
  public static int decode(String paramString, OutputStream paramOutputStream)
    throws IOException
  {
    return encoder.decode(paramString, paramOutputStream);
  }
  
  public static byte[] decode(String paramString)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      encoder.decode(paramString, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("exception decoding Hex string: " + localIOException);
    }
  }
  
  public static byte[] decode(byte[] paramArrayOfByte)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      encoder.decode(paramArrayOfByte, 0, paramArrayOfByte.length, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("exception decoding Hex string: " + localIOException);
    }
  }
  
  public static int encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, OutputStream paramOutputStream)
    throws IOException
  {
    return encoder.encode(paramArrayOfByte, paramInt1, paramInt2, paramOutputStream);
  }
  
  public static int encode(byte[] paramArrayOfByte, OutputStream paramOutputStream)
    throws IOException
  {
    return encoder.encode(paramArrayOfByte, 0, paramArrayOfByte.length, paramOutputStream);
  }
  
  public static byte[] encode(byte[] paramArrayOfByte)
  {
    return encode(paramArrayOfByte, 0, paramArrayOfByte.length);
  }
  
  public static byte[] encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      encoder.encode(paramArrayOfByte, paramInt1, paramInt2, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new RuntimeException("exception encoding Hex string: " + localIOException);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.encoders.Hex
 * JD-Core Version:    0.7.0.1
 */