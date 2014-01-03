package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.view.CandidateKeyboardViewUtils;
import com.touchtype.keyboard.view.CandidateOrderingProvider;
import com.touchtype.keyboard.view.CandidateViewInterface;
import java.util.Iterator;
import java.util.List;

public final class CandidateKeyboardViewCompatibility
  extends LinearLayout
  implements OnThemeChangedListener, CandidateViewInterface
{
  private final CandidateOrderingProvider mCandidateOrderingProvider;
  private final Context mContext;
  private final float mHPadding;
  private final InputEventModel mIem;
  private final Learner mLearner;
  private final int mNumCandidates;
  private final float mVPadding;
  
  public CandidateKeyboardViewCompatibility(Context paramContext, Learner paramLearner, InputEventModel paramInputEventModel, int paramInt, CandidateOrderingProvider paramCandidateOrderingProvider, float paramFloat1, float paramFloat2)
  {
    super(paramContext);
    this.mContext = paramContext;
    this.mLearner = paramLearner;
    this.mIem = paramInputEventModel;
    this.mNumCandidates = paramInt;
    this.mCandidateOrderingProvider = paramCandidateOrderingProvider;
    this.mHPadding = paramFloat1;
    this.mVPadding = paramFloat2;
    setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    setOrientation(0);
    setGravity(16);
    setPadding(0, 0, 0, 0);
    initChildren();
  }
  
  private void initChildren()
  {
    int i = this.mNumCandidates / 2;
    Theme localTheme = ThemeManager.getInstance(getContext()).getThemeHandler();
    setBackgroundDrawable(localTheme.getProperties().getCandidateBackground());
    ThemeRenderer localThemeRenderer = localTheme.getRenderer();
    int j = 0;
    if (j < this.mNumCandidates)
    {
      boolean bool;
      label55:
      CandidateButton localCandidateButton;
      if (j == i)
      {
        bool = true;
        localCandidateButton = new CandidateButton(getContext(), bool, this.mNumCandidates);
        localCandidateButton.setOnClickListener(CandidateKeyboardViewUtils.getCandidateButtonClickListener(this.mContext, this.mIem));
        localCandidateButton.setOnLongClickListener(CandidateKeyboardViewUtils.getCandidateButtonLongClickListener(this.mContext, this.mLearner, this.mIem, this));
        if (!bool) {
          break label155;
        }
      }
      label155:
      for (KeyStyle.StyleId localStyleId = KeyStyle.StyleId.TOPCANDIDATE;; localStyleId = KeyStyle.StyleId.CANDIDATE)
      {
        localCandidateButton.setCandidate(Candidate.empty(), localStyleId);
        localCandidateButton.setTextPaintAttributes(localThemeRenderer);
        addView(localCandidateButton);
        j++;
        break;
        bool = false;
        break label55;
      }
    }
  }
  
  private void updateBackground()
  {
    Theme localTheme = ThemeManager.getInstance(getContext()).getThemeHandler();
    setBackgroundDrawable(localTheme.getProperties().getCandidateBackground());
    ThemeRenderer localThemeRenderer = localTheme.getRenderer();
    for (int i = 0; i < getChildCount(); i++) {
      ((CandidateButton)getChildAt(i)).setTextPaintAttributes(localThemeRenderer);
    }
  }
  
  public View getView()
  {
    return this;
  }
  
  public boolean hasDifferentPadding(float paramFloat1, float paramFloat2)
  {
    return (this.mHPadding != paramFloat1) || (this.mVPadding != paramFloat2);
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    ThemeManager.getInstance(getContext()).addListener(this);
    updateBackground();
  }
  
  protected void onDetachedFromWindow()
  {
    ThemeManager.getInstance(getContext()).removeListener(this);
    super.onDetachedFromWindow();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    for (int k = 0; k < getChildCount(); k++)
    {
      View localView = getChildAt(k);
      if ((localView != null) && ((localView instanceof CandidateButton))) {
        ((CandidateButton)localView).setFullWidth(i);
      }
    }
    super.onMeasure(i, View.MeasureSpec.makeMeasureSpec(j, 1073741824));
  }
  
  public void onThemeChanged()
  {
    updateBackground();
  }
  
  public void updateCandidateKeys(List<Candidate> paramList, boolean paramBoolean)
  {
    int i = 0;
    int j = -1;
    int k = getChildCount();
    Iterator localIterator = this.mCandidateOrderingProvider.getIterator(getChildCount(), paramList.size());
    if ((localIterator.hasNext()) && (i < getChildCount()))
    {
      Integer localInteger = (Integer)localIterator.next();
      int i1;
      label70:
      int i2;
      if (localInteger != null)
      {
        i1 = i;
        j = Math.max(j, i1);
        if (localInteger == null) {
          break label167;
        }
        i2 = i;
        label87:
        k = Math.min(k, i2);
        if (localInteger != null) {
          if ((this.mCandidateOrderingProvider.getTopCandidateKeyIndex(getChildCount()) == i) && (!paramBoolean)) {
            break label174;
          }
        }
      }
      label167:
      label174:
      for (KeyStyle.StyleId localStyleId = KeyStyle.StyleId.CANDIDATE;; localStyleId = KeyStyle.StyleId.TOPCANDIDATE)
      {
        ((CandidateButton)getChildAt(i)).setCandidate((Candidate)paramList.get(localInteger.intValue()), localStyleId);
        i++;
        break;
        i1 = j;
        break label70;
        i2 = k;
        break label87;
      }
    }
    for (int m = 0; m < k; m++) {
      ((CandidateButton)getChildAt(m)).setCandidate(Candidate.empty(), KeyStyle.StyleId.CANDIDATE);
    }
    for (int n = j + 1; n < getChildCount(); n++) {
      ((CandidateButton)getChildAt(n)).setCandidate(Candidate.empty(), KeyStyle.StyleId.CANDIDATE);
    }
    requestLayout();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.CandidateKeyboardViewCompatibility
 * JD-Core Version:    0.7.0.1
 */