package com.touchtype_fluency;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public abstract interface Trainer
{
  public abstract void addSequence(Sequence paramSequence);
  
  public abstract void addSequence(Sequence paramSequence, TagSelector paramTagSelector);
  
  public abstract void addTermMapping(String paramString1, String paramString2);
  
  public abstract void addTermMapping(String paramString1, String paramString2, TagSelector paramTagSelector);
  
  public abstract String getBlacklist();
  
  public abstract Map<List<String>, Long> getNgramCounts();
  
  public abstract Map<List<String>, Long> getNgramCounts(TagSelector paramTagSelector);
  
  public abstract Map<String, Long> getTermCounts();
  
  public abstract Map<String, Long> getTermCounts(TagSelector paramTagSelector);
  
  public abstract String[] getTermsFromThreshold(long paramLong);
  
  public abstract void learnFrom(TouchHistory paramTouchHistory, Prediction paramPrediction);
  
  public abstract void learnFrom(TouchHistory paramTouchHistory, String[] paramArrayOfString);
  
  public abstract void learnMappings();
  
  public abstract void learnMappings(TagSelector paramTagSelector);
  
  public abstract void learnMappingsFrom(Prediction paramPrediction);
  
  public abstract void learnMappingsFrom(Prediction paramPrediction, ResultsFilter.PredictionSearchType paramPredictionSearchType);
  
  public abstract void learnMappingsFrom(Prediction paramPrediction, TagSelector paramTagSelector);
  
  public abstract void learnMappingsFrom(Prediction paramPrediction, TagSelector paramTagSelector, ResultsFilter.PredictionSearchType paramPredictionSearchType);
  
  public abstract void removeTerm(String paramString);
  
  public abstract void removeTerm(String paramString, TagSelector paramTagSelector);
  
  public abstract void setBlacklist(String paramString);
  
  public abstract void write()
    throws IOException;
  
  public abstract void write(TagSelector paramTagSelector)
    throws IOException;
  
  public abstract void write(TagSelector paramTagSelector, ModelFileVersion paramModelFileVersion)
    throws IOException;
  
  public abstract void write(ModelFileVersion paramModelFileVersion)
    throws IOException;
  
  public static enum ModelFileVersion
  {
    private final int versionNum;
    
    static
    {
      Earliest_Version = new ModelFileVersion("Earliest_Version", 4, Fluency_2_2.version());
      Latest_Version = new ModelFileVersion("Latest_Version", 5, SKSDK_1_3.version());
      ModelFileVersion[] arrayOfModelFileVersion = new ModelFileVersion[6];
      arrayOfModelFileVersion[0] = Fluency_2_2;
      arrayOfModelFileVersion[1] = SKSDK_1_0;
      arrayOfModelFileVersion[2] = SKSDK_1_0_1;
      arrayOfModelFileVersion[3] = SKSDK_1_3;
      arrayOfModelFileVersion[4] = Earliest_Version;
      arrayOfModelFileVersion[5] = Latest_Version;
      $VALUES = arrayOfModelFileVersion;
    }
    
    private ModelFileVersion(int paramInt)
    {
      this.versionNum = paramInt;
    }
    
    public int version()
    {
      return this.versionNum;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Trainer
 * JD-Core Version:    0.7.0.1
 */