package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.Region;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.preferences.TouchTypePreferences;

public final class DockedCompactPane
  extends CompactPane
  implements View.OnTouchListener
{
  private FlipTouchHandler mFlipHandler;
  private final ImageView[] mFlipTabs;
  
  public DockedCompactPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    paintBackground();
    ImageView[] arrayOfImageView = new ImageView[2];
    arrayOfImageView[0] = ((ImageView)this.mPaneView.findViewById(2131230856));
    arrayOfImageView[1] = ((ImageView)this.mPaneView.findViewById(2131230857));
    this.mFlipTabs = arrayOfImageView;
  }
  
  private void alignWithDockingArea(int paramInt)
  {
    movePaneTo(this.mPaneView, 0, this.mChoreographer.getWindowHeight() - paramInt, this.mChoreographer.getWindowWidth(), paramInt);
  }
  
  private void paintBackground()
  {
    ThemeProperties localThemeProperties = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties();
    this.mPaneView.setBackgroundDrawable(localThemeProperties.getDockedBackground());
  }
  
  private void setOnSide(int paramInt)
  {
    int[] arrayOfInt = getCorrectKeyboardSize();
    RelativeLayout.LayoutParams localLayoutParams = (RelativeLayout.LayoutParams)this.mKeyboardWrapper.getLayoutParams();
    if (paramInt == 1)
    {
      this.mPreferences.setDockedCompactHandedness(this.mContext, 1);
      setupFlipHandles();
      alignWithDockingArea(kbToPaneHeight(arrayOfInt[1]));
      localLayoutParams.addRule(11, 0);
      localLayoutParams.addRule(9, -1);
      this.mKeyboardWrapper.setLayoutParams(localLayoutParams);
    }
    for (;;)
    {
      notifyAboutBounds();
      return;
      this.mPreferences.setDockedCompactHandedness(this.mContext, 0);
      setupFlipHandles();
      alignWithDockingArea(kbToPaneHeight(arrayOfInt[1]));
      localLayoutParams.addRule(9, 0);
      localLayoutParams.addRule(11, -1);
      this.mKeyboardWrapper.setLayoutParams(localLayoutParams);
    }
  }
  
  private void setupFlipHandles()
  {
    if (this.mPreferences.getDockedCompactHandedness(this.mContext) == 0)
    {
      this.mFlipTabs[0].setVisibility(0);
      this.mFlipTabs[1].setVisibility(8);
      return;
    }
    this.mFlipTabs[0].setVisibility(8);
    this.mFlipTabs[1].setVisibility(0);
  }
  
  protected int[] getAspectRatioResourceIds()
  {
    return new int[] { 2131361919, 2131361920 };
  }
  
  protected int getBorderWidth()
  {
    return 0;
  }
  
  public int getContentInset()
  {
    return this.mInputView.getHeight() - getView().getHeight();
  }
  
  protected int[] getCorrectKeyboardSize()
  {
    int[] arrayOfInt = super.getCorrectKeyboardSize();
    arrayOfInt[0] = Math.min(arrayOfInt[0], this.mChoreographer.getWindowWidth() - getFlipTabWidth());
    return arrayOfInt;
  }
  
  int[] getDragTabSize()
  {
    return new int[] { 0, 0 };
  }
  
  int getFlipTabWidth()
  {
    return (int)this.mContext.getResources().getDimension(2131361897);
  }
  
  public int getLeftOfKeyboard()
  {
    int i = this.mPreferences.getDockedCompactHandedness(this.mContext);
    int j = 0;
    if (i == 1)
    {
      int[] arrayOfInt = getCorrectKeyboardSize();
      j = this.mChoreographer.getWindowWidth() - arrayOfInt[0];
    }
    return j;
  }
  
  public int getLeftOfPane(View.OnTouchListener paramOnTouchListener)
  {
    return getLeftOfKeyboard();
  }
  
  public int getPaneWidth()
  {
    if (this.mPreferences.getDockedCompactHandedness(this.mContext) == 1) {
      return this.mKeyboardWrapper.getWidth() + this.mFlipTabs[0].getWidth();
    }
    return this.mKeyboardWrapper.getWidth() + this.mFlipTabs[1].getWidth();
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.COMPACT_DOCKED;
  }
  
  protected Rect getStoredKeyboardBounds()
  {
    int[] arrayOfInt = getCorrectKeyboardSize();
    return new Rect(getLeftOfKeyboard(), 0, getLeftOfKeyboard() + arrayOfInt[0], arrayOfInt[1]);
  }
  
  protected int[] getStoredKeyboardPos()
  {
    int[] arrayOfInt = getCorrectKeyboardSize();
    int i = this.mChoreographer.getWindowHeight() - arrayOfInt[1];
    if (this.mPreferences.getDockedCompactHandedness(this.mContext) == 1) {}
    for (int j = 0;; j = this.mChoreographer.getWindowWidth() - arrayOfInt[0]) {
      return new int[] { j, i };
    }
  }
  
  public Region getTouchableRegion()
  {
    return new Region(this.mPaneView.getLeft(), this.mPaneView.getTop(), this.mPaneView.getRight(), this.mPaneView.getBottom());
  }
  
  public int getVisibleInset()
  {
    return this.mInputView.getHeight() - getView().getHeight();
  }
  
  protected boolean isDocked()
  {
    return true;
  }
  
  protected void movePostResize()
  {
    setOnSide(this.mPreferences.getDockedCompactHandedness(this.mContext));
  }
  
  void onDragFinished() {}
  
  public boolean onTouch(View paramView, MotionEvent paramMotionEvent)
  {
    if (this.mPreferences.getDockedCompactHandedness(this.mContext) == 0)
    {
      setOnSide(1);
      return true;
    }
    setOnSide(0);
    return true;
  }
  
  protected void savePosition() {}
  
  void setAlpha(float paramFloat) {}
  
  protected void setDragTabState(KeyboardChoreographer.TabViewState paramTabViewState)
  {
    this.mTabSingular.setVisibility(8);
  }
  
  public void setPaneSize(int paramInt1, int paramInt2)
  {
    super.setPaneSize(this.mKeyboardWrapper, paramInt1, paramInt2);
    super.setPaneSize(this.mPaneView, this.mChoreographer.getWindowWidth(), paramInt2);
  }
  
  protected void setThemedResources()
  {
    super.setThemedResources();
    paintBackground();
    ThemeProperties localThemeProperties = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties();
    this.mFlipTabs[0].setImageDrawable(localThemeProperties.getLeftFlipTab());
    this.mFlipTabs[1].setImageDrawable(localThemeProperties.getRightFlipTab());
  }
  
  protected void setupDragTabListeners()
  {
    this.mFlipHandler = new FlipTouchHandler(this, this.mContext, this.mPreferences);
    this.mFlipTabs[0].setOnTouchListener(this.mFlipHandler);
    this.mFlipTabs[1].setOnTouchListener(this.mFlipHandler);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.DockedCompactPane
 * JD-Core Version:    0.7.0.1
 */