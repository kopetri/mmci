package com.touchtype.settings;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.preference.Preference;
import android.widget.Toast;
import com.touchtype.settings.custompreferences.PreferenceUpdateLanguagesButton;
import com.touchtype_fluency.service.languagepacks.LanguagePackManager;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener;
import java.util.Vector;

public class ManagerLanguagePackListener
  implements LanguagePackListener
{
  private static final String TAG = ManagerLanguagePackListener.class.getSimpleName();
  private LanguagePreferenceConfiguration languagePackConfiguration;
  
  public ManagerLanguagePackListener(LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    this.languagePackConfiguration = paramLanguagePreferenceConfiguration;
  }
  
  public boolean isDeferrable()
  {
    return false;
  }
  
  public boolean onChange(final Context paramContext)
  {
    LanguagePackManager localLanguagePackManager;
    Activity localActivity;
    PreferenceUpdateLanguagesButton localPreferenceUpdateLanguagesButton;
    if (this.languagePackConfiguration.isUpdatingInProgress())
    {
      localLanguagePackManager = this.languagePackConfiguration.getLanguagePackManager();
      localActivity = this.languagePackConfiguration.getActivity();
      boolean bool = localLanguagePackManager.isDownloadConfigurationSuccess();
      this.languagePackConfiguration.setUpdatingInProgress(false);
      localPreferenceUpdateLanguagesButton = this.languagePackConfiguration.getUpdateButton();
      if ((localPreferenceUpdateLanguagesButton != null) && (localPreferenceUpdateLanguagesButton.isEnabled())) {
        localPreferenceUpdateLanguagesButton.setEnabled(true);
      }
      if (!bool) {
        break label171;
      }
    }
    for (;;)
    {
      synchronized (localLanguagePackManager.getLanguagePacks())
      {
        i = ???.size() - this.languagePackConfiguration.getLanguagePackCount();
        Resources localResources = paramContext.getResources();
        Object[] arrayOfObject = new Object[1];
        if (i > 0)
        {
          arrayOfObject[0] = Integer.valueOf(i);
          localActivity.runOnUiThread(new Runnable()
          {
            public void run()
            {
              Toast.makeText(paramContext, this.val$text, 1).show();
            }
          });
          this.languagePackConfiguration.createWidgets();
          return false;
        }
      }
      int i = 0;
      continue;
      label171:
      if (localPreferenceUpdateLanguagesButton != null) {
        localPreferenceUpdateLanguagesButton.setEnabled(true);
      }
      localActivity.runOnUiThread(new Runnable()
      {
        public void run()
        {
          Toast.makeText(paramContext, 2131297105, 1).show();
        }
      });
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.ManagerLanguagePackListener
 * JD-Core Version:    0.7.0.1
 */