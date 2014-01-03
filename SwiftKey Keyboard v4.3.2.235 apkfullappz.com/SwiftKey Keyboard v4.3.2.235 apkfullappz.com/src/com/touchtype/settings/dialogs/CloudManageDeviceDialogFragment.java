package com.touchtype.settings.dialogs;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.os.Bundle;
import com.touchtype.settings.CloudDevicesPreferenceConfiguration;
import com.touchtype.settings.PreferenceConfigFragment;

@TargetApi(11)
public final class CloudManageDeviceDialogFragment
  extends DialogFragment
{
  private CloudManageDeviceDialogHelper dialogHelper;
  
  public static CloudManageDeviceDialogFragment newInstance(int paramInt, Bundle paramBundle)
  {
    CloudManageDeviceDialogFragment localCloudManageDeviceDialogFragment = new CloudManageDeviceDialogFragment();
    paramBundle.putInt("CloudManageDeviceDialogHelper.Type", paramInt);
    localCloudManageDeviceDialogFragment.setArguments(paramBundle);
    return localCloudManageDeviceDialogFragment;
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.dialogHelper = new CloudManageDeviceDialogHelper();
    PreferenceConfigFragment localPreferenceConfigFragment = (PreferenceConfigFragment)getFragmentManager().findFragmentByTag("cloudDevicesPreferenceConfigFragment");
    this.dialogHelper.setConfig((CloudDevicesPreferenceConfiguration)localPreferenceConfigFragment.getConfig());
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    int i = getArguments().getInt("CloudManageDeviceDialogHelper.Type");
    String str1 = getArguments().getString("CloudManageDeviceDialogHelper.DeviceId");
    String str2 = getArguments().getString("CloudManageDeviceDialogHelper.DeviceName");
    boolean bool = getArguments().getBoolean("CloudManageDeviceDialogHelper.IsThisDevice");
    return this.dialogHelper.getDialog(getActivity(), i, str1, str2, bool);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.CloudManageDeviceDialogFragment
 * JD-Core Version:    0.7.0.1
 */