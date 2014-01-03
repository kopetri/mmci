package com.touchtype;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.touchtype.installer.InstallerPreferences;

public class LauncherActivity
  extends Activity
{
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Context localContext = getApplicationContext();
    InstallerPreferences localInstallerPreferences = InstallerPreferences.newInstance(localContext);
    if ((!localInstallerPreferences.isInstallComplete(localContext)) || (!localInstallerPreferences.isRestoreComplete(localContext))) {
      Launcher.launchInstaller(localContext, 0, new String[0]);
    }
    for (;;)
    {
      finish();
      return;
      Launcher.launchSettings(localContext);
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.LauncherActivity
 * JD-Core Version:    0.7.0.1
 */