package com.touchtype_fluency.internal;

import com.touchtype_fluency.Parameter;

public class ParameterImpl
  implements Parameter
{
  private long peer;
  
  private ParameterImpl(long paramLong)
  {
    this.peer = paramLong;
  }
  
  public static native void initIDs();
  
  public native Object defaultValue();
  
  public native Object getValue();
  
  public native Class getValueType();
  
  public native Object maxValue();
  
  public native Object minValue();
  
  public native void reset();
  
  public native void setValue(Object paramObject)
    throws ClassCastException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.internal.ParameterImpl
 * JD-Core Version:    0.7.0.1
 */