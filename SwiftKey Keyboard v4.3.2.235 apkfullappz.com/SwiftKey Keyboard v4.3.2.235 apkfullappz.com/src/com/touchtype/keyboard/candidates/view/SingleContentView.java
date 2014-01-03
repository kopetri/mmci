package com.touchtype.keyboard.candidates.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import com.touchtype.keyboard.key.contents.EmptyContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;

public class SingleContentView
  extends View
{
  private final Rect mBounds = new Rect();
  private final Context mContext;
  private KeyContent mKeyContent = new EmptyContent();
  protected KeyStyle.StyleId mStyleId = KeyStyle.StyleId.BASE;
  
  public SingleContentView(Context paramContext, ViewGroup.LayoutParams paramLayoutParams, Drawable paramDrawable)
  {
    super(paramContext);
    this.mContext = paramContext;
    setLayoutParams(paramLayoutParams);
    setBackgroundDrawable(paramDrawable);
  }
  
  public void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    ThemeRenderer localThemeRenderer = ThemeManager.getInstance(this.mContext).getThemeHandler().getRenderer();
    ResizeDrawable localResizeDrawable = this.mKeyContent.render(localThemeRenderer, this.mStyleId, KeyStyle.SubStyle.MAIN);
    localResizeDrawable.setBounds(this.mBounds);
    localResizeDrawable.draw(paramCanvas);
  }
  
  public void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mBounds.set(0, 0, paramInt1, paramInt2);
  }
  
  public void setContent(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId)
  {
    if ((this.mKeyContent.equals(paramKeyContent)) && (this.mStyleId == paramStyleId)) {
      return;
    }
    this.mKeyContent = paramKeyContent;
    this.mStyleId = paramStyleId;
    invalidate();
  }
  
  public void setStyleId(KeyStyle.StyleId paramStyleId)
  {
    this.mStyleId = paramStyleId;
    invalidate();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.candidates.view.SingleContentView
 * JD-Core Version:    0.7.0.1
 */