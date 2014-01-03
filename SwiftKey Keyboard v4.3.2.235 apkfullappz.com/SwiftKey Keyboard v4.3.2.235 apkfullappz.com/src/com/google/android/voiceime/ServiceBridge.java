package com.google.android.voiceime;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

final class ServiceBridge
{
  private final IntentApiTrigger.Callback mCallback;
  
  public ServiceBridge()
  {
    this(null);
  }
  
  public ServiceBridge(IntentApiTrigger.Callback paramCallback)
  {
    this.mCallback = paramCallback;
  }
  
  public void notifyResult(Context paramContext, String paramString)
  {
    ConnectionResponse localConnectionResponse = new ConnectionResponse(paramContext, paramString, null);
    paramContext.bindService(new Intent(paramContext, ServiceHelper.class), localConnectionResponse, 1);
  }
  
  public void startVoiceRecognition(final Context paramContext, String paramString)
  {
    final ConnectionRequest localConnectionRequest = new ConnectionRequest(paramString, null);
    localConnectionRequest.setServiceCallback(new ServiceHelper.Callback()
    {
      public void onResult(String paramAnonymousString)
      {
        ServiceBridge.this.mCallback.onRecognitionResult(paramAnonymousString);
        paramContext.unbindService(localConnectionRequest);
      }
    });
    paramContext.bindService(new Intent(paramContext, ServiceHelper.class), localConnectionRequest, 1);
  }
  
  private final class ConnectionRequest
    implements ServiceConnection
  {
    private final String mLanguageCode;
    private ServiceHelper.Callback mServiceCallback;
    
    private ConnectionRequest(String paramString)
    {
      this.mLanguageCode = paramString;
    }
    
    private void setServiceCallback(ServiceHelper.Callback paramCallback)
    {
      this.mServiceCallback = paramCallback;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      ((ServiceHelper.ServiceHelperBinder)paramIBinder).getService().startRecognition(this.mLanguageCode, this.mServiceCallback);
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName) {}
  }
  
  private final class ConnectionResponse
    implements ServiceConnection
  {
    private final Context mContext;
    private final String mRecognitionResult;
    
    private ConnectionResponse(Context paramContext, String paramString)
    {
      this.mRecognitionResult = paramString;
      this.mContext = paramContext;
    }
    
    public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
    {
      ((ServiceHelper.ServiceHelperBinder)paramIBinder).getService().notifyResult(this.mRecognitionResult);
      this.mContext.unbindService(this);
    }
    
    public void onServiceDisconnected(ComponentName paramComponentName) {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.ServiceBridge
 * JD-Core Version:    0.7.0.1
 */