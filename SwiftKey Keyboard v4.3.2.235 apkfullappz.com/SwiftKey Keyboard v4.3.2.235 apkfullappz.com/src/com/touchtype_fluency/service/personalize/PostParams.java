package com.touchtype_fluency.service.personalize;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

public class PostParams
{
  private final List<NameValuePair> params = new ArrayList();
  
  public void add(String paramString1, String paramString2)
  {
    this.params.add(new BasicNameValuePair(paramString1, paramString2));
  }
  
  public String toString()
  {
    return URLEncodedUtils.format(this.params, "UTF-8");
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.personalize.PostParams
 * JD-Core Version:    0.7.0.1
 */