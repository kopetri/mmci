package com.touchtype.settings.dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;
import com.touchtype.settings.CloudDevicesPreferenceConfiguration;

public final class CloudManageDeviceDialogHelper
{
  private CloudDevicesPreferenceConfiguration config;
  
  private DialogInterface.OnClickListener getChangeDeviceNameListener(final EditText paramEditText, final String paramString1, final String paramString2, final Context paramContext, final boolean paramBoolean)
  {
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramBoolean)
        {
          String str = paramEditText.getText().toString().trim();
          if (TextUtils.isEmpty(str)) {
            break label61;
          }
          if (!str.equals(paramString2)) {
            CloudManageDeviceDialogHelper.this.config.onConfirmedNewDeviceName(paramString1, str);
          }
        }
        for (;;)
        {
          paramAnonymousDialogInterface.cancel();
          return;
          label61:
          Toast.makeText(paramContext, 2131296557, 0).show();
        }
      }
    };
  }
  
  private DialogInterface.OnClickListener getDeleteDeviceListener(final String paramString1, final String paramString2, final boolean paramBoolean)
  {
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramBoolean) {
          CloudManageDeviceDialogHelper.this.config.onConfirmedDeleteDevice(paramString1, paramString2);
        }
        paramAnonymousDialogInterface.cancel();
      }
    };
  }
  
  private DialogInterface.OnClickListener getManageDeviceListener(final String paramString1, final String paramString2, final boolean paramBoolean)
  {
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramBoolean) {
          switch (paramAnonymousInt)
          {
          }
        }
        for (;;)
        {
          paramAnonymousDialogInterface.cancel();
          return;
          CloudManageDeviceDialogHelper.this.config.onRequestChangeDeviceName(paramString1, paramString2, paramBoolean);
          continue;
          CloudManageDeviceDialogHelper.this.config.onRequestDeleteDevice(paramString1, paramString2, paramBoolean);
          continue;
          if (paramAnonymousInt == 0) {
            CloudManageDeviceDialogHelper.this.config.onRequestDeleteDevice(paramString1, paramString2, paramBoolean);
          }
        }
      }
    };
  }
  
  private String[] getManageDeviceOptions(Context paramContext, boolean paramBoolean)
  {
    if (paramBoolean)
    {
      String[] arrayOfString2 = new String[2];
      arrayOfString2[0] = paramContext.getResources().getString(2131296553);
      arrayOfString2[1] = paramContext.getResources().getString(2131296560);
      return arrayOfString2;
    }
    String[] arrayOfString1 = new String[1];
    arrayOfString1[0] = paramContext.getResources().getString(2131296560);
    return arrayOfString1;
  }
  
  public Dialog getDialog(final Context paramContext, int paramInt, String paramString1, String paramString2, boolean paramBoolean)
  {
    switch (paramInt)
    {
    default: 
      return new AlertDialog.Builder(paramContext).setTitle(paramString2).setItems(getManageDeviceOptions(paramContext, paramBoolean), getManageDeviceListener(paramString1, paramString2, paramBoolean)).create();
    case 3: 
      ProgressDialog localProgressDialog2 = new ProgressDialog(paramContext);
      ((ProgressDialog)localProgressDialog2).setMessage(paramContext.getString(2131296558));
      return localProgressDialog2;
    case 4: 
      ProgressDialog localProgressDialog1 = new ProgressDialog(paramContext);
      ((ProgressDialog)localProgressDialog1).setMessage(paramContext.getString(2131296565));
      return localProgressDialog1;
    case 1: 
      FrameLayout localFrameLayout = new FrameLayout(paramContext);
      int j = paramContext.getResources().getDimensionPixelSize(2131361894);
      localFrameLayout.setPadding(j, j, j, j);
      final EditText localEditText = new EditText(paramContext);
      localEditText.setText(paramString2);
      localEditText.setFocusable(true);
      localEditText.selectAll();
      localEditText.setSingleLine();
      localEditText.post(new Runnable()
      {
        public void run()
        {
          ((InputMethodManager)paramContext.getSystemService("input_method")).showSoftInput(localEditText, 1);
        }
      });
      localFrameLayout.addView(localEditText);
      return new AlertDialog.Builder(paramContext).setTitle(2131296554).setView(localFrameLayout).setPositiveButton(2131296555, getChangeDeviceNameListener(localEditText, paramString1, paramString2, paramContext, true)).setNegativeButton(2131296556, getChangeDeviceNameListener(localEditText, paramString1, paramString2, paramContext, false)).create();
    }
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramContext).setTitle(paramString2);
    if (paramBoolean) {}
    for (int i = 2131296562;; i = 2131296561) {
      return localBuilder.setMessage(i).setPositiveButton(2131296563, getDeleteDeviceListener(paramString1, paramString2, true)).setNegativeButton(2131296564, getDeleteDeviceListener(paramString1, paramString2, false)).create();
    }
  }
  
  public void setConfig(CloudDevicesPreferenceConfiguration paramCloudDevicesPreferenceConfiguration)
  {
    this.config = paramCloudDevicesPreferenceConfiguration;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.CloudManageDeviceDialogHelper
 * JD-Core Version:    0.7.0.1
 */