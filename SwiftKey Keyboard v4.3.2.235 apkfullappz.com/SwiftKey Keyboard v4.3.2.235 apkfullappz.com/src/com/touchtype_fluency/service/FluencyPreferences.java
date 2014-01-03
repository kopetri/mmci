package com.touchtype_fluency.service;

import android.content.Context;
import com.touchtype_fluency.Parameter;

public abstract interface FluencyPreferences
{
  public abstract Parameter get(String paramString1, String paramString2);
  
  public abstract String[] getProperties(String paramString);
  
  public abstract String[] getTargets();
  
  public abstract void load(Context paramContext);
  
  public abstract void reset(Context paramContext);
  
  public abstract String set(String paramString1, String paramString2, Object paramObject);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.FluencyPreferences
 * JD-Core Version:    0.7.0.1
 */