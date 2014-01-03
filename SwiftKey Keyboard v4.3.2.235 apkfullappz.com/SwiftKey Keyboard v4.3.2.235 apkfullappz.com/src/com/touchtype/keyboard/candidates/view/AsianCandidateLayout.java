package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
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

public class AsianCandidateLayout
  extends RelativeLayout
  implements OnThemeChangedListener
{
  private final LinearLayout.LayoutParams mCandidateButtonParams = new LinearLayout.LayoutParams(-2, -1);
  private LinearLayout mCandidatesLayout;
  private HorizontalScrollView mCandidatesScrollView;
  private ImageButton mMoreButton;
  private final Set<VisibilityListener> mVisibilityListeners = new WeakHashSet(1);
  
  public AsianCandidateLayout(Context paramContext, AttributeSet paramAttributeSet)
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
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCandidatesScrollView = ((HorizontalScrollView)findViewById(2131230761));
    this.mCandidatesLayout = ((LinearLayout)findViewById(2131230762));
    this.mMoreButton = ((ImageButton)findViewById(2131230760));
    Context localContext = getContext();
    AsianTopCandidateButton localAsianTopCandidateButton = new AsianTopCandidateButton(localContext);
    this.mCandidatesLayout.addView(localAsianTopCandidateButton, this.mCandidateButtonParams);
    for (int i = 1; i < 15; i++)
    {
      AsianCandidateButton localAsianCandidateButton = new AsianCandidateButton(localContext);
      this.mCandidatesLayout.addView(localAsianCandidateButton, this.mCandidateButtonParams);
    }
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
  
  public void setArrangement(List<Candidate> paramList)
  {
    if (this.mCandidatesLayout == null) {
      return;
    }
    int i = 0;
    if (i < this.mCandidatesLayout.getChildCount())
    {
      AsianCandidateButton localAsianCandidateButton = (AsianCandidateButton)this.mCandidatesLayout.getChildAt(i);
      if (i < paramList.size())
      {
        Candidate localCandidate = (Candidate)paramList.get(i);
        localAsianCandidateButton.setCandidate(localCandidate);
        localAsianCandidateButton.setTag(localCandidate);
        localAsianCandidateButton.setVisibility(0);
      }
      for (;;)
      {
        i++;
        break;
        localAsianCandidateButton.setVisibility(8);
      }
    }
    this.mCandidatesScrollView.scrollTo(0, 0);
  }
  
  public void setOnClickCandidateListener(View.OnClickListener paramOnClickListener)
  {
    for (int i = 0; i < this.mCandidatesLayout.getChildCount(); i++) {
      ((AsianCandidateButton)this.mCandidatesLayout.getChildAt(i)).setOnClickListener(paramOnClickListener);
    }
    this.mMoreButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setOnVisibilityChangedListener(VisibilityListener paramVisibilityListener)
  {
    this.mVisibilityListeners.add(paramVisibilityListener);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidateLayout
 * JD-Core Version:    0.7.0.1
 */