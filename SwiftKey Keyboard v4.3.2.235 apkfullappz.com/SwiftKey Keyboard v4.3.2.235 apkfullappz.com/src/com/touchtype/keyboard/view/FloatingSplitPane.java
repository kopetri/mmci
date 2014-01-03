package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.touchtype.keyboard.DualKeyPressModelUpdater;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.SingularKeyPressModelUpdater;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.DeviceUtils;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutData.LayoutMap;

public final class FloatingSplitPane
  extends Pane
{
  private Drawable mBackground;
  private PaneDragHandler mDragHandlerLeft;
  private PaneDragHandler mDragHandlerRight;
  private InputEventModelTransformingWrapper mIemWrapperLeft;
  private InputEventModelTransformingWrapper mIemWrapperRight;
  private DualKeyPressModelUpdater mKeyPressModelUpdater;
  private final KeyboardViewContainer mKeyboardViewContainerLeft;
  private final KeyboardViewContainer mKeyboardViewContainerRight;
  private final View mLeftPane;
  private final FrameLayout mRibbonFrameLeft;
  private final FrameLayout mRibbonFrameRight;
  private final RibbonStateHandler mRibbonStateHandler;
  private final View mRightPane;
  private ImageView mTabLeft;
  private ImageView mTabRight;
  private final View mTopLevelWrapper;
  
  public FloatingSplitPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramTouchTypePreferences, paramViewGroup, paramKeyboardSwitcher, paramRibbonStateHandler, paramKeyboardChoreographer);
    this.mTopLevelWrapper = ((FrameLayout)((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903069, null));
    this.mRibbonStateHandler = paramRibbonStateHandler;
    this.mLeftPane = ((ViewGroup)this.mTopLevelWrapper.findViewById(2131230818));
    this.mRightPane = ((ViewGroup)this.mTopLevelWrapper.findViewById(2131230819));
    this.mKeyboardViewContainerLeft = ((KeyboardViewContainer)this.mLeftPane.findViewById(2131230855));
    this.mKeyboardViewContainerRight = ((KeyboardViewContainer)this.mRightPane.findViewById(2131230855));
    this.mRibbonFrameLeft = ((FrameLayout)this.mLeftPane.findViewById(2131230854));
    this.mRibbonFrameRight = ((FrameLayout)this.mRightPane.findViewById(2131230854));
    this.mTabLeft = ((ImageView)this.mLeftPane.findViewById(2131230852));
    this.mTabRight = ((ImageView)this.mRightPane.findViewById(2131230852));
  }
  
  private Rect getKeyPressModelKeyboardBoundsLeft()
  {
    int[] arrayOfInt = getCorrectKeyboardSize();
    return new Rect(0, 0, arrayOfInt[0], arrayOfInt[1]);
  }
  
  private Rect getKeyPressModelKeyboardBoundsRight()
  {
    int[] arrayOfInt = getCorrectKeyboardSize();
    int i = this.mChoreographer.getWindowWidth() - arrayOfInt[0];
    return new Rect(i, 0, i + arrayOfInt[0], arrayOfInt[1]);
  }
  
  private int[] getStoredLeftKeyboardPosition()
  {
    return new int[] { this.mPreferences.getKeyboardPosition(this.mContext, "pref_keyboard_position_horizontal_floating_split", paneToKbX(0)), this.mPreferences.getKeyboardPosition(this.mContext, "pref_keyboard_position_vertical_floating", defaultFloatingY()) };
  }
  
  private int movePanesSymmetrically(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i;
    label50:
    int j;
    int k;
    ViewGroup.MarginLayoutParams localMarginLayoutParams2;
    if ((paramView == this.mRightPane) && (paramInt1 != -1))
    {
      paramInt1 = this.mChoreographer.getWindowWidth() - paramInt1 - paramInt3;
      ViewGroup.MarginLayoutParams localMarginLayoutParams1 = (ViewGroup.MarginLayoutParams)this.mTopLevelWrapper.getLayoutParams();
      if (paramInt1 != -1) {
        break label184;
      }
      i = localMarginLayoutParams1.leftMargin;
      j = Math.min(Math.max(paramInt2, 0), this.mChoreographer.getWindowHeight() - paramInt4);
      k = 2 * (this.mChoreographer.getWindowWidth() / 2 - i);
      localMarginLayoutParams1.setMargins(i, j, 0, 0);
      localMarginLayoutParams1.width = k;
      localMarginLayoutParams1.height = paramInt4;
      this.mTopLevelWrapper.setLayoutParams(localMarginLayoutParams1);
      localMarginLayoutParams2 = (ViewGroup.MarginLayoutParams)this.mRightPane.getLayoutParams();
      if (paramInt1 != -1) {
        break label209;
      }
    }
    label184:
    label209:
    for (int m = localMarginLayoutParams2.leftMargin;; m = k - paramInt3)
    {
      localMarginLayoutParams2.setMargins(m, 0, 0, 0);
      this.mRightPane.setLayoutParams(localMarginLayoutParams2);
      return paramInt2 - j;
      if (paramView != this.mTopLevelWrapper) {
        break;
      }
      break;
      i = Math.min(Math.max(paramInt1, 0), this.mChoreographer.getWindowWidth() / 2 - paramInt3);
      break label50;
    }
  }
  
  private void saveVerticalPosition()
  {
    this.mPreferences.setKeyboardPosition(this.mContext, "pref_keyboard_position_vertical_floating", getTopOfKeyboard());
  }
  
  protected void addCandidatesView()
  {
    if (this.mRibbonFrameLeft.getChildCount() == 0)
    {
      Pair localPair = this.mRibbonStateHandler.getNewDualRibbonViews();
      this.mRibbonStateHandler.addVisibilityListener(this);
      this.mRibbonFrameLeft.addView((View)localPair.first);
      this.mRibbonFrameRight.addView((View)localPair.second);
    }
  }
  
  protected int[] getCorrectKeyboardSize()
  {
    Resources localResources = this.mContext.getResources();
    boolean bool = DeviceUtils.isDeviceInLandscape(this.mContext);
    int i = this.mKeyboardViewContainerLeft.getPreferredHeight();
    float f = this.mKeyboardViewContainerLeft.getTotalRowWeight();
    if (bool) {}
    for (int j = 2131361916;; j = 2131361915) {
      return new int[] { Math.min((int)(localResources.getFraction(j, 1, 1) * i * (4.0F / f)), paneToKbWidth(this.mChoreographer.getWindowWidth() / 2)), i };
    }
  }
  
  BaseKeyboardView<?> getKeyboardView()
  {
    return this.mKeyboardViewContainerLeft.getKeyboardView();
  }
  
  protected int getLayoutResId()
  {
    return this.mPreferences.getKeyboardLayout().getSplitLeftLayoutResId();
  }
  
  public int getLeftOfKeyboard()
  {
    return paneToKbX(this.mTopLevelWrapper.getLeft());
  }
  
  public int getLeftOfPane(View.OnTouchListener paramOnTouchListener)
  {
    if (paramOnTouchListener == this.mDragHandlerLeft) {
      return this.mTopLevelWrapper.getLeft();
    }
    return this.mTopLevelWrapper.getLeft() + this.mRightPane.getLeft();
  }
  
  public int getPaneHeight()
  {
    return this.mLeftPane.getHeight();
  }
  
  public int getPaneWidth()
  {
    return this.mLeftPane.getWidth();
  }
  
  protected int getPreferredPaneHeight()
  {
    return kbToPaneHeight(this.mKeyboardViewContainerLeft.getPreferredHeight());
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.SPLIT_FLOATING;
  }
  
  public Region getTouchableRegion()
  {
    int[] arrayOfInt = getDragTabSize();
    Path localPath = new Path();
    int i = this.mTopLevelWrapper.getTop() + this.mLeftPane.getBottom() - arrayOfInt[1];
    localPath.addRect(this.mTopLevelWrapper.getLeft() + this.mLeftPane.getLeft(), this.mTopLevelWrapper.getTop() + this.mLeftPane.getTop(), this.mTopLevelWrapper.getLeft() + this.mLeftPane.getRight(), i, Path.Direction.CW);
    localPath.addRect(this.mTopLevelWrapper.getLeft() + this.mRightPane.getLeft(), this.mTopLevelWrapper.getTop() + this.mRightPane.getTop(), this.mTopLevelWrapper.getLeft() + this.mRightPane.getRight(), i, Path.Direction.CW);
    int j = this.mTopLevelWrapper.getLeft() + this.mLeftPane.getWidth() / 2;
    int k = this.mTopLevelWrapper.getLeft() + this.mRightPane.getLeft() + this.mRightPane.getWidth() / 2;
    localPath.addRect(j - arrayOfInt[0] / 2, i, j + arrayOfInt[0] / 2, i + arrayOfInt[1], Path.Direction.CW);
    localPath.addRect(k - arrayOfInt[0] / 2, i, k + arrayOfInt[0] / 2, i + arrayOfInt[1], Path.Direction.CW);
    Region localRegion = new Region();
    localRegion.setPath(localPath, new Region(this.mTopLevelWrapper.getLeft(), this.mTopLevelWrapper.getTop(), this.mTopLevelWrapper.getRight(), this.mTopLevelWrapper.getBottom()));
    return localRegion;
  }
  
  public View getView()
  {
    return this.mTopLevelWrapper;
  }
  
  protected boolean isDocked()
  {
    return false;
  }
  
  protected boolean isRibbonShown()
  {
    return this.mRibbonFrameLeft.getVisibility() == 0;
  }
  
  public int movePaneTo(int paramInt1, int paramInt2, int paramInt3, int paramInt4, PaneDragHandler paramPaneDragHandler)
  {
    if (paramPaneDragHandler == this.mDragHandlerLeft) {
      return movePanesSymmetrically(this.mLeftPane, paramInt1, paramInt2, paramInt3, paramInt4);
    }
    return movePanesSymmetrically(this.mRightPane, paramInt1, paramInt2, paramInt3, paramInt4);
  }
  
  protected void movePostResize()
  {
    int[] arrayOfInt1 = getCorrectKeyboardSize();
    int[] arrayOfInt2 = getStoredLeftKeyboardPosition();
    movePaneTo(kbToPaneX(arrayOfInt2[0]), kbToPaneY(arrayOfInt2[1]), kbToPaneWidth(arrayOfInt1[0]), kbToPaneHeight(arrayOfInt1[1]), this.mDragHandlerLeft);
  }
  
  protected void notifyAboutBounds()
  {
    Rect localRect1 = getKeyPressModelKeyboardBoundsLeft();
    Rect localRect2 = getKeyPressModelKeyboardBoundsRight();
    this.mKeyPressModelUpdater.onBoundsUpdated(localRect1, localRect2);
    this.mIemWrapperLeft.newBoundsOnScreen(localRect1);
    this.mIemWrapperRight.newBoundsOnScreen(localRect2);
  }
  
  public boolean onBack()
  {
    return this.mKeyboardViewContainerLeft.getKeyboardView().handleBack() | this.mKeyboardViewContainerRight.getKeyboardView().handleBack();
  }
  
  public void onClose()
  {
    this.mInputView.removeView(this.mTopLevelWrapper);
    ThemeManager.getInstance(this.mContext).removeListener(this);
    ((FrameLayout)this.mLeftPane.findViewById(2131230854)).removeAllViews();
    ((FrameLayout)this.mRightPane.findViewById(2131230854)).removeAllViews();
    this.mRibbonStateHandler.removeVisibilityListener(this);
  }
  
  public void onDragBy(int paramInt1, int paramInt2, PaneDragHandler paramPaneDragHandler)
  {
    clippedOnDrag(movePaneTo(paramInt1 + this.mDragStartX, paramInt2 + this.mDragStartY, getPaneWidth(), getPaneHeight(), paramPaneDragHandler));
  }
  
  public void onThemeChanged()
  {
    setThemedResources();
    this.mLeftPane.invalidate();
  }
  
  protected void savePosition()
  {
    saveVerticalPosition();
    this.mPreferences.setKeyboardPosition(this.mContext, "pref_keyboard_position_horizontal_floating_split", getLeftOfKeyboard());
  }
  
  void setAlpha(float paramFloat)
  {
    this.mTopLevelWrapper.setAlpha(paramFloat);
  }
  
  protected void setCachedKeyboardDrawing(boolean paramBoolean)
  {
    this.mKeyboardViewContainerLeft.getKeyboardView().setCachedDrawing(paramBoolean);
    this.mKeyboardViewContainerRight.getKeyboardView().setCachedDrawing(paramBoolean);
  }
  
  protected int[] setCorrectPaneSize()
  {
    int[] arrayOfInt1 = getCorrectKeyboardSize();
    int[] arrayOfInt2 = new int[2];
    arrayOfInt2[0] = kbToPaneWidth(arrayOfInt1[0]);
    arrayOfInt2[1] = kbToPaneHeight(arrayOfInt1[1]);
    setPaneSize(arrayOfInt2[0], arrayOfInt2[1]);
    return arrayOfInt2;
  }
  
  protected void setDragTabState(KeyboardChoreographer.TabViewState paramTabViewState)
  {
    this.mTabLeft.setVisibility(8);
    this.mTabLeft.setAlpha(255);
    this.mTabRight.setVisibility(8);
    this.mTabRight.setAlpha(255);
    if ((paramTabViewState == KeyboardChoreographer.TabViewState.PARTIAL) || (paramTabViewState == KeyboardChoreographer.TabViewState.SHOW_MOVE))
    {
      this.mTabLeft.setVisibility(0);
      this.mTabRight.setVisibility(0);
      if (paramTabViewState == KeyboardChoreographer.TabViewState.PARTIAL)
      {
        this.mTabRight.setAlpha(128);
        this.mTabLeft.setAlpha(128);
      }
    }
    this.mTopLevelWrapper.invalidate();
  }
  
  public void setKeyboardView(MainKeyboardView paramMainKeyboardView, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper, SingularKeyPressModelUpdater paramSingularKeyPressModelUpdater, boolean paramBoolean)
  {
    throw new IllegalStateException("Can't set singular KeyboardView on a split pane");
  }
  
  public void setKeyboardView(MainKeyboardView paramMainKeyboardView1, MainKeyboardView paramMainKeyboardView2, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper1, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper2, DualKeyPressModelUpdater paramDualKeyPressModelUpdater, boolean paramBoolean)
  {
    this.mKeyboardViewContainerLeft.setKeyboardView(paramMainKeyboardView1, paramBoolean, this.mInputView);
    this.mKeyboardViewContainerRight.setKeyboardView(paramMainKeyboardView2, paramBoolean, this.mInputView);
    paramMainKeyboardView1.setPopupParent(this.mInputView);
    paramMainKeyboardView2.setPopupParent(this.mInputView);
    this.mIemWrapperLeft = paramInputEventModelTransformingWrapper1;
    this.mIemWrapperRight = paramInputEventModelTransformingWrapper2;
    this.mKeyboardViewContainerLeft.invalidate();
    this.mKeyboardViewContainerRight.invalidate();
    this.mKeyPressModelUpdater = paramDualKeyPressModelUpdater;
    setCorrectPaneSize();
    movePostResize();
    notifyAboutBounds();
  }
  
  public void setPaneSize(int paramInt1, int paramInt2)
  {
    setPaneSize(this.mLeftPane, paramInt1, paramInt2);
    setPaneSize(this.mRightPane, paramInt1, paramInt2);
    ViewGroup.LayoutParams localLayoutParams = this.mTopLevelWrapper.getLayoutParams();
    setPaneSize(this.mTopLevelWrapper, localLayoutParams.width, paramInt2);
  }
  
  protected void setRibbonVisibility(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.mRibbonFrameLeft.setVisibility(0);
      this.mRibbonFrameRight.setVisibility(0);
      return;
    }
    this.mRibbonFrameLeft.setVisibility(8);
    this.mRibbonFrameRight.setVisibility(8);
  }
  
  protected void setThemedResources()
  {
    ThemeProperties localThemeProperties = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties();
    this.mBackground = localThemeProperties.getFloatingKeyboardBackground();
    this.mLeftPane.findViewById(2131230853).setBackgroundDrawable(this.mBackground);
    this.mRightPane.findViewById(2131230853).setBackgroundDrawable(this.mBackground);
    this.mRibbonFrameLeft.setBackgroundDrawable(localThemeProperties.getCandidateBackground());
    this.mRibbonFrameRight.setBackgroundDrawable(localThemeProperties.getCandidateBackground());
    this.mTabLeft.setBackgroundDrawable(localThemeProperties.getBottomMoveTab());
    this.mTabRight.setBackgroundDrawable(localThemeProperties.getBottomMoveTab());
  }
  
  protected void setupBorder()
  {
    ViewGroup localViewGroup1 = (ViewGroup)this.mRightPane.findViewById(2131230853);
    ViewGroup localViewGroup2 = (ViewGroup)this.mLeftPane.findViewById(2131230853);
    localViewGroup1.setPadding(getBorderWidth(), getBorderWidth(), getBorderWidth(), getBorderWidth());
    localViewGroup2.setPadding(getBorderWidth(), getBorderWidth(), getBorderWidth(), getBorderWidth());
  }
  
  protected void setupDragTabListeners()
  {
    this.mDragHandlerLeft = new PaneDragHandler(this);
    this.mDragHandlerRight = new PaneDragHandler(this);
    this.mTabLeft.setOnTouchListener(this.mDragHandlerLeft);
    this.mTabRight.setOnTouchListener(this.mDragHandlerRight);
  }
  
  protected void sizeRibbon()
  {
    Pair localPair = this.mRibbonStateHandler.getDualRibbonViews();
    ((View)localPair.first).measure(View.MeasureSpec.makeMeasureSpec(getCorrectKeyboardSize()[0], 1073741824), 0);
    ((View)localPair.second).measure(View.MeasureSpec.makeMeasureSpec(getCorrectKeyboardSize()[0], 1073741824), 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.FloatingSplitPane
 * JD-Core Version:    0.7.0.1
 */