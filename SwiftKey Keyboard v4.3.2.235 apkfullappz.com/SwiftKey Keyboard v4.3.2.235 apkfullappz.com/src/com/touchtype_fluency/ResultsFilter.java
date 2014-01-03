package com.touchtype_fluency;

public class ResultsFilter
{
  private final CapitalizationHint mcapitalization;
  private final CorrectionMode mcorrection;
  private final int mnTotal;
  private final PredictionMode mprediction;
  private final PredictionSearchType msearchType;
  private final VerbatimMode mverbatim;
  
  static {}
  
  public ResultsFilter(int paramInt)
  {
    this(paramInt, CapitalizationHint.LOWER_CASE, VerbatimMode.ENABLED, PredictionMode.CURRENT_WORD_PREDICT, CorrectionMode.DEFAULT, PredictionSearchType.NORMAL);
  }
  
  public ResultsFilter(int paramInt, CapitalizationHint paramCapitalizationHint, VerbatimMode paramVerbatimMode)
  {
    this(paramInt, paramCapitalizationHint, paramVerbatimMode, PredictionMode.CURRENT_WORD_PREDICT, CorrectionMode.DEFAULT, PredictionSearchType.NORMAL);
  }
  
  public ResultsFilter(int paramInt, CapitalizationHint paramCapitalizationHint, VerbatimMode paramVerbatimMode, PredictionMode paramPredictionMode, CorrectionMode paramCorrectionMode, PredictionSearchType paramPredictionSearchType)
  {
    this.mnTotal = paramInt;
    this.mcapitalization = paramCapitalizationHint;
    this.mverbatim = paramVerbatimMode;
    this.mprediction = paramPredictionMode;
    this.mcorrection = paramCorrectionMode;
    this.msearchType = paramPredictionSearchType;
  }
  
  public ResultsFilter(int paramInt, CapitalizationHint paramCapitalizationHint, VerbatimMode paramVerbatimMode, PredictionSearchType paramPredictionSearchType)
  {
    this(paramInt, paramCapitalizationHint, paramVerbatimMode, PredictionMode.CURRENT_WORD_PREDICT, CorrectionMode.DEFAULT, paramPredictionSearchType);
  }
  
