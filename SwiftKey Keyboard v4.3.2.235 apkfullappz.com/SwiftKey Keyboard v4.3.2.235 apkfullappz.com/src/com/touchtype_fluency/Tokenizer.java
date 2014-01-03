package com.touchtype_fluency;

public abstract interface Tokenizer
{
  public abstract Sequence split(String paramString);
  
  public abstract Sequence split(String paramString, Mode paramMode);
  
  public abstract SequenceTermMap splitAt(String paramString, int paramInt1, int paramInt2, int paramInt3, Mode paramMode)
    throws IllegalArgumentException;
  
  public abstract ContextCurrentWord splitContextCurrentWord(String paramString, int paramInt);
  
  public abstract ContextCurrentWord splitContextCurrentWord(String paramString, int paramInt, boolean paramBoolean);
  
  public static enum Mode
  {
    static
    {
      Mode[] arrayOfMode = new Mode[2];
      arrayOfMode[0] = DONT_INCLUDE_WHITESPACE;
      arrayOfMode[1] = INCLUDE_WHITESPACE;
      $VALUES = arrayOfMode;
    }
    
    private Mode() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Tokenizer
 * JD-Core Version:    0.7.0.1
 */