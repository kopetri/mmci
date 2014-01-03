package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.MainKeyboard;
import com.touchtype.keyboard.SingularKeyPressModelUpdater;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.preferences.TouchTypePreferences;

public abstract class SingularPane
  extends Pane
{
  private InputEventModelTransformingWrapper mIemWrapper;
  private SingularKeyPressModelUpdater mKPMUKeyPressModelUpdater;
  protected final KeyboardViewContainer mKeyboardViewContainer;
  protected final LinearLayout mKeyboardWrapper;
  protected MainKeyboardView mMainKeyboardView;
  protected final ViewGroup mPaneView;
  protected final FrameLayout mRibbonFrame;
  protected ImageView mTabSingular;
  
  public SingularPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramTouchTypePreferences, paramViewGroup, paramKeyboardSwitcher, paramRibbonStateHandler, paramKeyboardChoreographer);
    LayoutInflater localLayoutInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    if ((paramContext.getResources().getBoolean(2131492901)) && (paramContext.getResources().getBoolean(2131492902))) {}
    for (int i = 2130903116;; i = 2130903080)
    {
      this.mPaneView = ((ViewGroup)localLayoutInflater.inflate(i, null));
      this.mKeyboardViewContainer = ((KeyboardViewContainer)this.mPaneView.findViewById(2131230855));
      this.mRibbonFrame = ((FrameLayout)this.mPaneView.findViewById(2131230854));
      setRibbonVisibility(false);
      this.mKeyboardWrapper = ((LinearLayout)this.mPaneView.findViewById(2131230853));
      setTabResources();
      return;
    }
  }
  
  private void setTabResources()
  {
    this.mTabSingular = ((ImageView)this.mPaneView.findViewById(2131230852));
  }
  
  protected void addCandidatesView()
  {
    if (this.mRibbonFrame.getChildCount() == 0)
    {
      this.mRibbonFrame.addView(wrappedNewRibbon());
      this.mRibbonStateHandler.addVisibilityListener(this);
    }
  }
  
  protected int[] getCorrectPaneSize()
  {
    int[] arrayOfInt1 = getCorrectKeyboardSize();
    int[] arrayOfInt2 = new int[2];
    arrayOfInt2[0] = kbToPaneWidth(arrayOfInt1[0]);
    arrayOfInt2[1] = kbToPaneHeight(arrayOfInt1[1]);
    return arrayOfInt2;
  }
  
  BaseKeyboardView<?> getKeyboardView()
  {
    return this.mKeyboardViewContainer.getKeyboardView();
  }
  
  public int getLeftOfKeyboard()
  {
    return paneToKbX(getLeftSafe(this.mPaneView));
  }
  
  public int getLeftOfPane(View.OnTouchListener paramOnTouchListener)
  {
    return getLeftSafe(this.mPaneView);
  }
  
  public int getPaneHeight()
  {
    return this.mPaneView.getHeight();
  }
  
  public int getPaneWidth()
  {
    return this.mPaneView.getWidth();
  }
  
  protected int getPreferredPaneHeight()
  {
    return kbToPaneHeight(this.mKeyboardViewContainer.getPreferredHeight());
  }
  
  protected abstract Rect getStoredKeyboardBounds();
  
  public Region getTouchableRegion()
  {
    int[] arrayOfInt = getDragTabSize();
    Path localPath = new Path();
    localPath.addRect(this.mPaneView.getLeft(), this.mPaneView.getTop(), this.mPaneView.getRight(), this.mPaneView.getBottom() - arrayOfInt[1], Path.Direction.CW);
    int i = this.mPaneView.getLeft() + this.mPaneView.getWidth() / 2;
    localPath.addRect(i - arrayOfInt[0] / 2, this.mPaneView.getBottom() - arrayOfInt[1], i + arrayOfInt[0] / 2, this.mPaneView.getBottom(), Path.Direction.CW);
    Region localRegion = new Region();
    localRegion.setPath(localPath, new Region(new Region(this.mPaneView.getLeft(), this.mPaneView.getTop(), this.mPaneView.getRight(), this.mPaneView.getBottom())));
    return localRegion;
  }
  
  public View getView()
  {
    return this.mPaneView;
  }
  
  protected boolean isRibbonShown()
  {
    return this.mRibbonFrame.getVisibility() == 0;
  }
  
  protected void notifyAboutBounds()
  {
    Rect localRect = getStoredKeyboardBounds();
    notifyAboutBounds(0, 0, localRect.width(), localRect.height());
  }
  
  protected void notifyAboutBounds(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    Rect localRect = new Rect(paramInt1, paramInt2, paramInt1 + paramInt3, paramInt2 + paramInt4);
    this.mKPMUKeyPressModelUpdater.onBoundsUpdated(localRect);
    this.mIemWrapper.newBoundsOnScreen(localRect);
  }
  
  public boolean onBack()
  {
    return this.mKeyboardViewContainer.getKeyboardView().handleBack();
  }
  
  public void onClose()
  {
    this.mInputView.removeView(this.mPaneView);
    ThemeManager.getInstance(this.mContext).removeListener(this);
    this.mRibbonFrame.removeAllViews();
    this.mRibbonStateHandler.removeVisibilityListener(this);
  }
  
  public void onDragBy(int paramInt1, int paramInt2, PaneDragHandler paramPaneDragHandler)
  {
    clippedOnDrag(movePaneTo(this.mPaneView, paramInt1 + this.mDragStartX, paramInt2 + this.mDragStartY, getPaneWidth(), getPaneHeight()));
  }
  
  public void onThemeChanged()
  {
    setThemedResources();
    this.mPaneView.invalidate();
  }
  
  void setAlpha(float paramFloat)
  {
    this.mPaneView.setAlpha(paramFloat);
  }
  
  protected void setCachedKeyboardDrawing(boolean paramBoolean)
  {
    this.mKeyboardViewContainer.getKeyboardView().setCachedDrawing(paramBoolean);
  }
  
  protected int[] setCorrectPaneSize()
  {
    int[] arrayOfInt = getCorrectPaneSize();
    setPaneSize(arrayOfInt[0], arrayOfInt[1]);
    return arrayOfInt;
  }
  
  protected void setDragTabState(KeyboardChoreographer.TabViewState paramTabViewState)
  {
    this.mTabSingular.setVisibility(8);
    this.mTabSingular.setAlpha(255);
    if ((paramTabViewState == KeyboardChoreographer.TabViewState.PARTIAL) || (paramTabViewState == KeyboardChoreographer.TabViewState.SHOW_MOVE))
    {
      this.mTabSingular.setVisibility(0);
      if (paramTabViewState == KeyboardChoreographer.TabViewState.PARTIAL) {
        this.mTabSingular.setAlpha(128);
      }
    }
  }
  
  public void setKeyboardView(MainKeyboardView paramMainKeyboardView, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper, SingularKeyPressModelUpdater paramSingularKeyPressModelUpdater, boolean paramBoolean)
  {
    this.mKeyboardViewContainer.setKeyboardView(paramMainKeyboardView, paramBoolean, this.mInputView);
    paramMainKeyboardView.setPopupParent(this.mInputView);
    this.mIemWrapper = paramInputEventModelTransformingWrapper;
    this.mKPMUKeyPressModelUpdater = paramSingularKeyPressModelUpdater;
    this.mKeyboardViewContainer.invalidate();
    this.mMainKeyboardView = paramMainKeyboardView;
    setRibbonSplit(((MainKeyboard)paramMainKeyboardView.getKeyboard()).getSplitStart(), ((MainKeyboard)paramMainKeyboardView.getKeyboard()).getSplitEnd());
    setCorrectPaneSize();
    movePostResize();
    notifyAboutBounds();
  }
  
  protected void setRibbonSplit(float paramFloat1, float paramFloat2) {}
  
  protected void setRibbonVisibility(boolean paramBoolean)
  {
    FrameLayout localFrameLayout = this.mRibbonFrame;
    if (paramBoolean) {}
    for (int i = 0;; i = 8)
    {
      localFrameLayout.setVisibility(i);
      return;
    }
  }
  
  protected void setThemedResources()
  {
    ThemeProperties localThemeProperties = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties();
    Drawable localDrawable = localThemeProperties.getFloatingKeyboardBackground();
    this.mKeyboardWrapper.setBackgroundDrawable(localDrawable);
    this.mRibbonFrame.setBackgroundDrawable(localThemeProperties.getCandidateBackground());
    this.mTabSingular.setBackgroundDrawable(localThemeProperties.getBottomMoveTab());
  }
  
  protected void setupBorder()
  {
    this.mKeyboardWrapper.setPadding(getBorderWidth(), getBorderWidth(), getBorderWidth(), getBorderWidth());
  }
  
  protected void setupDragTabListeners()
  {
    this.mTabSingular.setOnTouchListener(new PaneDragHandler(this));
  }
  
  protected SingleRibbonHolder wrappedNewRibbon()
  {
    SingleRibbonHolder localSingleRibbonHolder = new SingleRibbonHolder(this.mContext);
    localSingleRibbonHolder.addView(this.mRibbonStateHandler.getNewRibbonView());
    return localSingleRibbonHolder;
  }
  
  protected final class SingleRibbonHolder
    extends RibbonContainer
  {
    public SingleRibbonHolder(Context paramContext)
    {
      super();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.SingularPane
 * JD-Core Version:    0.7.0.1
 */