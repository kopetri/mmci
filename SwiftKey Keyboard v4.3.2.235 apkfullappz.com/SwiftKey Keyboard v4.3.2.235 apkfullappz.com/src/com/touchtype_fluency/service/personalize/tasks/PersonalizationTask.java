package com.touchtype_fluency.service.personalize.tasks;

import com.google.common.base.Preconditions;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;

public abstract class PersonalizationTask
  implements Runnable
{
  private static final int RETRY_TIME_MS = 1000;
  protected final String TAG = getClass().getSimpleName();
  private final PersonalizationTaskListener mListener;
  private String mLocation;
  private int mRemainingTries;
  private boolean mShouldNotifyListener;
  private final PersonalizationTaskExecutor mTaskExecutor;
  
  public PersonalizationTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener, int paramInt)
  {
    this.mTaskExecutor = paramPersonalizationTaskExecutor;
    this.mListener = paramPersonalizationTaskListener;
    this.mRemainingTries = paramInt;
  }
  
  public PersonalizationTask(PersonalizationTaskExecutor paramPersonalizationTaskExecutor, PersonalizationTaskListener paramPersonalizationTaskListener, int paramInt, String paramString)
  {
    this(paramPersonalizationTaskExecutor, paramPersonalizationTaskListener, paramInt);
    this.mLocation = paramString;
  }
  
  protected abstract void compute()
    throws TaskFailException;
  
  public String getLocation()
  {
    return this.mLocation;
  }
  
  protected void notifyListener(boolean paramBoolean, PersonalizationFailedReason paramPersonalizationFailedReason)
  {
    this.mShouldNotifyListener = false;
    if (paramBoolean)
    {
      this.mListener.onSuccess();
      return;
    }
    this.mListener.onFail(paramPersonalizationFailedReason);
  }
  
  protected void retry(PersonalizationFailedReason paramPersonalizationFailedReason)
  {
    this.mShouldNotifyListener = false;
    if (this.mRemainingTries > 0)
    {
      this.mRemainingTries = (-1 + this.mRemainingTries);
      this.mTaskExecutor.scheduleTask(this, 1000);
      return;
    }
    notifyListener(false, paramPersonalizationFailedReason);
  }
  
  public void run()
  {
    for (boolean bool = true;; bool = false) {
      try
      {
        this.mShouldNotifyListener = true;
        if (this.mLocation != null)
        {
          Preconditions.checkState(bool, "Location hasn't been set in the " + getClass().getSimpleName());
          compute();
          return;
        }
      }
      catch (TaskFailException localTaskFailException)
      {
        LogUtil.w(this.TAG, localTaskFailException.getMessage(), localTaskFailException);
        retry(localTaskFailException.getReason());
        return;
      }
      finally
      {
        if (!this.mShouldNotifyListener) {
          break;
        }
        LogUtil.w(this.TAG, "Task listener wasn't notified, something went wrong. Notifying of fail.");
        notifyListener(false, PersonalizationFailedReason.OTHER);
      }
    }
  }
  
  protected void schedule(PersonalizationTask paramPersonalizationTask, int paramInt)
  {
    this.mShouldNotifyListener = false;
    this.mTaskExecutor.scheduleTask(paramPersonalizationTask, paramInt);
  }
  
  public void setLocation(String paramString)
  {
    this.mLocation = paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.PersonalizationTask
 * JD-Core Version:    0.7.0.1
 */