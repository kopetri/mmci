package com.touchtype_fluency.service.personalize.tasks;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PersonalizationTaskExecutor
{
  private static final int FIXED_THREADS = 5;
  private static final String TAG = PersonalizationTaskExecutor.class.getSimpleName();
  private static final TimeUnit TIME_UNIT = TimeUnit.MILLISECONDS;
  private ScheduledExecutorService mExecutor;
  
  public PersonalizationTaskExecutor()
  {
    this(5);
  }
  
  public PersonalizationTaskExecutor(int paramInt)
  {
    this.mExecutor = Executors.newScheduledThreadPool(paramInt);
  }
  
  public void runTask(PersonalizationTask paramPersonalizationTask)
  {
    this.mExecutor.execute(paramPersonalizationTask);
  }
  
  public void scheduleTask(PersonalizationTask paramPersonalizationTask, int paramInt)
  {
    if (paramInt > 0)
    {
      this.mExecutor.schedule(paramPersonalizationTask, paramInt, TIME_UNIT);
      return;
    }
    runTask(paramPersonalizationTask);
  }
  
  public void stop()
  {
    this.mExecutor.shutdownNow();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.PersonalizationTaskExecutor
 * JD-Core Version:    0.7.0.1
 */