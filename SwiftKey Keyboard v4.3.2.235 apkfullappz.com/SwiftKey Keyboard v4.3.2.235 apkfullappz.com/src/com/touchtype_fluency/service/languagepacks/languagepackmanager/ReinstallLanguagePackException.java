package com.touchtype_fluency.service.languagepacks.languagepackmanager;

public class ReinstallLanguagePackException
  extends Exception
{
  private LanguagePack mLanguagePack;
  
  public ReinstallLanguagePackException(LanguagePack paramLanguagePack)
  {
    this.mLanguagePack = paramLanguagePack;
  }
  
  public LanguagePack getLanguagePack()
  {
    return this.mLanguagePack;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.ReinstallLanguagePackException
 * JD-Core Version:    0.7.0.1
 */