package com.touchtype.settings;

import android.content.Context;
import android.content.res.Resources;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.widget.Toast;
import com.touchtype.settings.custompreferences.FluencyPreferenceFactory;
import com.touchtype.settings.custompreferences.FluencyPreferenceFactory.PreferenceCreator;
import com.touchtype.settings.custompreferences.FluencyPreferenceFactory.PreferenceListener;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Parameter;
import com.touchtype_fluency.SwiftKeySDK;
import com.touchtype_fluency.service.FluencyPreferences;
import com.touchtype_fluency.service.FluencyServiceProxy;
import com.touchtype_fluency.service.Predictor;
import java.util.Arrays;
import junit.framework.Assert;

public final class FluencyPreferenceConfiguration
  extends PreferenceWrapper
{
  private final FluencyServiceProxy fluencyService = new FluencyServiceProxy();
  private final FluencyPreferenceFactory.PreferenceListener preferenceListener = new FluencyPreferenceFactory.PreferenceListener()
  {
    public boolean onChange(String paramAnonymousString1, String paramAnonymousString2, Object paramAnonymousObject)
    {
      FluencyPreferences localFluencyPreferences = FluencyPreferenceConfiguration.this.getPreferences();
      boolean bool = false;
      if (localFluencyPreferences != null)
      {
        String str = localFluencyPreferences.set(paramAnonymousString1, paramAnonymousString2, paramAnonymousObject);
        if (str != null) {
          Toast.makeText(FluencyPreferenceConfiguration.this.getContext(), str, 0).show();
        }
        bool = false;
        if (str == null) {
          bool = true;
        }
      }
      return bool;
    }
  };
  
  public FluencyPreferenceConfiguration(PreferenceActivity paramPreferenceActivity)
  {
    super(paramPreferenceActivity);
    setPreferenceScreen(createPreferenceScreen());
  }
  
  public FluencyPreferenceConfiguration(PreferenceFragment paramPreferenceFragment)
  {
    super(paramPreferenceFragment);
    setPreferenceScreen(createPreferenceScreen());
  }
  
  private void addPreferences(FluencyPreferences paramFluencyPreferences)
  {
    PreferenceCategory localPreferenceCategory1 = new PreferenceCategory(getContext());
    localPreferenceCategory1.setTitle("SDK " + SwiftKeySDK.getVersion());
    getPreferenceScreen().addPreference(localPreferenceCategory1);
    String[] arrayOfString1 = paramFluencyPreferences.getTargets();
    Arrays.sort(arrayOfString1);
    int i = arrayOfString1.length;
    for (int j = 0; j < i; j++)
    {
      String str1 = arrayOfString1[j];
      PreferenceCategory localPreferenceCategory2 = new PreferenceCategory(getContext());
      localPreferenceCategory2.setTitle(getName(str1));
      getPreferenceScreen().addPreference(localPreferenceCategory2);
      String[] arrayOfString2 = paramFluencyPreferences.getProperties(str1);
      Arrays.sort(arrayOfString2);
      int k = arrayOfString2.length;
      int m = 0;
      if (m < k)
      {
        String str2 = arrayOfString2[m];
        Parameter localParameter = paramFluencyPreferences.get(str1, str2);
        FluencyPreferenceFactory.PreferenceCreator localPreferenceCreator = FluencyPreferenceFactory.getFactory(localParameter.getValueType());
        if (localPreferenceCreator != null)
        {
          Preference localPreference = localPreferenceCreator.create(str1, str2, localParameter, getContext(), this.preferenceListener);
          localPreference.setTitle(getName(str2));
          localPreferenceCategory2.addPreference(localPreference);
        }
        for (;;)
        {
          m++;
          break;
          Object[] arrayOfObject = new Object[3];
          arrayOfObject[0] = str1;
          arrayOfObject[1] = str2;
          arrayOfObject[2] = localParameter.getValueType();
          LogUtil.e("FluencyPreferenceConfiguration", String.format("Could not create preference for %s:%s (of type %s)", arrayOfObject));
        }
      }
    }
  }
  
  private static String getName(String paramString)
  {
    return paramString.replace('-', ' ');
  }
  
  private FluencyPreferences getPreferences()
  {
    Predictor localPredictor = this.fluencyService.getPredictor();
    if (localPredictor != null)
    {
      FluencyPreferences localFluencyPreferences = localPredictor.getPreferences();
      if (localFluencyPreferences != null) {
        return localFluencyPreferences;
      }
      LogUtil.e("FluencyPreferenceConfiguration", "FluencyPreferences not available");
    }
    for (;;)
    {
      return null;
      LogUtil.e("FluencyPreferenceConfiguration", "Predictor not set");
    }
  }
  
  private void refreshPreferences(FluencyPreferences paramFluencyPreferences)
  {
    getPreferenceScreen().removeAll();
    addPreferences(paramFluencyPreferences);
  }
  
  public void destroy()
  {
    getPreferenceScreen().removeAll();
    this.fluencyService.onDestroy(getApplicationContext());
  }
  
  public void resetPreferences()
  {
    this.fluencyService.runWhenConnected(new Runnable()
    {
      public void run()
      {
        FluencyPreferences localFluencyPreferences = FluencyPreferenceConfiguration.this.getPreferences();
        if (localFluencyPreferences != null)
        {
          localFluencyPreferences.reset(FluencyPreferenceConfiguration.this.getApplicationContext());
          FluencyPreferenceConfiguration.this.refreshPreferences(localFluencyPreferences);
        }
      }
    });
  }
  
  public void setup(PreferenceActivity paramPreferenceActivity)
  {
    Assert.assertNotNull(getPreferenceScreen());
    Resources localResources = getPreferenceScreen().getContext().getResources();
    getPreferenceScreen().setTitle(localResources.getString(2131296675));
    getPreferenceScreen().setSummary(localResources.getString(2131296676));
    this.fluencyService.onCreate(getApplicationContext());
    this.fluencyService.runWhenConnected(new Runnable()
    {
      public void run()
      {
        FluencyPreferences localFluencyPreferences = FluencyPreferenceConfiguration.this.getPreferences();
        if (localFluencyPreferences != null) {
          FluencyPreferenceConfiguration.this.addPreferences(localFluencyPreferences);
        }
      }
    });
  }
  
  public void update()
  {
    if (this.fluencyService.isReady())
    {
      FluencyPreferences localFluencyPreferences = getPreferences();
      if (localFluencyPreferences != null) {
        refreshPreferences(localFluencyPreferences);
      }
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.FluencyPreferenceConfiguration
 * JD-Core Version:    0.7.0.1
 */