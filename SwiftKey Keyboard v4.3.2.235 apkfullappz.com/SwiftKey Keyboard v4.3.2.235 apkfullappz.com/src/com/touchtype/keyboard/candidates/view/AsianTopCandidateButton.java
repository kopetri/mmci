package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;

public class AsianTopCandidateButton
  extends AsianCandidateButton
{
  protected final KeyStyle.StyleId mStyle = KeyStyle.StyleId.TOPCANDIDATE;
  
  public AsianTopCandidateButton(Context paramContext)
  {
    super(paramContext);
  }
  
  public AsianTopCandidateButton(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    Drawable localDrawable = ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().getKeyDrawable(this.mContent, this.mState, this.mArea, this.mStyle);
    localDrawable.setBounds(this.mBounds);
    localDrawable.draw(paramCanvas);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.AsianTopCandidateButton
 * JD-Core Version:    0.7.0.1
 */