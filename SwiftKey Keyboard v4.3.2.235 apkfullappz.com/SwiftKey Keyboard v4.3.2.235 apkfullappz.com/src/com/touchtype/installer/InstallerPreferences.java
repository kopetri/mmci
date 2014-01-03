package com.touchtype.installer;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import com.touchtype.Constants.Installer;
import com.touchtype.resources.ProductConfiguration;
import java.util.Map;
import java.util.Set;

public final class InstallerPreferences
  implements SharedPreferences
{
  private final SharedPreferences mPreferences;
  
  private InstallerPreferences(SharedPreferences paramSharedPreferences)
  {
    this.mPreferences = paramSharedPreferences;
  }
  
  public static InstallerPreferences newInstance(Context paramContext)
  {
    return new InstallerPreferences(PreferenceManager.getDefaultSharedPreferences(paramContext));
  }
  
  public boolean contains(String paramString)
  {
    return this.mPreferences.contains(paramString);
  }
  
  public SharedPreferences.Editor edit()
  {
    return this.mPreferences.edit();
  }
  
  public Map<String, ?> getAll()
  {
    return this.mPreferences.getAll();
  }
  
  public boolean getBoolean(String paramString, boolean paramBoolean)
  {
    return this.mPreferences.getBoolean(paramString, paramBoolean);
  }
  
  public float getFloat(String paramString, float paramFloat)
  {
    return this.mPreferences.getFloat(paramString, paramFloat);
  }
  
  public String getInstallerCloudDeviceId()
  {
    return this.mPreferences.getString("stats_installer_cloud_device_id", "");
  }
  
  public boolean getInstallerCloudEnabled()
  {
    return this.mPreferences.getBoolean("stats_installer_cloud_enabled", false);
  }
  
  public boolean getInstallerCloudMarketingEnabled()
  {
    return this.mPreferences.getBoolean("stats_installer_cloud_marketing_enabled", false);
  }
  
  public boolean getInstallerCloudPersonalisedGmail()
  {
    return this.mPreferences.getBoolean("stats_installer_cloud_personalised_gmail", false);
  }
  
  public boolean getInstallerFlowEnabled()
  {
    return this.mPreferences.getBoolean("stats_installer_flow_enabled", false);
  }
  
  public String getInstallerProgressStat()
  {
    return this.mPreferences.getString("stats_installer_progress", "");
  }
  
  public String getInstallerStepChooseLang()
  {
    return this.mPreferences.getString("stats_installer_step_choose_lang", "");
  }
  
  public int getInt(String paramString, int paramInt)
  {
    return this.mPreferences.getInt(paramString, paramInt);
  }
  
  public long getLong(String paramString, long paramLong)
  {
    return this.mPreferences.getLong(paramString, paramLong);
  }
  
  public String getString(String paramString1, String paramString2)
  {
    return this.mPreferences.getString(paramString1, paramString2);
  }
  
  public Set<String> getStringSet(String paramString, Set<String> paramSet)
  {
    return this.mPreferences.getStringSet(paramString, paramSet);
  }
  
  public boolean isCloudPersonalisedGmailSetup()
  {
    return this.mPreferences.contains("stats_installer_cloud_personalised_gmail");
  }
  
  public boolean isInstallComplete(Context paramContext)
  {
    if (this.mPreferences.getInt("pref_install_state", 0) == -1) {
      return true;
    }
    if (ProductConfiguration.isPreinstalled(paramContext))
    {
      setInstallerCompleted();
      return true;
    }
    return false;
  }
  
  public boolean isRestoreComplete(Context paramContext)
  {
    if (!this.mPreferences.getBoolean("restored_from_backup", false)) {
      return true;
    }
    if (ProductConfiguration.isPreinstalled(paramContext))
    {
      setRestoreCompleted();
      return true;
    }
    return false;
  }
  
  public void registerOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    throw new UnsupportedOperationException("Method not implemented.");
  }
  
  public void setInstallerCloudDeviceId(String paramString)
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putString("stats_installer_cloud_device_id", paramString).commit();
      return;
    }
  }
  
  public void setInstallerCloudEnabled(boolean paramBoolean)
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putBoolean("stats_installer_cloud_enabled", paramBoolean).commit();
      return;
    }
  }
  
  public void setInstallerCloudMarketingEnabled(boolean paramBoolean)
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putBoolean("stats_installer_cloud_marketing_enabled", paramBoolean).commit();
      return;
    }
  }
  
  public void setInstallerCloudPersonalisedGmail(boolean paramBoolean)
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putBoolean("stats_installer_cloud_personalised_gmail", paramBoolean).commit();
      return;
    }
  }
  
  public void setInstallerCompleted()
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putInt("pref_install_state", -1).commit();
      setRestoreCompleted();
      return;
    }
  }
  
  public void setInstallerFlowEnabled(boolean paramBoolean)
  {
    synchronized (this.mPreferences.edit())
    {
      ???.putBoolean("stats_installer_flow_enabled", paramBoolean).commit();
      return;
    }
  }
  
  public void setInstallerProgressStat(int paramInt)
  {
    String str = (String)Constants.Installer.INSTALLER_STEPS.get(Integer.valueOf(paramInt));
    synchronized (this.mPreferences.edit())
    {
      ???.putString("stats_installer_progress", str).commit();
      return;
    }
  }
  
  public void setInstallerStepChooseLang(String paramString)
  {
    String str = this.mPreferences.getString("stats_installer_step_choose_lang", "");
    synchronized (this.mPreferences.edit())
    {
      if (TextUtils.isEmpty(str))
      {
        ???.putString("stats_installer_step_choose_lang", paramString);
        ???.commit();
        return;
      }
      ???.putString("stats_installer_step_choose_lang", str + "," + paramString);
    }
  }
  
  public void setRestoreCompleted()
  {
    synchronized (this.mPreferences.edit())
    {
      ???.remove("restored_from_backup").commit();
      return;
    }
  }
  
  public void unregisterOnSharedPreferenceChangeListener(SharedPreferences.OnSharedPreferenceChangeListener paramOnSharedPreferenceChangeListener)
  {
    throw new UnsupportedOperationException("Method not implemented.");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.installer.InstallerPreferences
 * JD-Core Version:    0.7.0.1
 */