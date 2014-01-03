package com.touchtype_fluency;

public class ContextCurrentWord
{
  private final Sequence context;
  private final String currentWord;
  
  public ContextCurrentWord(Sequence paramSequence, String paramString)
  {
    this.context = paramSequence;
    this.currentWord = paramString;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool1 = paramObject instanceof ContextCurrentWord;
    boolean bool2 = false;
    if (bool1)
    {
      ContextCurrentWord localContextCurrentWord = (ContextCurrentWord)paramObject;
      boolean bool3 = this.context.equals(localContextCurrentWord.context);
      bool2 = false;
      if (bool3)
      {
        boolean bool4 = this.currentWord.equals(localContextCurrentWord.currentWord);
        bool2 = false;
        if (bool4) {
          bool2 = true;
        }
      }
    }
    return bool2;
  }
  
  public Sequence getContext()
  {
    return this.context;
  }
  
  public String getCurrentWord()
  {
    return this.currentWord;
  }
  
  public int hashCode()
  {
    return 149 * this.context.hashCode() + this.currentWord.hashCode();
  }
  
  public String toString()
  {
    return this.context.toString() + " " + this.currentWord;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.ContextCurrentWord
 * JD-Core Version:    0.7.0.1
 */