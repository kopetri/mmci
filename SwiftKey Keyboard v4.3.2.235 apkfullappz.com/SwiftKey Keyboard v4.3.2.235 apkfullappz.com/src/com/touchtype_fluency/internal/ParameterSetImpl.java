package com.touchtype_fluency.internal;

import com.touchtype_fluency.Parameter;
import com.touchtype_fluency.ParameterSet;
import java.io.IOException;

public class ParameterSetImpl
  implements ParameterSet, Disposable
{
  private long peer;
  
  private ParameterSetImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public static native void initIDs();
  
  public void dispose()
  {
    this.peer = 0L;
  }
  
  public native Parameter get(String paramString1, String paramString2);
  
  public native String[] getProperties(String paramString);
  
  public native String[] getTargets();
  
  public native void loadFile(String paramString)
    throws IOException;
  
  public native void reset();
  
  public native void reset(String paramString)
    throws IllegalStateException;
  
  public native void saveFile(String paramString)
    throws IOException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.ParameterSetImpl
 * JD-Core Version:    0.7.0.1
 */