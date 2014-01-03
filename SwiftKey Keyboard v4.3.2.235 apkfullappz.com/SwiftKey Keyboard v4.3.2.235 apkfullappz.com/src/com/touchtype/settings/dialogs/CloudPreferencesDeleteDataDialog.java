package com.touchtype.settings.dialogs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;

public class CloudPreferencesDeleteDataDialog
  extends DialogPreference
{
  private CloudDeleteDialogCallback callback;
  
  public CloudPreferencesDeleteDataDialog(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if ((paramBoolean) && (this.callback != null)) {
      this.callback.onPressedOk();
    }
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    paramBuilder.setIcon(17301543);
    super.onPrepareDialogBuilder(paramBuilder);
  }
  
  public void setCallback(CloudDeleteDialogCallback paramCloudDeleteDialogCallback)
  {
    this.callback = paramCloudDeleteDialogCallback;
  }
  
  public static abstract interface CloudDeleteDialogCallback
  {
    public abstract void onPressedOk();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.CloudPreferencesDeleteDataDialog
 * JD-Core Version:    0.7.0.1
 */