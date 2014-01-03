package com.touchtype.keyboard.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Region;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService.Insets;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout.LayoutParams;
import com.touchtype.keyboard.DualKeyPressModelUpdater;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.SingularKeyPressModelUpdater;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.keyboard.inputeventmodel.InputEventModelTransformingWrapper;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.LogUtil;
import junit.framework.Assert;

public class KeyboardChoreographer
  implements OnThemeChangedListener
{
  private static final String TAG = KeyboardChoreographer.class.getSimpleName();
  private static final Drawable TRANSPARENT_BACKGROUND = new ColorDrawable(0);
  private final DockedCompactPane mCompactDockedPane;
  private final Pane mCompactFloatingPane;
  private Context mContext;
  private KeyboardState mCurrentState;
  private Pane mCurrentlyDisplayedPane;
  private EditorInfo mEditorInfo;
  private final Pane mFullDockedPane;
  private final Pane mFullFloatingPane;
  private ViewGroup mInputView;
  private final KeyboardSwitcher mKeyboardSwitcher;
  private long mLastDockStateChange = -1L;
  private MainKeyboardView mMainKeyboardView;
  private TouchTypePreferences mPreferences;
  private final Pane mSplitDockedPane;
  private final FloatingSplitPane mSplitPane;
  private final TouchTypeSoftKeyboard mTTSK;
  private int mWindowHeightPx;
  private int mWindowWidthPx;
  
  public KeyboardChoreographer(Context paramContext, KeyboardSwitcher paramKeyboardSwitcher, TouchTypePreferences paramTouchTypePreferences, RibbonStateHandler paramRibbonStateHandler, TouchTypeSoftKeyboard paramTouchTypeSoftKeyboard)
  {
    this.mContext = paramContext;
    this.mPreferences = paramTouchTypePreferences;
    this.mKeyboardSwitcher = paramKeyboardSwitcher;
    paramKeyboardSwitcher.setKeyboardContainer(this);
    this.mTTSK = paramTouchTypeSoftKeyboard;
    this.mInputView = ((ViewGroup)((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903070, null));
    updateWindowSize();
    this.mSplitPane = new FloatingSplitPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    this.mCompactFloatingPane = new FloatingCompactPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    this.mCompactDockedPane = new DockedCompactPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    this.mFullFloatingPane = new FloatingFullPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    this.mFullDockedPane = new DockedFullPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    this.mSplitDockedPane = new DockedSplitPane(paramContext, this, this.mInputView, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
    ThemeManager.getInstance(this.mContext).addListener(this);
  }
  
  private void displayPane(Pane paramPane)
  {
    if ((this.mCurrentlyDisplayedPane != null) && (!this.mCurrentlyDisplayedPane.isClosed())) {
      this.mCurrentlyDisplayedPane.closing();
    }
    this.mInputView.removeAllViews();
    this.mInputView.addView(paramPane.getView());
    this.mCurrentlyDisplayedPane = paramPane;
  }
  
  public static String getCurrentStateName(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    return KeyboardState.translateStyle(localTouchTypePreferences.getKeyboardLayoutStyle(paramContext), localTouchTypePreferences.getKeyboardDockedState(paramContext));
  }
  
  private boolean isHtcLockScreen()
  {
    return (this.mEditorInfo != null) && (this.mEditorInfo.packageName.equals("com.htc.lockscreen"));
  }
  
  private boolean isPaneAlreadyDisplayed(Pane paramPane)
  {
    View localView = paramPane.getView();
    for (int i = 0; i < this.mInputView.getChildCount(); i++) {
      if (this.mInputView.getChildAt(i) == localView) {
        return true;
      }
    }
    return false;
  }
  
  private KeyboardState lastState()
  {
    int i = this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    boolean bool = this.mPreferences.getKeyboardDockedState(this.mContext);
    switch (i)
    {
    default: 
      if (bool) {
        return KeyboardState.FULL_DOCKED;
      }
      break;
    case 2: 
      if (bool) {
        return KeyboardState.SPLIT_DOCKED;
      }
      return KeyboardState.SPLIT_FLOATING;
    case 3: 
      if (bool) {
        return KeyboardState.COMPACT_DOCKED;
      }
      return KeyboardState.COMPACT_FLOATING;
    }
    return KeyboardState.FULL_FLOATING;
  }
  
  private void reportRapidSwitchIfNecessary()
  {
    if ((this.mLastDockStateChange != -1L) && (SystemClock.uptimeMillis() - this.mLastDockStateChange < 6000L)) {
      this.mPreferences.getTouchTypeStats().incrementStatistic("stats_keyboard_mode_change_immediate");
    }
  }
  
  private void setupForState(KeyboardState paramKeyboardState)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$view$KeyboardState[paramKeyboardState.ordinal()])
    {
    }
    for (;;)
    {
      this.mKeyboardSwitcher.setKeyboardContainer(this);
      this.mCurrentState = paramKeyboardState;
      this.mTTSK.updateFullscreenMode();
      updateWindowSize();
      Pane localPane = getCurrentPane();
      if (!isPaneAlreadyDisplayed(localPane)) {
        displayPane(localPane);
      }
      localPane.init();
      this.mTTSK.refreshCandidates(true);
      return;
      this.mPreferences.setKeyboardLayoutStyle(this.mContext, 1);
      continue;
      this.mPreferences.setKeyboardLayoutStyle(this.mContext, 2);
      continue;
      this.mPreferences.setKeyboardLayoutStyle(this.mContext, 3);
    }
  }
  
  private void updateWindowSize()
  {
    DisplayMetrics localDisplayMetrics = this.mContext.getResources().getDisplayMetrics();
    this.mWindowHeightPx = (localDisplayMetrics.heightPixels - DeviceUtils.getStatusBarHeightPx(this.mContext));
    this.mWindowWidthPx = localDisplayMetrics.widthPixels;
  }
  
  public void closeKeyboardView()
  {
    BaseKeyboardView localBaseKeyboardView = getKeyboardView();
    if (localBaseKeyboardView != null) {
      localBaseKeyboardView.closing();
    }
  }
  
  public void closing()
  {
    getCurrentPane().closing();
  }
  
  @TargetApi(11)
  public void computeInsets(InputMethodService.Insets paramInsets)
  {
    paramInsets.contentTopInsets = getContentInset();
    paramInsets.visibleTopInsets = getVisibleInset();
    if (Build.VERSION.SDK_INT >= 11)
    {
      paramInsets.touchableInsets = 3;
      paramInsets.touchableRegion.set(getTouchableRegion());
    }
  }
  
  public void dismissPopupMenu(boolean paramBoolean)
  {
    getKeyboardView().dismissPopupWindow(paramBoolean);
  }
  
  int getContentInset()
  {
    return getCurrentPane().getContentInset();
  }
  
  public Context getContext()
  {
    return this.mContext;
  }
  
  Pane getCurrentPane()
  {
    if (this.mCurrentState == null)
    {
      LogUtil.w(TAG, "currentPane() was called before mCurrentState was set up");
      this.mCurrentState = lastState();
    }
    switch (1.$SwitchMap$com$touchtype$keyboard$view$KeyboardState[this.mCurrentState.ordinal()])
    {
    default: 
      Assert.assertTrue("Pane requested for invalid state: " + this.mCurrentState, false);
      return null;
    case 1: 
      return this.mFullFloatingPane;
    case 2: 
      return this.mFullDockedPane;
    case 3: 
      return this.mSplitPane;
    case 4: 
      return this.mSplitDockedPane;
    case 5: 
      return this.mCompactFloatingPane;
    }
    return this.mCompactDockedPane;
  }
  
  public View getInputView()
  {
    return this.mInputView;
  }
  
  public BaseKeyboardView<?> getKeyboardView()
  {
    return getCurrentPane().getKeyboardView();
  }
  
  public View getPopupParent()
  {
    return this.mInputView;
  }
  
  Region getTouchableRegion()
  {
    return getCurrentPane().getTouchableRegion();
  }
  
  int getVisibleInset()
  {
    return getCurrentPane().getVisibleInset();
  }
  
  public int getWindowHeight()
  {
    return this.mWindowHeightPx;
  }
  
  int getWindowTopOffset(int paramInt)
  {
    if ((this.mTTSK.isFullscreenMode()) || (isHtcLockScreen())) {
      return this.mWindowHeightPx - paramInt;
    }
    return 0;
  }
  
  public int getWindowWidth()
  {
    return this.mWindowWidthPx;
  }
  
  public boolean handleBack()
  {
    return getCurrentPane().onBack();
  }
  
  public boolean hasKeyboard()
  {
    return (getKeyboardView() != null) && (getKeyboardView().getKeyboard() != null);
  }
  
  public boolean isDocked()
  {
    return (this.mCurrentState == KeyboardState.FULL_DOCKED) || (this.mCurrentState == KeyboardState.SPLIT_DOCKED) || (this.mCurrentState == KeyboardState.COMPACT_DOCKED);
  }
  
  public boolean isHandlingCandidateBarItself()
  {
    return true;
  }
  
  public boolean isShown()
  {
    if (getKeyboardView() != null) {
      return getKeyboardView().isShown();
    }
    return false;
  }
  
  public void maximiseBackgroundView()
  {
    this.mInputView.setBackgroundDrawable(TRANSPARENT_BACKGROUND);
    FrameLayout.LayoutParams localLayoutParams = (FrameLayout.LayoutParams)this.mInputView.getLayoutParams();
    if ((this.mTTSK.isFullscreenMode()) || (isHtcLockScreen())) {}
    for (localLayoutParams.height = getCurrentPane().getPreferredPaneHeight();; localLayoutParams.height = this.mWindowHeightPx)
    {
      localLayoutParams.width = this.mWindowWidthPx;
      this.mInputView.setLayoutParams(localLayoutParams);
      return;
    }
  }
  
  public void newEditorInfo(EditorInfo paramEditorInfo)
  {
    this.mEditorInfo = paramEditorInfo;
    if (DeviceUtils.isScreenLocked(this.mContext))
    {
      getCurrentPane().closing();
      this.mInputView.removeAllViews();
      if (!this.mPreferences.getLockScreenDockOverride(this.mContext)) {
        this.mPreferences.setLockScreenDockOverride(this.mContext, true);
      }
    }
    while (!this.mPreferences.getLockScreenDockOverride(this.mContext)) {
      return;
    }
    this.mPreferences.setLockScreenDockOverride(this.mContext, false);
    this.mPreferences.restorePreLockScreenDockState(this.mContext);
  }
  
  public void onResize(int paramInt)
  {
    getCurrentPane().onResize(paramInt);
  }
  
  public void onThemeChanged()
  {
    getCurrentPane().onThemeChanged();
  }
  
  public void requestState(int paramInt)
  {
    Resources localResources = this.mContext.getResources();
    int i = this.mPreferences.getKeyboardLayoutStyle(this.mContext);
    boolean bool = this.mPreferences.getKeyboardDockedState(this.mContext);
    if (paramInt == localResources.getInteger(2131558454))
    {
      reportRapidSwitchIfNecessary();
      if (bool) {
        setupForState(KeyboardState.COMPACT_DOCKED);
      }
    }
    do
    {
      return;
      setupForState(KeyboardState.COMPACT_FLOATING);
      return;
      if (paramInt == localResources.getInteger(2131558453))
      {
        reportRapidSwitchIfNecessary();
        if (bool)
        {
          setupForState(KeyboardState.SPLIT_DOCKED);
          return;
        }
        setupForState(KeyboardState.SPLIT_FLOATING);
        return;
      }
      if (paramInt == localResources.getInteger(2131558452))
      {
        reportRapidSwitchIfNecessary();
        if (bool)
        {
          setupForState(KeyboardState.FULL_DOCKED);
          return;
        }
        setupForState(KeyboardState.FULL_FLOATING);
        return;
      }
      if (paramInt == localResources.getInteger(2131558457))
      {
        if (!bool) {
          this.mLastDockStateChange = SystemClock.uptimeMillis();
        }
        switch (i)
        {
        default: 
          setupForState(KeyboardState.FULL_DOCKED);
          return;
        case 3: 
          setupForState(KeyboardState.COMPACT_DOCKED);
          return;
        }
        setupForState(KeyboardState.SPLIT_DOCKED);
        return;
      }
      if (paramInt == localResources.getInteger(2131558456))
      {
        if (bool) {
          this.mLastDockStateChange = SystemClock.uptimeMillis();
        }
        if (i == 1)
        {
          setupForState(KeyboardState.FULL_FLOATING);
          return;
        }
        if (i == 2)
        {
          setupForState(KeyboardState.SPLIT_FLOATING);
          return;
        }
        setupForState(KeyboardState.COMPACT_FLOATING);
        return;
      }
    } while (paramInt != localResources.getInteger(2131558455));
    this.mMainKeyboardView.showResizePopup();
  }
  
  public void setKeyboardView(MainKeyboardView paramMainKeyboardView, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper, SingularKeyPressModelUpdater paramSingularKeyPressModelUpdater, boolean paramBoolean)
  {
    getCurrentPane().setKeyboardView(paramMainKeyboardView, paramInputEventModelTransformingWrapper, paramSingularKeyPressModelUpdater, paramBoolean);
    this.mMainKeyboardView = paramMainKeyboardView;
  }
  
  public void setKeyboardView(MainKeyboardView paramMainKeyboardView1, MainKeyboardView paramMainKeyboardView2, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper1, InputEventModelTransformingWrapper paramInputEventModelTransformingWrapper2, DualKeyPressModelUpdater paramDualKeyPressModelUpdater, boolean paramBoolean)
  {
    this.mSplitPane.setKeyboardView(paramMainKeyboardView1, paramMainKeyboardView2, paramInputEventModelTransformingWrapper1, paramInputEventModelTransformingWrapper2, paramDualKeyPressModelUpdater, paramBoolean);
    this.mMainKeyboardView = paramMainKeyboardView1;
  }
  
  public boolean showsItsOwnCandidateBar()
  {
    return true;
  }
  
  public void startInput()
  {
    KeyboardState localKeyboardState = lastState();
    boolean bool = DeviceUtils.isDeviceInLandscape(this.mContext);
    this.mPreferences.getTouchTypeStats().incrementStatistic(localKeyboardState.getOpenedCountStatsKey(bool));
    setupForState(lastState());
  }
  
  public static enum TabViewState
  {
    static
    {
      SHOW_FLIP = new TabViewState("SHOW_FLIP", 3);
      TabViewState[] arrayOfTabViewState = new TabViewState[4];
      arrayOfTabViewState[0] = HIDDEN;
      arrayOfTabViewState[1] = PARTIAL;
      arrayOfTabViewState[2] = SHOW_MOVE;
      arrayOfTabViewState[3] = SHOW_FLIP;
      $VALUES = arrayOfTabViewState;
    }
    
    private TabViewState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.KeyboardChoreographer
 * JD-Core Version:    0.7.0.1
 */