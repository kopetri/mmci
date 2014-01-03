package org.spongycastle.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;

public final class Strings
{
  public static char[] asCharArray(byte[] paramArrayOfByte)
  {
    char[] arrayOfChar = new char[paramArrayOfByte.length];
    for (int i = 0; i != arrayOfChar.length; i++) {
      arrayOfChar[i] = ((char)(0xFF & paramArrayOfByte[i]));
    }
    return arrayOfChar;
  }
  
  public static String fromByteArray(byte[] paramArrayOfByte)
  {
    return new String(asCharArray(paramArrayOfByte));
  }
  
  public static String fromUTF8ByteArray(byte[] paramArrayOfByte)
  {
    int i = 0;
    int j = 0;
    while (i < paramArrayOfByte.length)
    {
      j++;
      if ((0xF0 & paramArrayOfByte[i]) == 240)
      {
        j++;
        i += 4;
      }
      else if ((0xE0 & paramArrayOfByte[i]) == 224)
      {
        i += 3;
      }
      else if ((0xC0 & paramArrayOfByte[i]) == 192)
      {
        i += 2;
      }
      else
      {
        i++;
      }
    }
    char[] arrayOfChar = new char[j];
    int k = 0;
    int m = 0;
    if (k < paramArrayOfByte.length)
    {
      int n;
      if ((0xF0 & paramArrayOfByte[k]) == 240)
      {
        int i2 = ((0x3 & paramArrayOfByte[k]) << 18 | (0x3F & paramArrayOfByte[(k + 1)]) << 12 | (0x3F & paramArrayOfByte[(k + 2)]) << 6 | 0x3F & paramArrayOfByte[(k + 3)]) - 65536;
        int i3 = (char)(0xD800 | i2 >> 10);
        int i4 = (char)(0xDC00 | i2 & 0x3FF);
        int i5 = m + 1;
        arrayOfChar[m] = i3;
        n = i4;
        k += 4;
        m = i5;
      }
      for (;;)
      {
        int i1 = m + 1;
        arrayOfChar[m] = n;
        m = i1;
        break;
        if ((0xE0 & paramArrayOfByte[k]) == 224)
        {
          n = (char)((0xF & paramArrayOfByte[k]) << 12 | (0x3F & paramArrayOfByte[(k + 1)]) << 6 | 0x3F & paramArrayOfByte[(k + 2)]);
          k += 3;
        }
        else if ((0xD0 & paramArrayOfByte[k]) == 208)
        {
          n = (char)((0x1F & paramArrayOfByte[k]) << 6 | 0x3F & paramArrayOfByte[(k + 1)]);
          k += 2;
        }
        else if ((0xC0 & paramArrayOfByte[k]) == 192)
        {
          n = (char)((0x1F & paramArrayOfByte[k]) << 6 | 0x3F & paramArrayOfByte[(k + 1)]);
          k += 2;
        }
        else
        {
          n = (char)(0xFF & paramArrayOfByte[k]);
          k++;
        }
      }
    }
    return new String(arrayOfChar);
  }
  
  public static String[] split(String paramString, char paramChar)
  {
    Vector localVector = new Vector();
    int i = 1;
    while (i != 0)
    {
      int k = paramString.indexOf(paramChar);
      if (k > 0)
      {
        localVector.addElement(paramString.substring(0, k));
        paramString = paramString.substring(k + 1);
      }
      else
      {
        localVector.addElement(paramString);
        i = 0;
      }
    }
    String[] arrayOfString = new String[localVector.size()];
    for (int j = 0; j != arrayOfString.length; j++) {
      arrayOfString[j] = ((String)localVector.elementAt(j));
    }
    return arrayOfString;
  }
  
  public static byte[] toByteArray(String paramString)
  {
    byte[] arrayOfByte = new byte[paramString.length()];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)paramString.charAt(i));
    }
    return arrayOfByte;
  }
  
  public static byte[] toByteArray(char[] paramArrayOfChar)
  {
    byte[] arrayOfByte = new byte[paramArrayOfChar.length];
    for (int i = 0; i != arrayOfByte.length; i++) {
      arrayOfByte[i] = ((byte)paramArrayOfChar[i]);
    }
    return arrayOfByte;
  }
  
  public static String toLowerCase(String paramString)
  {
    int i = 0;
    char[] arrayOfChar = paramString.toCharArray();
    for (int j = 0; j != arrayOfChar.length; j++)
    {
      int k = arrayOfChar[j];
      if ((65 <= k) && (90 >= k))
      {
        i = 1;
        arrayOfChar[j] = ((char)(97 + (k - 65)));
      }
    }
    if (i != 0) {
      paramString = new String(arrayOfChar);
    }
    return paramString;
  }
  
  public static void toUTF8ByteArray(char[] paramArrayOfChar, OutputStream paramOutputStream)
    throws IOException
  {
    int i = 0;
    if (i < paramArrayOfChar.length)
    {
      int j = paramArrayOfChar[i];
      if (j < 128) {
        paramOutputStream.write(j);
      }
      for (;;)
      {
        i++;
        break;
        if (j < 2048)
        {
          paramOutputStream.write(0xC0 | j >> 6);
          paramOutputStream.write(0x80 | j & 0x3F);
        }
        else if ((j >= 55296) && (j <= 57343))
        {
          if (i + 1 >= paramArrayOfChar.length) {
            throw new IllegalStateException("invalid UTF-16 codepoint");
          }
          i++;
          int k = paramArrayOfChar[i];
          if (j > 56319) {
            throw new IllegalStateException("invalid UTF-16 codepoint");
          }
          int m = 65536 + ((j & 0x3FF) << 10 | k & 0x3FF);
          paramOutputStream.write(0xF0 | m >> 18);
          paramOutputStream.write(0x80 | 0x3F & m >> 12);
          paramOutputStream.write(0x80 | 0x3F & m >> 6);
          paramOutputStream.write(0x80 | m & 0x3F);
        }
        else
        {
          paramOutputStream.write(0xE0 | j >> 12);
          paramOutputStream.write(0x80 | 0x3F & j >> 6);
          paramOutputStream.write(0x80 | j & 0x3F);
        }
      }
    }
  }
  
  public static byte[] toUTF8ByteArray(String paramString)
  {
    return toUTF8ByteArray(paramString.toCharArray());
  }
  
  public static byte[] toUTF8ByteArray(char[] paramArrayOfChar)
  {
    ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
    try
    {
      toUTF8ByteArray(paramArrayOfChar, localByteArrayOutputStream);
      return localByteArrayOutputStream.toByteArray();
    }
    catch (IOException localIOException)
    {
      throw new IllegalStateException("cannot encode string to byte array!");
    }
  }
  
  public static String toUpperCase(String paramString)
  {
    int i = 0;
    char[] arrayOfChar = paramString.toCharArray();
    for (int j = 0; j != arrayOfChar.length; j++)
    {
      int k = arrayOfChar[j];
      if ((97 <= k) && (122 >= k))
      {
        i = 1;
        arrayOfChar[j] = ((char)(65 + (k - 97)));
      }
    }
    if (i != 0) {
      paramString = new String(arrayOfChar);
    }
    return paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.Strings
 * JD-Core Version:    0.7.0.1
 */