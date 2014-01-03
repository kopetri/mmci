package com.touchtype.resources;

import android.content.Context;
import android.content.res.Resources;
import com.touchtype.preferences.TouchTypePreferences;

public final class ApplicationResources
{
  public static String getUpgradePackageName(Context paramContext)
  {
    String str = TouchTypePreferences.getInstance(paramContext).getString("pkgname_upgrade", "");
    if (!str.isEmpty()) {
      return str;
    }
    return paramContext.getResources().getString(2131296349);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.resources.ApplicationResources
 * JD-Core Version:    0.7.0.1
 */