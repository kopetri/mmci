package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;

public final class DockedSplitPane
  extends DockedFullPane
{
  private float mRibbonSplitEnd = 0.0F;
  private float mRibbonSplitStart = 0.0F;
  private final DualRibbonHolder mSplitRibbonHolder = new DualRibbonHolder(this.mContext);
  
  public DockedSplitPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  private void setAppropriateRibbon()
  {
    if (useDualRibbons()) {
      if ((this.mRibbonFrame.getChildCount() == 0) || (!(this.mRibbonFrame.getChildAt(0) instanceof DualRibbonHolder)))
      {
        this.mSplitRibbonHolder.removeAllViews();
        this.mRibbonFrame.removeAllViews();
        Pair localPair = this.mRibbonStateHandler.getNewDualRibbonViews();
        this.mSplitRibbonHolder.addView((View)localPair.first);
        this.mSplitRibbonHolder.addView((View)localPair.second);
        this.mRibbonFrame.addView(this.mSplitRibbonHolder);
        this.mRibbonStateHandler.onStartInput();
      }
    }
    for (;;)
    {
      this.mRibbonStateHandler.addVisibilityListener(this);
      return;
      if ((this.mRibbonFrame.getChildCount() == 0) || (!(this.mRibbonFrame.getChildAt(0) instanceof SingularPane.SingleRibbonHolder)))
      {
        this.mRibbonFrame.removeAllViews();
        this.mRibbonFrame.addView(wrappedNewRibbon());
        this.mRibbonStateHandler.onStartInput();
      }
    }
  }
  
  private boolean useDualRibbons()
  {
    if (this.mRibbonSplitStart != 0.0F)
    {
      if (this.mRibbonSplitEnd == 0.0F) {
        this.mRibbonSplitEnd = (1.0F - this.mRibbonSplitStart);
      }
      return true;
    }
    return false;
  }
  
  protected void addCandidatesView()
  {
    if ((this.mRibbonFrame.getChildCount() == 0) || (!(this.mRibbonFrame.getChildAt(0) instanceof DualRibbonHolder))) {
      setAppropriateRibbon();
    }
    sizeRibbon();
  }
  
  protected int getLayoutResId()
  {
    boolean bool = this.mPreferences.getShowSplitNumpad(this.mContext, this.mContext.getResources().getBoolean(2131492877));
    if (this.mKeyboardSwitcher.getCurrentKeyboardLayoutId() != this.mPreferences.getKeyboardLayout().getSplitLayoutResId(bool)) {
      return this.mPreferences.getKeyboardLayout().getSplitLayoutResId(bool);
    }
    if (this.mKeyboardSwitcher.getCurrentKeyboardLayoutId() > 0) {
      return this.mKeyboardSwitcher.getCurrentKeyboardLayoutId();
    }
    return this.mPreferences.getKeyboardLayout().getSplitLayoutResId(bool);
  }
  
  protected String getPositionPrefsKey()
  {
    return "pref_keyboard_position_vertical_docked_split";
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.SPLIT_DOCKED;
  }
  
  protected void setRibbonSplit(float paramFloat1, float paramFloat2)
  {
    this.mRibbonSplitStart = paramFloat1;
    this.mRibbonSplitEnd = paramFloat2;
    sizeRibbon();
  }
  
  protected void setThemedResources()
  {
    super.setThemedResources();
    ThemeProperties localThemeProperties = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties();
    this.mMainKeyboardView.setBackgroundDrawable(localThemeProperties.getBackground());
  }
  
  protected void sizeRibbon()
  {
    setAppropriateRibbon();
    if (useDualRibbons())
    {
      this.mRibbonStateHandler.requestWidthLeftAt((int)(this.mChoreographer.getWindowWidth() * this.mRibbonSplitStart), 0);
      this.mRibbonStateHandler.requestWidthRightAt((int)(this.mChoreographer.getWindowWidth() * (1.0F - this.mRibbonSplitEnd)), (int)(this.mChoreographer.getWindowWidth() * this.mRibbonSplitEnd));
      return;
    }
    super.sizeRibbon();
  }
  
  private final class DualRibbonHolder
    extends RibbonContainer
  {
    public DualRibbonHolder(Context paramContext)
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.DockedSplitPane
 * JD-Core Version:    0.7.0.1
 */