package com.touchtype_fluency.internal;

import com.touchtype_fluency.ContextCurrentWord;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.SequenceTermMap;
import com.touchtype_fluency.SwiftKeySDK;
import com.touchtype_fluency.Tokenizer;
import com.touchtype_fluency.Tokenizer.Mode;

public class TokenizerImpl
  implements Tokenizer, Disposable
{
  private long peer;
  
  static {}
  
  private TokenizerImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public static native void initIDs();
  
  public static native ContextCurrentWord legacyGetContextCurrentWord(String paramString, int paramInt);
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public Sequence split(String paramString)
  {
    return split(paramString, Tokenizer.Mode.DONT_INCLUDE_WHITESPACE);
  }
  
  public native Sequence split(String paramString, Tokenizer.Mode paramMode);
  
  public native SequenceTermMap splitAt(String paramString, int paramInt1, int paramInt2, int paramInt3, Tokenizer.Mode paramMode);
  
  public native ContextCurrentWord splitContextCurrentWord(String paramString, int paramInt);
  
  public native ContextCurrentWord splitContextCurrentWord(String paramString, int paramInt, boolean paramBoolean);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.TokenizerImpl
 * JD-Core Version:    0.7.0.1
 */