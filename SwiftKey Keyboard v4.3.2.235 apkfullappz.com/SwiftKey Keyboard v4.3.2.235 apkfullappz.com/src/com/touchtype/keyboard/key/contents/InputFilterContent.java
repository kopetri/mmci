package com.touchtype.keyboard.key.contents;

import android.graphics.Typeface;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import java.util.Locale;

public final class InputFilterContent
  extends TextContent
{
  public InputFilterContent(String paramString1, String paramString2, Locale paramLocale, Typeface paramTypeface, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat)
  {
    super(paramString1, paramString2, paramLocale, paramHAlign, paramVAlign, paramFloat);
  }
  
  public static InputFilterContent getDefaultInputFilterContent(String paramString1, String paramString2, Locale paramLocale, Typeface paramTypeface, int paramInt)
  {
    return new InputFilterContent(paramString1, paramString2, paramLocale, paramTypeface, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.5F);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    String str = paramKeyState.getInputFilter();
    if (str != null) {
      return getDefaultMainTextContent(str, str, this.mLocale);
    }
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    while (!(paramObject instanceof InputFilterContent)) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    return super.equals(paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.InputFilterContent
 * JD-Core Version:    0.7.0.1
 */