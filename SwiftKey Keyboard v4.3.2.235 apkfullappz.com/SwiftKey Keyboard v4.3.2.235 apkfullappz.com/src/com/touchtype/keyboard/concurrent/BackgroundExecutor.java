package com.touchtype.keyboard.concurrent;

import android.os.Handler;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public final class BackgroundExecutor
{
  private final ExecutorService mExecutor;
  private final Handler mForegroundHandler = new Handler();
  private final Thread mForegroundThread = Thread.currentThread();
  
  public BackgroundExecutor(ExecutorService paramExecutorService)
  {
    this.mExecutor = paramExecutorService;
  }
  
  public void execute(Runnable paramRunnable)
  {
    this.mExecutor.execute(paramRunnable);
  }
  
  public void runInForegroundWhenPriorTasksComplete(final Runnable paramRunnable)
  {
    this.mExecutor.execute(new Runnable()
    {
      public void run()
      {
        BackgroundExecutor.this.mForegroundHandler.post(paramRunnable);
      }
    });
  }
  
  public Future<?> submit(Runnable paramRunnable)
  {
    return this.mExecutor.submit(paramRunnable);
  }
  
  public <T> Future<T> submit(Callable<T> paramCallable)
  {
    return this.mExecutor.submit(paramCallable);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.concurrent.BackgroundExecutor
 * JD-Core Version:    0.7.0.1
 */