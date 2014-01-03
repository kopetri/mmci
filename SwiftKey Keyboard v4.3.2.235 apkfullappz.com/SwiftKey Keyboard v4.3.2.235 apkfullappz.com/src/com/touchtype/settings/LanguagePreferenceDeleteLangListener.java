package com.touchtype.settings;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.widget.Toast;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.io.IOException;
import java.util.Vector;

public final class LanguagePreferenceDeleteLangListener
  implements DialogInterface.OnClickListener
{
  private final int mDialogId;
  private final LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
  
  public LanguagePreferenceDeleteLangListener(LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration, int paramInt)
  {
    this.mLanguagePreferenceConfiguration = paramLanguagePreferenceConfiguration;
    this.mDialogId = paramInt;
  }
  
  public void onClick(DialogInterface paramDialogInterface, int paramInt)
  {
    paramDialogInterface.dismiss();
    Vector localVector = this.mLanguagePreferenceConfiguration.getDisabledLanguages();
    LanguagePackManager localLanguagePackManager = this.mLanguagePreferenceConfiguration.getLanguagePackManager();
    LanguagePack localLanguagePack;
    if (paramInt < localVector.size()) {
      localLanguagePack = (LanguagePack)localVector.get(paramInt);
    }
    try
    {
      localLanguagePackManager.deleteLanguage(localLanguagePack);
      this.mLanguagePreferenceConfiguration.createWidgets();
      this.mLanguagePreferenceConfiguration.removeDialog(this.mDialogId);
      return;
    }
    catch (IOException localIOException)
    {
      for (;;)
      {
        Toast.makeText(this.mLanguagePreferenceConfiguration.getApplicationContext(), 2131297202, 1).show();
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.LanguagePreferenceDeleteLangListener
 * JD-Core Version:    0.7.0.1
 */