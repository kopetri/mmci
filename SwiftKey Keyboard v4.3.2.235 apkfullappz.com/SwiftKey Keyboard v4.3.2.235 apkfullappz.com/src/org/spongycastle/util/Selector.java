package org.spongycastle.util;

public abstract interface Selector
  extends Cloneable
{
  public abstract Object clone();
  
  public abstract boolean match(Object paramObject);
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.Selector
 * JD-Core Version:    0.7.0.1
 */