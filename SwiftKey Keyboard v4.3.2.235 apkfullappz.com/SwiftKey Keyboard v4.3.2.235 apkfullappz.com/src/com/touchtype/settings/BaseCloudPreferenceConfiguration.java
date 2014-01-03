package com.touchtype.settings;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.IBinder;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.widget.Toast;
import com.touchtype.cloud.CloudService;
import com.touchtype.cloud.CloudService.LocalBinder;
import com.touchtype.keyboard.concurrent.ThreadUtils;
import com.touchtype.sync.client.RequestListener;
import com.touchtype.sync.client.RequestListener.SyncError;
import com.touchtype.util.LogUtil;
import java.util.Map;

public abstract class BaseCloudPreferenceConfiguration
  extends PreferenceWrapper
{
  private CloudService cloudService;
  private ServiceConnection cloudServiceConnection = new ServiceConnection()
  {
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      synchronized (BaseCloudPreferenceConfiguration.this.syncServiceToken)
      {
        BaseCloudPreferenceConfiguration.access$102(BaseCloudPreferenceConfiguration.this, ((CloudService.LocalBinder)paramAnonymousIBinder).getService());
        BaseCloudPreferenceConfiguration.this.syncServiceToken.notify();
        BaseCloudPreferenceConfiguration.this.onServiceBound();
        return;
      }
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      BaseCloudPreferenceConfiguration.access$102(BaseCloudPreferenceConfiguration.this, null);
    }
  };
  private Object syncServiceToken = new Object();
  
  public BaseCloudPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
  }
  
  public BaseCloudPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
  }
  
  public void cleanup()
  {
    if (isBound()) {}
    synchronized (this.syncServiceToken)
    {
      getActivity().unbindService(this.cloudServiceConnection);
      this.cloudService = null;
      return;
    }
  }
  
  protected void deleteAccountAndReturnToMainSettings()
  {
    getCloudService().resetCloudState();
    returnToMainSettings();
  }
  
  protected CloudService getCloudService()
  {
    return this.cloudService;
  }
  
  protected String getString(int paramInt)
  {
    return getContext().getResources().getString(paramInt);
  }
  
  protected boolean isBound()
  {
    return this.cloudService != null;
  }
  
  public void onResume() {}
  
  protected abstract void onServiceBound();
  
  protected void returnToMainSettings()
  {
    if (getContext() != null)
    {
      getActivity().finish();
      Intent localIntent = new Intent(getContext(), TouchTypeKeyboardSettings.class);
      localIntent.addFlags(67108864);
      localIntent.addFlags(268435456);
      getContext().startActivity(localIntent);
    }
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    paramPreferenceActivity.bindService(new Intent(paramPreferenceActivity, CloudService.class), this.cloudServiceConnection, 1);
  }
  
  protected boolean shouldUpdateUiFromBackground()
  {
    return (isBound()) && (getContext() != null);
  }
  
  protected void showToast(final int paramInt)
  {
    if (shouldUpdateUiFromBackground()) {
      runOnUiThread(new Runnable()
      {
        public void run()
        {
          if (BaseCloudPreferenceConfiguration.this.getContext() != null) {
            Toast.makeText(BaseCloudPreferenceConfiguration.this.getContext(), paramInt, 0).show();
          }
        }
      });
    }
  }
  
  protected boolean waitForSyncServiceIfNecessary()
  {
    if (!ThreadUtils.onMainThread()) {}
    for (;;)
    {
      synchronized (this.syncServiceToken)
      {
        CloudService localCloudService = this.cloudService;
        if (localCloudService == null) {}
        try
        {
          this.syncServiceToken.wait(5000L);
          if (this.cloudService == null) {
            break;
          }
          return true;
        }
        catch (InterruptedException localInterruptedException)
        {
          LogUtil.e("BaseCloudPreferenceConfiguration", "Interrupted whilst waiting for syncService to bind");
          continue;
        }
      }
      LogUtil.w("BaseCloudPreferenceConfiguration", "waitForSyncServiceIfNecessary called from main thread");
    }
    return false;
  }
  
  protected abstract class SafeRequestListener
    implements RequestListener
  {
    protected SafeRequestListener() {}
    
    public void onError(RequestListener.SyncError paramSyncError, String paramString)
    {
      if (BaseCloudPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) {
        onErrorSafe(paramSyncError, paramString);
      }
    }
    
    public abstract void onErrorSafe(RequestListener.SyncError paramSyncError, String paramString);
    
    public void onSuccess(Map<String, String> paramMap)
    {
      if (BaseCloudPreferenceConfiguration.this.waitForSyncServiceIfNecessary()) {
        onSuccessSafe(paramMap);
      }
    }
    
    public abstract void onSuccessSafe(Map<String, String> paramMap);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.BaseCloudPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */