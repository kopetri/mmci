package com.touchtype.keyboard.key.delegates;

import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.KeyState;
import com.touchtype.keyboard.key.KeyState.StateType;
import com.touchtype.keyboard.key.KeyStateListener;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.renderer.ThemeRenderer;
import java.util.EnumSet;

public class SimpleDrawDelegate
  implements KeyStateListener, KeyDrawDelegate
{
  private final KeyArea mArea;
  private final KeyContent mContent;
  private KeyState mState;
  private final KeyStyle.StyleId mStyleId;
  
  public SimpleDrawDelegate(KeyStyle.StyleId paramStyleId, KeyArea paramKeyArea, KeyContent paramKeyContent, KeyState paramKeyState)
  {
    this.mStyleId = paramStyleId;
    this.mArea = paramKeyArea;
    this.mContent = paramKeyContent;
    this.mState = paramKeyState;
    this.mState.addListener(EnumSet.of(KeyState.StateType.PRESSED, KeyState.StateType.OPTIONS, KeyState.StateType.INPUT_FILTER, KeyState.StateType.BUFFERED_INPUT), this);
  }
  
  public KeyArea getArea()
  {
    return this.mArea;
  }
  
  public KeyContent getContent()
  {
    return this.mContent.applyKeyState(this.mState);
  }
  
  public Drawable getKeyDrawable(Theme paramTheme)
  {
    return paramTheme.getRenderer().getKeyDrawable(getContent(), this.mState, this.mArea, this.mStyleId);
  }
  
  public void onKeyStateChanged(KeyState paramKeyState)
  {
    paramKeyState.invalidateKey();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.SimpleDrawDelegate
 * JD-Core Version:    0.7.0.1
 */