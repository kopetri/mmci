package com.touchtype.keyboard.key.contents;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.google.common.base.Objects;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import com.touchtype.keyboard.theme.util.TextRendering.HAlign;
import com.touchtype.keyboard.theme.util.TextRendering.VAlign;
import java.util.HashSet;
import java.util.Set;

public final class LSSBContent
  implements KeyContent
{
  public final float mDensity;
  public final IconContent mLeftContent = new IconContent(KeyIcon.leftArrow, TextRendering.HAlign.LEFT, TextRendering.VAlign.CENTRE, 1.0F, false, false);
  public final String mName;
  public final IconContent mRightContent = new IconContent(KeyIcon.rightArrow, TextRendering.HAlign.RIGHT, TextRendering.VAlign.CENTRE, 1.0F, false, false);
  public final String mShortName;
  
  public LSSBContent(Context paramContext, String paramString1, String paramString2)
  {
    this.mShortName = paramString1;
    this.mName = paramString2;
    this.mDensity = paramContext.getResources().getDisplayMetrics().density;
  }
  
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
    LSSBContent localLSSBContent;
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof LSSBContent));
      if (paramObject == this) {
        return true;
      }
      localLSSBContent = (LSSBContent)paramObject;
    } while ((!this.mShortName.equals(localLSSBContent.mShortName)) || (!this.mName.equals(localLSSBContent.mName)) || (this.mDensity != localLSSBContent.mDensity));
    return true;
  }
  
  public Set<String> getInputStrings()
  {
    return new HashSet();
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.mShortName;
    arrayOfObject[1] = this.mName;
    arrayOfObject[2] = Float.valueOf(this.mDensity);
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public String toString()
  {
    return "LSSBContent";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.LSSBContent
 * JD-Core Version:    0.7.0.1
 */