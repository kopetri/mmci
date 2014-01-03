package com.touchtype.keyboard.key.contents;

import com.google.common.base.Objects;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import java.util.HashSet;
import java.util.Set;

public class IconContent
  implements KeyContent
{
  public final TextRendering.HAlign mHAlign;
  public final KeyIcon mIcon;
  protected int[] mIconState;
  public final boolean mLimitByArea;
  public final float mMaxSize;
  public final boolean mPositionByFullscale;
  private final KeyState.StateType mStateType;
  public final TextRendering.VAlign mVAlign;
  
  public IconContent(KeyIcon paramKeyIcon, KeyState.StateType paramStateType, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    if (paramKeyIcon == null) {
      throw new IllegalArgumentException("Could not construct IconContent: icon must be non-null");
    }
    this.mIcon = paramKeyIcon;
    this.mStateType = paramStateType;
    this.mHAlign = paramHAlign;
    this.mVAlign = paramVAlign;
    this.mMaxSize = paramFloat;
    this.mPositionByFullscale = paramBoolean1;
    this.mLimitByArea = paramBoolean2;
  }
  
  public IconContent(KeyIcon paramKeyIcon, TextRendering.HAlign paramHAlign, TextRendering.VAlign paramVAlign, float paramFloat, boolean paramBoolean1, boolean paramBoolean2)
  {
    this(paramKeyIcon, KeyState.StateType.NONE, paramHAlign, paramVAlign, paramFloat, paramBoolean1, paramBoolean2);
  }
  
  public static IconContent getDefaultBottomIconContent(KeyIcon paramKeyIcon)
  {
    return getDefaultBottomIconContent(paramKeyIcon, KeyState.StateType.NONE);
  }
  
  public static IconContent getDefaultBottomIconContent(KeyIcon paramKeyIcon, KeyState.StateType paramStateType)
  {
    return new IconContent(paramKeyIcon, paramStateType, TextRendering.HAlign.CENTRE, TextRendering.VAlign.BOTTOM, 1.0F, false, false);
  }
  
  public static IconContent getDefaultMainIconContent(KeyIcon paramKeyIcon)
  {
    return getDefaultMainIconContent(paramKeyIcon, KeyState.StateType.NONE);
  }
  
  public static IconContent getDefaultMainIconContent(KeyIcon paramKeyIcon, KeyState.StateType paramStateType)
  {
    return new IconContent(paramKeyIcon, paramStateType, TextRendering.HAlign.CENTRE, TextRendering.VAlign.CENTRE, 0.8F, false, true);
  }
  
  public static IconContent getDefaultTopIconContent(KeyIcon paramKeyIcon, KeyState.StateType paramStateType)
  {
    return new IconContent(paramKeyIcon, paramStateType, TextRendering.HAlign.RIGHT, TextRendering.VAlign.TOP, 0.9F, true, false);
  }
  
  public static IconContent getDefaultTopIconContent(KeyIcon paramKeyIcon, Float paramFloat)
  {
    if (paramFloat == null) {
      return getDefaultTopIconContent(paramKeyIcon, KeyState.StateType.NONE);
    }
    return new IconContent(paramKeyIcon, TextRendering.HAlign.RIGHT, TextRendering.VAlign.TOP, paramFloat.floatValue(), true, false);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    switch (1.$SwitchMap$com$touchtype$keyboard$key$KeyState$StateType[this.mStateType.ordinal()])
    {
    default: 
      return this;
    case 1: 
      this.mIconState = paramKeyState.getPressedDrawableState();
      return this;
    }
    this.mIconState = paramKeyState.getOptionDrawableState();
    return this;
  }
  
  public KeyContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    return this;
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    IconContent localIconContent;
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof IconContent));
      if (paramObject == this) {
        return true;
      }
      localIconContent = (IconContent)paramObject;
    } while ((!this.mIcon.equals(localIconContent.mIcon)) || (!this.mStateType.equals(localIconContent.mStateType)) || (!this.mIconState.equals(localIconContent.mIconState)) || (!this.mHAlign.equals(localIconContent.mHAlign)) || (!this.mVAlign.equals(localIconContent.mVAlign)) || (this.mMaxSize != localIconContent.mMaxSize) || (this.mPositionByFullscale != localIconContent.mPositionByFullscale) || (this.mLimitByArea != localIconContent.mLimitByArea));
    return true;
  }
  
  public int[] getIconState()
  {
    return this.mIconState;
  }
  
  public Set<String> getInputStrings()
  {
    return new HashSet();
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[8];
    arrayOfObject[0] = this.mIcon;
    arrayOfObject[1] = this.mStateType;
    arrayOfObject[2] = this.mIconState;
    arrayOfObject[3] = this.mHAlign;
    arrayOfObject[4] = this.mVAlign;
    arrayOfObject[5] = Float.valueOf(this.mMaxSize);
    arrayOfObject[6] = Boolean.valueOf(this.mPositionByFullscale);
    arrayOfObject[7] = Boolean.valueOf(this.mLimitByArea);
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public String toString()
  {
    return "IconId: " + this.mIcon;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.IconContent
 * JD-Core Version:    0.7.0.1
 */