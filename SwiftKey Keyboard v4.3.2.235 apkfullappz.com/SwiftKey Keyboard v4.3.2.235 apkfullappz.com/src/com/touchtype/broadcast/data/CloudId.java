package com.touchtype.broadcast.data;

import com.google.gson.Gson;

public final class CloudId
{
  public final String googleCloudId;
  
  public CloudId(String paramString)
  {
    this.googleCloudId = paramString;
  }
  
  public String toString()
  {
    return new Gson().toJson(this);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.broadcast.data.CloudId
 * JD-Core Version:    0.7.0.1
 */