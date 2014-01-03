package com.touchtype.keyboard.theme.painter;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.touchtype.keyboard.keys.view.MainTextKeyDrawable;
import com.touchtype.keyboard.theme.util.RectUtil;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.keyboard.theme.util.TextSizeLimiter;
import com.touchtype.keyboard.view.PreviewPopup;

public final class TextPainter
  extends PreviewPopupPainter
{
  private final String mText;
  private final TextPaint mTextPaint;
  
  public TextPainter(RectF paramRectF, Drawable paramDrawable, int paramInt, String paramString, TextPaint paramTextPaint)
  {
    super(paramRectF, paramDrawable, paramInt);
    this.mText = paramString;
    this.mTextPaint = paramTextPaint;
  }
  
  public boolean paint(PreviewPopup paramPreviewPopup)
  {
    if (!super.paint(paramPreviewPopup)) {
      return false;
    }
    Context localContext = paramPreviewPopup.getContext();
    ImageView localImageView = new ImageView(localContext);
    localImageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
    MainTextKeyDrawable localMainTextKeyDrawable = new MainTextKeyDrawable(this.mText, this.mTextPaint, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.7F, new TextSizeLimiter(localContext));
    Rect localRect = RectUtil.shrinkToPad(paramPreviewPopup.getBounds(), getPadding());
    localImageView.setScaleType(ImageView.ScaleType.FIT_XY);
    localImageView.setImageDrawable(localMainTextKeyDrawable);
    localMainTextKeyDrawable.setBounds(new Rect(0, 0, localRect.width(), localRect.height()));
    paramPreviewPopup.setContent(localImageView);
    return true;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.painter.TextPainter
 * JD-Core Version:    0.7.0.1
 */