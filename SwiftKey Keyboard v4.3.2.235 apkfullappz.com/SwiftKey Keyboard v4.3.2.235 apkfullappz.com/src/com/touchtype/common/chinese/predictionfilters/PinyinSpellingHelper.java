package com.touchtype.common.chinese.predictionfilters;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class PinyinSpellingHelper
  extends SpellingHelper
{
  private static final Map<String, List<String>> DEFAULT_12KEYS = new HashMap() {};
  static final Map<Character, Character> DEFAULT_DIGIT = new HashMap() {};
  
  private PinyinSpellingHelper(Map<String, List<String>> paramMap)
  {
    super(new ToXKeyTranslator(DEFAULT_DIGIT), paramMap, 6);
  }
  
  public static PinyinSpellingHelper create()
  {
    return new PinyinSpellingHelper(DEFAULT_12KEYS);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.chinese.predictionfilters.PinyinSpellingHelper
 * JD-Core Version:    0.7.0.1
 */