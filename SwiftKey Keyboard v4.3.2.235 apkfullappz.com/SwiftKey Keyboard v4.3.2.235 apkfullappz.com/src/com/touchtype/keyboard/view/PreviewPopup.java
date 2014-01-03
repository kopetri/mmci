package com.touchtype.keyboard.view;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import com.touchtype.util.LogUtil;

public final class PreviewPopup
  extends PopupWindow
{
  private static final String TAG = PopupWindow.class.getSimpleName();
  private final MainKeyboardView mParent;
  private final ViewGroup mViewGroup;
  private int mXOffset;
  private int mYOffset;
  
  public PreviewPopup(MainKeyboardView paramMainKeyboardView)
  {
    super(paramMainKeyboardView.getContext());
    this.mParent = paramMainKeyboardView;
    this.mViewGroup = new FrameLayout(paramMainKeyboardView.getContext());
    setContentView(this.mViewGroup);
  }
  
  public void dismiss()
  {
    if (isShowing()) {
      super.dismiss();
    }
  }
  
  public Rect getBounds()
  {
    return new Rect(0, 0, getWidth(), getHeight());
  }
  
  public Context getContext()
  {
    return this.mParent.getContext();
  }
  
  public MainKeyboardView getParent()
  {
    return this.mParent;
  }
  
  public void setBounds(Rect paramRect)
  {
    int[] arrayOfInt = new int[2];
    this.mParent.getLocationInWindow(arrayOfInt);
    this.mXOffset = (arrayOfInt[0] + paramRect.left);
    this.mYOffset = (arrayOfInt[1] + paramRect.top);
    setHeight(paramRect.height());
    setWidth(paramRect.width());
  }
  
  public void setContent(View paramView)
  {
    this.mViewGroup.removeAllViews();
    this.mViewGroup.addView(paramView);
  }
  
  public void show()
  {
    if (this.mParent.getWindowToken() != null)
    {
      if (isShowing())
      {
        getContentView().invalidate();
        update(this.mXOffset, this.mYOffset, getWidth(), getHeight());
      }
    }
    else {
      return;
    }
    try
    {
      showAtLocation(this.mParent, 0, this.mXOffset, this.mYOffset);
      return;
    }
    catch (RuntimeException localRuntimeException)
    {
      LogUtil.e(TAG, "Exception occurred trying to show PreivewPopup: " + localRuntimeException.getLocalizedMessage());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.PreviewPopup
 * JD-Core Version:    0.7.0.1
 */