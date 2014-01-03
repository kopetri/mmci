package com.touchtype_fluency.service.languagepacks.layouts;

import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.Map;
import java.util.Set;

public abstract interface LayoutChangeListener
{
  public abstract void onLayoutChanged(Map<LayoutData.LayoutMap, Set<LanguagePack>> paramMap);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.layouts.LayoutChangeListener
 * JD-Core Version:    0.7.0.1
 */