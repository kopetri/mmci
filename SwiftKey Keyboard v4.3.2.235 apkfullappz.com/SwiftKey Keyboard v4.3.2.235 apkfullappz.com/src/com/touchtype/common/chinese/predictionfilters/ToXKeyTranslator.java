package com.touchtype.common.chinese.predictionfilters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class ToXKeyTranslator
{
  private final Map<Character, Character> encoding;
  
  ToXKeyTranslator(Map<Character, Character> paramMap)
  {
    this.encoding = paramMap;
  }
  
  static ToXKeyTranslator create(Map<String, String[]> paramMap)
  {
    HashMap localHashMap = new HashMap();
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      String[] arrayOfString = (String[])localEntry.getValue();
      for (int i = 0; i < arrayOfString.length; i++)
      {
        if (arrayOfString[i].length() != 1) {
          throw new IllegalArgumentException("Entry value length should be 1. Found: " + arrayOfString[i]);
        }
        localHashMap.put(Character.valueOf(arrayOfString[i].charAt(0)), Character.valueOf(str.charAt(0)));
      }
    }
    return new ToXKeyTranslator(localHashMap);
  }
  
  protected Map<String, List<String>> createLookupTable(String[] paramArrayOfString)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str1 = paramArrayOfString[j];
      String str2 = translate(str1);
      Object localObject = (List)localHashMap.get(str2);
      if (localObject == null)
      {
        localObject = new ArrayList();
        localHashMap.put(str2, localObject);
      }
      ((List)localObject).add(str1);
    }
    return localHashMap;
  }
  
  char translate(char paramChar)
  {
    Character localCharacter = (Character)this.encoding.get(Character.valueOf(paramChar));
    if (localCharacter != null) {
      paramChar = localCharacter.charValue();
    }
    return paramChar;
  }
  
  String translate(String paramString)
  {
    char[] arrayOfChar = paramString.toCharArray();
    for (int i = 0; i < arrayOfChar.length; i++) {
      arrayOfChar[i] = translate(arrayOfChar[i]);
    }
    return new String(arrayOfChar);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.chinese.predictionfilters.ToXKeyTranslator
 * JD-Core Version:    0.7.0.1
 */