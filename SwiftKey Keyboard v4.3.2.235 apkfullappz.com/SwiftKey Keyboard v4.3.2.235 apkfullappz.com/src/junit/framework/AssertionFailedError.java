package junit.framework;

public final class AssertionFailedError
  extends AssertionError
{
  public AssertionFailedError() {}
  
  public AssertionFailedError(String paramString)
  {
    super(defaultString(paramString));
  }
  
  private static String defaultString(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    return paramString;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     junit.framework.AssertionFailedError
 * JD-Core Version:    0.7.0.1
 */