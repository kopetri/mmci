package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.MotionEvent;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyStateImpl;
import com.touchtype.keyboard.key.contents.EmptyContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.view.CandidateKeyboardViewUtils;

public final class CandidateButton
  extends Button
{
  private final KeyArea mArea = new KeyArea(new RectF(0.0F, 0.0F, 1.0F, 1.0F), 0);
  private final Rect mBounds = new Rect();
  private final Rect mCachedPadding = new Rect();
  private final EmptyContent mContent = new EmptyContent();
  private int mFullWidth = 0;
  private final boolean mIsCentral;
  private final int mMaximumTextSize = getContext().getResources().getDimensionPixelSize(2131361841);
  private final int mNumCandidates;
  private final KeyState mState = new KeyStateImpl();
  private KeyStyle.StyleId mStyleId = KeyStyle.StyleId.CANDIDATE;
  
  public CandidateButton(Context paramContext, boolean paramBoolean, int paramInt)
  {
    super(paramContext);
    this.mIsCentral = paramBoolean;
    this.mNumCandidates = paramInt;
    setEllipsize(null);
    setSingleLine(true);
    setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    setBackgroundDrawable(new ColorDrawable(0));
    setIncludeFontPadding(true);
    setGravity(17);
  }
  
  private void setPressedState(boolean paramBoolean)
  {
    if (this.mState.getPressedState() != paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      this.mState.setPressed(paramBoolean);
      if (i != 0) {
        invalidate();
      }
      return;
    }
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    Drawable localDrawable = ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().getKeyDrawable(this.mContent, this.mState, this.mArea, this.mStyleId);
    getDrawingRect(this.mBounds);
    localDrawable.getPadding(this.mCachedPadding);
    setPadding(this.mCachedPadding.left, this.mCachedPadding.top, this.mCachedPadding.right, this.mCachedPadding.bottom);
    localDrawable.setBounds(this.mBounds);
    localDrawable.draw(paramCanvas);
    super.onDraw(paramCanvas);
  }
  
  public void onMeasure(int paramInt1, int paramInt2)
  {
    float f1 = this.mFullWidth;
    if (this.mIsCentral) {}
    for (float f2 = CandidateKeyboardViewUtils.getCentralWidth(this.mNumCandidates);; f2 = CandidateKeyboardViewUtils.getSideWidth(this.mNumCandidates))
    {
      super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)(f2 * f1), 1073741824), paramInt2);
      return;
    }
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    super.onSizeChanged(paramInt1, paramInt2, paramInt3, paramInt4);
    CandidateTextUtils.setFontSize(this, getText(), this.mMaximumTextSize);
  }
  
  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    boolean bool = this.mState.getPressedState();
    if ((paramMotionEvent.getActionMasked() == 0) || (paramMotionEvent.getActionMasked() == 5)) {}
    for (bool = true;; bool = false) {
      do
      {
        setPressedState(bool);
        return super.onTouchEvent(paramMotionEvent);
      } while ((paramMotionEvent.getActionMasked() != 1) && (paramMotionEvent.getActionMasked() != 3) && (paramMotionEvent.getActionMasked() != 6));
    }
  }
  
  public void setCandidate(Candidate paramCandidate, KeyStyle.StyleId paramStyleId)
  {
    String str = paramCandidate.toString();
    if ((str != getText()) || (this.mStyleId != paramStyleId))
    {
      this.mStyleId = paramStyleId;
      setTag(paramCandidate);
      setText(str);
      CandidateTextUtils.setFontSize(this, getText(), this.mMaximumTextSize);
    }
  }
  
  public void setFullWidth(int paramInt)
  {
    this.mFullWidth = paramInt;
  }
  
  public void setTextPaintAttributes(ThemeRenderer paramThemeRenderer)
  {
    TextPaint localTextPaint = paramThemeRenderer.getTextPaint(this.mStyleId, KeyStyle.SubStyle.MAIN);
    setTypeface(localTextPaint.getTypeface());
    setTextColor(localTextPaint.getColor());
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.CandidateButton
 * JD-Core Version:    0.7.0.1
 */