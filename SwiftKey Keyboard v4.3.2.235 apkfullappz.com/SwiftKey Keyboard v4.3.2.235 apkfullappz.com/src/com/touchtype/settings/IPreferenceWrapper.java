package com.touchtype.settings;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceScreen;

public abstract interface IPreferenceWrapper
{
  public abstract void addPreference(int paramInt);
  
  public abstract PreferenceScreen createPreferenceScreen();
  
  public abstract Preference findPreference(CharSequence paramCharSequence);
  
  public abstract Activity getActivity();
  
  public abstract Context getApplicationContext();
  
  public abstract Context getContext();
  
  public abstract PreferenceScreen getPreferenceScreen();
  
  public abstract void removeDialog(int paramInt);
  
  public abstract boolean removePreference(Preference paramPreference);
  
  public abstract void runOnUiThread(Runnable paramRunnable);
  
  public abstract void setPreferenceScreen(PreferenceScreen paramPreferenceScreen);
  
  public abstract void showDialog(int paramInt);
  
  public abstract void showDialog(int paramInt, Bundle paramBundle);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.IPreferenceWrapper
 * JD-Core Version:    0.7.0.1
 */