package com.touchtype.installer.x;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.Vector;

public final class InstallerDialogListenerBuilder
{
  private XInstaller mInstaller;
  
  public InstallerDialogListenerBuilder(XInstaller paramXInstaller)
  {
    this.mInstaller = paramXInstaller;
  }
  
  public DialogInterface.OnClickListener getListener(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return null;
    }
    new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
        InstallerDialogListenerBuilder.this.mInstaller.downloadLanguage((LanguagePack)this.val$langs.get(paramAnonymousInt));
      }
    };
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.InstallerDialogListenerBuilder
 * JD-Core Version:    0.7.0.1
 */