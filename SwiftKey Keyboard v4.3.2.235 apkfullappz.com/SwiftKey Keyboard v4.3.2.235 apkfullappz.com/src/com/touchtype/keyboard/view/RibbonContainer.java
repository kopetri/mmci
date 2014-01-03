package com.touchtype.keyboard.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import com.touchtype.keyboard.theme.OnThemeChangedListener;
import com.touchtype.keyboard.theme.Theme;
import com.touchtype.keyboard.theme.ThemeManager;
import com.touchtype.keyboard.theme.ThemeProperties;

public class RibbonContainer
  extends FrameLayout
  implements OnThemeChangedListener
{
  public RibbonContainer(Context paramContext)
  {
    super(paramContext);
    ((LayoutInflater)paramContext.getSystemService("layout_inflater")).inflate(2130903110, this);
    setBackgroundDrawable(ThemeManager.getInstance(paramContext).getThemeHandler().getProperties().getCandidateBackground());
    ThemeManager.getInstance(paramContext).addListener(this);
  }
  
  private void updateBackgroudColour()
  {
    setBackgroundDrawable(ThemeManager.getInstance(getContext()).getThemeHandler().getProperties().getCandidateBackground());
  }
  
  public void onAttachedToWindow()
  {
    ThemeManager.getInstance(getContext()).addListener(this);
    updateBackgroudColour();
  }
  
  public void onDetachedFromWindow()
  {
    ThemeManager.getInstance(getContext()).removeListener(this);
  }
  
  public void onThemeChanged()
  {
    updateBackgroudColour();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.RibbonContainer
 * JD-Core Version:    0.7.0.1
 */