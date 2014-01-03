package com.touchtype_fluency.service;

import android.content.Context;
import com.google.common.base.Strings;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.touchtype.resources.ProductConfiguration;
import com.touchtype.storage.FolderDecorator;
import com.touchtype.util.LogUtil;
import com.touchtype_fluency.Parameter;
import com.touchtype_fluency.ParameterOutOfRangeException;
import com.touchtype_fluency.ParameterSet;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import junit.framework.Assert;

public class FluencyPreferencesImpl
  implements FluencyPreferences
{
  private static final String PARAMETERS_JSON = "parameters.json";
  private static final String TAG = "FluencyPreferencesImpl";
  private final FolderDecorator mFolder;
  private final ParameterSet mParameters;
  
  public FluencyPreferencesImpl(ParameterSet paramParameterSet, FolderDecorator paramFolderDecorator)
  {
    this.mParameters = paramParameterSet;
    this.mFolder = paramFolderDecorator;
    Assert.assertNotNull(this.mParameters);
    Assert.assertNotNull(this.mFolder);
  }
  
  private void applyFixedParameters(Context paramContext)
  {
    String str1 = ProductConfiguration.getSDKParameters(paramContext);
    if (!Strings.isNullOrEmpty(str1)) {
      for (;;)
      {
        try
        {
          Map localMap = (Map)new Gson().fromJson(str1, new TypeToken() {}.getType());
          Iterator localIterator1 = localMap.keySet().iterator();
          if (!localIterator1.hasNext()) {
            continue;
          }
          str2 = (String)localIterator1.next();
          Iterator localIterator2 = ((Map)localMap.get(str2)).entrySet().iterator();
          if (!localIterator2.hasNext()) {
            continue;
          }
          localEntry = (Map.Entry)localIterator2.next();
          str3 = (String)localEntry.getKey();
          if (!(localEntry.getValue() instanceof Double)) {
            continue;
          }
          localObject2 = Float.valueOf(((Double)localEntry.getValue()).floatValue());
        }
        catch (JsonSyntaxException localJsonSyntaxException)
        {
          String str2;
          Map.Entry localEntry;
          String str3;
          LogUtil.e("FluencyPreferencesImpl", localJsonSyntaxException.getMessage(), localJsonSyntaxException);
          return;
          Object localObject1 = localEntry.getValue();
          Object localObject2 = localObject1;
          continue;
        }
        catch (ParameterOutOfRangeException localParameterOutOfRangeException)
        {
          LogUtil.e("FluencyPreferencesImpl", localParameterOutOfRangeException.getMessage(), localParameterOutOfRangeException);
        }
        this.mParameters.get(str2, str3).setValue(localObject2);
      }
    }
  }
  
  private File getParametersFile()
  {
    return new File(this.mFolder.getBaseFolder(), "parameters.json");
  }
  
  public Parameter get(String paramString1, String paramString2)
  {
    return this.mParameters.get(paramString1, paramString2);
  }
  
  public String[] getProperties(String paramString)
  {
    return this.mParameters.getProperties(paramString);
  }
  
  public String[] getTargets()
  {
    return this.mParameters.getTargets();
  }
  
  public void load(Context paramContext)
  {
    applyFixedParameters(paramContext);
    try
    {
      this.mParameters.loadFile(getParametersFile().getAbsolutePath());
      return;
    }
    catch (IOException localIOException)
    {
      LogUtil.e("FluencyPreferencesImpl", "Couldn't load parameters file: " + localIOException.getMessage(), localIOException);
    }
  }
  
  public void reset(Context paramContext)
  {
    this.mParameters.reset();
    applyFixedParameters(paramContext);
  }
  
  public String set(String paramString1, String paramString2, Object paramObject)
  {
    Parameter localParameter = this.mParameters.get(paramString1, paramString2);
    if (localParameter == null) {
      return String.format("parameter %s:%s not found", new Object[] { paramString1, paramString2 });
    }
    try
    {
      localParameter.setValue(paramObject);
      this.mParameters.saveFile(getParametersFile().getAbsolutePath());
      return null;
    }
    catch (ClassCastException localClassCastException)
    {
      Object[] arrayOfObject2 = new Object[5];
      arrayOfObject2[0] = paramString1;
      arrayOfObject2[1] = paramString2;
      arrayOfObject2[2] = paramObject;
      arrayOfObject2[3] = localParameter.getValueType();
      arrayOfObject2[4] = paramObject.getClass();
      return String.format("could not set %s:%s to %s - wrong type (expected %s, found %s)", arrayOfObject2);
    }
    catch (ParameterOutOfRangeException localParameterOutOfRangeException)
    {
      Object[] arrayOfObject1 = new Object[5];
      arrayOfObject1[0] = paramString1;
      arrayOfObject1[1] = paramString2;
      arrayOfObject1[2] = paramObject;
      arrayOfObject1[3] = localParameter.minValue();
      arrayOfObject1[4] = localParameter.maxValue();
      return String.format("could not set %s:%s to %s - not in the allowed range (%s..%s)", arrayOfObject1);
    }
    catch (IOException localIOException) {}
    return "could not save preferences file";
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyPreferencesImpl
 * JD-Core Version:    0.7.0.1
 */