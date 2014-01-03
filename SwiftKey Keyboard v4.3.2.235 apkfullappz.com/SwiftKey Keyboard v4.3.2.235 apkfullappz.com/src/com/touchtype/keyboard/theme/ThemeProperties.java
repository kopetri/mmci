package com.touchtype.keyboard.theme;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.theme.util.DrawableLoader;
import com.touchtype.keyboard.theme.util.DrawableLoader.DrawableLoaderException;
import com.touchtype.util.LogUtil;
import java.io.File;
import java.io.FileNotFoundException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ThemeProperties
{
  private final Drawable mBackground;
  private final Drawable mBottomMoveTab;
  private final Drawable mCandidateBackground;
  private final Drawable mDockedBackground;
  private final Drawable mFloatingKeyboardBackground;
  private int mFlowInkHeadColor;
  private int mFlowInkTailColor;
  private final String mId;
  private final Drawable mLeftFlipTab;
  private final String mName;
  private final String mRendererId;
  private final Resources mRes;
  private final Drawable mRightFlipTab;
  
  public ThemeProperties(Context paramContext, int paramInt)
  {
    TypedArray localTypedArray = paramContext.obtainStyledAttributes(paramInt, R.styleable.ThemeProperties);
    this.mRes = paramContext.getResources();
    this.mId = localTypedArray.getString(0);
    this.mName = localTypedArray.getString(1);
    this.mBackground = localTypedArray.getDrawable(3);
    this.mDockedBackground = localTypedArray.getDrawable(6);
    this.mFloatingKeyboardBackground = localTypedArray.getDrawable(7);
    this.mBottomMoveTab = localTypedArray.getDrawable(9);
    this.mLeftFlipTab = localTypedArray.getDrawable(11);
    this.mRightFlipTab = localTypedArray.getDrawable(12);
    this.mCandidateBackground = loadCandidateBackground(localTypedArray.getDrawable(4), this.mBackground);
    this.mRendererId = localTypedArray.getString(5);
    this.mFlowInkHeadColor = localTypedArray.getInt(14, -3355444);
    this.mFlowInkTailColor = localTypedArray.getInt(15, -12303292);
    localTypedArray.recycle();
  }
  
  public ThemeProperties(Resources paramResources, File paramFile, XmlPullParser paramXmlPullParser)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException, XmlPullParserException
  {
    if (!verify(paramXmlPullParser)) {
      throw new XmlPullParserException("Invalid ThemeProperties");
    }
    String str = paramXmlPullParser.getNamespace();
    this.mId = paramXmlPullParser.getAttributeValue(str, "id");
    this.mName = paramXmlPullParser.getAttributeValue(str, "name");
    this.mRes = paramResources;
    this.mBackground = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "background"));
    this.mDockedBackground = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "dockedBackground"));
    this.mFloatingKeyboardBackground = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "floatingKeyboardBackground"));
    this.mLeftFlipTab = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "flipTabLeft"));
    this.mRightFlipTab = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "flipTabRight"));
    this.mBottomMoveTab = loadBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "moveTabBottom"));
    this.mCandidateBackground = loadCandidateBackground(paramResources, paramFile, paramXmlPullParser.getAttributeValue(str, "candidateBackground"), this.mBackground);
    this.mRendererId = paramXmlPullParser.getAttributeValue(str, "rendererId");
    try
    {
      this.mFlowInkHeadColor = Integer.parseInt(paramXmlPullParser.getAttributeValue(str, "flowInkHeadColor"));
      this.mFlowInkTailColor = Integer.parseInt(paramXmlPullParser.getAttributeValue(str, "flowInkTailColor"));
      return;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      LogUtil.e("ThemeProperties", "Theme appears not to provide Flow Ink settings, using defaults!");
      this.mFlowInkHeadColor = -3355444;
      this.mFlowInkTailColor = -12303292;
    }
  }
  
  private Drawable loadBackground(Resources paramResources, File paramFile, String paramString)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    try
    {
      ColorDrawable localColorDrawable = new ColorDrawable(Color.parseColor(paramString));
      return localColorDrawable;
    }
    catch (IllegalArgumentException localIllegalArgumentException) {}
    return DrawableLoader.loadDrawable(paramResources, paramFile, paramString);
  }
  
  private Drawable loadCandidateBackground(Resources paramResources, File paramFile, String paramString, Drawable paramDrawable)
    throws FileNotFoundException, DrawableLoader.DrawableLoaderException
  {
    if (paramString == null) {
      return paramDrawable;
    }
    return loadBackground(paramResources, paramFile, paramString);
  }
  
  private Drawable loadCandidateBackground(Drawable paramDrawable1, Drawable paramDrawable2)
  {
    if (paramDrawable1 == null) {
      return paramDrawable2;
    }
    return paramDrawable1;
  }
  
  private boolean verify(XmlPullParser paramXmlPullParser)
  {
    String str = paramXmlPullParser.getNamespace();
    return (paramXmlPullParser.getAttributeValue(str, "id") != null) && (paramXmlPullParser.getAttributeValue(str, "background") != null) && (paramXmlPullParser.getAttributeValue(str, "rendererId") != null);
  }
  
  public Drawable getBackground()
  {
    return this.mBackground.getConstantState().newDrawable(this.mRes);
  }
  
  public Drawable getBottomMoveTab()
  {
    return this.mBottomMoveTab.getConstantState().newDrawable(this.mRes);
  }
  
  public Drawable getCandidateBackground()
  {
    return this.mCandidateBackground.getConstantState().newDrawable();
  }
  
  public Drawable getDockedBackground()
  {
    return this.mDockedBackground;
  }
  
  public Drawable getFloatingKeyboardBackground()
  {
    return this.mFloatingKeyboardBackground.getConstantState().newDrawable(this.mRes);
  }
  
  public int getFlowInkHeadColor()
  {
    return this.mFlowInkHeadColor;
  }
  
  public int getFlowInkTailColor()
  {
    return this.mFlowInkTailColor;
  }
  
  public String getId()
  {
    return this.mId;
  }
  
  public Drawable getLeftFlipTab()
  {
    return this.mLeftFlipTab;
  }
  
  public String getName()
  {
    return this.mName;
  }
  
  public String getRendererId()
  {
    return this.mRendererId;
  }
  
  public Drawable getRightFlipTab()
  {
    return this.mRightFlipTab;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.theme.ThemeProperties
 * JD-Core Version:    0.7.0.1
 */