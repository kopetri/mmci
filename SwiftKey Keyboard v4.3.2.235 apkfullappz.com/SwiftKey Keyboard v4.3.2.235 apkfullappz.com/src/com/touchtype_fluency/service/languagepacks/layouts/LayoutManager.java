package com.touchtype_fluency.service.languagepacks.layouts;

import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;
import java.util.Map;
import java.util.Set;

public abstract interface LayoutManager
{
  public abstract void addListener(LayoutChangeListener paramLayoutChangeListener);
  
  public abstract Map<LayoutData.LayoutMap, Set<LanguagePack>> getLayoutMap();
  
  public abstract void removeListener(LayoutChangeListener paramLayoutChangeListener);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.layouts.LayoutManager
 * JD-Core Version:    0.7.0.1
 */