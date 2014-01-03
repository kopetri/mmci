package com.touchtype.settings.dialogs;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.os.Bundle;
import com.touchtype.settings.LanguagePreferenceConfiguration;
import com.touchtype.settings.LanguagePreferenceSetting.LanguagePreferenceFragment;
import com.touchtype.settings.TouchTypeKeyboardSettings;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.Iterator;
import java.util.Vector;

public class LanguageDialogFragment
  extends DialogFragment
{
  private static final String TAG = LanguageDialogFragment.class.getSimpleName();
  private LanguagePreferenceConfiguration mLanguagePreferenceConfiguration;
  
  public static LanguageDialogFragment newInstance(int paramInt, LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    LanguageDialogFragment localLanguageDialogFragment = new LanguageDialogFragment();
    Bundle localBundle = new Bundle();
    localBundle.putInt("title", paramInt);
    localLanguageDialogFragment.setArguments(localBundle);
    localLanguageDialogFragment.setLanguagePreferenceCongifuration(paramLanguagePreferenceConfiguration);
    return localLanguageDialogFragment;
  }
  
  private void setLanguagePreferenceCongifuration(LanguagePreferenceConfiguration paramLanguagePreferenceConfiguration)
  {
    this.mLanguagePreferenceConfiguration = paramLanguagePreferenceConfiguration;
  }
  
  public Dialog onCreateDialog(Bundle paramBundle)
  {
    if (this.mLanguagePreferenceConfiguration == null) {}
    try
    {
      LanguagePreferenceSetting.LanguagePreferenceFragment localLanguagePreferenceFragment = (LanguagePreferenceSetting.LanguagePreferenceFragment)TouchTypeKeyboardSettings.getCurrentFragment();
      if (localLanguagePreferenceFragment != null) {
        setLanguagePreferenceCongifuration(localLanguagePreferenceFragment.getConfiguration());
      }
    }
    catch (RuntimeException localRuntimeException)
    {
      DialogInterface.OnClickListener localOnClickListener;
      for (;;)
      {
        int i;
        LogUtil.e(TAG, "Orientation change." + localRuntimeException.getMessage(), localRuntimeException);
      }
      Vector localVector = this.mLanguagePreferenceConfiguration.getDisabledLanguages();
      CharSequence[] arrayOfCharSequence = new CharSequence[localVector.size()];
      int j = 0;
      Iterator localIterator = localVector.iterator();
      while (localIterator.hasNext())
      {
        arrayOfCharSequence[j] = ((LanguagePack)localIterator.next()).getName();
        j++;
      }
      localBuilder.setTitle(2131297198);
      localBuilder.setSingleChoiceItems(arrayOfCharSequence, -1, localOnClickListener);
      localBuilder.setCancelable(true);
      return localBuilder.create();
    }
    i = getArguments().getInt("title");
    localOnClickListener = this.mLanguagePreferenceConfiguration.getDeleteLanguageListener();
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this.mLanguagePreferenceConfiguration.getContext());
    switch (i)
    {
    default: 
      return null;
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
 * Qualified Name:     com.touchtype.settings.dialogs.LanguageDialogFragment
 * JD-Core Version:    0.7.0.1
 */