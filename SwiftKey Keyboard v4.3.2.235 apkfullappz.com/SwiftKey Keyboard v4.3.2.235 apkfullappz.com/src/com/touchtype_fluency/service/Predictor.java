package com.touchtype_fluency.service;

import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.ContextCurrentWord;
import com.touchtype_fluency.KeyPressModel;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Sequence.Type;
import com.touchtype_fluency.TouchHistory;
import java.util.Set;

public abstract interface Predictor
{
  public abstract void addListener(PredictorListener paramPredictorListener);
  
  public abstract void addToTemporaryModel(String paramString)
    throws PredictorNotReadyException;
  
  public abstract void addToUserModel(Sequence paramSequence)
    throws PredictorNotReadyException;
  
  public abstract void addToUserModel(String paramString1, Sequence.Type paramType, String paramString2)
    throws PredictorNotReadyException;
  
  public abstract void batchAddToUserModel(String paramString1, Sequence.Type paramType, String paramString2)
    throws PredictorNotReadyException;
  
  public abstract void clearLayoutKeys();
  
  public abstract void clearUserModel()
    throws PredictorNotReadyException;
  
  public abstract Sequence filterForget(Sequence paramSequence);
  
  public abstract CharacterMap getCharacterMap();
  
  public abstract KeyPressModel getKeyPressModel();
  
  public abstract Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter)
    throws PredictorNotReadyException;
  
  public abstract FluencyPreferences getPreferences();
  
  public abstract boolean isReady();
  
  public abstract void learnFrom(TouchHistory paramTouchHistory, Prediction paramPrediction)
    throws PredictorNotReadyException;
  
  public abstract void learnMappings()
    throws PredictorNotReadyException;
  
  public abstract void learnMappingsFrom(Prediction paramPrediction, ResultsFilter.PredictionSearchType paramPredictionSearchType)
    throws PredictorNotReadyException;
  
  public abstract void mergeUserModel(String paramString)
    throws PredictorNotReadyException;
  
  public abstract boolean queryTerm(String paramString)
    throws PredictorNotReadyException;
  
  public abstract void reloadCharacterMaps();
  
  public abstract void reloadLanguagePacks();
  
  public abstract void removeFromTemporaryModel(String paramString)
    throws PredictorNotReadyException;
  
  public abstract void removeListener(PredictorListener paramPredictorListener);
  
  public abstract void removeTerm(String paramString)
    throws PredictorNotReadyException;
  
  public abstract void removeTerms(Iterable<String> paramIterable)
    throws PredictorNotReadyException;
  
  public abstract void saveUserModel()
    throws PredictorNotReadyException;
  
  public abstract void setInputType(String paramString);
  
  public abstract void setIntentionalEvents(Set<String> paramSet);
  
  public abstract void setLayoutKeys(Set<String> paramSet);
  
  public abstract Sequence split(String paramString);
  
  public abstract ContextCurrentWord splitContextCurrentWord(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.Predictor
 * JD-Core Version:    0.7.0.1
 */