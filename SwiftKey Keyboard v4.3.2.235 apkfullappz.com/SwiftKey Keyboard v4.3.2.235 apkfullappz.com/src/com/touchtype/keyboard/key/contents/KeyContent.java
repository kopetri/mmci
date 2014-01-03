package com.touchtype.keyboard.key.contents;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import java.util.Set;

public abstract interface KeyContent
{
  public abstract KeyContent applyKeyState(KeyState paramKeyState);
  
  public abstract KeyContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState);
  
  public abstract Set<String> getInputStrings();
  
  public abstract ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.KeyContent
 * JD-Core Version:    0.7.0.1
 */