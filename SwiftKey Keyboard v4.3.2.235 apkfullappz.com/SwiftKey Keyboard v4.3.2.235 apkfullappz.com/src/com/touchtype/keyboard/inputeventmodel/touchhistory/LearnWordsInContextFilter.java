package com.touchtype.keyboard.inputeventmodel.touchhistory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

final class LearnWordsInContextFilter
{
  private static final Set<Character.UnicodeBlock> CJK_CODEBLOCK;
  
  static
  {
    HashSet localHashSet = new HashSet();
    CJK_CODEBLOCK = localHashSet;
    localHashSet.add(Character.UnicodeBlock.CJK_COMPATIBILITY);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS);
    CJK_CODEBLOCK.add(Character.UnicodeBlock.KANGXI_RADICALS);
  }
  
  static String filter(String paramString, int paramInt)
  {
    ArrayList localArrayList = new ArrayList(Arrays.asList(paramString.split("\\n")));
    if (localArrayList.size() == 0) {
      return paramString;
    }
    String str1;
    String str2;
    label46:
    StringBuffer localStringBuffer;
    if (paramInt <= 0)
    {
      str1 = null;
      if (localArrayList.size() != 0) {
        break label152;
      }
      str2 = null;
      localStringBuffer = new StringBuffer();
      if ((str1 != null) && (testString(str1)))
      {
        if (str2 != null) {
          break label174;
        }
        localStringBuffer.append(filterFirst(filterLast(str1)));
      }
    }
    for (;;)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext())
      {
        String str3 = (String)localIterator.next();
        if (testString(str3)) {
          localStringBuffer.append(str3).append("\n");
        }
      }
      str1 = (String)localArrayList.remove(0);
      break;
      label152:
      str2 = (String)localArrayList.remove(-1 + localArrayList.size());
      break label46;
      label174:
      localStringBuffer.append(filterFirst(str1)).append("\n");
    }
    if ((str2 != null) && (testString(str2))) {
      localStringBuffer.append(filterLast(str2));
    }
    return localStringBuffer.toString();
  }
  
  private static String filterFirst(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    return paramString.substring(testFirst(paramString));
  }
  
  private static String filterLast(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      return "";
    }
    return paramString.substring(0, testLast(paramString));
  }
  
  private static boolean isCJKCodePoint(int paramInt)
  {
    return CJK_CODEBLOCK.contains(Character.UnicodeBlock.of(paramInt));
  }
  
  private static int testFirst(String paramString)
  {
    int i = paramString.indexOf(" ");
    if (!paramString.matches("^[\\s.,?!({\"\\[].*"))
    {
      if (i != -1) {
        return i;
      }
      return 0;
    }
    return 0;
  }
  
  private static int testLast(String paramString)
  {
    int i = paramString.length();
    if ((i > 0) && (isCJKCodePoint(paramString.codePointBefore(i)))) {
      return i;
    }
    int j = paramString.lastIndexOf(" ");
    if (!paramString.matches(".*[\\s.,?!)}\"'\\]]$"))
    {
      if (j != -1) {
        return j;
      }
      return 0;
    }
    return i - 1;
  }
  
  private static boolean testString(String paramString)
  {
    return (!paramString.startsWith(">")) && (!paramString.matches("^On .* wrote:"));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.touchhistory.LearnWordsInContextFilter
 * JD-Core Version:    0.7.0.1
 */