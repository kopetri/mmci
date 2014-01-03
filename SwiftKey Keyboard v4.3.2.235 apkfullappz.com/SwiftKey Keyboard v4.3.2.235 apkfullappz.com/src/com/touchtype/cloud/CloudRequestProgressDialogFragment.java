package com.touchtype.cloud;

import android.os.AsyncTask;
import com.touchtype.keyboard.concurrent.ThreadUtils;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.ui.ProgressDialogFragment;
import java.util.Map;

public final class CloudRequestProgressDialogFragment
  extends ProgressDialogFragment
{
  private RequestListener externalListener;
  private RequestListener persistentListener = new RequestListener()
  {
    public void onError(final RequestListener.SyncError paramAnonymousSyncError, final String paramAnonymousString)
    {
      if (!ThreadUtils.onMainThread())
      {
        CloudRequestProgressDialogFragment.this.onErrorCallback(paramAnonymousSyncError, paramAnonymousString);
        return;
      }
      new AsyncTask()
      {
        protected Void doInBackground(Void... paramAnonymous2VarArgs)
        {
          CloudRequestProgressDialogFragment.1.this.onError(paramAnonymousSyncError, paramAnonymousString);
          return null;
        }
      }.execute(new Void[0]);
    }
    
    public void onSuccess(final Map<String, String> paramAnonymousMap)
    {
      if (!ThreadUtils.onMainThread())
      {
        CloudRequestProgressDialogFragment.this.onSuccessCallback(paramAnonymousMap);
        return;
      }
      new AsyncTask()
      {
        protected Void doInBackground(Void... paramAnonymous2VarArgs)
        {
          CloudRequestProgressDialogFragment.this.onSuccessCallback(paramAnonymousMap);
          return null;
        }
      }.execute(new Void[0]);
    }
  };
  
  private void onErrorCallback(RequestListener.SyncError paramSyncError, String paramString)
  {
    waitUntilResumedIfNecessary();
    if (this.externalListener != null) {
      this.externalListener.onError(paramSyncError, paramString);
    }
    dismiss();
  }
  
  private void onSuccessCallback(Map<String, String> paramMap)
  {
    waitUntilResumedIfNecessary();
    if (this.externalListener != null) {
      this.externalListener.onSuccess(paramMap);
    }
    dismiss();
  }
  
  public RequestListener getPersistentListener()
  {
    return this.persistentListener;
  }
  
  public void setExternalListener(RequestListener paramRequestListener)
  {
    this.externalListener = paramRequestListener;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.CloudRequestProgressDialogFragment
 * JD-Core Version:    0.7.0.1
 */