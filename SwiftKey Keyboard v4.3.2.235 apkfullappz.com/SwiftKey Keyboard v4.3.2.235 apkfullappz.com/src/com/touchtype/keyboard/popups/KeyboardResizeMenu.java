package com.touchtype.keyboard.popups;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.report.TouchTypeStats;
import java.util.Arrays;
import java.util.List;

public class KeyboardResizeMenu
  extends KeyboardPopupMenu
{
  private static final String TAG = KeyboardResizeMenu.class.getSimpleName();
  private static final List<Integer> mIdList;
  private KeyboardChoreographer mChoreographer;
  private FrameLayout mContainer;
  private int mIconId;
  private DisplayMetrics mMetrics;
  private TouchTypePreferences mPreferences = TouchTypePreferences.getInstance(this.mContext);
  private LinearLayout mResizeMenu;
  private Resources mResources;
  
  static
  {
    Integer[] arrayOfInteger = new Integer[5];
    arrayOfInteger[0] = Integer.valueOf(2131230913);
    arrayOfInteger[1] = Integer.valueOf(2131230914);
    arrayOfInteger[2] = Integer.valueOf(2131230915);
    arrayOfInteger[3] = Integer.valueOf(2131230916);
    arrayOfInteger[4] = Integer.valueOf(2131230917);
    mIdList = Arrays.asList(arrayOfInteger);
  }
  
  public KeyboardResizeMenu(Context paramContext, MainKeyboardView paramMainKeyboardView, View paramView, PopupWindow paramPopupWindow1, PopupWindow paramPopupWindow2, KeyboardChoreographer paramKeyboardChoreographer)
  {
    super(paramContext, paramMainKeyboardView, paramView, paramPopupWindow1, paramPopupWindow2, paramKeyboardChoreographer);
    this.mChoreographer = paramKeyboardChoreographer;
    this.mResources = paramContext.getResources();
    this.mMetrics = this.mResources.getDisplayMetrics();
  }
  
  private void setClickHandlers()
  {
    for (int i = 0; i < mIdList.size(); i++)
    {
      LinearLayout localLinearLayout = (LinearLayout)this.mContainer.findViewById(((Integer)mIdList.get(i)).intValue());
      final int j = i;
      if (localLinearLayout != null) {
        localLinearLayout.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            KeyboardResizeMenu.this.setCurrentScale(j);
            KeyboardResizeMenu.this.mChoreographer.onResize(j);
          }
        });
      }
    }
  }
  
  private void setCurrentScale(int paramInt)
  {
    for (int i = 0; i < mIdList.size(); i++)
    {
      LinearLayout localLinearLayout = (LinearLayout)this.mContainer.findViewById(((Integer)mIdList.get(i)).intValue());
      ((ImageView)localLinearLayout.findViewById(2131230887)).setImageResource(this.mIconId);
      if (localLinearLayout != null)
      {
        localLinearLayout.setBackgroundDrawable(null);
        if (paramInt == i) {
          localLinearLayout.setBackgroundDrawable(this.mResources.getDrawable(2130838210));
        }
      }
    }
    int j = this.mPreferences.getKeyboardScale(this.mContext, this.mPreferences.getCurrentKeyboardStateByName(this.mContext));
    if ((j == 2) && (paramInt != j)) {
      this.mPreferences.getTouchTypeStats().incrementStatistic("stats_shortcut_resize_uses");
    }
    for (;;)
    {
      this.mPreferences.setKeyboardScale(this.mContext, this.mPreferences.getCurrentKeyboardStateByName(this.mContext), paramInt);
      return;
      if ((paramInt == 2) && (j != paramInt)) {
        this.mPreferences.getTouchTypeStats().decrementStatistic("stats_shortcut_resize_uses");
      }
    }
  }
  
  private void setPosition()
  {
    int i = View.MeasureSpec.makeMeasureSpec(-2, 1073741824);
    int j = View.MeasureSpec.makeMeasureSpec(-2, 1073741824);
    this.mContainer.measure(i, j);
    int[] arrayOfInt1 = new int[2];
    this.mParent.getLocationOnScreen(arrayOfInt1);
    int[] arrayOfInt2 = new int[2];
    this.mParent.measure(i, j);
    this.mContainer.measure(i, j);
    this.mPopupParent.measure(i, j);
    this.mPopupParent.getLocationOnScreen(arrayOfInt2);
    this.mPopupWindow.showAtLocation(this.mParent, 0, this.mMetrics.widthPixels / 2 - this.mContainer.getMeasuredWidth() / 2, arrayOfInt1[1] - arrayOfInt2[1]);
  }
  
  public void show()
  {
    LayoutInflater localLayoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
    this.mContainer = ((FrameLayout)localLayoutInflater.inflate(2130903109, null));
    this.mContainer.setFocusable(true);
    switch (this.mPreferences.getKeyboardLayoutStyle(this.mContext))
    {
    default: 
      this.mIconId = 2130837972;
    }
    for (;;)
    {
      ColorDrawable localColorDrawable = new ColorDrawable(-7829368);
      localColorDrawable.setAlpha(1);
      this.mPopupWindow.setBackgroundDrawable(localColorDrawable);
      this.mPopupWindow.setContentView(this.mContainer);
      this.mPopupWindow.setWindowLayoutMode(-2, -2);
      this.mPopupWindow.setWidth(-2);
      this.mPopupWindow.setHeight(-2);
      showBackground(localLayoutInflater, this.mResources, this.mMetrics);
      this.mPopupWindowBackground.getBackground().setAlpha(0);
      this.mResizeMenu = ((LinearLayout)this.mContainer.findViewById(2131230912));
      this.mResizeMenu.setOnClickListener(new View.OnClickListener()
      {
        public void onClick(View paramAnonymousView) {}
      });
      setPosition();
      setClickHandlers();
      setCurrentScale(this.mPreferences.getKeyboardScale(this.mContext, KeyboardChoreographer.getCurrentStateName(this.mContext)));
      return;
      this.mIconId = 2130837668;
      continue;
      this.mIconId = 2130838231;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.popups.KeyboardResizeMenu
 * JD-Core Version:    0.7.0.1
 */