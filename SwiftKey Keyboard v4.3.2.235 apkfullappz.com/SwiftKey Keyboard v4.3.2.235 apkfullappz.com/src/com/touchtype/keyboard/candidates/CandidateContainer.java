package com.touchtype.keyboard.candidates;

import com.touchtype.keyboard.TouchHistoryProxy;
import java.util.List;
import java.util.Map;

public final class CandidateContainer
{
  private final List<Candidate> mCandidates;
  private final boolean mDumbMode;
  private final CandidatesUpdateRequestType mInputEventType;
  private final Map<String, String> mTags;
  private final TouchHistoryProxy mTouchHistory;
  
  public CandidateContainer(List<Candidate> paramList, boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType, TouchHistoryProxy paramTouchHistoryProxy, Map<String, String> paramMap)
  {
    this.mCandidates = paramList;
    this.mDumbMode = paramBoolean;
    this.mInputEventType = paramCandidatesUpdateRequestType;
    this.mTouchHistory = paramTouchHistoryProxy;
    this.mTags = paramMap;
  }
  
  public List<Candidate> getCandidates()
  {
    return this.mCandidates;
  }
  
  public boolean getDumbMode()
  {
    return this.mDumbMode;
  }
  
  public CandidatesUpdateRequestType getEventType()
  {
    return this.mInputEventType;
  }
  
  public Map<String, String> getTags()
  {
    return this.mTags;
  }
  
  public Candidate getTopCandidate()
  {
    if (this.mCandidates.size() > 0) {
      return (Candidate)this.mCandidates.get(0);
    }
    return Candidate.empty();
  }
  
  public TouchHistoryProxy getTouchHistory()
  {
    return this.mTouchHistory;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidateContainer
 * JD-Core Version:    0.7.0.1
 */