package com.touchtype.keyboard.candidates;

import com.touchtype.keyboard.TouchHistoryProxy;
import com.touchtype.keyboard.concurrent.BackgroundExecutor;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.FileCorruptException;
import com.touchtype_fluency.Prediction;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.ResultsFilter.CapitalizationHint;
import com.touchtype_fluency.ResultsFilter.VerbatimMode;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.service.FluencyServiceProxyI;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;

public class CandidatesUpdater
{
  private static final String TAG = CandidatesUpdater.class.getSimpleName();
  private Future<List<Candidate>> lastUpdateRequest;
  private Set<UpdatedCandidatesListener> listeners = new HashSet();
  private final BackgroundExecutor mBackgroundExecutor;
  private final FluencyServiceProxyI mFluencyProxy;
  private final Executor mForegroundExecutor;
  private CandidatesUpdateRequestType mLastEventType;
  private int mNumberOfPredictions = 1;
  private final UptoDateChecker mUptoDateChecker = new UptoDateChecker(null);
  private boolean mUsingZeroWidthSpace = false;
  
  public CandidatesUpdater(FluencyServiceProxyI paramFluencyServiceProxyI, BackgroundExecutor paramBackgroundExecutor, Executor paramExecutor)
  {
    this.mFluencyProxy = paramFluencyServiceProxyI;
    this.mBackgroundExecutor = paramBackgroundExecutor;
    this.mForegroundExecutor = paramExecutor;
  }
  
  private void update(UpdateCandidatesTask paramUpdateCandidatesTask, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if ((this.lastUpdateRequest != null) && (this.mLastEventType != CandidatesUpdateRequestType.FLOW_FAILED)) {
      this.lastUpdateRequest.cancel(false);
    }
    this.mLastEventType = paramCandidatesUpdateRequestType;
    this.lastUpdateRequest = this.mBackgroundExecutor.submit(paramUpdateCandidatesTask);
  }
  
  public void addListener(UpdatedCandidatesListener paramUpdatedCandidatesListener)
  {
    this.listeners.add(paramUpdatedCandidatesListener);
  }
  
  public void addListener(UpdatedCandidatesListener paramUpdatedCandidatesListener, int paramInt, CandidatesUpdateRequest paramCandidatesUpdateRequest)
  {
    this.listeners.add(paramUpdatedCandidatesListener);
    this.mNumberOfPredictions = Math.max(paramInt, this.mNumberOfPredictions);
    refresh(true, CandidatesUpdateRequestType.DEFAULT, paramCandidatesUpdateRequest);
  }
  
  public Candidate getUpdatedTopCandidate(CandidatesUpdateRequestType paramCandidatesUpdateRequestType, CandidatesUpdateRequestFactory paramCandidatesUpdateRequestFactory)
  {
    for (;;)
    {
      try
      {
        List localList = (List)this.lastUpdateRequest.get();
        if ((localList == null) || (localList.isEmpty())) {
          return Candidate.empty();
        }
        Candidate localCandidate = (Candidate)localList.get(0);
        return localCandidate;
      }
      catch (InterruptedException localInterruptedException)
      {
        return Candidate.empty();
      }
      catch (ExecutionException localExecutionException)
      {
        LogUtil.e(TAG, "ExecutionException: " + localExecutionException);
        refresh(true, paramCandidatesUpdateRequestType, paramCandidatesUpdateRequestFactory.createRequest());
      }
      catch (CancellationException localCancellationException) {}
    }
  }
  
  public void refresh(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType, CandidatesUpdateRequest paramCandidatesUpdateRequest)
  {
    update(new UpdateCandidatesTask(this.mForegroundExecutor, this.mFluencyProxy, paramCandidatesUpdateRequestType, paramCandidatesUpdateRequest, this.mNumberOfPredictions, this.listeners, this.mUptoDateChecker, paramBoolean, this.mUsingZeroWidthSpace, null), paramCandidatesUpdateRequestType);
  }
  
  public void removeListener(UpdatedCandidatesListener paramUpdatedCandidatesListener)
  {
    this.listeners.remove(paramUpdatedCandidatesListener);
  }
  
