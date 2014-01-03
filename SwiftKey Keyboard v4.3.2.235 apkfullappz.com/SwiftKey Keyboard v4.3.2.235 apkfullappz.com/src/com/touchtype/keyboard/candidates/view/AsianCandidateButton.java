package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.Button;
import com.touchtype.keyboard.candidates.Candidate;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyStateImpl;
import com.touchtype.keyboard.key.contents.CandidateContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;

public class AsianCandidateButton
  extends Button
{
  protected final KeyArea mArea = new KeyArea(new RectF(), 0);
  protected final Rect mBounds = new Rect();
  protected final CandidateContent mContent = new CandidateContent();
  protected final KeyState mState = new KeyStateImpl();
  protected final KeyStyle.StyleId mStyle = KeyStyle.StyleId.CANDIDATE;
  
  public AsianCandidateButton(Context paramContext)
  {
    super(paramContext, null);
    init();
  }
  
  public AsianCandidateButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }
  
  private void init()
  {
    setMinimumWidth(getContext().getResources().getDimensionPixelSize(2131361842));
    setBackgroundDrawable(new ColorDrawable(0));
    setGravity(17);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    Drawable localDrawable = ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().getKeyDrawable(this.mContent, this.mState, this.mArea, this.mStyle);
    localDrawable.setBounds(this.mBounds);
    localDrawable.draw(paramCanvas);
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mArea.getBounds().set(0.0F, 0.0F, paramInt1, paramInt2);
    this.mBounds.set(0, 0, paramInt1, paramInt2);
    if (paramInt2 != paramInt4) {
      setTextSize(0, 0.5F * paramInt2);
    }
  }
  
  public void setCandidate(Candidate paramCandidate)
  {
    setText(paramCandidate.toString());
    this.mContent.updateCandidate(paramCandidate);
  }
  
  public void setPressed(boolean paramBoolean)
  {
    if (this.mState.getPressedState() != paramBoolean) {}
    for (int i = 1;; i = 0)
    {
      this.mState.setPressed(paramBoolean);
      super.setPressed(paramBoolean);
      if (i != 0) {
        invalidate();
      }
      return;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianCandidateButton
 * JD-Core Version:    0.7.0.1
 */