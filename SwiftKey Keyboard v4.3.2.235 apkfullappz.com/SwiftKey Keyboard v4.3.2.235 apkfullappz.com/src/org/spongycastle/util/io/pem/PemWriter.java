package org.spongycastle.util.io.pem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.List;
import org.spongycastle.util.encoders.Base64;

public class PemWriter
  extends BufferedWriter
{
  private static final int LINE_LENGTH = 64;
  private char[] buf = new char[64];
  private final int nlLength;
  
  public PemWriter(Writer paramWriter)
  {
    super(paramWriter);
    String str = System.getProperty("line.separator");
    if (str != null)
    {
      this.nlLength = str.length();
      return;
    }
    this.nlLength = 2;
  }
  
  private void writeEncoded(byte[] paramArrayOfByte)
    throws IOException
  {
    byte[] arrayOfByte = Base64.encode(paramArrayOfByte);
    int i = 0;
    while (i < arrayOfByte.length)
    {
      for (int j = 0; (j != this.buf.length) && (i + j < arrayOfByte.length); j++) {
        this.buf[j] = ((char)arrayOfByte[(i + j)]);
      }
      write(this.buf, 0, j);
      newLine();
      i += this.buf.length;
    }
  }
  
  private void writePostEncapsulationBoundary(String paramString)
    throws IOException
  {
    write("-----END " + paramString + "-----");
    newLine();
  }
  
  private void writePreEncapsulationBoundary(String paramString)
    throws IOException
  {
    write("-----BEGIN " + paramString + "-----");
    newLine();
  }
  
  public int getOutputSize(PemObject paramPemObject)
  {
    int i = 4 + (6 + 2 * (10 + paramPemObject.getType().length() + this.nlLength));
    if (!paramPemObject.getHeaders().isEmpty())
    {
      Iterator localIterator = paramPemObject.getHeaders().iterator();
      while (localIterator.hasNext())
      {
        PemHeader localPemHeader = (PemHeader)localIterator.next();
        i += 2 + localPemHeader.getName().length() + localPemHeader.getValue().length() + this.nlLength;
      }
      i += this.nlLength;
    }
    int j = 4 * ((2 + paramPemObject.getContent().length) / 3);
    return i + (j + (-1 + (j + 64)) / 64 * this.nlLength);
  }
  
  public void writeObject(PemObjectGenerator paramPemObjectGenerator)
    throws IOException
  {
    PemObject localPemObject = paramPemObjectGenerator.generate();
    writePreEncapsulationBoundary(localPemObject.getType());
    if (!localPemObject.getHeaders().isEmpty())
    {
      Iterator localIterator = localPemObject.getHeaders().iterator();
      while (localIterator.hasNext())
      {
        PemHeader localPemHeader = (PemHeader)localIterator.next();
        write(localPemHeader.getName());
        write(": ");
        write(localPemHeader.getValue());
        newLine();
      }
      newLine();
    }
    writeEncoded(localPemObject.getContent());
    writePostEncapsulationBoundary(localPemObject.getType());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.io.pem.PemWriter
 * JD-Core Version:    0.7.0.1
 */