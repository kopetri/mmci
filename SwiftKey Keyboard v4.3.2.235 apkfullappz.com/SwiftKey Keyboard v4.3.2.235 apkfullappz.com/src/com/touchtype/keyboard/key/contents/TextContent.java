package com.touchtype.keyboard.key.contents;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.text.TextPaint;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import com.touchtype.util.LogUtil;
import java.util.Locale;
import java.util.Set;

public class TextContent
  implements KeyContent
{
  private static final String TAG = TextContent.class.getSimpleName();
  public final TextRendering.HAlign mHAlign;
  private final float mHeightLimit;
  private final String mLabel;
  protected final Locale mLocale;
  private final String mText;
  protected Typeface mTypeface = null;
  public final TextRendering.VAlign mVAlign;
  
  public TextContent(String paramString1, String paramString2, Locale paramLocale, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat)
  {
    if ((paramString1 == null) || (paramString2 == null)) {
      throw new IllegalArgumentException("Could not construct TextContent: label and text must be non-null");
    }
    this.mLabel = paramString1;
    this.mText = paramString2;
    this.mHAlign = paramHAlign;
    this.mVAlign = paramVAlign;
    this.mHeightLimit = paramFloat;
    this.mLocale = paramLocale;
  }
  
  public static KeyContent getDefaultBottomTextContent(String paramString1, String paramString2, Locale paramLocale, float paramFloat)
  {
    if (paramString1 == null) {
      paramString1 = paramString2;
    }
    try
    {
      TextRendering.HAlign localHAlign = TextRendering.HAlign.CENTRE;
      TextRendering.VAlign localVAlign = TextRendering.VAlign.BOTTOM;
      TextContent localTextContent = new TextContent(paramString1, paramString2, paramLocale, localHAlign, localVAlign, paramFloat);
      return localTextContent;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public static KeyContent getDefaultMainTextContent(String paramString1, String paramString2, Locale paramLocale)
  {
    return getDefaultMainTextContent(paramString1, paramString2, paramLocale, 0.5F);
  }
  
  public static KeyContent getDefaultMainTextContent(String paramString1, String paramString2, Locale paramLocale, float paramFloat)
  {
    if (paramString1 == null) {
      paramString1 = paramString2;
    }
    try
    {
      TextRendering.HAlign localHAlign = TextRendering.HAlign.CENTRE;
      TextRendering.VAlign localVAlign = TextRendering.VAlign.CENTRE;
      TextContent localTextContent = new TextContent(paramString1, paramString2, paramLocale, localHAlign, localVAlign, paramFloat);
      return localTextContent;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public static KeyContent getDefaultTopTextContent(String paramString, Locale paramLocale, float paramFloat)
  {
    try
    {
      TextContent localTextContent = new TextContent(paramString, paramString, paramLocale, TextRendering.HAlign.RIGHT, TextRendering.VAlign.TOP, paramFloat);
      return localTextContent;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return new EmptyContent();
  }
  
  public static float getLetterKeyBottomTextScale(Context paramContext)
  {
    try
    {
      float f = Float.valueOf(paramContext.getResources().getString(2131296313)).floatValue();
      return f;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      LogUtil.e(TAG, "Invalid keyscale stored. Using default letter key bottom text scale", localNumberFormatException);
    }
    return 0.8F;
  }
  
  public static float getLetterKeyMainTextHeight(Context paramContext)
  {
    try
    {
      float f = Float.valueOf(paramContext.getResources().getString(2131296314)).floatValue();
      return f;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      LogUtil.e(TAG, "Invalid height stored. Using default letter key main text height", localNumberFormatException);
    }
    return 0.5F;
  }
  
  protected static boolean isShifted(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    return (paramShiftState == TouchTypeSoftKeyboard.ShiftState.SHIFTED) || (paramShiftState == TouchTypeSoftKeyboard.ShiftState.CAPSLOCKED);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    return this;
  }
  
  public TextContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    String str1;
    if (isShifted(paramShiftState))
    {
      str1 = this.mLabel.toUpperCase(this.mLocale);
      if (!isShifted(paramShiftState)) {
        break label79;
      }
    }
    label79:
    for (String str2 = this.mText.toUpperCase(this.mLocale);; str2 = this.mText.toLowerCase(this.mLocale))
    {
      return new TextContent(str1, str2, this.mLocale, this.mHAlign, this.mVAlign, this.mHeightLimit);
      str1 = this.mLabel.toLowerCase(this.mLocale);
      break;
    }
  }
  
  public TextPaint applyTextStyle(TextPaint paramTextPaint)
  {
    if (this.mTypeface == null) {
      return paramTextPaint;
    }
    TextPaint localTextPaint = new TextPaint();
    localTextPaint.set(paramTextPaint);
    localTextPaint.setTypeface(this.mTypeface);
    return localTextPaint;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    TextContent localTextContent;
    do
    {
      do
      {
        do
        {
          return false;
        } while (!(paramObject instanceof TextContent));
        if (paramObject == this) {
          return true;
        }
        localTextContent = (TextContent)paramObject;
      } while ((!this.mLabel.equals(localTextContent.mLabel)) || (!this.mText.equals(localTextContent.mText)) || (this.mHeightLimit != localTextContent.mHeightLimit) || (!this.mHAlign.equals(localTextContent.mHAlign)) || (!this.mVAlign.equals(localTextContent.mVAlign)) || (!this.mLocale.equals(localTextContent.mLocale)));
      if (this.mTypeface != null) {
        break;
      }
    } while (localTextContent.mTypeface != null);
    for (;;)
    {
      return true;
      if (!this.mTypeface.equals(localTextContent.mTypeface)) {
        break;
      }
    }
  }
  
  public float getHeightLimit()
  {
    return this.mHeightLimit;
  }
  
  public Set<String> getInputStrings()
  {
    String[] arrayOfString = new String[2];
    arrayOfString[0] = getText().toLowerCase(this.mLocale);
    arrayOfString[1] = getText().toUpperCase(this.mLocale);
    return Sets.newHashSet(arrayOfString);
  }
  
  public String getLabel()
  {
    return this.mLabel;
  }
  
  public String getText()
  {
    return this.mText;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[7];
    arrayOfObject[0] = this.mLabel;
    arrayOfObject[1] = this.mText;
    arrayOfObject[2] = Float.valueOf(this.mHeightLimit);
    arrayOfObject[3] = this.mHAlign;
    arrayOfObject[4] = this.mVAlign;
    arrayOfObject[5] = this.mLocale;
    arrayOfObject[6] = this.mTypeface;
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public void setTypeface(Typeface paramTypeface)
  {
    this.mTypeface = paramTypeface;
  }
  
  public String toString()
  {
    return TAG + " - {Text: " + getText() + ", Label: " + getLabel() + "}";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.TextContent
 * JD-Core Version:    0.7.0.1
 */