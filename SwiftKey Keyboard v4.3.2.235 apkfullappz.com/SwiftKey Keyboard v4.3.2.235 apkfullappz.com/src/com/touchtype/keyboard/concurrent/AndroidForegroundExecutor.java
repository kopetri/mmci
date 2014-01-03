package com.touchtype.keyboard.concurrent;

import android.os.Handler;
import android.os.Looper;
import java.util.concurrent.Executor;

public final class AndroidForegroundExecutor
  implements Executor
{
  private final Handler mHandler = new Handler(Looper.getMainLooper());
  
  public void execute(Runnable paramRunnable)
  {
    this.mHandler.post(paramRunnable);
  }
  
  public void shutdown()
  {
    this.mHandler.removeCallbacksAndMessages(null);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.concurrent.AndroidForegroundExecutor
 * JD-Core Version:    0.7.0.1
 */