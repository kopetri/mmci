package com.touchtype.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import com.touchtype.util.LogUtil;

public final class TaskProgressDialogFragment<Params, Progress, Result>
  extends ProgressDialogFragment
{
  private TaskProgressDialogCallback<Result> callback;
  private Params[] params;
  private ProgressDialogTaskWrapper<Params, Progress, Result> task;
  
  private void onTaskFinished(Result paramResult)
  {
    if (this.callback != null) {
      this.callback.onPostExecute(paramResult);
    }
    for (;;)
    {
      dismiss();
      return;
      LogUtil.w("TaskProgressDialogFragment", "Callback hasn't been set for a TaskProgressDialogFragment");
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (this.task != null) {
      this.task.execute(this.params);
    }
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (this.task != null) {
      this.task.cancel(true);
    }
  }
  
  public void setCallback(TaskProgressDialogCallback<Result> paramTaskProgressDialogCallback)
  {
    this.callback = paramTaskProgressDialogCallback;
  }
  
  public void setTask(ProgressDialogTaskWrapper<Params, Progress, Result> paramProgressDialogTaskWrapper, Params... paramVarArgs)
  {
    this.task = paramProgressDialogTaskWrapper;
    this.params = paramVarArgs;
    this.task.setHostFragment(this);
  }
  
  public static abstract class ProgressDialogTaskWrapper<Params, Progress, Result>
    extends AsyncTask<Params, Progress, Result>
  {
    private TaskProgressDialogFragment<Params, Progress, Result> hostFragment;
    
    private void setHostFragment(TaskProgressDialogFragment<Params, Progress, Result> paramTaskProgressDialogFragment)
    {
      this.hostFragment = paramTaskProgressDialogFragment;
    }
    
    protected abstract Result doBackgroundWork(Params... paramVarArgs);
    
    protected final Result doInBackground(Params... paramVarArgs)
    {
      Object localObject = doBackgroundWork(paramVarArgs);
      this.hostFragment.waitUntilResumedIfNecessary();
      return localObject;
    }
    
    protected final void onPostExecute(Result paramResult)
    {
      this.hostFragment.onTaskFinished(paramResult);
    }
  }
  
  public static abstract interface TaskProgressDialogCallback<Result>
  {
    public abstract void onPostExecute(Result paramResult);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.ui.TaskProgressDialogFragment
 * JD-Core Version:    0.7.0.1
 */