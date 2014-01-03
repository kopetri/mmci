package com.touchtype_fluency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

public abstract interface CharacterMap
{
  public abstract void addLanguage(InputStream paramInputStream)
    throws IOException, FileCorruptException, IllegalStateException;
  
  public abstract void addLanguage(String paramString)
    throws FileCorruptException, IllegalStateException;
  
  public abstract void addLanguageFromFile(String paramString)
    throws FileNotFoundException, FileCorruptException, IllegalStateException;
  
  public abstract String getAccentedVariantsOf(String paramString);
  
  public abstract String getAccentedVariantsOf(String paramString, Set<String> paramSet);
  
  public abstract Map<String, String[]> getLayout();
  
  public abstract void removeLanguage(String paramString)
    throws IllegalStateException;
  
  public abstract void resetLanguages();
  
  public abstract void setLayout(InputStream paramInputStream)
    throws IOException, FileCorruptException, IllegalStateException;
  
  public abstract void setLayout(String paramString)
    throws FileCorruptException, IllegalStateException;
  
  public abstract void setLayout(Map<String, String[]> paramMap);
  
  public abstract void setLayoutFromFile(String paramString)
    throws FileNotFoundException, FileCorruptException, IllegalStateException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.CharacterMap
 * JD-Core Version:    0.7.0.1
 */