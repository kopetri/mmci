package com.touchtype.keyboard.key.contents;

import android.content.Context;
import com.google.common.base.Objects;
import com.touchtype.keyboard.key.KeyFactory.KeyLoaderException;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextMetrics;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import java.util.Locale;
import java.util.Set;

public class ScaleLinkedTextContent
  extends TextContent
{
  private static final String TAG = ScaleLinkedTextContent.class.getSimpleName();
  private final TextMetrics mTextMetrics;
  
  private ScaleLinkedTextContent(String paramString1, String paramString2, Locale paramLocale, TextMetrics paramTextMetrics, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat)
  {
    super(paramString1, paramString2, paramLocale, paramHAlign, paramVAlign, paramFloat);
    this.mTextMetrics = paramTextMetrics;
  }
  
  public static ScaleLinkedTextContent create(String paramString1, String paramString2, Locale paramLocale, TextMetrics paramTextMetrics, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat)
  {
    if (paramTextMetrics != null)
    {
      paramTextMetrics.update(paramString1.toLowerCase(paramLocale));
      paramTextMetrics.update(paramString1.toUpperCase(paramLocale));
    }
    return new ScaleLinkedTextContent(paramString1, paramString2, paramLocale, paramTextMetrics, paramHAlign, paramVAlign, paramFloat);
  }
  
  public static KeyContent getDefaultBottomTextContent(Context paramContext, String paramString1, String paramString2, Locale paramLocale, TextMetrics paramTextMetrics)
    throws KeyFactory.KeyLoaderException
  {
    if (paramString1 == null) {
      paramString1 = paramString2;
    }
    try
    {
      TextRendering.HAlign localHAlign = TextRendering.HAlign.CENTRE;
      TextRendering.VAlign localVAlign = TextRendering.VAlign.BOTTOM;
      float f = getLetterKeyBottomTextScale(paramContext);
      ScaleLinkedTextContent localScaleLinkedTextContent = create(paramString1, paramString2, paramLocale, paramTextMetrics, localHAlign, localVAlign, f);
      return localScaleLinkedTextContent;
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new KeyFactory.KeyLoaderException(localNullPointerException);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public static KeyContent getDefaultMainTextContent(String paramString1, String paramString2, Locale paramLocale, float paramFloat, TextMetrics paramTextMetrics)
    throws KeyFactory.KeyLoaderException
  {
    if (paramString1 == null) {
      paramString1 = paramString2;
    }
    try
    {
      TextRendering.HAlign localHAlign = TextRendering.HAlign.CENTRE;
      TextRendering.VAlign localVAlign = TextRendering.VAlign.CENTRE;
      ScaleLinkedTextContent localScaleLinkedTextContent = create(paramString1, paramString2, paramLocale, paramTextMetrics, localHAlign, localVAlign, paramFloat);
      return localScaleLinkedTextContent;
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new KeyFactory.KeyLoaderException(localNullPointerException);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public static KeyContent getDefaultTopTextContent(String paramString, Locale paramLocale, TextMetrics paramTextMetrics)
    throws KeyFactory.KeyLoaderException
  {
    try
    {
      ScaleLinkedTextContent localScaleLinkedTextContent = create(paramString, paramString, paramLocale, paramTextMetrics, TextRendering.HAlign.RIGHT, TextRendering.VAlign.TOP, 1.0F);
      return localScaleLinkedTextContent;
    }
    catch (NullPointerException localNullPointerException)
    {
      throw new KeyFactory.KeyLoaderException(localNullPointerException);
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public ScaleLinkedTextContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    String str1;
    if (isShifted(paramShiftState))
    {
      str1 = getLabel().toUpperCase(this.mLocale);
      if (!isShifted(paramShiftState)) {
        break label83;
      }
    }
    label83:
    for (String str2 = getText().toUpperCase(this.mLocale);; str2 = getText().toLowerCase(this.mLocale))
    {
      return new ScaleLinkedTextContent(str1, str2, this.mLocale, this.mTextMetrics, this.mHAlign, this.mVAlign, getHeightLimit());
      str1 = getLabel().toLowerCase(this.mLocale);
      break;
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof ScaleLinkedTextContent));
      if (paramObject == this) {
        return true;
      }
    } while ((!super.equals(paramObject)) || (!this.mTextMetrics.equals(((ScaleLinkedTextContent)paramObject).mTextMetrics)));
    return true;
  }
  
  public Set<String> getLinkSet()
  {
    if (this.mTextMetrics == null) {
      return null;
    }
    return this.mTextMetrics.getLinkSet();
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(super.hashCode());
    arrayOfObject[1] = this.mTextMetrics;
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public String toString()
  {
    return "{Text: " + getText() + ", Label: " + getLabel() + "}";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.ScaleLinkedTextContent
 * JD-Core Version:    0.7.0.1
 */