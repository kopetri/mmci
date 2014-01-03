package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.VisibilityListener;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.util.WeakHashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class AsianCandidateLayoutSplitBase
  extends RelativeLayout
  implements OnThemeChangedListener
{
  protected final LinearLayout.LayoutParams mCandidateButtonParams = new LinearLayout.LayoutParams(-2, -1);
  protected LinearLayout mCandidatesLayout;
  protected ObservableHorizontalScrollView mCandidatesScrollView;
  protected AsianCandidatePaneTracker mPaneTracker;
  private final Set<VisibilityListener> mVisibilityListeners = new WeakHashSet(1);
  
  public AsianCandidateLayoutSplitBase(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setBackgroundDrawable(ThemeManager.getInstance(paramContext).getThemeHandler().getProperties().getCandidateBackground());
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ThemeManager.getInstance(getContext()).addListener(this);
  }
  
  protected void onDetachedFromWindow()
  {
    ThemeManager.getInstance(getContext()).removeListener(this);
    super.onDetachedFromWindow();
  }
  
  public void onThemeChanged()
  {
    setBackgroundDrawable(ThemeManager.getInstance(getContext()).getThemeHandler().getProperties().getCandidateBackground());
  }
  
  public void onVisibilityChanged(View paramView, int paramInt)
  {
    Iterator localIterator = this.mVisibilityListeners.iterator();
    if (localIterator.hasNext())
    {
      VisibilityListener localVisibilityListener = (VisibilityListener)localIterator.next();
      if (paramInt == 0) {}
      for (boolean bool = true;; bool = false)
      {
        localVisibilityListener.onVisibilityChanged(bool);
        break;
      }
    }
  }
  
  public void scrollTo(int paramInt1, int paramInt2)
  {
    this.mCandidatesScrollView.scrollTo(paramInt1, paramInt2);
  }
  
  public abstract void setArrangement(List<Candidate> paramList);
  
  public void setOnClickCandidateListener(View.OnClickListener paramOnClickListener)
  {
    for (int i = 0; i < this.mCandidatesLayout.getChildCount(); i++) {
      ((AsianCandidateButton)this.mCandidatesLayout.getChildAt(i)).setOnClickListener(paramOnClickListener);
    }
  }
  
  public void setOnHorizontalScrollListener(ObservableHorizontalScrollView.OnHorizontalScrollListener paramOnHorizontalScrollListener)
  {
    this.mCandidatesScrollView.addOnHorizontalScrollListener(paramOnHorizontalScrollListener);
  }
  
  public void setOnVisibilityChangedListener(VisibilityListener paramVisibilityListener)
  {
    this.mVisibilityListeners.add(paramVisibilityListener);
  }
  
  public void setPaneTracker(AsianCandidatePaneTracker paramAsianCandidatePaneTracker)
  {
    this.mPaneTracker = paramAsianCandidatePaneTracker;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidateLayoutSplitBase
 * JD-Core Version:    0.7.0.1
 */