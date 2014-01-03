package android.support.v4.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class SlidingPaneLayout
  extends ViewGroup
{
  static final SlidingPanelLayoutImpl IMPL = new SlidingPanelLayoutImplBase();
  private boolean mCanSlide;
  private int mCoveredFadeColor;
  private final ViewDragHelper mDragHelper;
  private boolean mFirstLayout = true;
  private float mInitialMotionX;
  private float mInitialMotionY;
  private boolean mIsUnableToDrag;
  private final int mOverhangSize;
  private PanelSlideListener mPanelSlideListener;
  private int mParallaxBy;
  private float mParallaxOffset;
  private final ArrayList<DisableLayerRunnable> mPostedRunnables = new ArrayList();
  private boolean mPreservedOpenState;
  private Drawable mShadowDrawable;
  private float mSlideOffset;
  private int mSlideRange;
  private View mSlideableView;
  private int mSliderFadeColor = -858993460;
  private final Rect mTmpRect = new Rect();
  
  static
  {
    int i = Build.VERSION.SDK_INT;
    if (i >= 17)
    {
      IMPL = new SlidingPanelLayoutImplJBMR1();
      return;
    }
    if (i >= 16)
    {
      IMPL = new SlidingPanelLayoutImplJB();
      return;
    }
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet)
  {
    this(paramContext, paramAttributeSet, 0);
  }
  
  public SlidingPaneLayout(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    float f = paramContext.getResources().getDisplayMetrics().density;
    this.mOverhangSize = ((int)(0.5F + 32.0F * f));
    ViewConfiguration.get(paramContext);
    setWillNotDraw(false);
    ViewCompat.setAccessibilityDelegate(this, new AccessibilityDelegate());
    ViewCompat.setImportantForAccessibility(this, 1);
    this.mDragHelper = ViewDragHelper.create(this, 0.5F, new DragHelperCallback(null));
    this.mDragHelper.setEdgeTrackingEnabled(1);
    this.mDragHelper.setMinVelocity(400.0F * f);
  }
  
  private boolean closePane(View paramView, int paramInt)
  {
    boolean bool1;
    if (!this.mFirstLayout)
    {
      boolean bool2 = smoothSlideTo(0.0F, paramInt);
      bool1 = false;
      if (!bool2) {}
    }
    else
    {
      this.mPreservedOpenState = false;
      bool1 = true;
    }
    return bool1;
  }
  
  private void dimChildView(View paramView, float paramFloat, int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    if ((paramFloat > 0.0F) && (paramInt != 0))
    {
      i = (int)(paramFloat * ((0xFF000000 & paramInt) >>> 24)) << 24 | 0xFFFFFF & paramInt;
      if (localLayoutParams.dimPaint == null) {
        localLayoutParams.dimPaint = new Paint();
      }
      localLayoutParams.dimPaint.setColorFilter(new PorterDuffColorFilter(i, PorterDuff.Mode.SRC_OVER));
      if (ViewCompat.getLayerType(paramView) != 2) {
        ViewCompat.setLayerType(paramView, 2, localLayoutParams.dimPaint);
      }
      invalidateChildRegion(paramView);
    }
    while (ViewCompat.getLayerType(paramView) == 0)
    {
      int i;
      return;
    }
    if (localLayoutParams.dimPaint != null) {
      localLayoutParams.dimPaint.setColorFilter(null);
    }
    DisableLayerRunnable localDisableLayerRunnable = new DisableLayerRunnable(paramView);
    this.mPostedRunnables.add(localDisableLayerRunnable);
    ViewCompat.postOnAnimation(this, localDisableLayerRunnable);
  }
  
  private void invalidateChildRegion(View paramView)
  {
    IMPL.invalidateChildRegion(this, paramView);
  }
  
  private void onPanelDragged(int paramInt)
  {
    LayoutParams localLayoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
    this.mSlideOffset = ((paramInt - (getPaddingLeft() + localLayoutParams.leftMargin)) / this.mSlideRange);
    if (this.mParallaxBy != 0) {
      parallaxOtherViews(this.mSlideOffset);
    }
    if (localLayoutParams.dimWhenOffset) {
      dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
    }
    dispatchOnPanelSlide(this.mSlideableView);
  }
  
  private boolean openPane(View paramView, int paramInt)
  {
    if ((this.mFirstLayout) || (smoothSlideTo(1.0F, paramInt)))
    {
      this.mPreservedOpenState = true;
      return true;
    }
    return false;
  }
  
  private void parallaxOtherViews(float paramFloat)
  {
    LayoutParams localLayoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
    if ((localLayoutParams.dimWhenOffset) && (localLayoutParams.leftMargin <= 0)) {}
    for (int i = 1;; i = 0)
    {
      int j = getChildCount();
      for (int k = 0; k < j; k++)
      {
        View localView = getChildAt(k);
        if (localView != this.mSlideableView)
        {
          int m = (int)((1.0F - this.mParallaxOffset) * this.mParallaxBy);
          this.mParallaxOffset = paramFloat;
          localView.offsetLeftAndRight(m - (int)((1.0F - paramFloat) * this.mParallaxBy));
          if (i != 0) {
            dimChildView(localView, 1.0F - this.mParallaxOffset, this.mCoveredFadeColor);
          }
        }
      }
    }
  }
  
  private static boolean viewIsOpaque(View paramView)
  {
    if (ViewCompat.isOpaque(paramView)) {}
    Drawable localDrawable;
    do
    {
      return true;
      if (Build.VERSION.SDK_INT >= 18) {
        return false;
      }
      localDrawable = paramView.getBackground();
      if (localDrawable == null) {
        break;
      }
    } while (localDrawable.getOpacity() == -1);
    return false;
    return false;
  }
  
  protected boolean checkLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    return ((paramLayoutParams instanceof LayoutParams)) && (super.checkLayoutParams(paramLayoutParams));
  }
  
  public boolean closePane()
  {
    return closePane(this.mSlideableView, 0);
  }
  
  public void computeScroll()
  {
    if (this.mDragHelper.continueSettling(true))
    {
      if (!this.mCanSlide) {
        this.mDragHelper.abort();
      }
    }
    else {
      return;
    }
    ViewCompat.postInvalidateOnAnimation(this);
  }
  
  void dispatchOnPanelClosed(View paramView)
  {
    if (this.mPanelSlideListener != null) {}
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelOpened(View paramView)
  {
    if (this.mPanelSlideListener != null) {}
    sendAccessibilityEvent(32);
  }
  
  void dispatchOnPanelSlide(View paramView)
  {
    if (this.mPanelSlideListener != null) {}
  }
  
  public void draw(Canvas paramCanvas)
  {
    super.draw(paramCanvas);
    if (getChildCount() > 1) {}
    for (View localView = getChildAt(1); (localView == null) || (this.mShadowDrawable == null); localView = null) {
      return;
    }
    int i = this.mShadowDrawable.getIntrinsicWidth();
    int j = localView.getLeft();
    int k = localView.getTop();
    int m = localView.getBottom();
    int n = j - i;
    this.mShadowDrawable.setBounds(n, k, j, m);
    this.mShadowDrawable.draw(paramCanvas);
  }
  
  protected boolean drawChild(Canvas paramCanvas, View paramView, long paramLong)
  {
    LayoutParams localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    int i = paramCanvas.save(2);
    if ((this.mCanSlide) && (!localLayoutParams.slideable) && (this.mSlideableView != null))
    {
      paramCanvas.getClipBounds(this.mTmpRect);
      this.mTmpRect.right = Math.min(this.mTmpRect.right, this.mSlideableView.getLeft());
      paramCanvas.clipRect(this.mTmpRect);
    }
    boolean bool;
    if (Build.VERSION.SDK_INT < 11)
    {
      if ((!localLayoutParams.dimWhenOffset) || (this.mSlideOffset <= 0.0F)) {
        break label203;
      }
      if (!paramView.isDrawingCacheEnabled()) {
        paramView.setDrawingCacheEnabled(true);
      }
      Bitmap localBitmap = paramView.getDrawingCache();
      if (localBitmap != null)
      {
        paramCanvas.drawBitmap(localBitmap, paramView.getLeft(), paramView.getTop(), localLayoutParams.dimPaint);
        bool = false;
        paramCanvas.restoreToCount(i);
        return bool;
      }
      Log.e("SlidingPaneLayout", "drawChild: child view " + paramView + " returned null drawing cache");
    }
    for (;;)
    {
      bool = super.drawChild(paramCanvas, paramView, paramLong);
      break;
      label203:
      if (paramView.isDrawingCacheEnabled()) {
        paramView.setDrawingCacheEnabled(false);
      }
    }
  }
  
  protected ViewGroup.LayoutParams generateDefaultLayoutParams()
  {
    return new LayoutParams();
  }
  
  public ViewGroup.LayoutParams generateLayoutParams(AttributeSet paramAttributeSet)
  {
    return new LayoutParams(getContext(), paramAttributeSet);
  }
  
  protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams paramLayoutParams)
  {
    if ((paramLayoutParams instanceof ViewGroup.MarginLayoutParams)) {
      return new LayoutParams((ViewGroup.MarginLayoutParams)paramLayoutParams);
    }
    return new LayoutParams(paramLayoutParams);
  }
  
  public int getCoveredFadeColor()
  {
    return this.mCoveredFadeColor;
  }
  
  public int getParallaxDistance()
  {
    return this.mParallaxBy;
  }
  
  public int getSliderFadeColor()
  {
    return this.mSliderFadeColor;
  }
  
  boolean isDimmed(View paramView)
  {
    if (paramView == null) {}
    LayoutParams localLayoutParams;
    do
    {
      return false;
      localLayoutParams = (LayoutParams)paramView.getLayoutParams();
    } while ((!this.mCanSlide) || (!localLayoutParams.dimWhenOffset) || (this.mSlideOffset <= 0.0F));
    return true;
  }
  
  public boolean isOpen()
  {
    return (!this.mCanSlide) || (this.mSlideOffset == 1.0F);
  }
  
  public boolean isSlideable()
  {
    return this.mCanSlide;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mFirstLayout = true;
  }
  
  protected void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    this.mFirstLayout = true;
    int i = 0;
    int j = this.mPostedRunnables.size();
    while (i < j)
    {
      ((DisableLayerRunnable)this.mPostedRunnables.get(i)).run();
      i++;
    }
    this.mPostedRunnables.clear();
  }
  
  public boolean onInterceptTouchEvent(MotionEvent paramMotionEvent)
  {
    int i = MotionEventCompat.getActionMasked(paramMotionEvent);
    if ((!this.mCanSlide) && (i == 0) && (getChildCount() > 1))
    {
      View localView = getChildAt(1);
      if (localView != null) {
        if (this.mDragHelper.isViewUnder(localView, (int)paramMotionEvent.getX(), (int)paramMotionEvent.getY())) {
          break label100;
        }
      }
    }
    boolean bool1;
    label100:
    for (boolean bool6 = true;; bool6 = false)
    {
      this.mPreservedOpenState = bool6;
      if ((this.mCanSlide) && ((!this.mIsUnableToDrag) || (i == 0))) {
        break;
      }
      this.mDragHelper.cancel();
      bool1 = super.onInterceptTouchEvent(paramMotionEvent);
      return bool1;
    }
    if ((i == 3) || (i == 1))
    {
      this.mDragHelper.cancel();
      return false;
    }
    int j = 0;
    switch (i)
    {
    }
    boolean bool3;
    do
    {
      float f3;
      float f4;
      boolean bool2;
      do
      {
        for (;;)
        {
          if (!this.mDragHelper.shouldInterceptTouchEvent(paramMotionEvent))
          {
            bool1 = false;
            if (j == 0) {
              break;
            }
          }
          return true;
          this.mIsUnableToDrag = false;
          float f5 = paramMotionEvent.getX();
          float f6 = paramMotionEvent.getY();
          this.mInitialMotionX = f5;
          this.mInitialMotionY = f6;
          boolean bool4 = this.mDragHelper.isViewUnder(this.mSlideableView, (int)f5, (int)f6);
          j = 0;
          if (bool4)
          {
            boolean bool5 = isDimmed(this.mSlideableView);
            j = 0;
            if (bool5) {
              j = 1;
            }
          }
        }
        float f1 = paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY();
        f3 = Math.abs(f1 - this.mInitialMotionX);
        f4 = Math.abs(f2 - this.mInitialMotionY);
        bool2 = f3 < this.mDragHelper.getTouchSlop();
        j = 0;
      } while (!bool2);
      bool3 = f4 < f3;
      j = 0;
    } while (!bool3);
    this.mDragHelper.cancel();
    this.mIsUnableToDrag = true;
    return false;
  }
  
  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = paramInt3 - paramInt1;
    int j = getPaddingLeft();
    int k = getPaddingRight();
    int m = getPaddingTop();
    int n = getChildCount();
    int i1 = j;
    int i2 = j;
    float f;
    int i3;
    label71:
    View localView;
    int i5;
    int i6;
    boolean bool2;
    if (this.mFirstLayout)
    {
      if ((this.mCanSlide) && (this.mPreservedOpenState))
      {
        f = 1.0F;
        this.mSlideOffset = f;
      }
    }
    else
    {
      i3 = 0;
      if (i3 >= n) {
        break label327;
      }
      localView = getChildAt(i3);
      if (localView.getVisibility() != 8)
      {
        LayoutParams localLayoutParams = (LayoutParams)localView.getLayoutParams();
        i5 = localView.getMeasuredWidth();
        i6 = 0;
        if (!localLayoutParams.slideable) {
          break label277;
        }
        int i9 = localLayoutParams.leftMargin + localLayoutParams.rightMargin;
        int i10 = Math.min(i2, i - k - this.mOverhangSize) - i1 - i9;
        this.mSlideRange = i10;
        if (i10 + (i1 + localLayoutParams.leftMargin) + i5 / 2 <= i - k) {
          break label271;
        }
        bool2 = true;
        label193:
        localLayoutParams.dimWhenOffset = bool2;
        i1 += (int)(i10 * this.mSlideOffset) + localLayoutParams.leftMargin;
      }
    }
    for (;;)
    {
      int i7 = i1 - i6;
      localView.layout(i7, m, i7 + i5, m + localView.getMeasuredHeight());
      i2 += localView.getWidth();
      i3++;
      break label71;
      f = 0.0F;
      break;
      label271:
      bool2 = false;
      break label193;
      label277:
      boolean bool1 = this.mCanSlide;
      i6 = 0;
      if (bool1)
      {
        int i8 = this.mParallaxBy;
        i6 = 0;
        if (i8 != 0) {
          i6 = (int)((1.0F - this.mSlideOffset) * this.mParallaxBy);
        }
      }
      i1 = i2;
    }
    label327:
    if (this.mFirstLayout)
    {
      if (!this.mCanSlide) {
        break label402;
      }
      if (this.mParallaxBy != 0) {
        parallaxOtherViews(this.mSlideOffset);
      }
      if (((LayoutParams)this.mSlideableView.getLayoutParams()).dimWhenOffset) {
        dimChildView(this.mSlideableView, this.mSlideOffset, this.mSliderFadeColor);
      }
    }
    for (;;)
    {
      updateObscuredViewsVisibility(this.mSlideableView);
      this.mFirstLayout = false;
      return;
      label402:
      for (int i4 = 0; i4 < n; i4++) {
        dimChildView(getChildAt(i4), 0.0F, this.mSliderFadeColor);
      }
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getMode(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt1);
    int k = View.MeasureSpec.getMode(paramInt2);
    int m = View.MeasureSpec.getSize(paramInt2);
    int n;
    int i1;
    label88:
    float f;
    boolean bool1;
    int i2;
    int i3;
    int i4;
    label138:
    View localView2;
    LayoutParams localLayoutParams2;
    if (i != 1073741824) {
      if (isInEditMode())
      {
        if ((i != -2147483648) && (i == 0)) {
          j = 300;
        }
        n = -1;
        i1 = 0;
        switch (k)
        {
        default: 
          f = 0.0F;
          bool1 = false;
          i2 = j - getPaddingLeft() - getPaddingRight();
          i3 = getChildCount();
          if (i3 > 2) {
            Log.e("SlidingPaneLayout", "onMeasure: More than two child views are not supported.");
          }
          this.mSlideableView = null;
          i4 = 0;
          if (i4 >= i3) {
            break label540;
          }
          localView2 = getChildAt(i4);
          localLayoutParams2 = (LayoutParams)localView2.getLayoutParams();
          if (localView2.getVisibility() == 8) {
            localLayoutParams2.dimWhenOffset = false;
          }
          break;
        }
      }
    }
    do
    {
      i4++;
      break label138;
      throw new IllegalStateException("Width must have an exact value or MATCH_PARENT");
      if (k != 0) {
        break;
      }
      if (isInEditMode())
      {
        if (k != 0) {
          break;
        }
        k = -2147483648;
        m = 300;
        break;
      }
      throw new IllegalStateException("Height must not be UNSPECIFIED");
      n = m - getPaddingTop() - getPaddingBottom();
      i1 = n;
      break label88;
      n = m - getPaddingTop() - getPaddingBottom();
      i1 = 0;
      break label88;
      if (localLayoutParams2.weight <= 0.0F) {
        break label306;
      }
      f += localLayoutParams2.weight;
    } while (localLayoutParams2.width == 0);
    label306:
    int i14 = localLayoutParams2.leftMargin + localLayoutParams2.rightMargin;
    int i15;
    label342:
    int i16;
    if (localLayoutParams2.width == -2)
    {
      i15 = View.MeasureSpec.makeMeasureSpec(j - i14, -2147483648);
      if (localLayoutParams2.height != -2) {
        break label496;
      }
      i16 = View.MeasureSpec.makeMeasureSpec(n, -2147483648);
      label362:
      localView2.measure(i15, i16);
      int i17 = localView2.getMeasuredWidth();
      int i18 = localView2.getMeasuredHeight();
      if ((k == -2147483648) && (i18 > i1)) {
        i1 = Math.min(i18, n);
      }
      i2 -= i17;
      if (i2 >= 0) {
        break label534;
      }
    }
    label534:
    for (boolean bool2 = true;; bool2 = false)
    {
      localLayoutParams2.slideable = bool2;
      bool1 |= bool2;
      if (!localLayoutParams2.slideable) {
        break;
      }
      this.mSlideableView = localView2;
      break;
      if (localLayoutParams2.width == -1)
      {
        i15 = View.MeasureSpec.makeMeasureSpec(j - i14, 1073741824);
        break label342;
      }
      i15 = View.MeasureSpec.makeMeasureSpec(localLayoutParams2.width, 1073741824);
      break label342;
      label496:
      if (localLayoutParams2.height == -1)
      {
        i16 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
        break label362;
      }
      i16 = View.MeasureSpec.makeMeasureSpec(localLayoutParams2.height, 1073741824);
      break label362;
    }
    label540:
    if ((bool1) || (f > 0.0F))
    {
      int i5 = j - this.mOverhangSize;
      int i6 = 0;
      if (i6 < i3)
      {
        View localView1 = getChildAt(i6);
        LayoutParams localLayoutParams1;
        int i7;
        label630:
        int i8;
        label638:
        int i13;
        if (localView1.getVisibility() != 8)
        {
          localLayoutParams1 = (LayoutParams)localView1.getLayoutParams();
          if (localView1.getVisibility() != 8)
          {
            if ((localLayoutParams1.width != 0) || (localLayoutParams1.weight <= 0.0F)) {
              break label723;
            }
            i7 = 1;
            if (i7 == 0) {
              break label729;
            }
            i8 = 0;
            if ((!bool1) || (localView1 == this.mSlideableView)) {
              break label793;
            }
            if ((localLayoutParams1.width < 0) && ((i8 > i5) || (localLayoutParams1.weight > 0.0F)))
            {
              if (i7 == 0) {
                break label777;
              }
              if (localLayoutParams1.height != -2) {
                break label739;
              }
              i13 = View.MeasureSpec.makeMeasureSpec(n, -2147483648);
              label702:
              localView1.measure(View.MeasureSpec.makeMeasureSpec(i5, 1073741824), i13);
            }
          }
        }
        for (;;)
        {
          i6++;
          break;
          label723:
          i7 = 0;
          break label630;
          label729:
          i8 = localView1.getMeasuredWidth();
          break label638;
          label739:
          if (localLayoutParams1.height == -1)
          {
            i13 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
            break label702;
          }
          i13 = View.MeasureSpec.makeMeasureSpec(localLayoutParams1.height, 1073741824);
          break label702;
          i13 = View.MeasureSpec.makeMeasureSpec(localView1.getMeasuredHeight(), 1073741824);
          break label702;
          if (localLayoutParams1.weight > 0.0F)
          {
            int i9;
            if (localLayoutParams1.width == 0) {
              if (localLayoutParams1.height == -2) {
                i9 = View.MeasureSpec.makeMeasureSpec(n, -2147483648);
              }
            }
            for (;;)
            {
              if (!bool1) {
                break label935;
              }
              int i11 = j - (localLayoutParams1.leftMargin + localLayoutParams1.rightMargin);
              int i12 = View.MeasureSpec.makeMeasureSpec(i11, 1073741824);
              if (i8 == i11) {
                break;
              }
              localView1.measure(i12, i9);
              break;
              if (localLayoutParams1.height == -1)
              {
                i9 = View.MeasureSpec.makeMeasureSpec(n, 1073741824);
              }
              else
              {
                i9 = View.MeasureSpec.makeMeasureSpec(localLayoutParams1.height, 1073741824);
                continue;
                i9 = View.MeasureSpec.makeMeasureSpec(localView1.getMeasuredHeight(), 1073741824);
              }
            }
            label935:
            int i10 = Math.max(0, i2);
            localView1.measure(View.MeasureSpec.makeMeasureSpec(i8 + (int)(localLayoutParams1.weight * i10 / f), 1073741824), i9);
          }
        }
      }
    }
    label777:
    label793:
    setMeasuredDimension(j, i1);
    this.mCanSlide = bool1;
    if ((this.mDragHelper.getViewDragState() != 0) && (!bool1)) {
      this.mDragHelper.abort();
    }
  }
  
  protected void onRestoreInstanceState(Parcelable paramParcelable)
  {
    SavedState localSavedState = (SavedState)paramParcelable;
    super.onRestoreInstanceState(localSavedState.getSuperState());
    if (localSavedState.isOpen) {
      openPane();
    }
    for (;;)
    {
      this.mPreservedOpenState = localSavedState.isOpen;
      return;
      closePane();
    }
  }
  
  protected Parcelable onSaveInstanceState()
  {
    SavedState localSavedState = new SavedState(super.onSaveInstanceState());
    if (isSlideable()) {}
    for (boolean bool = isOpen();; bool = this.mPreservedOpenState)
    {
      localSavedState.isOpen = bool;
      return localSavedState;
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if (paramInt1 != paramInt3) {
      this.mFirstLayout = true;
    }
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (!this.mCanSlide) {
      return super.onTouchEvent(paramMotionEvent);
    }
    this.mDragHelper.processTouchEvent(paramMotionEvent);
    switch (0xFF & paramMotionEvent.getAction())
    {
    }
    for (;;)
    {
      return true;
      float f5 = paramMotionEvent.getX();
      float f6 = paramMotionEvent.getY();
      this.mInitialMotionX = f5;
      this.mInitialMotionY = f6;
      continue;
      if (isDimmed(this.mSlideableView))
      {
        float f1 = paramMotionEvent.getX();
        float f2 = paramMotionEvent.getY();
        float f3 = f1 - this.mInitialMotionX;
        float f4 = f2 - this.mInitialMotionY;
        int i = this.mDragHelper.getTouchSlop();
        if ((f3 * f3 + f4 * f4 < i * i) && (this.mDragHelper.isViewUnder(this.mSlideableView, (int)f1, (int)f2))) {
          closePane(this.mSlideableView, 0);
        }
      }
    }
  }
  
  public boolean openPane()
  {
    return openPane(this.mSlideableView, 0);
  }
  
  public void requestChildFocus(View paramView1, View paramView2)
  {
    super.requestChildFocus(paramView1, paramView2);
    if ((!isInTouchMode()) && (!this.mCanSlide)) {
      if (paramView1 != this.mSlideableView) {
        break label36;
      }
    }
    label36:
    for (boolean bool = true;; bool = false)
    {
      this.mPreservedOpenState = bool;
      return;
    }
  }
  
  void setAllChildrenVisible()
  {
    int i = 0;
    int j = getChildCount();
    while (i < j)
    {
      View localView = getChildAt(i);
      if (localView.getVisibility() == 4) {
        localView.setVisibility(0);
      }
      i++;
    }
  }
  
  public void setCoveredFadeColor(int paramInt)
  {
    this.mCoveredFadeColor = paramInt;
  }
  
  public void setPanelSlideListener(PanelSlideListener paramPanelSlideListener)
  {
    this.mPanelSlideListener = paramPanelSlideListener;
  }
  
  public void setParallaxDistance(int paramInt)
  {
    this.mParallaxBy = paramInt;
    requestLayout();
  }
  
  public void setShadowDrawable(Drawable paramDrawable)
  {
    this.mShadowDrawable = paramDrawable;
  }
  
  public void setShadowResource(int paramInt)
  {
    setShadowDrawable(getResources().getDrawable(paramInt));
  }
  
  public void setSliderFadeColor(int paramInt)
  {
    this.mSliderFadeColor = paramInt;
  }
  
  boolean smoothSlideTo(float paramFloat, int paramInt)
  {
    if (!this.mCanSlide) {}
    int i;
    do
    {
      return false;
      LayoutParams localLayoutParams = (LayoutParams)this.mSlideableView.getLayoutParams();
      i = (int)(getPaddingLeft() + localLayoutParams.leftMargin + paramFloat * this.mSlideRange);
    } while (!this.mDragHelper.smoothSlideViewTo(this.mSlideableView, i, this.mSlideableView.getTop()));
    setAllChildrenVisible();
    ViewCompat.postInvalidateOnAnimation(this);
    return true;
  }
  
  void updateObscuredViewsVisibility(View paramView)
  {
    int i = getPaddingLeft();
    int j = getWidth() - getPaddingRight();
    int k = getPaddingTop();
    int m = getHeight() - getPaddingBottom();
    int i1;
    int i2;
    int i3;
    int n;
    int i4;
    label76:
    View localView;
    if ((paramView != null) && (viewIsOpaque(paramView)))
    {
      i1 = paramView.getLeft();
      i2 = paramView.getRight();
      i3 = paramView.getTop();
      n = paramView.getBottom();
      i4 = 0;
      int i5 = getChildCount();
      if (i4 >= i5) {
        return;
      }
      localView = getChildAt(i4);
      if (localView == paramView) {
        return;
      }
      int i6 = Math.max(i, localView.getLeft());
      int i7 = Math.max(k, localView.getTop());
      int i8 = Math.min(j, localView.getRight());
      int i9 = Math.min(m, localView.getBottom());
      if ((i6 < i1) || (i7 < i3) || (i8 > i2) || (i9 > n)) {
        break label202;
      }
    }
    label202:
    for (int i10 = 4;; i10 = 0)
    {
      localView.setVisibility(i10);
      i4++;
      break label76;
      n = 0;
      i1 = 0;
      i2 = 0;
      i3 = 0;
      break;
    }
  }
  
  final class AccessibilityDelegate
    extends AccessibilityDelegateCompat
  {
    private final Rect mTmpRect = new Rect();
    
    AccessibilityDelegate() {}
    
    private void copyNodeInfoNoChildren(AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat1, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat2)
    {
      Rect localRect = this.mTmpRect;
      paramAccessibilityNodeInfoCompat2.getBoundsInParent(localRect);
      paramAccessibilityNodeInfoCompat1.setBoundsInParent(localRect);
      paramAccessibilityNodeInfoCompat2.getBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setBoundsInScreen(localRect);
      paramAccessibilityNodeInfoCompat1.setVisibleToUser(paramAccessibilityNodeInfoCompat2.isVisibleToUser());
      paramAccessibilityNodeInfoCompat1.setPackageName(paramAccessibilityNodeInfoCompat2.getPackageName());
      paramAccessibilityNodeInfoCompat1.setClassName(paramAccessibilityNodeInfoCompat2.getClassName());
      paramAccessibilityNodeInfoCompat1.setContentDescription(paramAccessibilityNodeInfoCompat2.getContentDescription());
      paramAccessibilityNodeInfoCompat1.setEnabled(paramAccessibilityNodeInfoCompat2.isEnabled());
      paramAccessibilityNodeInfoCompat1.setClickable(paramAccessibilityNodeInfoCompat2.isClickable());
      paramAccessibilityNodeInfoCompat1.setFocusable(paramAccessibilityNodeInfoCompat2.isFocusable());
      paramAccessibilityNodeInfoCompat1.setFocused(paramAccessibilityNodeInfoCompat2.isFocused());
      paramAccessibilityNodeInfoCompat1.setAccessibilityFocused(paramAccessibilityNodeInfoCompat2.isAccessibilityFocused());
      paramAccessibilityNodeInfoCompat1.setSelected(paramAccessibilityNodeInfoCompat2.isSelected());
      paramAccessibilityNodeInfoCompat1.setLongClickable(paramAccessibilityNodeInfoCompat2.isLongClickable());
      paramAccessibilityNodeInfoCompat1.addAction(paramAccessibilityNodeInfoCompat2.getActions());
      paramAccessibilityNodeInfoCompat1.setMovementGranularities(paramAccessibilityNodeInfoCompat2.getMovementGranularities());
    }
    
    public boolean filter(View paramView)
    {
      return SlidingPaneLayout.this.isDimmed(paramView);
    }
    
    public void onInitializeAccessibilityEvent(View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      super.onInitializeAccessibilityEvent(paramView, paramAccessibilityEvent);
      paramAccessibilityEvent.setClassName(SlidingPaneLayout.class.getName());
    }
    
    public void onInitializeAccessibilityNodeInfo(View paramView, AccessibilityNodeInfoCompat paramAccessibilityNodeInfoCompat)
    {
      AccessibilityNodeInfoCompat localAccessibilityNodeInfoCompat = AccessibilityNodeInfoCompat.obtain(paramAccessibilityNodeInfoCompat);
      super.onInitializeAccessibilityNodeInfo(paramView, localAccessibilityNodeInfoCompat);
      copyNodeInfoNoChildren(paramAccessibilityNodeInfoCompat, localAccessibilityNodeInfoCompat);
      localAccessibilityNodeInfoCompat.recycle();
      paramAccessibilityNodeInfoCompat.setClassName(SlidingPaneLayout.class.getName());
      paramAccessibilityNodeInfoCompat.setSource(paramView);
      ViewParent localViewParent = ViewCompat.getParentForAccessibility(paramView);
      if ((localViewParent instanceof View)) {
        paramAccessibilityNodeInfoCompat.setParent((View)localViewParent);
      }
      int i = SlidingPaneLayout.this.getChildCount();
      for (int j = 0; j < i; j++)
      {
        View localView = SlidingPaneLayout.this.getChildAt(j);
        if ((!filter(localView)) && (localView.getVisibility() == 0))
        {
          ViewCompat.setImportantForAccessibility(localView, 1);
          paramAccessibilityNodeInfoCompat.addChild(localView);
        }
      }
    }
    
    public boolean onRequestSendAccessibilityEvent(ViewGroup paramViewGroup, View paramView, AccessibilityEvent paramAccessibilityEvent)
    {
      if (!filter(paramView)) {
        return super.onRequestSendAccessibilityEvent(paramViewGroup, paramView, paramAccessibilityEvent);
      }
      return false;
    }
  }
  
  private final class DisableLayerRunnable
    implements Runnable
  {
    final View mChildView;
    
    DisableLayerRunnable(View paramView)
    {
      this.mChildView = paramView;
    }
    
    public void run()
    {
      if (this.mChildView.getParent() == SlidingPaneLayout.this)
      {
        ViewCompat.setLayerType(this.mChildView, 0, null);
        SlidingPaneLayout.this.invalidateChildRegion(this.mChildView);
      }
      SlidingPaneLayout.this.mPostedRunnables.remove(this);
    }
  }
  
  private final class DragHelperCallback
    extends ViewDragHelper.Callback
  {
    private DragHelperCallback() {}
    
    public int clampViewPositionHorizontal(View paramView, int paramInt1, int paramInt2)
    {
      SlidingPaneLayout.LayoutParams localLayoutParams = (SlidingPaneLayout.LayoutParams)SlidingPaneLayout.this.mSlideableView.getLayoutParams();
      int i = SlidingPaneLayout.this.getPaddingLeft() + localLayoutParams.leftMargin;
      int j = i + SlidingPaneLayout.this.mSlideRange;
      return Math.min(Math.max(paramInt1, i), j);
    }
    
    public int getViewHorizontalDragRange(View paramView)
    {
      return SlidingPaneLayout.this.mSlideRange;
    }
    
    public void onEdgeDragStarted(int paramInt1, int paramInt2)
    {
      SlidingPaneLayout.this.mDragHelper.captureChildView(SlidingPaneLayout.this.mSlideableView, paramInt2);
    }
    
    public void onViewCaptured(View paramView, int paramInt)
    {
      SlidingPaneLayout.this.setAllChildrenVisible();
    }
    
    public void onViewDragStateChanged(int paramInt)
    {
      if (SlidingPaneLayout.this.mDragHelper.getViewDragState() == 0)
      {
        if (SlidingPaneLayout.this.mSlideOffset == 0.0F)
        {
          SlidingPaneLayout.this.updateObscuredViewsVisibility(SlidingPaneLayout.this.mSlideableView);
          SlidingPaneLayout.this.dispatchOnPanelClosed(SlidingPaneLayout.this.mSlideableView);
          SlidingPaneLayout.access$502(SlidingPaneLayout.this, false);
        }
      }
      else {
        return;
      }
      SlidingPaneLayout.this.dispatchOnPanelOpened(SlidingPaneLayout.this.mSlideableView);
      SlidingPaneLayout.access$502(SlidingPaneLayout.this, true);
    }
    
    public void onViewPositionChanged(View paramView, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      SlidingPaneLayout.this.onPanelDragged(paramInt1);
      SlidingPaneLayout.this.invalidate();
    }
    
    public void onViewReleased(View paramView, float paramFloat1, float paramFloat2)
    {
      SlidingPaneLayout.LayoutParams localLayoutParams = (SlidingPaneLayout.LayoutParams)paramView.getLayoutParams();
      int i = SlidingPaneLayout.this.getPaddingLeft() + localLayoutParams.leftMargin;
      if ((paramFloat1 > 0.0F) || ((paramFloat1 == 0.0F) && (SlidingPaneLayout.this.mSlideOffset > 0.5F))) {
        i += SlidingPaneLayout.this.mSlideRange;
      }
      SlidingPaneLayout.this.mDragHelper.settleCapturedViewAt(i, paramView.getTop());
      SlidingPaneLayout.this.invalidate();
    }
    
    public boolean tryCaptureView(View paramView, int paramInt)
    {
      if (SlidingPaneLayout.this.mIsUnableToDrag) {
        return false;
      }
      return ((SlidingPaneLayout.LayoutParams)paramView.getLayoutParams()).slideable;
    }
  }
  
  public static class LayoutParams
    extends ViewGroup.MarginLayoutParams
  {
    private static final int[] ATTRS = { 16843137 };
    Paint dimPaint;
    boolean dimWhenOffset;
    boolean slideable;
    public float weight = 0.0F;
    
    public LayoutParams()
    {
      super(-1);
    }
    
    public LayoutParams(Context paramContext, AttributeSet paramAttributeSet)
    {
      super(paramAttributeSet);
      TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramAttributeSet, ATTRS);
      this.weight = localTypedArray.getFloat(0, 0.0F);
      localTypedArray.recycle();
    }
    
    public LayoutParams(ViewGroup.LayoutParams paramLayoutParams)
    {
      super();
    }
    
    public LayoutParams(ViewGroup.MarginLayoutParams paramMarginLayoutParams)
    {
      super();
    }
  }
  
  public static abstract interface PanelSlideListener {}
  
  static class SavedState
    extends View.BaseSavedState
  {
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator()
    {
      public SlidingPaneLayout.SavedState createFromParcel(Parcel paramAnonymousParcel)
      {
        return new SlidingPaneLayout.SavedState(paramAnonymousParcel, null);
      }
      
      public SlidingPaneLayout.SavedState[] newArray(int paramAnonymousInt)
      {
        return new SlidingPaneLayout.SavedState[paramAnonymousInt];
      }
    };
    boolean isOpen;
    
    private SavedState(Parcel paramParcel)
    {
      super();
      if (paramParcel.readInt() != 0) {}
      for (boolean bool = true;; bool = false)
      {
        this.isOpen = bool;
        return;
      }
    }
    
    SavedState(Parcelable paramParcelable)
    {
      super();
    }
    
    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      if (this.isOpen) {}
      for (int i = 1;; i = 0)
      {
        paramParcel.writeInt(i);
        return;
      }
    }
  }
  
  static abstract interface SlidingPanelLayoutImpl
  {
    public abstract void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView);
  }
  
  static class SlidingPanelLayoutImplBase
    implements SlidingPaneLayout.SlidingPanelLayoutImpl
  {
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      ViewCompat.postInvalidateOnAnimation(paramSlidingPaneLayout, paramView.getLeft(), paramView.getTop(), paramView.getRight(), paramView.getBottom());
    }
  }
  
  static final class SlidingPanelLayoutImplJB
    extends SlidingPaneLayout.SlidingPanelLayoutImplBase
  {
    private Method mGetDisplayList;
    private Field mRecreateDisplayList;
    
    SlidingPanelLayoutImplJB()
    {
      try
      {
        this.mGetDisplayList = View.class.getDeclaredMethod("getDisplayList", null);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        for (;;)
        {
          try
          {
            this.mRecreateDisplayList = View.class.getDeclaredField("mRecreateDisplayList");
            this.mRecreateDisplayList.setAccessible(true);
            return;
          }
          catch (NoSuchFieldException localNoSuchFieldException)
          {
            Log.e("SlidingPaneLayout", "Couldn't fetch mRecreateDisplayList field; dimming will be slow.", localNoSuchFieldException);
          }
          localNoSuchMethodException = localNoSuchMethodException;
          Log.e("SlidingPaneLayout", "Couldn't fetch getDisplayList method; dimming won't work right.", localNoSuchMethodException);
        }
      }
    }
    
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      if ((this.mGetDisplayList != null) && (this.mRecreateDisplayList != null)) {
        try
        {
          this.mRecreateDisplayList.setBoolean(paramView, true);
          this.mGetDisplayList.invoke(paramView, null);
          super.invalidateChildRegion(paramSlidingPaneLayout, paramView);
          return;
        }
        catch (Exception localException)
        {
          for (;;)
          {
            Log.e("SlidingPaneLayout", "Error refreshing display list state", localException);
          }
        }
      }
      paramView.invalidate();
    }
  }
  
  static final class SlidingPanelLayoutImplJBMR1
    extends SlidingPaneLayout.SlidingPanelLayoutImplBase
  {
    public void invalidateChildRegion(SlidingPaneLayout paramSlidingPaneLayout, View paramView)
    {
      ViewCompat.setLayerPaint(paramView, ((SlidingPaneLayout.LayoutParams)paramView.getLayoutParams()).dimPaint);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     android.support.v4.widget.SlidingPaneLayout
 * JD-Core Version:    0.7.0.1
 */