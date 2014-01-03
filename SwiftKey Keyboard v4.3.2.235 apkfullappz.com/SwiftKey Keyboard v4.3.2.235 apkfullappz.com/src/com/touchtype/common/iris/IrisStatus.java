package com.touchtype.common.iris;

 enum IrisStatus
{
  static
  {
    SERVER_TRANSIENT_ERROR = new IrisStatus("SERVER_TRANSIENT_ERROR", 2);
    IrisStatus[] arrayOfIrisStatus = new IrisStatus[3];
    arrayOfIrisStatus[0] = SERVER_OK;
    arrayOfIrisStatus[1] = SERVER_UNRECOVERABLE_ERROR;
    arrayOfIrisStatus[2] = SERVER_TRANSIENT_ERROR;
    $VALUES = arrayOfIrisStatus;
  }
  
  private IrisStatus() {}
  
  public static IrisStatus statusCodeToIrisStatus(int paramInt)
  {
    switch (paramInt / 100)
    {
    case 3: 
    case 4: 
    default: 
      return SERVER_UNRECOVERABLE_ERROR;
    case 2: 
      return SERVER_OK;
    }
    return SERVER_TRANSIENT_ERROR;
  }
  
  public boolean shouldReschedule()
  {
    return this == SERVER_TRANSIENT_ERROR;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.common.iris.IrisStatus
 * JD-Core Version:    0.7.0.1
 */