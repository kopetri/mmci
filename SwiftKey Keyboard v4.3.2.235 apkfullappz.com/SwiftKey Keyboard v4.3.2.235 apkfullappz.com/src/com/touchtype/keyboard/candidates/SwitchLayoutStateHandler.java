package com.touchtype.keyboard.candidates;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import com.touchtype.keyboard.KeyboardBehaviour;
import com.touchtype.keyboard.KeyboardChangeListener;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.KeyboardSwitcher.DynamicSwitch;
import com.touchtype.keyboard.LayoutType;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.view.MultiViewSwitcher;
import com.touchtype.keyboard.view.RibbonButton;

public class SwitchLayoutStateHandler
  implements KeyboardChangeListener, OnThemeChangedListener
{
  private static final String TAG = SwitchLayoutStateHandler.class.getSimpleName();
  private final Context mContext;
  private LayoutType mCurrentLayoutType = null;
  private final KeyboardSwitcher mKeyboardSwitcher;
  private View mLeftLettersLsView;
  private View mLeftNumbersLsView;
  private View mLeftSmileysLsView;
  private View mLeftSymbolsLsView;
  private MultiViewSwitcher mLeftViewSwitcher;
  private final View.OnClickListener mLettersOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      SwitchLayoutStateHandler.this.mKeyboardSwitcher.selectKeyboard(KeyboardSwitcher.DynamicSwitch.ABC.getValue(), false);
    }
  };
  private final View.OnClickListener mNumbersOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      SwitchLayoutStateHandler.this.mKeyboardSwitcher.selectKeyboard(2131034654, false);
    }
  };
  private View mRightLettersLsView;
  private View mRightNumbersLsView;
  private View mRightSmileysLsView;
  private View mRightSymbolsLsView;
  private MultiViewSwitcher mRightViewSwitcher;
  private final View.OnClickListener mSmileysOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      SwitchLayoutStateHandler.this.mKeyboardSwitcher.selectKeyboard(2131034567, false);
    }
  };
  private final View.OnClickListener mSymbolsOnClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      SwitchLayoutStateHandler.this.mKeyboardSwitcher.selectKeyboard(2131034655, false);
    }
  };
  
  public SwitchLayoutStateHandler(Context paramContext, KeyboardSwitcher paramKeyboardSwitcher)
  {
    this.mContext = paramContext;
    this.mKeyboardSwitcher = paramKeyboardSwitcher;
  }
  
  private MultiViewSwitcher createLsView(MultiViewSwitcher paramMultiViewSwitcher, View paramView1, View paramView2, View paramView3, View paramView4)
  {
    if (paramMultiViewSwitcher != null) {
      paramMultiViewSwitcher.removeAllViews();
    }
    MultiViewSwitcher localMultiViewSwitcher = new MultiViewSwitcher(this.mContext, getLsRibbonViewParams());
    localMultiViewSwitcher.setMeasureAllChildren(false);
    localMultiViewSwitcher.registerView(LayoutType.STANDARD.getValue(), paramView1);
    localMultiViewSwitcher.registerView(LayoutType.SYMBOLS.getValue(), paramView2);
    localMultiViewSwitcher.registerView(LayoutType.SYMBOLS_ALT.getValue(), paramView3);
    localMultiViewSwitcher.registerView(LayoutType.SMILEYS.getValue(), paramView4);
    if (this.mCurrentLayoutType != null) {
      localMultiViewSwitcher.switchView(this.mCurrentLayoutType.getValue(), 0, 0);
    }
    localMultiViewSwitcher.setBackgroundDrawable(ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties().getCandidateBackground());
    return localMultiViewSwitcher;
  }
  
  private ViewGroup.LayoutParams getLsRibbonViewParams()
  {
    return new ViewGroup.LayoutParams(Math.round(TypedValue.applyDimension(5, 5.0F, this.mContext.getResources().getDisplayMetrics())), -1);
  }
  
  private void showLsView(LayoutType paramLayoutType)
  {
    if (this.mLeftViewSwitcher == null) {
      this.mLeftViewSwitcher = getNewLeftLsView();
    }
    if (this.mRightViewSwitcher == null) {
      this.mRightViewSwitcher = getNewRightLsView();
    }
    this.mLeftViewSwitcher.switchView(paramLayoutType.getValue(), 0, 0);
    this.mRightViewSwitcher.switchView(paramLayoutType.getValue(), 0, 0);
  }
  
  public MultiViewSwitcher getNewLeftLsView()
  {
    this.mLeftLettersLsView = new RibbonButton(this.mContext, this.mSymbolsOnClickListener, "{&=");
    this.mLeftNumbersLsView = new RibbonButton(this.mContext, this.mLettersOnClickListener, "abc");
    this.mLeftSymbolsLsView = new RibbonButton(this.mContext, this.mLettersOnClickListener, "abc");
    this.mLeftSmileysLsView = new RibbonButton(this.mContext, this.mLettersOnClickListener, "abc");
    this.mLeftViewSwitcher = createLsView(this.mLeftViewSwitcher, this.mLeftLettersLsView, this.mLeftNumbersLsView, this.mLeftSymbolsLsView, this.mLeftSmileysLsView);
    return this.mLeftViewSwitcher;
  }
  
  public MultiViewSwitcher getNewRightLsView()
  {
    this.mRightLettersLsView = new RibbonButton(this.mContext, this.mNumbersOnClickListener, "123");
    this.mRightNumbersLsView = new RibbonButton(this.mContext, this.mSymbolsOnClickListener, "{&=");
    this.mRightSymbolsLsView = new RibbonButton(this.mContext, this.mSmileysOnClickListener, ":-)");
    this.mRightSmileysLsView = new RibbonButton(this.mContext, this.mSymbolsOnClickListener, "{&=");
    this.mRightViewSwitcher = createLsView(this.mRightViewSwitcher, this.mRightLettersLsView, this.mRightNumbersLsView, this.mRightSymbolsLsView, this.mRightSmileysLsView);
    return this.mRightViewSwitcher;
  }
  
  public void onKeyboardChanged(KeyboardBehaviour paramKeyboardBehaviour)
  {
    LayoutType localLayoutType = paramKeyboardBehaviour.getLayoutType();
    if (this.mCurrentLayoutType != localLayoutType)
    {
      this.mCurrentLayoutType = localLayoutType;
      showLsView(this.mCurrentLayoutType);
    }
  }
  
  public void onThemeChanged()
  {
    Drawable localDrawable = ThemeManager.getInstance(this.mContext).getThemeHandler().getProperties().getCandidateBackground();
    if (this.mLeftViewSwitcher != null) {
      this.mLeftViewSwitcher.setBackgroundDrawable(localDrawable);
    }
    if (this.mRightViewSwitcher != null) {
      this.mRightViewSwitcher.setBackgroundDrawable(localDrawable);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.SwitchLayoutStateHandler
 * JD-Core Version:    0.7.0.1
 */