package com.touchtype_fluency;

public abstract interface SentenceSegmenter
{
  public abstract boolean isSentenceInitial(Sequence paramSequence);
  
  public abstract boolean isSentenceInitial(Sequence paramSequence, String paramString);
  
  public abstract int[] split(Sequence paramSequence);
  
  public abstract int[] split(Sequence paramSequence, String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.SentenceSegmenter
 * JD-Core Version:    0.7.0.1
 */