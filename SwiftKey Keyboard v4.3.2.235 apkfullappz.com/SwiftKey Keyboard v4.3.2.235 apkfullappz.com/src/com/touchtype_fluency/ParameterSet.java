package com.touchtype_fluency;

import java.io.IOException;

public abstract interface ParameterSet
{
  public abstract Parameter get(String paramString1, String paramString2);
  
  public abstract String[] getProperties(String paramString);
  
  public abstract String[] getTargets();
  
  public abstract void loadFile(String paramString)
    throws IOException;
  
  public abstract void reset();
  
  public abstract void reset(String paramString)
    throws IllegalStateException;
  
  public abstract void saveFile(String paramString)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.ParameterSet
 * JD-Core Version:    0.7.0.1
 */