package com.touchtype.keyboard.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.touchtype.keyboard.BaseKeyboard;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.key.delegates.KeyDrawDelegate;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.DensityIndependentBitmapDrawableFactory;
import com.touchtype.keyboard.view.touch.TouchEvent;
import com.touchtype.keyboard.view.touch.TouchHandler;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.KeyHeightUtil;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public abstract class BaseKeyboardView<T extends BaseKeyboard<?>>
  extends FrameLayout
{
  private static final String TAG = BaseKeyboardView.class.getSimpleName();
  private Bitmap mBuffer;
  private Canvas mCanvas;
  private boolean mDrawPending;
  protected Set<KeyDrawDelegate> mInvalidatedKeys = new HashSet();
  private boolean mIsDrawnFromCache;
  protected final T mKeyboard;
  protected final List<KeyStateListener> mListeners = new ArrayList(40);
  protected final Matrix mMatrix = new Matrix();
  private Bitmap mOriginalBuffer;
  protected TouchHandler mTouchHandler;
  
  public BaseKeyboardView(Context paramContext, T paramT)
  {
    super(paramContext);
    this.mKeyboard = paramT;
    this.mMatrix.reset();
    this.mTouchHandler = onCreateTouchHandler(paramContext, this.mKeyboard.mEmptyKey);
    setBackgroundDrawable(DensityIndependentBitmapDrawableFactory.create());
  }
  
  private KeyStateListener createListener(final Key paramKey)
  {
    KeyStateListener local1 = new KeyStateListener()
    {
      public void onKeyStateChanged(KeyState paramAnonymousKeyState)
      {
        BaseKeyboardView.this.invalidateKey(paramKey);
      }
    };
    this.mListeners.add(local1);
    return local1;
  }
  
  private Canvas getCanvas()
  {
    int i;
    if (this.mBuffer == null) {
      i = 0;
    }
    try
    {
      this.mBuffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
      if (i != 0)
      {
        TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(getContext());
        int j = localTouchTypePreferences.getInt("low_memory_warning", 0);
        if (j < 31)
        {
          if (j % 10 == 0)
          {
            Resources localResources = getResources();
            Object[] arrayOfObject = new Object[1];
            arrayOfObject[0] = getResources().getString(2131296303);
            String str = localResources.getString(2131297080, arrayOfObject);
            Toast.makeText(getContext(), str, 1).show();
          }
          localTouchTypePreferences.putInt("low_memory_warning", j + 1);
        }
      }
      invalidateAllKeys();
      this.mCanvas = new Canvas(this.mBuffer);
      return this.mCanvas;
    }
    catch (OutOfMemoryError localOutOfMemoryError1)
    {
      for (;;)
      {
        LogUtil.w(TAG, "OOM: Trying to draw keyboard on reduced RGB_565 palette");
        i = 1;
        try
        {
          this.mBuffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
        }
        catch (OutOfMemoryError localOutOfMemoryError2)
        {
          LogUtil.w(TAG, "OOM: Requesting GC as last resort before trying RGB_565 again");
          System.gc();
          try
          {
            this.mBuffer = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.RGB_565);
          }
          catch (OutOfMemoryError localOutOfMemoryError3)
          {
            LogUtil.e(TAG, "OOM: Not enough memory to draw keyboard");
            throw localOutOfMemoryError3;
          }
        }
      }
    }
  }
  
  private Rect getClip(KeyDrawDelegate paramKeyDrawDelegate)
  {
    Rect localRect = transformVirtualRect(paramKeyDrawDelegate.getArea().getBounds());
    localRect.offset(getPaddingLeft(), getPaddingTop());
    return localRect;
  }
  
  private Collection<? extends KeyDrawDelegate> getInvalidatedKeys()
  {
    if (this.mDrawPending)
    {
      this.mInvalidatedKeys.clear();
      return this.mKeyboard.getKeys();
    }
    return this.mInvalidatedKeys;
  }
  
  private Theme getThemeHandler()
  {
    return ThemeManager.getInstance(getContext()).getThemeHandler();
  }
  
  private void onBufferDraw()
  {
    if ((getWidth() <= 0) || (getHeight() <= 0) || (this.mKeyboard == null)) {
      throw new IllegalStateException();
    }
    Canvas localCanvas = getCanvas();
    localCanvas.save();
    Iterator localIterator = getInvalidatedKeys().iterator();
    while (localIterator.hasNext())
    {
      KeyDrawDelegate localKeyDrawDelegate = (KeyDrawDelegate)localIterator.next();
      localCanvas.clipRect(getClip(localKeyDrawDelegate), Region.Op.REPLACE);
      localCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
      Drawable localDrawable = localKeyDrawDelegate.getKeyDrawable(getThemeHandler());
      localDrawable.setBounds(transformVirtualRect(localKeyDrawDelegate.getArea().getBounds()));
      localDrawable.draw(localCanvas);
    }
    this.mInvalidatedKeys.clear();
    localCanvas.restore();
    this.mDrawPending = false;
  }
  
  public void closing()
  {
    if (this.mBuffer != null)
    {
      this.mBuffer.recycle();
      this.mBuffer = null;
    }
    if (this.mOriginalBuffer != null)
    {
      this.mOriginalBuffer.recycle();
      this.mOriginalBuffer = null;
    }
    this.mCanvas = null;
    this.mListeners.clear();
    ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().reset();
    this.mTouchHandler.closing();
  }
  
  public void dismissPopupWindow(boolean paramBoolean) {}
  
  protected Key getKeyFromTouchEvent(TouchEvent paramTouchEvent, int paramInt)
  {
    float f1 = paramTouchEvent.getX(paramInt);
    float f2 = paramTouchEvent.getY(paramInt);
    return this.mKeyboard.getKeyAt(f1, f2);
  }
  
  public T getKeyboard()
  {
    return this.mKeyboard;
  }
  
  public int getPreferredHeight()
  {
    return (int)(this.mKeyboard.getTotalRowWeight() * KeyHeightUtil.getCurrentKeyHeight(getContext()));
  }
  
  public boolean handleBack()
  {
    dismissPopupWindow(false);
    return false;
  }
  
  protected boolean innerTouchEvent(TouchEvent paramTouchEvent)
  {
    for (int i = 0; i < paramTouchEvent.getPointerCount(); i++) {
      this.mTouchHandler.handleTouchEvent(paramTouchEvent, i, getKeyFromTouchEvent(paramTouchEvent, i));
    }
    return true;
  }
  
  public void invalidateAllKeys()
  {
    this.mInvalidatedKeys.clear();
    this.mDrawPending = true;
    invalidate();
  }
  
  public boolean invalidateKey(KeyDrawDelegate paramKeyDrawDelegate)
  {
    this.mInvalidatedKeys.add(paramKeyDrawDelegate);
    invalidate(getClip(paramKeyDrawDelegate));
    return true;
  }
  
  protected void onAttachedToWindow()
  {
    Iterator localIterator = this.mKeyboard.getKeys().iterator();
    while (localIterator.hasNext())
    {
      Key localKey = (Key)localIterator.next();
      localKey.getState().addListener(KeyState.StateType.REDRAW, createListener(localKey));
    }
  }
  
  protected TouchHandler onCreateTouchHandler(Context paramContext, Key paramKey)
  {
    return new TouchHandler(paramContext, paramKey);
  }
  
  public void onDetachedFromWindow()
  {
    closing();
    super.onDetachedFromWindow();
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    if ((this.mIsDrawnFromCache) && (this.mBuffer != null))
    {
      paramCanvas.drawBitmap(this.mBuffer, 0.0F, 0.0F, null);
      return;
    }
    if ((this.mBuffer == null) || (this.mDrawPending) || (!this.mInvalidatedKeys.isEmpty())) {
      onBufferDraw();
    }
    paramCanvas.drawBitmap(this.mBuffer, 0.0F, 0.0F, null);
    this.mOriginalBuffer = this.mBuffer;
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), getPreferredHeight());
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    if ((!this.mIsDrawnFromCache) || (this.mOriginalBuffer == null)) {}
    for (this.mBuffer = null;; this.mBuffer = Bitmap.createScaledBitmap(this.mOriginalBuffer, paramInt1, paramInt2, true))
    {
      updateTransformationMatrix(paramInt1, paramInt2);
      return;
    }
  }
  
  public final boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    return innerTouchEvent(TouchEvent.createTouchEvent(paramMotionEvent, this.mMatrix));
  }
  
  public void setCachedDrawing(boolean paramBoolean)
  {
    this.mIsDrawnFromCache = paramBoolean;
    this.mBuffer = null;
    invalidateAllKeys();
  }
  
  public Point transformVirtualPoint(PointF paramPointF)
  {
    return new Point(Math.round(paramPointF.x * getWidth()), Math.round(paramPointF.y * getHeight()));
  }
  
  public Rect transformVirtualRect(RectF paramRectF)
  {
    return new Rect(Math.round(paramRectF.left * getWidth()), Math.round(paramRectF.top * getHeight()), Math.round(paramRectF.right * getWidth()), Math.round(paramRectF.bottom * getHeight()));
  }
  
  public void updateTransformationMatrix(float paramFloat1, float paramFloat2)
  {
    this.mMatrix.setScale(1.0F / paramFloat1, 1.0F / paramFloat2);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.BaseKeyboardView
 * JD-Core Version:    0.7.0.1
 */