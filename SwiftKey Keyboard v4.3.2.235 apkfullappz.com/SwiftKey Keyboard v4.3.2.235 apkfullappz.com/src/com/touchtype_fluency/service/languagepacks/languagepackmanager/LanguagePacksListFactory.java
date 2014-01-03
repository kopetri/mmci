package com.touchtype_fluency.service.languagepacks.languagepackmanager;

class LanguagePacksListFactory
{
  private final LanguagePackFactory languagePackFactory;
  
  public LanguagePacksListFactory(LanguagePackFactory paramLanguagePackFactory)
  {
    this.languagePackFactory = paramLanguagePackFactory;
  }
  
  public LanguagePacksList create(String paramString, LanguagePacks paramLanguagePacks)
  {
    return new LanguagePacksList(paramString, this.languagePackFactory, paramLanguagePacks);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePacksListFactory
 * JD-Core Version:    0.7.0.1
 */