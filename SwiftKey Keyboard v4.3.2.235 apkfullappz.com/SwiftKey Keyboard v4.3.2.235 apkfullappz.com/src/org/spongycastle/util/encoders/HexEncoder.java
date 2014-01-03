package org.spongycastle.util.encoders;

import java.io.IOException;
import java.io.OutputStream;

public class HexEncoder
  implements Encoder
{
  protected final byte[] decodingTable = new byte[''];
  protected final byte[] encodingTable = { 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102 };
  
  public HexEncoder()
  {
    initialiseDecodingTable();
  }
  
  private boolean ignore(char paramChar)
  {
    return (paramChar == '\n') || (paramChar == '\r') || (paramChar == '\t') || (paramChar == ' ');
  }
  
  public int decode(String paramString, OutputStream paramOutputStream)
    throws IOException
  {
    int i = 0;
    for (int j = paramString.length(); (j > 0) && (ignore(paramString.charAt(j - 1))); j--) {}
    int k = 0;
    while (k < j)
    {
      for (int m = k; (m < j) && (ignore(paramString.charAt(m))); m++) {}
      byte[] arrayOfByte1 = this.decodingTable;
      int n = m + 1;
      int i1 = arrayOfByte1[paramString.charAt(m)];
      for (int i2 = n; (i2 < j) && (ignore(paramString.charAt(i2))); i2++) {}
      byte[] arrayOfByte2 = this.decodingTable;
      k = i2 + 1;
      paramOutputStream.write(arrayOfByte2[paramString.charAt(i2)] | i1 << 4);
      i++;
    }
    return i;
  }
  
  public int decode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, OutputStream paramOutputStream)
    throws IOException
  {
    int i = 0;
    for (int j = paramInt1 + paramInt2; (j > paramInt1) && (ignore((char)paramArrayOfByte[(j - 1)])); j--) {}
    int k = paramInt1;
    while (k < j)
    {
      for (int m = k; (m < j) && (ignore((char)paramArrayOfByte[m])); m++) {}
      byte[] arrayOfByte1 = this.decodingTable;
      int n = m + 1;
      int i1 = arrayOfByte1[paramArrayOfByte[m]];
      for (int i2 = n; (i2 < j) && (ignore((char)paramArrayOfByte[i2])); i2++) {}
      byte[] arrayOfByte2 = this.decodingTable;
      k = i2 + 1;
      paramOutputStream.write(arrayOfByte2[paramArrayOfByte[i2]] | i1 << 4);
      i++;
    }
    return i;
  }
  
  public int encode(byte[] paramArrayOfByte, int paramInt1, int paramInt2, OutputStream paramOutputStream)
    throws IOException
  {
    for (int i = paramInt1; i < paramInt1 + paramInt2; i++)
    {
      int j = 0xFF & paramArrayOfByte[i];
      paramOutputStream.write(this.encodingTable[(j >>> 4)]);
      paramOutputStream.write(this.encodingTable[(j & 0xF)]);
    }
    return paramInt2 * 2;
  }
  
  protected void initialiseDecodingTable()
  {
    for (int i = 0; i < this.encodingTable.length; i++) {
      this.decodingTable[this.encodingTable[i]] = ((byte)i);
    }
    this.decodingTable[65] = this.decodingTable[97];
    this.decodingTable[66] = this.decodingTable[98];
    this.decodingTable[67] = this.decodingTable[99];
    this.decodingTable[68] = this.decodingTable[100];
    this.decodingTable[69] = this.decodingTable[101];
    this.decodingTable[70] = this.decodingTable[102];
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.encoders.HexEncoder
 * JD-Core Version:    0.7.0.1
 */