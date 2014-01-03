package com.touchtype_fluency.internal;

import com.touchtype_fluency.CharacterMap;
import com.touchtype_fluency.FileCorruptException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Set;

public class CharacterMapImpl
  implements CharacterMap, Disposable
{
  private long peer;
  
  public static native void initIDs();
  
  private String inputStreamToString(InputStream paramInputStream)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuilder localStringBuilder = new StringBuilder();
    for (;;)
    {
      String str = localBufferedReader.readLine();
      if (str == null) {
        break;
      }
      localStringBuilder.append(str + "\n");
    }
    paramInputStream.close();
    return localStringBuilder.toString();
  }
  
  public void addLanguage(InputStream paramInputStream)
    throws IOException, FileCorruptException, IllegalStateException
  {
    addLanguage(inputStreamToString(paramInputStream));
  }
  
  public native void addLanguage(String paramString);
  
  public native void addLanguageFromFile(String paramString);
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public native String getAccentedVariantsOf(String paramString);
  
  public native String getAccentedVariantsOf(String paramString, Set<String> paramSet);
  
  public native Map<String, String[]> getLayout();
  
  public native void removeLanguage(String paramString);
  
  public native void resetLanguages();
  
  public void setLayout(InputStream paramInputStream)
    throws IOException, FileCorruptException, IllegalStateException
  {
    setLayout(inputStreamToString(paramInputStream));
  }
  
  public native void setLayout(String paramString);
  
  public native void setLayout(Map<String, String[]> paramMap);
  
  public native void setLayoutFromFile(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.CharacterMapImpl
 * JD-Core Version:    0.7.0.1
 */