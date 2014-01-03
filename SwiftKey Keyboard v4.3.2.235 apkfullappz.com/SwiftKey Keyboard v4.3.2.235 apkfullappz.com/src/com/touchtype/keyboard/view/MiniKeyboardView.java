package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.RectF;
import android.widget.FrameLayout.LayoutParams;
import com.touchtype.keyboard.MiniKeyboard;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.view.touch.MiniKeyboardClosedCallback;
import com.touchtype.keyboard.view.touch.PopupTouchHandler;
import com.touchtype.keyboard.view.touch.TouchHandler;

public final class MiniKeyboardView
  extends BaseKeyboardView<MiniKeyboard>
  implements MiniKeyboardClosedCallback
{
  private final RectF mAvailableRect = new RectF(0.0F, 0.0F, 1.0F, 1.0F);
  private final RectF mDisplayRect = new RectF();
  private boolean mInvalidDRect = true;
  private final MiniKeyboard mKeyboard;
  private final PointF mOwningCentre;
  private final Key mOwningKey;
  private final MainKeyboardView mParent;
  private int mPointerId;
  
  public MiniKeyboardView(Context paramContext, MiniKeyboard paramMiniKeyboard, Key paramKey, MainKeyboardView paramMainKeyboardView)
  {
    super(paramContext, paramMiniKeyboard);
    this.mKeyboard = paramMiniKeyboard;
    this.mParent = paramMainKeyboardView;
    this.mOwningKey = paramKey;
    setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
    this.mOwningCentre = new PointF(this.mOwningKey.getArea().getDrawBounds().centerX(), this.mOwningKey.getArea().getDrawBounds().centerY());
  }
  
  public void closing()
  {
    super.closing();
    this.mParent.reattachTouchEvents(this.mPointerId);
  }
  
  public RectF getDisplayRect()
  {
    if (this.mInvalidDRect)
    {
      this.mDisplayRect.set(this.mKeyboard.fitAtMost(this.mAvailableRect, this.mOwningCentre));
      this.mInvalidDRect = false;
    }
    return this.mDisplayRect;
  }
  
  protected void onAttachedToWindow()
  {
    super.onAttachedToWindow();
    this.mPointerId = this.mParent.delegateTouchEventsTo(this, this.mOwningKey);
    if (this.mPointerId == -1) {
      closing();
    }
    Key localKey = this.mKeyboard.getInitialKey();
    this.mTouchHandler.setInitialTouch(this.mMatrix, this.mPointerId, localKey);
  }
  
  public void onClosed()
  {
    this.mOwningKey.cancel();
    this.mParent.reattachTouchEvents(this.mPointerId);
  }
  
  protected TouchHandler onCreateTouchHandler(Context paramContext, Key paramKey)
  {
    return new PopupTouchHandler(paramContext, paramKey, this);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    this.mInvalidDRect = true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.MiniKeyboardView
 * JD-Core Version:    0.7.0.1
 */