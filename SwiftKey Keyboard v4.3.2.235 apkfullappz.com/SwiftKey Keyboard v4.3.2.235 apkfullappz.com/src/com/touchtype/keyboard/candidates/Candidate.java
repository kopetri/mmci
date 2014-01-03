package com.touchtype.keyboard.candidates;

import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.service.TouchTypeExtractedText;
import java.util.Iterator;
import java.util.Set;

public final class Candidate
{
  private final Prediction mPrediction;
  private final Ranking mRanking;
  private final Type mType;
  private boolean mUseZeroWidthSpace;
  private final CharSequence mVerbatim;
  
  private Candidate(Prediction paramPrediction, Ranking paramRanking, CharSequence paramCharSequence, Type paramType)
  {
    this.mPrediction = paramPrediction;
    this.mRanking = paramRanking;
    this.mVerbatim = paramCharSequence;
    this.mType = paramType;
    this.mUseZeroWidthSpace = false;
  }
  
  public static Candidate empty()
  {
    return new Candidate(null, Ranking.EMPTY, "", Type.EMPTY);
  }
  
  public static Candidate fromFluency(Prediction paramPrediction, Ranking paramRanking, CharSequence paramCharSequence)
  {
    if ((paramCharSequence.length() > 0) && (paramCharSequence.equals(paramPrediction.getPrediction()))) {
      return verbatim(paramCharSequence, paramRanking, paramPrediction);
    }
    if (paramPrediction.getTags().contains("prefix")) {}
    for (Type localType = Type.PREDICTION;; localType = Type.CORRECTION) {
      return new Candidate(paramPrediction, paramRanking, "", localType);
    }
  }
  
  public static Candidate verbatim(CharSequence paramCharSequence)
  {
    return verbatim(paramCharSequence, Ranking.VERBATIM, null);
  }
  
  private static Candidate verbatim(CharSequence paramCharSequence, Ranking paramRanking, Prediction paramPrediction)
  {
    if (paramCharSequence.length() == 0) {
      return new Candidate(paramPrediction, paramRanking, paramCharSequence, Type.EMPTY);
    }
    return new Candidate(paramPrediction, paramRanking, paramCharSequence, Type.TRUE_VERBATIM);
  }
  
