package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Region;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.SingularKeyPressModelUpdater;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.candidates.VisibilityListener;
import com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;

public abstract class Pane
  implements VisibilityListener, OnThemeChangedListener
{
  private int mBorderWidth = -1;
  protected final KeyboardChoreographer mChoreographer;
  private boolean mClosed = false;
  protected final Context mContext;
  protected int mDragStartX;
  protected int mDragStartY;
  private int[] mDragTabSize = null;
  private PaneDragHandler mDraggedBy;
  protected boolean mDraggedOffScreen = false;
  protected final ViewGroup mInputView;
  protected final KeyboardSwitcher mKeyboardSwitcher;
  protected final TouchTypePreferences mPreferences;
  protected final RibbonStateHandler mRibbonStateHandler;
  
  protected Pane(Context paramContext, TouchTypePreferences paramTouchTypePreferences, ViewGroup paramViewGroup, KeyboardSwitcher paramKeyboardSwitcher, RibbonStateHandler paramRibbonStateHandler, KeyboardChoreographer paramKeyboardChoreographer)
  {
    this.mContext = paramContext;
    this.mPreferences = paramTouchTypePreferences;
    this.mInputView = paramViewGroup;
    this.mKeyboardSwitcher = paramKeyboardSwitcher;
    this.mRibbonStateHandler = paramRibbonStateHandler;
    this.mChoreographer = paramKeyboardChoreographer;
  }
  
  protected abstract void addCandidatesView();
  
  protected void applyTheme()
  {
    setThemedResources();
    this.mInputView.post(new Runnable()
    {
      public void run()
      {
        Pane.this.setThemedResources();
        Pane.this.mInputView.postInvalidate();
      }
    });
  }
  
  protected int calculatedRibbonHeight()
  {
    return this.mRibbonStateHandler.getCalculatedHeight();
  }
  
  protected void clippedOnDrag(int paramInt)
  {
    if (paramInt > 0.35D * getDragTabSize()[1])
    {
      this.mDraggedOffScreen = true;
      setDragTabState(KeyboardChoreographer.TabViewState.PARTIAL);
      return;
    }
    this.mDraggedOffScreen = false;
    setDragTabState(KeyboardChoreographer.TabViewState.SHOW_MOVE);
  }
  
  void closing()
  {
    this.mClosed = true;
    onClose();
  }
  
  protected int currentRibbonHeight()
  {
    if (isRibbonShown()) {
      return calculatedRibbonHeight();
    }
    return 0;
  }
  
  protected int defaultFloatingY()
  {
    return this.mChoreographer.getWindowHeight() / 2;
  }
  
  protected void dock()
  {
    this.mChoreographer.requestState(this.mContext.getResources().getInteger(2131558457));
  }
  
  void dragCancelled()
  {
    dragStopped();
  }
  
  protected void dragStopped()
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getView().getLayoutParams();
    if (localMarginLayoutParams.topMargin + localMarginLayoutParams.height / 2 < 0.35F * this.mChoreographer.getWindowHeight()) {
      this.mPreferences.getTouchTypeStats().incrementStatistic("stats_keyboard_moved_to_top");
    }
    for (;;)
    {
      setAlpha(1.0F);
      this.mDraggedBy = null;
      this.mDraggedOffScreen = false;
      return;
      if (localMarginLayoutParams.topMargin + localMarginLayoutParams.height / 2 > 0.65F * this.mChoreographer.getWindowHeight()) {
        this.mPreferences.getTouchTypeStats().incrementStatistic("stats_keyboard_moved_to_bottom");
      } else {
        this.mPreferences.getTouchTypeStats().incrementStatistic("stats_keyboard_moved_to_middle");
      }
    }
  }
  
  protected void exitResizeMode()
  {
    savePosition();
    setCachedKeyboardDrawing(false);
    sizeRibbon();
    getView().requestLayout();
    notifyAboutBounds();
  }
  
  protected int getBorderWidth()
  {
    if (this.mBorderWidth == -1) {
      this.mBorderWidth = ((int)this.mContext.getResources().getDimension(2131361896));
    }
    return this.mBorderWidth;
  }
  
  public int getContentInset()
  {
    return this.mInputView.getHeight();
  }
  
  protected abstract int[] getCorrectKeyboardSize();
  
  int[] getDragTabSize()
  {
    if (this.mDragTabSize == null)
    {
      BitmapFactory.Options localOptions = new BitmapFactory.Options();
      localOptions.inTargetDensity = this.mContext.getResources().getDisplayMetrics().densityDpi;
      Bitmap localBitmap = BitmapFactory.decodeResource(this.mContext.getResources(), 2130837909, localOptions);
      this.mDragTabSize = new int[] { localBitmap.getWidth(), localBitmap.getHeight() };
    }
    return this.mDragTabSize;
  }
  
  abstract BaseKeyboardView<?> getKeyboardView();
  
  protected abstract int getLayoutResId();
  
  abstract int getLeftOfPane(View.OnTouchListener paramOnTouchListener);
  
  protected int getLeftSafe(View paramView)
  {
    return ((ViewGroup.MarginLayoutParams)paramView.getLayoutParams()).leftMargin;
  }
  
  protected abstract int getPreferredPaneHeight();
  
  protected abstract KeyboardState getState();
  
  protected int getTopOfKeyboard()
  {
    return paneToKbY(getTopSafe(getView()));
  }
  
  protected int getTopSafe(View paramView)
  {
    return ((ViewGroup.MarginLayoutParams)paramView.getLayoutParams()).topMargin + this.mChoreographer.getWindowTopOffset(getPreferredPaneHeight());
  }
  
  abstract Region getTouchableRegion();
  
  abstract View getView();
  
  public int getVisibleInset()
  {
    return this.mInputView.getHeight();
  }
  
  void init()
  {
    this.mClosed = false;
    boolean bool1 = this.mPreferences.getKeyboardDockedState(this.mContext);
    this.mPreferences.setKeyboardDockedState(this.mContext, isDocked());
    KeyboardSwitcher localKeyboardSwitcher = this.mKeyboardSwitcher;
    int i = getLayoutResId();
    boolean bool2 = this.mPreferences.getKeyboardDockedState(this.mContext);
    boolean bool3 = false;
    if (bool1 != bool2) {
      bool3 = true;
    }
    localKeyboardSwitcher.selectKeyboard(i, bool3);
    setupRibbon();
    setupBorder();
    setupDragTabListeners();
    applyTheme();
    setDragTabState(KeyboardChoreographer.TabViewState.SHOW_MOVE);
    notifyAboutBounds();
  }
  
  boolean isClosed()
  {
    return this.mClosed;
  }
  
  protected abstract boolean isDocked();
  
  boolean isDraggedBySomeoneElse(View.OnTouchListener paramOnTouchListener)
  {
    return (this.mDraggedBy != null) && (this.mDraggedBy != paramOnTouchListener);
  }
  
  protected abstract boolean isRibbonShown();
  
  protected int kbToPaneHeight(int paramInt)
  {
    return paramInt + 2 * getBorderWidth() + currentRibbonHeight() + getDragTabSize()[1];
  }
  
  protected int kbToPaneWidth(int paramInt)
  {
    return paramInt + 2 * getBorderWidth();
  }
  
  protected int kbToPaneX(int paramInt)
  {
    return paramInt - getBorderWidth();
  }
  
  protected int kbToPaneY(int paramInt)
  {
    return paramInt - currentRibbonHeight() - getBorderWidth();
  }
  
  protected int movePaneTo(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)paramView.getLayoutParams();
    if (paramInt1 == -1) {}
    for (int i = localMarginLayoutParams.leftMargin;; i = Math.min(Math.max(paramInt1, 0), this.mChoreographer.getWindowWidth() - paramInt3))
    {
      int j = Math.min(Math.max(paramInt2, 0), this.mChoreographer.getWindowHeight() - paramInt4) - this.mChoreographer.getWindowTopOffset(getPreferredPaneHeight());
      localMarginLayoutParams.setMargins(i, j, 0, 0);
      paramView.setLayoutParams(localMarginLayoutParams);
      return paramInt2 - j;
    }
  }
  
  protected abstract void movePostResize();
  
  protected abstract void notifyAboutBounds();
  
  abstract boolean onBack();
  
  abstract void onClose();
  
  abstract void onDragBy(int paramInt1, int paramInt2, PaneDragHandler paramPaneDragHandler);
  
  void onDragFinished()
  {
    if (this.mDraggedOffScreen)
    {
      this.mPreferences.getTouchTypeStats().incrementStatistic("stats_keyboard_drag_docked");
      dock();
    }
    for (;;)
    {
      dragStopped();
      return;
      savePosition();
    }
  }
  
  void onDragStarted(PaneDragHandler paramPaneDragHandler)
  {
    setAlpha(0.75F);
    this.mDraggedBy = paramPaneDragHandler;
    this.mDraggedOffScreen = false;
    this.mDragStartX = getLeftOfPane(paramPaneDragHandler);
    this.mDragStartY = getTopSafe(getView());
  }
  
  public void onResize(int paramInt)
  {
    this.mPreferences.setKeyboardScale(this.mContext, getState().getName(), paramInt);
    setCorrectPaneSize();
    movePostResize();
    exitResizeMode();
  }
  
  public void onVisibilityChanged(boolean paramBoolean)
  {
    setRibbonVisibility(paramBoolean);
    setCorrectPaneSize();
    movePostResize();
  }
  
  protected int paneToKbWidth(int paramInt)
  {
    return paramInt - 2 * getBorderWidth();
  }
  
  protected int paneToKbX(int paramInt)
  {
    return paramInt + getBorderWidth();
  }
  
  protected int paneToKbY(int paramInt)
  {
    return paramInt + getBorderWidth() + currentRibbonHeight();
  }
  
  protected abstract void savePosition();
  
  abstract void setAlpha(float paramFloat);
  
  protected abstract void setCachedKeyboardDrawing(boolean paramBoolean);
  
  protected abstract int[] setCorrectPaneSize();
  
  protected abstract void setDragTabState(KeyboardChoreographer.TabViewState paramTabViewState);
  
  abstract void setKeyboardView(MainKeyboardView paramMainKeyboardView, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper, SingularKeyPressModelUpdater paramSingularKeyPressModelUpdater, boolean paramBoolean);
  
  public void setPaneSize(int paramInt1, int paramInt2)
  {
    setPaneSize(getView(), paramInt1, paramInt2);
  }
  
  public void setPaneSize(View paramView, int paramInt1, int paramInt2)
  {
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    localLayoutParams.width = paramInt1;
    localLayoutParams.height = paramInt2;
    paramView.setLayoutParams(localLayoutParams);
    this.mChoreographer.maximiseBackgroundView();
  }
  
  protected abstract void setRibbonVisibility(boolean paramBoolean);
  
  protected abstract void setThemedResources();
  
  protected abstract void setupBorder();
  
  protected abstract void setupDragTabListeners();
  
  protected void setupRibbon()
  {
    this.mRibbonStateHandler.addVisibilityListener(this);
    addCandidatesView();
    this.mRibbonStateHandler.onStartInput();
  }
  
  protected void sizeRibbon()
  {
    this.mRibbonStateHandler.getRibbonView().measure(View.MeasureSpec.makeMeasureSpec(getCorrectKeyboardSize()[0], 1073741824), 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.Pane
 * JD-Core Version:    0.7.0.1
 */