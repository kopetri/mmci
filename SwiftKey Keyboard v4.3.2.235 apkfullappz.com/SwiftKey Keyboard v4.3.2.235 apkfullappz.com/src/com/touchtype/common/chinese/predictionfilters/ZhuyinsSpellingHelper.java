package com.touchtype.common.chinese.predictionfilters;

import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class ZhuyinsSpellingHelper
  extends SpellingHelper
{
  static final String[] ALL_ZHUYINS = { "ˇ", "ˋ", "ˊ", "˙", "ㄅ", "ㄅㄚ", "ㄅㄛ", "ㄅㄞ", "ㄅㄟ", "ㄅㄠ", "ㄅㄢ", "ㄅㄣ", "ㄅㄤ", "ㄅㄥ", "ㄅㄧ", "ㄅㄧㄝ", "ㄅㄧㄠ", "ㄅㄧㄢ", "ㄅㄧㄣ", "ㄅㄧㄥ", "ㄅㄨ", "ㄆ", "ㄆㄚ", "ㄆㄛ", "ㄆㄞ", "ㄆㄟ", "ㄆㄠ", "ㄆㄡ", "ㄆㄢ", "ㄆㄣ", "ㄆㄤ", "ㄆㄥ", "ㄆㄧ", "ㄆㄧㄝ", "ㄆㄧㄠ", "ㄆㄧㄢ", "ㄆㄧㄣ", "ㄆㄧㄥ", "ㄆㄨ", "ㄇ", "ㄇㄚ", "ㄇㄛ", "ㄇㄞ", "ㄇㄟ", "ㄇㄠ", "ㄇㄡ", "ㄇㄢ", "ㄇㄣ", "ㄇㄤ", "ㄇㄥ", "ㄇㄧ", "ㄇㄧㄝ", "ㄇㄧㄠ", "ㄇㄧㄡ", "ㄇㄧㄢ", "ㄇㄧㄣ", "ㄇㄧㄥ", "ㄇㄨ", "ㄈ", "ㄈㄚ", "ㄈㄛ", "ㄈㄟ", "ㄈㄡ", "ㄈㄢ", "ㄈㄣ", "ㄈㄤ", "ㄈㄥ", "ㄈㄨ", "ㄉ", "ㄉㄚ", "ㄉㄜ", "ㄉㄞ", "ㄉㄟ", "ㄉㄠ", "ㄉㄡ", "ㄉㄢ", "ㄉㄤ", "ㄉㄥ", "ㄉㄧ", "ㄉㄧㄚ", "ㄉㄧㄝ", "ㄉㄧㄠ", "ㄉㄧㄡ", "ㄉㄧㄢ", "ㄉㄧㄥ", "ㄉㄨ", "ㄉㄨㄛ", "ㄉㄨㄟ", "ㄉㄨㄢ", "ㄉㄨㄣ", "ㄉㄨㄥ", "ㄊ", "ㄊㄚ", "ㄊㄜ", "ㄊㄞ", "ㄊㄟ", "ㄊㄠ", "ㄊㄡ", "ㄊㄢ", "ㄊㄤ", "ㄊㄧ", "ㄊㄧㄝ", "ㄊㄧㄠ", "ㄊㄧㄢ", "ㄊㄧㄥ", "ㄊㄨㄛ", "ㄊㄨㄟ", "ㄊㄨㄢ", "ㄊㄨㄣ", "ㄊㄨㄥ", "ㄋ", "ㄋㄚ", "ㄋㄜ", "ㄋㄞ", "ㄋㄟ", "ㄋㄠ", "ㄋㄡ", "ㄋㄢ", "ㄋㄣ", "ㄋㄤ", "ㄋㄥ", "ㄋㄧ", "ㄋㄧㄝ", "ㄋㄧㄠ", "ㄋㄧㄡ", "ㄋㄧㄢ", "ㄋㄧㄣ", "ㄋㄧㄤ", "ㄋㄧㄥ", "ㄋㄨ", "ㄋㄨㄛ", "ㄋㄨㄢ", "ㄋㄨㄥ", "ㄋㄩ", "ㄋㄩㄝ", "ㄌ", "ㄌㄚ", "ㄌㄜ", "ㄌㄞ", "ㄌㄟ", "ㄌㄠ", "ㄌㄡ", "ㄌㄢ", "ㄌㄤ", "ㄌㄥ", "ㄌㄧ", "ㄌㄧㄚ", "ㄌㄧㄝ", "ㄌㄧㄠ", "ㄌㄧㄡ", "ㄌㄧㄢ", "ㄌㄧㄣ", "ㄌㄧㄤ", "ㄌㄧㄥ", "ㄌㄨ", "ㄌㄨㄛ", "ㄌㄨㄢ", "ㄌㄨㄣ", "ㄌㄨㄥ", "ㄌㄩ", "ㄌㄩㄝ", "ㄍ", "ㄍㄚ", "ㄍㄜ", "ㄍㄞ", "ㄍㄟ", "ㄍㄠ", "ㄍㄡ", "ㄍㄢ", "ㄍㄣ", "ㄍㄤ", "ㄍㄥ", "ㄍㄨ", "ㄍㄨㄚ", "ㄍㄨㄛ", "ㄍㄨㄞ", "ㄍㄨㄟ", "ㄍㄨㄢ", "ㄍㄨㄣ", "ㄍㄨㄥ", "ㄍㄨㄤ", "ㄎ", "ㄎㄚ", "ㄎㄜ", "ㄎㄞ", "ㄎㄠ", "ㄎㄡ", "ㄎㄢ", "ㄎㄣ", "ㄎㄤ", "ㄎㄥ", "ㄎㄨ", "ㄎㄨㄚ", "ㄎㄨㄛ", "ㄎㄨㄞ", "ㄎㄨㄟ", "ㄎㄨㄢ", "ㄎㄨㄣ", "ㄎㄨㄥ", "ㄎㄨㄤ", "ㄏ", "ㄏㄚ", "ㄏㄞ", "ㄏㄟ", "ㄏㄠ", "ㄏㄡ", "ㄏㄢ", "ㄏㄣ", "ㄏㄤ", "ㄏㄥ", "ㄏㄨ", "ㄏㄨㄚ", "ㄏㄨㄛ", "ㄏㄨㄞ", "ㄏㄨㄟ", "ㄏㄨㄢ", "ㄏㄨㄣ", "ㄏㄨㄥ", "ㄏㄨㄤ", "ㄐ", "ㄐㄧㄚ", "ㄐㄧㄝ", "ㄐㄧㄠ", "ㄐㄧㄡ", "ㄐㄧㄢ", "ㄐㄧㄣ", "ㄐㄧㄤ", "ㄐㄧㄥ", "ㄐㄩ", "ㄐㄩㄝ", "ㄐㄩㄢ", "ㄐㄩㄣ", "ㄐㄩㄥ", "ㄑ", "ㄑㄧ", "ㄑㄧㄚ", "ㄑㄧㄝ", "ㄑㄧㄠ", "ㄑㄧㄡ", "ㄑㄧㄢ", "ㄑㄧㄣ", "ㄑㄧㄤ", "ㄑㄧㄥ", "ㄑㄩ", "ㄑㄩㄝ", "ㄑㄩㄢ", "ㄑㄩㄣ", "ㄑㄩㄥ", "ㄒ", "ㄒㄧ", "ㄒㄧㄚ", "ㄒㄧㄝ", "ㄒㄧㄠ", "ㄒㄧㄡ", "ㄒㄧㄢ", "ㄒㄧㄣ", "ㄒㄧㄤ", "ㄒㄧㄥ", "ㄒㄩ", "ㄒㄩㄝ", "ㄒㄩㄢ", "ㄒㄩㄣ", "ㄒㄩㄥ", "ㄓ", "ㄓㄡ", "ㄓㄨㄚ", "ㄓㄨㄛ", "ㄓㄨㄞ", "ㄓㄨㄟ", "ㄓㄨㄢ", "ㄓㄨㄣ", "ㄓㄨㄥ", "ㄓㄨㄤ", "ㄗ", "ㄗㄚ", "ㄗㄜ", "ㄗㄞ", "ㄗㄟ", "ㄗㄠ", "ㄗㄡ", "ㄗㄢ", "ㄗㄣ", "ㄗㄤ", "ㄗㄥ", "ㄗㄨㄛ", "ㄗㄨㄟ", "ㄗㄨㄢ", "ㄗㄨㄣ", "ㄗㄨㄥ", "ㄓㄚ", "ㄓㄞ", "ㄓㄢ", "ㄓㄣ", "ㄓㄜ", "ㄓㄠ", "ㄓㄤ", "ㄓㄥ", "ㄔ", "ㄔㄡ", "ㄔㄨ", "ㄔㄨㄛ", "ㄔㄨㄞ", "ㄔㄨㄟ", "ㄔㄨㄢ", "ㄔㄨㄣ", "ㄔㄨㄥ", "ㄔㄨㄤ", "ㄕ", "ㄕㄟ", "ㄕㄡ", "ㄕㄨ", "ㄕㄨㄚ", "ㄕㄨㄛ", "ㄕㄨㄞ", "ㄕㄨㄟ", "ㄕㄨㄢ", "ㄕㄨㄣ", "ㄕㄨㄤ", "ㄡ", "ㄖ", "ㄖㄜ", "ㄖㄠ", "ㄖㄡ", "ㄖㄢ", "ㄖㄣ", "ㄖㄤ", "ㄖㄥ", "ㄖㄨㄛ", "ㄖㄨㄟ", "ㄖㄨㄢ", "ㄖㄨㄣ", "ㄖㄨㄥ", "ㄘ", "ㄘㄚ", "ㄘㄜ", "ㄘㄞ", "ㄘㄠ", "ㄘㄢ", "ㄘㄣ", "ㄘㄤ", "ㄘㄥ", "ㄘㄨ", "ㄘㄨㄛ", "ㄘㄨㄟ", "ㄘㄨㄢ", "ㄘㄨㄣ", "ㄘㄨㄥ", "ㄔㄚ", "ㄔㄞ", "ㄔㄢ", "ㄔㄣ", "ㄔㄜ", "ㄔㄠ", "ㄔㄤ", "ㄔㄥ", "ㄙ", "ㄙㄚ", "ㄙㄜ", "ㄙㄞ", "ㄙㄠ", "ㄙㄡ", "ㄙㄢ", "ㄙㄣ", "ㄙㄤ", "ㄙㄥ", "ㄙㄨ", "ㄙㄨㄛ", "ㄙㄨㄟ", "ㄙㄨㄢ", "ㄙㄨㄣ", "ㄙㄨㄥ", "ㄕㄚ", "ㄕㄞ", "ㄕㄢ", "ㄕㄣ", "ㄕㄜ", "ㄕㄠ", "ㄕㄤ", "ㄕㄥ", "ㄚ", "ㄛ", "ㄜ", "ㄝ", "ㄞ", "ㄟ", "ㄠ", "ㄢ", "ㄣ", "ㄤ", "ㄥ", "ㄦ", "ㄧ", "ㄧㄚ", "ㄧㄛ", "ㄧㄝ", "ㄧㄠ", "ㄧㄡ", "ㄧㄢ", "ㄧㄣ", "ㄧㄤ", "ㄧㄥ", "ㄨ", "ㄨㄚ", "ㄨㄛ", "ㄨㄞ", "ㄨㄟ", "ㄨㄢ", "ㄨㄣ", "ㄨㄤ", "ㄨㄥ", "ㄩ", "ㄩㄝ", "ㄩㄢ", "ㄩㄣ", "ㄩㄥ" };
  static final Map<Character, Character> DEFAULT_LAYOUT = new HashMap() {};
  
  private ZhuyinsSpellingHelper(ToXKeyTranslator paramToXKeyTranslator)
  {
    super(paramToXKeyTranslator, ALL_ZHUYINS);
  }
  
  public static ZhuyinsSpellingHelper create(Map<String, String[]> paramMap)
  {
    if ((paramMap == null) || (paramMap.isEmpty())) {
      throw new IllegalArgumentException("Layout should not be null.");
    }
    return new ZhuyinsSpellingHelper(ToXKeyTranslator.create(paramMap));
  }
  
  protected List<String> prioritize(List<String> paramList, Predictions paramPredictions, int paramInt)
  {
    HashMap localHashMap = new HashMap();
    int i;
    int k;
    int m;
    label37:
    int i1;
    if (paramPredictions == null)
    {
      i = 0;
      int j = ((String)paramList.get(0)).length();
      k = 0;
      m = 0;
      if (k >= i) {
        break label173;
      }
      Iterator localIterator2 = paramPredictions.get(k).getTags().iterator();
      while (localIterator2.hasNext())
      {
        String str1 = (String)localIterator2.next();
        if (str1.startsWith("input:"))
        {
          String str2 = getSymbolAt(str1.substring(6), paramInt);
          String str3 = str2.substring(0, Math.min(j, str2.length()));
          if (!localHashMap.containsKey(str3))
          {
            i1 = m + 1;
            localHashMap.put(str3, Integer.valueOf(m));
          }
        }
      }
    }
    for (;;)
    {
      k++;
      m = i1;
      break label37;
      i = paramPredictions.size();
      break;
      label173:
      ArrayList localArrayList = new ArrayList(m);
      for (int n = 0; n < m; n++) {
        localArrayList.add(null);
      }
      Iterator localIterator1 = localHashMap.entrySet().iterator();
      while (localIterator1.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator1.next();
        localArrayList.set(((Integer)localEntry.getValue()).intValue(), localEntry.getKey());
      }
      return localArrayList;
      i1 = m;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.chinese.predictionfilters.ZhuyinsSpellingHelper
 * JD-Core Version:    0.7.0.1
 */