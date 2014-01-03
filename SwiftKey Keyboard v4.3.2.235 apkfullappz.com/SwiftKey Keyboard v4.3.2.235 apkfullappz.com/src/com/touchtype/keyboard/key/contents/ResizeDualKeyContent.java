package com.touchtype.keyboard.key.contents;

import com.google.common.base.Objects;
import com.touchtype.keyboard.key.KeyIcon;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.OptionState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.keys.view.ResizeDrawable;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.KeyStyle.SubStyle;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import java.util.EnumSet;

public final class ResizeDualKeyContent
  extends DualKeyContent
{
  private final IconContent mMainIcon;
  private final EnumSet<KeyState.OptionState> mTopActiveStates;
  
  public ResizeDualKeyContent(EnumSet<KeyState.OptionState> paramEnumSet, IconContent paramIconContent1, IconContent paramIconContent2, KeyState.StateType paramStateType, float paramFloat)
  {
    super(paramIconContent1, paramIconContent2, paramFloat);
    this.mTopActiveStates = paramEnumSet;
    this.mMainIcon = IconContent.getDefaultMainIconContent(paramIconContent2.mIcon, paramStateType);
  }
  
  public static ResizeDualKeyContent createResizeDualKeyContent(KeyIcon paramKeyIcon1, KeyIcon paramKeyIcon2, KeyState.StateType paramStateType)
  {
    return createResizeDualKeyContent(EnumSet.of(KeyState.OptionState.DONE, KeyState.OptionState.SMILEY), paramKeyIcon1, paramKeyIcon2, paramStateType);
  }
  
  public static ResizeDualKeyContent createResizeDualKeyContent(EnumSet<KeyState.OptionState> paramEnumSet, KeyIcon paramKeyIcon1, KeyIcon paramKeyIcon2, KeyState.StateType paramStateType)
  {
    return new ResizeDualKeyContent(paramEnumSet, IconContent.getDefaultTopIconContent(paramKeyIcon1, paramStateType), IconContent.getDefaultBottomIconContent(paramKeyIcon2, paramStateType), paramStateType, 0.65F);
  }
  
  public KeyContent applyKeyState(KeyState paramKeyState)
  {
    if (this.mTopActiveStates.contains(paramKeyState.getOptionState())) {
      return super.applyKeyState(paramKeyState);
    }
    return this.mMainIcon.applyKeyState(paramKeyState);
  }
  
  public boolean equals(Object paramObject)
  {
    if (paramObject == null) {}
    ResizeDualKeyContent localResizeDualKeyContent;
    do
    {
      do
      {
        return false;
      } while (!(paramObject instanceof ResizeDualKeyContent));
      if (paramObject == this) {
        return true;
      }
      localResizeDualKeyContent = (ResizeDualKeyContent)paramObject;
    } while ((!super.equals(paramObject)) || (!this.mTopActiveStates.equals(localResizeDualKeyContent.mTopActiveStates)) || (!this.mMainIcon.equals(localResizeDualKeyContent.mMainIcon)));
    return true;
  }
  
  public int hashCode()
  {
    Object[] arrayOfObject = new Object[3];
    arrayOfObject[0] = Integer.valueOf(super.hashCode());
    arrayOfObject[1] = this.mTopActiveStates;
    arrayOfObject[2] = this.mMainIcon;
    return Objects.hashCode(arrayOfObject);
  }
  
  public ResizeDrawable render(ThemeRenderer paramThemeRenderer, KeyStyle.StyleId paramStyleId, KeyStyle.SubStyle paramSubStyle)
  {
    return paramThemeRenderer.getContentDrawable(this, paramStyleId, paramSubStyle);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.contents.ResizeDualKeyContent
 * JD-Core Version:    0.7.0.1
 */