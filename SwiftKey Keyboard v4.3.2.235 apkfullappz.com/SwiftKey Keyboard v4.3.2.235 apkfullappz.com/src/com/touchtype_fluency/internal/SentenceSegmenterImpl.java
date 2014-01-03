package com.touchtype_fluency.internal;

import com.touchtype_fluency.SentenceSegmenter;
import com.touchtype_fluency.Sequence;
import com.touchtype_fluency.SwiftKeySDK;

public class SentenceSegmenterImpl
  implements SentenceSegmenter, Disposable
{
  private long peer;
  
  static {}
  
  private SentenceSegmenterImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public static native void initIDs();
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public native boolean isSentenceInitial(Sequence paramSequence);
  
  public native boolean isSentenceInitial(Sequence paramSequence, String paramString);
  
  public native int[] split(Sequence paramSequence);
  
  public native int[] split(Sequence paramSequence, String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.SentenceSegmenterImpl
 * JD-Core Version:    0.7.0.1
 */