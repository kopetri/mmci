package com.touchtype.logcat;

import android.content.Context;
import android.os.AsyncTask;
import com.touchtype.AWSClient;
import com.touchtype.AsyncTaskListener;
import com.touchtype.util.LogUtil;
import java.io.File;

public class AWSClientPutTask
  extends AsyncTask<Void, Void, Integer>
{
  private static final String ASYNC_TASK_ID = AWSClientPutTask.class.getSimpleName();
  private final AsyncTaskListener<Integer> mCallback;
  private final Context mContext;
  private final File mFile;
  private final File mZip;
  
  public AWSClientPutTask(AsyncTaskListener<Integer> paramAsyncTaskListener, Context paramContext, File paramFile1, File paramFile2)
  {
    this.mCallback = paramAsyncTaskListener;
    this.mContext = paramContext;
    this.mFile = paramFile1;
    this.mZip = paramFile2;
  }
  
  protected Integer doInBackground(Void... paramVarArgs)
  {
    try
    {
      Integer localInteger = Integer.valueOf(new AWSClient(this.mContext.getString(2131296320)).put(this.mZip));
      return localInteger;
    }
    catch (Exception localException)
    {
      LogUtil.e(ASYNC_TASK_ID, localException.getMessage());
    }
    return Integer.valueOf(-1);
  }
  
  protected void onCancelled(Integer paramInteger)
  {
    this.mCallback.onCancelledTask(ASYNC_TASK_ID, paramInteger);
    if (paramInteger != null) {
      LogcatAWSResponseHandler.handleAWSResponse(this.mContext, paramInteger, this.mFile, this.mZip);
    }
  }
  
  protected void onPostExecute(Integer paramInteger)
  {
    super.onPostExecute(paramInteger);
    this.mCallback.onPostExecuteTask(ASYNC_TASK_ID, paramInteger);
    LogcatAWSResponseHandler.handleAWSResponse(this.mContext, paramInteger, this.mFile, this.mZip);
  }
  
  protected void onPreExecute()
  {
    super.onPreExecute();
    this.mCallback.onPreExecuteTask(ASYNC_TASK_ID);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.logcat.AWSClientPutTask
 * JD-Core Version:    0.7.0.1
 */