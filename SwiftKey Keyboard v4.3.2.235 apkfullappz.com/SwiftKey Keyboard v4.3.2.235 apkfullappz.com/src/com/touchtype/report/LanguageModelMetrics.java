package com.touchtype.report;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class LanguageModelMetrics
{
  @SerializedName("correctedWords")
  private int mCorrectedWords = 0;
  @SerializedName("enteredCharacters")
  private int mEnteredCharacters = 0;
  @SerializedName("flowAutocommit")
  private int mFlowAutocommit = 0;
  @SerializedName("flowEarlyLift")
  private int mFlowEarlyLift = 0;
  @SerializedName("flowThroughSpace")
  private int mFlowThroughSpace = 0;
  @SerializedName("flowedCharacters")
  private int mFlowedCharacters = 0;
  @SerializedName("flowedWords")
  private int mFlowedWords = 0;
  @SerializedName("nextWordPredictions")
  private int mNextWordPredictions = 0;
  @SerializedName("predictedWords")
  private int mPredictedWords = 0;
  @SerializedName("selectedPredictions")
  private int mSelectedPredictions;
  @SerializedName("shownWords")
  private int mShownWords = 0;
  @SerializedName("source")
  private String mSource = null;
  @SerializedName("spacesInference")
  private int mSpacesInference = 0;
  @SerializedName("totalKeystrokes")
  private int mTotalKeystrokes = 0;
  @SerializedName("totalWords")
  private int mTotalWords = 0;
  @SerializedName("version")
  private int mVersion = 0;
  
  public LanguageModelMetrics() {}
  
  public LanguageModelMetrics(String paramString, int paramInt)
  {
    this();
    this.mSource = paramString;
    this.mVersion = paramInt;
  }
  
  public String getSource()
  {
    return this.mSource;
  }
  
  public void incrementCorrectedWords()
  {
    this.mCorrectedWords = (1 + this.mCorrectedWords);
  }
  
  public void incrementEnteredCharactersBy(int paramInt)
  {
    this.mEnteredCharacters = (paramInt + this.mEnteredCharacters);
  }
  
  public void incrementFlowAutocommit()
  {
    this.mFlowAutocommit = (1 + this.mFlowAutocommit);
  }
  
  public void incrementFlowEarlyLift()
  {
    this.mFlowEarlyLift = (1 + this.mFlowEarlyLift);
  }
  
  public void incrementFlowThroughSpace()
  {
    this.mFlowThroughSpace = (1 + this.mFlowThroughSpace);
  }
  
  public void incrementFlowedCharactersBy(int paramInt)
  {
    this.mFlowedCharacters = (paramInt + this.mFlowedCharacters);
  }
  
  public void incrementFlowedWordsBy(int paramInt)
  {
    this.mFlowedWords = (paramInt + this.mFlowedWords);
  }
  
  public void incrementNextWordPredictions()
  {
    this.mNextWordPredictions = (1 + this.mNextWordPredictions);
  }
  
  public void incrementPredictedWords()
  {
    this.mPredictedWords = (1 + this.mPredictedWords);
  }
  
  public void incrementSelectedPredictions()
  {
    this.mSelectedPredictions = (1 + this.mSelectedPredictions);
  }
  
  public void incrementShownWords()
  {
    this.mShownWords = (1 + this.mShownWords);
  }
  
  public void incrementSpacesInferenceBy(int paramInt)
  {
    this.mSpacesInference = (paramInt + this.mSpacesInference);
  }
  
  public void incrementTotalKeystrokesBy(int paramInt)
  {
    this.mTotalKeystrokes = (paramInt + this.mTotalKeystrokes);
  }
  
  public void incrementTotalWordsBy(int paramInt)
  {
    this.mTotalWords = (paramInt + this.mTotalWords);
  }
  
  public String toJSON()
  {
    return new Gson().toJson(this, LanguageModelMetrics.class);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.LanguageModelMetrics
 * JD-Core Version:    0.7.0.1
 */