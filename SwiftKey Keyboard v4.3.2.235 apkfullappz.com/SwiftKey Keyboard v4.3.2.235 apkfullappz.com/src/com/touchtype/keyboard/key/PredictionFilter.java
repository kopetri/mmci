package com.touchtype.keyboard.key;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.touchtype.keyboard.key.contents.KeyContent;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class PredictionFilter
{
  private static final Set<String> mExceptions;
  private Predicate<String> mFilter = new Predicate()
  {
    public boolean apply(String paramAnonymousString)
    {
      return PredictionFilter.isValid(paramAnonymousString, PredictionFilter.mExceptions);
    }
  };
  private Set<String> mPredictionFilterSet = new HashSet();
  
  static
  {
    HashSet localHashSet = new HashSet();
    mExceptions = localHashSet;
    localHashSet.add("@");
    mExceptions.add("#");
  }
  
  private static boolean isValid(char paramChar)
  {
    int i = Character.getType(paramChar);
    return (Character.isLetterOrDigit(paramChar)) || (i == 8) || (i == 7) || (i == 6);
  }
  
  private static boolean isValid(String paramString, Set<String> paramSet)
  {
    boolean bool = true;
    if ((paramString == null) || (paramString.length() == 0)) {
      bool = false;
    }
    for (;;)
    {
      return bool;
      if (!paramSet.contains(paramString))
      {
        char[] arrayOfChar = paramString.toCharArray();
        int i = arrayOfChar.length;
        for (int j = 0; j < i; j++) {
          if (!isValid(Character.valueOf(arrayOfChar[j]).charValue())) {
            return false;
          }
        }
      }
    }
  }
  
  public void addKey(KeyContent paramKeyContent, PopupContent paramPopupContent)
  {
    HashSet localHashSet = new HashSet();
    if (paramKeyContent != null) {
      localHashSet.addAll(paramKeyContent.getInputStrings());
    }
    if (paramPopupContent != null) {
      localHashSet.addAll(paramPopupContent.getInputStrings());
    }
    Collection localCollection = Collections2.filter(localHashSet, this.mFilter);
    this.mPredictionFilterSet.addAll(localCollection);
  }
  
  public Set<String> getPredictionFilterSet()
  {
    return this.mPredictionFilterSet;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.PredictionFilter
 * JD-Core Version:    0.7.0.1
 */