package com.touchtype.installer.x;

import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.widget.Toast;
import com.touchtype.TouchTypeUtilities;
import com.touchtype.installer.InstallerPreferences;

public final class InstallerActivityResult
{
  private final XInstaller mInstaller;
  private final InstallerPreferences mInstallerPrefs;
  
  public InstallerActivityResult(XInstaller paramXInstaller, InstallerPreferences paramInstallerPreferences)
  {
    this.mInstaller = paramXInstaller;
    this.mInstallerPrefs = paramInstallerPreferences;
  }
  
  public boolean activityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    int i = 1;
    switch (paramInt1)
    {
    case 3: 
    default: 
      i = 0;
    }
    do
    {
      do
      {
        return i;
      } while (TouchTypeUtilities.isTouchTypeEnabled(this.mInstaller));
      String str2 = this.mInstaller.getResources().getString(2131296406);
      Object[] arrayOfObject = new Object[i];
      arrayOfObject[0] = this.mInstaller.mProductName;
      String str3 = String.format(str2, arrayOfObject);
      Toast.makeText(this.mInstaller, str3, i).show();
      break;
      if ((paramInt2 == -1) && (paramIntent != null))
      {
        String str1 = paramIntent.getStringExtra("CloudSetupActivity.SignedInUsername");
        if (!TextUtils.isEmpty(str1)) {
          this.mInstaller.setCloudSignedInUsername(str1);
        }
      }
      if (this.mInstaller.getResources().getBoolean(2131492935))
      {
        this.mInstallerPrefs.setInstallerProgressStat(4);
        return i;
      }
      this.mInstallerPrefs.setInstallerProgressStat(5);
      this.mInstaller.nextFrame();
      return i;
      if (paramInt2 == 0)
      {
        this.mInstaller.exitWithError();
        break;
      }
      TouchTypeUtilities.storePreference(this.mInstaller, "installer_eula_accepted", Boolean.valueOf(i));
      this.mInstallerPrefs.setInstallerProgressStat(0);
      break;
    } while (!this.mInstaller.checkTrialUpgrade());
    return false;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.x.InstallerActivityResult
 * JD-Core Version:    0.7.0.1
 */