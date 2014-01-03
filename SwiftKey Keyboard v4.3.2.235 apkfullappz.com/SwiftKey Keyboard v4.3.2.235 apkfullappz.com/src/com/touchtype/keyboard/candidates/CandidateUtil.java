package com.touchtype.keyboard.candidates;

import com.touchtype_fluency.Prediction;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class CandidateUtil
{
  private static Candidate candidateFrom(List<Prediction> paramList, int paramInt, CharSequence paramCharSequence)
  {
    if (paramInt < paramList.size()) {
      return Candidate.fromFluency((Prediction)paramList.get(paramInt), Candidate.Ranking.getRanking(paramInt), paramCharSequence);
    }
    return Candidate.empty();
  }
  
  public static List<Candidate> getCandidatesFromPredictions(List<Prediction> paramList, CharSequence paramCharSequence, VerbatimCandidatePosition paramVerbatimCandidatePosition, boolean paramBoolean)
  {
    ArrayList localArrayList = new ArrayList();
    switch (1.$SwitchMap$com$touchtype$keyboard$candidates$CandidateUtil$VerbatimCandidatePosition[paramVerbatimCandidatePosition.ordinal()])
    {
    }
    for (;;)
    {
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        ((Candidate)localIterator.next()).setUsingZeroWidthSpace(paramBoolean);
      }
      for (int i = 0; i < paramList.size(); i++) {
        localArrayList.add(candidateFrom(paramList, i, paramCharSequence));
      }
      insertVerbatimFirst(paramList, paramCharSequence, localArrayList);
      continue;
      insertVerbatimSecond(paramList, paramCharSequence, localArrayList);
    }
    return localArrayList;
  }
  
  private static void insertVerbatimFirst(List<Prediction> paramList, CharSequence paramCharSequence, List<Candidate> paramList1)
  {
    if (paramList.isEmpty()) {
      paramList1.add(Candidate.verbatim(paramCharSequence));
    }
    int i;
    do
    {
      return;
      i = -1;
      for (int j = 0; j < paramList.size(); j++)
      {
        paramList1.add(candidateFrom(paramList, j, paramCharSequence));
        if (((Candidate)paramList1.get(j)).getType() == Candidate.Type.TRUE_VERBATIM) {
          i = j;
        }
      }
    } while ((paramCharSequence.length() == 0) || (i == 0));
    if (i != -1)
    {
      paramList1.add(0, (Candidate)paramList1.remove(i));
      return;
    }
    paramList1.add(0, Candidate.verbatim(paramCharSequence));
  }
  
  private static void insertVerbatimSecond(List<Prediction> paramList, CharSequence paramCharSequence, List<Candidate> paramList1)
  {
    if (paramList.isEmpty()) {
      paramList1.add(Candidate.verbatim(paramCharSequence));
    }
    do
    {
      return;
      for (int i = 0; i < paramList.size(); i++) {
        paramList1.add(candidateFrom(paramList, i, paramCharSequence));
      }
    } while ((paramCharSequence.length() == 0) || (isVerbatimPredicted(paramCharSequence, paramList1, 2)));
    if (((Candidate)paramList1.get(0)).isFluencyVerbatim())
    {
      paramList1.add(0, Candidate.verbatim(paramCharSequence));
      return;
    }
    paramList1.add(1, Candidate.verbatim(paramCharSequence));
  }
  
  private static boolean isVerbatimPredicted(CharSequence paramCharSequence, List<Candidate> paramList, int paramInt)
  {
    for (int i = 0; (i < paramList.size()) && (i < paramInt); i++) {
      if (((Candidate)paramList.get(i)).getType() == Candidate.Type.TRUE_VERBATIM) {
        return true;
      }
    }
    return false;
  }
  
  public static enum VerbatimCandidatePosition
  {
    static
    {
      VERBATIM_FIRST = new VerbatimCandidatePosition("VERBATIM_FIRST", 2);
      VerbatimCandidatePosition[] arrayOfVerbatimCandidatePosition = new VerbatimCandidatePosition[3];
      arrayOfVerbatimCandidatePosition[0] = NO_VERBATIM;
      arrayOfVerbatimCandidatePosition[1] = VERBATIM_SECOND;
      arrayOfVerbatimCandidatePosition[2] = VERBATIM_FIRST;
      $VALUES = arrayOfVerbatimCandidatePosition;
    }
    
    private VerbatimCandidatePosition() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidateUtil
 * JD-Core Version:    0.7.0.1
 */