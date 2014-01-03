package com.touchtype_fluency;

import java.io.IOException;
import java.util.Map;

public abstract interface KeyPressModel
{
  public abstract void addTag(String paramString1, String paramString2);
  
  public abstract String[] closestKey(Point paramPoint);
  
  public abstract String getTag(String paramString);
  
  public abstract void loadFile(String paramString)
    throws IOException;
  
  public abstract void removeAllTags();
  
  public abstract void removeTag(String paramString);
  
  public abstract void saveFile(String paramString)
    throws FileNotWritableException, IllegalStateException;
  
  public abstract void set();
  
  public abstract void set(Map<KeyShape, Character[]> paramMap);
  
  public abstract void setKeyShape(KeyShape paramKeyShape, Character[] paramArrayOfCharacter)
    throws IllegalStateException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.KeyPressModel
 * JD-Core Version:    0.7.0.1
 */