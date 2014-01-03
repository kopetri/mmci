package com.touchtype.keyboard.inputeventmodel;

import android.os.Handler;
import android.view.inputmethod.InputConnection;

public class ChromeBrowserWorkaround
{
  private static final String TAG = ChromeBrowserWorkaround.class.getSimpleName();
  private CharSequence cachedCurrentWordContext;
  private int currentCount;
  private final Handler handler = new Handler();
  private final MinimalInputMethodService inputMethodService;
  private final PredictionsRequester predictionRequester;
  private Runnable refreshCurrentWordContextTask = new Runnable()
  {
    private boolean contextMismatch(CharSequence paramAnonymousCharSequence)
    {
      if (ChromeBrowserWorkaround.this.cachedCurrentWordContext == null) {
        if (paramAnonymousCharSequence == null) {}
      }
      while (!ChromeBrowserWorkaround.this.cachedCurrentWordContext.equals(paramAnonymousCharSequence))
      {
        return true;
        return false;
      }
      return false;
    }
    
    public void run()
    {
      if (ChromeBrowserWorkaround.access$010(ChromeBrowserWorkaround.this) > 0)
      {
        CharSequence localCharSequence = ChromeBrowserWorkaround.this.readCurrentWordContext();
        if (contextMismatch(localCharSequence))
        {
          ChromeBrowserWorkaround.access$102(ChromeBrowserWorkaround.this, localCharSequence);
          ChromeBrowserWorkaround.this.invalidateCandidates();
        }
        ChromeBrowserWorkaround.this.scheduleRefresh();
      }
    }
  };
  private boolean started;
  
  public ChromeBrowserWorkaround(MinimalInputMethodService paramMinimalInputMethodService, PredictionsRequester paramPredictionsRequester)
  {
    this.inputMethodService = paramMinimalInputMethodService;
    this.predictionRequester = paramPredictionsRequester;
  }
  
  private void scheduleRefresh()
  {
    delayTask(this.refreshCurrentWordContextTask);
  }
  
  protected void delayTask(Runnable paramRunnable)
  {
    this.handler.removeCallbacks(paramRunnable);
    this.handler.postDelayed(paramRunnable, 50L);
  }
  
  protected void invalidateCandidates()
  {
    this.predictionRequester.invalidateCandidates(false);
  }
  
  public void onStartInput(String paramString)
  {
    if ((paramString != null) && (EditorInfoUtils.isChromeBrowser(paramString)))
    {
      this.started = true;
      return;
    }
    this.started = false;
  }
  
  protected CharSequence readCurrentWordContext()
  {
    InputConnection localInputConnection = this.inputMethodService.getCurrentInputConnection();
    if (localInputConnection == null) {
      return "";
    }
    return localInputConnection.getTextBeforeCursor(5, 0);
  }
  
  public void recordCurrentWordContext()
  {
    if (this.started)
    {
      this.cachedCurrentWordContext = readCurrentWordContext();
      this.currentCount = 40;
      scheduleRefresh();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.keyboard.inputeventmodel.ChromeBrowserWorkaround
 * JD-Core Version:    0.7.0.1
 */