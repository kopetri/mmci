package com.touchtype.report.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import java.util.List;
import java.util.Set;

final class Accuracy
{
  @SerializedName("plmm")
  private List<LanguageModelMetrics> mLanguageModelsStats;
  @SerializedName("total")
  private Total mTotal;
  
  public static Accuracy newInstance(TouchTypeStats paramTouchTypeStats)
  {
    Accuracy localAccuracy = new Accuracy();
    localAccuracy.mTotal = Total.newInstance(paramTouchTypeStats);
    localAccuracy.mLanguageModelsStats = paramTouchTypeStats.getAllLanguageModelMetrics();
    return localAccuracy;
  }
  
  static final class Total
  {
    @SerializedName("backspaceLongpress")
    private int mBackspaceLongpress;
    @SerializedName("backspaceFlowedWords")
    private int mBackspaceOnFlowedWord;
    @SerializedName("backspaces")
    private int mBackspacePresses;
    @SerializedName("candidateRanking")
    private JsonObject mCandidateRanking;
    @SerializedName("characters")
    private int mCharacters;
    @SerializedName("keystrokes")
    private int mKeystrokes;
    
    public static Total newInstance(TouchTypeStats paramTouchTypeStats)
    {
      Total localTotal = new Total();
      JsonObject localJsonObject = new JsonParser().parse(paramTouchTypeStats.getCandidateRankingsString()).getAsJsonObject();
      if (localJsonObject.entrySet().isEmpty()) {
        localJsonObject = null;
      }
      localTotal.mCandidateRanking = localJsonObject;
      localTotal.mBackspaceLongpress = paramTouchTypeStats.getStatisticInt("stats_backspace_longpress_uses");
      localTotal.mBackspaceOnFlowedWord = paramTouchTypeStats.getStatisticInt("stats_backspace_on_flowed_word");
      localTotal.mBackspacePresses = paramTouchTypeStats.getStatisticInt("stats_backspace_presses");
      localTotal.mCharacters = paramTouchTypeStats.getStatisticInt("stats_entered_characters");
      localTotal.mKeystrokes = paramTouchTypeStats.getStatisticInt("stats_key_strokes");
      return localTotal;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.report.json.Accuracy
 * JD-Core Version:    0.7.0.1
 */