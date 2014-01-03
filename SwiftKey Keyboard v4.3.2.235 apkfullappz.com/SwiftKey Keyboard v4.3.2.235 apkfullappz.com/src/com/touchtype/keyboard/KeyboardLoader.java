package com.touchtype.keyboard;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.TypedValue;
import android.util.Xml;
import com.touchtype.R.styleable;
import com.touchtype.keyboard.inputeventmodel.DefaultPredictionProvider;
import com.touchtype.keyboard.inputeventmodel.InputEventModel;
import com.touchtype.keyboard.key.Key;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyFactory;
import com.touchtype.keyboard.key.KeyFactory.FlowOrSwipe;
import com.touchtype.keyboard.key.KeyFactory.KeyLoaderException;
import com.touchtype.keyboard.theme.util.TextMetrics.TextMetricsRegister;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.FluencyServiceProxy;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.xmlpull.v1.XmlPullParserException;

public final class KeyboardLoader
{
  public static final Locale DEFAULT_LOCALE = Locale.getDefault();
  private static final String TAG = KeyboardLoader.class.getSimpleName();
  
  private static boolean areArrowsEnabled(Context paramContext)
  {
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    boolean bool = DeviceUtils.isDeviceTablet(paramContext);
    return (localTouchTypePreferences.isArrowsEnabled()) && (localTouchTypePreferences.getKeyboardDockedState(paramContext)) && (!bool);
  }
  
