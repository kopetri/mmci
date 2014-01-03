package com.touchtype.settings.dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import com.touchtype.preferences.PrioritisedChooserActivity;
import com.touchtype.util.EnvironmentInfoUtil;

public class EmailDialogPreference
  extends DialogPreference
{
  public EmailDialogPreference(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public EmailDialogPreference(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  private void launchEmailToIntent(Context paramContext)
  {
    if (getKey().contentEquals(paramContext.getString(2131296848)))
    {
      String str1 = paramContext.getString(2131296846);
      String str2 = paramContext.getString(2131296847);
      String str3 = "\n\n----------\n" + EnvironmentInfoUtil.getApplicationInfo(paramContext);
      Intent localIntent = new Intent("android.intent.action.SENDTO", Uri.parse("mailto:" + str1));
      localIntent.putExtra("android.intent.extra.SUBJECT", str2);
      localIntent.putExtra("android.intent.extra.TEXT", str3);
      paramContext.startActivity(PrioritisedChooserActivity.createChooser(paramContext, localIntent, paramContext.getString(2131296849)));
    }
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    super.onClick(paramDialogInterface, paramInt);
    if (paramInt == -1) {
      launchEmailToIntent(getContext());
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.EmailDialogPreference
 * JD-Core Version:    0.7.0.1
 */