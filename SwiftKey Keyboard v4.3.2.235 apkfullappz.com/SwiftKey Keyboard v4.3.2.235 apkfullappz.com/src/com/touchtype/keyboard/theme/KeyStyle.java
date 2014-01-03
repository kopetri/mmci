package com.touchtype.keyboard.theme;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.theme.util.ColorFilterContainer;
import com.touchtype.keyboard.theme.util.ColorFilterUtil;
import com.touchtype.keyboard.theme.util.DrawableLoader;
import com.touchtype.keyboard.theme.util.DrawableLoader.DrawableLoaderException;
import com.touchtype.keyboard.view.AppTypeFaceCache;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class KeyStyle
{
  public final Drawable mBackground;
  public final ColorFilterContainer mBackgroundColorFilter;
  public final ColorFilterContainer mBottomColorFilter;
  public final TextPaint mBottomTextPaint;
  public final int mCandidateTextShadowColor;
  public final float mCandidateTextShadowDx;
  public final float mCandidateTextShadowDy;
  public final float mCandidateTextShadowRadius;
  public final int mColor;
  public final ColorFilterContainer mColorFilter;
  public final StyleId mId;
  public final ColorFilterContainer mMiniBackgroundColorFilter;
  public final Drawable mMiniKeyboardBackground;
  public final Drawable mPopupBackground;
  public final ColorFilterContainer mPopupColorFilter;
  public final TextPaint mPopupTextPaint;
  public final Drawable mSeamlessBackground;
  public final ColorFilterContainer mSecondaryColorFilter;
  public final TextPaint mTextPaint;
  public final int mTextStyle;
  public final ColorFilterContainer mTopColorFilter;
  public final TextPaint mTopTextPaint;
  
  public KeyStyle(Context paramContext, StyleId paramStyleId, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.KeyStyle);
    this.mId = paramStyleId;
    this.mColor = localTypedArray.getColor(1, -1);
    this.mColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 2);
    this.mSecondaryColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 7);
    this.mBottomColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 8);
    this.mTopColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 6);
    this.mBackgroundColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 3);
    this.mPopupColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 4);
    this.mMiniBackgroundColorFilter = ColorFilterUtil.parseColorFilterContainer(localTypedArray, 5);
    this.mTextStyle = parseTextStyle(localTypedArray.getString(34), 0);
    Typeface localTypeface = AppTypeFaceCache.getFont(paramContext, localTypedArray.getString(33), Typeface.defaultFromStyle(this.mTextStyle));
    this.mTextPaint = new TextPaint();
    int i = localTypedArray.getColor(9, this.mColor);
    int j = localTypedArray.getColor(10, this.mColor);
    float f1 = localTypedArray.getFloat(11, 0.0F);
    float f2 = localTypedArray.getFloat(12, 0.0F);
    float f3 = localTypedArray.getFloat(13, 0.0F);
    this.mTextPaint.setColor(i);
    this.mTextPaint.setShadowLayer(f1, f2, f3, j);
    this.mTextPaint.setTypeface(localTypeface);
    this.mTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    this.mCandidateTextShadowRadius = f1;
    this.mCandidateTextShadowDx = f2;
    this.mCandidateTextShadowDy = f3;
    this.mCandidateTextShadowColor = j;
    this.mTopTextPaint = new TextPaint();
    int k = localTypedArray.getColor(14, i);
    int m = localTypedArray.getColor(15, j);
    float f4 = localTypedArray.getFloat(16, f1);
    float f5 = localTypedArray.getFloat(17, 0.0F);
    float f6 = localTypedArray.getFloat(18, 0.0F);
    this.mTopTextPaint.setColor(k);
    this.mTopTextPaint.setShadowLayer(f4, f5, f6, m);
    this.mTopTextPaint.setTypeface(localTypeface);
    this.mTopTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    this.mBottomTextPaint = new TextPaint();
    int n = localTypedArray.getColor(19, i);
    int i1 = localTypedArray.getColor(20, j);
    float f7 = localTypedArray.getFloat(21, f1);
    float f8 = localTypedArray.getFloat(22, 0.0F);
    float f9 = localTypedArray.getFloat(23, 0.0F);
    this.mBottomTextPaint.setColor(n);
    this.mBottomTextPaint.setShadowLayer(f7, f8, f9, i1);
    this.mBottomTextPaint.setTypeface(localTypeface);
    this.mBottomTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    if (!ProductConfiguration.isWatchBuild(paramContext)) {}
    for (this.mBackground = localTypedArray.getDrawable(24);; this.mBackground = new ColorDrawable(0))
    {
      this.mSeamlessBackground = localTypedArray.getDrawable(27);
      this.mMiniKeyboardBackground = localTypedArray.getDrawable(25);
      this.mPopupBackground = localTypedArray.getDrawable(26);
      this.mPopupTextPaint = new TextPaint();
      int i2 = localTypedArray.getColor(28, i);
      int i3 = localTypedArray.getColor(29, j);
      float f10 = localTypedArray.getFloat(30, f1);
      float f11 = localTypedArray.getFloat(31, 0.0F);
      float f12 = localTypedArray.getFloat(32, 0.0F);
      this.mPopupTextPaint.setColor(i2);
      this.mPopupTextPaint.setShadowLayer(f10, f11, f12, i3);
      this.mPopupTextPaint.setTypeface(localTypeface);
      this.mPopupTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
      localTypedArray.recycle();
      return;
    }
  }
  
  public KeyStyle(Resources paramResources, File paramFile, XmlPullParser paramXmlPullParser)
    throws XmlPullParserException, FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    String str1 = paramXmlPullParser.getNamespace();
    if (!verify(paramXmlPullParser))
    {
      String str2 = paramXmlPullParser.getAttributeValue(str1, "id");
      StringBuilder localStringBuilder = new StringBuilder("Invalid Style: ");
      if (str2 == null) {
        str2 = "Unknown";
      }
      String str3 = str2;
      XmlPullParserException localXmlPullParserException = new XmlPullParserException(str3);
      throw localXmlPullParserException;
    }
    this.mId = parseStyleId(paramResources, paramXmlPullParser.getAttributeValue(str1, "id"));
    this.mColor = Color.parseColor(paramXmlPullParser.getAttributeValue(str1, "color"));
    this.mColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "color_filter"));
    this.mSecondaryColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "secondary_color_filter"));
    this.mBottomColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "bottom_color_filter"));
    this.mTopColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "top_color_filter"));
    this.mBackgroundColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "background_color_filter"));
    this.mPopupColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "popup_color_filter"));
    this.mMiniBackgroundColorFilter = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str1, "minibackground_color_filter"));
    this.mTextStyle = parseTextStyle(paramXmlPullParser.getAttributeValue(str1, "textStyle"), 0);
    Typeface localTypeface = Typeface.defaultFromStyle(this.mTextStyle);
    this.mTextPaint = new TextPaint();
    int i = loadColor(paramXmlPullParser.getAttributeValue(str1, "text_color"), this.mColor);
    int j = loadColor(paramXmlPullParser.getAttributeValue(str1, "text_shadow_color"), this.mColor);
    float f1 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "text_shadow_radius"), 0.0F);
    float f2 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "text_shadow_dx"), 0.0F);
    float f3 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "text_shadow_dy"), 0.0F);
    this.mTextPaint.setColor(i);
    this.mTextPaint.setShadowLayer(f1, f2, f3, j);
    this.mTextPaint.setTypeface(localTypeface);
    this.mTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    this.mCandidateTextShadowRadius = f1;
    this.mCandidateTextShadowDx = f2;
    this.mCandidateTextShadowDy = f3;
    this.mCandidateTextShadowColor = j;
    this.mTopTextPaint = new TextPaint();
    int k = loadColor(paramXmlPullParser.getAttributeValue(str1, "top_text_color"), i);
    int m = loadColor(paramXmlPullParser.getAttributeValue(str1, "top_text_shadow_color"), j);
    float f4 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "top_text_shadow_radius"), f1);
    float f5 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "top_text_shadow_dx"), 0.0F);
    float f6 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "top_text_shadow_dy"), 0.0F);
    this.mTopTextPaint.setColor(k);
    this.mTopTextPaint.setShadowLayer(f4, f5, f6, m);
    this.mTopTextPaint.setTypeface(localTypeface);
    this.mTopTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    this.mBottomTextPaint = new TextPaint();
    int n = loadColor(paramXmlPullParser.getAttributeValue(str1, "bottom_text_color"), i);
    int i1 = loadColor(paramXmlPullParser.getAttributeValue(str1, "bottom_text_shadow_color"), j);
    float f7 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "bottom_text_shadow_radius"), f1);
    float f8 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "bottom_text_shadow_dx"), 0.0F);
    float f9 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "bottom_text_shadow_dy"), 0.0F);
    this.mBottomTextPaint.setColor(n);
    this.mBottomTextPaint.setShadowLayer(f7, f8, f9, i1);
    this.mBottomTextPaint.setTypeface(localTypeface);
    this.mBottomTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
    if (!paramResources.getBoolean(2131492901)) {}
    for (this.mBackground = DrawableLoader.loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str1, "background"));; this.mBackground = new ColorDrawable(0))
    {
      this.mSeamlessBackground = DrawableLoader.loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str1, "seamlessBackground"));
      this.mMiniKeyboardBackground = DrawableLoader.loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str1, "miniKeyboardBackground"));
      this.mPopupBackground = DrawableLoader.loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str1, "popupBackground"));
      this.mPopupTextPaint = new TextPaint();
      int i2 = loadColor(paramXmlPullParser.getAttributeValue(str1, "popupTextColor"), i);
      int i3 = loadColor(paramXmlPullParser.getAttributeValue(str1, "popup_text_shadow_color"), j);
      float f10 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "popup_text_shadow_radius"), f1);
      float f11 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "popup_text_shadow_dx"), 0.0F);
      float f12 = loadFloat(paramXmlPullParser.getAttributeValue(str1, "popup_text_shadow_dy"), 0.0F);
      this.mPopupTextPaint.setColor(i2);
      this.mPopupTextPaint.setShadowLayer(f10, f11, f12, i3);
      this.mPopupTextPaint.setTypeface(localTypeface);
      this.mPopupTextPaint.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
      return;
    }
  }
  
  public KeyStyle(Resources paramResources, File paramFile, XmlPullParser paramXmlPullParser, KeyStyle paramKeyStyle)
    throws XmlPullParserException
  {
    if (paramKeyStyle == null) {
      throw new XmlPullParserException("Unknown Parent Style");
    }
    String str = paramXmlPullParser.getNamespace();
    this.mId = parseStyleId(paramResources, paramXmlPullParser.getAttributeValue(str, "id"));
    this.mColor = loadColor(paramXmlPullParser.getAttributeValue(str, "color"), paramKeyStyle.mColor);
    ColorFilterContainer localColorFilterContainer1 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "color_filter"));
    if (localColorFilterContainer1 != null) {}
    for (;;)
    {
      this.mColorFilter = localColorFilterContainer1;
      ColorFilterContainer localColorFilterContainer2 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "secondary_color_filter"));
      label114:
      ColorFilterContainer localColorFilterContainer3;
      label140:
      ColorFilterContainer localColorFilterContainer4;
      label166:
      ColorFilterContainer localColorFilterContainer5;
      label192:
      ColorFilterContainer localColorFilterContainer6;
      label218:
      ColorFilterContainer localColorFilterContainer7;
      label244:
      Typeface localTypeface;
      if (localColorFilterContainer2 != null)
      {
        this.mSecondaryColorFilter = localColorFilterContainer2;
        localColorFilterContainer3 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "bottom_color_filter"));
        if (localColorFilterContainer3 == null) {
          break label988;
        }
        this.mBottomColorFilter = localColorFilterContainer3;
        localColorFilterContainer4 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "top_color_filter"));
        if (localColorFilterContainer4 == null) {
          break label998;
        }
        this.mTopColorFilter = localColorFilterContainer4;
        localColorFilterContainer5 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "background_color_filter"));
        if (localColorFilterContainer5 == null) {
          break label1008;
        }
        this.mBackgroundColorFilter = localColorFilterContainer5;
        localColorFilterContainer6 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "popup_color_filter"));
        if (localColorFilterContainer6 == null) {
          break label1018;
        }
        this.mPopupColorFilter = localColorFilterContainer6;
        localColorFilterContainer7 = ColorFilterUtil.parseColorFilterContainer(paramXmlPullParser.getAttributeValue(str, "minibackground_color_filter"));
        if (localColorFilterContainer7 == null) {
          break label1028;
        }
        this.mMiniBackgroundColorFilter = localColorFilterContainer7;
        this.mTextStyle = parseTextStyle(paramXmlPullParser.getAttributeValue(str, "textStyle"), paramKeyStyle.mTextStyle);
        localTypeface = Typeface.defaultFromStyle(this.mTextStyle);
      }
      try
      {
        int i3 = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "text_color"));
        i = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "text_shadow_color"));
        f1 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "text_shadow_radius"));
        f2 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "text_shadow_dx"));
        f3 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "text_shadow_dy"));
        localTextPaint2 = new TextPaint();
        localTextPaint2.setColor(i3);
        localTextPaint2.setShadowLayer(f1, f2, f3, i);
        localTextPaint2.setTypeface(localTypeface);
        boolean bool2 = isFakeBoldRequired(localTypeface, this.mTextStyle);
        localTextPaint2.setFakeBoldText(bool2);
        this.mTextPaint = localTextPaint2;
        this.mCandidateTextShadowRadius = f1;
        this.mCandidateTextShadowDx = f2;
        this.mCandidateTextShadowDy = f3;
        this.mCandidateTextShadowColor = i;
      }
      catch (Exception localException3)
      {
        try
        {
          int i1 = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "top_text_color"));
          int i2 = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "top_text_shadow_color"));
          float f10 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "top_text_shadow_radius"));
          float f11 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "top_text_shadow_dx"));
          float f12 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "top_text_shadow_dy"));
          localTextPaint4 = new TextPaint();
          localTextPaint4.setColor(i1);
          localTextPaint4.setShadowLayer(f10, f11, f12, i2);
          localTextPaint4.setTypeface(localTypeface);
          boolean bool1 = isFakeBoldRequired(localTypeface, this.mTextStyle);
          localTextPaint4.setFakeBoldText(bool1);
          this.mTopTextPaint = localTextPaint4;
        }
        catch (Exception localException3)
        {
          try
          {
            int m = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "bottom_text_color"));
            int n = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "bottom_text_shadow_color"));
            float f7 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "bottom_text_shadow_radius"));
            float f8 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "bottom_text_shadow_dx"));
            float f9 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "bottom_text_shadow_dy"));
            localTextPaint5 = new TextPaint();
            localTextPaint5.setColor(m);
            localTextPaint5.setShadowLayer(f7, f8, f9, n);
            localTextPaint5.setTypeface(localTypeface);
            localTextPaint5.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
            this.mBottomTextPaint = localTextPaint5;
            if (!paramResources.getBoolean(2131492901))
            {
              this.mBackground = loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "background"), paramKeyStyle.mBackground);
              this.mSeamlessBackground = loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "seamlessBackground"), paramKeyStyle.mSeamlessBackground);
              this.mMiniKeyboardBackground = loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "miniKeyboardBackground"), paramKeyStyle.mMiniKeyboardBackground);
              this.mPopupBackground = loadDrawable(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "popupBackground"), paramKeyStyle.mPopupBackground);
            }
          }
          catch (Exception localException3)
          {
            try
            {
              for (;;)
              {
                int j = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "popup_text_color"));
                int k = Color.parseColor(paramXmlPullParser.getAttributeValue(str, "popup_text_shadow_color"));
                float f4 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "popup_text_shadow_radius"));
                float f5 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "popup_text_shadow_dx"));
                float f6 = Float.parseFloat(paramXmlPullParser.getAttributeValue(str, "popup_text_shadow_dy"));
                localTextPaint6 = new TextPaint();
                localTextPaint6.setColor(j);
                localTextPaint6.setShadowLayer(f4, f5, f6, k);
                localTextPaint6.setTypeface(localTypeface);
                localTextPaint6.setFakeBoldText(isFakeBoldRequired(localTypeface, this.mTextStyle));
                this.mPopupTextPaint = localTextPaint6;
                return;
                localColorFilterContainer1 = paramKeyStyle.mColorFilter;
                break;
                localColorFilterContainer2 = paramKeyStyle.mSecondaryColorFilter;
                break label114;
                label988:
                localColorFilterContainer3 = paramKeyStyle.mBottomColorFilter;
                break label140;
                label998:
                localColorFilterContainer4 = paramKeyStyle.mTopColorFilter;
                break label166;
                label1008:
                localColorFilterContainer5 = paramKeyStyle.mBackgroundColorFilter;
                break label192;
                label1018:
                localColorFilterContainer6 = paramKeyStyle.mPopupColorFilter;
                break label218;
                label1028:
                localColorFilterContainer7 = paramKeyStyle.mPopupColorFilter;
                break label244;
                localException1 = localException1;
                TextPaint localTextPaint1 = paramKeyStyle.mTextPaint;
                TextPaint localTextPaint2 = new TextPaint(localTextPaint1);
                float f1 = paramKeyStyle.mCandidateTextShadowRadius;
                float f2 = paramKeyStyle.mCandidateTextShadowDx;
                float f3 = paramKeyStyle.mCandidateTextShadowDy;
                int i = paramKeyStyle.mCandidateTextShadowColor;
                continue;
                localException2 = localException2;
                TextPaint localTextPaint3 = paramKeyStyle.mTextPaint;
                TextPaint localTextPaint4 = new TextPaint(localTextPaint3);
                continue;
                localException3 = localException3;
                TextPaint localTextPaint5 = new TextPaint(paramKeyStyle.mTextPaint);
              }
              this.mBackground = new ColorDrawable(0);
            }
            catch (Exception localException4)
            {
              for (;;)
              {
                TextPaint localTextPaint6 = new TextPaint(paramKeyStyle.mTextPaint);
              }
            }
          }
        }
      }
    }
  }
  
  private static boolean correctStyle(int paramInt1, int paramInt2)
  {
    return isBold(paramInt1) == isBold(paramInt2);
  }
  
  private static boolean isBold(int paramInt)
  {
    return (paramInt == 1) || (paramInt == 3);
  }
  
  private static boolean isFakeBoldRequired(Typeface paramTypeface, int paramInt)
  {
    if ((paramTypeface != null) && (!correctStyle(paramInt, paramTypeface.getStyle()))) {
      return isBold(paramInt);
    }
    return false;
  }
  
  private static int loadColor(String paramString, int paramInt)
  {
    try
    {
      int i = Color.parseColor(paramString);
      return i;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      LogUtil.w("KeyStyle", "Failed to parse: \"" + paramString + "\" as a color");
      return paramInt;
    }
    catch (NullPointerException localNullPointerException) {}
    return paramInt;
  }
  
  private static Drawable loadDrawable(Resources paramResources, File paramFile, String paramString, Drawable paramDrawable)
  {
    try
    {
      Drawable localDrawable = DrawableLoader.loadDrawable(paramResources, paramFile, paramString);
      return localDrawable;
    }
    catch (Exception localException) {}
    return paramDrawable;
  }
  
  private static float loadFloat(String paramString, float paramFloat)
  {
    try
    {
      float f = Float.parseFloat(paramString);
      return f;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      LogUtil.w("KeyStyle", "Failed to parse: \"" + paramString + "\" as a float");
      return paramFloat;
    }
    catch (NullPointerException localNullPointerException) {}
    return paramFloat;
  }
  
  public static StyleId parseStyleId(Resources paramResources, String paramString)
  {
    if (paramString.equals(paramResources.getString(2131297235))) {
      return StyleId.BASE;
    }
    if (paramString.equals(paramResources.getString(2131297236))) {
      return StyleId.FUNCTION;
    }
    if (paramString.equals(paramResources.getString(2131297238))) {
      return StyleId.CANDIDATE;
    }
    if (paramString.equals(paramResources.getString(2131297237))) {
      return StyleId.SPECIAL;
    }
    if (paramString.equals(paramResources.getString(2131297239))) {
      return StyleId.LSSB;
    }
    if (paramString.equals(paramResources.getString(2131297240))) {
      return StyleId.TOPCANDIDATE;
    }
    if (paramString.equals(paramResources.getString(2131297241))) {
      return StyleId.MINIKB;
    }
    LogUtil.w("KeyStyle", "Unable to parse styleID, using BASE");
    return StyleId.BASE;
  }
  
  private static int parseTextStyle(String paramString, int paramInt)
  {
    if (paramString == null) {}
    do
    {
      return paramInt;
      if (paramString.equals("normal")) {
        return 0;
      }
      if (paramString.equals("bold")) {
        return 1;
      }
      if (paramString.equals("italic")) {
        return 2;
      }
    } while ((!paramString.equals("bold|italic")) && (!paramString.equals("italic|bold")));
    return 3;
  }
  
  private boolean verify(XmlPullParser paramXmlPullParser)
  {
    String str = paramXmlPullParser.getNamespace();
    return (paramXmlPullParser.getAttributeValue(str, "id") != null) && (((paramXmlPullParser.getAttributeValue(str, "color") != null) && (paramXmlPullParser.getAttributeValue(str, "background") != null) && (paramXmlPullParser.getAttributeValue(str, "popupBackground") != null)) || (paramXmlPullParser.getAttributeValue(str, "parent") != null));
  }
  
  public ColorFilterContainer getColorFilterContainer(SubStyle paramSubStyle)
  {
    if (paramSubStyle == null) {
      return ColorFilterContainer.NULL_FILTER_CONTAINER;
    }
    switch (1.$SwitchMap$com$touchtype$keyboard$theme$KeyStyle$SubStyle[paramSubStyle.ordinal()])
    {
    default: 
      return this.mColorFilter;
    case 1: 
      return this.mBottomColorFilter;
    case 2: 
      return this.mTopColorFilter;
    }
    return this.mSecondaryColorFilter;
  }
  
  public TextPaint getTextPaint(SubStyle paramSubStyle)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$theme$KeyStyle$SubStyle[paramSubStyle.ordinal()])
    {
    default: 
      return this.mTextPaint;
    case 1: 
      return this.mBottomTextPaint;
    }
    return this.mTopTextPaint;
  }
  
  public static enum StyleId
  {
    static
    {
      CANDIDATE = new StyleId("CANDIDATE", 2);
      SPECIAL = new StyleId("SPECIAL", 3);
      LSSB = new StyleId("LSSB", 4);
      TOPCANDIDATE = new StyleId("TOPCANDIDATE", 5);
      MINIKB = new StyleId("MINIKB", 6);
      StyleId[] arrayOfStyleId = new StyleId[7];
      arrayOfStyleId[0] = BASE;
      arrayOfStyleId[1] = FUNCTION;
      arrayOfStyleId[2] = CANDIDATE;
      arrayOfStyleId[3] = SPECIAL;
      arrayOfStyleId[4] = LSSB;
      arrayOfStyleId[5] = TOPCANDIDATE;
      arrayOfStyleId[6] = MINIKB;
      $VALUES = arrayOfStyleId;
    }
    
    private StyleId() {}
  }
  
  public static enum SubStyle
  {
    static
    {
      BOTTOM = new SubStyle("BOTTOM", 1);
      TOP = new SubStyle("TOP", 2);
      SECONDARY = new SubStyle("SECONDARY", 3);
      SubStyle[] arrayOfSubStyle = new SubStyle[4];
      arrayOfSubStyle[0] = MAIN;
      arrayOfSubStyle[1] = BOTTOM;
      arrayOfSubStyle[2] = TOP;
      arrayOfSubStyle[3] = SECONDARY;
      $VALUES = arrayOfSubStyle;
    }
    
    private SubStyle() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.KeyStyle
 * JD-Core Version:    0.7.0.1
 */