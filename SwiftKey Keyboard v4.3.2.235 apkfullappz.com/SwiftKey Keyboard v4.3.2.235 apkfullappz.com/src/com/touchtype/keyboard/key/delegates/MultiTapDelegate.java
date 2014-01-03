package com.touchtype.keyboard.key.delegates;

import android.os.Handler;
import android.os.Looper;
import com.touchtype.keyboard.view.touch.TouchEvent.Touch;

public final class MultiTapDelegate
  extends ClickDelegate
{
  private static final Handler mResetHandler = new Handler(Looper.getMainLooper());
  private final MultiTapCallback mCallback;
  private final Runnable mResetCountRunnable;
  private final int mResetDelay;
  private int mTapCount = 0;
  
  public MultiTapDelegate(MultiTapCallback paramMultiTapCallback, int paramInt)
  {
    super(paramMultiTapCallback);
    this.mCallback = paramMultiTapCallback;
    this.mResetDelay = paramInt;
    this.mResetCountRunnable = new Runnable()
    {
      public void run()
      {
        MultiTapDelegate.access$002(MultiTapDelegate.this, 0);
      }
    };
  }
  
  protected void fireCallback(TouchEvent.Touch paramTouch)
  {
    super.fireCallback(paramTouch);
    MultiTapCallback localMultiTapCallback = this.mCallback;
    int i = 1 + this.mTapCount;
    this.mTapCount = i;
    localMultiTapCallback.multitap(paramTouch, i);
    mResetHandler.removeCallbacks(this.mResetCountRunnable);
    mResetHandler.postDelayed(this.mResetCountRunnable, this.mResetDelay);
  }
  
  public static abstract interface MultiTapCallback
    extends ClickFiredCallback
  {
    public abstract void multitap(TouchEvent.Touch paramTouch, int paramInt);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.key.delegates.MultiTapDelegate
 * JD-Core Version:    0.7.0.1
 */