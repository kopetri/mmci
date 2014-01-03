package com.touchtype.keyboard.key.contents;

import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;

public final class ShiftIconContent
  extends IconContent
{
  public ShiftIconContent(KeyIcon paramKeyIcon)
  {
    super(paramKeyIcon, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.8F, false, true);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    return this;
  }
  
  public IconContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    if (paramShiftState == TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED)
    {
      this.mIconState = new int[] { 16842919 };
      return this;
    }
    this.mIconState = new int[] { -16842919 };
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    while (!(paramObject instanceof ShiftIconContent)) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    return super.equals(paramObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, KeyStyle.SubStyle.SECONDARY);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.ShiftIconContent
 * JD-Core Version:    0.7.0.1
 */