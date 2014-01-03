package com.touchtype.keyboard.key.contents;

import com.google.common.base.Objects;
import com.google.common.collect.Sets;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.service.TouchTypeSoftKeyboard.ShiftState;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import java.util.Set;

public class DualKeyContent
  implements KeyContent
{
  public final KeyContent mBottomContent;
  public final float mRatio;
  public final KeyContent mTopContent;
  
  public DualKeyContent(KeyContent paramKeyContent1, KeyContent paramKeyContent2, float paramFloat)
  {
    this.mTopContent = paramKeyContent1;
    this.mBottomContent = paramKeyContent2;
    this.mRatio = paramFloat;
  }
  
  public static DualKeyContent createDefaultDualContent(KeyContent paramKeyContent1, KeyContent paramKeyContent2)
  {
    return new DualKeyContent(paramKeyContent1, paramKeyContent2, 0.65F);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    return new DualKeyContent(this.mTopContent.applyKeyState(paramKeyState), this.mBottomContent.applyKeyState(paramKeyState), this.mRatio);
  }
  
  public DualKeyContent applyShiftState(TouchTypeSoftKeyboard.ShiftState paramShiftState)
  {
    return new DualKeyContent(this.mTopContent.applyShiftState(paramShiftState), this.mBottomContent.applyShiftState(paramShiftState), this.mRatio);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    DualKeyContent localDualKeyContent;
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof DualKeyContent));
      if (paramObject == this) {
        return true;
      }
      localDualKeyContent = (DualKeyContent)paramObject;
    } while ((!this.mTopContent.equals(localDualKeyContent.mTopContent)) || (!this.mBottomContent.equals(localDualKeyContent.mBottomContent)) || (this.mRatio != localDualKeyContent.mRatio));
    return true;
  }
  
  public Set<String> getInputStrings()
  {
    return Sets.union(this.mBottomContent.getInputStrings(), this.mTopContent.getInputStrings());
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = this.mTopContent;
    arrayOfObject[1] = this.mBottomContent;
    arrayOfObject[2] = Float.valueOf(this.mRatio);
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
  
  public String toString()
  {
    return "{Bottom: " + this.mBottomContent.toString() + ", Top: " + this.mTopContent.toString() + "}";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.DualKeyContent
 * JD-Core Version:    0.7.0.1
 */