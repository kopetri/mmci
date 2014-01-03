package com.touchtype.keyboard.view;

import android.widget.PopupWindow;

public abstract interface PopupProvider<T extends PopupWindow>
{
  public abstract void detachPopup(T paramT);
  
  public abstract T getPopup()
    throws IllegalStateException;
  
  public abstract void recycle();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.PopupProvider
 * JD-Core Version:    0.7.0.1
 */