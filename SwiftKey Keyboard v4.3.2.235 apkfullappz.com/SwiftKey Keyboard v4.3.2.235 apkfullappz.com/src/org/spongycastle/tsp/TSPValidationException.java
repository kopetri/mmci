package org.spongycastle.tsp;

public class TSPValidationException
  extends TSPException
{
  private int failureCode = -1;
  
  public TSPValidationException(String paramString)
  {
    super(paramString);
  }
  
  public TSPValidationException(String paramString, int paramInt)
  {
    super(paramString);
    this.failureCode = paramInt;
  }
  
  public int getFailureCode()
  {
    return this.failureCode;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     org.spongycastle.tsp.TSPValidationException
 * JD-Core Version:    0.7.0.1
 */