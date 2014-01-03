package com.touchtype.keyboard.theme.renderer;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ScalingInsetLayerDrawable.Inset;
import com.touchtype.keyboard.theme.KeyStyle;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.util.ColorFilterContainer;
import java.util.Map;

public final class RecolourableSeamlessThemeRenderer
  extends RecolourableThemeRenderer
{
  public RecolourableSeamlessThemeRenderer(Map<KeyStyle.StyleId, KeyStyle> paramMap, Map<KeyIcon, Drawable> paramMap1, Context paramContext)
  {
    super(paramMap, paramMap1, paramContext);
  }
  
  protected Drawable getBackgroundDrawable(KeyState paramKeyState, KeyArea paramKeyArea, KeyStyle.StyleId paramStyleId)
  {
    KeyStyle localKeyStyle = getStyle(paramStyleId);
    if ((0x1 & paramKeyArea.mEdgeFlags) == 1) {}
    for (Drawable localDrawable = localKeyStyle.mSeamlessBackground;; localDrawable = localKeyStyle.mBackground)
    {
      localDrawable.setState(paramKeyState.getPressedDrawableState());
      localDrawable.setColorFilter(getStyle(paramStyleId).mBackgroundColorFilter.getColorFilter(localDrawable.getState()));
      return localDrawable;
    }
  }
  
  protected ScalingInsetLayerDrawable.Inset getKeyInset(KeyArea paramKeyArea)
  {
    return new ScalingInsetLayerDrawable.Inset();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.renderer.RecolourableSeamlessThemeRenderer
 * JD-Core Version:    0.7.0.1
 */