  private String wrapPredictionWithSeparator(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.mPrediction.size(); i++)
    {
      localStringBuffer.append(this.mPrediction.get(i));
      if (i < -1 + this.mPrediction.size()) {
        localStringBuffer.append(paramString);
      }
    }
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    Candidate localCandidate;
    if ((paramObject instanceof Candidate))
    {
      localCandidate = (Candidate)paramObject;
      if (this.mType == localCandidate.mType) {}
    }
    else
    {
      return false;
    }
    if (this.mPrediction != null) {
      return this.mPrediction.getPrediction().equals(localCandidate.mPrediction.getPrediction());
    }
    return this.mVerbatim.equals(localCandidate.mVerbatim);
  }
  
  public int getNumberOfWords()
  {
    int i;
    if (this.mPrediction != null) {
      i = this.mPrediction.size();
    }
    for (;;)
    {
      return i;
      i = 1;
      char[] arrayOfChar = toString().toCharArray();
      int j = arrayOfChar.length;
      for (int k = 0; k < j; k++) {
        if (TouchTypeExtractedText.isSpace(arrayOfChar[k])) {
          i++;
        }
      }
    }
  }
  
  public Prediction getPrediction()
  {
    return this.mPrediction;
  }
  
  public Ranking getRanking()
  {
    return this.mRanking;
  }
  
  public String getSeparator()
  {
    if (this.mPrediction != null)
    {
      if ((this.mPrediction.getSeparator().equals("​")) && (!this.mUseZeroWidthSpace)) {
        return " ";
      }
      if ((this.mPrediction.getSeparator().equals(" ")) && (this.mUseZeroWidthSpace)) {
        return "​";
      }
      return this.mPrediction.getSeparator();
    }
    return " ";
  }
  
  public Type getType()
  {
    return this.mType;
  }
  
  public int hashCode()
  {
    int i = this.mType.hashCode();
    if (this.mPrediction != null) {
      i = i * 29 + this.mPrediction.getPrediction().hashCode();
    }
    return i * 29 + this.mVerbatim.hashCode();
  }
  
  public boolean isFluencyVerbatim()
  {
    return (this.mType == Type.CORRECTION) && (this.mPrediction.getTags().contains("verbatim"));
  }
  
  public boolean isVerbatim()
  {
    return (this.mType == Type.TRUE_VERBATIM) || (isFluencyVerbatim());
  }
  
  public void setUsingZeroWidthSpace(boolean paramBoolean)
  {
    this.mUseZeroWidthSpace = paramBoolean;
  }
  
  public String source()
  {
    Prediction localPrediction = getPrediction();
    if (localPrediction != null)
    {
      Iterator localIterator = localPrediction.getTags().iterator();
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        if (str.startsWith("source:")) {
          return str.substring(7);
        }
      }
    }
    return "";
  }
  
  public String toString()
  {
    if (this.mPrediction != null)
    {
      if ((this.mPrediction.getSeparator().equals("​")) && (!this.mUseZeroWidthSpace)) {
        return wrapPredictionWithSeparator(" ");
      }
      if ((this.mPrediction.getSeparator().equals(" ")) && (this.mUseZeroWidthSpace)) {
        return wrapPredictionWithSeparator("​");
      }
      return this.mPrediction.getPrediction();
    }
    return this.mVerbatim.toString();
  }
  
  public int version()
  {
    Prediction localPrediction = getPrediction();
    int i = 0;
    String str;
    if (localPrediction != null)
    {
      Iterator localIterator = localPrediction.getTags().iterator();
      do
      {
        boolean bool = localIterator.hasNext();
        i = 0;
        if (!bool) {
          break;
        }
        str = (String)localIterator.next();
      } while (!str.startsWith("version:"));
    }
    try
    {
      int j = Integer.valueOf(str.substring(8)).intValue();
      i = j;
      return i;
    }
    catch (NumberFormatException localNumberFormatException) {}
    return 0;
  }
  
  public static enum Ranking
  {
    private static Ranking[] ranks;
    
    static
    {
      FIRST = new Ranking("FIRST", 2);
      SECOND = new Ranking("SECOND", 3);
      THIRD = new Ranking("THIRD", 4);
      Ranking[] arrayOfRanking1 = new Ranking[5];
      arrayOfRanking1[0] = EMPTY;
      arrayOfRanking1[1] = VERBATIM;
      arrayOfRanking1[2] = FIRST;
      arrayOfRanking1[3] = SECOND;
      arrayOfRanking1[4] = THIRD;
      $VALUES = arrayOfRanking1;
      Ranking[] arrayOfRanking2 = new Ranking[3];
      arrayOfRanking2[0] = FIRST;
      arrayOfRanking2[1] = SECOND;
      arrayOfRanking2[2] = THIRD;
      ranks = arrayOfRanking2;
    }
    
    private Ranking() {}
    
    public static Ranking getRanking(int paramInt)
    {
      if (paramInt < ranks.length) {
        return ranks[paramInt];
      }
      return ranks[(-1 + ranks.length)];
    }
  }
  
  public static enum Type
  {
    static
    {
      EMPTY = new Type("EMPTY", 3);
      Type[] arrayOfType = new Type[4];
      arrayOfType[0] = CORRECTION;
      arrayOfType[1] = PREDICTION;
      arrayOfType[2] = TRUE_VERBATIM;
      arrayOfType[3] = EMPTY;
      $VALUES = arrayOfType;
    }
    
    private Type() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.Candidate
 * JD-Core Version:    0.7.0.1
 */