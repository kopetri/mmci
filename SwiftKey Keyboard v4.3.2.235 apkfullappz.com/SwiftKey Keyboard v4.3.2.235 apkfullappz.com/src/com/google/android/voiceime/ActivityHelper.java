package com.google.android.voiceime;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import java.util.ArrayList;

public class ActivityHelper
  extends Activity
{
  private ServiceBridge mServiceBridge;
  
  private Dialog createResultDialog(final String[] paramArrayOfString)
  {
    if (Build.VERSION.SDK_INT < 11) {}
    for (AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);; localBuilder = new AlertDialog.Builder(this, 16973937))
    {
      localBuilder.setItems(paramArrayOfString, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ActivityHelper.this.notifyResult(paramArrayOfString[paramAnonymousInt]);
        }
      });
      localBuilder.setCancelable(true);
      localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          ActivityHelper.this.notifyResult(null);
        }
      });
      localBuilder.setNeutralButton(17039360, new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          ActivityHelper.this.notifyResult(null);
        }
      });
      return localBuilder.create();
    }
  }
  
  private void notifyResult(String paramString)
  {
    this.mServiceBridge.notifyResult(this, paramString);
    finish();
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if ((paramInt1 == 1) && (paramIntent != null) && (paramIntent.hasExtra("android.speech.extra.RESULTS")))
    {
      ArrayList localArrayList = paramIntent.getStringArrayListExtra("android.speech.extra.RESULTS");
      createResultDialog((String[])localArrayList.toArray(new String[localArrayList.size()])).show();
      return;
    }
    notifyResult(null);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    this.mServiceBridge = new ServiceBridge();
    Intent localIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
    localIntent.putExtra("calling_package", getClass().getPackage().getName());
    localIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
    localIntent.putExtra("android.speech.extra.MAX_RESULTS", 5);
    if (paramBundle != null)
    {
      String str = paramBundle.getString("android.speech.extra.LANGUAGE");
      if (str != null) {
        localIntent.putExtra("android.speech.extra.LANGUAGE", str);
      }
    }
    startActivityForResult(localIntent, 1);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.google.android.voiceime.ActivityHelper
 * JD-Core Version:    0.7.0.1
 */