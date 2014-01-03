package com.touchtype.cloud.ui;

import android.app.Activity;
import android.support.v4.app.DialogFragment;
import com.touchtype.cloud.CloudSetupActivity;
import com.touchtype.util.LogUtil;

public abstract class CloudSetupDialogFragment
  extends DialogFragment
{
  protected abstract void initCallback(CloudSetupActivity paramCloudSetupActivity);
  
  public void onAttach(Activity paramActivity)
  {
    super.onAttach(paramActivity);
    try
    {
      initCallback((CloudSetupActivity)paramActivity);
      return;
    }
    catch (ClassCastException localClassCastException)
    {
      LogUtil.e("CloudSetupDialogFragment", "Host activity for this DialogFragment must be a CloudSetupActivity");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.ui.CloudSetupDialogFragment
 * JD-Core Version:    0.7.0.1
 */