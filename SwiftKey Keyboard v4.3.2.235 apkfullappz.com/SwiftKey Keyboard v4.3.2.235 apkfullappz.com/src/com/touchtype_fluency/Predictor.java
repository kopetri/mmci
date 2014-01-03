package com.touchtype_fluency;

import java.util.Set;

public abstract interface Predictor
{
  public abstract void clearLayoutKeys();
  
  public abstract Predictions get(Sequence paramSequence1, TouchHistory paramTouchHistory, Sequence paramSequence2, ResultsFilter paramResultsFilter);
  
  public abstract CharacterMap getCharacterMap();
  
  public abstract Predictions getCorrections(Sequence paramSequence1, TouchHistory paramTouchHistory, Sequence paramSequence2, ResultsFilter paramResultsFilter);
  
  public abstract KeyPressModel getKeyPressModel();
  
  public abstract String getMostLikelyLanguage(Sequence paramSequence);
  
  public abstract Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter);
  
  public abstract SearchType getSearchType();
  
  public abstract boolean queryTerm(String paramString);
  
  public abstract boolean queryTerm(String paramString, TagSelector paramTagSelector);
  
  public abstract boolean queryTerm(String paramString1, TagSelector paramTagSelector, String paramString2);
  
  public abstract void setIntentionalEvents(Set<String> paramSet);
  
  public abstract void setLayoutKeys(Set<String> paramSet);
  
  public abstract void setSearchType(SearchType paramSearchType);
  
  public static enum SearchType
  {
    static
    {
      CANGJIE = new SearchType("CANGJIE", 3);
      STROKE = new SearchType("STROKE", 4);
      JAPANESE = new SearchType("JAPANESE", 5);
      SearchType[] arrayOfSearchType = new SearchType[6];
      arrayOfSearchType[0] = NORMAL;
      arrayOfSearchType[1] = PINYIN;
      arrayOfSearchType[2] = ZHUYIN;
      arrayOfSearchType[3] = CANGJIE;
      arrayOfSearchType[4] = STROKE;
      arrayOfSearchType[5] = JAPANESE;
      $VALUES = arrayOfSearchType;
    }
    
    private SearchType() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Predictor
 * JD-Core Version:    0.7.0.1
 */