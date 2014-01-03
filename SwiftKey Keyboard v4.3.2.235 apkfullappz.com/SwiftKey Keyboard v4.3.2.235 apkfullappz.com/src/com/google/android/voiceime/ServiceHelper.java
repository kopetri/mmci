package com.google.android.voiceime;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ServiceHelper
  extends Service
{
  private final IBinder mBinder = new ServiceHelperBinder();
  private Callback mCallback;
  
  public void notifyResult(String paramString)
  {
    if (this.mCallback != null) {
      this.mCallback.onResult(paramString);
    }
  }
  
  public IBinder onBind(Intent paramIntent)
  {
    return this.mBinder;
  }
  
  public void onCreate()
  {
    super.onCreate();
  }
  
  public void onDestroy()
  {
    super.onDestroy();
  }
  
  public void startRecognition(String paramString, Callback paramCallback)
  {
    this.mCallback = paramCallback;
    Intent localIntent = new Intent(this, ActivityHelper.class);
    localIntent.addFlags(268435456);
    startActivity(localIntent);
  }
  
  public static abstract interface Callback
  {
    public abstract void onResult(String paramString);
  }
  
  public final class ServiceHelperBinder
    extends Binder
  {
    public ServiceHelperBinder() {}
    
    ServiceHelper getService()
    {
      return ServiceHelper.this;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.ServiceHelper
 * JD-Core Version:    0.7.0.1
 */