package com.touchtype_fluency.service.languagepacks.languagepackmanager;

public class MaximumLanguagesException
  extends Exception
{
  private int maxLanguagePacks;
  
  public MaximumLanguagesException(int paramInt)
  {
    this.maxLanguagePacks = paramInt;
  }
  
  public int getMaxLanguagePacks()
  {
    return this.maxLanguagePacks;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.MaximumLanguagesException
 * JD-Core Version:    0.7.0.1
 */