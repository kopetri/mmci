package com.touchtype.keyboard.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.touchtype.keyboard.key.contents.KeyContent;
import com.touchtype.keyboard.theme.KeyStyle.StyleId;

public abstract interface OneCandidateViewInterface
{
  public abstract void candidateSelected(boolean paramBoolean);
  
  public abstract View getView();
  
  public abstract void setContent(KeyContent paramKeyContent, KeyStyle.StyleId paramStyleId);
  
  public abstract void setThemeAttributes(Drawable paramDrawable);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.OneCandidateViewInterface
 * JD-Core Version:    0.7.0.1
 */