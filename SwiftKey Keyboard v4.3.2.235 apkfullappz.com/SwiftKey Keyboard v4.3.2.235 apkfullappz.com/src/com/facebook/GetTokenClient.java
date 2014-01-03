package com.facebook;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

final class GetTokenClient
  implements ServiceConnection
{
  final String applicationId;
  final Context context;
  final Handler handler;
  CompletedListener listener;
  boolean running;
  Messenger sender;
  
  GetTokenClient(Context paramContext, String paramString)
  {
    Context localContext = paramContext.getApplicationContext();
    if (localContext != null) {}
    for (;;)
    {
      this.context = localContext;
      this.applicationId = paramString;
      this.handler = new Handler()
      {
        public void handleMessage(Message paramAnonymousMessage)
        {
          GetTokenClient.this.handleMessage(paramAnonymousMessage);
        }
      };
      return;
      localContext = paramContext;
    }
  }
  
  private void callback(Bundle paramBundle)
  {
    if (!this.running) {}
    CompletedListener localCompletedListener;
    do
    {
      return;
      this.running = false;
      localCompletedListener = this.listener;
    } while (localCompletedListener == null);
    localCompletedListener.completed(paramBundle);
  }
  
  private void getToken()
  {
    Bundle localBundle = new Bundle();
    localBundle.putString("com.facebook.platform.extra.APPLICATION_ID", this.applicationId);
    Message localMessage = Message.obtain(null, 65536);
    localMessage.arg1 = 20121101;
    localMessage.setData(localBundle);
    localMessage.replyTo = new Messenger(this.handler);
    try
    {
      this.sender.send(localMessage);
      return;
    }
    catch (RemoteException localRemoteException)
    {
      callback(null);
    }
  }
  
  private void handleMessage(Message paramMessage)
  {
    Bundle localBundle;
    if (paramMessage.what == 65537)
    {
      localBundle = paramMessage.getData();
      if (localBundle.getString("com.facebook.platform.status.ERROR_TYPE") == null) {
        break label37;
      }
      callback(null);
    }
    for (;;)
    {
      this.context.unbindService(this);
      return;
      label37:
      callback(localBundle);
    }
  }
  
  void cancel()
  {
    this.running = false;
  }
  
  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    this.sender = new Messenger(paramIBinder);
    getToken();
  }
  
  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    this.sender = null;
    this.context.unbindService(this);
    callback(null);
  }
  
  void setCompletedListener(CompletedListener paramCompletedListener)
  {
    this.listener = paramCompletedListener;
  }
  
  boolean start()
  {
    Intent localIntent1 = new Intent("com.facebook.platform.PLATFORM_SERVICE");
    localIntent1.addCategory("android.intent.category.DEFAULT");
    Intent localIntent2 = NativeProtocol.validateKatanaServiceIntent(this.context, localIntent1);
    if (localIntent2 == null)
    {
      callback(null);
      return false;
    }
    this.running = true;
    this.context.bindService(localIntent2, this, 1);
    return true;
  }
  
  static abstract interface CompletedListener
  {
    public abstract void completed(Bundle paramBundle);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.GetTokenClient
 * JD-Core Version:    0.7.0.1
 */