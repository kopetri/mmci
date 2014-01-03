package com.touchtype.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import java.util.UUID;

public final class InstallationId
{
  public static String getId(Context paramContext)
  {
    SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(paramContext);
    if (!localSharedPreferences.contains("installation_id")) {
      localSharedPreferences.edit().putString("installation_id", UUID.randomUUID().toString()).commit();
    }
    return localSharedPreferences.getString("installation_id", null);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.util.InstallationId
 * JD-Core Version:    0.7.0.1
 */