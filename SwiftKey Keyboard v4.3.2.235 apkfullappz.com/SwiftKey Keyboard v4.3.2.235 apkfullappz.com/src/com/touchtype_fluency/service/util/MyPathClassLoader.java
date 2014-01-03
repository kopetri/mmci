package com.touchtype_fluency.service.util;

import dalvik.system.PathClassLoader;

public class MyPathClassLoader
  extends PathClassLoader
{
  public MyPathClassLoader(String paramString1, String paramString2, ClassLoader paramClassLoader)
  {
    super(paramString1, paramString2, paramClassLoader);
  }
  
  public String myFindLibrary(String paramString)
  {
    return findLibrary(paramString);
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.util.MyPathClassLoader
 * JD-Core Version:    0.7.0.1
 */