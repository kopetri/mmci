package com.touchtype_fluency;

public class WordBreakIterator
{
  public static final int DONE = -1;
  protected long peer;
  
  static {}
  
  public WordBreakIterator()
  {
    createPeer();
  }
  
  private native void createPeer();
  
  private native void destroyPeer();
  
  static native void initIDs();
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  protected void finalize()
  {
    destroyPeer();
  }
  
  public native int first();
  
  public native int last();
  
  public native int next();
  
  public native int previous();
  
  public native void setText(String paramString);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.WordBreakIterator
 * JD-Core Version:    0.7.0.1
 */