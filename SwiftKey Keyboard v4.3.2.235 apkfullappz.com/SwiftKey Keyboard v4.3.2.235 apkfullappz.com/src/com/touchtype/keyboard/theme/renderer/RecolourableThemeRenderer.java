package com.touchtype.keyboard.theme.renderer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.contents.IconContent;
import com.touchtype.keyboard.key.contents.LSSBContent;
import com.touchtype.keyboard.keys.view.DualContentKeyDrawable;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.keys.view.SpacebarLanguageDrawable;
import com.touchtype.keyboard.theme.KeyStyle;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.util.ColorFilterContainer;
import java.util.Map;

public class RecolourableThemeRenderer
  extends DefaultThemeRenderer
{
  public RecolourableThemeRenderer(Map<KeyStyle.StyleId, KeyStyle> paramMap, Map<KeyIcon, Drawable> paramMap1, Context paramContext)
  {
    super(paramMap, paramMap1, paramContext);
  }
  
  protected Drawable getBackgroundDrawable(KeyState paramKeyState, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = super.getBackgroundDrawable(paramKeyState, paramKeyArea, paramStyleId);
    localDrawable.setColorFilter(getStyle(paramStyleId).mBackgroundColorFilter.getColorFilter(localDrawable.getState()));
    return localDrawable;
  }
  
  public ResizeDrawable getContentDrawable(IconContent paramIconContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    ResizeDrawable localResizeDrawable = super.getContentDrawable(paramIconContent, paramStyleId, paramSubStyle);
    localResizeDrawable.setColorFilter(getStyle(paramStyleId).getColorFilterContainer(paramSubStyle).getColorFilter(localResizeDrawable.getState()));
    return localResizeDrawable;
  }
  
  public ResizeDrawable getContentDrawable(LSSBContent paramLSSBContent, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    SpacebarLanguageDrawable localSpacebarLanguageDrawable = (SpacebarLanguageDrawable)super.getContentDrawable(paramLSSBContent, paramStyleId, paramSubStyle);
    KeyStyle localKeyStyle = getStyle(paramStyleId);
    localSpacebarLanguageDrawable.setColorFilter(localKeyStyle.getColorFilterContainer(paramSubStyle).getColorFilter(localSpacebarLanguageDrawable.getState()));
    localSpacebarLanguageDrawable.setTextColor(localKeyStyle.mColor);
    return localSpacebarLanguageDrawable;
  }
  
  protected ResizeDrawable getDualContentKeyDrawable(ResizeDrawable paramResizeDrawable1, ResizeDrawable paramResizeDrawable2, float paramFloat, KeyStyle.StyleId paramStyleId)
  {
    KeyStyle localKeyStyle = getStyle(paramStyleId);
    paramResizeDrawable1.setColorFilter(localKeyStyle.getColorFilterContainer(KeyStyle.SubStyle.TOP).getColorFilter(paramResizeDrawable1.getState()));
    paramResizeDrawable2.setColorFilter(localKeyStyle.getColorFilterContainer(KeyStyle.SubStyle.BOTTOM).getColorFilter(paramResizeDrawable2.getState()));
    return new DualContentKeyDrawable(paramResizeDrawable1, paramResizeDrawable2, paramFloat);
  }
  
  protected Drawable getMiniKeyboardBackground(KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = super.getMiniKeyboardBackground(paramStyleId);
    localDrawable.setColorFilter(getStyle(paramStyleId).mMiniBackgroundColorFilter.getColorFilter(localDrawable.getState()));
    return localDrawable;
  }
  
  protected Drawable getPopupBackground(KeyStyle.StyleId paramStyleId)
  {
    Drawable localDrawable = super.getPopupBackground(paramStyleId);
    localDrawable.setColorFilter(getStyle(paramStyleId).mPopupColorFilter.getColorFilter(localDrawable.getState()));
    return localDrawable;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.renderer.RecolourableThemeRenderer
 * JD-Core Version:    0.7.0.1
 */