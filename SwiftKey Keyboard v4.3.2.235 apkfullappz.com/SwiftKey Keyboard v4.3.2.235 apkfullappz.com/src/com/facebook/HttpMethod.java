package com.facebook;

public enum HttpMethod
{
  static
  {
    DELETE = new HttpMethod("DELETE", 2);
    HttpMethod[] arrayOfHttpMethod = new HttpMethod[3];
    arrayOfHttpMethod[0] = GET;
    arrayOfHttpMethod[1] = POST;
    arrayOfHttpMethod[2] = DELETE;
    $VALUES = arrayOfHttpMethod;
  }
  
  private HttpMethod() {}
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.facebook.HttpMethod
 * JD-Core Version:    0.7.0.1
 */