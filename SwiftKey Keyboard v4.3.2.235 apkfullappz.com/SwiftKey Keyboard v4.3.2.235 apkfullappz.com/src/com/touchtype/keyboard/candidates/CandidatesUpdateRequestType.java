package com.touchtype.keyboard.candidates;

public enum CandidatesUpdateRequestType
{
  static
  {
    HARD = new CandidatesUpdateRequestType("HARD", 2);
    FLOW = new CandidatesUpdateRequestType("FLOW", 3);
    LAST_USED = new CandidatesUpdateRequestType("LAST_USED", 4);
    FLOW_FAILED = new CandidatesUpdateRequestType("FLOW_FAILED", 5);
    FLOW_SUCCEEDED = new CandidatesUpdateRequestType("FLOW_SUCCEEDED", 6);
    ASIAN = new CandidatesUpdateRequestType("ASIAN", 7);
    CandidatesUpdateRequestType[] arrayOfCandidatesUpdateRequestType = new CandidatesUpdateRequestType[8];
    arrayOfCandidatesUpdateRequestType[0] = DEFAULT;
    arrayOfCandidatesUpdateRequestType[1] = TAP;
    arrayOfCandidatesUpdateRequestType[2] = HARD;
    arrayOfCandidatesUpdateRequestType[3] = FLOW;
    arrayOfCandidatesUpdateRequestType[4] = LAST_USED;
    arrayOfCandidatesUpdateRequestType[5] = FLOW_FAILED;
    arrayOfCandidatesUpdateRequestType[6] = FLOW_SUCCEEDED;
    arrayOfCandidatesUpdateRequestType[7] = ASIAN;
    $VALUES = arrayOfCandidatesUpdateRequestType;
  }
  
  private CandidatesUpdateRequestType() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidatesUpdateRequestType
 * JD-Core Version:    0.7.0.1
 */