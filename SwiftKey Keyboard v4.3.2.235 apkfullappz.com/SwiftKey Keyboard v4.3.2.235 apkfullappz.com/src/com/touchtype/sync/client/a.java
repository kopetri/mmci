package com.touchtype.sync.client;

import org.apache.commons.codec.binary.Base64;

final class a
{
  private String a;
  private String b;
  
  a(String paramString1, String paramString2)
  {
    this.a = paramString1;
    this.b = paramString2;
  }
  
  public final String toString()
  {
    StringBuilder localStringBuilder = new StringBuilder().append(this.a).append(":").append(this.b);
    return "Basic " + new String(Base64.encodeBase64(localStringBuilder.toString().getBytes()));
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.a
 * JD-Core Version:    0.7.0.1
 */