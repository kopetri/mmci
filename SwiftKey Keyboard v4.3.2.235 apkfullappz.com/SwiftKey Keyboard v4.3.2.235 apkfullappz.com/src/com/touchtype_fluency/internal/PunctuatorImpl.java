package com.touchtype_fluency.internal;

import com.touchtype_fluency.FileCorruptException;
import com.touchtype_fluency.Punctuator;
import com.touchtype_fluency.Punctuator.Action;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class PunctuatorImpl
  implements Punctuator, Disposable
{
  private long peer;
  
  private PunctuatorImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  private native String getPredictionTriggerString();
  
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
  
  private native int[] punctuateInt(String paramString1, String paramString2, String paramString3);
  
  public void addRules(InputStream paramInputStream)
    throws IOException, FileCorruptException
  {
    addRules(inputStreamToString(paramInputStream));
  }
  
  public native void addRules(String paramString);
  
  public native void addRulesFromFile(String paramString);
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public String getPredictionTrigger()
  {
    return getPredictionTriggerString();
  }
  
  public native String getWordSeparator(String paramString);
  
  public Punctuator.Action[] punctuate(String paramString1, String paramString2, String paramString3)
  {
    int[] arrayOfInt = punctuateInt(paramString1, paramString2, paramString3);
    Punctuator.Action[] arrayOfAction = new Punctuator.Action[arrayOfInt.length];
    for (int i = 0; i < arrayOfAction.length; i++) {
      arrayOfAction[i] = Punctuator.Action.values()[arrayOfInt[i]];
    }
    return arrayOfAction;
  }
  
  public native void removeRulesWithID(String paramString);
  
  public native void resetRules();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.PunctuatorImpl
 * JD-Core Version:    0.7.0.1
 */