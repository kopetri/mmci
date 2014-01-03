package com.touchtype.installer;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

public class ScrollerAwareWebView
  extends WebView
{
  private boolean mOverScrolledY;
  
  public ScrollerAwareWebView(Context paramContext)
  {
    super(paramContext);
  }
  
  public ScrollerAwareWebView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onScrollChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = computeVerticalScrollOffset();
    int j = computeVerticalScrollRange();
    int k = i * 100;
    if (j > 0) {}
    for (;;)
    {
      if (k / j > 90) {
        this.mOverScrolledY = true;
      }
      return;
      j = 1;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.ScrollerAwareWebView
 * JD-Core Version:    0.7.0.1
 */