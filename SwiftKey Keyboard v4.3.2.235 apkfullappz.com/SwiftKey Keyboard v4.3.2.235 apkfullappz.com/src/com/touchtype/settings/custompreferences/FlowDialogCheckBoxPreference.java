package com.touchtype.settings.custompreferences;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.CheckBoxPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;

public class FlowDialogCheckBoxPreference
  extends CheckBoxPreference
{
  public FlowDialogCheckBoxPreference(Context paramContext)
  {
    super(paramContext);
  }
  
  public FlowDialogCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public FlowDialogCheckBoxPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onClick()
  {
    super.onClick();
    Context localContext = getContext();
    Resources localResources = localContext.getResources();
    if (getSharedPreferences().getBoolean(getKey(), localResources.getBoolean(2131492870)))
    {
      setChecked(false);
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(localContext);
      LayoutInflater localLayoutInflater = (LayoutInflater)localContext.getSystemService("layout_inflater");
      localBuilder.setTitle(localResources.getString(2131296493));
      localBuilder.setView(localLayoutInflater.inflate(2130903074, null));
      localBuilder.setPositiveButton(localResources.getString(2131297275), new DialogInterface.OnClickListener()
      {
        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          FlowDialogCheckBoxPreference.this.setChecked(true);
        }
      });
      localBuilder.setNegativeButton(localResources.getString(2131297276), null);
      localBuilder.create().show();
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.FlowDialogCheckBoxPreference
 * JD-Core Version:    0.7.0.1
 */