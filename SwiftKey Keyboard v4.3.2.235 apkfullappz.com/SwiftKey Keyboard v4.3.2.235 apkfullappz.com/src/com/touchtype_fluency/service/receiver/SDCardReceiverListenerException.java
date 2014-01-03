package com.touchtype_fluency.service.receiver;

public class SDCardReceiverListenerException
  extends Exception
{
  private static final long serialVersionUID = 1L;
  private String mMessage = null;
  
  public SDCardReceiverListenerException()
  {
    this.mMessage = "Cannot assign more than one listener at a time";
  }
  
  public SDCardReceiverListenerException(String paramString)
  {
    this.mMessage = paramString;
  }
  
  public String getMessage()
  {
    return this.mMessage;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype_fluency.service.receiver.SDCardReceiverListenerException
 * JD-Core Version:    0.7.0.1
 */