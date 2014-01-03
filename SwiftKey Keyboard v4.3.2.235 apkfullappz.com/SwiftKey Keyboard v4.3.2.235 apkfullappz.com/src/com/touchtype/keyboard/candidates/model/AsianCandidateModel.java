package com.touchtype.keyboard.candidates.model;

import android.content.Context;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.candidates.CandidateContainer;
import com.touchtype.keyboard.candidates.VisibilityListener;
import com.touchtype.keyboard.candidates.view.AsianCandidateButton;
import com.touchtype.keyboard.candidates.view.AsianCandidateLayout;
import com.touchtype.keyboard.candidates.view.AsianCandidateLayoutSplitLeft;
import com.touchtype.keyboard.candidates.view.AsianCandidateLayoutSplitRight;
import com.touchtype.keyboard.candidates.view.AsianCandidatePaneTracker;
import com.touchtype.keyboard.candidates.view.AsianComposingPopup;
import com.touchtype.keyboard.candidates.view.AsianResultsPopup;
import com.touchtype.keyboard.candidates.view.ObservableHorizontalScrollView.OnHorizontalScrollListener;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.preferences.HapticsUtil;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AsianCandidateModel
  implements View.OnClickListener, VisibilityListener, CandidateModelInterface
{
  private static final String TAG = AsianCandidateModel.class.getSimpleName();
  private AsianComposingPopup mAsianComposingPopup;
  private AsianResultsPopup mAsianResultsPopup;
  private AsianCandidateLayout mCandidateView;
  private AsianCandidateLayoutSplitLeft mCandidateViewLeft;
  private AsianCandidateLayoutSplitRight mCandidateViewRight;
  private List<Candidate> mCandidates;
  private final Context mContext;
  private InputEventModel mInputEventModel;
  private final ObservableHorizontalScrollView.OnHorizontalScrollListener mLeftPaneScrollListener;
  private final AsianCandidatePaneTracker mPaneTracker;
  private final ObservableHorizontalScrollView.OnHorizontalScrollListener mRightPaneScrollListener;
  
  public AsianCandidateModel(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPaneTracker = new AsianCandidatePaneTracker(15);
    this.mLeftPaneScrollListener = new ObservableHorizontalScrollView.OnHorizontalScrollListener()
    {
      public void onScroll(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        AsianCandidateModel.this.mCandidateViewRight.scrollTo(paramAnonymousInt1, paramAnonymousInt2);
      }
    };
    this.mRightPaneScrollListener = new ObservableHorizontalScrollView.OnHorizontalScrollListener()
    {
      public void onScroll(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        AsianCandidateModel.this.mCandidateViewLeft.scrollTo(paramAnonymousInt1, paramAnonymousInt2);
      }
    };
  }
  
  public void candidateSelected() {}
  
  public boolean cyclePages()
  {
    return false;
  }
  
  public View getCandidateView(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    if (this.mCandidateView == null)
    {
      this.mInputEventModel = paramInputEventModel;
      this.mCandidateView = ((AsianCandidateLayout)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903041, null));
      this.mCandidateView.setOnClickCandidateListener(this);
      this.mCandidateView.setOnVisibilityChangedListener(this);
      this.mAsianResultsPopup = new AsianResultsPopup(this.mCandidateView, this.mInputEventModel);
      this.mAsianComposingPopup = new AsianComposingPopup(this.mCandidateView, this.mInputEventModel);
    }
    return this.mCandidateView;
  }
  
  public Candidate getDefaultCandidate()
  {
    if ((this.mCandidates != null) && (!this.mCandidates.isEmpty())) {
      return (Candidate)this.mCandidates.get(0);
    }
    return Candidate.empty();
  }
  
  public Pair<View, View> getDualCandidateViews(Learner paramLearner, InputEventModel paramInputEventModel)
  {
    if ((this.mCandidateViewLeft == null) || (this.mCandidateViewRight == null))
    {
      LayoutInflater localLayoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
      this.mCandidateViewLeft = ((AsianCandidateLayoutSplitLeft)localLayoutInflater.inflate(2130903042, null));
      this.mCandidateViewRight = ((AsianCandidateLayoutSplitRight)localLayoutInflater.inflate(2130903043, null));
      this.mCandidateViewLeft.setPaneTracker(this.mPaneTracker);
      this.mCandidateViewLeft.setOnHorizontalScrollListener(this.mLeftPaneScrollListener);
      this.mCandidateViewLeft.setOnClickCandidateListener(this);
      this.mCandidateViewLeft.setOnVisibilityChangedListener(this);
      this.mCandidateViewRight.setPaneTracker(this.mPaneTracker);
      this.mCandidateViewRight.setOnHorizontalScrollListener(this.mRightPaneScrollListener);
      this.mCandidateViewRight.setOnClickCandidateListener(this);
      this.mCandidateViewRight.setOnVisibilityChangedListener(this);
    }
    return new Pair(this.mCandidateViewLeft, this.mCandidateViewRight);
  }
  
  public int getNumCandsRequired()
  {
    return 15;
  }
  
  public int getNumberOfPages()
  {
    return 1;
  }
  
  public void onClick(View paramView)
  {
    if ((paramView.getId() == 2131230760) || (paramView.getId() == 2131230767)) {
      this.mAsianResultsPopup.show(this.mCandidates);
    }
    Candidate localCandidate;
    do
    {
      return;
      if (!(paramView instanceof AsianCandidateButton)) {
        break;
      }
      localCandidate = (Candidate)((AsianCandidateButton)paramView).getTag();
    } while ((localCandidate == null) || (localCandidate.toString().length() <= 0));
    HapticsUtil.hapticClick(this.mContext);
    this.mInputEventModel.onPredictionSelected(localCandidate);
    return;
    String str = TAG;
    Locale localLocale = Locale.ENGLISH;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramView.getId());
    arrayOfObject[1] = paramView.getClass().getSimpleName();
    Log.w(str, String.format(localLocale, "Unexpected view with ID %d and class %s", arrayOfObject));
  }
  
  public void onVisibilityChanged(boolean paramBoolean)
  {
    if (!paramBoolean)
    {
      if (this.mAsianComposingPopup.isShowing()) {
        this.mAsianComposingPopup.dismiss();
      }
      if (this.mAsianResultsPopup.isShowing()) {
        this.mAsianResultsPopup.dismiss();
      }
    }
  }
  
  public void setCandidates(CandidateContainer paramCandidateContainer)
  {
    this.mCandidates = paramCandidateContainer.getCandidates();
    if (this.mCandidateView != null) {
      this.mCandidateView.setArrangement(this.mCandidates);
    }
    if (this.mCandidateViewLeft != null) {
      this.mCandidateViewLeft.setArrangement(this.mCandidates);
    }
    if (this.mCandidateViewRight != null) {
      this.mCandidateViewRight.setArrangement(this.mCandidates);
    }
    if (paramCandidateContainer.getTags() != null) {}
    for (String str = (String)paramCandidateContainer.getTags().get("composing_popup_text"); (str != null) && (str.length() > 0) && (!str.equals(" ")); str = null)
    {
      this.mAsianComposingPopup.setText(str);
      this.mAsianComposingPopup.show();
      return;
    }
    this.mAsianComposingPopup.dismiss();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.model.AsianCandidateModel
 * JD-Core Version:    0.7.0.1
 */