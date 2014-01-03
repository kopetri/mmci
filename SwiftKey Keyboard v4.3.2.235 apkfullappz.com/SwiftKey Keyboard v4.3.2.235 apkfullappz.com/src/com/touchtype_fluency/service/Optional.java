package com.touchtype_fluency.service;

class Optional<T>
{
  private T value;
  
  public Optional()
  {
    this.value = null;
  }
  
  public Optional(T paramT)
  {
    this.value = paramT;
  }
  
  public void clear()
  {
    this.value = null;
  }
  
  public T getValue()
  {
    if (!isDefined()) {
      throw new NullPointerException();
    }
    return this.value;
  }
  
  public boolean isDefined()
  {
    return this.value != null;
  }
  
  public void setValue(T paramT)
  {
    this.value = paramT;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.Optional
 * JD-Core Version:    0.7.0.1
 */