package com.touchtype_fluency.service.personalize;

import java.util.HashMap;

class RssSearchResult
  extends HashMap<String, String>
{
  static final String TITLE_KEY = "title";
  static final String URL_KEY = "url";
  
  public RssSearchResult(String paramString1, String paramString2)
  {
    super.put("title", paramString1);
    super.put("url", paramString2);
  }
  
  public String getTitle()
  {
    return (String)super.get("title");
  }
  
  public String getUrl()
  {
    return (String)super.get("url");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.RssSearchResult
 * JD-Core Version:    0.7.0.1
 */