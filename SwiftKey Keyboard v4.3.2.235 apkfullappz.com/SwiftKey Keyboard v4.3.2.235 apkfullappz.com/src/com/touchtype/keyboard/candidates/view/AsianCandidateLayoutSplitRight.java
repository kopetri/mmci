package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import java.util.List;

public class AsianCandidateLayoutSplitRight
  extends AsianCandidateLayoutSplitBase
  implements OnThemeChangedListener
{
  private List<Candidate> mLastCandidates;
  private final AsianCandidatePaneTracker.OnLeftPaneUpdatedListener mLeftPaneListener = new AsianCandidatePaneTracker.OnLeftPaneUpdatedListener()
  {
    public void onPaneUpdated()
    {
      if (AsianCandidateLayoutSplitRight.this.mLastCandidates != null)
      {
        AsianCandidateLayoutSplitRight.this.setArrangement(AsianCandidateLayoutSplitRight.this.mLastCandidates);
        AsianCandidateLayoutSplitRight.this.invalidate();
      }
    }
  };
  private ImageButton mMoreButton;
  
  public AsianCandidateLayoutSplitRight(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onFinishInflate()
  {
    super.onFinishInflate();
    this.mCandidatesScrollView = ((ObservableHorizontalScrollView)findViewById(2131230768));
    this.mCandidatesLayout = ((LinearLayout)findViewById(2131230769));
    this.mMoreButton = ((ImageButton)findViewById(2131230767));
    Context localContext = getContext();
    for (int i = 0; i < 15; i++)
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
    int i = 0;
    if (i < this.mCandidatesLayout.getChildCount())
    {
      AsianCandidateButton localAsianCandidateButton = (AsianCandidateButton)this.mCandidatesLayout.getChildAt(i);
      if ((i < this.mPaneTracker.getRightCandidates()) && (i + this.mPaneTracker.getLeftCandidates() < paramList.size()))
      {
        Candidate localCandidate = (Candidate)paramList.get(i + this.mPaneTracker.getLeftCandidates());
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
    this.mLastCandidates = paramList;
    scrollTo(0, 0);
  }
  
  public void setOnClickCandidateListener(View.OnClickListener paramOnClickListener)
  {
    super.setOnClickCandidateListener(paramOnClickListener);
    this.mMoreButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setPaneTracker(AsianCandidatePaneTracker paramAsianCandidatePaneTracker)
  {
    super.setPaneTracker(paramAsianCandidatePaneTracker);
    this.mPaneTracker.addOnLeftPaneUpdatedListener(this.mLeftPaneListener);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidateLayoutSplitRight
 * JD-Core Version:    0.7.0.1
 */