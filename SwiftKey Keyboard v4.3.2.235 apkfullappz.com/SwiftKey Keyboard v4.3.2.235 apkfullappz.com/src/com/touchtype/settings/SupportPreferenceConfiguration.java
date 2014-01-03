package com.touchtype.settings;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.touchtype.AsyncTaskListener;
import com.touchtype.common.util.FileUtils;
import com.touchtype.logcat.AWSClientPutTask;
import com.touchtype.logcat.LogcatDumper;
import com.touchtype.settings.dialogs.AWSLogcatUploaderDialogFragment;
import java.io.File;

public final class SupportPreferenceConfiguration
  extends PreferenceWrapper
  implements AsyncTaskListener<Integer>
{
  public SupportPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
  }
  
  public SupportPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
  }
  
  private AlertDialog getSendLogcatDialog(Context paramContext)
  {
    new AlertDialog.Builder(getActivity()).setTitle(paramContext.getString(2131296836)).setMessage(paramContext.getString(2131296838)).setPositiveButton(paramContext.getString(2131296826), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        if (paramAnonymousInt == -1) {
          SupportPreferenceConfiguration.this.sendLogcat(SupportPreferenceConfiguration.this.getContext());
        }
        paramAnonymousDialogInterface.cancel();
      }
    }).setNegativeButton(paramContext.getString(2131296827), null).create();
  }
  
  private void sendLogcat(Context paramContext)
  {
    File localFile1 = LogcatDumper.getLatestLogFile(paramContext);
    if ((localFile1 != null) && (localFile1.exists()))
    {
      File localFile2 = FileUtils.compressFileToZip(localFile1, ".log");
      if (localFile2 != null)
      {
        new AWSClientPutTask(this, paramContext, localFile1, localFile2).execute(new Void[0]);
        return;
      }
      Toast.makeText(paramContext, paramContext.getString(2131296842), 0).show();
      return;
    }
    Toast.makeText(paramContext, paramContext.getString(2131296843), 0).show();
  }
  
  public DialogFragment getDialogFragment(int paramInt)
  {
    return new AWSLogcatUploaderDialogFragment();
  }
  
  public void onCancelledTask(String paramString, Integer paramInteger)
  {
    if (paramString.equals(AWSClientPutTask.class.getSimpleName())) {
      removeDialog(0);
    }
  }
  
  public Dialog onCreateDialog(final int paramInt, Bundle paramBundle)
  {
    ProgressDialog localProgressDialog = new ProgressDialog(getContext());
    localProgressDialog.setMessage(getContext().getString(2131296839));
    localProgressDialog.setCancelable(false);
    localProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        SupportPreferenceConfiguration.this.removeDialog(paramInt);
      }
    });
    return localProgressDialog;
  }
  
  public void onPostExecuteTask(String paramString, Integer paramInteger)
  {
    if (paramString.equals(AWSClientPutTask.class.getSimpleName())) {
      removeDialog(0);
    }
  }
  
  public void onPreExecuteTask(String paramString)
  {
    if (paramString.equals(AWSClientPutTask.class.getSimpleName())) {
      showDialog(0);
    }
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    final Context localContext = getContext();
    PreferenceScreen localPreferenceScreen = (PreferenceScreen)findPreference(localContext.getString(2131296835));
    if (localPreferenceScreen != null)
    {
      if (!localContext.getResources().getBoolean(2131492931)) {
        getPreferenceScreen().removePreference(localPreferenceScreen);
      }
    }
    else {
      return;
    }
    localPreferenceScreen.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener()
    {
      public boolean onPreferenceClick(Preference paramAnonymousPreference)
      {
        SupportPreferenceConfiguration.this.getSendLogcatDialog(localContext).show();
        return true;
      }
    });
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.SupportPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */