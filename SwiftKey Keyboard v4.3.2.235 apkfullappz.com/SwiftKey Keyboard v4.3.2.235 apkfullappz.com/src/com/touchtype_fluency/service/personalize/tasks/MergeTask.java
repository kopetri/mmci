package com.touchtype_fluency.service.personalize.tasks;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudService.LocalBinder;
import com.touchtype.preferences.TouchTypePreferences;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.Predictor;
import com.touchtype_fluency.service.PredictorNotReadyException;
import com.touchtype_fluency.service.personalize.PersonalizationFailedReason;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

class MergeTask
  extends PersonalizationTask
{
  private static final int RETRIES = 5;
  private CountDownLatch mAddToSyncModelLatch;
  private CountDownLatch mBindToCloudServiceLatch;
  private CloudService mCloudService;
  private ServiceConnection mCloudServiceConnection;
  private Context mContext;
  private Predictor mPredictor;
  
  public MergeTask(Context paramContext, PersonalizationTaskListener paramPersonalizationTaskListener, Predictor paramPredictor)
  {
    super(null, paramPersonalizationTaskListener, 5);
    this.mContext = paramContext;
    this.mPredictor = paramPredictor;
  }
  
  private void initialiseCloudService()
  {
    this.mBindToCloudServiceLatch = new CountDownLatch(1);
    this.mCloudServiceConnection = new ServiceConnection()
    {
      public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
      {
        MergeTask.access$102(MergeTask.this, ((CloudService.LocalBinder)paramAnonymousIBinder).getService());
        MergeTask.this.mBindToCloudServiceLatch.countDown();
      }
      
      public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
      {
        MergeTask.access$102(MergeTask.this, null);
      }
    };
    this.mContext.bindService(new Intent(this.mContext, CloudService.class), this.mCloudServiceConnection, 1);
  }
  
  public void compute()
  {
    String str = getLocation();
    if (this.mPredictor != null)
    {
      try
      {
        this.mPredictor.mergeUserModel(str);
        if (TouchTypePreferences.getInstance(this.mContext).isCloudAccountSetup())
        {
          initialiseCloudService();
          this.mBindToCloudServiceLatch.await();
          if (this.mCloudService == null) {
            return;
          }
          this.mAddToSyncModelLatch = new CountDownLatch(1);
          this.mCloudService.addLMToSyncModel(str, new RequestListener()
          {
            public void onError(RequestListener.SyncError paramAnonymousSyncError, String paramAnonymousString)
            {
              MergeTask.this.mAddToSyncModelLatch.countDown();
            }
            
            public void onSuccess(Map<String, String> paramAnonymousMap)
            {
              MergeTask.this.mAddToSyncModelLatch.countDown();
            }
          });
          this.mAddToSyncModelLatch.await();
          this.mContext.unbindService(this.mCloudServiceConnection);
          notifyListener(true, null);
          return;
        }
        notifyListener(true, null);
        return;
      }
      catch (PredictorNotReadyException localPredictorNotReadyException)
      {
        LogUtil.w(this.TAG, "Tried to merge models but the predictor wasn't ready", localPredictorNotReadyException);
        notifyListener(false, PersonalizationFailedReason.OTHER);
        return;
      }
      catch (InterruptedException localInterruptedException)
      {
        LogUtil.w(this.TAG, "Interrupted whilst waiting for sync service to bind", localInterruptedException);
        notifyListener(true, null);
        return;
      }
    }
    else
    {
      LogUtil.w(this.TAG, "Predictor was null");
      notifyListener(false, PersonalizationFailedReason.OTHER);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.tasks.MergeTask
 * JD-Core Version:    0.7.0.1
 */