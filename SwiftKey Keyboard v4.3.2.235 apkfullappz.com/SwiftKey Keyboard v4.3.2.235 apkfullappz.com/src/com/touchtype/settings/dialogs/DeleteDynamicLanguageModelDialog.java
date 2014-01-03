package com.touchtype.settings.dialogs;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import com.touchtype.settings.AdvancedPreferenceConfiguration;
import junit.framework.Assert;

public class DeleteDynamicLanguageModelDialog
  extends DialogPreference
{
  private AdvancedPreferenceConfiguration mConfiguration = null;
  
  public DeleteDynamicLanguageModelDialog(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setEnabled(false);
  }
  
  protected void onDialogClosed(boolean paramBoolean)
  {
    super.onDialogClosed(paramBoolean);
    if (paramBoolean)
    {
      Assert.assertNotNull("Configuration has not been set", this.mConfiguration);
      this.mConfiguration.clearUserModel();
    }
  }
  
  protected void onPrepareDialogBuilder(AlertDialog.Builder paramBuilder)
  {
    paramBuilder.setIcon(17301543);
    super.onPrepareDialogBuilder(paramBuilder);
  }
  
  public void setConfiguration(AdvancedPreferenceConfiguration paramAdvancedPreferenceConfiguration)
  {
    this.mConfiguration = paramAdvancedPreferenceConfiguration;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.DeleteDynamicLanguageModelDialog
 * JD-Core Version:    0.7.0.1
 */