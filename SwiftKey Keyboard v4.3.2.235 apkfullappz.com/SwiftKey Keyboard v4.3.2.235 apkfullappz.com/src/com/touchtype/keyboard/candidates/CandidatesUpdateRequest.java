package com.touchtype.keyboard.candidates;

import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.PredictionSearchType;
import com.touchtype_fluency.Sequence;

public final class CandidatesUpdateRequest
{
  private final ResultsFilter.CapitalizationHint mCapitalizationAtStart;
  private final boolean mDumbInputMode;
  private final ResultsFilter.PredictionSearchType mSearchType;
  private final Sequence mSequence;
  private final TouchHistoryProxy mTouchHistory;
  private final CandidateUtil.VerbatimCandidatePosition mVerbatimInsertionBehaviour;
  private final String mVerbatimWord;
  
  public CandidatesUpdateRequest(Sequence paramSequence, String paramString, CandidateUtil.VerbatimCandidatePosition paramVerbatimCandidatePosition, TouchHistoryProxy paramTouchHistoryProxy, ResultsFilter.CapitalizationHint paramCapitalizationHint, boolean paramBoolean, ResultsFilter.PredictionSearchType paramPredictionSearchType)
  {
    this.mSequence = paramSequence;
    this.mVerbatimWord = paramString;
    this.mTouchHistory = paramTouchHistoryProxy;
    this.mCapitalizationAtStart = paramCapitalizationHint;
    this.mDumbInputMode = paramBoolean;
    this.mVerbatimInsertionBehaviour = paramVerbatimCandidatePosition;
    this.mSearchType = paramPredictionSearchType;
  }
  
  public ResultsFilter.CapitalizationHint getCapitalizationAtStart()
  {
    return this.mCapitalizationAtStart;
  }
  
  public boolean getDumbInputMode()
  {
    return this.mDumbInputMode;
  }
  
  public ResultsFilter.PredictionSearchType getSearchType()
  {
    return this.mSearchType;
  }
  
  public Sequence getSequence()
  {
    return this.mSequence;
  }
  
  public TouchHistoryProxy getTouchHistory()
  {
    return this.mTouchHistory;
  }
  
  public String getVerbatimWord()
  {
    return this.mVerbatimWord;
  }
  
  public CandidateUtil.VerbatimCandidatePosition verbatimInsertionBehaviour()
  {
    return this.mVerbatimInsertionBehaviour;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidatesUpdateRequest
 * JD-Core Version:    0.7.0.1
 */