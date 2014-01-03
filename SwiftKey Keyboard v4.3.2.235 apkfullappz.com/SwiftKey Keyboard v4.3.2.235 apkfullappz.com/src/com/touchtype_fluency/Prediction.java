package com.touchtype_fluency;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Prediction
  extends AbstractList<String>
{
  private final ArrayList<HashSet<String>> encodings;
  private final String[] keys;
  private final String[] prediction;
  private final double probability;
  private final String[] separators;
  private final Set<String> tags;
  private final Integer[] termBreaks;
  
  static
  {
    if (!Prediction.class.desiredAssertionStatus()) {}
    for (boolean bool = true;; bool = false)
    {
      $assertionsDisabled = bool;
      return;
    }
  }
  
  public Prediction(String paramString, double paramDouble, Set<String> paramSet)
  {
    this(new String[1], new String[0], paramDouble, paramSet, new String[0], new Integer[0], new ArrayList());
    this.prediction[0] = paramString;
  }
  
  public Prediction(String[] paramArrayOfString1, String[] paramArrayOfString2, double paramDouble, Set<String> paramSet, Integer[] paramArrayOfInteger, ArrayList<HashSet<String>> paramArrayList)
  {
    this(paramArrayOfString1, paramArrayOfString2, paramDouble, paramSet, new String[0], paramArrayOfInteger, paramArrayList);
  }
  
  private Prediction(String[] paramArrayOfString1, String[] paramArrayOfString2, double paramDouble, Set<String> paramSet, String[] paramArrayOfString3, Integer[] paramArrayOfInteger, ArrayList<HashSet<String>> paramArrayList)
  {
    this.prediction = paramArrayOfString1;
    this.separators = paramArrayOfString2;
    this.probability = paramDouble;
    this.tags = paramSet;
    this.keys = paramArrayOfString3;
    this.termBreaks = paramArrayOfInteger;
    this.encodings = paramArrayList;
  }
  
  private static boolean approxEquals(double paramDouble1, double paramDouble2)
  {
    return Math.abs(paramDouble1 - paramDouble2) < 0.0001D * Math.min(Math.abs(paramDouble1), Math.abs(paramDouble2));
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof Prediction;
    boolean bool2 = false;
    if (bool1)
    {
      Prediction localPrediction = (Prediction)paramObject;
      boolean bool3 = getPrediction().equals(localPrediction.getPrediction());
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = approxEquals(this.probability, localPrediction.probability);
        bool2 = false;
        if (bool4)
        {
          boolean bool5 = this.tags.equals(localPrediction.tags);
          bool2 = false;
          if (bool5) {
            bool2 = true;
          }
        }
      }
    }
    return bool2;
  }
  
  public String get(int paramInt)
  {
    return this.prediction[paramInt];
  }
  
  public ArrayList<HashSet<String>> getEncodings()
  {
    return this.encodings;
  }
  
  public String getPrediction()
  {
    int i = 1;
    if (this.prediction.length == 0) {
      return new String();
    }
    if (this.prediction.length == i) {
      return this.prediction[0];
    }
    assert (this.separators.length == -1 + this.prediction.length);
    StringBuilder localStringBuilder = new StringBuilder(this.prediction[0]);
    while (i < this.prediction.length)
    {
      localStringBuilder.append(this.separators[(i - 1)]);
      localStringBuilder.append(this.prediction[i]);
      i++;
    }
    return localStringBuilder.toString();
  }
  
  public double getProbability()
  {
    return this.probability;
  }
  
  @Deprecated
  public String getSeparator()
  {
    String str = "";
    for (int i = 0; i < this.separators.length; i++) {
      if (this.separators[i].length() > str.length()) {
        str = this.separators[i];
      }
    }
    return str;
  }
  
  public String[] getSeparators()
  {
    return this.separators;
  }
  
  public Set<String> getTags()
  {
    return this.tags;
  }
  
  public Integer[] getTermBreaks()
  {
    Integer[] arrayOfInteger = new Integer[this.termBreaks.length];
    for (int i = 0; i < this.termBreaks.length; i++) {
      arrayOfInteger[i] = Integer.valueOf(0xFFFF & this.termBreaks[i].intValue() >> 16);
    }
    return arrayOfInteger;
  }
  
  public int hashCode()
  {
    return 149 * (Arrays.hashCode(this.prediction) + 149 * (new Double(this.probability).hashCode() + 149 * (149 + this.tags.hashCode())));
  }
  
  public int size()
  {
    return this.prediction.length;
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = getPrediction();
    arrayOfObject[1] = Double.valueOf(this.probability);
    return String.format("%s : %e", arrayOfObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Prediction
 * JD-Core Version:    0.7.0.1
 */