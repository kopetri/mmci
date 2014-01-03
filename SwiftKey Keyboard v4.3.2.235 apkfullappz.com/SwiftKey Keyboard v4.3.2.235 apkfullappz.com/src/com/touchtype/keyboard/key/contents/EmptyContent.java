package com.touchtype.keyboard.key.contents;

import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.keys.view.ResizeDrawable.EmptyDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import java.util.HashSet;
import java.util.Set;

public class EmptyContent
  implements KeyContent
{
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    return this;
  }
  
  public KeyContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    while (!(paramObject instanceof EmptyContent)) {
      return false;
    }
    return paramObject == this;
  }
  
  public Set<String> getInputStrings()
  {
    return new HashSet();
  }
  
  public int hashCode()
  {
    return 13;
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return new ResizeDrawable.EmptyDrawable();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.EmptyContent
 * JD-Core Version:    0.7.0.1
 */