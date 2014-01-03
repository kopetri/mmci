package com.touchtype_fluency.internal;

import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.KeyPressModel;
import com.touchtype_fluency.ModelSetDescription;
import com.touchtype_fluency.ParameterSet;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.Predictor;
import com.touchtype_fluency.Predictor.SearchType;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.SentenceSegmenter;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.TagSelector;
import com.touchtype_fluency.TagSelectors;
import com.touchtype_fluency.Tokenizer;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.Trainer;
import com.touchtype_fluency.Trainer.ModelFileVersion;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class PredictorImpl
  implements Predictor, Session, Trainer
{
  private List<Disposable> disposableRefs = Collections.synchronizedList(new ArrayList());
  private long peer;
  
  private native CharacterMapImpl getCharacterMapImpl();
  
  private native KeyPressModelImpl getKeyPressModelImpl();
  
  private native ParameterSetImpl getParameterSetImpl();
  
  private native PunctuatorImpl getPunctuatorImpl();
  
  private native int getSearchTypeInt();
  
  private native SentenceSegmenterImpl getSentenceSegmenterImpl();
  
  private native TokenizerImpl getTokenizerImpl();
  
  private Disposable handout(Disposable paramDisposable)
  {
    this.disposableRefs.add(paramDisposable);
    return paramDisposable;
  }
  
  public static native void initIDs();
  
  public void addSequence(Sequence paramSequence)
  {
    addSequence(paramSequence, TagSelectors.enabledModels());
  }
  
  public native void addSequence(Sequence paramSequence, TagSelector paramTagSelector);
  
  public void addTermMapping(String paramString1, String paramString2)
  {
    addTermMapping(paramString1, paramString2, TagSelectors.enabledModels());
  }
  
  public native void addTermMapping(String paramString1, String paramString2, TagSelector paramTagSelector);
  
  public native void batchLoad(ModelSetDescription[] paramArrayOfModelSetDescription)
    throws IOException;
  
  public native void batchUnload(ModelSetDescription[] paramArrayOfModelSetDescription);
  
  public native void clearLayoutKeys();
  
  public void dispose()
  {
    Iterator localIterator = this.disposableRefs.iterator();
    while (localIterator.hasNext()) {
      ((Disposable)localIterator.next()).dispose();
    }
    this.disposableRefs.clear();
    disposeInternal();
  }
  
  public native void disposeInternal();
  
  public native void enableModels(TagSelector paramTagSelector);
  
  public native Predictions get(Sequence paramSequence1, TouchHistory paramTouchHistory, Sequence paramSequence2, ResultsFilter paramResultsFilter);
  
  public native String getBlacklist();
  
  public CharacterMap getCharacterMap()
  {
    return (CharacterMap)handout(getCharacterMapImpl());
  }
  
  public native Predictions getCorrections(Sequence paramSequence1, TouchHistory paramTouchHistory, Sequence paramSequence2, ResultsFilter paramResultsFilter);
  
  public KeyPressModel getKeyPressModel()
  {
    return (KeyPressModel)handout(getKeyPressModelImpl());
  }
  
  public native ModelSetDescription[] getLoadedSets();
  
  public native String getMostLikelyLanguage(Sequence paramSequence);
  
  public Map<List<String>, Long> getNgramCounts()
  {
    return getNgramCounts(TagSelectors.enabledModels());
  }
  
  public native Map<List<String>, Long> getNgramCounts(TagSelector paramTagSelector);
  
  public ParameterSet getParameterSet()
  {
    return (ParameterSet)handout(getParameterSetImpl());
  }
  
  public native Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter);
  
  public Predictor getPredictor()
  {
    return this;
  }
  
  public Punctuator getPunctuator()
  {
    return (Punctuator)handout(getPunctuatorImpl());
  }
  
  public Predictor.SearchType getSearchType()
  {
    return Predictor.SearchType.values()[getSearchTypeInt()];
  }
  
  public SentenceSegmenter getSentenceSegmenter()
  {
    return (SentenceSegmenter)handout(getSentenceSegmenterImpl());
  }
  
  public String[] getTags()
  {
    return getTags(TagSelectors.allModels());
  }
  
  public native String[] getTags(TagSelector paramTagSelector);
  
  public Map<String, Long> getTermCounts()
  {
    return getTermCounts(TagSelectors.enabledModels());
  }
  
  public native Map<String, Long> getTermCounts(TagSelector paramTagSelector);
  
  public String[] getTermsFromThreshold(long paramLong)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = getTermCounts().entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      if (((Long)localEntry.getValue()).longValue() >= paramLong) {
        localArrayList.add(localEntry.getKey());
      }
    }
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public Tokenizer getTokenizer()
  {
    return (Tokenizer)handout(getTokenizerImpl());
  }
  
  public Trainer getTrainer()
  {
    return this;
  }
  
  public native void learnFrom(TouchHistory paramTouchHistory, Prediction paramPrediction);
  
  public native void learnFrom(TouchHistory paramTouchHistory, String[] paramArrayOfString);
  
  public void learnMappings()
  {
    learnMappings(TagSelectors.enabledModels());
  }
  
  public native void learnMappings(TagSelector paramTagSelector);
  
  public void learnMappingsFrom(Prediction paramPrediction)
  {
    learnMappingsFrom(paramPrediction, TagSelectors.enabledModels());
  }
  
  public void learnMappingsFrom(Prediction paramPrediction, ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    learnMappingsFrom(paramPrediction, TagSelectors.enabledModels(), paramPredictionSearchType);
  }
  
  public void learnMappingsFrom(Prediction paramPrediction, TagSelector paramTagSelector)
  {
    learnMappingsFrom(paramPrediction, paramTagSelector, ResultsFilter.PredictionSearchType.NORMAL);
  }
  
  public native void learnMappingsFrom(Prediction paramPrediction, TagSelector paramTagSelector, ResultsFilter.PredictionSearchType paramPredictionSearchType);
  
  public native void load(ModelSetDescription paramModelSetDescription)
    throws IOException;
  
  public boolean queryTerm(String paramString)
  {
    return queryTerm(paramString, TagSelectors.enabledModels(), "");
  }
  
  public boolean queryTerm(String paramString, TagSelector paramTagSelector)
  {
    return queryTerm(paramString, paramTagSelector, "");
  }
  
  public native boolean queryTerm(String paramString1, TagSelector paramTagSelector, String paramString2);
  
  public void removeTerm(String paramString)
  {
    removeTerm(paramString, TagSelectors.allModels());
  }
  
  public native void removeTerm(String paramString, TagSelector paramTagSelector);
  
  public native void setBlacklist(String paramString);
  
  public native void setIntentionalEvents(Set<String> paramSet);
  
  public native void setLayoutKeys(Set<String> paramSet);
  
  public native void setParameterLearning(boolean paramBoolean);
  
  public native void setSearchType(Predictor.SearchType paramSearchType);
  
  public native void unload(ModelSetDescription paramModelSetDescription);
  
  public native void verify(ModelSetDescription paramModelSetDescription)
    throws IOException;
  
  public void write()
    throws IOException
  {
    write(TagSelectors.allModels(), Trainer.ModelFileVersion.Latest_Version);
  }
  
  public void write(TagSelector paramTagSelector)
    throws IOException
  {
    write(paramTagSelector, Trainer.ModelFileVersion.Latest_Version);
  }
  
  public native void write(TagSelector paramTagSelector, Trainer.ModelFileVersion paramModelFileVersion)
    throws IOException;
  
  public void write(Trainer.ModelFileVersion paramModelFileVersion)
    throws IOException
  {
    write(TagSelectors.allModels(), paramModelFileVersion);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.PredictorImpl
 * JD-Core Version:    0.7.0.1
 */