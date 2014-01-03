package com.touchtype;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.provider.Settings.Secure;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;
import com.touchtype.settings.dialogs.EmailDialogPreference;
import com.touchtype.util.LogUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TouchTypeUtilities
{
  private static final String TAG = TouchTypeUtilities.class.getSimpleName();
  
  public static boolean checkIMEStatus(Context paramContext)
  {
    return Settings.Secure.getString(paramContext.getContentResolver(), "default_input_method").startsWith(paramContext.getPackageName() + "/");
  }
  
  private static boolean containsUnsafeIntent(PreferenceScreen paramPreferenceScreen, Preference paramPreference)
  {
    Intent localIntent = paramPreference.getIntent();
    PackageManager localPackageManager = paramPreferenceScreen.getContext().getPackageManager();
    if ((localIntent != null) && (localPackageManager.queryIntentActivities(localIntent, 65536).isEmpty())) {}
    while (((paramPreference instanceof EmailDialogPreference)) && (localPackageManager.queryIntentActivities(new Intent("android.intent.action.VIEW", Uri.parse(paramPreferenceScreen.getContext().getResources().getString(2131297187))), 65536).isEmpty())) {
      return true;
    }
    return false;
  }
  
  public static Intent getIMSettingsIntent()
  {
    try
    {
      Intent localIntent1 = new Intent();
      localIntent1.setAction("android.settings.INPUT_METHOD_SETTINGS");
      return localIntent1;
    }
    catch (ActivityNotFoundException localActivityNotFoundException)
    {
      Intent localIntent2 = new Intent();
      localIntent2.setAction("android.intent.action.MAIN");
      localIntent2.setClassName("com.android.settings", "com.android.settings.LanguageSettings");
      return localIntent2;
    }
  }
  
  public static boolean isStorageAvailable()
  {
    return Environment.getExternalStorageState().equals("mounted");
  }
  
  public static boolean isTouchTypeEnabled(Activity paramActivity)
  {
    InputMethodManager localInputMethodManager = (InputMethodManager)paramActivity.getSystemService("input_method");
    String str1 = paramActivity.getPackageName();
    Iterator localIterator = localInputMethodManager.getEnabledInputMethodList().iterator();
    while (localIterator.hasNext())
    {
      InputMethodInfo localInputMethodInfo = (InputMethodInfo)localIterator.next();
      String str2 = localInputMethodInfo.getPackageName();
      String str3 = localInputMethodInfo.getServiceName();
      if ((str2.equals(str1)) && ((str3.equals("com.touchtype.KeyboardService")) || (str3.equals(".KeyboardService")))) {
        return true;
      }
    }
    return false;
  }
  
  public static void openIMESelector(Context paramContext)
  {
    ((InputMethodManager)paramContext.getSystemService("input_method")).showInputMethodPicker();
  }
  
  public static void removeUnsafePreferences(PreferenceScreen paramPreferenceScreen)
  {
    if (paramPreferenceScreen != null)
    {
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramPreferenceScreen.getPreferenceCount(); i++)
      {
        Preference localPreference = paramPreferenceScreen.getPreference(i);
        if ((localPreference.isEnabled()) && (containsUnsafeIntent(paramPreferenceScreen, localPreference))) {
          localArrayList.add(localPreference);
        }
      }
      Iterator localIterator = localArrayList.iterator();
      while (localIterator.hasNext()) {
        paramPreferenceScreen.removePreference((Preference)localIterator.next());
      }
    }
  }
  
  public static void storePreference(Activity paramActivity, String paramString, Object paramObject)
  {
    for (;;)
    {
      synchronized (PreferenceManager.getDefaultSharedPreferences(paramActivity).edit())
      {
        if ((paramObject instanceof String))
        {
          ???.putString(paramString, (String)paramObject);
          ???.commit();
          return;
        }
        if ((paramObject instanceof Integer)) {
          ???.putInt(paramString, ((Integer)paramObject).intValue());
        }
      }
      if ((paramObject instanceof Boolean)) {
        ???.putBoolean(paramString, ((Boolean)paramObject).booleanValue());
      } else {
        LogUtil.w(TAG, "Storing preference: type not supported: " + paramObject.toString() + ". Called from " + paramActivity.getClass().getName());
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.TouchTypeUtilities
 * JD-Core Version:    0.7.0.1
 */