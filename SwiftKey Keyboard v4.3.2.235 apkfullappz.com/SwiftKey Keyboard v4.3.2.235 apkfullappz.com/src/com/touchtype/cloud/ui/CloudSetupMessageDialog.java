package com.touchtype.cloud.ui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.touchtype.cloud.CloudSetupActivity;

public final class CloudSetupMessageDialog
  extends CloudSetupDialogFragment
{
  private CloudSetupFragmentCallbacks.CloudSetupBasicDialogCallback callback;
  
  public static CloudSetupMessageDialog newInstance(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    CloudSetupMessageDialog localCloudSetupMessageDialog = new CloudSetupMessageDialog();
    Bundle localBundle = new Bundle();
    localBundle.putString("CloudSetupMessageDialog.Title", paramString1);
    localBundle.putString("CloudSetupMessageDialog.Message", paramString2);
    localBundle.putString("CloudSetupMessageDialog.ButtonText", paramString3);
    localBundle.putBoolean("CloudSetupMessageDialog.Cancelable", paramBoolean);
    localCloudSetupMessageDialog.setArguments(localBundle);
    return localCloudSetupMessageDialog;
  }
  
  protected void initCallback(CloudSetupActivity paramCloudSetupActivity)
  {
    this.callback = paramCloudSetupActivity.getMessageDialogCallback();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    String str1 = getArguments().getString("CloudSetupMessageDialog.Title");
    String str2 = getArguments().getString("CloudSetupMessageDialog.Message");
    String str3 = getArguments().getString("CloudSetupMessageDialog.ButtonText");
    boolean bool = getArguments().getBoolean("CloudSetupMessageDialog.Cancelable");
    new AlertDialog.Builder(getActivity()).setTitle(str1).setMessage(str2).setPositiveButton(str3, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        CloudSetupMessageDialog.this.getTag();
      }
    }).setCancelable(bool).create();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.ui.CloudSetupMessageDialog
 * JD-Core Version:    0.7.0.1
 */