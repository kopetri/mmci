package org.spongycastle.util.io.pem;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import org.spongycastle.util.encoders.Base64;

public class PemReader
  extends BufferedReader
{
  private static final String BEGIN = "-----BEGIN ";
  private static final String END = "-----END ";
  
  public PemReader(Reader paramReader)
  {
    super(paramReader);
  }
  
  private PemObject loadObject(String paramString)
    throws IOException
  {
    String str1 = "-----END " + paramString;
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    String str2;
    for (;;)
    {
      str2 = readLine();
      if (str2 == null) {
        break;
      }
      if (str2.indexOf(":") >= 0)
      {
        int i = str2.indexOf(':');
        localArrayList.add(new PemHeader(str2.substring(0, i), str2.substring(i + 1).trim()));
      }
      else
      {
        if (str2.indexOf(str1) != -1) {
          break;
        }
        localStringBuffer.append(str2.trim());
      }
    }
    if (str2 == null) {
      throw new IOException(str1 + " not found");
    }
    return new PemObject(paramString, localArrayList, Base64.decode(localStringBuffer.toString()));
  }
  
  public PemObject readPemObject()
    throws IOException
  {
    for (String str1 = readLine(); (str1 != null) && (!str1.startsWith("-----BEGIN ")); str1 = readLine()) {}
    if (str1 != null)
    {
      String str2 = str1.substring(11);
      int i = str2.indexOf('-');
      String str3 = str2.substring(0, i);
      if (i > 0) {
        return loadObject(str3);
      }
    }
    return null;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.io.pem.PemReader
 * JD-Core Version:    0.7.0.1
 */