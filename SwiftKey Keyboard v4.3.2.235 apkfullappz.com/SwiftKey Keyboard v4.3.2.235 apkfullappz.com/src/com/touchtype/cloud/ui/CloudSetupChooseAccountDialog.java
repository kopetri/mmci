package com.touchtype.cloud.ui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import com.touchtype.cloud.CloudSetupActivity;

public final class CloudSetupChooseAccountDialog
  extends CloudSetupDialogFragment
{
  private CloudSetupFragmentCallbacks.CloudSetupChooseGoogleAccountCallback callback;
  
  public static CloudSetupChooseAccountDialog newInstance(String[] paramArrayOfString)
  {
    CloudSetupChooseAccountDialog localCloudSetupChooseAccountDialog = new CloudSetupChooseAccountDialog();
    Bundle localBundle = new Bundle();
    localBundle.putStringArray("CloudSetupChooseAccountDialog.accounts", paramArrayOfString);
    localCloudSetupChooseAccountDialog.setArguments(localBundle);
    return localCloudSetupChooseAccountDialog;
  }
  
  protected void initCallback(CloudSetupActivity paramCloudSetupActivity)
  {
    this.callback = paramCloudSetupActivity.getChooseAccountCallback();
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    final String[] arrayOfString = getArguments().getStringArray("CloudSetupChooseAccountDialog.accounts");
    new AlertDialog.Builder(getActivity()).setTitle(2131297264).setItems(arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        CloudSetupChooseAccountDialog.this.callback.onChoseAccount(arrayOfString[paramAnonymousInt]);
      }
    }).create();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.cloud.ui.CloudSetupChooseAccountDialog
 * JD-Core Version:    0.7.0.1
 */