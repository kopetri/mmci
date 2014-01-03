package com.touchtype.sync.client;

public class Device
{
  private String description;
  private String id;
  private Long lastActivityTime;
  
  public Device(String paramString1, String paramString2, Long paramLong)
  {
    this.id = paramString1;
    this.description = paramString2;
    this.lastActivityTime = paramLong;
  }
  
  final void a(String paramString)
  {
    this.description = paramString;
  }
  
  public String getDescription()
  {
    return this.description;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public Long getLastActivityTime()
  {
    return this.lastActivityTime;
  }
}


/* Location:           C:\Users\sebastian\Mobile Mensch Computer Interaktion\02\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\SwiftKey Keyboard v4.3.2.235 apkfullappz.com\dex2jar-0.0.9.15\classes-dex2jar.jar
 * Qualified Name:     com.touchtype.sync.client.Device
 * JD-Core Version:    0.7.0.1
 */