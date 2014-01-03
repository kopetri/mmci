package com.touchtype.keyboard.key;

import android.content.res.TypedArray;
import android.util.TypedValue;

public final class KeyFields
{
  private final KeyIcon mBottomIcon;
  private final String mBottomLabel;
  private final String mBottomText;
  private final int mKeyStyle;
  private final String mOverrideMetricsTag;
  private final String mTag;
  private final KeyIcon mTopIcon;
  private final String mTopLabel;
  
  public KeyFields(TypedArray paramTypedArray, String paramString)
  {
    LabelContent localLabelContent1 = getLabelContent(paramTypedArray.peekValue(10));
    Object localObject1 = localLabelContent1.mStringLabel;
    LabelContent localLabelContent2 = getLabelContent(paramTypedArray.peekValue(11));
    Object localObject2 = localLabelContent2.mStringLabel;
    if (paramTypedArray.getBoolean(12, false))
    {
      String str2 = KeyFactory.getLocalCurrencyGlyph();
      if ((str2 != null) && (localObject1 != null)) {
        localObject1 = str2;
      }
    }
    for (;;)
    {
      this.mTopLabel = ((String)localObject1);
      this.mBottomLabel = ((String)localObject2);
      this.mTopIcon = localLabelContent1.mIconLabel;
      this.mBottomIcon = localLabelContent2.mIconLabel;
      this.mKeyStyle = paramTypedArray.getInteger(8, 0);
      this.mBottomText = paramTypedArray.getString(15);
      this.mTag = paramString;
      this.mOverrideMetricsTag = paramTypedArray.getString(7);
      return;
      if (paramTypedArray.getBoolean(13, false))
      {
        String str1 = KeyFactory.getLocalCurrencyGlyph();
        if (paramTypedArray.getBoolean(14, false))
        {
          if ((str1 != null) && (!str1.equals("$"))) {
            localObject2 = "$";
          }
        }
        else if ((str1 != null) && (!str1.equals(localObject2)))
        {
          if (localObject1 != null) {
            localObject1 = localObject2;
          }
          localObject2 = str1;
        }
      }
    }
  }
  
  private KeyFields(LabelContent paramLabelContent1, LabelContent paramLabelContent2, String paramString)
  {
    this.mTopLabel = paramLabelContent1.mStringLabel;
    this.mTopIcon = paramLabelContent1.mIconLabel;
    this.mBottomLabel = paramLabelContent2.mStringLabel;
    this.mBottomIcon = paramLabelContent2.mIconLabel;
    this.mBottomText = paramString;
    this.mTag = null;
    this.mOverrideMetricsTag = null;
    this.mKeyStyle = 0;
  }
  
  public static KeyFields getDefaultSpaceKeyFields()
  {
    return new KeyFields(new LabelContent(null, null), new LabelContent(null, KeyIcon.SpaceKey), null);
  }
  
  private LabelContent getLabelContent(TypedValue paramTypedValue)
  {
    if (paramTypedValue == null) {
      return new LabelContent(null, null);
    }
    if (paramTypedValue.type == 16) {
      return new LabelContent(null, KeyIcon.getFromEnumValue(paramTypedValue.data));
    }
    return new LabelContent(paramTypedValue.string.toString(), null);
  }
  
  public KeyIcon getBottomIcon()
  {
    return this.mBottomIcon;
  }
  
  public String getBottomLabel()
  {
    if ((this.mBottomLabel != null) || (this.mBottomIcon != null)) {
      return this.mBottomLabel;
    }
    return "NOLABEL";
  }
  
  public String getBottomText()
  {
    if (this.mBottomText != null) {
      return this.mBottomText;
    }
    if (this.mBottomLabel != null) {
      return this.mBottomLabel;
    }
    return "";
  }
  
  public int getKeyStyle()
  {
    return this.mKeyStyle;
  }
  
  public String getOverrideMetricsTag()
  {
    if (this.mOverrideMetricsTag != null) {
      return this.mOverrideMetricsTag;
    }
    return this.mTag;
  }
  
  public KeyIcon getTopIcon()
  {
    return this.mTopIcon;
  }
  
  public String getTopLabel()
  {
    return this.mTopLabel;
  }
  
  public boolean hasDualContents()
  {
    return (this.mTopLabel != null) || (this.mTopIcon != null);
  }
  
  private static final class LabelContent
  {
    private final KeyIcon mIconLabel;
    private final String mStringLabel;
    
    public LabelContent(String paramString, KeyIcon paramKeyIcon)
    {
      this.mStringLabel = paramString;
      this.mIconLabel = paramKeyIcon;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.KeyFields
 * JD-Core Version:    0.7.0.1
 */