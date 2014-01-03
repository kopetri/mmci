package org.spongycastle.i18n.filter;

public class UntrustedInput
{
  protected Object input;
  
  public UntrustedInput(Object paramObject)
  {
    this.input = paramObject;
  }
  
  public Object getInput()
  {
    return this.input;
  }
  
  public String getString()
  {
    return this.input.toString();
  }
  
  public String toString()
  {
    return this.input.toString();
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.i18n.filter.UntrustedInput
 * JD-Core Version:    0.7.0.1
 */