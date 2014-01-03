package com.touchtype.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import com.touchtype.keyboard.concurrent.ThreadUtils;
import com.touchtype.util.LogUtil;
import java.util.concurrent.CountDownLatch;

public abstract class ProgressDialogFragment
  extends DialogFragment
{
  private boolean fragmentRecreated = false;
  private int progressMessageResId;
  private CountDownLatch resumedLatch = null;
  private Object resumedLock = new Object();
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (paramBundle != null)
    {
      this.progressMessageResId = paramBundle.getInt("progressMessageResId");
      this.fragmentRecreated = true;
    }
    setRetainInstance(true);
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    ProgressDialog localProgressDialog = new ProgressDialog(getActivity());
    localProgressDialog.setMessage(getString(this.progressMessageResId));
    return localProgressDialog;
  }
  
  public void onDestroyView()
  {
    Dialog localDialog = getDialog();
    if ((localDialog != null) && (getRetainInstance())) {
      localDialog.setDismissMessage(null);
    }
    super.onDestroyView();
  }
  
  public void onResume()
  {
    super.onResume();
    synchronized (this.resumedLock)
    {
      if (this.resumedLatch != null) {
        this.resumedLatch.countDown();
      }
      return;
    }
  }
  
  public void onSaveInstanceState(Bundle paramBundle)
  {
    paramBundle.putInt("progressMessageResId", this.progressMessageResId);
  }
  
  public void setProgressMessageResId(int paramInt)
  {
    this.progressMessageResId = paramInt;
  }
  
  protected void waitUntilResumedIfNecessary()
  {
    if (!ThreadUtils.onMainThread())
    {
      if (!isResumed()) {
        synchronized (this.resumedLock)
        {
          this.resumedLatch = new CountDownLatch(1);
        }
      }
      try
      {
        this.resumedLatch.await();
        this.resumedLatch = null;
        return;
        localObject2 = finally;
        throw localObject2;
      }
      catch (InterruptedException localInterruptedException)
      {
        for (;;)
        {
          LogUtil.e("ProgressDialogFragment", "Interrupted whilst waiting for the fragment to return to a resumed state");
        }
      }
    }
    LogUtil.e("ProgressDialogFragment", "waitUntilResumedIfNecessary called on the main thread - ensure it is only called from the background");
  }
  
  public boolean wasRecreated()
  {
    return this.fragmentRecreated;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.ui.ProgressDialogFragment
 * JD-Core Version:    0.7.0.1
 */