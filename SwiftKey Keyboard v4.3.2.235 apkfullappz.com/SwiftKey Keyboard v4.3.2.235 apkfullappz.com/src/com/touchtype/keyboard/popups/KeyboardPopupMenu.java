package com.touchtype.keyboard.popups;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.keyboard.view.MainKeyboardView;

public abstract class KeyboardPopupMenu
{
  protected final KeyboardChoreographer mChoreographer;
  protected Context mContext;
  protected final MainKeyboardView mParent;
  protected final View mPopupParent;
  protected PopupWindow mPopupWindow;
  protected PopupWindow mPopupWindowBackground;
  
  public KeyboardPopupMenu(Context paramContext, MainKeyboardView paramMainKeyboardView, View paramView, PopupWindow paramPopupWindow1, PopupWindow paramPopupWindow2, KeyboardChoreographer paramKeyboardChoreographer)
  {
    this.mContext = paramContext;
    this.mPopupParent = paramView;
    this.mParent = paramMainKeyboardView;
    this.mPopupWindow = paramPopupWindow1;
    this.mPopupWindowBackground = paramPopupWindow2;
    this.mChoreographer = paramKeyboardChoreographer;
  }
  
  public abstract void show();
  
  protected void showBackground(LayoutInflater paramLayoutInflater, Resources paramResources, DisplayMetrics paramDisplayMetrics)
  {
    this.mPopupWindowBackground.setContentView(paramLayoutInflater.inflate(2130903071, null));
    this.mPopupWindowBackground.setBackgroundDrawable(paramResources.getDrawable(2130838211));
    this.mPopupWindowBackground.setWindowLayoutMode(0, 0);
    this.mPopupWindowBackground.setWidth(paramDisplayMetrics.widthPixels);
    this.mPopupWindowBackground.setHeight(paramDisplayMetrics.heightPixels);
    this.mPopupWindowBackground.showAtLocation(this.mParent, 0, 0, 0);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.popups.KeyboardPopupMenu
 * JD-Core Version:    0.7.0.1
 */