package com.google.android.gms.internal;

import android.os.Handler;
import android.os.IInterface;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import java.util.ArrayList;

public abstract class p<T extends IInterface>
  implements GooglePlayServicesClient
{
  public static final String[] aG = { "service_esmobile", "service_googleme" };
  private boolean aA;
  private final ArrayList<p<T>.b<?>> aB;
  private T av;
  private ArrayList<GooglePlayServicesClient.ConnectionCallbacks> aw;
  final ArrayList<GooglePlayServicesClient.ConnectionCallbacks> ax;
  private boolean ay;
  private ArrayList<GooglePlayServicesClient.OnConnectionFailedListener> az;
  final Handler mHandler;
  
  public final void a(p<T>.b<?> paramp)
  {
    synchronized (this.aB)
    {
      this.aB.add(paramp);
      this.mHandler.sendMessage(this.mHandler.obtainMessage(2, paramp));
      return;
    }
  }
  
  public boolean isConnected()
  {
    return this.av != null;
  }
  
  public boolean isConnectionCallbacksRegistered(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    x.d(paramConnectionCallbacks);
    synchronized (this.aw)
    {
      boolean bool = this.aw.contains(paramConnectionCallbacks);
      return bool;
    }
  }
  
  public boolean isConnectionFailedListenerRegistered(GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    x.d(paramOnConnectionFailedListener);
    synchronized (this.az)
    {
      boolean bool = this.az.contains(paramOnConnectionFailedListener);
      return bool;
    }
  }
  
  protected final void n()
  {
    if (!isConnected()) {
      throw new IllegalStateException("Not connected. Call connect() and wait for onConnected() to be called.");
    }
  }
  
  protected final T o()
  {
    n();
    return this.av;
  }
  
  public void registerConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    x.d(paramConnectionCallbacks);
    synchronized (this.aw)
    {
      if (this.aw.contains(paramConnectionCallbacks))
      {
        Log.w("GmsClient", "registerConnectionCallbacks(): listener " + paramConnectionCallbacks + " is already registered");
        if (isConnected()) {
          this.mHandler.sendMessage(this.mHandler.obtainMessage(4, paramConnectionCallbacks));
        }
        return;
      }
      if (this.ay) {
        this.aw = new ArrayList(this.aw);
      }
      this.aw.add(paramConnectionCallbacks);
    }
  }
  
  public void registerConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    x.d(paramOnConnectionFailedListener);
    synchronized (this.az)
    {
      if (this.az.contains(paramOnConnectionFailedListener))
      {
        Log.w("GmsClient", "registerConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " is already registered");
        return;
      }
      if (this.aA) {
        this.az = new ArrayList(this.az);
      }
      this.az.add(paramOnConnectionFailedListener);
    }
  }
  
  public void unregisterConnectionCallbacks(GooglePlayServicesClient.ConnectionCallbacks paramConnectionCallbacks)
  {
    x.d(paramConnectionCallbacks);
    synchronized (this.aw)
    {
      if (this.aw != null)
      {
        if (this.ay) {
          this.aw = new ArrayList(this.aw);
        }
        if (this.aw.remove(paramConnectionCallbacks)) {
          break label82;
        }
        Log.w("GmsClient", "unregisterConnectionCallbacks(): listener " + paramConnectionCallbacks + " not found");
      }
      label82:
      while ((!this.ay) || (this.ax.contains(paramConnectionCallbacks))) {
        return;
      }
      this.ax.add(paramConnectionCallbacks);
    }
  }
  
  public void unregisterConnectionFailedListener(GooglePlayServicesClient.OnConnectionFailedListener paramOnConnectionFailedListener)
  {
    x.d(paramOnConnectionFailedListener);
    synchronized (this.az)
    {
      if (this.az != null)
      {
        if (this.aA) {
          this.az = new ArrayList(this.az);
        }
        if (!this.az.remove(paramOnConnectionFailedListener)) {
          Log.w("GmsClient", "unregisterConnectionFailedListener(): listener " + paramOnConnectionFailedListener + " not found");
        }
      }
      return;
    }
  }
  
  public abstract class b<TListener>
  {
    private boolean aI;
    private TListener mListener;
    
    public b()
    {
      Object localObject;
      this.mListener = localObject;
      this.aI = false;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.gms.internal.p
 * JD-Core Version:    0.7.0.1
 */