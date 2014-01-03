package com.touchtype.keyboard.candidates;

import android.content.Context;
import android.util.Pair;
import android.view.InflateException;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.KeyboardChangeListener;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.model.AsianCandidateModel;
import com.touchtype.keyboard.candidates.model.CandidateModelInterface;
import com.touchtype.keyboard.candidates.model.OneCandidateModel;
import com.touchtype.keyboard.candidates.model.TapCandidateModel;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.MultiViewSwitcher;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.LogUtil;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public final class CandidateStateHandler
  implements KeyboardChangeListener, UpdatedCandidatesListener, DefaultPredictionProvider
{
  private boolean mBlockNonAsian = false;
  private CandidatesUpdater mCandidatesUpdater;
  private final Context mContext;
  private CandidateState mCurrentState;
  private CandidateState mDefaultState;
  private final Map<CandidateState, CandidateModelInterface> mModels;
  private CandidatesUpdateRequestFactory mRequestFactory;
  private MultiViewSwitcher mSecondaryViewSwitcher;
  private MultiViewSwitcher mViewSwitcher;
  
  private CandidateStateHandler(CandidateState paramCandidateState, Map<CandidateState, CandidateModelInterface> paramMap, Context paramContext)
    throws IllegalArgumentException
  {
    if ((!paramMap.containsKey(paramCandidateState)) || (paramMap.get(paramCandidateState) == null)) {
      throw new IllegalArgumentException("No candidate model was provided for the default candidate state");
    }
    this.mContext = paramContext;
    this.mModels = paramMap;
    this.mCurrentState = paramCandidateState;
    this.mDefaultState = paramCandidateState;
  }
  
  public static CandidateStateHandler createCandidateStateHandler(Context paramContext, TouchTypeStats paramTouchTypeStats)
  {
    return new CandidateStateHandler(CandidateState.TAP, createModels(paramContext, paramTouchTypeStats), paramContext);
  }
  
  private static HashMap<CandidateState, CandidateModelInterface> createModels(Context paramContext, TouchTypeStats paramTouchTypeStats)
  {
    LinkedHashMap localLinkedHashMap = new LinkedHashMap();
    if (ProductConfiguration.isWatchBuild(paramContext)) {}
    for (TapCandidateModel localTapCandidateModel = new TapCandidateModel(paramContext, paramTouchTypeStats, 1, 3);; localTapCandidateModel = new TapCandidateModel(paramContext, paramTouchTypeStats))
    {
      localLinkedHashMap.put(CandidateState.TAP, localTapCandidateModel);
      OneCandidateModel localOneCandidateModel = new OneCandidateModel(paramContext, paramTouchTypeStats);
      localLinkedHashMap.put(CandidateState.FLOW, localOneCandidateModel);
      AsianCandidateModel localAsianCandidateModel = new AsianCandidateModel(paramContext);
      localLinkedHashMap.put(CandidateState.ASIAN, localAsianCandidateModel);
      return localLinkedHashMap;
    }
  }
  
  private MultiViewSwitcher createNewCandidatesView(Map<CandidateState, CandidateModelInterface> paramMap, Learner paramLearner, InputEventModel paramInputEventModel)
  {
    localMultiViewSwitcher = new MultiViewSwitcher(this.mContext, 2130968576, 2130968577, new FrameLayout.LayoutParams(-1, -1));
    try
    {
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        CandidateState localCandidateState = (CandidateState)localIterator.next();
        localMultiViewSwitcher.registerView(localCandidateState.ordinal(), ((CandidateModelInterface)paramMap.get(localCandidateState)).getCandidateView(paramLearner, paramInputEventModel));
      }
      return localMultiViewSwitcher;
    }
    catch (InflateException localInflateException)
    {
      LogUtil.e("CandidateStateHandler", "Failed to inflate candidates view: " + localInflateException.getMessage(), localInflateException);
      localMultiViewSwitcher.switchView(this.mDefaultState.ordinal(), 0, 0);
      localMultiViewSwitcher.setBackgroundColor(0);
    }
  }
  
  private Pair<MultiViewSwitcher, MultiViewSwitcher> createNewDualCandidatesViews(Map<CandidateState, CandidateModelInterface> paramMap, Learner paramLearner, InputEventModel paramInputEventModel)
  {
    localMultiViewSwitcher1 = new MultiViewSwitcher(this.mContext, 2130968576, 2130968577, new FrameLayout.LayoutParams(-1, -1));
    localMultiViewSwitcher2 = new MultiViewSwitcher(this.mContext, 2130968576, 2130968577, new FrameLayout.LayoutParams(-1, -1));
    try
    {
      Iterator localIterator = paramMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        CandidateState localCandidateState = (CandidateState)localIterator.next();
        Pair localPair = ((CandidateModelInterface)paramMap.get(localCandidateState)).getDualCandidateViews(paramLearner, paramInputEventModel);
        localMultiViewSwitcher1.registerView(localCandidateState.ordinal(), (View)localPair.first);
        localMultiViewSwitcher2.registerView(localCandidateState.ordinal(), (View)localPair.second);
      }
      return new Pair(localMultiViewSwitcher1, localMultiViewSwitcher2);
    }
    catch (InflateException localInflateException)
    {
      LogUtil.e("CandidateStateHandler", "Failed to inflate candidates view: " + localInflateException.getMessage(), localInflateException);
      localMultiViewSwitcher1.switchView(this.mDefaultState.ordinal(), 0, 0);
      localMultiViewSwitcher1.setBackgroundColor(0);
      localMultiViewSwitcher2.switchView(this.mDefaultState.ordinal(), 0, 0);
      localMultiViewSwitcher2.setBackgroundColor(0);
    }
  }
  
  private void displayCandidates(CandidateContainer paramCandidateContainer, CandidateState paramCandidateState)
  {
    ((CandidateModelInterface)this.mModels.get(paramCandidateState)).setCandidates(paramCandidateContainer);
  }
  
  private void switchViewToState(int paramInt1, int paramInt2, int paramInt3)
  {
    this.mViewSwitcher.switchView(paramInt1, paramInt2, paramInt3);
    if (this.mSecondaryViewSwitcher != null) {
      this.mSecondaryViewSwitcher.switchView(paramInt1, paramInt2, paramInt3);
    }
  }
  
  public void cycleCandidates()
  {
    ((CandidateModelInterface)this.mModels.get(this.mCurrentState)).cyclePages();
  }
  
  public Candidate getDefaultPrediction(boolean paramBoolean, CandidatesUpdateRequestType paramCandidatesUpdateRequestType)
  {
    if (paramBoolean) {
      return this.mCandidatesUpdater.getUpdatedTopCandidate(paramCandidatesUpdateRequestType, this.mRequestFactory);
    }
    return ((CandidateModelInterface)this.mModels.get(this.mCurrentState)).getDefaultCandidate();
  }
  
  public View getNewCandidatesView(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    if (this.mViewSwitcher != null) {
      this.mViewSwitcher.removeAllViews();
    }
    this.mViewSwitcher = createNewCandidatesView(this.mModels, paramLearner, paramInputEventModel);
    return this.mViewSwitcher;
  }
  
  public Pair<MultiViewSwitcher, MultiViewSwitcher> getNewDualCandidateViews(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    if (this.mViewSwitcher != null) {
      this.mViewSwitcher.removeAllViews();
    }
    if (this.mSecondaryViewSwitcher != null) {
      this.mSecondaryViewSwitcher.removeAllViews();
    }
    Pair localPair = createNewDualCandidatesViews(this.mModels, paramLearner, paramInputEventModel);
    this.mViewSwitcher = ((MultiViewSwitcher)localPair.first);
    this.mSecondaryViewSwitcher = ((MultiViewSwitcher)localPair.second);
    return localPair;
  }
  
  public int getNumberOfCandidates()
  {
    int i = 0;
    Iterator localIterator = this.mModels.values().iterator();
    while (localIterator.hasNext())
    {
      CandidateModelInterface localCandidateModelInterface = (CandidateModelInterface)localIterator.next();
      i = Math.max(i, localCandidateModelInterface.getNumCandsRequired() * localCandidateModelInterface.getNumberOfPages());
    }
    return i;
  }
  
  public void onCandidatesUpdated(CandidateContainer paramCandidateContainer)
  {
    int i = 0;
    if ((this.mDefaultState == CandidateState.ASIAN) && (paramCandidateContainer.getEventType() != CandidatesUpdateRequestType.ASIAN) && (this.mBlockNonAsian)) {
      return;
    }
    int j;
    CandidateState localCandidateState1;
    switch (1.$SwitchMap$com$touchtype$keyboard$candidates$CandidatesUpdateRequestType[paramCandidateContainer.getEventType().ordinal()])
    {
    default: 
      CandidateState localCandidateState2 = this.mCurrentState;
      CandidateState localCandidateState3 = CandidateState.FLOW;
      j = 0;
      if (localCandidateState2 == localCandidateState3)
      {
        CandidateState localCandidateState4 = this.mDefaultState;
        CandidateState localCandidateState5 = CandidateState.TAP;
        j = 0;
        if (localCandidateState4 == localCandidateState5)
        {
          j = 200;
          ((CandidateModelInterface)this.mModels.get(this.mCurrentState)).candidateSelected();
        }
      }
      localCandidateState1 = this.mDefaultState;
    }
    for (;;)
    {
      if (localCandidateState1 != this.mCurrentState)
      {
        switchViewToState(localCandidateState1.ordinal(), i, j);
        this.mCurrentState = localCandidateState1;
      }
      displayCandidates(paramCandidateContainer, this.mCurrentState);
      return;
      localCandidateState1 = this.mCurrentState;
      i = 0;
      j = 0;
      continue;
      localCandidateState1 = CandidateState.FLOW;
      i = 0;
      j = 0;
      continue;
      i = 2130968582;
      localCandidateState1 = CandidateState.TAP;
      j = 0;
      continue;
      localCandidateState1 = CandidateState.ASIAN;
      i = 0;
      j = 0;
    }
  }
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    if (paramKeyboardBehaviour.shouldUseAsianCandidateBar()) {}
    for (CandidateState localCandidateState = CandidateState.ASIAN;; localCandidateState = CandidateState.TAP)
    {
      this.mDefaultState = localCandidateState;
      this.mBlockNonAsian = paramKeyboardBehaviour.shouldBlockNonAsianCandidates();
      return;
    }
  }
  
  public void setCandidatesUpdater(CandidatesUpdater paramCandidatesUpdater)
  {
    this.mCandidatesUpdater = paramCandidatesUpdater;
  }
  
  public void setRequestFactory(CandidatesUpdateRequestFactory paramCandidatesUpdateRequestFactory)
  {
    this.mRequestFactory = paramCandidatesUpdateRequestFactory;
  }
  
  public static enum CandidateState
  {
    static
    {
      FLOW = new CandidateState("FLOW", 1);
      ASIAN = new CandidateState("ASIAN", 2);
      CandidateState[] arrayOfCandidateState = new CandidateState[3];
      arrayOfCandidateState[0] = TAP;
      arrayOfCandidateState[1] = FLOW;
      arrayOfCandidateState[2] = ASIAN;
      $VALUES = arrayOfCandidateState;
    }
    
    private CandidateState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.CandidateStateHandler
 * JD-Core Version:    0.7.0.1
 */