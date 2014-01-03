package com.touchtype_fluency.service;

public abstract interface DownloadListener
{
  public abstract void onCompletion(Result paramResult);
  
  public static enum Result
  {
    static
    {
      CANCELLED = new Result("CANCELLED", 1);
      NETWORK_FAILURE = new Result("NETWORK_FAILURE", 2);
      CONFIG_FAILURE = new Result("CONFIG_FAILURE", 3);
      Result[] arrayOfResult = new Result[4];
      arrayOfResult[0] = SUCCESS;
      arrayOfResult[1] = CANCELLED;
      arrayOfResult[2] = NETWORK_FAILURE;
      arrayOfResult[3] = CONFIG_FAILURE;
      $VALUES = arrayOfResult;
    }
    
    private Result() {}
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.DownloadListener
 * JD-Core Version:    0.7.0.1
 */