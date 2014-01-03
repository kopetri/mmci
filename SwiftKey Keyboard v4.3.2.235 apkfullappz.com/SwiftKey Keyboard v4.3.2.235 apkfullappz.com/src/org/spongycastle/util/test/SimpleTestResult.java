package org.spongycastle.util.test;

public class SimpleTestResult
  implements TestResult
{
  private static final String SEPARATOR = System.getProperty("line.separator");
  private Throwable exception;
  private String message;
  private boolean success;
  
  public SimpleTestResult(boolean paramBoolean, String paramString)
  {
    this.success = paramBoolean;
    this.message = paramString;
  }
  
  public SimpleTestResult(boolean paramBoolean, String paramString, Throwable paramThrowable)
  {
    this.success = paramBoolean;
    this.message = paramString;
    this.exception = paramThrowable;
  }
  
  public static TestResult failed(Test paramTest, String paramString)
  {
    return new SimpleTestResult(false, paramTest.getName() + ": " + paramString);
  }
  
  public static TestResult failed(Test paramTest, String paramString, Object paramObject1, Object paramObject2)
  {
    return failed(paramTest, paramString + SEPARATOR + "Expected: " + paramObject1 + SEPARATOR + "Found   : " + paramObject2);
  }
  
  public static TestResult failed(Test paramTest, String paramString, Throwable paramThrowable)
  {
    return new SimpleTestResult(false, paramTest.getName() + ": " + paramString, paramThrowable);
  }
  
  public static String failedMessage(String paramString1, String paramString2, String paramString3, String paramString4)
  {
    StringBuffer localStringBuffer = new StringBuffer(paramString1);
    localStringBuffer.append(" failing ").append(paramString2);
    localStringBuffer.append(SEPARATOR).append("    expected: ").append(paramString3);
    localStringBuffer.append(SEPARATOR).append("    got     : ").append(paramString4);
    return localStringBuffer.toString();
  }
  
  public static TestResult successful(Test paramTest, String paramString)
  {
    return new SimpleTestResult(true, paramTest.getName() + ": " + paramString);
  }
  
  public Throwable getException()
  {
    return this.exception;
  }
  
  public boolean isSuccessful()
  {
    return this.success;
  }
  
  public String toString()
  {
    return this.message;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.util.test.SimpleTestResult
 * JD-Core Version:    0.7.0.1
 */