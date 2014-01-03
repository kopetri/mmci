package com.touchtype.settings;

import android.app.Fragment;

public final class PreferenceConfigFragment
  extends Fragment
{
  private PreferenceWrapper mConfig;
  
  public PreferenceConfigFragment()
  {
    setRetainInstance(true);
  }
  
  public PreferenceWrapper getConfig()
  {
    return this.mConfig;
  }
  
  public void setConfig(PreferenceWrapper paramPreferenceWrapper)
  {
    this.mConfig = paramPreferenceWrapper;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.PreferenceConfigFragment
 * JD-Core Version:    0.7.0.1
 */