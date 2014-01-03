package com.touchtype.keyboard.keys.view;

import android.graphics.Canvas;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextPaint;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.TextRenderInfo;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.theme.util.TextSizeLimiter;

public final class MainTextKeyDrawableLegacy
  extends MainTextKeyDrawable
{
  public MainTextKeyDrawableLegacy(String paramString, TextPaint paramTextPaint, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, TextSizeLimiter paramTextSizeLimiter)
  {
    super(paramString, paramTextPaint, paramHAlign, paramVAlign, paramFloat, null, paramTextSizeLimiter);
  }
  
  public void draw(Canvas paramCanvas)
  {
    if (this.mTextRenderInfo == null) {
      return;
    }
    paramCanvas.save();
    paramCanvas.clipRect(getBounds());
    this.mTextPaint.setTextSize(this.mTextRenderInfo.size);
    StaticLayout localStaticLayout = new StaticLayout(this.mText, this.mTextPaint, paramCanvas.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
    paramCanvas.translate(this.mTextRenderInfo.x, this.mTextRenderInfo.y - localStaticLayout.getLineBaseline(0));
    localStaticLayout.draw(paramCanvas);
    paramCanvas.restore();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.keys.view.MainTextKeyDrawableLegacy
 * JD-Core Version:    0.7.0.1
 */