  static native void initIDs();
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof ResultsFilter;
    boolean bool2 = false;
    if (bool1)
    {
      ResultsFilter localResultsFilter = (ResultsFilter)paramObject;
      int i = this.mnTotal;
      int j = localResultsFilter.mnTotal;
      bool2 = false;
      if (i == j)
      {
        boolean bool3 = this.mcapitalization.equals(localResultsFilter.mcapitalization);
        bool2 = false;
        if (bool3)
        {
          boolean bool4 = this.mverbatim.equals(localResultsFilter.mverbatim);
          bool2 = false;
          if (bool4)
          {
            boolean bool5 = this.mprediction.equals(localResultsFilter.mprediction);
            bool2 = false;
            if (bool5)
            {
              boolean bool6 = this.mcorrection.equals(localResultsFilter.mcorrection);
              bool2 = false;
              if (bool6)
              {
                boolean bool7 = this.msearchType.equals(localResultsFilter.msearchType);
                bool2 = false;
                if (bool7) {
                  bool2 = true;
                }
              }
            }
          }
        }
      }
    }
    return bool2;
  }
  
  public CapitalizationHint getCapitalization()
  {
    return this.mcapitalization;
  }
  
  public CorrectionMode getCorrection()
  {
    return this.mcorrection;
  }
  
  public PredictionMode getPrediction()
  {
    return this.mprediction;
  }
  
  public PredictionSearchType getPredictionSearchType()
  {
    return this.msearchType;
  }
  
  public int getTotal()
  {
    return this.mnTotal;
  }
  
  public VerbatimMode getVerbatim()
  {
    return this.mverbatim;
  }
  
  public int hashCode()
  {
    return 149 * (this.mnTotal + 149 * (this.mcapitalization.hashCode() + 149 * (this.mverbatim.hashCode() + 149 * (this.mprediction.hashCode() + 149 * (this.mcorrection.hashCode() + 149 * (149 + this.msearchType.hashCode()))))));
  }
  
  public String toString()
  {
    Object[] arrayOfObject = new Object[6];
    arrayOfObject[0] = Integer.valueOf(this.mnTotal);
    arrayOfObject[1] = this.mcapitalization;
    arrayOfObject[2] = this.mverbatim;
    arrayOfObject[3] = this.mprediction;
    arrayOfObject[4] = this.mcorrection;
    arrayOfObject[5] = this.msearchType;
    return String.format("Total: %d, %s, %s, %s, %s, %s", arrayOfObject);
  }
  
  public ResultsFilter with(CapitalizationHint paramCapitalizationHint)
  {
    return new ResultsFilter(this.mnTotal, paramCapitalizationHint, this.mverbatim, this.mprediction, this.mcorrection, this.msearchType);
  }
  
  public ResultsFilter with(CorrectionMode paramCorrectionMode)
  {
    return new ResultsFilter(this.mnTotal, this.mcapitalization, this.mverbatim, this.mprediction, paramCorrectionMode, this.msearchType);
  }
  
  public ResultsFilter with(PredictionMode paramPredictionMode)
  {
    return new ResultsFilter(this.mnTotal, this.mcapitalization, this.mverbatim, paramPredictionMode, this.mcorrection, this.msearchType);
  }
  
  public ResultsFilter with(PredictionSearchType paramPredictionSearchType)
  {
    return new ResultsFilter(this.mnTotal, this.mcapitalization, this.mverbatim, this.mprediction, this.mcorrection, paramPredictionSearchType);
  }
  
  public ResultsFilter with(VerbatimMode paramVerbatimMode)
  {
    return new ResultsFilter(this.mnTotal, this.mcapitalization, paramVerbatimMode, this.mprediction, this.mcorrection, this.msearchType);
  }
  
  public ResultsFilter withTotal(int paramInt)
  {
    return new ResultsFilter(paramInt, this.mcapitalization, this.mverbatim, this.mprediction, this.mcorrection, this.msearchType);
  }
  
  public static enum CapitalizationHint
  {
    static
    {
      INITIAL_UPPER_CASE = new CapitalizationHint("INITIAL_UPPER_CASE", 1);
      UPPER_CASE = new CapitalizationHint("UPPER_CASE", 2);
      FORCE_LOWER_CASE = new CapitalizationHint("FORCE_LOWER_CASE", 3);
      CapitalizationHint[] arrayOfCapitalizationHint = new CapitalizationHint[4];
      arrayOfCapitalizationHint[0] = LOWER_CASE;
      arrayOfCapitalizationHint[1] = INITIAL_UPPER_CASE;
      arrayOfCapitalizationHint[2] = UPPER_CASE;
      arrayOfCapitalizationHint[3] = FORCE_LOWER_CASE;
      $VALUES = arrayOfCapitalizationHint;
    }
    
    private CapitalizationHint() {}
  }
  
  public static enum CorrectionMode
  {
    static
    {
      AGGRESSIVE = new CorrectionMode("AGGRESSIVE", 1);
      CorrectionMode[] arrayOfCorrectionMode = new CorrectionMode[2];
      arrayOfCorrectionMode[0] = DEFAULT;
      arrayOfCorrectionMode[1] = AGGRESSIVE;
      $VALUES = arrayOfCorrectionMode;
    }
    
    private CorrectionMode() {}
  }
  
  public static enum PredictionMode
  {
    static
    {
      PredictionMode[] arrayOfPredictionMode = new PredictionMode[2];
      arrayOfPredictionMode[0] = CURRENT_WORD_PREDICT;
      arrayOfPredictionMode[1] = RETROSPECTIVE_CORRECT;
      $VALUES = arrayOfPredictionMode;
    }
    
    private PredictionMode() {}
  }
  
  public static enum PredictionSearchType
  {
    static
    {
      CANGJIE = new PredictionSearchType("CANGJIE", 3);
      STROKE = new PredictionSearchType("STROKE", 4);
      JAPANESE = new PredictionSearchType("JAPANESE", 5);
      PredictionSearchType[] arrayOfPredictionSearchType = new PredictionSearchType[6];
      arrayOfPredictionSearchType[0] = NORMAL;
      arrayOfPredictionSearchType[1] = PINYIN;
      arrayOfPredictionSearchType[2] = ZHUYIN;
      arrayOfPredictionSearchType[3] = CANGJIE;
      arrayOfPredictionSearchType[4] = STROKE;
      arrayOfPredictionSearchType[5] = JAPANESE;
      $VALUES = arrayOfPredictionSearchType;
    }
    
    private PredictionSearchType() {}
  }
  
  public static enum VerbatimMode
  {
    static
    {
      VerbatimMode[] arrayOfVerbatimMode = new VerbatimMode[2];
      arrayOfVerbatimMode[0] = DISABLED;
      arrayOfVerbatimMode[1] = ENABLED;
      $VALUES = arrayOfVerbatimMode;
    }
    
    private VerbatimMode() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.ResultsFilter
 * JD-Core Version:    0.7.0.1
 */