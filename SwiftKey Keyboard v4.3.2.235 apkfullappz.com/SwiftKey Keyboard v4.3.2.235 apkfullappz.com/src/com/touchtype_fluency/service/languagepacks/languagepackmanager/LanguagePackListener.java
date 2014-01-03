package com.touchtype_fluency.service.languagepacks.languagepackmanager;

import android.content.Context;

public abstract interface LanguagePackListener
{
  public abstract boolean isDeferrable();
  
  public abstract boolean onChange(Context paramContext);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePackListener
 * JD-Core Version:    0.7.0.1
 */