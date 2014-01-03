package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import com.touchtype.keyboard.BaseKeyboard;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.view.fx.EffectsSurfaceView;
import com.touchtype.preferences.TouchTypePreferences;

public class KeyboardViewContainer
  extends FrameLayout
  implements SharedPreferences.OnSharedPreferenceChangeListener, OnThemeChangedListener
{
  private BaseKeyboardView<?> mKeyboardView;
  private final TouchTypePreferences mPreferences;
  private EffectsSurfaceView mSurfaceView;
  private final ThemeManager mThemeManager;
  
  public KeyboardViewContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mPreferences = TouchTypePreferences.getInstance(paramContext.getApplicationContext());
    this.mThemeManager = ThemeManager.getInstance(paramContext.getApplicationContext());
    this.mPreferences.registerOnSharedPreferenceChangeListener(this);
    this.mThemeManager.addListener(this);
  }
  
  private boolean containsEffectsSurface()
  {
    if (this.mSurfaceView == null) {}
    for (;;)
    {
      return false;
      for (int i = 0; i < getChildCount(); i++) {
        if (getChildAt(i) == this.mSurfaceView) {
          return true;
        }
      }
    }
  }
  
  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((TouchTypeSoftKeyboard.getInstance() != null) && (this.mKeyboardView != null)) {
      return this.mKeyboardView.onTouchEvent(paramMotionEvent);
    }
    return false;
  }
  
  public BaseKeyboardView<?> getKeyboardView()
  {
    return this.mKeyboardView;
  }
  
  public int getPreferredHeight()
  {
    return this.mKeyboardView.getPreferredHeight();
  }
  
  public float getTotalRowWeight()
  {
    return this.mKeyboardView.getKeyboard().getTotalRowWeight();
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mPreferences.registerOnSharedPreferenceChangeListener(this);
    this.mThemeManager.addListener(this);
  }
  
  protected void onDetachedFromWindow()
  {
    this.mPreferences.unregisterOnSharedPreferenceChangeListener(this);
    this.mThemeManager.removeListener(this);
    getKeyboardView().closing();
    if (containsEffectsSurface())
    {
      this.mSurfaceView.onPause();
      removeView(this.mSurfaceView);
    }
    this.mSurfaceView = null;
    super.onDetachedFromWindow();
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    if (this.mKeyboardView != null)
    {
      this.mKeyboardView.measure(paramInt1, paramInt2);
      if (this.mSurfaceView != null)
      {
        int i = View.MeasureSpec.makeMeasureSpec(this.mKeyboardView.getMeasuredWidth(), 1073741824);
        int j = View.MeasureSpec.makeMeasureSpec(this.mKeyboardView.getMeasuredHeight(), 1073741824);
        this.mSurfaceView.measure(i, j);
      }
      setMeasuredDimension(this.mKeyboardView.getMeasuredWidth(), this.mKeyboardView.getMeasuredHeight());
      return;
    }
    super.onMeasure(paramInt1, paramInt2);
  }
  
  public void onSharedPreferenceChanged(SharedPreferences paramSharedPreferences, String paramString)
  {
    if (paramString.equals(getContext().getResources().getString(2131296498)))
    {
      if (!this.mPreferences.isFlowEnabled()) {
        break label60;
      }
      if (!containsEffectsSurface())
      {
        this.mSurfaceView = new EffectsSurfaceView(getContext());
        addView(this.mSurfaceView);
      }
    }
    return;
    label60:
    removeView(this.mSurfaceView);
    this.mSurfaceView = null;
  }
  
  public void onThemeChanged()
  {
    if (containsEffectsSurface())
    {
      removeView(this.mSurfaceView);
      this.mSurfaceView = new EffectsSurfaceView(getContext());
      addView(this.mSurfaceView);
    }
  }
  
  public void onWindowVisibilityChanged(int paramInt)
  {
    super.onWindowVisibilityChanged(paramInt);
    if ((this.mSurfaceView != null) && (containsEffectsSurface())) {}
    switch (paramInt)
    {
    default: 
      return;
    case 0: 
      this.mSurfaceView.onResume();
      return;
    }
    this.mSurfaceView.onPause();
  }
  
  public void setKeyboardView(BaseKeyboardView paramBaseKeyboardView, boolean paramBoolean, View paramView)
  {
    if (this.mKeyboardView != null)
    {
      int j = getPreferredHeight();
      paramBaseKeyboardView.updateTransformationMatrix(this.mKeyboardView.getWidth(), j);
      removeView(this.mKeyboardView);
    }
    this.mKeyboardView = paramBaseKeyboardView;
    if (this.mKeyboardView != null) {
      addView(this.mKeyboardView, 0);
    }
    int i = 0;
    while (i < getChildCount())
    {
      View localView = getChildAt(i);
      if ((localView != this.mSurfaceView) && (localView != this.mKeyboardView)) {
        removeViewAt(i);
      } else {
        i++;
      }
    }
    if ((this.mSurfaceView == null) && (paramBoolean))
    {
      this.mSurfaceView = new EffectsSurfaceView(getContext());
      addView(this.mSurfaceView);
    }
    if (paramBoolean)
    {
      this.mSurfaceView.setPopupParent(paramView);
      this.mSurfaceView.requestLayout();
      this.mSurfaceView.listenToFlowEvents();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.KeyboardViewContainer
 * JD-Core Version:    0.7.0.1
 */