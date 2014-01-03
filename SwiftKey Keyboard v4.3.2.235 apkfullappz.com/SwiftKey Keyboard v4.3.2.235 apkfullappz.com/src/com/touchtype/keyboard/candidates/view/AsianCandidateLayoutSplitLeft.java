package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AsianCandidateLayoutSplitLeft
  extends AsianCandidateLayoutSplitBase
  implements OnThemeChangedListener
{
  private final Set<AsianCandidateButton> mDirtyCandidateButtons = new HashSet(3);
  private boolean mFirstDraw = false;
  private Handler mHandler;
  
  public AsianCandidateLayoutSplitLeft(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private void adjustToVisibleArea()
  {
    int i = 0;
    for (int j = 0; j < this.mCandidatesLayout.getChildCount(); j++)
    {
      i += ((AsianCandidateButton)this.mCandidatesLayout.getChildAt(j)).getMeasuredWidth();
      if ((i > this.mCandidatesScrollView.getWidth()) && (j > 0))
      {
        int k = j;
        this.mPaneTracker.updateLeft(k);
        if (k != 0) {}
        for (int m = this.mCandidatesScrollView.getWidth() / k;; m = this.mCandidatesScrollView.getWidth())
        {
          LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(m, -1);
          for (int n = 0; n < k; n++)
          {
            AsianCandidateButton localAsianCandidateButton = (AsianCandidateButton)this.mCandidatesLayout.getChildAt(n);
            localAsianCandidateButton.setLayoutParams(localLayoutParams);
            this.mDirtyCandidateButtons.add(localAsianCandidateButton);
          }
        }
      }
    }
    this.mPaneTracker.updateLeft(0);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mHandler = new Handler();
    this.mCandidatesScrollView = ((ObservableHorizontalScrollView)findViewById(2131230764));
    this.mCandidatesLayout = ((LinearLayout)findViewById(2131230765));
    this.mCandidatesLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
    {
      public boolean onPreDraw()
      {
        return !AsianCandidateLayoutSplitLeft.this.mFirstDraw;
      }
    });
    Context localContext = getContext();
    AsianTopCandidateButton localAsianTopCandidateButton = new AsianTopCandidateButton(localContext);
    this.mCandidatesLayout.addView(localAsianTopCandidateButton, this.mCandidateButtonParams);
    for (int i = 1; i < 15; i++)
    {
      AsianCandidateButton localAsianCandidateButton = new AsianCandidateButton(localContext);
      this.mCandidatesLayout.addView(localAsianCandidateButton, this.mCandidateButtonParams);
    }
  }
  
  public void setArrangement(List<Candidate> paramList)
  {
    if (this.mCandidatesLayout == null) {
      return;
    }
    this.mFirstDraw = true;
    int i = 0;
    if (i < this.mCandidatesLayout.getChildCount())
    {
      AsianCandidateButton localAsianCandidateButton = (AsianCandidateButton)this.mCandidatesLayout.getChildAt(i);
      if (i < paramList.size())
      {
        Candidate localCandidate = (Candidate)paramList.get(i);
        if (this.mDirtyCandidateButtons.remove(localAsianCandidateButton)) {
          localAsianCandidateButton.setLayoutParams(this.mCandidateButtonParams);
        }
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
    scrollTo(0, 0);
    this.mHandler.removeCallbacksAndMessages(null);
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        AsianCandidateLayoutSplitLeft.access$002(AsianCandidateLayoutSplitLeft.this, false);
        AsianCandidateLayoutSplitLeft.this.adjustToVisibleArea();
        AsianCandidateLayoutSplitLeft.this.invalidate();
      }
    });
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidateLayoutSplitLeft
 * JD-Core Version:    0.7.0.1
 */