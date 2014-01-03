package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.inputmethod.CompletionInfo;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard;
import java.util.Arrays;

public class CompletionView
  extends View
{
  private Rect mBgPadding;
  private int mColorNormal;
  private CompletionInfo[] mCompletions;
  private int mCurrentWordIndex;
  private int mDescent;
  private Drawable mDivider;
  private GestureDetector mGestureDetector;
  Handler mHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      }
      do
      {
        return;
        CompletionView.this.mPreviewText.setVisibility(8);
        return;
        CompletionView.this.mPreviewText.setVisibility(8);
      } while (CompletionView.this.mTouchX == -1);
      CompletionView.this.removeHighlight();
    }
  };
  private Paint mPaint;
  private int mPopupPreviewX;
  private int mPopupPreviewY;
  private PopupWindow mPreviewPopup;
  private TextView mPreviewText;
  private boolean mScrolled;
  private int mSelectedIndex;
  private Drawable mSelectionHighlight;
  private TouchTypeSoftKeyboard mService;
  private int mTargetScrollX;
  private int mTotalWidth;
  private int mTouchX = -1;
  private int[] mWordWidth = new int[32];
  private int[] mWordX = new int[32];
  
  public CompletionView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    this.mSelectionHighlight = paramContext.getResources().getDrawable(2130838069);
    LayoutInflater localLayoutInflater = (LayoutInflater)paramContext.getSystemService("layout_inflater");
    this.mPreviewPopup = new PopupWindow(paramContext);
    this.mPreviewText = ((TextView)localLayoutInflater.inflate(2130903067, null));
    this.mPreviewPopup.setWindowLayoutMode(-2, -2);
    this.mPreviewPopup.setContentView(this.mPreviewText);
    this.mPreviewPopup.setBackgroundDrawable(null);
    this.mColorNormal = paramContext.getResources().getColor(2131165204);
    this.mDivider = paramContext.getResources().getDrawable(2130838058);
    this.mPaint = new Paint();
    this.mPaint.setColor(this.mColorNormal);
    this.mPaint.setAntiAlias(true);
    this.mPaint.setTextSize(this.mPreviewText.getTextSize());
    this.mPaint.setStrokeWidth(0.0F);
    this.mDescent = ((int)this.mPaint.descent());
    this.mGestureDetector = new GestureDetector(paramContext, new GestureDetector.SimpleOnGestureListener()
    {
      public boolean onScroll(MotionEvent paramAnonymousMotionEvent1, MotionEvent paramAnonymousMotionEvent2, float paramAnonymousFloat1, float paramAnonymousFloat2)
      {
        int i = CompletionView.this.getWidth();
        CompletionView.access$302(CompletionView.this, true);
        int j = CompletionView.this.getScrollX() + (int)paramAnonymousFloat1;
        if (j < 0) {
          j = 0;
        }
        if ((paramAnonymousFloat1 > 0.0F) && (j + i > CompletionView.this.mTotalWidth)) {
          j -= (int)paramAnonymousFloat1;
        }
        CompletionView.access$502(CompletionView.this, j);
        CompletionView.this.scrollTo(j, CompletionView.this.getScrollY());
        CompletionView.this.hidePreview();
        return true;
      }
    });
    setHorizontalFadingEdgeEnabled(true);
    setWillNotDraw(false);
    setHorizontalScrollBarEnabled(false);
    setVerticalScrollBarEnabled(false);
    scrollTo(0, 0);
  }
  
  private void acceptCompletion()
  {
    if (this.mSelectedIndex != -1)
    {
      this.mService.acceptCompletion(this.mCompletions[this.mSelectedIndex]);
      this.mService.onDisplayCompletions(null);
    }
    this.mSelectedIndex = -1;
  }
  
  private void hidePreview()
  {
    this.mCurrentWordIndex = -1;
    if (this.mPreviewPopup.isShowing()) {
      this.mHandler.sendMessageDelayed(this.mHandler.obtainMessage(1), 60L);
    }
  }
  
  private void removeHighlight()
  {
    this.mTouchX = -1;
    invalidate();
  }
  
  private void scrollToTarget()
  {
    int i = getScrollX();
    int j;
    if (this.mTargetScrollX > i)
    {
      j = i + 20;
      if (j >= this.mTargetScrollX)
      {
        j = this.mTargetScrollX;
        requestLayout();
      }
    }
    for (;;)
    {
      scrollTo(j, getScrollY());
      return;
      j = i - 20;
      if (j <= this.mTargetScrollX)
      {
        j = this.mTargetScrollX;
        requestLayout();
      }
    }
  }
  
  private void showPreview(int paramInt, String paramString)
  {
    int i = this.mCurrentWordIndex;
    this.mCurrentWordIndex = paramInt;
    if ((i != this.mCurrentWordIndex) || (paramString != null))
    {
      if (paramInt == -1) {
        hidePreview();
      }
    }
    else {
      return;
    }
    Object localObject;
    int j;
    int k;
    int[] arrayOfInt;
    if (paramString != null)
    {
      localObject = paramString;
      this.mPreviewText.setText((CharSequence)localObject);
      this.mPreviewText.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
      j = (int)(20.0F + this.mPaint.measureText((CharSequence)localObject, 0, ((CharSequence)localObject).length())) + this.mPreviewText.getPaddingLeft() + this.mPreviewText.getPaddingRight();
      k = this.mPreviewText.getMeasuredHeight();
      this.mPopupPreviewX = (this.mWordX[paramInt] - this.mPreviewText.getPaddingLeft() - getScrollX());
      this.mPopupPreviewY = (-k);
      this.mHandler.removeMessages(1);
      arrayOfInt = new int[2];
      getLocationInWindow(arrayOfInt);
      if (!this.mPreviewPopup.isShowing()) {
        break label220;
      }
      this.mPreviewPopup.update(this.mPopupPreviewX, this.mPopupPreviewY + arrayOfInt[1], j, k);
    }
    for (;;)
    {
      this.mPreviewText.setVisibility(0);
      return;
      localObject = this.mCompletions[paramInt].getText();
      break;
      label220:
      this.mPreviewPopup.setWidth(j);
      this.mPreviewPopup.setHeight(k);
      this.mPreviewPopup.showAtLocation(this, 0, this.mPopupPreviewX, this.mPopupPreviewY + arrayOfInt[1]);
    }
  }
  
  private void updateScrollPosition(int paramInt)
  {
    if (paramInt != getScrollX())
    {
      this.mTargetScrollX = paramInt;
      requestLayout();
      invalidate();
      this.mScrolled = true;
    }
  }
  
  public void clear()
  {
    this.mCompletions = null;
    this.mTouchX = -1;
    this.mSelectedIndex = -1;
    invalidate();
    Arrays.fill(this.mWordWidth, 0);
    Arrays.fill(this.mWordX, 0);
    if (this.mPreviewPopup.isShowing()) {
      this.mPreviewPopup.dismiss();
    }
  }
  
  public int computeHorizontalScrollRange()
  {
    return this.mTotalWidth;
  }
  
  public int getTargetScrollX()
  {
    return this.mTargetScrollX;
  }
  
  public void onDetachedFromWindow()
  {
    super.onDetachedFromWindow();
    hidePreview();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    if (paramCanvas != null) {
      super.onDraw(paramCanvas);
    }
    this.mTotalWidth = 0;
    if (this.mCompletions == null) {}
    label400:
    do
    {
      return;
      int i = getHeight();
      if (this.mBgPadding == null)
      {
        this.mBgPadding = new Rect(0, 0, 0, 0);
        if (getBackground() != null) {
          getBackground().getPadding(this.mBgPadding);
        }
        this.mDivider.setBounds(0, this.mBgPadding.top, this.mDivider.getIntrinsicWidth(), this.mDivider.getIntrinsicHeight());
      }
      int j = 0;
      int k = Math.min(this.mCompletions.length, 32);
      Rect localRect = this.mBgPadding;
      Paint localPaint = this.mPaint;
      int m = this.mTouchX;
      int n = getScrollX();
      boolean bool = this.mScrolled;
      int i1 = (int)(i + this.mPaint.getTextSize() - this.mDescent) / 2;
      int i2 = 0;
      if (i2 < k)
      {
        CompletionInfo localCompletionInfo = this.mCompletions[i2];
        CharSequence localCharSequence;
        int i3;
        if (localCompletionInfo != null)
        {
          localCharSequence = localCompletionInfo.getText();
          if ((localCharSequence != null) && (localCharSequence.length() != 0))
          {
            if (this.mWordWidth[i2] == 0) {
              break label400;
            }
            i3 = this.mWordWidth[i2];
          }
        }
        for (;;)
        {
          this.mWordX[i2] = j;
          if ((m + n >= j) && (m + n < j + i3) && (!bool) && (m != -1))
          {
            if (paramCanvas != null)
            {
              paramCanvas.translate(j, 0.0F);
              this.mSelectionHighlight.setBounds(0, localRect.top, i3, i);
              this.mSelectionHighlight.draw(paramCanvas);
              paramCanvas.translate(-j, 0.0F);
              showPreview(i2, null);
            }
            this.mSelectedIndex = i2;
          }
          if (paramCanvas != null)
          {
            paramCanvas.drawText(localCharSequence, 0, localCharSequence.length(), j + 10, i1, localPaint);
            paramCanvas.translate(j + i3, 0.0F);
            this.mDivider.draw(paramCanvas);
            paramCanvas.translate(-j - i3, 0.0F);
          }
          localPaint.setTypeface(Typeface.DEFAULT);
          j += i3;
          i2++;
          break;
          i3 = 20 + (int)localPaint.measureText(localCharSequence, 0, localCharSequence.length());
          this.mWordWidth[i2] = i3;
        }
      }
      this.mTotalWidth = j;
    } while (this.mTargetScrollX == getScrollX());
    scrollToTarget();
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    if (this.mGestureDetector.onTouchEvent(paramMotionEvent)) {
      return true;
    }
    int i = paramMotionEvent.getAction();
    int j = (int)paramMotionEvent.getX();
    int k = (int)paramMotionEvent.getY();
    this.mTouchX = j;
    switch (i)
    {
    default: 
      return true;
    case 0: 
      this.mScrolled = false;
      invalidate();
      return true;
    case 2: 
      if (k <= 0) {
        acceptCompletion();
      }
      invalidate();
      return true;
    }
    if (!this.mScrolled) {
      acceptCompletion();
    }
    this.mSelectedIndex = -1;
    removeHighlight();
    hidePreview();
    requestLayout();
    return true;
  }
  
  public void scrollNext()
  {
    int i = 0;
    int j = getScrollX();
    int k = this.mCompletions.length;
    int m = j + getWidth();
    for (;;)
    {
      if (i < k)
      {
        if ((this.mWordX[i] > m) || (this.mWordX[i] + this.mWordWidth[i] < m)) {
          break label118;
        }
        if ((this.mWordWidth[i] < getWidth()) || (this.mWordX[i] > j)) {
          break label107;
        }
      }
      label107:
      for (int n = j + getWidth();; n = this.mWordX[i])
      {
        j = Math.min(n, this.mTotalWidth - getWidth());
        updateScrollPosition(j);
        return;
      }
      label118:
      i++;
    }
  }
  
  public void scrollPrev()
  {
    int i = 0;
    int j = this.mCompletions.length;
    int k = getScrollX();
    int m = 0;
    if (i < j)
    {
      if ((this.mWordX[i] < k) && (this.mWordX[i] + this.mWordWidth[i] >= k - 1)) {
        m = i;
      }
    }
    else {
      if (this.mWordWidth[m] >= getWidth()) {
        break label110;
      }
    }
    label110:
    for (int n = this.mWordX[m] + this.mWordWidth[m] - getWidth();; n = k - getWidth())
    {
      if (n < 0) {
        n = 0;
      }
      updateScrollPosition(n);
      return;
      i++;
      break;
    }
  }
  
  public void setCompletions(CompletionInfo[] paramArrayOfCompletionInfo, TouchTypeSoftKeyboard paramTouchTypeSoftKeyboard)
  {
    clear();
    this.mCompletions = paramArrayOfCompletionInfo;
    this.mService = paramTouchTypeSoftKeyboard;
    scrollTo(0, 0);
    this.mTargetScrollX = 0;
    onDraw(null);
    invalidate();
    requestLayout();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.CompletionView
 * JD-Core Version:    0.7.0.1
 */