package org.apache.commons.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;

public final class FilenameUtils
{
  public static final String EXTENSION_SEPARATOR_STR = new Character('.').toString();
  private static final char OTHER_SEPARATOR = '\\';
  private static final char SYSTEM_SEPARATOR = File.separatorChar;
  
  static
  {
    if (isSystemWindows())
    {
      OTHER_SEPARATOR = '/';
      return;
    }
  }
  
  static boolean isSystemWindows()
  {
    return SYSTEM_SEPARATOR == '\\';
  }
  
  static String[] splitOnTokens(String paramString)
  {
    if ((paramString.indexOf("?") == -1) && (paramString.indexOf("*") == -1)) {
      return new String[] { paramString };
    }
    char[] arrayOfChar = paramString.toCharArray();
    ArrayList localArrayList = new ArrayList();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < arrayOfChar.length)
    {
      if ((arrayOfChar[i] == '?') || (arrayOfChar[i] == '*'))
      {
        if (localStringBuffer.length() != 0)
        {
          localArrayList.add(localStringBuffer.toString());
          localStringBuffer.setLength(0);
        }
        if (arrayOfChar[i] == '?') {
          localArrayList.add("?");
        }
      }
      for (;;)
      {
        i++;
        break;
        if ((localArrayList.size() == 0) || ((i > 0) && (!localArrayList.get(-1 + localArrayList.size()).equals("*"))))
        {
          localArrayList.add("*");
          continue;
          localStringBuffer.append(arrayOfChar[i]);
        }
      }
    }
    if (localStringBuffer.length() != 0) {
      localArrayList.add(localStringBuffer.toString());
    }
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static boolean wildcardMatch(String paramString1, String paramString2, IOCase paramIOCase)
  {
    if ((paramString1 == null) && (paramString2 == null)) {
      return true;
    }
    if ((paramString1 == null) || (paramString2 == null)) {
      return false;
    }
    if (paramIOCase == null) {
      paramIOCase = IOCase.SENSITIVE;
    }
    String str = paramIOCase.convertCase(paramString1);
    String[] arrayOfString = splitOnTokens(paramIOCase.convertCase(paramString2));
    int i = 0;
    int j = 0;
    int k = 0;
    Stack localStack = new Stack();
    label166:
    do
    {
      if (localStack.size() > 0)
      {
        int[] arrayOfInt = (int[])localStack.pop();
        k = arrayOfInt[0];
        j = arrayOfInt[1];
        i = 1;
      }
      if (k < arrayOfString.length)
      {
        if (arrayOfString[k].equals("?"))
        {
          j++;
          i = 0;
        }
        for (;;)
        {
          k++;
          break;
          if (!arrayOfString[k].equals("*")) {
            break label166;
          }
          i = 1;
          if (k == -1 + arrayOfString.length) {
            j = str.length();
          }
        }
        if (i != 0)
        {
          j = str.indexOf(arrayOfString[k], j);
          if (j == -1) {
            break label262;
          }
          m = str.indexOf(arrayOfString[k], j + 1);
          if (m >= 0) {
            localStack.push(new int[] { k, m });
          }
        }
        while (str.startsWith(arrayOfString[k], j))
        {
          int m;
          j += arrayOfString[k].length();
          i = 0;
          break;
        }
      }
      if ((k == arrayOfString.length) && (j == str.length())) {
        return true;
      }
    } while (localStack.size() > 0);
    label262:
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.apache.commons.io.FilenameUtils
 * JD-Core Version:    0.7.0.1
 */