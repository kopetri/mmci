package com.touchtype_fluency.service.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StringUtil
{
  public static String digestToString(byte[] paramArrayOfByte)
  {
    StringWriter localStringWriter = new StringWriter();
    PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
    int i = paramArrayOfByte.length;
    for (int j = 0; j < i; j++)
    {
      byte b = paramArrayOfByte[j];
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = Byte.valueOf(b);
      localPrintWriter.format("%02x", arrayOfObject);
    }
    return localStringWriter.toString();
  }
  
  public static String extractParameterValue(String paramString1, String paramString2)
  {
    String str = "";
    int i = paramString1.indexOf(paramString2);
    if (i != -1)
    {
      int j = paramString1.indexOf('&', i);
      if (j == -1) {
        j = paramString1.length();
      }
      str = paramString1.substring(1 + (i + paramString2.length()), j);
    }
    return str;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.StringUtil
 * JD-Core Version:    0.7.0.1
 */