  public void setCharacterMapLayout(final String paramString)
  {
    this.mFluencyProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        CharacterMap localCharacterMap = CandidatesUpdater.this.mFluencyProxy.getPredictor().getCharacterMap();
        if (localCharacterMap == null)
        {
          LogUtil.w(CandidatesUpdater.TAG, "Charactermap unavailable");
          return;
        }
        try
        {
          localCharacterMap.setLayout(paramString);
          return;
        }
        catch (FileCorruptException localFileCorruptException)
        {
          LogUtil.e(CandidatesUpdater.TAG, "Invalid 12-key JSON character map");
        }
      }
    });
  }
  
  public void setInputType(final String paramString)
  {
    this.mFluencyProxy.runWhenConnected(new Runnable()
    {
      public void run()
      {
        CandidatesUpdater.this.mFluencyProxy.getPredictor().setInputType(paramString);
      }
    });
  }
  
  public void setUsingZeroWidthSpace(boolean paramBoolean)
  {
    this.mUsingZeroWidthSpace = paramBoolean;
  }
  
  private static final class TouchHistoryHashAndSize
  {
    private final int mHash;
    private final int mSize;
    
    public TouchHistoryHashAndSize(TouchHistory paramTouchHistory)
    {
      this.mHash = paramTouchHistory.hashCode();
      this.mSize = paramTouchHistory.size();
    }
    
    public boolean equals(Object paramObject)
    {
      boolean bool1 = paramObject instanceof TouchHistoryHashAndSize;
      boolean bool2 = false;
      if (bool1)
      {
        TouchHistoryHashAndSize localTouchHistoryHashAndSize = (TouchHistoryHashAndSize)paramObject;
        int i = this.mHash;
        int j = localTouchHistoryHashAndSize.mHash;
        bool2 = false;
        if (i == j)
        {
          int k = this.mSize;
          int m = localTouchHistoryHashAndSize.mSize;
          bool2 = false;
          if (k == m) {
            bool2 = true;
          }
        }
      }
      return bool2;
    }
  }
  
  private static final class UpdateCandidatesTask
    implements Callable<List<Candidate>>
  {
    private final CandidatesUpdateRequestType mEventType;
    private final FluencyServiceProxyI mFluencyProxy;
    private final boolean mForce;
    private final Executor mForegroundExecutor;
    private final Set<UpdatedCandidatesListener> mListeners;
    private final int mNumberOfPredictions;
    private final CandidatesUpdateRequest mRequest;
    private final CandidatesUpdater.UptoDateChecker mUptoDateChecker;
    private final boolean mUsingZeroWidthSpace;
    
    private UpdateCandidatesTask(Executor paramExecutor, FluencyServiceProxyI paramFluencyServiceProxyI, CandidatesUpdateRequestType paramCandidatesUpdateRequestType, CandidatesUpdateRequest paramCandidatesUpdateRequest, int paramInt, Set<UpdatedCandidatesListener> paramSet, CandidatesUpdater.UptoDateChecker paramUptoDateChecker, boolean paramBoolean1, boolean paramBoolean2)
    {
      this.mForegroundExecutor = paramExecutor;
      this.mFluencyProxy = paramFluencyServiceProxyI;
      this.mEventType = paramCandidatesUpdateRequestType;
      this.mNumberOfPredictions = paramInt;
      this.mListeners = paramSet;
      this.mRequest = paramCandidatesUpdateRequest;
      this.mUptoDateChecker = paramUptoDateChecker;
      this.mForce = paramBoolean1;
      this.mUsingZeroWidthSpace = paramBoolean2;
    }
    
    private boolean arePrerequisitesReady()
    {
      Predictor localPredictor = this.mFluencyProxy.getPredictor();
      return (this.mFluencyProxy.isReady()) && (localPredictor != null) && (localPredictor.isReady());
    }
    
    private List<Candidate> getPredictions(int paramInt, Sequence paramSequence, String paramString)
    {
      try
      {
        CandidateUtil.VerbatimCandidatePosition localVerbatimCandidatePosition1 = this.mRequest.verbatimInsertionBehaviour();
        CandidateUtil.VerbatimCandidatePosition localVerbatimCandidatePosition2 = CandidateUtil.VerbatimCandidatePosition.NO_VERBATIM;
        int i = 0;
        if (localVerbatimCandidatePosition1 != localVerbatimCandidatePosition2) {
          i = 1;
        }
        ResultsFilter.CapitalizationHint localCapitalizationHint = this.mRequest.getCapitalizationAtStart();
        if (i != 0) {}
        for (ResultsFilter.VerbatimMode localVerbatimMode = ResultsFilter.VerbatimMode.ENABLED;; localVerbatimMode = ResultsFilter.VerbatimMode.DISABLED)
        {
          ResultsFilter localResultsFilter = new ResultsFilter(paramInt, localCapitalizationHint, localVerbatimMode, this.mRequest.getSearchType());
          Object localObject = this.mFluencyProxy.getPredictions(paramSequence, this.mRequest.getTouchHistory().getTouchHistory(), localResultsFilter);
          if ((this.mEventType == CandidatesUpdateRequestType.FLOW) && (((List)localObject).size() > 0) && (((Prediction)((List)localObject).get(0)).getProbability() == 0.0D)) {
            localObject = new ArrayList();
          }
          return CandidateUtil.getCandidatesFromPredictions((List)localObject, paramString, this.mRequest.verbatimInsertionBehaviour(), this.mUsingZeroWidthSpace);
        }
        return null;
      }
      catch (PredictorNotReadyException localPredictorNotReadyException)
      {
        LogUtil.e(CandidatesUpdater.TAG, localPredictorNotReadyException.getMessage(), localPredictorNotReadyException);
      }
    }
    
    private void updateCandidates(final List<Candidate> paramList)
    {
      this.mForegroundExecutor.execute(new Runnable()
      {
        public void run()
        {
          if (paramList != null)
          {
            CandidateContainer localCandidateContainer = new CandidateContainer(paramList, CandidatesUpdater.UpdateCandidatesTask.this.mRequest.getDumbInputMode(), CandidatesUpdater.UpdateCandidatesTask.this.mEventType, CandidatesUpdater.UpdateCandidatesTask.this.mRequest.getTouchHistory(), null);
            Iterator localIterator = CandidatesUpdater.UpdateCandidatesTask.this.mListeners.iterator();
            while (localIterator.hasNext()) {
              ((UpdatedCandidatesListener)localIterator.next()).onCandidatesUpdated(localCandidateContainer);
            }
          }
        }
      });
    }
    
    public List<Candidate> call()
      throws Exception
    {
      Sequence localSequence;
      String str;
      boolean bool;
      CandidatesUpdater.TouchHistoryHashAndSize localTouchHistoryHashAndSize;
      if (this.mRequest.getSequence() != null)
      {
        localSequence = this.mRequest.getSequence();
        str = this.mRequest.getVerbatimWord();
        bool = arePrerequisitesReady();
        localTouchHistoryHashAndSize = new CandidatesUpdater.TouchHistoryHashAndSize(this.mRequest.getTouchHistory().getTouchHistory());
        if ((!this.mForce) && (this.mUptoDateChecker.upToDate(bool, str, localTouchHistoryHashAndSize, this.mRequest))) {
          break label130;
        }
        if (!bool) {
          break label124;
        }
      }
      label124:
      for (List localList1 = getPredictions(this.mNumberOfPredictions, localSequence, str);; localList1 = null)
      {
        updateCandidates(localList1);
        this.mUptoDateChecker.setRequestDetails(bool, str, localTouchHistoryHashAndSize, this.mRequest, localList1);
        return localList1;
        str = null;
        localSequence = null;
        break;
      }
      label130:
      if (this.mUptoDateChecker.dumbModeChanged(this.mRequest.getDumbInputMode()))
      {
        List localList2 = this.mUptoDateChecker.lastCandidates();
        updateCandidates(localList2);
        this.mUptoDateChecker.setRequestDetails(bool, str, localTouchHistoryHashAndSize, this.mRequest, localList2);
        return localList2;
      }
      return this.mUptoDateChecker.lastCandidates();
    }
  }
  
  private static final class UptoDateChecker
  {
    private List<Candidate> mCandidates = null;
    private ResultsFilter.CapitalizationHint mCapitalizationAtStart = null;
    private boolean mDumbMode = false;
    private boolean mPrerequisitesReady = false;
    private Sequence mSequence = null;
    private CandidatesUpdater.TouchHistoryHashAndSize mTouchHistoryHashAndSize = null;
    private CandidateUtil.VerbatimCandidatePosition mVerbatimInsertionBehaviour = CandidateUtil.VerbatimCandidatePosition.NO_VERBATIM;
    private String mVerbatimText = null;
    
    static boolean safeEquals(Object paramObject1, Object paramObject2)
    {
      if (paramObject1 == null) {
        return paramObject2 == null;
      }
      return paramObject1.equals(paramObject2);
    }
    
    boolean dumbModeChanged(boolean paramBoolean)
    {
      return this.mDumbMode != paramBoolean;
    }
    
    List<Candidate> lastCandidates()
    {
      return this.mCandidates;
    }
    
    void setRequestDetails(boolean paramBoolean, String paramString, CandidatesUpdater.TouchHistoryHashAndSize paramTouchHistoryHashAndSize, CandidatesUpdateRequest paramCandidatesUpdateRequest, List<Candidate> paramList)
    {
      this.mPrerequisitesReady = paramBoolean;
      this.mSequence = paramCandidatesUpdateRequest.getSequence();
      this.mVerbatimText = paramString;
      this.mTouchHistoryHashAndSize = paramTouchHistoryHashAndSize;
      this.mCapitalizationAtStart = paramCandidatesUpdateRequest.getCapitalizationAtStart();
      this.mVerbatimInsertionBehaviour = paramCandidatesUpdateRequest.verbatimInsertionBehaviour();
      this.mDumbMode = paramCandidatesUpdateRequest.getDumbInputMode();
      this.mCandidates = paramList;
    }
    
    boolean upToDate(boolean paramBoolean, String paramString, CandidatesUpdater.TouchHistoryHashAndSize paramTouchHistoryHashAndSize, CandidatesUpdateRequest paramCandidatesUpdateRequest)
    {
      return (this.mPrerequisitesReady == paramBoolean) && (safeEquals(this.mSequence, paramCandidatesUpdateRequest.getSequence())) && (safeEquals(this.mVerbatimText, paramString)) && (this.mVerbatimInsertionBehaviour == paramCandidatesUpdateRequest.verbatimInsertionBehaviour()) && (safeEquals(this.mTouchHistoryHashAndSize, paramTouchHistoryHashAndSize)) && (this.mCapitalizationAtStart == paramCandidatesUpdateRequest.getCapitalizationAtStart());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidatesUpdater
 * JD-Core Version:    0.7.0.1
 */