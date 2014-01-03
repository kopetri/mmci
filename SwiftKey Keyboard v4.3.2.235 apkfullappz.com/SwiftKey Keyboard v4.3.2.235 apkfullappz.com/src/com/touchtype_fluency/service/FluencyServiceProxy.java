package com.touchtype_fluency.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.touchtype.social.UserNotificationManager;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Predictions;
import com.touchtype_fluency.ResultsFilter;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.Session;
import com.touchtype_fluency.TouchHistory;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.layouts.LayoutManager;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

public class FluencyServiceProxy
  implements FluencyServiceProxyI
{
  private static final String TAG = "FluencyServiceProxy";
  private ServiceConnection fluencyServiceConnection = new ServiceConnection()
  {
    FluencyServiceImpl.Listener mListener;
    
    public void onServiceConnected(ComponentName paramAnonymousComponentName, IBinder paramAnonymousIBinder)
    {
      final FluencyServiceImpl localFluencyServiceImpl = (FluencyServiceImpl)((FluencyServiceImpl.LocalBinder)paramAnonymousIBinder).getService();
      this.mListener = new FluencyServiceImpl.Listener()
      {
        public void onReady()
        {
          FluencyServiceProxy.access$002(FluencyServiceProxy.this, localFluencyServiceImpl);
          FluencyServiceProxy.this.onServiceConnected();
          Iterator localIterator = FluencyServiceProxy.this.mDeferredActions.iterator();
          while (localIterator.hasNext()) {
            ((Runnable)localIterator.next()).run();
          }
          FluencyServiceProxy.this.mDeferredActions.clear();
        }
      };
      localFluencyServiceImpl.addListener(this.mListener);
    }
    
    public void onServiceDisconnected(ComponentName paramAnonymousComponentName)
    {
      FluencyServiceProxy.access$202(FluencyServiceProxy.this, false);
      FluencyServiceProxy.access$002(FluencyServiceProxy.this, null);
      FluencyServiceProxy.this.mDeferredActions.clear();
    }
  };
  private boolean mBound = false;
  private Vector<Runnable> mDeferredActions = new Vector();
  private FluencyService mFluencyService;
  
  public static Date getExpiry()
  {
    return FluencyServiceImpl.getExpiry();
  }
  
  private void unbindFluencyService(Context paramContext)
  {
    if (this.mBound) {}
    try
    {
      paramContext.unbindService(this.fluencyServiceConnection);
      label15:
      this.mBound = false;
      this.mFluencyService = null;
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      break label15;
    }
  }
  
  public DynamicModelHandler getDynamicModelHandler()
  {
    if (this.mFluencyService != null) {
      return this.mFluencyService.getDynamicModelHandler();
    }
    return null;
  }
  
  public File getFolder()
  {
    return this.mFluencyService.getFolder();
  }
  
  public LanguagePackManager getLanguagePackManager()
  {
    if (this.mFluencyService != null) {
      return this.mFluencyService.getLanguagePackManager();
    }
    return null;
  }
  
  public LayoutManager getLayoutManager()
  {
    return this.mFluencyService.getLayoutManager();
  }
  
  public Predictions getPredictions(Sequence paramSequence, TouchHistory paramTouchHistory, ResultsFilter paramResultsFilter)
    throws PredictorNotReadyException
  {
    Predictor localPredictor = getPredictor();
    if ((!isReady()) || (localPredictor == null)) {
      throw new PredictorNotReadyException();
    }
    return localPredictor.getPredictions(paramSequence, paramTouchHistory, paramResultsFilter);
  }
  
  public Predictor getPredictor()
  {
    if (this.mFluencyService != null) {
      return this.mFluencyService.getPredictor();
    }
    LogUtil.e("FluencyServiceProxy", "Fluency service was null when a predictor was requested");
    return null;
  }
  
  public Session getSession()
  {
    if (this.mFluencyService != null) {
      return this.mFluencyService.getSession();
    }
    LogUtil.e("FluencyServiceProxy", "Fluency service was null when a session was requested");
    return null;
  }
  
  public UserNotificationManager getUserNotificationManager()
  {
    if (this.mFluencyService != null) {
      return this.mFluencyService.getUserNotificationManager();
    }
    return null;
  }
  
  public boolean isReady()
  {
    return this.mFluencyService != null;
  }
  
  public void onCreate(Context paramContext)
  {
    Intent localIntent = new Intent(paramContext, FluencyServiceImpl.class);
    paramContext.startService(localIntent);
    this.mBound = paramContext.bindService(localIntent, this.fluencyServiceConnection, 1);
    if (!this.mBound)
    {
      this.mBound = paramContext.bindService(localIntent, this.fluencyServiceConnection, 1);
      if (!this.mBound) {
        LogUtil.e(paramContext.getPackageName(), "FluencyService is unbound!");
      }
    }
  }
  
  public void onDestroy(Context paramContext)
  {
    unbindFluencyService(paramContext);
  }
  
  protected void onServiceConnected() {}
  
  public void runWhenConnected(Runnable paramRunnable)
  {
    if (this.mFluencyService != null)
    {
      paramRunnable.run();
      return;
    }
    this.mDeferredActions.add(paramRunnable);
  }
  
  public void saveFluencyMetrics()
  {
    if (this.mFluencyService != null) {
      this.mFluencyService.saveFluencyMetrics();
    }
  }
  
  public void showManagementUI(Context paramContext)
  {
    this.mFluencyService.showManagementUI(paramContext);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyServiceProxy
 * JD-Core Version:    0.7.0.1
 */