package com.touchtype_fluency.service.languagepacks;

import android.text.TextUtils;
import com.touchtype_fluency.service.languagepacks.languagepackmanager.LanguagePack;

public class LanguagePackUtil
{
  public static String getLayoutPreferenceIdForLanguage(LanguagePack paramLanguagePack)
  {
    return paramLanguagePack.getLanguage() + "_" + paramLanguagePack.getCountry() + "_layout";
  }
  
  public static String getLocaleString(LanguagePack paramLanguagePack)
  {
    String str1 = paramLanguagePack.getCountry();
    StringBuilder localStringBuilder = new StringBuilder().append(paramLanguagePack.getLanguage());
    if (TextUtils.isEmpty(str1)) {}
    for (String str2 = "";; str2 = "-" + str1) {
      return str2;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.languagepacks.LanguagePackUtil
 * JD-Core Version:    0.7.0.1
 */