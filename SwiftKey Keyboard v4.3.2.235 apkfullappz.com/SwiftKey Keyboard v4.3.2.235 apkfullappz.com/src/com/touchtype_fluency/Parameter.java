package com.touchtype_fluency;

public abstract interface Parameter
{
  public abstract Object defaultValue();
  
  public abstract Object getValue();
  
  public abstract Class getValueType();
  
  public abstract Object maxValue();
  
  public abstract Object minValue();
  
  public abstract void reset();
  
  public abstract void setValue(Object paramObject)
    throws ClassCastException, ParameterOutOfRangeException;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.Parameter
 * JD-Core Version:    0.7.0.1
 */