package com.touchtype.keyboard.candidates;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.Pair;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.CompletionInfo;
import com.touchtype.keyboard.Learner;
import com.touchtype.keyboard.candidates.view.CompletionViewContainer;
import com.touchtype.keyboard.candidates.view.OneCandidateViewCompatibility;
import com.touchtype.keyboard.candidates.view.SingleContentView;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.inputeventmodel.PredictionsAvailability;
import com.touchtype.keyboard.inputeventmodel.listeners.PredictionsAvailabilityListener;
import com.touchtype.keyboard.key.contents.EmptyContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.view.MultiViewSwitcher;
import com.touchtype.report.TouchTypeStats;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.KeyHeightUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public final class RibbonStateHandler
  implements PredictionsAvailabilityListener, OnThemeChangedListener
{
  private final CandidateStateHandler mCandidateStateHandler;
  private boolean mCompletionsAvailable = false;
  private final Context mContext;
  private RibbonState mCurrentState;
  private boolean mFluencyConnected = false;
  private final FluencyServiceProxy mFluencyService;
  private final InputEventModel mInputEventModel;
  private final Learner mLearner;
  private PredictionsAvailability mPredictionsAvailability = PredictionsAvailability.DISABLED;
  private MultiViewSwitcher mSecondaryViewSwitcher;
  private MultiViewSwitcher mViewSwitcher;
  private final Set<VisibilityListener> mVisibilityListeners = new HashSet(1);
  private boolean mWindowShown = false;
  
  public RibbonStateHandler(Context paramContext, Learner paramLearner, InputEventModel paramInputEventModel, CandidateStateHandler paramCandidateStateHandler, FluencyServiceProxy paramFluencyServiceProxy, TouchTypeStats paramTouchTypeStats)
  {
    this.mContext = paramContext;
    this.mFluencyService = paramFluencyServiceProxy;
    this.mCandidateStateHandler = paramCandidateStateHandler;
    this.mInputEventModel = paramInputEventModel;
    this.mLearner = paramLearner;
    getNewRibbonView();
    paramInputEventModel.addUpdatedCandidatesListener(this.mCandidateStateHandler, this.mCandidateStateHandler.getNumberOfCandidates());
    ThemeManager.getInstance(paramContext).addListener(this);
  }
  
  private int calculatedHeight()
  {
    return (int)(KeyHeightUtil.getCurrentKeyHeight(this.mContext) * KeyHeightUtil.getRibbonRelativeHeight(this.mContext));
  }
  
  private MultiViewSwitcher createNewRibbonView(View paramView)
  {
    int i = calculatedHeight();
    MultiViewSwitcher local3 = new MultiViewSwitcher(this.mContext, 2130968576, 2130968577, new ViewGroup.LayoutParams(-1, i))
    {
      public void onMeasure(int paramAnonymousInt1, int paramAnonymousInt2)
      {
        int i = View.MeasureSpec.makeMeasureSpec(RibbonStateHandler.this.calculatedHeight(), -2147483648);
        if (this.mRequestedWidth != -3) {
          paramAnonymousInt1 = View.MeasureSpec.makeMeasureSpec(this.mRequestedWidth, 1073741824);
        }
        super.onMeasure(paramAnonymousInt1, i);
      }
    };
    local3.setMeasureAllChildren(false);
    local3.registerView(RibbonState.LOADING.ordinal(), getRibbonView(this.mContext.getResources().getString(2131296858)));
    local3.registerView(RibbonState.NO_SD_CARD.ordinal(), getRibbonView(this.mContext.getResources().getString(2131296859)));
    local3.registerView(RibbonState.COMPLETIONS.ordinal(), 2130903068);
    local3.registerView(RibbonState.CANDIDATES.ordinal(), paramView);
    local3.registerView(RibbonState.CANDIDATES_HIDDEN.ordinal(), createRibbonViewFromKeyContent(new EmptyContent()));
    if (this.mCurrentState != null) {
      showView(this.mCurrentState);
    }
    return local3;
  }
  
  private View createRibbonView(KeyContent paramKeyContent, ViewGroup.LayoutParams paramLayoutParams, Drawable paramDrawable)
  {
    if ((Build.VERSION.SDK_INT > 10) || (Build.VERSION.SDK_INT < 9))
    {
      SingleContentView localSingleContentView = new SingleContentView(this.mContext, paramLayoutParams, paramDrawable);
      ((SingleContentView)localSingleContentView).setContent(paramKeyContent, KeyStyle.StyleId.TOPCANDIDATE);
      return localSingleContentView;
    }
    OneCandidateViewCompatibility localOneCandidateViewCompatibility = new OneCandidateViewCompatibility(this.mContext, paramLayoutParams, paramDrawable);
    ((OneCandidateViewCompatibility)localOneCandidateViewCompatibility).setContent(paramKeyContent, KeyStyle.StyleId.TOPCANDIDATE);
    return localOneCandidateViewCompatibility;
  }
  
  private View createRibbonViewFromKeyContent(KeyContent paramKeyContent)
  {
    return createRibbonView(paramKeyContent, new ViewGroup.LayoutParams(-1, -1), ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties().getCandidateBackground());
  }
  
  private View getRibbonView(String paramString)
  {
    ViewGroup.LayoutParams localLayoutParams = new ViewGroup.LayoutParams(-1, this.mContext.getResources().getDimensionPixelOffset(2131361836));
    ColorDrawable localColorDrawable = new ColorDrawable(0);
    return createRibbonView(new TextContent(paramString, paramString, Locale.getDefault(), TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.45F), localLayoutParams, localColorDrawable);
  }
  
  private void setRequestedWidthAt(MultiViewSwitcher paramMultiViewSwitcher, int paramInt1, int paramInt2)
  {
    paramMultiViewSwitcher.setRequestedWidthAt(paramInt1, paramInt2);
  }
  
  public void addVisibilityListener(VisibilityListener paramVisibilityListener)
  {
    if (paramVisibilityListener != null) {
      this.mVisibilityListeners.add(paramVisibilityListener);
    }
  }
  
  public int getCalculatedHeight()
  {
    if (this.mCandidateStateHandler != null) {
      return calculatedHeight();
    }
    return 0;
  }
  
  public Pair<View, View> getDualRibbonViews()
  {
    return new Pair(this.mViewSwitcher, this.mSecondaryViewSwitcher);
  }
  
  public Pair<View, View> getNewDualRibbonViews()
  {
    if (this.mViewSwitcher != null) {
      this.mViewSwitcher.removeAllViews();
    }
    if (this.mSecondaryViewSwitcher != null) {
      this.mSecondaryViewSwitcher.removeAllViews();
    }
    this.mInputEventModel.addPredictionsEnabledListener(this);
    Pair localPair = this.mCandidateStateHandler.getNewDualCandidateViews(this.mLearner, this.mInputEventModel);
    this.mViewSwitcher = createNewRibbonView((View)localPair.first);
    this.mSecondaryViewSwitcher = createNewRibbonView((View)localPair.second);
    return new Pair(this.mViewSwitcher, this.mSecondaryViewSwitcher);
  }
  
  public View getNewRibbonView()
  {
    if (this.mViewSwitcher != null) {
      this.mViewSwitcher.removeAllViews();
    }
    this.mInputEventModel.addPredictionsEnabledListener(this);
    this.mSecondaryViewSwitcher = null;
    this.mViewSwitcher = createNewRibbonView(this.mCandidateStateHandler.getNewCandidatesView(this.mLearner, this.mInputEventModel));
    return this.mViewSwitcher;
  }
  
  public RibbonState getRibbonState()
  {
    return this.mCurrentState;
  }
  
  public View getRibbonView()
  {
    return this.mViewSwitcher;
  }
  
  public void onDisplayCompletions(CompletionInfo[] paramArrayOfCompletionInfo, TouchTypeSoftKeyboard paramTouchTypeSoftKeyboard, boolean paramBoolean)
  {
    if ((paramBoolean) && (paramArrayOfCompletionInfo != null) && (paramArrayOfCompletionInfo.length != 0))
    {
      ((CompletionViewContainer)this.mViewSwitcher.getChildAt(RibbonState.COMPLETIONS.ordinal())).setCompletions(paramArrayOfCompletionInfo, paramTouchTypeSoftKeyboard);
      if (this.mSecondaryViewSwitcher != null) {
        ((CompletionViewContainer)this.mSecondaryViewSwitcher.getChildAt(RibbonState.COMPLETIONS.ordinal())).setCompletions(paramArrayOfCompletionInfo, paramTouchTypeSoftKeyboard);
      }
      this.mCompletionsAvailable = true;
      showView(RibbonState.COMPLETIONS);
    }
    for (;;)
    {
      updateView();
      return;
      this.mCompletionsAvailable = false;
    }
  }
  
  public void onPredictionsAvailabilityUpdate(PredictionsAvailability paramPredictionsAvailability)
  {
    if (paramPredictionsAvailability != this.mPredictionsAvailability) {
      setPredictionsAvailability(paramPredictionsAvailability);
    }
  }
  
  public void onStartInput()
  {
    this.mWindowShown = true;
    if (this.mPredictionsAvailability == PredictionsAvailability.UNAVAILABLE_NO_SD_CARD)
    {
      showView(RibbonState.NO_SD_CARD);
      return;
    }
    if (this.mPredictionsAvailability == PredictionsAvailability.DISABLED)
    {
      if ((ProductConfiguration.isWatchBuild(this.mContext)) && (this.mInputEventModel.isLayoutCyclingEnabled()))
      {
        showView(RibbonState.CANDIDATES_HIDDEN);
        return;
      }
      showView(RibbonState.HIDDEN);
      return;
    }
    if (this.mFluencyService.isReady())
    {
      this.mFluencyConnected = true;
      showView(RibbonState.CANDIDATES);
      return;
    }
    showView(RibbonState.LOADING);
    this.mFluencyService.runWhenConnected(new Runnable()
    {
      public void run()
      {
        RibbonStateHandler.access$002(RibbonStateHandler.this, true);
        RibbonStateHandler.this.showView(RibbonStateHandler.RibbonState.CANDIDATES);
      }
    });
  }
  
  public void onThemeChanged()
  {
    Drawable localDrawable = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties().getCandidateBackground();
    if (this.mViewSwitcher != null) {
      this.mViewSwitcher.setBackgroundDrawable(localDrawable);
    }
    if (this.mSecondaryViewSwitcher != null) {
      this.mSecondaryViewSwitcher.setBackgroundDrawable(localDrawable);
    }
  }
  
  public void onWindowHidden()
  {
    this.mWindowShown = false;
    showView(RibbonState.HIDDEN);
  }
  
  public void removeVisibilityListener(VisibilityListener paramVisibilityListener)
  {
    this.mVisibilityListeners.remove(paramVisibilityListener);
  }
  
  public void requestWidthLeftAt(int paramInt1, int paramInt2)
  {
    setRequestedWidthAt(this.mViewSwitcher, paramInt1, paramInt2);
  }
  
  public void requestWidthRightAt(int paramInt1, int paramInt2)
  {
    setRequestedWidthAt(this.mSecondaryViewSwitcher, paramInt1, paramInt2);
  }
  
  public void setPredictionsAvailability(PredictionsAvailability paramPredictionsAvailability)
  {
    this.mPredictionsAvailability = paramPredictionsAvailability;
    updateView();
  }
  
  public void showView(RibbonState paramRibbonState)
  {
    if (paramRibbonState == RibbonState.HIDDEN) {}
    for (boolean bool = false;; bool = true)
    {
      Iterator localIterator = this.mVisibilityListeners.iterator();
      while (localIterator.hasNext()) {
        ((VisibilityListener)localIterator.next()).onVisibilityChanged(bool);
      }
      this.mViewSwitcher.switchView(paramRibbonState.ordinal(), 0, 0);
      if (this.mSecondaryViewSwitcher != null) {
        this.mSecondaryViewSwitcher.switchView(paramRibbonState.ordinal(), 0, 0);
      }
    }
    this.mCurrentState = paramRibbonState;
  }
  
  public void updateView()
  {
    if (this.mCurrentState == null) {}
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              return;
              switch (4.$SwitchMap$com$touchtype$keyboard$candidates$RibbonStateHandler$RibbonState[this.mCurrentState.ordinal()])
              {
              default: 
                return;
              case 1: 
                if (this.mPredictionsAvailability == PredictionsAvailability.UNAVAILABLE_NO_SD_CARD)
                {
                  showView(RibbonState.NO_SD_CARD);
                  return;
                }
                break;
              }
            } while (!this.mFluencyConnected);
            showView(RibbonState.CANDIDATES);
            return;
          } while (this.mPredictionsAvailability != PredictionsAvailability.UNAVAILABLE_NO_SD_CARD);
          showView(RibbonState.NO_SD_CARD);
          return;
          if (this.mPredictionsAvailability == PredictionsAvailability.ENABLED)
          {
            showView(RibbonState.LOADING);
            this.mFluencyService.runWhenConnected(new Runnable()
            {
              public void run()
              {
                RibbonStateHandler.access$002(RibbonStateHandler.this, true);
                RibbonStateHandler.this.showView(RibbonStateHandler.RibbonState.CANDIDATES);
              }
            });
            return;
          }
        } while ((!this.mCompletionsAvailable) || (!this.mWindowShown));
        showView(RibbonState.COMPLETIONS);
        return;
      } while ((!this.mCompletionsAvailable) || (!this.mWindowShown));
      showView(RibbonState.COMPLETIONS);
      return;
    } while (this.mCompletionsAvailable);
    if ((this.mPredictionsAvailability == PredictionsAvailability.ENABLED) && (this.mFluencyConnected))
    {
      showView(RibbonState.CANDIDATES);
      return;
    }
    showView(RibbonState.HIDDEN);
  }
  
  public static enum RibbonState
  {
    static
    {
      COMPLETIONS = new RibbonState("COMPLETIONS", 2);
      CANDIDATES = new RibbonState("CANDIDATES", 3);
      CANDIDATES_HIDDEN = new RibbonState("CANDIDATES_HIDDEN", 4);
      HIDDEN = new RibbonState("HIDDEN", 5);
      RibbonState[] arrayOfRibbonState = new RibbonState[6];
      arrayOfRibbonState[0] = LOADING;
      arrayOfRibbonState[1] = NO_SD_CARD;
      arrayOfRibbonState[2] = COMPLETIONS;
      arrayOfRibbonState[3] = CANDIDATES;
      arrayOfRibbonState[4] = CANDIDATES_HIDDEN;
      arrayOfRibbonState[5] = HIDDEN;
      $VALUES = arrayOfRibbonState;
    }
    
    private RibbonState() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.RibbonStateHandler
 * JD-Core Version:    0.7.0.1
 */