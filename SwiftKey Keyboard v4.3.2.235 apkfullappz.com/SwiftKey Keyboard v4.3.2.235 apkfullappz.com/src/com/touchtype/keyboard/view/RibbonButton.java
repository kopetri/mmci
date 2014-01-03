package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyStateImpl;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.util.LogUtil;
import java.util.Locale;

public class RibbonButton
  extends Button
{
  private static final String TAG = RibbonButton.class.getSimpleName();
  private final KeyArea mArea = new KeyArea(new RectF(), 0);
  private final Rect mBounds = new Rect();
  private final KeyContent mContent;
  private final KeyState mState = new KeyStateImpl();
  private final KeyStyle.StyleId mStyle = KeyStyle.StyleId.CANDIDATE;
  
  public RibbonButton(Context paramContext, View.OnClickListener paramOnClickListener, String paramString)
  {
    super(paramContext, null);
    this.mContent = TextContent.getDefaultMainTextContent(paramString, paramString, Locale.UK);
    setBackgroundDrawable(ThemeManager.getInstance(paramContext).getThemeHandler().getProperties().getCandidateBackground());
    setOnClickListener(paramOnClickListener);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    if ((this.mArea.getBounds().width() != 0.0F) && (this.mArea.getBounds().height() != 0.0F))
    {
      Drawable localDrawable = ThemeManager.getInstance(getContext()).getThemeHandler().getRenderer().getKeyDrawable(this.mContent, this.mState, this.mArea, this.mStyle);
      localDrawable.setBounds(this.mBounds);
      localDrawable.draw(paramCanvas);
      return;
    }
    LogUtil.e(TAG, "Trying to draw a ribbon button with no area");
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mArea.getBounds().set(0.0F, 0.0F, paramInt1, paramInt2);
    this.mBounds.set(0, 0, paramInt1, paramInt2);
    if (paramInt2 != paramInt4) {
      setTextSize(0, 0.5F * paramInt2);
    }
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
 * Qualified Name:     com.touchtype.keyboard.view.RibbonButton
 * JD-Core Version:    0.7.0.1
 */