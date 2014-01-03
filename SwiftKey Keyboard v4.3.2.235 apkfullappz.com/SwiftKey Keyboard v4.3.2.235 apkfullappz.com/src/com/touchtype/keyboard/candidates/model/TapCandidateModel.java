package com.touchtype.keyboard.candidates.model;

import android.content.Context;
import android.os.Build.VERSION;
import android.util.Pair;
import android.view.View;
import com.google.common.base.Strings;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.view.CandidateKeyboardViewCompatibility;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.view.CandidateKeyboardView;
import com.touchtype.keyboard.view.CandidateOrderingProvider;
import com.touchtype.keyboard.view.CandidateViewInterface;
import com.touchtype.keyboard.view.DefaultCandidateOrderingProvider;
import com.touchtype.keyboard.view.LeftPaneCandidateOrderingProvider;
import com.touchtype.keyboard.view.RightPaneCandidateOrderingProvider;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TapCandidateModel
  implements CandidateModelInterface
{
  private CandidateViews mCandidateViews;
  private final Context mContext;
  private List<Candidate> mCurrentCandidates;
  private Boolean mCurrentDumbMode;
  private int mCurrentNumberOfCandidates;
  private int mCurrentPage = 0;
  private final int mDefaultNumberOfCandidates;
  private int mNumberOfPages;
  private final TouchTypeStats mStats;
  
  public TapCandidateModel(Context paramContext, TouchTypeStats paramTouchTypeStats)
  {
    this(paramContext, paramTouchTypeStats, 3, 1);
  }
  
  public TapCandidateModel(Context paramContext, TouchTypeStats paramTouchTypeStats, int paramInt1, int paramInt2)
  {
    this.mContext = paramContext;
    this.mStats = paramTouchTypeStats;
    this.mDefaultNumberOfCandidates = paramInt1;
    this.mCurrentNumberOfCandidates = this.mDefaultNumberOfCandidates;
    this.mNumberOfPages = paramInt2;
  }
  
  private CandidateViewInterface createCandidateKeyboardView(Learner paramLearner, InputEventModel paramInputEventModel, int paramInt, CandidateOrderingProvider paramCandidateOrderingProvider, float paramFloat1, float paramFloat2)
  {
    if ((Build.VERSION.SDK_INT > 10) || (Build.VERSION.SDK_INT < 9)) {
      return new CandidateKeyboardView(this.mContext, paramLearner, paramInputEventModel, paramInt, paramCandidateOrderingProvider, paramFloat1, paramFloat2);
    }
    return new CandidateKeyboardViewCompatibility(this.mContext, paramLearner, paramInputEventModel, paramInt, paramCandidateOrderingProvider, paramFloat1, paramFloat2);
  }
  
  public void candidateSelected() {}
  
  public boolean cyclePages()
  {
    List localList = this.mCurrentCandidates;
    boolean bool = false;
    if (localList != null)
    {
      this.mCurrentPage = (1 + this.mCurrentPage);
      int i = this.mCurrentPage * this.mCurrentNumberOfCandidates;
      if (i >= this.mCurrentCandidates.size())
      {
        this.mCurrentPage = 0;
        i = 0;
      }
      int j = (1 + this.mCurrentPage) * this.mCurrentNumberOfCandidates;
      if (j > this.mCurrentCandidates.size()) {
        j = this.mCurrentCandidates.size();
      }
      this.mCandidateViews.updateCandidateKeys(this.mCurrentCandidates.subList(i, j), this.mCurrentDumbMode.booleanValue());
      bool = true;
    }
    return bool;
  }
  
  public View getCandidateView(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    float[] arrayOfFloat = TouchTypePreferences.getInstance(this.mContext).getCurrentCandidatePadding(this.mContext);
    if ((this.mCandidateViews == null) || (this.mCandidateViews.count() == 2) || (this.mCandidateViews.hasDifferentPadding(arrayOfFloat[0], arrayOfFloat[1]))) {
      this.mCandidateViews = new CandidateViews(createCandidateKeyboardView(paramLearner, paramInputEventModel, this.mDefaultNumberOfCandidates, new DefaultCandidateOrderingProvider(), arrayOfFloat[0], arrayOfFloat[1]));
    }
    this.mCurrentNumberOfCandidates = this.mDefaultNumberOfCandidates;
    return this.mCandidateViews.getView();
  }
  
  public Candidate getDefaultCandidate()
  {
    int i = this.mCurrentPage * this.mCurrentNumberOfCandidates;
    if ((this.mCurrentCandidates != null) && (this.mCurrentCandidates.size() > i)) {
      return (Candidate)this.mCurrentCandidates.get(i);
    }
    return Candidate.empty();
  }
  
  public Pair<View, View> getDualCandidateViews(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    if ((this.mCandidateViews == null) || (this.mCandidateViews.count() != 2))
    {
      float[] arrayOfFloat = TouchTypePreferences.getInstance(this.mContext).getCurrentCandidatePadding(this.mContext);
      this.mCandidateViews = new CandidateViews(createCandidateKeyboardView(paramLearner, paramInputEventModel, 1, new LeftPaneCandidateOrderingProvider(), arrayOfFloat[0], arrayOfFloat[1]), createCandidateKeyboardView(paramLearner, paramInputEventModel, 1, new RightPaneCandidateOrderingProvider(), arrayOfFloat[0], arrayOfFloat[1]));
    }
    this.mCurrentNumberOfCandidates = 2;
    return this.mCandidateViews.getViews();
  }
  
  public int getNumCandsRequired()
  {
    return this.mCurrentNumberOfCandidates;
  }
  
  public int getNumberOfPages()
  {
    return this.mNumberOfPages;
  }
  
  public void setCandidates(CandidateContainer paramCandidateContainer)
  {
    if (this.mCandidateViews != null)
    {
      this.mCurrentCandidates = paramCandidateContainer.getCandidates();
      this.mCurrentDumbMode = Boolean.valueOf(paramCandidateContainer.getDumbMode());
      this.mCurrentPage = 0;
      int i = Math.min(this.mCurrentCandidates.size(), this.mCurrentNumberOfCandidates);
      this.mCandidateViews.updateCandidateKeys(this.mCurrentCandidates.subList(0, i), this.mCurrentDumbMode.booleanValue());
      Iterator localIterator = this.mCurrentCandidates.iterator();
      while (localIterator.hasNext())
      {
        Candidate localCandidate = (Candidate)localIterator.next();
        String str = localCandidate.source();
        if (!Strings.isNullOrEmpty(str))
        {
          int j = localCandidate.version();
          this.mStats.getLanguageMetricsPerSourceAndVersion(str, j).incrementShownWords();
        }
      }
    }
  }
  
  private final class CandidateViews
  {
    private List<CandidateViewInterface> mViews = new ArrayList(2);
    
    public CandidateViews(CandidateViewInterface paramCandidateViewInterface)
    {
      this.mViews.add(paramCandidateViewInterface);
    }
    
    public CandidateViews(CandidateViewInterface paramCandidateViewInterface1, CandidateViewInterface paramCandidateViewInterface2)
    {
      this.mViews.add(paramCandidateViewInterface1);
      this.mViews.add(paramCandidateViewInterface2);
    }
    
    public int count()
    {
      return this.mViews.size();
    }
    
    public View getView()
    {
      return ((CandidateViewInterface)this.mViews.get(0)).getView();
    }
    
    public Pair<View, View> getViews()
    {
      return new Pair(((CandidateViewInterface)this.mViews.get(0)).getView(), ((CandidateViewInterface)this.mViews.get(1)).getView());
    }
    
    public boolean hasDifferentPadding(float paramFloat1, float paramFloat2)
    {
      Iterator localIterator = this.mViews.iterator();
      while (localIterator.hasNext()) {
        if (((CandidateViewInterface)localIterator.next()).hasDifferentPadding(paramFloat1, paramFloat2)) {
          return true;
        }
      }
      return false;
    }
    
    public void updateCandidateKeys(List<Candidate> paramList, boolean paramBoolean)
    {
      Iterator localIterator = this.mViews.iterator();
      while (localIterator.hasNext()) {
        ((CandidateViewInterface)localIterator.next()).updateCandidateKeys(paramList, paramBoolean);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.model.TapCandidateModel
 * JD-Core Version:    0.7.0.1
 */