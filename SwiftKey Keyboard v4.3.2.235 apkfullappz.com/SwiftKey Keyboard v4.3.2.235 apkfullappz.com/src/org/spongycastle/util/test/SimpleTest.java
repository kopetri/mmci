package org.spongycastle.util.test;

import java.io.PrintStream;
import org.spongycastle.util.Arrays;

public abstract class SimpleTest
  implements Test
{
  protected static void runTest(Test paramTest)
  {
    runTest(paramTest, System.out);
  }
  
  protected static void runTest(Test paramTest, PrintStream paramPrintStream)
  {
    TestResult localTestResult = paramTest.perform();
    paramPrintStream.println(localTestResult.toString());
    if (localTestResult.getException() != null) {
      localTestResult.getException().printStackTrace(paramPrintStream);
    }
  }
  
  private TestResult success()
  {
    return SimpleTestResult.successful(this, "Okay");
  }
  
  protected boolean areEqual(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2)
  {
    return Arrays.areEqual(paramArrayOfByte1, paramArrayOfByte2);
  }
  
  protected void fail(String paramString)
  {
    throw new TestFailedException(SimpleTestResult.failed(this, paramString));
  }
  
  protected void fail(String paramString, Object paramObject1, Object paramObject2)
  {
    throw new TestFailedException(SimpleTestResult.failed(this, paramString, paramObject1, paramObject2));
  }
  
  protected void fail(String paramString, Throwable paramThrowable)
  {
    throw new TestFailedException(SimpleTestResult.failed(this, paramString, paramThrowable));
  }
  
  public abstract String getName();
  
  public TestResult perform()
  {
    try
    {
      performTest();
      TestResult localTestResult = success();
      return localTestResult;
    }
    catch (TestFailedException localTestFailedException)
    {
      return localTestFailedException.getResult();
    }
    catch (Exception localException)
    {
      return SimpleTestResult.failed(this, "Exception: " + localException, localException);
    }
  }
  
  public abstract void performTest()
    throws Exception;
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.SimpleTest
 * JD-Core Version:    0.7.0.1
 */