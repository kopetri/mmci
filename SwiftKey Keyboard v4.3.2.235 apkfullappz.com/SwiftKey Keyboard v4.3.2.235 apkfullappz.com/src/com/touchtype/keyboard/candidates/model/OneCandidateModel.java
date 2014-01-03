package com.touchtype.keyboard.candidates.model;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.google.common.base.Strings;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.view.OneCandidateView;
import com.touchtype.keyboard.candidates.view.OneCandidateViewCompatibility;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.key.contents.CandidateContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.view.OneCandidateViewInterface;
import com.touchtype.report.LanguageModelMetrics;
import com.touchtype.report.TouchTypeStats;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class OneCandidateModel
  implements CandidateModelInterface, OnThemeChangedListener
{
  private Candidate mCandidate;
  private final Context mContext;
  private final TouchTypeStats mStats;
  private final OneCandidateViews mViews;
  
  public OneCandidateModel(Context paramContext, TouchTypeStats paramTouchTypeStats)
  {
    this.mContext = paramContext;
    this.mStats = paramTouchTypeStats;
    this.mViews = createOneCandidateViews(new ViewGroup.LayoutParams(-1, -1), ThemeManager.getInstance(paramContext).getThemeHandler().getProperties().getCandidateBackground());
    ThemeManager.getInstance(paramContext).addListener(this);
  }
  
  private OneCandidateViews createOneCandidateViews(ViewGroup.LayoutParams paramLayoutParams, Drawable paramDrawable)
  {
    Object localObject1;
    if ((Build.VERSION.SDK_INT > 10) || (Build.VERSION.SDK_INT < 9)) {
      localObject1 = new OneCandidateView(this.mContext, paramLayoutParams, paramDrawable);
    }
    for (Object localObject2 = new OneCandidateView(this.mContext, paramLayoutParams, paramDrawable);; localObject2 = new OneCandidateViewCompatibility(this.mContext, paramLayoutParams, paramDrawable))
    {
      return new OneCandidateViews((OneCandidateViewInterface)localObject1, (OneCandidateViewInterface)localObject2);
      localObject1 = new OneCandidateViewCompatibility(this.mContext, paramLayoutParams, paramDrawable);
    }
  }
  
  public void candidateSelected()
  {
    this.mViews.candidateSelected(true);
  }
  
  public boolean cyclePages()
  {
    return false;
  }
  
  public View getCandidateView(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    return this.mViews.getView();
  }
  
  public Candidate getDefaultCandidate()
  {
    if (this.mCandidate != null) {
      return this.mCandidate;
    }
    return Candidate.empty();
  }
  
  public Pair<View, View> getDualCandidateViews(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    return this.mViews.getViews();
  }
  
  public int getNumCandsRequired()
  {
    return 1;
  }
  
  public int getNumberOfPages()
  {
    return 1;
  }
  
  public void onThemeChanged()
  {
    Drawable localDrawable = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties().getCandidateBackground();
    this.mViews.setThemeAttributes(localDrawable);
  }
  
  public void setCandidates(CandidateContainer paramCandidateContainer)
  {
    this.mViews.candidateSelected(false);
    this.mCandidate = paramCandidateContainer.getTopCandidate();
    CandidateContent localCandidateContent = new CandidateContent();
    localCandidateContent.updateCandidate(this.mCandidate);
    this.mViews.setContent(localCandidateContent, KeyStyle.StyleId.CANDIDATE);
    String str = this.mCandidate.source();
    if (!Strings.isNullOrEmpty(str))
    {
      int i = this.mCandidate.version();
      this.mStats.getLanguageMetricsPerSourceAndVersion(str, i).incrementShownWords();
    }
  }
  
  private final class OneCandidateViews
  {
    private List<OneCandidateViewInterface> mViews = new ArrayList(2);
    
    public OneCandidateViews(OneCandidateViewInterface paramOneCandidateViewInterface1, OneCandidateViewInterface paramOneCandidateViewInterface2)
    {
      this.mViews.add(paramOneCandidateViewInterface1);
      this.mViews.add(paramOneCandidateViewInterface2);
    }
    
    public void candidateSelected(boolean paramBoolean)
    {
      Iterator localIterator = this.mViews.iterator();
      while (localIterator.hasNext()) {
        ((OneCandidateViewInterface)localIterator.next()).candidateSelected(paramBoolean);
      }
    }
    
    public View getView()
    {
      return ((OneCandidateViewInterface)this.mViews.get(0)).getView();
    }
    
    public Pair<View, View> getViews()
    {
      return new Pair(((OneCandidateViewInterface)this.mViews.get(0)).getView(), ((OneCandidateViewInterface)this.mViews.get(1)).getView());
    }
    
    public void setContent(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId)
    {
      Iterator localIterator = this.mViews.iterator();
      while (localIterator.hasNext()) {
        ((OneCandidateViewInterface)localIterator.next()).setContent(paramKeyContent, paramStyleId);
      }
    }
    
    public void setThemeAttributes(Drawable paramDrawable)
    {
      Iterator localIterator = this.mViews.iterator();
      while (localIterator.hasNext()) {
        ((OneCandidateViewInterface)localIterator.next()).setThemeAttributes(paramDrawable);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.model.OneCandidateModel
 * JD-Core Version:    0.7.0.1
 */