  private static Row createRowFromXml(Context paramContext, KeyboardAttributes paramKeyboardAttributes, XmlResourceParser paramXmlResourceParser)
    throws IOException, XmlPullParserException
  {
    boolean bool1 = areArrowsEnabled(paramContext);
    if ((paramKeyboardAttributes.getDefinesAlterateView()) && (TouchTypePreferences.getInstance(paramContext).getUseAlternateView(false))) {}
    for (boolean bool2 = true;; bool2 = false)
    {
      Resources localResources = paramContext.getResources();
      TypedArray localTypedArray = localResources.obtainAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard_Row);
      boolean bool3 = localTypedArray.getBoolean(2, false);
      boolean bool4 = localTypedArray.getBoolean(5, false);
      localTypedArray.getBoolean(3, false);
      boolean bool5 = localTypedArray.getBoolean(4, false);
      localTypedArray.recycle();
      if (!shouldShowRow(bool5, bool4, bool2, bool3, bool1)) {
        break;
      }
      return new Row(paramKeyboardAttributes, localResources, paramXmlResourceParser);
    }
    skipToEndOfRow(paramXmlResourceParser);
    return null;
  }
  
  public static float getDimensionOrFraction(TypedArray paramTypedArray, int paramInt, float paramFloat1, float paramFloat2)
  {
    TypedValue localTypedValue = paramTypedArray.peekValue(paramInt);
    if (localTypedValue == null) {}
    float f;
    do
    {
      do
      {
        return paramFloat2;
        if (localTypedValue.type == 5) {
          return paramTypedArray.getDimensionPixelOffset(paramInt, Math.round(paramFloat2));
        }
      } while (localTypedValue.type != 6);
      f = paramTypedArray.getFraction(paramInt, 1, 1, -1.0F);
    } while (f == -1.0F);
    return f * paramFloat1;
  }
  
  public static MainKeyboard loadKeyboard(Context paramContext, FluencyServiceProxy paramFluencyServiceProxy, InputEventModel paramInputEventModel, KeyboardSwitcher paramKeyboardSwitcher, TextMetrics.TextMetricsRegister paramTextMetricsRegister, LanguageSwitchData paramLanguageSwitchData, int paramInt1, int paramInt2, KeyFactory.FlowOrSwipe paramFlowOrSwipe, boolean paramBoolean, DefaultPredictionProvider paramDefaultPredictionProvider, KeyboardBehaviour paramKeyboardBehaviour)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt1);
    float f1 = 0.0F;
    float f2 = 0.0F;
    KeyboardAttributes localKeyboardAttributes = null;
    Row localRow1 = null;
    Object localObject = null;
    ArrayList localArrayList = new ArrayList(40);
    LayoutType localLayoutType = null;
    float f3 = 0.0F;
    float f4 = 0.0F;
    for (;;)
    {
      try
      {
        localHashSet = new HashSet();
        f5 = preCountRowWeightAndCollectPrimaryLetters(paramContext, paramInt1, paramInt2, localHashSet);
        localKeyFactory1 = null;
      }
      catch (XmlPullParserException localXmlPullParserException2)
      {
        HashSet localHashSet;
        float f5;
        KeyFactory localKeyFactory1;
        int i;
        String str1;
        Integer localInteger;
        KeyFactory.FlowOrSwipe localFlowOrSwipe;
        KeyFactory localKeyFactory2;
        Row localRow2;
        float f6;
        float f7;
        String str2;
        continue;
      }
      catch (IOException localIOException2)
      {
        continue;
      }
      catch (KeyboardLoaderException localKeyboardLoaderException2)
      {
        continue;
      }
      try
      {
        i = localXmlResourceParser.next();
        if (i == 1) {
          continue;
        }
        if (i != 2) {
          continue;
        }
        str1 = localXmlResourceParser.getName();
        if (str1.endsWith("Keyboard")) {
          if (localKeyboardAttributes != null) {
            throw new KeyboardLoaderException("Keyboards cannot be nested");
          }
        }
      }
      catch (KeyboardLoaderException localKeyboardLoaderException1)
      {
        throw new RuntimeException(localKeyboardLoaderException1);
        localKeyboardAttributes = KeyboardAttributes.parseKeyboardAttributes(paramContext, paramInt2, localXmlResourceParser, f5);
        f3 = localKeyboardAttributes.mSplitStart;
        f4 = localKeyboardAttributes.mSplitEnd;
        localInteger = Integer.valueOf(localKeyboardAttributes.mNextLayoutId);
        localLayoutType = localKeyboardAttributes.mLayoutType;
        localFlowOrSwipe = paramFlowOrSwipe;
        if (localKeyboardAttributes.mDisableFlow) {
          localFlowOrSwipe = KeyFactory.FlowOrSwipe.getDisabledValue(paramFlowOrSwipe);
        }
        localKeyFactory1 = new KeyFactory(paramContext, paramFluencyServiceProxy, paramInputEventModel, paramKeyboardSwitcher, paramLanguageSwitchData, paramKeyboardBehaviour, localKeyboardAttributes.mLocale, localFlowOrSwipe, localKeyboardAttributes.mRightToLeft, paramBoolean, localKeyboardAttributes.mDisablePopups, paramTextMetricsRegister, localInteger.intValue(), paramDefaultPredictionProvider, localHashSet);
        continue;
        if (!"Row".equals(str1)) {
          continue;
        }
        if (localKeyboardAttributes == null) {
          throw new KeyboardLoaderException("Rows cannot be defined outside a Keyboard");
        }
      }
      catch (IOException localIOException1)
      {
        throw new RuntimeException(localIOException1);
        if (localRow1 != null) {
          throw new KeyboardLoaderException("Rows cannot be nested");
        }
      }
      catch (XmlPullParserException localXmlPullParserException1)
      {
        throw new RuntimeException(localXmlPullParserException1);
      }
      localRow1 = createRowFromXml(paramContext, localKeyboardAttributes, localXmlResourceParser);
      if ((localRow1 != null) && (localRow1.mode != 0) && (localRow1.mode != paramInt2))
      {
        skipToEndOfRow(localXmlResourceParser);
        localRow1 = null;
        continue;
        if (str1.endsWith("Key"))
        {
          if (localRow1 == null) {
            throw new KeyboardLoaderException("Keys cannot be defined outside a Row");
          }
          if (localObject != null) {
            throw new KeyboardLoaderException("Keys cannot be nested");
          }
          localKeyFactory2 = localKeyFactory1;
          localRow2 = localRow1;
          f6 = f1;
          f7 = f2;
          try
          {
            Key localKey = localKeyFactory2.createKeyFromXml(paramContext, localRow2, str1, f6, f7, localXmlResourceParser);
            localObject = localKey;
          }
          catch (KeyFactory.KeyLoaderException localKeyLoaderException)
          {
            throw new KeyboardLoaderException(localKeyLoaderException);
          }
        }
        else if ("Gap".equals(str1))
        {
          if (localRow1 == null) {
            throw new KeyboardLoaderException("Gaps cannot be defined outside a Row");
          }
          if (localObject != null) {
            throw new KeyboardLoaderException("Gaps cannot be defined inside a Key");
          }
          f1 += getDimensionOrFraction(paramContext.getResources().obtainAttributes(Xml.asAttributeSet(localXmlResourceParser), R.styleable.TouchTypeKeyboard_Gap), 0, localKeyboardAttributes.mDisplayRect.width(), 0.0F);
          continue;
          if (i == 3)
          {
            str2 = localXmlResourceParser.getName();
            if ("Row".equals(str2))
            {
              if (localRow1 == null) {
                throw new KeyboardLoaderException("Got Row end tag without start");
              }
              f2 += localRow1.getTotalHeight();
              localRow1 = null;
              f1 = 0.0F;
            }
            else if (str2.endsWith("Key"))
            {
              if (localObject == null) {
                throw new KeyboardLoaderException("Got Key end tag without start");
              }
              f1 += localObject.getArea().getBounds().width();
              localArrayList.add(localObject);
              localObject = null;
            }
            else if (str2.endsWith("Keyboard"))
            {
              if (localKeyboardAttributes == null) {
                throw new KeyboardLoaderException("Got Keyboard end tag without start");
              }
              localKeyboardAttributes = null;
            }
          }
        }
      }
    }
    return new MainKeyboard(localArrayList, localKeyFactory1.getLayout(), localKeyFactory1.getPredictionFilter(), localKeyFactory1.getIntentionalEventFilter(), localKeyFactory1.getFlowOrSwipe(), localLayoutType, localKeyFactory1.createEmptyKey(), f5, f3, f4);
  }
  
  public static KeyboardAttributes loadKeyboardAttributes(Context paramContext, int paramInt1, int paramInt2)
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt1);
    try
    {
      int i;
      do
      {
        i = localXmlResourceParser.next();
        if (i == 1) {
          break;
        }
      } while ((i != 2) || (!localXmlResourceParser.getName().endsWith("Keyboard")));
      KeyboardAttributes localKeyboardAttributes = KeyboardAttributes.parseKeyboardAttributes(paramContext, paramInt2, localXmlResourceParser, 1.0F);
      return localKeyboardAttributes;
    }
    catch (IOException localIOException)
    {
      LogUtil.e(TAG, "IOException when loading KeyboardAttributes");
      throw new RuntimeException(localIOException);
    }
    catch (XmlPullParserException localXmlPullParserException)
    {
      LogUtil.e(TAG, "XmlPullParserException when loading KeyboardAttributes");
      throw new RuntimeException(localXmlPullParserException);
    }
    catch (KeyboardLoaderException localKeyboardLoaderException)
    {
      LogUtil.e(TAG, "KeyboardLoaderException when loading KeyboardAttributes");
      throw new RuntimeException(localKeyboardLoaderException);
    }
    LogUtil.e(TAG, "No KeyboardAttributes found in specified resource");
    throw new RuntimeException("No KeyboardAttributes found in specified resource");
  }
  
  private static RectF parsePadRect(Resources paramResources, XmlResourceParser paramXmlResourceParser, RectF paramRectF)
  {
    return parsePadRect(paramResources, paramXmlResourceParser, paramRectF, new RectF(0.0F, 0.0F, 0.0F, 0.0F));
  }
  
  public static RectF parsePadRect(Resources paramResources, XmlResourceParser paramXmlResourceParser, RectF paramRectF1, RectF paramRectF2)
  {
    TypedArray localTypedArray = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard);
    float f1 = getDimensionOrFraction(localTypedArray, 1, 1.0F, paramRectF2.left);
    float f2 = getDimensionOrFraction(localTypedArray, 2, paramRectF1.width(), paramRectF2.right);
    float f3 = getDimensionOrFraction(localTypedArray, 3, paramRectF1.height(), paramRectF2.top);
    float f4 = getDimensionOrFraction(localTypedArray, 4, paramRectF1.height(), paramRectF2.bottom);
    localTypedArray.recycle();
    return new RectF(f1, f3, f2, f4);
  }
  
  private static float preCountRowWeightAndCollectPrimaryLetters(Context paramContext, int paramInt1, int paramInt2, Set<String> paramSet)
    throws XmlPullParserException, IOException
  {
    XmlResourceParser localXmlResourceParser = paramContext.getResources().getXml(paramInt1);
    Resources localResources = paramContext.getResources();
    boolean bool1 = areArrowsEnabled(paramContext);
    TouchTypePreferences localTouchTypePreferences = TouchTypePreferences.getInstance(paramContext);
    float f1 = 0.0F;
    for (;;)
    {
      int i = localXmlResourceParser.next();
      if (i == 1) {
        break;
      }
      if (i == 2)
      {
        String str1 = localXmlResourceParser.getName();
        if ("Row".equals(str1))
        {
          TypedArray localTypedArray2 = localResources.obtainAttributes(Xml.asAttributeSet(localXmlResourceParser), R.styleable.TouchTypeKeyboard);
          float f2 = localTypedArray2.getFloat(12, 1.0F);
          if ((localTypedArray2.getBoolean(11, false)) && (localTouchTypePreferences.getUseAlternateView(false))) {}
          for (boolean bool2 = true;; bool2 = false)
          {
            localTypedArray2.recycle();
            TypedArray localTypedArray3 = localResources.obtainAttributes(Xml.asAttributeSet(localXmlResourceParser), R.styleable.TouchTypeKeyboard_Row);
            boolean bool3 = localTypedArray3.getBoolean(2, false);
            boolean bool4 = localTypedArray3.getBoolean(4, false);
            boolean bool5 = localTypedArray3.getBoolean(5, false);
            int j = localTypedArray3.getResourceId(1, 0);
            localTypedArray3.recycle();
            if ((!shouldShowRow(bool4, bool5, bool2, bool3, bool1)) || ((j != 0) && (j != paramInt2))) {
              break label223;
            }
            f1 += f2;
            break;
          }
          label223:
          skipToEndOfRow(localXmlResourceParser);
        }
        else if (("LetterKey".equals(str1)) || ("CurrencyKey".equals(str1)))
        {
          TypedArray localTypedArray1 = paramContext.obtainStyledAttributes(Xml.asAttributeSet(localXmlResourceParser), R.styleable.LatinKey, 2130772124, 0);
          String str2 = localTypedArray1.getString(11);
          if (str2.length() > 0) {
            paramSet.add(str2);
          }
          localTypedArray1.recycle();
        }
      }
    }
    return f1;
  }
  
  private static boolean shouldShowRow(boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
  {
    if (((paramBoolean2) && (paramBoolean3) && (paramBoolean4) && (paramBoolean5)) || ((!paramBoolean2) && (!paramBoolean3) && (!paramBoolean4) && (!paramBoolean5))) {
      return true;
    }
    if (((paramBoolean2) && (paramBoolean3) && (!paramBoolean4) && (!paramBoolean5)) || ((!paramBoolean2) && (!paramBoolean3) && (paramBoolean4) && (paramBoolean5))) {
      return true;
    }
    return (paramBoolean1) && (((paramBoolean2) && (paramBoolean3)) || ((!paramBoolean2) && (!paramBoolean3)));
  }
  
  private static void skipToEndOfRow(XmlResourceParser paramXmlResourceParser)
    throws XmlPullParserException, IOException
  {
    int i;
    do
    {
      i = paramXmlResourceParser.next();
    } while ((i != 1) && ((i != 3) || (!paramXmlResourceParser.getName().equals("Row"))));
  }
  
  public static final class KeyboardAttributes
  {
    public final float mDefaultKeyWidth;
    public final RectF mDefaultPadRect;
    public final boolean mDefinesAlternateView;
    public final boolean mDisableFlow;
    public final boolean mDisablePopups;
    public final RectF mDisplayRect;
    public final int mKeyboardMode;
    public final LayoutType mLayoutType;
    public final Locale mLocale;
    public final int mNextLayoutId;
    public final boolean mRightToLeft;
    public final float mSplitEnd;
    public final float mSplitStart;
    public final float mTotalWeight;
    public final float mVerticalWeight;
    
    public KeyboardAttributes(Context paramContext, int paramInt1, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, RectF paramRectF, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat5, int paramInt2, LayoutType paramLayoutType)
    {
      this.mDefaultKeyWidth = paramFloat2;
      this.mSplitStart = paramFloat3;
      this.mSplitEnd = paramFloat4;
      if (paramRectF != null)
      {
        this.mDefaultPadRect = paramRectF;
        this.mDisplayRect = getDisplayRect();
        this.mDisableFlow = paramBoolean1;
        this.mDisablePopups = paramBoolean2;
        this.mKeyboardMode = paramInt1;
        this.mLocale = paramLocale;
        this.mRightToLeft = paramBoolean3;
        this.mDefinesAlternateView = paramBoolean4;
        this.mVerticalWeight = paramFloat5;
        if (paramFloat1 == 0.0F) {
          break label121;
        }
      }
      for (;;)
      {
        this.mTotalWeight = paramFloat1;
        this.mNextLayoutId = paramInt2;
        this.mLayoutType = paramLayoutType;
        return;
        paramRectF = new RectF(0.0F, 0.0F, 0.0F, 0.0F);
        break;
        label121:
        paramFloat1 = 1.0F;
      }
    }
    
    public KeyboardAttributes(Context paramContext, int paramInt, float paramFloat1, float paramFloat2, RectF paramRectF)
    {
      this(paramContext, paramInt, paramFloat2, paramFloat1, paramRectF, KeyboardLoader.DEFAULT_LOCALE, false, false, false, false, 1.0F);
    }
    
    public KeyboardAttributes(Context paramContext, int paramInt, float paramFloat1, float paramFloat2, RectF paramRectF, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, float paramFloat3)
    {
      this(paramContext, paramInt, paramFloat1, paramFloat2, 0.0F, 0.0F, paramRectF, paramLocale, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramFloat3, 2131034654, null);
    }
    
    private static RectF getDisplayRect()
    {
      return new RectF(0.0F, 0.0F, 1.0F, 1.0F);
    }
    
    public static KeyboardAttributes parseKeyboardAttributes(Context paramContext, int paramInt, XmlResourceParser paramXmlResourceParser, float paramFloat)
      throws KeyboardLoader.KeyboardLoaderException
    {
      RectF localRectF = getDisplayRect();
      TypedArray localTypedArray = paramContext.getResources().obtainAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard);
      float f1 = KeyboardLoader.getDimensionOrFraction(localTypedArray, 0, localRectF.width(), 0.1F);
      float f2 = localTypedArray.getFloat(12, 1.0F);
      float f3 = KeyboardLoader.getDimensionOrFraction(localTypedArray, 5, 1.0F, 0.0F);
      float f4 = KeyboardLoader.getDimensionOrFraction(localTypedArray, 6, 1.0F, 0.0F);
      boolean bool1 = localTypedArray.getBoolean(7, false);
      boolean bool2 = localTypedArray.getBoolean(8, false);
      String str = localTypedArray.getString(9);
      Locale localLocale;
      boolean bool3;
      if (str == null)
      {
        localLocale = KeyboardLoader.DEFAULT_LOCALE;
        bool3 = localTypedArray.getBoolean(10, false);
        if ((!localTypedArray.getBoolean(11, false)) || (!TouchTypePreferences.getInstance(paramContext).getUseAlternateView(false))) {
          break label250;
        }
      }
      label250:
      for (boolean bool4 = true;; bool4 = false)
      {
        int i = localTypedArray.getResourceId(13, -2147483648);
        if (i == -2147483648) {
          i = localTypedArray.getInt(13, 2131034654);
        }
        LayoutType localLayoutType = LayoutType.forValue(localTypedArray.getInt(14, LayoutType.STANDARD.getValue()));
        localTypedArray.recycle();
        return new KeyboardAttributes(paramContext, paramInt, paramFloat, f1, f3, f4, KeyboardLoader.parsePadRect(paramContext.getResources(), paramXmlResourceParser, localRectF), localLocale, bool1, bool2, bool3, bool4, f2, i, localLayoutType);
        localLocale = new Locale(str);
        break;
      }
    }
    
    public float getDefaultKeyHeight()
    {
      return getKeyHeight(this.mVerticalWeight);
    }
    
    public RectF getDefaultPadRect()
    {
      return getPadRect(this.mVerticalWeight);
    }
    
    public boolean getDefinesAlterateView()
    {
      return this.mDefinesAlternateView;
    }
    
    public float getKeyHeight(float paramFloat)
    {
      return paramFloat / this.mTotalWeight;
    }
    
    public RectF getPadRect(float paramFloat)
    {
      return new RectF(this.mDefaultPadRect.left, this.mDefaultPadRect.top * getKeyHeight(paramFloat), this.mDefaultPadRect.right, this.mDefaultPadRect.bottom * getKeyHeight(paramFloat));
    }
  }
  
  public static final class KeyboardLoaderException
    extends Exception
  {
    public KeyboardLoaderException() {}
    
    public KeyboardLoaderException(Exception paramException)
    {
      super();
    }
    
    public KeyboardLoaderException(String paramString)
    {
      super();
    }
  }
  
  public static final class Row
  {
    public final float mDefaultKeyHeight;
    public final float mDefaultKeyWidth;
    public final RectF mDefaultPadRect;
    public final int mEdgeFlags;
    public final int mode;
    public final KeyboardLoader.KeyboardAttributes parent;
    
    public Row(KeyboardLoader.KeyboardAttributes paramKeyboardAttributes, int paramInt)
    {
      this.parent = paramKeyboardAttributes;
      this.mDefaultKeyWidth = paramKeyboardAttributes.mDefaultKeyWidth;
      this.mDefaultKeyHeight = paramKeyboardAttributes.getDefaultKeyHeight();
      this.mDefaultPadRect = paramKeyboardAttributes.getDefaultPadRect();
      this.mode = paramKeyboardAttributes.mKeyboardMode;
      this.mEdgeFlags = paramInt;
    }
    
    public Row(KeyboardLoader.KeyboardAttributes paramKeyboardAttributes, Resources paramResources, XmlResourceParser paramXmlResourceParser)
    {
      this.parent = paramKeyboardAttributes;
      TypedArray localTypedArray1 = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard);
      this.mDefaultKeyWidth = KeyboardLoader.getDimensionOrFraction(localTypedArray1, 0, paramKeyboardAttributes.mDisplayRect.width(), paramKeyboardAttributes.mDefaultKeyWidth);
      float f = localTypedArray1.getFloat(12, paramKeyboardAttributes.mVerticalWeight);
      this.mDefaultKeyHeight = paramKeyboardAttributes.getKeyHeight(f);
      localTypedArray1.recycle();
      this.mDefaultPadRect = KeyboardLoader.parsePadRect(paramResources, paramXmlResourceParser, new RectF(0.0F, 0.0F, 1.0F, this.mDefaultKeyHeight), paramKeyboardAttributes.getPadRect(f));
      TypedArray localTypedArray2 = paramResources.obtainAttributes(Xml.asAttributeSet(paramXmlResourceParser), R.styleable.TouchTypeKeyboard_Row);
      this.mEdgeFlags = localTypedArray2.getInt(0, 0);
      this.mode = localTypedArray2.getResourceId(1, 0);
      localTypedArray2.recycle();
    }
    
    public float getTotalHeight()
    {
      return this.mDefaultKeyHeight;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.KeyboardLoader
 * JD-Core Version:    0.7.0.1
 */