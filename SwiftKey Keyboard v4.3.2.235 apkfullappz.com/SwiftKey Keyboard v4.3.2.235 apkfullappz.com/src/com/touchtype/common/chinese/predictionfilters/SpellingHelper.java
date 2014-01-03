package com.touchtype.common.chinese.predictionfilters;

import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class SpellingHelper
{
  private final Map<String, List<String>> lookUpTable;
  private final int maximumSymbolLength;
  private final ToXKeyTranslator translator;
  
  protected SpellingHelper(ToXKeyTranslator paramToXKeyTranslator, Map<String, List<String>> paramMap, int paramInt)
  {
    this.translator = paramToXKeyTranslator;
    this.lookUpTable = paramMap;
    this.maximumSymbolLength = paramInt;
  }
  
  protected SpellingHelper(ToXKeyTranslator paramToXKeyTranslator, String[] paramArrayOfString)
  {
    this(paramToXKeyTranslator, paramToXKeyTranslator.createLookupTable(paramArrayOfString), computeMaxSymbolLength(paramArrayOfString));
  }
  
  private static int computeMaxSymbolLength(String[] paramArrayOfString)
  {
    int i = 0;
    int j = paramArrayOfString.length;
    int k = 0;
    if (k < j)
    {
      String str = paramArrayOfString[k];
      if (i > str.length()) {}
      for (;;)
      {
        k++;
        break;
        i = str.length();
      }
    }
    return i;
  }
  
  protected String getBestInputTag(Predictions paramPredictions, String paramString)
  {
    if ((paramPredictions != null) && (paramPredictions.size() > 0))
    {
      Iterator localIterator = paramPredictions.get(0).getTags().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith("input:")) {
          paramString = str.substring(6);
        }
      }
    }
    return paramString;
  }
  
  public List<String> getPredictionsFilters(Predictions paramPredictions, int paramInt)
  {
    ArrayList localArrayList = new ArrayList();
    String str1 = getSymbolAt(getBestInputTag(paramPredictions, ""), paramInt);
    for (String str2 = this.translator.translate(str1); str2.length() > 0; str2 = str2.substring(0, -1 + str2.length()))
    {
      List localList = (List)this.lookUpTable.get(str2);
      if (localList != null) {
        localArrayList.addAll(prioritize(localList, paramPredictions, paramInt));
      }
    }
    return localArrayList;
  }
  
  protected String getSymbolAt(String paramString, int paramInt)
  {
    if (paramInt > paramString.length()) {
      throw new IllegalArgumentException("Offset cannot be larger than the input length");
    }
    int i = paramString.length();
    int j = 0;
    int m;
    for (int k = paramInt; j < i; k = m)
    {
      if (paramString.charAt(j) != '\'')
      {
        m = k - 1;
        if (k == 0)
        {
          int n = paramString.indexOf('\'', j);
          if (n == -1) {
            n = paramString.length();
          }
          return paramString.substring(j, Math.min(n, j + this.maximumSymbolLength));
        }
      }
      else
      {
        m = k;
      }
      j++;
    }
    return "";
  }
  
  protected List<String> prioritize(List<String> paramList, Predictions paramPredictions, int paramInt)
  {
    return paramList;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.chinese.predictionfilters.SpellingHelper
 * JD-Core Version:    0.7.0.1
 */