package com.touchtype.settings.dialogs;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnKeyListener;
import android.content.res.Resources;
import android.view.KeyEvent;
import com.touchtype.settings.LanguagePreferenceConfiguration;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.Iterator;
import java.util.Vector;

public final class LanguagePreferenceDialog
{
  private LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
  
  public LanguagePreferenceDialog(LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    this.mLanguagePreferenceConfiguration = paramLanguagePreferenceConfiguration;
  }
  
  public AlertDialog getDialog(int paramInt, DialogInterface.OnClickListener paramOnClickListener)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mLanguagePreferenceConfiguration.getContext());
    switch (paramInt)
    {
    default: 
      return null;
    case 1: 
      Vector localVector = this.mLanguagePreferenceConfiguration.getDisabledLanguages();
      CharSequence[] arrayOfCharSequence = new CharSequence[localVector.size()];
      int i = 0;
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext())
      {
        arrayOfCharSequence[i] = ((LanguagePack)localIterator.next()).getName();
        i++;
      }
      localBuilder.setTitle(2131297198);
      localBuilder.setSingleChoiceItems(arrayOfCharSequence, -1, paramOnClickListener);
      localBuilder.setOnKeyListener(new DialogInterface.OnKeyListener()
      {
        public boolean onKey(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt, KeyEvent paramAnonymousKeyEvent)
        {
          if (paramAnonymousInt == 4)
          {
            LanguagePreferenceDialog.this.mLanguagePreferenceConfiguration.removeDialog(1);
            return true;
          }
          return false;
        }
      });
      return localBuilder.create();
    }
    Resources localResources = this.mLanguagePreferenceConfiguration.getApplicationContext().getResources();
    localBuilder.setTitle(localResources.getString(2131297199));
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localResources.getString(2131297101).toLowerCase();
    localBuilder.setMessage(localResources.getString(2131297200, arrayOfObject));
    localBuilder.setCancelable(true);
    localBuilder.setNeutralButton(2131297201, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {}
    });
    return localBuilder.create();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.dialogs.LanguagePreferenceDialog
 * JD-Core Version:    0.7.0.1
 */