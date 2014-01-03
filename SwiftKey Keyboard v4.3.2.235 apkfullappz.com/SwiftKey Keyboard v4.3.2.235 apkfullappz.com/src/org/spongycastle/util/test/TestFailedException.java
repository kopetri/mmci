package org.spongycastle.util.test;

public class TestFailedException
  extends RuntimeException
{
  private TestResult _result;
  
  public TestFailedException(TestResult paramTestResult)
  {
    this._result = paramTestResult;
  }
  
  public TestResult getResult()
  {
    return this._result;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.TestFailedException
 * JD-Core Version:    0.7.0.1
 */