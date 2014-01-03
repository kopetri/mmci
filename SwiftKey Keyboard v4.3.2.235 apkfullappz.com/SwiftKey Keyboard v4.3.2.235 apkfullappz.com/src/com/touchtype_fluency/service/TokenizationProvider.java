package com.touchtype_fluency.service;

import com.touchtype_fluency.ContextCurrentWord;
import com.touchtype_fluency.Sequence;

public abstract interface TokenizationProvider
{
  public abstract ContextCurrentWord getContextCurrentWord(String paramString);
  
  public abstract Sequence getSequence(String paramString);
  
  public static final class ContextCurrentWord
  {
    private final Sequence mContext;
    private final String mCurrentWord;
    
    public ContextCurrentWord(ContextCurrentWord paramContextCurrentWord)
    {
      this.mCurrentWord = paramContextCurrentWord.getCurrentWord();
      this.mContext = paramContextCurrentWord.getContext();
    }
    
    public ContextCurrentWord(String paramString, Sequence paramSequence)
    {
      this.mCurrentWord = paramString;
      this.mContext = paramSequence;
    }
    
    public Sequence getContext()
    {
      return this.mContext;
    }
    
    public String getCurrentWord()
    {
      return this.mCurrentWord;
    }
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.TokenizationProvider
 * JD-Core Version:    0.7.0.1
 */