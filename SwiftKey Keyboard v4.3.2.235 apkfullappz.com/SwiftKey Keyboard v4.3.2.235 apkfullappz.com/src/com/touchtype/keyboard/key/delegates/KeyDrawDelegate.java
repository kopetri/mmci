package com.touchtype.keyboard.key.delegates;

import android.graphics.drawable.Drawable;
import com.touchtype.keyboard.key.KeyArea;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.theme.Theme;

public abstract interface KeyDrawDelegate
{
  public abstract KeyArea getArea();
  
  public abstract KeyContent getContent();
  
  public abstract Drawable getKeyDrawable(Theme paramTheme);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.KeyDrawDelegate
 * JD-Core Version:    0.7.0.1
 */