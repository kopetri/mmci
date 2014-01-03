package org.spongycastle.util.test;

public abstract interface TestResult
{
  public abstract Throwable getException();
  
  public abstract boolean isSuccessful();
  
  public abstract String toString();
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.TestResult
 * JD-Core Version:    0.7.0.1
 */