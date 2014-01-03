package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Region;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.touchtype.keyboard.KeyboardSwitcher;
import com.touchtype.keyboard.candidates.RibbonStateHandler;
import com.touchtype.preferences.TouchTypePreferences;

public class DockedFullPane
  extends FullPane
{
  public DockedFullPane(Context paramContext, KeyboardChoreographer paramKeyboardChoreographer, ViewGroup paramViewGroup, RibbonStateHandler paramRibbonStateHandler, TouchTypePreferences paramTouchTypePreferences, KeyboardSwitcher paramKeyboardSwitcher)
  {
    super(paramContext, paramKeyboardChoreographer, paramViewGroup, paramRibbonStateHandler, paramTouchTypePreferences, paramKeyboardSwitcher);
  }
  
  protected int defaultKeyboardYPosition()
  {
    return this.mChoreographer.getWindowHeight() - this.mKeyboardViewContainer.getPreferredHeight();
  }
  
  protected int getBorderWidth()
  {
    return 0;
  }
  
  public int getContentInset()
  {
    return this.mInputView.getHeight() - getView().getHeight();
  }
  
  int[] getDragTabSize()
  {
    return new int[] { 0, 0 };
  }
  
  protected String getPositionPrefsKey()
  {
    return "pref_keyboard_position_vertical_docked_full";
  }
  
  protected KeyboardState getState()
  {
    return KeyboardState.FULL_DOCKED;
  }
  
  public Region getTouchableRegion()
  {
    return new Region(this.mPaneView.getLeft(), this.mPaneView.getTop(), this.mPaneView.getRight(), this.mPaneView.getBottom());
  }
  
  public int getVisibleInset()
  {
    return this.mInputView.getHeight() - getView().getHeight();
  }
  
  protected boolean isDocked()
  {
    return true;
  }
  
  protected void movePostResize()
  {
    movePaneTo(getView(), 0, kbToPaneY(defaultKeyboardYPosition()), kbToPaneWidth(this.mChoreographer.getWindowWidth()), getPreferredPaneHeight());
  }
  
  protected void setDragTabState(KeyboardChoreographer.TabViewState paramTabViewState)
  {
    this.mTabSingular.setVisibility(8);
  }
  
  protected void setupDragTabListeners() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.DockedFullPane
 * JD-Core Version:    0.7.0.1
 */