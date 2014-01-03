package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

public class ProgressPreference
  extends Preference
{
  protected Button mCancelButton;
  private int mDeferredCurrent;
  private int mDeferredMax;
  private View.OnClickListener mDeferredOnCancelListener;
  private boolean mDeferredShowProgress;
  protected ProgressBar mProgressBar;
  private LinearLayout mProgressGroup;
  
  public ProgressPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setWidgetLayoutResource(2130903107);
  }
  
  public void hideProgress()
  {
    if (this.mProgressBar == null)
    {
      this.mDeferredShowProgress = false;
      return;
    }
    this.mProgressGroup.setVisibility(8);
    this.mProgressBar.setVisibility(8);
    this.mCancelButton.setVisibility(8);
  }
  
  protected void onBindView(View paramView)
  {
    super.onBindView(paramView);
    this.mProgressBar = ((ProgressBar)paramView.findViewById(2131230910));
    this.mProgressGroup = ((LinearLayout)paramView.findViewById(16908312));
    this.mCancelButton = ((Button)paramView.findViewById(2131230911));
    hideProgress();
    if (this.mDeferredOnCancelListener != null)
    {
      setOnCancelListener(this.mDeferredOnCancelListener);
      this.mDeferredOnCancelListener = null;
    }
    for (;;)
    {
      if (this.mDeferredShowProgress)
      {
        showProgress(this.mDeferredCurrent, this.mDeferredMax);
        this.mDeferredShowProgress = false;
      }
      return;
      setOnCancelListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView)
        {
          ProgressPreference.this.hideProgress();
        }
      });
    }
  }
  
  protected View onCreateView(ViewGroup paramViewGroup)
  {
    View localView = super.onCreateView(paramViewGroup);
    this.mProgressBar = ((ProgressBar)localView.findViewById(2131230910));
    this.mCancelButton = ((Button)localView.findViewById(2131230911));
    this.mProgressGroup = ((LinearLayout)localView.findViewById(16908312));
    return localView;
  }
  
  public void onProgress(int paramInt1, int paramInt2)
  {
    if (this.mProgressBar != null)
    {
      if ((paramInt1 != -1) && (paramInt2 != -1))
      {
        this.mProgressBar.setIndeterminate(false);
        this.mProgressBar.setMax(paramInt2);
        this.mProgressBar.setProgress(paramInt1);
      }
    }
    else {
      return;
    }
    this.mProgressBar.setIndeterminate(true);
  }
  
  public void setOnCancelListener(View.OnClickListener paramOnClickListener)
  {
    if (this.mCancelButton == null)
    {
      this.mDeferredOnCancelListener = paramOnClickListener;
      return;
    }
    this.mCancelButton.setOnClickListener(paramOnClickListener);
  }
  
  public void setSummary(CharSequence paramCharSequence)
  {
    super.setSummary(paramCharSequence);
  }
  
  public void showProgress(int paramInt1, int paramInt2)
  {
    if (this.mProgressBar == null)
    {
      this.mDeferredShowProgress = true;
      if ((paramInt1 != -1) && (paramInt2 != -1))
      {
        this.mDeferredCurrent = paramInt1;
        this.mDeferredMax = paramInt2;
      }
      return;
    }
    if ((paramInt1 != -1) && (paramInt2 != -1))
    {
      this.mProgressBar.setIndeterminate(false);
      this.mProgressBar.setMax(paramInt2);
      this.mProgressBar.setProgress(paramInt1);
    }
    for (;;)
    {
      this.mProgressGroup.setVisibility(0);
      this.mProgressBar.setVisibility(0);
      this.mCancelButton.setVisibility(0);
      return;
      this.mProgressBar.setIndeterminate(true);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.ProgressPreference
 * JD-Core Version:    0.7.0.1
 */