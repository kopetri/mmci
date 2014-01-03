package com.touchtype.settings.custompreferences;

import android.content.Context;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.widget.EditText;
import android.widget.Toast;
import com.touchtype_fluency.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class FluencyPreferenceFactory
{
  private static final Map<Class<?>, PreferenceCreator> preferenceCreators;
  
  static
  {
    HashMap localHashMap = new HashMap();
    localHashMap.put(Float.class, new NumericPreferenceCreator(8194));
    localHashMap.put(Integer.class, new NumericPreferenceCreator(2));
    localHashMap.put(Boolean.class, new BooleanPreferenceCreator(null));
    localHashMap.put([Ljava.lang.Float.class, new FloatArrayPreferenceCreator(null));
    preferenceCreators = Collections.unmodifiableMap(localHashMap);
  }
  
  public static PreferenceCreator getFactory(Class<?> paramClass)
  {
    return (PreferenceCreator)preferenceCreators.get(paramClass);
  }
  
  private static final class BooleanPreferenceCreator
    extends FluencyPreferenceFactory.PreferenceCreator
  {
    public Preference create(final String paramString1, final String paramString2, Parameter paramParameter, Context paramContext, final FluencyPreferenceFactory.PreferenceListener paramPreferenceListener)
    {
      CheckBoxPreference localCheckBoxPreference = new CheckBoxPreference(paramContext);
      localCheckBoxPreference.setDefaultValue(paramParameter.getValue());
      localCheckBoxPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
      {
        public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
        {
          return paramPreferenceListener.onChange(paramString1, paramString2, paramAnonymousObject);
        }
      });
      return localCheckBoxPreference;
    }
  }
  
  private static final class FloatArrayPreferenceCreator
    extends FluencyPreferenceFactory.PreferenceCreator
  {
    public Preference create(final String paramString1, final String paramString2, Parameter paramParameter, final Context paramContext, final FluencyPreferenceFactory.PreferenceListener paramPreferenceListener)
    {
      EditTextPreference localEditTextPreference = new EditTextPreference(paramContext);
      localEditTextPreference.setDefaultValue(Arrays.toString((Float[])paramParameter.getValue()));
      localEditTextPreference.getEditText().setInputType(524289);
      localEditTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
      {
        public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
        {
          String str = (String)paramAnonymousObject;
          try
          {
            Float[] arrayOfFloat = FluencyPreferenceFactory.FloatArrayPreferenceCreator.this.parseString(str);
            boolean bool = paramPreferenceListener.onChange(paramString1, paramString2, arrayOfFloat);
            return bool;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Toast.makeText(paramContext, "bad parameter value - " + str, 0).show();
          }
          return false;
        }
      });
      return localEditTextPreference;
    }
    
    Float[] parseString(String paramString)
    {
      StringTokenizer localStringTokenizer = new StringTokenizer(paramString.trim().replaceAll("[()\\[\\]{}]", ""), " ,;:");
      ArrayList localArrayList = new ArrayList();
      while (localStringTokenizer.hasMoreTokens()) {
        localArrayList.add(Float.valueOf(Float.parseFloat(localStringTokenizer.nextToken().trim())));
      }
      return (Float[])localArrayList.toArray(new Float[0]);
    }
  }
  
  private static final class NumericPreferenceCreator
    extends FluencyPreferenceFactory.PreferenceCreator
  {
    final int mInputType;
    
    public NumericPreferenceCreator(int paramInt)
    {
      this.mInputType = paramInt;
    }
    
    public Preference create(final String paramString1, final String paramString2, Parameter paramParameter, final Context paramContext, final FluencyPreferenceFactory.PreferenceListener paramPreferenceListener)
    {
      EditTextPreference localEditTextPreference = new EditTextPreference(paramContext);
      localEditTextPreference.setDefaultValue(paramParameter.getValue().toString());
      localEditTextPreference.getEditText().setInputType(this.mInputType);
      localEditTextPreference.getEditText().setSelectAllOnFocus(true);
      localEditTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
      {
        public boolean onPreferenceChange(Preference paramAnonymousPreference, Object paramAnonymousObject)
        {
          String str = (String)paramAnonymousObject;
          try
          {
            if ((0x2000 & FluencyPreferenceFactory.NumericPreferenceCreator.this.mInputType) != 0) {
              return paramPreferenceListener.onChange(paramString1, paramString2, Float.valueOf(Float.parseFloat(str)));
            }
            boolean bool = paramPreferenceListener.onChange(paramString1, paramString2, Integer.valueOf(Integer.parseInt(str)));
            return bool;
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Toast.makeText(paramContext, "bad parameter value - " + str, 0).show();
          }
          return false;
        }
      });
      return localEditTextPreference;
    }
  }
  
  public static abstract class PreferenceCreator
  {
    public abstract Preference create(String paramString1, String paramString2, Parameter paramParameter, Context paramContext, FluencyPreferenceFactory.PreferenceListener paramPreferenceListener);
  }
  
  public static abstract interface PreferenceListener
  {
    public abstract boolean onChange(String paramString1, String paramString2, Object paramObject);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.settings.custompreferences.FluencyPreferenceFactory
 * JD-Core Version:    0.7.0.1
 */