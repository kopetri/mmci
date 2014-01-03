package com.touchtype.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;

public abstract class PreferenceWrapper
  implements IPreferenceWrapper
{
  private IPreferenceWrapper iPrefWrapper;
  
  public PreferenceWrapper(PreferenceActivity paramPreferenceActivity)
  {
    this.iPrefWrapper = new ActivityPreferenceWrapper(paramPreferenceActivity);
  }
  
  public PreferenceWrapper(PreferenceFragment paramPreferenceFragment)
  {
    this.iPrefWrapper = new FragmentPreferenceWrapper(paramPreferenceFragment, this);
  }
  
  public void addPreference(int paramInt)
  {
    this.iPrefWrapper.addPreference(paramInt);
  }
  
  public PreferenceScreen createPreferenceScreen()
  {
    return this.iPrefWrapper.createPreferenceScreen();
  }
  
  public Preference findPreference(CharSequence paramCharSequence)
  {
    return this.iPrefWrapper.findPreference(paramCharSequence);
  }
  
  public Activity getActivity()
  {
    return this.iPrefWrapper.getActivity();
  }
  
  public Context getApplicationContext()
  {
    return this.iPrefWrapper.getApplicationContext();
  }
  
  public Context getContext()
  {
    return this.iPrefWrapper.getContext();
  }
  
  @TargetApi(11)
  public DialogFragment getDialogFragment(int paramInt)
  {
    return null;
  }
  
  @TargetApi(11)
  public DialogFragment getDialogFragment(int paramInt, Bundle paramBundle)
  {
    return null;
  }
  
  public PreferenceScreen getPreferenceScreen()
  {
    return this.iPrefWrapper.getPreferenceScreen();
  }
  
  public void removeDialog(int paramInt)
  {
    this.iPrefWrapper.removeDialog(paramInt);
  }
  
  public boolean removePreference(Preference paramPreference)
  {
    return this.iPrefWrapper.removePreference(paramPreference);
  }
  
  public void runOnUiThread(Runnable paramRunnable)
  {
    this.iPrefWrapper.runOnUiThread(paramRunnable);
  }
  
  public void setActivity(PreferenceActivity paramPreferenceActivity)
  {
    ((ActivityPreferenceWrapper)this.iPrefWrapper).setActivity(paramPreferenceActivity);
  }
  
  public void setFragment(PreferenceFragment paramPreferenceFragment)
  {
    ((FragmentPreferenceWrapper)this.iPrefWrapper).setFragment(paramPreferenceFragment);
  }
  
  public void setPreferenceScreen(PreferenceScreen paramPreferenceScreen)
  {
    this.iPrefWrapper.setPreferenceScreen(createPreferenceScreen());
  }
  
  public void showDialog(int paramInt)
  {
    this.iPrefWrapper.showDialog(paramInt);
  }
  
  public void showDialog(int paramInt, Bundle paramBundle)
  {
    this.iPrefWrapper.showDialog(paramInt, paramBundle);
  }
  
  private static final class ActivityPreferenceWrapper
    implements IPreferenceWrapper
  {
    private PreferenceActivity mActivity;
    
    public ActivityPreferenceWrapper(PreferenceActivity paramPreferenceActivity)
    {
      this.mActivity = paramPreferenceActivity;
    }
    
    public void addPreference(int paramInt)
    {
      this.mActivity.addPreferencesFromResource(paramInt);
    }
    
    public PreferenceScreen createPreferenceScreen()
    {
      return this.mActivity.getPreferenceManager().createPreferenceScreen(getContext());
    }
    
    public Preference findPreference(CharSequence paramCharSequence)
    {
      return this.mActivity.findPreference(paramCharSequence);
    }
    
    public Activity getActivity()
    {
      return this.mActivity;
    }
    
    public Context getApplicationContext()
    {
      return this.mActivity.getApplicationContext();
    }
    
    public Context getContext()
    {
      return getActivity();
    }
    
    public PreferenceScreen getPreferenceScreen()
    {
      return this.mActivity.getPreferenceScreen();
    }
    
    public void removeDialog(int paramInt)
    {
      this.mActivity.removeDialog(paramInt);
    }
    
    public boolean removePreference(Preference paramPreference)
    {
      return getPreferenceScreen().removePreference(paramPreference);
    }
    
    public void runOnUiThread(Runnable paramRunnable)
    {
      this.mActivity.runOnUiThread(paramRunnable);
    }
    
    public void setActivity(PreferenceActivity paramPreferenceActivity)
    {
      this.mActivity = paramPreferenceActivity;
    }
    
    public void setPreferenceScreen(PreferenceScreen paramPreferenceScreen)
    {
      this.mActivity.setPreferenceScreen(createPreferenceScreen());
    }
    
    public void showDialog(int paramInt)
    {
      this.mActivity.showDialog(paramInt);
    }
    
    public void showDialog(int paramInt, Bundle paramBundle)
    {
      this.mActivity.showDialog(paramInt, paramBundle);
    }
  }
  
  @TargetApi(11)
  private static final class FragmentPreferenceWrapper
    implements IPreferenceWrapper
  {
    private PreferenceFragment mFragment;
    private PreferenceWrapper mPreferenceWrapper;
    
    public FragmentPreferenceWrapper(PreferenceFragment paramPreferenceFragment, PreferenceWrapper paramPreferenceWrapper)
    {
      this.mFragment = paramPreferenceFragment;
      this.mPreferenceWrapper = paramPreferenceWrapper;
    }
    
    public void addPreference(int paramInt)
    {
      this.mFragment.addPreferencesFromResource(paramInt);
    }
    
    public PreferenceScreen createPreferenceScreen()
    {
      return this.mFragment.getPreferenceManager().createPreferenceScreen(getContext());
    }
    
    public Preference findPreference(CharSequence paramCharSequence)
    {
      return this.mFragment.findPreference(paramCharSequence);
    }
    
    public Activity getActivity()
    {
      return this.mFragment.getActivity();
    }
    
    public Context getApplicationContext()
    {
      return this.mFragment.getActivity().getApplicationContext();
    }
    
    public Context getContext()
    {
      return getActivity();
    }
    
    public PreferenceScreen getPreferenceScreen()
    {
      return this.mFragment.getPreferenceScreen();
    }
    
    public void removeDialog(int paramInt)
    {
      FragmentTransaction localFragmentTransaction = this.mFragment.getFragmentManager().beginTransaction();
      Fragment localFragment = this.mFragment.getFragmentManager().findFragmentByTag("dialog");
      if (localFragment != null)
      {
        localFragmentTransaction.remove(localFragment);
        localFragmentTransaction.commitAllowingStateLoss();
      }
    }
    
    public boolean removePreference(Preference paramPreference)
    {
      return this.mFragment.getPreferenceScreen().removePreference(paramPreference);
    }
    
    public void runOnUiThread(Runnable paramRunnable)
    {
      if (this.mFragment.getActivity() != null) {
        this.mFragment.getActivity().runOnUiThread(paramRunnable);
      }
    }
    
    public void setFragment(PreferenceFragment paramPreferenceFragment)
    {
      this.mFragment = paramPreferenceFragment;
    }
    
    public void setPreferenceScreen(PreferenceScreen paramPreferenceScreen)
    {
      this.mFragment.setPreferenceScreen(createPreferenceScreen());
    }
    
    public void showDialog(int paramInt)
    {
      this.mPreferenceWrapper.getDialogFragment(paramInt).show(this.mFragment.getFragmentManager(), "dialog");
    }
    
    public void showDialog(int paramInt, Bundle paramBundle)
    {
      this.mPreferenceWrapper.getDialogFragment(paramInt, paramBundle).show(this.mFragment.getFragmentManager(), "dialog");
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.PreferenceWrapper
 * JD-Core Version:    0.7.0.1
 */