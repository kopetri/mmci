package com.touchtype.keyboard.view.touch;

import android.content.Context;
import com.touchtype.keyboard.key.Key;

public final class PopupTouchHandler
  extends TouchHandler
{
  private final MiniKeyboardClosedCallback mCallback;
  
  public PopupTouchHandler(Context paramContext, Key paramKey, MiniKeyboardClosedCallback paramMiniKeyboardClosedCallback)
  {
    super(paramContext, paramKey);
    this.mCallback = paramMiniKeyboardClosedCallback;
  }
  
  public void cancelPointer(int paramInt)
  {
    this.mCallback.onClosed();
  }
  
  protected void upPointer(Key paramKey, TouchEvent.Touch paramTouch, int paramInt)
  {
    super.upPointer(paramKey, paramTouch, paramInt);
    this.mCallback.onClosed();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.view.touch.PopupTouchHandler
 * JD-Core Version:    0.7.0.1
 */