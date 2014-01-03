package com.touchtype.keyboard.popups;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.touchtype.keyboard.QuickLayoutSwitch;
import com.touchtype.keyboard.view.KeyboardChoreographer;
import com.touchtype.keyboard.view.MainKeyboardView;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.util.DeviceUtils;
import com.touchtype.util.KeyHeightUtil;
import java.util.Iterator;
import java.util.List;

public final class LayoutSettingsMenu
  extends KeyboardPopupMenu
{
  private final List<QuickLayoutSwitch> mQuickSwitchLayouts;
  
  public LayoutSettingsMenu(Context paramContext, MainKeyboardView paramMainKeyboardView, View paramView, PopupWindow paramPopupWindow1, PopupWindow paramPopupWindow2, KeyboardChoreographer paramKeyboardChoreographer, List<QuickLayoutSwitch> paramList)
  {
    super(paramContext, paramMainKeyboardView, paramView, paramPopupWindow1, paramPopupWindow2, paramKeyboardChoreographer);
    this.mQuickSwitchLayouts = paramList;
  }
  
  public void show()
  {
    Resources localResources = this.mContext.getResources();
    DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
    int i = View.MeasureSpec.makeMeasureSpec(-2, 1073741824);
    int j = View.MeasureSpec.makeMeasureSpec(-2, 1073741824);
    LayoutInflater localLayoutInflater = (LayoutInflater)this.mContext.getSystemService("layout_inflater");
    FrameLayout localFrameLayout = (FrameLayout)localLayoutInflater.inflate(2130903094, null);
    localFrameLayout.setFocusable(true);
    TextView localTextView = (TextView)localFrameLayout.findViewById(2131230884);
    ImageView localImageView1 = (ImageView)localFrameLayout.findViewById(2131230883);
    LinearLayout localLinearLayout1 = (LinearLayout)localFrameLayout.findViewById(2131230886);
    LinearLayout localLinearLayout2 = (LinearLayout)localFrameLayout.findViewById(2131230889);
    LinearLayout localLinearLayout3 = (LinearLayout)localFrameLayout.findViewById(2131230890);
    LinearLayout localLinearLayout4;
    switch (TouchTypePreferences.getInstance(this.mContext).getKeyboardLayoutStyle(this.mContext))
    {
    default: 
      localLinearLayout4 = localLinearLayout1;
      Drawable localDrawable = localResources.getDrawable(2130838210);
      localLinearLayout4.setBackgroundDrawable(localDrawable);
      this.mContext.getSystemService("keyguard");
      if ((Build.VERSION.SDK_INT < 11) || (DeviceUtils.isScreenLocked(this.mContext))) {
        ((LinearLayout)localFrameLayout.findViewById(2131230882)).setVisibility(8);
      }
      break;
    }
    for (;;)
    {
      label222:
      if (this.mQuickSwitchLayouts.size() > 0)
      {
        View localView1 = localFrameLayout.findViewById(2131230891);
        LinearLayout localLinearLayout5 = (LinearLayout)localFrameLayout.findViewById(2131230892);
        Iterator localIterator = this.mQuickSwitchLayouts.iterator();
        for (;;)
        {
          if (localIterator.hasNext())
          {
            final QuickLayoutSwitch localQuickLayoutSwitch = (QuickLayoutSwitch)localIterator.next();
            View localView2 = localLayoutInflater.inflate(2130903095, null);
            View.OnClickListener local1 = new View.OnClickListener()
            {
              public void onClick(View paramAnonymousView)
              {
                localQuickLayoutSwitch.apply();
              }
            };
            localView2.setOnClickListener(local1);
            ImageView localImageView2 = (ImageView)localView2.findViewById(2131230894);
            localImageView2.setImageResource(localQuickLayoutSwitch.getLayoutIconResId());
            if (localQuickLayoutSwitch.isCurrentLayout()) {
              localImageView2.setColorFilter(localResources.getColor(2131165230));
            }
            ((TextView)localView2.findViewById(2131230895)).setText(localQuickLayoutSwitch.getLayoutNameResId());
            LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(0, -2, 1.0F);
            localLinearLayout5.addView(localView2, localLayoutParams);
            continue;
            localLinearLayout4 = localLinearLayout3;
            break;
            localLinearLayout4 = localLinearLayout2;
            break;
            if ((this.mChoreographer.isDocked()) || (localTextView == null) || (localImageView1 == null)) {
              break label222;
            }
            localTextView.setText(2131297283);
            localImageView1.setImageResource(2130838063);
            localFrameLayout.findViewById(2131230882).setTag(Integer.valueOf(localResources.getInteger(2131558457)));
            break label222;
          }
        }
        localView1.setVisibility(0);
        localLinearLayout5.setVisibility(0);
        localFrameLayout.requestLayout();
      }
    }
    localFrameLayout.measure(i, j);
    ColorDrawable localColorDrawable = new ColorDrawable(-7829368);
    localColorDrawable.setAlpha(1);
    this.mPopupWindow.setBackgroundDrawable(localColorDrawable);
    this.mPopupWindow.setContentView(localFrameLayout);
    this.mPopupWindow.setWindowLayoutMode(-2, -2);
    this.mPopupWindow.setWidth(-2);
    this.mPopupWindow.setHeight(-2);
    showBackground(localLayoutInflater, localResources, localDisplayMetrics);
    int[] arrayOfInt1 = new int[2];
    this.mParent.getLocationOnScreen(arrayOfInt1);
    int k = KeyHeightUtil.getCurrentKeyHeight(this.mContext);
    int m = localResources.getDimensionPixelSize(2131361811);
    if (this.mChoreographer.isDocked())
    {
      if ((DeviceUtils.isDeviceTablet(this.mContext)) || (DeviceUtils.isDeviceInLandscape(this.mContext))) {}
      for (int n = arrayOfInt1[0] + k / 2;; n = (this.mChoreographer.getWindowWidth() - localFrameLayout.getMeasuredWidth()) / 2)
      {
        this.mPopupWindow.showAtLocation(this.mParent, 0, n, this.mChoreographer.getWindowHeight() - localFrameLayout.getMeasuredHeight() - k);
        return;
      }
    }
    int[] arrayOfInt2 = new int[2];
    this.mPopupParent.getLocationOnScreen(arrayOfInt2);
    this.mPopupWindow.showAtLocation(this.mParent, 0, arrayOfInt1[0] + k / 2, arrayOfInt1[1] - arrayOfInt2[1] + this.mParent.getHeight() - localFrameLayout.getMeasuredHeight() - k - m);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.popups.LayoutSettingsMenu
 * JD-Core Version:    0.7.0.1
 */