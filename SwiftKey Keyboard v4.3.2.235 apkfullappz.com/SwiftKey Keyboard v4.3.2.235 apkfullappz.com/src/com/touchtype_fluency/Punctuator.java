package com.touchtype_fluency;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public abstract interface Punctuator
{
  public abstract void addRules(InputStream paramInputStream)
    throws IOException, FileCorruptException, DependencyNotFoundException;
  
  public abstract void addRules(String paramString)
    throws FileCorruptException, DependencyNotFoundException;
  
  public abstract void addRulesFromFile(String paramString)
    throws FileNotFoundException, FileCorruptException, DependencyNotFoundException;
  
  public abstract String getPredictionTrigger();
  
  public abstract String getWordSeparator(String paramString);
  
  public abstract Action[] punctuate(String paramString1, String paramString2, String paramString3);
  
  public abstract void removeRulesWithID(String paramString);
  
  public abstract void resetRules();
  
  public static enum Action
  {
    static
    {
      INS_LANG_SPECIFIC_SPACE = new Action("INS_LANG_SPECIFIC_SPACE", 2);
      INS_PREDICTION = new Action("INS_PREDICTION", 3);
      INS_FOCUS = new Action("INS_FOCUS", 4);
      DUMB_MODE = new Action("DUMB_MODE", 5);
      Action[] arrayOfAction = new Action[6];
      arrayOfAction[0] = BACKSPACE;
      arrayOfAction[1] = INS_SPACE;
      arrayOfAction[2] = INS_LANG_SPECIFIC_SPACE;
      arrayOfAction[3] = INS_PREDICTION;
      arrayOfAction[4] = INS_FOCUS;
      arrayOfAction[5] = DUMB_MODE;
      $VALUES = arrayOfAction;
    }
    
    private Action() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Punctuator
 * JD-Core Version:    0.7.0.1
 */