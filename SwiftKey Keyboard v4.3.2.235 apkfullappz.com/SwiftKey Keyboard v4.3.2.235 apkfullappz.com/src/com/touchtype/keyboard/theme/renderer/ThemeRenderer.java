package com.touchtype.keyboard.theme.renderer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.PopupContent;
import com.touchtype.keyboard.key.PopupContent.LSSBPopupContent;
import com.touchtype.keyboard.key.PopupContent.MiniKeyboardContent;
import com.touchtype.keyboard.key.PopupContent.PreviewContent;
import com.touchtype.keyboard.key.contents.CandidateContent;
import com.touchtype.keyboard.key.contents.DualKeyContent;
import com.touchtype.keyboard.key.contents.IconContent;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.key.contents.LSSBContent;
import com.touchtype.keyboard.key.contents.ScaleLinkedTextContent;
import com.touchtype.keyboard.key.contents.TextContent;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.theme.KeyStyle;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.painter.PreviewPopupPainter;
import com.touchtype.keyboard.theme.util.TextRendering;
import java.util.Map;

public abstract class ThemeRenderer
{
  protected Context mContext;
  protected Map<KeyIcon, Drawable> mIcons;
  private Map<KeyStyle.StyleId, KeyStyle> mStyles;
  
  public ThemeRenderer(Map<KeyStyle.StyleId, KeyStyle> paramMap, Map<KeyIcon, Drawable> paramMap1, Context paramContext)
  {
    this.mStyles = paramMap;
    this.mIcons = paramMap1;
    this.mContext = paramContext;
  }
  
  public ResizeDrawable getContentDrawable(CandidateContent paramCandidateContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramCandidateContent, paramStyleId, paramSubStyle);
  }
  
  public ResizeDrawable getContentDrawable(DualKeyContent paramDualKeyContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramDualKeyContent, paramStyleId, paramSubStyle);
  }
  
  public ResizeDrawable getContentDrawable(IconContent paramIconContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramIconContent, paramStyleId, paramSubStyle);
  }
  
  public abstract ResizeDrawable getContentDrawable(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle);
  
  public ResizeDrawable getContentDrawable(LSSBContent paramLSSBContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramLSSBContent, paramStyleId, paramSubStyle);
  }
  
  public ResizeDrawable getContentDrawable(ScaleLinkedTextContent paramScaleLinkedTextContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramScaleLinkedTextContent, paramStyleId, paramSubStyle);
  }
  
  public ResizeDrawable getContentDrawable(TextContent paramTextContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return getContentDrawable(paramTextContent, paramStyleId, paramSubStyle);
  }
  
  public abstract Drawable getKeyDrawable(KeyContent paramKeyContent, KeyState paramKeyState, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId);
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.LSSBPopupContent paramLSSBPopupContent, KeyStyle.StyleId paramStyleId)
  {
    return getPopupPainter(paramKeyArea, paramLSSBPopupContent, paramStyleId);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.MiniKeyboardContent paramMiniKeyboardContent, KeyStyle.StyleId paramStyleId)
  {
    return getPopupPainter(paramKeyArea, paramMiniKeyboardContent, paramStyleId);
  }
  
  public PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent.PreviewContent paramPreviewContent, KeyStyle.StyleId paramStyleId)
  {
    return getPopupPainter(paramKeyArea, paramPreviewContent, paramStyleId);
  }
  
  public abstract PreviewPopupPainter getPopupPainter(KeyArea paramKeyArea, PopupContent paramPopupContent, KeyStyle.StyleId paramStyleId);
  
  protected KeyStyle getStyle(KeyStyle.StyleId paramStyleId)
  {
    return (KeyStyle)this.mStyles.get(paramStyleId);
  }
  
  public TextPaint getTextPaint(KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return ((KeyStyle)this.mStyles.get(paramStyleId)).getTextPaint(paramSubStyle);
  }
  
  public void reset() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.renderer.ThemeRenderer
 * JD-Core Version:    0.7.0.1
 */