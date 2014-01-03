package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources.NotFoundException;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;
import com.touchtype.util.LogUtil;

public class MultiViewSwitcher
  extends ViewAnimator
{
  private final SparseArray<Animation> mAnimationCache = new SparseArray();
  private final Context mContext;
  private Handler mDelayedSwitchHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      if (paramAnonymousMessage.what == 0) {
        MultiViewSwitcher.this.doDelayedSwitchView(paramAnonymousMessage.arg1, paramAnonymousMessage.arg2);
      }
    }
  };
  private boolean mDelayedSwitchWaiting = false;
  protected int mRequestedWidth = -3;
  
  public MultiViewSwitcher(Context paramContext, int paramInt1, int paramInt2, ViewGroup.LayoutParams paramLayoutParams)
  {
    this(paramContext, paramLayoutParams);
    setInAnimation(this.mContext, paramInt1);
    setOutAnimation(this.mContext, paramInt2);
  }
  
  public MultiViewSwitcher(Context paramContext, ViewGroup.LayoutParams paramLayoutParams)
  {
    super(paramContext);
    this.mContext = paramContext;
    setLayoutParams(paramLayoutParams);
  }
  
  private void doDelayedSwitchView(int paramInt1, int paramInt2)
  {
    switchView(paramInt1, paramInt2);
    this.mDelayedSwitchWaiting = false;
  }
  
  private Animation getAnimation(int paramInt)
  {
    Object localObject = (Animation)this.mAnimationCache.get(paramInt);
    if (localObject == null) {}
    try
    {
      Animation localAnimation = AnimationUtils.loadAnimation(getContext(), paramInt);
      localObject = localAnimation;
      this.mAnimationCache.put(paramInt, localObject);
      return localObject;
    }
    catch (Resources.NotFoundException localNotFoundException)
    {
      LogUtil.w("ViewSwitcher", "Animation not found");
    }
    return null;
  }
  
  private void switchView(int paramInt1, int paramInt2)
  {
    if (paramInt1 != getDisplayedChild())
    {
      setDisplayedChild(paramInt1);
      Animation localAnimation = getAnimation(paramInt2);
      if (localAnimation != null) {
        getCurrentView().startAnimation(localAnimation);
      }
    }
  }
  
  public boolean registerView(int paramInt1, int paramInt2)
    throws InflateException
  {
    return registerView(paramInt1, ((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(paramInt2, null));
  }
  
  public boolean registerView(int paramInt, View paramView)
    throws IllegalArgumentException
  {
    if (paramView == null) {
      throw new IllegalArgumentException("Attempted to register a null view.");
    }
    if (getChildAt(paramInt) != null) {
      return false;
    }
    ViewGroup.LayoutParams localLayoutParams = paramView.getLayoutParams();
    if (localLayoutParams != null) {}
    for (;;)
    {
      addView(paramView, paramInt, localLayoutParams);
      return true;
      localLayoutParams = getLayoutParams();
    }
  }
  
  public void setRequestedWidthAt(int paramInt1, int paramInt2)
  {
    this.mRequestedWidth = paramInt1;
    ViewGroup.MarginLayoutParams localMarginLayoutParams = (ViewGroup.MarginLayoutParams)getLayoutParams();
    localMarginLayoutParams.width = paramInt1;
    localMarginLayoutParams.setMargins(paramInt2, 0, 0, 0);
    setLayoutParams(localMarginLayoutParams);
    measure(View.MeasureSpec.makeMeasureSpec(paramInt1, 1073741824), 0);
    requestLayout();
  }
  
  public boolean switchView(int paramInt1, int paramInt2, int paramInt3)
  {
    if (getChildAt(paramInt1) == null) {
      return false;
    }
    if (this.mDelayedSwitchWaiting)
    {
      this.mDelayedSwitchWaiting = false;
      this.mDelayedSwitchHandler.removeMessages(0);
    }
    if (paramInt3 == 0) {
      switchView(paramInt1, paramInt2);
    }
    for (;;)
    {
      return true;
      Message localMessage = this.mDelayedSwitchHandler.obtainMessage(0, paramInt1, paramInt2);
      this.mDelayedSwitchHandler.sendMessageDelayed(localMessage, paramInt3);
      this.mDelayedSwitchWaiting = true;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.MultiViewSwitcher
 * JD-Core Version:    0.7.0.1
 */